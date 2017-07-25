package com.ejuzuo.common.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

public final class PasswordUtils {
	// 注册、修改密码 密码验证规则：
	// 1.只能是数字、字母、数字字母组合6-16位 
	// 2.不能重复的数字、字母   如：11111 aaaaaaa
	// 3.不能是连续的数字、字母(倒序也不行) 123456  abcdefg gfedcba
	// 4.密码不能在密码库
	
	static final Pattern PASSWORD_PATTERN = Pattern.compile("^[0-9a-zA-Z]{6,15}$");
	
	//网络易猜密码库
	static final List<String> pwdList = new ArrayList<String>();
	static {
		pwdList.add("112233");
		pwdList.add("123123");
		pwdList.add("123321");
		pwdList.add("abcabc");
		pwdList.add("abc123");
		pwdList.add("a1b2c3");
		pwdList.add("aaa111");
		pwdList.add("123qwe");
		pwdList.add("qwerty");
		pwdList.add("qweasd");
		pwdList.add("admin");
		pwdList.add("password");
		pwdList.add("passwd");
		pwdList.add("iloveyou");
		pwdList.add("5201314");
		pwdList.add("adobe123");
		pwdList.add("letmein");
		pwdList.add("photoshop");
		pwdList.add("monkey");
		pwdList.add("shadow");
		pwdList.add("sunshine");
		pwdList.add("password1");
		pwdList.add("princess");
		pwdList.add("azerty");
		pwdList.add("trustno1");
	}
	
	/**
	 * 密码验证
	 * */
	public static Map<String,String> validatePwd(String pwd){
		Map<String,String> result = new HashMap<String,String>(4);
		pwd = StringUtils.trim(pwd);
		if(StringUtils.isBlank(pwd)){
			result.put("status","fail");
			result.put("errMsg","输入密码不能为空。");
			return result;
		}
		if(pwdList.contains(pwd)){
			result.put("status","fail");
			result.put("errMsg","该密码存在被盗风险，请更换。");
			return result;
		}
		if(pwd.length()<6 || pwd.length()>15){
			result.put("status","fail");
			result.put("errMsg","请输入长度在6-15位内的密码。");
			return result;
		}
		boolean baseValidate = PASSWORD_PATTERN.matcher(pwd).matches();
		if(!baseValidate){
			result.put("status","fail");
			result.put("errMsg","密码不能为数字、字母以外字符。");
			return result;
		} 
		if(isRepeat(pwd)){
			result.put("status","fail");
			result.put("errMsg","密码不能为重复数字或字母。");
			return result;
		} 
		if(isOrder(pwd)){
			result.put("status","fail");
			result.put("errMsg","密码不能为连续数字或字母。");
			return result;
		}
		result.put("status", "success");
		return result;
	}

	
	/**
	 * 判断密码是否为相同数字或字母
	 * return true:即为相同  false:不相同
	 * */
	private static boolean isRepeat(String pwd) {
		String regex = pwd.substring(0, 1) + "{" + pwd.length() + "}";  
	    return pwd.matches(regex);  
	} 
	
	/**
	 * 判断是否为连续数字或字母 （含逆序）
	 * return true:连续/逆序连续  false:非连续
	 * */
	private static boolean isOrder(String pwd){
		char[] chars = pwd.toCharArray();
		boolean orderFlag = true;
		for(int i=1;i<chars.length;i++) {
			int j = i-1;
			if(chars[i]-chars[j] == 1){
				chars[j] = chars[i];
			} else {
				orderFlag = false;
				break;
			}
		}
		if(!orderFlag){
			for(int i=1;i<chars.length;i++) {
				int j = i-1;
				if(chars[j]-chars[i] == 1){
					chars[j] = chars[i];
				} else {
					orderFlag = false;
					break;
				}
			}
		}
		return orderFlag;
	}
	
}
