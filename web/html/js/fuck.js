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

//<![CDATA[
function WebForm_OnSubmit() {
    if (typeof (ValidatorOnSubmit) == "function"
        && ValidatorOnSubmit() == false)
        return false;
    return CheckForm();
    return true;
}
var TXID = 'SelectedTX';
function InitExamTX(pageLoad) {
    var obj = document.getElementsByName("Tm_tx_ID");
    var tx = "";
    var txnameArr = [];
    var isAdd;

    for (var i = 0; i < obj.length; i++) {
        isAdd = true;

        if (obj[i].checked) {

            if (isAdd) {

                tx += obj[i].value + ",";
                txnameArr[txnameArr.length] = obj[i].parentNode.nextSibling.innerText;
            }
        }
    }
    //���tx��Ϊ��
    //����ѡ����һ����ѡ��
    if (tx != "") {
        //�������
        //substr �ַ����ӵڼ�λȡ���ڼ�λ
        //��0���ܳ��ȼ�һ
        //ȥ�����һ���ַ�  ���һ��,
        var TXValue = tx.substr(0, tx.length - 1);
        //document.getElementById(TXID).value �õ�  SelectedTX ����ؼ�(����Ԫ��) ��ֵ
        //����������ֵ �� TXValue ��ͬ
        if (document.getElementById(TXID).value == TXValue) {
            SetTMTotalNum();
            //���� ����Ĵ��벻��ִ��
            return;
        }
        //����������ֵ �� TXValue��ͬ
        //��������ֵ
        document.getElementById(TXID).value = TXValue;
        //����Idȥ����
        //���
        var myTable = document.getElementById("PolicyTable");
        //ÿ��������� <tr>��ʾ
        //�Ա����У�rows������ѭ��
        for (var i = myTable.rows.length - 1; i >= 0; i--) {
            //ɾ����
            myTable.deleteRow(i);
        }
        //������� = ���.������
        var myNewRow = myTable.insertRow();
        //�����е���ʽ��  tab_label
        myNewRow.className = "tab_label";
        //�߶�24
        myNewRow.height = 24;
        //InsertCell����
        //����  �������  �ַ���  λ�� ����
        InsertCell(myNewRow, "��⼯", "center", 100);
        InsertCell(myNewRow, "���", "center", 100);
        InsertCell(myNewRow, "֪ʶ��", "center", 100);
        InsertCell(myNewRow, "�Ѷ�", "center", 50);
        //�������Լ����������� txnameArr  ����ѡ��,����⡭���� ����ѭ��
        for (var i = 0; i < txnameArr.length; i++) {
            InsertCell(myNewRow, txnameArr[i], "center", 100);
        }
        InsertCell(myNewRow, "����", "center", 50);
        //�ֽ�����һ��
        var myNewRow = myTable.insertRow();
        myNewRow.className = "tab_label";
        myNewRow.height = 24;
        //4 �ϲ���Ԫ��
        InsertCell(myNewRow, "�ܼ�", "center", 100, 4);
        //�� txidArr ����ѭ�� ����,����
        var txidArr = TXValue.split(",");
        //ѭ��
        for (var i = 0; i < txidArr.length; i++) {
            //����  ���ı���
            InsertCell(
                myNewRow,
                "<input type='text' readonly='readonly' style='width:20px;background-color:#FFFF80' id='stat_" + txidArr[i] + "'>",
                "center", 100);
        }
        InsertCell(myNewRow, "&nbsp;", "center", 50);
        //����� 400+���鳤��*100
        myTable.width = 400 + txnameArr.length * 100;

    } else {
        window.alert("��������ѡȡ1�����Ͳ����Ծ���ɲ���");
        changeTabThree('2');
    }
}

function DoStart() {
    document.getElementById("btn_Submit").disabled = false;
}

function ActiveTabChanged(sender, e) {
    if (sender.get_activeTab().get_tabIndex() == 2) {
        InitExamTX();
    }
    if (sender.get_activeTab().get_tabIndex() == 1) {
        RedomTxSet();
    }
}


