<%@ page language="java" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";

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
    <script language="javascript" type="text/javascript" src="<%=path%>/html/js/ExamAdd/Admin/main.js"></script>

    <script type="text/javascript">
        function goback() {
            history.go(-1);
        }
        $(function () {
            var eId = document.getElementById("hid_examId").value;
            $.ajax({
                type: "Post",
                url: "/ajax/getStrategyTypesByExamId",
                data: "examId=" + eId,
                success: function (msg) {
                    if ($.trim(msg) != "") {
                        var jsonstr = eval("(" + msg + ")");
                        InitStrategyTable(jsonstr);
                    }
                }
            });
        });

        //    2016年4月28日 14:01:47
        function InitStrategyTable(jsondata) {
            var jsontype = jsondata["typedata"];
            var sdata = jsondata["strategydata"];
            var myTable = document.getElementById("strategy_list");
            //删除干净
            for (var i = myTable.rows.length - 1; i >= 0; i--) {
                myTable.deleteRow(i);
            }

            //插入头
            var myNewRow = myTable.insertRow();
//           myNewRow.className="theadth";
//            myNewRow.height=24;
            InsertCell(myNewRow, "题库集", "center", 100);
            InsertCell(myNewRow, "题库", "center", 100);
            InsertCell(myNewRow, "知识点", "center", 100);
            //添加类型名称
            for (var i = 0; i < jsontype.length; i++) {
                InsertCell(
                        myNewRow, jsontype[i].typeName, "center", 50
                );
            }
            //添加数据
//            alert("数据数量L:"+sdata.length);
            for (var i = 0; i < sdata.length; i++) {
                myNewRow = myTable.insertRow();
                var tmp = sdata [i];
                InsertCell(myNewRow, tmp["bankName"], "center", 100);
                InsertCell(myNewRow, tmp["libName"], "center", 100);
                InsertCell(myNewRow, tmp["pointName"], "center", 100);

                for (var j = 0; j < jsontype.length; j++) {
                    InsertCell(
                            myNewRow, tmp["type_" + (jsontype[j].id)], "center", 50
                    );
                }
            }
        }
    </script>


</head>
<body class="mainbody">

<input type="hidden" id="hid_examId" value="${exam.id}"/>
<div class="navigation"><a href="javascript:history.go(-1);" class="back">后退</a>首页 &gt; <a
        href="/userexam/exams">考试管理</a> &gt; <a href="#">详细信息</a></div>
<col width="150px">
<col>
<div class="nlist2 clearfix">
    <h2>考试信息</h2>
    <ul>
        <li>考试名称：${exam.examName}</li>
        <li>开始时间：${exam.begaintime}</li>
        <li>结束时间：${exam.endtime}</li>
        <li>考试时长：
            <c:choose>
                <c:when test="${exam.examTime==0}">不限时长</c:when>
                <c:otherwise>${exam.examTime} 分钟</c:otherwise>
            </c:choose>
        </li>
        <li>手动判卷：${exam.manual}</li>
        <li>查看答案：${exam.seepaper==1?'允许查看答案':'不允许查看答案'}</li>
        <li>保存试卷：${exam.savepaper==0?'不保存试卷':(exam.savepaper==1?'交卷自动保存':'随时手动保存')}</li>
        <li>考试次数：最多 ${exam.entermaxtimes} 次</li>
        <li>题目乱序：${exam.isorder==0?'不乱序':'乱序'}</li>
        <li>移出限制：
            <c:choose>
                <c:when test="${exam.moveouttimes==-1}">不限次数</c:when>
                <c:otherwise>${exam.moveouttimes} 次</c:otherwise>
            </c:choose></li>
        <li>显示方式：
            <c:choose>
            <c:when test="${exam.moveouttimes==2}">一屏一题</c:when>
            <c:when test="${exam.moveouttimes==1}">一屏一题型</c:when>
            <c:otherwise>一屏所有题</c:otherwise>
            </c:choose>
        <li>考试规模：${exam.ispartuser==1?'部分用户':'全部用户'}</li>
    </ul>
    <div class="line10"></div>
    <h2>考试策略</h2>
    <div class="line10"></div>
    <table border="0" id="strategy_list" cellspacing="0" cellpadding="0" class="border_table">
        <tbody id="nav_box">
        </tbody>
    </table>

    <div class="foot_btn_box">
        <input type="button" value=" 开始考试 " id="btnSubmit" class="btnSubmit"/>
        &nbsp;<input type="button" class="btnSubmit" value=" 返 回 " onclick="goback()"/>
    </div>
</div>
</body>
</html>