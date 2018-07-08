<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<title>summary log list</title>
<content tag="heading">GIÁM SÁT QUÁ TRÌNH CHẠY THỦ TỤC SUMMARY</content>

<form:form commandName="filter" method="post" action="summary.htm">
	<table width="100%" class="form">
		<tr>
			<td align="left">Summary Logs</td>
				<td>            
				     <select name="logs" id="logs" onchange="xl()">
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
        	<td>Mã lỗi </td><td><form:input path="errorCode" id="errorCode" size="16" maxlength="30" onchange="xl()"/></td>
		    <td>Tên thủ tục</td><td> <form:input path="procedureName" id="procedureName" size="16" maxlength="30" onchange="xl()"/></td>
            <td>Kết quả</td><td> <form:input path="result" id="result" size="16" maxlength="30" onchange="xl()"/></td>
        </tr>
        <tr>
        	<td align="left">Câu lệnh</td><td><form:input path="sqlStatement" id="sqlStatement" size="16" maxlength="30" onchange="xl()"/></td>
        	<td>Từ ngày</td>
        	<td><input value="${startDate}" name="startDate" id="startDate" size="16" maxlength="20">
            	<img alt="calendar" titleKey="Click to choose the start date" id="chooseStartDate" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
        	<td>Đến ngày</td>
        	<td><input value="${endDate}" name="endDate" id="endDate" size="16" maxlength="20">
            	<img alt="calendar" titleKey="Click to choose the end date" id="chooseEndDate" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
            <td></td>
            <td><input type="submit" class="button" name="filter"  id="submit" value="Tìm kiếm"/></td>
        </tr>
	</table>
</form:form>
<br/>
<div id="result" style="overflow: auto;">
<display:table name="${summaryLogList}" id="summaryLog" class="simple2" export="true" requestURI="" pagesize="100" sort="external" defaultsort="1" size="${totalSize}">
    <display:column property="logTime" format="{0,date,dd/MM/yyyy HH:mm}" titleKey="THỜI GIAN"/>        
    <display:column property="errorCode" titleKey="MÃ LỖI"/>
    <display:column property="procedureName" titleKey="TÊN THỦ TỤC"/>        
    <display:column property="day" format="{0,date,dd/MM/yyyy}" titleKey="NGÀY"/>
    <display:column property="hour" titleKey="GIỜ"/>        
    <display:column property="result" titleKey="KẾT QUẢ"/>
    <display:column property="sqlStatement" titleKey="CÂU LỆNH" style="width:700px"/>
    <display:setProperty name="export.csv.filename" value="SummaryLog.csv"/>
	<display:setProperty name="export.excel.filename" value="SummaryLog.xls"/>
	<display:setProperty name="export.xml.filename" value="SummaryLog.xml"/>
</display:table>
</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/calendar.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/calendar_en.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/calendar_setup.js"></script>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/calendar-blue.css" />
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
<script type="text/javascript">