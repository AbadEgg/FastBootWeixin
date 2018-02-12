package com.mlnx;

import com.mlnx.device.ecg.EcgDeviceInfo;
import com.mlnx.device.server.DubboDeviceServerApplication;
import com.mlnx.device.server.mybatis.mapper.TEcgDeviceMapper;

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
@SpringBootTest(classes = DubboDeviceServerApplication.class)
public class EcgDeviceServiceTest {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private TEcgDeviceMapper tEcgDeviceMapper;

    @Test
    public void test(){
        EcgDeviceInfo EcgDeviceInfo = tEcgDeviceMapper.selectEcgDeviceInfo("CMS0001");
        logger.info(EcgDeviceInfo.toString());
    }
}
