package moe.src.leyline.interfaces.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import moe.src.leyline.business.domain.user.User;
import moe.src.leyline.business.infrastructure.security.JWTTokenUtils;
import moe.src.leyline.business.service.UserService;
import moe.src.leyline.framework.infrastructure.common.exceptions.LeylineException;
import moe.src.leyline.framework.infrastructure.common.exceptions.PersistenceException;
import moe.src.leyline.framework.interfaces.rest.LeylineRestCRUD;
import moe.src.leyline.interfaces.dto.user.TokenDTO;
import moe.src.leyline.interfaces.dto.user.UserDTO;
import moe.src.leyline.interfaces.dto.user.UserLoginDTO;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.assertj.core.api.Assertions.*;

/**
 * Created by POJO on 6/8/16.
 */
@RestController
@RequestMapping(value = "api/user/")
public class UserAPI extends LeylineRestCRUD<UserService, User, UserDTO> {

    @Autowired
    UserService userService;

    @RequestMapping(value = "login", method = RequestMethod.POST, consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public @ResponseBody TokenDTO login(@RequestBody final UserDTO login)
            throws LeylineException {

        if (login == null) {
            throw new LeylineException("Invalid login");
        }
        User user = userService.checkAndGet(login.getName(), login.getPassword());

        return new TokenDTO(JWTTokenUtils.sign(user));
    }

    @RequestMapping(value = "reg", method = RequestMethod.POST, consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public @ResponseBody TokenDTO reg(@RequestBody final UserDTO reg)
            throws LeylineException {
        assertThat(reg.getId()).isNotNull();
        if (reg.getName() == null || reg.getPassword() == null) {
            throw new LeylineException("Invalid Params");
        }
        userService.reg(dtoAssembler.buildDO(reg));
        return login(reg);
    }

    @RequestMapping(value = "{id}/pass", method = RequestMethod.POST, consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)

    public @ResponseBody TokenDTO changePassword(@PathVariable Long id, @RequestBody final UserLoginDTO userLogin) throws PersistenceException, LeylineException {
        User u = userService.get(id);
        //checkOwnerOf(u)
        u.setUnHashedPassword(userLogin.password);
        u = userService.save(u);
        User user = userService.checkAndGet(u.getName(), u.getPassword());
        return new TokenDTO(JWTTokenUtils.sign(user));
    }

}