function GetTKList(tkjId) {
    var objid = "TKList";
    //����Id�� TKList
    //�������������������
    document.getElementById(objid).innerHTML = "";
    //ͨ�� InsertOption ����
    //����  ���  �ַ�(��������...)  -1
    InsertOption(document.getElementById(objid), '��������...', '-1');
    //����
    document.getElementById(objid).disabled = true;

    //ajax
    $.ajax({
        //post�ύ
        type: "POST",
        //��ַ
        url: "tk_findTkByTkjId",
        //��ַ����ֵ
        //��⼯������ѡ�е�ֵ  ��⼯����Id
        data: "tkjId=" + tkjId,
        //�ɹ�
        success: function (msg) {
            //alert(msg);
            //�з���ֵ
            if ($.trim(msg) != "") {
                //���
                //attr ����  �� disabled���� ����Ϊ false
                $("#" + objid).attr("disabled", false);
                $("#" + objid).html(msg);
                //��⼯�����仯
                //�����������˱仯
                //�ĸ���ⱻĬ��ѡ��
                //alert(document.getElementById(objid).value);
                GetZSDList(tkjId, $("#" + objid).val());
            } else {
                $("#" + objid).attr("disabled", true);
                $("#" + objid).html(
                    "<option value='0'>=�������=</option>");
            }
        }
    });
}


function GetZSDList(tkjId, tkId) {
    var objid = "ZSDList";
    //��֪ʶ�����������
    document.getElementById(objid).innerHTML = "";
    //
    InsertOption(document.getElementById(objid), '֪ʶ�������...', '-1');
    //����
    document.getElementById(objid).disabled = true;

    //ajax
    $.ajax({
        //post�ύ
        type: "POST",
        //��ַ
        //�������Id�õ�֪ʶ��
        url: "tm_findZsdByTkId",
        //��ַ����ֵ
        //���������ѡ�е�ֵ  �������Id
        data: "tkjId=" + tkjId + "&tkId=" + tkId,
        //�ɹ�
        success: function (msg) {
            //alert(msg);
            //�з���ֵ
            if ($.trim(msg) != "") {
                //���
                //attr ����  �� disabled���� ����Ϊ false
                $("#" + objid).attr("disabled", false);
                //innerHTML
                $("#" + objid).html(msg);
            } else {
                $("#" + objid).attr("disabled", true);
                $("#" + objid).html(
                    "<option value='����֪ʶ��'>=����֪ʶ��=</option>");
            }
        }
    });
}

function PolicyEnabled(k) {
    var myObj = document.getElementById("PolicyTable")
        .getElementsByTagName("INPUT");
    for (var i = 0; i < myObj.length; i++) {
        myObj[i].disabled = k;
    }

    document.getElementById('tmtx_all').disabled = k;

    myObj = document.getElementsByName("Tm_tx_ID");
    for (var i = 0; i < myObj.length; i++) {
        myObj[i].disabled = k;
    }
}

var tkjName = "";
//���������
//֪ʶ���ѯ
function PolicyAdd() {
    if ($("#TKJList").val() == -1) {
        alert("��ѡ����⼯");
        document.getElementById("TKJList").focus();
        return false;
    }
    //alert("tkjid=" + $("#TKJList").val() + "&tkid=" + $("#TKList").val() + "&zsd=" + $("#ZSDList").val() +
    //  		   "&nd=" + $("#NDList").val() + "&tx=" + $("#SelectedTX").val());
    //��ť����
    //document.getElementById('PolicyAddButton').disabled = true;
    //ajax
    $.ajax({
        //post�ύ
        type: "POST",
        //��ַ
        //������⼯Id���Id֪ʶ����Ŀ���ͳ���
        url: "tm_findTmByTkjIdTkIdZsd",
        //��ַ����ֵ
        //��⼯ ��� ֪ʶ�� �Ѷ� ����
        data: "tkjid=" + $("#TKJList").val() + "&tkid="
        + $("#TKList").val() + "&zsd="
        + $("#ZSDList").val() + "&nd=" + $("#NDList").val()
        + "&tx=" + $("#SelectedTX").val(),
        //�ɹ�
        success: function (msg) {
            //�����ַ���
            //alert(msg);
            //[{"tmTxId":1,"tmTxNum":4},{"tmTxId":2,"tmTxNum":1},{"tmTxId":3,"tmTxNum":1}]
            //{"tkjName":"�����ϵ","list":[{"tmTxId":1,"tmTxNum":4},{"tmTxId":2,"tmTxNum":1},{"tmTxId":3,"tmTxNum":1}]}
            //���ַ���ת��json��ʽ
            var json = eval("(" + msg + ")");
            if (chkTk($("#TKJList option:selected").text(), $(
                    "#TKList option:selected").text(), $(
                    "#ZSDList option:selected").text())) {
                if (json.list.length > 0) {
                    AddRow(json);
                } else {
                    alert("����ѡ��û����Ŀ");
                    return false;
                }
            }
        }
    });

    /*             var LocationUrl;
     LocationUrl = "ExamPolicyAdd.ashx";
     LocationUrl += "?TKJID=" + document.getElementById('TKJList').value;
     LocationUrl += "&TKID=" + document.getElementById('TKList').value;
     LocationUrl += "&ZSD=" + escape(document.getElementById('ZSDList').value);
     LocationUrl += "&ND=" + document.getElementById('NDList').value;
     LocationUrl += "&TX=" + document.getElementById('SelectedTX').value;
     document.getElementById('PolicyScript').src=LocationUrl; */
}

