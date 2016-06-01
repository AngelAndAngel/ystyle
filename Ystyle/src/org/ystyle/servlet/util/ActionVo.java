package org.ystyle.servlet.util;

import java.util.Map;

/**
 * action配置中对action的标示
 * 
 * @author duyunfei
 * 
 */
public class ActionVo {

	private String name;
	private String method;
	private String className;
	private Map<String,Result> results;


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public Map<String, Result> getResults() {
		return results;
	}

	public void setResults(Map<String, Result> results) {
		this.results = results;
	}


}
