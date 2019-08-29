package com.baizhi.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.Jedis;

@Configuration
public class RedisConf {
    @Bean
    public Jedis getJedis() {
        Jedis jedis = new Jedis("192.168.247.138", 6379);
        return jedis;
    }
}
