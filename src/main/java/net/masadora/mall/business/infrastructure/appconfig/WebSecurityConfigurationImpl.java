package net.masadora.mall.business.infrastructure.appconfig;

import moe.src.leyline.framework.infrastructure.appconfig.LeylineWebSecurityConfiguration;
import net.masadora.mall.business.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

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
        http  .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .anyRequest().permitAll()
                .and().addFilterBefore(new StatelessAuthenticationFilter(),
                UsernamePasswordAuthenticationFilter.class).formLogin().and().csrf().disable();

    }


}
