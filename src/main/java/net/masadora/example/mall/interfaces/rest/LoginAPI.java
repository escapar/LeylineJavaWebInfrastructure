package net.masadora.example.mall.interfaces.rest;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by POJO on 6/8/16.
 */
@RestController
@RequestMapping(value = "api/admin/")
public class LoginAPI {

    @RequestMapping(value = "/login", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    @SuppressWarnings(value = "unchecked")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String find()  {
        return "boo";
    }

}
