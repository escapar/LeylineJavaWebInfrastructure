package moe.src.leyline.interfaces.vc.error;

import org.springframework.stereotype.Controller;

import moe.src.leyline.framework.interfaces.vc.error.ErrorBrief;
import moe.src.leyline.framework.interfaces.vc.error.LeylineErrorController;

/**
 * Created by POJO on 6/16/16.
 */
@Controller
public class ErrorController extends LeylineErrorController {
    @Override
    public String handle403(ErrorBrief eb) {
        return "redirect:http://login.gi";
    }
}