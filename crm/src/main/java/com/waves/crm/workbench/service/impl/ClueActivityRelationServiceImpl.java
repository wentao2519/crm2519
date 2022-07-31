package com.waves.crm.workbench.service.impl;

import com.waves.crm.workbench.domain.ClueActivityRelation;
import com.waves.crm.workbench.mapper.ClueActivityRelationMapper;
import com.waves.crm.workbench.service.ClueActivityRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author huangWenTao
 * @desc
 * @date 2022/7/17 21:21
 */
@Service("clueActivityRelationService")
public class ClueActivityRelationServiceImpl implements ClueActivityRelationService {

    @Autowired
    private ClueActivityRelationMapper clueActivityRelationMapper;
    @Override
    public int saveCreateClueActivityRelationByList(List<ClueActivityRelation> clueActivityRelationList) {

        return clueActivityRelationMapper.insertClueActivityRelationByList(clueActivityRelationList);
    }

    @Override
    public int deleteClueActivityRelationByClueIdActivityId(ClueActivityRelation clueActivityRelation) {
        return clueActivityRelationMapper.deleteClueActivityRelationByClueIdActivityId(clueActivityRelation);
    }
}
