document.writeln("<style type='text/css'>.wait{margin-top:10px;height:100px; width:100%;padding-top:90px;text-align:center;background-image:url(../../App_Image/wait.gif);background-repeat:no-repeat;background-position:center 60px;}	</style>");
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