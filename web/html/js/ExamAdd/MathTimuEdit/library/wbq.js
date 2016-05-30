/*
   WebEQ Solutions Library basic infrastructure. This file must always be
        included when using the Solutions Library functionality.
   Copyright (c) 2003, Design Science, Inc., all rights reserved
*/

// Root of WebEQ class file tree.  Set via setCodebase().
var javaCodebase = ".";

// Control class names
var viewerControl = "webeq3.ViewerControl";
var inputControl = "webeq3.editor.InputControl";
var graphControl = "webeq3.grapher.GraphControl";
var spControl = "webeq3.eval.ServiceProvider";

// applet defaults
var appletHeight = 50;
var appletWidth = 75;
var eqnEmptyString = "<math><mrow></mrow></math>";

// flag indicating MathPlayer is not available
var showAsApplet = true;

// Stored browser information in a structure called 'is'.
// One can then query is.ie or is.ns7.
is = new BrowserCheck();

// Write appropriate headers into the page.  If MathPlayer is
// available, this inserts the necessary declarations for it.
if (is.ie && testInstall()) {
        writeMathPlayerHeader();
}

//---------------  global Solutions Library API functions -------------------

// Sets the codebase directory for this session
function setCodebase(base) {
   javaCodebase = base;
   if (javaCodebase.charAt(javaCodebase.length-1)=='/') {
	javaCodebase = javaCodebase.substring(0, javaCodebase.length-1);
   }
}

//-------------------  support functions  --------------------------

// Method used to determine reader's browser type.
function BrowserCheck() {
   var b = navigator.appName;
   if (b.indexOf('Netscape')!=-1) this.b="ns";
   else if (b=="Microsoft Internet Explorer") this.b = "ie";
   if (!b) alert('Unidentified browser.\nThis browser is not supported,');

   this.version = navigator.appVersion;
   this.v = parseInt(this.version);
   this.ns = (this.b=="ns" && this.v>=4);
   this.ns4 = (this.b=="ns" && this.v==4);
   this.ns6=(this.b=="ns" && this.v==5);
   this.ns7 = (this.b=="ns" && this.v==7);
   this.ie = (this.b=="ie" && this.v>=4);
   this.ie4 = (this.version.indexOf('MSIE 4')>0);
   this.ie5 = (this.version.indexOf('MSIE 5')>0);
   this.ie55=(this.version.indexOf('MSIE 5.5')>0);
   this.ie6=(this.version.indexOf('MSIE 6.0')>0);
   this.min = (this.ns||this.ie);
   this.mac = (this.version.indexOf("Mac") != -1);
}

// writes MathPlayer header declarations into the page
function writeMathPlayerHeader() {
   // add namespace info
   var html = (document.getElementsByTagName("html"))[0];
   html.setAttribute("xmlns:m","http://www.w3.org/1998/Math/MathML");
   html.setAttribute("xmlns:dsi","http://www.mathtype.com/MathML");

   // add object tags
   document.writeln('<object id="mpbh" classid="clsid:32F66A20-7614-11D4-BD11-00104BD3F987">');
   document.writeln('</object>');
   document.writeln('<?import namespace="M" implementation="#mpbh" ?>');
   showAsApplet = false;
}

// verifies that MathPlayer is installed on the client's computer.
function testInstall() {
   var mpInst=false;
   if (is.ie && !is.mac) {
      // note: this is wrapped in an eval because Netscape doesn't support events
      eval (' try { var ActiveX = new ActiveXObject("MathPlayer.Factory.1"); mpInst = (ActiveX!=null); ActiveX=null;} catch (e) {  mpInst=false; }');
   }
   return mpInst;
}

// Method to insert the WebEQ Service Provider applet into the page
function writeServiceProvider() {
   if (document.write) { // this will not work in Netscape's XML mode
      var sptag = "<applet archive=\"WebEQApplet.jar\" codebase=\"";
      sptag += javaCodebase+"\" code=\""+spControl+"\" height=\"0\" width=\"0\" id=\"provider\" mayscript>";
      sptag += '<param name="useslibrary" value="WebEQApplet">';
      sptag += '<param name="useslibrarycodebase" value="WebEQApplet.cab">';
      sptag += '<param name="useslibraryversion" value="3,6,0,0"></applet>';

      document.write("<div id=\"providerDiv\">" + sptag + "</div>");
   } else {
      alert("unable to write service provider to the page");
   }
}

//--------------------  Error handling methods ---------------------------------------------//
// show a specific error
function showError(momObj, msg) {
   if (momObj && momObj.id) {
      alert("Error with object " + momObj.id + ": " + msg);
   } else {
      alert("Error: " + msg);
   }
}

