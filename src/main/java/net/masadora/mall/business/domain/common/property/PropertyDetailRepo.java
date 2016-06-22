package net.masadora.mall.business.domain.common.property;

import net.masadora.mall.framework.domain.CacheableRepo;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by POJO on 6/21/16.
 */
public interface PropertyDetailRepo extends CacheableRepo<PropertyDetail> {
    List<PropertyDetail> findByPropertyAndDisplayTrue(Property p);

   // @Query("from PropertyDetail pd where pd.property.category.id = ?1 group by pd.property.id"
    @Query(value = "select p.id as propertyId ,p.name as propertyName,pd.id as propertyDetailId,pd.value as propertyDetailValue from mall_d_property_detail pd , mall_d_property p , mall_d_category c where pd.PROPERTY_ID = p.ID and p.CATEGORY_ID = c.id and c.id = ?1 GROUP BY p.id",nativeQuery = true)
    List<Object> findByPropertyCategoryAndDisplayTrueGroupByCategory(Long c);

}
