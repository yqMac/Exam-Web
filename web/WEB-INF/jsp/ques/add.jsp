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
    <title>添加题目</title>
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
        function bankChange() {
            var bId = $("#selQuesBank").val();
            $.ajax({
                type: "Post",
                url: "/ques/ajaxGetLibOptionByBankId",
                data: "bankId=" + bId,
                success: function (msg) {
                    if ($.trim(msg) != "") {
                        $("#selQuesLib").html(msg);
                        libChange();
                    }
                    else {
                        $("#selQuesLib").html("<option value=''>==请选题库集==</option>");
                    }
                }
            });
        }
        function libChange() {
            var bId = $("#selQuesLib").val();
            $.ajax({
                type: "Post",
                url: "/ques/ajaxGetPointOptionByLibId",
                data: "libId=" + bId,
                success: function (msg) {
                    if ($.trim(msg) != "") {
                        $("#selQuesPoint").html(msg);
                    }
                    else {
                        $("#selQuesPoint").html("<option value=''>==请选题库==</option>");
                    }
                }
            });
        }
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
    <script type="text/javascript">
        //表单验证
        $(function () {
            $("#form1").validate({
                errorPlacement: function (lable, element) {
                    element.ligerTip({content: lable.html(), appendIdTo: lable});
                },
                success: function (lable) {
                    lable.ligerHideTip();
                }
            });
        });
        //菜单事件处理
        $(function () {
            //初始化按钮事件
            $("#nav_box tr").each(function (i) {
                initButton(i);
            });
            //添加按钮(点击绑定)
            $("#navAddButton").click(function () {
                var navSize = $('#nav_box tr').size();
                var navRow = getTr(navSize);
                $("#nav_box").append(navRow);
                initButton(navSize);
            });

        });

        //表格行的菜单内容
        function getTr(indexValue) {
            var navRow = '<tr class="td_c">'
//                    + '<td><input  type="hidden" value="0" />选项</td>'
                    + '<td><input name="optiontexts" type="text" class="txtInput middle" /></td>'
                    + '<td><img alt="删除" src="<%=path%>/html/images/icon_del.gif" class="operator" /></td>'
                    + '</tr>';
            return navRow;
        }

        //初始化按钮事件
        function initButton(indexValue) {
            //功能操作按钮
            $("#nav_box tr:eq(" + indexValue + ") .operator").each(function (i) {
                switch (i) {
                    //删除
                    case 0:
                        $(this).click(function () {
                            var obj = $(this);
                            $.ligerDialog.confirm("确定要删除吗？", "提示信息", function (result) {
                                if (result) {
                                    obj.parent().parent().remove(); //删除节点
                                }
                            });
                        });
                        break;
                }
            });
        }

    </script>
</head>
<body class="mainbody">
<sf:form name="form1" method="post" id="form1" modelAttribute="TQues">

    <div class="navigation">
        <a href="javascript:history.go(-1);" class="back">后退</a>首页 &gt; <a
            href="/ques/queses">题目管理</a> &gt; <a href="#">增加题目</a></div>
    <div>
        <table class="form_table">
            <col width="150px">
            <col>
            <tbody>
            <tr>
                <th>题库集：</th>
                <td>
                    <select id="selQuesBank" onclick="bankChange()" class="select2 required">
                        <option value="-1">==选择题库集==</option>
                        <c:forEach items="${banks}" var="bank">
                            <option value="${bank.id}">${bank.bankName}</option>
                        </c:forEach>
                    </select>
                        <%--<s:select onchange="bankChange()" id="selQuesBank" list="#banks" name="bankId" listKey="id"--%>
                        <%--listValue="bankName" headerKey="-1" headerValue="==请选题库集==" class="select2 required"/>--%>
                </td>
            </tr>
            <tr>
                <th>题库：</th>
                <td>
                    <select onchange="libChange()" id="selQuesLib" class="select2 required">
                        <option value="">==请选题库集==</option>
                    </select>
                </td>
            </tr>
            <tr>
                <th>知识点：</th>
                <td>
                    <select name="pointId" id="selQuesPoint" class="select2 required">
                        <option value="">==请选题库==</option>
                    </select>
                </td>
            </tr>
            <tr>
                <th>题目类型：</th>
                <td>
                    <select id="selQuesType" name="typeId" class="select2 required">
                        <option value="-1">==选择题型==</option>
                        <c:forEach items="${qtypes}" var="t">
                            <option value="${t.id}">${t.typeName}</option>
                        </c:forEach>
                    </select>
                        <%--<s:select id="selQuesType" list="#qtypes" name="typeId" listKey="id" listValue="typeName"--%>
                        <%--headerKey="-1" headerValue="==请选择题型==" class="select2 required"/>--%>
                </td>
            </tr>
            <tr>
                <th>题目内容：</th>
                <td>
                    <sf:textarea path="quesContent" cols="30" rows="3" class="small2"/>
                </td>
            </tr>
            <tr>
                <th>题目分数：</th>
                <td>
                    <sf:input path="score" class="txtInput normal"/>
                </td>
            </tr>
            <tr>
                <th>题目选项：</th>
                <td>
                    <button  id="navAddButton" type="button" class="btnSelect"><span class="add">添 加</span></button>
                </td>
            </tr>
            <tr>
                <th></th>
                <td>
                    <table id="" border="0" cellspacing="0" cellpadding="0" class="border_table">
                        <thead>
                        <tr>
                                <%--<th width="15">选项</th>--%>
                            <th width="180">内容</th>
                            <th width="10">操作</th>
                        </tr>
                        </thead>
                        <tbody id="nav_box">

                        </tbody>
                    </table>
                </td>
            </tr>
            <tr>
                <th>题目答案：</th>
                <td>
                    <sf:textarea path="answer" cols="30" rows="3" class="small2"/>
                </td>
            </tr>
            <tr>
                <th>题目难度：</th>
                <td>
                    <sf:input path="diffDegr" class="txtInput normal"/>
                </td>
            </tr>

        </table>
    </div>

    <div class="foot_btn_box">
        <input type="submit" value="提交保存" id="btnSubmit" class="btnSubmit"/>
        &nbsp;<input type="reset" class="btnSubmit" value="重 置"/>
    </div>
</sf:form>
</body>
</html>