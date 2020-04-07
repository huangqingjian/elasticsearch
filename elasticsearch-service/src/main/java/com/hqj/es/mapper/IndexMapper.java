package com.hqj.es.mapper;

import com.hqj.es.domain.IndexDomain;
import com.hqj.es.bean.IndexQueryBean;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 索引Mapper
 */
public interface IndexMapper {
    /**
     * 查询索引
     *
     * @param query
     * @return
     */
    List<IndexDomain> selectByQuery(IndexQueryBean query);

    /**
     * 查询
     *
     * @param id
     * @return
     */
    IndexDomain selectById(@Param("id") Long id);

    /**
     * 通过索引名查询
     *
     * @param name
     * @return
     */
    List<IndexDomain> selectByName(@Param("name") String name);

    /**
     * 删除
     *
     * @param id
     */
    void deleteById(@Param("id") Long id);

    /**
     * 插入
     *
     * @param index
     * @return
     */
    Long insert(IndexDomain index);

    /**
     * 更新
     *
     * @param index
     */
    void update(IndexDomain index);

    /**
     * 插入或更新
     *
     * @param index
     * @return
     */
    Long insertOrUpdate(IndexDomain index);
}
