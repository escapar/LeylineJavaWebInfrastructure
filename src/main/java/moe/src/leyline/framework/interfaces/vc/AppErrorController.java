package moe.src.leyline.framework.interfaces.vc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * 拦截并处理错误信息,需要在业务层implement
 */

public abstract class AppErrorController implements ErrorController {

    private static final String PATH = "/error";

    @Autowired
    private ErrorAttributes errorAttributes;

    public ModelAndView error(ErrorBrief eb, HttpServletRequest request, HttpServletResponse response) {
        // Appropriate HTTP response code (e.g. 404 or 500) is automatically set by Spring.
        // Here we just define response body.
        String view;
        HashMap <String, Object> m = new HashMap<>();
        m.put("error",eb);
        switch (eb.status){
            case 400: view = handle400(eb);break;
            case 403: view = handle403(eb);break;
            case 404: view = handle404(eb);break;
            case 405: view = handle405(eb);break;
            case 500: view = handle500(eb);break;
            case 501: view = handle501(eb);break;
            case 502: view = handle502(eb);break;
            case 503: view = handle503(eb);break;
            default: view = otherErrors(eb);
        }
        return new ModelAndView(view,m);
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

    public Map<String, Object> getErrorAttributes(HttpServletRequest request, boolean includeStackTrace) {
        RequestAttributes requestAttributes = new ServletRequestAttributes(request);
        return errorAttributes.getErrorAttributes(requestAttributes, includeStackTrace);
    }

}