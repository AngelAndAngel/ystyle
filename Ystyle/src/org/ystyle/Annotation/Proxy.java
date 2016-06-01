package org.ystyle.Annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 通过此注解 可以为任意类创建jdk代理
 * @author duyf
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Proxy {
	/*
	 * 必须指定自定义代理工厂 并且此工厂必须是ProxyFactory的子类
	 */	
	public Class proxyFactoryClass();
		
	/*
	 * 传递任意参数 让自定义代理工厂自行处理
	 */
	 public String params() default "";
	
}
