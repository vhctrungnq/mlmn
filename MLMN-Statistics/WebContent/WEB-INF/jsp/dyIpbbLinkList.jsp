<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

	<title>IP BACKBONE</title>
	<c:if test="${IPBB == 'E' }">
		<content tag="heading">BÁO CÁO BW IPBB - CÁC LINK TẢI CAO</content>
	</c:if>
	<c:if test="${IPBB != 'E' }">
		<content tag="heading">IP BACKBONE DATA ${direction} DAILY REPORT</content>
	</c:if>
	
<ul class="ui-tabs-nav">
<c:choose>
	<c:when test="${IPBB == 'N' }">
  <li class=""><a href="${pageContext.request.contextPath}/report/core/ip-backbone/hr-internet.htm?link=${link}&startDate=${startDate}&endDate=${endDate}"><span>Báo cáo giờ</span></a></li>
  	</c:when>
  	<c:when test="${IPBB == 'Y' }">
  <li class=""><a href="${pageContext.request.contextPath}/report/core/ip-backbone/hr-link.htm?link=${link}&startDate=${startDate}&endDate=${endDate}"><span>Báo cáo giờ</span></a></li>
 </c:when>
 </c:choose>
  <li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/report/core/ip-backbone/dy-link.htm?function=${function }&direction=${direction}&startDate=${startDate}&endDate=${endDate}"><span>Báo cáo ngày</span></a></li>
</ul>
<br>
	<form method="get" action="dy-link.htm?function=${function }" name = "frmSample" onSubmit = "return ValidateForm()">
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
			         <input value="${function}" id="function" name="function" type="hidden"/>
			        &nbsp;&nbsp;Từ ngày <input value="${startDate}" name="startDate" id="startDate" size="10" maxlength="10">
	                &nbsp;&nbsp;Tới ngày <input value="${endDate}" name="endDate" id="endDate" size="10" maxlength="10">
	                &nbsp;&nbsp;<input type="submit" class="button" name="submit" id="submit"value="View Report"/>
	            </td>
	        </tr>		
		</table>
	</form> 

<c:choose>

	<c:when test="${IPBB == 'N' }">
<div style=" overflow-x: auto;overflow-y: hidden;">
			<h1>${titleInternetInternational}</h1>
			<display:table name="${vdyinternet}" id="IXP" requestURI="" pagesize="100" class="simple2">
	    		<display:column property="day" format="{0,date,dd/MM/yyyy}" titleKey="DATE"/>
	    		<display:column property="direction" titleKey="DIRECTION"/>
	    		<display:column property="link" titleKey="LINK" headerClass="hide" class="hide"/>
	    		<display:column title="LINK" media="html">
		   	 	<a href="${pageContext.request.contextPath}/report/core/ip-backbone/hr-internet.htm?direction=${IXP.direction}&link=${IXP.link}&startDate=<fmt:formatDate pattern="dd/MM/yyyy" value="${IXP.day}"/>&endDate=<fmt:formatDate pattern="dd/MM/yyyy" value="${IXP.day}"/>">${IXP.link}</a>
		    	</display:column>
	    		<display:column property="bandWidth" titleKey="BandWidth"/>
	    		<display:column property="inKbitSecond" titleKey="Kbit/second (Peak)"/>
	    		<display:column property="inUtilization" titleKey="Utilization (%)" class="IN_UTILIZATION"/>
	    		<display:column property="outKbitSecond" titleKey="Kbit/second (Peak)"/>
	    		<display:column property="outUtilization" titleKey="Utilization (%)" class="OUT_UTILIZATION"/>
	    		<display:column property="delayUsMax" titleKey="DELAY_US_MAX"/>
	    		<display:column property="delayUkMax" titleKey="DELAY_UK_MAX"/>
	    		<display:column property="delayJpMax" titleKey="DELAY_JP_MAX"/>
	    		<display:column property="delayHkMax" titleKey="DELAY_HK_MAX"/>
	    		<display:column property="delaySingMax" titleKey="DELAY_SING_MAX"/>
		</display:table>
			<h1>${titleInternet}</h1>
		<display:table name="${vdyinternet2}" id="NIX" requestURI="" pagesize="100" class="simple2" export="true">
	    		<display:column property="day" format="{0,date,dd/MM/yyyy}" titleKey="DATE"/>
	    		<display:column property="direction" titleKey="DIRECTION"/>
	    		<display:column property="link" titleKey="LINK" headerClass="hide" class="hide"/>
	    		<display:column title="LINK" media="html">
		   	 	<a href="${pageContext.request.contextPath}/report/core/ip-backbone/hr-internet.htm?direction=${NIX.direction}&link=${NIX.link}&startDate=<fmt:formatDate pattern="dd/MM/yyyy" value="${NIX.day}"/>&endDate=<fmt:formatDate pattern="dd/MM/yyyy" value="${NIX.day}"/>">${NIX.link}</a>
		    	</display:column>
	    		<display:column property="bandWidth" titleKey="BandWidth"/>
	    		<display:column property="inKbitSecond" titleKey="Kbit/second (Peak)"/>
	    		<display:column property="inUtilization" titleKey="Utilization (%)" class="IN_UTILIZATION"/>
	    		<display:column property="outKbitSecond" titleKey="Kbit/second (Peak)"/>
	    		<display:column property="outUtilization" titleKey="Utilization (%)" class="OUT_UTILIZATION"/>
	    		<display:column property="delayTuoitreMax" titleKey="Delay Tuoitre MAX(ms)"/>
	    		<display:column property="delayThanhnienMax" titleKey="Delay Thanhnien MAX(ms)"/>
		</display:table>
