<%@ page language="java" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>人员管理</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <link type="text/css" rel="stylesheet" href="<%=path%>/html/scripts/ui/skins/Aqua/css/ligerui-all.css"/>
    <link type="text/css" rel="stylesheet" href="<%=path%>/html/images/style.css"/>
    <script type="text/javascript" src="<%=path%>/html/scripts/jquery/jquery-1.3.2.min.js"></script>
    <script type="text/javascript" src="<%=path%>/html/scripts/ui/js/ligerBuild.min.js"></script>
    <script type="text/javascript" src="<%=path%>/html/js/function.js"></script>
</head>
<body class="mainbody">
<div class="navigation">首页 &gt; 人员信息管理 &gt; 人员管理</div>
<div class="tools_box">
    <div class="tools_bar">
        <a href="javascript:void(0);" onclick="checkAll(this);" class="tools_btn"><span><b class="all">全选</b></span></a>
        <a href="add" class="tools_btn"><span><b class="import">添加人员</b></span></a>
        <a href="users" class="tools_btn"><span><b class="refresh">刷新信息</b></span></a>
        <a href="####" class="tools_btn"><span><b class="delete">批量删除</b></span></a>
    <div class="search_box">
        <form method="get">
            用户组：
            <select name="classId" id="selOragan" class="select2">
                <option value="0"
                        <c:if test="${0 eq classId}">selected</c:if> >所有
                </option>
                <c:forEach items="${classes}" var="c">
                    <option value="${c.id}"
                            <c:if test="${c.id eq classId}">selected</c:if>
                    >${c.className} </option>
                </c:forEach>
            </select>
            &nbsp;
            姓名：<input type="text" name="fuzz" value="${fuzz}"/>
            <input type="submit" value="筛选"/>
        </form>
    </div>
    </div>

</div>
<table width="100%" border="0" cellspacing="0" cellpadding="0" class="msgtable">
    <thead>
    <tr>
        <th width="6%">选择</th>
        <th width="10%">帐号</th>
        <th width="10%">密码</th>
        <th width="10%">昵称</th>
        <th width="25%">用户组</th>
        <th align="center">角色</th>
        <th width="6%">修改</th>
        <th width="6%">删除</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${datas.datas}" var="c">
        <tr>
            <td align="center"><span class="checkall"><input id="${c.id}" type="checkbox"/></span>
            </td>
            <td align="center">${c.username}</td>
            <td align="center">${c.password}</td>
            <td align="center">${c.nickname}</td>
            <td align="center">${c.TClass.className}</td>
            <td align="center">${c.TRole.roleName}</td>
            <td align="center"><a href="${c.id}/update">修改</a></td>
            <td align="center"><a href="${c.id}/delete">删除</a></td>
        </tr>
    </c:forEach>
    </tbody>
    <tfoot>
    <tr align="center" style="background-color:#F9F9F9">
        <td colspan="8">
            <jsp:include page="/jsp/pager.jsp">
                <jsp:param name="totalRecord" value="${datas.total}"/>
                <jsp:param name="url" value="users"/>
                <jsp:param name="params" value="classId,fuzz"/>
            </jsp:include>
        </td>
    </tr>
    </tfoot>
</table>

<div class="line10"></div>
</body>
</html>
