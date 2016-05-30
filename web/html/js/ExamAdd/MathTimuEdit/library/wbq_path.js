/*
  WebEQ Solutions Library Step-through Logic Module
  Copyright (c) 2003, Design Science, Inc., all rights reserved
*/

// ------------------  Single-path Step-through Objects ----------------
// Single-path step-throughs require two objects, a SinglePath object
// and the individual SinglePathSteps contained within it.

// Constructs a single-path step-through step
function SinglePathStep() {
   this.content;           // what is shown for this step
   this.explainText;       // help text for this step
   this.id;                // unique identifier for this step.
}

// sets the content for this step
SinglePathStep.prototype.setContent = function(content) { 
	// handle case where they passed an Equation object
   if (typeof content == "object" && content.toString) { 
      this.content = content.toString();
   } else {
      this.content = content; 
   }
};
// Returns the content for this step
SinglePathStep.prototype.getContent = function() { return this.content; };

// Get and set explanations
SinglePathStep.prototype.setHelp = function(text) { this.explainText = text; };
SinglePathStep.prototype.getHelp = function() { return this.explainText; };

// Internal methods for setting step id's
SinglePathStep.prototype.setID = function(id) { this.id = id; };
SinglePathStep.prototype.getID = function() { return this.content; };


// ------------------------- A SinglePath() ---------------------------

function SinglePath() {
   this.curStep = 0;          // the step to show
   this.steps;                // list of steps
   this.style = "v";          // style of steps: 'vertical' or 'horizontal'
   this.separator = " = ";    // add an equals sign between steps by default
   this.helpLoc = "automatic"; // help location is 'automatic', 'manual' or 'popup'
   this.valid = false;        // if this object is initialized
}

// Sets whether steps are presented vertically or horizontally.
SinglePath.prototype.setLayout = function(direction, helpLocation) {
   // set style (default to 'vertical')
   if (direction == "horizontal") {
      this.style = "h";
   } else {
      this.style = "v";
   }

   if (helpLocation == "automatic") {
      this.helpLoc = "automatic";
   }
   else if (helpLocation == "manual") { 
      this.helpLoc = "manual";
   }
   else {
      this.helpLoc = "popup";
   }
};

SinglePath.prototype.setSeparator = function(character) {
   this.separator = character;
};

// Accessor methods for data and  style parameter
SinglePath.prototype.getStepDirection = function() { 
   return (this.style=="v" ? "vertical" : "horizontal");  
};
SinglePath.prototype.getSeparator = function() { 
   return this.separator; 
};
SinglePath.prototype.getHelpLocation = function() { 
   return this.helpLoc;
};

// Internal method which states what step we are currently on.
SinglePath.prototype.setCurrentStep = function(num) { this.curStep = num; };

// Set the collection of steps for this step-through
SinglePath.prototype.setSteps = function(steps) {
   // an array of SinglePathSteps should be passed in
   this.steps = [];
   for (var i=0; i<steps.length; i++) {
      if (typeof steps[i] == "string") {
         // get the actual object
         this.steps[i] = document.getElementById(steps[i]);
      } else if (typeof steps[i] == "object") {
         this.steps[i] = steps[i];
      }
   }
   this.valid=true;
};

// Reset the path back to the first setp
SinglePath.prototype.reset = function() { 
   this.curStep = 0; 
   document.getElementById(this.steps[0].id).style.display = 'block'; 
   for(var i=1; i<this.steps.length; i++) {
     document.getElementById(this.steps[i].id).style.display = 'none';
   }
   if (this.style=="h" || this.helpLoc == "manual") {
    var helpdiv = document.getElementById("help");
    if (helpdiv != null) { helpdiv.innerHTML = this.steps[0].getHelp(); }
   }
};

// Uncovers the next step in the example
SinglePath.prototype.showNextStep = function() {
   if (!this.valid) { showError(this, "must call setSteps before you can show a step"); }

   // if all steps are shown, don't do anything
   if (this.curStep >= this.steps.length-1) return;

   if (this.style=="h") {
      document.getElementById(this.steps[this.curStep].id).style.display = 'none';
   }
   document.getElementById(this.steps[++this.curStep].id).style.display = 'block';

   if ((this.helpLoc=="automatic") && (this.style=="h")) { 
      this.updateHelp(); 
   }

};

