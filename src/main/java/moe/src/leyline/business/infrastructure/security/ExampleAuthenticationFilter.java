package moe.src.leyline.business.infrastructure.security;

import java.util.Calendar;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import moe.src.leyline.business.service.UserService;
import moe.src.leyline.framework.infrastructure.security.StatelessAuthenticationFilter;
import moe.src.leyline.framework.infrastructure.security.UserAuthentication;

/**
 * Created by POJO on 6/15/16.
 */
@Component("CookieAuthenticationFilter")
public class ExampleAuthenticationFilter extends StatelessAuthenticationFilter {
    public UserService userService;

    @Autowired
    public ExampleAuthenticationFilter(UserService userService){
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
            //request.setAttribute("claims", c);
            return new UserAuthentication(userService.getByClaims(c));
            //MDC.put("name", claims.get("name"));
        }
        catch (final Exception e) {
            throw new ServletException("Invalid token.");
        }

    }
}