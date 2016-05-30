<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head id="Head1">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>后台管理面板</title>


    <link rel="stylesheet" type="text/css" href="<%=path%>/html/js/themes/default/easyui.css"/>
    <link rel="stylesheet" type="text/css" href="<%=path%>/html/js/themes/icon.css"/>
    <script type="text/javascript" src="<%=path%>/html/js/jquery-1.4.2.min.js"></script>
    <script type="text/javascript" src="<%=path%>/html/js/jQuery.easyui.js"></script>
    <script type="text/javascript" src='<%=path%>/html/js/outlook2.js'></script>
    <link href="<%=path%>/html/images/style.css" rel="stylesheet" type="text/css"/>
    <link href="<%=path%>/html/css/default.css" rel="stylesheet" type="text/css"/>
    <script type="text/javascript">
        $(function(){
            if(window.parent!=window){
                window.parent.location.href="/user/login";
            }
        });
    </script>
    <script type="text/javascript">
        ${menus}
        //设置登录窗口
        function openPwd() {
            //jquery的写法
            //$ 使用jquery
            //# 根据id查找
            //$('#w')
            //document.getElementById("w");
            $('#w').window({
                //窗口的标题文本
                title: '修改密码',
                width: 300,
                //定义是否将窗体显示为模式化窗口。
                modal: true,
                shadow: true,
                closed: true,
                height: 170,
                //定义是否能够改变窗口大小。
                resizable: false
            });
        }
        //关闭登录窗口
        function closePwd() {
            $('#w').window('close');
        }


        //修改密码
        function serverLogin() {
            var $newpass = $('#txtNewPass');
            var $rePass = $('#txtRePass');

            //document.getElementById("w").value
            //val()
            if ($newpass.val() == '') {
                msgShow('系统提示', '请输入密码！', 'warning');
                return false;
            }
            if ($rePass.val() == '') {
                msgShow('系统提示', '请在一次输入密码！', 'warning');
                return false;
            }

            if ($newpass.val() != $rePass.val()) {
                msgShow('系统提示', '两次密码不一至！请重新输入', 'warning');
                return false;
            }

            $.post('/ajax/editpassword.ashx?newpass=' + $newpass.val(), function (msg) {
                msgShow('系统提示', '恭喜，密码修改成功！<br>您的新密码为：' + msg, 'info');
                $newpass.val('');
                $rePass.val('');
                close();
            })

        }

        $(function () {

            openPwd();

            $('#editpass').click(function () {
                $('#w').window('open');
            });

            $('#btnEp').click(function () {
                serverLogin();
            });

            $('#btnCancel').click(function () {
                closePwd();
            });

            $('#loginOut').click(function () {
                $.messager.confirm('系统提示', '您确定要退出本次登录吗?', function (r) {

                    if (r) {
                        location.href = '/ajax/loginout.ashx';
                    }
                });
            })
        });


    </script>

</head>
<body class="easyui-layout" style="overflow-y: hidden" scroll="no">
<noscript>
    <div id="noscript">
        <img src="<%=path%>/html/images/noscript.gif" alt='抱歉，请开启脚本支持！'/>
    </div>
</noscript>
<div region="north" class="header" split="false" border="false">
    <div class="header_box">
        <div class="header_right"><span><b>${TUser.username}（${TUser.nickname}）</b>您好，欢迎光临</span> <br/>
            <a href="javascript:f_addTab('home','管理中心','welcome/welcome.jsp')">管理中心</a> |
            <a id="lbtnExit" href="/user/logout">安全退出</a></div>
        <a class="logo">KHcms Logo</a></div>
</div>
<div region="south" split="true" id="footer">
    <div class="footer">< -----天津科技大学通用考试系统----- ></div>
</div>

<div region="west" split="true" title="导航菜单" id="west">
    <div class="easyui-accordion" fit="true" border="false">
        <!--  导航内容 -->
    </div>
</div>

<div id="mainPanle" region="center" style="background: #eee; overflow-y:hidden">
    <div id="tabs" class="easyui-tabs" fit="true" border="false">
        <div title="欢迎使用" style="padding:20px;overflow:hidden;" id="home">
            <h1>欢迎使用天津科技大学通用考试系统!</h1>
        </div>
    </div>
</div>

<!--修改密码窗口-->
<div id="w" class="easyui-window" title="修改密码" collapsible="false" minimizable="false" maximizable="false"
     icon="icon-save">
    <div class="easyui-layout" fit="true">
        <div region="center" border="false" class="w_1">
            <table cellpadding=3>
                <tr>
                    <td>新密码：</td>
                    <td><input id="txtNewPass" type="Password" class="txt01"/></td>
                </tr>
                <tr>
                    <td>确认密码：</td>
                    <td><input id="txtRePass" type="Password" class="txt01"/></td>
                </tr>
            </table>
        </div>
        <div region="south" border="false" class="w_2">
            <a id="btnEp" class="easyui-linkbutton" icon="icon-ok" href="javascript:void(0)">确定</a>
            <a id="btnCancel" class="easyui-linkbutton" icon="icon-cancel" href="javascript:void(0)">取消</a>
        </div>
    </div>
</div>

<!--标签页右键菜单-->
<div id="mm" class="easyui-menu">
    <div id="mm-tabclose">关闭</div>
    <div id="mm-tabcloseall">全部关闭</div>
    <div id="mm-tabcloseother">除此之外全部关闭</div>
    <div class="menu-sep"></div>
    <div id="mm-tabcloseright">当前页右侧全部关闭</div>
    <div id="mm-tabcloseleft">当前页左侧全部关闭</div>
    <div class="menu-sep"></div>
    <div id="mm-exit">退出</div>
</div>
</body>
</html>