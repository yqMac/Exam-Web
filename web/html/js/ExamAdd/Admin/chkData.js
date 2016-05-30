/* *******************************************************************************
 * 作    者：tianjj/田建军
 * 创建时间：2007-6-16 11:09:05
 * 描    述：
 * 修 改 人：
 * 修改时间：
 * 描    述：
 * ******************************************************************************* */

String.prototype.Trim =function(){
	return   this.replace(/(^\s*)|(\s*$)/g, "");
};

//检查身份证输入格式是否正确，如不正确弹出警告对话框
function isIdCard(s,msg)
{
	var str=s.value.Trim();
	if (str.length == 0)
	{
		return true;
	}
	str = s.value.Trim().replace(/[^\x00-\xff]/g,"**").length;
	if(str != 15 && str != 18)
	{
		alert((msg?msg:'身份证输入格式错误！请改正。'));
		s.focus();
		if(s.type == "text" || s.type == "TEXTAREA")
		{
			s.select();
		}
		return false;
	}
	str = s.value.Trim().substr(0,17);
	if(str*1!=str)
	{
		alert((msg?msg:'身份证输入格式错误！请改正。'));
		s.focus();
		if(s.type == "text" || s.type == "TEXTAREA")
			s.select();
		return false;
	}
	return true;
}
	
	//验证日期是否大于今天
function isAfterToday(s,msg)
{
    if (s == undefined)
    {
        return true;
    }
    
    if (s.value.length == 0)
	{
		return true;
	}
	
	if (isDate(s,msg) == false)
	{
	    return false;
	}
	else
	{
	   	var str = s.value;
		var reg = /^(\d{4})(-|\/)(0?[1-9]|1[0-2])(-|\/)(0?[1-9]|[12][0-9]|3[01])$/g; 
		var r = reg.exec(str); 
		if(r == null)
		{
			alert(msg);
			if(s.type == "text" || s.type == "TEXTAREA")
				s.select();
			return false;
		}
	    var valDate = new Date(r[1], r[3]-1,r[5]); 
	    var today = new Date();
	    if (today < valDate)
	    {
	       return true;
	    }
	    else
	    {
	        alert((msg?msg:'请选择大于今天的日期'));
	        if(s.type == "text" || s.type == "TEXTAREA")
				s.select();
			return false;
	    }
	}
}
	
	
//检查输入日期格式是否正确，不正确则弹出提示对话框

function isDate(s,msg)
{
	if(s == undefined)
	{ 
		return true;
	}
		if (s.value.length == 0)
		{
			return true;
		}
		if (!msg)
		{
			msg='不存在该日期﹐请改正';
		}
		var str = s.value;
		var reg = /^(\d{4})(-|\/)(0?[1-9]|1[0-2])(-|\/)(0?[1-9]|[12][0-9]|3[01])$/g; 
		var r = reg.exec(str); 
		if(r == null)
		{
			alert(msg);
			if(s.type == "text" || s.type == "TEXTAREA")
				s.select();
			return false;
		}
		var d = new Date(r[1], r[3]-1,r[5]); 
		var newStr = d.getFullYear()+r[2]+(d.getMonth()+1)+r[2]+d.getDate();
		var reg = /(\/|-)(0*)( *)([1-9])/g;
		var t = s.value.replace(reg,'$1$4');
		if (newStr == t)
		{
			if(s.type == "text" || s.type == "TEXTAREA")
			{
				var strTemp = s.value;
				strTemp = strTemp.replace(/[^0-9]/g,'-');
				strTemp = strTemp.replace(/(\/|-)(\d)\b/g,'-0$2');
				s.value = strTemp;
			}
			return true;
		}
		s.focus();
		if(s.type == "text" || s.type == "TEXTAREA")
		{
			s.select();
		}
		alert(msg);
		return false;
}

