package org.ystyle.ProxyFactory;

import java.lang.reflect.Method;

import org.apache.log4j.Logger;
import org.ystyle.Annotation.Transactional;
import org.ystyle.db.Session;
import org.ystyle.db.SessionFactory;
import org.ystyle.db.TransactionManager;

/**
 * service层代理的工厂
 * 
 * @author Administrator
 * 
 */
public class ServiceProxyFactory extends DefaultProxyFactory {
	private static final Logger logger = Logger.getLogger(ServiceProxyFactory.class);
	
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		Object returnValue = null;
		/* 处理事务注解 start */
		Class targetClass = this.getTargetObject().getClass();
		Method targetMethod = targetClass.getMethod(method.getName(), method
				.getParameterTypes());
		Transactional tran = targetMethod.getAnnotation(Transactional.class);
		if (tran != null) {
			/* 说明此方法已使用了Transactional的注解，则需要在方法完成后提交事务 */
			logger.debug("开启事务  :"+targetClass.getName()+"."+method);
			Session session=SessionFactory.getSession();
			TransactionManager transactionManager=session.beginTransaction();
			try {
				returnValue = method.invoke(this.getTargetObject(), args);
				transactionManager.commit();
				logger.debug("事务已提交");
			} catch (Exception ex) {
				logger.debug("事务已回滚");
				transactionManager.rollback();
				Exception e0=new Exception();
	        	e0.initCause(ex);
	        	throw e0;
			}
			SessionFactory.closeSession(session);
		}else{
			returnValue = method.invoke(this.getTargetObject(), args);
		}
		/* 处理事务注解 end */

		return returnValue;
	}

}
