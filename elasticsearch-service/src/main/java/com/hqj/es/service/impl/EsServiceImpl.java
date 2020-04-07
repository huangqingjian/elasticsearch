package com.hqj.es.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.hqj.es.client.EsClient;
import com.hqj.es.domain.*;
import com.hqj.es.dto.*;
import com.hqj.es.enums.DataType;
import com.hqj.es.enums.SyncType;
import com.hqj.es.mapper.*;
import com.hqj.utils.BeanUtils;
import com.hqj.utils.JsoupUtils;
import com.hqj.common.enums.YesNo;
import com.hqj.common.exception.ServiceException;
import com.hqj.common.dto.PageDTO;
import com.hqj.es.bean.PageBean;
import com.hqj.es.bean.IndexQueryBean;
import com.hqj.es.enums.MatchType;
import com.hqj.es.service.EsService;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * es服务
 */
@Service
public class EsServiceImpl implements EsService {
    private static final Logger log = LoggerFactory.getLogger(EsServiceImpl.class);

    private static final String INDEX_ID_ILLEGAL = "索引ID非法";
    private static final String INDEX_NAME_NOT_NULL = "索引名称不能为空";
    private static final String INDEX_MAPPING_NOT_NULL = "索引映射不能为空";
    private static final String INDEX_MAPPING_NAME_NOT_NULL = "映射字段名称不能为空";
    private static final String INDEX_NOT_EXISTS = "索引%s不存在";
    private static final String INDEX_NAME_EXISTS = "索引%s已存在";
    private static final String INDEX_TYPE_NAME_EXISTS = "索引类型%s已存在";
    private static final String SYNC_SQL_NOT_NULL = "同步SQL不能为空";
    private static final String SYNC_INTER_NOT_NULL = "同步接口不能为空";

    @Autowired
    private IndexMapper indexMapper;
    @Autowired
    private IndexSqlMapper indexSqlMapper;
    @Autowired
    private IndexInterfaceMapper indexInterfaceMapper;
    @Autowired
    private IndexJobMapper indexJobMapper;
    @Autowired
    private IndexJobLogMapper indexJobLogMapper;
    @Autowired
    private IndexPropertyMapper indexPropertyMapper;
    @Autowired
    private EsClient esClient;

    /**
     * 查询索引
     *
     * @param queryDTO
     * @return
     */
    @Override
    public PageDTO listIndex(IndexQueryDTO queryDTO) {
        IndexQueryBean query = BeanUtils.map(queryDTO, IndexQueryBean.class);
        // 分页
        PageHelper.startPage(query.getPageNum(), query.getPageSize());
        List<IndexDomain> indexs = indexMapper.selectByQuery(query);
        PageInfo<IndexDomain> pageInfo = new PageInfo(indexs);
        // 转换为PageDTO
        PageDTO<IndexDTO> page = BeanUtils.map(pageInfo, PageDTO.class);
        page.setList(BeanUtils.mapList(pageInfo.getList(), IndexDTO.class));
        if(!CollectionUtils.isEmpty(page.getList())) {
            List<Long> indexIds = page.getList().stream().map(e -> e.getId()).collect(Collectors.toList());
            List<IndexJobDomain> indexJobs = indexJobMapper.selectByIndexIds(indexIds);
            Map<Long, IndexJobDomain> indexJobMap = Maps.newHashMap();
            if(!CollectionUtils.isEmpty(indexJobs)) {
                indexJobMap = indexJobs.stream().collect(Collectors.toMap(k -> k.getIndexId(), v -> v, (k1, k2) -> k2));
            }
            for(IndexDTO index : page.getList()) {
                IndexJobDomain indexJob = indexJobMap.get(index.getId());
                if(indexJob != null) {
                    index.setJob(BeanUtils.map(indexJob, IndexJobDTO.class));
                }
            }
        }
        return page;
    }

    /**
     * 查询索引任务日志
     *
     * @param queryDTO
     * @return
     */
    @Override
    public PageDTO listIndexJobLog(IndexQueryDTO queryDTO) {
        IndexQueryBean query = BeanUtils.map(queryDTO, IndexQueryBean.class);
        // 分页
        PageHelper.startPage(query.getPageNum(), query.getPageSize());
        List<IndexJobLogDomain> indexJobLogs = indexJobLogMapper.selectByQuery(query);
        PageInfo<IndexDomain> pageInfo = new PageInfo(indexJobLogs);
        // 转换为PageDTO
        PageDTO page = BeanUtils.map(pageInfo, PageDTO.class);
        page.setList(BeanUtils.mapList(pageInfo.getList(), IndexJobLogDTO.class));
        return page;
    }

