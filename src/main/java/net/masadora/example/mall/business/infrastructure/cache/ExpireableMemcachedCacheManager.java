package net.masadora.example.mall.business.infrastructure.cache;

import moe.src.leyline.framework.infrastructure.cache.memcached.MemcachedCacheManager;

import org.springframework.stereotype.Component;

/**
 * Created by POJO on 6/12/16.
 */
@Component
public class ExpireableMemcachedCacheManager extends MemcachedCacheManager<ExpireableMemcachedCache>{

}
