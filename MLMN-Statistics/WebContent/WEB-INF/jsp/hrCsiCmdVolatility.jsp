<%@ include file="/includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    

<ul class="ui-tabs-nav">
<c:choose>
	<c:when test="${function == 'csi-cpu'}">
		<li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/report/core/csi-cmd-volatility/csi-cpu.htm"><span>CPU Usage</span></a></li>
		<li class=""><a href="${pageContext.request.contextPath}/report/core/csi-cmd-volatility/csi-mem.htm"><span>Memory Usage</span></a></li>
		
	</c:when>
 	<c:when test="${function == 'csi-mem'}">
		<li class=""><a href="${pageContext.request.contextPath}/report/core/csi-cmd-volatility/csi-cpu.htm"><span>CPU Usage</span></a></li>
		<li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/report/core/csi-cmd-volatility/csi-mem.htm"><span>Memory Usage</span></a></li>
		
	</c:when>
 	<c:otherwise></c:otherwise>
</c:choose>
</ul>
<title>CSI CMD CPU, Memory Usage</title>
<content tag="heading">CSI CMD CPU, Memory Usage</content>

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
			
			
			</td>
			<td align="right">
				<fmt:message key="Refresh"/>&nbsp;
				<input type="text" id="autoRefresh" name="autoRefresh" value="${autoRefresh}" style="width: 6%" maxlength="4"/>&nbsp;giây
				<input class="button" type="submit" name="filter" value="<fmt:message key="Cập nhật"/>" />
			</td>	
					
		</tr>
</table>
<br>
<div  style="overflow: auto;">
<display:table name="${vRpCsicmdList}" id="vRpCsicmdList" requestURI="" pagesize="100" class="simple2" export="true" sort="external" defaultsort="1">
				<display:column property="region" titleKey="TT" sortable="true" headerClass="master-header-1"/>	
				<display:column property="type" titleKey="Type" sortable="true" headerClass="master-header-1"/>	    
				
				<c:choose>
					<c:when test="${function == 'csi-cpu'}">
						<display:column property="previousCpuused" titleKey="Previous CPU(%)" sortable="true" headerClass="master-header-2"/>
			    		<display:column property="cpuused" titleKey="CPU Usage(%)" sortable="true" headerClass="master-header-2"/>
			    		<display:column property="chenhLechCpu" titleKey="Chênh lệch CPU(%)" sortable="true" headerClass="master-header-2"/>
					</c:when>
					<c:when test="${function == 'csi-mem'}">
						<display:column property="previousMemused" titleKey="Previous Memory(%)" sortable="true" headerClass="master-header-2"/>
			    		<display:column property="memused" titleKey="Memory Usage(%)" sortable="true" headerClass="master-header-2"/>
						<display:column property="chenhLechMem" titleKey="Chênh lệch Memory(%)" sortable="true" headerClass="master-header-2"/>
					</c:when>
					<c:otherwise></c:otherwise>
			</c:choose>
			</display:table>
</div>
<table border="0" width="100%" cellspacing="0" cellpadding="0" class="form">
<c:choose>
		<c:when test="${function == 'csi-cpu'}">
			<tr>
				<td>
					<div id="chartUtilIn" style="width: 98%; margin: 1em auto"></div>
				</td>
				
			</tr>
		</c:when>
		<c:when test="${function == 'csi-mem'}">
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
		<c:when test="${function == 'csi-cpu'}">
			${chartUtilIn}
		</c:when>
		<c:when test="${function == 'csi-mem'}">
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