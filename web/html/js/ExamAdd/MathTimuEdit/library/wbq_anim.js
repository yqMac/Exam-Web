/*
  WebEQ Solutions Library Animator Objects
  Copyright (c) 2003, Design Science, Inc., all rights reserved
*/


// Constructs an animator object which employs a parametric path
function PathAnimator() {
   this.init();
}

// Internal method initializing all member variables
PathAnimator.prototype.init = function() {
   // time params
   this.tmin=0;
   this.tmax=100;
   this.curT = this.tmin;

   // parameter range (user) params (mins and maxes of functions)
   // NOTE: null values mean "auto-fit"
   this.uxmin = null;
   this.uxmax = null;
   this.uymin = null;
   this.uymax = null;

   // speed/smoothness params
   this.delta = .1;    // how far each point is from the previous one
   this.delay = 100;   // length of time between calculations

   // formulas
   this.xmml;
   this.ymml;
   
   // viewport (screen) params (min and max on screen)
   this.sxmin;
   this.sxmax;
   this.symin;
   this.symax;
   this.autosize = true;

   // storage points to minimize calculations (private)
   this.ax=null;
   this.ay=null;

   // storage of starting point so we don't have to recalculate it.
   this.startX;        // starting x-coord
   this.startY;        // starting y-coord

   // overall params
   this.valid = false; // valid after mml is added & required params set
   this.active=false;  // active when 'play'ed

   // dynamic layers
   this.dynlayer = null; // check null value to do default 
   this.divName = null;  // stores divName if given

   this.vaporText;     // if a vapor trail is desired, store the text
   this.vaporNum;      // "

};

// MathML accessor method directly
PathAnimator.prototype.setMathML = function(mml_x, mml_y) { 
   this.xmml = mml_x; this.ymml = mml_y; this.valid=true; 
   // zero out any previous scaling
   if (this.autosize) {
	this.uxmin = null;
	this.uxmax = null;
	this.uymin = null;
	this.uymax = null;
	this.ax = null;
	this.ay = null;
    }
};
PathAnimator.prototype.getXMathML = function() { return this.xmml; };
PathAnimator.prototype.getYMathML = function() { return this.ymml; };

// Accessor methods for setting Equation objects
PathAnimator.prototype.setEquations = function(eqn_x, eqn_y) { 
  this.xmml = eqn_x.mml; 
  this.ymml = eqn_y.mml; 
  this.valid=true; 
};
PathAnimator.prototype.getXEquation = function() { return new Equation(this.xmml); };
PathAnimator.prototype.getYEquation = function() { return new Equation(this.ymml); };

// Further accessor methods
PathAnimator.prototype.setDelta = function(delta) { 
  this.delta = delta; 
};
PathAnimator.prototype.getDelta = function() { return this.delta; };
PathAnimator.prototype.setDelay = function(delay) { 
  this.delay = Math.abs(delay); 
};
PathAnimator.prototype.getDelay = function() { return this.delay; };

// Set the time parameter range
PathAnimator.prototype.setParameterRange = function(tmin, tmax) {
   this.tmin = tmin;
   this.tmax = tmax;
};

// Set the location of the div when the page is loaded
PathAnimator.prototype.setStartPoint = function(x, y) {
   this.startX = x;
   this.startY = y;
};

// Specify location on the screen
PathAnimator.prototype.setViewPort = function(sxmin, symin, sxmax, symax) {
   this.sxmin = sxmin;
   this.sxmax = sxmax;
   this.symin = symin;
   this.symax = symax;
   this.autosize = true;
   this.ax=null;
   this.ay=null;
};

// Specify range of function and location on screen
PathAnimator.prototype.mapViewPort = function(uxmin, uymin, uxmax, uymax, sxmin, symin, sxmax, symax) {
   this.uxmin = uxmin;
   this.uxmax = uxmax;
   this.uymin = uymin;
   this.uymax = uymax;
   this.sxmin = sxmin;
   this.sxmax = sxmax;
   this.symin = symin;
   this.symax = symax;
   this.autosize = false;
   this.ax=null;
   this.ay=null;
};

// Creates an animate-able <div> tag from an object already on the screen
PathAnimator.prototype.setDiv = function(divName) {
   this.divName = divName;
};

// Creates a series of animate-able <div> tags from arbitrary strings of HTML
PathAnimator.prototype.setVaporTrail = function(num, text) {
   this.vaporText = text;
   this.vaporNum = num;
};

