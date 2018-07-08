<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    

<title>IP BACK BONE</title>
<content tag="heading">IP BACKBONE DATA DAILY REPORT</content>
<ul class="ui-tabs-nav">

	<c:choose>
		<c:when test="${function == 'list' && module == 'data'}">
			<li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/report/core/ip-backbone/ipbb-data.htm?function=list&module=data"><span>Bảng số liệu</span></a></li>
			<li class=""><a href="${pageContext.request.contextPath}/report/core/ip-backbone/ipbb-data.htm?function=grap&module=data"><span>Biểu đồ (%)</span></a></li>
		</c:when>
		<c:when test="${function == 'grap'  && module == 'data'}">
			<li class=""><a href="${pageContext.request.contextPath}/report/core/ip-backbone/ipbb-data.htm?function=list&module=data"><span>Bảng số liệu</span></a></li>
			<li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/report/core/ip-backbone/ipbb-data.htm?function=grap&module=data"><span>Biểu đồ (%)</span></a></li>
		</c:when>
		<c:when test="${function == 'list' &&  module == 'internet'}">
			<li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/report/core/ip-backbone/ipbb-data.htm?function=list&module=internet"><span>Bảng số liệu</span></a></li>
			<li class=""><a href="${pageContext.request.contextPath}/report/core/ip-backbone/ipbb-data.htm?function=grap&module=internet_ixp"><span>Biểu đồ IXP(Mb/s)</span></a></li>
			<li class=""><a href="${pageContext.request.contextPath}/report/core/ip-backbone/ipbb-data.htm?function=grap&module=internet_nix"><span>Biểu đồ NIX(Mb/s)</span></a></li>
			
		</c:when>
		<c:when test="${function == 'grap'  &&  module == 'internet_ixp'}">
			<li class=""><a href="${pageContext.request.contextPath}/report/core/ip-backbone/ipbb-data.htm?function=list&module=internet"><span>Bảng số liệu</span></a></li>
			<li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/report/core/ip-backbone/ipbb-data.htm?function=grap&module=internet_ixp"><span>Biểu đồ IXP(Mb/s)</span></a></li>
			<li class=""><a href="${pageContext.request.contextPath}/report/core/ip-backbone/ipbb-data.htm?function=grap&module=internet_nix"><span>Biểu đồ NIX(Mb/s)</span></a></li>
			
		</c:when>
		<c:when test="${function == 'grap'  && module == 'internet_nix'}">
			<li class=""><a href="${pageContext.request.contextPath}/report/core/ip-backbone/ipbb-data.htm?function=list&module=internet"><span>Bảng số liệu</span></a></li>
			<li class=""><a href="${pageContext.request.contextPath}/report/core/ip-backbone/ipbb-data.htm?function=grap&module=internet_ixp"><span>Biểu đồ IXP(Mb/s)</span></a></li>
			<li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/report/core/ip-backbone/ipbb-data.htm?function=grap&module=internet_nix"><span>Biểu đồ NIX(Mb/s)</span></a></li>
			
		</c:when>
	</c:choose>
 </ul>
<br/>
	<form method="get" action="ipbb-data.htm" name ="frmSample" onSubmit = "return ValidateForm()">
		<table style="width:100%;" class="form">
			<tr>
				<td align="left">
				<c:if test="${function == 'list' }">
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
			        &nbsp;&nbsp;Từ ngày <input value="${startDate}" name="startDate" id="startDate" size="10" maxlength="10">
	                
				</c:if>
					&nbsp;&nbsp;Tới ngày <input value="${endDate}" name="endDate" id="endDate" size="10" maxlength="10">
	                <input value="${function}" id="function" name="function" type="hidden"/>
	                <input value="${module}" id="module" name="module" type="hidden"/>
	                
	                &nbsp;&nbsp;<input type="submit" class="button" name="submit" id="submit"value="View Report"/>
	            </td>
	        </tr>		
		</table>
	</form>
	<br/>
