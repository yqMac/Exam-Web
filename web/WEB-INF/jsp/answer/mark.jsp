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
    <title>手工评分</title>
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
    <script type="text/javascript" src="<%=path%>/html/js/ExamAdd/Admin/chkData.js"></script>
    <script type="text/javascript">
        window.onload = init;

        function init() {
            //考试id
            eId = document.getElementById("hid_examId");
            //题型
            tId = document.getElementById("seltype");

            //答案id
            aId = document.getElementById("hid_answerId");
            //考生姓名
            userNickName = document.getElementById("userName");
            //问题内容
            quesContent = document.getElementById("quesContent");
            //标准答案
            quesAnswer = document.getElementById("quesAnswer");
            //题目分数
            quesScore = document.getElementById("queScore");
            //考生答案
            userAnswer = document.getElementById("userAnswer");

            //考生分数
            score = document.getElementById("userScore");
            eName = document.getElementById("eName");
        }
        //考试id
        var eId, eName, tId, aId, userNickName, userUserName, quesContent, quesAnswer, quesScore, userAnswer, score;

        function test001() {
            var post_score = score.value;
            isNumeric(score, "输入的分数格式不正确");
//            alert("oiver");

        }
        function btnnext() {
            var examId = eId.value;
            var typeId = tId.value;

            $.ajax({
                type: "Post",
                url: "/ajax/getNextUserAnswer",
                data: "examId=" + examId + "&typeId=" + typeId,
                success: function (remsg) {
                    if (remsg == "null") {
                        $("#btnMark").attr("disabled", "disabled");
                        alert("该考试所有题目已经评分完毕");
                    }
                    //alert(remsg);


                    var msg = eval("(" + remsg + ")");
                    eName.innerHTML = msg.examName;
                    aId.value = msg.answerId;
                    eId.value = msg.examId;
                    userName.innerText = msg.userUserName + "(" + msg.userNickName + ")";
                    quesContent.innerText = msg.quesContent;
                    quesAnswer.innerText = msg.quesAnswer;

                    userAnswer.innerText = msg.userAnswer;

                    quesScore.innerText = msg.quesScore;
                    score.value = "";
                }
            });
        }
        function btnmark() {
            var post_answerId = aId.value;
            var post_score = score.value;
            if (post_score == "") {
                alert("输入的分数格式不正确");
                return;
            }

            if(!isNumeric(score, "输入的分数格式不正确")){
                return ;
            }

            $.ajax({
                type: "Post",
                url: "/ajax/markOneAnswer",
                data: "answerId=" + post_answerId + "&score=" + post_score,
                success: function (msg) {
//                    alert(msg);
                    btnnext();
                }
            });
        }


    </script>

</head>
<body class="mainbody">
<div class="navigation">
    <a href="javascript:history.go(-1);" class="back">后退</a>首页 &gt; <a
        href="/answer/exams">需评分考试</a> &gt; <a href="#">手工评分</a></div>
<div>
    <input type="hidden" id="hid_answerId" value="${answer.id}"/>
    <input type="hidden" id="hid_examId" value="${answer.TExam.id}"/>


    <table class="form_table">
        <col width="150px">
        <col>
        <tbody>
        <tr>
            <th>当前考试：</th>
            <td id="eName">
                ${answer.TExam.examName}
            </td>
        </tr>
        <tr>
            <th>考生：</th>
            <td id="userName">
                ${answer.TUserGrade.TUser.username}(${answer.TUserGrade.TUser.nickname})
            </td>
        </tr>
        <tr>
            <th>题型：</th>
            <td>
                <select id="seltype" class="select2">
                    <option value="0" selected="selected">所有</option>
                    <c:forEach items="${types}" var="t">
                        <option value="${t.id}">${t.typeName}</option>
                    </c:forEach>
                </select>
            </td>
        </tr>
        <tr>
            <th>题目内容：</th>
            <td id="quesContent">
                ${answer.TPaperQues.quesContent}
                <%--<textarea id="quesContent" readonly="readonly" cols="30" rows="3" class="small2"--%>
                <%--style="background-color: #EFFFFF">--%>
                <%--</textarea>--%>
            </td>
        </tr>
        <tr>
            <th>标准答案：</th>
            <td id="quesAnswer">
                ${answer.TPaperQues.answer}
                <%--<textarea id="quesAnswer" readonly="readonly" cols="30" rows="3" class="small2"--%>
                <%--style="background-color: #EFFFFF">--%>
                <%--</textarea>--%>
            </td>
        </tr>
        <tr>
            <th>题目分数：</th>
            <td id="queScore">
                ${answer.TPaperQues.score}
            </td>
        </tr>
        <tr>
            <th>考生答案：</th>
            <td id="userAnswer" style="color:#00f">
                ${answer.answer}
                <%--<textarea id="userAnswer" readonly="readonly" style="background-color: #EFFFFF" cols="30" rows="3" class="small2">--%>
                <%--</textarea>--%>
            </td>
        </tr>
        <tr>
            <th>评分分数：</th>
            <td>
                <input id="userScore" class="txtInput normal"/>
            </td>
        </tr>
    </table>
</div>
<div class="foot_btn_box">
    <input type="button" value="确认分数" id="btnMark" class="btnSubmit" onclick="btnmark()"/>
    &nbsp;
    <input type="button" value=" 下一题 " id="btnNext" class="btnSubmit" onclick="btnnext()"/>
    &nbsp;
    <input type="button" value=" 返 回 " id="btnReturn" class="btnSubmit" onclick="javascript:history.go(-1);"/>
</div>
</body>
</html>