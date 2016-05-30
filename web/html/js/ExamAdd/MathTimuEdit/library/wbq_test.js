/*
  WebEQ Solutions Library Test and Quiz Logic Module
  Copyright (c) 2003, Design Science, Inc., all rights reserved
*/

// Constructs a MathTest
function MathTest(){
   this.init();
}

// Internal method called by all question constructors to initialize parameters
MathTest.prototype.init = function() {
   // scoring parameters
   this.right=0;              // number of question answered correctly
   this.wrong=0;              // number of question answered incorrectly
   this.whichwrong = []; // question numbers denoting which are wrong
   this.pts = 0;              // number of points student scored
   this.totalPts = 0;         // number of total possible points
   this.questions;            // list of all QA objects in the test
   this.q_items;              // list of all scoreable items in the test
};

// sets the array of questions for the test
MathTest.prototype.setQuestions = function(questions) {
   // in order to handle matching groups, we ask each question 
   // to add it's own subparts to the list of scoreable items
   this.questions = questions;

   this.q_items = [];
   for (var i=0; i<questions.length; i++) {
      questions[i].addItems(this.q_items);
   }
};

// Resets all answers in the form to their initial state
MathTest.prototype.resetForm = function() {
   // in general, nothing is needed to reset a form
   this.reset();
   for (var i=0; i<this.q_items.length; i++) {
      this.q_items[i].reset();
   }
};

// Internal method to reset the scoring variables to their initial state
MathTest.prototype.reset = function() {
   this.percent=0;
   this.wrong=0;
   this.right=0;
   this.whichwrong = []; // NOTE: splice doesn't seem to work in IE 6.0
   this.pts=0;
   this.totalPts=0;
};

// score the test
MathTest.prototype.score = function(){
   // go through each question and check it
   for (var i = 0; i < this.q_items.length; i++){ 
      var pts = this.q_items[i].check();

      if (pts > 0) { // if it got points, it's right
         this.right++;
      } 
      // if it wasn't answered, isn't right, but isn't wrong
      else if (this.q_items[i].isAnswered()) { 
         this.wrong++;
         this.whichwrong.push(this.q_items[i].questionNum);
      }

      this.pts += pts;
      this.totalPts += this.q_items[i].getMaxPoints();
   }
};

// Internal method that returns the grade this student would have gotten
MathTest.prototype.getGradeStr = function(){
   var percent = Math.round(((this.pts*100)/this.totalPts));
   if (percent >= 90) return "Great Score  --  A!";
   else if (percent >= 80) return "Good Score  --  B.";
   else if (percent >= 70) return "Average Score  --  C.";
   else return "Keep Trying!";
};

// Internal method stating which answers were wrong
MathTest.prototype.showWrong = function(){
   if (this.wrong > 0) { msg.document.writeln("<b> You scored incorrectly on the following questions:</b><p>"); }
   msg.document.write("<b>");
   for (var i = 0; i < this.whichwrong.length; i++) {
      msg.document.write(this.whichwrong[i]);
      if (i<this.whichwrong.length-1) {
         msg.document.write(", ");
      }
   }
   msg.document.write("</b>");
};

// Opens a results window and scores the test
MathTest.prototype.showResults = function () {
   var bgColor="black", textColor="white", titleColor="#ff99ff";
   var title="Your Test Results";

   this.score();  // first, score the test

   msg=open("","TestResults","toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=yes,resizable=yes,copyhistory=no,width=400,height=400");
   msg.document.clear();
   msg.document.writeln("<html>");
   msg.document.writeln("<head><title>"+title+"</title></head>");
   msg.document.writeln("<body  onBlur=\"self.setTimeout('window.close()','5000')\" bgcolor='"+bgColor+"' text='"+textColor+"'>");
   msg.document.writeln("<br/>");
   msg.document.writeln("<center><h3><font color='"+titleColor+"'>"+title+"</font></h3></center>");
   msg.document.writeln("<center><hr width='75%'/></center>");
   msg.document.writeln("<blockquote>");
    
   var grade = this.getGradeStr(); // returns grading criteria
   
   msg.document.writeln("<p>");
   msg.document.writeln("<b> <font color='"+titleColor+"'>"+grade+"</font> </b>");
   msg.document.writeln("</p>");

   msg.document.writeln("<B>You scored " + Math.round(((this.pts*100)/this.totalPts)) + "% on this test.</B>");  
   msg.document.write("<B>You answered a total of " + this.right + " items correctly out of " + this.q_items.length+", " );
   msg.document.write("giving you a total of " + this.pts + " points out of a possible " + this.totalPts + ".</B><p>");
   this.showWrong();
   msg.document.writeln("</blockquote>");
   this.reset();
   msg.document.writeln("<center><hr width='75%'/></center>");
   msg.document.writeln("<p><form>");
   msg.document.writeln("<center><input type='submit' value='Done' onclick='window.close();'/></center>");
   msg.document.writeln("</form></p>");
   msg.document.writeln("</body>");
   msg.document.writeln("</html>");
   msg.document.close();
};

