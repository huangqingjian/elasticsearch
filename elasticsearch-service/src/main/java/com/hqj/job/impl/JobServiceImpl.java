package com.hqj.job.impl;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.hqj.es.domain.IndexInterfaceDomain;
import com.hqj.es.domain.IndexJobDomain;
import com.hqj.es.domain.IndexJobLogDomain;
import com.hqj.es.domain.IndexSqlDomain;
import com.hqj.es.enums.SyncAllRule;
import com.hqj.es.enums.SyncIncrRule;
import com.hqj.es.enums.SyncType;
import com.hqj.es.mapper.IndexInterfaceMapper;
import com.hqj.es.mapper.IndexJobLogMapper;
import com.hqj.es.mapper.IndexJobMapper;
import com.hqj.es.mapper.IndexSqlMapper;
import com.hqj.utils.JsoupUtils;
import com.hqj.common.exception.ServiceException;
import com.hqj.dynamic.mapper.DynamicMapper;
import com.hqj.es.bean.IndexJobQueryBean;
import com.hqj.job.JobService;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * job服务
 */
@Service
public class JobServiceImpl implements JobService {
    private static final Logger log = LoggerFactory.getLogger(JobServiceImpl.class);

    // 最大次数
    private static final int MAX_COUNT = 3;

    @Autowired
    @Qualifier("executor")
    private ThreadPoolExecutor executor;
    @Autowired
    private RestHighLevelClient client;
    @Autowired
    private IndexJobMapper indexJobMapper;
    @Autowired
    private IndexSqlMapper indexSqlMapper;
    @Autowired
    private IndexInterfaceMapper indexInterfaceMapper;
    @Autowired
    private IndexJobLogMapper indexJobLogMapper;
    @Autowired
    private DynamicMapper dynamicMapper;

    /**
     * 5分钟job
     */
    @Override
    public void fiveMinusJob() {
        sync(SyncIncrRule.FIVEMINUTES.getValue(), null);
    }

    /**
     * 30分钟job
     */
    @Override
    public void thirtyMinusjob() {
        sync(SyncIncrRule.THIRTYMINUTES.getValue(), null);
    }

    /**
     * 60分钟job
     */
    @Override
    public void sixtyMinusJob() {
        sync(SyncIncrRule.SIXTYMINUTES.getValue(), null);
    }

    /**
     * 每天job
     */
    @Override
    public void dayJob() {
        sync(null, SyncAllRule.EVERYDAY.getValue());
    }

    /**
     * 每周job
     */
    @Override
    public void weekJob() {
        sync(null, SyncAllRule.EVERYWEEK.getValue());
    }

    /**
     * 每月job
     */
    @Override
    public void monthJob() {
        sync(null, SyncAllRule.EVERYMONTH.getValue());
    }

    /**
     * 手动触发
     *
     * @param indexId
     * @param isAll
     */
    @Override
    public void manual(Long indexId, boolean isAll) {
        log.info("the request parameter of manual in {}: {}", this.getClass().getSimpleName(), indexId);
        if(indexId == null || indexId == 0L) {
            return;
        }
        List<IndexJobDomain> indexJobs = indexJobMapper.selectByIndexIds(Lists.newArrayList(indexId));
        log.info("the index jobs: {}", JSON.toJSONString(indexJobs));
        if(CollectionUtils.isEmpty(indexJobs)) {
            return;
        }
        IndexJobDomain indexJob = indexJobs.get(0);
        executor.execute(new JobThread(indexJob, isAll));
    }

    /**
     * 数据同步
     *
     * @param syncIncrRule
     * @param syncAllRule
     */
    private void sync(Integer syncIncrRule, Integer syncAllRule) {
        // 增量job在0-7点不执行
        if(syncIncrRule != null) {
            int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
            if(hour <= 6 && hour >= 0) {
                log.info("not execute incr sync rule at {}: {}", new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date()), syncIncrRule);
                return;
            }
        }
        int pageStart = 0;
        int pageSize = 10;
        boolean isAll = true;
        if(syncIncrRule != null && syncAllRule == null) {
            isAll = false;
        }
        List<IndexJobDomain> indexJobs = getIndexJobs(syncIncrRule, syncAllRule, pageStart, pageSize);
        while (!CollectionUtils.isEmpty(indexJobs)) {
            CountDownLatch latch = new CountDownLatch(indexJobs.size());
            // 同步数据
            for(IndexJobDomain indexJob : indexJobs) {
                executor.execute(new JobThread(latch, indexJob, isAll));
            }
            // 等待批次任务执行完成
            try {
                latch.await();
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
            // 循环迭代
            pageStart += 10;
            indexJobs = getIndexJobs(syncIncrRule, syncAllRule, pageStart, pageSize);
        }
    }

