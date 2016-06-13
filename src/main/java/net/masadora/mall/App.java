package net.masadora.mall;

import com.whalin.MemCached.MemCachedClient;
import moe.src.leyline.framework.LeylineApp;
import net.masadora.mall.business.infrastructure.cache.MemcachedCacheManager;
import net.masadora.mall.business.infrastructure.cache.MemcachedPoolConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.cache.CacheManagerCustomizer;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackages = "net.masadora.mall.business")
@ComponentScan(basePackages = {"net.masadora.mall.business", "net.masadora.mall.interfaces"})
@EntityScan(basePackages = "net.masadora.mall.business.domain")
@EnableCaching

public class App extends LeylineApp {

    @Bean
    public CacheManagerCustomizer<MemcachedCacheManager> cacheManagerCustomizer() {
        return new CacheManagerCustomizer<MemcachedCacheManager>() {
            @Autowired
            MemcachedPoolConfig memcachedPoolConfig;

            @Override
            public void customize(MemcachedCacheManager cacheManager) {

                cacheManager.setClient(new MemCachedClient());
            }
        };
    }


    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}
