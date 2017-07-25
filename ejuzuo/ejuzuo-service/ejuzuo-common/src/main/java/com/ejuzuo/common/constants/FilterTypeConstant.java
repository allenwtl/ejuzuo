package com.ejuzuo.common.constants;

import java.util.HashMap;
import java.util.Map;

public class FilterTypeConstant {
	
	public static Integer NORMAL = 0;
	
	public static Integer BANNED = 1;
	
	public static Integer REPLACE = 2;
	
	public static Integer MANAUAL_JUDGE = 3;
	
	private static Map<Integer,String> descriptionMap = new HashMap<Integer,String>();
	
	static{
		descriptionMap.put(NORMAL, "正常");
		descriptionMap.put(BANNED, "审核不通过");
		descriptionMap.put(REPLACE, "替换");
		descriptionMap.put(MANAUAL_JUDGE, "人工审核");
	}
	
	public static String getDescriptionMap(Integer value){
		return descriptionMap.get(value);
	}

}
