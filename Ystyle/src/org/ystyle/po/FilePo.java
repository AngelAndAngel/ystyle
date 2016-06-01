package org.ystyle.po;

import java.io.File;
import java.io.Serializable;

/**
 * 表单上传类
 * 
 * @author duyf
 * 
 */
public class FilePo implements Serializable {

	// 文件名 带后缀
	private String filename;

	// 相对于web的路径
	private String webpath;

	// 实际文件
	private File file;

	// 类型
	private String contentType;
	
	//大小 kb
	private double size;

	public double getSize() {
		return size;
	}

	public void setSize(double size) {
		this.size = size;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getWebpath() {
		return webpath;
	}

	public void setWebpath(String webpath) {
		this.webpath = webpath;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public String toString() {
		return "\nfilename:" + this.filename + "\n webpath:" + this.webpath
				+ "\n contentType:" + this.contentType + "\n size:" + size+ "kb\n file:" + file;
	}

}
