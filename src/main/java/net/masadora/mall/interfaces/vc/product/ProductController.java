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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


/**
 * 商品相关后端渲染控制器
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


    /**
     * 根据类目和关键词搜索
     * @param model
     * @param pageable
     * @param categoryId
     * @param keyword
     * @return
     * @throws ApplicationException
     */
    public String listByCategoryAndKeyword(Model model, Pageable pageable, Long categoryId, String keyword) throws ApplicationException {
        Page res = productService.search(keyword,categoryId,pageable);
        return list(model,res);
    }

    /**
     * 带筛选器列出
     * @param model
     * @param pageable
     * @param productProperties
     * @param categoryId
     * @param keyword
     * @return
     * @throws ApplicationException
     */
    public String listWithFilter(Model model, Pageable pageable, String productProperties,Long categoryId,String keyword) throws ApplicationException {
        List productPList = splitPropertIdsToList(productProperties);
        model.addAttribute("filterContents", propertyService.getPropertyDetailForPropertyFilter(categoryId,productPList));
        model.addAttribute("currentProperties", propertyService.findDetailsStringByPropertyIds(productPList));
        return listByPropertiesAndKeyword(model,pageable,productProperties,keyword);
    }

    /**
     * 根据属性字符串和关键词搜索
     * @param model
     * @param pageable
     * @param productProperties
     * @param keyword
     * @return
     * @throws ApplicationException
     */
    public String listByPropertiesAndKeyword(Model model, Pageable pageable,String productProperties,String keyword) throws ApplicationException {
        Page res = buildResultsFilteredByProperties(productProperties,keyword,pageable);
        return list(model,res);
    }

    /**
     * 根据属性字符串造结果
     * @param productProperties
     * @param keyword
     * @param pageable
     * @return
     */
    public Page buildResultsFilteredByProperties(String productProperties,String keyword,Pageable pageable){
        List productPList = splitPropertIdsToList(productProperties);
        return keyword == null || keyword.isEmpty() ?
                productService.filter(productPList,pageable) :
                productService.filter(keyword,productPList,pageable);
    }

    @RequestMapping("sort/{property}/{direction}/keyword/{keyword}/page/{page}")
    public String listByKeywordAndDirection(Model model, @PathVariable Integer page, @PathVariable String direction, @PathVariable String property, @PathVariable String keyword, @RequestParam(required = false) Integer pagesize) throws ApplicationException {
        return listByCategoryAndKeyword(model, getPageRequest(page,direction,property,pagesize),null,keyword);
    }

    @RequestMapping("sort/{property}/{direction}/keyword/{keyword}/category/{categoryId}/page/{page}")
    public String listByKeywordAndCategoryAndDirection(Model model, @PathVariable Integer page, @PathVariable String direction, @PathVariable String property, @PathVariable Long categoryId,@PathVariable String keyword, @RequestParam(required = false) Integer pagesize) throws ApplicationException {
        return listByCategoryAndKeyword(model, getPageRequest(page,direction,property,pagesize),categoryId,keyword);
    }

    @RequestMapping("sort/{property}/{direction}/category/{categoryId}/page/{page}")
    public String listByKeywordAndDirection(Model model, @PathVariable Integer page, @PathVariable String direction, @PathVariable String property, @PathVariable Long categoryId, @RequestParam(required = false) Integer pagesize) throws ApplicationException {
        return listByCategoryAndKeyword(model, getPageRequest(page,direction,property,pagesize),categoryId,null);
    }

    @RequestMapping("sort/{property}/{direction}/property/{productProperties}/page/{page}")
    public String listByProductPropertiesAndDirection(Model model, @PathVariable Integer page, @PathVariable String direction, @PathVariable String property, @PathVariable String productProperties,@RequestParam(required = false) Integer pagesize) throws ApplicationException {
        return listByPropertiesAndKeyword(model, getPageRequest(page,direction,property,pagesize),productProperties,null);
    }

    @RequestMapping("sort/{property}/{direction}/keyword/{keyword}/property/{productProperties}/page/{page}")
    public String listByProductPropertiesAndKeywordAndDirection(Model model, @PathVariable Integer page, @PathVariable String direction, @PathVariable String property, @PathVariable String productProperties, @PathVariable String keyword, @RequestParam(required = false) Integer pagesize) throws ApplicationException {
        return listByPropertiesAndKeyword(model, getPageRequest(page,direction,property,pagesize),productProperties,keyword);
    }

    @RequestMapping("sort/{property}/{direction}/property/{productProperties}/category/{categoryId}/page/{page}")
    public String listByProductPropertiesAndCategoryIdAndDirection(Model model, @PathVariable Long categoryId,@PathVariable Integer page, @PathVariable String direction, @PathVariable String property, @PathVariable String productProperties,@RequestParam(required = false) Integer pagesize) throws ApplicationException {
        return listWithFilter(model, getPageRequest(page,direction,property,pagesize),productProperties,categoryId,null);
    }


    @RequestMapping("sort/{property}/keyword/{keyword}/page/{page}")
    public String listByKeyword(Model model, @PathVariable Integer page, @PathVariable String direction, @PathVariable String property, @PathVariable String keyword, @RequestParam(required = false) Integer pagesize) throws ApplicationException {
        return listByCategoryAndKeyword(model, getPageRequest(page,direction,property,pagesize),null,keyword);
    }

    @RequestMapping("sort/{property}/keyword/{keyword}/category/{categoryId}/page/{page}")
    public String listByKeywordAndCategory(Model model, @PathVariable Integer page, @PathVariable String direction, @PathVariable String property, @PathVariable Long categoryId,@PathVariable String keyword, @RequestParam(required = false) Integer pagesize) throws ApplicationException {
        return listByCategoryAndKeyword(model, getPageRequest(page,direction,property,pagesize),categoryId,keyword);
    }

    @RequestMapping("sort/{property}/category/{categoryId}/page/{page}")
    public String listByKeyword(Model model, @PathVariable Integer page, @PathVariable String direction, @PathVariable String property, @PathVariable Long categoryId, @RequestParam(required = false) Integer pagesize) throws ApplicationException {
        return listByCategoryAndKeyword(model, getPageRequest(page,direction,property,pagesize),categoryId,null);
    }

    @RequestMapping("sort/{property}/property/{productProperties}/page/{page}")
    public String listByProductProperties(Model model, @PathVariable Integer page, @PathVariable String direction, @PathVariable String property, @PathVariable String productProperties,@RequestParam(required = false) Integer pagesize) throws ApplicationException {
        return listByPropertiesAndKeyword(model, getPageRequest(page,direction,property,pagesize),productProperties,null);
    }

    @RequestMapping("sort/{property}/keyword/{keyword}/property/{productProperties}/page/{page}")
    public String listByProductPropertiesAndKeyword(Model model, @PathVariable Integer page, @PathVariable String direction, @PathVariable String property, @PathVariable String productProperties, @PathVariable String keyword, @RequestParam(required = false) Integer pagesize) throws ApplicationException {
        return listByPropertiesAndKeyword(model, getPageRequest(page,direction,property,pagesize),productProperties,keyword);
    }

    @RequestMapping("sort/{property}/property/{productProperties}/category/{categoryId}/page/{page}")
    public String listByProductPropertiesAndCategoryId(Model model, @PathVariable Long categoryId,@PathVariable Integer page, @PathVariable String direction, @PathVariable String property, @PathVariable String productProperties,@RequestParam(required = false) Integer pagesize) throws ApplicationException {
        return listWithFilter(model, getPageRequest(page,direction,property,pagesize),productProperties,categoryId,null);
    }

}

