package net.masadora.example.mall.interfaces.rest;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import moe.src.leyline.framework.interfaces.rest.LeylineRestCRUD;
import net.masadora.example.mall.business.domain.product.Product;
import net.masadora.example.mall.business.service.ProductService;
import net.masadora.example.mall.interfaces.dto.ProductDTO;

/**
 * Created by POJO on 6/4/16.
 */
@RestController
@RequestMapping(value = "api/product")

public class ProductAPI extends LeylineRestCRUD<ProductService, ProductDTO, Product> {
    @Autowired
    ProductService productService;

    @RequestMapping(value = "customed/SQL/{id}", method = RequestMethod.GET, produces = "application/json")
    Map customedNativeAPI(@PathVariable Long id){
        return productService.customedSQLOperationById(id);
    }

    @RequestMapping(value = "customed/DSL/{id}", method = RequestMethod.GET, produces = "application/json")
    Map customedQueryDSLAPI(@PathVariable Long id){
        return productService.customedDSLOperationById(id);
    }

    @RequestMapping(value = "customed/HQL/{id}", method = RequestMethod.GET, produces = "application/json")
    Map customedHQLAPI(@PathVariable Long id){
        return productService.customedHQLOperationById(id);
    }
}
