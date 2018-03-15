package com.mlnx.device_server.task;

import com.alibaba.fastjson.JSONObject;
import com.mlnx.local.data.store.bp.BpAvgStore;
import com.mlnx.local.data.store.bp.BpStore;
import com.mlnx.local.data.utils.BpAvgUtils;
import com.mlnx.local.data.utils.DateUtils;
import com.mlnx.utils.RedisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author fzh
 * @create 2018/3/14 15:26
 */
@Service
public class BpTask {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    private static final String BP_PATIENT = "bp_patient";

    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Autowired
    private BpStore bpStore;

    @Autowired
    private BpAvgStore bpAvgStore;

    @Autowired
    private RedisUtil<String> redisUtil;

    @Scheduled(cron = "0 0 2 * * *")
    // 每天凌晨
    public void bp() {
        try {
          logger.info("---------start  开始统计血压时间   start-------------："
                            + format.format(new Date()));
            Set<String> patientIds = new HashSet();
            patientIds = redisUtil.sGet(BP_PATIENT);
            long startTime = DateUtils.getDayBefore(new Date()).getTime();
            long endTime = startTime + 24*60*60*1000;
            for (String patientId:patientIds) {
                List<JSONObject> bpdatas = bpStore.getBpData(startTime,endTime,Integer.valueOf(patientId));
                if(bpdatas!=null && bpdatas.size()>0){
                    bpAvgStore.saveBpAvg(BpAvgUtils.bpDataProcess(bpdatas));
                }
            }
          logger.info("---------end  统计完血压时间    end-------------："
                    + format.format(new Date()));
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("血压每日统计数据", e.getMessage());
        }
    }
}
