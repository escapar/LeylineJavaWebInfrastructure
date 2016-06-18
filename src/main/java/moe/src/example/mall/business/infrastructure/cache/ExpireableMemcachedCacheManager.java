package moe.src.example.mall.business.infrastructure.cache;

import org.springframework.stereotype.Component;

import moe.src.leyline.framework.infrastructure.cache.memcached.MemcachedCacheManager;

/**
 * Created by POJO on 6/12/16.
 */
@Component
public class ExpireableMemcachedCacheManager extends MemcachedCacheManager<ExpireableMemcachedCache>{

}
