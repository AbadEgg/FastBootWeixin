package com.mlnx;

import com.mlnx.core.DeviceShare;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SessionShareRedisApplicationTests {

	@Autowired
	private DeviceShare deviceShare;

	@Test
	public void contextLoads() {
	}

	@Test
	public void testSave(){
		String deviceId = "test";
		deviceShare.saveDevice(deviceId);
	}

}
