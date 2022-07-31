package com.waves.crm.workbench.service;

import com.waves.crm.workbench.domain.ClueActivityRelation;

import java.util.List;


/**
 * @author huangWenTao
 * @desc
 * @date 2022/7/17 21:20
 */
public interface ClueActivityRelationService {

    int saveCreateClueActivityRelationByList(List<ClueActivityRelation> clueActivityRelationList);

    int deleteClueActivityRelationByClueIdActivityId(ClueActivityRelation clueActivityRelation);
}
