// JScript 文件
function DrawImage(ImgD,iwidth)
{
	var image=new Image();
	image.src=ImgD.src;
	if(image.width>0 && image.height>0)
	{
		if(image.width>iwidth)
		{  
			ImgD.width=iwidth;
			ImgD.height=(image.height*iwidth)/image.width;
		}
	}
	ImgD.style.display = "block";
} 


function ShowImage(id,pos)
{
	var ReturnString = "";
	
	for(var i=0;i<image_arr.length;i++)
	{	    
		if(image_arr[i][0]==id && image_arr[i][2]==pos)
		{
			var DisplayType = image_arr[i][1];
			var FileExt = image_arr[i][5].toLowerCase();
			
			if(ReturnString=="")
			{
				ReturnString = "<div class='titlename' style='height:auto!important;min-height:25px!important;height:25px;background-color:#ffffff;'>";
			}
			if(DisplayType==0)
			{
				if(FileExt=="gif" || FileExt=="jpg" || FileExt=="jpeg" || FileExt=="png" || FileExt=="bmp")
				{
					ReturnString += "<img border='0' src='" + image_arr[i][3] + "' onload='DrawImage(this,700)' style='display:block'><br><br>";
				}
				else if(FileExt=="swf")
				{
					ReturnString += "<object classid='clsid:D27CDB6E-AE6D-11cf-96B8-444553540000' codebase='http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=7,0,19,0' width='<%=FlashWidth%>' height='<%=FlashHeight%>'>";
					ReturnString += "<param name='movie' value='" + image_arr[i][3] + "'>";
					ReturnString += "<param name='quality' value='high'>";
					ReturnString += "<embed src='" + image_arr[i][3] + "' width='<%=FlashWidth%>' height='<%=FlashHeight%>' quality='high' pluginspage='http://www.macromedia.com/go/getflashplayer' type='application/x-shockwave-flash'></embed></object>";
					ReturnString += "<br><br>"
				}
				else if(FileExt=="rm" || FileExt=="ra" || FileExt=="ram" || FileExt=="rmvb")
				{
					ReturnString += "<OBJECT classid=clsid:CFCDAA03-8BE4-11cf-B84B-0020AFBBCCFA class=OBJECT id=RAOCX width='400' height='268'>";
					ReturnString += "<PARAM NAME=SRC VALUE='" + image_arr[i][3] + "'><PARAM NAME=CONSOLE VALUE='" + image_arr[i][3] + "'>";
					ReturnString += "<PARAM NAME=CONTROLS VALUE=imagewindow><PARAM NAME=AUTOSTART VALUE=false>";
					ReturnString += "</OBJECT>";
					ReturnString += "<OBJECT classid=CLSID:CFCDAA03-8BE4-11CF-B84B-0020AFBBCCFA height=32 id='video' width='400'>";
					ReturnString += "<PARAM NAME=SRC VALUE='" + image_arr[i][3] + "'><PARAM NAME=AUTOSTART VALUE=false>";
					ReturnString += "<PARAM NAME=CONTROLS VALUE=controlpanel><PARAM NAME=CONSOLE VALUE='" + image_arr[i][3] + "'>";
					ReturnString += "</OBJECT><br><br>"
				}
				else if(FileExt=="mp3" || FileExt=="wma" || FileExt=="wmv" || FileExt=="avi" || FileExt=="asf" || FileExt=="asx" || FileExt=="mid" || FileExt=="midi" || FileExt=="mpg" || FileExt=="mpeg")
				{
					ReturnString += "<object align=middle classid=CLSID:22d6f312-b0f6-11d0-94ab-0080c74c7e95 class=OBJECT id=MediaPlayer width='400' height='300'>";
					ReturnString += "<PARAM NAME=AUTOSTART VALUE=false>";
					ReturnString += "<param name=ShowStatusBar value=-1>";
					ReturnString += "<param name='EnableFullScreenControls' value='1'>";
					ReturnString += "<param name='EnableContextMenu' value='0'>";
					ReturnString += "<param name=Filename value='" + image_arr[i][3] + "'>";
					ReturnString += "<embed type=application/x-oleobject codebase=http://activex.microsoft.com/activex/controls/mplayer/en/nsmp2inf.cab#Version=5,1,52,701 flename=mp src='" + image_arr[i][3] + "' width='400' height='300'>";
					ReturnString += "</embed>";
					ReturnString += "</object><br><br>"
				}
				else if(FileExt=="txt")
				{
					//ReturnString += "<iframe src='" + image_arr[i][3] + "' onload='this.height=this.Document.body.scrollHeight' frameborder='0' width='100%'>"
					ReturnString += "<iframe src='" + image_arr[i][3] + "' frameborder='0' width='100%'>";
					ReturnString += "</iframe><br><br>"
				}
				else
				{
					DisplayType=1
				}
			}
			if(DisplayType==1)
			{
				ReturnString += "<div align='left'>附件：<a href='" + image_arr[i][3] + "' target='_blank' class='myLink'>" + image_arr[i][4] + "</a></div><br>"
			}
		}
	}
	if(ReturnString!="")
	{
		ReturnString += "</div>";
		document.write(ReturnString);
	}
}
