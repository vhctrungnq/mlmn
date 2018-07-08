<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    

<title>GGSN Weekly Report</title>
<content tag="heading">GGSN QOS WEEKLY REPORT</content>

<ul class="ui-tabs-nav">
  <li class=""><a href="${pageContext.request.contextPath}/report/core/ggsn/hr.htm?region=${msc.region}&ggsnName=${ggsn.ggsnName}&startDate=${startDate}&startHour=${startHour}&endDate=${endDate}&endHour=${endHour}"><span>Báo cáo giờ</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/core/ggsn/dy.htm?region=${msc.region}&day=${day}"><span>Báo cáo ngày</span></a></li>
  <li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/report/core/ggsn/wk.htm?region=${msc.region}&ggsnName=${ggsn.ggsnName}&startWeek=${startWeek}&startYear=${startYear}&endWeek=${endWeek}&endYear=${endYear}"><span>Báo cáo tuần</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/core/ggsn/mn.htm?region=${msc.region}"><span>Báo cáo tháng</span></a></li>
</ul>

<div class="ui-tabs-panel">
	
		<table width="100%" class="form">
			<tr>
			    <td align="left">
			  <form method="get" action="wk.htm" name = "frmSample" onSubmit = "return ValidateForm()">			  			
			      Trung tâm
					<select name="region" id="region" onchange="xl()">
					<option value="">--Tất cả--</option>
					        <c:forEach var="msc" items="${Regionlist}">
					              <c:choose>
					                <c:when test="${msc.region == region}">
					                    <option value="${msc.region}" selected="selected">${msc.region}</option>
					                </c:when>
					                <c:otherwise>
					                    <option value="${msc.region}">${msc.region}</option>
					                </c:otherwise>
					              </c:choose>
					    </c:forEach>
					</select>
					GGSN
					<select name="ggsnName" id="ggsnName" onchange="xl()">
					<option value="">--Select GGSN--</option>
					        <c:forEach var="ggsn" items="${Ggsnlist}">
					              <c:choose>
					                <c:when test="${ggsn.ggsnName == ggsnName}">
					                    <option value="${ggsn.ggsnName}" selected="selected">${ggsn.ggsnName}</option>
					                </c:when>
					                <c:otherwise>
					                    <option value="${ggsn.ggsnName}">${ggsn.ggsnName}</option>
					                </c:otherwise>
					              </c:choose>
					    </c:forEach>
					</select>
	            	&nbsp;Từ tuần <input value="${startWeek}" name="startWeek" id="startWeek" size="2" maxlength="2" onchange ="javascript:checkNumber(document.frmSample.startWeek);">
					<img alt="calendar" title="Click to choose the start week number" id="chooseStartWeek" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
	            	&nbsp;Năm <input value="${startYear}" name="startYear" id="startYear" size="4" maxlength="4" onchange ="javascript:checkNumber(document.frmSample.startYear);">
	            	&nbsp;Tới tuần <input value="${endWeek}" name="endWeek" id="endWeek" size="2" maxlength="2" onchange ="javascript:checkNumber(document.frmSample.endWeek);">
					<img alt="calendar" title="Click to choose the end week number" id="chooseEndWeek" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
	            	&nbsp;Năm <input value="${endYear}" name="endYear" id="endYear" size="4" maxlength="4" onchange ="javascript:checkNumber(document.frmSample.endYear);">
	            	&nbsp;<input type="submit" class="button" name="submit" id="submit" value="View Report"/>
	          </form>
	            </td>
	        </tr>		
		</table>
	<br/>
</div>
	
	
<div  style="overflow: auto;">
		<display:table name="${WkGGSNQoS}" id="WkGGSNQoS" requestURI="" pagesize="100" class="simple3" export="true">
		    <display:column property="week" titleKey="WEEK"/>
	    	<display:column property="year" titleKey="YEAR"/>
		    <display:column property ="region"  titleKey="TT" />
		    <display:column property ="ggsnid"  titleKey="GGSNID" headerClass="hide" class="hide"/>
		    <display:column property ="ggsnName"  titleKey="GGSN NAME" />
		     <display:column property="activePdp" titleKey="Active PDP"/>
		    <display:column property="pdpActSucc" titleKey="PDP Act Succ (%)"/>
		    <display:column property="pdpUtility" titleKey="PDP Utility (%)"/>
		    <display:column property="dlThroughput" titleKey="Dowlink Throughput (Mbps)"/>
		    <display:column property="ulThroughput" titleKey="Uplink Throughput (Mbps)"/>
		    <display:column property="throughputUtility" titleKey="Throughput Utility (%)"/>
		    <display:column property="backgroudUp" titleKey="Traffic Handling Background Up"/>
		    <display:column property="backgroudDown" titleKey="Traffic Handling Background Down"/>
		    <display:column property="interPrio1Up" titleKey="Traffic Handling Interactive Prio 1 Up"/>
		    <display:column property="interPrio1Down" titleKey="Traffic Handling Interactive Prio 1 Down"/>
		    <display:column property="interPrio2Up" titleKey="Traffic Handling Interactive Prio 2 Up"/>
		    <display:column property="interPrio2Down" titleKey="Traffic Handling Interactive Prio 2 Down "/>
		    <display:column property="interPrio3Up" titleKey="Traffic Handling Interactive Prio 3 Up"/>
		    <display:column property="interPrio3Down" titleKey="Traffic Handling Interactive Prio 3 Down"/>
		    <display:column property="streamingUp" titleKey="Traffic Handling Streaming Up"/>
		    <display:column property="streamingDown" titleKey="Traffic Handling Streaming Down"/>
		    <display:column property="converUp" titleKey="Traffic Handling Conversational Up"/>
		    <display:column property="converDown" titleKey="Traffic Handling Conversational Down"/>
		    <display:column property="cpuRate" titleKey="CPU (%)"/>
		    <display:column property="ipAddUtiltity" titleKey="IP ADD Utility (%)"/>
		</display:table>
	</div>
	
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/calendar.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/calendar_en.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/calendar_setup.js"></script>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/calendar-blue.css" />

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
	
	 $(function() {
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

				lastXhr = $.getJSON( "${pageContext.request.contextPath}/ajax/getBsc.htm", request, function( data, status, xhr ) {
					cache[ term ] = data;
					if ( xhr === lastXhr ) {
						response( data );
					}
				});
			}
		});
	}); 
</script>
