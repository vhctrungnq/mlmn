<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%--<title>plsql error log list</title>
<content tag="heading">GIÁM SÁT LỖI THỦ TỤC PL_SQL</content>
 --%>
 <title>${title}</title>
<content tag="heading">${title}</content> 	

<form:form commandName="filter" method="post" action="plsqlError.htm">
	<table style="width:100%;" class="form">
		<tr>
		    <td style="width:70px;">
            	<fmt:message key="plsqlError.startDate"/>
            </td>
            <td  class="wid15">
            	<input value="${startDate}" name="startDate" id="startDate" size="16" maxlength="16">
			    <img alt="calendar" title="Click to choose the start date" id="chooseStartDate" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
            </td>
            <td style="width:70px; padding-left:5px;">
            	<fmt:message key="plsqlError.endDate"/>
            </td>
            <td  class="wid15">
            	<input value="${endDate}" name="endDate" id="endDate" size="16" maxlength="16">
	            <img alt="calendar" title="Click to choose the end date" id="chooseEndDate" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
            </td>
            <td style="width:100px; padding-left:5px;">
            	<fmt:message key="plsqlError.proc"/>
            </td>
            <td  style="width:150px;"><form:input path="proc"  name="proc" id="proc" size="20"/></td>  
            <td>
            	<input class="button" type="submit" class="button" value="<fmt:message key="button.search"/>" />
            </td>
          
        </tr>		
	</table>
</form:form>
<br/>

<div  style="overflow: auto;" class="tableStandar">
<display:table name="${plsqlErrorLogList}" id="plsqlErrorLog"  requestURI="" pagesize="100"  export="true" partialList="true" size="${totalSize}" sort="external" defaultsort="1">
    <display:column title="STT">
				<c:out value="${plsqlErrorLog_rowNum + startRecord}" />
	</display:column>
    <display:column property="logTime" format="{0,date,dd/MM/yyyy HH:mm}"  titleKey="plsqlError.logTime"  sortable="true" sortName="LOG_TIME"/>        
    <display:column property="proc"  titleKey="plsqlError.proc"  sortable="true" sortName="PROC"/>
    <display:column property="errCode"  titleKey="plsqlError.errCode"  sortable="true" sortName="ERR_CODE"/>        
    <display:column property="errMsg"  titleKey="plsqlError.errMsg"  sortable="true" sortName="ERR_MSG"/>
    <display:column property="description"  titleKey="plsqlError.description"  sortable="true" sortName="DESCRIPTION"/>
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