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
import moe.src.leyline.business.domain.user.DomainUser;
import moe.src.leyline.business.domain.user.UserRepo;

/**
 * Created by POJO on 6/8/16.
 */
@RestController
@RequestMapping(value = "api/admin/")
public class LoginAPI {

    @Autowired
    UserRepo userRepo;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(@RequestBody final UserLogin login)
            throws ServletException {

        if (login.username == null || login.password == null) {
            throw new ServletException("Invalid login");
        }
        DomainUser domainUser = userRepo.checkAndGet(login.username, login.password);


        String name = login.username;

        return  Jwts.builder().setSubject(login.username)
                .claim("roles", domainUser.getRole()).claim("name", name).claim("id", domainUser.getId()).setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS256, "FBSASECRET!").compact();
    }

    @RequestMapping(value = "/reg", method = RequestMethod.POST)
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


    private static class UserLogin {
        public String username;
        public String password;
    }
}
