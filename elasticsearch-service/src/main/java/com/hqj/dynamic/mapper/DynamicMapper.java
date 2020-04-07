package com.hqj.dynamic.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 动态mapper
 */
public interface DynamicMapper {
    /**
     * 通过sql查询
     *
     * @param sql
     * @return
     */
    List<Map> selectBySql(@Param("sql") String sql);
}
