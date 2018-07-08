<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<style type="text/css">    #doublescroll { overflow: auto; overflow-y: hidden; }    #doublescroll p { margin: 0; padding: 1em; white-space: nowrap; }</style>
<title>GGSN SUMMARY REPORT</title>
<content tag="heading">GGSN SUMMARY REPORT</content>

<div class="ui-tabs-panel">
	<form method="get" action="dy.htm" name = "frmSample" onSubmit = "return ValidateForm()">
		<table width ="100%" class="form">
			<tr>
			    <td align="left">
					GGSN
					<select name="ggsnid" id="ggsnid" onchange="xl()">
					<option value="">--Select GGSN--</option>
					        <c:forEach var="ggsn" items="${Ggsnlist}">
					              <c:choose>
					                <c:when test="${ggsn.ggsnid == ggsnid}">
					                    <option value="${ggsn.ggsnid}" selected="selected">${ggsn.ggsnid}</option>
					                </c:when>
					                <c:otherwise>
					                    <option value="${ggsn.ggsnid}">${ggsn.ggsnid}</option>
					                </c:otherwise>
					              </c:choose>
					    </c:forEach>
					</select>
	               Từ Ngày <input value="${startDate}" name="startDate" id="startDate" size="10" maxlength="10"/>
	                Đến Ngày <input value="${endDate}" name="endDate" id="endDate" size="10" maxlength="10"/>
	                &nbsp;&nbsp;<input type="submit" class="button" name="submit" id="submit" value="View Report"/>
	            </td>
	        </tr>		
		</table>
	</form>
	<br/>
