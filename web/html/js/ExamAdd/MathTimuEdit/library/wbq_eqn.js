/* 
  WebEQ Solutions Library Equation Wrappers
  Copyright (c) 2003, Design Science, Inc., all rights reserved

  An equation object store MathML equation data, and optionally
  coordinates with a browser-dependent display component in the page.

  All equations inherit their 'appearance' from the global eqnDefault object.  
  The values in this global object can be set by the user, and affect all equations 
  created after that point.  The appearance of individual equations can be changed 
  directly also, but after a display component has been inserted into
  the page, the refresh() method must be called to make changes take effect.

  An equation can only be shown in one location, however, its MathML can be 
  passed to any number of services.  If there is a need to show it in more than one 
  location, for whatever need, the clone method must be called.
*/

Equation.id = 1; // instance of id that works across all equation objects

// Create an equation from the MathML.  The constructor can be called 
// in two ways: Equation(String mml) and Equation(Array args), where
// args = [mml, height, width, ascent].
function Equation(args) {

   this.id = Equation.id++;  // assign a unique id to this equation

   if (typeof args == "string" || args instanceof String) {
      this.mml = args;
      this.w = appletWidth;     // set default values
      this.h = appletHeight;    
      this.a = appletHeight;    
      this.needResize = true;   // indicate we need to resize when possible
   }  
   else {
      this.mml = args[0];      // set explicit values, so no need to resize
      this.w = args[1];         
      this.h = args[2];         
      this.a = args[3];         
      this.needResize = false;
   }

   // Store the <span> surrounding the associated display component in
   // the page.  The reason is one cannot get the DOM parent of an applet.
   // So, to remove the applet from the DOM we save the parent.  Note,
   // that display components are always contained in a <span> which
   // defines ambient equation rendering properties
   this.boundto = null;         // associated display component span

   // appearance parameters (initial vals set from defaults)
   this.fontsize = eqnDefaults.size;   // size of font (in pixels)
   this.fgcolor = eqnDefaults.fgcolor; // rgb/named-color value of the text
   this.bgcolor = eqnDefaults.bgcolor; // rgb/named-color value of the background 
}

Equation.prototype.refresh = function() {
   if (this.boundto) {
      if (showAsApplet) {
         applet = document.getElementById("mom" + this.id);

	 // update the applet with all the new properties
         applet.setMathML(this.mml); 
	 applet.setFontSize(Number(this.fontsize));
	 applet.setBackgroundColor(this.bgcolor);
	 applet.setForegroundColor(this.fgcolor);

	 if (this.needsResize) {
	   // get the h,w,a from it
	   this.w = applet.getPreferredWidth();
	   this.h = applet.getPreferredHeight();
	   this.a = applet.getPreferredAscent();
	   this.needsResize = false;

	   // in order to replace this, we need to convince the 
	   // equation that it's not bound, and then to replace 
	   // it in the same location. NOTE: by using "internalReplace", 
	   // we're replacing the existing div/span with a copy
	   var boundHolder = this.boundto; 
	   this.boundto=null;
	   this.insertIntoPage("internalReplace", boundHolder); 
	 }
	 else {
           applet.refresh(); // refresh the applet inside the div
         }
      } else {
	 if (this.needsResize) {
           this.needsResize = false;
	   var boundHolder = this.boundto; 
	   this.boundto=null;
	   this.insertIntoPage("internalReplace", boundHolder); 
         }
	 else {
           // mathplayer, update style properties on the span
           this.boundto.removeAttribute("style");

           // this.boundto.setAttribute("style", this.getSpanAttrs());
           //
           // one would like this to work in IE, but instead
           // one has to set the individual properties in the
           // runtime style object

           this.boundto.style.color = this.fgcolor;
           this.boundto.style.background = this.bgcolor;
           this.boundto.style.fontsize = this.fontsize;
           this.boundto.firstChild.update();
         }
      }
   }
};

