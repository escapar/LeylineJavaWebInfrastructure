package net.masadora.mall.interfaces.vc.error;

import net.masadora.mall.framework.interfaces.vc.ErrorBrief;
import net.masadora.mall.framework.interfaces.vc.LeylineErrorController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;


/**
 * Created by POJO on 6/16/16.
 */
@Controller
public class ErrorController extends LeylineErrorController {
    @Override
    public String handle403(ErrorBrief eb) {
        return "redirect:http://masadora.gi";
    }

    @RequestMapping(value = "/error")
    String errorMapping(HttpServletRequest request, HttpServletResponse response){
        return error(request,response);
    }
}