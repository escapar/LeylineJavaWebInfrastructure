package net.masadora.mall.interfaces.dto.category;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import net.masadora.mall.framework.interfaces.dto.LeylineDTO;
import net.masadora.mall.framework.interfaces.view.LeylineView;

/**
 * Created by POJO on 6/21/16.
 */
@Data
public class CategoryDTO implements LeylineDTO{
    @JsonView(LeylineView.LIST.class)
    private Long id;

    @JsonView(LeylineView.LIST.class)
    private String name;

    private CategoryDTO rootCategory;

}
