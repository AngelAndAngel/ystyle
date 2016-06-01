package org.ystyle.db;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import org.ystyle.db.TransactionManager;

public interface Session {
	public TransactionManager beginTransaction();

	public Connection getConnection();

	public boolean isConnected();

	public void close();

	public int update(String sql, Object... params);
	
	public int[] updateBatch(String sql, Object[][] params);
	

	public <T> List<T> query(String sql, Class cls, Object... params);
	
	public List<Map<String,Object>> query(String sql,Object...params);
	
	public Long queryCount(String sql,Object... params);
}
