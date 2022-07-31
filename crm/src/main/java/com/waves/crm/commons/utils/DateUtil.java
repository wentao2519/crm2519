package com.waves.crm.commons.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author huangWenTao
 * @desc Date类型数据进行处理的工具类
 * @date 2022/6/6 10:01
 */
public class DateUtil {

    private DateUtil(){

    }

    /**
     * 对指定的date对象进行格式化 ：yyyy-MM-dd HH:mm:ss
     * @param date
     * @return
     */
    public static String formatDateTime(Date date){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return simpleDateFormat.format(date);
    }

 /**
     * 对指定的date对象进行格式化 ：yyyy-MM-dd
     * @param date
     * @return
     */
    public static String formatDate(Date date){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return simpleDateFormat.format(date);
    }

    /**
     * 对指定的date对象进行格式化 ： HH:mm:ss
     * @param date
     * @return
     */
    public static String formatTime(Date date){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
        return simpleDateFormat.format(date);
    }


}
