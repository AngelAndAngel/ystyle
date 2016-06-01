package org.ystyle.Annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * 表示自动注入
 * @author 杜云飞
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Autowired {
	/**
	 * 注入依赖的类 必须为可以实例化的类
	 * 不能是抽象类或者接口
	 */
	public Class iocClass();
	
	/**
	 * 假如需要代理，则提供代理class全名
	 * 非必填项
	 * @return
	 */
	/*public String proxyFactoryClass() default "" ;*/
	
	
}