//检查Email输入格式是否正确，不正确则弹出提示对话框
function isEmail(s,msg)
{
	if(s == undefined)
	{
		 return true;
	}
		if (s.value.length == 0)
		{
			return true;
		} 
		var reg = /\w+([-+.']\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*/gi; 
		if (reg.test(s.value))
		{
			return true;
		}
		else 
		{
			alert((msg?msg:'请输入标准格式E_mail'));
			s.focus();
			if(s.type == "text" || s.type == "TEXTAREA")
			{
				s.select();
			}
			return false;
		}
}

//检查输入是否为英文，不是则弹出警告对话框

function isEnglish(s,msg)
{
	if(s == undefined)
	{ 
		return true;
	}
		var reg = /^[a-z]*$/gi;
		if(reg.test(s.value))
		{
			return true;
		}
		else
		{
			alert((msg?msg:'此项目只能输入英文﹐请改正'));
			s.focus();
			if(s.type == "text" || s.type == "TEXTAREA")
			{
				s.select();
			}
			return false;
		}
}

//检查输入是否为中文，不是则弹出警告对话框

function isChinese(s,msg)
{
	if(s == undefined)
	{
		return true;
	}
		var reg = /[^\u4E00-\u9FA5]/g;
		if (reg.test(s.value))
		{
			s.focus();
			if(s.type == "text" || s.type == "TEXTAREA")
			{
				s.select();
			}
			alert((msg?msg:'此项目只能输入中文﹐请改正'));
			return false;
		}
		return true;
}

//检查输入是否为中文及全角符号，是则弹出警告对话框

function isNoChinese(s,msg)
{
	if(s == undefined)
	{
		return true;
	}
		var reg = /[\u4E00-\u9FA5]|[\uFE30-\uFFA0]/gi;
		if (reg.test(s.value))
		{
			if(s.type == "text" || s.type == "TEXTAREA")
				s.select();
			alert((msg?msg:'此项目不能输入中文及全角符号﹐请改正'));
			return false;
		}
		return true;
}

//检查输入是否为空，是则弹出警告对话框

function isNoZero(s,msg)
{
	if(s == undefined)
	{
		return true;
	}
		var str=s.value.Trim();
		if (str.length != 0)
		{
			return true;
		}
		else
		{
			alert((msg?msg:'此项目不能为空﹐请改正'));
			s.focus();
			if(s.type == "text" || s.type == "TEXTAREA")
				s.select();
			return false;
		}
}

//检查输入是否为空，是则弹出警告对话框

function isNoZeroWithoutTrim(s,msg)
{
	if(s == undefined)
	{
		return true;
	}
		var str=s.value;
		if (str.length != 0)
		{
			return true;
		}
		else
		{
			alert((msg?msg:'此项目不能为空﹐请改正'));
			s.focus();
			if(s.type == "text" || s.type == "TEXTAREA")
				s.select();
			return false;
		}
}

//检查输入是否为数字，不是则弹出警告对话框

function isNumeric(s,msg)
{
	if(s == undefined)
	{
		return true;
	}
		if (s.value*1 != s.value)
		{
			alert((msg?msg:'此项目为只能输入数字﹐请改正'));
			s.focus();
			if(s.type == "text" || s.type == "TEXTAREA")
				s.select();
			return false;
		}
		return true;
}

//检查输入是否为英文和数字，不是则弹出警告对话框
function isEN(s,msg)
{
	if(s == undefined)
	{
		return true;
	}
		var reg = /^[a-z0-9.]*$/gi;
		if(reg.test(s.value))
		{
			return true;
		}
		else
		{
			alert((msg?msg:'此项目只能输入英文和数字﹐请改正'));
			s.focus();
			if(s.type == "text" || s.type == "TEXTAREA")
			{
				s.select();
			}
			return false;
		}
}

//检查输入是否为整数，不是则弹出警告对话框

function isInteger(s,msg)
{
	if(s == undefined)
	{
		return true;
	}
		var reg = /^[0-9]*$/gi;
		if(reg.test(s.value))
		{
			return true;
		}
		else
		{
			alert((msg?msg:'此项目只能输入整数﹐请改正'));
			s.focus();
			if(s.type == "text" || s.type == "TEXTAREA")
			{
				s.select();
				s.value='';
			}
			return false;
		}
}
//检查输入是否为正整数，不是则弹出警告对话框
function isJusInteger(s,msg)
{
	if(s == undefined)
	{
		return true;
	}
		var reg = /^((-\d+)|(0+))$/gi;
		if(reg.test(s.value))
		{
			return true;
		}
		else
		{
			alert((msg?msg:'此项目只能输入整数﹐请改正'));
			s.focus();
			if(s.type == "text" || s.type == "TEXTAREA")
			{
			    s.value = "";
				s.select();
				
			}
			return false;
		}
}

//检查输入内容长度是否大于等于最小长度，不是则弹出提示对话框
function MinLength(s,v,msg)
{

	if(s == undefined)
	{
		return true;
	}
		if (s.value.Trim().length >= v)
		{
			return true;
		}
		else
		{
			alert((msg?msg:'输入内容长度需大于或等于' + v + '﹐请改正'));
			s.focus();
			if(s.type == "text" || s.type == "TEXTAREA")
			{
				s.select();
			}
			return false;
		}
}

//检查输入内容长度是否小于等于最大输入长度，不是则弹出警告对话框
function MaxLength(s,v,msg)
{
	if(s == undefined)
	{
		return true;
	}
		if (s.value.Trim().length <= v)
		{
			return true;
		}
		else
		{
			alert((msg?msg:'输入内容长度需小于或等于' + v + '﹐请改正'));
			s.focus();
			if(s.type == "text" || s.type == "TEXTAREA")
			{
				s.select();
			}
			return false;
		}
}

//检查输入内容转换成字节后长度是否大于等于最小输入长度，不是则弹出警告对话框
function MinLength$(s,v,msg)
{
	if(s == undefined)
	{
		return true;
	}
		if (s.value.Trim().replace(/[^\x00-\xff]/g,"**").length >= v)
		{
			return true;
		}
		else
		{
			alert((msg?msg:'输入内容长度需大于或等于' + v + '﹐请改正'));
			s.focus();
			if(s.type == "text" || s.type == "TEXTAREA")
			{
				s.select();
			}
			return false;
		}
}

//检查输入内容转换成字节后长度是否大于等于最小输入长度，不是则弹出警告对话框
function MinLengthWithoutTrim$(s,v,msg)
{
	if(s == undefined)
	{
		return true;
	}
		if (s.value.replace(/[^\x00-\xff]/g,"**").length >= v)
		{
			return true;
		}
		else
		{
			alert((msg?msg:'输入内容长度需大于或等于' + v + '﹐请改正'));
			s.focus();
			if(s.type == "text" || s.type == "TEXTAREA")
			{
				s.select();
			}
			return false;
		}
}

//检查输入内容转换成字节后长度是否小于等于最大输入长度，不是则弹出警告对话框
function MaxLength$(s,v,msg)
{
	if(s == undefined)
	{
		return true;
	}
		if (s.value.Trim().replace(/[^\x00-\xff]/g,"**").length<=v)
		{
			return true;
		}
		else
		{
			alert((msg?msg:'输入内容长度需小于或等于' + v + '﹐请改正'));
			s.focus();
		
			if(s.type == "text" || s.type == "TEXTAREA" )
			{
				s.select();
			}
			return false;
		}
}

//检查输入内容转换成字节后长度是否小于等于最大输入长度，不是则弹出警告对话框
function MaxLengthWithoutTrim$(s,v,msg)
{
	if(s == undefined)
	{
		return true;
	}
		if (s.value.replace(/[^\x00-\xff]/g,"**").length<=v)
		{
			return true;
		}
		else
		{
			alert((msg?msg:'输入内容长度需小于或等于' + v + '﹐请改正'));
			s.focus();
		
			if(s.type == "text" || s.type == "TEXTAREA" )
			{
				s.select();
			}
			return false;
		}
}

//检查输入值是否大于等于最小值，不是则弹出提示对话框
function MinValue(s,v,msg)
{
	if(s == undefined)
	{
		return true;
	}
		var str = s.value;
		if (str.length == 0)
		{
			return true;
		}
		if (s.value.Trim() >= v)
		{
			return true;
		}
		else
		{
			alert((msg?msg:'输入值需大于或等于' + v + '﹐请改正'));
			s.focus();
			if(s.type == "text" || s.type == "TEXTAREA")
			{
				s.select();
			}
			return false;
		}
}

//检查输入值是否小于等于最大值，不是则弹出警告对话框
function MaxValue(s,v,msg)
{
	if(s == undefined)
	{
		return true;
	}
		var str = s.value;
		if (str.length == 0)
		{
			return true;
		}
		if (s.value.Trim() <= v)
		{
			return true;
		}
		else
		{
			alert((msg?msg:'输入值需小于或等于' + v + '﹐请改正'));
			s.focus();
			if(s.type == "text" || s.type == "TEXTAREA")
			{
				s.select();
			}
			return false;
		}
}


//检查输入结束日期是否大于开始日期，不是则弹出警告对话框
function DateCheck(startdate,enddate,msg)
{
	stime = Date.parse(new Date(startdate.value.replace(/-/g,'/'))); 
    etime = Date.parse(new Date(enddate.value.replace(/-/g,'/'))); 
   
    if(etime-stime < 0)
    {
		alert((msg?msg:'结束日期应大于开始日期﹐请改正'));
		enddate.focus();
		if(enddate.type == "text" || enddate.type == "TEXTAREA")
		{
			enddate.select();
		}
		return false;
	}
	else
	{
		return true;
	}
}


//检查中止数值是否大于起始数值，不是则弹出警告对话框
function IntegerCheck(intstart,intend,msg)
{
	var ints = parseInt(intstart.value); 
    var inte = parseInt(intend.value); 
   
    if(inte < ints)
    {
		alert((msg?msg:'中止数值应大于起始数值﹐请改正'));
		intend.focus();
		if(intend.type == "text" || intend.type == "TEXTAREA")
		{
			intend.select();
		}
		return false;
		
	}
	else
	{
		return true;
	}
}


function chkForm(frmName)
{
	this.frmName=frmName;
	this.chkFunBody = "";
	this.ObjReturn = "";
	
	this.isType = function(eleName,chkFun,strMsg)
	{
		this.chkFunBody += chkFun+"(document.all."+eleName;
		if(strMsg)this.chkFunBody +=",\""+strMsg+"\"";
			this.chkFunBody +=") && ";
		var tmp = "return( "+ this.chkFunBody +" true)";
		this.ObjReturn = this.chkFunBody +" true";
	};

	this.isPass = function(eleName,chkFun,chkValue,strMsg)
	{
		this.chkFunBody += chkFun+"(document.all."+eleName + "," + chkValue;
		if(strMsg)this.chkFunBody +=",\""+strMsg+"\"";
			this.chkFunBody +=") && ";
		var tmp = "return( "+ this.chkFunBody +" true)";
		this.ObjReturn = this.chkFunBody +" true";
	};
	
	this.Check = function(eleName1,eleName2,chkFun,strMsg)
	{
		this.chkFunBody += chkFun+"("+this.frmName+"."+eleName1 + "," +this.frmName+"."+eleName2;
		if(strMsg)this.chkFunBody +=",\""+strMsg+"\"";
			this.chkFunBody +=") && ";
		var tmp = "return( "+ this.chkFunBody +" true)";
		this.ObjReturn = this.chkFunBody +" true";
	}
	
}


