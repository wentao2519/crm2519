package com.waves.crm.commons.utils;

import java.util.UUID;

/**
 * @author huangWenTao
 * @desc
 * @date 2022/6/9 21:24
 */
public class UUIDUtil {

    private UUIDUtil(){

    }

    /**
     * 生成UUID
     * @return
     */
    public static String getUUID(){
        return UUID.randomUUID().toString().replaceAll("-","");
    }
}
