package moe.src.leyline.interfaces.rest;

import moe.src.leyline.business.domain.product.Product;
import moe.src.leyline.business.service.ProductService;
import moe.src.leyline.framework.interfaces.rest.CRUDAPI;
import moe.src.leyline.framework.interfaces.rest.RestCRUDAPI;
import moe.src.leyline.interfaces.dto.ProductDTO;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by bytenoob on 6/4/16.
 */
@RestController
@RequestMapping(value = "api/product")

public class ProductAPI extends RestCRUDAPI<ProductService, ProductDTO, Product> {

}
