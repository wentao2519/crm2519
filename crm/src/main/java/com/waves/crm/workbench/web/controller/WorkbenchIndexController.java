package com.waves.crm.workbench.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author huangWenTao
 * @desc
 * @date 2022/6/6 9:41
 */
@Controller
@RequestMapping("/workbench")
public class WorkbenchIndexController {

    @RequestMapping("/index.do")
    public String index(){
        //跳转到业务主页
        return "workbench/index";
    }

}
