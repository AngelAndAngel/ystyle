package org.ystyle.Container;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import org.ystyle.Container.BeanContainer;
import org.ystyle.Annotation.Autowired;
import org.ystyle.Annotation.Proxy;
import org.ystyle.Annotation.Service;
import org.ystyle.Annotation.SingleTon;
import org.ystyle.ProxyFactory.ProxyFactory;

/**
 * Service或者DAO等层的容器 在对各级进行注入的时候 假如每次都去读取Autowired注解，然后装配注入，
 * 会造成很大的性能浪费，本容器用来缓存，提供各种需要注入的bean，假如容器中不存在，同时是单例类则读取，但是不缓存到本容器，假如不是单例，则
 * 读取并缓存起来以便下次读取相同的对象；假如容器存在，则直接返回。也就是 说本容器缓存起来的的都是单例对象
 * 这里的单例对象是指用@SingleTon注解标识的类
 * 
 * @author duyf
 * 
 */
public class BeanContainer {

	private static BeanContainer bc = new BeanContainer();

	private BeanContainer() {

	}

	public static BeanContainer instince() {
		return bc;
	}
	/* 缓存单例类 */
	private static Map<String, Object> autoObjMap = new ConcurrentHashMap<String, Object>();

	public Object getBean(String name) {
		if (autoObjMap.containsKey(name)) {
			// 在缓存中存在 则说明是单例类
			return autoObjMap.get(name);
		}

		return null;
	}
	
	public boolean containsKey(String name) {
		return autoObjMap.containsKey(name);
	}
	
	public void saveBean(String name,Object obj){
		 autoObjMap.put(name,obj);
	}
	public String getMapInfo(){
		StringBuffer sb=new StringBuffer();
		sb.append("----------------------已缓存的bean--------------------------\n");
		Set<Entry<String,Object>> set=autoObjMap.entrySet();
		for(Entry<String,Object> ent:set){
			sb.append(ent.getKey()).append(" : ").append(ent.getValue()).append("\n");
		}
		return sb.toString();
	}

	/**
	 * 设置注入元素
	 * 
	 * @param obj
	 *            主对象
	 * @param cls
	 *            主对象的class对象 实现：根据反射得到每个field的注入情况
	 *            假如是自动注入，则根据autowire的iocClass属性来自动装配
	 */
	public void AutowiredSet(Object obj) {
		Class cls = obj.getClass();
	
		// 得到自定义的属性
		Field[] fields = cls.getDeclaredFields();
		try {
			for (Field f : fields) {
				// 循环判断是否有Autowired的自动注入field
				Autowired au = f.getAnnotation(Autowired.class);
				if (au != null) {
					// 假如此属性被注解为Autowired
					// 得到需要注入的实例class
					Class ioc = au.iocClass();
					// 构造set方法
					String setName = "set"
							+ f.getName().substring(0, 1).toUpperCase()
							+ f.getName().substring(1);

					// 得到action set的方法以及它的参数类型，注意此时不能是参数子类的类型。
					Method setMethod = cls.getMethod(setName, new Class[] { f
							.getType() });

					Object iocObj = null;

					if (autoObjMap.containsKey(ioc.getName())) {
						iocObj=getBean(ioc.getName());
						setMethod.invoke(obj, iocObj);
						/*if(iocObj instanceof java.lang.reflect.Proxy){
							//假如你注入的bean是被代理的 那么你得找出原有主体类来进行操作
							java.lang.reflect.Proxy iocProxy=(java.lang.reflect.Proxy)iocObj;
							ProxyFactory pf=(ProxyFactory)iocProxy.getInvocationHandler(iocProxy);
							Object targetObj=pf.getTargetObject();
							System.out.println("iocProxy: "+targetObj);
							AutowiredSet(targetObj);
						}else{
							AutowiredSet(iocObj);
						}
                                                                                                       
						*/
						continue;
					}
					iocObj = ioc.newInstance();
					//先把Field注入，再做代理
					AutowiredSet(iocObj);
					// 没有标记注解，则为普通注入
					Object setObject = setProxyObject(iocObj);
					setMethod.invoke(obj, setObject);
				}

			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
    public Object setProxyObject(Object srcObj) throws Exception{
    	Object setObject=srcObj;
    	Service service = srcObj.getClass().getAnnotation(
				Service.class);
    	Proxy proxy = srcObj.getClass().getAnnotation(Proxy.class);
		if (service != null) {
			// 利用Service层的代理构建出被注入实例的代理对象，执行set方法注入进去。
			Object proxyFactoryClass = service.proxyFactoryClass()
					.newInstance();
			setObject = setProxyObject(proxyFactoryClass, srcObj,null);
		} 
		if (proxy != null) {
			// 对非service的类进行代理扩展
			Object proxyFactoryClass = proxy.proxyFactoryClass()
					.newInstance();
			String params=proxy.params();
			setObject = setProxyObject(proxyFactoryClass, setObject,params);
			
		} 
		return setObject;
    }
	public Object setProxyObject(Object proxyFactoryClass, Object obj,Object params) {
		checkProxy(proxyFactoryClass);
		ProxyFactory pf = (ProxyFactory) proxyFactoryClass;
		return pf.factory(obj,params);
	}

	public boolean checkSingle(Object obj) {
		SingleTon single = obj.getClass().getAnnotation(SingleTon.class);
		if (single == null) {
			return false;
		}
		return true;
	}

	public void checkProxy(Object proxyFactoryClass) {
		if (!(proxyFactoryClass instanceof ProxyFactory)) {
			throw new RuntimeException("所提供的代理类[" + proxyFactoryClass
					+ "]必须实现ProxyFactory接口");
		}
	}

}
