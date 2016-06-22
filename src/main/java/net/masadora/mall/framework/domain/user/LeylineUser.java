package net.masadora.mall.framework.domain.user;

import net.masadora.mall.framework.domain.LeylineDO;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Created by POJO on 6/8/16.
 */
public interface LeylineUser extends LeylineDO, UserDetails, CredentialsContainer,Authentication {
    public String getPassword();

    public String getName();

    public Object getRole();
}
