package net.masadora.mall.business.infrastructure.common;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;

/**
 * @author XUYI
 * Spring Security 3.1 PasswordEncoder
 */
public class EncryptUtil {
    //从配置文件中获得
    private static final String SITE_WIDE_SECRET = "my-secret-key";
    private static final PasswordEncoder encoder = new StandardPasswordEncoder(
       SITE_WIDE_SECRET);
 
    public static String encrypt(String rawPassword) {
         return encoder.encode(rawPassword);
    }
 
    public static boolean match(String rawPassword, String password) {
         return encoder.matches(rawPassword, password);
    }

 }