    /**
     * 获取索引
     *
     * @param id
     * @return
     */
    @Override
    public IndexDTO getSimpleIndex(Long id) {
        if(id == null || id == 0L) {
            return null;
        }
        // 查询索引
        IndexDomain index = indexMapper.selectById(id);
        if(index == null) {
            return null;
        }
        return BeanUtils.map(index, IndexDTO.class);
    }

    /**
     * 获取索引
     *
     * @param id
     * @return
     */
    @Override
    public IndexDTO getIndex(Long id) {
        if(id == null || id == 0L) {
            return null;
        }
        // 查询索引
        IndexDomain index = indexMapper.selectById(id);
        if(index == null) {
            return null;
        }
        IndexDTO indexDTO = BeanUtils.map(index, IndexDTO.class);
        // 查询索引sql
        List<IndexSqlDomain> indexSqls = indexSqlMapper.selectByIndexId(id);
        if(!CollectionUtils.isEmpty(indexSqls)) {
            indexDTO.setSql(BeanUtils.map(indexSqls.get(0), IndexSqlDTO.class));
        }
        // 查询索引接口
        List<IndexInterfaceDomain> indexInterfaces = indexInterfaceMapper.selectByIndexId(id);
        if(!CollectionUtils.isEmpty(indexInterfaces)) {
            indexDTO.setInter(BeanUtils.map(indexInterfaces.get(0), IndexInterfaceDTO.class));
        }
        // 查询索引Job
        List<IndexJobDomain> indexJobs = indexJobMapper.selectByIndexIds(Lists.newArrayList(id));
        if(!CollectionUtils.isEmpty(indexJobs)) {
            indexDTO.setJob(BeanUtils.map(indexJobs.get(0), IndexJobDTO.class));
        }
        // 查询索引属性
        List<IndexPropertyDomain> indexProperties = indexPropertyMapper.selectByIndexId(id);
        if(!CollectionUtils.isEmpty(indexProperties)) {
            List<IndexPropertyDTO> indexPropertyDTOS = BeanUtils.mapList(indexProperties, IndexPropertyDTO.class);
            indexPropertyDTOS.stream().forEach(e -> {
                e.setAnalyzed(DataType.getDataTypeAnayzed(e.getType()));
            });
            indexDTO.setProperties(indexPropertyDTOS);
        }
        return indexDTO;
    }

    /**
     * 创建索引
     *
     * @param reqDTO
     * @return
     */
    @Transactional
    @Override
    public Long createIndex(IndexSaveOrUpdateReqDTO reqDTO) {
        // 索引数据校验
        validateIndex(reqDTO);
        // 保存索引
        Long indexId = saveIndex(reqDTO);
        // 创建es索引
        createIndexInEs(reqDTO);
        return indexId;
    }

    /**
     * 更新索引
     *
     * @param reqDTO
     * @return
     */
    @Override
    public Long updateIndex(IndexSaveOrUpdateReqDTO reqDTO) {
        // 索引有效性校验
        if(reqDTO.getId() == null || reqDTO.getId() == 0L) {
            throw new ServiceException(INDEX_ID_ILLEGAL);
        }
        IndexDomain index1 = indexMapper.selectById(reqDTO.getId());
        Optional.ofNullable(index1).orElseThrow(() -> new ServiceException(String.format(INDEX_ID_ILLEGAL, reqDTO.getName())));
        IndexDomain index2 = BeanUtils.map(reqDTO, IndexDomain.class);
        index2.setUpdateBy(reqDTO.getUserId());
        indexMapper.insertOrUpdate(index2);
        if(reqDTO.getJob() != null) {
            // 更新索引Job
            IndexJobDomain indexJob = BeanUtils.map(reqDTO.getJob(), IndexJobDomain.class);
            indexJob.setUpdateBy(reqDTO.getUserId());
            indexJobMapper.insertOrUpdate(indexJob);
            if(SyncType.SQL.getValue() == reqDTO.getJob().getDataSyncType()) {
                // 索引sql
                if(reqDTO.getSql() != null) {
                    IndexSqlDomain indexSql = BeanUtils.map(reqDTO.getSql(), IndexSqlDomain.class);
                    indexSql.setIndexId(index2.getId());
                    indexSql.setJobId(indexJob.getId());
                    indexSqlMapper.insertOrUpdate(indexSql);
                    // 删除接口
                    indexInterfaceMapper.deleteByIndexId(index2.getId());
                }
            } else if(SyncType.INTERFACE.getValue() == reqDTO.getJob().getDataSyncType()) {
                if (reqDTO.getInter() != null) {
                    IndexInterfaceDomain indexInterface = BeanUtils.map(reqDTO.getInter(), IndexInterfaceDomain.class);
                    indexInterface.setIndexId(index2.getId());
                    indexInterface.setJobId(indexJob.getId());
                    indexInterfaceMapper.insertOrUpdate(indexInterface);
                    // 删除sql
                    indexSqlMapper.deleteByIndexId(index2.getId());
                }
            }
        }
        return reqDTO.getId();
    }