// Insert a display component into a DOM node, preferably a <div> or <span>.
// Where the component is added depends on the 'where' param.
// which can be 'start', 'end', 'replaceAll' or (internally) 'internalReplace'
Equation.prototype.insertIntoPage = function(where, obj) {
  if (this.boundto) { 
    showError(this, "must call 'removeFromPage' before re-inserting"); 
    return; 
  }

  if (typeof obj == "string") {
     obj = document.getElementById(obj);
     if ((!obj) || (obj == null)) { 
       showError(this, "unknown object supplied"); 
       return; 
     }
   }

   // we have to handle MathPlayer differently
   if (is.ie && !showAsApplet) {
      var markup = this.getSpan(this.toBrowserDependentString());
      // figure out where to put the markup
      switch(where) {
         case 'start':
         default:
            obj.insertAdjacentHTML("afterBegin", markup);
            this.boundto = obj.firstChild; // div is first child
            break;
         case 'end':
            obj.insertAdjacentHTML("beforeEnd", markup);
            this.boundto = obj.lastChild; // div is last child
            break;
         case 'replaceAll':
            obj.innerHTML = markup;
            this.boundto = obj.firstChild; // div is only (first) child
            break;
         case 'internalReplace':
            var sib = obj.previousSibling;
            var par = obj.parentNode;
            obj.outerHTML = markup;
            // now, get the applet/behavior
            if (sib) {
               this.boundto = sib.nextSibling;
            } else {
               this.boundto = obj = par.firstChild;
            }
            break;
      }  
   } 
	
   // No MathPlayer so show as an applet
   else {
      
      formatter.setCurrentEqn(this);

      // Netscape 6/7 applet case
      if (is.ns6 || is.ns7) {
        var markup = formatter.getAppletCode("viewer", this.h, this.w, this.a, "mom"+this.id);
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
	      obj.insertBefore(parsedHTML,obj.firstChild);
	      this.boundto = obj.firstChild; // div is first child
	      break;
	   case 'end':
	      obj.appendChild(parsedHTML);
	      this.boundto = obj.lastChild; // div is last child
	      break;
	   case 'replaceAll':
	      obj.innerHTML=markup;
	      this.boundto = obj.firstChild;    // div is only (first) child
	      break;
	   case 'internalReplace':
	      // replace <math>...</math> with applet tag
	      var sib = obj.previousSibling;
	      var par = obj.parentNode;
	      par.replaceChild(parsedHTML, obj);
	      // now, get the applet
	      if (sib) {
		 this.boundto = sib.nextSibling;
	      } else {
		 this.boundto = obj = par.firstChild;
	      }
	      break;
	}
      }
      // IE applet case
      else {
	 var appletObject = formatter.getAppletObject("viewer", this.h, this.w, this.a, "mom"+this.id);
	 switch(where) {
	    case 'start':
	    default:
	       obj.insertAdjacentElement("afterBegin", appletObject);
               this.boundto = obj.firstChild; // div is first child
	       break;
	    case 'end':
	       obj.insertAdjacentElement("beforeEnd", appletObject);
               this.boundto = obj.lastChild; // div is last child
	       break;
	    case 'replaceAll':
	       obj.innerHTML = "";
	       obj.appendChild(appletObject);
               this.boundto = obj.firstChild; // div is first child
	       break;
	    case 'internalReplace':
	       obj.replaceNode(appletObject);
	       this.boundto = document.getElementById("mom" + this.id + "Span");
	       break;
	 }  
      }
   }
};

// Write out the equation on the webpage.
Equation.prototype.write = function() {
   if (this.boundto) { showError(this, "already inserted"); return; }
   var markup = this.toBrowserDependentString();
   document.write("\n"+ markup);
   // Once span is part of the DOM, we can get its ID
   this.boundto = document.getElementById("mom" + this.id + "Span");
};

// Convenience method, so one can write "value =" + eqn
// instead of "value=" + eqn.toBrowserDependentString()
Equation.prototype.toString = function() {
   return this.toBrowserDependentString();
};

// Return markup for browser-dependent display component
Equation.prototype.toBrowserDependentString = function() {
   var markup;
   formatter.setCurrentEqn(this);
   if (is.ie && !showAsApplet) {
      this.mml = formatter.styleEqn();
      // wrap object in a span tag and associate an ID with it     
      markup = this.getSpan(this.mml); 
   } else {
      markup = formatter.getAppletCode("viewer", this.h, this.w, this.a, "mom"+this.id);
   }
   return markup;
};


// Remove associated display component from page
Equation.prototype.removeFromPage = function() {
   if (!this.boundto) { showError(this, "not attached"); return; }

   // we know equation is bound to a location...
   if (is.ie){
      this.boundto.removeNode(true);  // true = recursively remove children
   } else if  (is.ns) {
      try {  this.boundto.parentNode.removeChild(this.boundto);   }
      catch (e) { /*dom exception */ }
   }
   this.boundto=null; // no longer bound to anything
};

