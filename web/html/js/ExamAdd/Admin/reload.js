function reloadOpener()
{
    try
    {
        if(window.opener)
        {
            
            window.opener.__doPostBack("btnSearch","");
        }
    }
    catch(ex)
    {
        
    }
}

