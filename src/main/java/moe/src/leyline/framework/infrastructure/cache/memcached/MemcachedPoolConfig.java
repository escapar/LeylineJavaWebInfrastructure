package moe.src.leyline.framework.infrastructure.cache.memcached;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * Created by POJO on 6/12/16.
 */
@Configuration
@ConfigurationProperties(prefix = "memcached")
@PropertySource("memcached.properties")

public class MemcachedPoolConfig {
    @Value("${database.memcached.object.servers}")
    private String servers;

    @Value("${memcached.object.weights}")
    private String weights;

    private Boolean failover;

    private Boolean failback;

    private Integer initConn;

    private Integer minConn;

    private Integer maxConn;

    private Integer maintSleep;

    private Boolean nagle;

    private Integer socketTO;

    private Boolean aliveCheck;

    public String getServers() {
        return servers;
    }

    public void setServers(String servers) {
        this.servers = servers;
    }

    public String getWeights() {
        return weights;
    }

    public void setWeights(String weights) {
        this.weights = weights;
    }

    public Boolean getFailover() {
        return failover;
    }

    public void setFailover(Boolean failover) {
        this.failover = failover;
    }

    public Boolean getFailback() {
        return failback;
    }

    public void setFailback(Boolean failback) {
        this.failback = failback;
    }

    public Integer getInitConn() {
        return initConn;
    }

    public void setInitConn(Integer initConn) {
        this.initConn = initConn;
    }

    public Integer getMinConn() {
        return minConn;
    }

    public void setMinConn(Integer minConn) {
        this.minConn = minConn;
    }

    public Integer getMaxConn() {
        return maxConn;
    }

    public void setMaxConn(Integer maxConn) {
        this.maxConn = maxConn;
    }

    public Integer getMaintSleep() {
        return maintSleep;
    }

    public void setMaintSleep(Integer maintSleep) {
        this.maintSleep = maintSleep;
    }

    public Boolean getNagle() {
        return nagle;
    }

    public void setNagle(Boolean nagle) {
        this.nagle = nagle;
    }

    public Integer getSocketTO() {
        return socketTO;
    }

    public void setSocketTO(Integer socketTO) {
        this.socketTO = socketTO;
    }

    public Boolean getAliveCheck() {
        return aliveCheck;
    }

    public void setAliveCheck(Boolean aliveCheck) {
        this.aliveCheck = aliveCheck;
    }
}
