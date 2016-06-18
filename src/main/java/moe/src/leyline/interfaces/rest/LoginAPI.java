package moe.src.leyline.interfaces.rest;

import java.util.Date;

import javax.servlet.ServletException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Value;
import moe.src.leyline.business.domain.user.DomainUser;
import moe.src.leyline.business.domain.user.UserRepo;
import moe.src.leyline.business.infrastructure.security.JWTTokenUtils;

/**
 * Created by POJO on 6/8/16.
 */
@RestController
@RequestMapping(value = "api/user/")
public class LoginAPI {

    @Autowired
    UserRepo userRepo;

    @RequestMapping(value = "login", method = RequestMethod.POST)
    public String login(@RequestBody final UserLogin login)
            throws ServletException {

        if (login == null) {
            throw new ServletException("Invalid login");
        }
        DomainUser domainUser = userRepo.checkAndGet(login.username, login.password);

        return JWTTokenUtils.sign(domainUser);
    }

    @RequestMapping(value = "reg", method = RequestMethod.POST)
    public String reg(@RequestBody final UserLogin reg)
            throws ServletException {

        if (reg.username == null || reg.password == null) {
            throw new ServletException("Invalid Params");
        }
        DomainUser domainUser = new DomainUser();
        domainUser.setName(reg.username);
        domainUser.setPassword(BCrypt.hashpw(reg.password, BCrypt.gensalt()));
        domainUser.setRole(0);
        userRepo.save(domainUser);

        return login(reg);
    }


    @Value private static class UserLogin {
        private String username;
        private String password;
    }
}
