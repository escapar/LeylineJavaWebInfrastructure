package net.masadora.mall.interfaces.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.sun.tools.javac.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import net.masadora.mall.framework.interfaces.dto.LeylineDTO;
import net.masadora.mall.framework.interfaces.view.LeylineView;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Set;

/**
 * Created by POJO on 6/3/16.
 */
@EqualsAndHashCode
@ToString
@Data
public class ProductDTO implements LeylineDTO {

    @JsonView(LeylineView.LIST.class)
    private Long id;
    @JsonView(LeylineView.LIST.class)
    private Long createdAt;
    @JsonView(LeylineView.LIST.class)
    private Long deletedAt;
    @JsonView(LeylineView.LIST.class)
    private String description;
    @JsonView(LeylineView.LIST.class)
    private Long modifiedAt;
    @JsonView(LeylineView.LIST.class)
    private String name;
    @JsonView(LeylineView.LIST.class)
    private float price;
    @JsonView(LeylineView.LIST.class)
    private boolean reservable;
    @JsonView(LeylineView.LIST.class)
    private int soldCount;
    @JsonView(LeylineView.LIST.class)
    private int stock;
    @JsonView(LeylineView.LIST.class)
    private int weight;
    @JsonView(LeylineView.LIST.class)
    private int vendorId;
    @JsonView(LeylineView.LIST.class)
    private String vendorName;
    @JsonView(LeylineView.LIST.class)
    private boolean searchable;
    @JsonView(LeylineView.LIST.class)
    @JsonDeserialize(contentAs=ProductImageDTO.class)
    private Set<ProductImageDTO> images;


}