    /**
     * 删除索引
     *
     * @param id
     * @return
     */
    @Override
    @Transactional
    public void deleteIndex(Long id) {
        IndexDomain index = indexMapper.selectById(id);
        if(index == null) {
            return;
        }
        indexMapper.deleteById(id);
        indexJobMapper.deleteByIndexId(id);
        indexPropertyMapper.deleteByIndexId(id);
        esClient.deleteIndex(index.getName());
    }

    /**
     * 索引Job开关
     *
     * @param id
     * @param switchs
     * @return
     */
    @Override
    public void switchIndexJob(Long id, Integer switchs) {
        indexJobMapper.updateSwitchs(id, switchs);
    }


    /**
     * 删除索引任务日志
     *
     * @param id
     * @return
     */
    @Override
    public void deleteIndexJobLog(Long id) {
        indexJobLogMapper.deleteById(id);
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
        return esClient.getById(indexName, id);
    }

    /**
     * 通过ID批量获取数据
     *
     * @param indexName 索引名称
     * @param ids        ids
     * @return
     */
    @Override
    public List<Map<String, Object>> getByIds(String indexName, List<String> ids) {
        return esClient.getByIds(indexName, ids);
    }

    /**
     * es搜索
     *
     * @param searchDTO
     */
    @Override
    public com.hqj.es.dto.PageDTO searchByPage(SearchDTO searchDTO) {
        log.info("the request parameter of searchByPage in {}: {}", this.getClass().getSimpleName(), JSON.toJSONString(searchDTO));
        // 拼接查询条件
        BoolQueryBuilder query = null;
        if(!CollectionUtils.isEmpty(searchDTO.getFields()) || !CollectionUtils.isEmpty(searchDTO.getOrFields())
                || !CollectionUtils.isEmpty(searchDTO.getRangeFields()) || !CollectionUtils.isEmpty(searchDTO.getOrRangeFields())) {
            query = QueryBuilders.boolQuery();
        }
        if(!CollectionUtils.isEmpty(searchDTO.getFields())) {
            for(SearchFieldDTO field : searchDTO.getFields()) {
                if(!StringUtils.isEmpty(field.getFieldValue())) {
                    if(MatchType.ANALYZE_MATCH.getValue().equals(field.getMatchType())) {
                        query.must(QueryBuilders.matchQuery(field.getFieldName(), field.getFieldValue()));
                    } else if(MatchType.PRECISE_MATCH.getValue().equals(field.getMatchType())) {
                        query.must(QueryBuilders.termQuery(field.getFieldName(), field.getFieldValue()));
                    } else if(MatchType.LIKE_MATCH.getValue().equals(field.getMatchType())) {
                        query.must(QueryBuilders.matchPhraseQuery(field.getFieldName(), field.getFieldValue()));
                    } else if(MatchType.WILDCARD.getValue().equals(field.getMatchType())) {
                        query.must(QueryBuilders.wildcardQuery(field.getFieldName(), "*" + field.getFieldValue() + "*"));
                    } else {
                        query.must(QueryBuilders.matchQuery(field.getFieldName(), field.getFieldValue()));
                    }
                }
                if(!CollectionUtils.isEmpty(field.getFieldValues())) {
                    if(MatchType.ANALYZE_MULTI_MATCH.getValue().equals(field.getMatchType())) {
                        query.must(QueryBuilders.multiMatchQuery(field.getFieldName(), field.getFieldValues().toArray(new String[]{})));
                    } else if (MatchType.PRECISE_MULTI_MATCH.getValue().equals(field.getMatchType())) {
                        query.must(QueryBuilders.termsQuery(field.getFieldName(), field.getFieldValues().toArray(new String[]{})));
                    } else {
                        query.must(QueryBuilders.multiMatchQuery(field.getFieldName(), field.getFieldValues().toArray(new String[]{})));
                    }
                }
            }
        }
        if(!CollectionUtils.isEmpty(searchDTO.getOrFields())) {
            BoolQueryBuilder shouldQuery = QueryBuilders.boolQuery();
            for(SearchFieldDTO field : searchDTO.getOrFields()) {
                if(!StringUtils.isEmpty(field.getFieldValue())) {
                    if(MatchType.ANALYZE_MATCH.getValue().equals(field.getMatchType())) {
                        shouldQuery.should(QueryBuilders.matchQuery(field.getFieldName(), field.getFieldValue()));
                    } else if(MatchType.PRECISE_MATCH.getValue().equals(field.getMatchType())) {
                        shouldQuery.should(QueryBuilders.termQuery(field.getFieldName(), field.getFieldValue()));
                    } else if(MatchType.LIKE_MATCH.getValue().equals(field.getMatchType())) {
                        shouldQuery.should(QueryBuilders.matchPhraseQuery(field.getFieldName(), field.getFieldValue()));
                    } else if(MatchType.WILDCARD.getValue().equals(field.getMatchType())) {
                        shouldQuery.should(QueryBuilders.wildcardQuery(field.getFieldName(), "*" + field.getFieldValue() + "*"));
                    } else {
                        shouldQuery.should(QueryBuilders.matchQuery(field.getFieldName(), field.getFieldValue()));
                    }
                }
                if(!CollectionUtils.isEmpty(field.getFieldValues())) {
                    if(MatchType.ANALYZE_MULTI_MATCH.getValue().equals(field.getMatchType())) {
                        shouldQuery.should(QueryBuilders.multiMatchQuery(field.getFieldName(), field.getFieldValues().toArray(new String[]{})));
                    } else if (MatchType.PRECISE_MULTI_MATCH.getValue().equals(field.getMatchType())) {
                        shouldQuery.should(QueryBuilders.termsQuery(field.getFieldName(), field.getFieldValues().toArray(new String[]{})));
                    } else {
                        shouldQuery.should(QueryBuilders.multiMatchQuery(field.getFieldName(), field.getFieldValues().toArray(new String[]{})));
                    }
                }
            }
            query.must(shouldQuery);
        }
        if(!CollectionUtils.isEmpty(searchDTO.getRangeFields())) {
            for(SearchRangeFieldDTO field : searchDTO.getRangeFields()) {
                if(!StringUtils.isEmpty(field.getStartValue()) || !StringUtils.isEmpty(field.getEndValue())) {
                    RangeQueryBuilder rangeQueryBuilder = QueryBuilders.rangeQuery(field.getFieldName());
                    if(!StringUtils.isEmpty(field.getStartValue())) {
                        rangeQueryBuilder.gte(field.getStartValue());
                    }
                    if(!StringUtils.isEmpty(field.getEndValue())) {
                        rangeQueryBuilder.lte(field.getEndValue());
                    }
                    query.must(rangeQueryBuilder);
                }
            }
        }
        if(!CollectionUtils.isEmpty(searchDTO.getOrRangeFields())) {
            BoolQueryBuilder shouldQuery = QueryBuilders.boolQuery();
            for(SearchRangeFieldDTO field : searchDTO.getOrRangeFields()) {
                if(!StringUtils.isEmpty(field.getStartValue()) || !StringUtils.isEmpty(field.getEndValue())) {
                    RangeQueryBuilder rangeQueryBuilder = QueryBuilders.rangeQuery(field.getFieldName());
                    if(!StringUtils.isEmpty(field.getStartValue())) {
                        rangeQueryBuilder.gte(field.getStartValue());
                    }
                    if(!StringUtils.isEmpty(field.getEndValue())) {
                        rangeQueryBuilder.lte(field.getEndValue());
                    }
                    shouldQuery.should(rangeQueryBuilder);
                }
            }
            query.must(shouldQuery);
        }

        // es 查询
        PageBean esPageBean = esClient.searchByPage(searchDTO.getIndexName(),
                searchDTO.getStartPage(),
                searchDTO.getPageSize(),
                query,
                searchDTO.getShowField(),
                searchDTO.getSortField(),
                searchDTO.getHighlightField());
        if(esPageBean == null) {
            return null;
        }
        com.hqj.es.dto.PageDTO pageDTO = new com.hqj.es.dto.PageDTO();
        pageDTO.setPageNum(esPageBean.getCurrentPage());
        pageDTO.setPages(esPageBean.getPageCount());
        pageDTO.setPageSize(esPageBean.getPageSize());
        pageDTO.setTotal(esPageBean.getRecordCount());
        pageDTO.setList(esPageBean.getRecordList());
        return pageDTO;
    }

