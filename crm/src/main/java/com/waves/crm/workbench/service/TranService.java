package com.waves.crm.workbench.service;

import com.waves.crm.workbench.domain.Tran;

import java.util.List;
import java.util.Map;

/**
 * @author huangWenTao
 * @desc
 * @date 2022/7/27 19:31
 */
public interface TranService {

    List<Tran> queryTranByCustomerId(String customerId);

    void saveCreateTran(Map<String, Object> map);
}
