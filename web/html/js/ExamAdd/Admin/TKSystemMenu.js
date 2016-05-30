// JScript 文件
    document.body.insertAdjacentHTML("afterBegin","<input name='treeTk_tkj_id' id='treeTk_tkj_id' type='hidden' value=''>");
    document.body.insertAdjacentHTML("afterBegin","<input name='treeTk_lx_id' id='treeTk_lx_id' type='hidden' value=''>");
    document.body.insertAdjacentHTML("afterBegin","<input name='TypeName' id='TypeName' type='hidden' value=''>");
    document.body.insertAdjacentHTML("afterBegin","<input name='treeitemtext' id='teeeitemtext' type='hidden' value=''>");
    document.body.insertAdjacentHTML("afterBegin","<input name='treeNodeid' id='treeNodeid' type='hidden' value=''>");

    function contextMenu(treeviewid)
    {
        this.items = [];
        this.addItem = function (item)
        {
	        this.items[this.items.length] = item;
        };

        this.show = function (oDoc)
        {
	        var strShow = '';
	        var i;

            // 加上word-break: keep-all; 防止菜单项换行

	        strShow = "<div id='rightmenu' style='word-break: keep-all;BACKGROUND-COLOR: #ffffff; BORDER: #000000 1px solid; LEFT: 0px; POSITION: absolute; TOP: 0px; VISIBILITY: hidden; Z-INDEX: 10'>";
	        strShow += "<table border='0' height='";
	        strShow += this.items.length * 20;
	        strShow += "' cellpadding='0' cellspacing='0'>";
	        strShow += "<tr height='3'><td bgcolor='#d0d0ce' width='2'></td><td>";
	        strShow += "<table border='0' width='100%' height='100%' cellpadding=0 cellspacing=0 bgcolor='#ffffff'>";
	        strShow += "<tr><td bgcolor='#d0d0ce' width='23'></td><td><img src=' ' height='1' border='0'></td></tr></table>";
	        strShow += "</td><td width='2'></td></tr>";
	        strShow += "<tr><td bgcolor='#d0d0ce'></td><td>";
	        strShow += "<table border='0' width='100%' height='100%' cellpadding=3 cellspacing=0 bgcolor='#ffffff'>";
    		
	        oDoc.write(strShow);

	        for(i=0; i<this.items.length; i++)
	        {
		        this.items[i].show(oDoc);
	        }
    		
	        strShow = "</table></td><td></td></tr>";
	        strShow += "<tr height='3'><td bgcolor='#d0d0ce'></td><td>";
	        strShow += "<table border='0' width='100%' height='100%' cellpadding=0 cellspacing=0 bgcolor='#ffffff'>";
	        strShow += "<tr><td bgcolor='#d0d0ce' width='23'></td><td><img src=' ' height='1' border='0'></td></tr></table>";
	        strShow += "</td><td></td></tr>";
	        strShow += "</table></div>\n";
    		
	        oDoc.write(strShow);
        };
        this.showRoot = function(oDoc)
        {
             var strShow = '';
	        var i;

            // 加上word-break: keep-all; 防止菜单项换行

	        strShow = "<div id='rightmenu1' style='word-break: keep-all;BACKGROUND-COLOR: #ffffff; BORDER: #000000 1px solid; LEFT: 0px; POSITION: absolute; TOP: 0px; VISIBILITY: hidden; Z-INDEX: 10'>";
	        strShow += "<table border='0' height='";
	        strShow += this.items.length * 20;
	        strShow += "' cellpadding='0' cellspacing='0'>";
	        strShow += "<tr height='3'><td bgcolor='#d0d0ce' width='2'></td><td>";
	        strShow += "<table border='0' width='100%' height='100%' cellpadding=0 cellspacing=0 bgcolor='#ffffff'>";
	        strShow += "<tr><td bgcolor='#d0d0ce' width='23'></td><td><img src=' ' height='1' border='0'></td></tr></table>";
	        strShow += "</td><td width='2'></td></tr>";
	        strShow += "<tr><td bgcolor='#d0d0ce'></td><td>";
	        strShow += "<table border='0' width='100%' height='100%' cellpadding=3 cellspacing=0 bgcolor='#ffffff'>";
    		
	        oDoc.write(strShow);

	        for(i=0; i<this.items.length; i++)
	        {
		        this.items[i].show(oDoc);
	        }
    		
	        strShow = "</table></td><td></td></tr>";
	        strShow += "<tr height='3'><td bgcolor='#d0d0ce'></td><td>";
	        strShow += "<table border='0' width='100%' height='100%' cellpadding=0 cellspacing=0 bgcolor='#ffffff'>";
	        strShow += "<tr><td bgcolor='#d0d0ce' width='23'></td><td><img src=' ' height='1' border='0'></td></tr></table>";
	        strShow += "</td><td></td></tr>";
	        strShow += "</table></div>\n";
    		
	        oDoc.write(strShow);
        };
        this.show1 = function(oDoc)
        {
             var strShow = '';
	        var i;

            // 加上word-break: keep-all; 防止菜单项换行

	        strShow = "<div id='rightmenu2' style='word-break: keep-all;BACKGROUND-COLOR: #ffffff; BORDER: #000000 1px solid; LEFT: 0px; POSITION: absolute; TOP: 0px; VISIBILITY: hidden; Z-INDEX: 10'>";
	        strShow += "<table border='0' height='";
	        strShow += this.items.length * 20;
	        strShow += "' cellpadding='0' cellspacing='0'>";
	        strShow += "<tr height='3'><td bgcolor='#d0d0ce' width='2'></td><td>";
	        strShow += "<table border='0' width='100%' height='100%' cellpadding=0 cellspacing=0 bgcolor='#ffffff'>";
	        strShow += "<tr><td bgcolor='#d0d0ce' width='23'></td><td><img src=' ' height='1' border='0'></td></tr></table>";
	        strShow += "</td><td width='2'></td></tr>";
	        strShow += "<tr><td bgcolor='#d0d0ce'></td><td>";
	        strShow += "<table border='0' width='100%' height='100%' cellpadding=3 cellspacing=0 bgcolor='#ffffff'>";
    		
	        oDoc.write(strShow);

	        for(i=0; i<this.items.length; i++)
	        {
		        this.items[i].show(oDoc);
	        }
    		
	        strShow = "</table></td><td></td></tr>";
	        strShow += "<tr height='3'><td bgcolor='#d0d0ce'></td><td>";
	        strShow += "<table border='0' width='100%' height='100%' cellpadding=0 cellspacing=0 bgcolor='#ffffff'>";
	        strShow += "<tr><td bgcolor='#d0d0ce' width='23'></td><td><img src=' ' height='1' border='0'></td></tr></table>";
	        strShow += "</td><td></td></tr>";
	        strShow += "</table></div>\n";
    		
	        oDoc.write(strShow);
        }
    }
    
    function contextItem(type,url,target,text,confirmtext)
    {
        this.type = type ? type : 'Link';
        this.url = url ? url : '';
        this.target = target ? target : '';
        this.text = text ? text : '';
        this.confirmtext = confirmtext ? confirmtext : '';
        
        this.show = function (oDoc)
        {
            var strShow = '';
            if (this.type == 'Separator')
	        {
		        strShow += "<tr height='2'><td class='ltdexit'><font size='1'>&nbsp;</font></td>";
		        strShow += "<td class='mtdexit' colspan='2'><hr color='#000000' size='1'></td></tr>";
	        }
	        else if(this.type == 'Insert' || this.type == 'Modify' || this.type == 'Delete')
	        {
	            strShow += "<tr ";
		        strShow += "onmouseover=\"changeStyle(this, 'on');\" ";
		        strShow += "onmouseout=\"changeStyle(this, 'out');\" ";
		        
		        if(this.type == 'Insert' || this.type == 'Modify')
		        {
	                strShow += "onclick=\"";
	                strShow += "FixSizeWin('" + this.url;
	                if(this.url.indexOf("?")>0)
	                {
	                    strShow += "&"
	                }
	                else
	                {
	                    strShow += "?"
	                }
	                strShow += "Tk_tkj_id=' + document.getElementById('treeTk_tkj_id').value + ";
	               // strShow += "' + document.getElementById('treeTk_tkj_id').value + ";
	                strShow += "'&Tk_lx_id=' + document.getElementById('treeTk_lx_id').value + ";
	                strShow += "'&type=' + document.getElementById('TypeName').value,''," + this.confirmtext + ")\"";       
		        }
		        else if(this.type == 'Delete')
		        {
	                strShow += "onclick=\"";
	                if(this.confirmtext != '')
	                {
	                    strShow += "if(window.confirm('" + this.confirmtext + "')) "
	                }
	                strShow += "document.getElementById('" + this.target + "').src='" + this.url;
	                if(this.url.indexOf("?")>0)
	                {
	                    strShow += "&"
	                }
	                else
	                {
	                    strShow += "?"
	                }       
	                strShow += "Tk_tkj_id=' + document.getElementById('treeTk_tkj_id').value + ";
	                strShow += "'&eid=' + document.getElementById('treeNodeid').value + ";
	                strShow += "'&Tk_lx_id=' + document.getElementById('treeTk_lx_id').value + ";
	                strShow += "'&type=' + document.getElementById('TypeName').value;\"";
		        }
		        
		        strShow += ">";
		        strShow += "<td class='ltdexit' width='16'>&nbsp;";
		        
		        strShow += "</td><td class='mtdexit'>";
		        strShow += this.text;
		        strShow += "</td><td class='rtdexit' width='5'>&nbsp;</td></tr>";
	        }
	        
	        oDoc.write(strShow);
        }
    }

    function changeStyle(obj, cmd)
    { 
        if(obj)
        {
	        try 
	        {
		        var imgObj = obj.children(0).children(0);

		        if(cmd == 'on') 
		        {
			        obj.children(0).className = 'ltdfocus';
			        obj.children(1).className = 'mtdfocus';
			        obj.children(2).className = 'rtdfocus';
    				
			        if(imgObj)
			        {
				        if(imgObj.tagName.toUpperCase() == 'IMG')
				        {
					        imgObj.style.left = '-1px';
					        imgObj.style.top = '-1px';
				        }
			        }
		        }
		        else if(cmd == 'out') 
		        {
			        obj.children(0).className = 'ltdexit';
			        obj.children(1).className = 'mtdexit';
			        obj.children(2).className = 'rtdexit';

			        if(imgObj)
			        {
				        if(imgObj.tagName.toUpperCase() == 'IMG')
				        {
					        imgObj.style.left = '0px';
					        imgObj.style.top = '0px';
				        }
			        }
		        }
	        }
	        catch (e) {}
        }
    }

    function showMenu(treeviewid)
    {
        hideMenu();
        if(treeviewid==null)
        {
            treeviewid="TreeView1";
        }
        window.event.returnValue = false;
        document.getElementById('treeitemtext').value;
        document.getElementById('treeTk_tkj_id').value="";
        document.getElementById('treeTk_lx_id').value="";
        document.getElementById('TypeName').value="";
        document.getElementById('treeNodeid').value = "";
        
        var ele = window.event.srcElement;
        
      
      if ( ele.id == "")
      return false;
        
        if(ele)
        {
            if(ele.href)
           { 
                if(ele.id.indexOf(treeviewid + "t")==0)
                {
                    document.getElementById('treeitemtext').value = ele.innerText;
                    /*
                    if(ele.href.indexOf("#")>0)
                    {
                        document.getElementById('treeTk_tkj_id').value = ele.href.substr(ele.href.indexOf("#")+1,1);
                    }
                    if(ele.href.indexOf("@")>0)
                    {
                        document.getElementById('treeTk_lx_id').value = ele.href.substr(ele.href.indexOf("@")+1,1);
                    }
                    if(ele.href.indexOf("$")>0)
                    {
                        document.getElementById('TypeName').value = ele.href.substr(ele.href.indexOf("$")+1,2);
                    }
                    */
                    var type;
                    type = ele.href.substring(ele.href.indexOf("$") + 1);
                  
                    if ( ele.href.indexOf("@") > 0 )
                    {
                        var tkjid = ele.href.substring(ele.href.indexOf("?") + 1);
                        tkjid = tkjid.substring(0,tkjid.indexOf("&"));
                        
                        tkjid = tkjid.substring(tkjid.indexOf("=") + 1);
                      
                        var tkjlxid = ele.href.substring(ele.href.indexOf("@")+1);
                        if ( tkjlxid.indexOf("&") > 0)
                        {
                            tkjlxid = tkjlxid.substring(0,tkjlxid.indexOf("&"));
                        }
                        
                       
                        
                        document.getElementById('treeTk_tkj_id').value  = tkjid;
                        document.getElementById('treeTk_lx_id').value  = tkjlxid;
                        //alert("tkjid=" + document.getElementById('treeTk_tkj_id').value);
                        //alert("tklxid=" + document.getElementById('treeTk_lx_id').value);
                        
                    }
                    else
                    {
                        var tkjid = ele.href.substring(ele.href.indexOf("#")+1);
                        if ( tkjid.indexOf("&") > 0)
                        {
                            tkjid = tkjid.substring(0,tkjid.indexOf("&"));
                        }
                        document.getElementById('treeTk_tkj_id').value  = tkjid;
                        document.getElementById('treeTk_lx_id').value  = tkjid
                    }
                     document.getElementById('TypeName').value = type;
                document.getElementById('treeNodeid').value = ele.id;
                }
               
            }
        }
       
       // if(document.getElementById('treeTk_tkj_id').value=="") return false;
      
        var x, y, w, h, ox, oy;

        x = event.clientX;
        y = event.clientY;

      // var obj = document.getElementById('rightmenu');
  
             var obj;
             if ( ele)
             {
               if(ele.href)
             {     
                 if ( ele.href.indexOf("@") > 0 )
                 {
                    obj = document.getElementById('rightmenu2');
                 }
                
                 if (ele.href.indexOf("@")<0 && ele.href.indexOf("$"))
                 {
                    obj =document.getElementById('rightmenu');
                 }
                
                if (ele.href.indexOf("&") < 0)
                {
                   obj = document.getElementById('rightmenu1');
                }
               }
            }
        if (obj == null)
	        return true;

        ox = document.body.clientWidth;
        oy = document.body.clientHeight;
        

        if(x > ox || y > oy)
	        return false;

        w = obj.offsetWidth;
        h = obj.offsetHeight;
        
       // alert("x=" + x + " ; y=" + y);

        if((x + w - 25) > ox)
	        x = x - w;

//        if((y + h) > oy)
//	        y = y - h;

        
        //alert("w=" + w + " ; h=" + h);
        //alert("ox=" + ox + " ;oy=" + oy);
        // obj.style.posLeft = x + document.body.scrollLeft;
        // obj.style.posTop = y + document.body.scrollTop;
        // xhtml不支持上面的了

        // 就是说如果你的页头声明了页是xhtml的话就不能用上面那句了，vs2005创建的aspx会默认加上xhtml声明
        // 此时应该用如下的方法
        obj.style.posLeft = x + document.documentElement.scrollLeft;
        obj.style.posTop = y + document.documentElement.scrollTop;

        obj.style.visibility = 'visible';

        return false;
    }

    function hideMenu()
    {
        if(event.button == 0)
        {
	        var obj = document.getElementById('rightmenu');
	        if (obj == null)
		        return true;

	        obj.style.visibility = 'hidden';
	        obj.style.posLeft = 0;
	        obj.style.posTop = 0;
	        
	        //独立Root菜单
	        
	        obj =  document.getElementById('rightmenu1');
	        if (obj == null)
		        return true;

	        obj.style.visibility = 'hidden';
	        obj.style.posLeft = 0;
	        obj.style.posTop = 0;
	        
	        
	          //独立Root菜单
	        
	        obj =  document.getElementById('rightmenu2');
	        if (obj == null)
		        return true;

	        obj.style.visibility = 'hidden';
	        obj.style.posLeft = 0;
	        obj.style.posTop = 0;
        }
    }

    function writeStyle()
    {
        var strStyle = '';

        strStyle += "<STYLE type='text/css'>";
        strStyle += "TABLE {Font-FAMILY: 'Tahoma','Verdana','宋体'; FONT-SIZE: 9pt}";
        strStyle += ".mtdfocus {BACKGROUND-COLOR: #ccccff; BORDER-BOTTOM: #000000 1px solid; BORDER-TOP: #000000 1px solid; CURSOR: hand}";
        strStyle += ".mtdexit {BACKGROUND-COLOR: #ffffff; BORDER-BOTTOM: #ffffff 1px solid; BORDER-TOP: #ffffff 1px solid}";
        strStyle += ".ltdfocus {BACKGROUND-COLOR: #ccccff; BORDER-BOTTOM: #000000 1px solid; BORDER-TOP: #000000 1px solid; BORDER-LEFT: #000000 1px solid; CURSOR: hand}";
        strStyle += ".ltdexit {BACKGROUND-COLOR: #d0d0ce; BORDER-BOTTOM: #d0d0ce 1px solid; BORDER-TOP: #d0d0ce 1px solid; BORDER-LEFT: #d0d0ce 1px solid}";
        strStyle += ".rtdfocus {BACKGROUND-COLOR: #ccccff; BORDER-BOTTOM: #000000 1px solid; BORDER-TOP: #000000 1px solid; BORDER-RIGHT: #000000 1px solid; CURSOR: hand}";
        strStyle += ".rtdexit {BACKGROUND-COLOR: #ffffff; BORDER-BOTTOM: #ffffff 1px solid; BORDER-TOP: #ffffff 1px solid; BORDER-RIGHT: #ffffff 1px solid}";
        strStyle += "</STYLE>";

        document.write(strStyle);
    }

    function toggleMenu(isEnable)
    {
        if(isEnable)
	        document.oncontextmenu = showMenu;
        else
	        document.oncontextmenu = new function() {return true;};
    }

    writeStyle();


