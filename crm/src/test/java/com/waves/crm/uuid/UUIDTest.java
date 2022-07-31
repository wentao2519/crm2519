package com.waves.crm.uuid;

import org.junit.Test;

import java.util.UUID;

/**
 * @author huangWenTao
 * @desc
 * @date 2022/6/15 16:25
 */
public class UUIDTest {

    @Test
    public void test01() {
        String s = UUID.randomUUID().toString().replaceAll("-", "");
        System.out.println(s);
    }
}
