package com.mlnx.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import redis.clients.jedis.JedisPoolConfig;

import java.lang.reflect.Method;

/**
 * @author fzh
 * @create 2018/2/26 13:19
 */
@Configuration
@PropertySource(value = "classpath:redis.properties")
@EnableCaching
public class RedisConfig extends CachingConfigurerSupport{

    @Value("${redis.server.host}")
    private String host;

    @Value("${redis.server.port}")
    private int port;

    @Value("${redis.server.password}")
    private String password;

    @Value("${redis.server.maxTotal}")
    private int maxTotal;

    @Value("${redis.server.maxWaitMills}")
    private int maxWaitMills;

    @Value("${redis.server.maxIdle}")
    private int maxIdle;

    @Value("${redis.server.minIdle}")
    private int minIdle;

    @Value("${redis.server.timeout}")
    private int timeout;

    @Value("${redis.server.database}")
    private int database;

    @Override
    @Bean
    public KeyGenerator keyGenerator() {
        return new KeyGenerator() {
            @Override
            public Object generate(Object target, Method method, Object... params) {
                StringBuilder sb = new StringBuilder();
                sb.append(target.getClass().getName());
                sb.append(method.getName());
                for (Object obj : params) {
                    sb.append(obj.toString());
                }
                return sb.toString();
            }
        };
    }

    @SuppressWarnings("rawtypes")
    @Bean
    public CacheManager cacheManager(RedisTemplate redisTemplate) {
        RedisCacheManager rcm = new RedisCacheManager(redisTemplate);
        //设置缓存过期时间
        //rcm.setDefaultExpiration(60);//秒
        return rcm;
    }

    @Bean
    public JedisPoolConfig jedisPoolConfig(){
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(maxTotal);
        jedisPoolConfig.setMaxWaitMillis(maxWaitMills);
        jedisPoolConfig.setMaxIdle(maxIdle);
        jedisPoolConfig.setMinIdle(minIdle);
        return jedisPoolConfig;
    }

    @Bean
    public JedisConnectionFactory jedisConnectionFactory(){
        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();
        jedisConnectionFactory.setHostName(host);
        jedisConnectionFactory.setPort(port);
        jedisConnectionFactory.setPassword(password);
        jedisConnectionFactory.setPoolConfig(jedisPoolConfig());
        jedisConnectionFactory.setTimeout(timeout);
        jedisConnectionFactory.setDatabase(database);
        return jedisConnectionFactory;
    }
    @Bean
    public RedisTemplate<String, String> redisTemplate() {
        RedisTemplate template = new RedisTemplate();
        template.setConnectionFactory(jedisConnectionFactory());
        return template;
    }
}
