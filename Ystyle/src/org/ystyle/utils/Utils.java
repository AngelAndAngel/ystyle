package org.ystyle.utils;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Collection;
import java.util.Enumeration;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.apache.commons.beanutils.BeanUtils;

public class Utils {

	private static final String PLACEHOLDER_START = "${";
	public static void copyFile(File source,File des){
		   FileInputStream fis = null;
		   FileOutputStream fos = null;
		   try {
		    fis = new FileInputStream(source);
		    fos = new FileOutputStream(des);

		    byte[] bb = new byte[ (int) source.length()];
		    fis.read(bb);
		    fos.write(bb);

		   } catch (IOException e) {
		    e.printStackTrace();
		   } finally {
		    try {
		     fis.close();
		     fos.close();
		    } catch (IOException e) {
		     e.printStackTrace();
		    }
		   }

	}
	
	public static String join(String[] arr,String delims){
		if(arr==null || arr.length==0){
			return "";
		}
		if(delims==null){
			delims=",";
		}
		String result="";
		for(String s:arr){
			if("".equals(result)){
				result=s;
			}else{
				result=result+delims+s;
			}
		}
		return result;
	}
	
	
	/**
	 * 打印每个对象的值，用来测试
	 */
	public static void printFields(Object obj) {
		Class cls = obj.getClass();
		Field[] fields = cls.getDeclaredFields();
		System.out.println("----------------" + cls.getName()
				+ "---------------");
		try {
			for (Field field : fields) {
				String[] fieldvalue=null;
				if(field.getType().isArray()){
					fieldvalue= BeanUtils.getArrayProperty(obj,field.getName());
				}else{
					fieldvalue=new String[1];
					fieldvalue[0]=BeanUtils.getProperty(obj, field.getName());
				}
				System.out.println(field.getName() + " : " + join(fieldvalue,","));
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	/**
	 * 打印列表中每个对象的值，用来测试
	 */

	public static void printListFields(Collection list) {
		for (Object obj : list) {
			System.out
					.println("===============================================");
			if (obj instanceof String) {
				System.out.println(obj);
			} else {
				printFields(obj);
			}
		}
	}
	
	/**
	 * 解析占位符具体操作
	 * @param property
	 */
	public static String resolvePlaceHolder(String property,ReplaceHolder rh) {
		if ( property.indexOf( PLACEHOLDER_START ) < 0 ) {
			return property;
		}
		StringBuffer buff = new StringBuffer();
		char[] chars = property.toCharArray();
		for ( int pos = 0; pos < chars.length; pos++ ) {
			if ( chars[pos] == '$' ) {
				if ( chars[pos+1] == '{' ) {
					String propertyName = "";
					int x = pos + 2;
					for (  ; x < chars.length && chars[x] != '}'; x++ ) {
						propertyName += chars[x];
						if ( x == chars.length - 1 ) {
							throw new IllegalArgumentException( "unmatched placeholder start [" + property + "]" );
						}
					}
					String systemProperty = rh.extract( propertyName );
					buff.append( systemProperty == null ? "" : systemProperty );
					pos = x + 1;
					if ( pos >= chars.length ) {
						break;
					}
				}
			}
			buff.append( chars[pos] );
		}
		String rtn = buff.toString();
		return isEmpty( rtn ) ? null : rtn;
	}
	
	/**
	 * 判断字符串的空(null或者.length=0)
	 * @param string
	 */
	public static boolean isEmpty(String string) {
		return string == null || string.trim().equals("");
	}
	
	/**
	 * 通过包名得到所有class
	 * @param packageName
	 */
	/**
	 * 从包package中获取所有的Class
	 * 
	 * @param pack
	 */
	public static Set<Class<?>> getClasses(String pack) {

		// 第一个class类的集合
		Set<Class<?>> classes = new LinkedHashSet<Class<?>>();
		// 是否循环迭代
		boolean recursive = true;
		// 获取包的名字 并进行替换
		String packageName = pack;
		String packageDirName = packageName.replace('.', '/');
		// 定义一个枚举的集合 并进行循环来处理这个目录下的things
		Enumeration<URL> dirs;
		try {
			dirs = Thread.currentThread().getContextClassLoader().getResources(
					packageDirName);
			// 循环迭代下去
			while (dirs.hasMoreElements()) {
				// 获取下一个元素
				URL url = dirs.nextElement();
				// 得到协议的名称
				String protocol = url.getProtocol();
				// 如果是以文件的形式保存在服务器上
				if ("file".equals(protocol)) {
					// 获取包的物理路径
					String filePath = URLDecoder.decode(url.getFile(), "UTF-8");
					// 以文件的方式扫描整个包下的文件 并添加到集合中
					findAndAddClassesInPackageByFile(packageName, filePath,
							recursive, classes);
				} else if ("jar".equals(protocol)) {
					// 如果是jar包文件
					// 定义一个JarFile
					JarFile jar;
					try {
						// 获取jar
						jar = ((JarURLConnection) url.openConnection())
								.getJarFile();
						// 从此jar包 得到一个枚举类
						Enumeration<JarEntry> entries = jar.entries();
						// 同样的进行循环迭代
						while (entries.hasMoreElements()) {
							// 获取jar里的一个实体 可以是目录 和一些jar包里的其他文件 如META-INF等文件
							JarEntry entry = entries.nextElement();
							String name = entry.getName();
							// 如果是以/开头的
							if (name.charAt(0) == '/') {
								// 获取后面的字符串
								name = name.substring(1);
							}
							// 如果前半部分和定义的包名相同
							if (name.startsWith(packageDirName)) {
								int idx = name.lastIndexOf('/');
								// 如果以"/"结尾 是一个包
								if (idx != -1) {
									// 获取包名 把"/"替换成"."
									packageName = name.substring(0, idx)
											.replace('/', '.');
								}
								// 如果可以迭代下去 并且是一个包
								if ((idx != -1) || recursive) {
									// 如果是一个.class文件 而且不是目录
									if (name.endsWith(".class")
											&& !entry.isDirectory()) {
										// 去掉后面的".class" 获取真正的类名
										String className = name.substring(
												packageName.length() + 1, name
														.length() - 6);
										try {
											// 添加到classes
											classes.add(Class
													.forName(packageName + '.'
															+ className));
										} catch (ClassNotFoundException e) {
											// log
											// .error("添加用户自定义视图类错误 找不到此类的.class文件");
											e.printStackTrace();
										}
									}
								}
							}
						}
					} catch (IOException e) {
						// log.error("在扫描用户定义视图时从jar包获取文件出错");
						e.printStackTrace();
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return classes;
	}
	
	/**
	 * 以文件的形式来获取包下的所有Class
	 * 
	 * @param packageName
	 * @param packagePath
	 * @param recursive
	 * @param classes
	 */
	public static void findAndAddClassesInPackageByFile(String packageName,
			String packagePath, final boolean recursive, Set<Class<?>> classes) {
		// 获取此包的目录 建立一个File
		File dir = new File(packagePath);
		// 如果不存在或者 也不是目录就直接返回
		if (!dir.exists() || !dir.isDirectory()) {
			// log.warn("用户定义包名 " + packageName + " 下没有任何文件");
			return;
		}
		// 如果存在 就获取包下的所有文件 包括目录
		File[] dirfiles = dir.listFiles(new FileFilter() {
			// 自定义过滤规则 如果可以循环(包含子目录) 或则是以.class结尾的文件(编译好的java类文件)
			public boolean accept(File file) {
				return (recursive && file.isDirectory())
						|| ((file.getName().endsWith(".class"))&&file.getName().indexOf("$")<0);
			}
		});
		// 循环所有文件
		for (File file : dirfiles) {
			// 如果是目录 则继续扫描
			if (file.isDirectory()) {
				findAndAddClassesInPackageByFile(packageName + "."
						+ file.getName(), file.getAbsolutePath(), recursive,
						classes);
			} else {
				// 如果是java类文件 去掉后面的.class 只留下类名
				String className = file.getName().substring(0,
						file.getName().length() - 6);
				try {
					// 添加到集合中去
					//classes.add(Class.forName(packageName + '.' + className));
                    //这里用forName有一些不好，会触发static方法，没有使用classLoader的load干净
                    classes.add(Thread.currentThread().getContextClassLoader().loadClass(packageName + '.' + className));  
                               } catch (ClassNotFoundException e) {
					// log.error("添加用户自定义视图类错误 找不到此类的.class文件");
					e.printStackTrace();
				}
			}
		}
	}
}
