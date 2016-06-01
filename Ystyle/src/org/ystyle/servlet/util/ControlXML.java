package org.ystyle.servlet.util;
import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.ystyle.converter.DateConverter;
import org.ystyle.converter.FileConverter;
import org.ystyle.converter.IntegerConverter;
import org.ystyle.converter.TypeConverter;

/**
 * 用来解析控制器的xml文件
 * 
 * @author Administrator
 * 
 */
public class ControlXML {

	public final static String CONTROL_CONFIG = "control.xml";

	private static ControlXML controlXml = new ControlXML();

	private Map<String, Namespace> namespaces = new HashMap<String, Namespace>();

	private Map<String,TypeConverter> convertMap=new HashMap<String, TypeConverter>();
	
	private Map<String,Result> globalResults=new HashMap<String, Result>();
	
	public Map<String, Result> getGlobalResults() {
		return globalResults;
	}

	private ControlXML() {
		convertMap.put("java.util.Date", new DateConverter());
		convertMap.put("java.lang.Integer", new IntegerConverter());
		convertMap.put("org.ystyle.po.FilePo", new FileConverter());
	}

	public static ControlXML getInstance() {
		return controlXml;
	}

	public void readXml(String xmlurl) throws DocumentException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		/* 读取xml文件 */
		SAXReader reader = new SAXReader();
		Document document = reader.read(new File(xmlurl));
		Element rootElement = document.getRootElement();

		/* 读取namespace配置（包含result配置） start */
		List<Element> namespaces_list = rootElement.elements("namespace");
		for (Element namespace : namespaces_list) {
			/* 读取action后的集合 */
			Map<String, ActionVo> actions = new HashMap<String, ActionVo>();

			/* 读取action配置（包含result配置） start */
			List actions_list = namespace.elements("action");
			for (Iterator it = actions_list.iterator(); it.hasNext();) {
				Element action = (Element) it.next();
				ActionVo avo = new ActionVo();
				avo.setName(action.attributeValue("name"));
				avo.setMethod(action.attributeValue("method"));
				avo.setClassName(action.attributeValue("class"));

				List<Element> avo_results = action.elements("result");
				Map<String,Result> list_result = new HashMap<String, Result>();
				for (Element result : avo_results) {
					Result rs = new Result();
					rs.setName(result.attributeValue("name"));
					rs.setType(result.attributeValue("type"));
					rs.setUrltext(result.getText().trim());
					list_result.put(rs.getName(),rs);
				}
				avo.setResults(list_result);
				actions.put(avo.getName(), avo);
			}
			/* 读取action配置（包含result配置） end */

			namespaces.put(namespace.attributeValue("name"), new Namespace(
					namespace.attributeValue("name"), actions));
		}
		/* 读取namespace配置（包含result配置） end */

		/*读取converter配置 start*/
		List<Element> converter_list = rootElement.elements("converter");
		for(Element convertElement:converter_list){
			String type=convertElement.attributeValue("type");
			String handle=convertElement.attributeValue("handle");
			TypeConverter tc=(TypeConverter)(Class.forName(handle).newInstance());
			convertMap.put(type,tc);
		}
		/*读取converter配置 end*/
		
		/* 读取 global-results start*/
		List<Element> global_results_list = rootElement.elements("global-results");
		if(global_results_list!=null && global_results_list.size()>0){
			Element global_results=global_results_list.get(0);
			List<Element> results=global_results.elements("result");
			for(Element result:results){
				Result rs = new Result();
				rs.setName(result.attributeValue("name"));
				rs.setType(result.attributeValue("type"));
				rs.setUrltext(result.getText().trim());
				globalResults.put(rs.getName(),rs);
			}	
		}	
		/* 读取 global-results end*/
		
		/* 后续会加上其他配置 */

	}

	public ActionVo getAction(String namespacename, String actionname) {
		if (namespaces == null || namespaces.isEmpty()) {
			throw new RuntimeException("请确保之前调用了readXml(xml)方法");
		}
		Namespace ns = namespaces.get(namespacename);
		ActionVo avo = null;
		if (ns != null) {
			avo=ns.getListActions().get(actionname);
		}
		
		return avo;
	}

	public static void main(String args[]) throws DocumentException {

		

	}

	public Map<String, TypeConverter> getConvertMap() {
		return convertMap;
	}

}
