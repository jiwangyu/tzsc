package com.jwy.bjtumidas.utils;

import java.security.MessageDigest;

public class MD5Encoder {
    public static String encode(String string) throws Exception {
        byte[] hash = MessageDigest.getInstance("MD5").digest(
                string.getBytes("UTF-8"));
        StringBuilder hex = new StringBuilder(hash.length * 2);
        for (byte b : hash) {
            if ((b & 0xFF) < 0x10) {
                hex.append("0");
            }
            hex.append(Integer.toHexString(b & 0xFF));
        }
        return hex.toString();
    }

    /**
     * 5次md5加密
     *
     * @param pwd
     * @return
     */
    public static String fiveMD5Encode(String pwd) {
        try {
            return encode(encode(encode(encode(encode(pwd)))));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

}
