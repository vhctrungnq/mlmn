<%@ include file="/includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    

<ul class="ui-tabs-nav">
<c:choose>
	<c:when test="${function == 'ipbb-in'}">
		<li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/report/bieu-do-giam-sat-ipbb/ipbb-in.htm"><span><fmt:message key="Traffic In MaxUtilzation"/></span></a></li>
		<li class=""><a href="${pageContext.request.contextPath}/report/bieu-do-giam-sat-ipbb/ipbb-out.htm"><span><fmt:message key="Traffic Out MaxUtilzation"/></span></a></li>
		
	</c:when>
 	<c:when test="${function == 'ipbb-out'}">
		<li class=""><a href="${pageContext.request.contextPath}/report/bieu-do-giam-sat-ipbb/ipbb-in.htm"><span><fmt:message key="Traffic In MaxUtilzation"/></span></a></li>
		<li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/report/bieu-do-giam-sat-ipbb/ipbb-out.htm"><span><fmt:message key="Traffic Out MaxUtilzation"/></span></a></li>
		
	</c:when>
 	<c:otherwise></c:otherwise>
</c:choose>
</ul>
<title>IPBB Data- Max utilization</title>
<content tag="heading">IPBB Data- Max utilization </content>

<div class="ui-tabs-panel">
<form:form id="filterController" method="post" action="${function}.htm">
<table border="0" width="100%" cellspacing="0" cellpadding="0" class="form">
		<tr>
			<td class="wid50"></td>
			<td class="wid50"></td> 
		</tr>
		<tr>
			<td>
			&nbsp;Ngày <input value="${date}" name="date" id="date" size="10" maxlength="10">
			 <img alt="calendar" title="Click to choose the start date" id="chooseStartDate" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>&nbsp;
			
			<input class="button" type="submit" name="filter" value="<fmt:message key="Hiển thị"/>" />
			</td>
			<td align="right">
				<fmt:message key="Refresh"/>&nbsp;
				<input type="text" id="autoRefresh" name="autoRefresh" value="${autoRefresh}" style="width: 6%" maxlength="4"/>&nbsp;giây
			</td>			
		</tr>
</table>
<br>
<div  style="overflow: auto;">
<display:table name="${vRpipbbdata}" id="vRpipbbid" requestURI="" pagesize="100" class="simple2" export="true" sort="external" defaultsort="1">
	<display:column property="day" format="{0,date,dd/MM/yyyy}" titleKey="DAY" sortable="true" headerClass="master-header-1"/>		    
    <display:column property="direction" titleKey="Direction"   sortable="true" headerClass="master-header-1"/>
    <display:column property="delayAvg" titleKey="Delay Average" sortable="true" headerClass="master-header-2"/>
    <display:column property="delayMax" titleKey="Delay Maximum" sortable="true" headerClass="master-header-2"/>
	<c:choose>
			<c:when test="${function == 'ipbb-in'}">
				<display:column property="inKbitSecond" titleKey="InkbitSecond" sortable="true" headerClass="master-header-2"/>
	    		<display:column property="inMaxUtilization" titleKey="Max Utilization" sortable="true" headerClass="master-header-2"/>
			</c:when>
			<c:when test="${function == 'ipbb-out'}">
				<display:column property="outKbitSecond" titleKey="OutkbitSecond" sortable="true" headerClass="master-header-2"/>
	    		<display:column property="outMaxUtilization" titleKey="Max Utilization" sortable="true" headerClass="master-header-2"/>
			</c:when>
			<c:otherwise></c:otherwise>
	</c:choose>
</display:table>
</div>
<table border="0" width="100%" cellspacing="0" cellpadding="0" class="form">
<c:choose>
		<c:when test="${function == 'ipbb-in'}">
			<tr>
				<td>
					<div id="chartUtilIn" style="width: 98%; margin: 1em auto"></div>
				</td>
				
			</tr>
		</c:when>
		<c:when test="${function == 'ipbb-out'}">
			<tr>
			<td>
					<div id="chartUtilOut" style="width: 98%; margin: 1em auto"></div>
				</td>
				
			</tr>
		</c:when>
		<c:otherwise></c:otherwise>
</c:choose>
</table>
</form:form>
</div>

<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/highcharts.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/exporting.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/themes/grid.js"></script>

<c:choose>
		<c:when test="${function == 'ipbb-in'}">
			${chartUtilIn}
		</c:when>
		<c:when test="${function == 'ipbb-out'}">
			${chartUtilOut}
		</c:when>
		<c:otherwise></c:otherwise>
</c:choose>


<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/calendar.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/calendar_en.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/calendar_setup.js"></script>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/calendar-blue.css" />

<script type="text/javascript">
Calendar.setup({
    inputField		:	"date",	// id of the input field
    ifFormat		:	"%d/%m/%Y",   	// format of the input field
    button			:   "chooseStartDate",  	// trigger for the calendar (button ID)
    singleClick		:   false					// double-click mode
});
var autoRefresh = $('#autoRefresh').val();	
	setTimeout(function(){
		$('#filterController').submit();
	}, 
	autoRefresh * 1000);
</script>