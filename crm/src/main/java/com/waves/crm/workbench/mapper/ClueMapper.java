package com.waves.crm.workbench.mapper;

import com.waves.crm.workbench.domain.Activity;
import com.waves.crm.workbench.domain.Clue;

import java.util.List;
import java.util.Map;

public interface ClueMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_clue
     *
     * @mbggenerated Thu Jul 14 21:10:37 GMT+08:00 2022
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_clue
     *
     * @mbggenerated Thu Jul 14 21:10:37 GMT+08:00 2022
     */
    int insert(Clue record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_clue
     *
     * @mbggenerated Thu Jul 14 21:10:37 GMT+08:00 2022
     */
    int insertSelective(Clue record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_clue
     *
     * @mbggenerated Thu Jul 14 21:10:37 GMT+08:00 2022
     */
    Clue selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_clue
     *
     * @mbggenerated Thu Jul 14 21:10:37 GMT+08:00 2022
     */
    int updateByPrimaryKeySelective(Clue record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_clue
     *
     * @mbggenerated Thu Jul 14 21:10:37 GMT+08:00 2022
     */
    int updateByPrimaryKey(Clue record);


    /**
     * 添加线索
     * @param clue
     * @return
     */
    int insertClue(Clue clue);

    /**
     * 根据条件分页查询 clueList
     * @param map
     * @return
     */
    List<Clue> selectClueByConditionForPage(Map<String, Object> map);

    /**
     * 根据条件查询线索总条数
     * @param map
     * @return
     */
    int selectCountOfClueByCondition(Map<String, Object> map);

    /**
     * 根据id查询线索的明细信息
     * @param id
     * @return
     */
    Clue selectClueForDetailById(String id);

    /**
     * 根据ids数据批量删除线索
     * @param ids
     * @return
     */
    int deleteClueByIds(String[] ids);

    Clue selectClueById(String id);

    int updateClue(Clue clue);

    /**
     * 根据id查询线索信息
     * @param id
     * @return
     */
    Clue selectClueInfoById(String id);


    /**
     * 根据id删除线索
     * @param id
     * @return
     */
    int deleteClueById(String id);
}