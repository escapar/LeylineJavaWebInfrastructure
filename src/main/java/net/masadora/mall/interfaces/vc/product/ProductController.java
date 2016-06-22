package net.masadora.mall.interfaces.vc.product;

import net.masadora.mall.business.domain.product.Product;
import net.masadora.mall.business.service.ProductService;
import net.masadora.mall.business.service.PropertyService;
import net.masadora.mall.framework.infrastructure.common.exceptions.ApplicationException;
import net.masadora.mall.framework.interfaces.vc.PageableController;
import net.masadora.mall.interfaces.dto.product.ProductDTO;
import net.masadora.mall.interfaces.dto.product.ProductDTOAssembler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


/**
 * Created by POJO on 6/7/16.
 */
@Controller
@RequestMapping("product")
public class ProductController extends PageableController<ProductService, Product,ProductDTO> {
    @Autowired
    ProductService productService;
    @Autowired
    PropertyService propertyService;

    public ProductController(){
        setDtoAssembler(new ProductDTOAssembler());
    }

    public String list(Model model, Pageable pageable,String keyword) throws ApplicationException {
        Page res = productService.search(keyword,pageable);
        return list(model,res);
    }

    public String list(Model model, Pageable pageable,Long categoryId,String keyword) throws ApplicationException {
        Page res = productService.search(keyword,categoryId,pageable);
        return list(model,res);
    }

    // 这个不应该用了！!!!
    public String filterThenList(Model model, Pageable pageable,String productProperties,String keyword) throws ApplicationException {
        List productPList = Arrays.asList(productProperties.split(",")).parallelStream().map(i->Long.valueOf(i)).collect(Collectors.toList());
        Page res = keyword == null || keyword.isEmpty() ?
                productService.filter(productPList,pageable) :
                productService.filter(keyword,productPList,pageable);
        return list(model,res);
    }

    public String list(Model model, Pageable pageable,Long categoryId) throws ApplicationException {
        Page res = productService.search(categoryId,pageable);
        return list(model,res);
    }

    public String listWithFilter(Long categoryId,String productProperties, Model model, Pageable pageable,String keyword) throws ApplicationException {
        List productPList = Arrays.asList(productProperties.split(",")).parallelStream().map(i->Long.valueOf(i)).collect(Collectors.toList());
        model.addAttribute("filterContents", propertyService.getPropertyDetailForPropertyFilter(categoryId,productPList));
        model.addAttribute("currentProperties", propertyService.findDetailsByPropertyIds(productPList));
        Page res = keyword == null || keyword.isEmpty() ?
                productService.filter(productPList,pageable) :
                productService.filter(keyword,productPList,pageable);
        return list(model,res);
    }

    /**
     *  EX: http://masadora.gi:9999/product/sort/name,id/asc/keyword/yo0/page/0
     **/
    @RequestMapping("sort/{property}/{direction}/keyword/{keyword}/page/{page}")
    public String listByKeywordAndDirection(Model model, @PathVariable Integer page, @PathVariable String direction, @PathVariable String property, @PathVariable String keyword, @RequestParam(required = false) Integer pagesize) throws ApplicationException {
        return list(model, getPageRequest(page,direction,property,pagesize),keyword);
    }

    /**
     *  EX: http://masadora.gi:9999/product/sort/name,id/asc/keyword/yo0/catogory/1/page/0
     **/
    @RequestMapping("sort/{property}/{direction}/keyword/{keyword}/category/{categoryId}/page/{page}")
    public String listByKeywordAndCategoryAndDirection(Model model, @PathVariable Integer page, @PathVariable String direction, @PathVariable String property, @PathVariable Long categoryId,@PathVariable String keyword, @RequestParam(required = false) Integer pagesize) throws ApplicationException {
        return list(model, getPageRequest(page,direction,property,pagesize),categoryId,keyword);
    }

    /**
     *  EX: http://masadora.gi:9999/product/sort/name,id/asc/keyword/yo0/catogory/1/page/0
     **/
    @RequestMapping("sort/{property}/{direction}/category/{categoryId}/page/{page}")
    public String listByKeywordAndDirection(Model model, @PathVariable Integer page, @PathVariable String direction, @PathVariable String property, @PathVariable Long categoryId, @RequestParam(required = false) Integer pagesize) throws ApplicationException {
        return list(model, getPageRequest(page,direction,property,pagesize),categoryId);
    }

    /**
     *  EX: http://masadora.gi:9999/product/sort/id/asc/property/4/page/0.html
     **/
    @RequestMapping("sort/{property}/{direction}/property/{productProperties}/page/{page}")
    public String listByProductProperties(Model model, @PathVariable Integer page, @PathVariable String direction, @PathVariable String property, @PathVariable String productProperties,@RequestParam(required = false) Integer pagesize) throws ApplicationException {
        return filterThenList(model, getPageRequest(page,direction,property,pagesize),productProperties,null);
    }

    /**
     *  EX: http://masadora.gi:9999/product/sort/id/asc/keyword/yoo/property/4/page/0.html
     **/
    @RequestMapping("sort/{property}/{direction}/keyword/{keyword}/property/{productProperties}/page/{page}")
    public String listByProductPropertiesAndKeyword(Model model, @PathVariable Integer page, @PathVariable String direction, @PathVariable String property, @PathVariable String productProperties, @PathVariable String keyword, @RequestParam(required = false) Integer pagesize) throws ApplicationException {
        return filterThenList(model, getPageRequest(page,direction,property,pagesize),productProperties,keyword);
    }

    @RequestMapping("sort/{property}/{direction}/property/{productProperties}/category/{categoryId}/page/{page}")
    public String listByProductPropertiesAndCategoryId(Model model, @PathVariable Long categoryId,@PathVariable Integer page, @PathVariable String direction, @PathVariable String property, @PathVariable String productProperties,@RequestParam(required = false) Integer pagesize) throws ApplicationException {
        return listWithFilter(categoryId,productProperties,model, getPageRequest(page,direction,property,pagesize),null);
    }

}

