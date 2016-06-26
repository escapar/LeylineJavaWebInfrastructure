package moe.src.leyline.interfaces.dto.website;

import lombok.Data;
import moe.src.leyline.framework.interfaces.dto.LeylineDTO;

/**
 * Created by bytenoob on 6/19/16.
 */

@Data public class WebsiteDTO implements LeylineDTO {

    private String id;

    private Long createdAt;

    private String description;

    private String domain;

    private Long modifiedAt;

    private Object screenshot;

    private Object title;

    private String username;

    private String verifyKey;

}
