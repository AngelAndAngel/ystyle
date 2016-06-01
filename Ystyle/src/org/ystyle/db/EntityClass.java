package org.ystyle.db;

import java.util.HashMap;
import java.util.Map;

/**
 * 用来描述entity
 * @author duyf
 *
 */
public class EntityClass {

	/*表名*/
	private String table;
	
	/*类名*/
	private String classname;
	
	/*Id属性*/
	private String IdField;
	
	/*属性-字段映射*/
	private Map<String,String> fieldMap=new HashMap<String, String>();
	
	/*插入语句*/
	private String insertSql;
	
	/*更新语句*/
	private String updateSql;
	
	/*删除语句*/
	private String deleteSql;

	public String getTable() {
		return table;
	}

	public void setTable(String table) {
		this.table = table;
	}

	public String getIdField() {
		return IdField;
	}

	public void setIdField(String idField) {
		IdField = idField;
	}

	public Map<String, String> getFieldMap() {
		return fieldMap;
	}

	public void setFieldMap(Map<String, String> fieldMap) {
		this.fieldMap = fieldMap;
	}

	public String getInsertSql() {
		return insertSql;
	}

	public void setInsertSql(String insertSql) {
		this.insertSql = insertSql;
	}

	public String getUpdateSql() {
		return updateSql;
	}

	public void setUpdateSql(String updateSql) {
		this.updateSql = updateSql;
	}

	public String getDeleteSql() {
		return deleteSql;
	}

	public void setDeleteSql(String deleteSql) {
		this.deleteSql = deleteSql;
	}

	public String getClassname() {
		return classname;
	}

	public void setClassname(String classname) {
		this.classname = classname;
	}
	
	
	
	
}
