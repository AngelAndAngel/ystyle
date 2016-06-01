package org.ystyle.db;

import java.util.HashMap;
import java.util.Map;

import org.ystyle.db.EntityClass;

/**
 * 映射存储
 * @author duyf
 *
 */
public class MappingClass {

	/**
	 * 类名--映射类
	 */
	private static Map<String,EntityClass> entityMap=new HashMap<String, EntityClass>();
	
	public static void putM_C(String clsName,EntityClass entityClass){
		entityMap.put(clsName,entityClass);
	}
	
	public static EntityClass getM_C(String clsName){
		return entityMap.get(clsName);
	}
	
	public static boolean hasEntityClass(String clsName){
		return entityMap.containsKey(clsName);
	}
	
}
