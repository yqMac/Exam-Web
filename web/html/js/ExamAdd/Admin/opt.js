var rightObj = {};

function getQueryString(name)
{
  var reg = new RegExp("(^|&|\\?)"+ name +"=([^&]*)(&|$)");
  var r = window.location.href.match(reg);
  if (r!=null) return unescape(r[2]); return null;
}

function MoveItems(type,global)
{
    var FromObj = (type==1)?document.getElementById("leftList"):document.getElementById("rightList");
    var ToObj = (type==1)?document.getElementById("rightList"):document.getElementById("leftList");
    if(global==undefined) global=false;
	
    var k = 0;
    var len = FromObj.options.length;
    Label:
    for(var i=0;i<len;i++)
    {
	    if(FromObj.options[i-k].selected || global)
	    {
		    if(type==1)
		    {
			    if(rightObj[FromObj.options[i-k].value]!=undefined)
			    {
				    FromObj.removeChild(FromObj.options[i-(k++)]);
				    continue;
			    }
			    rightObj[FromObj.options[i-k].value] = FromObj.options[i-k].innerText;
		    }
		    else
		    {
			    delete rightObj[FromObj.options[i-k].value];
		    }
		    var o = FromObj.removeChild(FromObj.options[i-(k++)]);
		    ToObj.appendChild(o);
	    }
    }
}

function SubmitForm()
{
    var rightList = document.getElementById("rightList");
    var UserIDs = "";
    var UserNames = "";
    
    var MinItem = getQueryString("MinItem");
    var MaxItem = getQueryString("MaxItem");
    
    if(MinItem!=null && MaxItem!=null)
    {
        if(parseInt(MaxItem)>parseInt(MinItem))
        {
            if(rightList.options.length<parseInt(MinItem) || rightList.options.length>parseInt(MaxItem))
            {
                window.alert("您必须选择" + MinItem + "-" + MaxItem + "个对象！");
                return;
            }
        }
    }
    else
    {
        if(MinItem!=null)
        {
            if(rightList.options.length<parseInt(MinItem))
            {
                window.alert("您必须选择" + MinItem + "个以上对象！");
                return;
            }
        }
        
        if(MaxItem!=null)
        {
            if(rightList.options.length>parseInt(MaxItem))
            {
                window.alert("您最多可以选择" + MaxItem + "个对象！");
                return;
            }
        }
    }  
    
    for(var i=0;i<rightList.options.length;i++)
    {
        UserIDs += rightList.options[i].value + ", ";
        UserNames += rightList.options[i].innerText + ", ";
    }
    if(UserIDs!="")
    {
        UserIDs = UserIDs.substr(0,UserIDs.length-2);
    }
    if(UserNames!="")
    {
        UserNames = UserNames.substr(0,UserNames.length-2);
    }	
    window.returnValue = [UserIDs,UserNames];
    window.close();
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
	var returnValue = window.showModalDialog(url,window,'dialogWidth:520px;dialogHeight:540px;status: no;scroll: no');
	if(returnValue != undefined) 
	{
	document.getElementById(UserIDElement).value = returnValue[0];
	document.getElementById(UserNameElement).value = returnValue[1];
	}	
}    
