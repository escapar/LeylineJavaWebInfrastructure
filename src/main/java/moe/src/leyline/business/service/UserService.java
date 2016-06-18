package moe.src.leyline.business.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import javaslang.collection.Stream;
import moe.src.leyline.business.domain.user.User;
import moe.src.leyline.business.domain.user.UserRepo;
import moe.src.leyline.framework.service.LeylineUserDetailsService;

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

    @Override
    public Collection<? extends GrantedAuthority> getRole(User user) {
        Collection<? extends GrantedAuthority> authorities = null;
        switch (user.getRole()) {
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


    public org.springframework.security.core.userdetails.User getUserExample(Long id) throws Exception{
        User user = userRepo.get(id);
        if(true || user.getPassword().equals("wtfCookieValue")){
            return new org.springframework.security.core.userdetails.User(user.getName(),user.getPassword(),getRole(user));
        }
        return null;
    }
}
