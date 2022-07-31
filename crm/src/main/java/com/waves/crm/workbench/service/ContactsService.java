package com.waves.crm.workbench.service;

import com.waves.crm.workbench.domain.Contacts;

import java.util.List;

/**
 * @author huangWenTao
 * @desc
 * @date 2022/7/27 19:37
 */
public interface ContactsService {

    List<Contacts> queryContactsByCustomerId(String customerId);

    List<Contacts> queryContactsByFullName(String fullname);

}