// Writes out all of the steps into the webpage.  This should be called somewhere
// in the body of the document in order for the step-by-step example to work. 
SinglePath.prototype.write = function() {
   if (this.style=="v") {
      if (this.separator && this.separator.length>0) {
         document.write(this.writeMultiColumn());
      } else {
         document.write(this.writeColumn());
         this.reset(); // display the first step
      }
   } else {
      document.write(this.writeRow());
   }
};


// Internal method, called by write().  Writes out the next step  
// below the previous ones.
SinglePath.prototype.writeColumn = function() {
   var text = "";
   for (var i=0; i<this.steps.length; i++) {
      this.steps[i].id = "mom_step"+i;
      text += "<div id=\""+this.steps[i].id+"\"style=\" display:none\">\n"; 
      text += "<table cellpadding=\"0\" cellspacing=\"0\"><tr><td>\n";
      text += this.steps[i].content+"\n";
      text += "</td><td width=\"20px\"></td><td>\n";
      if (this.helpLoc == "automatic") {
         text += this.steps[i].explainText+"\n";
      }
      text += "</td></tr></table>\n";
      text += "</div>\n";
   }
   return text;
};

// Internal method, called by write().  Writes out the next
// step in an equals aligned vertical display
SinglePath.prototype.writeMultiColumn = function() {
   // print out step 1 (located differently than other steps)
   this.steps[0].id = "mom_step0";
   var text = "<table><tr><td valign=\"top\">";
   text += "<div id=\""+this.steps[0].id+"\">\n"; 
   text += this.steps[0].content+"\n";
   text += "</div>\n";
   text += "</td><td>\n";

   for (var i=1; i<this.steps.length; i++) {
      this.steps[i].id = "mom_step"+i;
      text += "<div id=\""+this.steps[i].id+"\"style=\" display:none\">\n"; 
      text += "<table cellpadding=\"0\" cellspacing=\"0\"><tr><td>\n";
      if (i>0) {
         text += this.separator;
      }
      text += this.steps[i].content+"\n";
      text += "</td><td width=\"20px\"></td><td>\n";
     if (this.helpLoc == "automatic") {
         text += this.steps[i].explainText+"\n";
     }
      text += "</td></tr></table>\n";
      text += "</div>\n";
   }
   text += "</td></tr></table>\n";
   return text;
};


// Internal method, called by write().  Writes out the next step horizontally 
// after the previous ones.  
SinglePath.prototype.writeRow = function() {
   var text="";
   // print all step groups (step 1, step 1 = step 2 ...) invisibly
   for (var i=0; i<this.steps.length; i++) {
      this.steps[i].id = "mom_step"+i;
      text += "<div id=\""+this.steps[i].id+"\""; 
      if (i>0) {
         text += "style=\"display:none\">\n";
      } else {
         text += ">\n";
      }
      for (var j=0; j<=i; j++) {
         text += this.steps[j].content+"\n";
         if (j<i && this.separator && this.separator.length > 0) {
            text += this.separator;
         }
      }
      text += "</div>\n";
   }
   if (this.helpLoc == "automatic") {
      text += "<br><div id=\"help\">" + this.steps[this.curStep].getHelp() + "</div>\n";
   }

   return text;
};

// Writes out the introductory explanation into the webpage
SinglePath.prototype.writeInitialHelp = function() { 
   if (this.helpLoc != "manual") {
      alert("Warning: use of writeInitialHelp() requires manual positioning of help text.");
   }
   else {
      document.write("<span id=\"help\">" + this.steps[0].getHelp() + "</span>\n"); 
   }
};

// updates the help text to show the text for the current step.
SinglePath.prototype.updateHelp = function() { 
   if (this.helpLoc == "popup") { return; }
   var helpdiv = document.getElementById("help");
   if (helpdiv) {
      helpdiv.innerHTML = this.steps[this.curStep].getHelp(); 
   }
};

