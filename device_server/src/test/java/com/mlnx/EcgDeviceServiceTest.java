package com.mlnx;

import com.alibaba.fastjson.JSON;
import com.mlnx.device.ecg.EcgDeviceInfo;
import com.mlnx.device_server.DeviceServerApplication;
import com.mlnx.device_server.mybatis.mapper.TDeviceMapper;
import com.mlnx.device_server.task.BpTask;
import com.mlnx.local.data.domain.BpAvg;
import com.mlnx.local.data.domain.DeviceOnlineRecord;
import com.mlnx.local.data.store.bp.BpAvgStore;
import com.mlnx.local.data.store.device.DeviceStore;
import com.mlnx.local.data.utils.DateUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.ParseException;
import java.util.List;
import java.util.Random;

/**
 * Created by amanda.shan on 2017/12/23.
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DeviceServerApplication.class)
public class EcgDeviceServiceTest {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private TDeviceMapper tDeviceMapper;

    @Autowired
    private BpTask bpTask;

    @Autowired
    private BpAvgStore bpAvgStore;

    @Autowired
    private DeviceStore deviceStore;

    @Test
    public void test(){
        EcgDeviceInfo ecgDeviceInfo = tDeviceMapper.getEcgDeviceInfo("HEK07EW17070015M");
        logger.info(ecgDeviceInfo.toString());

        Integer patientId = tDeviceMapper.getPatientId("cms0001");
        logger.info("patientId:"+patientId);
    }

    @Test
    public void bpTask(){
        bpTask.bp();
    }

    @Test
    public void getBp() throws ParseException {
        List<BpAvg> list =  bpAvgStore.getBpAvg(DateUtils.getPastDate(7),DateUtils.getPastDate(-1),8);
        for (BpAvg bpAvg:list) {
            System.out.println(JSON.toJSONString(bpAvg));
        }
    }

    @Test
    public void creatBpDatas() throws ParseException {
        Random random = new Random();
        for (int i = 0; i < 20 ; i++) {
            BpAvg bpAvg = new BpAvg();
            bpAvg.setPatientId(8);
            bpAvg.setDayTime(DateUtils.getPastDate(i));

            bpAvg.setDiastolicAvg(60 + random.nextInt(30));
            bpAvg.setDiastolicDayAvg(60 + random.nextInt(30));
            bpAvg.setDiastolicNightAvg(60 + random.nextInt(30));
            bpAvg.setSystolicAvg(90 + random.nextInt(50));
            bpAvg.setSystolicDayAvg(90 + random.nextInt(50));
            bpAvg.setDiastolicNightAvg(90 + random.nextInt(50));
            bpAvg.setHeartAvg(60 + random.nextInt(20));
            bpAvg.setHeartDayAvg(60 + random.nextInt(20));
            bpAvg.setHeartNightAvg(60 + random.nextInt(20));
            bpAvgStore.saveBpAvg(bpAvg);
            logger.info(String.format("bpAvg%s数据插入成功",i));
        }
    }

    @Test
    public void getDeviceOnlineRecords() throws Exception {
        List<DeviceOnlineRecord> list = deviceStore.get(DateUtils.getPastDate(3),DateUtils.getPastDate(-1),3);
        System.out.println(list.size());
    }
}
