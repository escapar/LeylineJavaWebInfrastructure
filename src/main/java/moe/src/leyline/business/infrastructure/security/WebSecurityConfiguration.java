package moe.src.leyline.business.infrastructure.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import moe.src.leyline.business.service.UserService;
import moe.src.leyline.framework.infrastructure.security.LeylineWebSecurityConfiguration;

/**
 * Created by bytenoob on 6/9/16.
 */
@Configuration
public class WebSecurityConfiguration extends LeylineWebSecurityConfiguration<UserService> {

    public WebSecurityConfiguration() {
        super(true);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .anyRequest().permitAll()
                .and().addFilterBefore(new JWTAuthenticationFilter(getUserDetailsService()),
                UsernamePasswordAuthenticationFilter.class).formLogin().and().csrf().disable();

    }

}