<c:choose>
     <c:when test="${function == 'list' }">
   		<div  style=" overflow-x: auto;overflow-y: hidden;">
		<display:table name="${vipbbdata}" id="user"  requestURI="" pagesize="50" class="simple2" export="true">
	    		<display:column property ="day" format="{0,date,dd/MM/yyyy}" titleKey="DAY" />
	    		<display:column property="direction" titleKey="DIRECTION" headerClass="hide" class="hide"/>
	    		<display:column title="DIRECTION" media="html">
		   	 	<a href="${pageContext.request.contextPath}/report/core/ip-backbone/dy-link.htm?direction=${user.direction}&startDate=<fmt:formatDate pattern="dd/MM/yyyy" value="${user.day}"/>&endDate=<fmt:formatDate pattern="dd/MM/yyyy" value="${user.day}"/>">${user.direction}</a>
		    	</display:column>
		    	<display:column property="bandWidth" titleKey="BAND_WIDTH"/>
	    		<display:column property="inKbitSecond" titleKey="IN_KBIT_SECOND"/>
	    		<display:column property="inMaxUtilization" titleKey="IN_MAX_UTILIZATION" class="IN_MAX_UTILIZATION"/>
	    		<display:column property="outKbitSecond" titleKey="OUT_KBIT_SECOND"/>
	    		<display:column property="outMaxUtilization" titleKey="OUT_MAX_UTILIZATION" class="OUT_MAX_UTILIZATION"/>
	    		
	    		<display:column property="diemDau" titleKey="ipbb.traffic.diemdau" />
	    		<display:column property="diemCuoi" titleKey="ipbb.traffic.diemcuoi" />
	    		<display:column property="donViQuanLy" titleKey="ipbb.traffic.donviquanly" />
	    		<display:column property="doiTacTruyenDan" titleKey="ipbb.traffic.doitactruyendan" />
	    		<display:column property="congVanTangBw" titleKey="ipbb.traffic.congvantangbw"/>
		</display:table>
		</div>
   </c:when>
<c:when test="${function == 'grap' }">
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/text_date.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/highcharts.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/exporting.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/themes/grid.js"></script>
<c:choose>
	<c:when test="${module == 'internet_ixp' || module == 'internet_nix'  }">
	<table style="width:99%">
		<tr>
			<td><div id="inMaxUtilInternet" style="width: 100%; margin: 1em auto"></div></td>
		</tr>
		<tr>
			<td><div id="outMaxUtilInternet" style="width: 100%; margin: 1em auto"></div></td>
		</tr>
	</table> 
		${chartdivInternet}
		${chartDivOutInternet}
	</c:when>
	<c:otherwise>
	<table style="width:99%">
		<tr>
			<td><div id="inMaxUtilizationChart" style="width: 100%; margin: 1em auto"></div></td>
		</tr>
		<tr>
			<td><div id="outMaxUtilizationChart" style="width: 100%; margin: 1em auto"></div></td>
		</tr>
	</table>  
		${chartdiv}
		${chartDivOut}
	</c:otherwise>	
</c:choose> 
	</c:when>
</c:choose>
 
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/text_date.js"></script>
<script>
    $("#user").change(function () {
      window.location = '${pageContext.request.contextPath}/log/checkjob/detail.htm?user=' + $("#user").val();
      
      });  
    ${highlight}
</script>  
<script type = "text/javascript">
function xl(){
	var sub = document.getElementById("submit");
	sub.focus();
} 

script type = "text/javascript">
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
	    trs=trs +'<th rowspan="2" style="color:blue;">Date</th>';
	    trs=trs +'<th rowspan="2" style="color:blue;width:205px">Direction</th>';
	    trs=trs +'<th rowspan="2" style="color:blue;">BandWidth</th>';
	    trs=trs +'<th colspan="2" style="color:blue;">Traffic in</th>';
	    trs=trs +'<th colspan="2" style="color:blue;">Traffic out</th>';
	    trs=trs +'<th rowspan="2" style="color:blue;"><fmt:message key="ipbb.traffic.diemdau"/></th>';
	    trs=trs +'<th rowspan="2" style="color:blue;"><fmt:message key="ipbb.traffic.diemcuoi"/></th>';
	    trs=trs +'<th rowspan="2" style="color:blue;"><fmt:message key="ipbb.traffic.donviquanly"/></th>';
	    trs=trs +'<th rowspan="2" style="color:blue;"><fmt:message key="ipbb.traffic.doitactruyendan"/></th>';
	    trs=trs +'<th rowspan="2" style="color:blue;"><fmt:message key="ipbb.traffic.congvantangbw"/></th></tr>';
 
	    trs=trs +'<tr><th style="color:blue;">kbit/second (Peak)</th>';
	    trs=trs +'<th style="color:blue;" >MAX Utilization (%)</th>';
	    trs=trs +'<th style="color:blue;">kbit/second (Peak)</th>';
	    trs=trs +'<th style="color:blue;">MAX Utilization (%)</th></tr>';  
	$('#user thead').html(trs);
	});
</script>