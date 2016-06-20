package net.masadora.mall.business.service;

import javaslang.collection.Stream;
import net.masadora.mall.framework.service.LeylineUserDetailsService;
import net.masadora.mall.business.domain.user.User;
import net.masadora.mall.business.domain.user.UserRepo;
import net.masadora.mall.business.infrastructure.common.CookieUtil;
import net.masadora.mall.business.infrastructure.common.DESUtil;
import net.masadora.mall.business.infrastructure.common.MasadoraProps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Collection;

/**
 * Created by POJO on 6/9/16.
 */
@Service
public class UserService extends LeylineUserDetailsService<UserRepo, User> {
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

    @Override
    public Collection<? extends GrantedAuthority> getRole(User user) {
        Collection<? extends GrantedAuthority> authorities = null;
        switch (user.getRole().getId().intValue()) {
            case 1:
                authorities = Stream.of("ROLE_ADMIN", "ROLE_USER")
                        .map(SimpleGrantedAuthority::new)
                        .toJavaList();
                break;
            case 2:
                authorities = Stream.of("ROLE_USER")
                        .map(SimpleGrantedAuthority::new)
                        .toJavaList();
        }

        return authorities;
    }


    /**
     * 检测cookievalue合法性
     * @param cookieValue
     * @return User
     * @throws Exception
     */
    public org.springframework.security.core.userdetails.User getUserByCookieValue(String cookieValue) throws Exception{
        if(cookieValue.contains(":")){
            String[] values = cookieValue.split(":");
            if(CookieUtil.webKeyOKAY(values[1])){
                User user = userRepo.get(Long.valueOf(DESUtil.decrypt(values[0])));
                if(user.getPassword().equals(DESUtil.decrypt(values[2])))
                    return new org.springframework.security.core.userdetails.User(user.getName(),user.getPassword(),getRole(user));
            }
        }
        return null;
    }
}
