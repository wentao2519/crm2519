package com.waves.crm.settings.service;

import com.waves.crm.settings.domain.DicValue;

import java.util.List;

/**
 * @author huangWenTao
 * @desc
 * @date 2022/7/3 23:14
 */
public interface DicValueService {

    List<DicValue> queryAllDicValue();

    List<DicValue> queryDicValueByTypeCode(String typeCode);
}
