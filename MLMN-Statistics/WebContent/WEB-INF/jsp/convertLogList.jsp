<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<title>convert log list</title>
<content tag="heading"> GIÁM SÁT QUÁ TRÌNH CONVERT</content>

<form:form commandName="filter" method="get" action="convert.htm">
	<table style="width:100%;" class="form">
		<tr>
		    <td align="left">
            	Từ ngày <input value="${startDate}" name="startDate" id="startDate" size="16" maxlength="20">
			    <img alt="calendar" titleKey="Click to choose the start date" id="chooseStartDate" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
            	&nbsp;&nbsp;Tới ngày <input value="${endDate}" name="endDate" id="endDate" size="16" maxlength="20">
	            <img alt="calendar" titleKey="Click to choose the end date" id="chooseEndDate" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
            	&nbsp;Mã trạng thái<form:input value="${statusCode}" path="statusCode" id="statusCode" size="16" maxlength="9"/>
            	&nbsp;Tên file<form:input value="${fileName}" path="fileName" name="fileName" id="fileName" size="16" maxlength="16"/>
            	&nbsp;&nbsp;<input type="submit" class="button" name="filter" id="submit" value="Tìm kiếm"/>
            </td>
        </tr>		
	</table>
</form:form>
<br/>

<div  style="overflow: auto;">
<display:table name="${convertLogList}" id="convertLog" class="simple2" export="true" requestURI="" pagesize="100" sort="external" defaultsort="1" size="${totalSize}">
    <display:column property="convertTime" format="{0,date,dd/MM/yyyy HH:mm}" titleKey="THỜI ĐIỂM"/>        
    <display:column property="fileName" titleKey="TÊN FILE"/>
    <display:column property="convertDuration" titleKey="THỜI GIAN (MILLISECONDS)"/>        
    <display:column property="originalSize" titleKey="KÍCH THƯỚC FILE TRƯỚC KHI CONVERT (BYTES)"/>
    <display:column property="convertedSize" titleKey="KÍCH THƯỚC FILE SAU KHI CONVERT (BYTES)"/>        
    <display:column property="statusCode" titleKey="MÃ TRẠNG THÁI"/>
    <display:setProperty name="export.csv.filename" value="ConvertLog.csv"/>
	<display:setProperty name="export.excel.filename" value="ConvertLog.xls"/>
	<display:setProperty name="export.xml.filename" value="ConvertLog.xml"/>
</display:table>
</div>

<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/calendar.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/calendar_en.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/calendar_setup.js"></script>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/calendar-blue.css" />

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

<script language = "Javascript">
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