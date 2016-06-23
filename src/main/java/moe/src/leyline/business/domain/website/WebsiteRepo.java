package moe.src.leyline.business.domain.website;

import moe.src.leyline.framework.domain.LeylineCacheableRepo;
import org.springframework.data.jpa.repository.QueryHints;

import javax.persistence.QueryHint;

/**
 * Created by bytenoob on 6/19/16.
 */
public interface WebsiteRepo extends LeylineCacheableRepo<Website> {

    @QueryHints(value = { @QueryHint(name = "org.hibernate.cacheable", value = "true")})
    Website getByVerifyKey(String verifyKey);
}
