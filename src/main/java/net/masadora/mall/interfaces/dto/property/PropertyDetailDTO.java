package net.masadora.mall.interfaces.dto.property;

import lombok.Data;
import net.masadora.mall.framework.interfaces.dto.AppDTO;

/**
 * Created by POJO on 6/21/16.
 */
@Data
public class PropertyDetailDTO implements AppDTO {
    private Long id;

    private Long propertyId;

    private String propertyName;

    private String value;

    @Override
    public String toString() {
        return propertyName + " : " + value;
    }
}
