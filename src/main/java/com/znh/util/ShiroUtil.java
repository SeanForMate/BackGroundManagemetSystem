package com.znh.util;

import java.util.UUID;

import org.apache.shiro.crypto.hash.Md5Hash;

public class ShiroUtil {
	
	 /**
     * 生成32的随机盐值
     */
    public static String createSalt(){
    	String source = UUID.randomUUID().toString().replaceAll("-", "");
    	String salt = UUID.randomUUID().toString().replaceAll("-", "");
    	Md5Hash hash = new Md5Hash(source,salt,2);
        return hash.toString();
    }

    /**
     * 加盐加密
     * @param srcPwd    原始密码
     * @param saltValue 盐值
     */
    public static String saltEncryption(Object srcPwd,String saltValue){
    	Md5Hash hash = new Md5Hash(srcPwd,saltValue,2);
        return hash.toString();
    }
}
