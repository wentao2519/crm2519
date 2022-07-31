package com.waves.crm.workbench.mapper;

import com.waves.crm.workbench.domain.Contacts;

import java.util.List;

public interface ContactsMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_contacts
     *
     * @mbggenerated Sat Jul 23 20:27:11 GMT+08:00 2022
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_contacts
     *
     * @mbggenerated Sat Jul 23 20:27:11 GMT+08:00 2022
     */
    int insert(Contacts record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_contacts
     *
     * @mbggenerated Sat Jul 23 20:27:11 GMT+08:00 2022
     */
    int insertSelective(Contacts record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_contacts
     *
     * @mbggenerated Sat Jul 23 20:27:11 GMT+08:00 2022
     */
    Contacts selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_contacts
     *
     * @mbggenerated Sat Jul 23 20:27:11 GMT+08:00 2022
     */
    int updateByPrimaryKeySelective(Contacts record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_contacts
     *
     * @mbggenerated Sat Jul 23 20:27:11 GMT+08:00 2022
     */
    int updateByPrimaryKey(Contacts record);

    int insertContacts(Contacts contacts);

    /**
     * 根据客户id查询联系人
     * @param customerId
     * @return
     */
    List<Contacts> selectContactsByCustomerId(String customerId);

    /**
     * 根据联系人姓名 模糊查询
     * @param fullname
     * @return
     */
    List<Contacts> selectContactsByFullName(String fullname);

}