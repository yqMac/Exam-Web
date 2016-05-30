<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";

%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>修改权限</title>
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
        //表单验证
        $(function () {
            $("#form1").validate({
                invalidHandler: function (e, validator) {
                    parent.jsprint("有 " + validator.numberOfInvalids() + " 项填写有误，请检查！", "", "Warning");
                },
                errorPlacement: function (lable, element) {
                    //可见元素显示错误提示
                    if (element.parents(".tab_con").css('display') != 'none') {
                        element.ligerTip({content: lable.html(), appendIdTo: lable});
                    }
                },
                success: function (lable) {
                    lable.ligerHideTip();
                }
            });
        });
    </script>
</head>
<body class="mainbody">
<sf:form name="form1" method="post"  id="form1" modelAttribute="right">

    <div class="navigation"><a href="javascript:history.go(-1);" class="back">后退</a>首页 &gt; <a
            href="rights">权限管理</a> &gt; <a href="#">修改权限</a></div>
    <sf:hidden path="id"/>
    <%--<sf:hidden path="right.id"/>--%>
    <table class="form_table">
        <col width="150px">
        <col>
        <tbody>
        <tr>
            <th>父权限：</th>
            <td>
                <sf:select path="parent.id" items="${rights}" itemLabel="rightName" itemValue="id"
                           class="select2 required"/>
                <%--<s:select name="parentId" list="#rights" listKey="id" listValue="rightName" class="select2 required"/>--%>
            </td>
        </tr>
        <tr>
            <th>权限名称：</th>
            <td>
                <sf:input path="rightName" maxlength="100" class="txtInput normal"/>
                <%--<sf:input path="rightName" maxlength="100" class="txtInput normal"/>--%>
            </td>
        </tr>
        <tr>
            <th>管理链接：</th>
            <td>
                <sf:input path="rightUrl" maxlength="100" class="txtInput normal"/>
            </td>
        </tr>
    </table>
    </div>

    <div class="foot_btn_box">
        <input type="submit" value="提交保存" id="btnSubmit" class="btnSubmit"/>
        &nbsp;<input type="reset" class="btnSubmit" value="重 置"/>
    </div>
    </div>
</sf:form>
</body>
</html>