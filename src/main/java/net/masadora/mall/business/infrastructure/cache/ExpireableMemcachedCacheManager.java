package net.masadora.mall.business.infrastructure.cache;

import com.whalin.MemCached.MemCachedClient;
import com.whalin.MemCached.SockIOPool;
import moe.src.leyline.framework.infrastructure.cache.memcached.MemcachedCache;
import moe.src.leyline.framework.infrastructure.cache.memcached.MemcachedCacheManager;
import moe.src.leyline.framework.infrastructure.cache.memcached.MemcachedPoolConfig;
import org.jodah.typetools.TypeResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.support.AbstractCacheManager;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashSet;

/**
 * Created by POJO on 6/12/16.
 */
@Component
public class ExpireableMemcachedCacheManager extends MemcachedCacheManager<ExpireableMemcachedCache>{

}
