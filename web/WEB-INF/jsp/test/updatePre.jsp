﻿<%@ page language="java" pageEncoding="UTF-8" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";

%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>修改题库集</title>
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
<form name="form1" method="post" action="quesBank_update" id="form1">

    <div class="navigation"><a href="javascript:history.go(-1);" class="back">后退</a>首页 &gt; <a
            href="quesBank_list">题库集管理</a> &gt; <a href="#">修改题库集</a></div>
    <s:hidden name="id"/>
    <table class="form_table">
        <col width="150px">
        <col>
        <tbody>
       <tr>
            <th>题库集名称：</th>
            <td>
                <sf:input path="bankName" maxlength="100" class="txtInput normal"/>
                <%--<input name="user.username" type="text" maxlength="100" id="txtTitle" class="txtInput normal required" />--%>
            </td>
        </tr>
    </table>
    </div>

    <div class="foot_btn_box">
        <input type="submit" value="提交保存" id="btnSubmit" class="btnSubmit"/>
        &nbsp;<input type="reset" class="btnSubmit" value="重 置"/>
    </div>
    </div>
</form>
</body>
</html>