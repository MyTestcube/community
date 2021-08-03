package life.majiang.community.community.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

//created by codedrinker on 2021/8/3
@Controller
public class HelloController {

    @GetMapping("/hello")
    public  String hello(@RequestParam(name = "name")String name , Model model){
        model.addAttribute("name",name);
        return "hello";

    }
}
