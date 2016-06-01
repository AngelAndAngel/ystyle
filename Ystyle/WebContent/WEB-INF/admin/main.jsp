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
            <li class="active"><a href="${ctx}/admin/manager.action">导入数据</a></li>
            <li><a href="${ctx}/admin/toUserList.action">人员信息</a></li>
          </ul>
        </div><!-- /.nav-collapse -->
      </div><!-- /.container -->
    </div><!-- /.navbar -->

    <div class="container" style="padding-top:60px;">

      <div class="row">
         <div class="col-md-9"  >
             <div class="panel panel-default">
  <!-- Default panel contents -->
  <div class="panel-heading">上传数据
  &nbsp;
  </div>
  <div class="panel-body">
    <p>
        <form role="form" action="${ctx}/admin/upload.action" method="post" enctype="multipart/form-data">
 
         <div class="form-group">
           <label for="exampleInputFile1">文件1</label>
           <input type="file" id="exampleInputFile1" name="formVo.sourceFile">
        </div>
        
        <div class="form-group">
           <label for="exampleInputFile2">文件2</label>
           <input type="file" id="exampleInputFile2" name="formVo.sourceFile">
        </div>
  
       <button type="submit" class="btn btn-default">上传文件</button>
</form>
    </p>
    <p></p>
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
