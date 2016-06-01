package org.ystyle.converter;

import java.lang.reflect.Field;

/**
 * form提交到action，进行值的转换
 * @author duyf
 *
 */
public interface TypeConverter {
	
	/**
	 * 
	 * @param value 将要转换的值
	 * @param field 将要转换的属性 元数据包含了更多的信息
	 * @return 得到被转换后的对象
	 */
	public Object convertValue(Object value, Field field);
}
