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

    private Long id;
    private Long createdAt;
    private Long deletedAt;
    private String description;
    private Long modifiedAt;
    private String name;
    private float price;
    private boolean reservable;
    private int soldCount;
    private int stock;
    private int weight;
    private int vendorId;
    private String vendorName;
    @JsonDeserialize(contentAs=ProductImageDTO.class)
    private Set<ProductImageDTO> images;

}
