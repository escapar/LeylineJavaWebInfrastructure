package net.masadora.mall.interfaces.rest;

import net.masadora.mall.business.service.PropertyService;
import net.masadora.mall.framework.infrastructure.common.exceptions.PersistenceException;
import net.masadora.mall.framework.interfaces.dto.assembler.DTOAssembler;
import net.masadora.mall.framework.interfaces.rest.LeylineRestCRUD;
import net.masadora.mall.business.domain.product.Product;
import net.masadora.mall.business.service.ProductService;
import net.masadora.mall.interfaces.dto.product.ProductDTO;
import net.masadora.mall.interfaces.dto.product.ProductDTOAssembler;
import net.masadora.mall.interfaces.dto.product.ProductUpdateDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by POJO on 6/4/16.
 */
@RestController
@RequestMapping(value = "api/product")

public class ProductAPI extends LeylineRestCRUD<ProductService, ProductDTO, Product> {
    @Autowired
    ProductService productService;
    @Autowired
    PropertyService propertyService;

    public ProductAPI(){
        setDTOAssembler(new ProductDTOAssembler());
    }

    @RequestMapping(value = "/admin/batch", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    @SuppressWarnings(value = "unchecked")
    public void update(@RequestBody ProductUpdateDTO dto) throws IOException, PersistenceException {
        productService.saveBatchAdmin(
                dtoAssembler.buildDO(dto.getRoot()),
                dtoAssembler.buildDOList(dto.getOthers()));
    }

    @RequestMapping(value = "/filter/{catId}", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    @SuppressWarnings(value = "unchecked")
    public Map filterTest(@PathVariable Long catId) throws IOException, PersistenceException {
        return propertyService.getPropertyDetailForPropertyFilter(catId);
    }


}
