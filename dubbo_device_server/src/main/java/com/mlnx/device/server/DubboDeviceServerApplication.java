package com.mlnx.device.server;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@MapperScan("com.mlnx.device.server.mybatis.mapper")
@EnableScheduling
@EnableTransactionManagement
public class DubboDeviceServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(DubboDeviceServerApplication.class, args);
	}
}
