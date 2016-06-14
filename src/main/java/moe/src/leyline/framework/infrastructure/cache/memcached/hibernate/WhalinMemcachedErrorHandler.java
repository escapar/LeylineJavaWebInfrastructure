package moe.src.leyline.framework.infrastructure.cache.memcached.hibernate;

/**
 * Created by POJO on 6/14/16.
 */

import com.whalin.MemCached.ErrorHandler;
import com.whalin.MemCached.MemCachedClient;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * DOCUMENT ME!
 *
 * @author George Wei
 */
public class WhalinMemcachedErrorHandler implements ErrorHandler {
    private static final Logger log = LoggerFactory.getLogger(WhalinMemcachedErrorHandler.class);

    public void handleErrorOnDelete(MemCachedClient client, Throwable error,
                                    String cacheKey) {
        log.error("Error on delete cacheKey [{}]: {}", cacheKey, error);
    }

    public void handleErrorOnFlush(MemCachedClient client, Throwable error) {
        log.error("Error on flush: {}", error);
    }

    public void handleErrorOnGet(MemCachedClient client, Throwable error,
                                 String cacheKey) {
        log.error("Error on get cacheKey [{}]: {}", cacheKey, error);
    }

    public void handleErrorOnGet(MemCachedClient client, Throwable error,
                                 String[] cacheKeys) {
        handleErrorOnGet(client, error, StringUtils.join(cacheKeys, ", "));
    }

    public void handleErrorOnInit(MemCachedClient client, Throwable error) {
        log.error("Error on initialization: {}", error);
    }

    public void handleErrorOnSet(MemCachedClient client, Throwable error,
                                 String cacheKey) {
        log.error("Error on set cacheKey [{}]: {}", cacheKey, error);
    }

    public void handleErrorOnStats(MemCachedClient client, Throwable error) {
        log.error("Error on stats: {}", error);
    }
}
