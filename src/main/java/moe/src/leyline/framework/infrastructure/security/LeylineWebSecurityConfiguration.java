package moe.src.leyline.framework.infrastructure.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import moe.src.leyline.framework.service.LeylineUserDetailsService;

/**
 * Created by POJO on 6/5/16.
 */
@Configuration
public abstract class LeylineWebSecurityConfiguration<S extends LeylineUserDetailsService> extends WebSecurityConfigurerAdapter {

    @Autowired
    private S userDetailsService;

    public LeylineWebSecurityConfiguration(Boolean a){
        super(a);
    }
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

    public S getUserDetailsService(){
        return userDetailsService;
    }
}