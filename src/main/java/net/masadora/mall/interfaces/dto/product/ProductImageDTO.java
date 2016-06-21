package net.masadora.mall.interfaces.dto.product;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.masadora.mall.framework.interfaces.dto.LeylineDTO;
import net.masadora.mall.framework.interfaces.view.LeylineView;

/**
 * Created by POJO on 6/20/16.
 */
@Data @NoArgsConstructor @AllArgsConstructor
public class ProductImageDTO implements LeylineDTO {
    @JsonView(LeylineView.LIST.class)
    private Long id;
    @JsonView(LeylineView.LIST.class)
    private String description;
    @JsonView(LeylineView.LIST.class)
    private String url;
}
