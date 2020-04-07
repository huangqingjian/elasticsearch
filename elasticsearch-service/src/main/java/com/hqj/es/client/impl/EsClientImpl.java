package com.hqj.es.client.impl;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.hqj.common.constant.Constant;
import com.hqj.common.exception.ServiceException;
import com.hqj.es.bean.PageBean;
import com.hqj.es.client.EsClient;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.client.indices.PutMappingRequest;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;

/**
 * es客户端
 */
@Component
public class EsClientImpl implements EsClient {
    private static final Logger log = LoggerFactory.getLogger(EsClientImpl.class);
    private static final String CREATE_INDEX_FAIL = "%s index fail to create";
    private static final String CREATE_UPDATE_INDEX_TYPE_FAIL = "%s index type fail to create or update";
    private static final String INDEX_NOT_EXISTS = "%s index not exists";
    private static final String DELETE_INDEX_FAIL = "%s index fail to delete";

    @Autowired
    private RestHighLevelClient client;

    /**
     * 创建或更新索引
     *
     * @param indexName 索引名称
     * @param mappering 类型映射
     * @param shards    分片数
     * @param replicas  副本数
     */
    @Override
    public void createOrUpdateIndex(String indexName, String mappering, Integer shards, Integer replicas) {
        log.info("the request parameter of createOrUpdateIndex in {}: {} {}", this.getClass().getSimpleName(), indexName, mappering);
        try {
            // 检查index是否已存在
            if (!this.indexExist(indexName)) {
                CreateIndexRequest request = new CreateIndexRequest(indexName);
                buildSetting(request, shards, replicas);
                if(!StringUtils.isEmpty(mappering)) {
                    request.mapping(mappering, XContentType.JSON);
                }
                CreateIndexResponse response = client.indices().create(request, RequestOptions.DEFAULT);
                if (!response.isAcknowledged()) {
                    throw new ServiceException(String.format(CREATE_INDEX_FAIL, indexName));
                }
            } else {
                // 创建或更新映射
                if (!StringUtils.isEmpty(mappering)) {
                    PutMappingRequest request = new PutMappingRequest(indexName);
                    request.source(mappering, XContentType.JSON);
                    AcknowledgedResponse response = client.indices().putMapping(request, RequestOptions.DEFAULT);
                    if(!response.isAcknowledged()) {
                        throw new ServiceException(String.format(CREATE_UPDATE_INDEX_TYPE_FAIL, indexName));
                    }
                }
            }
        } catch (Exception e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /**
     * 删除索引
     *
     * @param indexName 索引名称
     * @return
     */
    @Override
    public void deleteIndex(String indexName) {
        log.info("the request parameter of deleteIndex in {}: {}", this.getClass().getSimpleName(), indexName);
        try {
            // 检查index是否已存在
            if (this.indexExist(indexName)) {
                client.indices().delete(new DeleteIndexRequest(indexName), RequestOptions.DEFAULT);
            }
        } catch (Exception e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /**
     * 数据添加，指定ID
     *
     * @param indexName 索引名称
     * @param id        id
     * @param objectMap 待添加数据
     * @return
     */
    @Override
    public void saveOrUpdate(String indexName, String id, Map<String, Object> objectMap) {
        log.info("the request parameter of addData in {}: {} {} {}", this.getClass().getSimpleName(), indexName, id, JSON.toJSONString(objectMap));
        try {
            IndexRequest request = new IndexRequest(indexName);
            request.id(id);
            request.source(JSON.toJSONString(objectMap), XContentType.JSON);
            client.index(request, RequestOptions.DEFAULT);
        } catch (Exception e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /**
     * 通过ID删除数据
     *
     * @param indexName 索引名称
     * @param id        id
     */
    @Override
    public void deleteById(String indexName, String id) {
        log.info("the request parameter of deleteDataById in {}: {} {}", this.getClass().getSimpleName(), indexName, id);
        deleteByIds(indexName, Lists.newArrayList(id));
    }

    /**
     * 通过ID删除数据
     *
     * @param indexName 索引名称
     * @param ids       ids
     */
    @Override
    public void deleteByIds(String indexName, List<String> ids) {
        log.info("the request parameter of deleteDataById in {}: {} {}", this.getClass().getSimpleName(), indexName, JSON.toJSONString(ids));
        try {
            BulkRequest request = new BulkRequest();
            ids.forEach(item -> request.add(new DeleteRequest(indexName, item.toString())));
            client.bulk(request, RequestOptions.DEFAULT);
        } catch (Exception e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /** 断某个index是否存在
     *
     * @param indexName 索引名
     *
     * @return
     * @throws
     */
    @Override
    public boolean indexExist(String indexName) {
        try {
            GetIndexRequest request = new GetIndexRequest(indexName);
            return client.indices().exists(request, RequestOptions.DEFAULT);
        } catch (Exception e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /**
     * 通过ID获取数据
     *
     * @param indexName 索引名称
     * @param id        id
     * @return
     */
    @Override
    public Map<String, Object> getById(String indexName, String id) {
        log.info("the request parameter of getById in {}: {} {}", this.getClass().getSimpleName(), indexName, id);
        try {
            GetRequest request = new GetRequest(indexName, id);
            GetResponse response = client.get(request, RequestOptions.DEFAULT);
            return response.getSourceAsMap();
        } catch (Exception e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /**
     * 通过ID批量获取数据
     *
     * @param indexName 索引名称
     * @param ids       ids
     * @return
     */
    @Override
    public List<Map<String, Object>> getByIds(String indexName, List<String> ids) {
        log.info("the request parameter of getByIds in {}: {} {}", this.getClass().getSimpleName(), indexName, JSON.toJSONString(ids));
        try {
            SearchRequest searchRequest = new SearchRequest(indexName);
            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
            searchSourceBuilder.query(QueryBuilders.termsQuery("_id", ids));
            searchRequest.source(searchSourceBuilder);
            SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
            List<Map<String, Object>> sourceMapList = Lists.newLinkedList();
            if (searchResponse.status().getStatus() == 200) {
                // 解析对象
                SearchHit [] searchHits = searchResponse.getHits().getHits();
                if(searchHits != null && searchHits.length > 0) {
                    for(SearchHit searchHit : searchHits) {
                        sourceMapList.add(searchHit.getSourceAsMap());
                    }
                }
            }
            return sourceMapList;
        } catch (Exception e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /**
     * 使用分词查询，分页
     *
     * @param indexName      索引名称
     * @param pageNum        当前页
     * @param pageSize       每页显示条数
     * @param query          查询条件
     * @return
     */
    @Override
    public PageBean searchByPage(String indexName, int pageNum, int pageSize, QueryBuilder query) {
        return searchByPage(indexName, pageNum, pageSize, query, null, null, null);
    }

    /**
     * 使用分词查询，分页
     *
     * @param indexName      索引名称
     * @param pageNum        当前页
     * @param pageSize       每页显示条数
     * @param query          查询条件
     * @param sortField      排序字段
     * @return
     */
    @Override
    public PageBean searchByPage(String indexName, int pageNum, int pageSize, QueryBuilder query, String sortField) {
        return searchByPage(indexName, pageNum, pageSize, query, null, sortField, null);
    }

    /**
     * 使用分词查询，分页
     *
     * @param indexName      索引名称
     * @param pageNum        当前页
     * @param pageSize       每页显示条数
     * @param query          查询条件
     * @param fields         需要显示的字段，逗号分隔（缺省为全部字段）
     * @param sortField      排序字段
     * @return
     */
    @Override
    public PageBean searchByPage(String indexName, int pageNum, int pageSize, QueryBuilder query, String fields, String sortField) {
        return searchByPage(indexName, pageNum, pageSize, query, fields, sortField, null);
    }

    /**
     * 使用分词查询，高亮、排序、分页
     *
     * @param indexName      索引名称
     * @param pageNum        当前页
     * @param pageSize       每页显示条数
     * @param query          查询条件
     * @param fields         需要显示的字段，逗号分隔（缺省为全部字段）
     * @param sortField      排序字段
     * @param highlightField 高亮字段
     * @return
     */
    @Override
    public PageBean searchByPage(String indexName, int pageNum, int pageSize, QueryBuilder query, String fields, String sortField, String highlightField) {
        try {
            SearchRequest searchRequest = new SearchRequest(indexName);
            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
            // 需要显示的字段，逗号分隔（缺省为全部字段）
            if (!StringUtils.isEmpty(fields)) {
                searchSourceBuilder.fetchSource(fields.split(","), null);
            }
            // 排序字段
            if (!StringUtils.isEmpty(sortField)) {
                searchSourceBuilder.sort(new FieldSortBuilder(sortField).order(SortOrder.DESC));
            }
            // 高亮（xxx=111,aaa=222）
            if (!StringUtils.isEmpty(highlightField)) {
                HighlightBuilder highlightBuilder = new HighlightBuilder();
                // 设置前缀
                highlightBuilder.preTags("<span style='color:red' >");
                // 设置后缀
                highlightBuilder.postTags("</span>");
                String[] highlights = highlightField.split(",");
                for(String highlight : highlights) {
                    HighlightBuilder.Field highlightTitle = new HighlightBuilder.Field(highlight);
                    // 荧光笔类型
                    highlightTitle.highlighterType("unified");
                    // 设置高亮字段
                    highlightBuilder.field(highlightTitle);
                }
                highlightBuilder.requireFieldMatch(false);
                searchSourceBuilder.highlighter(highlightBuilder);
            }
            // 设置是否按查询匹配度排序
            searchSourceBuilder.explain(true);
            if (pageNum <= 0) {
                pageNum = 1;
            }
            searchSourceBuilder.query(QueryBuilders.matchAllQuery());
            searchSourceBuilder.query(query);
            // 分页应用
            searchSourceBuilder.from((pageNum - 1) * pageSize).size(pageSize);
            searchSourceBuilder.trackTotalHits(true);
            // 打印的内容 可以在Elasticsearch head和Kibana上执行查询
            log.info("\n{}", searchSourceBuilder);
            // 执行搜索,返回搜索响应信息
            searchRequest.source(searchSourceBuilder);
            SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
            long totalHits = searchResponse.getHits().getTotalHits().value;
            long length = searchResponse.getHits().getHits().length;
            log.info("total: {}, length: {}", totalHits, length);
            if (searchResponse.status().getStatus() == 200) {
                // 解析对象
                List<Map<String, Object>> sourceList = setSearchResponse(searchResponse, highlightField);
                return new PageBean(pageNum, pageSize, (int) totalHits, sourceList);
            }
            return null;
        } catch (Exception e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /**
     * 高亮结果集 特殊处理
     *
     * @param searchResponse 搜索的结果集
     * @param highlightField 高亮字段
     */
    private List<Map<String, Object>> setSearchResponse(SearchResponse searchResponse, String highlightField) {
        List<Map<String, Object>> sourceList = Lists.newArrayList();
        for (SearchHit searchHit : searchResponse.getHits().getHits()) {
            Map<String, Object> resultMap = getResultMap(searchHit, highlightField);
            sourceList.add(resultMap);
        }
        return sourceList;
    }

    /**
     * 获取高亮结果集
     *
     * @param hit
     * @param highlightField
     * @return
     */
    private Map<String, Object> getResultMap(SearchHit hit, String highlightField) {
        if (!StringUtils.isEmpty(highlightField)) {
            HighlightField hf = hit.getHighlightFields().get(highlightField);
            if(hf != null) {
                Text[] texts = hf.getFragments();
                if (texts != null) {
                    String hightStr = "";
                    for (Text str : texts) {
                        hightStr += str.string();
                    }
                    // 遍历高亮结果集，覆盖正常结果集
                    hit.getSourceAsMap().put(highlightField, hightStr);
                }
            }
        }
        return hit.getSourceAsMap();
    }

    /**
     * 设置分片
     *
     * @param request
     * @param shards
     * @param replicas
     */
    private void buildSetting(CreateIndexRequest request, Integer shards, Integer replicas) {
        request.settings(Settings.builder().put("index.number_of_shards", (shards == null || shards == 0) ? Constant.DEFAULT_SHARDS : shards)
                .put("index.number_of_replicas",(replicas == null || replicas == 0) ? Constant.DEFAULT_REPLICAS : replicas));

    }

}
