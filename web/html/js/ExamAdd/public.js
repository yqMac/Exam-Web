var oldSubObj;
var oldObj;
var m_IsMath="0";//0表示本版本不支持数学公式 1表示为数学公式
function openContent(sysname,url,note,obj)
{
	document.getElementById("tdTitle").innerHTML = "&nbsp;您的位置：" + sysname + "&gt;&gt;" + note;
	window.open(url,"content","");
	if(oldSubObj)
		oldSubObj.className = "menuUnselct";
	obj.className="menuSelect";
	oldSubObj = obj;
}

function showChild(obj,eventObj)
{
	var obj = document.getElementById(obj);
	var eventObj = document.getElementById(eventObj);
	if(obj.style.display=="block")
	{
		obj.style.display="none";//li_manage_plus
		eventObj.childNodes[0].innerHTML="<img src='../App_Image/Admin/plus.gif' />";
		return;
	}
	document.getElementById("span1").innerHTML="<img src='../App_Image/Admin/sub.gif' />";
	obj.style.display="block";
	obj.childNodes[0].click();
}

function showmenu(id) {
	var list = document.getElementById("list"+id);
	var menu = document.getElementById("menu"+id);
	var span = document.getElementById("span"+id);
	var click = document.getElementById("click"+id);
	if (list.style.display=="none") {
		document.getElementById("list"+id).style.display="block";
		span.innerHTML="<img src='../App_Image/Admin/sub.gif' />";
		click.onclick();
	}else {
		document.getElementById("list"+id).style.display="none";
		span.innerHTML="<img src='../App_Image/Admin/plus.gif' />";
	}
}
//数学公式的处理
function MathBuild(m_Content)
{
    var p_Content="";
    if(m_IsMath=="0")
    {
        p_Content=m_Content;
    }
    else
    {
        p_Content=new Equation([m_Content,70, 50,0]);
    }
    return p_Content;
}