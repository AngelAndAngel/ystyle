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
	    <script type="text/javascript" src="${ctx}/js/calendar/WdatePicker.js"></script>
	  
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
  <div class="panel-heading">添加员工
  </div>
  <div class="panel-body">
      <form id="actform" method="post" action="${ctx}/admin/addUser.action" role="form">
  <div class="form-group">
    <label for="username">姓名</label>
    <input type="text" class="form-control"  style="width:30%;" id="username" name="userinfo.username">
  </div>
  
   <div class="form-group">
    <label for="starttime">上班时间</label>
    <input type="text"  value="9:00" class="form-control "  onclick="WdatePicker({dateFmt:'HH:mm',isShowToday:false,isShowOthers:false})" style="width:30%;" id="starttime" name="userinfo.starttime">
   
  </div>

   <div class="form-group">
    <label for="starttime">下班时间</label>
    <input type="text" value="18:00" class="form-control"  onclick="WdatePicker({isShowWeek:false,dateFmt:'HH:mm'})" style="width:30%;" id="endtime" name="userinfo.endtime">
  </div>
  <button type="submit" class="btn btn-default">Submit</button>
</form>

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
