package net.masadora.example.mall.business.infrastructure.security;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import moe.src.leyline.framework.infrastructure.security.StatelessAuthenticationFilter;
import moe.src.leyline.framework.infrastructure.security.UserAuthentication;
import net.masadora.mall.business.infrastructure.common.CookieUtil;
import net.masadora.mall.business.infrastructure.common.DESUtil;
import net.masadora.mall.business.service.UserService;

/**
 * Created by POJO on 6/15/16.
 */
@Component("CookieAuthenticationFilter")
public class CookieAuthenticationFilter extends StatelessAuthenticationFilter {
    public UserService userService;

    @Autowired
    public CookieAuthenticationFilter(UserService userService){
        this.userService = userService;
    }

    @Override
    public Authentication getAuthentication(HttpServletRequest request) throws ServletException{
        Cookie cookie = CookieUtil.getCookieByName(request);
        if(cookie != null) {
            try {
                String cookieValue = DESUtil.decrypt(cookie.getValue());
                User user = userService.getUserByCookieValue(cookieValue);
                if (user != null) {
                    return new UserAuthentication(user);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return null;
    }
}