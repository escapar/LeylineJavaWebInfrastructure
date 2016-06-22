package net.masadora.mall.business.domain.product;

import net.masadora.mall.framework.domain.LeylineRepo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.stereotype.Repository;

import javax.persistence.QueryHint;
import java.util.List;

/**
 * Created by POJO on 6/20/16.
 */
@Repository
public interface ProductRepo extends LeylineRepo<Product>,QuerydslBinderCustomizer<QProduct> {
    @QueryHints(value = { @QueryHint(name = "org.hibernate.cacheable", value = "true")})
    Page<Product> findByNameLikeAndRootProductIsNull(String name, Pageable p);

   // @Query(value = "select p from mall_d_product p, mall_m2m_category_product r where r.CATEGORY_ID = ?1 and r.PRODUCT_ID=p.ID and p.ROOT_PROUDCT_ID = null and p.name like ?2 ",nativeQuery = true)
    @QueryHints(value = { @QueryHint(name = "org.hibernate.cacheable", value = "true")})
    Page<Product> findByNameLikeAndCategories_IdAndRootProductIsNull(String name,Long categoryId,Pageable p);

    @QueryHints(value = { @QueryHint(name = "org.hibernate.cacheable", value = "true")})
    Page<Product> findByProperties_IdInAndRootProductIsNull(List<Long> properties,Pageable p);

    @QueryHints(value = { @QueryHint(name = "org.hibernate.cacheable", value = "true")})
    Page<Product> findByNameLikeAndProperties_IdInAndRootProductIsNull(String name,List<Long> properties,Pageable p);

    @QueryHints(value = { @QueryHint(name = "org.hibernate.cacheable", value = "true")})
    Page<Product> findByCategories_IdAndRootProductIsNull(Long categoryId,Pageable p);


    @Override //TODO: 这个还没测试!
    default void customize(QuerydslBindings bindings, QProduct product) {

        bindings.bind(product.name).first((path, value) -> path.contains(value));
    }
}
