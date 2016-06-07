package moe.src.leyline.interfaces.vc.product;

import moe.src.leyline.business.service.ProductService;
import moe.src.leyline.framework.interfaces.vc.PagingAndSortingController;
import moe.src.leyline.interfaces.dto.ProductDTO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * Created by POJO on 6/7/16.
 */
@Controller
@RequestMapping("product")
public class ProductController extends PagingAndSortingController<ProductService, ProductDTO> {

}
