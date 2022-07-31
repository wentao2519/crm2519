package com.waves.crm.workbench.service;

import com.waves.crm.workbench.domain.Activity;
import com.waves.crm.workbench.domain.Clue;

import java.util.List;
import java.util.Map;

/**
 * @author huangWenTao
 * @desc
 * @date 2022/7/14 21:54
 */

public interface ClueService {

    int saveCreateClue(Clue clue);

    List<Clue> queryClueByConditionForPage(Map<String, Object> map);

    int queryCountOfClueByCondition(Map<String, Object> map);

    Clue queryClueForDetailById(String id);

    int deleteClueByIds(String[] ids);


    Clue queryClueById(String id);

    int saveEditClue(Clue clue);

    void saveConvertClue(Map<String, Object> map);

}
