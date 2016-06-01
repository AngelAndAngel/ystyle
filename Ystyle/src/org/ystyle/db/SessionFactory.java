package org.ystyle.db;

import org.ystyle.db.DefaultSession;
import org.ystyle.db.Session;
   

public class SessionFactory {

	private static ThreadLocal<Session> currentConnThread = new ThreadLocal<Session>();
	
	private static Session openSession() {
		Session session=new DefaultSession();
		return session;
	}

	/**
	 * @return 返回当前线程变量中的数据库连接
	 */
	public static Session getSession() {
		Session session = currentConnThread.get();
		if (session == null) {
			// 重新创建一个session
			session = openSession();
			// 绑定到当前线程变量
			currentConnThread.set(session);
		}
		return session;

	}

	public static void closeSession(Session session) {
		if (session != null) {
			currentConnThread.remove();
			session.close();
			session=null;
		}
	}


}
