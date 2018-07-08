
/* Kiem tra dinh dang thang, nam*/
	var minYear=1900;
	var maxYear=2100;
	var minWeek=1;
	var maxWeek=54;
	var dtCh= "/";
	function isDate1(dtStr){
		var year = dtStr;
		if (year.length != 4 || year==0 || year<minYear || year>maxYear || year != year.getfullyear()){
			alert("Vui lòng nhập năm trong khoảng từ "+minYear+" đến "+maxYear);
			return false;
		}
		return true;
	}
	
	function ValidateFormYear(){
		var dt=document.frmSample.startYear;
		var dt1=document.frmSample.endYear;
		if (isDate1(dt.value)==false){
			dt.focus();
			return false;
		}
		if (isDate1(dt1.value)==false){
			dt1.focus();
			return false;
		}
	    return true;
	 }

	/*Kiem tra dinh dang dd/mm/yyyy*/	
	function isInteger(s){
			var i;
		    for (i = 0; i < s.length; i++){   
		        var c = s.charAt(i);
		        if (((c < "0") || (c > "9"))) return false;
		    }
		    return true;
		}
	
	function stripCharsInBag(s, bag){
			var i;
		    var returnString = "";
		    for (i = 0; i < s.length; i++){   
		        var c = s.charAt(i);
		        if (bag.indexOf(c) == -1) returnString += c;
		    }
		    return returnString;
		}
	
	function daysInFebruary (year){
		    return (((year % 4 == 0) && ( (!(year % 100 == 0)) || (year % 400 == 0))) ? 29 : 28 );
		}
	function DaysArray(n) {
			for (var i = 1; i <= n; i++) {
				this[i] = 31;
				if (i==4 || i==6 || i==9 || i==11) {this[i] = 30;}
				if (i==2) {this[i] = 29;}
		   } 
		   return this;
		}
	
	function isDate(dtStr){
			var daysInMonth = DaysArray(12);
			var pos1=dtStr.indexOf(dtCh);
			var pos2=dtStr.indexOf(dtCh,pos1+1);
			var strDay=dtStr.substring(0,pos1);
			var strMonth=dtStr.substring(pos1+1,pos2);
			var strYear=dtStr.substring(pos2+1);
			strYr=strYear;
			if (strDay.charAt(0)=="0" && strDay.length>1) strDay=strDay.substring(1);
			if (strMonth.charAt(0)=="0" && strMonth.length>1) strMonth=strMonth.substring(1);
			for (var i = 1; i <= 3; i++) {
				if (strYr.charAt(0)=="0" && strYr.length>1) strYr=strYr.substring(1);
			}
			month=parseInt(strMonth);
			day=parseInt(strDay);
			year=parseInt(strYr);
			if (pos1==-1 || pos2==-1){
				alert("Không đúng định dạng : dd/mm/yyyy");
				return false;
			}
			if (strMonth.length<1 || month<1 || month>12){
				alert("Vui lòng nhập lại tháng");
				return false;
			}
			if (strDay.length<1 || day<1 || day>31 || (month==2 && day>daysInFebruary(year)) || day > daysInMonth[month]){
				alert("Vui lòng nhập lại ngày");
				return false;
			}
			if (strYear.length != 4 || year==0 || year<minYear || year>maxYear){
				alert("Vui lòng nhập năm trong khoảng từ "+minYear+" đến "+maxYear);
				return false;
			}
			if (dtStr.indexOf(dtCh,pos2+1)!=-1 || isInteger(stripCharsInBag(dtStr, dtCh))==false){
				alert("Nhập sai định dạng");
				return false;
			}
			return true;
		}
	
	function ValidateForm(){
			var dt=document.frmSample.startDate;
			var dt1=document.frmSample.endDate;
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
	/*Kiem tra dinh dang tuan, nam*/
	function isDate2(dtStr){
		var week = dtStr;
		if (week==0 || week<minWeek || week>maxWeek){
			alert("Vui lòng nhập tuần trong khoảng từ "+minWeek+" đến "+maxWeek);
			return false;
		}
		return true;
	}
	function checkValidateForm()
	{
		var vWeek=document.frmSample.week;
		var vYear=document.frmSample.year;
		if (isDate2(vWeek.value)==false){
			vWeek.focus();
			return false;
		}
		if (isDate1(vYear.value)==false){
			vYear.focus();
			return false;
		}
		return true;
	}
	function ValidateFormWeek(){
		
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
		if (isDate1(dt.value)==false){
			dt.focus();
			return false;
		}
		if (isDate1(dt1.value)==false){
			dt1.focus();
			return false;
		}
		
	    return true;
	 }