    /**
     * 获取定时job
     *
     * @param syncIncrRule
     * @param syncAllRule
     * @param pageStart
     * @param pageSize
     * @return
     */
    private List<IndexJobDomain> getIndexJobs(Integer syncIncrRule, Integer syncAllRule, Integer pageStart, Integer pageSize) {
        IndexJobQueryBean query = new IndexJobQueryBean();
        query.setSyncIncrRule(syncIncrRule);
        query.setSyncAllRule(syncAllRule);
        query.setPageStart(pageStart);
        query.setPageSize(pageSize);
        List<IndexJobDomain> indexJobs = indexJobMapper.selectByQuery(query);
        return indexJobs;
    }

    /**
     * 任务线程
     */
    class JobThread implements Runnable {
        private CountDownLatch latch;
        private IndexJobDomain job;
        private Boolean isAll;

        public JobThread(IndexJobDomain job, Boolean isAll) {
            this.job = job;
            this.isAll = isAll;
        }

        public JobThread(CountDownLatch latch, IndexJobDomain job, Boolean isAll) {
            this.latch = latch;
            this.job = job;
            this.isAll = isAll;
        }

        @Override
        public void run() {
            int total = 0;
            Date startTime = null;
            Date endTime = null;
            String errorCode = "0";
            String errorMessage = "success";
            try {
                startTime = new Date();
                if (SyncType.SQL.getValue() == job.getDataSyncType()) {
                    List<IndexSqlDomain> indexSqls = indexSqlMapper.selectByIndexId(job.getIndexId());
                    if (!CollectionUtils.isEmpty(indexSqls)) {
                        for (IndexSqlDomain indexSql : indexSqls) {
                            if (!StringUtils.isEmpty(indexSql.getSql())) {
                                Integer pageStart = 0;
                                Integer pageSize = 500;

                                List<Map> datas = getDataBySql(indexSql.getSql(), job.getLastSyncTime(), pageStart, pageSize, isAll);
                                while (!CollectionUtils.isEmpty(datas)) {
                                    log.info(this.job.getIndexName() + ",pageStart=" + pageStart);
                                    total += datas.size();
                                    // 插入es
                                    bulk(job, datas);
                                    // 循环迭代
                                    pageStart += 500;
                                    datas = getDataBySql(indexSql.getSql(), job.getLastSyncTime(), pageStart, pageSize, isAll);
                                }
                            }
                        }
                    }
                } else if (SyncType.INTERFACE.getValue() == job.getDataSyncType()) {
                    List<IndexInterfaceDomain> indexInterfaces = indexInterfaceMapper.selectByIndexId(job.getIndexId());
                    if (!CollectionUtils.isEmpty(indexInterfaces)) {
                        for (IndexInterfaceDomain indexInterface : indexInterfaces) {
                            if (!StringUtils.isEmpty(indexInterface.getUrl())) {
                                Integer pageNum = 1;
                                Integer pageSize = 500;
                                List<Map> datas = getDataByUrl(indexInterface.getUrl(), job.getLastSyncTime(), pageNum, pageSize, isAll);
                                while (!CollectionUtils.isEmpty(datas)) {
                                    log.info(this.job.getIndexName() + ", pageNum=" + pageNum);
                                    total += datas.size();
                                    // 插入es
                                    bulk(job, datas);
                                    pageNum += 1;
                                    datas = getDataByUrl(indexInterface.getUrl(), job.getLastSyncTime(), pageNum, pageSize, isAll);
                                }
                            }
                        }
                    }
                }
                endTime = new Date();
                // 更新最新同步时间
                indexJobMapper.updateLastSyncTime(job.getId(), startTime);
            } catch (Exception e) {
                log.error("job={}, error={}", JSON.toJSONString(job), e.getMessage(), e);
                errorCode = "1";
                errorMessage = e.getMessage();
            } finally {
                // 同步日志
                insertJobLog(job, startTime, endTime, total, errorCode, errorMessage);
                // CountDownLatch--，防止异常导致线程一直阻塞
                if (latch != null) {
                    latch.countDown();
                }
            }
        }
    }

