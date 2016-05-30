/*
	WebEQ Solutions Library Control Wrapper Objects
   Copyright (c) 2003, Design Science, Inc., all rights reserved
*/

// -----------------------  Control Base Class --------------------
// Contains common functionality of all controls.  The base class
// should not be instantiated directly.  

// Global id counter, used to generate unique id's.
Control.id =1; 

function Control() {
   this.init(appletHeight, appletWidth);
}

// private method controlling initialization of control
Control.prototype.init = function(h, w) {
   this.eqn;               // the equation object that the control is bound to
   this.id =Control.id++;  // unique ID for control
   this.bound=false;       // whether or not it is written into the page
   this.boundto;           // associated control applet for this wrapper
   if (h == null || w == null) {
      // height and width not entered (or entered wrong), so use defaults
      this.h = appletHeight;
      this.w = appletWidth;
   } else {
      this.h = h;
      this.w = w;
   }
   this.needsResize = false;
   this.bgcolor=null;    //take default setting for background color unless set
   this.error = "";
   this.alertErrors = false;
};


// Write out the WebEQ control onto the webpage
Control.prototype.write = function() {
   if (this.bound) { this.setError("already attached"); return; }
   document.write(this.getAppletTag()); // write the applet

   // the span is part of the DOM now, we can get it's ID
   this.boundto = document.getElementById("mom_ctrl" + this.id);
   if (!this.boundto) { this.setError("unable to insert applet into page"); return; }
   this.bound = true;
   this.needsResize = false;
};

// print the applet control in a specific object (specified by id)
Control.prototype.insertIntoPage = function(where, obj) {
   if (this.bound) { this.setError("already attached"); return; }
   if (typeof obj == "string") {
      obj = document.getElementById(obj);
      if ((!obj) || (obj == null)) { this.setError("unknown object id supplied"); return; }
   }

   if (is.ns6 || is.ns7) {
      this.NSinsertIntoPage(where, obj);
   }
   else {
      this.IEinsertIntoPage(where, obj);
   }
   this.needsResize = false;
};

//private function for inserting an applet in NS6/7
Control.prototype.NSinsertIntoPage = function(where, obj) {
  var markup = this.getAppletTag();
  var parsedHTML = null;
  var r = document.createRange();
  r.setStartBefore(document.body);
  try {
    parsedHTML = r.createContextualFragment(markup);
  }
  catch (e) {
    alert("Invalid markup: "+markup);
  }
  switch (where){
    case 'start':
    default:
      obj.insertBefore(parsedHTML, obj.firstChild);
      break;
    case 'end':
      obj.appendChild(parsedHTML);
      break;
    case 'replaceAll':
      obj.innerHTML=markup;
      break;
    case 'internalReplace': // private - replace just the applet
      alert("To resize in Netscape, remove Control and re-insert into page.");
  }
  this.bound = true;
  this.boundto = document.getElementById("mom_ctrl" + this.id);
};

//private function for inserting an applet in IE
Control.prototype.IEinsertIntoPage = function(where, obj) {
  var applet = this.getAppletObject();
  switch(where) {
    case 'start':
    default:
      obj.insertAdjacentElement("afterBegin", applet);
      break;
    case 'end':
      obj.insertAdjacentElement("beforeEnd", applet);
      break;
    case 'replaceAll':
      obj.innerHTML = "";
      obj.appendChild(applet);
      break;
    case 'internalReplace': // private - replace just the applet
      obj.replaceNode(applet);
      break;
  }  
  this.bound = true;
  this.boundto = document.getElementById("mom_ctrl" + this.id);
};

// remove the control from its specified location on the webpage.
Control.prototype.removeFromPage = function() {
   if (!this.bound) { this.setError("not attached"); return; }

   if (this.boundto) {
      if (is.ie){
         this.boundto.removeNode(true);  // true = recursively remove children
      } else if  (is.ns) {
         try {  this.boundto.parentNode.removeChild(this.boundto);   }
         catch (e) { /*dom exception */ 
            this.setError("Cannot remove control from the page");
         }
      }
   }
   this.bound = false;
   this.boundto=0;
};

//private function for generating applet tag boilerplate
Control.prototype.getAppletTagHeader = function(myControl) {
   var text = '<applet archive="WebEQApplet.jar" codebase="'+javaCodebase+'" ';
   text += 'code="'+myControl+'" height="' + this.h + '" ';
   text += 'width="' + this.w + '" id="mom_ctrl' + this.id + '">';
   text += '<param name="useslibrary" value="WebEQApplet">';
   text += '<param name="useslibrarycodebase" value="WebEQApplet.cab">';
   text += '<param name="useslibraryversion" value="3,6,0,0">';
   return text;
};

