package com.waves.crm.settings.service.impl;

import com.waves.crm.settings.domain.DicType;
import com.waves.crm.settings.mapper.DicTypeMapper;
import com.waves.crm.settings.service.DicTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author huangWenTao
 * @desc
 * @date 2022/7/3 22:39
 */
@Service("dicTypeService")
public class DicTypeServiceImpl implements DicTypeService {

    @Autowired
    private DicTypeMapper dicTypeMapper;

    @Override
    public List<DicType> queryAllDicType() {
        return dicTypeMapper.selectAllDicType();
    }

    @Override
    public int saveDicType(DicType dicType) {
        return dicTypeMapper.insertDicType(dicType);
    }

    @Override
    public int deleteDicTypeByCodes(String[] codes) {
        return dicTypeMapper.deleteDicTypeByCodes(codes);
    }

    @Override
    public int editDicType(Map<String,Object> map) {
        return dicTypeMapper.updateDicType(map);
    }

    @Override
    public DicType queryDicTypeByCode(String code) {
        return dicTypeMapper.selectDicTypeByCode(code);
    }
}
