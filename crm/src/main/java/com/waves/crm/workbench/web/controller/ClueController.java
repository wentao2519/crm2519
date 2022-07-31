package com.waves.crm.workbench.web.controller;

import com.waves.crm.commons.constants.Constant;
import com.waves.crm.commons.domain.ReturnObject;
import com.waves.crm.commons.utils.DateUtil;
import com.waves.crm.commons.utils.UUIDUtil;
import com.waves.crm.settings.domain.DicValue;
import com.waves.crm.settings.domain.User;
import com.waves.crm.settings.service.DicValueService;
import com.waves.crm.settings.service.UserService;
import com.waves.crm.workbench.domain.Activity;
import com.waves.crm.workbench.domain.Clue;
import com.waves.crm.workbench.domain.ClueActivityRelation;
import com.waves.crm.workbench.domain.ClueRemark;
import com.waves.crm.workbench.mapper.ClueActivityRelationMapper;
import com.waves.crm.workbench.service.ActivityService;
import com.waves.crm.workbench.service.ClueActivityRelationService;
import com.waves.crm.workbench.service.ClueRemarkService;
import com.waves.crm.workbench.service.ClueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * @author huangWenTao
 * @desc
 * @date 2022/7/14 21:14
 */
@Controller
@RequestMapping("/workbench/clue")
public class ClueController {

    @Autowired
    private UserService userService;

    @Autowired
    private DicValueService dicValueService;

    @Autowired
    private ClueService clueService;

    @Autowired
    private ClueRemarkService clueRemarkService;

    @Autowired
    private ActivityService activityService;

    @Autowired
    private ClueActivityRelationService clueActivityRelationService;

    @RequestMapping("/index.do")
    public String index(HttpServletRequest request) {
        Map<String, List<DicValue>> dicValueMap = new HashMap<>();
        List<User> userList = userService.queryAllUsers();
        List<DicValue> appellationList = dicValueService.queryDicValueByTypeCode(Constant.APPELLATION);
        List<DicValue> clueStateList = dicValueService.queryDicValueByTypeCode(Constant.CLUE_STATE);
        List<DicValue> sourceList = dicValueService.queryDicValueByTypeCode(Constant.SOURCE);

        dicValueMap.put("appellationList", appellationList);
        dicValueMap.put("clueStateList", clueStateList);
        dicValueMap.put("sourceList", sourceList);

        request.setAttribute("userList", userList);
        request.setAttribute("dicValueMap", dicValueMap);
        return "workbench/clue/index";
    }