//���һ��
function AddRow(json) {
    //ͨ��Id�ҵ���� ��ֵ�� myTable����
    var myTable = document.getElementById("PolicyTable");
    //���������һ��
    var myNewRow = myTable.insertRow(myTable.rows.length - 1);
    //myTable.rows �еĳ���
    //2�ı���һ����ʽ ���� ����һ����ʽ
    if (myTable.rows.length % 2 == 0) {
        myNewRow.className = 'tab_tr1';
    } else {
        myNewRow.className = 'tr_al';
    }
    myNewRow.height = 24;
    //json����
    //alert(json.tkjName);
    tkjName = json.tkjName;
    //��⼯
    InsertCell(myNewRow, tkjName, 'center');
    //���
    //ͨ��Id�ҵ�����������ѡ���ҵ��ı�
    InsertCell(myNewRow, $("#TKList option:selected").text(),
        'center');
    //֪ʶ��
    InsertCell(myNewRow, $("#ZSDList option:selected").text(),
        'center');
    //�Ѷ�
    InsertCell(myNewRow, $("#NDList option:selected").text(),
        'center');

    //json.list �� json��һ������  ��������һ��json����
    var jsonArr = json.list;
    //document.getElementById(TXID).value �����������ŵ�����  1,2
    var TXValue = document.getElementById(TXID).value;
    //�� txidArr ����ѭ�� ����,����
    var txidArr = TXValue.split(",");
    //ѭ�� ����
    for (var i = 0; i < txidArr.length; i++) {
        //txidArr[i]  ����ѭ��������  1  2
        //{"tkjName":"�����ϵ","list":[{"tmTxId":1,"tmTxNum":4},{"tmTxId":2,"tmTxNum":1},{"tmTxId":3,"tmTxNum":1}]}
        //{"tkjName":"�����ϵ","list":[{"tmTxId":1,"tmTxNum":4},{"tmTxId":2,"tmTxNum":1}]}
        //{"tkjName":"���ⳣʶ","list":[{"tmTxId":1,"tmTxNum":2}]}
        //ѭ������json��

        //i �϶��� 0 1 2
        //����ѭ��������
        //1���ж�   ����ѭ���ĵ�һ������3 �� json�������һ������2
        //if(txidArr[i] == jsonArr[i].tmTxId){
        //	InsertCell(myNewRow,"<input type='text' name='tm_num_" + tmtxObject.tmTxId + "' value='0' onmouseover='this.select()' onblur='CheckTMNum(this)' class='InputSolid' style='width:15px' > / <font color=\"red\">" + tmtxObject.tmTxNum + "</font>","center");
        //}
        //else{
        //	InsertCell(myNewRow,"<input type='text' name='tm_num_" + tmtxObject.tmTxId + "' value='0' onmouseover='this.select()' onblur='CheckTMNum(this)' class='InputSolid' style='width:15px' > / <font color=\"red\">0</font>","center");
        //}

        //��ʱ����
        var tempNum = -1;
        //1,2,4
        for (var j = 0; j < jsonArr.length; j++) {
            //alert(i);
            //ѭ������õ�����
            var tmtxObject = jsonArr[j];
            //����.����
            //alert(tmtxObject.tmTxId);
            //alert(tmtxObject.tmTxNum);
            //������ѭ��������  = json�����������
            //alert(txidArr[i] + "a");
            //alert(tmtxObject.tmTxId + "b");
            //�������ѭ�������� = json�����������
            if (txidArr[i] == tmtxObject.tmTxId) {
                //alert("aaaaaaa");
                //InsertCell(myNewRow,"<input type='text' name='tm_num_" + tmtxObject.tmTxId + "' value='0' onmouseover='this.select()' onblur='CheckTMNum(this)' class='InputSolid' style='width:15px' > / <font color=\"red\">" + tmtxObject.tmTxNum + "</font>","center");
                tempNum = j;
                break;
            }
            //else{
            ////��ӿ�
            //alert("bbbbbb");
            //����ѭ��������Id > json����������ʹ�
            //if(txidArr[i] < tmtxObject.tmTxId){
            //	InsertCell(myNewRow,"<input type='text' name='tm_num_" + tmtxObject.tmTxId + "' value='0' onmouseover='this.select()' onblur='CheckTMNum(this)' class='InputSolid' style='width:15px' > / <font color=\"red\">0</font>","center");
            //}
            //}
        }
        if (tempNum > -1) {
            //alert(tempNum);
            //alert(jsonArr[tempNum].tmTxId);
            InsertCell(
                myNewRow,
                "<input type='text' name='tm_num_"
                + jsonArr[tempNum].tmTxId
                + "' value='0' onmouseover='this.select()' onblur='CheckTMNum(this)' class='InputSolid' style='width:15px' > / <font color=\"red\">"
                + jsonArr[tempNum].tmTxNum + "</font>",
                "center");
        } else {
            InsertCell(
                myNewRow,
                "<input type='text' name='tm_num_"
                + txidArr[i]
                + "' value='0' onmouseover='this.select()' onblur='CheckTMNum(this)' class='InputSolid' style='width:15px' > / <font color=\"red\">0</font>",
                "center");
        }

    }
    InsertCell(
        myNewRow,
        "<input type='hidden' name='clTkjId' value='"
        + $("#TKJList option:selected").val()
        + "'>"
        + "<input type='hidden' name='clTkId' value='"
        + $("#TKList option:selected").val()
        + "'>"
        + "<input type='hidden' name='clZsd' value='"
        + $("#ZSDList option:selected").val()
        + "'>"
        + "<input type='hidden' name='nd' value='"
        + $("#NDList option:selected").val()
        + "'>"
        + "<input type='button' value='ɾ��' class='two_button_w' onclick='DeleteRow(this)'>",
        "center");
}

