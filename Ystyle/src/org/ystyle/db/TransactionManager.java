package org.ystyle.db;
import java.sql.SQLException;

import org.ystyle.db.Session;


/**
 * 事务管理器
 * @author Administrator
 *
 */
public class TransactionManager {

	private Session session;
	public TransactionManager(Session session){
		this.session=session;
	}
	/**
	 * 开启事务
	 * @throws SQLException
	 */
	public void begin(){
		try {
			//把事务提交方式改为手工提交
			session.getConnection().setAutoCommit(false);
		} catch (SQLException e) {
		     throw new RuntimeException("事务开始失败"+e);
		}		
	}
	/**
	 * 提交事务(不关闭连接)
	 * @throws SQLException 
	 */
	public void commit(){
		try {
			session.getConnection().commit();
		} catch (SQLException e) {
			throw new RuntimeException("提交事务出现异常",e);
		}
	}
	
	/**
	 * 回滚事务(不关闭连接)
	 */
	public void rollback(){
		try {
			session.getConnection().rollback();
		} catch (SQLException e) {
	       throw new RuntimeException("回滚事务出现异常",e);
		}
	}
	
	
	
	
}
