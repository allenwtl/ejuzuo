package com.ejuzuo.web.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by tianlun.wu on 2016/4/13 0013.
 */

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AvoidDuplicateSubmitAnnotation {
    boolean needSaveToken() default false;
    boolean needRemoveToken() default false;
}
