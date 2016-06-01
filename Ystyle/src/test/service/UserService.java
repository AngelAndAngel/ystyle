package test.service;

import java.util.List;

import org.ystyle.db.Session;
import org.ystyle.db.SessionFactory;

import test.entity.Userinfo;

public class UserService {

	public List<Userinfo> findAllUser() {
		Session session = SessionFactory.getSession();
		List<Userinfo> list = session.query("select * from userinfo",
				Userinfo.class);
		SessionFactory.closeSession(session);
		return list;
	}

	public void saveUser(Userinfo user) {
		Session session = SessionFactory.getSession();
		session.update("insert into userinfo values(?,?,?,?)", user.getToid(),
				user.getUsername(), user.getStarttime(), user.getEndtime());
		SessionFactory.closeSession(session);
	}

	public void saveUserBatch(List<Userinfo> list) {
		Session session = SessionFactory.getSession();
		String[][] params = new String[list.size()][4];
		for (int i = 0; i < list.size(); i++) {
			Userinfo user = list.get(i);
			params[i][0] = user.getToid();
			params[i][1] = user.getUsername();
			params[i][2] = user.getStarttime();
			params[i][3] = user.getEndtime();
		}
		session.updateBatch("insert into userinfo values(?,?,?,?)", params);
		SessionFactory.closeSession(session);
	}

	public Userinfo findUserByToid(String toid) {
		Session session = SessionFactory.getSession();
		List<Userinfo> list = session.query(
				"select * from userinfo where toid=?", Userinfo.class, toid);
		SessionFactory.closeSession(session);
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	public void updateUser(Userinfo user) {
		Session session = SessionFactory.getSession();
		session.update(
				"update userinfo set username=?,starttime=?,endtime=? where toid=?",
				user.getUsername(), user.getStarttime(), user.getEndtime(),
				user.getToid());
		SessionFactory.closeSession(session);
	}

	public void deleteUser(String id) {
		Session session = SessionFactory.getSession();
		session.update("delete from userinfo where toid=?", id);
		SessionFactory.closeSession(session);
	}

}