//private function for generating applet tag boilerplate
Control.prototype.getAppletObjectHeader = function(myControl) {
   var applet = document.createElement("applet");
   applet.codebase = javaCodebase;
   applet.archive = "WebEQApplet.jar";
   applet.useslibrary = "WebEQApplet";
   applet.useslibrarycodebase = "WebEQApplet.cab";
   applet.useslibraryversion = "3,6,0,0";
   applet.code = myControl;
   applet.height = this.h;
   applet.width = this.w;
   applet.id = "mom_ctrl" + this.id;
   return applet;
};

// getter/setter methods for the wrapper properties
Control.prototype.setEquation = function(eqn) { this.eqn = eqn; };
Control.prototype.getEquation = function() { return this.eqn; };
Control.prototype.setMathML = function(mml) { this.setEquation(new Equation(mml)); };
Control.prototype.getMathML = function() { 
   if (this.eqn) {
      return this.eqn.mml;
   } else {
      return "";
   }
};

Control.prototype.setSize = function(w, h) { this.w = w; this.h=h; this.needsResize=true;};
Control.prototype.getWidth = function() { return this.w; };
Control.prototype.getHeight = function() { return this.h; };
Control.prototype.getID = function() { return this.id; };
Control.prototype.isDisplayed = function() { return this.bound; };
Control.prototype.setBackgroundColor = function(bgcolor) { 
   this.bgcolor = bgcolor;  
   if (this.bound) { // set background color of the applet
      this.boundto.setBackgroundColor(bgcolor);
   }
};
Control.prototype.getBackgroundColor = function() { return this.bgcolor; };

// return node that the control is bound to on the webpage
Control.prototype.getDisplayComponent = function() { 
   if (!this.bound) { return null; }
   else { return this.boundto; }
};

// methods for handling errors
// method for setting the error message
Control.prototype.setError = function(msg) {
	this.error = msg;
	if (this.alertErrors && msg!=""){
		showError(this, this.error);
	}
};

// wether to alert with each error or be quiet (alert = true|false)
Control.prototype.alertOnErrors = function(alert) {
	this.alertErrors = alert;
};

// method for getting the error message
Control.prototype.getError = function() {
	return this.error;
};

// --------------------------  Viewer Control --------------------

// Wrapper for the WebEQ Viewer Control

function ViewerControl(wrap, h, w) {
   this.init(h, w);
   this.wrap = wrap;
   this.fontsize = null;      // take default setting for font size unless set
   this.fgcolor = null;    // take default setting for font size unless set
}
ViewerControl.prototype = new Control();
ViewerControl.prototype.constructor = ViewerControl;
ViewerControl.superclass = Control.prototype;

// set the equation that the viewer control is supposed to show
ViewerControl.prototype.setEquation = function(eqn) {
   this.eqn = eqn;
   if (this.bound) { // if bound to a location on the page, update that
      if (!this.boundto) { this.setError("applet does not exist with name "+this.id); return; }
      this.boundto.setEquation(this.eqn.mml);
      this.boundto.refresh();
   }
};

ViewerControl.prototype.setForegroundColor = function(fgcolor) { this.fgcolor = fgcolor; };
ViewerControl.prototype.getForegroundColor = function() { return this.fgcolor; };
ViewerControl.prototype.setFontSize = function(size) { this.fontsize = size; };
ViewerControl.prototype.getFontSize = function() { return this.fontsize; };

// private -- not in API, returns the viewer control applet text
ViewerControl.prototype.getAppletTag = function() {
   var text = this.getAppletTagHead(viewerControl);

   if (this.eqn && this.eqn.mml) { // an equation has been entered
      text += '<param name="eq" value="' + this.eqn.mml + '"/>';
   }
   text += '<param name="selection" value="true"/>';
   text += '<param name="parser" value="MathML"/>';
   if (this.wrap) {
      text += '<param name="linebreak" value="true"/>';
      text += '<param name="valign" value="top"/>';
      text += '<param name="align" value="left"/>';
   }
   if (this.bgcolor && this.bgcolor.length > 0) {
      text += '<param name="background" value="' + this.bgcolor + '"/>';
   }
   if (this.fgcolor && this.fgcolor.length > 0) {
      text += '<param name="foreground" value="' + this.fgcolor + '"/>';
   }
   if (this.fontsize && this.fontsize.length > 0) {
      text += '<param name="size" value="' + this.fontsize + '"/>';
   }
   text += '</applet>';
   return text;
};

