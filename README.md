
# ystyle介绍 

Ystyle是本人好几年前写的一个mvc框架(拥有类似struts2+spring/aop+DB功能),现在重新整理了一下，并写了一个完整的CURD功能（包括页面和数据库），放到github上来，这里先说下大致的模块及API。


* 控制层核心API：org.ystyle.servlet.MainFilter
* 容器管理及bean缓存：org.ystyle.Container
* 注解定义package：org.ystyle.Annotation
* 注解功能列表如下
    * @Autowired:自动注入
    * @Proxy:生成代理（基于JDK动态代理）
    * @Service:Service层注解
    * @SingleTon:缓存bean为单例
    * @Transactional:事务拦截
    * @UploadFile:自动上传并可指定文件夹或作用域内的属性
   变量，比如${param.xxx}/${requestScope.xxx}/${sessionScope.xxx}/       
    * 属性转换器(package)：org.ystyle.converter
      * 主要实现在form表单自动注入值时的数据转换，比如
        * 字符串-->数字
        * 字符串-->日期
        * 二进制流-->文件<br/>
       用户可以自定义转换器并配置在config.xml中

* 数据库连接池及Session:org.ystyle.db,
  这个package下的内容起始没有写完，目前实现了自己的连 接池(SimpleDataSource)以及配置第三方连接池，Session 
  接口及实现(DefaultSession)          

* 代理工厂及实现(package)：org.ystyle.ProxyFactory
    
* 其他重要组件：
  * 为了截获上传数据，包装了request，即MulRequestWraper
  * 为了解决重复提交问题，实现了一个tag，即TokenTag

# IT攻城狮
  IT攻城狮公众号汇集了众多原创技术文章，内容涵盖Java体系，分布式架构，前端开发，NodeJS等热门技术栈!<br/>
  另：ystyle框架详细设计思路将全部发在IT攻城狮公众号上，希望大家关注并交流！
  
![IT攻城狮](http://www.easyle.net/images/ewm.jpg)



