package com.ejuzuo.admin.webBindType;

import java.beans.PropertyEditorSupport;

import org.apache.commons.lang3.StringUtils;

import com.ejuzuo.common.BaseType;

/**
 * 实体类型转换器 
 */
public class BaseTypeEditor extends PropertyEditorSupport{
	private Class<? extends BaseType> clazz;
	
	public BaseTypeEditor(Class<? extends BaseType> clazz){
		this.clazz = clazz;
	}
	
	@Override
	public void setAsText(String text) throws IllegalArgumentException{
		if(StringUtils.isBlank(text)){
			super.setValue(null);
		}else{
			super.setValue(BaseType.valueOf(clazz, Integer.parseInt(text)));
		}
	}
}