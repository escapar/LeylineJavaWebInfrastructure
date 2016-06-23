package moe.src.leyline.interfaces.rest;


import moe.src.leyline.framework.interfaces.vc.LeylineErrorBrief;
import moe.src.leyline.framework.interfaces.vc.LeylineErrorController;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by POJO on 6/16/16.
 */
@RestController
public class ErrorController extends LeylineErrorController {
    @Override
    public String genericError(LeylineErrorBrief eb){
        return eb.toString();
    }
}