package moe.src.leyline.business.service;

import io.jsonwebtoken.Claims;
import javaslang.collection.Stream;
import moe.src.leyline.business.domain.user.DomainUser;
import moe.src.leyline.business.domain.user.DomainUserRepo;
import moe.src.leyline.business.domain.website.Website;
import moe.src.leyline.business.infrastructure.common.ROLE_CONSTS;
import moe.src.leyline.framework.infrastructure.common.exceptions.PersistenceException;
import moe.src.leyline.framework.service.LeylineUserDetailsService;
import moe.src.leyline.interfaces.dto.UserLoginDTO;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by POJO on 6/9/16.
 */
@Service
public class DomainUserService extends LeylineUserDetailsService<DomainUserRepo, DomainUser> {
    @Autowired
    DomainUserRepo domainUserRepo;

    @Autowired
    WebsiteService websiteService;

    public DomainUserService(){

    }

    @Override
    public String getPassword(final DomainUser domainUser) {
        return domainUser.getPassword();
    }

    @Override
    public Collection<? extends GrantedAuthority> getRole(DomainUser domainUser) {
        Collection<? extends GrantedAuthority> authorities = null;
        switch (domainUser.getRole()) {
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


    public DomainUser getByClaims(Claims c) throws Exception{
        return c == null ? null : domainUserRepo.findByNameEquals((String)c.get("name"));
    }

    @Transactional(propagation = Propagation.SUPPORTS,readOnly=true)
    public DomainUser checkAndGet(String username,String password){
        return domainUserRepo.checkAndGet(username,password);
    }

    public DomainUser reg(UserLoginDTO reg) throws PersistenceException{
        DomainUser domainUser = new DomainUser();
        domainUser.setName(reg.username);
        domainUser.setUnHashedPassword(reg.password);
        domainUser.setRole(ROLE_CONSTS.ROLE_UNCHECKED_USER);
        domainUser = save(domainUser);
        Website website = new Website();
        website.setCreatedAt(new DateTime().getMillis());
        website.setDomain(reg.domain);
        website.setVerifyKey(random(7));
        website.setUser(domainUser);
        websiteService.save(website);
        return domainUser;
    }

    public DomainUser verify (DomainUser user) throws PersistenceException{
        user.setRole(ROLE_CONSTS.ROLE_USER);
        return save(user);
    }

    private static String random(int length) {
        StringBuilder builder = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            builder.append((char) (ThreadLocalRandom.current().nextInt(33, 128)));
        }
        return builder.toString();
    }


}
