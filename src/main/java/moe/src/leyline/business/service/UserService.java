package moe.src.leyline.business.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import io.jsonwebtoken.Claims;
import javaslang.collection.Stream;
import moe.src.leyline.business.domain.user.DomainUser;
import moe.src.leyline.business.domain.user.UserRepo;
import moe.src.leyline.framework.service.LeylineUserDetailsService;

/**
 * Created by POJO on 6/9/16.
 */
@Service
public class UserService extends LeylineUserDetailsService<UserRepo, DomainUser> {
    @Autowired
    UserRepo userRepo;


    public UserService(){

    }

    @Override
    public String getPassword(final DomainUser domainUser) {
        return domainUser.getPassword();
    }

    @Override
    public Collection<? extends GrantedAuthority> getRole(DomainUser domainUser) {
        Collection<? extends GrantedAuthority> authorities = null;
        switch (domainUser.getRole()) {
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


    public DomainUser getByClaims(Claims c) throws Exception{
        return c == null ? null : userRepo.findByNameEquals((String)c.get("name"));
    }

    @Transactional(propagation = Propagation.SUPPORTS,readOnly=true)
    public DomainUser checkAndGet(String username,String password){
        return userRepo.checkAndGet(username,password);
    }


}
