package net.masadora.mall.business.domain.product;

import java.util.List;
import java.util.stream.Stream;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import moe.src.leyline.framework.domain.LeylineRepo;

/**
 * Created by POJO on 6/3/16.
 */
@Repository
public interface ProductRepo extends LeylineRepo<Product> {
    @Query(value = "select name from product where id = ?1",nativeQuery = true)
    Stream<Object> findCustomedByIdToStreamNative(Long id);

    @Query(value = "select p.name from Product p where p.id = ?1")
    Stream<Object> findCustomedByIdToStreamHQL(Long id);

    default Stream<Object> findCustomedByIdToStreamDSL(Long id){
        return Stream.of(this.findAll(QProduct.product.id.eq(id)));
    }

}
