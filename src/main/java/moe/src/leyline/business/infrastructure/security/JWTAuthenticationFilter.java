package moe.src.leyline.business.infrastructure.security;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import moe.src.leyline.business.domain.user.DomainUser;
import moe.src.leyline.business.service.UserService;
import moe.src.leyline.framework.infrastructure.security.StatelessAuthenticationFilter;
import moe.src.leyline.framework.infrastructure.security.UserAuthentication;

/**
 * Created by POJO on 6/15/16.
 */
@Component("CookieAuthenticationFilter")
public class JWTAuthenticationFilter extends StatelessAuthenticationFilter {
    public UserService userService;

    @Autowired
    public JWTAuthenticationFilter(UserService userService){
        this.userService = userService;
    }

    @Override
    public Authentication getAuthentication(HttpServletRequest request) throws ServletException{
        final String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("JUICE ")) {
            throw new ServletException("Missing or invalid Authorization header.");
        }

        final String token = authHeader.substring(6); // The part after "JUICE "

        try {
            Claims c = JWTTokenUtils.parse(token);
            DomainUser domainUser = userService.getByClaims(c);
            //request.setAttribute("claims", c);
            //MDC.put("name", claims.get("name"));
            return domainUser == null ? null :  new UserAuthentication(new User(domainUser.getName(), domainUser.getPassword(),userService.getRole(domainUser)));
        }
        catch (final Exception e) {
            throw new ServletException("Invalid token.");
        }

    }
}