package net.masadora.mall.framework.domain.user;

import net.masadora.mall.framework.domain.LeylineDO;

/**
 * Created by POJO on 6/8/16.
 */
public interface LeylineUser extends LeylineDO {
    public String getPassword();

    public String getName();

    public Object getRole();
}
