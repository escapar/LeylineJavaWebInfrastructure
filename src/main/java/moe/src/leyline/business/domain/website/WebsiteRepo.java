package moe.src.leyline.business.domain.website;

import java.util.List;

import javax.persistence.QueryHint;

import org.springframework.data.jpa.repository.QueryHints;

import moe.src.leyline.business.domain.user.User;
import moe.src.leyline.framework.domain.LeylineCacheableRepo;

/**
 * Created by bytenoob on 6/19/16.
 */
public interface WebsiteRepo extends LeylineCacheableRepo<Website> {

    @QueryHints(value = { @QueryHint(name = "org.hibernate.cacheable", value = "true") })
    Website getByVerifyKey(String verifyKey);

    @QueryHints(value = { @QueryHint(name = "org.hibernate.cacheable", value = "true") })
    List<Website> findByUser(User user);

}
