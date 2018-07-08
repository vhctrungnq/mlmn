<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<title>${title}</title>
<content tag="heading">${title}</content> 	
<form:form commandName="filter" method="post" action="import.htm">
	<table style="width:100%;" class="form">
		<tr>
		    <td style="width:70px;">
            	<fmt:message key="importLog.startDate"/>
            </td>
            <td class="wid15">
            	<input value="${startDate}" name="startDate" id="startDate" size="16" maxlength="16">
			    <img alt="calendar" title="Click to choose the start date" id="chooseStartDate" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
            </td>
            <td style="width:70px; padding-left:5px;">
            	<fmt:message key="importLog.endDate"/>
            </td>
            <td class="wid15">
            	<input value="${endDate}" name="endDate" id="endDate" size="16" maxlength="16">
	            <img alt="calendar" title="Click to choose the end date" id="chooseEndDate" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
            </td>
            <td style="width:70px; padding-left:5px;">
            	<fmt:message key="convertFile.fileName"/>
            </td>
            <td  style="width:150px;">
            	<input value="${fileName}" name="fileName" id="fileName" size="20"/>
            </td>
            <td style="width:100px; padding-left:5px;">
            	<fmt:message key="importLog.statusCode"/>
            </td>
            <td  style="width:150px;">
            	<input value="${statusCode}" name="statusCode" id="statusCode" size="20"/>
            </td>
             <td>
            	<input class="button" type="submit" class="button" value="<fmt:message key="button.search"/>" />
            </td>
        </tr>		
	</table>
</form:form>
<br/>
<div  class="tableStandar">
<display:table name="${importLogList}" id="importLog" requestURI="" pagesize="100"  export="true" partialList="true" size="${totalSize}" sort="external" defaultsort="1">
    <display:column title="STT">
				<c:out value="${importLog_rowNum + startRecord}" />
	</display:column>
	<display:column property="fileId" titleKey="importLog.fileId"  sortable="true" sortName="FILE_ID"/>        
    <display:column property="fileName" titleKey="convertFile.fileName"  sortable="true" sortName="FILE_NAME"/>        
    <display:column property="importTime" format="{0,date,dd/MM/yyyy HH:mm}" titleKey="importLog.importTime"  sortable="true" sortName="IMPORT_TIME"/>
    <display:column property="importDuration" titleKey="importLog.importDuration"  sortable="true" sortName="IMPORT_DURATION"/>        
    <display:column property="statusCode" titleKey="importLog.statusCode"  sortable="true" sortName="STATUS_CODE"/>
    <display:column property="importedResult" titleKey="importLog.importedResult"  sortable="true" sortName="IMPORTED_RESULT"/>
    <display:setProperty name="export.csv.include_header" value="true" />
	<display:setProperty name="export.excel.include_header" value="true" />
	<display:setProperty name="export.xml.include_header" value="true" />
	<display:setProperty name="export.xml.filename" value="${exportFileName}.xml" />
	<display:setProperty name="export.csv.filename" value="${exportFileName}.csv" />
	<display:setProperty name="export.excel.filename" value="${exportFileName}.xls" />
</display:table>
</div>
<script type="text/javascript" src="/VMSC2-Alarm-Feedback/js/calendar/calendar.js"></script>
<script type="text/javascript" src="/VMSC2-Alarm-Feedback/js/calendar/calendar_en.js"></script>
<script type="text/javascript" src="/VMSC2-Alarm-Feedback/js/calendar/calendar_setup.js"></script>
<script type="text/javascript" src="/VMSC2-Alarm-Feedback/js/calendar/chosen.jquery.js" ></script>

<link rel="stylesheet" type="text/css" media="all" href="/VMSC2-Alarm-Feedback/styles/calendar-blue.css" />
<link rel="stylesheet" type="text/css" href="/VMSC2-Alarm-Feedback/styles/chosen.css"/>

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

<script type = "text/javascript">
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