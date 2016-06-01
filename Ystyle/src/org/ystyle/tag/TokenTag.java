package org.ystyle.tag;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspContext;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.ystyle.utils.ActionContext;

public class TokenTag extends SimpleTagSupport {
	public void doTag() throws JspException, IOException {
		JspContext jc = super.getJspContext();
		JspWriter writer = jc.getOut();
		HttpServletRequest request=ActionContext.getRequest();
		HttpSession session=request.getSession();
		
		//生成令牌
		String sToken = UUID.randomUUID().toString().toUpperCase();
		session.setAttribute("token",sToken);
        String html="<input type='hidden' id='client_token' name='client_token' value='"+sToken+"' />"; 
		writer.println(html);

	}
}
