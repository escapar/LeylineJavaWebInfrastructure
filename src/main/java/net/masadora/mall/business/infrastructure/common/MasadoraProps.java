package net.masadora.mall.business.infrastructure.common;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * Created by POJO on 6/8/16.
 */

@Configuration
@ConfigurationProperties(prefix = "app")
@PropertySource("masadora.properties")
public class MasadoraProps {
    public  int ROLE_USER = 1;
    public  int ROLE_ADMIN = 2;
    public  String cookieName;
    public  String saveCookieName;
    public  int saveCookieAge;
    public  String cookieWebkey;
    public  int cookieAge;
    public  String domain;
    public  String logoutCookieName;

    public int getROLE_USER() {
        return ROLE_USER;
    }

    public void setROLE_USER(int ROLE_USER) {
        this.ROLE_USER = ROLE_USER;
    }

    public int getROLE_ADMIN() {
        return ROLE_ADMIN;
    }

    public void setROLE_ADMIN(int ROLE_ADMIN) {
        this.ROLE_ADMIN = ROLE_ADMIN;
    }

    public String getCookieName() {
        return cookieName;
    }

    public void setCookieName(String cookieName) {
        this.cookieName = cookieName;
    }

    public String getSaveCookieName() {
        return saveCookieName;
    }

    public void setSaveCookieName(String saveCookieName) {
        this.saveCookieName = saveCookieName;
    }

    public int getSaveCookieAge() {
        return saveCookieAge;
    }

    public void setSaveCookieAge(int saveCookieAge) {
        this.saveCookieAge = saveCookieAge;
    }

    public String getCookieWebkey() {
        return cookieWebkey;
    }

    public void setCookieWebkey(String cookieWebkey) {
        this.cookieWebkey = cookieWebkey;
    }

    public int getCookieAge() {
        return cookieAge;
    }

    public void setCookieAge(int cookieAge) {
        this.cookieAge = cookieAge;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getLogoutCookieName() {
        return logoutCookieName;
    }

    public void setLogoutCookieName(String logoutCookieName) {
        this.logoutCookieName = logoutCookieName;
    }
}
