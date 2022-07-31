package com.waves.crm.workbench.service;

import com.waves.crm.workbench.domain.Customer;

import java.util.List;
import java.util.Map;

/**
 * @author huangWenTao
 * @desc
 * @date 2022/7/25 15:53
 */
public interface CustomerService {

    int saveCreateCustomer(Customer customer);

    List<Customer> queryCustomerByConditionForPage(Map<String, Object> map);

    int queryCountOfCustomerByCondition(Map<String, Object> map);

    int deleteCustomerByIds(String[] id);

    Customer queryCustomerById(String id);

    int saveEditCustomer(Customer customer);

    Customer queryCustomerDetailInfoById(String id);

    List<String> queryCustomerNameByName(String name);

}
