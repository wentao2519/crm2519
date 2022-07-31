package com.waves.crm.settings.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author huangWenTao
 * @desc
 * @date 2022/6/7 23:39
 */
@Controller
@RequestMapping("/settings")
public class SettingsController {

    @RequestMapping("/index.do")
    public String index(){
        return "settings/index";
    }



}
