package com.waves.crm.settings.web.controller;

import com.waves.crm.commons.constants.Constant;
import com.waves.crm.commons.domain.ReturnObject;
import com.waves.crm.commons.utils.DateUtil;
import com.waves.crm.commons.utils.UUIDUtil;
import com.waves.crm.settings.domain.User;
import com.waves.crm.settings.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author huangWenTao
 * @desc
 * @date 2022/6/5 13:56
 */
@Controller
@RequestMapping("/settings/qx/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/toLogin.do")
    public String toLogin() {
        return "settings/qx/user/login";
    }


    @RequestMapping("/login.do")
    @ResponseBody
    public Object login(String loginAct, String loginPwd, String isRemPwd, HttpServletRequest request, HttpSession session, HttpServletResponse response) {

//        封装Map
        Map<String, Object> map = new HashMap<>();
        map.put("loginAct", loginAct);
        map.put("loginPwd", loginPwd);

        User user = userService.queryUserByLoginAvtAndPwd(map);

        // 判断用户登录
        ReturnObject returnObject = new ReturnObject();
        if (user == null) {
            //登录失败 用户名或密码错误！
            returnObject.setCode(Constant.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMessage("用户名或密码错误！");
        } else {
            // 判断用户名密码是否合法

            if (DateUtil.formatDateTime(new Date()).compareTo(user.getExpireTime()) > 0) {
                //登录失败 账号已过期！
                returnObject.setCode(Constant.RETURN_OBJECT_CODE_FAIL);
                returnObject.setMessage("账号已过期！");
            } else if ("0".equals(user.getLockState())) {
                //登录失败 账号状态被锁定！
                returnObject.setCode(Constant.RETURN_OBJECT_CODE_FAIL);
                returnObject.setMessage("账号状态被锁定！");
            } else if (!user.getAllowIps().contains(request.getRemoteAddr())) {
                // 登录失败 账号ip受限！
                returnObject.setCode(Constant.RETURN_OBJECT_CODE_FAIL);
                returnObject.setMessage("账号ip受限！");
            } else {
                // 登录成功！
                returnObject.setCode(Constant.RETURN_OBJECT_CODE_SUCCESS);

                // 把User放到session中
                session.setAttribute(Constant.SESSION_USER, user);


                if ("true".equals(isRemPwd)) {
                    // 如果记住密码 那么就往外写cookie
                    Cookie loginActCookie = new Cookie("loginAct", loginAct);
                    loginActCookie.setMaxAge(10 * 24 * 60 * 60);//记录10天
                    response.addCookie(loginActCookie);
                    Cookie loginPwdCookie = new Cookie("loginPwd", loginPwd);
                    loginPwdCookie.setMaxAge(10 * 24 * 60 * 60);//记录10天
                    response.addCookie(loginPwdCookie);
                } else {
                    // 没有勾选记住密码
                    Cookie loginActCookie = new Cookie("loginAct", "1");
                    loginActCookie.setMaxAge(0);//记录0秒 删除cookie
                    response.addCookie(loginActCookie);
                    Cookie loginPwdCookie = new Cookie("loginPwd", "1");
                    loginPwdCookie.setMaxAge(0);//记录0秒 删除cookie
                    response.addCookie(loginPwdCookie);
                }
            }
        }
        return returnObject;
    }

    @RequestMapping("/logout.do")
    public String logout(HttpServletResponse response, HttpSession session) {

        // 清空cookie
        Cookie loginActCookie = new Cookie("loginAct", "1");
        loginActCookie.setMaxAge(0);//记录0秒 删除cookie
        response.addCookie(loginActCookie);
        Cookie loginPwdCookie = new Cookie("loginPwd", "1");
        loginPwdCookie.setMaxAge(0);//记录0秒 删除cookie
        response.addCookie(loginPwdCookie);

        // 销毁session 清除session中的数据
        session.invalidate();

        // 重定向到首页
        return "redirect:/";
    }

    @RequestMapping("/modifyLoginPwd.do")
    @ResponseBody
    public Object modifyLoginPwd(String originalPwd, String confirmPwd, HttpSession session) {
        ReturnObject returnObject = new ReturnObject();
        User user = (User) session.getAttribute(Constant.SESSION_USER);

        Map<String, Object> map = new HashMap<>();
        map.put("id", user.getId());
        map.put("originalPwd", originalPwd);
        map.put("confirmPwd", confirmPwd);

        User retUser = userService.queryUserByIdAndLoginPwd(map);
        if (retUser != null) {
            if (!retUser.getLoginPwd().equals(confirmPwd)) {

                try {
                    int ret = userService.modifyLoginPwd(map);
                    if (ret > 0) {
                        returnObject.setCode(Constant.RETURN_OBJECT_CODE_SUCCESS);
                    } else {
                        returnObject.setCode(Constant.RETURN_OBJECT_CODE_FAIL);
                        returnObject.setMessage("系统忙，请稍后重试...");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    returnObject.setCode(Constant.RETURN_OBJECT_CODE_FAIL);
                    returnObject.setMessage("系统忙，请稍后重试...");
                }
            } else {
                returnObject.setCode(Constant.RETURN_OBJECT_CODE_FAIL);
                returnObject.setMessage("新密码与原密码一致！");
            }
        } else {
            returnObject.setCode(Constant.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMessage("原密码错误！");
        }
        return returnObject;
    }

    @RequestMapping("/registerUser.do")
    @ResponseBody
    public Object registerUser(User user, HttpServletRequest request) {
        ReturnObject returnObject = new ReturnObject();

        user.setId(UUIDUtil.getUUID());
        user.setCreateBy(user.getName());
        user.setAllowIps(request.getRemoteHost());
        user.setCreatetime(DateUtil.formatDateTime(new Date()));
        try {
            // 账号是否重复
            if (userService.queryUserByLoginAct(user.getLoginAct()) == null) {
                // 一个姓名只能创建一个账号
                if (userService.queryUserByName(user.getName()) == null) {

                    int ret = userService.saveCreateUser(user);
                    if (ret > 0) {
                        returnObject.setCode(Constant.RETURN_OBJECT_CODE_SUCCESS);
                        returnObject.setReturnData(user);
                        returnObject.setMessage("恭喜你，注册成功！");

                    } else {
                        returnObject.setCode(Constant.RETURN_OBJECT_CODE_FAIL);
                        returnObject.setMessage("系统忙，请稍后重试...");
                    }
                }else {
                    returnObject.setCode(Constant.RETURN_OBJECT_CODE_FAIL);
                    returnObject.setMessage(user.getName()+"已有帐号！");
                }
            }else {
                returnObject.setCode(Constant.RETURN_OBJECT_CODE_FAIL);
                returnObject.setMessage("账号"+user.getLoginAct()+"已存在！");
            }
        } catch (Exception e) {
            e.printStackTrace();
            returnObject.setCode(Constant.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMessage("系统忙，请稍后重试...");
        }

        return returnObject;
    }


}