// Internal method which initializes the evaluator object
PathAnimator.prototype.evalInit = function() {
   if (typeof document.provider == "undefined") { 
      showError(this, "please use plotter.write somewhere in the page"); 
      return;
   }

   // create 2 separate evaluator services.  One for each independent variable
   this.xevaluator = document.provider.getService("evaluator");
   if (!this.xevaluator) { 
     showError(this, "unable to initialize evaluator"); this.valid = false; return; 
   }
   this.yevaluator = document.provider.getService("evaluator");
   if (!this.yevaluator) { 
     showError(this, "unable to initialize evaluator"); this.valid = false; return; 
   }

   this.xevaluator.setMathML(this.xmml);
   this.yevaluator.setMathML(this.ymml);
   // ensure valid MathML statements have been provided
   this.valid = (this.xevaluator.canEvaluate() && this.yevaluator.canEvaluate());

   // report errors
   if (!this.valid) {
      var msg = "Can't animate - " +  this.xevaluator.getError();
      if (this.yevaluator.getError() != "" && this.xevaluator.getError() != "") {
	msg += ", " + this.yevaluator.getError();
      }
      else {
        msg += this.yevaluator.getError();
      }
      alert(msg);
   }	
};

// Internal method to evaluate at a specific point
PathAnimator.prototype.evalAt = function(pt, evaluator) {
   if (!evaluator) {
      this.valid=false;
      this.active=false;
      return;
   }
   return evaluator.evalAt(pt); 
};

// Move the object along its path
PathAnimator.prototype.play = function(name) {
   if (!this.valid) { showError(this, "MathML must be added before running animator"); return  }

   // the only way dynlayer can be null at this point is if a divName was set
   if (this.dynlayer == null) {
      this.dynlayer = new Array(new DynLayer(this.divName));
   }

   if (!this.active) {  // don't run it if already running.
      this.evalInit();  // set up animation to be ready to start

      if (!this.valid) { return; } // don't even bother trying to run animation if we can't

      // if we're supposed to auto-size the function, run it through it's paces
      if (this.uxmin == null || this.uxmax == null || this.uymin == null || this.uymax == null) {
         this.ascertainRange();
      }

      if (this.sxmin == null || this.sxmax == null || this.symin == null || this.symax == null) {
         this.ascertainScreenSize();
      }
      this.active = true;

      // set time parameter to start
      this.curT = this.tmin;

      // get first points
      var x = this.mapToScreenX(this.evalAt(this.tmin, this.xevaluator)); 
      var y = this.mapToScreenY(this.evalAt(this.tmin, this.yevaluator));

      this.dynlayer[0].moveTo(x,y);

      // store starting points for 'reset' function
      this.startX = x;
      this.startY = y;

      if (this.valid) {
         eval(name+".slide(\""+name+"\")");
      } else {
         showError(this, "unable to plot equation, please check");
      }
   }
};

// Internal method mapping user x to screen x
PathAnimator.prototype.mapToScreenX = function(x) {
   if (this.ax == null) {
      this.ax = (this.sxmax-this.sxmin) / (this.uxmax-this.uxmin);
   }

   return this.ax * (x-this.uxmin) + this.sxmin;
};

// Internal method mapping user y to screen y
PathAnimator.prototype.mapToScreenY = function(y) {
   if (this.ay == null) {
      this.ay = (this.symax-this.symin) / (this.uymax-this.uymin);
   }
   return this.ay * (y-this.uymin) + this.symin;
};

// Internal method used to set the user space max & min params
PathAnimator.prototype.ascertainRange = function() {

   // loop through all values in the range in order to figure out the global min and max
   var curVal;
   for (var t=this.tmin; t<this.tmax; t+=this.delta) {

      curVal = this.evalAt(t, this.xevaluator);
      if (this.uxmin == null || curVal < this.uxmin) {
         this.uxmin = curVal;
      }
      if (this.uxmax == null || curVal > this.uxmax) {
         this.uxmax = curVal;
      }

      curVal = this.evalAt(t, this.yevaluator);
      if (this.uymin == null || curVal < this.uymin) {
         this.uymin = curVal;
      }
      if (this.uymax == null || curVal > this.uymax) {
         this.uymax = curVal;
      }
   }
};

