<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

	<title>IP BACKBONE</title>
	<content tag="heading">${title} DAILY REPORT</content>
<ul class="ui-tabs-nav">
	<li class=""><a href="${pageContext.request.contextPath}/report/core/ip-backbone-fw/hr.htm"><span>Báo cáo giờ</span></a></li>
 	<li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/report/core/ip-backbone-fw/dy.htm"><span>Báo cáo ngày</span></a></li>
</ul>
<br>
	<form method="get" action="dy.htm">
	
		<table style="width:100%;" class="form">
			<tr>
				<td align="left">
					ROUTE 
			  		<select name="routeid" id="routeid" onchange="xl()">
			  			<option value="">Tất cả</option>
			              <c:forEach var="items" items="${routeList}">
				              <c:choose>
				                <c:when test="${items.routeid == routeid}">
				                    <option value="${items.routeid}" selected="selected">${items.routeid}</option>
				                </c:when>
				                <c:otherwise>
				                    <option value="${items.routeid}">${items.routeid}</option>
				                </c:otherwise>
				              </c:choose>
						    </c:forEach>
			        </select>
	               &nbsp;&nbsp;SCP 
			  		<select name="scp" id="scp" onchange="xl()">
			  			<option value="">Tất cả</option>
			              <c:forEach var="items" items="${scpList}">
				              <c:choose>
				                <c:when test="${items.dev == scp}">
				                    <option value="${items.dev}" selected="selected">${items.dev}</option>
				                </c:when>
				                <c:otherwise>
				                    <option value="${items.dev}">${items.dev}</option>
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
			<display:table name="${dyIpbbFw}"  id = "dyIpbbFw" requestURI="" pagesize="100" class="simple2"  export="true">
	    		<display:column property="day" format="{0,date,dd/MM/yyyy}" titleKey="DATE" sortable="true"/>
				<display:column property ="route"  titleKey="route"  sortable="true" class="ROUTE"/>
				<display:column property ="scp"  titleKey="scp"  sortable="true" headerClass="hide" class="hide"/>
				<display:column title="scp" media="html"  sortable="true">
		   	 		<a href="${pageContext.request.contextPath}/report/core/ip-backbone-fw/hr.htm?route=${dyIpbbFw.route}&scp=${dyIpbbFw.scp}&startDate=<fmt:formatDate pattern="dd/MM/yyyy" value="${dyIpbbFw.day}"/>&endDate=<fmt:formatDate pattern="dd/MM/yyyy" value="${dyIpbbFw.day}"/>">${dyIpbbFw.scp}</a>
		    	</display:column>
		    	<display:column property ="cpuMax"  titleKey="CPU Max (%)"  sortable="true" class="CPU_MAX"/>
				<display:column property ="cpuUtil"  titleKey="CPU Utilization (%)"  sortable="true" class="CPU_UTIL"/>
				<display:column property ="memmoryUtil"  titleKey="Memory Utilization (%)"  sortable="true" class="MEMORY_UTIL"/>
				<display:column property ="avgSession"  titleKey="Session Max "  sortable="true" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" class="AVG_SESSION"/>
				<display:column property ="totalSession"  titleKey="Session Util(%)"  sortable="true" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" class="TOTAL_SESSION"/>
			</display:table>
		
</div>

	${html }

<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/text_date.js"></script>	
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/highcharts.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/exporting.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/themes/grid.js"></script>
${chartAreaLink1}
${chartAreaLink2}
${chartAreaLink3}
${chartAreaLink4}
${chartAreaLink5}
${chartAreaLink6}
${chartAreaLink7}
${chartAreaLink8}
<script>
	 ${highlight}
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