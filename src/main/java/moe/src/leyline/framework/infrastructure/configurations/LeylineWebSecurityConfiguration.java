package moe.src.leyline.framework.infrastructure.configurations;

import moe.src.leyline.framework.service.LeylineUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Created by POJO on 6/5/16.
 */
@Configuration
public abstract class LeylineWebSecurityConfiguration<S extends LeylineUserDetailsService> extends WebSecurityConfigurerAdapter {

    @Autowired
    private S userDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .anyRequest().permitAll();
    }

}
