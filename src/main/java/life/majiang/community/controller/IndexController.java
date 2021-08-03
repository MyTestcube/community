package life.majiang.community.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

//created by codedrinker on 2021/8/3
@Controller
public class IndexController {

    @GetMapping("/")
    public  String index(){

        return "index";

    }
}
