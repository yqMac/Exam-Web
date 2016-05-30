function document.oncontextmenu(){event.returnValue=false;};//��������Ҽ�
function document.onselectstart(){event.returnValue=false;};//�������ѡ��
function window.onhelp(){return false}; //����F1����
function document.onmousewheel()//����Shift+����,Ctrl+����
{
	if(event.shiftKey || event.ctrlKey)
	{
		event.keyCode=0; 
		event.returnValue=false; 		
	}
}
function document.onkeydown();
{ 
  if ((window.event.altKey)&& 
      ((window.event.keyCode==37)||   //���� Alt+ ����� �� 
       (window.event.keyCode==39)))   //���� Alt+ ����� �� 
  { 
     event.returnValue=false; 
  } 
  if ((event.keyCode==116)||               //���� F5 ˢ�¼�
      (event.ctrlKey && event.keyCode==82)){ //Ctrl + R 
     event.keyCode=0; 
     event.returnValue=false; 
     } 
  if(event.keyCode==32 || event.keyCode==8)	//���׿ո��,���˼�
  {
    if(!(event.srcElement.tagName=="INPUT" && event.srcElement.type=="text") && event.srcElement.tagName!="TEXTAREA")
    {
     event.keyCode=0; 
     event.returnValue=false; 
    }
  }
  if (event.keyCode==27){event.keyCode=0;event.returnValue=false;}  //����ESC
  
  if (event.keyCode==114){event.keyCode=0;event.returnValue=false;}  //����F3
  if (event.keyCode==122){event.keyCode=0;event.returnValue=false;}  //����F11
  if(event.ctrlKey && event.keyCode==67) {event.keyCode=0;event.returnValue=false;}	//���� Ctrl+c
  if(event.ctrlKey && event.keyCode==86) {event.keyCode=0;event.returnValue=false;}	//���� Ctrl+v
  if(event.ctrlKey && event.keyCode==70) {event.keyCode=0;event.returnValue=false;}	//���� Ctrl+f
  if(event.ctrlKey && event.keyCode==87) {event.keyCode=0;event.returnValue=false;}	//���� Ctrl+w
  if(event.ctrlKey && event.keyCode==69) {event.keyCode=0;event.returnValue=false;}	//���� Ctrl+e
  if(event.ctrlKey && event.keyCode==72) {event.keyCode=0;event.returnValue=false;}	//���� Ctrl+h
  if(event.ctrlKey && event.keyCode==73) {event.keyCode=0;event.returnValue=false;}	//���� Ctrl+i
  if(event.ctrlKey && event.keyCode==79) {event.keyCode=0;event.returnValue=false;}	//���� Ctrl+o
  if(event.ctrlKey && event.keyCode==76) {event.keyCode=0;event.returnValue=false;}	//���� Ctrl+l
  if(event.ctrlKey && event.keyCode==80) {event.keyCode=0;event.returnValue=false;}	//���� Ctrl+p
  if(event.ctrlKey && event.keyCode==66) {event.keyCode=0;event.returnValue=false;}	//���� Ctrl+b
  if (event.ctrlKey && event.keyCode==78) {event.keyCode=0;event.returnValue=false;}  //���� Ctrl+n
  if (event.shiftKey && event.keyCode==121){event.keyCode=0;event.returnValue=false;}  //���� shift+F10 
  if (window.event.srcElement.tagName == "A" && window.event.shiftKey) {event.keyCode=0;event.returnValue=false;}             //���� shift ���������¿�һ��ҳ 
} 