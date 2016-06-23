package moe.src.leyline.business.infrastructure.common;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * Created by POJO on 6/8/16.
 */

@Configuration
@Data
@ConfigurationProperties(prefix = "app")
@PropertySource("cookie.properties")
public class CookieProps {
    public String cookieName;
    public String saveCookieName;
    public int saveCookieAge;
    public String cookieWebkey;
    public int cookieAge;
    public String domain;
    public String logoutCookieName;
}