MathTest.prototype.writeControls = function(testName) {
  document.write("  <table cellspacing=\"10\"><tr><td>");
  document.write("     <input onclick=\""+testName+".showResults();\" type=\"submit\" value=\"Evaluate\">");
  document.write("  </td><td>");
  document.write("     <input type=\"reset\" value=\"Restart\" onclick=\""+testName+".resetForm(); return true;\">"); 
  document.write("  </td></tr></table>");
 };


// Writes the entire working test into the web page, including scoring
// and reset buttons  
MathTest.prototype.write = function(testName, asTable) {
   document.write("<form onsubmit=\"return false;\">");
    if (asTable) {
      document.write("     <table cellspacing=\"10\">");
   }
   // show the QA items 
   for (var i=0; i<this.questions.length; i++) {
      this.questions[i].write(asTable);
   }
   if (asTable) {
      document.write ("    </table>");
   }
   document.write("<center>");
   this.writeControls(testName);
   document.write("</center>");
   document.write("</form>");
};


//--------------------- Question/Answer functions --------------------
//
// Question/Answer base class

QA.answerID = 1; // create unique answer control names
QA.answer_string = "wbq_answer_";

// Create a generic question/answer object
function QA(){
   this.init();
}

// private method used to initialize all variables
QA.prototype.init = function() {
   this.questionNum;    // text - the number of the question (e.g. 1 or 2a)
   this.questionText;   // text - the question 
   this.correctAnswer;  // text - the answer
   this.numPoints;      // point value of question

   // document object in string form (used to find & update answer)
   this.answerObjStr = QA.answer_string + QA.answerID++;
   this.valid=false;   // is the question initialized
};

//  single question general information
QA.prototype.setData = function(num, qText, answer, weight) {
   this.questionNum = num;
   this.questionText = qText;
   this.correctAnswer = answer;
   this.weight = weight;
   this.answered = false;
};

// returns max points a question could have
QA.prototype.getMaxPoints = function() { return this.weight; };
// returns whether the question was answered
QA.prototype.isAnswered = function () { return this.answered; };

QA.prototype.getNumAndText = function(isWrapped) {
   var text = "";
   if (isWrapped) {
      text  += "<tr><td valign=\"top\"><b>"+this.questionNum+"</b> "+this.questionText; + "</td>\n";
   } 
   else {
      text = "<p><b>"+this.questionNum+"</b> " + this.questionText+"\n";
      text += "<br/>\n";
   }
   return text;
};

//----------------------  EquationQA --------------------------------
// An EquationQA uses the WebEQ Input Control to accept an equation
// as an answer.  The reader's answer is compared to the test author's 
// using the "functional" equality check of the WebEQ Comparator.

EquationQA.prototype = new QA(); 
EquationQA.prototype.constructor = EquationQA; 
EquationQA.superclass = QA.prototype;

// EquationQA constructor
function EquationQA() {
   this.init();
   this.bgcolor;                 // background color of applet
   this.fgcolor ;                // foreground color of applet
   this.fontSize;                // size of text in applet
   this.toolbar;                 // custom toolbar, if any
   this.height = 150;            // default applet height
   this.width = 400;             // default applet width
}

// Internal method adding the item to the test's item list
EquationQA.prototype.addItems= function(itemArray) {
   itemArray.push(this);
};

// Internal method.  Resets the InputControl
EquationQA.prototype.reset = function () {
   var obj = eval("document."+this.answerObjStr);
   obj.setMathML(eqnEmptyString); 
   this.answered=false;
};

