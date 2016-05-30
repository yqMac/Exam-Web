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
    <title>修改知识点</title>
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
                url: "ques_ajaxGetLibOptionByBankId",
                data: "bankId=" + bId,
                success: function (msg) {
                    if ($.trim(msg) != "") {
                        $("#selQuesLib").html(msg);
                    }
                    else {
                        $("#selQuesLib").html("<option value=''>==请选题库集==</option>");
                    }
                }
            });
        }
        //加载编辑器
        $(function () {
            var editor = KindEditor.create('textarea[name="txtContent"]', {
                resizeType: 1,
                uploadJson: '../../tools/upload_ajax.ashx?action=EditorFile&IsWater=1',
                fileManagerJson: '../../tools/upload_ajax.ashx?action=ManagerFile',
                allowFileManager: true
            });

        });
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
        //初始化上传控件
        $(function () {
            InitSWFUpload("../../tools/upload_ajax.ashx", "Filedata", "10240 KB", "../../scripts/swfupload/swfupload.swf", 1, 1);
        });
    </script>
</head>
<body class="mainbody">
<sf:form name="form1" method="post" id="form1" modelAttribute="TQuesPoint">

    <div class="navigation"><a href="javascript:history.go(-1);" class="back">后退</a>首页 &gt; <a
            href="/point/points">知识点管理</a> &gt; <a href="#">修改知识点</a></div>
    <sf:hidden path="id"/>
    <table class="form_table">
        <col width="150px">
        <col>
        <tbody>
        <tr>
            <th>题库集：</th>
            <td>
                <select name="bankId" onchange="bankChange()" id="selQuesBank" class="select2 required">
                    <c:forEach items="${banks}" var="bank">
                        <option value="${bank.id}">${bank.bankName}</option>
                    </c:forEach>
                </select>
                         <%--<s:select onchange="bankChange()" id="selQuesBank" list="#banks" name="bankId" listKey="id"--%>
                          <%--listValue="bankName" headerKey="-1" headerValue="==请选题库集==" class="select2 required"/>--%>
            </td>
        </tr>
        <tr>
            <th>题库名称：</th>
            <td>
                <select name="libId" id="selQuesLib"  class="select2 required">
                    <c:forEach items="${libs}" var="lib">
                        <option value="${lib.id}">${lib.libName}</option>
                    </c:forEach>
                </select>
            </td>
        </tr>
        <tr>
            <th>知识点名称：</th><td>
            <sf:input path="pointName"  maxlength="100"  id="txtPointName" class="txtInput normal required"/>
            <%--<input name="pointName" type="text" maxlength="100" id="txtPointName" class="txtInput normal required"/>--%>
            </td>
        </tr>
        </tbody>
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