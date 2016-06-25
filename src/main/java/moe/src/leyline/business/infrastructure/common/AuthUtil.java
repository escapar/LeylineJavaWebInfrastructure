package moe.src.leyline.business.infrastructure.common;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javaslang.collection.Stream;

/**
 * Created by POJO on 6/21/16.
 */
public class AuthUtil {
    public static final int COMMON_USER = 0;
    public static final int ADMIN = 1;
    public static final int DOMESTIC = 2;
    public static final int FOREIGN = 3;
    public static final int CUSTOMER_SERVICE = 4;
    public static final int DOMESTIC_PRACTISE = 5;
    public static final int OTHER_USER = 6;
    public static final int CHIEF_EDITOR = 7;
    public static final int COMMON_EDITOR = 8;
    public static final int ANONYMOUS = -1;

    public static Collection<? extends GrantedAuthority> getRole(Long id) {
        Collection<? extends GrantedAuthority> authorities = null;
        switch (id.intValue()) {
        case ADMIN:
            authorities = Stream.of("COMMON_USER", "ADMIN")
                    .map(SimpleGrantedAuthority::new)
                    .toJavaList();
            break;
        case COMMON_USER:
            authorities = Stream.of("ROLE_USER")
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

    public static Collection<? extends GrantedAuthority> getRole(int id) {
        return getRole((long) id);
    }
}
