package com.rightdarkdoc.config;

public interface MyConfig {

    public static  Integer U_R = 1;
    public static Integer U_C = 3;
    public static Integer U_W = 7;
    public static  Integer U_EXE = 15;

    /**
     * 消息相关
     */
    public static Integer SYS_MESSAGE = 0;          //普通消息
    public static Integer APPLY_MESSAGE = 1;        //申请消息
    public static Integer INVITE_MESSAGE = 2;       //邀请消息


    /**
     * 访问文档相关
     */
    public static Integer MAX_VIEW_DOC_NUM = 50;



}
