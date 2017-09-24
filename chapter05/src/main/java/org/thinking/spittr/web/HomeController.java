package org.thinking.spittr.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Controller //声明为一个控制器
@RequestMapping("/")
public class HomeController {

    // 处理对"/"的GET请求
    @RequestMapping(method = GET)
    public String home(Model model){
        return "home"; //视图名为hoem
    }
}
