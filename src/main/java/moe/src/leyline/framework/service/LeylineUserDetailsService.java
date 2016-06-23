package moe.src.leyline.framework.service;

import javaslang.collection.Stream;
import moe.src.leyline.framework.domain.user.LeylineUser;
import moe.src.leyline.framework.domain.user.LeylineUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;

/**
 *  实现Spring Security需要的UserDetailsService
 */
@Service
public abstract class LeylineUserDetailsService<T extends LeylineUserRepo, D extends LeylineUser> extends LeylineTransactionalService<T,D> implements UserDetailsService {
    @Autowired
    private T userRepo;

    @SuppressWarnings(value = "unchecked")
    @Override
    public LeylineUser loadUserByUsername(String username) throws
            UsernameNotFoundException {
        D user = (D) userRepo.findByNameEquals(username);
        if (user == null) {
            throw new UsernameNotFoundException("Username " + username + " not found");
        }
        return user;
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
    public LeylineUser getCurrentUser() {
        Authentication auth =
                SecurityContextHolder.getContext().getAuthentication();
        return  auth.getPrincipal() instanceof LeylineUser ? (LeylineUser)auth.getPrincipal() : null;
    }

    /**
     * 传入用户是否当前登录用户
     */
    public Boolean checkOwner(LeylineUser user) {
        return getCurrentUser().getUsername().equals(user.getName());
    }

}
