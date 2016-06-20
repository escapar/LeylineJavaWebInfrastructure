package net.masadora.mall.framework.infrastructure.security;

import net.masadora.mall.framework.service.LeylineUserDetailsService;
import org.jodah.typetools.TypeResolver;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpRequestResponseHolder;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

/**
 * Created by POJO on 6/16/16.
 */
public abstract class StatefulAuthenticationFilter<T extends LeylineUserDetailsService> extends OncePerRequestFilter {
    private T userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        ServletContext servletContext = request.getServletContext();
        Class<T> userServiceClass = (Class<T>)TypeResolver.resolveRawArguments(StatefulAuthenticationFilter.class, getClass())[0];
        WebApplicationContext webApplicationContext = WebApplicationContextUtils.getWebApplicationContext(servletContext);
        userService = webApplicationContext.getBean(userServiceClass);
        handleAuth((HttpServletRequest)request);
        filterChain.doFilter(request, response);
    }

    public abstract Authentication getAuthentication(HttpServletRequest request);

    public void handleAuth(HttpServletRequest request){
            HttpServletRequest httpRequest = request;
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if(authentication == null){
                authentication = getAuthentication(httpRequest);
                if(authentication == null){
                    authentication = new UsernamePasswordAuthenticationToken("anonymous", "", Arrays.asList(new SimpleGrantedAuthority("ROLE_ANONYMOUS")));
                }
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }

    }


    public T getUserService(){
        return userService;
    }
}
