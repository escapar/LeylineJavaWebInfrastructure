package moe.src.leyline.business.infrastructure.security;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Data;
import lombok.Setter;
import moe.src.leyline.business.domain.user.DomainUser;

/**
 * Created by bytenoob on 6/18/16.
 */

@ConfigurationProperties(locations = "classpath:jwt.properties", prefix = "jwt")
@Setter
@Component
public class JWTTokenUtils {

    public static String signingKey;

    public static Claims parse(final HttpServletRequest request){
        final String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("JUICE ")) {
            return null;
        }
        return parse(authHeader.substring(6)); // The part after "JUICE "
    }

    public static Claims parse(final String token){
        return Jwts.parser().setSigningKey(signingKey).requireNotBefore(new DateTime().minusWeeks(1).toDate())
                .parseClaimsJws(token).getBody();
    }

    public static String sign(final DomainUser user){
        return  user == null ? null :
                Jwts.builder().setSubject(user.getName())
                .claim("role", user.getRole())
                .claim("name", user.getName())
                .claim("id", user.getId())
                .setIssuedAt(new Date())
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
