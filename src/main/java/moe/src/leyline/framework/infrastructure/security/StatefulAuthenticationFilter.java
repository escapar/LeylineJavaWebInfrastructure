package moe.src.leyline.framework.infrastructure.security;

import moe.src.leyline.business.infrastructure.common.AuthUtil;
import moe.src.leyline.framework.service.LeylineTransactionalService;

import org.jodah.typetools.TypeResolver;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 有状态的用户验证类,不包含具体逻辑,需要业务层implement getAuthentication
 */
public abstract class StatefulAuthenticationFilter<T extends LeylineTransactionalService> extends OncePerRequestFilter {
    private T userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        ServletContext servletContext = request.getServletContext();
        Class<T> userServiceClass = (Class<T>)TypeResolver.resolveRawArguments(StatefulAuthenticationFilter.class, getClass())[0];
        WebApplicationContext webApplicationContext = WebApplicationContextUtils.getWebApplicationContext(servletContext);
        userService = webApplicationContext.getBean(userServiceClass);
        handleAuth(request);
        filterChain.doFilter(request, response);
    }

    public abstract Authentication getAuthentication(HttpServletRequest request);

    public void handleAuth(HttpServletRequest request){
        Authentication authentication = getAuthentication(request);
        if(authentication == null){
            authentication = new UsernamePasswordAuthenticationToken("anonymous", "", AuthUtil.getRole(AuthUtil.ANONYMOUS));
        }
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }


    public T getUserService(){
        return userService;
    }
}
