package com.hqj.es.service;

import com.hqj.common.dto.PageDTO;
import com.hqj.es.dto.*;
import com.hqj.es.dto.*;

import java.util.List;
import java.util.Map;

/**
 * es服务
 */
public interface EsService {
    /**
     * 查询索引
     *
     * @param queryDTO
     * @return
     */
    PageDTO<IndexDTO> listIndex(IndexQueryDTO queryDTO);

    /**
     * 查询索引任务日志
     *
     * @param queryDTO
     * @return
     */
    PageDTO<IndexJobLogDTO> listIndexJobLog(IndexQueryDTO queryDTO);

    /**
     * 获取索引
     *
     * @param id
     * @return
     */
    IndexDTO getSimpleIndex(Long id);

    /**
     * 获取索引
     *
     * @param id
     * @return
     */
    IndexDTO getIndex(Long id);

    /**
     * 删除索引
     *
     * @param id
     * @return
     */
    void deleteIndex(Long id);

    /**
     * 索引Job开关
     *
     * @param id
     * @param switchs
     * @return
     */
    void switchIndexJob(Long id, Integer switchs);

    /**
     * 删除索引任务日志
     *
     * @param id
     * @return
     */
    void deleteIndexJobLog(Long id);

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
     * es搜索
     *
     * @param searchDTO
     */
    com.hqj.es.dto.PageDTO searchByPage(SearchDTO searchDTO);

    /**
     * 创建索引
     *
     * @param reqDTO
     * @return
     */
    Long createIndex(IndexSaveOrUpdateReqDTO reqDTO);

    /**
     * 更新索引
     *
     * @param reqDTO
     * @return
     */
    Long updateIndex(IndexSaveOrUpdateReqDTO reqDTO);

    /**
     * 获取索引属性
     *
     * @param url
     * @return
     */
    List<IndexMappingDTO> getIndexMapping(String url);

    /**
     * 获取索引属性头
     *
     * @return
     */
    List<String> getIndexPropertyHeader();

    /**
     * 获取索引映射
     *
     * @param indexId
     * @return
     */
    List<List<String>> getIndexProperty(Long indexId);
}
