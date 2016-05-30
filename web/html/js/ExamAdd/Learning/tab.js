function changeTab(obj)
{	
	var tab1 = document.getElementById("tab1");
	var tab2 = document.getElementById("tab2");
	var block1 = document.getElementById("block1");
	var block2 = document.getElementById("block2");
	if (obj=="1"){
		tab1.className="tab_on";
		tab2.className="tab_off";
		block1.style.display="block";
		block2.style.display="none";
	}else if (obj=="2") {
		tab1.className="tab_off";
		tab2.className="tab_on";
		block1.style.display="none";
		block2.style.display="block";
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
	if (obj=="1"){
		tab1.className="tab_on";
		tab2.className="tab_off";
		tab3.className="tab_off";
		block1.style.display="block";
		block2.style.display="none";
		block3.style.display="none";
	}else if (obj=="2") {
		tab1.className="tab_off";
		tab2.className="tab_on";
		tab3.className="tab_off";
		block1.style.display="none";
		block2.style.display="block";
		block3.style.display="none";
	}else if (obj=="3") {
		tab1.className="tab_off";
		tab2.className="tab_off";
		tab3.className="tab_on";
		block1.style.display="none";
		block2.style.display="none";
		block3.style.display="block";
	}
}
function changeTabFour(obj)
{	
	var tab1 = document.getElementById("tab1");
	var tab2 = document.getElementById("tab2");
	var tab3 = document.getElementById("tab3");
	var tab4 = document.getElementById("tab4");
	var block1 = document.getElementById("block1");
	var block2 = document.getElementById("block2");
	var block3 = document.getElementById("block3");
	var block4 = document.getElementById("block4");
	if (obj=="1"){
		tab1.className="tab_on";
		tab2.className="tab_off";
		tab3.className="tab_off";
		tab4.className="tab_off";
		block1.style.display="block";
		block2.style.display="none";
		block3.style.display="none";
		block4.style.display="none";
	}else if (obj=="2") {
		tab1.className="tab_off";
		tab2.className="tab_on";
		tab3.className="tab_off";
		tab4.className="tab_off";
		block1.style.display="none";
		block2.style.display="block";
		block3.style.display="none";
		block4.style.display="none";
	}else if (obj=="3") {
		tab1.className="tab_off";
		tab2.className="tab_off";
		tab3.className="tab_on";
		tab4.className="tab_off";
		block1.style.display="none";
		block2.style.display="none";
		block3.style.display="block";
		block4.style.display="none";
	}else if (obj=="4") {
		tab1.className="tab_off";
		tab2.className="tab_off";
		tab3.className="tab_off";
		tab4.className="tab_on";
		block1.style.display="none";
		block2.style.display="none";
		block3.style.display="none";
		block4.style.display="block";
	}
}