    /**
     * 插入es
     *
     * @param job
     * @param datas
     */
    private void bulk(IndexJobDomain job, List<Map> datas) {
        BulkRequest request = new BulkRequest();
        for (Map data : datas) {
            // 默认以id作为es主键，id不存在随机生成主键
            String id = data.get("id") == null ? UUID.randomUUID().toString().replace("-", "") : String.valueOf(data.get("id"));
            request.add(new IndexRequest(job.getIndexName()).id(id).source(data));
        }
        //
        int retry = 1;
        while (retry <= MAX_COUNT) {
            try {
                BulkResponse response = client.bulk(request, RequestOptions.DEFAULT);
                if (response.hasFailures()) {
                    log.error("bulk request fail {}: {}", job.getIndexName(), response.buildFailureMessage());
                    if(retry == MAX_COUNT) {
                        throw new ServiceException(response.buildFailureMessage());
                    }
                } else {
                    break;
                }
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                if(retry == MAX_COUNT) {
                    throw new ServiceException(e.getMessage());
                }
            }
            retry ++;
        }
    }

    /**
     * 插入任务日志
     *
     * @param job
     * @param startTime
     * @param endTime
     * @param total
     * @param errorCode
     * @param errorMessage
     */
    private void insertJobLog(IndexJobDomain job, Date startTime, Date endTime, Integer total, String errorCode, String errorMessage) {
        try {
            IndexJobLogDomain indexJobLog = new IndexJobLogDomain();
            indexJobLog.setIndexId(job.getIndexId());
            indexJobLog.setIndexName(job.getIndexName());
            indexJobLog.setJobId(job.getId());
            indexJobLog.setDataSyncType(job.getDataSyncType());
            indexJobLog.setTotal(total);
            indexJobLog.setStartTime(startTime);
            indexJobLog.setEndTime(endTime);
            indexJobLog.setErrorCode(errorCode);
            indexJobLog.setErrorMessage(errorMessage);
            indexJobLogMapper.insert(indexJobLog);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    /**
     * 动态sql查询
     *
     * @param sql
     * @param lastSyncTime
     * @param pageStart
     * @param pageSize
     * @param
     * @return
     */
    private List<Map> getDataBySql(String sql, Date lastSyncTime, Integer pageStart, Integer pageSize, Boolean isAll) {
        String lastSyncTimeStr = "1970-01-01 00:00:00";
        if(!isAll && lastSyncTime != null) {
            lastSyncTimeStr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(lastSyncTime);
        }
        // sql中默认替换字段：#startTime#
        sql = sql.replace("#startTime#", "'" + lastSyncTimeStr + "'");
        // 拼接最新同步时间
        String querySql = sql + " limit " + pageStart + ", " + pageSize;
        List<Map> datas =  dynamicMapper.selectBySql(querySql);
        return datas;
    }

    /**
     * 接口查询
     *
     * @param url
     * @param pageNum
     * @param pageSize
     * @param isAll
     * @return
     */
    private List<Map> getDataByUrl(String url, Date lastSyncTime, Integer pageNum, Integer pageSize, Boolean isAll) {
        String result = "";
        try {
            String lastSyncTimeStr = "1970-01-01 00:00:00";
            if (!isAll && lastSyncTime != null) {
                lastSyncTimeStr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(lastSyncTime);
            }
            Map<String, Object> params = Maps.newHashMap();
            params.put("pageNum", String.valueOf(pageNum));
            params.put("pageSize", String.valueOf(pageSize));
            params.put("startTime", lastSyncTimeStr);
            result = JsoupUtils.doHttp(url, params);
            if (!StringUtils.isEmpty(result)) {
                Map map = JSON.parseObject(result, Map.class);
                if ("0".equals(map.get("code"))) {
                    if (map.get("data") != null) {
                        return JSON.parseArray(String.valueOf(map.get("data")), Map.class);
                    } else if (map.get("result") != null) {
                        return JSON.parseArray(String.valueOf(map.get("result")), Map.class);
                    }
                }
            }
            return null;
        } catch (Exception e) {
            log.error("error={}, data={}", e.getMessage(), result, e);
            throw new ServiceException(e);
        }
    }
}