// private -- not in API, returns the viewer control applet text
ViewerControl.prototype.getAppletObject = function() {
   var applet = this.getAppletObjectHeader(viewerControl);

   if (this.eqn && this.eqn.mml) { // an equation has been entered
      applet.eq= this.eqn.mml;
   }
   applet.selection = true;
   if (this.wrap) {
      applet.linebreak="true";
      applet.valign="top";
      applet.align="left";
   }
   if (this.bgcolor && this.bgcolor.length > 0) {
      applet.background=this.bgcolor;
   }
   if (this.fgcolor && this.fgcolor.length > 0) {
      applet.foreground=this.fgcolor;
   }
   if (this.fontsize && this.fontsize.length > 0) {
      applet.size=this.fontsize;
   }
   return applet;
};

// Refreshes the applet.  Style changes shouldn't take effect
// until refresh is called
ViewerControl.prototype.refresh = function() {
   if (this.bound) {
      if (!this.boundto) { this.setError("applet does not exist with name "+this.id); return; }
      if (this.needsResize) {
	this.bound=false;
        this.insertIntoPage("internalReplace", this.boundto);
	return;
      }
      if (!this.boundto) { this.setError("applet does not exist with name "+this.id); return; }
      if (this.fgcolor && this.fgcolor.length>0) {this.boundto.setForegroundColor(this.fgcolor);}
      if (this.bgcolor && this.bgcolor.length>0) {this.boundto.setBackgroundColor(this.bgcolor);}
      if (this.fontsize && this.fontsize.length>0) {this.boundto.setFontSize(this.fontsize);}
      this.boundto.refresh();
   }
};

// ---------------  Input Control --------------------
// wrapper for the WebEQ Input control

function InputControl(h, w) {
   this.init(h, w);
   this.toolbar="";
   this.fontsize = null;     // use default unless set
   this.fgcolor = null;      // use default unless set
   this.highlighton = false; // turn off syntax highlighting by default
}
InputControl.prototype = new Control();
InputControl.prototype.constructor = InputControl;
InputControl.superclass = Control.prototype;

// get an Equation object wrapping the control's current mathml
InputControl.prototype.getEquation = function(asContent) {
   var mml;
   if (this.bound) {
      var applet = this.boundto;
      if (!applet) { this.setError("applet does not exist with name "+this.id); return; }
      if (asContent) {
         mml =applet.getContentMathML();
      } else {
         mml =applet.getMathML();
      }
   }
   return new Equation(mml);
};

// convenience method to get MathML directly
InputControl.prototype.getMathML = function() {
   return this.getEquation().getMathML();
};

// set the equation shown in the input control
InputControl.prototype.setEquation = function(eqn) {
   this.eqn = eqn;
   if (this.bound) {
      if (!this.boundto) { this.setError("applet does not exist with name "+this.id); return; }
      this.boundto.setMathML(this.eqn.mml);
      this.boundto.refresh();
   }
};

InputControl.prototype.setToolbar = function(toolDefn) { 
   this.toolbar = toolDefn; 
   // we ask for a resize to force reinsertion into the page,
   // as a toolbar config can only be set via an applet parameter
   this.needsResize = true;  
};
InputControl.prototype.getToolbar = function() { return this.toolbar; };
InputControl.prototype.setForegroundColor = function(fgcolor) { this.fgcolor = fgcolor; };
InputControl.prototype.getForegroundColor = function() { return this.fgcolor; };
InputControl.prototype.setFontSize = function(size) { this.fontsize = size; };
InputControl.prototype.getFontSize = function() { return this.fontsize; };

// remove any equation from the input control
InputControl.prototype.clearEquation = function() {
   this.eqn = null;
   if (this.bound) { // clear the equation in the control
      this.boundto.clearEquation();
   }
};

// return whether or not there is an equation shown in the input control
InputControl.prototype.isEmpty = function() {
   if (this.eqn == null ) { return true; }
   if (this.bound) {
      return this.boundto.isEmpty();
   } else { // we don't have the ability to verify 'empty equations' so, just state that one exists
      return false; 
   }
};

// takes effect immediately
InputControl.prototype.highlightSyntax = function(turnOn) {
   this.highlighton = turnOn;
   if (this.bound) {
      return this.boundto.highlightSyntax(turnOn);
   }
};

