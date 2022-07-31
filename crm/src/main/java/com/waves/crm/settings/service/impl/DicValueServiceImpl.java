package com.waves.crm.settings.service.impl;

import com.waves.crm.settings.domain.DicValue;
import com.waves.crm.settings.mapper.DicValueMapper;
import com.waves.crm.settings.service.DicValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author huangWenTao
 * @desc
 * @date 2022/7/3 23:15
 */
@Service("dicValueService")
public class DicValueServiceImpl implements DicValueService {
    @Autowired
    private DicValueMapper dicValueMapper;


    @Override
    public List<DicValue> queryAllDicValue() {
        return dicValueMapper.selectAllDicValue();
    }

    @Override
    public List<DicValue> queryDicValueByTypeCode(String typeCode) {
        return dicValueMapper.selectDicValueByTypeCode(typeCode);
    }
}
