<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<title>kpi mapping form</title>
<body class="section-5"/>
<content tag="heading">KPI MAPPING LIST</content>

<form:form commandName="kpiMapping" method="post" action="form.htm" name = "frmSample" onSubmit = "return ValidateForm()">
	<form:hidden path="configId" />
	
    <table class="simple2">
        <tr>
            <td width="300"><b>Tên báo cáo<font color = "red">(*)</font></b></td>
            <td>
            	<form:input path="reportName" maxlength="500"/>
            	<br/><font color="red"><form:errors path="reportName"/></font>
            </td>
        </tr>
        <tr>
            <td><b>Tên cột báo cáo<font color = "red">(*)</font></b></td>
            <td>
            	<form:input path="reportNameColumn" maxlength="100"/>
            	<br/><font color="red"><form:errors path="reportNameColumn"/></font>
            </td>
        </tr>
        <tr>
            <td><b>Công thức</b></td>
            <td>
            	<form:input path="formula" maxlength="700"/>
            </td>
        </tr>
        <tr>
            <td><b>Nhà sản xuất</b></td><td>
           			<form:select path="vendor">
						<form:option value="ALCATEL"/>
			            <form:option value="HUAWEI"/>
			            <form:option value="ERICSSON"/>
			            <form:option value="NOKIA SIEMEN"/>
			        </form:select></td>
        </tr>
        <tr>
            <td><b>Tên bảng<font color = "red">(*)</font></b></td>
            <td><form:input path="tableName" maxlength="30"/>
            <br/><font color="red"><form:errors path="tableName"/></font>
            </td>
        </tr>
        <tr>
            <td><b>Tên cột<font color = "red">(*)</font></b></td>
            <td><form:input path="tableColumn" maxlength="30"/>
            <br/><font color="red"><form:errors path="tableColumn"/></font>
            </td>
        </tr>
        <tr>
            <td><b>Công thức CSDL</b></td>
            <td><form:input path="databaseFormula" maxlength="700"/></td>
        </tr>
        <tr>
            <td><b>Trạng thái</b></td>
            <td>
            	<form:select path="status">
                    <form:option value="Y" label="Đang sử dụng"/>
                    <form:option value="N" label="Không sử dụng"/>
                </form:select>
			</td>
        </tr>
         <tr>
            <td><b>Ghi chú</b></td>
            <td><form:textarea path="description"  maxlength="300"/></td>
        </tr>
        <tr>
            <td><b>Mạng</b></td><td>
           			 <form:select path="network">
								<form:option value="2G"/>
			                    <form:option value="3G"/>
			                    <form:option value="CORE"/>
			              </form:select></td> 
        </tr>
         <tr>
            <td><b>Phiên bản</b></td>
            <td><form:input path="version" maxlength="10"/></td>
        </tr>
        <tr>
            <td></td>
            <td>
                <input type="submit" class="button" name="save" value="Lưu lại"/>
                <input type="button" value="Hủy bỏ" onClick='window.location="list.htm"' class = "button">
            </td>
        </tr>
    </table>
    
</form:form>

<script language = "Javascript">
function xl(){
	var sub = document.getElementById("submit");
	sub.focus();
} 
// Declaring valid date character, minimum year and maximum year
var dtCh= "/";
var minYear=1900;
var maxYear=2100;

function isInteger(s){
	var i;
    for (i = 0; i < s.length; i++){   
        // Check that current character is number.
        var c = s.charAt(i);
        if (((c < "0") || (c > "9"))) return false;
    }
    // All characters are numbers.
    return true;
}

function stripCharsInBag(s, bag){
	var i;
    var returnString = "";
    // Search through string's characters one by one.
    // If character is not in bag, append to returnString.
    for (i = 0; i < s.length; i++){   
        var c = s.charAt(i);
        if (bag.indexOf(c) == -1) returnString += c;
    }
    return returnString;
}

function daysInFebruary (year){
	// February has 29 days in any year evenly divisible by four,
    // EXCEPT for centurial years which are not also divisible by 400.
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
	var dt=document.frmSample.updateDate;
	if (isDate(dt.value)==false){
		dt.focus();
		return false;
	}
    return true;
 }

</script>
<script type="text/javascript">
	$(function() {
		 $( "#updateDate" ).datepicker({
			dateFormat: "dd/mm/yy",
			showOn: "button",
			buttonImage: "${pageContext.request.contextPath}/images/calendar.png",
			buttonImageOnly: true
		});
	});
</script>