// private -- not in API, returns the input control applet text
InputControl.prototype.getAppletTag = function() {
   var text = this.getAppletTagHeader(inputControl);
   if (this.eqn && this.eqn.mml) { // an equation has been entered
      text += '<param name="eq" value="' + this.eqn.mml + '"/>';
   }
   if (this.toolbar != "") {
      text += '<param name="toolbar" value="'+this.toolbar+'"/>';
   }
   if (this.bgcolor && this.bgcolor.length > 0) {
      text += '<param name="background" value="' + this.bgcolor + '"/>';
   }
   if (this.fgcolor && this.fgcolor.length > 0) {
      text += '<param name="foreground" value="' + this.fgcolor + '"/>';
   }
   if (this.fontsize && this.fontsize.length > 0) {
      text += '<param name="size" value="' + this.fontsize + '"/>';
   }
   text += '</applet>';
   return text;
};

// private -- not in API, returns the viewer control applet text
InputControl.prototype.getAppletObject = function() {
   var applet = this.getAppletObjectHeader(inputControl);	

   if (this.eqn && this.eqn.mml) { // an equation has been entered
      applet.eq= this.eqn.mml;
   }
   applet.selection = true;
   if (this.toolbar != "") {
      applet.toolbar=this.toolbar;
   }
   if (this.bgcolor && this.bgcolor.length > 0) {
      applet.background=this.bgcolor;
   }
   if (this.fgcolor && this.fgcolor.length > 0) {
      applet.foreground=this.fgcolor;
   }
   if (this.fontsize && this.fontsize.length > 0) {
      applet.size=this.fontsize;
   }
   return applet;
};


// Refreshes the applet.  Style changes shouldn't take effect
// until refresh is called
InputControl.prototype.refresh = function() {
   if (this.bound) {
      if (!this.boundto) { this.setError("applet does not exist with name "+this.id); return; }
      if (this.needsResize) {
	this.bound=false;
        this.insertIntoPage("internalReplace", this.boundto);
	return;
      }
      if (this.fgcolor && this.fgcolor.length>0) {this.boundto.setForegroundColor(this.fgcolor);}
      if (this.bgcolor && this.bgcolor.length>0) {this.boundto.setBackgroundColor(this.bgcolor);}
      if (this.fontsize && this.fontsize.length>0) {this.boundto.setFontSize(this.fontsize);}
      this.boundto.refresh();
   }
};

// ---------------  Graph Control --------------------

// the WebEQ Graph control wrapper
function GraphControl(h, w) {
   this.init(h, w);
   this.eqn = [];    // NOTE: graphs can handle multiple equations
   this.colors = []; // Each graph line can be a specific color
   this.hatches = []; // Each line can be filled using angled hatches

   // hold additional graph parameters
   this.axiscolor;      // color of the 2 axes
   this.graphbg;     // color of the graph part of the applet
   this.inset;          // how far in the graph starts from the edge of the applet (labels are in this space)
   this.labelcolor;     // color of the labels
   this.npts;           // # of pts used to draw the graph line
   this.nxtics;            // # of tic marks between x-axis divisions
   this.nytics;         // # of tic marks between y-axis divisions
   this.xmax;           // maximum value shown on x-axis
   this.ymax;           // maximum value shown on y-axis
   this.xmin;           // minimum value shown on x-axis
   this.ymin;           // minimum value shown on y-axis
   this.xaxis;          // where the x-axis crosses the y-axis
   this.yaxis;          // where the y'axis crosses the x-axis
}

GraphControl.prototype = new Control();
GraphControl.prototype.constructor = GraphControl;
GraphControl.superclass = Control.prototype;

GraphControl.prototype.getNumEquations = function() { return this.eqn.length; };
GraphControl.prototype.getEquation = function(eqnNum) {
   if (eqnNum > this.eqn.length) {
      this.setError("equation " + eqnNum + " does not exist"); 
      return;
   }
   return this.eqn[eqnNum];
};

