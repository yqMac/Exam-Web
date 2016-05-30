function AllSelect()
{
	var a = document.getElementsByName("checkbox2");
	for(i=0;i<a.length;i++)
	{
		if(a[i].checked==true)
		{
			a[i].checked=false;
		}
		else
		{
			a[i].checked=true;
		}
	}
}