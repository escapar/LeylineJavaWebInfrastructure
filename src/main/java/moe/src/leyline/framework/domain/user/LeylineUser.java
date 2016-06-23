package moe.src.leyline.framework.domain.user;

import moe.src.leyline.framework.domain.LeylineDO;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * 用户的抽象类,权限框架的一部分,要求实现几个必须的Spring Security接口
 */
public interface LeylineUser extends LeylineDO, UserDetails, CredentialsContainer,Authentication {
    public String getPassword();

    public String getName();

    public Object getRole();
}
