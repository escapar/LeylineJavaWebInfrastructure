package moe.src.leyline.interfaces.dto;

import lombok.Data;
import moe.src.leyline.framework.interfaces.dto.LeylineDTO;

/**
 * Created by bytenoob on 6/18/16.
 */
@Data
public class UserDTO implements LeylineDTO{
    private long id;

    private String name;

    private String password;

    private int role;
}
