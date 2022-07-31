package com.waves.crm.workbench.mapper;

import com.waves.crm.workbench.domain.ClueRemark;

import java.util.List;

public interface ClueRemarkMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_clue_remark
     *
     * @mbggenerated Sat Jul 16 18:58:48 GMT+08:00 2022
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_clue_remark
     *
     * @mbggenerated Sat Jul 16 18:58:48 GMT+08:00 2022
     */
    int insert(ClueRemark record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_clue_remark
     *
     * @mbggenerated Sat Jul 16 18:58:48 GMT+08:00 2022
     */
    int insertSelective(ClueRemark record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_clue_remark
     *
     * @mbggenerated Sat Jul 16 18:58:48 GMT+08:00 2022
     */
    ClueRemark selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_clue_remark
     *
     * @mbggenerated Sat Jul 16 18:58:48 GMT+08:00 2022
     */
    int updateByPrimaryKeySelective(ClueRemark record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_clue_remark
     *
     * @mbggenerated Sat Jul 16 18:58:48 GMT+08:00 2022
     */
    int updateByPrimaryKey(ClueRemark record);

    /**
     * 根据clueID查询线索备注
     * @param clueId
     * @return
     */
    List<ClueRemark> selectClueRemarkForDetailByClueId(String clueId);

    /**
     * 根据clueId查询该线索下所有备注
     */
    List<ClueRemark> selectClueRemarkByClueId(String clueId);

    /**
     * 根据clueId删除线索备注
     * @param clueId
     * @return
     */
    int deleteClueRemarkByClueId(String clueId);

    /**
     * 保存创建的线索备注
     * @param clueRemark
     * @return
     */
    int insertClueRemark(ClueRemark clueRemark);

    /**
     * 根据id删除线索备注
     * @param id
     * @return
     */
    int deleteClueRemarkById(String id);

    /**
     * 根据id修改线索备注
     * @param clueRemark
     * @return
     */
    int updateClueRemarkById(ClueRemark clueRemark);
}