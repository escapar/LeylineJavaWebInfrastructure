package net.masadora.mall.business.infrastructure.appconfig;

import moe.src.leyline.framework.infrastructure.common.exceptions.LeylineException;
import net.masadora.mall.business.infrastructure.common.CookieUtil;
import net.masadora.mall.business.infrastructure.common.DESUtil;
import net.masadora.mall.business.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by POJO on 6/15/16.
 */
@Component
public class StatelessAuthenticationFilter extends GenericFilterBean {

    public UserService userService;

    public StatelessAuthenticationFilter(){

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
            throws AccessDeniedException,IOException,ServletException {
        ServletContext servletContext = request.getServletContext();
        WebApplicationContext webApplicationContext = WebApplicationContextUtils.getWebApplicationContext(servletContext);
        userService = webApplicationContext.getBean(UserService.class);
        try {
            HttpServletRequest httpRequest = (HttpServletRequest) request;
            Authentication authentication = getAuthentication(httpRequest);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }catch (Exception e){
            e.printStackTrace();
            throw new AccessDeniedException("Illegal");
        }
        filterChain.doFilter(request, response);
    }

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
        throw new ServletException("authError");
    }
}
