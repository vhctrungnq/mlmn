<%@ include file="/commons/taglibs.jsp"   %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<title>LẤY DỮ LIỆU TỪ FEEDBACK TẬP TRUNG</title>
<content tag="heading">LẤY DỮ LIỆU TỪ FEEDBACK TẬP TRUNG</content>
<form action="getDataFeedback.htm" method="POST">
	<table border="0" cellspacing="5" cellpadding="0" width="100%">
		<tr>
			<td class="wid30 mwid110" align="right">Trạng thái của feedback</td>
			<td>
				<select id="status" name="status" class="wid30">
					<option value="">--Tất cả--</option>
       				<c:forEach var="items" items="${getStatusFbList}">
		              	<c:choose>
		                <c:when test="${items.value == status}">
		                    <option value="${items.value}" selected="selected">${items.name}</option>
		                </c:when>
		                <c:otherwise>
		                    <option value="${items.value}">${items.name}</option>
		                </c:otherwise>
		              	</c:choose>
			    	</c:forEach>
				</select>
			</td>
		</tr>
		<tr>
			<td align="right">Từ ngày</td>
			<td>
				<input type="text" id="startDate" name="startDate" value="${startDate}" class="wid20" maxlength="20"/>
				<img alt="calendar" title="Click to choose the start date" id="chooseStartDate" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
			</td>
		</tr>
		<tr>
			<td align="right">Đến ngày</td>
			<td>
				<input type="text" id="endDate" name="endDate" value="${endDate}" class="wid20" maxlength="20"/>
	   			<img alt="calendar" title="Click to choose the end date" id="chooseEndDate" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
			</td>
		</tr>
		<tr>
			<td align="right">Tiến trình</td>
			<td>
				<input type="submit" value="Lấy dữ liệu" class="button" >&nbsp;
				<input class="button" type="button" value="<fmt:message key="global.button.quayLai"/>" onClick='window.location="list.htm"'>
			</td>
		</tr>
		<tr>
			<td style="max-width:800px;word-wrap: break-word;" colspan="2">
				<span style="color: blue;font-weight: bold;">${result}</span>
			</td>
		</tr>
	</table>
</form>	
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar_en.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar_setup.js"></script>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/calendar-blue.css" />

<script type="text/javascript">
$(document).ready(function(){
	Calendar.setup({
	    inputField		:	"startDate",	// id of the input field
	    ifFormat		:	"%d/%m/%Y %H:%M:%S",   	// format of the input field
	    button			:   "chooseStartDate",  	// trigger for the calendar (button ID)
	    showsTime		:	true,
	    singleClick		:   false					// double-click mode
	});

	Calendar.setup({
	    inputField		:	"endDate",	// id of the input field
	    ifFormat		:	"%d/%m/%Y %H:%M:%S",   	// format of the input field
	    button			:   "chooseEndDate",  	// trigger for the calendar (button ID)
	    showsTime		:	true,
	    singleClick		:   false					// double-click mode
	});
});

function focusIt()
{
  var mytext = document.getElementById("status");
  mytext.focus();
}

onload = focusIt;

</script>

