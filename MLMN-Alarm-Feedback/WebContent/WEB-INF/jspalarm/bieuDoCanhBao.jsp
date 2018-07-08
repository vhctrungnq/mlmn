<%@ include file="/includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<title>${titleSystem}</title>
<content tag="heading">${titleSystem}</content>

<ul class="ui-tabs-nav">
<c:choose>
	<c:when test="${function == 'theo-gio'}">
		<li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/alarm/bieu-do-canh-bao/theo-gio.htm"><span><fmt:message key="title.tabControls.theoGio"/></span></a></li>
		<li class=""><a href="${pageContext.request.contextPath}/alarm/bieu-do-canh-bao/theo-ngay.htm"><span><fmt:message key="title.tabControls.theoNgay"/></span></a></li>
	</c:when>
 	<c:when test="${function == 'theo-ngay'}">
		<li class=""><a href="${pageContext.request.contextPath}/alarm/bieu-do-canh-bao/theo-gio.htm"><span><fmt:message key="title.tabControls.theoGio"/></span></a></li>
		<li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/alarm/bieu-do-canh-bao/theo-ngay.htm"><span><fmt:message key="title.tabControls.theoNgay"/></span></a></li>
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
				<c:when test="${function == 'theo-gio'}">
					<td>
						<fmt:message key="baoCaoTongHop.ngay"/>&nbsp;
						<input type="text" id="startDate" name="startDate" value="${startDate}" style="width: 16%" maxlength="20"/>
		          		<img alt="calendar" title="Click to choose the start date" id="chooseStartDate" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>&nbsp;
		          		<input class="button" type="submit" name="filter" value="<fmt:message key="global.form.timkiem"/>" />
		          	</td>
		          	
          		</c:when>
			 	<c:when test="${function == 'theo-ngay'}">
				 	<td>
				 		<fmt:message key="baoCaoTongHop.tuNgay"/>&nbsp;
						<input type="text" id="startDate" name="startDate" value="${startDate}" style="width: 16%" maxlength="20"/>
		          		<img alt="calendar" title="Click to choose the start date" id="chooseStartDate" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>&nbsp;
						<fmt:message key="baoCaoTongHop.denNgay"/>&nbsp;
						<input type="text" id="endDate" name="endDate" value="${endDate}" style="width: 16%" maxlength="20"/>
		          		<img alt="calendar" title="Click to choose the end date" id="chooseEndDate" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>&nbsp;
		          		<input class="button" type="submit" name="filter" value="<fmt:message key="global.form.timkiem"/>" />
	          		</td>
	          		
			 	</c:when>
			 	<c:otherwise></c:otherwise>
			</c:choose>	
			<td align="right">
				<fmt:message key="baoCaoTuan.autoRefresh"/>&nbsp;
				<input type="text" id="autoRefresh" name="autoRefresh" value="${autoRefresh}" style="width: 6%" maxlength="4"/>&nbsp;giây
			</td>		
		</tr>
		${html}
		<tr>
			<td colspan="2"><h3><fmt:message key="title.soLuongCanhBao"/></h3></td>
		</tr>
		<tr>
			<td colspan="2">
				<div style="width:100%;overflow:auto; ">
					<display:table name="${soLuongCBList}" class="simple2" id="item" requestURI="" pagesize="15" sort="external" defaultsort="1" export="true" >
						<display:column class="centerColumnMana" titleKey="global.list.STT"> <c:out value="${item_rowNum}"/> </display:column>
						<display:column property="system" titleKey="baoCaoTuan.thietBi" sortable="true" sortName="SYSTEM"/>
						<display:column property="day" format="{0,date,dd/MM/yyyy}" titleKey="baoCaoTuan.ngay" sortable="true" sortName="DAY"/>
						<c:choose>
							<c:when test="${function == 'theo-gio'}">
								<display:column class="centerColumnMana" property="hour" titleKey="baoCaoTuan.gio" sortable="true" sortName="HOUR"/>
							</c:when>
						</c:choose>				
						<display:column class="centerColumnMana" property="alarmClass" titleKey="baoCaoTuan.mucDoCanhBao" sortable="true" sortName="ALARM_CLASS"/>
						<display:column class="centerColumnMana" property="qty" titleKey="baoCaoTuan.soLuong" sortable="true" sortName="QTY"/>
		    			
						<display:setProperty name="export.csv.include_header" value="true" />
						<display:setProperty name="export.excel.include_header" value="true" />
						<display:setProperty name="export.xml.include_header" value="true" />
						<display:setProperty name="export.xml.filename" value="${exportFileName}.xml" />
						<display:setProperty name="export.csv.filename" value="${exportFileName}.csv" />
						<display:setProperty name="export.excel.filename" value="${exportFileName}.xls" /> 
							
					</display:table>
				</div>
			</td>
		</tr>
</table>
</form:form>
</div>

<script type="text/javascript" src="${pageContext.request.contextPath}/js/highchart2.3.3/highcharts.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/highchart2.3.3/modules/exporting.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/themes/grid.js"></script>
${chartdivScript}

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

var functionCalendar = '<c:out value="${function}"/>';


var autoRefresh = $('#autoRefresh').val();	
setTimeout(function(){
	$('#filterController').submit();
}, 
autoRefresh * 1000);

if(functionCalendar == 'theo-ngay'){
	
	Calendar.setup({
	    inputField		:	"endDate",	// id of the input field
	    ifFormat		:	"%d/%m/%Y",   	// format of the input field
	    button			:   "chooseEndDate",  	// trigger for the calendar (button ID)
	    singleClick		:   false					// double-click mode
	});
}

function focusIt()
{
  var mytext = document.getElementById("startDate");
  mytext.focus();
}

onload = focusIt;

</script>

