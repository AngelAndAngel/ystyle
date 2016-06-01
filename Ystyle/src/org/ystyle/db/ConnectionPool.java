package org.ystyle.db;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Map.Entry;

import javax.sql.DataSource;

import org.ystyle.utils.InvocakeHelp;

public class ConnectionPool {

	private DataSource dataSource;

	private static String DB_PROPERTIES = "db.properties";
	
	private static Properties prop = new Properties();

	public static boolean show_sql=false;
	// 单例 start
	private static ConnectionPool cp = new ConnectionPool();

	private ConnectionPool() {

		try {
			prop.load(Thread.currentThread().getContextClassLoader()
					.getResourceAsStream(DB_PROPERTIES));
			/* 加载全局 */
            String showsql=prop.getProperty("show_sql"); 
			if("true".equals(showsql)){
				show_sql=true;
			}
			/* 加载simple */
			String simpleClassName = prop.getProperty("simple.driverClassName");
			if (simpleClassName != null) {
				String username = prop.getProperty("simple.username");
				String password = prop.getProperty("simple.password");
				String url = prop.getProperty("simple.url");
				String maxActive = prop.getProperty("simple.maxActive");
				String initSize = prop.getProperty("simple.initSize");
				String minIdle = prop.getProperty("simple.minIdle");
				SimpleDataSource simple_dataSource = new SimpleDataSource();
				simple_dataSource.setDriverClassName(simpleClassName);
				simple_dataSource.setUsername(username);
				simple_dataSource.setPassword(password);
				simple_dataSource.setUrl(url);
				simple_dataSource.setMaxActive((maxActive == null || maxActive
						.equals("")) ? SimpleDataSource.DEFAULT_MAXACTIVE
						: Integer.parseInt(maxActive));
				simple_dataSource.setInitSize((initSize == null || initSize
						.equals("")) ? SimpleDataSource.DEFAULT_INITSIZE
								: Integer.parseInt(maxActive));
				simple_dataSource.setMinIdle((minIdle == null || minIdle
						.equals("")) ? SimpleDataSource.DEFAULT_MINIDLE
						: Integer.parseInt(maxActive));
				simple_dataSource.init();
				dataSource = simple_dataSource;
			}

			String selfDataSource = prop.getProperty("self.dataSource");
			if (selfDataSource != null) {
				Map<String, String> fieldMap = ifDataSource("self");
				Object selfds = InvocakeHelp.newInstance(selfDataSource, null);
				for (Entry<String, String> ent : fieldMap.entrySet()) {
					callSetMethod(selfds, ent.getKey(), ent.getValue());
				}
				dataSource = DataSource.class.cast(selfds);
			}

			String c3p0DriverClass = prop.getProperty("c3p0.driverClass");
			if (c3p0DriverClass != null) {
				Map<String, String> fieldMap = ifDataSource("c3p0");
				Object cpds = InvocakeHelp.newInstance(
						"com.mchange.v2.c3p0.ComboPooledDataSource", null);
				for (Entry<String, String> ent : fieldMap.entrySet()) {
					callSetMethod(cpds, ent.getKey(), ent.getValue());
				}

				dataSource = DataSource.class.cast(cpds);
			}
			String proxoolxml = prop.getProperty("proxool.driver");
			if (proxoolxml != null) {
				Map<String, String> fieldMap = ifDataSource("proxool");
				Object proxoolds = InvocakeHelp.newInstance(
						"org.logicalcobwebs.proxool.ProxoolDataSource", null);
				for (Entry<String, String> ent : fieldMap.entrySet()) {
					callSetMethod(proxoolds, ent.getKey(), ent.getValue());
				}

				dataSource = DataSource.class.cast(proxoolds);
			}

			String druidurl = prop.getProperty("druid.url");
			if (druidurl != null) {
				Map<String, String> fieldMap = ifDataSource("druid");
				Object druidds = InvocakeHelp.newInstance(
						"com.alibaba.druid.pool.DruidDataSource", null);
				for (Entry<String, String> ent : fieldMap.entrySet()) {
					callSetMethod(druidds, ent.getKey(), ent.getValue());
				}
				dataSource = DataSource.class.cast(druidds);
				// DruidDataSource dds=new DruidDataSource();
				// dds.getConnection();
				// dataSource.getConnection();
				System.out.println("druid加载完毕!");

			}

			// dds.setUrl(jdbcUrl)

			System.out.println("数据库加载完毕!");
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("数据库加载失败" + e);
		}
	}

	public static ConnectionPool instance() {
		return cp;
	}

	// 单例 end

	public Connection getConnection() {
		Connection conn=null;
		try {
			conn = dataSource.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}

	public  void closeConnection(Connection conn){
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 根据类型 得到连接池配置的参数
	 * @param type
	 * @return
	 */
	private Map<String,String> ifDataSource(String type){
		Map<String,String> map=new HashMap<String,String>();
			for(Entry<Object,Object> ent:prop.entrySet()){
				String key=ent.getKey().toString();
				if(key.indexOf(type)!=-1){
					String keyname=key.split("\\.")[1];
					map.put(keyname,ent.getValue().toString());
				}
			}		
		return map;
	}

	
	private static void callSetMethod(Object owner, String fieldName,
			Object value) {
		String setName = "set" + fieldName.substring(0, 1).toUpperCase()
				+ fieldName.substring(1);
		Class ownerClass = owner.getClass();
		
		try {
			
			Method[] methods=ownerClass.getMethods();
			Method methodfinal=null;
			for(Method method:methods){
				if(method.getName().equals(setName)){
					methodfinal=method;
					Class type=method.getParameterTypes()[0];
					if(type.getName().equals("java.lang.Integer") || type.getName().equals("int")){
						value=Integer.parseInt(value.toString());
					}else if(type.getName().equals("java.lang.Long") || type.getName().equals("long") ){
						value=Long.parseLong(value.toString());
					}else if(type.getName().equals("java.lang.Double") || type.getName().equals("double") ){
						value=Double.parseDouble(value.toString());
					}
					break;
				}
			}
			methodfinal.invoke(owner,value);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}

	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
}
