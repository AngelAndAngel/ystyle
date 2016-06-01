package org.ystyle.servlet;

import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

public class MulRequestWraper extends HttpServletRequestWrapper {

	private Map<String, Object[]> paramMap = new HashMap<String, Object[]>();

	public MulRequestWraper(HttpServletRequest request) {
		super(request);
      
		try {
			String  encoding=(request.getCharacterEncoding()==null?"UTF-8":request.getCharacterEncoding());
			FileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(factory);
			List<FileItem> items = upload.parseRequest(request);
			
			Iterator<FileItem> iter = items.iterator();
            while (iter.hasNext()) {
				FileItem item = (FileItem) iter.next();

				String fieldName = item.getFieldName();
				if (paramMap.containsKey(fieldName)) {
					Object[] paramValue = paramMap.get(fieldName);

					// 构造同类数组
					Object[] paramValueTemp = (Object[]) Array.newInstance(
							paramValue[0].getClass(), paramValue.length + 1);
					for (int i = 0; i < paramValue.length; i++) {
						paramValueTemp[i] = paramValue[i];
					}

					if (item.isFormField()) {
						paramValueTemp[paramValueTemp.length - 1] = item
								.getString(encoding);
					} else {
						if (item.getSize() > 0) {
							paramValueTemp[paramValueTemp.length - 1] = item;
						}
					}

					paramMap.put(fieldName, paramValueTemp);
				} else {
					if (item.isFormField()) {
						paramMap.put(fieldName,
								new String[] { item.getString(encoding) });
					} else {
						if (item.getSize() > 0) {
							paramMap.put(fieldName, new FileItem[] { item });
						}
					}

				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public String getParameter(String name) {
		Object[] values= paramMap.get(name);
		if(values!=null && values.length>0){
			return (String)values[0];
		}
		return super.getParameter(name);
	}

	public String[] getParameterValues(String name) {
		Object[] values= paramMap.get(name);
		if(values!=null){
			return (String[])values;
		}
		return super.getParameterValues(name);
	}
	
	
	
	public Map getParameterMap() {
		
		paramMap.putAll(super.getParameterMap());
		return paramMap;
	}

}
