package moe.src.leyline.interfaces.dto.website;

import lombok.Data;
import moe.src.leyline.business.domain.website.Website;
import moe.src.leyline.framework.interfaces.dto.LeylineDTO;

/**
 * Created by bytenoob on 6/25/16.
 */
@Data
public class WebsiteRelationDTO implements LeylineDTO {
    private Long id;

    private String description;

    private String title;

    private WebsiteDTO master;

    private WebsiteDTO servant;

    private boolean approved;

    private Long createdAt;
}
