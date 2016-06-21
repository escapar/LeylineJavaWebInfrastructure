package net.masadora.mall.business.infrastructure.security;

import net.masadora.mall.business.domain.user.User;
import net.masadora.mall.framework.infrastructure.security.StatefulAuthenticationFilter;
import net.masadora.mall.framework.infrastructure.security.UserAuthentication;
import net.masadora.mall.business.infrastructure.common.CookieUtil;
import net.masadora.mall.business.infrastructure.common.DESUtil;
import net.masadora.mall.business.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by POJO on 6/15/16.
 */
@Component
public class CookieAuthenticationFilter extends StatefulAuthenticationFilter<UserService> {

    @Override
    public Authentication getAuthentication(HttpServletRequest request){
        User user;
        if(request.getSession().getAttribute("user")!=null) {
            user = (User)request.getSession().getAttribute("user");
            return new UserAuthentication(user);
        }else {
            Cookie cookie = CookieUtil.getCookieByName(request);
            if (cookie != null) {
                try {
                    String cookieValue = DESUtil.decrypt(cookie.getValue());
                    user = getUserService().getUserByCookieValue(cookieValue);
                    if (user != null) {
                        request.getSession().setAttribute("user", user);
                        return new UserAuthentication(user);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }
        }
        return null;
    }



}
