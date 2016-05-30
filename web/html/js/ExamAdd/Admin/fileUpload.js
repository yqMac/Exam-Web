

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