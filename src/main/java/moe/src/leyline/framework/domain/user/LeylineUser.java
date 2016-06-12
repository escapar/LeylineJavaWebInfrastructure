package moe.src.leyline.framework.domain.user;

import moe.src.leyline.framework.domain.LeylineDO;

/**
 * Created by POJO on 6/8/16.
 */
public interface LeylineUser extends LeylineDO {
    public String getPassword();

    public String getName();

    public int getRole();
}
