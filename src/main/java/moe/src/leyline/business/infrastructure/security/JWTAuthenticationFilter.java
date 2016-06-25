package moe.src.leyline.business.infrastructure.security;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import moe.src.leyline.business.domain.user.User;
import moe.src.leyline.business.service.UserService;
import moe.src.leyline.framework.infrastructure.security.StatelessAuthenticationFilter;

/**
 * Created by POJO on 6/15/16.
 */
@Component("JWTAuthenticationFilter")
public class JWTAuthenticationFilter extends StatelessAuthenticationFilter {
    public UserService userService;

    @Autowired
    public JWTAuthenticationFilter(UserService userService) {
        this.userService = userService;
    }

    @Override
    public Authentication getAuthentication(HttpServletRequest request) throws ServletException {
        User user = null;
        try {
            Claims c = JWTTokenUtils.parse(request);
            user = userService.getByClaims(c);
            MDC.put("name", c.get("name"));
            return user;
        } catch (final Exception e) {
            e.printStackTrace();
            return user;
        }
    }
}