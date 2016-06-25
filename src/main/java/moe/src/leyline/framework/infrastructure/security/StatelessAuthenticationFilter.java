package moe.src.leyline.framework.infrastructure.security;

import java.io.IOException;
import java.util.Arrays;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.DelegatingFilterProxy;

/**
 * Created by POJO on 6/15/16.
 */
public abstract class StatelessAuthenticationFilter extends DelegatingFilterProxy implements Filter {

    @Override
    public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain filterChain) throws AccessDeniedException, ServletException, IOException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        Authentication authentication = getAuthentication(httpRequest);
        if (authentication == null) {
            authentication = new UsernamePasswordAuthenticationToken("anonymous", "", Arrays.asList(new SimpleGrantedAuthority("ROLE_ANONYMOUS")));
        }
        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request, response);
    }

    public abstract Authentication getAuthentication(HttpServletRequest request) throws ServletException;
}
