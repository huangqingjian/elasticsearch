package com.hqj.es.bean;


/**
 * 索引job查询条件
 */
public class IndexJobQueryBean {
    /**
     * 全量同步规则
     */
    private Integer syncAllRule;

    /**
     * 用户Id
     */
    private Integer syncIncrRule;

    /**
     * 起始位置
     */
    private Integer pageStart;

    /**
     * 每页记录数
     */
    private Integer pageSize;

    public Integer getSyncAllRule() {
        return syncAllRule;
    }

    public void setSyncAllRule(Integer syncAllRule) {
        this.syncAllRule = syncAllRule;
    }

    public Integer getSyncIncrRule() {
        return syncIncrRule;
    }

    public void setSyncIncrRule(Integer syncIncrRule) {
        this.syncIncrRule = syncIncrRule;
    }

    public Integer getPageStart() {
        return pageStart;
    }

    public void setPageStart(Integer pageStart) {
        this.pageStart = pageStart;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
