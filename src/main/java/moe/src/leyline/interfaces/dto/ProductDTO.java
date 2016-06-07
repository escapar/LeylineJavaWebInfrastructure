package moe.src.leyline.interfaces.dto;

import com.fasterxml.jackson.annotation.JsonView;
import moe.src.leyline.framework.interfaces.dto.DTO;
import moe.src.leyline.framework.interfaces.view.VIEW;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by POJO on 6/3/16.
 */
@EqualsAndHashCode
@Data
public class ProductDTO extends DTO {
    @JsonView(VIEW.LIST.class)
    private int id;

    @JsonView(VIEW.LIST.class)
    private String name;

    @JsonView(VIEW.DETAIL.class)
    private double price;
}
