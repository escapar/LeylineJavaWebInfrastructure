package moe.src.leyline.framework.domain.user;

import javax.persistence.QueryHint;

import org.springframework.data.jpa.repository.QueryHints;

import moe.src.leyline.framework.domain.LeylineCachingRepo;

/**
 * Created by POJO on 6/8/16.
 */
public interface LeylineUserRepo<T extends LeylineUser> extends LeylineCachingRepo<T> {
    @QueryHints(value = { @QueryHint(name = "org.hibernate.cacheable", value = "true")})
    T findByNameEquals(String name);
}
