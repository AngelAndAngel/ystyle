package org.ystyle.ProxyFactory;

import java.lang.reflect.Method;
import java.sql.Connection;

import org.ystyle.db.SimpleDataSource;

public class ConnProxyFactory extends DefaultProxyFactory {
	private SimpleDataSource dataSource;

	public ConnProxyFactory(SimpleDataSource dataSource) {
		this.dataSource = dataSource;
	}

	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		Object returnValue = null;
		if (method.getName().equals("close")) {
			dataSource.closeConnection((Connection)proxy);
		} else {
			returnValue = method.invoke(this.getTargetObject(), args);
		}
		return returnValue;
	}


}
