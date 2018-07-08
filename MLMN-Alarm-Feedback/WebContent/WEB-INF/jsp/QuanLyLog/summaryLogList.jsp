<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<style>   
 
    #logs {
     visibility: visible !important;
    }
   
</style>
<title>${title}</title>
<content tag="heading">${title}</content> 	

<form:form commandName="filter" method="post" action="summary.htm" enctype="multipart/form-data" >
	<table style="width:100%;" class="form">
		<tr>
				<td style="width:70px;">
	            	<fmt:message key="summaryLog.startDate"/>
	            </td>
	            <td style="width:150px;">
	            	<input value="${startDate}" name="startDate" id="startDate" size="16" maxlength="16">
				    <img alt="calendar" title="Click to choose the start date" id="chooseStartDate" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
	            </td>
	            <td style="width:70px; padding-left:5px;">
	            	<fmt:message key="summaryLog.endDate"/>
	            </td>
	            <td style="width:150px;">
	            	<input value="${endDate}" name="endDate" id="endDate" size="16" maxlength="16">
		            <img alt="calendar" title="Click to choose the end date" id="chooseEndDate" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
	            </td>
	            <td style="width:70px; padding-left:5px;">
	            	<fmt:message key="summaryLog.errorCode"/>
	            </td>
	            <td  style="width:150px;">
	            	<form:input path="errorCode" id="errorCode" size="20" onchange="xl()"/>
	            </td>
	           <td style="width:100px; padding-left:5px;">
	            	<fmt:message key="summaryLog.procedureName"/>
	            </td>
	            <td style="width:150px;"><form:input path="procedureName" id="procedureName" size="40" onchange="xl()"/></td>
	            <td></td>
            </tr>
            <tr>
			<td><fmt:message key="summaryLog.logs"/></td>
			<td>    
			     <select name="logs" id="logs" onchange="xl()" style="width:140px;">
		              <c:forEach var="items" items="${summaryTypeList}">
			              <c:choose>
			                <c:when test="${items.value == logs}">
			                    <option value="${items.value}" selected="selected">${items.value}</option>
			                </c:when>
			                <c:otherwise>
			                    <option value="${items.value}">${items.value}</option>
			                </c:otherwise>
			              </c:choose>
					    </c:forEach>
		        </select>
        	</td>
        	<td style="padding-left:5px;"><fmt:message key="summaryLog.result"/></td>
        	<td>  <form:input path="result" id="result" size="20" onchange="xl()" maxlength="1000"/></td>
        	<td style="padding-left:5px;"><fmt:message key="summaryLog.sqlStatement"/></td>
        	<td colspan="3"><form:input path="sqlStatement"  id="sqlStatement" size="83" maxlength="3000" onchange="xl()"/></td>	
            <td style="padding-left:5px;"><input class="button" type="submit" class="button" value="<fmt:message key="button.search"/>" /></td>
        </tr>
	</table>
</form:form>
<br/>
<div id="result" style="overflow: auto;">
<display:table name="${summaryLogList}" id="summaryLog" class="simple2" requestURI="" pagesize="100"  export="true" partialList="true" size="${totalSize}" sort="external" defaultsort="1">
    <display:column title="STT">
				<c:out value="${summaryLog_rowNum + startRecord}" />
	</display:column>
	<display:column property="logTime" format="{0,date,dd/MM/yyyy HH:mm}" titleKey="summaryLog.logTime"  sortable="true" sortName="LOG_TIME"/>        
    <display:column property="errorCode" titleKey="summaryLog.errorCode"  sortable="true" sortName="ERROR_CODE"/>
    <display:column property="procedureName" titleKey="summaryLog.procedureName"  sortable="true" sortName="PROCEDURE_NAME"/>        
    <display:column property="day" format="{0,date,dd/MM/yyyy}" titleKey="summaryLog.day"  sortable="true" sortName="DAY"/>
    <display:column property="hour" titleKey="summaryLog.hour"  sortable="true" sortName="HOUR"/>        
    <display:column property="result" titleKey="summaryLog.result"  sortable="true" sortName="RESULT"/>
    <display:column property="sqlStatement" titleKey="summaryLog.sqlStatement"  sortable="true" sortName="SQL_STATEMENT"/>
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
 <script>
    $("#logs").change(function () {
    		window.location = '${pageContext.request.contextPath}/log/summary.htm?logs=' + $("#logs").val();
    });
</script>
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
