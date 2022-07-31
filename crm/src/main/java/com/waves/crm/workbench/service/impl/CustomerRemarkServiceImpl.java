package com.waves.crm.workbench.service.impl;

import com.waves.crm.workbench.domain.CustomerRemark;
import com.waves.crm.workbench.mapper.CustomerRemarkMapper;
import com.waves.crm.workbench.service.CustomerRemarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author huangWenTao
 * @desc
 * @date 2022/7/27 18:37
 */
@Service("customerRemarkService")
public class CustomerRemarkServiceImpl implements CustomerRemarkService {

    @Autowired
    private CustomerRemarkMapper customerRemarkMapper;

    @Override
    public List<CustomerRemark> queryCustomerRemarkDetailInfoByCustomerId(String customerId) {
        return customerRemarkMapper.selectCustomerRemarkDetailInfoByCustomerId(customerId);
    }

    @Override
    public int saveCreateCustomerRemark(CustomerRemark customerRemark) {
        return customerRemarkMapper.insertCustomerRemark(customerRemark);
    }

    @Override
    public int deleteCustomerRemarkById(String id) {
        return customerRemarkMapper.deleteCustomerRemarkById(id);
    }

    @Override
    public int saveEditCustomerRemark(CustomerRemark customerRemark) {
        return customerRemarkMapper.updateCustomerRemarkById(customerRemark);
    }
}
