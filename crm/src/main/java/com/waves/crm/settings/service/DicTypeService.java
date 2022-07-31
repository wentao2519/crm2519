package com.waves.crm.settings.service;

import com.waves.crm.settings.domain.DicType;

import java.util.List;
import java.util.Map;

/**
 * @author huangWenTao
 * @desc
 * @date 2022/7/3 22:38
 */
public interface DicTypeService {

    List<DicType> queryAllDicType();

    int saveDicType(DicType dicType);

    int deleteDicTypeByCodes(String[] codes);

    int editDicType(Map<String,Object> map);

    DicType queryDicTypeByCode(String code);
}
