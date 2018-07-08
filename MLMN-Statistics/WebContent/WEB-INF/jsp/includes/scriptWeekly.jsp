<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/calendar.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/calendar_en.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/calendar_setup.js"></script>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/calendar-blue.css" />
<script  type="text/javascript">
${highlight}
</script>
<script type="text/javascript">
function CalcKeyCode(aChar) {
	  var character = aChar.substring(0,1);
	  var code = aChar.charCodeAt(0);
	  return code;
	}

	
	function checkNumber(val) {
		var strPass = val.value;
		var strLength = strPass.length;
		for(var i=1; i<= strLength;i++){
		  var lchar = val.value.charAt((strLength) - i);
		  var cCode = CalcKeyCode(lchar);

		  if (cCode < 48 || cCode > 57 ) {
		    var myNumber = val.value.substring(0, (strLength)-i);
		    val.value = myNumber;
		  }
		}
		  var sub = document.getElementById("submit");
			sub.focus();
		  return false;
		}
	
		var minYear=1900;
		var maxYear=2100;
		var minWeek=1;
		var maxWeek=53;
		function isDate(dtStr){
			var year = dtStr;
			if (year.length != 4 || year==0 || year<minYear || year>maxYear || year != year.getfullyear()){
				alert("Vui lòng nhập năm trong khoảng từ "+minYear+" đến "+maxYear);
				return false;
			}
			return true;
		}
		function isDate2(dtStr){
			var week = dtStr;
			if (week==0 || week<minWeek || week>maxWeek){
				alert("Vui lòng nhập tuần trong khoảng từ "+minWeek+" đến "+maxWeek);
				return false;
			}
			return true;
		}

		function ValidateForm(){
			
			var dt=document.frmSample.startYear;
			var dt1=document.frmSample.endYear;
			var wk=document.frmSample.startWeek;
			var wk1=document.frmSample.endWeek;
			
			if (isDate2(wk.value)==false){
				wk.focus();
				return false;
			}
			if (isDate2(wk1.value)==false){
				wk1.focus();
				return false;
			}
			if (isDate(dt.value)==false){
				dt.focus();
				return false;
			}
			if (isDate(dt1.value)==false){
				dt1.focus();
				return false;
			}
			
		    return true;
		 }
</script>	
<script type="text/javascript">
	Calendar.setup({
	    inputField		:	"startWeek",	// id of the input field
	    ifFormat		:	"%W",   	// format of the input field
	    button			:   "chooseStartWeek",  	// trigger for the calendar (button ID)
	    singleClick		:   false					// double-click mode
	});
	Calendar.setup({
	    inputField		:	"endWeek",	// id of the input field
	    ifFormat		:	"%W",   	// format of the input field
	    button			:   "chooseEndWeek",  	// trigger for the calendar (button ID)
	    singleClick		:   false					// double-click mode
	});
</script>