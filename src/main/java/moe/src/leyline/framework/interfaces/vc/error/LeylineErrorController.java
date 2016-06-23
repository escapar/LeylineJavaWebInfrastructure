package moe.src.leyline.framework.interfaces.vc.error;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Created by POJO on 6/16/16.
 */

public abstract class LeylineErrorController implements ErrorController {

    private static final String PATH = "/error";

    @Autowired
    private ErrorAttributes errorAttributes;

    @RequestMapping(value = PATH)
    String error(HttpServletRequest request, HttpServletResponse response) {
        // Appropriate HTTP response code (e.g. 404 or 500) is automatically set by Spring.
        // Here we just define response body.
        ErrorBrief eb= new ErrorBrief(response.getStatus(), getErrorAttributes(request, false));
        switch (eb.status){
            case 400: return handle400(eb);
            case 403: return handle403(eb);
            case 404: return handle404(eb);
            case 405: return handle405(eb);
            case 500: return handle500(eb);
            case 501: return handle501(eb);
            case 502: return handle502(eb);
            case 503: return handle503(eb);
            default: return otherErrors(eb);
        }
    }

    public String genericError(ErrorBrief eb){
        return "/error/" + eb.status;
    }

    public String otherErrors(ErrorBrief eb){
        return genericError(eb);
    }

    public String handle400(ErrorBrief eb){
        return genericError(eb);
    }

    public String handle403(ErrorBrief eb){
        return genericError(eb);
    }

    public String handle404(ErrorBrief eb){
        return genericError(eb);
    }

    public String handle405(ErrorBrief eb){
        return genericError(eb);
    }

    public String handle500(ErrorBrief eb){
        return genericError(eb);
    }

    public String handle501(ErrorBrief eb){
        return genericError(eb);
    }


    public String handle502(ErrorBrief eb){
        return genericError(eb);
    }


    public String handle503(ErrorBrief eb){
        return genericError(eb);
    }

    @Override
    public String getErrorPath() {
        return PATH;
    }

    private Map<String, Object> getErrorAttributes(HttpServletRequest request, boolean includeStackTrace) {
        RequestAttributes requestAttributes = new ServletRequestAttributes(request);
        return errorAttributes.getErrorAttributes(requestAttributes, includeStackTrace);
    }

}