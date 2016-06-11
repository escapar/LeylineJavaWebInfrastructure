package net.masadora.mall.business.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import com.querydsl.core.types.dsl.BooleanExpression;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import moe.src.leyline.framework.service.LeylineDomainService;
import net.masadora.mall.business.domain.product.Product;
import net.masadora.mall.business.domain.product.ProductRepo;
import net.masadora.mall.business.domain.product.QProduct;

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
