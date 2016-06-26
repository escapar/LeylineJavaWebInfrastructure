package moe.src.leyline.interfaces.dto.user;

import com.fasterxml.jackson.annotation.JsonView;

import lombok.Data;
import moe.src.leyline.business.domain.commons.lang.Lang;
import moe.src.leyline.business.domain.commons.location.Location;
import moe.src.leyline.business.domain.commons.platform.Platform;
import moe.src.leyline.framework.interfaces.dto.LeylineDTO;
import moe.src.leyline.framework.interfaces.view.LeylineView;

/**
 * Created by bytenoob on 6/18/16.
 */
@Data
public class UserDTO implements LeylineDTO {
    @JsonView(LeylineView.DETAIL.class)
    private long id;

    @JsonView(LeylineView.DETAIL.class)
    private String name;

    private String password;

    private String domain;

    private int role;

    @JsonView(LeylineView.DETAIL.class)
    private Platform platform;

    @JsonView(LeylineView.DETAIL.class)
    private Lang lang;

    @JsonView(LeylineView.DETAIL.class)
    private Location location;
}