    /**
     * 获取索引映射
     *
     * @param url
     * @return
     */
    @Override
    public List<IndexMappingDTO> getIndexMapping(String url) {
        String result = JsoupUtils.doHttp(url);
        return JSON.parseArray(result, IndexMappingDTO.class);
    }

    /**
     * 获取索引属性头
     *
     * @return
     */
    @Override
    public List<String> getIndexPropertyHeader() {
        List<String> propertityHeader = Lists.newArrayList();
        propertityHeader.add("字段名称");
        propertityHeader.add("字段类型");
        propertityHeader.add("是否索引");
        propertityHeader.add("索引分词");
        propertityHeader.add("搜索分词");
        return propertityHeader;
    }

    /**
     * 获取索引属性
     *
     * @param indexId
     * @return
     */
    @Override
    public List<List<String>> getIndexProperty(Long indexId) {
        List<IndexPropertyDomain> indexProperties = indexPropertyMapper.selectByIndexId(indexId);
        List<List<String>> properties = Lists.newArrayList();
        if(!CollectionUtils.isEmpty(indexProperties)) {
            for(IndexPropertyDomain ip : indexProperties) {
                List<String> property = Lists.newArrayList();
                property.add(StringUtils.isEmpty(ip.getName()) ? "" : ip.getName());
                property.add(StringUtils.isEmpty(ip.getType()) ? "" : ip.getType());
                property.add((ip.getIndexed() == null ? 0 : ip.getIndexed()) + "");
                property.add(StringUtils.isEmpty(ip.getAnalyzer()) ? "" : ip.getAnalyzer());
                property.add(StringUtils.isEmpty(ip.getSearchAnalyzer()) ? "" : ip.getSearchAnalyzer());
                properties.add(property);
            }
        }
        return properties;
    }

