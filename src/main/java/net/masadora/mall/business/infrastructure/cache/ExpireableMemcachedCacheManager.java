package net.masadora.mall.business.infrastructure.cache;

import net.masadora.mall.framework.infrastructure.cache.memcached.MemcachedCacheManager;
import org.springframework.stereotype.Component;

/**
 * Created by POJO on 6/12/16.
 */
@Component
public class ExpireableMemcachedCacheManager extends MemcachedCacheManager<ExpireableMemcachedCache>{

}
