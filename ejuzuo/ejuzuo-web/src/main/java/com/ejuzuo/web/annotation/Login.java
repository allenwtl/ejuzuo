package com.ejuzuo.web.annotation;

import com.ejuzuo.web.util.ResultTypeEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 在需要登录的方法上加入
 * Created by tianlun.wu on 2016/3/26 0026.
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Login {

    ResultTypeEnum value() default ResultTypeEnum.page ;
}