    @RequestMapping("/saveCreateClue.do")
    @ResponseBody
    public Object saveCreateClue(Clue clue, HttpSession session) {
        User user = (User) session.getAttribute(Constant.SESSION_USER);
        ReturnObject returnObject = new ReturnObject();

        clue.setId(UUIDUtil.getUUID());
        clue.setCreateBy(user.getId());
        clue.setCreateTime(DateUtil.formatDateTime(new Date()));

        try {
            int ret = clueService.saveCreateClue(clue);
            if (ret > 0) {
                returnObject.setCode(Constant.RETURN_OBJECT_CODE_SUCCESS);
            } else {
                returnObject.setCode(Constant.RETURN_OBJECT_CODE_FAIL);
                returnObject.setMessage("系统忙，请稍后再试！");
            }
        } catch (Exception e) {
            e.printStackTrace();
            returnObject.setCode(Constant.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMessage("系统忙，请稍后再试！");
        }
        return returnObject;
    }

    @RequestMapping("/queryClueByConditionForPage.do")
    @ResponseBody
    public Object queryClueByConditionForPage(String fullname, String company, String phone, String source, String owner,
                                              String mphone, String state, int pageNo, int pageSize) {
        //封装参数
        Map<String, Object> map = new HashMap<>();
        map.put("fullname", fullname);
        map.put("owner", owner);
        map.put("company", company);
        map.put("phone", phone);
        map.put("source", source);
        map.put("mphone", mphone);
        map.put("state", state);
        map.put("beginNo", (pageNo - 1) * pageSize);
        map.put("pageSize", pageSize);

        List<Clue> clueList = clueService.queryClueByConditionForPage(map);
        int totalRows = clueService.queryCountOfClueByCondition(map);

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("clueList", clueList);
        resultMap.put("totalRows", totalRows);

        return resultMap;
    }

    @RequestMapping("/detailClue.do")
    public String detailClue(String id, HttpServletRequest request) {
        Clue clue = clueService.queryClueForDetailById(id);
        List<ClueRemark> clueRemarkList = clueRemarkService.queryClueRemarkForDetailByClueId(id);
        List<Activity> activityList = activityService.queryActivityForDetailByClueId(id);

        request.setAttribute("clue", clue);
        request.setAttribute("remarkList", clueRemarkList);
        request.setAttribute("activityList", activityList);

        return "workbench/clue/detail";
    }

    @RequestMapping("/queryActivityForDetailByNameClueId.do")
    @ResponseBody
    public Object queryActivityForDetailByNameClueId(String activityName, String clueId) {

        Map<String, Object> map = new HashMap<>();
        map.put("activityName", activityName);
        map.put("clueId", clueId);

        return activityService.queryActivityForDetailByNameClueId(map);
    }

    @RequestMapping("/saveClueActivityRelation.do")
    @ResponseBody
    public Object saveClueActivityRelation(String[] activityId, String clueId) {
        ReturnObject returnObject = new ReturnObject();
        // 封装参数
        List<ClueActivityRelation> clueActivityRelationList = new ArrayList<>();
        ClueActivityRelation clueActivityRelation = null;
        for (String id : activityId) {
             clueActivityRelation = new ClueActivityRelation();
             clueActivityRelation.setId(UUIDUtil.getUUID());
             clueActivityRelation.setActivityId(id);
             clueActivityRelation.setClueId(clueId);
             clueActivityRelationList.add(clueActivityRelation);
        }

        try {
            int ret = clueActivityRelationService.saveCreateClueActivityRelationByList(clueActivityRelationList);
            if (ret>0){
                returnObject.setCode(Constant.RETURN_OBJECT_CODE_SUCCESS);
                List<Activity> activityList = activityService.queryActivityForDetailByIds(activityId);
                returnObject.setReturnData(activityList);
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
    @RequestMapping("/deleteClueByIds.do")
    @ResponseBody
    public Object deleteClueByIds(String[] id){
        ReturnObject returnObject = new ReturnObject();
        try {
            int ret = clueService.deleteClueByIds(id);
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

    @RequestMapping("/queryClueById.do")
    @ResponseBody
    public Object queryClueById(String id) {
        return clueService.queryClueById(id);
    }

    @RequestMapping("/saveEditClue.do")
    @ResponseBody
    public Object saveEditClue(Clue clue, HttpSession session) {

        clue.setEditTime(DateUtil.formatDateTime(new Date()));
        User user = (User) session.getAttribute(Constant.SESSION_USER);
        clue.setEditBy(user.getId());
        ReturnObject returnObject = new ReturnObject();
        try {
            int ret = clueService.saveEditClue(clue);
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

    @RequestMapping("/removeClueActivityRelation.do")
    @ResponseBody
    public Object removeClueActivityRelation(ClueActivityRelation clueActivityRelation){
        ReturnObject returnObject = new ReturnObject();

        try {
            int ret = clueActivityRelationService.deleteClueActivityRelationByClueIdActivityId(clueActivityRelation);
            if (ret > 0) {
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


    @RequestMapping("/saveCreateClueRemark.do")
    @ResponseBody
    public Object saveCreateClueRemark(ClueRemark clueRemark,HttpSession session){
        ReturnObject returnObject = new ReturnObject();
        User user = (User) session.getAttribute(Constant.SESSION_USER);
        clueRemark.setId(UUIDUtil.getUUID());
        clueRemark.setCreateBy(user.getId());
        clueRemark.setCreateTime(DateUtil.formatDateTime(new Date()));
        clueRemark.setEditFlag(Constant.REMARK_EDIT_FLAG_NO_EDITED);

        try {
            int ret = clueRemarkService.saveCreateClueRemark(clueRemark);
            if (ret>0){
                returnObject.setCode(Constant.RETURN_OBJECT_CODE_SUCCESS);
                returnObject.setReturnData(clueRemark);
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

    @RequestMapping("/deleteClueRemarkById.do")
    @ResponseBody
    public Object deleteClueRemarkById(String id){
        ReturnObject returnObject = new ReturnObject();
        try {
            int ret = clueRemarkService.deleteClueRemarkById(id);
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

    @RequestMapping("/saveEditClueRemark.do")
    @ResponseBody
    public Object saveEditClueRemark(ClueRemark clueRemark ,HttpSession session){
        ReturnObject returnObject = new ReturnObject();

        User user = (User) session.getAttribute(Constant.SESSION_USER);
        clueRemark.setEditFlag(Constant.REMARK_EDIT_FLAG_YES_EDITED);
        clueRemark.setEditBy(user.getId());
        clueRemark.setEditTime(DateUtil.formatDateTime(new Date()));

        try {
            //调用service层方法，保存修改的市场活动备注
            int ret = clueRemarkService.saveEditClueRemark(clueRemark);

            if(ret>0){
                returnObject.setCode(Constant.RETURN_OBJECT_CODE_SUCCESS);
                returnObject.setReturnData(clueRemark);
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

    @RequestMapping("/toConvert.do")
    public String toConvert(String id,HttpServletRequest request){
        Clue clue = clueService.queryClueForDetailById(id);
        List<DicValue> stageList = dicValueService.queryDicValueByTypeCode(Constant.STAGE);
        request.setAttribute("clue",clue);
        request.setAttribute("stageList",stageList);

        return "workbench/clue/convert";
    }

    @RequestMapping("/queryActivityForConvertByNameClueId.do")
    @ResponseBody
    public Object queryActivityForConvertByNameClueId(String activityName,String clueId){
        Map<String, Object> map = new HashMap<>();
        map.put("activityName",activityName);
        map.put("clueId",clueId);

        return activityService.queryActivityForConvertByNameClueId(map);
    }

    @RequestMapping("/convertClue.do")
    @ResponseBody
    public Object convertClue(String clueId,String money,String name,String expectedDate,
                              String stage,String activityId,String isCreateTran,HttpSession session){

        ReturnObject returnObject = new ReturnObject();

//        封装参数
        Map<String, Object> map = new HashMap<>();
        map.put("clueId",clueId);
        map.put("money",money);
        map.put("name",name);
        map.put("expectedDate",expectedDate);
        map.put("stage",stage);
        map.put("activityId",activityId);
        map.put("isCreateTran",isCreateTran);
        map.put(Constant.SESSION_USER,session.getAttribute(Constant.SESSION_USER));

        try {
            clueService.saveConvertClue(map);

            returnObject.setCode(Constant.RETURN_OBJECT_CODE_SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
            returnObject.setCode(Constant.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMessage("系统忙，请稍后重试...");
        }

        return returnObject;
    }


}
