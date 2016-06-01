package org.ystyle.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;
import org.ystyle.db.ConnectionPool;

public class AppListener implements ServletContextListener {
	private static final Logger logger = Logger.getLogger(AppListener.class);

	@Override
	public void contextInitialized(ServletContextEvent event) {
		// init db pool
		ConnectionPool.instance();
		ServletContext ctx = event.getServletContext();
		ctx.setAttribute("ctx", ctx.getContextPath());		
	}

	@Override
	public void contextDestroyed(ServletContextEvent event) {
	}
}
