<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";

%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>个人信息</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <link href="<%=path%>/html/scripts/ui/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css"/>
    <link href="<%=path%>/html/images/style.css" rel="stylesheet" type="text/css"/>
    <script type="text/javascript" src="<%=path%>/html/scripts/jquery/jquery-1.3.2.min.js"></script>
    <script type="text/javascript" src="<%=path%>/html/scripts/jquery/jquery.form.js"></script>
    <script type="text/javascript" src="<%=path%>/html/scripts/jquery/jquery.validate.min.js"></script>
    <script type="text/javascript" src="<%=path%>/html/scripts/jquery/messages_cn.js"></script>
    <script type="text/javascript" src="<%=path%>/html/scripts/ui/js/ligerBuild.min.js"></script>
    <script type='text/javascript' src="<%=path%>/html/scripts/swfupload/swfupload.js"></script>
    <script type='text/javascript' src="<%=path%>/html/scripts/swfupload/swfupload.queue.js"></script>
    <script type="text/javascript" src="<%=path%>/html/scripts/swfupload/swfupload.handlers.js"></script>
    <script type="text/javascript" src="<%=path%>/html/js/function.js"></script>
    <script language="javascript" type="text/javascript" src="<%=path%>/html/js/ExamAdd/Admin/main.js"></script>
    <script type="text/javascript">
        function goback() {
            history.go(-1);
        }

        function modifySelf (){
            var username = document.getElementById("username");
            var password = document.getElementById("password");
            var nickname = document.getElementById("nickname");
            var email = document.getElementById("email");

//            username.style.backgroundColor="#EFFFFF";
//            username.removeAttribute("readonly");

            password.style.backgroundColor="#EFFFFF";
            password.removeAttribute("readonly");
            nickname.style.backgroundColor="#EFFFFF";
            nickname.removeAttribute("readonly");
            email.style.backgroundColor="#EFFFFF";
            email.removeAttribute("readonly");

        }
    </script>


</head>
<body class="mainbody">
<div class="navigation"> 欢迎您：${loginUser.username}(${loginUser.nickname})</div>

<col width="150px">
<col>
<div class="nlist2 clearfix">
    <form method="post">
    <input type="hidden" id="hid_examId" name="id" value="${loginUser.id}"/>
    <h2>个人信息</h2>
    <ul>
        <li>用户名：<input type="text" id="username" name="username" readonly="readonly" value="${loginUser.username}" style="border:0px;background-color: #FFFFFF"  class="txtInput normal required"/></li>
        <li>用户组：
            <input type="hidden" name="TClass.id" value="${loginUser.TClass.id}"/>
            <input type="text" readonly="readonly" value="${loginUser.TClass.className}" style="border:0px;background-color: #FFFFFF"   class="txtInput normal required"/></li>
        <li>角&nbsp;&nbsp;&nbsp;色：
            <input type="hidden" name="TRole.id" value="${loginUser.TRole.id}"/>
            <input type="text"  readonly="readonly" value="${loginUser.TRole.roleName}"  style="border:0px;background-color: #FFFFFF"  class="txtInput normal required"/></li>
        <li>密&nbsp;&nbsp;&nbsp;码：<input type="text" id="password" name="password" readonly="readonly" value="${loginUser.password}"  style="border:0px;background-color: #FFFFFF"  class="txtInput normal required"/></li>
        <li>昵&nbsp;&nbsp;&nbsp;称：<input type="text" id="nickname" name="nickname" readonly="readonly" value="${loginUser.nickname}"  style="border:0px;background-color: #FFFFFF"  class="txtInput normal required"/></li>
        <li>邮&nbsp;&nbsp;&nbsp;箱：<input type="text" id="email" name="email" readonly="readonly" value="${loginUser.nickname}"  style="border:0px;background-color: #FFFFFF"  class="txtInput normal required"/></li>


    <div class="foot_btn_box">
       <input type="button" value="修改信息" id="btnmodify" class="btnSubmit" onclick="modifySelf()"/>
        &nbsp;<input type="submit" value="提交修改" id="btnsubmit" class="btnSubmit"/>
    </div>

    </form>
</body>
</html>