//�ж���⼯����⣩
//��⼯ ��� ֪ʶ��
function chkTk(p_TkjName, p_TkName, p_ZSD) {
    //ͨ��Id�ҵ���� ��ֵ�� myTable����
    var myTable = document.getElementById("PolicyTable");
    var result = true;
    //�Ա�������н���ѭ��
    for (var i = 0; i < myTable.rows.length; i++) {
        //old
        //����ѭ���ĵ�һ����Ԫ�� = ��⼯����
        //����    (�ڶ�����Ԫ��  = '����'  ����  �ڶ�����Ԫ�� = �������)
        //����     (��������Ԫ�� = '����' ���� ��������Ԫ�� = ֪ʶ��)

        //my
        //��һ����Ԫ�� = ��⼯ ���� �ڶ�����Ԫ�� = ���  ���ҵ�������Ԫ�� = ֪ʶ��
        if (myTable.rows[i].cells[0].innerHTML == p_TkjName
            && (myTable.rows[i].cells[1].innerHTML == p_TkName || myTable.rows[i].cells[1].innerHTML == '����')
            && (myTable.rows[i].cells[2].innerHTML == p_ZSD || myTable.rows[i].cells[2].innerHTML == '����')) {
            alert("�Ѿ��д����,��ѡ���������!");
            result = false;
            break;
        }
    }
    return result;
}

