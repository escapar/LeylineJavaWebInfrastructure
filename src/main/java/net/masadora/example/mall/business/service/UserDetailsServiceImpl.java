package net.masadora.example.mall.business.service;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import javaslang.collection.Stream;
import moe.src.leyline.framework.service.LeylineUserDetailsService;
import net.masadora.example.mall.business.domain.user.User;
import net.masadora.example.mall.business.domain.user.UserRepo;
import net.masadora.example.mall.business.infrastructure.configurations.MasadoraConstants;

/**
 * Created by POJO on 6/9/16.
 */
@Service
public class UserDetailsServiceImpl extends LeylineUserDetailsService<UserRepo,User> {
    @Override
    public String getPassword(final User user) {
        return user.getPassword();
    }

    @Override
    public Collection<? extends GrantedAuthority> getRole(User user) {
        Collection<? extends GrantedAuthority> authorities = null;
        switch (user.getRole()){
            case MasadoraConstants.ROLE_USER : authorities = Stream.of("ROLE_ADMIN","ROLE_USER")
                .map(SimpleGrantedAuthority::new)
                .toJavaList();break;
            case MasadoraConstants.ROLE_ADMIN :authorities = Stream.of("ROLE_USER")
                .map(SimpleGrantedAuthority::new)
                .toJavaList();
        }

        return authorities;
    }
}
