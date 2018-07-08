<%@ include file="/includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
  <style>   
 	#doublescroll { overflow: auto; overflow-y: hidden; }  
    #doublescroll p { margin: 0; padding: 1em; white-space: nowrap; }
</style>  
<title>${title}</title>
<content tag="heading">${title}</content>
<div>
<form method="post" action="tong_hop.htm">
		<table>
			<tr>
				<td style="width: 70px;">
					<fmt:message key="tongHopDip.tuNgay"/>	
				</td>
				<td style="width: 150px;">
					<input type="text" id="startDate" name="startDate" value="${startDate}" size="10" maxlength="10"/>
       						<img alt="calendar" title="Click to choose the start date" id="chooseStartDate" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
				</td>
				<td style="width: 80px; padding-left: 5px;"><fmt:message key="tongHopDip.denNgay"/></td>
				<td style="width: 150px;">
					<input type="text" id="endDate" name="endDate" value="${endDate}" size="10" maxlength="10"/>
       						<img alt="calendar" title="Click to choose the end date" id="chooseEndDate" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
				</td>
				<td><input class="button" type="submit" class="button"
					value="<fmt:message key="global.form.timkiem"/>" /></td>
			</tr>				
		</table>
</form>
</div>				
<div id="doublescroll" >
	 <display:table name="${tongHopList}" class="simple2" id="item" requestURI="" pagesize="50" export="true">
	 	<c:forEach var="i" items="${count}">
			<display:column property="[${i}]" class="centerColumnMana"/> 
		</c:forEach>
			
		<display:setProperty name="export.csv.include_header" value="false" />
		<display:setProperty name="export.excel.include_header" value="false" />
		<display:setProperty name="export.xml.include_header" value="false" />
		<display:setProperty name="export.xml.filename" value="${exportFileName}.xml" />
		<display:setProperty name="export.csv.filename" value="${exportFileName}.csv" />
		<display:setProperty name="export.excel.filename" value="${exportFileName}.xls" /> 
			
	</display:table>
</div>
				
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar_en.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar_setup.js"></script>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/calendar-blue.css" />

<link type="text/css" rel="Stylesheet" href="${pageContext.request.contextPath}/js/jquery/jquery-ui-1.8.23.custom.css" />
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery/jquery-ui.min-1.8.9.js"></script>

<script type="text/javascript">
Calendar.setup({
    inputField		:	"startDate",	// id of the input field
    ifFormat		:	"%d/%m/%Y",   	// format of the input field
    button			:   "chooseStartDate",  	// trigger for the calendar (button ID)
    singleClick		:   false					// double-click mode
});

Calendar.setup({
    inputField		:	"endDate",	// id of the input field
    ifFormat		:	"%d/%m/%Y",   	// format of the input field
    button			:   "chooseEndDate",  	// trigger for the calendar (button ID)
    singleClick		:   false					// double-click mode
});

function focusIt()
{
  var mytext = document.getElementById("startDate");
  mytext.focus();
}

onload = focusIt;
</script>

<script type="text/javascript">
$(document).ready(function(){
	var trs='<tr>';
	trs=trs + '<th rowspan="3" style="padding-top:25px;" ><fmt:message key="tongHopDip.Day"/></th>';
	trs=trs + '<th rowspan="3" style="padding-top:25px;"><fmt:message key="tongHopDip.dipMH"/></th>';
	trs=trs + '<th rowspan="3" style="padding-top:25px;"><fmt:message key="tongHopDip.sumDip"/></th>';
	trs=trs +'<th colspan="${duobletransType}"><fmt:message key="tongHopDip.TruyenDanCC"/></th>';
	trs=trs +'<th colspan="${counttransType}" rowspan="2" style="padding-top:13px;"><fmt:message key="tongHopDip.TruyendanCC3day"/></th>';
	trs=trs +'</tr>';
	trs=trs +'<tr>';
	trs=trs + '<th colspan="${counttransType}"><fmt:message key="tongHopDip.loaiTD"/></th>';
	trs=trs + '<th colspan="${counttransType}"><fmt:message key="tongHopDip.spLanCC"/></th>';
	trs=trs +'</tr>';
	trs=trs +'<tr>';
	trs=trs + '<th><fmt:message key="tongHopDip.total"/></th>';
	<c:forEach items="${transTypeList}" var="listOfNames">
		trs=trs +'<th>${listOfNames}</th>';
	</c:forEach>
	trs=trs + '<th><fmt:message key="tongHopDip.total"/></th>';
	<c:forEach items="${transTypeList}" var="listOfNames">
		trs=trs +'<th>${listOfNames}</th>';
	</c:forEach>
	trs=trs + '<th><fmt:message key="tongHopDip.total"/></th>';
	<c:forEach items="${transTypeList}" var="listOfNames">
		trs=trs +'<th>${listOfNames}</th>';
	</c:forEach>
	trs=trs +'</tr>';
	$('#item thead').html(trs);
	$('#item tbody tr:first').remove();
});

</script>