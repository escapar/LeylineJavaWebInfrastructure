package moe.src.leyline.business.domain.website;

import javax.persistence.QueryHint;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;

import moe.src.leyline.business.domain.user.DomainUser;
import moe.src.leyline.framework.domain.LeylineCachingRepo;

/**
 * Created by bytenoob on 6/19/16.
 */
public interface WebsiteRepo extends LeylineCachingRepo<Website> {

    @QueryHints(value = { @QueryHint(name = "org.hibernate.cacheable", value = "true")})
    Website getByVerifyKey(String verifyKey);
}
