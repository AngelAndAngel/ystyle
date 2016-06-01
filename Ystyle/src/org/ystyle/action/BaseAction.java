package org.ystyle.action;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializeConfig;

/**
 * 提供基础action，用于被业务action继承
 * 本类提供的功能有 1,设置:
 *                 request
 *                 response
 *                 session
 *                 servletContext; 
 *                2,提供java对象与json的转化功能 
 * @author duyunfei
 *
 */
public class BaseAction implements ContextAction {

	public final static String SUCCESS = "success";
	public final static String ERROR = "error";
	protected HttpServletRequest request;
	protected HttpServletResponse response;
	protected HttpSession session;
	protected ServletContext servletContext;

	public static final String CONTNET_TYPE_XML = "xml"; // 以xml响应输出
	public static final String CONTNET_TYPE_JSON = "json"; // 以json响应输出
	public static final String CONTNET_TYPE_TEXT = "text"; // 以text响应输出

	public static final String RESPONSE_TYPE_XML = "text/xml";
	public static final String RESPONSE_TYPE_JSON = "application/json";
	public static final String RESPONSE_TYPE_TEXT = "text/plain";

	public static final String DEAULT_CHARSET = "UTF-8";

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}

	public void setServletContext(ServletContext context) {
		this.servletContext = context;
	}

	public void setSession(HttpSession session) {
		this.session = session;
	}

	protected void doAjaxXml(String xmlbody) throws IOException {

		doAjax(xmlbody, RESPONSE_TYPE_XML);
	}

	protected void doAjaxText(String text) throws IOException {

		doAjax(text, RESPONSE_TYPE_TEXT);

	}


	protected void doAjax(String body, String type) throws IOException {

		request.setCharacterEncoding(DEAULT_CHARSET);
		response.setContentType(type + ";charset=" + DEAULT_CHARSET);
		response.setHeader("Cache-Control", "no-cache");
		if (RESPONSE_TYPE_XML.equals(type)) {
			if (!body.startsWith("<?xml")) {
				StringBuffer b = new StringBuffer();
				b.append("<?xml version=\"1.0\" encoding=\"" + DEAULT_CHARSET
						+ "\"?>\n");
				b.append(body);
				body = b.toString();
			}
		}

		response.getWriter().write(body);
		response.flushBuffer();

	}

	/**
	 * 处理ajax需要转换对象与json
	 * 
	 * @param list
	 *            对象列表���Դ���list����
	 * @param config
	 * @throws Exception
	 */
	protected void doAjaxJson(List list, SerializeConfig config) throws Exception {
		String data = JSON.toJSONString(list,config);
		request.setCharacterEncoding("UTF-8");
		response.setContentType("application/json;charset=UTF-8");
		response.setHeader("Cache-Control", "no-cache");
		response.getWriter().write(data);
		response.flushBuffer();
	}

	/**
	 * 处理ajax需要转换对象与json
	 * 
	 * @param instance
	 *            对象���Դ���list����
	 * @param config
	 * @throws Exception
	 */
	protected void doAjaxJson(Object instance, SerializeConfig config)
			throws Exception {
		String data = JSON.toJSONString(instance,config);
		request.setCharacterEncoding("UTF-8");
		response.setContentType("application/json;charset=UTF-8");
		response.setHeader("Cache-Control", "no-cache");
		response.getWriter().write(data);
		response.flushBuffer();
	}

	private static SerializeConfig mapping = new SerializeConfig();
	static {
		//mapping.put(Date.class, new SimpleDateFormatSerializer("yyyy-MM-dd HH:mm:ss"));
	}
	/**
	 * List和Object都支持 采用fastjson
	 * 
	 * @param instance
	 * @throws Exception
	 */
	protected void doAjaxJson(Object instance) throws Exception {
		String data = JSON.toJSONStringWithDateFormat(instance, "yyyy-MM-dd HH:mm:ss");
		request.setCharacterEncoding("UTF-8");
		response.setContentType("application/json;charset=UTF-8");
		response.setHeader("Cache-Control", "no-cache");
		response.getWriter().write(data);
		response.flushBuffer();
	}
	
	protected String getPath() {
		String path = request.getContextPath();
		return (path == null || path.length() == 0) ? "/" : path;
	}
}
