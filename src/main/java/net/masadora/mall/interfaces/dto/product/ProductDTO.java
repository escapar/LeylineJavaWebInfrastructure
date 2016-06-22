package net.masadora.mall.interfaces.dto.product;

import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import net.masadora.mall.framework.interfaces.dto.AppDTO;
import net.masadora.mall.framework.interfaces.view.AppView;
import net.masadora.mall.interfaces.dto.category.CategoryDTO;
import net.masadora.mall.interfaces.dto.property.PropertyDetailDTO;

import java.util.List;
import java.util.Set;

/**
 * Created by POJO on 6/3/16.
 */
@EqualsAndHashCode
@ToString
@Data
public class ProductDTO implements AppDTO {

    @JsonView(AppView.LIST.class)
    private Long id;
    @JsonView(AppView.LIST.class)
    private Long createdAt;
    @JsonView(AppView.LIST.class)
    private Long deletedAt;
    @JsonView(AppView.LIST.class)
    private String description;
    @JsonView(AppView.LIST.class)
    private Long modifiedAt;
    @JsonView(AppView.LIST.class)
    private String name;
    @JsonView(AppView.LIST.class)
    private float price;
    @JsonView(AppView.LIST.class)
    private boolean reservable;
    @JsonView(AppView.LIST.class)
    private int soldCount;
    @JsonView(AppView.LIST.class)
    private int stock;
    @JsonView(AppView.LIST.class)
    private int weight;
    @JsonView(AppView.LIST.class)
    private int vendorId;
    @JsonView(AppView.LIST.class)
    private String vendorName;

    @JsonView(AppView.LIST.class)
    @JsonDeserialize(contentAs=ProductImageDTO.class)
    private Set<ProductImageDTO> images;

    @JsonView(AppView.LIST.class)
    @JsonDeserialize(contentAs=CategoryDTO.class)
    private List<CategoryDTO> categories;

    @JsonView(AppView.LIST.class)
    @JsonDeserialize(contentAs=PropertyDetailDTO.class)
    private List<PropertyDetailDTO> properties;

}
