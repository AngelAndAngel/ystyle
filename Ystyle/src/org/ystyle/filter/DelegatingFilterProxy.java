package org.ystyle.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.ystyle.Container.BeanContainer;

/**
 * Filter的代理类，系统所有的Filter共用一个Filter
 * 
 * @author angel
 * 
 */
public class DelegatingFilterProxy implements Filter {

	private Filter targetFilterBean;

	// http://blog.csdn.net/defonds/article/details/7191546
	public void destroy() {

	}

	public void doFilter(ServletRequest arg0, ServletResponse arg1,
			FilterChain arg2) throws IOException, ServletException {
		targetFilterBean.doFilter(arg0, arg1, arg2);
	}

	public void init(FilterConfig arg0) throws ServletException {
		String targetFilterBeanStr = arg0.getInitParameter("targetFilterBean");
		try {
			targetFilterBean = (Filter) Class.forName(targetFilterBeanStr)
					.newInstance();
			BeanContainer.instince().AutowiredSet(targetFilterBean);
			targetFilterBean.init(arg0);
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

}