// checks the answer.
EquationQA.prototype.check = function () {
   var applt = document.getElementById(this.answerObjStr);
   if (!applt) { showError(this, "no question with name "+ this.answerObjStr); return; }

   var compare = document.provider.getService("comparator");
   if (!compare) { showError(this, "unable to initialize comparator"); return; }
   compare.setMathML(applt.getMathML(), this.correctAnswer);

   if (applt.getPackedMathML() != eqnEmptyString) {  
      this.answered = true;
      var evalResult = compare.compareAsFunction();

      if (evalResult){
         return this.weight;
      }
   }
   return 0;
};

// sets the size of the input control applet
EquationQA.prototype.setSize = function(width, height) {
   this.height=height;
   this.width=width;
};
// sets the foreground color
EquationQA.prototype.setForegroundColor = function(color) {
this.fgcolor = color; };
// returns the foreground color
EquationQA.prototype.getForegroundColor = function() { return this.fgcolor; };
// sets the background color
EquationQA.prototype.setBackgroundColor = function(color) { this.bgcolor = color; };
// returns the background color
EquationQA.prototype.getBackgroundColor = function() { return this.bgcolor; };
// sets the textsize
EquationQA.prototype.setFontSize = function(size) { this.fontSize = size; };
// returns the font size
EquationQA.prototype.getFontSize = function() { return this.fontSize; };
// sets a custom toolbar config string
EquationQA.prototype.setToolbar = function(toolStr) { this.toolbar = toolStr; };
// returns any custom toolbar config string
EquationQA.prototype.getToolbar = function() { return this.toolbar; };


// writes out all pieces needed to create a long answer
EquationQA.prototype.write = function(isWrapped) {
   var text = "";
   if (typeof document.provider == "undefined") {
      writeServiceProvider();
   }
   
   text  += this.getNumAndText(isWrapped);
   if (isWrapped) { text += "<td>\n"; } 

   text += '<applet archive="WebEQApplet.jar" codebase="'+javaCodebase+'" ';
   text += 'code="'+inputControl+'" height="' + this.height + '" ';
   text += 'width="' + this.width + '" id="'+this.answerObjStr+'">';
   text += '<param name="useslibrary" value="WebEQApplet">';
   text += '<param name="useslibrarycodebase" value="WebEQApplet.cab">';
   text += '<param name="useslibraryversion" value="3,6,0,0">';

   if (this.fontSize) {
      text += "    <param name=\"size\" value=\""+this.fontSize+"\">\n";
   }
   if (this.fgcolor) {
      text += "    <param name=\"foreground\" value=\""+this.fgcolor+"\">\n";
   }
   if (this.bgcolor) {
      text += "    <param name=\"background\" value=\""+this.bgcolor+"\">\n";
   }
   if (this.toolbar) {
      text += "    <param name=\"toolbar\" value=\""+this.toolbar+"\">\n";
   }
   text += "  </applet>\n";

   if (isWrapped) {
      text += "</td></tr>\n";
   } else {
      text += "</p>\n";
   }
   document.write(text);
};

//-------------------   Short Answer QA Object   -------------------------
// A short answer is a simple text input widget.  It allows users to enter in
// anything that can be typed on their keyboard, such as numbers or variables.

ShortAnswerQA.prototype = new QA(); 
ShortAnswerQA.prototype.constructor = ShortAnswerQA; 
ShortAnswerQA.superclass = QA.prototype;

// create a short answer.
function ShortAnswerQA() {
   this.init();
   this.size = 5;
}

// Internal method adding the item to the test's item list
ShortAnswerQA.prototype.addItems= function(itemArray) {
   itemArray.push(this);
};

// Internal method for clearing the form field
ShortAnswerQA.prototype.reset = function () {
   this.answered=false;
};


// checks the answer.
ShortAnswerQA.prototype.check = function () {
   var curObj = document.getElementById(this.answerObjStr);
   if (!curObj) { 
       showError(this, "no question with name" + this.answerObjStr);
      return; 
   }

   if (curObj.value != "") {
      this.answered = true;
   }

   if(curObj.value == this.correctAnswer){
      return this.weight;
   } else  {
      return 0;
   }
};

// set the width of the input text box
ShortAnswerQA.prototype.setWidth = function(size) { this.size = size; };

