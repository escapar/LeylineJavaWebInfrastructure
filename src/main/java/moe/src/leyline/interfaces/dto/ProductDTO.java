package moe.src.leyline.interfaces.dto;

import lombok.Data;
import moe.src.leyline.infrastructure.tagging.DTO;

/**
 * Created by POJO on 5/30/16.
 */
@Data public class ProductDTO extends DTO {

    private static final long serialVersionUID = 5651646679052571189L;
    private Integer id;
    private String  name;
    private Double  price;


}
