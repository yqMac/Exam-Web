<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%
    String path = request.getContextPath();
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>添加人员</title>
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
<sf:form name="form1" method="post" id="form1" modelAttribute="TUser">

    <div class="navigation"><a href="javascript:history.go(-1);" class="back">后退</a>首页 &gt; <a
            href="users">人员管理</a> &gt; <a href="#">增加人员</a></div>
    <table class="form_table">
        <col width="150px">
        <col>
        <tbody>
        <tr>
            <th>用户组：</th>
            <td>
                    <%--<sf:select path="TClass.id}" items="${classs}" itemLabel="className" itemvalue="id"--%>
                    <%--id="drpArticleType" class="select2 required"/>--%>
                <select name="classId" id="drpArticleType" class="select2 required">
                    <option value="">请选择用户组</option>
                    <c:forEach items="${classs}" var="o">
                        <option value="${o.id}">${o.className}</option>
                    </c:forEach>
                </select>

            </td>
        </tr>
        <tr>
            <th>角色：</th>
            <td>
                    <%--<sf:select path="${TRole.id}" items="${roles}" itemLabel="roleName" itemvalue="id" id="ddlCategoryId"--%>
                    <%--class="select2 required"/>--%>
                <select name="roleId" id="ddlCategoryId" class="select2 required">
                    <option value="">请选择角色...</option>
                    <c:forEach items="${roles}" var="r">
                        <option value="${r.id}">${r.roleName}</option>
                    </c:forEach>
                </select>

            </td>
        </tr>
        <tr>
            <th>帐号：</th>
            <td>
                <sf:input path="username" maxlength="100" id="txtTitle" class="txtInput normal required"/>
                    <%--<input name="username" type="text" maxlength="100" id="txtTitle" class="txtInput normal required" />--%>
            </td>
        </tr>
        <tr>
            <th>密码：</th>
            <td><sf:password path="password" maxlength="100" id="txtPass" class="txtInput normal required"/></td>
                <%--<td>--%>
                <%--<span id="cblItem"><input id="cblItem_0" type="checkbox" name="cblItem$0" /><label for="cblItem_0">允许评论</label><input id="cblItem_1" type="checkbox" name="cblItem$1" /><label for="cblItem_1">置顶</label><input id="cblItem_2" type="checkbox" name="cblItem$2" /><label for="cblItem_2">推荐</label><input id="cblItem_3" type="checkbox" name="cblItem$3" /><label for="cblItem_3">热点</label><input id="cblItem_4" type="checkbox" name="cblItem$4" /><label for="cblItem_4">幻灯</label><input id="cblItem_5" type="checkbox" name="cblItem$5" /><label for="cblItem_5">隐藏</label></span>--%>
                <%--</td>--%>
        </tr>
        <tr>
            <th>昵称：</th>
            <td><sf:input path="nickname" maxlength="100" id="txtNickName" class="txtInput normal"/>
            </td>
        </tr>

    </table>
    <div class="foot_btn_box">
        <input type="submit" value="提交保存" id="btnSubmit" class="btnSubmit"/>
        &nbsp;<input type="reset" class="btnSubmit" value="重 置"/>
    </div>
</sf:form>
</body>
</html>