package net.masadora.mall.interfaces.dto.product;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import net.masadora.mall.framework.interfaces.dto.LeylineDTO;
import net.masadora.mall.framework.interfaces.view.LeylineView;

import java.util.List;

/**
 * Created by POJO on 6/21/16.
 */
@Data
public class ProductUpdateDTO implements LeylineDTO{
    @JsonView(LeylineView.LIST.class)
    ProductDTO root;
    @JsonView(LeylineView.LIST.class)
    List<ProductDTO> others;
}
