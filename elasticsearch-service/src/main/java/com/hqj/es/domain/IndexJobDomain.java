package com.hqj.es.domain;

import com.hqj.common.domain.BaseDomain;

import java.util.Date;

/**
 * 索引job
 */
public class IndexJobDomain extends BaseDomain {
    /**
     * 主键
     */
    private Long id;
    /**
     * 索引Id
     */
    private Long indexId;
    /**
     * 索引名称
     */
    private String indexName;
    /**
     * 开关:0 关，1：开
     */
    private Integer switchs;
    /**
     * 数据同步方式：0：SQL，1：提供服务接口
     */
    private Integer dataSyncType;
    /**
     * 全量同步数据规则：1：每天一次，2：每周一次：3：每月一次
     */
    private Integer dataSyncAllRule;
    /**
     * 增量同步数据规则：1：5分钟一次，2：30分钟一次 3：60分钟一次
     */
    private Integer dataSyncIncrRule;
    /**
     * 最近一次同步完成时间
     */
    private Date lastSyncTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIndexId() {
        return indexId;
    }

    public void setIndexId(Long indexId) {
        this.indexId = indexId;
    }

    public String getIndexName() {
        return indexName;
    }

    public void setIndexName(String indexName) {
        this.indexName = indexName;
    }

    public Integer getSwitchs() {
        return switchs;
    }

    public void setSwitchs(Integer switchs) {
        this.switchs = switchs;
    }

    public Integer getDataSyncType() {
        return dataSyncType;
    }

    public void setDataSyncType(Integer dataSyncType) {
        this.dataSyncType = dataSyncType;
    }

    public Integer getDataSyncAllRule() {
        return dataSyncAllRule;
    }

    public void setDataSyncAllRule(Integer dataSyncAllRule) {
        this.dataSyncAllRule = dataSyncAllRule;
    }

    public Integer getDataSyncIncrRule() {
        return dataSyncIncrRule;
    }

    public void setDataSyncIncrRule(Integer dataSyncIncrRule) {
        this.dataSyncIncrRule = dataSyncIncrRule;
    }

    public Date getLastSyncTime() {
        return lastSyncTime;
    }

    public void setLastSyncTime(Date lastSyncTime) {
        this.lastSyncTime = lastSyncTime;
    }
}
