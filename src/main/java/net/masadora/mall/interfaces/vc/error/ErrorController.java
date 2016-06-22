package net.masadora.mall.interfaces.vc.error;

import net.masadora.mall.framework.interfaces.vc.ErrorBrief;
import net.masadora.mall.framework.interfaces.vc.AppErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Created by POJO on 6/16/16.
 */
@Controller
public class ErrorController extends AppErrorController {
    @Override
    public String handle403(ErrorBrief eb) {
        return "redirect:http://masadora.gi";
    }

    @RequestMapping(value = "/error")
    String errorMapping(HttpServletRequest request, HttpServletResponse response){
        return error(request,response);
    }
}