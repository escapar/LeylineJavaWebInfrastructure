package moe.src.leyline.business.service;

import java.util.Collection;

import org.apache.commons.lang.RandomStringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import io.jsonwebtoken.Claims;
import javaslang.collection.Stream;
import moe.src.leyline.business.domain.user.User;
import moe.src.leyline.business.domain.user.UserRepo;
import moe.src.leyline.business.domain.website.Website;
import moe.src.leyline.business.infrastructure.common.ROLE_CONSTS;
import moe.src.leyline.framework.infrastructure.common.exceptions.PersistenceException;
import moe.src.leyline.framework.service.LeylineUserDetailsService;
import moe.src.leyline.interfaces.dto.UserLoginDTO;

/**
 * Created by POJO on 6/9/16.
 */
@Service
public class UserService extends LeylineUserDetailsService<UserRepo, User> {
    @Autowired
    UserRepo userRepo;

    @Autowired
    WebsiteService websiteService;

    public UserService() {

    }

    @Override
    public Collection<? extends GrantedAuthority> getRole(User user) {
        Collection<? extends GrantedAuthority> authorities = null;
        switch (user.getRole()) {
        case ROLE_CONSTS.ROLE_ADMIN:
            authorities = Stream.of("ROLE_ADMIN", "ROLE_USER")
                    .map(SimpleGrantedAuthority::new)
                    .toJavaList();
            break;
        case ROLE_CONSTS.ROLE_USER:
            authorities = Stream.of("ROLE_USER")
                    .map(SimpleGrantedAuthority::new)
                    .toJavaList();
            break;
        case ROLE_CONSTS.ROLE_UNCHECKED_USER:
            authorities = Stream.of("ROLE_UNCHECKED_USER")
                    .map(SimpleGrantedAuthority::new)
                    .toJavaList();
            break;
        default:
            authorities = Stream.of("ROLE_ANONYMOUS")
                    .map(SimpleGrantedAuthority::new)
                    .toJavaList();
        }

        return authorities;
    }

    public User getByClaims(Claims c) throws Exception {
        return c == null ? null : userRepo.findByNameEquals((String) c.get("name"));
    }

    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public User checkAndGet(String username, String password) {
        return userRepo.checkAndGet(username, password);
    }

    public User reg(UserLoginDTO reg) throws PersistenceException {
        User user = save(
                new User()
                        .setName(reg.username)
                        .setUnHashedPassword(reg.password)
                        .setRole(ROLE_CONSTS.ROLE_UNCHECKED_USER));

        websiteService.save(
                new Website()
                        .setCreatedAt(new DateTime().getMillis())
                        .setDomain(reg.domain)
                        .setVerifyKey(RandomStringUtils.random(7))
                        .setUser(user));

        return user;
    }

    public User verify(User user) throws PersistenceException {
        return save(user.setRole(ROLE_CONSTS.ROLE_USER));
    }

}
