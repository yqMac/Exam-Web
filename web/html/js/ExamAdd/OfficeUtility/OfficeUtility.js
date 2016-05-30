// Excel/Word读取与写入


    //创建EXCEL类,var excelApp = new ExcelApp("filepath");
    var ErrMsg;

    function ExcelApp(filename)
    {

        //ExcelApp类信息

            this.Version = "ExcelApp 1.0.0";
        //类的主体

        //ExcelApp的属性

            this.File = filename;
        
        //ExcelApp的方法;
            this.Alert = function(str){alert(str)};// 改方法为测试
            this.ExportToPage = function(fileName,hiddenObjID,resultDiv)
                                {
                                    try{                                    
                                          var exApp   =   new   ActiveXObject("Excel.Application");
                                          //创建EXCEL对象
                                          var bk=exApp.Workbooks.Open(fileName); //创建EXCEL实例，读取指定路径的文件
                                            
                                          var printString = $GetTM(bk);
                                          document.getElementById(resultDiv).innerHTML = printString;          
                                          document.getElementById(hiddenObjID).value = $MakeXml(bk);
                                          //document.write($MakeXml(bk));
                                          bk.Close();
                                          exApp.Quit();  
                                          Cleanup();
                                          return true;
                                      }
                                      catch(e)
                                      {
                                        alert(e.message);
                                        window.status=e.message;
                                        return false;
                                      }      
                                };
            this.ImportTo = $MakeExcel;

    }
    //OFFICE工具调用类

    /*
    说 明：
    _fileInputID : input-file的ID，当_type为 noselectedinput时，该参数为文件路径的字符串，作为共用参数传递

    _objID : 用于存放处理后的HTML代码容器，通常为一个Input-Hidden
    _type : 处理类型，包括 
            excelimport     -   EXCEL文件导入
            noselectedinput -    不选择文件直接EXCEL文件导入
            excelexport     -    EXCEL文件导出
    resultDiv : 用于呈现结果的容器

    */
    
    function OfficeUtility(_fileInputID,_objID,_type,resultDiv)
    {
        this.Type = _type;
        this.FileInputObj = document.getElementById(_fileInputID);
        this.HiddenPostObj = document.getElementById(_objID);

        var type = this.Type.toLowerCase();

            if(type=="excelimport")
            {   
                if(this.FileInputObj.value==""){alert("请先选择一个EXCEL文档，再进行操作");return false}
                if(CheckInputFileExt(this.FileInputObj.value,"xls"))
                {
                    var excelExport = new ExcelApp(this.FileInputObj.value);
                    return excelExport.ExportToPage(this.FileInputObj.value,_objID,resultDiv);
                }
                else
                {
                    alert("请选择扩展名为 xls 的文件");
                    return false;
                } 
                 
            }
            //文件无选择直接导入
            //共用_fileInputID参数作为文件路径传递

            if(type=="noselectedinput")
            {
                    var excelExport = new ExcelApp(_fileInputID);
                    return excelExport.ExportToPage(_fileInputID,_objID,resultDiv);
            }
            if(type=="excelexport")
            {
                var excel = new ExcelApp(this.FileInputObj.value);
                excel.ImportTo();
            }
            if(type=="wordexport")
            {
            
            }
            if(type=="wordimport")
            {
            
            }
            return false;
    }
    
    //业务方法
    //生成题目列表
    function $GetTM(excelObj)
    {
            var i=4;
            var temp = "";
            temp = "<table id='dataXml' class='tab_table'>";
            temp = temp + "<tr>";
            temp = temp + "<th>行号</th>"; 
            temp = temp + "<th>" + GetValue(excelObj.Worksheets(1).Cells(3,2).value) + "</th>";   
            temp = temp + "<th>" + GetValue(excelObj.Worksheets(1).Cells(3,3).value) + "</th>";   
            temp = temp + "<th>" + GetValue(excelObj.Worksheets(1).Cells(3,4).value) + "</th>";   
            temp = temp + "<th>" + GetValue(excelObj.Worksheets(1).Cells(3,5).value) + "</th>";   
            temp = temp + "<th>" + GetValue(excelObj.Worksheets(1).Cells(3,6).value) + "</th>"; 
            temp = temp + "<th>" + GetValue(excelObj.Worksheets(1).Cells(3,7).value) + "</th>";
            temp = temp + "<th>" + GetValue(excelObj.Worksheets(1).Cells(3,8).value) + "</th>";   
            temp = temp + "<th>" + GetValue(excelObj.Worksheets(1).Cells(3,9).value) + "</th>"; 
            temp = temp + "<th>" + GetValue(excelObj.Worksheets(1).Cells(3,10).value) + "</th>"; 
            temp = temp + "<th>" + GetValue(excelObj.Worksheets(1).Cells(3,11).value) + "</th>"; 
            temp = temp + "</tr>";
            temp = temp + "<tbody>";
              while (excelObj.Worksheets(1).Cells(i,2).value!="undefined" && excelObj.Worksheets(1).Cells(i,2).value!=""  && excelObj.Worksheets(1).Cells(i,2).value!=null)
              {    
                if(i % 2==0)
                {
                    temp = temp + "<tr class='tr_al'>";        
                }
                else
                {
                    temp = temp + "<tr class='tab_tr1'>";  
                }
                temp = temp + "<td>" + i + "</td>";
                temp = temp + "<td>" + GetValue(excelObj.Worksheets(1).Cells(i,2).value) +"</td>";   
                temp = temp + "<td>" + GetValue(excelObj.Worksheets(1).Cells(i,3).value) +"</td>";   
                temp = temp + "<td>" + GetValue(excelObj.Worksheets(1).Cells(i,4).value) +"</td>";   
                temp = temp + "<td>" + GetValue(excelObj.Worksheets(1).Cells(i,5).value) +"</td>";   
                temp = temp + "<td>" + GetValue(excelObj.Worksheets(1).Cells(i,6).value) +"</td>";   
                temp = temp + "<td>" + GetValue(excelObj.Worksheets(1).Cells(i,7).value) +"</td>";   
                temp = temp + "<td>" + GetValue(excelObj.Worksheets(1).Cells(i,8).value) +"</td>";     
                temp = temp + "<td>" + GetValue(excelObj.Worksheets(1).Cells(i,9).value) +"</td>"; 
                temp = temp + "<td>" + GetValue(excelObj.Worksheets(1).Cells(i,10).value) +"</td>"; 
                temp = temp + "<td>" + GetValue(excelObj.Worksheets(1).Cells(i,11).value) +"</td>";                                                   
                temp = temp + "</tr>";
                i=i+1;
              }    
              temp = temp + "</tbody>";     
              temp = temp + "</table>";

              return temp;        
    }  
    
    function $MakeXml(excelObj)
    {
            var i=4;
            var xmlstr;
            xmlstr = "<?xml version='1.0' encoding='utf-8' ?>\r\n";
            xmlstr = xmlstr + "<excelTmList>\r\n";
              while (excelObj.Worksheets(1).Cells(i,2).value!="undefined" && excelObj.Worksheets(1).Cells(i,2).value!=""  && excelObj.Worksheets(1).Cells(i,2).value!=null)
              {    
                xmlstr = xmlstr + "\t<excelTm>\r\n";
                xmlstr = xmlstr + "\t\t<rowIndex>" + i + "</rowIndex>\r\n";
                xmlstr = xmlstr + "\t\t<tm_zsd>" + GetValue(excelObj.Worksheets(1).Cells(i,2).value) +"</tm_zsd>\r\n";   
                xmlstr = xmlstr + "\t\t<tm_tx>" + GetValue(excelObj.Worksheets(1).Cells(i,3).value) +"</tm_tx>\r\n";   
                xmlstr = xmlstr + "\t\t<tm_nd>" + GetValue(excelObj.Worksheets(1).Cells(i,4).value) +"</tm_nd>\r\n";   
                xmlstr = xmlstr + "\t\t<tm_fs>" + GetValue(excelObj.Worksheets(1).Cells(i,5).value) +"</tm_fs>\r\n";   
                xmlstr = xmlstr + "\t\t<tm_nr>" + GetValue(excelObj.Worksheets(1).Cells(i,6).value) +"</tm_nr>\r\n";   
                xmlstr = xmlstr + "\t\t<tm_da>" + GetValue(excelObj.Worksheets(1).Cells(i,7).value) +"</tm_da>\r\n";   
                xmlstr = xmlstr + "\t\t<tm_bzda>" + GetValue(excelObj.Worksheets(1).Cells(i,8).value) +"</tm_bzda>\r\n";     
                xmlstr = xmlstr + "\t\t<tm_zy>" + GetValue(excelObj.Worksheets(1).Cells(i,9).value) +"</tm_zy>\r\n"; 
                xmlstr = xmlstr + "\t\t<ParentId>" + GetValue(excelObj.Worksheets(1).Cells(i,10).value) +"</ParentId>\r\n"; 
                xmlstr = xmlstr + "\t\t<memo>" + GetValue(excelObj.Worksheets(1).Cells(i,11).value) +"</memo>\r\n";              
                xmlstr = xmlstr + "\t</excelTm>\r\n";
                i=i+1;
              }    
              xmlstr = xmlstr + "</excelTmList>";     
              return xmlstr;         
    }  
    function $MakeExcel(){
        var i,j;
        try {
          var xls    = new ActiveXObject ( "Excel.Application" );
         }
        catch(e) {
             alert( "要打印该表，您必须安装Excel电子表格软件，同时浏览器须使用“ActiveX 控件”，您的浏览器须允许执行控件。 请点击【帮助】了解浏览器设置方法！");
                  return "";
         }

        xls.visible =true;  //设置excel为可见


        var xlBook = xls.Workbooks.Add;
        var xlsheet = xlBook.Worksheets(1);
        <!--合并-->
         xlsheet.Range(xlsheet.Cells(1,2),xlsheet.Cells(1,11)).mergecells=true;
         xlsheet.Range(xlsheet.Cells(1,2),xlsheet.Cells(1,11)).value="考试系统试题导出文件";
         //说明
         xlsheet.Range(xlsheet.Cells(2,2),xlsheet.Cells(2,11)).mergecells=true;
         xlsheet.Range(xlsheet.Cells(2,2),xlsheet.Cells(2,11)).value="说明：1、题型必须与您系统中的保持一致，否则试题不能导入系统中\r\n"
                                                                    + "2、难度分为1-5等，其中1表示最易，5表示最难\r\n"
                                                                    + "3、同一知识点、题型和难度的题目的分数应该相同，推荐问答题5分，其他题2分\r\n"
                                                                    + "4、填空题用三个英文下滑线“___”表示填空处，多个答案用逗号隔开\r\n"
                                                                    + "5、可选项只对选择题有效，其他题型可选项为空。多个选项之间用“;”隔开\r\n"
                                                                    + "6、答案中单选题只能是A-Z的一个字母;多选题可以是多个字母，中间没有分隔符;判断题只能是1或0，1表示对，0表示错误。\r\n"
                                                                    + "7、所有问题题目和答案加起来不能超过6000个英文字符或者1000个汉字"; 
        //xlsheet.Range(xlsheet.Cells(2,1),xlsheet.Cells(2,6)).Interior.ColorIndex=5;//设置底色为蓝色 
                    //   xlsheet.Range(xlsheet.Cells(1,1),xlsheet.Cells(1,6)).Font.ColorIndex=4;//设置字体色         
       // xlsheet.Rows(1). Interior .ColorIndex = 5 ;//设置底色为蓝色  设置背景色 Rows(1).Font.ColorIndex=4  

        <!--设置行高-->
            xlsheet.Rows(1).RowHeight = 25;
            xlsheet.Rows(2).RowHeight = 100;

        <!--设置字体 ws.Range(ws.Cells(i0+1,j0), ws.Cells(i0+1,j1)).Font.Size = 13 -->
            xlsheet.Rows(1).Font.Size=14;
            xlsheet.Rows(3).Font.Size=13;
        <!--设置字体 设置选定区的字体  xlsheet.Range(xlsheet.Cells(i0,j0), ws.Cells(i0,j0)).Font.Name = "黑体" -->
            xlsheet.Rows(1).Font.Name="黑体";
            xlsheet.Rows(3).Font.Name="黑体";
        <!--设置列宽 xlsheet.Columns(2)=14;-->
            xlsheet.Columns("B:K").ColumnWidth =18;
         <!--设置显示字符而不是数字-->
            xlsheet.Columns(2).NumberFormatLocal="@";
            xlsheet.Columns(10).NumberFormatLocal="@";


         //设置单元格内容自动换行 range.WrapText  =  true  ;
         //设置单元格内容水平对齐方式 range.HorizontalAlignment  =  Excel.XlHAlign.xlHAlignCenter;//设置单元格内容竖直堆砌方式

          //range.VerticalAlignment=Excel.XlVAlign.xlVAlignCenter
        //range.WrapText  =  true;  xlsheet.Rows(3).WrapText=true  自动换行
       

        //alert("aa");
         var oTable=createTable();
         //设置标题栏

         //alert(oTable.rows(0).cells.length);
         for(i=1;i<=oTable.rows(0).cells.length;i++)
         {
            xlsheet.Cells(3,i+1).Value=oTable.rows(0).cells(i-1).innerHTML;
         }
         var rowNum=oTable.rows.length;
         for(i=1;i<rowNum;i++)
         {
             for (j=1;j<=oTable.rows(0).cells.length;j++)
             {
                //html table类容写到excel
                xlsheet.Cells(i+3,j+1).Value=oTable.rows(i).cells(j-1).innerHTML;
             }
        }
        //<!--   xlsheet.Range(xls.Cells(i+4,2),xls.Cells(rowNum,4)).Merge; -->
        // xlsheet.Range(xlsheet.Cells(i, 4), xlsheet.Cells(i-1, 6)).BorderAround , 4
         // for(mn=1,mn<=6;mn++) .     xlsheet.Range(xlsheet.Cells(1, mn), xlsheet.Cells(i1, j)).Columns.AutoFit;
          xlsheet.Columns.AutoFit;
          xlsheet.Range(xlsheet.Cells(1,1),xlsheet.Cells(rowNum+3,11)).HorizontalAlignment =-4108;//居中
          xlsheet.Range(xlsheet.Cells(1,1),xlsheet.Cells(1,11)).VerticalAlignment =-4108;
          xlsheet.Range( xlsheet.Cells(2,1),xlsheet.Cells(rowNum+3,11)).Font.Size=10;
          xlsheet.Range( xlsheet.Cells(2,1),xlsheet.Cells(rowNum+3,11)).Borders(3).Weight = 2; //设置左边距

          xlsheet.Range( xlsheet.Cells(2,1),xlsheet.Cells(rowNum+3,11)).Borders(4).Weight = 2;//设置右边距

          xlsheet.Range( xlsheet.Cells(2,1),xlsheet.Cells(rowNum+3,11)).Borders(1).Weight = 2;//设置顶边距

          xlsheet.Range( xlsheet.Cells(2,1),xlsheet.Cells(rowNum+3,11)).Borders(2).Weight = 2;//设置底边距

          xlsheet.Range( xlsheet.Cells(2,2),xlsheet.Cells(11,2)).HorizontalAlignment =-4131;    //说明文字居左
           xls.UserControl = true;  //很重要,不能省略,不然会出问题 意思是excel交由用户控制
           xls=null;
           xlBook=null;
           xlsheet=null;
    }    
    //公共方法;
    
    function GetValue(svalue)
    {
        if (svalue == null || svalue=="")
        {
            svalue="";
        }
        return svalue;
    }
    //判断选择的文件是否符合要求

    function CheckInputFileExt(value,allowExt)
    {
        var   s   =   value;
        if(!(s.substring(s.lastIndexOf(".")+1).toLowerCase() == allowExt || s.value==""))   
        {                   
            return false;
        }   
        return true;
    } 
    //关闭进程，垃圾回收

   function  Cleanup()  {  
       CollectGarbage(); 
   }        



    
