package net.masadora.mall.interfaces.dto.product;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import net.masadora.mall.framework.interfaces.dto.AppDTO;
import net.masadora.mall.framework.interfaces.view.AppView;

import java.util.List;

/**
 * Created by POJO on 6/21/16.
 */
@Data
public class ProductUpdateDTO implements AppDTO {
    @JsonView(AppView.LIST.class)
    ProductDTO root;
    @JsonView(AppView.LIST.class)
    List<ProductDTO> others;
}
