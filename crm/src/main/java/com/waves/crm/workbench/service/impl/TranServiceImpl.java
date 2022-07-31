package com.waves.crm.workbench.service.impl;

import com.waves.crm.commons.constants.Constant;
import com.waves.crm.commons.utils.DateUtil;
import com.waves.crm.commons.utils.UUIDUtil;
import com.waves.crm.settings.domain.User;
import com.waves.crm.workbench.domain.Customer;
import com.waves.crm.workbench.domain.Tran;
import com.waves.crm.workbench.mapper.CustomerMapper;
import com.waves.crm.workbench.mapper.TranMapper;
import com.waves.crm.workbench.service.TranService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author huangWenTao
 * @desc
 * @date 2022/7/27 19:32
 */
@Service("tranService")
public class TranServiceImpl implements TranService {
    @Autowired
    private CustomerMapper customerMapper;

    @Autowired
    private TranMapper tranMapper;

    @Override
    public List<Tran> queryTranByCustomerId(String customerId) {
        return tranMapper.selectTranByCustomerId(customerId);
    }

    @Override
    public void saveCreateTran(Map<String, Object> map) {
        // 根据name精确查询客户
        String customerName = (String) map.get("customerName");
        User user = (User) map.get(Constant.SESSION_USER);
        Customer customer = customerMapper.selectCustomerByName(customerName);

        // 客户不存在 创建新客户
        if (customer==null){
            customer=new Customer();
            customer.setId(UUIDUtil.getUUID());
            customer.setCreateBy(user.getId());
            customer.setCreateTime(DateUtil.formatDateTime(new Date()));
            customer.setName(customerName);
            customer.setOwner(user.getId());
            customerMapper.insertCustomer(customer);
        }
        // 客户存在 保存创建的交易
        Tran tran = new Tran();
        tran.setStage((String) map.get("stage"));
        tran.setOwner((String) map.get("owner"));
        tran.setNextContactTime((String) map.get("nextContactTime"));
        tran.setName((String) map.get("name"));
        tran.setMoney((String) map.get("money"));
        tran.setId(UUIDUtil.getUUID());
        tran.setExpectedDate((String) map.get("expectedDate"));
        tran.setCustomerId(customer.getId());
        tran.setCreateBy(user.getId());
        tran.setCreateTime(DateUtil.formatDateTime(new Date()));
        tran.setContactSummary((String) map.get("contactSummary"));
        tran.setContactsId((String) map.get("contactsId"));
        tran.setActivityId((String) map.get("activityId"));
        tran.setDescription((String) map.get("description"));
        tran.setSource((String) map.get("source"));
        tran.setType((String) map.get("type"));

        tranMapper.insertTran(tran);
    }
}
