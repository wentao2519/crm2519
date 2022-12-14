package com.waves.crm.workbench.service.impl;

import com.waves.crm.commons.constants.Constant;
import com.waves.crm.commons.utils.DateUtil;
import com.waves.crm.commons.utils.UUIDUtil;
import com.waves.crm.settings.domain.User;
import com.waves.crm.workbench.domain.*;
import com.waves.crm.workbench.mapper.*;
import com.waves.crm.workbench.service.ClueService;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author huangWenTao
 * @desc
 * @date 2022/7/14 21:54
 */
@Service("clueService")
public class ClueServiceImpl implements ClueService {

    @Autowired
    private ClueMapper clueMapper;

    @Autowired
    private CustomerMapper customerMapper;

    @Autowired
    private ContactsMapper contactsMapper;

    @Autowired
    private ClueRemarkMapper clueRemarkMapper;

    @Autowired
    private CustomerRemarkMapper customerRemarkMapper;

    @Autowired
    private ContactsRemarkMapper contactsRemarkMapper;

    @Autowired
    private ClueActivityRelationMapper clueActivityRelationMapper;

    @Autowired
    private ContactsActivityRelationMapper contactsActivityRelationMapper;

    @Autowired
    private TranMapper tranMapper;

    @Autowired
    private TranRemarkMapper tranRemarkMapper;

    @Override
    public int saveCreateClue(Clue clue) {
        return clueMapper.insertClue(clue);
    }

    @Override
    public List<Clue> queryClueByConditionForPage(Map<String, Object> map) {
        return clueMapper.selectClueByConditionForPage(map);
    }

    @Override
    public int queryCountOfClueByCondition(Map<String, Object> map) {
        return clueMapper.selectCountOfClueByCondition(map);
    }

    @Override
    public Clue queryClueForDetailById(String id) {
        return clueMapper.selectClueForDetailById(id);
    }

    @Override
    public int deleteClueByIds(String[] ids) {
        return clueMapper.deleteClueByIds(ids);
    }

    @Override
    public Clue queryClueById(String id) {
        return clueMapper.selectClueById(id);
    }

    @Override
    public int saveEditClue(Clue clue) {
        return clueMapper.updateClue(clue);
    }

