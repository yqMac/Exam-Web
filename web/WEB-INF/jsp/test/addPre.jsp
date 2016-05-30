<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>添加题库集</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <link href="<%=path%>/html/scripts/ui/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
    <link href="<%=path%>/html/images/style.css" rel="stylesheet" type="text/css" />
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
                        element.ligerTip({ content: lable.html(), appendIdTo: lable });
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
<form name="form1" method="post" action="test_add" id="form1">


            <table class="form_table">
                <col width="150px"><col>
                <tbody>
                <tr>
                    <th>名称0：</th>
                    <td><input name="%{maps}['1']"  type="text"/></td>
                    <td><input name="sss[0].TQuesPoint.id" type="text"/></td>
                    <td><input name="sss[0].quesCount"  type="text"/></td>

                </tr>
                <tr>
                    <th>名称0：</th>
                    <td><input name="sss[1].TQuesType.id" value="1" type="text"/></td>
                    <td><input name="sss[1].TQuesPoint.id" value="1" type="text"/></td>
                    <td><input name="sss[1].quesCount" value="1" type="text"/></td>

                </tr>
                <tr>
                    <th>名称0：</th>
                    <td><input name="sss[2].TQuesType.id" value="2" type="text"/></td>
                    <td><input name="sss[2].TQuesPoint.id" value="2" type="text"/></td>
                    <td><input name="sss[2].quesCount" value="2" type="text"/></td>

                </tr>
            </table>


        <div class="foot_btn_box">
            <input type="submit"  value="提交保存" id="btnSubmit" class="btnSubmit" />
            &nbsp;<input  type="reset" class="btnSubmit" value="重 置" />
        </div>
    </div>
</form>
</body>
</html>