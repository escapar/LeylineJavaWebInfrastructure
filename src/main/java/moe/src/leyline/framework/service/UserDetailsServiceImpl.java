package moe.src.leyline.framework.service;

import javaslang.collection.List;
import javaslang.collection.Stream;
import moe.src.leyline.business.domain.user.UserRepo;
import moe.src.leyline.framework.infrastructure.common.AppConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collection;
import javaslang.control.Match;

/**
 * Created by POJO on 6/8/16.
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepo userRepo;
    @Override
    public UserDetails loadUserByUsername(String username) throws
            UsernameNotFoundException {
        moe.src.leyline.business.domain.user.User user = userRepo.findByNameEquals(username);
        if (user == null) {
            throw new UsernameNotFoundException("Username " + username + " not found");
        }
        return new User(username, user.getPassword(), getRoleById(user.getRole()));
    }

    private Collection<? extends GrantedAuthority> getRoleById(int role) {
        Collection<? extends GrantedAuthority> authorities = null;
        switch (role){
            case AppConstants.ROLE_USER : authorities = Stream.of("ROLE_ADMIN","ROLE_USER")
                    .map(SimpleGrantedAuthority::new)
                    .toJavaList();break;
            case AppConstants.ROLE_ADMIN :authorities = Stream.of("ROLE_USER")
                    .map(SimpleGrantedAuthority::new)
                    .toJavaList();
        }

        return authorities;
    }
}