// sets a specific equation in the graph control.
// Note that the graph control can have multiple equations, 
// and that each equation can be shown in its own color.
GraphControl.prototype.setEquation = function(eqn, eqnNum, color, hatched) {

   // handle invalid parameters
   if (typeof eqn == "string") {
      this.setError("Add an equation object, not MathML to the graph control");
      return;
   }
   if (eqnNum < 0 || eqnNum > 9) {
      this.setError("Overflow!  Only 10 equations can be shown by the grapher");
      return;
   }
   if (hatched == "true") {
      hatched = true;
   }
   if (hatched != true) {
      hatched = false; // don't mess with this, just set everything unusual to false
   }

   // set all of the values
   // NOTE: there *may* be a problem if a higher number is added without
   // the lower ones.
   this.eqn[eqnNum] = eqn; // store the equation
   this.colors[eqnNum] = color;
   this.hatches[eqnNum] = hatched;
   
   if (this.bound) { // update the equation
	  this.setError("");
      if (!this.boundto) { this.setError("applet does not exist with name "+this.id); return; }
      this.boundto.setMathML(this.eqn[eqnNum].getMathML(), eqnNum);
      var tmpErr = this.boundto.getError();
      this.boundto.setGraphColor(color, eqnNum);
      tmpErr += this.boundto.getError().length > 0 ? "\n" + this.boundto.getError() : "";
	  this.boundto.setAngledHatching(hatched, eqnNum);
      tmpErr += this.boundto.getError().length > 0 ? "\n" + this.boundto.getError() : "";
      this.boundto.refresh();
      tmpErr += this.boundto.getError().length > 0 ? "\n" + this.boundto.getError() : "";
	  if (tmpErr.length > 0) {
		  this.setError(tmpErr); 
	  }
	}
};

GraphControl.prototype.setMathML = function(mml, eqnNum, color, hatched) { 
   return this.setEquation(new Equation(mml), eqnNum, color, hatched);
};
GraphControl.prototype.getMathML = function(eqnNum) {
   return this.getEquation(eqnNum).getMathML();
};

GraphControl.prototype.getAxesColor = function() { return this.axiscolor; };
GraphControl.prototype.setAxesColor = function(color) { this.axiscolor=color; };
GraphControl.prototype.getGraphBackground = function() { return this.graphbg; };
GraphControl.prototype.setGraphBackground = function(color) { this.graphbg=color; };
GraphControl.prototype.getInset = function() { return this.inset; };
GraphControl.prototype.setInset = function(inset) { this.inset=inset; };
GraphControl.prototype.getLabelColor = function() { return this.labelcolor; };
GraphControl.prototype.setLabelColor = function(color) { this.labelcolor=color; };
GraphControl.prototype.getNPts = function() { return this.npts; };
GraphControl.prototype.setNPts = function(npts) { this.npts=npts; };
GraphControl.prototype.getNXTics = function() { return this.nxtics; };
GraphControl.prototype.setNXTics = function(nxtics) { this.nxtics=nxtics; };
GraphControl.prototype.getNYTics = function() { return this.nytics; };
GraphControl.prototype.setNYTics = function(nytics) { this.nytics=nytics; };
GraphControl.prototype.getXMax = function() { return this.xmax; };
GraphControl.prototype.setXMax = function(xmax) { this.xmax=xmax; };
GraphControl.prototype.getYMax = function() { return this.ymax; };
GraphControl.prototype.setYMax = function(ymax) { this.ymax=ymax; };
GraphControl.prototype.getXMin = function() { return this.xmin; };
GraphControl.prototype.setXMin = function(xmin) { this.xmin=xmin; };
GraphControl.prototype.getYMin = function() { return this.ymin; };
GraphControl.prototype.setYMin = function(ymin) { this.ymin=ymin; };
GraphControl.prototype.getAxesIntersectionX = function() { return this.xaxis; };
GraphControl.prototype.getAxesIntersectionY = function() { return this.yaxis; };
GraphControl.prototype.setAxesIntersection = function(xaxis, yaxis) {
   this.xaxis = xaxis;
   this.yaxis = yaxis;
};

GraphControl.prototype.getError = function() {
   if (this.bound) {
      this.error = this.boundto.getError();
   }
   return this.error;
};