function PolicyModify() {
    if (window.confirm("�޸Ĳ��Ժ��Ծ����⽫���½�����ԭ���Ծ����⽫�ᱻɾ����\n��ȷ����")) {
        document.getElementById('PolicyAddButton').style.display = 'inline';
        document.getElementById('PolicyModifyButton').style.display = 'none';
        document.getElementById('PolicyFlag').value = '1';
        PolicyEnabled(0);
    }
}

function CheckTMNum(obj) {
    var tm_num_max = obj.parentNode.innerText.replace(" / ", "");
    if (isNaN(tm_num_max))
        return;

    if (isNaN(obj.value)) {
        obj.value = "0";
    } else {
        if (parseInt(obj.value) > parseInt(tm_num_max)) {
            obj.value = tm_num_max;
        }
    }

    SetTMTotalNum();
}

function SetTMTotalNum() {
    var myTable = document.getElementById('PolicyTable');
    if (myTable.rows.length > 0) {
        var ScoreType = document.getElementById('ScoreType02').checked;
        var TXNumObj;
        var TXArr = document.getElementById(TXID).value.split(",");
        var TotalAccount = 0; //��Ŀ����
        var TotalScore = 0; //��Ŀ�ܷ�
        var TXAccount; //������Ŀ����
        var TempNum;
        for (var i = 0; i < TXArr.length; i++) {
            TXNumObj = document.getElementsByName("tm_num_"
                + TXArr[i]);
            TXAccount = 0;
            for (var j = 0; j < TXNumObj.length; j++) {
                if (TXNumObj[j].value == "") {
                    TempNum = 0;
                } else {
                    TempNum = parseInt(TXNumObj[j].value);
                }
                TotalAccount += TempNum;
                TXAccount += TempNum;
                if (ScoreType) {
                    TotalScore += parseInt(document
                            .getElementById("score_" + TXArr[i]).value)
                        * TempNum;
                }
            }
            document.getElementById("stat_" + TXArr[i]).value = TXAccount;
        }
        document.getElementById('TotalAccount').value = TotalAccount;
        if (ScoreType) {
            document.getElementById('TotalScore').value = TotalScore;
        } else {
            document.getElementById('TotalScore').value = document
                .getElementById('is_100').value;
        }
    }
}

function DeleteRow(obj) {
    var myTable = document.getElementById("PolicyTable");
    var myRow = obj.parentNode.parentNode;
    myTable.deleteRow(myRow.rowIndex);

    SetTMTotalNum();
}

function AdjustTMTXOrderKey() {
    var k = 0;
    var TMTXObj = document.getElementsByName("Tm_tx_ID");
    for (var i = 0; i < TMTXObj.length; i++) {
        if (TMTXObj[i].checked)
            k++;
    }
    for (var i = 0; i < TMTXObj.length; i++) {
        if (!TMTXObj[i].checked) {
            k++;
            document.getElementById("orderkey_" + TMTXObj[i].value).value = k
                .toString();
        }
    }
}

