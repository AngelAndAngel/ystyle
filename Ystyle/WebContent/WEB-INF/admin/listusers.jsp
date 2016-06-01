<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
String path = request.getContextPath();
%>

<!DOCTYPE html>
<html>
	<head>
		<title>考勤</title>
		<jsp:include page="/includeJsAndCss.jsp"></jsp:include>
	</head>
  
  <body>
     <div class="navbar navbar-fixed-top navbar-inverse" role="navigation">
      <div class="container">
        <div class="navbar-header">
          <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
          <a class="navbar-brand" href="#">后台管理系统</a>
        </div>
        <div class="collapse navbar-collapse">
          <ul class="nav navbar-nav">
             <li ><a href="${ctx}/admin/manager.action">导入数据</a></li>
            <li class="active"><a href="${ctx}/admin/toUserList.action">人员信息</a></li>
          </ul>
        </div><!-- /.nav-collapse -->
      </div><!-- /.container -->
    </div><!-- /.navbar -->

    <div class="container" style="padding-top:60px;">

      <div class="row">
         <div class="col-md-9"  >
             <div class="panel panel-default">
  <!-- Default panel contents -->
  <div class="panel-heading">所有人员列表
  &nbsp;<a href="${ctx}/admin/toAddUser.action">添加员工</a>
  </div>
  <div class="panel-body">
       <table class="table table-striped">
          <tr><td>姓名</td><td>上班时间</td><td>下班时间</td><td>操作</td></tr> 
          <c:forEach var="user" items="${requestScope.users}">
          <tr><td>${user.username}</td><td>${user.starttime}</td><td>${user.endtime}</td>
          <td><a href="${ctx}/admin/toUpdateUser.action?toid=${user.toid}">修改</a>&nbsp;
          <a href="javascript:del('${user.toid}')">删除</a>&nbsp;
          </td>
          </tr> 
          </c:forEach>
       </table>
  </div>
</div>
         </div>
       
      </div><!--/row-->

      <hr>

      <footer>
        <p>&copy; Company 2013</p>
      </footer>

    </div><!--/.container-->
     
  </body>
</html>
<script type="text/javascript">
<!--
 function del(id){
    if(confirm("确定要删除此人？")){
        window.location.href="${ctx}/admin/deleteUser.action?id="+id;
    }
 }
//-->
</script>
