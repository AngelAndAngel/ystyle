package org.ystyle.utils;


public class ActionReplaceHolder implements ReplaceHolder {

	private static ActionReplaceHolder arh=new ActionReplaceHolder();
	private ActionReplaceHolder(){
		
	}
	public static ActionReplaceHolder getInstance(){
		return arh;
	}
	@Override
	public String extract(String value) {
		if(value.indexOf("requestScope.")!=-1){
			String prop=value.substring(value.indexOf("requestScope.")+"requestScope.".length());
			return (String)ActionContext.getRequest().getAttribute(prop);
		}else if(value.indexOf("sessionScope.")!=-1){
			String prop=value.substring(value.indexOf("sessionScope.")+"sessionScope.".length());
			return (String)ActionContext.getRequest().getSession().getAttribute(prop);
		}else if(value.indexOf("param.")!=-1){
			String prop=value.substring(value.indexOf("param.")+"param.".length());
			return ActionContext.getRequest().getParameter(prop);
		}
		return value;
	}

}
