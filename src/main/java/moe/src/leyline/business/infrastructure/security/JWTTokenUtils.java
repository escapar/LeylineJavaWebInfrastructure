package moe.src.leyline.business.infrastructure.security;

import javax.servlet.http.HttpServletRequest;

import org.joda.time.DateTime;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Setter;
import moe.src.leyline.business.domain.user.User;

/**
 * Created by bytenoob on 6/18/16.
 */

@ConfigurationProperties(locations = "classpath:jwt.properties", prefix = "jwt")
@Setter
@Component
public class JWTTokenUtils {

    public static String signingKey;

    public static Claims parse(final HttpServletRequest request) {
        final String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("JUICE ")) {
            return null;
        }
        return parse(authHeader.substring(6)); // The part after "JUICE "
    }

    public static Claims parse(final String token) {
        return Jwts.parser().setSigningKey(signingKey)
                .parseClaimsJws(token).getBody();
    }

    public static String sign(final User user) {
        return user == null ? null :
                Jwts.builder().setSubject(user.getName())
                        .claim("role", user.getRole())
                        .claim("name", user.getName())
                        .claim("id", user.getId())
                        .setExpiration(new DateTime().plusWeeks(1).toDate())
                        .signWith(SignatureAlgorithm.HS256, signingKey)
                        .compact();
    }

    public static String getSigningKey() {
        return signingKey;
    }

    public static void setSigningKey(final String signingKey) {
        JWTTokenUtils.signingKey = signingKey;
    }
}
