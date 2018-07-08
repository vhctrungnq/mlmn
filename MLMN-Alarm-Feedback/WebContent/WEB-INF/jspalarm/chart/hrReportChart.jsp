<%@ include file="/commons/taglibs.jsp"   %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
   
<title>${titleSystem}</title>
<content tag="heading">${titleSystem}</content> 	
<div class="ui-tabs-panel">
<!-- type=NE,TEAM,PROVINCE,REGION,ALL -->
<form:form commandName="filter" method="post" action="${function}.htm?alarmtype=${alarmtype}">
 	<table >
		
			<tr> 
				<td style="width:70px"><fmt:message key="report.shour"/></td>
				<td style="width:50px">
						<input value="${startHour}" name="startHour" id="startHour" size="5" maxlength="5">
				</td>
				<td style="width:70px"><fmt:message key="report.day"/></td>
				<td style="width:110px">
						<input type ="text"  value="${startDate}" name="startDate" id="startDate" size="10" maxlength="10" style="width:70px">
			   			 <img alt="calendar" title="Click to choose the from date" id="chooseSDate" style="cursor: pointer;position: absolute;" src="${pageContext.request.contextPath}/images/calendar.png"/>
				</td>
				<td style="width:50px"><fmt:message key="report.ehour"/></td>
				<td style="width:50px">
						<input value="${endHour}" name="endHour" id="endHour" size="5" maxlength="5">
				</td>
				<td style="padding-left: 5px;width:30px"><fmt:message key="alarmLog.sdateT"/></td>
				<td style="width:110px">
					<input type ="text"  value="${endDate}" name="endDate" id="endDate" size="10" maxlength="10" style="width:70px">
			   		<img alt="calendar" title="Click to choose the to date" id="chooseEDate" style="cursor: pointer; position: absolute;" src="${pageContext.request.contextPath}/images/calendar.png"/>
				</td>
				<td style="padding-left: 5px;">
					<input class="button" type="submit" id="button" value="<fmt:message key="global.form.timkiem"/>" />
				</td>
			</tr>
		</table>
	</form:form>
</div>

<div id="availChart" style="width: 100%; margin: 2em auto"></div>
<div class="clear"></div>
<div><b>Danh sách số liệu</b></div>
<div id="doublescroll">
	 <display:table name="${dataChartList}" class="simple2" id="item" requestURI="" pagesize="50" sort="external" defaultsort="1" export="true">
	  	<display:column class="centerColumnMana" titleKey="global.list.STT"> <c:out value="${item_rowNum}"/> </display:column>
	  	<display:column property="day" format="{0,date,dd/MM/yyyy}" title="Day" sortable="true" sortName="DAY"/>
		<display:column property="hour" title="Hour" sortable="true" sortName="HOUR" />
		<display:column property="a1" title="A1" sortable="true" sortName="A1" />
		<display:column property="a2" title="A2" sortable="true" sortName="A2" />
		<display:column property="a3" title="A3" sortable="true" sortName="A3" />
		<display:column property="a4" title="A4" sortable="true" sortName="A4" />
		<display:column property="total" title="Total" sortable="true" sortName="TOTAL" />
	   	<display:setProperty name="export.csv.include_header" value="true" />
		<display:setProperty name="export.excel.include_header" value="true" />
		<display:setProperty name="export.xml.include_header" value="true" />
		<display:setProperty name="export.xml.filename" value="${exportFileName}.xml" />
		<display:setProperty name="export.csv.filename" value="${exportFileName}.csv" />
		<display:setProperty name="export.excel.filename" value="${exportFileName}.xls" />
	</display:table>
</div>
	

<script type="text/javascript" src="${pageContext.request.contextPath}/js/highchart2.3.3/highcharts.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/highchart2.3.3/modules/exporting.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar_en.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar_setup.js"></script>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/calendar-blue.css" />
<script type="text/javascript">
Calendar.setup({
    inputField		:	"startDate",	// id of the input field
    ifFormat		:	"%d/%m/%Y",   	// format of the input field
    button			:   "chooseStartDate",  	// trigger for the calendar (button ID)
    singleClick		:   false					// double-click mode
});
Calendar.setup({
    inputField		:	"endDate",	// id of the input field
    ifFormat		:	"%d/%m/%Y",   	// format of the input field
    button			:   "chooseEndDate",  	// trigger for the calendar (button ID)
    singleClick		:   false					// double-click mode
});
</script>

${availChart}

