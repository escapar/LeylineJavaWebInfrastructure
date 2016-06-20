package net.masadora.mall.interfaces.dto;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import net.masadora.mall.framework.interfaces.dto.LeylineDTO;
import net.masadora.mall.framework.interfaces.view.LeylineView;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

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
}
