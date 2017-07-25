package com.ejuzuo.common.util;

import org.apache.commons.lang3.StringEscapeUtils;

public class StringUtil {
	
	/**过滤掉字符串的html标签*/
	public static String dealHtmlString(String str){
		String str1=str.replaceAll("<[a-zA-Z]+[1-9]?[^><]*>", "").replaceAll("</[a-zA-Z]+[1-9]?>", "");//过滤HTML标签
		String str2=StringEscapeUtils.unescapeHtml4(str1);//HTML 特殊字符转义
		return str2;
	}
}
