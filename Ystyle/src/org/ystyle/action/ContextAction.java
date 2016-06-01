package org.ystyle.action;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 为action提供应用程序，请求，会话等相关的资源
 * @author Administrator
 *
 */
public interface ContextAction {
	public void setRequest(HttpServletRequest request);
	public void setResponse(HttpServletResponse response);
	public void setSession(HttpSession session);
	public void setServletContext(ServletContext context);	
}
