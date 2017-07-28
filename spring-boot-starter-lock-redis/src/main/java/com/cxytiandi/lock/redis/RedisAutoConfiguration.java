package com.cxytiandi.lock.redis;

import java.io.IOException;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 使用方直接注入RedissonClient即可使用分布式锁服务<br>
 * 文档参考：https://github.com/redisson/redisson/wiki/8.-%E5%88%86%E5%B8%83%E5%BC%8F%E9%94%81%E5%92%8C%E5%90%8C%E6%AD%A5%E5%99%A8
 * @author yinjihuan
 *
 */
@ConditionalOnProperty(prefix = "spring.lock", value = "enable", matchIfMissing = true)
@Configuration
@EnableConfigurationProperties(RedisConfig.class)
public class RedisAutoConfiguration {
	
	@Autowired
	private RedisConfig redisConfig;
	
	@Bean(destroyMethod="shutdown")
    RedissonClient redisson() throws IOException {
        Config config = new Config();
        String address = "redis://" + redisConfig.getHost() + ":" + redisConfig.getPort();
        config.useSingleServer().setAddress(address);
        return Redisson.create(config);
    }

}
