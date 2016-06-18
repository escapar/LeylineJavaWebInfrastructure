package moe.src.leyline.interfaces.rest;

import java.io.Serializable;
import java.util.Date;

import javax.servlet.ServletException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;
import moe.src.leyline.business.domain.user.DomainUser;
import moe.src.leyline.business.domain.user.UserRepo;
import moe.src.leyline.business.infrastructure.security.JWTTokenUtils;
import moe.src.leyline.business.service.UserService;
import moe.src.leyline.framework.infrastructure.common.exceptions.LeylineException;
import moe.src.leyline.framework.infrastructure.common.exceptions.PersistenceException;
import moe.src.leyline.framework.interfaces.rest.LeylineRestCRUD;
import moe.src.leyline.interfaces.dto.TokenDTO;
import moe.src.leyline.interfaces.dto.UserDTO;
import moe.src.leyline.interfaces.dto.UserLoginDTO;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * Created by POJO on 6/8/16.
 */
@RestController
@RequestMapping(value = "api/user/")
public class LoginAPI extends LeylineRestCRUD<UserService,UserDTO,DomainUser>{

    @Autowired
    UserService userService;

    @RequestMapping(value = "login", method = RequestMethod.POST,consumes = APPLICATION_JSON_VALUE,produces = APPLICATION_JSON_VALUE)
    public @ResponseBody TokenDTO login(@RequestBody final UserLoginDTO login)
            throws LeylineException {

        if (login == null) {
            throw new LeylineException("Invalid login");
        }
        DomainUser domainUser = userService.checkAndGet(login.username, login.password);

        return new TokenDTO(JWTTokenUtils.sign(domainUser));
    }

    @RequestMapping(value = "reg", method = RequestMethod.POST, consumes = APPLICATION_JSON_VALUE,produces = APPLICATION_JSON_VALUE)
    public @ResponseBody TokenDTO reg(@RequestBody final UserLoginDTO reg)
            throws PersistenceException,LeylineException {

        if (reg.username == null || reg.password == null) {
            throw new LeylineException("Invalid Params");
        }
        DomainUser domainUser = new DomainUser();
        domainUser.setName(reg.username);
        domainUser.setUnHashedPassword(reg.password);
        domainUser.setRole(0);
        userService.save(domainUser);

        return login(reg);
    }

    @RequestMapping(value = "{id}/pass", method = RequestMethod.POST, consumes = APPLICATION_JSON_VALUE,produces = APPLICATION_JSON_VALUE)

    public @ResponseBody TokenDTO changePassword(@PathVariable Long id, @RequestBody final UserLoginDTO userLogin) throws PersistenceException,LeylineException{
        DomainUser u = userService.get(id);
        //checkOwnerOf(u)
        u.setUnHashedPassword(userLogin.password);
        u = userService.save(u);
        return login(new UserLoginDTO(u.getId(),u.getName(),userLogin.getPassword()));
    }





}
