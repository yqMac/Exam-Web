<%--
  Created by IntelliJ IDEA.
  User: yqmac
  Date: 2016/5/7 0007
  Time: 17:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>错误页面</title>
</head>
<body>

<h1 style="text-align: center">
    <a href="javascript:window.parent.location.href='/user/login'">登录页面</a>&nbsp; <a href="/user/index">首页</a>
    <br/>
    <hr/>
    错误信息:${exception.message}
    <br/>
</h1>

</body>
</html>
