package moe.src.leyline.interfaces.rest;

import org.springframework.security.access.prepost.PreAuthorize;
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