// Returns unformatted MathML for this equation
Equation.prototype.getMathML = function() {
   return this.mml;
};

// Sets the MathML, and formats it according to the style set in the
// formatter option 
Equation.prototype.setMathML = function(mml) {
   this.mml = mml;
   formatter.setCurrentEqn(this);
   this.mml = formatter.styleEqn();
   this.needsResize = true;
   this.refresh(); // does a resize
};

// Initializes equation object fom MathML pre-written into
// the webpage.  The container element for the markup must have an ID. 
Equation.prototype.setMathMLFromID = function(id) {
   if (typeof id == "undefined") { showError(this, "no such object exists"); return; }
   this.mml = id.innerHTML;
   if (this.mml.search("<(m:)?math") == -1) { 
      this.mml = id.parentNode.innerHTML;
      // remove any 'junk' around the equation
      this.mml = this.mml.replace(/^.*?<(m:)?math/im, "<$1math");
      this.mml = this.mml.replace(/<\/(m:)?math>.*?$/im, "</$1math>");
      this.needsResize = true;
      this.insertIntoPage("internalReplace", id); 
      if (this.mml.length == 0) {
         alert("no MathML found in node with id=" + id);
      }
   }
};

// Set the size for the applet for this equation 
Equation.prototype.setSize = function(w, h, a) {
   this.w = w;
   this.h = h;
   if (a == null) {
      this.a = h;
   } else {
      this.a = a;
   }
   this.needsResize = true;
};

// sets font size
Equation.prototype.setFontSize = function(size) { 
  this.fontsize = size;
  this.needsResize = true;
};

// returns font size
Equation.prototype.getFontSize = function() { 
  return this.fontsize;
};

// set foreground color
Equation.prototype.setForegroundColor = function(color) { 
  this.fgcolor = color;
};

// return foreground color
Equation.prototype.getForegroundColor = function() { 
  return this.fgcolor;
};

Equation.prototype.setBackgroundColor = function(color) { // set background color
  this.bgcolor = color;
};

Equation.prototype.getBackgroundColor = function() { // return background color
  return this.bgcolor;
};


// Internal method to create a <span> for the equation that 
// contains all of the necessary style attributes for the equation
Equation.prototype.getSpan = function(content) {
   // first, construct the style line, if any
   var styleTag=this.getSpanAttrs();   
   // style tag created, now create span tag
   if (styleTag.length > 0) {
      styleTag = ' style="'+styleTag+'"';
   }
   var spanTag = '<span id="mom' + this.id + 'Span"'+styleTag+'>'+content+'</span>';

   return spanTag;
};

// Internal method called by getSpan and refresh
Equation.prototype.getSpanAttrs = function() {
   var styleAttrStr = "";
   if (this.fontsize) {
      styleAttrStr += 'font-size: '+this.fontsize+'; ';
   }
  
   if (this.fgcolor && this.fgcolor.length > 0) {
      styleAttrStr += 'color: '+this.fgcolor+'; ';
   }
  
   if (this.bgcolor && this.bgcolor.length > 0) {
      styleAttrStr += 'background: '+this.bgcolor+'; ';
   }

   return styleAttrStr;
};

// Returns the unique identifier associated with the equation
Equation.prototype.getID = function() { return this.id; };

// States whether the associated display component is inserted in the page
Equation.prototype.isDisplayed = function() { return (this.boundto != null); };

// returns a pointer to the associated display component
Equation.prototype.getDisplayComponent = function() { 
   if (! this.boundto ) { return null; }
   else { return this.boundto.firstChild; }
};

// Creates a copy of an existing equation
Equation.prototype.clone = function() {
   var neweq = new Equation(this.mml);
   // set equation specific parameters
   neweq.w = this.w;
   neweq.h = this.h;
   neweq.a = this.a;
   neweq.needsResize = this.needResize;
   neweq.fontsize = this.fontsize;
   neweq.fgcolor = this.fgcolor;
   neweq.bgcolor = this.bgcolor;
   return neweq;
};

// ------------------  EquationDefaults -----------------------
// Stores all parameters that specify  the 'appearance' of an equation.  

