package com.mlnx.device_server.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author fzh
 * @create 2018/3/14 15:26
 */
@Service
public class BpTask {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Scheduled(cron = "0 59 23 * * *")
    // 每天凌晨
    public void bp() {
        try {
          logger.info("---------start  开始统计血压异常时间   start-------------："
                            + new Date().toString());

          logger.info("---------end  统计完血压异常时间    end-------------："
                    + new Date().toString());
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("血压每日统计数据", e.getMessage());
        }
    }
}
