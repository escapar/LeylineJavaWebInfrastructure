package net.masadora.mall.business.domain.product;

import net.masadora.mall.framework.domain.LeylineRepo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by POJO on 6/20/16.
 */
@Repository
public interface ProductRepo extends LeylineRepo<Product>{
    Page<Product> findByNameLikeAndRootProductIsNull(String name, Pageable p);
}
