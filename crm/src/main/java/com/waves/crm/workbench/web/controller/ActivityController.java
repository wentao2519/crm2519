package com.waves.crm.workbench.web.controller;

import com.waves.crm.commons.constants.Constant;
import com.waves.crm.commons.domain.ReturnObject;
import com.waves.crm.commons.utils.DateUtil;
import com.waves.crm.commons.utils.HSSFUtil;
import com.waves.crm.commons.utils.UUIDUtil;
import com.waves.crm.settings.domain.User;
import com.waves.crm.settings.service.UserService;
import com.waves.crm.workbench.domain.Activity;
import com.waves.crm.workbench.domain.ActivityRemark;
import com.waves.crm.workbench.service.ActivityRemarkService;
import com.waves.crm.workbench.service.ActivityService;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.*;

/**
 * @author huangWenTao
 * @desc
 * @date 2022/6/8 11:29
 */
@Controller
@RequestMapping("/workbench/activity")
public class ActivityController {

    @Autowired
    private UserService userService;

    @Autowired
    private ActivityService activityService;

    @Autowired
    private ActivityRemarkService activityRemarkService;

    @RequestMapping("/index.do")
    public String index(HttpServletRequest request) {
        List<User> userList = userService.queryAllUsers();
        request.setAttribute("userList", userList);
        return "workbench/activity/index";
    }

