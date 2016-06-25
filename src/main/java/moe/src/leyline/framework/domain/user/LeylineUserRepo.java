package moe.src.leyline.framework.domain.user;

import javax.persistence.QueryHint;

import org.springframework.data.jpa.repository.QueryHints;

import moe.src.leyline.framework.domain.LeylineCacheableRepo;

/**
 * 抽象用户Repo,权限框架的一部分
 */
public interface LeylineUserRepo<T extends LeylineUser> extends LeylineCacheableRepo<T> {
    @QueryHints(value = { @QueryHint(name = "org.hibernate.cacheable", value = "true") })
    T findByNameEquals(String name);
}
