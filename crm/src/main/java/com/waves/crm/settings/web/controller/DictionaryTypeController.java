package com.waves.crm.settings.web.controller;

import com.waves.crm.commons.constants.Constant;
import com.waves.crm.commons.domain.ReturnObject;
import com.waves.crm.commons.utils.DateUtil;
import com.waves.crm.settings.domain.DicType;
import com.waves.crm.settings.domain.User;
import com.waves.crm.settings.service.DicTypeService;
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
 * @date 2022/7/3 21:59
 */
@Controller
@RequestMapping("/settings/dictionary/type")
public class DictionaryTypeController {
    @Autowired
    private DicTypeService dicTypeService;

    @RequestMapping("/index.do")
    public String index() {
        return "settings/dictionary/type/index";
    }

    @RequestMapping("/save.do")
    public String save() {
        return "settings/dictionary/type/save";
    }

    @RequestMapping("/edit.do")
    public String edit(String code , HttpServletRequest request) {
        DicType dicType = dicTypeService.queryDicTypeByCode(code);
        request.setAttribute("id",code);
        request.setAttribute("dicType",dicType);
        return "settings/dictionary/type/edit";
    }

    @RequestMapping("/saveDicType.do")
    @ResponseBody
    public Object saveDicType(DicType dicType) {
        List<DicType> dicTypeList = dicTypeService.queryAllDicType();
        ReturnObject returnObject = null;
        for (DicType dt : dicTypeList) {
            if (dicType.getCode().equals(dt.getCode())) {
                returnObject = new ReturnObject();
                returnObject.setCode(Constant.RETURN_OBJECT_CODE_FAIL);
                returnObject.setMessage("编码不能重复");
            }
        }
        if (returnObject == null){
            returnObject = new ReturnObject();
            try {
                int ret = dicTypeService.saveDicType(dicType);
                if (ret > 0) {
                    returnObject.setCode(Constant.RETURN_OBJECT_CODE_SUCCESS);
                } else {
                    returnObject.setCode(Constant.RETURN_OBJECT_CODE_FAIL);
                    returnObject.setMessage("系统正忙，请稍后重试...");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return returnObject;
    }

    @RequestMapping("/queryAllDicType.do")
    @ResponseBody
    public Object queryAllDicType() {

        List<DicType> dicTypeList = dicTypeService.queryAllDicType();
        //根据查询结果结果，生成响应信息
        Map<String, Object> retMap = new HashMap<>();
        retMap.put("dicTypeList", dicTypeList);
        return retMap;
    }

    @RequestMapping("/deleteDicTypeByCodes.do")
    @ResponseBody
    public Object deleteDicTypeByCodes(String[] code){
        ReturnObject returnObject = new ReturnObject();
        try {
            int ret = dicTypeService.deleteDicTypeByCodes(code);
            if (ret > 0) {
                returnObject.setCode(Constant.RETURN_OBJECT_CODE_SUCCESS);
            } else {
                returnObject.setCode(Constant.RETURN_OBJECT_CODE_FAIL);
                returnObject.setMessage("系统忙，请稍后再试...");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnObject;
    }

    @RequestMapping("/editDicType.do")
    @ResponseBody
    public Object editDicType(DicType dicType,String id){

        ReturnObject returnObject = new ReturnObject();
        Map<String, Object> map = new HashMap<>();
        map.put("code",dicType.getCode());
        map.put("name",dicType.getName());
        map.put("description",dicType.getDescription());
        map.put("id",id);
        try {
            int ret = dicTypeService.editDicType(map);
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

}