// Authors may choose to reveal help via a button, rather than printing the 
// help message directly on the page
SinglePath.prototype.showPopupHelp = function() {
   var bgColor="white", textColor="black", titleColor="#0099ff";
   var title="Hint";

   msg=window.open("","TestResults","toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=yes,resizable=yes,copyhistory=no,width=400,height=400");
   msg.document.clear();
   msg.document.writeln("<html>");
   msg.document.writeln("<head><title>"+title+"</title></head>");
   msg.document.writeln("<body onBlur=\"self.setTimeout('window.close()','4500')\" bgcolor='"+bgColor+"' text='"+textColor+"'>");
   msg.document.writeln("<br/>");
   msg.document.writeln("<center><h3><font color='"+titleColor+"'>"+title+"</font></h3></center>");
   msg.document.writeln("<center><hr width='75%'/></center>");
   msg.document.writeln("<blockquote>");
   msg.document.writeln(this.steps[this.curStep].getHelp());
   msg.document.writeln("</blockquote>");
   msg.document.writeln("<center><hr width='75%'/></center>");
   msg.document.writeln("<p><form>");
   msg.document.writeln("<center><input type='submit' value='Done' onclick='window.close();'/></center>");
   msg.document.writeln("</form></p>");
   msg.document.writeln("</body>");
   msg.document.writeln("</html>");
   msg.document.close()
};


// --------------------------- MultiPathStep Object ----------------------
// Multi-path step-throughs require two objects, a MultiPath object
// and the individual MultiPathSteps contained within it.

// Global singleton instance of the MultiPath object so that steps can access it.
var MultiPathName; 

// global step id counter
var MultiPathStepId = 0;

// Constructs single MultiPathStep
function MultiPathStep() {
   this.id = ++MultiPathStepId;   // unique id of the step
   this.content;                  // content of step, may include multiple equations
   this.nextSteps = [];  // the next steps user could go to
   this.choices = [];    // list of choices that could be done
   this.helpText;                 // help text message
}

// set the content of the step
MultiPathStep.prototype.setContent = function(content) { 
   // handle case where they passed an Equation object
   if ((typeof content == "object") && (content.toString)) { 
      this.content = content.toString();
   } else {
      this.content = content; 
   }
};

// Return the content of the step
MultiPathStep.prototype.getContent = function() { return this.content; };

// Internal methods allowing user to get/set the id of the step
MultiPathStep.prototype.setID = function(id) { this.id=id; };
MultiPathStep.prototype.getID = function() { return this.id; };

// Set the steps that may follow this one
MultiPathStep.prototype.setNextStep = function(nextstep, choice) { 
   this.nextSteps.push(nextstep);
   this.choices.push(choice);
};

// Get the collection of next steps
MultiPathStep.prototype.getNextStep = function(num) { 
   if (num < this.nextSteps.length) return nextSteps[num]; 
   else return null;
};

// Set the help associated with the step
MultiPathStep.prototype.setHelp = function(helpstr) { this.helpText = helpstr; };

// Show the help associated with the step
MultiPathStep.prototype.getHelp = function() { return this.helpText; };

// Sets both content and help simultaneously
MultiPathStep.prototype.setData = function(content, help) { 
   this.content=content; 
   this.helpText = help;
};

// Show a specific choice associated with this step
MultiPathStep.prototype.showChoice = function(i) {
   if (num < this.choices.length) return choices[num]; 
   else return null;
};

