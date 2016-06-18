package moe.src.leyline.business.infrastructure.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

/**
 * Created by bytenoob on 6/18/16.
 */
public class JWTTokenUtils {
    public static Claims parse(final String token){
        return Jwts.parser().setSigningKey("FBSASECRET!")
                .parseClaimsJws(token).getBody();
    }
}
