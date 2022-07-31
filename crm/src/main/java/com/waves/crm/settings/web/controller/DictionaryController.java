package com.waves.crm.settings.web.controller;

import com.waves.crm.settings.domain.DicType;
import com.waves.crm.settings.domain.User;
import com.waves.crm.settings.service.DicTypeService;
import com.waves.crm.settings.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author huangWenTao
 * @desc
 * @date 2022/7/3 21:31
 */
@Controller
@RequestMapping("/settings/dictionary")
public class DictionaryController {

    @Autowired
    private UserService userService;

    @Autowired
    private DicTypeService dicTypeService;

    @RequestMapping("/index.do")
    public String index(HttpServletRequest request){
        List<User> userList = userService.queryAllUsers();
        request.setAttribute("userList", userList);


        return "settings/dictionary/index";
    }
}
