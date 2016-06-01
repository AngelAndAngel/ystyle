package org.ystyle.db;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Logger;

import javax.sql.DataSource;

import org.ystyle.ProxyFactory.ConnProxyFactory;
import org.ystyle.dbutil.DbUtils;

public class SimpleDataSource implements DataSource {

	public final static int DEFAULT_MINIDLE=5;
	public final static int DEFAULT_INITSIZE=20;
	public final static int DEFAULT_MAXACTIVE=10;
	
	// 最小可用连接数
	private int minIdle;

	// 初始化连接数
	private int initSize;

	// 同时连接最大并发数量
	private int maxActive;

	private String driverClassName = "com.mysql.jdbc.Driver";
	private String username = "root";
	private String password = "root";
	private String url = "jdbc:mysql://localhost:3306/test?useUnicode=true&amp;characterEncoding=utf8";

	// 连接代理工厂
	private ConnProxyFactory cpf = new ConnProxyFactory(this);

	private final ReentrantLock lock = new ReentrantLock(true);
	
	private Condition corcurrentCon=lock.newCondition();
	

	// 连接池
	private List<Connection> connPool = new ArrayList<Connection>();

	// 连接不成功次数
	private AtomicInteger errcount = new AtomicInteger(0);

	// 应用中的连接并发数量
	private int activeCount = 0;

	public SimpleDataSource(){
		
	}
	
	public SimpleDataSource(String driverClassName,String username,String password,String url,int maxActive,int initSize,int minIdle) {
		System.out.println("Hello I am SimpleDataSource !");
		this.driverClassName=driverClassName;
		this.username=username;
		this.password=password;
		this.url=url;
		this.maxActive=maxActive;
		this.initSize=initSize;
		this.minIdle=minIdle;
		init();
	}
	
	
	

	public void init() {
		DbUtils.loadDriver(driverClassName);
		lock.lock();
		try{
			for (int i = 0; i < initSize; i++) {
				connPool.add(createConnection());
			}	
		}finally{
			lock.unlock();
		}
		
	}

	// 释放连接
	public void closeConnection(Connection conn) throws SQLException {
		lock.lock();
		try {
			putPool(conn);
			activeCount--;
			//释放连接后唤醒等待
			corcurrentCon.signalAll();
		} finally {
			lock.unlock();
		}
		
		
	}

	/**
	 * 此方法必须保证还有可用连接 外围方法必须同步
	 * 
	 * @return
	 */
	private Connection searchConntion() {
		Connection conn = connPool.get(0);
		connPool.remove(0);
		return conn;
	}

	/**
	 * 放入连接池
	 */
	private void putPool(Connection conn) {
		connPool.add(conn);
	}

	/**
	 * 创建新的连接 外围方法必须同步
	 * 
	 * @return
	 * @throws SQLException
	 */
	private Connection createConnection() {
		Connection conn = null;
		try {
			conn = (Connection) cpf.factory(DriverManager.getConnection(url,
					username, password), null);
		} catch (SQLException e) {
			errcount.incrementAndGet();
			System.out.println("连接错误次数: " + errcount);
			if (errcount.get() >= 5) {
				throw new RuntimeException(" cannot connect the database!");
			}
			try {
				Thread.sleep(2000);
				conn = createConnection();
				if (conn != null) {
					errcount.set(0);
					return conn;
				}

			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}

		}
		return conn;

	}

	public Connection getConnection() throws SQLException {
		Connection conn = null;
		lock.lock();
		try {
			if(activeCount>=maxActive){
				//超过并发量 等待
				corcurrentCon.await();
			}
			if (connPool.size() <= minIdle) {
				//生成新连接
				conn = createConnection();
			} else {
				conn = searchConntion();
			}
			activeCount++;
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			lock.unlock();
		}

		return conn;

	}

	public int getMinIdle() {
		return minIdle;
	}

	public void setMinIdle(int minIdle) {
		this.minIdle = minIdle;
	}

	public int getInitSize() {
		return initSize;
	}

	public void setInitSize(int initSize) {
		this.initSize = initSize;
	}

	public int getMaxActive() {
		return maxActive;
	}

	public void setMaxActive(int maxActive) {
		this.maxActive = maxActive;
	}
	
	public Connection getConnection(String username, String password)
			throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public PrintWriter getLogWriter() throws SQLException {
		throw new UnsupportedOperationException("getLogWriter");
	}

	public int getLoginTimeout() throws SQLException {
		return 0;
	}

    // JDK 1.7 compatible
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        return null;
    }

    public void setLogWriter(PrintWriter out) throws SQLException {
		throw new UnsupportedOperationException("setLogWriter");
	}

	public void setLoginTimeout(int seconds) throws SQLException {
		throw new UnsupportedOperationException("setLoginTimeout");
	}

	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		return DataSource.class.equals(iface);
	}

	public <T> T unwrap(Class<T> iface) throws SQLException {
		return (T) this;
	}

	public String getDriverClassName() {
		return driverClassName;
	}

	public void setDriverClassName(String driverClassName) {
		this.driverClassName = driverClassName;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