    /**
     * 保存或更新索引
     *
     * @param reqDTO
     */
    private void createIndexInEs(IndexSaveOrUpdateReqDTO reqDTO) {
        StringBuilder builder = new StringBuilder();
        if(!CollectionUtils.isEmpty(reqDTO.getProperties())) {
            builder.append("{");
            builder.append("\"properties\"");
            builder.append(":");
            builder.append("{");
            List<IndexPropertyDTO> properties = reqDTO.getProperties();
            for(int i = 0; i < properties.size(); i++) {
                IndexPropertyDTO itp = properties.get(i);
                builder.append("\"" + itp.getName() + "\"");
                builder.append(":");
                builder.append("{");
                StringBuilder subBuilder = new StringBuilder();
                if (!StringUtils.isEmpty(itp.getType())) {
                    subBuilder.append("\"type\"");
                    subBuilder.append(":");
                    subBuilder.append("\"" + itp.getType() + "\"");
                    if(DataType.DATE.getValue().equals(itp.getType())) {
                        subBuilder.append(",");
                        subBuilder.append("\"format\"");
                        subBuilder.append(":");
                        subBuilder.append("\"yyyy-MM-dd HH:mm:ss\"");
                    }
                    subBuilder.append(",");
                }
                if (itp.getIndexed() != null && itp.getIndexed() == YesNo.YES.getValue()) {
                    subBuilder.append("\"index\"");
                    subBuilder.append(":");
                    subBuilder.append("true");
                    subBuilder.append(",");
                    if (!StringUtils.isEmpty(itp.getAnalyzer())) {
                        subBuilder.append("\"analyzer\"");
                        subBuilder.append(":");
                        subBuilder.append("\"" + itp.getAnalyzer() + "\"");
                        subBuilder.append(",");
                    }
                    if (!StringUtils.isEmpty(itp.getSearchAnalyzer())) {
                        subBuilder.append("\"search_analyzer\"");
                        subBuilder.append(":");
                        subBuilder.append("\"" + itp.getSearchAnalyzer() + "\"");
                        subBuilder.append(",");
                    }
                } else {
                    subBuilder.append("\"index\"");
                    subBuilder.append(":");
                    subBuilder.append("false");
                    subBuilder.append(",");
                }
                String temp = subBuilder.toString();
                builder.append(temp.substring(0, temp.length() - 1));
                builder.append("}");
                if(i < properties.size() - 1) {
                    builder.append(",");
                }
            }
            builder.append("}");
            builder.append("}");
        }
        esClient.createOrUpdateIndex(reqDTO.getName(), builder.toString(), reqDTO.getShards(), reqDTO.getReplicas());
    }

