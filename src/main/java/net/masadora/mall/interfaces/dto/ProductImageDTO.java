package net.masadora.mall.interfaces.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.masadora.mall.framework.interfaces.dto.LeylineDTO;

/**
 * Created by POJO on 6/20/16.
 */
@Data @NoArgsConstructor @AllArgsConstructor
public class ProductImageDTO implements LeylineDTO {
    private Long id;

    private String description;

    private String url;
}