function EquationDefaults() {
  this.size = "12";       // size of font (in pixels)
  this.fgcolor = "black"; // rgb/named-color value of the text
  this.bgcolor = "white"; // rgb/named-color value of the background 
}

// create a global instance of this object
var eqnDefaults = new EquationDefaults();  

// global method that returns eqnDefaults
function getEquationDefaults() {
  return eqnDefaults;
}

// sets font size
EquationDefaults.prototype.setFontSize = function(size) { 
  this.size = size;
};

// returns font size
EquationDefaults.prototype.getFontSize = function() { 
  return this.size;
};

// set foreground color
EquationDefaults.prototype.setForegroundColor = function(color) { 
  this.fgcolor = color;
};

// return foreground color
EquationDefaults.prototype.getForegroundColor = function() { 
  return this.fgcolor;
};

// set background color
EquationDefaults.prototype.setBackgroundColor = function(color) { 
  this.bgcolor = color;
};

// return background color
EquationDefaults.prototype.getBackgroundColor = function() { 
  return this.bgcolor;
};

//---------------------- Equation Formatter -------------------------
// Internal object for formating MathML.  Options are:
// Namespace: prefix string
// Declaration: xmlns:m="http://www.w3.org/1998/Math/MathML"
// PrettyPrint: whether to format with whitespace

// Equation formatter object constructor  
function EquationFormatter() {
   this.eq = null;
   this.browserSpecificParams();
   this.valid = false; // no equation has been passed in yet
}

// Internal method to set the attributes according to the browser that
// is being used 
EquationFormatter.prototype.browserSpecificParams = function() {
   if (is.ie) {
      if (showAsApplet) {
        this.ns=false;
        this.dec=false;
        this.pp=false;
      }
      else {
        // use mathplayer encoding
        this.ns=true;
        this.dec=true;
        this.pp=false;
      }
   } 
  else if (is.ns) {
      // prepare for native MathML or applets
      this.ns=false;
      this.dec=true;
      this.pp=false;
   }
};

// Attach an equation to the formatter object so that it can be formatted
EquationFormatter.prototype.setCurrentEqn = function(eq) {
   this.eq = eq;
   this.valid = true;   
};

// Detaches a formatted equation
EquationFormatter.prototype.detach = function() {
   this.eq = null;
   this.valid = false;
};

EquationFormatter.prototype.setNamespace = function(hasNS) { this.ns = hasNS; };
EquationFormatter.prototype.hasNamespace = function() { return this.ns; };
EquationFormatter.prototype.setDeclaration = function(hasDec) { this.dec = hasDec; };
EquationFormatter.prototype.hasDeclaration = function() { return this.dec; };
EquationFormatter.prototype.setPrettyPrint = function(isPP) { this.pp = isPP; };
EquationFormatter.prototype.isPrettyPrint = function() { return this.pp; };

// If the formatter is attached to an equation, return the equation
EquationFormatter.prototype.getCurrentEqn = function() {
   if (!this.valid) { showError(this, "not attached to an equation"); return; }
   return this.eq;
};

// a 'quick and dirty' method that puts in all params at once, instead of calling all separate methods
EquationFormatter.prototype.getFormattedMathML = function(eq, namespace, declaration, prettyprint) {
   this.eq = eq; // FIX - Should the ID be set?  Or just verified against the equation?
   this.ns = namespace;
   this.dec = declaration;
   this.pp = prettyprint;
   this.valid = true;
   return this.styleEqn();
};

// format the equation based on the parameters given
EquationFormatter.prototype.styleEqn = function() {
   if (!this.valid) { showError(this, "no associated equation"); return; }
   var mml = this.eq.mml; // set the MathML
   mml += ""; // convice NS7 this really is a string...
   // Add or remove the namespace attribute to all MathML elements
   if (this.ns) {
      // there appears to be a bug in the RegExp machinery.  The
      // following code *should* work in order to insert an 'm:' in
      // any tag that doesn't have one, but it doesn't really work. 
      // so, we have to do it in 2 passes instead.
      mml = mml.replace(/(<\/?)m:/g, "$1"); // remove any m:
      mml = mml.replace(/(<\/?)/g, "$1m:"); // insert m: in every tag
   } else {
      mml = mml.replace(/(<\/?)m:/g, "$1");
   }

   if (this.dec) {
      mml = mml.replace(/math>/, "math xmlns:m=\"http://www.w3.org/1998/Math/MathML\">");
   } else {
      mml = mml.replace(/<math.*?>/, "<math>");
   }


   // remove all formatting
   //mml = mml.replace(/\n/g, "");
   mml = mml.replace(/&amp;/g, "&"); // handle escaped entities.
   mml = mml.replace(/^\s* /g, "");

   mml = mml.replace(/\s*$/g, "");
   mml = mml.replace (/\t/g, " ");
   mml = mml.replace(/\s\s+/g, " ");
   // if it's prettyprinted, add new formatting in
   if (this.pp) {
      mml = this.prettyPrint(mml);
   }
   return mml;
};