    /**
     * 保存索引
     *
     * @param reqDTO
     * @return
     */
    private Long saveIndex(IndexSaveOrUpdateReqDTO reqDTO) {
        if(StringUtils.isEmpty(reqDTO.getName())) {

        }
        // 索引数据
        IndexDomain index = BeanUtils.map(reqDTO, IndexDomain.class);
        index.setCreateBy(reqDTO.getUserId());
        index.setUpdateBy(reqDTO.getUserId());
        indexMapper.insertOrUpdate(index);
        // 索引job
        Long jobId = null;
        if(reqDTO.getJob() != null) {
            IndexJobDomain indexJob = BeanUtils.map(reqDTO.getJob(), IndexJobDomain.class);
            indexJob.setIndexId(index.getId());
            indexJob.setIndexName(index.getName());
            indexJob.setCreateBy(reqDTO.getUserId());
            indexJob.setUpdateBy(reqDTO.getUserId());
            indexJobMapper.insertOrUpdate(indexJob);
            jobId = indexJob.getId();
        }
        if(reqDTO.getJob() != null) {
            if(SyncType.SQL.getValue() == reqDTO.getJob().getDataSyncType()) {
                // 索引sql
                if(reqDTO.getSql() != null) {
                    IndexSqlDomain indexSql = BeanUtils.map(reqDTO.getSql(), IndexSqlDomain.class);
                    indexSql.setIndexId(index.getId());
                    indexSql.setJobId(jobId);
                    indexSqlMapper.insertOrUpdate(indexSql);
                }
            } else if(SyncType.INTERFACE.getValue() == reqDTO.getJob().getDataSyncType()) {
                if(reqDTO.getInter() != null) {
                    IndexInterfaceDomain indexInterface = BeanUtils.map(reqDTO.getInter(), IndexInterfaceDomain.class);
                    indexInterface.setIndexId(index.getId());
                    indexInterface.setJobId(jobId);
                    indexInterfaceMapper.insertOrUpdate(indexInterface);
                }
            }
        }
        // 索引属性
        if(!CollectionUtils.isEmpty(reqDTO.getProperties())) {
            List<IndexPropertyDomain> properties = BeanUtils.mapList(reqDTO.getProperties(), IndexPropertyDomain.class);
            properties.stream().forEach(property -> {
                // 索引Id
                property.setIndexId(index.getId());
                property.setCreateBy(reqDTO.getUserId());
                property.setUpdateBy(reqDTO.getUserId());
                indexPropertyMapper.insert(property);
            });
        }
        return index.getId();
    }

    /**
     * 索引数据校验
     *
     * @param reqDTO
     */
    private void validateIndex(IndexSaveOrUpdateReqDTO reqDTO) {
        // 索引有效性校验
        if(reqDTO.getId() != null && reqDTO.getId() != 0L) {
            IndexDomain index = indexMapper.selectById(reqDTO.getId());
            Optional.ofNullable(index).orElseThrow(() -> new ServiceException(String.format(INDEX_NOT_EXISTS, reqDTO.getName())));
        } else {
            List<IndexDomain> indexs = indexMapper.selectByName(reqDTO.getName());
            if(!CollectionUtils.isEmpty(indexs)) {
                throw new ServiceException(String.format(INDEX_NAME_EXISTS, reqDTO.getName()));
            }
        }
        if(StringUtils.isEmpty(reqDTO.getName())) {
            throw new ServiceException(INDEX_NAME_NOT_NULL);
        }
        if(!CollectionUtils.isEmpty(reqDTO.getProperties())) {
            reqDTO.getProperties().forEach(e -> {
                if(StringUtils.isEmpty(e.getName())) {
                    throw new ServiceException(INDEX_MAPPING_NAME_NOT_NULL);
                }
            });
        }
        if(reqDTO.getJob() != null) {
            if(SyncType.SQL.getValue() == reqDTO.getJob().getDataSyncType()) {
                if(reqDTO.getSql() == null || StringUtils.isEmpty(reqDTO.getSql().getSql())) {
                    throw new ServiceException(SYNC_SQL_NOT_NULL);
                }
            } else if(SyncType.INTERFACE.getValue() == reqDTO.getJob().getDataSyncType()) {
                if(reqDTO.getInter() == null || StringUtils.isEmpty(reqDTO.getInter().getUrl())) {
                    throw new ServiceException(SYNC_INTER_NOT_NULL);
                }
            }
        }
    }
}
