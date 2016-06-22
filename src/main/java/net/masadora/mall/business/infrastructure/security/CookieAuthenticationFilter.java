package net.masadora.mall.business.infrastructure.security;

import net.masadora.mall.business.domain.user.User;
import net.masadora.mall.business.infrastructure.common.CookieUtil;
import net.masadora.mall.business.infrastructure.common.DESUtil;
import net.masadora.mall.business.service.UserService;
import net.masadora.mall.framework.infrastructure.security.StatefulAuthenticationFilter;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by POJO on 6/15/16.
 */
@Component
public class CookieAuthenticationFilter extends StatefulAuthenticationFilter<UserService> {

    @Override
    public Authentication getAuthentication(HttpServletRequest request){
        if(request.getSession().getAttribute("user")!=null) {
            try {
                return (User) request.getSession().getAttribute("user");
            }catch (Exception e){
                e.printStackTrace();
                return reAuth(request);
            }
        }else {
            return reAuth(request);
        }
    }

    public User reAuth(HttpServletRequest request){
        Cookie cookie = CookieUtil.getCookieByName(request);
        if (cookie != null) {
            try {
                String cookieValue = DESUtil.decrypt(cookie.getValue());
                User user = getUserService().getUserByCookieValue(cookieValue);
                if (user != null) {
                    request.getSession().setAttribute("user", user);
                    return user;
                }
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }

}
