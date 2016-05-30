// Dynamic Layer Object
// sophisticated layer/element targeting and animation object which provides the core 
// functionality needed in most DHTML applications 
// 19990604

// Copyright (C) 1999 Dan Steinman
// Distributed under the terms of the GNU Library General Public License
// Available at http://www.dansteinman.com/dynapi/

function DynLayer(id,nestref,frame) {
   if (!is.ns6 && !DynLayer.set && !frame) DynLayerInit();
   this.frame = frame || self;
   if (is.ns) {
      if (is.ns4) {
         if (!frame) {
            if (!nestref) var nestref = DynLayer.nestRefArray[id];
            if (!DynLayerTest(id,nestref)) return;
            this.css = (nestref)? eval("document."+nestref+".document."+id) : document.layers[id];
         }
         else this.css = (nestref)? eval("frame.document."+nestref+".document."+id) : frame.document.layers[id];
         this.elm = this.event = this.css;
         this.doc = this.css.document;
      }
      else if (is.ns6) {
         this.elm = document.getElementById(id);
         this.css = this.elm.style;
         this.doc = document;
      }
      this.x = this.css.left;
      this.y = this.css.top;
      this.w = this.css.clip.width;
      this.h = this.css.clip.height;
   }
   else if (is.ie) {
      this.elm = this.event = this.frame.document.all[id];
      this.css = this.frame.document.all[id].style;
      this.doc = document;
      this.x = this.elm.offsetLeft;
      this.y = this.elm.offsetTop;
      this.w = (is.ie4)? this.css.pixelWidth : this.elm.offsetWidth;
      this.h = (is.ie4)? this.css.pixelHeight : this.elm.offsetHeight;
   }
   this.id = id;
   this.nestref = nestref;
   this.obj = id + "DynLayer";
   eval(this.obj + "=this");
}

DynLayer.prototype.moveTo = DynLayerMoveTo;
DynLayer.prototype.moveBy = DynLayerMoveBy;
DynLayer.prototype.show = DynLayerShow;
DynLayer.prototype.hide = DynLayerHide;
DynLayerTest = new Function('return true');

// DynLayerInit Function
function DynLayerInit(nestref) {
   if (!DynLayer.set) DynLayer.set = true;
   if (is.ns) {
      if (nestref) ref = eval('document.' + nestref + '.document');
      else {nestref = ''; ref = document;}
      for (var i=0; i<ref.layers.length; i++) {
         var divname = ref.layers[i].name;
         DynLayer.nestRefArray[divname] = nestref;
         var index = divname.indexOf("div");
         if (index > 0) {
            eval(divname.substr(0,index)+' = new DynLayer("'+divname+'","'+nestref+'")');
         }
         if (ref.layers[i].document.layers.length > 0) {
            DynLayer.refArray[DynLayer.refArray.length] = (nestref=='')? ref.layers[i].name : nestref+'.document.'+ref.layers[i].name;
         }
      }
      if (DynLayer.refArray.i < DynLayer.refArray.length) {
         DynLayerInit(DynLayer.refArray[DynLayer.refArray.i++]);
      }
   }
   else if (is.ie) {
      for (var i=0; i<document.all.tags("div").length; i++) {
         var divname = document.all.tags("div")[i].id;
         var index = divname.indexOf("div");
         if (index > 0) {
            eval(divname.substr(0,index)+' = new DynLayer("'+divname+'")');
         }
      }
   }
   return true;
}
function DynLayerMoveTo(x,y) {
   if (x!=null) {
      this.x = x;
      if (is.ns) this.css.left = this.x;
      else this.css.pixelLeft = this.x;
   }
   if (y!=null) {
      this.y = y;
      if (is.ns) this.css.top = this.y;
      else this.css.pixelTop = this.y;
   }
}

function DynLayerMoveBy(x,y) {
   this.moveTo(this.x+x,this.y+y);
}

function DynLayerShow() {
   this.css.visibility = (is.ns4)? "show" : "visible";
}

function DynLayerHide() {
   this.css.visibility = (is.ns4)? "hide" : "hidden";
}


DynLayer.nestRefArray = [];
DynLayer.refArray = [];
DynLayer.refArray.i = 0;
DynLayer.set = false;
