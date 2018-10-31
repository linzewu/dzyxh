package com.xs.common.Annotation;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.xs.enums.CommonUserOperationEnum;

@Retention(RUNTIME)
@Target(METHOD)
public @interface UserOperation {
	
	
	public String code();
	
	public String name();
	
	public CommonUserOperationEnum userOperationEnum() default CommonUserOperationEnum.Default;
	
	public boolean isMain() default true;
	
	/**
	 * 是否允许被授权
	 * @return
	 */
	public boolean isEmpowered() default true;

}
