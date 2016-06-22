package net.masadora.mall.business.domain.common.property;

import net.masadora.mall.framework.domain.CacheableRepo;

import java.util.List;

/**
 * Created by POJO on 6/22/16.
 */
public interface PropertyRepo extends CacheableRepo<Property> {
    List<Property> findByCategoryId(Long categoryId);
}
