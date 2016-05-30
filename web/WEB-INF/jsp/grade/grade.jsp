<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>考试成绩管理</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <link type="text/css" rel="stylesheet" href="<%=path%>/html/scripts/ui/skins/Aqua/css/ligerui-all.css" />
    <link type="text/css" rel="stylesheet" href="<%=path%>/html/images/style.css" />
    <script type="text/javascript" src="<%=path%>/html/scripts/jquery/jquery-1.3.2.min.js"></script>
    <script type="text/javascript" src="<%=path%>/html/scripts/ui/js/ligerBuild.min.js"></script>
    <script type="text/javascript" src="<%=path%>/html/js/function.js"></script>
</head>
<body class="mainbody">
<div class="navigation">
    <a href="javascript:history.go(-1);" class="back">后退</a>首页 &gt; <a
        href="/grade/exams">考试成绩管理</a> &gt; <a href="#">成绩详情</a></div>


    <table width="100%" border="0" cellspacing="0" cellpadding="0" class="msgtable">
        <tr>
            <th width="6%">选择</th>
            <th width="10%">考生姓名</th>
            <th width="10%">考试名称</th>
            <th width="10%">开始时间</th>
            <th width="10%">结束时间</th>
            <th width="10%">考试时长</th>
            <th width="6%">考生成绩</th>
            <%--<th width="6%">删除</th>--%>
        </tr>

        <c:forEach items="${grades}" var="grade">
            <tr>
                <td align="center"><span class="checkall"><input id="${grade.id}" type="checkbox"  /></span></td>
                <td align="center">${grade.TUser.username}(${grade.TUser.nickname}) </td>
                <td align="center">${grade.TExam.examName}</td>
                <td align="center">${grade.begaintime}</td>
                <td align="center">${grade.endtime} </td>
                <td align="center">${grade.spend}</td>
                <td align="center">${grade.grade} </td>
            </tr>
        </c:forEach>
        <%--<s:iterator value="#exams">--%>
        <%--<tr>--%>
            <%--<td align="center"><span class="checkall"><input id="<s:property value="id"/>" type="checkbox"  /></span></td>--%>
            <%--<td align="center"><s:property value="examName"/> </td>--%>
            <%--<td align="center"><s:property value="begaintime"/></td>--%>
            <%--<td align="center"><s:property value="endtime"/> </td>--%>
            <%--<td align="center"><s:property value="examTime==-1?'不限时':(examTime+' 分钟')"/></td>--%>
            <%--<td align="center"><s:property value="ispartuser==1?'部分员工':'全部员工'"/> </td>--%>
            <%--<td align="center"><a href="userexam_detail?id=<s:property value="id"/>">详细信息</a></td>--%>
            <%--&lt;%&ndash;<td align="center"><a href="exam_delete?id=<s:property value="id"/>" >删除</a></td>&ndash;%&gt;--%>
        <%--</tr>--%>
        <%--</s:iterator>--%>
    </table>
    <div class="line10"></div>
</body>
</html>

