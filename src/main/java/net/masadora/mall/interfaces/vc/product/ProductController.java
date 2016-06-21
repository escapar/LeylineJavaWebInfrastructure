package net.masadora.mall.interfaces.vc.product;

import net.masadora.mall.framework.infrastructure.common.exceptions.LeylineException;
import net.masadora.mall.framework.interfaces.dto.assembler.DTOAssembler;
import net.masadora.mall.framework.interfaces.vc.LeylinePageableController;
import net.masadora.mall.business.service.ProductService;
import net.masadora.mall.interfaces.dto.ProductDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


/**
 * Created by POJO on 6/7/16.
 */
@Controller
@RequestMapping("product")
public class ProductController extends LeylinePageableController<ProductService, ProductDTO> {
    @Autowired
    ProductService productService;

    public String list(Model model, Pageable pageable,String keyword) throws LeylineException {
        Page res = productService.search(keyword,pageable);
        model.addAttribute("page", DTOAssembler.buildPageDTO(res, ProductDTO.class));
        return "product/list";
    }

    /**
     *  EX: http://masadora.gi:9999/product/sort/name,id/asc/keyword/yo0/page/0
     **/
    @RequestMapping("sort/{property}/{direction}/keyword/{keyword}/page/{page}")
    public String listByPropertyAndDirection(Model model, @PathVariable Integer page, @PathVariable String direction, @PathVariable String property, @PathVariable String keyword, @RequestParam(required = false) Integer pagesize) throws LeylineException {
        Sort.Direction d = Sort.Direction.DESC;
        Pageable pReal;
        if (direction != null && !direction.isEmpty() && direction.toUpperCase().equals("ASC")) {
            d = Sort.Direction.ASC;
        }
        if(pagesize == null){
            pagesize = getPagesize();
        }
        pReal = new PageRequest(page, pagesize, d, property.split(","));
        return list(model, pReal,keyword);
    }
}
