供对ResultSet 中的值的包装过滤，所有类都实现了java.lang.reflect.InvocationHandler 。其功能比如将ResultSet 中所有String 值进行trim() 操作，或是把所有null值赋一个有意义的新值。
