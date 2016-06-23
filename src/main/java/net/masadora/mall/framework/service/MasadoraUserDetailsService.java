package net.masadora.mall.framework.service;

import javaslang.collection.Stream;
import net.masadora.mall.framework.domain.user.AppUser;
import net.masadora.mall.framework.domain.user.AppUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;

/**
 *  实现Spring Security需要的UserDetailsService
 */
@Service
public abstract class MasadoraUserDetailsService<T extends AppUserRepo, D extends AppUser> implements UserDetailsService {
    @Autowired
    private T userRepo;

    @SuppressWarnings(value = "unchecked")
    @Override
    public User loadUserByUsername(String username) throws
            UsernameNotFoundException {
        D user = (D) userRepo.findByNameEquals(username);
        if (user == null) {
            throw new UsernameNotFoundException("Username " + username + " not found");
        }
        return new User(username, getPassword(user), getRole(user));
    }

    public String getPassword(D user) {
        return user.getPassword();
    }

    public Collection<? extends GrantedAuthority> getRole(D user) {
        return Stream.of(new SimpleGrantedAuthority("ROLE_USER")).toJavaList();
    }

    /**
     * 当前登录用户
     */
    public User getCurrentUser() {
        Authentication auth =
                SecurityContextHolder.getContext().getAuthentication();
        return  auth.getPrincipal() instanceof User ? (User)auth.getPrincipal() : null;
    }

    /**
     * 传入用户是否当前登录用户
     */
    public Boolean checkOwner(AppUser user) {
        return getCurrentUser().getUsername().equals(user.getName());
    }

}
