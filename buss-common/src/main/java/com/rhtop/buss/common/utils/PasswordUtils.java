package com.rhtop.buss.common.utils;

import java.util.Random;

import org.apache.shiro.crypto.hash.Md5Hash;

public class PasswordUtils {
	
	public static String md5Password(String password){
		return new Md5Hash(password).toHex();
	}
	  
    public static String md5Password(String password, String salt) {
    	return new Md5Hash(password, salt).toHex();
    }
	  
    public static String randomPassword(){
    	char[] pwdchs = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 'd', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };
	  
    	Random rand = new Random();
    	String pwd = "";
	  
    	for (int j = 0; j < 6; ++j) {
    		pwd = pwd + pwdchs[rand.nextInt(62)];
    	}
    	return pwd;
	}
}