// private method containing all code needed to print out the MathML
// in a 'pretty' manner. that is, with each element on a different
// line, and all children indented from their parents 
EquationFormatter.prototype.prettyPrint = function(mml) {
   // can change these 2 parameters to control printout
   var widthOfIndent = 2;
   var leftMargin = 0; // formerly startSpace

   var mathrows, newrows = [], lastrow="";
   var j=0; // start indenting at 0
   mathrows = mml.split("<");

   mathrows.shift(); // get rid of empty tag at front

   var mathrow = mathrows.shift();
   var opentag="", closetag="";

   while (typeof mathrow != "undefined") {
      var combined = false;
      if (mathrow.search( /^\// ) != -1) {   // un-indent closing tags first
	      j--;
      }
      // get information to keep token tags on same line
      // this makes code easier to read by having things like
      // <mn>1</mn> all together 

      // match things that don't start with a slash up until a > or a
      // space (signifying attributes) 
      var pattern = /^([^\/][^>|\s]*)/; 
      var result;
      opentag;
      if ((result = pattern.exec(mathrow)) != null) {
         if (result) {
            opentag = result[1];
         } 
      }

      // matching things that start with a slash up until a > or a
      // space (signifying attributes) 
      var pattern = /^\/([^>|\s]*)/; 
      var result;
      closetag;
      if ((result = pattern.exec(mathrow)) != null) {
         if (result) {
            closetag = result[1];
         } 
      }

      // if open and close are the same and the line is short enough,
      // keep it all on 1 line 
      if (opentag != "" && opentag == closetag && lastrow.length < 35 && mathrow.length < 10) {
         newrows.pop(); // remove the old row

         // if there is no space before the data, remove the space after
         if (lastrow.search(/>[^\s]/)!= -1) { 
            lastrow=lastrow.replace(/\s*$/, "");
         } 
         // there is a space before the data, ensure one is added after
	      else {                               
            lastrow +=" ";
            lastrow=lastrow.replace(/\s\s* /," "); // clean up multiple spaces
         }
         opentag = closetag = "";
         lastrow += "<"+mathrow;
         mathrow = lastrow;
         combined=true;
      }

      // now that the line is figured out, figure out the spacing
      var newrow="";

      for (var i=0; i<leftMargin; i++) { newrow += " "; }
      for (var i=0; i<j*widthOfIndent; i++) { newrow += " ";}

      newrow += "<"+mathrow+"\n";
      if (newrow.search(/^\s*$/) == -1) {
         newrows.push(newrow);
      }

      // if this isn't an empty tag, and a closing tag wasn't just
      // added and it isn't a comment, indent 
      if ( mathrow.search(/\/\s*>/) == -1 && closetag == "" && !(combined) && mathrow.search( /^!--.*-->/) == -1 ) { // indent last
         j++;
      }
      lastrow = mathrow;
      mathrow = mathrows.shift();
   }

   var fstring = newrows.join("");
   newrows.splice(0); // clear the old array
   return fstring;
};

// private method to generate applet tag boiler plate
EquationFormatter.prototype.getAppletTagHeader = function(myControl, height, width, domID) {
    var text = "<applet archive=\"WebEQApplet.jar\" ";
    //text += "codebase=\""+javaCodebase+"\" code=\""+myControl;
    text += "codebase=\WebEQApplet.jar\" code=\""+myControl;
    text += "\" height=\""+height+"\" width=\""+width+"\" id=\""+domID+"\" align=\"middle\">\n";
    text += '<param name="useslibrary" value="WebEQApplet">';
    text += '<param name="useslibrarycodebase" value="WebEQApplet.cab">';
    text += '<param name="useslibraryversion" value="3,6,0,0">';
    return text;
};


// Generate WebEQ browser control applet code.
EquationFormatter.prototype.getAppletCode = function(controlType, height, width, ascent, domID) {
   if (this.eq == null) { showError(this, "no associated equation"); return; }
   mml = this.formatAsParam();
   // for debugging
   // this.eq.bgcolor="red";

   // now create applet tag based on the type
   var appletText="";
   if (ascent == 0) {
     ascent = height;
   }
   
   if (controlType == "viewer") {
      if (height == appletHeight && width == appletWidth && ascent == appletHeight) {
         appletText += "<span id=\""+domID+"Span\">";
	 appletText += this.getAppletTagHeader(viewerControl, height, width, domID);
         appletText += "   <param name=\"valign\" value=\"baseline\"/>\n";
         appletText += "   <param name=\"align\" value=\"left\"/>\n";
      }
      else {
	 var shift = (is.ie) ? 16 - ascent : 16 - ascent; // this is black magic -- edit to suit
         appletText += "<span style=\"position:relative; top: " + shift + "px\" id=\""+domID+"Span\">";
	 appletText += this.getAppletTagHeader(viewerControl, height, width, domID);
         appletText += "   <param name=\"valign\" value=\"top\"/>\n";

      }
      appletText += "   <param name=\"controls\" value=\"true\"/>\n";
      appletText += "   <param name=\"selection\" value=\"true\"/>\n";
   } 
   else if (controlType = "input") {
      appletText += this.getAppletTagHeader(inputControl, height, width, domID);
   }

   appletText += "   <param name=\"eq\" value=\""+mml+"\"/>\n";
   if (this.eq.fontsize>0) {
      appletText += "   <param name=\"size\" value=\""+this.eq.fontsize+"\"/>\n";
   }
   if (this.eq.fgcolor != "") {
      appletText += "   <param name=\"foreground\" value=\""+this.eq.fgcolor+"\"/>\n";
   }
   if (this.eq.bgcolor != "") {
      appletText += "   <param name=\"background\" value=\""+this.eq.bgcolor+"\"/>\n";
   }


   appletText += "</applet>\n";
   if (type="viewer") {
      appletText += "</span>\n";
   }

   return appletText;
};


EquationFormatter.prototype.getAppletObject = function(controlType, height, width, ascent, domID) {
   if (this.eq == null) { showError(this, "no associated equation"); return; }

   var applet = document.createElement("applet");
   //applet.codebase = javaCodebase;
   applet.codebase = "WebEQApplet.jar";
   applet.archive = "WebEQApplet.jar";
   applet.useslibrary = "WebEQApplet";
   applet.useslibrarycodebase = "WebEQApplet.cab";
   applet.useslibraryversion = "3,6,0,0";
   applet.code = viewerControl;
   applet.height = height;
   applet.width = width;
   applet.id = domID;

   if (this.eq && this.eq.mml) { // an equation has been entered
      applet.eq= this.eq.mml;
   }
   applet.selection = true;
   applet.controls = true;

   if (height == appletHeight && width == appletWidth && ascent == appletHeight) {
	applet.align="left";
	applet.valign="baseline";
   }
   else {
	applet.valign="top";
   }

   if (this.eq.bgcolor && this.eq.bgcolor.length > 0) {
      applet.background=this.eq.bgcolor;
   }
   if (this.eq.fgcolor && this.eq.fgcolor.length > 0) {
      applet.foreground=this.eq.fgcolor;
   }
   if (this.eq.fontsize > 0) {
      applet.size=this.eq.fontsize;
   }

   // now set up span wrapper
   var span = document.createElement("span");
   span.id = domID + "Span";
   span.style.position="relative"; 
   // this method is only called for IE applets, so we do IE CSS alignment
   span.style.top=height - ascent; 
   span.appendChild(applet);

   return span;
};

// Internal method used with getAppletCode for reformatting MathML as
// an attribute by escaping all quote characters
EquationFormatter.prototype.formatAsParam = function() {
   // first, shrink the equation down 
   this.ns = false;
   this.dec = false;
   this.pp = false;
   mml = this.styleEqn();

   // now, turn this into a parameter
   mml = mml.replace(/\"/g, "'");
   return mml
};

// Create an internal singleton global instance.  Note, since the
// EquationFormatter constructor called instance methods, we must
// put this call after the methods are defined.
var formatter = new EquationFormatter(); 


