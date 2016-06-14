package net.masadora.mall.business.service;

import moe.src.leyline.framework.service.LeylineDomainService;
import net.masadora.mall.business.domain.product.Product;
import net.masadora.mall.business.domain.product.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by POJO on 6/3/16.
 */
@Service
public class ProductService extends LeylineDomainService<ProductRepo> {
    @Autowired
    ProductRepo productRepo;

    @Cacheable(key = "#id",cacheNames = "customedSQLProduct#2h")
    public Map customedSQLOperationById(Long id) {
        String[] paramsField = {"name"};
        return customedQueryResult(paramsField,
                productRepo.findCustomedByIdToStreamNative(id));
    }

    public List customedDSLOperationById(Long id) {
        return productRepo
                .findCustomedByIdToStreamDSL(id)
                .collect(Collectors.toList());
    }

    public Product findProductsById(Long id){
        return productRepo.findById(id);
    }
    public Map customedHQLOperationById(Long id) {
        String[] paramsField = {"name"};
        return customedQueryResult(paramsField,
                productRepo.findCustomedByIdToStreamHQL(id));
    }
}
