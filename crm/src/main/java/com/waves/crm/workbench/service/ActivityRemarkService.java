package com.waves.crm.workbench.service;

import com.waves.crm.workbench.domain.ActivityRemark;

import java.util.List;

/**
 * @author huangWenTao
 * @desc
 * @date 2022/7/10 17:54
 */
public interface ActivityRemarkService {
    List<ActivityRemark> queryActivityRemarkForDetailByActivityId(String activityId);

    int saveCreateActivityRemark(ActivityRemark activityRemark);

    int deleteActivityRemarkById(String id);

    int saveEditActivityRemark(ActivityRemark activityRemark);
}