// private -- not in API, returns the graph control applet text.
GraphControl.prototype.getAppletTag = function() {
   var text = this.getAppletTagHeader(graphControl);
   var numEqns = this.eqn.length;
   if (numEqns > 9) { // cannot be longer than 9 elements
     numEqns = 9;
   }

   for (var i=0; i<numEqns; i++) {
      if (this.eqn[i] && this.eqn[i].mml) {
         text += '<param name="eq' + i + '" value="' + this.eqn[i].mml + '"/>';
         if (this.colors[i]) {
            text += '<param name="eqcolor' + i + '" value="' + this.colors[i] + '"/>';
         }
         if (this.hatches[i]) {
            text += '<param name="anglehatch' + i + '" value="true"/>';
         }
      }
   }
   if (this.bgcolor && this.bgcolor.length > 0) {
      text += '<param name="bordercolor" value="' + this.bgcolor + '"/>';
   }
   if (this.axiscolor && this.axiscolor.length > 0) {
      text += '<param name="axiscolor" value="' + this.axiscolor + '"/>';
   }
   if (this.graphbg && this.graphbg.length > 0) {
      text += '<param name="graphbackground" value="' + this.graphbg + '"/>';
   }
   if (this.inset != null) {
      text += '<param name="inset" value="' + this.inset + '"/>';
   }
   if (typeof this.labelcolor != "undefined" && this.labelcolor.length > 0) {
      text += '<param name="labelcolor" value="' + this.labelcolor + '"/>';
   }
   if (this.npts != null) {
      text += '<param name="npts" value="' + this.npts + '"/>';
   }
   if (this.nxtics != null) {
      text += '<param name="nxtics" value="' + this.nxtics + '"/>';
   }
   if (this.nytics != null) {
      text += '<param name="nytics" value="' + this.nytics + '"/>';
   }
   if (this.xmax != null) {
      text += '<param name="xmax" value="' + this.xmax + '"/>';
   }
   if (this.ymax != null) {
      text += '<param name="ymax" value="' + this.ymax + '"/>';
   }
   if (this.xmin != null) {
      text += '<param name="xmin" value="' + this.xmin + '"/>';
   }
   if (this.ymin != null) {
      text += '<param name="ymin" value="' + this.ymin + '"/>';
   }
   if (this.xaxis != null) {
      text += '<param name="xaxis" value="' + this.xaxis + '"/>';
   }
   if (this.yaxis != null) {
      text += '<param name="yaxis" value="' + this.yaxis + '"/>';
   }

   text += '</applet>';
   return text;
};

// private -- not in API, returns the graph control applet object
GraphControl.prototype.getAppletObject = function() {
   var applet = this.getAppletObjectHeader(graphControl);	

   var numEqns = this.eqn.length;
   if (numEqns > 9) { // cannot be longer than 9 elements
     numEqns = 9;
   }

   for (var i=0; i<numEqns; i++) {
      if (this.eqn[i] && this.eqn[i].mml) {
         var stmt = "applet.eq" + i + "=" + this.eqn[i].mml;
	 eval(stmt);
         if (this.colors[i]) {
            stmt = "applet.eqcolor" + i + "=" + this.colors[i];
            eval(stmt);
         }
         if (this.hatches[i]) {
            stmt = "applet.anglehatch" + i + "=true";
            eval(stmt);
         }
      }
   }

   if (this.bgcolor && this.bgcolor.length > 0) {
      applet.bordercolor = this.bgcolor;
   }
   if (this.axiscolor && this.axiscolor.length > 0) {
      applet.axiscolor = this.axiscolor;
   }
   if (this.graphbg && this.graphbg.length > 0) {
      applet.graphbackgroun=this.graphbg;
   }
   if (this.inset && this.inset.length > 0) {
      applet.inset=this.inset;
   }
   if (this.labelcolor && this.labelcolor.length > 0) {
      applet.labelcolor=this.labelcolor;
   }
   if (this.npts && this.npts.length > 0) {
      applet.npts=this.npts;
   }
   if (this.nxtics && this.nxtics.length > 0) {
      applet.nxtics=this.nxtics;
   }
   if (this.nytics && this.nytics.length > 0) {
      applet.nytics=this.nytics;
   }
   if (this.xmax && this.xmax.length > 0) {
      applet.xmax=this.xmax;
   }
   if (this.ymax && this.ymax.length > 0) {
      applet.ymax=this.ymax;
   }
   if (this.xmin && this.xmin.length > 0) {
      applet.xmin=this.xmin;
   }
   if (this.ymin && this.ymin.length > 0) {
      applet.ymin=this.ymin;
   }
   if (this.xaxis && this.xaxis.length > 0) {
      applet.xaxis=this.xaxis;
   }
   if (this.yaxis && this.yaxis.length > 0) {
      applet.yaxis=this.yaxis;
   }
   return applet;
};

