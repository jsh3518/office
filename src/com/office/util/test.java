package com.office.util;

public class test {
	private static final String KEY = "abcdefgabcdefg12"; 
    /**
     * 测试
     */
    public static void main(String[] args) throws Exception {  
        String content = "111";  
        System.out.println("加密前：" + content);  
        System.out.println("加密密钥和解密密钥：" + KEY);  
        String encrypt = AesUtil.aesEncrypt(content, KEY);  
        System.out.println("加密后：" + encrypt);  
        String decrypt = AesUtil.aesDecrypt(encrypt, KEY);  
        System.out.println("解密后：" + decrypt);  
    }

}
