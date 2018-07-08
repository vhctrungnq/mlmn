<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<title>${title}</title>
<content tag="heading">${title}</content> 	

<form:form commandName="filter" method="post" action="coreService.htm">
	<table style="width:100%;" class="form">
		<tr>
		    <td style="width:170px;">
            	<fmt:message key="coreLog.startDate"/>
            </td>
            <td style="width:170px;">
            	<input value="${startDate}" name="startDate" id="startDate" size="16" maxlength="20">
			    <img alt="calendar" title="Click to choose the start date" id="chooseStartDate" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
            </td>
            <td style="width:70px; padding-left:5px;">
            	<fmt:message key="coreLog.endDate"/>
            </td>
            <td style="width:170px;">
            	<input value="${endDate}" name="endDate" id="endDate" size="16" maxlength="20">
	            <img alt="calendar" title="Click to choose the end date" id="chooseEndDate" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
            </td>
            <td >
            	<input type="submit" class="button" id="submit" value="<fmt:message key="button.search"/>"/>
            </td>
        </tr>		
	</table>
</form:form>
<div class="tableStandar">
<display:table name="${coreLogList}" id="coreLog"  requestURI="" pagesize="100"  export="true" partialList="true" size="${totalSize}" sort="external" defaultsort="1">
    <display:column title="STT">
				<c:out value="${coreLog_rowNum + startRecord}" />
	</display:column>
	<display:column property="logTime" format="{0,date,dd/MM/yyyy HH:mm}" titleKey="coreLog.logTime"  sortable="true" sortName="LOG_TIME"/>        
    <display:column property="selectionTime" format="{0,date,dd/MM/yyyy HH:mm}" titleKey="coreLog.selectionTime"  sortable="true" sortName="SELECTION_TIME"/>
    <display:column property="selectionCount" titleKey="coreLog.selectionCount"  sortable="true" sortName="SELECTION_COUNT"/>
    <display:column property="convertTime" format="{0,date,dd/MM/yyyy HH:mm}" titleKey="coreLog.convertTime"  sortable="true" sortName="CONVERT_TIME"/>        
    <display:column property="convertCount" titleKey="coreLog.convertCount"  sortable="true" sortName="CONVERT_COUNT"/>
    <display:column property="importTime" format="{0,date,dd/MM/yyyy HH:mm}" titleKey="coreLog.importTime"  sortable="true" sortName="IMPORT_TIME"/>
    <display:column property="importCount" titleKey="coreLog.importCount"  sortable="true" sortName="IMPORT_COUNT"/>
    <display:column property="finishTime" format="{0,date,dd/MM/yyyy HH:mm}" titleKey="coreLog.finishTime"  sortable="true" sortName="FINISH_TIME"/>
   <display:setProperty name="export.csv.include_header" value="true" />
	<display:setProperty name="export.excel.include_header" value="true" />
	<display:setProperty name="export.xml.include_header" value="true" />
	<display:setProperty name="export.xml.filename" value="${exportFileName}.xml" />
	<display:setProperty name="export.csv.filename" value="${exportFileName}.csv" />
	<display:setProperty name="export.excel.filename" value="${exportFileName}.xls" />

</display:table>
</div>

<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar_en.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar_setup.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/chosen.jquery.js" ></script>

<link rel="stylesheet" type="text/css" media="all" href="${pageContext.request.contextPath}/styles/calendar-blue.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/chosen.css"/>

<script type="text/javascript">
Calendar.setup({
    inputField		:	"startDate",	// id of the input field
    ifFormat		:	"%d/%m/%Y %H:%M",   	// format of the input field
    button			:   "chooseStartDate",  	// trigger for the calendar (button ID)
    showsTime		:	true,
    singleClick		:   false					// double-click mode
});

Calendar.setup({
    inputField		:	"endDate",	// id of the input field
    ifFormat		:	"%d/%m/%Y %H:%M",   	// format of the input field
    button			:   "chooseEndDate",   	// trigger for the calendar (button ID)
    showsTime		:	true,
    singleClick		:   false					// double-click mode
});
</script>
<script type="text/javascript">
function xl(){
	var sub = document.getElementById("submit");
	sub.focus();
} 

function focusIt()
{
  var mytext = document.getElementById("startDate");
  mytext.focus();
}

onload = focusIt;
</script>


