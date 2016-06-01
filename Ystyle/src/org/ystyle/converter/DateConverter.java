package org.ystyle.converter;

import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.ystyle.converter.TypeConverter;

public class DateConverter implements TypeConverter {

	private static final String[] FORMAT = {
		"HH",  // 2
		"yyyy", // 4
		"HH:mm", // 5
		"yyyy-MM", // 7
		"HH:mm:ss", // 8
		"yyyy-MM-dd", // 10
		"yyyy-MM-dd HH", // 13
		"yyyy-MM-dd HH:mm", // 16
		"yyyy-MM-dd HH:mm:ss" // 19
	};  
	
	private static final DateFormat[] ACCEPT_DATE_FORMATS = new DateFormat[FORMAT.length]; //支持转换的日期格式   
	
	static {		
		for(int i=0; i<FORMAT.length; i++){
			ACCEPT_DATE_FORMATS[i] = new SimpleDateFormat(FORMAT[i]);
		}
	}
	
	public Object convertValue(Object value,Field field) {    
        
		if(value==null||value.equals("")){
			return null;
		}
        //String[] params = (String[])value;    
        String dateString = (String)value;//获取日期的字符串
        int len = dateString != null ? dateString.length() : 0;
        int index  = -1;
        
        if (len > 0) {
			for (int i = 0; i < FORMAT.length; i++) {
				if (len == FORMAT[i].length()) {
					index = i;
				}
			}
		}
       
        
        if(index >= 0){
        	try {
				return ACCEPT_DATE_FORMATS[index].parse(dateString);
			} catch (ParseException e) {		
				return null;
			}
        } 
        return null;    
    }

}
