package org.ystyle.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 表示受事务控制的方法
 * @author 杜云飞
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Transactional {
     
	 public String value() default "受事务控制方法";
	 public Class[] rollbackFor() default {RuntimeException.class};
	
}
