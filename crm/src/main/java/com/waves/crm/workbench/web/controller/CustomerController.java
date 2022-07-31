package com.waves.crm.workbench.web.controller;

import com.waves.crm.commons.constants.Constant;
import com.waves.crm.commons.domain.ReturnObject;
import com.waves.crm.commons.utils.DateUtil;
import com.waves.crm.commons.utils.UUIDUtil;
import com.waves.crm.settings.domain.DicValue;
import com.waves.crm.settings.domain.User;
import com.waves.crm.settings.service.DicValueService;
import com.waves.crm.settings.service.UserService;
import com.waves.crm.workbench.domain.*;
import com.waves.crm.workbench.mapper.TranMapper;
import com.waves.crm.workbench.service.ContactsService;
import com.waves.crm.workbench.service.CustomerRemarkService;
import com.waves.crm.workbench.service.CustomerService;
import com.waves.crm.workbench.service.TranService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author huangWenTao
 * @desc
 * @date 2022/7/25 15:38
 */
@Controller
@RequestMapping("/workbench/customer")
public class CustomerController {

    @Autowired
    private UserService userService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CustomerRemarkService customerRemarkService;

    @Autowired
    private TranService tranService;

    @Autowired
    private ContactsService contactsService;

    @Autowired
    private DicValueService dicValueService;



