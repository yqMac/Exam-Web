//
//	函数	FixSizeWin
//
//	作用	构建窗体对象
//
//	参数	url 窗体url, target 窗体目标, width 窗体宽度, height 窗体高度, resize 是否可改变窗口大小

//
//	返值	不返回值

//
var w = null;
function FixSizeWin(url,target,width,height,resize,fullscreen,scrollbars){
        var tt,left,top,sc;
        sc= "no";
       
        if(fullscreen)
        {
            left=0;
            top=0;
            width=screen.availWidth;
            height=screen.availHeight;
        }
        else
        {
		    if (!width) width=screen.width;
		    if (!height) height=screen.height-60;
		    left=(screen.width-width)/2;
		    if(left<0){ left=0;}

		    top=(screen.height-60-height)/2;
		    if(top<0){ top=0;}
		}
        if(scrollbars>0)
        {
            sc="yes";
        }
        tt="toolbar=no, menubar=no, scrollbars="+sc+",location=no, status=no,";
        /*
        if(resize==true)
        {
            tt=tt+"resizable=yes,";
        }
        else
        {
            tt=tt+"resizable=no,";
        }
        */
        tt =tt + "resizable=yes,";
        tt=tt+"width="+width+",height="+height+",left="+left+",top="+top;
	    w=window.open(url,target,tt);
	try{
		w.focus();
	}catch(e){}
}
//
//	函数	UserDialog
//
//	作用	创建选择帐户窗体
//
//	参数	RootUrl 学习平台的相对根路径, UserIDElement 用于存放UserID的HiddenField控件的ID名称, UserNameElement 用于存放UserName的TextBox控件的ID名称, MinItem 最少选择人员数目，可选参数,MaxItem 最多选择人员数目，可选参数

//
//	返值	不返回值

//
function UserDialog(RootUrl,UserIDElement,UserNameElement,MinItem,MaxItem)
{
	var url = RootUrl + "Admin/SelectStudent.aspx";
	url += "?UserIDs=" + document.getElementById(UserIDElement).value;
	url += "&UserNames=" + escape(document.getElementById(UserNameElement).value);
	if(MinItem!=null)
	{
	    url += "&MinItem=" + MinItem;   
	}
	if(MaxItem!=null)
	{
	    url += "&MaxItem=" + MaxItem;   
	}
	alert(url);
	var returnValue = window.showModalDialog(url,window,'dialogWidth:520px;dialogHeight:540px;status: no;scroll: no');
	if(returnValue != undefined) 
	{
	document.getElementById(UserIDElement).value = returnValue[0];
	document.getElementById(UserNameElement).value = returnValue[1];
	}	
}
//
//	函数	AdjustOption
//
//	作用	根据不同的选择项启用或禁用相应的输入框
//
//	参数	ObjID 输入框ID, Num 传入的值，为1时启用输入框，否则禁用输入框
//
//	例子	参考Admin/Exam/ExamCreate.aspx页面相关的用法

//
//	返值	不返回值

//
function AdjustOption(ObjID,Num)
{
    if(Num==1)
    {
        document.getElementById(ObjID).style.backgroundColor = "#FFFFFF";
        document.getElementById(ObjID).disabled = false;
    }
    else
    {
        document.getElementById(ObjID).style.backgroundColor = "#EEFFFF";
        if(document.getElementById(ObjID).type=="checkbox")
        {
            document.getElementById(ObjID).checked = false;
        }        
        document.getElementById(ObjID).disabled = true;
    }
}

//
//	函数	CheckAll
//
//	作用	全选/取消全选

//
//	参数	Obj 对象, ContainerID 容器ID，可以不填

//
//	例子	参考Admin/Exam/ExamCreate.aspx页面相关的用法

//
//	返值	不返回值

//
function CheckAll(Obj,ContainerID)
{
    try
    {
        var OperObj;
        if(ContainerID==null)
        {
            OperObj = document.getElementsByTagName("INPUT");
        }
        else
        {
            OperObj = document.getElementById(ContainerID).getElementsByTagName("INPUT");
        }
        for(var i=0;i<OperObj.length;i++)
        {
            if(OperObj[i]!=Obj)
            {
                if(OperObj[i].type=="checkbox")
                {
	                OperObj[i].checked = Obj.checked;
	            }
	        }
        }
    }
    catch(e)
    {
    }
}

