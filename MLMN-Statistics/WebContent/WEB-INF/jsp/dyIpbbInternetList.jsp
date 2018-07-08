<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

	<title>IP BACKBONE</title>
	<content tag="heading">${title} DAILY REPORT</content>
<ul class="ui-tabs-nav">
	<li class=""><a href="${pageContext.request.contextPath}/report/core/ip-backbone/hr_delay_internet.htm?direction=${direction}&link=${link}&startDate=${startDate}&endDate=${endDate}"><span>Báo cáo giờ</span></a></li>
 	<li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/report/core/ip-backbone/dy_delay_internet.htm?direction=${direction}&startDate=${startDate}&endDate=${endDate}"><span>Báo cáo ngày</span></a></li> 
</ul>
<br>
	<form method="get" action="dy_delay_internet.htm">
	
		<table style="width:100%;" class="form">
			<tr>
				<td align="left">
					DIRECTION 
			  		<select name="direction" id="direction" onchange="xl()">
			  			<option value="">Tất cả</option>
			              <c:forEach var="items" items="${directionList}">
				              <c:choose>
				                <c:when test="${items.direction == direction}">
				                    <option value="${items.direction}" selected="selected">${items.direction}</option>
				                </c:when>
				                <c:otherwise>
				                    <option value="${items.direction}">${items.direction}</option>
				                </c:otherwise>
				              </c:choose>
						    </c:forEach>
			        </select>
	               &nbsp;&nbsp;LINK 
			  		<select name="link" id="link" onchange="xl()">
			  			<option value="">Tất cả</option>
			              <c:forEach var="items" items="${linkList}">
				              <c:choose>
				                <c:when test="${items.link == link}">
				                    <option value="${items.link}" selected="selected">${items.link}</option>
				                </c:when>
				                <c:otherwise>
				                    <option value="${items.link}">${items.link}</option>
				                </c:otherwise>
				              </c:choose>
						    </c:forEach>
			        </select>
			        &nbsp;&nbsp;Từ ngày <input value="${startDate}" name="startDate" id="startDate" size="10" maxlength="10">
	                &nbsp;&nbsp;Tới ngày <input value="${endDate}" name="endDate" id="endDate" size="10" maxlength="10">
	                &nbsp;&nbsp;<input type="submit" class="button" name="submit" id="submit"value="View Report"/>
	            </td>
	        </tr>		
		</table>
	</form>

<br>
<div style=" overflow-x: auto;overflow-y: hidden;">
			<display:table name="${vRpDyIpbbInternet}"  id = "vRpDyIpbbInternet" requestURI="" pagesize="100" class="simple2"  export="true">
	    		<display:column property="day" format="{0,date,dd/MM/yyyy}" titleKey="DATE" sortable="true"  headerClass="master-header-1"/>
	    		<display:column property="direction" titleKey="DIRECTION" sortable="true"  headerClass="master-header-1"/>
	    		<display:column property="link" titleKey="LINK"  headerClass="hide" class="hide"/>
	    		<display:column title="LINK" media="html"  sortable="true"  headerClass="master-header-1">
		   	 			<a href="${pageContext.request.contextPath}/report/core/ip-backbone/hr_delay_internet.htm?direction=${vRpDyIpbbInternet.direction}&link=${vRpDyIpbbInternet.link}&startDate=<fmt:formatDate pattern="dd/MM/yyyy" value="${vRpDyIpbbInternet.day}"/>&endDate=<fmt:formatDate pattern="dd/MM/yyyy" value="${vRpDyIpbbInternet.day}"/>">${vRpDyIpbbInternet.link}</a>
		    	</display:column>
	    		<display:column property ="refValue"  titleKey="Unit(ms)"  sortable="true"  headerClass="master-header-1"  class = "rightColumnMana"/>
				<display:column property ="delayMs1Max"  titleKey="Max DelayTime"  sortable="true" headerClass="master-header-3"  class = "rightColumnMana"/>
				<display:column property ="delayMs1Avg"  titleKey="Avg DelayTime"  sortable="true" headerClass="master-header-3" class = "AVG1 rightColumnMana"/>
				<display:column property ="perfomance1"  titleKey="Performance"  sortable="true" class = "PER1 rightColumnMana" headerClass="master-header-3"/>
				<display:column property ="delayMs2Max"  titleKey="Max DelayTime"  sortable="true" headerClass="master-header-4" class = "rightColumnMana"/>
				<display:column property ="delayMs2Avg"  titleKey="Avg DelayTime"  sortable="true" headerClass="master-header-4" class = "AVG2 rightColumnMana"/>
				<display:column property ="perfomance2"  titleKey="Performance"  sortable="true" class = "PER2 rightColumnMana" headerClass="master-header-4"/>
			</display:table>
		
