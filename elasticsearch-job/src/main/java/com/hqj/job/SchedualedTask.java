package com.hqj.job;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 增量任务
 */
@Component
public class SchedualedTask {
    private static final Logger log = LoggerFactory.getLogger(SchedualedTask.class);

    @Autowired
    private JobService jobService;

    /********************************** 增量job *********************************/

    /**
     * 每5分钟
     */
    @Scheduled(cron = "0 0/5 * * * ?")
    public void cronEveryFiveMinus(){
        log.info("start five minus job at {}", new Date().toString());
        jobService.fiveMinusJob();
        log.info("end five minus job at {}", new Date().toString());
    }

    /**
     * 每30分钟
     */
    @Scheduled(cron = "0 0/30 * * * ?")
    public void cronEveryThirtyMinus(){
        log.info("start thirty minus job at {}", new Date().toString());
        jobService.thirtyMinusjob();
        log.info("end thirty minus job at {}", new Date().toString());
    }

    /**
     * 每60分钟
     */
    @Scheduled(cron = "0 0 * * * ?")
    public void cronEverySixtyMinus(){
        log.info("start sixty minus job at {}", new Date().toString());
        jobService.sixtyMinusJob();
        log.info("end sixty minus job at {}", new Date().toString());
    }

    /********************************** 全量job *********************************/

    /**
     * 每天凌晨
     */
    @Scheduled(cron = "0 0 0 * * ?")
    public void cronEveryday(){
        log.info("start day job at {}", new Date().toString());
        jobService.dayJob();
        log.info("end day job at {}", new Date().toString());
    }

    /**
     * 每周一凌晨
     */
    @Scheduled(cron = "0 0 0 ? * MON")
    public void cronEveryweek(){
        log.info("start week job at {}", new Date().toString());
        jobService.weekJob();
        log.info("end week job at {}", new Date().toString());
    }

    /**
     * 每月1号凌晨
     */
    @Scheduled(cron = "0 0 0 1 * ?")
    public void cronEverymonth(){
        log.info("end month job at {}", new Date().toString());
        jobService.monthJob();
        log.info("end month job at {}", new Date().toString());
    }
}
