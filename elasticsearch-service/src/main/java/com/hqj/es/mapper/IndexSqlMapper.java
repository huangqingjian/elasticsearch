package com.hqj.es.mapper;

import com.hqj.es.domain.IndexSqlDomain;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 任务sql mapper
 */
public interface IndexSqlMapper {
    /**
     * 查询
     *
     * @param id
     * @return
     */
    IndexSqlDomain selectById(@Param("id") Long id);

    /**
     * 查询
     *
     * @param indexId
     * @return
     */
    List<IndexSqlDomain> selectByIndexId(@Param("indexId") Long indexId);

    /**
     * 删除
     *
     * @param id
     */
    void deleteById(@Param("id") Long id);

    /**
     * 删除
     *
     * @param indexId
     */
    void deleteByIndexId(@Param("indexId") Long indexId);

    /**
     * 插入
     *
     * @param indexSql
     * @return
     */
    Long insert(IndexSqlDomain indexSql);

    /**
     * 更新
     *
     * @param indexSql
     */
    void update(IndexSqlDomain indexSql);

    /**
     * 插入或更新
     *
     * @param indexSql
     * @return
     */
    Long insertOrUpdate(IndexSqlDomain indexSql);
}
