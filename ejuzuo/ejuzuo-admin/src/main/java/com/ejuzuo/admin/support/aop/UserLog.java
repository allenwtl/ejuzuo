package com.ejuzuo.admin.support.aop;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * System Manager - User Behavior Log Annotation<br>
 * value : 用户基本操作描述，default is "UNKNOWN"<br>
 * logParameters : 是否需要记录 request parameters，default is true
 * <hr>
 * Copyright (C) 2010-2015 www.cfwb.com Inc. All rights reserved.<br><br>
 * 
 * @author xiaoqing.chen at 2015-06-02
 *
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface UserLog {

	String value() default "UNKNOWN";
	
	boolean logParameters() default true;
	
}
