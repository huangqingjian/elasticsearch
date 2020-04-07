package com.hqj.es.domain;

/**
 * 索引sql
 */
public class IndexSqlDomain {
    /**
     * 主键
     */
    private Long id;
    /**
     * 索引Id
     */
    private Long indexId;
    /**
     * 任务Id
     */
    private Long jobId;
    /**
     * sql
     */
    private String sql;
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

    public Long getJobId() {
        return jobId;
    }

    public void setJobId(Long jobId) {
        this.jobId = jobId;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public Integer getDeleted() {
        return deleted;
    }

    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }
}
