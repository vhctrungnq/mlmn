<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<script  type="text/javascript">
${highlight}
</script>
<script type="text/javascript">
		function xl(){
			var sub = document.getElementById("submit");
			sub.focus();
			
		} 
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
			function isDate(dtStr){
				var year = dtStr;
				if (year.length != 4 || year==0 || year<minYear || year>maxYear || year != year.getfullyear()){
					alert("Vui lòng nhập năm trong khoảng từ "+minYear+" đến "+maxYear);
					return false;
				}
				return true;
			}

function ValidateForm(){
	var dt=document.frmSample.startYear;
	var dt1=document.frmSample.endYear;
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