function iframeAutoFit()
{
	var ex;
	try
	{
		if(window!=parent)
		{
		    var parentHeight = screen.availHeight - 280;
			var a = parent.document.getElementsByTagName("IFRAME");
			for(var i=0; i<a.length; i++) 
			{
				if(a[i].contentWindow==window)
				{
					a[i].style.height = parentHeight;
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
