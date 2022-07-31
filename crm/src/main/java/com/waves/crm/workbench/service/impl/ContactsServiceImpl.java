package com.waves.crm.workbench.service.impl;

import com.waves.crm.workbench.domain.Contacts;
import com.waves.crm.workbench.mapper.ContactsMapper;
import com.waves.crm.workbench.service.ContactsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author huangWenTao
 * @desc
 * @date 2022/7/27 19:38
 */
@Service("contactsService")
public class ContactsServiceImpl implements ContactsService {

    @Autowired
    private ContactsMapper contactsMapper;

    @Override
    public List<Contacts> queryContactsByCustomerId(String customerId) {
        return contactsMapper.selectContactsByCustomerId(customerId);
    }

    @Override
    public List<Contacts> queryContactsByFullName(String fullname) {
        return contactsMapper.selectContactsByFullName(fullname);
    }
}
