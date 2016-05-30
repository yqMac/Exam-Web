// webeqInstall.js -- WebEQ ViewerControl installation script 
// 
// copyright (c) Design Science, Inc. 2001
function webeqInstall() {

  // determine browser type
  var doit=false;
  if (window.navigator.appVersion.indexOf("MSIE") != -1) {
    doit = checkIE();
  }
  else {
    doit = checkNetscape();
  }
  if (doit) {

    // ***NOTE***

    // You will need to change the path to the DeploymentKit/index file to
    // reflect the actual locations on your server

    window.open("DeploymentKit/index.html", "wbq", "width=380,height=420,resizable=1"); 
  }
}

function checkIE() {
  // Version info is verified by the object tag. Evidently
  // there is no way to check this in script in advance.
  var applet = document.tester;
  var p = document.tester.use_parser;
  if ( typeof p == "undefined" ) {
    return true;
  }
  else {
    return false;
  }

}

function checkNetscape() {
  // first check if it is there...
  var needToUpdate = true;
  var applet = document.tester;
  if ( typeof applet != "undefined" ) {
    needToUpdate = false;
  }
  var trigger = netscape.softupdate.Trigger;
  var version = trigger.GetVersionInfo("java/download/dsi/webeq");
  var minversion = new netscape.softupdate.VersionInfo(3,0,0,2);
  if (version != null) {
    if (version.compareTo(minversion) < 0) {
      // old version is registered
      return true;
    }
    else {
      // there is current version info, but if the database 
      // is out of sync the code may not really be there
      return needToUpdate;
    }
  }
  else {
    // since version is null, its never been installed
    return true;
  }

}
