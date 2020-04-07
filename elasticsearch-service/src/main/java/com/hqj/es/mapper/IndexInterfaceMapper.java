package com.hqj.es.mapper;

import com.hqj.es.domain.IndexInterfaceDomain;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 索引接口 mapper
 */
public interface IndexInterfaceMapper {
    /**
     * 查询
     *
     * @param id
     * @return
     */
    IndexInterfaceDomain selectById(@Param("id") Long id);

    /**
     * 查询
     *
     * @param indexId
     * @return
     */
    List<IndexInterfaceDomain> selectByIndexId(@Param("indexId") Long indexId);

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
     * @param indexInterface
     * @return
     */
    Long insert(IndexInterfaceDomain indexInterface);

    /**
     * 更新
     *
     * @param indexInterface
     */
    void update(IndexInterfaceDomain indexInterface);

    /**
     * 插入或更新
     *
     * @param indexInterface
     * @return
     */
    Long insertOrUpdate(IndexInterfaceDomain indexInterface);
}