// Internal method used to figure out the x,y values of the current window
PathAnimator.prototype.ascertainScreenSize = function() {
   var offsetX = (is.ns)? window.pageXOffset : window.document.body.scrollLeft;
   var offsetY = (is.ns)? window.pageYOffset : window.document.body.scrollTop;
   var winW = (is.ns)? window.innerWidth : window.document.body.offsetWidth-27; 
   var winH = (is.ns)? window.innerHeight : window.document.body.offsetHeight-22;
   this.sxmin = offsetX;
   this.sxmax = winW+offsetX;
   this.symin = offsetY;
   this.symax = winH+offsetY;
};

// Internal method that slides the object a little bit at a time over and over 
// until the animation is complete 
PathAnimator.prototype.slide = function(str) {
   if (this.active && (this.curT+this.delta)<=this.tmax) {
      this.curT += this.delta;
      var x = this.mapToScreenX(this.evalAt(this.curT, this.xevaluator));
      var y = this.mapToScreenY(this.evalAt(this.curT, this.yevaluator));

      // shift trail so that each successive point moves to where the previous one was
      for (var i=this.dynlayer.length-1; i>0; i--) {
         this.dynlayer[i].moveTo(this.dynlayer[i-1].x, this.dynlayer[i-1].y);
      }
      // move the leading point to the new location
      this.dynlayer[0].moveTo(x,y);

        setTimeout(str+".slide(\""+str+"\")", this.delay);
   }
   else {
      this.stop(str, this.dynlayer.length);
   }
};

// Stop the animation.
PathAnimator.prototype.stop = function() {
   // this doesn't work quite right, since it happens all at once with no delay.  
   this.curT=this.tmin;
   if (!this.active || this.dynlayer == null) { return; }
   // continue moving all 'remaining' layers on top of the stopped ones
   this.active = false;

   for (var j=0; j<this.dynlayer.length; j++) {
      for (var i=this.dynlayer.length-1; i>j; i--) {
         this.dynlayer[i].moveTo(this.dynlayer[i-1].x, this.dynlayer[i-1].y);
      }
   }
};

// Reset the animation.
PathAnimator.prototype.reset = function() {
   this.stop();
   for (var j=0; j<this.dynlayer.length; j++) {
      this.dynlayer[j].moveTo(this.startX, this.startY);
   }
};

// Write out a specific number of <div> elements for a 'vapor trail'
PathAnimator.prototype.write = function() {
   // Note: the name is hard-coded here
   var divName = "moveDiv"; 

   // add the service provider first (to maximize time to initialize)
   if (typeof document.provider == "undefined") {
      writeServiceProvider();
   }

   // add all the divs to the document.
   if (this.divName == null) { // a pre-created <div> wasn't specified
      this.dynlayer = [];

      if (this.vaporText) { // create the vapor trail
         for (var i=0; i<this.vaporNum; i++) {
            document.write("<div id=\""+divName+i+"\" style=\"position:absolute; left:"+this.startX+"px; top:"+this.startY+"px; width:5px; height:5px\">"+this.vaporText+"</div>");
         }
         // turn all of the <div> elements into movable layers
         for (var i=0; i<this.vaporNum; i++) {
            this.dynlayer[i] = new DynLayer(divName+i);
         }
      } else {
         // no div or vaporText was set, so default to a single "o"
         document.write("<div id=\""+divName+"0\" style=\"position:absolute; left:"+this.startX+"px; top:"+this.startY+"px; width:5px; height:5px\">o</div>");
         this.dynlayer[0] = new DynLayer(divName+"0");
      }
   }
};



// ------------------------  FunctionAnimator ------------------------------

FunctionAnimator.prototype = new PathAnimator(); 
FunctionAnimator.prototype.constructor = FunctionAnimator; 
FunctionAnimator.superclass = PathAnimator.prototype;

// create an animation along a path
function FunctionAnimator() {
   this.init();
}

// Override base class functionality
FunctionAnimator.prototype.setMathML = function(mml_y) { 
   FunctionAnimator.superclass.setMathML.call(this, "<math><mi>t</mi></math>", mml_y);
};
FunctionAnimator.prototype.getMathML = function() { return this.ymml; };

FunctionAnimator.prototype.setEquation = function(eqn_x) { 
   FunctionAnimator.superclass.setMathML.call(this, "<math><mi>t</mi></math>", eqn_x.mml);
};
FunctionAnimator.prototype.getEquation = function() { return new Equation(this.ymml); };