    @RequestMapping("/index.do")
    public String index(HttpServletRequest request) {
        List<User> userList = userService.queryAllUsers();
        request.setAttribute("userList", userList);
        return "workbench/customer/index";
    }
    @RequestMapping("/saveCreateCustomer.do")
    @ResponseBody
    public Object saveCreateCustomer(Customer customer, HttpSession session){
        ReturnObject returnObject = new ReturnObject();
        User user = (User) session.getAttribute(Constant.SESSION_USER);
        customer.setId(UUIDUtil.getUUID());
        customer.setCreateBy(user.getId());
        customer.setCreateTime(DateUtil.formatDateTime(new Date()));

        try {
            int ret = customerService.saveCreateCustomer(customer);
            if (ret>0){
                returnObject.setCode(Constant.RETURN_OBJECT_CODE_SUCCESS);
            }else {
                returnObject.setCode(Constant.RETURN_OBJECT_CODE_FAIL);
                returnObject.setMessage("系统忙，请稍后重试！");
            }
        }catch (Exception e){
            e.printStackTrace();
            returnObject.setCode(Constant.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMessage("系统忙，请稍后重试！");
        }

        return returnObject;
    }

    @RequestMapping("/queryCustomerByConditionForPage.do")
    @ResponseBody
    public Object queryCustomerByConditionForPage(String name,String owner,String phone,String website,int pageNo, int pageSize){
        Map<String, Object> map = new HashMap<>();
        map.put("name",name);
        map.put("owner",owner);
        map.put("phone",phone);
        map.put("website",website);
        map.put("beginNo",(pageNo - 1) * pageSize);
        map.put("pageSize",pageSize);

        List<Customer> customerList = customerService.queryCustomerByConditionForPage(map);
        int totalRows = customerService.queryCountOfCustomerByCondition(map);

        Map<String, Object> retMap = new HashMap<>();
        retMap.put("customerList",customerList);
        retMap.put("totalRows",totalRows);
        return retMap;
    }

    @RequestMapping("/deleteCustomerByIds.do")
    @ResponseBody
    public Object deleteCustomerById(String[] id){
        ReturnObject returnObject = new ReturnObject();
        try {
            int ret = customerService.deleteCustomerByIds(id);
            if (ret>0){
                returnObject.setCode(Constant.RETURN_OBJECT_CODE_SUCCESS);
            }else {
                returnObject.setCode(Constant.RETURN_OBJECT_CODE_FAIL);
                returnObject.setMessage("系统忙，请稍后重试...");
            }
        }catch (Exception e){
            e.printStackTrace();
            returnObject.setCode(Constant.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMessage("系统忙，请稍后重试...");
        }

        return returnObject;
    }

    @RequestMapping("/queryCustomerById.do")
    @ResponseBody
    public Object queryCustomerById(String id){
        return customerService.queryCustomerById(id);
    }


    @RequestMapping("/saveEditCustomer.do")
    @ResponseBody
    public Object saveEditCustomer(Customer customer,HttpSession session){
        ReturnObject returnObject = new ReturnObject();
        User user = (User) session.getAttribute(Constant.SESSION_USER);
        customer.setEditBy(user.getId());
        customer.setEditTime(DateUtil.formatDateTime(new Date()));

        try {
            int ret = customerService.saveEditCustomer(customer);
            if (ret > 0) {
                returnObject.setCode(Constant.RETURN_OBJECT_CODE_SUCCESS);
            } else {
                returnObject.setCode(Constant.RETURN_OBJECT_CODE_FAIL);
                returnObject.setMessage("系统忙，请稍后再试...");
            }
        } catch (Exception e) {
            e.printStackTrace();
            returnObject.setCode(Constant.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMessage("系统忙，请稍后再试...");
        }
        return returnObject;
    }

    @RequestMapping("/detailCustomer.do")
    public String detailCustomer(String id , HttpServletRequest request){
        // 客户详细信息
        Customer customer = customerService.queryCustomerDetailInfoById(id);
        // 客户备注详细信息
        List<CustomerRemark> customerRemarkList = customerRemarkService.queryCustomerRemarkDetailInfoByCustomerId(id);
        // 联系人 信息
        List<Contacts> contactsList = contactsService.queryContactsByCustomerId(id);
        // 交易 信息
        List<Tran> tranList = tranService.queryTranByCustomerId(id);
        //所有者
        List<User> userList = userService.queryAllUsers();
        // 来源
        List<DicValue> sourceList = dicValueService.queryDicValueByTypeCode(Constant.SOURCE);
        // 称呼
        List<DicValue> appellationList = dicValueService.queryDicValueByTypeCode(Constant.APPELLATION);

        request.setAttribute("customer",customer);
        request.setAttribute("customerRemarkList",customerRemarkList);
        request.setAttribute("contactsList",contactsList);
        request.setAttribute("tranList",tranList);
        request.setAttribute("userList",userList);
        request.setAttribute("sourceList",sourceList);
        request.setAttribute("appellationList",appellationList);

        return "workbench/customer/detail";
    }
    @RequestMapping("/saveCreateCustomerRemark.do")
    @ResponseBody
    public Object saveCreateCustomerRemark(CustomerRemark customerRemark,HttpSession session){
        ReturnObject returnObject = new ReturnObject();
        User user = (User) session.getAttribute(Constant.SESSION_USER);
        customerRemark.setId(UUIDUtil.getUUID());
        customerRemark.setCreateBy(user.getId());
        customerRemark.setCreateTime(DateUtil.formatDateTime(new Date()));
        customerRemark.setEditFlag(Constant.REMARK_EDIT_FLAG_NO_EDITED);

        try {
            int ret = customerRemarkService.saveCreateCustomerRemark(customerRemark);
            if (ret>0){
                returnObject.setCode(Constant.RETURN_OBJECT_CODE_SUCCESS);
                returnObject.setReturnData(customerRemark);
            }else {
                returnObject.setCode(Constant.RETURN_OBJECT_CODE_FAIL);
                returnObject.setMessage("系统忙，请稍后重试！");
            }
        }catch (Exception e){
            e.printStackTrace();
            returnObject.setCode(Constant.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMessage("系统忙，请稍后重试！");
        }

        return returnObject;
    }

    @RequestMapping("/deleteCustomerRemark.do")
    @ResponseBody
    public Object deleteCustomerRemark(String id){
        ReturnObject returnObject = new ReturnObject();
        try {
            int ret = customerRemarkService.deleteCustomerRemarkById(id);
            if (ret>0){
                returnObject.setCode(Constant.RETURN_OBJECT_CODE_SUCCESS);
            }else {
                returnObject.setCode(Constant.RETURN_OBJECT_CODE_FAIL);
                returnObject.setMessage("系统忙，请稍后再试...");
            }
        }catch (Exception e){
            e.printStackTrace();
            returnObject.setCode(Constant.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMessage("系统忙，请稍后再试...");
        }


        return returnObject;
    }

    @RequestMapping("/saveEditCustomerRemark.do")
    @ResponseBody
    public Object saveEditCustomerRemark(CustomerRemark customerRemark , HttpSession session){
        ReturnObject returnObject = new ReturnObject();

        User user = (User) session.getAttribute(Constant.SESSION_USER);
        customerRemark.setEditFlag(Constant.REMARK_EDIT_FLAG_YES_EDITED);
        customerRemark.setEditBy(user.getId());
        customerRemark.setEditTime(DateUtil.formatDateTime(new Date()));

        try {
            //调用service层方法，保存修改的市场活动备注
            int ret = customerRemarkService.saveEditCustomerRemark(customerRemark);

            if(ret>0){
                returnObject.setCode(Constant.RETURN_OBJECT_CODE_SUCCESS);
                returnObject.setReturnData(customerRemark);
            }else{
                returnObject.setCode(Constant.RETURN_OBJECT_CODE_FAIL);
                returnObject.setMessage("系统忙，请稍后重试....");
            }
        }catch (Exception e){
            e.printStackTrace();
            returnObject.setCode(Constant.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMessage("系统忙，请稍后重试....");
        }
        return returnObject;

    }

    @RequestMapping("/queryCustomerNameByName.do")
    @ResponseBody
    public Object queryCustomerNameByName(String customerName){
        return customerService.queryCustomerNameByName(customerName);
    }

}
