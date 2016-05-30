<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>题库集管理</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <link type="text/css" rel="stylesheet" href="<%=path%>/html/scripts/ui/skins/Aqua/css/ligerui-all.css" />
    <link type="text/css" rel="stylesheet" href="<%=path%>/html/images/style.css" />
    <script type="text/javascript" src="<%=path%>/html/scripts/jquery/jquery-1.3.2.min.js"></script>
    <script type="text/javascript" src="<%=path%>/html/scripts/ui/js/ligerBuild.min.js"></script>
    <script type="text/javascript" src="<%=path%>/html/js/function.js"></script>
</head>
<body class="mainbody">
<form name="form1" method="post" action="###" id="form1">
    <script type="text/javascript">
        //<![CDATA[
        var theForm = document.forms['form1'];
        if (!theForm) {
            theForm = document.form1;
        }
        function __doPostBack(eventTarget, eventArgument) {
            if (!theForm.onsubmit || (theForm.onsubmit() != false)) {
                theForm.__EVENTTARGET.value = eventTarget;
                theForm.__EVENTARGUMENT.value = eventArgument;
                theForm.submit();
            }
        }
        //]]>
    </script>

    <div class="navigation">首页 &gt; 题目资源管理 &gt; 题库集管理</div>
    <div class="tools_box">
        <div class="tools_bar">
            <a href="javascript:void(0);" onclick="checkAll(this);" class="tools_btn"><span><b class="all">全选</b></span></a>
            <a href="quesBank/add" class="tools_btn"><span><b class="import">添加题库集</b></span></a>
            <a href="quesBank_list" class="tools_btn"><span><b class="refresh">刷新信息</b></span></a>
            <a href="####" class="tools_btn"><span><b class="delete">批量删除</b></span></a>
        </div>
    </div>


    <table width="100%" border="0" cellspacing="0" cellpadding="0" class="msgtable">
        <tr>
            <th width="6%">选择</th>
            <th width="10%">题库集名称</th>
            <th width="10%">题库数量</th>
            <th width="6%">修改</th>
            <th width="6%">删除</th>
        </tr>

        <s:iterator value="#banks">
        <tr>
            <td align="center"><span class="checkall"><input id="<s:property value="id"/>" type="checkbox"  /></span></td>
            <td align="center"><s:property value="bankName"/> </td>
            <td align="center"><s:property value="TQuesLibs.size()"/></td>

            <td align="center"><a href="quesBank_updatePre?id=<s:property value="id"/>">修改</a> </td>
            <td align="center"><a href="quesBank_delete?id=<s:property value="id"/>" >删除</a></td>
        </tr>
        </s:iterator>


    </table>

    <div class="line10"></div>
</form>
</body>
</html>

