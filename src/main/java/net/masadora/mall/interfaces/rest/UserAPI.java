package net.masadora.mall.interfaces.rest;

import net.masadora.mall.business.domain.user.User;
import net.masadora.mall.business.service.UserService;
import net.masadora.mall.framework.interfaces.rest.RestCRUD;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by POJO on 6/23/16.
 */
@RestController
@RequestMapping("login")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class UserAPI {
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String boo(){
        return "boo";
    }
}
