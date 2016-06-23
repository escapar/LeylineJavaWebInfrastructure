package net.masadora.mall.framework.domain.user;

import net.masadora.mall.business.domain.user.User;
import net.masadora.mall.framework.domain.CacheableRepo;
import org.springframework.data.jpa.repository.QueryHints;

import javax.persistence.QueryHint;

/**
 * 抽象用户Repo,权限框架的一部分
 */
public interface AppUserRepo<T extends AppUser> extends CacheableRepo<User> {
    @QueryHints(value = { @QueryHint(name = "org.hibernate.cacheable", value = "true")})
    User findByNameEquals(String name);
}
