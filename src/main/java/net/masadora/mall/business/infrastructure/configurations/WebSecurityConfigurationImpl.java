package net.masadora.mall.business.infrastructure.configurations;

import moe.src.leyline.framework.infrastructure.configurations.LeylineWebSecurityConfiguration;
import net.masadora.mall.business.service.UserDetailsServiceImpl;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

/**
 * Created by bytenoob on 6/9/16.
 */
@Configuration
public class WebSecurityConfigurationImpl extends LeylineWebSecurityConfiguration<UserDetailsServiceImpl> {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .anyRequest().permitAll()
                .and().formLogin().and().csrf().disable();
    }
}
