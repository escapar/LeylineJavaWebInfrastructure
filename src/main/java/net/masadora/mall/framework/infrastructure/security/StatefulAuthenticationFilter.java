package net.masadora.mall.framework.infrastructure.security;

import net.masadora.mall.framework.service.LeylineUserDetailsService;
import org.jodah.typetools.TypeResolver;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created by POJO on 6/16/16.
 */
public abstract class StatefulAuthenticationFilter<T extends LeylineUserDetailsService> extends GenericFilterBean {
    private T userService;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
            throws AccessDeniedException, IOException, ServletException {
        ServletContext servletContext = request.getServletContext();
        Class<T> userServiceClass = (Class<T>)TypeResolver.resolveRawArguments(StatefulAuthenticationFilter.class, getClass())[0];
        WebApplicationContext webApplicationContext = WebApplicationContextUtils.getWebApplicationContext(servletContext);
        userService = webApplicationContext.getBean(userServiceClass);
        handleAuth((HttpServletRequest)request);
        filterChain.doFilter(request, response);
    }

    public abstract Authentication getAuthentication(HttpServletRequest request) throws Exception;

    public void handleAuth(HttpServletRequest request){
        try {
            HttpServletRequest httpRequest = request;
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if(authentication == null){
                SecurityContextHolder.getContext().setAuthentication(getAuthentication(httpRequest));
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new AccessDeniedException("Illegal");
        }
    }

    public T getUserService(){
        return userService;
    }
}
