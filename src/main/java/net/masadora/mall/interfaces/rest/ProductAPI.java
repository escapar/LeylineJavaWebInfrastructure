package net.masadora.mall.interfaces.rest;

import net.masadora.mall.framework.infrastructure.common.exceptions.PersistenceException;
import net.masadora.mall.framework.interfaces.rest.LeylineRestCRUD;
import net.masadora.mall.business.domain.product.Product;
import net.masadora.mall.business.service.ProductService;
import net.masadora.mall.interfaces.dto.ProductDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * Created by POJO on 6/4/16.
 */
@RestController
@RequestMapping(value = "api/product")

public class ProductAPI extends LeylineRestCRUD<ProductService, ProductDTO, Product> {
    @Autowired
    ProductService productService;

}
