package org.ystyle.converter;

import java.lang.reflect.Field;

import org.ystyle.converter.TypeConverter;

public class IntegerConverter implements TypeConverter {

	public Object convertValue(Object value,Field field) {
		
		if(value==null || value.toString().trim().equals("")){
			return null;
		}
		
		if(field.getType().isArray()){
			String[] intStr=(String[])value;
			Integer[] returnInt=new Integer[intStr.length];
			for(int i=0;i<intStr.length;i++){
				if(intStr[i]!=null&&!(intStr[i].trim().equals(""))){
					returnInt[i]=Integer.parseInt(intStr[i]);	
				}
				
			}
			return returnInt;
		}else{
			return Integer.parseInt(value.toString());   	
		}
		
		
	}

}
