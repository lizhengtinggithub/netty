package com.lzttest.socket.Utils;

/**
 * @author lysoft
 * @description
 * @date 2019/11/13
 */
public  class MessageFormat {

    /** 客户端身份---狱警*/
    public static final String KIND_YUJING = "KIND_YUJING";
    /** 客户端身份---犯人*/
    public static final String KIND_FANREN = "KIND_FANREN";

    /** 客户端身份*/
    private static String kind = "";
    /** 客户端 地址*/
    private static String address = "";
    /** 客户端 请求内容*/
    private static String msg = "";

    private static String messageFormat = kind+"&"+address+"&"+msg;
    /** 组合信息 */
    public static String getMessage(String kind, String address, String msg) {
        return kind+"&"+address+"&"+msg;
    }


    /** 分割信息  */
    public static String[] getArr(String msg) {
       return msg.split("&");
    }


}
