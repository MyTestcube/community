package life.majiang.community.advice;
import life.majiang.community.exception.CustomizeException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
public class CustomizeExceptionHandler {
    @ExceptionHandler(Exception.class)
    ModelAndView handle(HttpServletRequest request, Throwable e, Model model) {
        if (e instanceof CustomizeException) {
            model.addAttribute("message",e.getMessage());
        }else {
            model.addAttribute("message","服务冒烟了，要不你稍后在试一试！！！");
        }
        return new ModelAndView("error");
    }
}
