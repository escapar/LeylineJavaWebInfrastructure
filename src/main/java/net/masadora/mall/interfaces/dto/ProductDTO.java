package net.masadora.mall.interfaces.dto;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import lombok.EqualsAndHashCode;
import moe.src.leyline.framework.interfaces.dto.LeylineDTO;
import moe.src.leyline.framework.interfaces.view.LeylineView;

/**
 * Created by POJO on 6/3/16.
 */
@EqualsAndHashCode
@Data
public class ProductDTO implements LeylineDTO {
    @JsonView(LeylineView.LIST.class)
    private int id;

    @JsonView(LeylineView.LIST.class)
    private String name;

    @JsonView(LeylineView.DETAIL.class)
    private double price;
}
