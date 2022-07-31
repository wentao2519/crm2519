package com.waves.crm.settings.web.controller;

import com.waves.crm.settings.domain.DicType;
import com.waves.crm.settings.domain.DicValue;
import com.waves.crm.settings.service.DicTypeService;
import com.waves.crm.settings.service.DicValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author huangWenTao
 * @desc
 * @date 2022/7/3 22:09
 */
@Controller
@RequestMapping("/settings/dictionary/value")
public class DictionaryValueController {

    @Autowired
    private DicValueService dicValueService;
    @Autowired
    private DicTypeService dicTypeService;
    @RequestMapping("index.do")
    public String index(){
        return "settings/dictionary/value/index";
    }


    @RequestMapping("/save.do")
    public String save(HttpServletRequest request){
        List<DicType> dicTypeList = dicTypeService.queryAllDicType();
        request.setAttribute("dicTypeList", dicTypeList);
        return "settings/dictionary/value/save";
    }
    @RequestMapping("/edit.do")
    public String edit(){
        return "settings/dictionary/value/edit";
    }


    @RequestMapping("/queryAllDicValue.do")
    @ResponseBody
    public Object queryAllDicType(){

        List<DicValue> dicValueList = dicValueService.queryAllDicValue();
        //根据查询结果结果，生成响应信息
        Map<String, Object> retMap = new HashMap<>();
        retMap.put("dicValueList",dicValueList);
        return retMap;
    }
}
