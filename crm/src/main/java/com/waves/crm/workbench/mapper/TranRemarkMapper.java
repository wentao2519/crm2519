package com.waves.crm.workbench.mapper;

import com.waves.crm.workbench.domain.TranRemark;

import java.util.List;

public interface TranRemarkMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_tran_remark
     *
     * @mbggenerated Sun Jul 24 17:40:21 GMT+08:00 2022
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_tran_remark
     *
     * @mbggenerated Sun Jul 24 17:40:21 GMT+08:00 2022
     */
    int insert(TranRemark record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_tran_remark
     *
     * @mbggenerated Sun Jul 24 17:40:21 GMT+08:00 2022
     */
    int insertSelective(TranRemark record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_tran_remark
     *
     * @mbggenerated Sun Jul 24 17:40:21 GMT+08:00 2022
     */
    TranRemark selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_tran_remark
     *
     * @mbggenerated Sun Jul 24 17:40:21 GMT+08:00 2022
     */
    int updateByPrimaryKeySelective(TranRemark record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_tran_remark
     *
     * @mbggenerated Sun Jul 24 17:40:21 GMT+08:00 2022
     */
    int updateByPrimaryKey(TranRemark record);

    /**
     * 批量保存交易备注
     * @param tranRemarkList
     * @return
     */
     int insertTranRemarkByList(List<TranRemark> tranRemarkList);
}