package com.masadora.shop.interfaces.rest;

import com.masadora.shop.domain.product.Product;
import com.masadora.shop.interfaces.dto.ProductDTO;
import com.masadora.shop.service.ProductService;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import moe.src.leyline.interfaces.rest.DTOCRUDAPI;

/**
 * Created by bytenoob on 6/4/16.
 */
@RestController
@RequestMapping(value="product")
public class ProductAPI extends DTOCRUDAPI<ProductService,ProductDTO,Product> {

}
