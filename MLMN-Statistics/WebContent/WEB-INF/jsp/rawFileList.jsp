<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<title>raw file list</title>
<content tag="heading">	DANH SÁCH DỮ LIỆU THÔ</content>

<form method="get" action="rawFile.htm" name = "frmSample" onSubmit = "return ValidateForm()">
	<table style="width:100%;" class="form">
		<tr>
		    <td>Từ ngày</td><td><input value="${startDate}" name="startDate" id="startDate" size="10" maxlength="10"></td>
	        <td>Tới ngày</td><td><input value="${endDate}" name="endDate" id="endDate" size="10" maxlength="10"></td>
            <td>Trạng thái File Convert</td>
            <td><select name="convertFlag" id="convertFlag" style="width: 192px;">
	            				<c:choose>
								<c:when test="${convertFlag == 0}">
									<option value="2">Tất cả</option>
									<option value="-1">Convert bị lỗi</option>
									<option value="0" selected="selected">Chưa convert</option>
									<option value="1" >Đã convert</option>			
								</c:when>
								<c:when test="${convertFlag == -1}">
									<option value="-1" selected="selected">Convert bị lỗi</option>
									<option value="0">Chưa convert</option>
									<option value="1" >Đã convert</option>
									<option value="2">Tất cả</option>
								</c:when>
								<c:when test="${convertFlag == 1}">
									<option value="2">Tất cả</option>
									<option value="-1">Convert bị lỗi</option>
									<option value="0" >Chưa convert</option>
									<option value="1" selected="selected">Đã convert</option>					
								</c:when>
								<c:otherwise>
									<option value="2" selected="selected">Tất cả</option>
									<option value="-1">Convert bị lỗi</option>
									<option value="0" >Chưa convert</option>
									<option value="1" >Đã convert</option>									
								</c:otherwise>
								</c:choose>
							</select></td> 
            <td>Tên bảng thô</td><td><input value="${rawTable}" name="rawTable" id="rawTable" size="16" maxlength="30"/></td>         
        </tr>
        <tr>
        	<td>Tên file</td><td><input value="${fileName}" name="fileName" id="fileName" size="16" maxlength="300"/></td>
        	<td>Tên node</td><td><input value="${nodeName}" name="nodeName" id="nodeName" size="16" maxlength="200"/></td>
			<td>Trạng thái File Import</td>
			<td><select name="importFlag" id="importFlag" style="width: 192px;">
			            				<c:choose>
										<c:when test="${importFlag == -1}">
											<option value="2">Tất cả</option>
											<option value="0">Chưa import</option>
											<option value="1" >Đã import</option>
											<option value="-1" selected="selected" >Import lỗi</option>
										</c:when>
										<c:when test="${importFlag == 0}">
											<option value="2">Tất cả</option>
											<option value="0" selected="selected">Chưa import</option>
											<option value="1" >Đã import</option>
											<option value="-1">Import lỗi</option>
										</c:when>
										<c:when test="${importFlag ==1}">
											<option value="2">Tất cả</option>
											<option value="0" >Chưa import</option>
											<option value="1" selected="selected">Đã import</option>
											<option value="-1" >Import lỗi</option>											
										</c:when>
										<c:otherwise>
											<option value="2" selected="selected">Tất cả</option>
											<option value="0" >Chưa import</option>
											<option value="1" >Đã import</option>
											<option value="-1" >Import lỗi</option>											
										</c:otherwise>
										</c:choose>
									</select></td> 
          <td></td><td><input type="submit" class="button" name="filter" id="submit" value="Tìm kiếm"/></td>
        </tr>		
	</table>
</form>
<br/>
<div  style="overflow: auto;">
<display:table name="${rawFileList}" id="rawFileList" class="simple3" export="true" requestURI="" pagesize="100" sort="external" defaultsort="1" size="${totalSize}">
    <display:column property="day" format="{0,date,dd/MM/yyyy}" titleKey="NGÀY SINH DỮ LIỆU"/>        
    <display:column property="hour" titleKey="GIỜ SINH DỮ LIỆU"/>
    <display:column property="fileId" titleKey="FILE ID"/>
    <display:column property="fileName" titleKey="TÊN FILE"/>        
    <display:column property="statusFileConvert" titleKey="TRẠNG THÁI CONVERT FILE"/>
    <display:column property="statusFileImport" titleKey="TRẠNG THÁI IMPORT FILE"/>
    <display:column property="nodeName" titleKey="TÊN NODE"/>
    <display:column property="rawTable" titleKey="TÊN BẢNG DỮ LIỆU THÔ"/>
    <display:column property="originalName" titleKey="TÊN GỐC CỦA FILE"/>
    <display:setProperty name="export.csv.filename" value="Rawfile.csv"/>
	<display:setProperty name="export.excel.filename" value="Rawfile.xls"/>
	<display:setProperty name="export.xml.filename" value="Rawfile.xml"/>
</display:table>
</div>

<script type = "text/javascript">
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

</script>
<script type="text/javascript">
$(function() {
	$( "#startDate" ).datepicker({
		dateFormat: "dd/mm/yy",
		showOn: "button",
		buttonImage: "${pageContext.request.contextPath}/images/calendar.png",
		buttonImageOnly: true
	});
	$( "#endDate" ).datepicker({
		dateFormat: "dd/mm/yy",
		showOn: "button",
		buttonImage: "${pageContext.request.contextPath}/images/calendar.png",
		buttonImageOnly: true
	});
	});
</script>