package org.ystyle.servlet.util;

/**
 * action配置文件中对result的标示
 * @author duyunfei
 *
 */
public class Result {

	private String name;
	private String type;
	private String urltext;

	public Result() {

	}

	public Result(String name, String type, String urltext) {
		super();
		this.name = name;
		this.type = type;
		this.urltext = urltext;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUrltext() {
		return urltext;
	}

	public void setUrltext(String urltext) {
		this.urltext = urltext;
	}

}