// Refreshes the applet.  Style changes shouldn't take effect
// until refresh is called
GraphControl.prototype.refresh = function() {
   if (this.bound) {
      if (!this.boundto) { this.setError("applet does not exist with name "+this.id); return; }
      if (this.needsResize) {
		this.bound=false;
        this.insertIntoPage("internalReplace", this.boundto);
		return;
      }

      var tmpErr = "";
	  if (this.axiscolor && this.axiscolor.length> 0) { 
		  this.boundto.setAxesColor(this.axiscolor);
	      tmpErr += this.boundto.getError().length > 0 ? "\n" + this.boundto.getError() : "";	  
	  }
      if (this.graphbg && this.graphbg.length>0) {
		  this.boundto.setGraphBackground(this.graphbg);
	      tmpErr += this.boundto.getError().length > 0 ? "\n" + this.boundto.getError() : "";	  
	  }
      if (this.inset && this.inset.length>0) {
		  this.boundto.setInset(this.inset);
	      tmpErr += this.boundto.getError().length > 0 ? "\n" + this.boundto.getError() : "";	  
	  }
      if (this.labelcolor && this.labelcolor.length>0) {
		  this.boundto.setLabelColor(this.labelcolor);
	      tmpErr += this.boundto.getError().length > 0 ? "\n" + this.boundto.getError() : "";	  
	  }
      if (this.npts && this.npts.length>0) {
		  this.boundto.setNPts(this.npts);
	      tmpErr += this.boundto.getError().length > 0 ? "\n" + this.boundto.getError() : "";	  
	  }
      if (this.nxtics && this.nxtics.length>0) {
		  this.boundto.setNXTics(this.nxtics);
	      tmpErr += this.boundto.getError().length > 0 ? "\n" + this.boundto.getError() : "";	  
	  }
      if (this.nytics && this.nytics.length>0) {
		  this.boundto.setNYTics(this.nytics);
	      tmpErr += this.boundto.getError().length > 0 ? "\n" + this.boundto.getError() : "";	  
	  }
      if (this.xmax && this.xmax.length>0) {
		  this.boundto.setXMax(this.xmax);
	      tmpErr += this.boundto.getError().length > 0 ? "\n" + this.boundto.getError() : "";	  
	  }
      if (this.ymax && this.ymax.length>0) {
		  this.boundto.setYMax(this.ymax);
	      tmpErr += this.boundto.getError().length > 0 ? "\n" + this.boundto.getError() : "";	  
	  }
      if (this.xmin && this.xmin.length>0) {
		  this.boundto.setXMin(this.xmin);
	      tmpErr += this.boundto.getError().length > 0 ? "\n" + this.boundto.getError() : "";	  
	  }
      if (this.ymin && this.ymin.length>0) {
		  this.boundto.setYMin(this.ymin);
	      tmpErr += this.boundto.getError().length > 0 ? "\n" + this.boundto.getError() : "";	  
	  }
      if (this.xaxis && this.xaxis.length>0) {
		  this.boundto.setAxesIntersection(this.xaxis, this.yaxis);
	      tmpErr += this.boundto.getError().length > 0 ? "\n" + this.boundto.getError() : "";	  
      }
      this.boundto.refresh();
	  tmpErr += this.boundto.getError().length > 0 ? "\n" + this.boundto.getError() : "";	  
	  this.setError(tmpErr);
   }
};

// --------------------------  Services Control --------------------
// NOTE: this control does not extend from the hierarchy.  It is not the same type of 
// control.  However, from a user's point of view, it is the same sort of thing... 
// a wrapper for a WebEQ control.

function ServicesControl() {
   this.bound = false;
   this.eval = null; 
   this.compare = null; 
   this.lastError = ""; 
}

// first include the methods for ensuring that the services control is added to the page
ServicesControl.prototype.write = function() {
   writeServiceProvider();
   this.bound = true;
};
ServicesControl.prototype.insertIntoPage = function() {
   insertServiceProvider();
   this.bound = true;
};

// private method to verify the existance of the services provider applet 
// somewhere to the page
ServicesControl.prototype.check = function() {
   if (! document.provider) { 
      this.setError("must write services provider in page first"); return false; 
   }
   else return true;
};

// return the last error given
ServicesControl.prototype.getError = function() {
   var lastErr = "";
   if (this.eval) {
     lastErr = this.eval.getError();
   }
   if (this.compare) {
     lastErr += this.compare.getError();
   }
   return lastErr;
};

//------------------  Evaluator functions -------------------------------

// return the evaluator object to the programmer.
ServicesControl.prototype.getEvaluator = function() {
   if (this.check()) { return document.provider.getService("evaluator"); }
   return null;
};

// private method used to check whether evaluator can be used
ServicesControl.prototype.evalCheck = function() {
   if (!this.eval) {
      this.eval = this.getEvaluator();
      if (!this.eval) {
         this.setError("must write services provider in page first"); return false;
      }
   }
   return true;
};

