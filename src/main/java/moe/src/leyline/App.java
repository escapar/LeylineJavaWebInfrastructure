package moe.src.leyline;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.redisson.Config;
import org.redisson.Redisson;
import org.redisson.RedissonClient;
import org.redisson.spring.cache.CacheConfig;
import org.redisson.spring.cache.RedissonSpringCacheManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.io.Resource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import moe.src.leyline.framework.LeylineApp;

@EnableJpaRepositories(basePackages = "moe.src.leyline.business")
@ComponentScan(basePackages = {"moe.src.leyline.business","moe.src.leyline.interfaces"})
@EntityScan(basePackages = "moe.src.leyline.business.domain")

public class App extends LeylineApp{

    @Bean(destroyMethod="shutdown")
    RedissonClient redisson() throws IOException {
        Config config = new Config();
        config.useSingleServer().setAddress("127.0.0.1:6379");
        return Redisson.create(config);
    }

    @Bean
    CacheManager cacheManager(RedissonClient redissonClient) {
        Map<String, CacheConfig> config = new HashMap<>();
        config.put("testMap", new CacheConfig(24*60*1000, 12*60*1000));
        return new RedissonSpringCacheManager(redissonClient, config);
    }

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}
