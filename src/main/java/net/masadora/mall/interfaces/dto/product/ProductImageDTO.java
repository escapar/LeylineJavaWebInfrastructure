package net.masadora.mall.interfaces.dto.product;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.masadora.mall.framework.interfaces.dto.AppDTO;
import net.masadora.mall.framework.interfaces.view.AppView;

/**
 * Created by POJO on 6/20/16.
 */
@Data @NoArgsConstructor @AllArgsConstructor
public class ProductImageDTO implements AppDTO {
    @JsonView(AppView.LIST.class)
    private Long id;
    @JsonView(AppView.LIST.class)
    private String description;
    @JsonView(AppView.LIST.class)
    private String url;
}