    @Override
    public void saveConvertClue(Map<String, Object> map) {
        String clueId = (String) map.get("clueId");
        User user = (User) map.get(Constant.SESSION_USER);
        // ??????????????????
        String isCreateTran = (String) map.get("isCreateTran");
        // ??????id??????????????????
        Clue clue = clueMapper.selectClueInfoById(clueId);
        // ??????????????????????????????????????????????????????
        Customer customer = new Customer();
        customer.setId(UUIDUtil.getUUID());
        customer.setAddress(clue.getAddress());
        customer.setContactSummary(clue.getContactSummary());
        customer.setCreateBy(user.getId());
        customer.setCreateTime(DateUtil.formatDateTime(new Date()));
        customer.setDescription(clue.getDescription());
        customer.setName(clue.getCompany());
        customer.setNextContactTime(clue.getNextContactTime());
        customer.setOwner(user.getId());
        customer.setPhone(clue.getPhone());
        customer.setWebsite(clue.getWebsite());
        customerMapper.insertCustomer(customer);

        // ?????????????????????????????????????????????????????????
        Contacts contacts = new Contacts();
        contacts.setId(UUIDUtil.getUUID());
        contacts.setAddress(clue.getAddress());
        contacts.setContactSummary(clue.getContactSummary());
        contacts.setCreateBy(user.getId());
        contacts.setCreateTime(DateUtil.formatDateTime(new Date()));
        contacts.setAppellation(clue.getAppellation());
        contacts.setCustomerId(customer.getId());
        contacts.setEmail(clue.getEmail());
        contacts.setDescription(clue.getDescription());
        contacts.setFullname(clue.getFullname());
        contacts.setJob(clue.getJob());
        contacts.setMphone(clue.getMphone());
        contacts.setNextContactTime(clue.getNextContactTime());
        contacts.setOwner(user.getId());
        contacts.setSource(clue.getSource());
        contactsMapper.insertContacts(contacts);

        //??????clueId??????????????????????????????
        List<ClueRemark> clueRemarkList = clueRemarkMapper.selectClueRemarkByClueId(clueId);

        // ???????????????????????????????????????????????????
        // ??????????????????????????????????????????????????????

        if (clueRemarkList != null && clueRemarkList.size() > 0) {
            CustomerRemark customerRemark = null;
            ContactsRemark contactsRemark = null;
            List<CustomerRemark> customerRemarkList = new ArrayList<>();
            List<ContactsRemark> contactsRemarkList = new ArrayList<>();
            for (ClueRemark clueRemark : clueRemarkList) {
                customerRemark = new CustomerRemark();
                customerRemark.setCreateBy(clueRemark.getCreateBy());
                customerRemark.setCustomerId(customer.getId());
                customerRemark.setCreateTime(clueRemark.getCreateTime());
                customerRemark.setId(UUIDUtil.getUUID());
                customerRemark.setEditBy(clueRemark.getEditBy());
                customerRemark.setEditFlag(clueRemark.getEditFlag());
                customerRemark.setEditTime(clueRemark.getEditTime());
                customerRemark.setNoteContent(clueRemark.getNoteContent());
                customerRemarkList.add(customerRemark);

                contactsRemark = new ContactsRemark();
                contactsRemark.setCreateBy(clueRemark.getCreateBy());
                contactsRemark.setContactsId(contacts.getId());
                contactsRemark.setCreateTime(clueRemark.getCreateTime());
                contactsRemark.setId(UUIDUtil.getUUID());
                contactsRemark.setEditBy(clueRemark.getEditBy());
                contactsRemark.setEditFlag(clueRemark.getEditFlag());
                contactsRemark.setEditTime(clueRemark.getEditTime());
                contactsRemark.setNoteContent(clueRemark.getNoteContent());
                contactsRemarkList.add(contactsRemark);
            }

            customerRemarkMapper.insertCustomerRemarkByList(customerRemarkList);
            contactsRemarkMapper.insertContactsRemarkByList(contactsRemarkList);
        }
        List<ClueActivityRelation> clueActivityRelationList = clueActivityRelationMapper.selectClueActivityRelationByClueId(clueId);

        if (clueActivityRelationList != null && clueActivityRelationList.size() > 0) {
            ContactsActivityRelation contactsActivityRelation = null;
            List<ContactsActivityRelation> contactsActivityRelationList = new ArrayList<>();
            for (ClueActivityRelation clueActivityRelation : clueActivityRelationList) {
                contactsActivityRelation = new ContactsActivityRelation();
                contactsActivityRelation.setId(UUIDUtil.getUUID());
                contactsActivityRelation.setContactsId(contacts.getId());
                contactsActivityRelation.setActivityId(clueActivityRelation.getActivityId());
                contactsActivityRelationList.add(contactsActivityRelation);
            }
            //     ?????????????????????????????????????????????????????????????????????????????????????????????
            contactsActivityRelationMapper.insertContactsActivityRelationByList(contactsActivityRelationList);
        }


        //	    ????????????????????????,???????????????????????????????????????
        if ("true".equals(isCreateTran)) {
            Tran tran = new Tran();
            tran.setId(UUIDUtil.getUUID());
            tran.setOwner(user.getId());
            tran.setMoney((String) map.get("money"));
            tran.setName((String) map.get("name"));
            tran.setExpectedDate((String) map.get("expectedDate"));
            tran.setCustomerId(customer.getId());
            tran.setStage((String) map.get("stage"));
            tran.setActivityId((String) map.get("activityId"));
            tran.setContactsId(contacts.getId());
            tran.setCreateBy(user.getId());
            tran.setCreateTime(DateUtil.formatDateTime(new Date()));
            tranMapper.insertTran(tran);

            //	    ????????????????????????,???????????????????????????????????????????????????????????????
            if (clueRemarkList != null && clueRemarkList.size() > 0) {
                TranRemark tranRemark = null;
                List<TranRemark> tranRemarkList = new ArrayList<>();
                for (ClueRemark clueRemark : clueRemarkList) {
                    tranRemark = new TranRemark();
                    tranRemark.setCreateBy(clueRemark.getCreateBy());
                    tranRemark.setTranId(tran.getId());
                    tranRemark.setCreateTime(clueRemark.getCreateTime());
                    tranRemark.setId(UUIDUtil.getUUID());
                    tranRemark.setEditBy(clueRemark.getEditBy());
                    tranRemark.setEditFlag(clueRemark.getEditFlag());
                    tranRemark.setEditTime(clueRemark.getEditTime());
                    tranRemark.setNoteContent(clueRemark.getNoteContent());
                    tranRemarkList.add(tranRemark);
                }
                tranRemarkMapper.insertTranRemarkByList(tranRemarkList);
            }

        }
//        ?????????????????????
        clueRemarkMapper.deleteClueRemarkByClueId(clueId);
//        ??????????????????????????????????????????
        clueActivityRelationMapper.deleteClueActivityRelationByClueId(clueId);
//        ????????????
        clueMapper.deleteClueById(clueId);
    }


}