    @RequestMapping("/saveCreateActivity.do")
    @ResponseBody
    public Object saveCreateActivity(Activity activity, HttpSession session) {
        User user = (User) session.getAttribute(Constant.SESSION_USER);
        activity.setId(UUIDUtil.getUUID());
        activity.setCreateTime(DateUtil.formatDateTime(new Date()));
        activity.setCreateBy(user.getId());

        ReturnObject returnObject = new ReturnObject();

        try {
            int ret = activityService.saveCreateActivity(activity);
            if (ret > 0) {
                returnObject.setCode(Constant.RETURN_OBJECT_CODE_SUCCESS);
            } else {
                returnObject.setCode(Constant.RETURN_OBJECT_CODE_FAIL);
                returnObject.setMessage("??????????????????????????????...");
            }
        } catch (Exception e) {
            e.printStackTrace();
            returnObject.setCode(Constant.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMessage("??????????????????????????????...");
        }

        return returnObject;
    }

    @RequestMapping("/queryActivityByConditionForPage.do")
    @ResponseBody
    public Object queryActivityByConditionForPage(String name, String owner, String startDate, String endDate,
                                                  int pageNo, int pageSize) {

        //????????????
        Map<String, Object> map = new HashMap<>();
        map.put("name", name);
        map.put("owner", owner);
        map.put("startDate", startDate);
        map.put("endDate", endDate);
        map.put("beginNo", (pageNo - 1) * pageSize);
        map.put("pageSize", pageSize);
        //??????service????????????????????????
        List<Activity> activityList = activityService.queryActivityByConditionForPage(map);
        int totalRows = activityService.queryCountOfActivityByCondition(map);
        //?????????????????????????????????????????????
        Map<String, Object> retMap = new HashMap<>();
        retMap.put("activityList", activityList);
        retMap.put("totalRows", totalRows);
        return retMap;
    }

    @RequestMapping("/deleteActivityByIds.do")
    @ResponseBody
    public Object deleteActivityByIds(String[] id) {
        ReturnObject returnObject = new ReturnObject();
        try {
            int ret = activityService.deleteActivityByIds(id);
            if (ret > 0) {
                returnObject.setCode(Constant.RETURN_OBJECT_CODE_SUCCESS);
            } else {
                returnObject.setCode(Constant.RETURN_OBJECT_CODE_FAIL);
                returnObject.setMessage("???????????????????????????...");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnObject;
    }

    @RequestMapping("/queryActivityById.do")
    @ResponseBody
    public Object queryActivityById(String id) {
        return activityService.queryActivityById(id);
    }

    @RequestMapping("/saveEditActivity.do")
    @ResponseBody
    public Object saveEditActivity(Activity activity, HttpSession session) {

        activity.setEditTime(DateUtil.formatDateTime(new Date()));
        User user = (User) session.getAttribute(Constant.SESSION_USER);
        activity.setEditBy(user.getId());
        ReturnObject returnObject = new ReturnObject();
        try {
            int ret = activityService.saveEditActivity(activity);
            if (ret > 0) {
                returnObject.setCode(Constant.RETURN_OBJECT_CODE_SUCCESS);
            } else {
                returnObject.setCode(Constant.RETURN_OBJECT_CODE_FAIL);
                returnObject.setMessage("???????????????????????????...");
            }
        } catch (Exception e) {
            e.printStackTrace();
            returnObject.setCode(Constant.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMessage("???????????????????????????...");
        }
        return returnObject;
    }

    @RequestMapping("/exportAllActivity.do")
    public void exportAllActivity(HttpServletResponse response) throws Exception {
        // ??????service ????????????????????????
        List<Activity> activityList = activityService.queryAllActivity();
        // ??????excel?????? ???activityList??????excel?????????
        HSSFWorkbook workbook = new HSSFWorkbook();
        // ???
        HSSFSheet sheet = workbook.createSheet("??????????????????");
        // ???
        HSSFRow row = sheet.createRow(0);
        // ???
        HSSFCell cell = row.createCell(0);
        cell.setCellValue("ID");
        cell = row.createCell(1);
        cell.setCellValue("?????????");
        cell = row.createCell(2);
        cell.setCellValue("??????");
        cell = row.createCell(3);
        cell.setCellValue("????????????");
        cell = row.createCell(4);
        cell.setCellValue("????????????");
        cell = row.createCell(5);
        cell.setCellValue("??????");
        cell = row.createCell(6);
        cell.setCellValue("??????");
        cell = row.createCell(7);
        cell.setCellValue("????????????");
        cell = row.createCell(8);
        cell.setCellValue("?????????");
        cell = row.createCell(9);
        cell.setCellValue("????????????");
        cell = row.createCell(10);
        cell.setCellValue("?????????");

        //?????? activityList ??????HSSFRow?????? ?????????????????????

        if (activityList != null && activityList.size() > 0) {
            Activity activity = null;
            for (int i = 0; i < activityList.size(); i++) {
                activity = activityList.get(i);
                // ??????????????????activity????????????
                row = sheet.createRow(i + 1);

                cell = row.createCell(0);
                cell.setCellValue(activity.getId());
                cell = row.createCell(1);
                cell.setCellValue(activity.getOwner());
                cell = row.createCell(2);
                cell.setCellValue(activity.getName());
                cell = row.createCell(3);
                cell.setCellValue(activity.getStartDate());
                cell = row.createCell(4);
                cell.setCellValue(activity.getEndDate());
                cell = row.createCell(5);
                cell.setCellValue(activity.getCost());
                cell = row.createCell(6);
                cell.setCellValue(activity.getDescription());
                cell = row.createCell(7);
                cell.setCellValue(activity.getCreateTime());
                cell = row.createCell(8);
                cell.setCellValue(activity.getCreateBy());
                cell = row.createCell(9);
                cell.setCellValue(activity.getEditTime());
                cell = row.createCell(10);
                cell.setCellValue(activity.getEditBy());

            }
        }
        // ??????workbook?????? ??????excel??????
        /*OutputStream outputStream = new FileOutputStream("D:\\CRM??????-ssm\\serverDir\\activityList.xls");
        workbook.write(outputStream);*/  // ???1??? ??????---> ??????
        // ????????????
        /*outputStream.close();
        workbook.close();*/
        // ?????????????????? ??????????????????
        response.setContentType("application/octet-stream;charset=UTF-8");
        response.addHeader("Content-Disposition", "attachment;filename=activityList.xls");
        OutputStream out = response.getOutputStream();
        /*
        InputStream in = new FileInputStream("D:\\CRM??????-ssm\\serverDir\\activityList.xls");
        byte[] buff = new byte[256];
        int length = 0;
        while ((length = in.read(buff))!=-1){
            out.write(buff,0,length); // ???2????????? ---> ??????
        }
        in.close();*/
        workbook.write(out); // {1} ?????? ??????---> ??????
        workbook.close();
        out.flush();
    }

    @RequestMapping("/exportAllActivityByIds.do")
    public void exportAllActivityByIds(String[] id, HttpServletResponse response) throws Exception {
        List<Activity> activityList = activityService.queryAllActivityByIds(id);
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("??????????????????");
        HSSFRow row = sheet.createRow(0);
        HSSFCell cell = row.createCell(0);
        cell.setCellValue("ID");
        cell = row.createCell(1);
        cell.setCellValue("?????????");
        cell = row.createCell(2);
        cell.setCellValue("??????");
        cell = row.createCell(3);
        cell.setCellValue("????????????");
        cell = row.createCell(4);
        cell.setCellValue("????????????");
        cell = row.createCell(5);
        cell.setCellValue("??????");
        cell = row.createCell(6);
        cell.setCellValue("??????");
        cell = row.createCell(7);
        cell.setCellValue("????????????");
        cell = row.createCell(8);
        cell.setCellValue("?????????");
        cell = row.createCell(9);
        cell.setCellValue("????????????");
        cell = row.createCell(10);
        cell.setCellValue("?????????");

        //?????? activityList ??????HSSFRow?????? ?????????????????????
        if (activityList != null && activityList.size() > 0) {
            Activity activity = null;
            for (int i = 0; i < activityList.size(); i++) {
                activity = activityList.get(i);
                // ??????????????????activity????????????
                row = sheet.createRow(i + 1);

                cell = row.createCell(0);
                cell.setCellValue(activity.getId());
                cell = row.createCell(1);
                cell.setCellValue(activity.getOwner());
                cell = row.createCell(2);
                cell.setCellValue(activity.getName());
                cell = row.createCell(3);
                cell.setCellValue(activity.getStartDate());
                cell = row.createCell(4);
                cell.setCellValue(activity.getEndDate());
                cell = row.createCell(5);
                cell.setCellValue(activity.getCost());
                cell = row.createCell(6);
                cell.setCellValue(activity.getDescription());
                cell = row.createCell(7);
                cell.setCellValue(activity.getCreateTime());
                cell = row.createCell(8);
                cell.setCellValue(activity.getCreateBy());
                cell = row.createCell(9);
                cell.setCellValue(activity.getEditTime());
                cell = row.createCell(10);
                cell.setCellValue(activity.getEditBy());

            }
        }
        // ?????????????????? ??????????????????
        response.setContentType("application/octet-stream;charset=UTF-8");
        response.addHeader("Content-Disposition", "attachment;filename=activityList.xls");
        OutputStream out = response.getOutputStream();
        workbook.write(out);
        workbook.close();
        out.flush();
    }


    @RequestMapping("importActivity.do")
    @ResponseBody
    public Object importActivity(MultipartFile activityFile,HttpSession session) {
        ReturnObject returnObject = new ReturnObject();
        User user = (User) session.getAttribute(Constant.SESSION_USER);
        try {
            // ???excel???????????????????????????
           /* String originalFilename = activityFile.getOriginalFilename();
            File file = new File("D:\\CRM??????-ssm\\serverDir", originalFilename);
            activityFile.transferTo(file);*/

            // ??????excel?????? ????????????????????? ??????activityList
//            InputStream inputStream = new FileInputStream("D:\\CRM??????-ssm\\serverDir\\" + originalFilename);
            //??????excel????????????HSSFWorkbook??????????????????excel?????????????????????
            InputStream inputStream = activityFile.getInputStream();//???????????????????????????
            HSSFWorkbook workbook = new HSSFWorkbook(inputStream);
            //??????wb??????HSSFSheet???????????????????????????????????????
            HSSFSheet sheet = workbook.getSheetAt(0);//????????????????????????0?????????????????????
            //??????sheet??????HSSFRow???????????????????????????????????????
            HSSFRow row = null;
            //??????sheet??????HSSFCell???????????????????????????????????????
            HSSFCell cell = null;
            Activity activity = null;
            List<Activity> activityList = new ArrayList<>();
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {//sheet.getLastRowNum()????????????????????????
                row = sheet.getRow(i);//????????????????????????0?????????????????????
                activity = new Activity();
                activity.setId(UUIDUtil.getUUID());
                activity.setOwner(user.getId());
                activity.setCreateTime(DateUtil.formatDateTime(new Date()));
                activity.setCreateBy(user.getId());
                for (int j = 0; j < row.getLastCellNum(); j++) {//row.getLastCellNum():?????????????????????+1
                    cell = row.getCell(j);//????????????????????????0?????????????????????
                    String cellValue = HSSFUtil.getCellValueForStr(cell);
                    if (j==0){
                        activity.setName(cellValue);
                    } else if (j==1) {
                        activity.setStartDate(cellValue);
                    } else if (j==2) {
                        activity.setEndDate(cellValue);
                    } else if (j==3) {
                        activity.setCost(cellValue);
                    } else if (j==4) {
                        activity.setDescription(cellValue);
                    }
                }
                activityList.add(activity);
            }
            int ret = activityService.saveCreateActivityByList(activityList);
            returnObject.setCode(Constant.RETURN_OBJECT_CODE_SUCCESS);
            returnObject.setMessage("???????????????????????????"+ret+"????????????");
            returnObject.setReturnData(ret);
        } catch (Exception e) {
            e.printStackTrace();
            returnObject.setCode(Constant.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMessage("??????????????????????????????");
        }
        return returnObject;
    }

    @RequestMapping("/detailActivity.do")
    public String detailActivity(String id,HttpServletRequest request){
        Activity activity = activityService.queryActivityForDetailById(id);
        List<ActivityRemark> activityRemarkList = activityRemarkService.queryActivityRemarkForDetailByActivityId(id);

        request.setAttribute("activity",activity);
        request.setAttribute("remarkList",activityRemarkList);

        return "workbench/activity/detail";
    }


    @RequestMapping("/saveCreateActivityRemark.do")
    @ResponseBody
    public Object saveCreateActivityRemark(ActivityRemark activityRemark,HttpSession session){
        User user = (User) session.getAttribute(Constant.SESSION_USER);
        // ????????????
        activityRemark.setId(UUIDUtil.getUUID());
        activityRemark.setCreateTime(DateUtil.formatDateTime(new Date()));
        activityRemark.setCreateBy(user.getId());
        activityRemark.setEditFlag(Constant.REMARK_EDIT_FLAG_NO_EDITED);

        ReturnObject returnObject = new ReturnObject();
        try {
            int ret = activityRemarkService.saveCreateActivityRemark(activityRemark);
            if (ret>0){
                returnObject.setCode(Constant.RETURN_OBJECT_CODE_SUCCESS);
                returnObject.setReturnData(activityRemark);
            }else {
                returnObject.setCode(Constant.RETURN_OBJECT_CODE_FAIL);
                returnObject.setMessage("???????????????????????????...");
            }

        }catch (Exception e){
            e.printStackTrace();

            returnObject.setCode(Constant.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMessage("???????????????????????????...");
        }
        return returnObject;
    }

    @RequestMapping("/deleteActivityRemarkById.do")
    @ResponseBody
    public Object deleteActivityRemarkById(String id){
        ReturnObject returnObject = new ReturnObject();
        try {
            int ret = activityRemarkService.deleteActivityRemarkById(id);
            if (ret>0){
                returnObject.setCode(Constant.RETURN_OBJECT_CODE_SUCCESS);
            }else {
                returnObject.setCode(Constant.RETURN_OBJECT_CODE_FAIL);
                returnObject.setMessage("???????????????????????????...");
            }
        }catch (Exception e){
            e.printStackTrace();
            returnObject.setCode(Constant.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMessage("???????????????????????????...");
        }
        return returnObject;
    }

    @RequestMapping("/saveEditActivityRemark.do")
    @ResponseBody
    public Object saveEditActivityRemark(ActivityRemark activityRemark,HttpSession session){
        ReturnObject returnObject = new ReturnObject();

        User user = (User) session.getAttribute(Constant.SESSION_USER);
        activityRemark.setEditFlag(Constant.REMARK_EDIT_FLAG_YES_EDITED);
        activityRemark.setEditBy(user.getId());
        activityRemark.setEditTime(DateUtil.formatDateTime(new Date()));

        try {
            //??????service?????????????????????????????????????????????
            int ret = activityRemarkService.saveEditActivityRemark(activityRemark);

            if(ret>0){
                returnObject.setCode(Constant.RETURN_OBJECT_CODE_SUCCESS);
                returnObject.setReturnData(activityRemark);
            }else{
                returnObject.setCode(Constant.RETURN_OBJECT_CODE_FAIL);
                returnObject.setMessage("???????????????????????????....");
            }
        }catch (Exception e){
            e.printStackTrace();
            returnObject.setCode(Constant.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMessage("???????????????????????????....");
        }
        return returnObject;
    }

}