</div>

<!-- 	<br/>
	<div id="chartMaxLink1" style="width: 100%; margin: 1em auto"></div>
	<br/>
	<div id="chartMaxLink2" style="width: 100%; margin: 1em auto"></div>
 -->
<div  style="overflow: auto;">
		<h1>IPBB LINK1 MONTHLY REPORT</h1>
		<display:table name="${MnIpbblink1}" id="MnIpbblink1" requestURI="" pagesize="50" class="simple2" export="true" style="width: 200%;">
			<display:column property ="month"  titleKey="MONTH" sortable="true" headerClass="master-header-1"/>
		    <display:column property="year" titleKey="YEAR" sortable="true" headerClass="master-header-1"/>
	    	<display:column property="direction" titleKey="DIRECTION" sortable="true" headerClass="master-header-1"/>
		    <display:column property="link" titleKey="LINK" sortable="true" headerClass="master-header-1"/>
		    <display:column property="refValue" titleKey="Unit(ms)" sortable="true" headerClass="master-header-1" class="REF_VALUE rightColumnMana"/>
		    <display:column property="tongDat" titleKey="Tổng đạt" class = "TONG_DAT rightColumnMana" sortable="true" headerClass="master-header-1"/>
		    <display:column property="tongKhongDat" titleKey="Tổng không đạt" sortable="true" class = "TONG_KHONG_DAT rightColumnMana" headerClass="master-header-1"/>
		    
		    <display:column property="ngay01" titleKey="01" class = "PER01_1 rightColumnMana"  sortable="true" headerClass="master-header-2"/>
		    <display:column property="ngay02" titleKey="02" class = "PER02_1 rightColumnMana" sortable="true" headerClass="master-header-2"/>
		    <display:column property="ngay03" titleKey="03" class = "PER03_1 rightColumnMana"  sortable="true" headerClass="master-header-2"/>
		    <display:column property="ngay04" titleKey="04" class = "PER04_1 rightColumnMana" sortable="true" headerClass="master-header-2"/>
		    <display:column property="ngay05" titleKey="05" class = "PER05_1 rightColumnMana" sortable="true" headerClass="master-header-2"/>
		    <display:column property="ngay06" titleKey="06" class = "PER06_1 rightColumnMana" sortable="true" headerClass="master-header-2"/>
		    <display:column property="ngay07" titleKey="07" class = "PER07_1 rightColumnMana" sortable="true" headerClass="master-header-2"/>
		    <display:column property="ngay08" titleKey="08" class = "PER08_1 rightColumnMana" sortable="true" headerClass="master-header-2"/>
		    <display:column property="ngay09" titleKey="09" class = "PER09_1 rightColumnMana" sortable="true" headerClass="master-header-2"/>
		    <display:column property="ngay10" titleKey="10" class = "PER10_1 rightColumnMana" sortable="true" headerClass="master-header-2"/>
		    <display:column property="ngay11" titleKey="11" class = "PER11_1 rightColumnMana" sortable="true" headerClass="master-header-2"/>
		    <display:column property="ngay12" titleKey="12" class = "PER12_1 rightColumnMana" sortable="true" headerClass="master-header-2"/>
		    <display:column property="ngay13" titleKey="13" class = "PER13_1 rightColumnMana" sortable="true" headerClass="master-header-2"/>
		    <display:column property="ngay14" titleKey="14" class = "PER14_1 rightColumnMana" sortable="true" headerClass="master-header-2"/>
		    <display:column property="ngay15" titleKey="15" class = "PER15_1 rightColumnMana" sortable="true" headerClass="master-header-2"/>
		    <display:column property="ngay16" titleKey="16" class = "PER16_1 rightColumnMana" sortable="true" headerClass="master-header-2"/>
		    <display:column property="ngay17" titleKey="17" class = "PER17_1 rightColumnMana" sortable="true" headerClass="master-header-2"/>
		    <display:column property="ngay18" titleKey="18" class = "PER18_1 rightColumnMana" sortable="true" headerClass="master-header-2"/>
		    <display:column property="ngay19" titleKey="19" class = "PER19_1 rightColumnMana" sortable="true" headerClass="master-header-2"/>
		    <display:column property="ngay20" titleKey="20" class = "PER20_1 rightColumnMana" sortable="true" headerClass="master-header-2"/>
		    <display:column property="ngay21" titleKey="21" class = "PER21_1 rightColumnMana" sortable="true" headerClass="master-header-2"/>
		    <display:column property="ngay22" titleKey="22" class = "PER22_1 rightColumnMana" sortable="true" headerClass="master-header-2"/>
		    <display:column property="ngay23" titleKey="23" class = "PER23_1 rightColumnMana" sortable="true" headerClass="master-header-2"/>
		    <display:column property="ngay24" titleKey="24" class = "PER24_1 rightColumnMana" sortable="true" headerClass="master-header-2"/>
		    <display:column property="ngay25" titleKey="25" class = "PER25_1 rightColumnMana" sortable="true" headerClass="master-header-2"/>
		    <display:column property="ngay26" titleKey="26" class = "PER26_1 rightColumnMana" sortable="true" headerClass="master-header-2"/>
		    <display:column property="ngay27" titleKey="27" class = "PER27_1 rightColumnMana" sortable="true" headerClass="master-header-2"/>
		    <display:column property="ngay28" titleKey="28" class = "PER28_1 rightColumnMana" sortable="true" headerClass="master-header-2"/>
		    <display:column property="ngay29" titleKey="29" class = "PER29_1 rightColumnMana" sortable="true" headerClass="master-header-2"/>
		    <display:column property="ngay30" titleKey="30" class = "PER30_1 rightColumnMana" sortable="true" headerClass="master-header-2"/>
		    <display:column property="ngay31" titleKey="31" class = "PER31_1 rightColumnMana" sortable="true" headerClass="master-header-2"/>
		    
	    	 <display:setProperty name="export.csv.filename" value="IPBB_Link1List.csv"/>
		     <display:setProperty name="export.excel.filename" value="IPBB_Link1List.xls"/>
		     <display:setProperty name="export.xml.filename" value="IPBB_Link1List.xml"/>
		</display:table> 
	</div>

	<div style="overflow: auto;">
		<h1>IPBB LINK2 MONTHLY REPORT</h1>
		<display:table name="${MnIpbblink2}" id="MnIpbblink2" requestURI="" pagesize="50" class="simple2" export="true" style="width: 200%;">
			<display:column property ="month"  titleKey="MONTH" sortable="true" headerClass="master-header-1"/>
		    <display:column property="year" titleKey="YEAR" sortable="true" headerClass="master-header-1"/>
	    	<display:column property="direction" titleKey="DIRECTION" sortable="true" headerClass="master-header-1"/>
		    <display:column property="link" titleKey="LINK" sortable="true" headerClass="master-header-1"/>
		    <display:column property="refValue" titleKey="Unit(ms)" sortable="true" headerClass="master-header-1" class="REF_VALUE rightColumnMana"/>
		    <display:column property="tongDat" titleKey="Tổng đạt" class = "TONG_DAT rightColumnMana" sortable="true" headerClass="master-header-1"/>
		    <display:column property="tongKhongDat" titleKey="Tổng không đạt" sortable="true" class = "TONG_KHONG_DAT rightColumnMana" headerClass="master-header-1"/>
		    
		    <display:column property="ngay01" titleKey="01" class = "PER01_1 rightColumnMana"  sortable="true" headerClass="master-header-2"/>
		    <display:column property="ngay02" titleKey="02" class = "PER02_1 rightColumnMana" sortable="true" headerClass="master-header-2"/>
		    <display:column property="ngay03" titleKey="03" class = "PER03_1 rightColumnMana"  sortable="true" headerClass="master-header-2"/>
		    <display:column property="ngay04" titleKey="04" class = "PER04_1 rightColumnMana" sortable="true" headerClass="master-header-2"/>
		    <display:column property="ngay05" titleKey="05" class = "PER05_1 rightColumnMana" sortable="true" headerClass="master-header-2"/>
		    <display:column property="ngay06" titleKey="06" class = "PER06_1 rightColumnMana" sortable="true" headerClass="master-header-2"/>
		    <display:column property="ngay07" titleKey="07" class = "PER07_1 rightColumnMana" sortable="true" headerClass="master-header-2"/>
		    <display:column property="ngay08" titleKey="08" class = "PER08_1 rightColumnMana" sortable="true" headerClass="master-header-2"/>
		    <display:column property="ngay09" titleKey="09" class = "PER09_1 rightColumnMana" sortable="true" headerClass="master-header-2"/>
		    <display:column property="ngay10" titleKey="10" class = "PER10_1 rightColumnMana" sortable="true" headerClass="master-header-2"/>
		    <display:column property="ngay11" titleKey="11" class = "PER11_1 rightColumnMana" sortable="true" headerClass="master-header-2"/>
		    <display:column property="ngay12" titleKey="12" class = "PER12_1 rightColumnMana" sortable="true" headerClass="master-header-2"/>
		    <display:column property="ngay13" titleKey="13" class = "PER13_1 rightColumnMana" sortable="true" headerClass="master-header-2"/>
		    <display:column property="ngay14" titleKey="14" class = "PER14_1 rightColumnMana" sortable="true" headerClass="master-header-2"/>
		    <display:column property="ngay15" titleKey="15" class = "PER15_1 rightColumnMana" sortable="true" headerClass="master-header-2"/>
		    <display:column property="ngay16" titleKey="16" class = "PER16_1 rightColumnMana" sortable="true" headerClass="master-header-2"/>
		    <display:column property="ngay17" titleKey="17" class = "PER17_1 rightColumnMana" sortable="true" headerClass="master-header-2"/>
		    <display:column property="ngay18" titleKey="18" class = "PER18_1 rightColumnMana" sortable="true" headerClass="master-header-2"/>
		    <display:column property="ngay19" titleKey="19" class = "PER19_1 rightColumnMana" sortable="true" headerClass="master-header-2"/>
		    <display:column property="ngay20" titleKey="20" class = "PER20_1 rightColumnMana" sortable="true" headerClass="master-header-2"/>
		    <display:column property="ngay21" titleKey="21" class = "PER21_1 rightColumnMana" sortable="true" headerClass="master-header-2"/>
		    <display:column property="ngay22" titleKey="22" class = "PER22_1 rightColumnMana" sortable="true" headerClass="master-header-2"/>
		    <display:column property="ngay23" titleKey="23" class = "PER23_1 rightColumnMana" sortable="true" headerClass="master-header-2"/>
		    <display:column property="ngay24" titleKey="24" class = "PER24_1 rightColumnMana" sortable="true" headerClass="master-header-2"/>
		    <display:column property="ngay25" titleKey="25" class = "PER25_1 rightColumnMana" sortable="true" headerClass="master-header-2"/>
		    <display:column property="ngay26" titleKey="26" class = "PER26_1 rightColumnMana" sortable="true" headerClass="master-header-2"/>
		    <display:column property="ngay27" titleKey="27" class = "PER27_1 rightColumnMana" sortable="true" headerClass="master-header-2"/>
		    <display:column property="ngay28" titleKey="28" class = "PER28_1 rightColumnMana" sortable="true" headerClass="master-header-2"/>
		    <display:column property="ngay29" titleKey="29" class = "PER29_1 rightColumnMana" sortable="true" headerClass="master-header-2"/>
		    <display:column property="ngay30" titleKey="30" class = "PER30_1 rightColumnMana" sortable="true" headerClass="master-header-2"/>
		    <display:column property="ngay31" titleKey="31" class = "PER31_1 rightColumnMana" sortable="true" headerClass="master-header-2"/>
		   
	    	 <display:setProperty name="export.csv.filename" value="IPBB_Link1List.csv"/>
		     <display:setProperty name="export.excel.filename" value="IPBB_Link1List.xls"/>
		     <display:setProperty name="export.xml.filename" value="IPBB_Link1List.xml"/>
		</display:table>
	</div>
<%-- <script type="text/javascript" src="${pageContext.request.contextPath}/scripts/text_date.js"></script>	
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/highcharts.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/exporting.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/themes/grid.js"></script>
${chartAreaLink1}
${chartAreaLink2} --%>
<script>
	 ${highlight}
	 ${highlight2};
	 ${highlight3};
</script>  
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