// writes out all pieces needed to create a short answer
ShortAnswerQA.prototype.write = function(isWrapped) {
   var text = "";
   text  += this.getNumAndText(isWrapped);
   if (isWrapped) {
      text += "<td>\n";
      text += "  <input type=\"text\" id=\""+this.answerObjStr+"\" size=\""+this.size+"\"/>\n";
      text += "</td></tr>\n";
   } else {
      text += "<input type=\"text\" id=\""+this.answerObjStr+"\" size=\""+this.size+"\"/></p>\n"
   }
   document.write(text);
};


// --------------------   MultipleChoiceQA Object -----------------------------
// A MultipleChoiceQA uses radio buttons to distiguish between a set number of choices
// that a user can choose from.  Only one answer can be chosen at a time from this
// list of alternatives.

MultipleChoiceQA.prototype = new QA();
MultipleChoiceQA.prototype.constructor = MultipleChoiceQA;
MultipleChoiceQA.superclass = QA.prototype;

// Constructs a multiple choice answer type
function MultipleChoiceQA() {
   this.init();
}

// set the choices that the user can choose from 
MultipleChoiceQA.prototype.setChoices = function (choices) {
   this.choices = choices;
};

// Internal method adding the item to the test's item list
MultipleChoiceQA.prototype.addItems= function(itemArray) {
   itemArray.push(this);
};

// Internal method for resetting the form
MultipleChoiceQA.prototype.reset = function () {
   this.answered=false;
};

// checks the answer to see if user chose the right one.
MultipleChoiceQA.prototype.check = function () {
   // NOTE: radio buttons must be accessed by name, not ID
   var curObj = document.getElementsByName(this.answerObjStr);
   if (!curObj) { showError(this, "no question with name" + this.answerObjStr); return; }

   for (var j=0; j<curObj.length; j++) {
      if (curObj[j].checked) {
         this.answered=true;
         if ((j+1) == this.correctAnswer){
            return this.weight;
         }
      }
   }
   return 0;
};

// writes out all pieces needed to create a multiple choice answer
MultipleChoiceQA.prototype.write = function(isWrapped) {
   var text = "";
   text += this.getNumAndText(isWrapped);
   if (isWrapped) {
      text += "<td>\n";
      text += "  <ol type=\"a\">\n";
      for (var i=0; i<this.choices.length; i++) {
         text += "    <li><input type=\"radio\" name=\""+this.answerObjStr+"\">"+this.choices[i]+"</li>\n";
      }
      text += "  </ol>\n";
      text += "</td></tr>\n";
   } else {
      text += "  <ol type=\"a\">\n";
      for (var i=0; i<this.choices.length; i++) {
         text += "    <li><input type=\"radio\" name=\""+this.answerObjStr+"\">"+this.choices[i]+"</li>\n";
      }
      text += "  </ol></p>\n";
   }
   document.write(text);
};


//------------------   MatchingQA Objects ---------------------------------
// Matching questions require two objects, the MatchingQA object for
// the question as a whole, and MatchingItem objects for the
// individual items

MatchingItem.prototype = new QA();
MatchingItem.prototype.constructor = MatchingItem;
MatchingItem.superclass = QA.prototype;

// Constructs a MatchingItem for a MatchingQA
function MatchingItem() {
   this.init();
   this.group=0; // set by the 'owner' MatchingQA object
}

// Internal method called by MatchingQA.setChoices()
MatchingItem.prototype.setGroup = function (group) {
   this.group = group;
};

// Internal method to reset the question
MatchingItem.prototype.reset = function () {
   this.answered=false;
};

// checks the answer for a specific matching question
MatchingItem.prototype.check = function () {
   var curObj = document.getElementById(this.answerObjStr);
   if (!curObj) { showError(this, "no question with name" + this.answerObjStr); return; }

    if (curObj.selectedIndex != 0) {
      this.answered=true;
    }

   if (curObj.selectedIndex == this.correctAnswer) {
      return this.weight;
   } else {
      return 0;
   }
};

// A MatchingQA objects contains all of individual MatchingItems.
// NOTE: this does not inherit from the QA base class.

MatchingQA.id=1;
MatchingQA.choice_string = "wbq_choice_";

