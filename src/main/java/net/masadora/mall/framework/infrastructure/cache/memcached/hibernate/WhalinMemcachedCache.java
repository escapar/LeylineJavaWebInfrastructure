package net.masadora.mall.framework.infrastructure.cache.memcached.hibernate;

/**
 * Created by POJO on 6/14/16.
 */

import com.mc.hibernate.memcached.LoggingMemcacheExceptionHandler;
import com.mc.hibernate.memcached.Memcache;
import com.mc.hibernate.memcached.MemcacheExceptionHandler;
import com.whalin.MemCached.MemCachedClient;
import com.whalin.MemCached.SockIOPool;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;

/**
 * DOCUMENT ME!
 *
 * @author George Wei
 */
public class WhalinMemcachedCache implements Memcache {

    private static final Logger log = LoggerFactory.getLogger(WhalinMemcachedCache.class);

    private final MemCachedClient memcachedClient;
    private final String poolName;

    private MemcacheExceptionHandler exceptionHandler = new LoggingMemcacheExceptionHandler();

    /* Constructor
     *
     * @param memcachedClient Instance of Danga's MemCachedClient
     * @param poolName SockIOPool name used to instantiate memcachedClient
     */
    public WhalinMemcachedCache(MemCachedClient memcachedClient, String poolName) {
        this.memcachedClient = memcachedClient;
        this.poolName = poolName;
    }

    public Object get(String key) {
        try {
            log.debug("MemCachedClient.get({})", key);
            return memcachedClient.get(key);
        } catch (Exception e) {
            exceptionHandler.handleErrorOnGet(key, e);
        }
        return null;
    }

    public Map<String, Object> getMulti(String... keys) {
        try {
            return memcachedClient.getMulti(keys);
        } catch (Exception e) {
            exceptionHandler.handleErrorOnGet(StringUtils.join(keys, ", "), e);
        }
        return null;
    }

    public void set(String key, int cacheTimeSeconds, Object o) {
        log.debug("MemCachedClient.set({})", key);
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            calendar.add(Calendar.SECOND, cacheTimeSeconds);

            memcachedClient.set(key, o, calendar.getTime());
        } catch (Exception e) {
            exceptionHandler.handleErrorOnSet(key, cacheTimeSeconds, o, e);
        }
    }

    public void delete(String key) {
        try {
            memcachedClient.delete(key);
        } catch (Exception e) {
            exceptionHandler.handleErrorOnDelete(key, e);
        }
    }

    public void incr(String key, int factor, int startingValue) {
        try {
            //Try to incr
            long rv = memcachedClient.incr(key, factor);

            //If the key is not found, add it with startingValue
            if (-1 == rv)
                memcachedClient.addOrIncr(key, startingValue);
        } catch (Exception e) {
            exceptionHandler.handleErrorOnIncr(key, factor, startingValue, e);
        }
    }

    public void shutdown() {
        log.debug("Shutting down danga MemCachedClient");

        //Danga's MemCachedClient does not provide a method to shutdown or
        //close it, let's shutdown its SockIOPool instead
        SockIOPool.getInstance(poolName).shutDown();
    }

    public void setExceptionHandler(MemcacheExceptionHandler exceptionHandler) {
        this.exceptionHandler = exceptionHandler;
    }
}
