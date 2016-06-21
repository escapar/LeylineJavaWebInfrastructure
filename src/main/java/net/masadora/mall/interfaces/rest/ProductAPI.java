package net.masadora.mall.interfaces.rest;

import net.masadora.mall.framework.infrastructure.common.exceptions.LeylineException;
import net.masadora.mall.framework.infrastructure.common.exceptions.PersistenceException;
import net.masadora.mall.framework.interfaces.dto.assembler.DTOAssembler;
import net.masadora.mall.framework.interfaces.rest.LeylineRestCRUD;
import net.masadora.mall.business.domain.product.Product;
import net.masadora.mall.business.service.ProductService;
import net.masadora.mall.interfaces.dto.ProductDTO;
import net.masadora.mall.interfaces.dto.ProductUpdateDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.User;
import org.springframework.ui.Model;
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

    @RequestMapping(value = "/admin/batch", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    @SuppressWarnings(value = "unchecked")
    public void update(@RequestBody ProductUpdateDTO dto) throws IOException, PersistenceException {
        productService.saveBatchAdmin(
                (Product)DTOAssembler.buildDO(dto.getRoot(),Product.class),
                DTOAssembler.buildDOList(dto.getOthers(),Product.class));
    }

}