//
//	函数	InsertCell
//
//	作用	表格动态的添加列

//
//	参数	RowObj 行对象, HTMLContent 列的内容，RowAlign 列对齐方式，可以不填,RowWidth 列宽度，可以不填,RowColSpan 合并列数目

//
//	返值	不返回值

//
function InsertCell(RowObj,HTMLContent,RowAlign,RowWidth,RowColSpan)
{
    var myNewCell = RowObj.insertCell();
    myNewCell.innerHTML = HTMLContent;
    if(RowAlign!=null)
    {
        myNewCell.align = RowAlign;
    }
    if(RowWidth!=null)
    {
        myNewCell.width = RowWidth;
    }
    if(RowColSpan!=null)
    {
        myNewCell.colSpan = RowColSpan;
    }
}

//
//	函数	InsertOption
//
//	作用	下拉列表地动态的添加行

//
//	参数	obj 下拉对象, text 文字内容，value 值内容

//
//	返值	不返回值

//
function InsertOption(obj,text,value)
{
    var oOption = document.createElement("OPTION");
    obj.options.add(oOption);
    oOption.innerText = text;
    if(value!=null)
    {
        oOption.value = value;
    }
    else
    {
        oOption.value = text;
    }
}

//
//	函数	Adjust
//
//	作用	给操作对象设置一个默认值或默认选项
//
//	参数	obj 操作对象, str 默认值

//
//	返值	不返回值

//
function Adjust(obj,str)
{
	if(obj.type == "text")	//text
	{
		obj.value = str;
	}
	else if(obj.type == "select-one")	//select
	{
		for(i=0;i<obj.length;i++)
		{
			if(obj.options[i].value == str)
				obj.options[i].selected = true;
		}
	}
	else if(obj[0].type == "radio")	//radio
	{
		for(i=0;i<obj.length;i++)
		{
			if(obj[i].value == str)
				obj[i].checked = true;
		}
	}
	else if(obj[0].type == "checkbox")	//checkbox
	{
		for(i=0;i<obj.length;i++)
		{
			if(obj[i].value == str)
				obj[i].checked = true;
		}
	}
}

//
//	函数	ParseDate
//
//	作用	将String字符串类型转换为Date日期类型，可用于检测是否是日期类型或根据日期类型做日期比较等操作

//
//	参数	str 字符串

//
//	返值	日期，解析不成功，返回"NaN"
//
function ParseDate(str)
{
    var myDate = new Date(Date.parse(str.replace(/-/g,"/")));
    return myDate;
}


//分页函数, 设置页面编号
function SetPageIndex(pindex)
{
    document.getElementById("PageIndex").value=pindex;   
    //alert(document.getElementByName("SearchButton").value)            ;
    __doPostBack("SearchButton","");
    //__doPostBack("SearchButton","")
}

//分页函数,用Go按钮触动设置页面
function ButtonSetPageIndex()
{
    document.getElementById("PageIndex").value=document.getElementById("TbPageNum").value;                   
    
    __doPostBack("SearchButton","");
    //form1.submit();    
}
//
//	函数	SelectDate
//
//	作用	创建选择日期窗体
//
//	参数	RootUrl 学习平台的相对根路径, DivID 控件ID
//
//	返值	不返回值

//
function SelectDate(RootUrl,DivID,Mode)
{
    return false;
	var fullPath = RootUrl + "App_JavaScript/Admin/calendar/PopDate.Htm";
	var strDate;	//Chinese date formate
	var intDate;	//Integer date formate
	var objEffectDate=DivID;
	intDate=showModalDialog(fullPath,window,"dialogWidth=335px;dialogHeight=303px;center=yes;border=this;help=no");
	if (intDate!=null)
	{
		objEffectDate.value=intDate.substr(0,4)+"-"+intDate.substr(5,2)+"-"+intDate.substr(8,2);
		if (objEffectDate.value!="undefined")
		{
			strDate=intDate.substr(0,4)+"-"+intDate.substr(5,2)+"-"+intDate.substr(8,2);
			if(Mode==1)
			{
			    strDate+=" 0:00:00";
			}
			else if(Mode==2)
			{
			    strDate+=" 23:59:59";
			}
			objEffectDate.value=strDate;
		}
		else
		{
			objEffectDate.value="";
		}
	}
	event.returnValue =false;
}