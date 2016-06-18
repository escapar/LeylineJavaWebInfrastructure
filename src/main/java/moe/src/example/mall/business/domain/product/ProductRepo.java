package moe.src.example.mall.business.domain.product;

import javax.persistence.QueryHint;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.stereotype.Repository;

import moe.src.leyline.framework.domain.LeylineRepo;

/**
 * Created by POJO on 6/3/16.
 */
@Repository
public interface ProductRepo extends LeylineRepo<Product> {

    @Query(value = "select p.name from product p where p.id = ?1", nativeQuery = true)
    Iterable<Object> findCustomedByIdSQL(Long id);

    @Query(value = "select p.id,p.name from Product p where p.id = ?1")
    @QueryHints(value = { @QueryHint(name = "org.hibernate.cacheable", value = "true")})
    Iterable<Object> findCustomedByIdHQL(Long id);

    @QueryHints(value = { @QueryHint(name = "org.hibernate.cacheable", value = "true")})
    Product findById(Long id);

    @QueryHints(value = { @QueryHint(name = "org.hibernate.cacheable", value = "true")})
    default Iterable<Product> findCustomedByIdToStreamDSL(Long id) {
        return this.findAll(QProduct.product.id.eq(id));
    }
}
