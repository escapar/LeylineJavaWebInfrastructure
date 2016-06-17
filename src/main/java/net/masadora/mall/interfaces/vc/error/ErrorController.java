package net.masadora.mall.interfaces.vc.error;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

import moe.src.leyline.framework.interfaces.vc.error.ErrorBrief;
import moe.src.leyline.framework.interfaces.vc.error.LeylineErrorController;

/**
 * Created by POJO on 6/16/16.
 */
@Controller
public class ErrorController extends LeylineErrorController {
    @Override
    public String handle403(ErrorBrief eb) {
        return "redirect:http://masadora.gi";
    }
}