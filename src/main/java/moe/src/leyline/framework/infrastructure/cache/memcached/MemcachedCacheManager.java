package moe.src.leyline.framework.infrastructure.cache.memcached;

import com.whalin.MemCached.MemCachedClient;
import com.whalin.MemCached.SockIOPool;
import org.jodah.typetools.TypeResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.support.AbstractCacheManager;

import java.util.Collection;
import java.util.HashSet;

/**
 * Created by POJO on 6/12/16.
 */

public class MemcachedCacheManager<T extends MemcachedCache> extends AbstractCacheManager{
    private Collection<Cache> caches = new HashSet<>();
    private MemCachedClient client;
    private Class<MemcachedCache> cacheT  = (Class<MemcachedCache>)TypeResolver.resolveRawArguments(MemcachedCacheManager.class, getClass())[0];;

    @Autowired
    MemcachedPoolConfig memcachedPoolConfig;

    public MemcachedCacheManager(){
    }

    public MemcachedCacheManager(MemCachedClient client){
        setClient(client);
    }

    @Override
    protected Collection<? extends Cache> loadCaches() {
        return this.caches;
    }

    @Override
    public void afterPropertiesSet() {
        checkState();
        super.afterPropertiesSet();
    }

    public MemCachedClient getClient(){
        return client;
    }

    public MemcachedCacheManager setClient(MemCachedClient m){
        client = m;
        return this;
    }

    public void setCaches(Collection<Cache> caches) {
        this.caches = caches;
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
                try {
                    rt = cacheT
                            .getConstructor(String.class, MemCachedClient.class)
                            .newInstance(name, client);
                    addCache(rt);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
        return rt;
    }

    private void checkState() {
        if(client == null) {
            setClient(buildClient());
        }
    }

    private MemCachedClient buildClient(){
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
            return new MemCachedClient(poolName);
    }

    private void updateCaches() {
        if(caches != null){
            caches.forEach(e-> {
                if(e instanceof MemcachedCache){
                    MemcachedCache memcachedCache = (MemcachedCache)e;
                    memcachedCache.setClient(client);
                }
            });
        }
    }
}
