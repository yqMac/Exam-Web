function changeTab(obj)
{	
	var tab1 = document.getElementById("tab1");
	var tab2 = document.getElementById("tab2");
	var block1 = document.getElementById("block1");
	var block2 = document.getElementById("block2");
	var btn_pro = document.getElementById("btn_pro");
	var btn_next = document.getElementById("btn_next");
	if (obj=="1"){
		tab1.className="tab_on";
		tab2.className="tab_off";
		block1.style.display="block";
		block2.style.display="none";
		btn_pro.style.display="none";
		btn_next.style.display="block";
	}else if (obj=="2") {
		tab1.className="tab_off";
		tab2.className="tab_on";
		block1.style.display="none";
		block2.style.display="block";
		btn_pro.style.display="block";
		btn_next.style.display="none";
	}
}
function changeTab2(obj)
{	
	var tab2 = document.getElementById("tab2");
	var tab3 = document.getElementById("tab3");
	var block2 = document.getElementById("block2");
	var block3 = document.getElementById("block3");
	var btn_pro = document.getElementById("btn_pro");
	var btn_next = document.getElementById("btn_next");
	if (obj=="2"){
		tab2.className="tab_on";
		tab3.className="tab_off";
		block2.style.display="block";
		block3.style.display="none";
		btn_pro.style.display="none";
		btn_next.style.display="block";
	}else if (obj=="3") {
		tab2.className="tab_off";
		tab3.className="tab_on";
		block2.style.display="none";
		block3.style.display="block";
		btn_pro.style.display="block";
		btn_next.style.display="none";
	}
}
function changeTabThree(obj)
{	
	var tab1 = document.getElementById("tab1");
	var tab2 = document.getElementById("tab2");
	var tab3 = document.getElementById("tab3");
	var block1 = document.getElementById("block1");
	var block2 = document.getElementById("block2");
	var block3 = document.getElementById("block3");
	var btn_pro = document.getElementById("btn_pro");
	var btn_next = document.getElementById("btn_next");
	var btn_pro1 = document.getElementById("btn_pro1");
	var btn_next1 = document.getElementById("btn_next1");
	if (obj=="1"){
		tab1.className="tab_on";
		tab2.className="tab_off";
		tab3.className="tab_off";
		block1.style.display="block";
		block2.style.display="none";
		block3.style.display="none";
		btn_pro.style.display="none";
		btn_pro1.style.display="none";
		btn_next.style.display="block";
		btn_next1.style.display="none";
	}else if (obj=="2") {
		tab1.className="tab_off";
		tab2.className="tab_on";
		tab3.className="tab_off";
		block1.style.display="none";
		block2.style.display="block";
		block3.style.display="none";
		btn_pro.style.display="block";
		btn_pro1.style.display="none";
		btn_next1.style.display="block";
		btn_next.style.display="none";
	}else if (obj=="3") {
		tab1.className="tab_off";
		tab2.className="tab_off";
		tab3.className="tab_on";
		block1.style.display="none";
		block2.style.display="none";
		block3.style.display="block";
		btn_pro1.style.display="block";
		btn_pro.style.display="none";
		btn_next.style.display="none";
		btn_next1.style.display="none";
	}
}