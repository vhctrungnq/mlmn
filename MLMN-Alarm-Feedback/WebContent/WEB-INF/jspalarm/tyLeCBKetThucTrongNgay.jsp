<%@ include file="/includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<title>${titleSystem}</title>
<content tag="heading">${titleSystem}</content>


<ul class="ui-tabs-nav">
<c:choose>
	<c:when test="${function == 'hien-tai'}">
		<li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/alarm/ty-le-canh-bao-da-ket-thuc/hien-tai.htm"><span><fmt:message key="title.tabControls.hienTai"/></span></a></li>
		<li class=""><a href="${pageContext.request.contextPath}/alarm/ty-le-canh-bao-da-ket-thuc/theo-ngay.htm"><span><fmt:message key="title.tabControls.theoNgay"/></span></a></li>
		<li class=""><a href="${pageContext.request.contextPath}/alarm/ty-le-canh-bao-da-ket-thuc/theo-thang.htm"><span><fmt:message key="title.tabControls.theoThang"/></span></a></li>
	</c:when>
 	<c:when test="${function == 'theo-ngay'}">
		<li class=""><a href="${pageContext.request.contextPath}/alarm/ty-le-canh-bao-da-ket-thuc/hien-tai.htm"><span><fmt:message key="title.tabControls.hienTai"/></span></a></li>
		<li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/alarm/ty-le-canh-bao-da-ket-thuc/theo-ngay.htm"><span><fmt:message key="title.tabControls.theoNgay"/></span></a></li>
		<li class=""><a href="${pageContext.request.contextPath}/alarm/ty-le-canh-bao-da-ket-thuc/theo-thang.htm"><span><fmt:message key="title.tabControls.theoThang"/></span></a></li>
	</c:when>
 	<c:when test="${function == 'theo-thang'}">
 		<li class=""><a href="${pageContext.request.contextPath}/alarm/ty-le-canh-bao-da-ket-thuc/hien-tai.htm"><span><fmt:message key="title.tabControls.hienTai"/></span></a></li>
		<li class=""><a href="${pageContext.request.contextPath}/alarm/ty-le-canh-bao-da-ket-thuc/theo-ngay.htm"><span><fmt:message key="title.tabControls.theoNgay"/></span></a></li>
		<li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/alarm/ty-le-canh-bao-da-ket-thuc/theo-thang.htm"><span><fmt:message key="title.tabControls.theoThang"/></span></a></li>
 	</c:when>
 	<c:otherwise></c:otherwise>
</c:choose>
</ul>
<div class="ui-tabs-panel">
<form:form id="filterController" method="post" action="${function}.htm">
<table border="0" width="100%" cellspacing="0" cellpadding="0" class="form">
		<tr>
			<td class="wid50"></td>
			<td class="wid50"></td> 
		</tr>
		<tr>
			<c:choose>
			<c:when test="${function == 'hien-tai'}">
				<td>
					<fmt:message key="baoCaoTongHop.khoangThoiGianKetThuc"/>&nbsp;
					<input type="text" id="startMinutes" name="startMinutes" value="${startMinutes}" style="width: 7%" maxlength="4"/>&nbsp;
					-&nbsp;
					<input type="text" id="endMinutes" name="endMinutes" value="${endMinutes}" style="width: 7%" maxlength="4"/>&nbsp;
					<input class="button" type="submit" name="filter" value="<fmt:message key="global.form.timkiem"/>" />
				</td>
				<td align="right">
					<fmt:message key="baoCaoTuan.autoRefresh"/>&nbsp;
					<input type="text" id="autoRefresh" name="autoRefresh" value="${autoRefresh}" style="width: 6%" maxlength="4"/>&nbsp;giây
					<span class="timeNowRate">${timeNow}</span>
				</td>
			</c:when>
			<c:when test="${function == 'theo-ngay'}">
				<td>
					<fmt:message key="baoCaoTongHop.khoangThoiGianKetThuc"/>&nbsp;
					<input type="text" id="startMinutes" name="startMinutes" value="${startMinutes}" style="width: 8%" maxlength="4"/>&nbsp;
					-&nbsp;
					<input type="text" id="endMinutes" name="endMinutes" value="${endMinutes}" style="width: 8%" maxlength="4"/>&nbsp;
					<fmt:message key="baoCaoTongHop.ngay"/>&nbsp;
					<input type="text" id="startDate" name="startDate" value="${startDate}" style="width: 16%" maxlength="20"/>
					<img alt="calendar" title="Click to choose the start date" id="chooseStartDate" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>&nbsp;
					<input class="button" type="submit" name="filter" value="<fmt:message key="global.form.timkiem"/>" />
          		</td>
          		<td align="right">
					<fmt:message key="baoCaoTuan.autoRefresh"/>&nbsp;
					<input type="text" id="autoRefresh" name="autoRefresh" value="${autoRefresh}" style="width: 6%" maxlength="4"/>&nbsp;giây
				</td>			
			 </c:when>
			 <c:when test="${function == 'theo-thang'}">
				 <td colspan="2">
		 			<fmt:message key="baoCaoTongHop.khoangThoiGianKetThuc"/>&nbsp;
					<input type="text" id="startMinutes" name="startMinutes" value="${startMinutes}" style="width: 4%" maxlength="4"/>&nbsp;
					-&nbsp;
					<input type="text" id="endMinutes" name="endMinutes" value="${endMinutes}" style="width: 4%" maxlength="4"/>&nbsp;
					Tháng&nbsp;
					<input type="text" id="startDate" name="startDate" value="${startDate}" style="width: 8%" maxlength="20"/>
   					<img alt="calendar" title="Click to choose the start date" id="chooseStartDate" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>&nbsp;
   					<input class="button" type="submit" name="filter" value="<fmt:message key="global.form.timkiem"/>" />	
          		 </td>
			 </c:when>
			<c:otherwise></c:otherwise>
			</c:choose>
		</tr>
		${html}
</table>
</form:form>
</div>

<script type="text/javascript" src="${pageContext.request.contextPath}/js/highchart2.3.3/highcharts.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/highchart2.3.3/modules/exporting.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/highchart2.3.3/themes/grid.js"></script>

${chartdivScript}

<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar_en.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar_setup.js"></script>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/calendar-blue.css" />
<script type="text/javascript">
$(document).ready(function(){
	
	var functionCalendar = '<c:out value="${function}"/>';
	if(functionCalendar == 'hien-tai'){
		var autoRefresh = $('#autoRefresh').val();	
		setTimeout(function(){
			$('#filterController').submit();
		}, 
		autoRefresh * 1000);
		}
	if(functionCalendar == 'theo-ngay'){
		Calendar.setup({
		    inputField		:	"startDate",	// id of the input field
		    ifFormat		:	"%d/%m/%Y",   	// format of the input field
		    button			:   "chooseStartDate",  	// trigger for the calendar (button ID)
		    singleClick		:   false					// double-click mode
		});

		var autoRefresh = $('#autoRefresh').val();	
		setTimeout(function(){
			$('#filterController').submit();
		}, 
		autoRefresh * 1000);
	}
	if(functionCalendar == 'theo-thang'){
		Calendar.setup({
		    inputField		:	"startDate",	// id of the input field
		    ifFormat		:	"%m/%Y",   	// format of the input field
		    button			:   "chooseStartDate",  	// trigger for the calendar (button ID)
		    singleClick		:   false					// double-click mode
		});
		
		}
	
});

function focusIt()
{
  var mytext = document.getElementById("startMinutes");
  mytext.focus();
}

onload = focusIt;
</script>

