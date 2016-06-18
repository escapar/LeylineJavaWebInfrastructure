package moe.src.leyline.business.infrastructure.security;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

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
        try {
            User user = userService.getUserExample((long)1);
            if (user != null) {
                return new UserAuthentication(user);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}