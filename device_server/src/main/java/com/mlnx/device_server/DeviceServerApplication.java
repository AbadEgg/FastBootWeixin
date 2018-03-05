package com.mlnx.device_server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.mlnx"})
public class DeviceServerApplication {

	public static void main(String[] args) {
//		EcgAnalysis.gpu8AcId = new byte[]{0x0E, (byte) 0x80, 0x01, (byte) 0x80, 0x16, 0x51, 0x36, 0x32, 0x38,
//				0x37, 0x37, 0x31};

//		EcgAnalysis.gpu8AcId = new byte[]{0x00, 0x00, 0x2F, 0x00, 0x16, 0x51, 0x36, 0x32, 0x38, 0x37, 0x37, 0x31};

		SpringApplication.run(DeviceServerApplication.class, args);
	}
}
