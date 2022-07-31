package com.waves.crm.workbench.web.controller;

import com.waves.crm.commons.constants.Constant;
import com.waves.crm.commons.domain.ReturnObject;
import com.waves.crm.settings.domain.DicValue;
import com.waves.crm.settings.domain.User;
import com.waves.crm.settings.service.DicValueService;
import com.waves.crm.settings.service.UserService;
import com.waves.crm.workbench.domain.Activity;
import com.waves.crm.workbench.service.ActivityService;
import com.waves.crm.workbench.service.ContactsService;
import com.waves.crm.workbench.service.CustomerService;
import com.waves.crm.workbench.service.TranService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * @author huangWenTao
 * @desc
 * @date 2022/7/29 19:06
 */
@Controller
@RequestMapping("/workbench/transaction")
public class TranController {


    @Autowired
    private DicValueService dicValueService;

    @Autowired
    private UserService userService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private TranService tranService;

    @Autowired
    private ActivityService activityService;

    @Autowired
    private ContactsService contactsService;

    @RequestMapping("/index.do")
    public String index(HttpServletRequest request) {
        //调用service层方法，查询动态数据
        List<User> userList = userService.queryAllUsers();
        List<DicValue> stageList = dicValueService.queryDicValueByTypeCode(Constant.STAGE);
        List<DicValue> transactionTypeList = dicValueService.queryDicValueByTypeCode(Constant.TRANSACTION_TYPE);
        List<DicValue> sourceList = dicValueService.queryDicValueByTypeCode(Constant.SOURCE);
        //把数据保存到request
        request.setAttribute("userList", userList);
        request.setAttribute("stageList", stageList);
        request.setAttribute("transactionTypeList", transactionTypeList);
        request.setAttribute("sourceList", sourceList);
        //请求转发
        return "workbench/transaction/index";
    }

    @RequestMapping("/toSave.do")
    public String toSave(HttpServletRequest request) {
        //调用service层方法，查询动态数据
        List<User> userList = userService.queryAllUsers();
        List<DicValue> stageList = dicValueService.queryDicValueByTypeCode(Constant.STAGE);
        List<DicValue> transactionTypeList = dicValueService.queryDicValueByTypeCode(Constant.TRANSACTION_TYPE);
        List<DicValue> sourceList = dicValueService.queryDicValueByTypeCode(Constant.SOURCE);
        //把数据保存到request中
        request.setAttribute("userList", userList);
        request.setAttribute("stageList", stageList);
        request.setAttribute("transactionTypeList", transactionTypeList);
        request.setAttribute("sourceList", sourceList);
        //请求转发
        return "workbench/transaction/save";
    }

    @RequestMapping("/getPossibilityByStage.do")
    @ResponseBody
    public Object getPossibilityByStage(String stageValue){
        // 解析Properties文件 获取阶段对应可能性
        ResourceBundle bundle = ResourceBundle.getBundle("possibility");

        return bundle.getString(stageValue);
    }

    @RequestMapping("/queryCustomerNameByName.do")
    @ResponseBody
    public Object queryCustomerNameByName(String customerName){
        return customerService.queryCustomerNameByName(customerName);
    }


    @RequestMapping("/queryActivityByName.do")
    @ResponseBody
    public Object queryActivityByName(String activityName){
        return activityService.queryActivityByName(activityName);
    }

   @RequestMapping("/queryContactsByFullName.do")
    @ResponseBody
    public Object queryContactsByFullName(String contactsName){
        return contactsService.queryContactsByFullName(contactsName);
    }



    @RequestMapping("/saveCreateTran.do")
    @ResponseBody
    public Object saveCreatedTran(@RequestParam Map<String, Object> map, HttpSession session){

        ReturnObject returnObject = new ReturnObject();


        map.put(Constant.SESSION_USER,session.getAttribute(Constant.SESSION_USER));


        try {
            tranService.saveCreateTran(map);
            returnObject.setCode(Constant.RETURN_OBJECT_CODE_SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
            returnObject.setCode(Constant.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMessage("系统忙，请稍后重试...");
        }
        return returnObject;
    }
}
