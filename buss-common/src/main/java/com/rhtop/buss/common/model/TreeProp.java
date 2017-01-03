package com.rhtop.buss.common.model;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TreeProp{
	public enum TreePropType{ID,CODE,TEXT,PARENT,PATH}
	TreePropType value();
}
