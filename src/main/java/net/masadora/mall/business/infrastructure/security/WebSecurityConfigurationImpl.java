package net.masadora.mall.business.infrastructure.security;

import net.masadora.mall.business.service.UserService;
import net.masadora.mall.framework.infrastructure.security.LeylineWebSecurityConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

/**
 * Created by bytenoob on 6/9/16.
 */
@Configuration
public class WebSecurityConfigurationImpl extends LeylineWebSecurityConfiguration<UserService> {

    public WebSecurityConfigurationImpl() {
        super(true);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .anyRequest().permitAll()
                .and().addFilterBefore(new CookieAuthenticationFilter(),
                BasicAuthenticationFilter.class).csrf().disable();

    }


}
