package com.hqj.es.client;

import com.hqj.es.bean.PageBean;
import org.elasticsearch.index.query.QueryBuilder;

import java.util.List;
import java.util.Map;

/**
 * es客户端
 */
public interface EsClient {
    /**
     * 创建或更新索引
     *
     * @param indexName 索引名称
     * @param mappering 类型映射
     * @param shards    分片数
     * @param replicas  副本数
     */
    void createOrUpdateIndex(String indexName, String mappering, Integer shards, Integer replicas);

    /**
     * 删除索引
     *
     * @param indexName 索引名称
     * @return
     */
    void deleteIndex(String indexName);

    /**
     * 数据添加，指定ID
     *
     * @param indexName 索引名称
     * @param id        id
     * @param objectMap 待添加数据
     * @return
     */
    void saveOrUpdate(String indexName, String id, Map<String, Object> objectMap);

    /**
     * 通过ID删除数据
     *
     * @param indexName 索引名称
     * @param id        id
     */
    void deleteById(String indexName, String id);

    /** 断某个index是否存在
     *
     * @param indexName 索引名
     *
     * @return
     * @throws
     */
    boolean indexExist(String indexName);

    /**
     * 通过ID删除数据
     *
     * @param indexName 索引名称
     * @param ids       ids
     */
    void deleteByIds(String indexName, List<String> ids);

    /**
     * 通过ID获取数据
     *
     * @param indexName 索引名称
     * @param id        id
     * @return
     */
    Map<String, Object> getById(String indexName, String id);

    /**
     * 通过ID批量获取数据
     *
     * @param indexName 索引名称
     * @param ids        ids
     * @return
     */
    List<Map<String, Object>> getByIds(String indexName, List<String> ids);

    /**
     * 使用分词查询，分页
     *
     * @param indexName      索引名称
     * @param pageNum        当前页
     * @param pageSize       每页显示条数
     * @param query          查询条件
     * @return
     */
    PageBean searchByPage(String indexName, int pageNum, int pageSize, QueryBuilder query);

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
    PageBean searchByPage(String indexName, int pageNum, int pageSize, QueryBuilder query, String sortField);

    /**
     * 使用分词查询，分页
     *
     * @param indexName      索引名称
     * @param pageNum        当前页
     * @param pageSize       每页显示条数
     * @param fields         需要显示的字段，逗号分隔（缺省为全部字段）
     * @param query          查询条件
     * @param sortField      排序字段
     * @return
     */
    PageBean searchByPage(String indexName, int pageNum, int pageSize, QueryBuilder query, String fields, String sortField);

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
    PageBean searchByPage(String indexName, int pageNum, int pageSize, QueryBuilder query, String fields, String sortField, String highlightField);
}
