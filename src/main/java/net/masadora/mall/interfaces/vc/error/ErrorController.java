package net.masadora.mall.interfaces.vc.error;

import net.masadora.mall.framework.interfaces.vc.AppErrorController;
import net.masadora.mall.framework.interfaces.vc.ErrorBrief;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by POJO on 6/16/16.
 */
@Controller
public class ErrorController extends AppErrorController {
    @Override
    public String handle403(ErrorBrief eb) {
        return "redirect:http://masadora.gi";
    }

    /**
     * 错误处理入口
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/error")
    public ModelAndView errorMapping(HttpServletRequest request, HttpServletResponse response){
        ErrorBrief eb= new ErrorBrief(response.getStatus(), getErrorAttributes(request, false));
        if(eb.exception.contains("Assert")) return assertResponse(eb);
        return error(eb,request,response);
    }

    /**
     * 尽量用assertion来判断数据合法 这样可以拿到很详尽的Error
     * @param eb
     * @return
     */
    @ResponseBody
    public ModelAndView assertResponse(ErrorBrief eb){
        Map<String,Object> model = new HashMap<>();
        model.put("error",eb.message.split("AssertionError:")[1].replaceAll("\n"," "));
        MappingJackson2JsonView v = new MappingJackson2JsonView();
        v.setPrettyPrint(true);
        return new ModelAndView(v,model);
    }
}