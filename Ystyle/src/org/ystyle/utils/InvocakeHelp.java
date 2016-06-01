package org.ystyle.utils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class InvocakeHelp {

	public static Object newInstance(String className, Object[] args) {
		try {
			Class newClass = Class.forName(className);
			if (args == null || args.length == 0) {
				return newClass.newInstance();
			} else {
				Class[] argsClasses = new Class[args.length];
				for (int i = 0; i < args.length; i++) {
					argsClasses[i] = args[i].getClass();
				}
				Constructor cons = newClass.getConstructor(argsClasses);
				return cons.newInstance(args);
			}

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	public static Object invokeMethod(Object owner, String methodName,
			Object[] args) {
		Class ownerClass = owner.getClass();
		Class[] argsClass = null;
		if (args != null && args.length != 0) {
			argsClass = new Class[args.length];
			for (int i = 0; i < args.length; i++) {
				argsClass[i] = args[i].getClass();
			}
		}
		try {
			Method method = ownerClass.getMethod(methodName, argsClass);
			return method.invoke(owner, args);
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return null;
	}
	
	/**
	 * 调用对象的set方法
	 * 
	 * @param obj
	 * @param fieldName
	 * @param value
	 * @param fieldType
	 */
	public static void callSetMethod(Object owner, String fieldName,
			Object value) {
		String setName = "set" + fieldName.substring(0, 1).toUpperCase()
				+ fieldName.substring(1);
		Class ownerClass = owner.getClass();
		try {
			Field field=ownerClass.getField(fieldName);
			Method method = ownerClass.getMethod(setName,field.getType());
			method.invoke(owner,value);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
	
	
	public static void checkSingle(){
		
	}
	
	
	

}
