﻿<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>知识点管理</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <link type="text/css" rel="stylesheet" href="<%=path%>/html/scripts/ui/skins/Aqua/css/ligerui-all.css" />
    <link type="text/css" rel="stylesheet" href="<%=path%>/html/images/style.css" />
    <script type="text/javascript" src="<%=path%>/html/scripts/jquery/jquery-1.3.2.min.js"></script>
    <script type="text/javascript" src="<%=path%>/html/scripts/ui/js/ligerBuild.min.js"></script>
    <script type="text/javascript" src="<%=path%>/html/js/function.js"></script>
</head>
<body class="mainbody">
    <div class="navigation">首页 &gt; 题目资源管理 &gt; 知识点管理</div>
    <div class="tools_box">
        <div class="tools_bar">
            <a href="javascript:void(0);" onclick="checkAll(this);" class="tools_btn"><span><b class="all">全选</b></span></a>
            <a href="add" class="tools_btn"><span><b class="import">添加知识点</b></span></a>
            <a href="points" class="tools_btn"><span><b class="refresh">刷新信息</b></span></a>
            <a href="####" class="tools_btn"><span><b class="delete">批量删除</b></span></a>
        </div>
    </div>
    <table width="100%" border="0" cellspacing="0" cellpadding="0" class="msgtable">
        <tr>
            <th width="6%">选择</th>
            <th width="10%">知识点名称</th>
            <th width="10%">题库集</th>
            <th width="10%">题库</th>
            <th width="10%">题目数量</th>
            <th width="6%">修改</th>
            <th width="6%">删除</th>
        </tr>
<c:forEach items="${points}" var="p">
    <tr>
        <td align="center"><span class="checkall"><input id="${p.id}" type="checkbox"  /></span></td>
        <td align="center">${p.pointName} </td>
        <td align="center">${p.TQuesBank.bankName}</td>
        <td align="center">${p.TQuesLib.libName}</td>
        <td align="center">${p.TQueses.size()}</td>
        <td align="center"><a href="${p.id}/update">修改</a> </td>
        <td align="center"><a href="${p.id}/delete">删除</a></td>
    </tr>
</c:forEach>
    </table>
    <div class="line10"></div>
</body>
</html>

