package com.waves.crm.commons.domain;

/**
 * @author huangWenTao
 * @desc
 * @date 2022/6/5 23:43
 */
public class ReturnObject {

    //状态码 0---失败  1---成功
    private String code;
    //提示信息
    private String message;
    //其他数据
    private Object returnData;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getReturnData() {
        return returnData;
    }

    public void setReturnData(Object returnData) {
        this.returnData = returnData;
    }
}
