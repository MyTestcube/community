package life.majiang.community.community.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

//created by codedrinker on 2021/8/3
@Controller
public class IndexController {

    @GetMapping("/")
    public  String index(){

        return "index";

    }
}
