package net.masadora.mall.interfaces.vc.product;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import moe.src.leyline.framework.interfaces.vc.LeylinePageableController;
import net.masadora.mall.business.service.ProductService;
import net.masadora.mall.interfaces.dto.ProductDTO;


/**
 * Created by POJO on 6/7/16.
 */
@Controller
@RequestMapping("product")
public class ProductController extends LeylinePageableController<ProductService, ProductDTO> {

}
