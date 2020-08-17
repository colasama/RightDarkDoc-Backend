package com.rightdarkdoc.config;

public interface MyConfig {


    public static final Integer PRIVATE = 0;
    public static final Integer U_R = 1;
    public static final Integer U_C = 3;
    public static final Integer U_W = 7;
    public static final Integer U_EXE = 15;

    /**
     * 消息相关
     */
    public static final Integer SYS_MESSAGE = 0;          //普通消息
    public static final Integer APPLY_MESSAGE = 1;        //申请消息
    public static final Integer INVITE_MESSAGE = 2;       //邀请消息


    /**
     * 访问文档相关
     */
    public static final Integer MAX_VIEW_DOC_NUM = 50;



}