// Internal method used to create the choices form for the 
// MultiPath's GetChoicesText() method 
MultiPathStep.prototype.showChoices = function() {
   var text ="";
   for (var i=0; i<this.nextSteps.length; i++) {
      text += "<input type=\"radio\" name=\"next\" value=\""+this.nextSteps[i].id+"\"/>"+this.choices[i]+"<br/>\n";
   }

   var spaces = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
   text += "<br />" + spaces;
   if (this.nextSteps.length > 0) {
      text += "<input type=\"button\" value=\"next step\" onclick=\""+MultiPathName+".showNextStep();\"/>\n";
   } else {
      text += "<b>You've reached an endpoint</b><br /><br />\n";
   }
   text += spaces;
   text += "<input type=\"button\" value=\" back up \" onclick=\""+MultiPathName+".backUpStep();\"/><br />";
   text += spaces;
   text += "<input type=\"button\" value=\"show hint\" onclick=\""+MultiPathName+".showPopupHelp();\"/>";
   text += spaces;
   text += "<input type=\"button\" value=\"start over\" onclick=\""+MultiPathName+".restart();\"/>";
   text += spaces + "<br />&nbsp;";
   return text;
};

// Constructs a MultiPath object
function MultiPath(nameStr) {
   this.startStep;               // starting step
   this.curStep = 0;             // the step to show
   this.hideLast = false;        // whether the last step stays visible
   this.prevSteps = []; // previous steps the user went through (to allow backup);
   MultiPathName = nameStr;      // the global variable name
}

// Set the first step in the tree
MultiPath.prototype.setStartStep = function(step) { 
   this.startStep = step; this.curStep = this.startStep; 
};

// Sets whether to hide the last step when displaying the next
MultiPath.prototype.hideLastStep = function(shouldHideLast) { 
   this.hideLast = shouldHideLast; 
};

// Writes out the entire tree into the document.  
MultiPath.prototype.write = function() {
   document.write(this.getContentText(this.startStep));
};

// Internal method which sets the step that is shown to the user
MultiPath.prototype.setCurrentStep = function(num) { this.curStep = num; };

// Internal method to create the div for the actual content
MultiPath.prototype.getContentText = function(curStep) {
   var text="";
   text += "<div id=\"mom_step"+curStep.id+"\""; 
   if (curStep != this.startStep) {
      text += "style=\"display:none\">\n";
   } else {
      text += ">\n";
   }
   text +=curStep.content+"\n";
   text += "</div>\n";

   // recursively create the children
   // NOTE: if steps are created, but are not part of the tree, 
	// a div will NOT be created for them
   for (var i=0; i<curStep.nextSteps.length; i++) {
      text += this.getContentText(curStep.nextSteps[i]);
   }

   return text;
};


// Private method - produce the choices in a div, so that the text can be replaced with new choices
MultiPath.prototype.getChoicesText = function() {
   var text = "<form id=\"wbq_mpform\">\n";
   text += "<table border=\"1\" cellpadding=\"3\"><tr><td>\n";
   text += "<div id=\"choices\">";
   text += this.curStep.showChoices();
   text += "</div>";

   text += "</td></tr></table>\n";
   text += "</form>";
   return text;
};
MultiPath.prototype.writeInitialChoices = function() { document.write(this.getChoicesText()); };


// Private method - produce the choices in a div, so that the text can be replaced with new choices
// NOTE: help may not be shown on the page at all, in which case, this <div> won't exist
MultiPath.prototype.getHelpText = function() {
   var text = "";
   text += "<table border=\"1\"><tr><td>\n";
   text += "<div id=\"help\">\n";
   text += this.curStep.getHelp();
   text += "</div>";
   text += "</td></tr></table>\n";
   return text;   
};

// writes the initial statement for the user of the page.  
MultiPath.prototype.writeInitialHelp = function() { document.write(this.getHelpText()); };

// show the next step based upon the choice the user made
MultiPath.prototype.showNextStep = function() {
   // if all steps are shown, don't do anything
   if (this.curStep.nextSteps.length == 0)
      return;

   var nextchoice = document.getElementById("wbq_mpform").next; // get the user input & show this

   // Netscape treats a radiogroup with one item as a radiobutton
   // so we treat this case separately
   if (typeof nextchoice.length == "undefined") {
      this.executeNextStep(nextchoice);
   }
   else {
   for (i =0; i<nextchoice.length; i++) {
      if (nextchoice[i].checked) {
	  this.executeNextStep(nextchoice[i]);
	}
     }
   }
};