</div>
	
	
<div  id="doublescroll">
		<display:table name="${DyGgsnSummary}" id="DyGgsnSummary" requestURI="" pagesize="100" class="simple3" export="true">
		    <display:column property ="day" format="{0,date,dd/MM/yyyy}" titleKey="DAY"  sortable="true"/>
		    <display:column property ="ggsnid"  titleKey="GGSNID" sortable="true" />
		    <display:column property ="cpuLoadr"  titleKey="CPU Load(%)"  sortable="true" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator"/>
		    <display:column property ="pssr"  titleKey="PSSR(%)"  sortable="true"  decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator"/>
		    <display:column property="actPdpCtx" titleKey="Act PDP Ctx"  sortable="true" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator"/>
		    <display:column property="pdpUtil" titleKey="PDP_UTIL(%)" sortable="true"  decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator"/>
		    <display:column property="thp" titleKey="THP (Mbit/s)"  sortable="true" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator"/>
		    <display:column property="thpUtil" titleKey="THP_UTIL (%)"  sortable="true" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator"/>
		    <display:column property="traffic" titleKey="Traffic (MB)" sortable="true"  decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator"/>
		    <display:column property="avePacketSize" titleKey="Ave Packet size"  sortable="true" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator"/>
		    <display:column property="cpuLoad" titleKey="Ref CPU Load" sortable="true"  decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator"/>
		    <display:column property="projectThp" titleKey="Project THP at Ref CPU load" sortable="true"  decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator"/>
		    <display:column property="gtpPacketLoss" titleKey="GTP Packet Loss" sortable="true"  decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator"/>
		    <display:column property="gtpErrors" titleKey="GTP Errors" sortable="true"  decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator"/>
		    <display:column property="gtpRequests" titleKey="GTP Requests with Unexpected Version" sortable="true"  decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator"/>
		    <display:column property="giPacketLost" titleKey="Gi Packet Lost(%)" sortable="true"  decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator"/>
		    <display:column property="totalApConfigured" titleKey="Total AP configured" sortable="true"  decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator"/>
		    <display:column property="cdrTranferSr" titleKey="CDR Tranfer SR(%)"  sortable="true" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator"/>
		    <display:column property="ocsConnectIniSr" titleKey="OCS Connect Ini SR(%)"  sortable="true" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator"/>
		    <display:column property="ocsSessionIniSr" titleKey="OCS Session Ini SR(%)"  sortable="true" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator"/>
		    <display:column property="ocsAveResponeTime" titleKey="OCS Ave Respone time(s)" sortable="true"  decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator"/>
		    <display:column property="gxCcrSr" titleKey="Gx CCR SR(%)"  sortable="true" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator"/>
		    <display:column property="updatePsrSgsn" titleKey="Update PSR(%) SGSN ini"  sortable="true" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator"/>
		    <display:column property="updatePsrGgsn" titleKey="Update PSR(%) GGSN ini"  sortable="true" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator"/>
		    <display:column property="deletePsrSgsn" titleKey="Delete PSR(%) SGSN Ini" sortable="true"  decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator"/>
		    <display:column property="deletePsrGgsn" titleKey="Delete PSR(%) GGSN Ini"  sortable="true" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator"/>
		    <display:column property="deactSrGgsn" titleKey="Deact SR(%) GGSN ini"  sortable="true" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator"/>
		    <display:column property="bearerDeactSrGgsn" titleKey="Bearer Deact SR(%) GGSN ini" sortable="true"  decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator"/>
		    <display:column property="deactSrGgsnTer" titleKey="Deact SR(%) GGSN ter" sortable="true"  decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator"/>
		    <display:column property="bearerDeactSrGgsnTer" titleKey="Bearer Deact SR(%) GGSN ter" sortable="true"  decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator"/>
		    <display:column property="grandTotal" titleKey="GRAND_TOTAL"  sortable="true" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator"/>
		    <display:column property="subTotal" titleKey="SUB_TOTAL" sortable="true"  decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator"/>
			
			 <display:setProperty name="export.csv.filename" value="GGSNList.csv"/>
			 <display:setProperty name="export.excel.filename" value="GGSNList.xls"/>
			 <display:setProperty name="export.xml.filename" value="GGSNList.xml"/>
		</display:table>
	</div>
	
	<div style="width:100%;padding-top: 10px">
		<div align="center" class="w50fl">
			<div align="center" id="chartAreaCPLOAD" style="width: 98%;"></div>
		</div>
		<div align="center" class="w50fl">
			<div align="center" id="chartAreaPSSR" style="width: 98%;"></div>
		</div>
	</div>
	<br/>
	<table style="width:99%">
		<tr>
			<td><div id="chartCPLOAD" style="width: 100%; margin: 1em auto"></div></td>
		</tr>
		<tr>
			<td><div id="chartPSSR" style="width: 100%; margin: 1em auto"></div></td>
		</tr>
		<tr>
			<td><div id="chartPDP" style="width: 100%; margin: 1em auto"></div></td>
		</tr>
		<tr>
			<td><div id="chartTHP" style="width: 100%; margin: 1em auto"></div></td>
		</tr>
		<tr>
			<td><div id="chartTrafic" style="width: 100%; margin: 1em auto"></div></td>
		</tr>
		<tr>
			<td><div id="chartOCS" style="width: 100%; margin: 1em auto"></div></td>
		</tr>
		<tr>
			<td><div id="chartGxCCR" style="width: 100%; margin: 1em auto"></div></td>
		</tr>
	</table>
	<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/text_date.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/highcharts.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/exporting.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/themes/grid.js"></script>
	${chartAreaCPLOAD}
	${chartAreaPSSR}
	${chartdivPDP}
	${chartdivTHP}
	${chartdivTrafic}
	${chartdivOCS}
	${chartdivGxCCR}
	
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
		var cache = {},
		lastXhr;
		$( "#mscid" ).autocomplete({
			minLength: 2,
			source: function( request, response ) {
				var term = request.term;
				if ( term in cache ) {
					response( cache[ term ] );
					return;
				}

				lastXhr = $.getJSON( "${pageContext.request.contextPath}/ajax/getMsc.htm", request, function( data, status, xhr ) {
					cache[ term ] = data;
					if ( xhr === lastXhr ) {
						response( data );
					}
				});
			}
		});
	});
</script>

<script type="text/javascript">
    function DoubleScroll(element) {
        var scrollbar= document.createElement('div');
        scrollbar.appendChild(document.createElement('div'));
        scrollbar.style.overflow= 'auto';
        scrollbar.style.overflowY= 'hidden';
        scrollbar.firstChild.style.width= element.scrollWidth+'px';
        scrollbar.firstChild.style.paddingTop= '1px';
        scrollbar.firstChild.appendChild(document.createTextNode('\xA0'));
        scrollbar.onscroll= function() {
            element.scrollLeft= scrollbar.scrollLeft;
        };
        element.onscroll= function() {
            scrollbar.scrollLeft= element.scrollLeft;
        };
        element.parentNode.insertBefore(scrollbar, element);
    }

    DoubleScroll(document.getElementById('doublescroll'));
</script>
