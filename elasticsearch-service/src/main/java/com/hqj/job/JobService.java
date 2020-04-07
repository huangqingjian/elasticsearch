package com.hqj.job;

/**
 * job服务
 */
public interface JobService {
    /**
     * 5分钟job
     */
    void fiveMinusJob();

    /**
     * 30分钟job
     */
    void thirtyMinusjob();

    /**
     * 60分钟job
     */
    void sixtyMinusJob();

    /**
     * 每天job
     */
    void dayJob();

    /**
     * 每周job
     */
    void weekJob();

    /**
     * 每周job
     */
    void monthJob();

    /**
     * 手动触发
     *
     * @param indexId
     * @param isAll
     */
    void manual(Long indexId, boolean isAll);
}
