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

/*
document.writeln("<div id='doing' style='Z-INDEX: 12000; LEFT: 0px; WIDTH: 100%; CURSOR: wait; POSITION: absolute; TOP: 0px; HEIGHT: 100%' style=''>");
document.writeln("<table width='100%' height='100%' id='Table1'>");
document.writeln("<tr align='center' valign='middle'>");
document.writeln("<td> <table id='Table2'>");
document.writeln("<tr align='center' valign='middle'>");
document.writeln("<td>正在加载页面... ...</td>");
document.writeln(" </tr></table> </td></tr></table></div>");
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
*/
