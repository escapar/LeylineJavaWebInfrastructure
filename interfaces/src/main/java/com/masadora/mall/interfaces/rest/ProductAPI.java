package com.masadora.mall.interfaces.rest;

import com.masadora.mall.business.domain.product.Product;
import com.masadora.mall.business.service.ProductService;
import com.masadora.mall.interfaces.dto.ProductDTO;
import com.masadora.mall.framework.interfaces.rest.DTOCRUDAPI;

import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by bytenoob on 6/4/16.
 */
@RestController
@Repository
@RequestMapping(value="product")
public class ProductAPI extends DTOCRUDAPI<ProductService,ProductDTO,Product> {

}
