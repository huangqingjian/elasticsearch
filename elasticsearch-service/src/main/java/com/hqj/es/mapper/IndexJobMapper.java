package com.hqj.es.mapper;

import com.hqj.es.bean.IndexJobQueryBean;
import com.hqj.es.domain.IndexJobDomain;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * 任务mapper
 */
public interface IndexJobMapper {
    /**
     * 查询
     *
     * @param id
     * @return
     */
    IndexJobDomain selectById(@Param("id") Long id);

    /**
     * 通过索引Id查询
     *
     * @param indexIds
     * @return
     */
    List<IndexJobDomain> selectByIndexIds(@Param("indexIds") List<Long> indexIds);

    /**
     *
     *
     * @param query
     * @return
     */
    List<IndexJobDomain> selectByQuery(IndexJobQueryBean query);

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
     * @param index
     * @return
     */
    Long insert(IndexJobDomain index);

    /**
     * 更新
     *
     * @param index
     */
    void update(IndexJobDomain index);

    /**
     * 更新最新同步时间
     *
     * @param id
     * @param lastSyncTime
     */
    void updateLastSyncTime(@Param("id") Long id, @Param("lastSyncTime") Date lastSyncTime);

    /**
     * 插入或更新
     *
     * @param index
     * @return
     */
    Long insertOrUpdate(IndexJobDomain index);

    /**
     * 索引开关
     *
     * @param id
     * @param switchs
     */
    void updateSwitchs(@Param("id") Long id, @Param("switchs") Integer switchs);
}
