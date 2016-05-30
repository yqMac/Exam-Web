<%@ page language="java" pageEncoding="UTF-8" %>

<%@taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";

%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>增删考生</title>
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
    <script type="text/javascript">

        function classChange() {
            var bId = $("#selclass").val();
            $.ajax({
                type: "Post",
                url: "/ajax/getUserOptionsByClassId",
                data: "classId=" + bId,
                success: function (msg) {
                    if ($.trim(msg) != "") {
                        $("#seluser").html(msg);
                    }
                    else {
                        $("#selclass").html("<option value='0'>所有</option>");
                    }
                }
            });
        }

        function delsel() {
            var eId = $("#hid_examId").val();
            var bId = $("#selclass").val();
            var cid = $("#seluser").val();
            $.ajax({
                type: "Post",
                url: "/ajax/delExamUsers",
                data: "examId=" + eId + "&classId=" + bId + "&userId="+cid,
                success: function (msg) {
                    if ($.trim(msg) != "") {
                        $("#userCount").html(msg);
                    }
                    else {
                        $("#userCount").html("未知");
                    }
                }
            });
        }
        function addsel() {
            var eId = $("#hid_examId").val();
            var bId = $("#selclass").val();
            var cid = $("#seluser").val();
            $.ajax({
                type: "Post",
                url: "/ajax/addExamUsers",
                data: "examId=" + eId + "&classId=" + bId + "&userId="+cid,
                success: function (msg) {
                    if ($.trim(msg) != "") {
                        $("#userCount").html(msg);
                    }
                    else {
                        $("#userCount").html("未知");
                    }
                }
            });
        }

    </script>
</head>
<body class="mainbody">

<form method="get">
    <input type="hidden" name="examId" id="hid_examId" value="${examId}"/>
</form>
<div class="navigation">
    <a href="javascript:history.go(-1);" class="back">后退</a>首页 &gt; <a
        href="/exam/exams">考试管理</a> &gt;<a
        href="detail">详细信息</a> &gt;<a
        href="#">增删考生</a></div>
<div>
    <table class="form_table">
        <col width="150px">
        <col>
        <tbody>

        <tr>
            <th>当前考生人数：</th>
            <td id="userCount">
                ${examusers}
            </td>
        </tr>
        <tr>
            <th>用户组</th>
            <td>
                <select name="classId" class="select2" onchange="classChange()" id="selclass">
                    <option value="0">所有</option>
                    <c:forEach items="${classs}" var="o">
                        <option value="${o.id}">${o.className}</option>
                    </c:forEach>
                </select>
            </td>
        </tr>
        <tr>
            <th>员工</th>
            <td>
                <select name="userId" class="select2" id="seluser">
                    <option value="0">所有</option>
                    <c:forEach items="${users}" var="c">
                        <option value="${c.id}">${c.username}</option>
                    </c:forEach>
                </select>
            </td>
        </tr>

    </table>
</div>

<div class="foot_btn_box">
    <input type="button" value="删除选中" id="btndel" class="btnSubmit" onclick="delsel()"/>
    <input type="button" value="增加选中" id="btnadd" class="btnSubmit" onclick="addsel()"/>

    &nbsp;<input type="button" class="btnSubmit" value=" 返 回 " onclick="javascript:history.go(-1);"/>
</div>
</body>
</html>