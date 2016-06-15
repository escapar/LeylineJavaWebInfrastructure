package net.masadora.mall.business.service;

import moe.src.leyline.framework.infrastructure.common.Utils;
import moe.src.leyline.framework.infrastructure.common.exceptions.PersistenceException;
import moe.src.leyline.framework.service.LeylineDomainService;
import net.masadora.mall.business.domain.product.Product;
import net.masadora.mall.business.domain.product.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.LinkedHashMap;
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

    public List<Map<String,Object>> customedSQLOperationById(Long id) throws PersistenceException{
        String[] paramsField = {"name"};
        return resMap(paramsField, productRepo.findCustomedByIdSQL(id));
    }

    public List customedDSLOperationById(Long id) {
        return Utils.it2List(productRepo.findCustomedByIdToStreamDSL(id));
    }

    public Product findProductsById(Long id){
        return productRepo.findById(id);
    }

    public List<Map<String,Object>> customedHQLOperationById(Long id) {
        String[] paramsField = {"id","name"};
        Product p = new Product();
        p.setName("sag");
        p.setPrice(1000);
        productRepo.save(p);
        p = new Product();

        productRepo.save(p);
        return resMap(paramsField, productRepo.findCustomedByIdHQL(id));
    }

}
