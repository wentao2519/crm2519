package com.waves.crm.workbench.service;

import com.waves.crm.workbench.domain.Clue;
import com.waves.crm.workbench.domain.ClueRemark;

import java.util.List;

/**
 * @author huangWenTao
 * @desc
 * @date 2022/7/16 19:07
 */
public interface ClueRemarkService {

    List<ClueRemark> queryClueRemarkForDetailByClueId(String clueId);

    int saveCreateClueRemark(ClueRemark clueRemark);

    int deleteClueRemarkById(String id);

    int saveEditClueRemark(ClueRemark clueRemark);
}
