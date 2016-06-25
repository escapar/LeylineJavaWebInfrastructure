package moe.src.leyline.business.domain.website;

import moe.src.leyline.framework.domain.LeylineCacheableRepo;

/**
 * Created by bytenoob on 6/25/16.
 */
public interface WebsiteRelationRepo extends LeylineCacheableRepo<WebsiteRelation> {
    WebsiteRelation findByMasterAndServant(Website master, Website servant);
}
