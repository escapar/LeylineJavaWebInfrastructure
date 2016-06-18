package moe.src.example.mall.interfaces.vc.product;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import moe.src.example.mall.interfaces.dto.ProductDTO;
import moe.src.leyline.framework.interfaces.vc.LeylinePageableController;
import moe.src.example.mall.business.service.ProductService;

/**
 * Created by POJO on 6/7/16.
 */
@Controller
@RequestMapping("product")
public class ProductController extends LeylinePageableController<ProductService, ProductDTO> {

}
