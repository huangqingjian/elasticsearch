package com.hqj.es.mapper;

import com.hqj.es.domain.IndexPropertyDomain;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 索引类型映射Mapper
 */
public interface IndexPropertyMapper {
    /**
     * 查询
     *
     * @param id
     * @return
     */
    IndexPropertyDomain selectById(@Param("id") Long id);

    /**
     * 通过索引Id查找索引类型映射
     *
     * @param indexId
     * @return
     */
    List<IndexPropertyDomain> selectByIndexId(@Param("indexId") Long indexId);

    /**
     * 删除
     *
     * @param id
     */
    void deleteById(@Param("id") Long id);

    /**
     * 通过索引Id删除
     *
     * @param indexId
     */
    void deleteByIndexId(@Param("indexId") Long indexId);

    /**
     * 插入
     *
     * @param indexTypeMapping
     * @return
     */
    Long insert(IndexPropertyDomain indexTypeMapping);

    /**
     * 更新
     *
     * @param indexTypeMapping
     */
    void update(IndexPropertyDomain indexTypeMapping);

    /**
     * 插入或更新
     *
     * @param indexTypeMapping
     * @return
     */
    Long insertOrUpdate(IndexPropertyDomain indexTypeMapping);
}
