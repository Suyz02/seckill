package com.xxxx.seckill.util;

import org.apache.commons.codec.digest.DigestUtils;

public class MD5Util {

    private static final String slat = "1ae2dd4fg9aa0widaj0fa90ashca";//密钥盐加密，双层加密

    //MD5加密
    public static String md5(String src){
        return DigestUtils.md5Hex(src);
    }

    public static String inputPassToFormPass(String inputPass){
        String str = "" + slat.charAt(3) + slat.charAt(10) + inputPass + slat.charAt(4) + slat.charAt(18);
        return md5(str);
    }
}
