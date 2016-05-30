var position;
var XSLSrc = "../Script/tree.xsl";
var TreeContainerID = "divTree";
var TreePositionID = "divPostion";

function CollapseNode(index,flag)
{
	if(document.getElementById("nav_" + index.toString() + "_0"))
	{
		if(flag==undefined)
		{
			flag = (document.getElementById("nav_" + index.toString() + "_0").style.display=="none"	)?true:false;
		}
		if(flag)
		{
			document.getElementById("nav_" + index.toString() + "_1").style.display="none";
			document.getElementById("nav_" + index.toString() + "_0").style.display="block";
			document.getElementById("sub_" + index.toString()).style.display="none";
		}
		else
		{
			document.getElementById("nav_" + index.toString() + "_0").style.display="none";
			document.getElementById("nav_" + index.toString() + "_1").style.display="block";
			if(position!=undefined)
			{
				if(position!=index) CollapseNode(position,true);
			}
			document.getElementById("sub_" + index.toString()).style.display="block";
			position = index;
		}
	}
}

function ClickItem(i,j)
{
	if(i==undefined || j==undefined) return;
	if(isNaN(i) || isNaN(j)) return;
	i--;
	CollapseNode(i,false);
	if(document.getElementById("link_" + i.toString() + "_" + j.toString()))
	{
		document.getElementById("link_" + i.toString() + "_" + j.toString()).click();	
	}
}

function LoadPage(text,url)
{
	if(url!="" && url!="#")
	{
		if(text != "") TreePositionID.innerHTML = text;
		parent.mainFrame.location.href = url;
	}
}

function Navigate(i,data)
{
	if(isNaN(i)) return;
	if(document.getElementById("data_" + (i-1).toString() + "_" + data.toString()))
	{
		var j = document.getElementById("data_" + (i-1).toString() + "_" + data.toString()).value;
		ClickItem(i,j);
	}
}

function LoadTree(Src,Index,SubIndex)
{

	var objXSLDoc = new ActiveXObject("Microsoft.XMLDOM");
	objXSLDoc.async = false;
	objXSLDoc.load(XSLSrc);
	var objXMLDoc = new ActiveXObject("Microsoft.XMLDOM");
	objXMLDoc.async = false;
	objXMLDoc.load(Src);
	document.getElementById(TreeContainerID).innerHTML = objXMLDoc.transformNode(objXSLDoc);
	if(Index!=undefined)
	{
		if(SubIndex==undefined) SubIndex=1;
		try
		{
			ClickItem(Index,SubIndex);
		}
		catch(e)
		{
			ClickItem(Index,0);
		}
	}
}

function LoadTreeXml(Src,Index,SubIndex)
{

	var objXSLDoc = new ActiveXObject("Microsoft.XMLDOM");
	objXSLDoc.async = false;
	objXSLDoc.load(XSLSrc);
	var objXMLDoc = new ActiveXObject("Microsoft.XMLDOM");
	objXMLDoc.async = false;
	objXMLDoc.loadXML(Src);
	document.getElementById(TreeContainerID).innerHTML = objXMLDoc.transformNode(objXSLDoc);
	if(Index!=undefined)
	{
		if(SubIndex==undefined) SubIndex=1;
		try
		{
			ClickItem(Index,SubIndex);
		}
		catch(e)
		{
			ClickItem(Index,0);
		}
	}
}

function LoadTreeContent(sContent,Index,SubIndex)
{
	var objXSLDoc = new ActiveXObject("Microsoft.XMLDOM");
	objXSLDoc.async = false;
	objXSLDoc.load(XSLSrc);
	var objXMLDoc = new ActiveXObject("Microsoft.XMLDOM");
	objXMLDoc.async = false;
	objXMLDoc.loadxml(sContent);
	document.getElementById(TreeContainerID).innerHTML = objXMLDoc.transformNode(objXSLDoc);
	if(Index!=undefined)
	{
		if(SubIndex==undefined) SubIndex=1;
		try
		{
			ClickItem(Index,SubIndex);
		}
		catch(e)
		{
			ClickItem(Index,0);
		}
	}
}