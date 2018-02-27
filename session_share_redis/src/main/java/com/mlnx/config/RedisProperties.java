package com.mlnx.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * @author fzh
 * @create 2018/2/26 13:10
 */
@Configuration
@ConfigurationProperties(prefix = RedisProperties.REDIS_PREFIX)
@PropertySource(value = "classpath:redis.properties")
@Data
public class RedisProperties {

    protected static final String REDIS_PREFIX = "redis.server";

    private String host;

    private int port;

    private String password;

    private int maxTotal;

    private int maxWaitMills;

    private int maxIdle;

    private int minIdle;

    private int timeout;

    private int database;


}
