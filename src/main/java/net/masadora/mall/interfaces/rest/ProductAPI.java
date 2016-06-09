package net.masadora.mall.interfaces.rest;

import net.masadora.mall.business.domain.product.Product;
import net.masadora.mall.business.service.ProductService;
import moe.src.leyline.framework.interfaces.rest.LeylineRestCRUD;
import net.masadora.mall.interfaces.dto.ProductDTO;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by bytenoob on 6/4/16.
 */
@RestController
@RequestMapping(value = "api/product")

public class ProductAPI extends LeylineRestCRUD<ProductService, ProductDTO, Product> {

}
