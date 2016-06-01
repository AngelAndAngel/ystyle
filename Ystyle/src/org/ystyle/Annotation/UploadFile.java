package org.ystyle.Annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自动上传，这个注解用在FilePo对象上
 * @author 杜云飞
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface UploadFile {
	
	/**
	 * 上传的路径，默认上传到根目录
	 * 支持嵌入表达式
	 * 比如"${param.xxx}/${requestScope.xxx}/${sessionScope.xxx}/"
	 */
   public String path() default "";
   
   /**
    * 文件名 为""表示用原文件名 
    */
   public String name() default "";
}
