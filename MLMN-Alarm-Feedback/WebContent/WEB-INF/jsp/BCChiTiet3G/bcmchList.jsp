<%@ include file="/commons/taglibs.jsp"   %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<title>${title}</title>
<style>   
 	#doublescroll { overflow: auto; overflow-y: hidden; }  
    #doublescroll p { margin: 0; padding: 1em; white-space: nowrap; }
     #dvtTeamProcess {
     visibility: visible !important;
    }
</style>
<content tag="heading">${title}</content> 	
<div>
<form:form commandName="filter" method="get" action="dsach_ch.htm">
<input type="hidden" name="alarmType" id="alarmType" value="${alarmType}">
<input type="hidden" name="reloadStr" id="reloadStr" value="${reloadStr}">

	<table>
		<tr>
			<td style="width: 80px;"><fmt:message key="vAlRbLossConfig3G.BCNgay"/></td>
			<td>
				<input type ="text"  value="${startTime}" name="startTime" id="startTime" size="17" maxlength="10" style="width:100px">
	   			 <img alt="calendar" title="Click to choose the start date" id="chooseStartDate" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
			</td>
			
			<td style="padding-left: 5px;width: 80px;"><fmt:message key="vAlRbLossConfig3G.rncid"/></td>
			<td>
				<select name="rncid" id="rncid" style="width: 120px;height:20px; padding-top: 4px;">
					<option value=""><fmt:message key="global.All"/></option>
	           		<c:forEach var="item" items="${rncList}">
						<c:choose>
			                <c:when test="${item == rncid}">
			                    <option value="${item}" selected="selected">${item}</option>
			                </c:when>
							<c:otherwise>
								<option value="${item}">${item}</option>
							</c:otherwise>
						</c:choose>
					</c:forEach>
				</select> 
			</td>
			<td style="padding-left: 5px;width: 80px;"><fmt:message key="vAlRbLossConfig3G.alarmLevel"/></td>
			<td>
				<%-- <form:input type ="text" path="alarmLevel" maxlength="18" style="width:80px;" rows="3"/> --%>
				<input type ="text"  value="${alarmLevel}" name="alarmLevel" id="alarmLevel" size="20" maxlength="19" width="80px">
			</td>
			<c:choose>
                <c:when test="${alarmType == 'DOWN_CELL'}">
                   <td style="padding-left: 5px;"><fmt:message key="vAlRbLossConfig3G.cellid"/></td>
					<td>
						<%-- <form:input type ="text" path="cellid" maxlength="18" style="width:80px;" rows="3"/> --%>
						<input type ="text"  value="${cellid}" name="cellid" id="cellid" size="20" maxlength="19" width="80px">
					</td>
					<td style="padding-left: 5px;"><fmt:message key="vAlRbLossConfig3G.totalMD"/></td>
					<td>
						<input type ="text"  value="${totalMD}" name="totalMD" id="totalMD" size="5" maxlength="19" width="80px">
						&nbsp;-&nbsp;
						<input type ="text"  value="${totalTimeE}" name="totalTimeE" id="totalTimeE" size="5" maxlength="19" width="80px">
					</td>
                </c:when>
				<c:otherwise>
					<td style="padding-left: 5px;width: 200px; " colspan="3" ><fmt:message key="vAlRbLossConfig3G.totalMD"/>
						<input type ="text"  value="${totalMD}" name="totalMD" id="totalMD" size="5" maxlength="19" width="80px">
						&nbsp;-&nbsp;
						<input type ="text"  value="${totalTimeE}" name="totalTimeE" id="totalTimeE" size="5" maxlength="19" width="80px">
					</td>
				</c:otherwise>
			</c:choose>
			
			
		</tr>
		<tr>
			<td><fmt:message key="vAlRbLossConfig3G.dvtTeamProcess"/></td>
			<td>
					<select name="dvtTeamProcess" id="dvtTeamProcess" style="width: 120px;height:20px; padding-top: 4px;">
					<option value=""><fmt:message key="global.All"/></option>
	           		<c:forEach var="item" items="${teamList}">
						<c:choose>
			                <c:when test="${item.deptCode == team}">
			                    <option value="${item.deptCode}" selected="selected">${item.deptCode}</option>
			                </c:when>
							<c:otherwise>
								<option value="${item.deptCode}">${item.deptCode}</option>
							</c:otherwise>
						</c:choose>
					</c:forEach>
				</select> 
			</td>
			<td style="padding-left: 5px;"><fmt:message key="vAlRbLossConfig3G.dvtUserProcess"/></td>
			<td>
				<%-- <form:input type ="text" path="dvtUserProcess" maxlength="18" style="width:80px;" rows="3"/> --%>
				<input type ="text"  value="${dvtUserProcess}" name="dvtUserProcess" id="dvtUserProcess" size="17" maxlength="19" width="80px">
			</td>
			<%-- <td style="padding-left: 10px;"><fmt:message key="vAlRbLossConfig3G.causeby"/></td>
			<td>
				<form:input type ="text" path="causeby" maxlength="18" style="width:80px;" rows="3"/>
				<input type ="text"  value="${causeby}" name="causeby" id="causeby" size="20" maxlength="19" width="80px">
			</td> --%>
			
			<td colspan="2" style="padding-left: 10px;"><input class="button" type="submit" class="button" 
								value="<fmt:message key="button.search"/>" />
			</td>
			</tr>	
	</table>
