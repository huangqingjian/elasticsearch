package com.hqj.es.domain;

import java.util.Date;

/**
 * 索引job日志
 */
public class IndexJobLogDomain {
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
     * jobId
     */
    private Long jobId;
    /**
     * 数据同步方式：0：SQL，1：提供服务接口
     */
    private Integer dataSyncType;
    /**
     * job开始时间
     */
    private Date startTime;
    /**
     * job结束时间
     */
    private Date endTime;
    /**
     * 本次同步数据量
     */
    private Integer total;
    /**
     * 错误码：0-成功，1-失败
     */
    private String errorCode;
    /**
     * 错误信息
     */
    private String errorMessage;
    /**
     * 是否删除，0-正常 1-删除
     */
    private Integer deleted;

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

    public Long getJobId() {
        return jobId;
    }

    public void setJobId(Long jobId) {
        this.jobId = jobId;
    }

    public Integer getDataSyncType() {
        return dataSyncType;
    }

    public void setDataSyncType(Integer dataSyncType) {
        this.dataSyncType = dataSyncType;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public Integer getDeleted() {
        return deleted;
    }

    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }
}
