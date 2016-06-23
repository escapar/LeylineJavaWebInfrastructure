package net.masadora.mall.business.infrastructure.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.access.ExceptionTranslationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

/**
 * Created by bytenoob on 6/9/16.
 */
@Configuration
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    public WebSecurityConfiguration() {
        super(true);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .anyRequest().permitAll()
                .and().addFilterBefore(new CookieAuthenticationFilter(),
                BasicAuthenticationFilter.class).addFilter(new ExceptionTranslationFilter(new BasicAuthenticationEntryPoint())).csrf().disable();

    }


}
