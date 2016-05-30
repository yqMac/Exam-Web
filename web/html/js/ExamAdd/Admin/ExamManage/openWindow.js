function changeTab(obj)
{	
	var tab2 = document.getElementById("tab2");
	var tab1 = document.getElementById("tab1");
	var block2 = document.getElementById("block2");
	var block1 = document.getElementById("block1");
	var btn_pro = document.getElementById("btn_pro");
	var btn_next = document.getElementById("btn_next");
	if (obj=="2"){
		tab2.className="tab_on";
		tab1.className="tab_off";
		block2.style.display="block";
		block1.style.display="none";
		btn_pro.style.display="block";
		btn_next.style.display="none";
	}else if (obj=="1") {
		tab2.className="tab_off";
		tab1.className="tab_on";
		block2.style.display="none";
		block1.style.display="block";
		btn_pro.style.display="none";
		btn_next.style.display="block";
	}
}