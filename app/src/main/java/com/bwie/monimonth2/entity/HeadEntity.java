package com.bwie.monimonth2.entity;

public class HeadEntity {

    private String Msg;
    private String code;

    public HeadEntity(String msg, String code) {
        Msg = msg;
        this.code = code;
    }

    public String getMsg() {
        return Msg;
    }

    public void setMsg(String msg) {
        Msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
