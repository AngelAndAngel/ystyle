package org.ystyle.create;

import java.sql.Connection;

import org.ystyle.create.CreateService;
import org.ystyle.action.BaseAction;
import org.ystyle.db.Session;
import org.ystyle.db.SessionFactory;

public class CreateAction extends BaseAction {

	public String createPojos() {
		String packageName=request.getParameter("packageName");
		String tablename=request.getParameter("tablename");
		String srcdir=request.getParameter("srcdir");
		String superClass=request.getParameter("superClass");
		Session session=SessionFactory.getSession();
		Connection conn=session.getConnection();
		try {
			String[] tablenameArr=tablename.split(",");
			for(String tn:tablenameArr){
				CreateService.generateCode(packageName, null,tn, srcdir, superClass,conn);	
			}
		    
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			SessionFactory.closeSession(session);
		}
		return "success";
	}

}
