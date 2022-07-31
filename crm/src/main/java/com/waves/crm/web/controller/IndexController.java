package com.waves.crm.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author huangWenTao
 * @desc
 * @date 2022/6/5 13:53
 */
@Controller
public class IndexController {

    @RequestMapping("/")
    public String Index(){
        return "index";
    }

}
