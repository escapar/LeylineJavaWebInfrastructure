package moe.src.example.mall.interfaces.rest;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import moe.src.leyline.framework.infrastructure.common.exceptions.PersistenceException;
import moe.src.leyline.framework.interfaces.rest.LeylineRestCRUD;
import net.masadora.mall.business.domain.product.Product;
import net.masadora.mall.business.service.ProductService;
import net.masadora.mall.interfaces.dto.ProductDTO;

/**
 * Created by POJO on 6/4/16.
 */
@RestController
@RequestMapping(value = "api/product")

public class ProductAPI extends LeylineRestCRUD<ProductService, ProductDTO, Product> {
    @Autowired
    ProductService productService;

    @RequestMapping(value = "customed/SQL/{id}", method = RequestMethod.GET, produces = "application/json")
    List<Map<String,Object>> customedNativeAPI(@PathVariable Long id) throws PersistenceException {
        return productService.customedSQLOperationById(id);
    }

    @RequestMapping(value = "customed/DSL/{id}", method = RequestMethod.GET, produces = "application/json")
    List<ProductDTO> customedQueryDSLAPI(@PathVariable Long id) {
        return productService.customedDSLOperationById(id);
    }

    @RequestMapping(value = "customed/HQL/{id}", method = RequestMethod.GET, produces = "application/json")
    List<Map<String,Object>> customedHQLAPI(@PathVariable Long id) {
        return productService.customedHQLOperationById(id);
    }

    @RequestMapping(value = "/currentUser" , method = RequestMethod.GET, produces = "application/json")
    User getUser(){
        return getCurrentUser();
    }

    @RequestMapping(value = "/test/{id}" , method = RequestMethod.GET, produces = "application/json")
    Product getProductById(@PathVariable Long id){
        return productService.findProductsById(id);
    }
}
