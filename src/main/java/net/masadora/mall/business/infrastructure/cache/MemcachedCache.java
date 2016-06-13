package net.masadora.mall.business.infrastructure.cache;

import com.whalin.MemCached.MemCachedClient;
import org.springframework.cache.Cache;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.support.SimpleValueWrapper;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.concurrent.Callable;

/**
 * Created by POJO on 6/12/16.
 */
@Component
public class MemcachedCache implements Cache {
    private MemCachedClient client;
    private String name;

    public MemcachedCache(){

    }

    public MemcachedCache(String name,MemCachedClient client) {
        Assert.notNull(client, "Memcache client must not be null");
        // TODO validate memcache "alive"
        this.client = client;
        this.name = name;
    }

    @Override
    @Cacheable(value="xxxx")
    public String getName() {
        return this.name;
    }

    @Override
    public Object getNativeCache() {
        return this.client;
    }

    @Override
    public ValueWrapper get(Object key) {
        Object value = this.client.get(objectToString(key));
        return (value != null ? new SimpleValueWrapper(value) : null);
    }

    @Override
    public <T> T get(Object key, Class<T> type) {
        return (T)get(key);
    }

    @Override
    public <T> T get(Object key, Callable<T> valueLoader) {
        return (T)get(key);
    }

    @Override
    public void put(Object key, Object value) {
        this.client.set(objectToString(key), value);

    }

    @Override
    public ValueWrapper putIfAbsent(Object key, Object value) {
        put(key,value);
        return null;
    }

    @Override
    public void evict(Object key) {
        this.client.delete(objectToString(key));

    }

    @Override
    public void clear() {
        // TODO delete all data
    }

    private static String objectToString(Object object) {
        if (object == null) {
            return null;
        } else if (object instanceof String) {
            return (String) object;
        } else {
            return object.toString();
        }
    }

    public void setClient(MemCachedClient client){
        this.client = client;
    }

    public MemCachedClient getClient() {
        return client;
    }

    public void setName(String name) {
        this.name = name;
    }
}