// private method called from showNextStep
MultiPath.prototype.executeNextStep = function(choice) {
   if (this.hideLast) { // if only 1 step is shown at a time, hide the previous step
      document.getElementById("mom_step" + this.curStep.id).style.display = 'none';
      document.getElementById("mom_step" + choice.value).style.display = 'block';
   } else {
      // need to make sure things are in the right order.
      // pull out the new div and insert it after the current step
      var oldstep = document.getElementById("mom_step" + this.curStep.id);
      var newstep = document.getElementById("mom_step" + choice.value);
      oldstep.parentNode.insertBefore(newstep, oldstep.nextSibling);
      newstep.style.display = 'block';
   }
   this.prevSteps.push(this.curStep); // store the choice for undoing later
   this.curStep = eval("step" + choice.value);

   // show the new set of choices (& optionally, show the new help)
   var ch = document.getElementById("choices");
   ch.innerHTML = this.curStep.showChoices();
   var he = document.getElementById("help");
   if (he) { // help may not always be on the page
      he.innerHTML = this.curStep.getHelp();
   }
};

// restart the tree from step 1
MultiPath.prototype.restart = function() {
   if (!this.hideLast) {
      for (var i=0; i<this.prevSteps.length; i++) {
         document.getElementById("mom_step"+this.prevSteps[i].id).style.display = 'none';
      }
   }
   document.getElementById("mom_step"+this.curStep.id).style.display = 'none';
   this.curStep = this.startStep;
   document.getElementById("mom_step"+this.curStep.id).style.display = 'block';

   // show the new set of choices (& optionally, show the new help)
   var ch = document.getElementById("choices");
   ch.innerHTML = this.curStep.showChoices();
   var he = document.getElementById("help");
   if (he) { // help may not always be on the page
      he.innerHTML = this.curStep.getHelp();
   }
   this.prevSteps = [];
};


// return the state of the tree to what it was previously
MultiPath.prototype.backUpStep = function() {
   // if first step is shown, don't do anything
   if (this.curStep == this.startStep)  { return; }

   var prevStep = this.prevSteps.pop();

   document.getElementById("mom_step" + this.curStep.id).style.display = 'none';
   if(this.hideLast)
      document.getElementById("mom_step" + prevStep.id).style.display = 'block';

   var ch = document.getElementById("choices");
   ch.innerHTML = prevStep.showChoices();
   var he = document.getElementById("help");
   if (he) { // help may not always be on the page
      he.innerHTML = prevStep.getHelp();
   }

   this.checkChoice();
   this.curStep = prevStep;
};


// Private method - set the correct choice when backing up a step
MultiPath.prototype.checkChoice = function() {
   var nextchoice = document.getElementById("wbq_mpform").next;
   for (i =0; i<nextchoice.length; i++)
      if(nextchoice[i].value == this.curStep.id) nextchoice[i].checked =true;
};


// display help text in a popup window
MultiPath.prototype.showPopupHelp = function() {
   var bgColor="white", textColor="black", titleColor="#0099ff";
   var title="Hint";

   msg=window.open("","TestResults","toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=yes,resizable=yes,copyhistory=no,width=400,height=400");
   msg.document.clear();
   msg.document.writeln("<html>");
   msg.document.writeln("<head><title>"+title+"</title></head>");
   msg.document.writeln("<body onBlur=\"self.setTimeout('window.close()','4500')\" bgcolor='"+bgColor+"' text='"+textColor+"'>");
   msg.document.writeln("<br/>");
   msg.document.writeln("<center><h3><font color='"+titleColor+"'>"+title+"</font></h3></center>");
   msg.document.writeln("<center><hr width='75%'/></center>");
   msg.document.writeln("<blockquote>");
   msg.document.writeln(this.curStep.getHelp());
   msg.document.writeln("</blockquote>");
   msg.document.writeln("<center><hr width='75%'/></center>");
   msg.document.writeln("<p><form>");
   msg.document.writeln("<center><input type='submit' value='Done' onclick='window.close();'/></center>");
   msg.document.writeln("</form></p>");
   msg.document.writeln("</body>");
   msg.document.writeln("</html>");
   msg.document.close()
};
