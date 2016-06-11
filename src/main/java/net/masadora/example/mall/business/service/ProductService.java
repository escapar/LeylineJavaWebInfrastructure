package net.masadora.example.mall.business.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import moe.src.leyline.framework.service.LeylineDomainService;
import net.masadora.example.mall.business.domain.product.ProductRepo;

/**
 * Created by POJO on 6/3/16.
 */
@Service
public class ProductService extends LeylineDomainService<ProductRepo> {
    @Autowired
    ProductRepo productRepo;

    public Map customedSQLOperationById(Long id){
        String[] paramsField = {"id"};
        return customedQueryResult(paramsField,productRepo.findCustomedByIdToStreamNative(id));
    }

    public Map customedDSLOperationById(Long id){
        String[] paramsField = {"id"};
        return customedQueryResult(paramsField,productRepo.findCustomedByIdToStreamDSL(id));
    }

    public Map customedHQLOperationById(Long id){
        String[] paramsField = {"id"};
        return customedQueryResult(paramsField,productRepo.findCustomedByIdToStreamHQL(id));
    }
}
