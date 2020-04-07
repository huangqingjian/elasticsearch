package com.hqj.es.mapper;

import com.hqj.es.domain.IndexJobLogDomain;
import com.hqj.es.bean.IndexQueryBean;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 任务日志mapper
 */
public interface IndexJobLogMapper {
    /**
     * 通过条件查询索引日志
     *
     * @param query
     * @return
     */
    List<IndexJobLogDomain> selectByQuery(IndexQueryBean query);
    /**
     * 查询
     *
     * @param id
     * @return
     */
    IndexJobLogDomain selectById(@Param("id") Long id);

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
    Long insert(IndexJobLogDomain index);

    /**
     * 更新
     *
     * @param index
     */
    void update(IndexJobLogDomain index);
}