// Sets the equation needed to run this
ServicesControl.prototype.setMathML = function(mml) {
   if (this.evalCheck()) {
      return this.eval.setMathML(mml);
   }
   return false;
};

// returns the equation needed to run this
ServicesControl.prototype.getMathML = function() {
   if (this.evalCheck()) {
      return this.eval.getMathML();
   }
};

// bind a variable to a specific value
ServicesControl.prototype.bindVariable = function(bvar, val) {
   if (this.evalCheck()) {
      this.eval.bindVariable(bvar, val);
   }
};

// unbind an equation variable from a specific value
ServicesControl.prototype.unbindVariable = function(bvar) {
   if (this.evalCheck()) {
      this.eval.unbindVariable(bvar);
   }
};

// state whether equation can be evaluated
ServicesControl.prototype.canEvaluate = function() {
   if (this.evalCheck()) {
      return this.eval.canEvaluate();
   }
   return false;
};

// evaluate the equation at a specific point
ServicesControl.prototype.evalAt = function(num) {
   if (this.evalCheck()) {
      // coerce JavaScript into passing mynum as a number, not a string
      var mynum = num * 1.0; 
      return this.eval.evalAt(mynum);
   }
};

//----------------------  Comparator functions ------------------------------

// private method, called by other 3 compare methods
ServicesControl.prototype.setupComparison = function(mml1, mml2) {
   if (this.check()) {
      if (!this.compare) {
	this.compare = document.provider.getService("comparator");
      }
      if (!this.compare) { this.setError("unable to initialize comparator"); return null; }
      this.compare.setMathML(mml1, mml2);
   }
};

// compare the two MathML strings as strings.  This method returns true if the two 
// MathML are exactly the same as strings
ServicesControl.prototype.compareAsString = function(mml1, mml2) {
   this.setupComparison(mml1, mml2);
   if (this.compare && this.compare.canCompareAsString()) {
      return this.compare.compareAsString();
   }
   return "uncomparable";
};

// compares two equations by converting to content mml and comparing
ServicesControl.prototype.compareAsContent = function(mml1, mml2) {
   this.setupComparison(mml1, mml2);
   if (this.compare && this.compare.canCompareAsContent()) {
      return this.compare.compareAsContent();
   }
   return "uncomparable";
};

// compares by converting to content and evaluating at test points
ServicesControl.prototype.compareAsFunction = function(mml1, mml2) {
   this.setupComparison(mml1, mml2);
   if (this.compare && this.compare.canCompareAsFunction()) {
      return this.compare.compareAsFunction();
   }
   return "uncomparable";
};

// Adds the WebEQ Service Provider applet into the page after the page is written out.
// NOTE: cannot use this method as the page is loading.  Use writeServiceProvider in that case.
function insertServiceProvider() {
   if (is.ie) {
      // for some reason, the attribute display='none' does not work correctly in IE if you add it to an object
      // so, just add the markup directly...

      var markup = "<div id=\"providerDiv\">" + this.getAppletTagHeader(spControl) + "</applet></div>";
      document.body.insertAdjacentHTML("beforeEnd",  markup);
   } else {
      if (!document.body) { // if in xml mode
         document.body = document.getElementsByTagName("body")[0];
      }
      var mybody = document.body;
      var mydiv = document.createElement("div");
      mydiv.setAttribute("id", "providerDiv");

      var myapplet = document.createElement("applet");
      myapplet.setAttribute("codebase", javaCodebase);
      myapplet.setAttribute("archive", "WebEQApplet.jar");
      myapplet.setAttribute("code", spControl);
      myapplet.setAttribute("height", "0");
      myapplet.setAttribute("width", "0");
      myapplet.setAttribute("id", "provider");
      myapplet.setAttribute("mayscript", "");
      
      var myparam_useslibrary = document.createElement("param");
      myparam.setAttribute("name", "useslibrary");
      myparam.setAttribute("value", "WebEQApplet");
      myapplet.appendChild(myparam_useslibrary);
      
      var myparam_useslibrarycodebase = document.createElement("param");
      myparam.setAttribute("name", "useslibrarycodebase");
      myparam.setAttribute("value", "WebEQApplet.cab");
      myapplet.appendChild(myparam_useslibrarycodebase);

      var myparam_useslibraryversion = document.createElement("param");
      myparam.setAttribute("name", "useslibraryversion");
      myparam.setAttribute("value", "3,6,0,0");
      myapplet.appendChild(myparam_useslibraryversion);
      
      mydiv.appendChild(myapplet);
      mybody.appendChild(mydiv);
   }
}
