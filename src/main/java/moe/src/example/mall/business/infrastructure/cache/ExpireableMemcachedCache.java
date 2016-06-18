package moe.src.example.mall.business.infrastructure.cache;

import java.util.Date;

import com.whalin.MemCached.MemCachedClient;

import org.joda.time.DateTime;
import org.springframework.stereotype.Component;

import moe.src.leyline.framework.infrastructure.cache.memcached.MemcachedCache;

/**
 * Created by POJO on 6/12/16.
 **/
@Component
public class ExpireableMemcachedCache extends MemcachedCache {

    public ExpireableMemcachedCache(){

    }

    public ExpireableMemcachedCache(String name,MemCachedClient client) {
        super(name,client);
    }

    @Override
    public Date getExpiryByName(String name){
        String ttl =
                name.lastIndexOf("#") > -1?
                        name.substring(name.lastIndexOf("#")) : null;
        String ts =
                name.lastIndexOf("!") > -1?
                        name.substring(name.lastIndexOf("!")) : null;

        DateTime now = new DateTime();
        if(ttl!=null && !ttl.isEmpty()){
            ttl = ttl.replace("#","").toLowerCase();
            if(ttl.contains("h")){
                return now.plusHours(Integer.valueOf(ttl.replace("h",""))).toDate();
            }
            if(ttl.contains("d")){
                return now.plusDays(Integer.valueOf(ttl.replace("d",""))).toDate();
            }
            if(ttl.contains("m")){
                return now.plusMinutes(Integer.valueOf(ttl.replace("m",""))).toDate();
            }
            return now.plusMillis(Integer.valueOf(ttl)).toDate();
        }else if(ts!=null && !ts.isEmpty()){
            return new DateTime(Long.valueOf(ts)).toDate();
        }
        return null;
    }


}
