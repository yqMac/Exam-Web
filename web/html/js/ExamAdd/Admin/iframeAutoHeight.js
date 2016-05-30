function iframeAutoFit()
{
	var ex;
	try
	{
		if(window!=parent)
		{
		    var parentHeight = screen.availHeight - 480;
			var a = parent.document.getElementsByTagName("IFRAME");
			for(var i=0; i<a.length; i++) 
			{
				if(a[i].contentWindow==window)
				{
					a[i].style.height = "485px";
					var h1=0, h2=0;
					if(document.documentElement&&document.documentElement.scrollHeight)
					{
						h1=document.documentElement.scrollHeight;
					}
					if(document.body) h2=document.body.scrollHeight;

					var h=Math.max(h1, h2);
					if(h<parentHeight) h=parentHeight;
					if(document.all) {h += 12;}
					if(window.opera) {h += 4;}
					a[i].style.height = h +"px";
					if(parent.document.getElementById("divTree"))
					{
					    parent.document.getElementById("divTree").style.height=h+"px";
					}
				}
			}
		}
	}
	catch (ex){}
}
if(document.attachEvent)  window.attachEvent("onload",  iframeAutoFit);  
else  window.addEventListener('load',  iframeAutoFit,  false);

document.writeln("<style type='text/css'>.wait{margin-top:100px;height:180px; width:100%;padding-top:90px;text-align:center;background-image:url(../../App_Image/wait.gif);background-repeat:no-repeat;background-position:center 60px;}	</style>");
document.writeln("<div id='doing' style='Z-INDEX: 12000; LEFT: 0px; WIDTH: 100%; CURSOR: wait; POSITION: absolute; TOP: 0px; HEIGHT: 100%;text-align:center;'>");
document.writeln("<div class='wait'>正在加载页面... ...");
document.writeln("</div>");
document.writeln("</div>");
function ShowWaiting()
{
	document.getElementById('doing').style.display = '';


}
function CloseWaiting()
{
	document.getElementById('doing').style.display = 'none';
}
function MyOnload()
{
	document.getElementById('doing').style.display = 'none';
}
if(document.attachEvent)  window.attachEvent("onload",  MyOnload);  
else  window.addEventListener('load',  MyOnload,  false);


/*appended by xu wenhao*/
function DisabledAllInput()
{
    var inputList;
	inputList = document.getElementsByTagName("input");
	for(var i=0;i<inputList.length;i++)
	{
	    inputList[i].disabled="disabled";
	}
}

function EnabledAllInput()
{
    var inputList;
	inputList = document.getElementsByTagName("input");
	for(var i=0;i<inputList.length;i++)
	{
	    inputList[i].disabled="";
	}
}
