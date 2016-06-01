package org.ystyle.db;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.ystyle.db.ConnectionPool;
import org.ystyle.db.Session;
import org.ystyle.db.TransactionManager;
import org.ystyle.dbutil.QueryRunner;
import org.ystyle.dbutil.handlers.BeanListHandler;
import org.ystyle.dbutil.handlers.MapListHandler;
import org.ystyle.dbutil.handlers.ScalarHandler;

public class DefaultSession implements Session {

	private Connection connection;
	private TransactionManager transaction;

	public DefaultSession() {
		connection = ConnectionPool.instance().getConnection();
		transaction = new TransactionManager(this);
	}

	public TransactionManager beginTransaction() {
		transaction.begin();
		return transaction;
	}

	public Connection getConnection() {
		return connection;
	}

	public boolean isConnected() {
		boolean isconnec = false;
		try {
			isconnec = connection.isClosed();
		} catch (SQLException e) {
		}
		return isconnec;
	}

	public void close() {
		try {
			connection.close();
			connection = null;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	public int update(String sql, Object... params) {
		QueryRunner qr = new QueryRunner();
		int count=0;
		try {
			count = qr.update(connection, sql, params);
		} catch (SQLException e) {
			e.printStackTrace();
			count=0;
		}
		return count;
	}

	@Override
	public int[] updateBatch(String sql, Object[][] params) {
		QueryRunner qr = new QueryRunner();
		int[] count={0};
		try {
			count=qr.batch(connection, sql, params);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	
	public <T> List<T> query(String sql, Class cls, Object... params) {
		QueryRunner qr = new QueryRunner();
		try {
			List<T> list = qr.query(connection,sql,
					new BeanListHandler<T>(cls),params);
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	

	public Long queryCount(String sql, Object... params) {
		QueryRunner qr = new QueryRunner();
		try {
			Object obj=qr.query(connection,sql,new ScalarHandler(),params);
			if(obj!=null){
				return new Long(obj.toString());		
			}else{
				return 0l;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Map<String, Object>> query(String sql, Object... params) {
		QueryRunner qr = new QueryRunner();
		try {
			List<Map<String,Object>> list=qr.query(connection,sql,new MapListHandler(),params);
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	

}
