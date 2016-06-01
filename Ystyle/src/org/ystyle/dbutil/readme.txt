依赖：DbUtils以一个独立的Jar包形式发布，仅仅依赖不低于1.5版本的Java。


DbUtils核心其实只有三个类/接口，即QueryRunner 、ResultSetHandler 和DbUtils （官方文档中写的是前两个）

一．下面先过一下DbUtils的几个包（package）：
1.org.apache.commons.dbutils 提供核心类/接口，提供最常用最通用的功能。
2.org.apache.commons.dbutils.handlers　提供对ResultSet 的各种形式的包装，所有类都实现了ResultSetHandler ，因此也可以看做是ResultSetHandler 的延伸。其功能比如把ResultSet 中每一行变成一个JavaBean或一个数组等。
3.org.apache.commons.dbutils.wrappers 提供对ResultSet 中的值的包装过滤，所有类都实现了java.lang.reflect.InvocationHandler 。其功能比如将ResultSet 中所有String 值进行trim() 操作，或是把所有null值赋一个有意义的新值。

二．下面是对DbUtils各类的使用进行介绍：
1.org.apache.commons.dbutils.DbUtils
DbUtils类是一个工具类，里面所有方法都是静态的，因此无需实例化即可调用，通过API介绍即可了解其功能：
    * close ：关闭Connection 、Statement 或ResultSet ，忽略null；
    * closeQuietly ：安静的关闭Connection 、Statement 或ResultSet ，它会尽力去关闭，忽略null和SQLException ；
    * commitAndClose ：提交并关闭Connection ，忽略null；
    * commitAndCloseQuietly ：提交并关闭Connection ，忽略null和SQLException ；
    * loadDriver ：和Class.forName().newInstance() 功能一样，但它能捕获异常，并返回true 或false ；
    * printStackTrace ：打印SQLException 的详细错误信息；
    * printWarnings ：打印Connection 的警告信息；
    * rollback ：回滚操作，忽略null；
    * rollbackAndClose ：回滚操作并关闭，忽略null；
    * rollbackAndCloseQuietly ：回滚操作并关闭，忽略null、SQLException 。
2.org.apache.commons.dbutils.QueryRunner 
    * batch ：执行成批的INSERT、UPDATE、DELETE操作；
    * fillStatement ：用Object[] 或JavaBean的值填充PreparedStatement 中的占位符；
    * query ：执行查询操作（Statement 或PreparedStatement 均可），并用ResultSetHandler 来处理ResultSet ；
    * update ：执行INSERT或UPDATE操作（Statement 或PreparedStatement 均可）。
3.org.apache.commons.dbutils.ResultSetHandler　所有在DbUtils里提供的的ResultSetHandler 实现都是线程安全的
    * ArrayHandler ：把ResultSet 第一行包装成Object[] ；
    * ArrayListHandler ：把ResultSet包装成List<Object[]> ；
    * BeanHandler ：把ResultSet 第一行包装成一个JavaBean；
    * BeanListHandler ：把ResultSet 第一行包装成一个List<JavaBean> ；
    * ColumnListHandler ：抽取ResultSet 指定的列，以List<Object> 对象的形式返回，默认第一列；
    * KeyedHandler ：包装ResultSet ，以Map<Object,Map<String,Object>> 对象形式返回，第一个Object 是指定的列值，第二个Map 中String 是列名；
    * MapHandler ：把ResultSet 第一行包装成Map<String, Object> ；
    * MapListHandler ：把ResultSet包装成List<Map<String,Object>> ；
    * ScalarHandler ：抽取ResultSet 第一行指定列，以Object 对象形式返回。
4.org.apache.commons.dbutils.wrappers.SqlNullCheckedResultSet 对ResultSet 中的null值进行处理。
5.org.apache.commons.dbutils.wrappers.StringTrimmedResultSet 对ResultSet 中的String 进行trim() 处理。