</div>
</c:when>
<c:when test="${IPBB == 'Y' }">	
<div  style=" overflow-x: auto;overflow-y: hidden;">
		<display:table name="${vdylink}" id="item" requestURI="" pagesize="100" class="simple2" export="true">
				<display:column property="day" format="{0,date,dd/MM/yyyy}" titleKey="DATE"/>
				<display:column property="direction" titleKey="DIRECTION"/>
				<display:column property="link" titleKey="LINK" headerClass="hide" class="hide"/>
	    		<display:column title="LINK" media="html">
		   	 	<a href="${pageContext.request.contextPath}/report/core/ip-backbone/hr-link.htm?direction=${item.direction}&link=${item.link}&startDate=<fmt:formatDate pattern="dd/MM/yyyy" value="${item.day}"/>&endDate=<fmt:formatDate pattern="dd/MM/yyyy" value="${item.day}"/>">${item.link}</a>
		    	</display:column>
	    		<display:column property="bandWidth" titleKey="BAND_WIDTH"/>
	    		<display:column property="inKbitSecond" titleKey="IN_KBIT_SECOND"/>
	    		<display:column property="inUtilization" titleKey="IN_UTILIZATION" class="IN_UTILIZATION"/>
	    		<display:column property="outKbitSecond" titleKey="OUT_KBIT_SECOND"/>
	    		<display:column property="outUtilization" titleKey="OUT_UTILIZATION" class="OUT_UTILIZATION"/>
	    		<display:column property="delayMax" titleKey="DELAY_MAX"/>
	    		<display:column property="delayAvg" titleKey="DELAY_AVG"/>
	    		<display:column property="jitterAvg" titleKey="JITTER_AVG"/>
	    		<%--<display:column property="maxOverThreshold50" titleKey="MAX_OVER_THRESHOLD_50"/>
	    		<display:column property="maxOverThreshold10" titleKey="MAX_OVER_THRESHOLD_10"/>--%>
	    		<display:column property="packetLoss" titleKey="PACKET_LOSS"/>
	    		<display:column property="mos" titleKey="MOS"/>
	    		<display:column property="lossConnectionDur" titleKey="LOSS_CONNECTION_DUR"/>
			    
		</display:table>
</div>
</c:when>	
<c:otherwise>
<div  style=" overflow-x: auto;overflow-y: hidden;">
		<display:table name="${vDyIpbbLink}" id="vDyIpbbLink" requestURI="" pagesize="100" class="simple2" export="true">
				<display:column property="day" format="{0,date,dd/MM/yyyy}" titleKey="DATE"/>
				<display:column property="direction" titleKey="DIRECTION"/>
				<display:column property="link" titleKey="LINK" sortable="true" headerClass="master-header-1"/>
	    		<display:column property="bandWidth" titleKey="BAND_WIDTH" sortable="true" headerClass="master-header-1"/>
	    		<display:column property="inKbitSecond" titleKey="IN_KBIT_SECOND"/>
	    		<display:column property="inUtilization" titleKey="IN_UTILIZATION" class="IN_UTILIZATION"/>
	    		<display:column property="outKbitSecond" titleKey="OUT_KBIT_SECOND"/>
	    		<display:column property="outUtilization" titleKey="OUT_UTILIZATION" class="OUT_UTILIZATION"/>
			    
		</display:table>
</div>
</c:otherwise>
</c:choose>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/text_date.js"></script>	
<script>
    $("#item").change(function () {
      window.location = '${pageContext.request.contextPath}/log/checkjob/detail.htm?user=' + $("#item").val();
      
      });  
    ${highlight}
</script>  
<script>
    $("#IXP").change(function () {
      window.location = '${pageContext.request.contextPath}/log/checkjob/detail.htm?user=' + $("#IXP").val();
      
      });  
    ${high}
</script>  
<script>
    $("#NIX").change(function () {
      window.location = '${pageContext.request.contextPath}/log/checkjob/detail.htm?user=' + $("#NIX").val();
      
      });  
    ${highlight}
</script>  
<script type="text/javascript">
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

