<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<title>core log list</title>
<content tag="heading"> GIÁM SÁT QUÁ TRÌNH XỬ LÝ CORE SERVICE</content>

<form:form commandName="filter" method="post" action="coreService.htm">
	<table width="100%" class="form">
		<tr>
		    <td align="left">
            	Thời gian ghi logs Từ ngày <input value="${startDate}" name="startDate" id="startDate" size="16" maxlength="20">
			    <img alt="calendar" titleKey="Click to choose the start date" id="chooseStartDate" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
            	&nbsp;&nbsp;Tới ngày <input value="${endDate}" name="endDate" id="endDate" size="16" maxlength="20">
	            <img alt="calendar" titleKey="Click to choose the end date" id="chooseEndDate" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
            	&nbsp;&nbsp;<input type="submit" class="button" name="filter" value="Tìm kiếm"/>
            </td>
        </tr>		
	</table>
</form:form>
<br/>
<div  style="overflow: auto;">
<display:table name="${coreLogList}" id="coreLog" class="simple2" export="true" requestURI="" pagesize="100" sort="external" defaultsort="1" size="${totalSize}">
    <display:column property="logTime" format="{0,date,dd/MM/yyyy HH:mm}" titleKey="THỜI GIAN GHI LOG"/>        
    <display:column property="selectionTime" format="{0,date,dd/MM/yyyy HH:mm}" titleKey="THỜI GIAN BẮT ĐẦU SELECT FILE"/>
    <display:column property="selectionCount" titleKey="SỐ LƯỢNG FILE SELECT"/>
    <display:column property="convertTime" format="{0,date,dd/MM/yyyy HH:mm}" titleKey="THỜI GIAN BẮT ĐẦU CONVERT FILE"/>        
    <display:column property="convertCount" titleKey="SỐ LƯỢNG FILE CONVERT"/>
    <display:column property="importTime" format="{0,date,dd/MM/yyyy HH:mm}" titleKey="THỜI GIAN BẮT ĐẦU IMPORT FILE"/>
    <display:column property="importCount" titleKey="SỐ LƯỢNG FILE IMPORT"/>
    <display:column property="finishTime" format="{0,date,dd/MM/yyyy HH:mm}" titleKey="THỜI GIAN KẾT THÚC"/>
    <display:setProperty name="export.csv.filename" value="CoreLog.csv"/>
	<display:setProperty name="export.excel.filename" value="CoreLog.xls"/>
	<display:setProperty name="export.xml.filename" value="CoreLog.xml"/>
</display:table>
</div>

<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/calendar.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/calendar_en.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/calendar_setup.js"></script>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/calendar-blue.css" />
<script>
    $("#user").change(function () {
      window.location = '${pageContext.request.contextPath}/log/checkjob/detail.htm?user=' + $("#user").val();
      
      });  
    ${highlight} 
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


