package net.masadora.mall.business.infrastructure.cache;

import com.whalin.MemCached.MemCachedClient;
import com.whalin.MemCached.SockIOPool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.support.AbstractCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashSet;

/**
 * Created by POJO on 6/12/16.
 */
@Component
public class MemcachedCacheManager extends AbstractCacheManager{
    private Collection<Cache> caches = new HashSet<>();
    private MemCachedClient client;
    @Autowired
    MemcachedPoolConfig memcachedPoolConfig;

    private MemcachedCacheManager(){

    }

    public MemcachedCacheManager(MemCachedClient client){
        setClient(client);
    }

    @Override
    protected Collection<? extends Cache> loadCaches() {
        return this.caches;
    }

    public void setCaches(Collection<Cache> caches) {
        this.caches = caches;
    }

    public void setClient(MemCachedClient client) {
        this.client = client;
        updateCaches();
    }

    public Cache getCache(String name){
        checkState();
        Cache rt = null;
        if (name != null) {
            try{
                rt = super.getCache(name);
            }catch (Exception e) {
                e.printStackTrace();
            }
            if (rt == null) {
                rt = new MemcachedCache(name, client);
                addCache(rt);
            }
        }
        return rt;
    }

    private void checkState() {
        if(client == null){
            String poolName = "masadoraPool";
            SockIOPool pool = SockIOPool.getInstance(poolName);

            String servers = memcachedPoolConfig.getServers();
            String weights = memcachedPoolConfig.getWeights();

            String[] split = weights.split(",");
            Integer[] weight = new Integer[split.length];

            for (int i = 0; i < split.length; i++) {
                weight[i] = Integer.valueOf(split[i]);
            }

            pool.setServers(servers.split(","));
            pool.setWeights(weight);
            pool.setFailover(memcachedPoolConfig.getFailover());
            pool.setFailback(memcachedPoolConfig.getFailback());
            pool.setInitConn(memcachedPoolConfig.getInitConn());
            pool.setMinConn(memcachedPoolConfig.getMinConn());
            pool.setMaxConn(memcachedPoolConfig.getMaxConn());
            pool.setMaintSleep(memcachedPoolConfig.getMaintSleep());
            pool.setNagle(memcachedPoolConfig.getNagle());
            pool.setSocketTO(memcachedPoolConfig.getSocketTO());
            pool.setAliveCheck(memcachedPoolConfig.getAliveCheck());
            pool.initialize();
            client = new MemCachedClient(poolName);
        }
    }

    private void updateCaches() {
        if(caches != null){
            caches.forEach(e-> {
                if(e instanceof MemcachedCache){
                    MemcachedCache memcacheCache = (MemcachedCache)e;
                    memcacheCache.setClient(client);
                }
            });
        }
    }
}
