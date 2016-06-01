package org.ystyle.utils;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ActionContext {

	public final static String HTTPREQUEST = "httprequest";
	public final static String HTTPRESPONSE = "httpresponse";
	private static ThreadLocal<ActionContext> threadLocal = new ThreadLocal<ActionContext>();

	public static ActionContext getContext() {
		return (ActionContext) threadLocal.get();
	}

	public static void setContext(ActionContext context) {
		threadLocal.set(context);
	}

	public static HttpServletRequest getRequest() {
		return (HttpServletRequest) getContext().get(HTTPREQUEST);
	}

	public static HttpServletResponse getResponse() {
		return (HttpServletResponse) getContext().get(HTTPRESPONSE);
	}

	
	
	public ActionContext() {

	}

	public ActionContext(Map<String, Object> context) {
		this.context = context;
	}

	/* 对象属性 start */
	private Map<String, Object> context = new HashMap<String, Object>();

	/* 对象属性 end */

	/* 对象方法 start */
	public Object get(String key) {
		return context.get(key);
	}

	public Object put(String key, Object value) {
		return context.put(key, value);
	}
	/* 对象方法 end */
}
