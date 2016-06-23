package net.masadora.mall.interfaces.rest;

import net.masadora.mall.business.domain.product.Product;
import net.masadora.mall.business.service.ProductService;
import net.masadora.mall.business.service.PropertyService;
import net.masadora.mall.framework.infrastructure.common.exceptions.PersistenceException;
import net.masadora.mall.framework.interfaces.rest.RestCRUD;
import net.masadora.mall.interfaces.dto.product.ProductDTO;
import net.masadora.mall.interfaces.dto.product.ProductDTOAssembler;
import net.masadora.mall.interfaces.dto.product.ProductUpdateDTO;
import org.assertj.core.api.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by POJO on 6/4/16.
 */
@RestController
@RequestMapping("api/product")
public class ProductAPI extends RestCRUD<ProductService, Product, ProductDTO> {

    @Autowired
    ProductService productService;
    @Autowired
    PropertyService propertyService;

    public ProductAPI(){
        setDTOAssembler(new ProductDTOAssembler());

    }

    /**
     * 更新一整组商品
     * @param dto
     * @throws IOException
     * @throws PersistenceException
     */
    @RequestMapping(value = "/admin/batch", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    @SuppressWarnings(value = "unchecked")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void update(@RequestBody ProductUpdateDTO dto) throws IOException, PersistenceException {
        productService.saveBatchAdmin(
                dtoAssembler.buildDO(dto.getRoot()),
                dtoAssembler.buildDOList(dto.getOthers()));
    }

    @Override
    public void assertUpdate(Product p){
        assertThat(p.getPrice()).isLessThan(450);
    }

}