<script type="text/javascript">
	$(document).ready(function(){
		var trs='<tr>';
	    trs=trs +'<th colspan="4" style="color:blue;"></th>';
	    trs=trs +'<th colspan="2" style="color:blue;">TRAFFIC IN</th>';
	    trs=trs +'<th colspan="2" style="color:blue;">TRAFFIC OUT</th>';
	    trs=trs +'<th colspan="6" style="color:blue;">SLA PARAMETERS</th>';

	    trs=trs +'<tr><th style="color:blue;">Date</th>';
	   	trs=trs +'<th style="color:blue;width:130px" >Direction</th>';
	    trs=trs +'<th style="color:blue;width:160px">Link</th>';
	    trs=trs +'<th style="color:blue;">BandWidth</th>';
	    trs=trs +'<th style="color:blue;">kbit/second (Peak)</th>';
	    trs=trs +'<th style="color:blue;" >Utilization (%)</th>';
	    trs=trs +'<th style="color:blue;">kbit/second (Peak)</th>';
	    trs=trs +'<th style="color:blue;">Utilization (%)</th>';
	    trs=trs +'<th style="color:blue;">Delay MAX (ms)</th>';
	    trs=trs +'<th style="color:blue;">Delay Average (ms)</th>';
	    trs=trs +'<th style="color:blue;">Jitter Average (ms)</th>';
	    trs=trs +'<th style="color:blue;">Package loss</th>';
	    trs=trs +'<th style="color:blue;">MOS</th>';
	    trs=trs +'<th style="color:blue;">Loss Connection Duration (Min)</th>';
	    
	$('#item thead').html(trs);
	});
</script>

<script type="text/javascript">
	$(document).ready(function(){
		var trs='<tr>';
	    trs=trs +'<th colspan="4" style="color:blue;"></th>';
	    trs=trs +'<th colspan="2" style="color:blue;">TRAFFIC IN</th>';
	    trs=trs +'<th colspan="2" style="color:blue;">TRAFFIC OUT</th>';
	    trs=trs +'<th colspan="5" style="color:blue;">SLA PARAMETERS</th>';

	    trs=trs +'<tr><th style="color:blue;">Date</th>';
	   	trs=trs +'<th style="color:blue;width:130px" >Direction</th>';
	    trs=trs +'<th style="color:blue;width:160px">Link</th>';
	    trs=trs +'<th style="color:blue;">BandWidth</th>';
	    trs=trs +'<th style="color:blue;">kbit/second (Peak)</th>';
	    trs=trs +'<th style="color:blue;" >Utilization (%)</th>';
	    trs=trs +'<th style="color:blue;">kbit/second (Peak)</th>';
	    trs=trs +'<th style="color:blue;">Utilization (%)</th>';
	    trs=trs +'<th style="color:blue;">Delay US MAX(ms)</th>';
	    trs=trs +'<th style="color:blue;">Delay UK MAX(ms)</th>';
	    trs=trs +'<th style="color:blue;">Delay JP MAX(ms)</th>';
	    trs=trs +'<th style="color:blue;">Delay HK MAX(ms)</th>';
	    trs=trs +'<th style="color:blue;">Delay SING MAX(ms)</th>';
	    
	$('#IXP thead').html(trs);
	});
</script>

<script type="text/javascript">
	$(document).ready(function(){
		var trs='<tr>';
	    trs=trs +'<th colspan="4" style="color:blue;"></th>';
	    trs=trs +'<th colspan="2" style="color:blue;">TRAFFIC IN</th>';
	    trs=trs +'<th colspan="2" style="color:blue;">TRAFFIC OUT</th>';
	    trs=trs +'<th colspan="2" style="color:blue;">SLA PARAMETERS</th>';

	    trs=trs +'<tr><th style="color:blue;">Date</th>';
	   	trs=trs +'<th style="color:blue;width:130px" >Direction</th>';
	    trs=trs +'<th style="color:blue;width:160px">Link</th>';
	    trs=trs +'<th style="color:blue;">BandWidth</th>';
	    trs=trs +'<th style="color:blue;">kbit/second (Peak)</th>';
	    trs=trs +'<th style="color:blue;" >Utilization (%)</th>';
	    trs=trs +'<th style="color:blue;">kbit/second (Peak)</th>';
	    trs=trs +'<th style="color:blue;">Utilization (%)</th>';
	    trs=trs +'<th style="color:blue;">Delay Tuoitre MAX(ms)</th>';
	    trs=trs +'<th style="color:blue;">Delay Vnexpress MAX(ms)</th>';
	    
	$('#NIX thead').html(trs);
	});
</script>
<script type="text/javascript">
	$(document).ready(function(){
		var trs='<tr>';
		trs=trs +'<th rowspan="2" style="color:blue;">Date</th>';
		trs=trs +'<th rowspan="2" style="color:blue;">Direction</th>';
		trs=trs +'<th rowspan="2" style="color:blue;">Link</th>';
		trs=trs +'<th rowspan="2" style="color:blue;">BandWidth</th>'; 
	    trs=trs +'<th colspan="2" style="color:blue;">TRAFFIC IN</th>';
	    trs=trs +'<th colspan="2" style="color:blue;">TRAFFIC OUT</th></tr>';

	    trs=trs +'<tr><th style="color:blue;">kbit/second (Peak)</th>';
	    trs=trs +'<th style="color:blue;" >Utilization (%)</th>';
	    trs=trs +'<th style="color:blue;">kbit/second (Peak)</th>';
	    trs=trs +'<th style="color:blue;">Utilization (%)</th></tr>';
	    
	$('#vDyIpbbLink thead').html(trs);
	});
</script>
<script type="text/javascript">
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