// Constructs a MatchingQA object
function MatchingQA(name) { // need the variable name to 'eval' the markChosen method
   this.questionNum;        // number associated with the group as a whole
   this.questionText;       // text introducing the matching group as a whole
   this.items;              // collection of the matching items
   this.choiceObjStrs;      // set of strings stating which choices are part of this group
   this.choiceTexts;        // overall texts outlining the individual choices
   this.init(name);
}

// private method used to associate a name
MatchingQA.prototype.init = function(name) {
   this.objName = name;
};

MatchingQA.prototype.getNumAndText = QA.prototype.getNumAndText;

// set the data for the matching question as a whole.  This is the
// number and text introducing the group as a whole 
MatchingQA.prototype.setData = function(num, text) {
   this.questionNum = num;
   this.questionText = text;
};

// Set an array containing the itemss of the matching group
MatchingQA.prototype.setItems = function(items) {
   this.choiceObjStrs = [];
   this.items = items;
   for (var i=0; i<items.length; i++) {
      this.items[i].setGroup(this);
      // for every matching item, there is 1 choice 
      this.choiceObjStrs.push(MatchingQA.choice_string + MatchingQA.id++);
   }
};

// Set an array containing the correct answers to all of the items
MatchingQA.prototype.setValues = function(choices) {
   this.choiceTexts = choices;
};

// Internal  method adding the items to the test's item list
MatchingQA.prototype.addItems = function(itemArray) {
   for (var j=0; j<this.items.length; j++) {
      itemArray.push(this.items[j]);
   }
};

// Internal method used to change the checkbox when a drop down box is selected
MatchingQA.prototype.markChosen = function() {
   // turn off all checkboxes
   for(n=0; n < this.choiceObjStrs.length; n++)
      document.getElementById(this.choiceObjStrs[n]).checked = false;
   // turn on appropriate checkboxes
   for(n=0; n < this.items.length; n++) {
      choice = document.getElementById(this.items[n].answerObjStr).selectedIndex;
      if (choice != 0) {
         document.getElementById(this.choiceObjStrs[choice-1]).checked = true;
      }
   }
};

// Write out the group of matching questions and answers into their 2 columns.
MatchingQA.prototype.write = function(isWrapped) {
   // first, construct the select options
   var optionString = this.createOptions();
   var text = this.getNumAndText(isWrapped);
   if (isWrapped) {
      text += "<td>\n";
      text += this.getOptionsTable(optionString);
      text += "</td></tr>\n";
   } else {
      text += this.getOptionsTable(optionString);
      text += "</p>\n";
   }
   document.write(text);
};

// internal method for showing options
MatchingQA.prototype.getOptionsTable = function(optionString) {
   var text = "";
      text += "  <table cellpadding=\"5\"><tr><td>\n";

   // lefthand column
      for (var i=0; i<this.items.length; i++) {
         text += "    <p><nobr>\n";
         text += "    <select id=\""+this.items[i].answerObjStr+"\" onchange=\""+this.objName+".markChosen()\">\n";
         text += optionString;
         text += "    </select>\n";
         text += this.items[i].questionText;
         text += "    </nobr></p>\n";
      }
      text += "</td> <td width=\"50px\"></td><td>\n";

      text += "<table cellpadding=\"1\" cellspacing=\"0\" border=\"0\">\n";

   // righthand column
      for (var i=0; i<this.choiceObjStrs.length; i++) {
         if (is.ie) {
            text += "    <tr><td>"+(i+1)+". <input type=\"checkbox\" id=\""+this.choiceObjStrs[i]+"\" disabled=\"disabled\"/>"+this.choiceTexts[i]+"</td></tr>\n";
         } else {
            text += "    <tr><td>"+(i+1)+". <input type=\"checkbox\" id=\""+this.choiceObjStrs[i]+"\"/>"+this.choiceTexts[i]+"</td></tr>\n";
         }
 
      }
      text += "  </table>\n";

      text += "</td></tr></table>\n";
   return text;
};


// Internal method creating the options for inclusion in the choices div
MatchingQA.prototype.createOptions = function() {
   var text = "      <option value=\"\"></option>\n";
   for (var i=0; i<this.choiceObjStrs.length; i++) {
      text += "      <option value=\""+this.choiceObjStrs[i]+"\">"+(i+1)+"</option>\n";
   }
   return text;
};

