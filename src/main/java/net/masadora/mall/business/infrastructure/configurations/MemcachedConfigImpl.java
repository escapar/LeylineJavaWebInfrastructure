package net.masadora.mall.business.infrastructure.configurations;

import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * Created by POJO on 6/10/16.
 */
@Configuration
@EnableCaching
@EnableAspectJAutoProxy
public class MemcachedConfigImpl extends CachingConfigurerSupport {
  /*  @Bean
    public CacheManager cacheManager()
    {
        MemcacheClientFactoryImpl cacheClientFactory = new MemcacheClientFactoryImpl();
        AddressProvider addressProvider = new DefaultAddressProvider("");
        CacheConfiguration cacheConfiguration = new CacheConfiguration();
        cacheConfiguration.setConsistentHashing(true); //TODO check this

        CacheFactory cacheFactory = new CacheFactory();
        cacheFactory.setCacheName           ( ""        );
        cacheFactory.setCacheClientFactory  ( cacheClientFactory    );
        cacheFactory.setAddressProvider     ( addressProvider       );
        cacheFactory.setConfiguration       ( cacheConfiguration    );

        Cache object = null;
        try {
            object = cacheFactory.getObject();
        } catch (Exception e) {
            e.printStackTrace();
        }

        SSMCache ssmCache = new SSMCache(object, 10000, true); //TODO be very carefully here, third param allow remove all entries!!

        ArrayList<SSMCache> ssmCaches = new ArrayList<SSMCache>();
        ssmCaches.add(0, ssmCache);

        SSMCacheManager ssmCacheManager = new SSMCacheManager();
        ssmCacheManager.setCaches(ssmCaches);

        return ssmCacheManager;
    }
*/

}
