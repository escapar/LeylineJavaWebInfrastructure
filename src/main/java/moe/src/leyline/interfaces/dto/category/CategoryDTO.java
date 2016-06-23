package moe.src.leyline.interfaces.dto.category;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import moe.src.leyline.framework.interfaces.view.AppView;
import net.masadora.mall.framework.interfaces.dto.AppDTO;

/**
 * Created by POJO on 6/21/16.
 */
@Data
public class CategoryDTO implements AppDTO {
    @JsonView(AppView.LIST.class)
    private Long id;

    @JsonView(AppView.LIST.class)
    private String name;

    private CategoryDTO rootCategory;

}
