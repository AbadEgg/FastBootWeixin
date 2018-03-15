package com.mlnx;

import com.mlnx.device.ecg.EcgDeviceInfo;
import com.mlnx.device_server.DeviceServerApplication;
import com.mlnx.device_server.mybatis.mapper.TDeviceMapper;

import com.mlnx.device_server.task.BpTask;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

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
}
