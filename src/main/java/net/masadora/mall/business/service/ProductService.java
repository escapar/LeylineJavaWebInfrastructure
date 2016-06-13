package net.masadora.mall.business.service;

import moe.src.leyline.framework.service.LeylineDomainService;
import net.masadora.mall.business.domain.product.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by POJO on 6/3/16.
 */
@Service
public class ProductService extends LeylineDomainService<ProductRepo> {
    @Autowired
    ProductRepo productRepo;

    @Cacheable(key = "#id",cacheNames = "wtf")
    public Map customedSQLOperationById(Long id) {
        String[] paramsField = {"id"};
        return customedQueryResult(paramsField, productRepo.findCustomedByIdToStreamNative(id));
    }

    public Map customedDSLOperationById(Long id) {
        String[] paramsField = {"id"};
        return customedQueryResult(paramsField, productRepo.findCustomedByIdToStreamDSL(id));
    }

    public Map customedHQLOperationById(Long id) {
        String[] paramsField = {"id"};
        return customedQueryResult(paramsField, productRepo.findCustomedByIdToStreamHQL(id));
    }
}