function CheckForm() {
    InitExamTX();
    var TabsID = "";
    if (document.getElementById('tk_cl_name').value == "") {
        changeTabThree('1');
        document.getElementById('tk_cl_name').focus();
        window.alert("�Ծ����Ʋ���Ϊ�գ�");
        DoStart();
        changeTabThree('1');
        return false;
    }
    var reg = /^[0-9]*[1-9][0-9]*$/;
    if (document.getElementById('rbtnSJ').checked) {
        //alert(document.getElementById('SuitNumber').value);
        if (!reg.test(document.getElementById('SuitNumber').value)) {
            alert("����ֻ��������");
            DoStart();
            changeTabThree('1');
            return false;
        } else {
            if (document.getElementById('SuitNumber').value == "1") {
                alert("����ֻ�������ֶ���ʧ����2��!");
                DoStart();
                changeTabThree('1');
                return false;
            }
        }
    }
    if (document.getElementById('TotalAccount').value == "0") {
        window.alert("��û��ѡ����Ŀ��");
        changeTabThree('2');
        DoStart();
        return false;
    }
    if (parseInt(document.getElementById('PassFS').value) > parseInt(document
            .getElementById('TotalScore').value)) {
        document.getElementById('PassFS').select();
        window.alert("�������̫���ˣ�");
        DoStart();
        return false;
    }
    if (parseInt(document.getElementById('PassFS').value) < 0) {
        document.getElementById('PassFS').select();
        window.alert("�����������С��0��");
        DoStart();
        return false;
    }
    var BTime = ParseDate(document.getElementById('valid_btime').value);
    var ETime = ParseDate(document.getElementById('valid_etime').value);
    if (BTime == "NaN") {
        changeTabThree('1');
        document.getElementById('valid_btime').select();
        window.alert("������һ����Ч�Ŀ�ʼʱ�䣡");
        changeTabThree('1');
        DoStart();
        return false;
    }
    if (ETime == "NaN") {
        changeTabThree('1');
        document.getElementById('valid_etime').select();
        window.alert("������һ����Ч�Ľ���ʱ�䣡");
        changeTabThree('1');
        DoStart();
        return false;
    }
    if (BTime > ETime) {
        changeTabThree('1');
        document.getElementById('valid_etime').select();
        window.alert("��Ч��ֹʱ�䲻��С����Ч��ʼʱ�䣡");
        changeTabThree('1');
        DoStart();
        return false;
    }
    if (document.getElementById('tk_cl_time_1').checked) {
        if (BTime.toLocaleDateString() != ETime
                .toLocaleDateString()) {
            changeTabThree('1');
            document.getElementById('valid_etime').select();
            window
                .alert("���ڿ��Լ�ʱѡ����ѡ��\"����ͳһ����\"��ʱ���Ծ���Ч���ռ�αض���һ���ڣ�");
            DoStart();
            return false;
        }
    }
    return true;
}

function AdjustOption_Y(ObjID1, ObjID2, Num) {
    if (Num == 1) {
        document.getElementById(ObjID1).style.backgroundColor = "#FFFFFF";
        document.getElementById(ObjID1).disabled = false;
        document.getElementById(ObjID2).style.backgroundColor = "#FFFFFF";
        document.getElementById(ObjID2).disabled = false;
    } else {
        document.getElementById(ObjID1).style.backgroundColor = "#EEFFFF";
        if (document.getElementById(ObjID1).type == "checkbox") {
            document.getElementById(ObjID1).checked = false;
        }
        document.getElementById(ObjID1).disabled = true;

        document.getElementById(ObjID2).style.backgroundColor = "#EEFFFF";
        if (document.getElementById(ObjID2).type == "checkbox") {
            document.getElementById(ObjID2).checked = false;
        }
        document.getElementById(ObjID2).disabled = true;
    }
}

//function chkTk(p_TkjName,p_TkName)
//{
//    var myTable = document.getElementById('PolicyTable');
//    //add by bowei ��ֹ����ظ������

//    for(var i=0;i<myTable.rows.length;i++)
//    {
//    	alert(myTable.rows[i].cells[0].innerHTML);
//    	alert(p_TkjName);
//    	alert(myTable.rows[i].cells[1].innerHTML);
//    	alert(p_TkName);
//        if(myTable.rows[i].cells[0].innerHTML== p_TkjName  && (myTable.rows[i].cells[1].innerHTML=='����' || myTable.rows[i].cells[0].innerHTML== p_TkName ))
//        {
//            alert('�Ѿ��д����,��ѡ���������!');
//            return false;
//        }
//    }
//    return true;
//}


function openwin() {
    var text = document.getElementById('tk_cl_name').value;

    if (text == "") {
        alert("��ֻ�ж������Ծ����Ʋſ��Խ�����Ϣ���ѣ�");
        return false;
    }
    var Stime = document.getElementById('valid_btime').value;
    var Etime = document.getElementById('valid_etime').value;

    FixSizeWin("ExamMessage.aspx?ClName=" + escape(text)
        + "&Stime=" + Stime + "&Etime=" + Etime, "message",
        500, 255)
}
//�ͷŵ����Ĵ��ڡ�<body onunload='CloseWin'>
function CloseWin() {
    if (w != null) {
        w.close();
    }
}

