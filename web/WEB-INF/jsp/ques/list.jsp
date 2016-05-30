<%@ page language="java" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>题目管理</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <link type="text/css" rel="stylesheet" href="<%=path%>/html/scripts/ui/skins/Aqua/css/ligerui-all.css"/>
    <link type="text/css" rel="stylesheet" href="<%=path%>/html/images/style.css"/>
    <script type="text/javascript" src="<%=path%>/html/scripts/jquery/jquery-1.3.2.min.js"></script>
    <script type="text/javascript" src="<%=path%>/html/scripts/ui/js/ligerBuild.min.js"></script>
    <script type="text/javascript" src="<%=path%>/html/js/function.js"></script>

</head>
<body class="mainbody">
<form method="get">
    <div class="navigation">首页 &gt; 题目资源管理 &gt; 题目管理</div>
    <div class="tools_box">
        <div class="tools_bar">
            <a href="javascript:void(0);" onclick="checkAll(this);" class="tools_btn"><span><b class="all">全选</b></span></a>
            <a href="add" class="tools_btn"><span><b class="import">添加题目</b></span></a>
            <a href="queses" class="tools_btn"><span><b class="refresh">刷新信息</b></span></a>
            <a href="####" class="tools_btn"><span><b class="delete">批量删除</b></span></a>
            <script type="text/javascript" language="javascript">
                function bankChange() {
                    var bId = $("#selbank").val();
                    if(bId==0){
                        $("#sellib").html("<option value='0' selected>所有</option>");
                        libChange();
                        return;
                    }
                    $.ajax({
                        type: "Post",
                        url: "/ajax/getLibOptionByBankId",
                        data: "bankId=" + bId,
                        success: function (msg) {
                            if ($.trim(msg) != "") {
                                msg="<option value='0' selected>所有</option>"+msg;
                                $("#sellib").html(msg);
                                libChange();
                            }
                            else {
                                $("#sellib").html("<option value='0' selected>所有</option>");
                            }
                        }
                    });
                }
                function libChange() {
                    var bId = $("#sellib").val();
                    if(bId==0){
                        $("#selpoint").html("<option value='0' selected>所有</option>");
                        return;
                    }
                    $.ajax({
                        type: "Post",
                        url: "/ajax/getPointOptionByLibId",
                        data: "libId=" + bId,
                        success: function (msg) {
                            if ($.trim(msg) != "") {
                                msg="<option value='0' selected>所有</option>"+msg;
                                $("#selpoint").html(msg);
                            }
                            else {
                                $("#selpoint").html("<option value='0' selected>所有</option>");
                            }
                        }
                    });
                }
            </script>

            <div class="search_box">
                题库集：
                <select name="bankId" onchange="bankChange()" id="selbank" class="select2" >
                    <option value="0" <c:if test="${0 eq typeId}">selected</c:if> >所有</option>
                    <c:forEach items="${banks}" var="bank">
                        <option value="${bank.id}" <c:if test="${bank.id eq bankId}">selected</c:if> >${bank.bankName}</option>
                    </c:forEach>
                </select>
                &nbsp;
                题库：
                <select name="libId" onchange="libChange()" id="sellib" class="select2" >
                    <option value="0" <c:if test="${0 eq typeId}">selected</c:if> >所有</option>
                    <c:forEach items="${libs}" var="lib">
                        <option value="${lib.id}" <c:if test="${lib.id eq libId}">selected</c:if> >${lib.libName}</option>
                    </c:forEach>
                </select>
                &nbsp;
                知识点：
                <select name="pointId" id="selpoint" class="select2" >
                    <option value="0" <c:if test="${0 eq typeId}">selected</c:if> >所有</option>
                    <c:forEach items="${points}" var="p">
                        <option value="${p.id}" <c:if test="${p.id eq pointId}">selected</c:if> >${p.pointName}</option>
                    </c:forEach>
                </select>
                &nbsp;
                题型：
                <select name="typeId" class="select2" >
                    <option value="0" <c:if test="${0 eq typeId}">selected</c:if> >所有</option>
                    <c:forEach items="${types}" var="t">
                            <option value="${t.id}" <c:if test="${t.id eq typeId}">selected</c:if> >${t.typeName}</option>
                    </c:forEach>
                </select>
                &nbsp;
                <input type="text" name="fuzz" value="${fuzz}"/>
                <input type="submit" value="筛选"/>
            </div>

        </div>
    </div>

    <table width="100%" border="0" cellspacing="0" cellpadding="0" class="msgtable">
        <thead>
        <tr>
            <th width="3%">选择</th>
            <th width="6%">题库集</th>
            <th width="6%">题库</th>
            <th width="6%">知识点</th>
            <th width="3%">难度</th>
            <th width="10%">内容</th>
            <th width="5%">类型</th>
            <th width="5%">分数</th>
            <th width="10%">创建时间</th>

            <th width="3%">修改</th>
            <th width="3%">删除</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${datas.datas}" var="q">
            <tr>
                <td align="center"><span class="checkall"><input id="${q.id}" type="checkbox"/></span>
                </td>
                <td align="center">${q.TQuesBank.bankName}</td>
                <td align="center">${q.TQuesLib.libName}</td>
                <td align="center">${q.TQuesPoint.pointName}</td>
                <td align="center">${q.diffDegr}</td>
                    <%--<td align="center">${q.quesContent.length()>=11?(q.quesContent.substring(0,11)+"..."):q.quesContent}</td>--%>
                <td align="center">${q.quesContent}</td>
                <td align="center">${q.TQuesType.typeName}</td>
                <td align="center">${q.score}</td>
                <td align="center">${q.createTime}</td>
                <td align="center"><a href="${q.id}/update">修改</a></td>
                <td align="center"><a href="${q.id}/delete">删除</a></td>
            </tr>
        </c:forEach>
        </tbody>
        <tfoot>
        <tr align="center" style="background-color:#F9F9F9">
            <td colspan="11">
                <jsp:include page="/jsp/pager.jsp">
                    <jsp:param name="totalRecord" value="${datas.total}"/>
                    <jsp:param name="url" value="queses"/>
                    <jsp:param name="params" value="bankId,libId,pointId,typeId,fuzz"/>
                </jsp:include>
            </td>
        </tr>
        </tfoot>
    </table>
    <div class="line10"></div>
</form>
</body>
</html>

