package net.masadora.mall.framework.infrastructure.cache.memcached;

import com.whalin.MemCached.MemCachedClient;
import org.springframework.cache.Cache;
import org.springframework.cache.support.SimpleValueWrapper;
import org.springframework.util.Assert;

import java.util.Date;

/**
 * Created by POJO on 6/12/16.
 */
public abstract class MemcachedCache implements Cache {
    private MemCachedClient client;
    private String name;
    private Date expiry;
    public MemcachedCache(){

    }

    public MemcachedCache(String name,MemCachedClient client) {
        Assert.notNull(client, "Memcache client must not be null");
        this.client = client;
        this.expiry = getExpiryByName(name);
        this.name = name;
    }

    public abstract Date getExpiryByName(String name);

    @Override
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
    public void put(Object key, Object value) {
        if(expiry == null || expiry.before(new Date())) {
            this.client.set(objectToString(key), value);
        }else {
            this.client.set(objectToString(key), value, expiry);
        }
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
