package net.masadora.mall.interfaces.dto.vendor;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import net.masadora.mall.framework.interfaces.dto.AppDTO;

/**
 * Created by POJO on 6/20/16.
 */
@EqualsAndHashCode
@ToString
@Data
public class VendorDTO implements AppDTO {
    private Long id;

    private String name;
}
