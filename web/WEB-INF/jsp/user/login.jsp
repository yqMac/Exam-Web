<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
  String path = request.getContextPath();
  String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>后台用户登录</title>
<script src="<%=path%>/html/js/jquery-1.4.2.min.js" type="text/javascript"></script>
<script src="<%=path%>/html/js/admin-login.js" type="text/javascript"></script>

  <script type="text/javascript">
    $(function(){
      if(window.parent!=window){
        window.parent.location.href="/user/login";
      }
    });
  </script>

<link href="<%=path%>/html/css/style-login.css" rel="stylesheet" type="text/css"/>


</head>
<body>
<div id="login">
  <div class="login">
  <form id="login-form"  method="post">
    <div class="login-left">
    <table>
      <tbody>
      <tr>
        <td>账号：</td>
        <td colspan="2"><input id="user.username" name="username" type="text"/></td>
      </tr>
      <tr>
        <td>密码：</td>
        <td colspan="2"><input id="user.userpwd" name="password" type="password"/></td>
      </tr>
      <tr>
        <td>验证：</td>
        <td><input id="verifycode" name ="checkcode" type="text"/></td>
        <td style="text-align: center"> <img src="verifycode" onclick="this.src='verifycode?ram='+Math.random()"/></td>
      </tr>
      </tbody>
    </table>
    </div>
    <div class="login-role">
    <input id="login-btn" type="submit" value="" />
    </div>
    </form>
  </div>
</div>
</body>
</html>