</form:form>
</div>

  <div id="doublescroll" >
	<display:table name="${alarmList}"  class="simple2" id="item" requestURI="" pagesize="50" sort="external" defaultsort="1" export="true">
	  	<display:column class="centerColumnMana" titleKey="global.list.STT" style="width:30px;"> <c:out value="${item_rowNum}"/> </display:column>
		<display:column property="rncid" titleKey="vAlRbLossConfig3G.rncid" sortable="true" sortName="RNCID" style="min-width:60px;max-width:60px;"/>
	  	<display:column property="alarmLevel" titleKey="vAlRbLossConfig3G.alarmLevel" sortable="true" sortName="ALARM_LEVEL" style="min-width:60px;max-width:60px;"/>
	  	<c:if test="${alarmType == 'DOWN_CELL'}">
			<display:column property="cellid" titleKey="vAlRbLossConfig3G.cellid" sortable="true" sortName="CELLID" style="min-width:60px;max-width:60px;"/>
	   	</c:if>
	  	<display:column property="sdate" format="{0,date,dd/MM/yyyy HH:mm}" titleKey="vAlRbLossConfig3G.sdate" sortable="true" sortName="SDATE" style="min-width:105px;max-width:105px;"/>
		<display:column property="edate"  format="{0,date,dd/MM/yyyy HH:mm}" titleKey="vAlRbLossConfig3G.edate" sortable="true" sortName="EDATE"  style="min-width:105px;max-width:105px;"/>
		<display:column property="duration" titleKey="vAlRbLossConfig3G.duration" sortable="true" sortName="DURATION"  style="min-width:40px;max-width:40px;"/>
		<display:column property="dvtTeamProcess" titleKey="vAlRbLossConfig3G.dvtTeamProcess" sortable="true" sortName="DVT_TEAM_PROCESS"  style="min-width:100px;max-width:100px;"/>
		<display:column property="dvtUserProcess" titleKey="vAlRbLossConfig3G.dvtUserProcess" sortable="true" sortName="DVT_USER_PROCESS"  style="min-width:100px;max-width:100px;"/>
		<display:column property="causebySy" titleKey="vAlRbLossConfig3G.causebySy" sortable="true" sortName="CAUSEBY_SY" style="min-width:60px;max-width:60px;"/>
		<display:column property="causeby" titleKey="vAlRbLossConfig3G.causeby" sortable="true" sortName="CAUSEBY" style="min-width:100px;"/>
		<display:column property="actionProcess"  titleKey="vAlRbLossConfig3G.actionProcess" sortable="true" sortName="ACTION_PROCESS" style="min-width:100px;" />
		<display:column property="description"  titleKey="vAlRbLossConfig3G.description" sortable="true" sortName="DESCRIPTION" style="min-width:100px;max-width:100px;"/>
		<c:if test="${checkCaTruc==true}">
			<display:column titleKey="title.quanLy" media="html" class="centerColumnMana" style="min-width:60px;max-width:60px;">
			<%-- <a href="cau_hinh_form.htm?sdate=${item.sdateStr}&rncid=${item.rncid}&alarmLevel=${item.alarmLevel}&fmAlarmId=${item.fmAlarmId}&alarmType=${alarmType}"><fmt:message key="button.edit"/></a>&nbsp;
	 			<a href="cau_hinh_delete.htm?sdate=${item.sdateStr}&rncid=${item.rncid}&alarmLevel=${item.alarmLevel}&fmAlarmId=${item.fmAlarmId}&alarmType=${alarmType}"
									onclick="return confirm('<fmt:message key="messsage.confirm.delete"/>')"><fmt:message key="button.delete"/></a>&nbsp;
			   --%>	
			   	 <a href="cau_hinh_form.htm?id=${item.id}&alarmType=${alarmType}"><fmt:message key="button.edit"/></a>&nbsp;
	 			<a href="cau_hinh_delete.htm?sdate=${item.sdateStr}&rncid=${item.rncid}&alarmLevel=${item.alarmLevel}&fmAlarmId=${item.fmAlarmId}&alarmType=${alarmType}"
									onclick="return confirm('<fmt:message key="messsage.confirm.delete"/>')"><fmt:message key="button.delete"/></a>&nbsp;
			  	
	 		</display:column>
	   	</c:if>
		
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

<script type="text/javascript"> $(".chzn-select").chosen(); $(".chzn-select-deselect").chosen({allow_single_deselect:true}); </script>
<script type="text/javascript">
    Calendar.setup({
        inputField		:	"startTime",	// id of the input field
        ifFormat		:	"%d/%m/%Y",   	// format of the input field
        button			:   "chooseStartDate",  	// trigger for the calendar (button ID)
        showsTime		:	true,
        singleClick		:   false					// double-click mode
    });

	
</script>
<script type="text/javascript">
	function focusIt()
	{
	  var mytext = document.getElementById("startTime");
	  mytext.focus();
	}
	onload = focusIt;
</script>

<script type="text/javascript">
$(document).ready(function(){
	var reload = $("#reload").val();
	if (reload=='Y') {
		setTimeout(function(){
			$('#filter').submit();
		}, 
		10000);
		$('#reloadStr').val('Y');
	}
});

$("#reload").change( function() {
	if ($(this).is(':checked')) {
		$('#time').css("display","block");
		$('#reloadStr').val('Y');
	} else {
		$('#time').css("display","none");
		$('#reloadStr').val('N');
	}
});
//reload
	if ($('#reload').is(':checked')) {
		$('#time').css("display","block");
		$('#reloadStr').val('Y');
		var timeLoad = $('#timeLoad').val();
		
		setTimeout(function(){
			$('#filter').submit();
		}, 
		timeLoad * 1000);
	} else {
		$('#time').css("display","none");
		$('#reloadStr').val('N');
	}
	
  </script>