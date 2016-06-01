package org.ystyle.Annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.ystyle.ProxyFactory.ServiceProxyFactory;

/**
 * 表明被注解的类为service
 * 则里面会包含如事务这类的处理
 * @author 杜云飞
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Service {
	public Class proxyFactoryClass() default ServiceProxyFactory.class;
}
