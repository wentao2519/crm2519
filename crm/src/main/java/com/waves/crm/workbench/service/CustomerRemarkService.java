package com.waves.crm.workbench.service;

import com.waves.crm.workbench.domain.CustomerRemark;

import java.util.List;

/**
 * @author huangWenTao
 * @desc
 * @date 2022/7/27 18:36
 */
public interface CustomerRemarkService {

    List<CustomerRemark> queryCustomerRemarkDetailInfoByCustomerId(String customerId);

    int saveCreateCustomerRemark(CustomerRemark customerRemark);


    int deleteCustomerRemarkById(String id);

    int saveEditCustomerRemark(CustomerRemark customerRemark);
}
