package com.waves.crm.commons.utils;

import org.apache.poi.hssf.usermodel.HSSFCell;

/**
 * @author huangWenTao
 * @desc
 * @date 2022/7/8 21:18
 */
public class HSSFUtil {
    /**
     * 从HSSFCell对象中获取列的值
     * @return
     */
    public static String getCellValueForStr(HSSFCell cell){
        String result = "";
        if (cell.getCellType()==HSSFCell.CELL_TYPE_STRING){
            result = cell.getStringCellValue();
        } else if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
            result = cell.getNumericCellValue()+"";
        } else if (cell.getCellType() == HSSFCell.CELL_TYPE_BOOLEAN) {
            result = cell.getBooleanCellValue()+"";
        } else if (cell.getCellType() == HSSFCell.CELL_TYPE_FORMULA) {
            result = cell.getCellFormula();
        } else {
            result = "";
        }
        return result;
    }
}
