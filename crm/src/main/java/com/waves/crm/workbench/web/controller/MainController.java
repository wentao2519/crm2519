package com.waves.crm.workbench.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author huangWenTao
 * @desc
 * @date 2022/6/7 22:59
 */
@Controller
@RequestMapping("/workbench/main")
public class MainController {

    @RequestMapping("/index.do")
    public String index(){
        return "workbench/main/index";
    }

}
