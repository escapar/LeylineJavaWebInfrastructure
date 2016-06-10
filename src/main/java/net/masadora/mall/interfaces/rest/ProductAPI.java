package net.masadora.mall.interfaces.rest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
