<%@ page language="java" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>考试管理</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <link type="text/css" rel="stylesheet" href="<%=path%>/html/scripts/ui/skins/Aqua/css/ligerui-all.css"/>
    <link type="text/css" rel="stylesheet" href="<%=path%>/html/images/style.css"/>
    <script type="text/javascript" src="<%=path%>/html/scripts/jquery/jquery-1.3.2.min.js"></script>
    <script type="text/javascript" src="<%=path%>/html/scripts/ui/js/ligerBuild.min.js"></script>
    <script type="text/javascript" src="<%=path%>/html/js/function.js"></script>
</head>
<body class="mainbody">
<div class="navigation">首页 &gt; 试卷信息管理 &gt; 考试管理</div>
<div class="tools_box">
    <div class="tools_bar">
        <a href="javascript:void(0);" onclick="checkAll(this);" class="tools_btn"><span><b class="all">全选</b></span></a>
        <a href="add" class="tools_btn"><span><b class="import">添加考试</b></span></a>
        <a href="exams" class="tools_btn"><span><b class="refresh">刷新信息</b></span></a>
        <a href="####" class="tools_btn"><span><b class="delete">批量删除</b></span></a>
    </div>
</div>

<table width="100%" border="0" cellspacing="0" cellpadding="0" class="msgtable">
    <tr>
        <th width="6%">选择</th>
        <th width="20%">考试名称</th>
        <th width="15%">开始时间</th>
        <th width="15%">结束时间</th>
        <th width="6%">考试时长</th>
        <th width="6%">考试规模</th>

        <th width="6%">详细信息</th>
        <th width="6%">删除</th>
    </tr>
    <c:forEach items="${exams}" var="e">
        <tr>
            <td align="center"><span class="checkall"><input id="${e.id}" type="checkbox"/></span></td>
            <td align="center">${e.examName}</td>
            <td align="center">${e.begaintime}</td>
            <td align="center">${e.endtime}</td>
            <td align="center">
                <c:choose>
                    <c:when test="${e.examTime==0}">不限时</c:when>
                    <c:otherwise>${e.examTime} 分钟 </c:otherwise>
                </c:choose>
            </td>
            <td align="center">${exam.ispartuser==1?'部分员工':'全部员工'} </td>
            <td align="center"><a href="${e.id}/detail">详细信息</a></td>
            <td align="center"><a href="${e.id}/delete">删除</a></td>
        </tr>
    </c:forEach>
</table>

<div class="line10"></div>
</body>
</html>

