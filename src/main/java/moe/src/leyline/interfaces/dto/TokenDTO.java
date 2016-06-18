package moe.src.leyline.interfaces.dto;

import java.io.Serializable;

/**
 * Created by bytenoob on 6/18/16.
 */
public class TokenDTO implements Serializable {
    private String token;

    public TokenDTO(String token){
        this.token = token;
    }
    public String getToken() {
        return token;
    }

    public void setToken(final String token) {
        this.token = token;
    }
}