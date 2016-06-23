package net.masadora.mall.business.service;

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
public class UserService extends MasadoraUserDetailsService<UserRepo, User> {
    @Autowired
    UserRepo userRepo;

    @Autowired
    private MasadoraProps masadoraProps;

    public UserService(){

    }

    @Override
    public String getPassword(final User user) {
        return user.getPassword();
    }


    /**
     * 检测cookievalue合法性
     * @param cookieValue
     * @return User
     * @throws Exception
     */
    public User getUserByCookieValue(String cookieValue) throws Exception{
        if(cookieValue.contains(":")){
            String[] values = cookieValue.split(":");
            if(CookieUtil.webKeyOKAY(values[1])){
                User user = userRepo.get(Long.valueOf(DESUtil.decrypt(values[0])));
                if(user.getPassword().equals(DESUtil.decrypt(values[2])))
                    return user;
            }
        }
        return null;
    }

}
