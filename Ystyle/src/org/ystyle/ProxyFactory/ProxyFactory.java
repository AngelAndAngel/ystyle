package org.ystyle.ProxyFactory;

import java.lang.reflect.InvocationHandler;

/**
 * 代理工厂总接口
 * 所有自定义代理工厂必须实现此接口
 * @author 杜云飞
 *
 */
public interface ProxyFactory extends InvocationHandler {
	public Object factory(Object targetObject,Object params);
	public Object getTargetObject();
	public Object getParams();
}
