package com.masadora.mall.interfaces.vc.product;

import com.masadora.mall.business.service.ProductService;
import com.masadora.mall.framework.interfaces.vc.PagingAndSortingController;

import com.masadora.mall.interfaces.dto.ProductDTO;

import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestMapping;


/**
 * Created by POJO on 6/7/16.
 */
@Controller
@RequestMapping("product")
public class ProductController extends PagingAndSortingController<ProductService , ProductDTO> {

}
