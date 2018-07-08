
﻿function getTheme(theme) {
	
  
    if (theme == null) {
        theme = 'classic';
    }
   
	var url = "/VMSC6-Alarm-Feedback/js/jQWidgets/jqwidgets/styles/jqx." + theme + '.css';
			   
	 if (document.createStyleSheet != undefined) {
        document.createStyleSheet(url);
    }
    else $(document).find('body').append('<link rel="stylesheet" href="' + url + '" media="screen" />');
    
  
    return theme;
};


function getDemoTheme(theme) {
	
	  
    if (theme == null) {
        theme = 'classic';
    }
   
	var url = "/VMSC6-Alarm-Feedback/js/jQWidgets/jqwidgets/styles/jqx." + theme + '.css';
			   
	 if (document.createStyleSheet != undefined) {
        document.createStyleSheet(url);
    }
    else $(document).find('head').append('<link rel="stylesheet" href="' + url + '" media="screen" />');
    
  
    return theme;
};