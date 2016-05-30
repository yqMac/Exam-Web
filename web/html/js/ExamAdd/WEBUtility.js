// JS工具类


//增加一些方法

String.prototype.Trim = function(){ return Trim(this);};



function Trim(str) 
{ 
    var i; 
    for(i=0;i<str.length;i++) 
    { 
        if(str.charAt(i)!=" "&&str.charAt(i)!=" ")break; 
    } 
    str=str.substring(i,str.length); 
    
    for(i=str.length-1;i>=0;i--) 
    { 
        if(str.charAt(i)!=" "&&str.charAt(i)!=" ")break; 
    } 
    str=str.substring(0,i+1); 
    return str; 
} 
//重置指定的FORM
function ResetForm(objForm)
{
    if(confirm("确定要重置您录入的信息吗？重置后您之前的文字将无法保存。"))
    {
        objForm.reset();
        return true;
    }
    return false;
}
//
//获取当前页的指定参数值

function hooyesQueryString(queryStringName)
{
    var returnValue="";
    var URLString=String(document.location);
    var serachLocation=-1;
    var queryStringLength=queryStringName.length;
    do
    {
        serachLocation=URLString.indexOf(queryStringName+"\=");
        if (serachLocation!=-1)
        {
            if ((URLString.charAt(serachLocation-1)=='?') || (URLString.charAt(serachLocation-1)=='&'))
            {
            URLString=URLString.substr(serachLocation);
            break;
            }
            URLString=URLString.substr(serachLocation+queryStringLength+1);
        }
    }
    while (serachLocation!=-1);
        if (serachLocation!=-1)
        {
            var seperatorLocation=URLString.indexOf("&");
            if (seperatorLocation==-1)
            {
            returnValue=URLString.substr(queryStringLength+1);
            }
        else
        {
            returnValue=URLString.substring(queryStringLength+1,seperatorLocation);
        } 
    }
    return returnValue;
}
//获取指定URL的指定参数值

function getQueryString(queryStringName,url)
{
    var returnValue="";
    var URLString=url;
    var serachLocation=-1;
    var queryStringLength=queryStringName.length;
    do
    {
        serachLocation=URLString.indexOf(queryStringName+"\=");
        if (serachLocation!=-1)
        {
            if ((URLString.charAt(serachLocation-1)=='?') || (URLString.charAt(serachLocation-1)=='&'))
            {
            URLString=URLString.substr(serachLocation);
            break;
            }
            URLString=URLString.substr(serachLocation+queryStringLength+1);
        }
    }
    while (serachLocation!=-1);
        if (serachLocation!=-1)
        {
            var seperatorLocation=URLString.indexOf("&");
            if (seperatorLocation==-1)
            {
            returnValue=URLString.substr(queryStringLength+1);
            }
        else
        {
            returnValue=URLString.substring(queryStringLength+1,seperatorLocation);
        } 
    }
    return returnValue;
}
//获取objId的对象

function $(objId)
{
    return document.getElementById(objId);
}
//验证器，参数：需验证的对象，正则表达式，提示信息
//reExTxt的值设置为empty ，验证是否为空

function Validator(obj,regExTxt,errMsg)
{
    if(typeof(obj)!="undefined")
    {
        var _value;
        _value = obj.value;
        if(regExTxt=="empty")
        {
            if(_value.Trim().length==0)
            {
                if(errMsg!="")
                {
                    alert(errMsg);
                }
                obj.select();
                return false;
            }
            return true;
        }
        
        
        if(_value.search(regExTxt)!=0)
        {
                if(errMsg!="")
                {
                    alert(errMsg);
                }
            obj.select();
            return false;
        }
        return true;
            
    }
    return false;
}
//屏蔽刷新
function DisableRefresh(){ 
   with (event){ 
           // F5 and Ctrl+R 
     if (keyCode==116 || (ctrlKey && keyCode==82)){ 
       event.keyCode = 0; 
       event.cancelBubble = true; 
       return false; 
     } 
   } 
} 

/*绑定的页面初始化*/

//页面禁止刷新
document.onkeydown = DisableRefresh; 




