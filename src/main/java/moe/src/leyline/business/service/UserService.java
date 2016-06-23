package moe.src.leyline.business.service;

import net.masadora.mall.business.domain.user.User;
import net.masadora.mall.business.domain.user.UserRepo;
import net.masadora.mall.business.infrastructure.common.CookieUtil;
import net.masadora.mall.business.infrastructure.common.DESUtil;
import net.masadora.mall.business.infrastructure.common.MasadoraProps;
import net.masadora.mall.framework.service.MasadoraUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by POJO on 6/9/16.
 */
@Service
public class UserService extends LeylineUserDetailsService<UserRepo, User> {
    @Autowired
    UserRepo userRepo;

    public UserService(){

    }

    @Override
    public String getPassword(final User user) {
        return user.getPassword();
    }



}
