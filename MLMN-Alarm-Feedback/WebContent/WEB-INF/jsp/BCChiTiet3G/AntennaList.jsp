<%@ include file="/commons/taglibs.jsp"   %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<style>   
 
     #dvtTeamProcess {
     visibility: visible !important;
    }
     .ui-autocomplete {
		max-height: 200px;
		overflow-y: auto;
		/* prevent horizontal scrollbar */
		overflow-x: hidden;
	}
	/* IE 6 doesn't support max-height
	* we use height instead, but this forces the menu to always be this tall
	*/
	* html .ui-autocomplete {
		height: 200px;
	}
</style>
<title>${title}</title>
<content tag="heading">${title}</content> 	
<div>
<form:form commandName="filter" method="get" action="antenna.htm">
	<table>
		<tr>
			<td style="width: 80px;"><fmt:message key="vAlRbLossAntenna.BCNgay"/></td>
			<td>
				<input type ="text"  value="${startTime}" name="startTime" id="startTime" size="17" maxlength="10" style="width:100px">
	   			 <img alt="calendar" title="Click to choose the start date" id="chooseStartDate" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
			</td>
			<td style="padding-left: 10px;width: 80px;"><fmt:message key="vAlRbLossAntenna.dvtTeamProcess"/></td>
			<td>
					<select name="dvtTeamProcess" id="dvtTeamProcess" style="width: 140px;height:20px; padding-top: 4px;">
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
			<td style="padding-left: 10px;width: 80px;"><fmt:message key="vAlRbLossAntenna.rncid"/></td>
			<td>
				
				<input type ="text" value="${rncid}" name="rncid" id="rncid" width="80px" size="20">
			</td>
			<td style="padding-left: 10px;width: 80px;"><fmt:message key="vAlRbLossAntenna.alarmLevel"/></td>
			<td>
				<%-- <form:input type ="text" path="alarmLevel" maxlength="18" style="width:80px;" rows="3"/> --%>
				<input type ="text"  value="${alarmLevel}" name="alarmLevel" id="alarmLevel" size="20" width="80px">
			</td>
			<td style="padding-left: 10px;width: 80px;"><fmt:message key="vAlRbLossAntenna.totalMD"/></td>
			<td>
				<input type ="text"  value="${totalMD}" name="totalMD" id="totalMD" size="5" maxlength="10" width="80px">
				&nbsp;-&nbsp;
				<input type ="text"  value="${totalTimeE}" name="totalTimeE" id="totalTimeE" size="5" maxlength="10" width="80px">
			
			</td>
			
			
		</tr>
		<tr>
			
			<td ><fmt:message key="vAlRbLossConfig3G.dvtUserProcess"/></td>
			<td>
				<%-- <form:input type ="text" path="dvtUserProcess" maxlength="18" style="width:80px;" rows="3"/> --%>
				<input type ="text"  value="${dvtUserProcess}" name="dvtUserProcess" id="dvtUserProcess" size="17" maxlength="19" width="80px">
			</td>
			<td style="padding-left: 10px;"><fmt:message key="vAlRbLossAntenna.sector"/></td>
			<td>
				<%-- <form:input type ="text" path="alarmLevel" maxlength="18" style="width:80px;" rows="3"/> --%>
				<input type ="text"  value="${sector}" name="sector" id="sector" size="20" maxlength="19" width="80px">
			</td>
			<td style="padding-left: 10px;"><fmt:message key="vAlRbLossAntenna.antenError"/></td>
			<td>
				<%-- <form:input type ="text" path="alarmLevel" maxlength="18" style="width:80px;" rows="3"/> --%>
				<input type ="text"  value="${antenError}" name="antenError" id="antenError" size="20" maxlength="19" width="80px">
			</td>
			<td style="padding-left: 10px;"><fmt:message key="vAlRbLossAntenna.statusKT"/></td>
			<td>
				<select name="statusKTMCH" id="statusKTMCH" style="width: 140px;height:20px; padding-top: 4px;">
				<option value=""><fmt:message key="global.All"/></option>
           		<c:forEach var="item" items="${statusKTMCHList}">
					<c:choose>
		                <c:when test="${item.name == statusKTMCH}">
		                    <option value="${item.name}" selected="selected">${item.value}</option>
		                </c:when>
						<c:otherwise>
							<option value="${item.name}">${item.value}</option>
						</c:otherwise>
					</c:choose>
				</c:forEach>
			</select> 
			</td>
			
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
		<display:column property="rncid" titleKey="vAlRbLossAntenna.rncid" sortable="true" sortName="RNCID"  style="min-width:60px;max-width:60px;"/>
	  	<display:column property="alarmLevel" titleKey="vAlRbLossAntenna.alarmLevel" sortable="true" sortName="ALARM_LEVEL"  style="min-width:60px;max-width:60px;"/>
	  	<display:column property="sdate" format="{0,date,dd/MM/yyyy HH:mm}" titleKey="vAlRbLossAntenna.sdate" sortable="true" sortName="SDATE"  style="min-width:105px;max-width:105px;"/>
		<display:column property="edate"  format="{0,date,dd/MM/yyyy HH:mm}" titleKey="vAlRbLossAntenna.edate" sortable="true" sortName="EDATE"  style="min-width:105px;max-width:105px;"/>
		<display:column property="duration" titleKey="vAlRbLossAntenna.duration" sortable="true" sortName="DURATION" style="min-width:40px;max-width:40px;"/>
		<display:column property="antenError" titleKey="vAlRbLossAntenna.antenError" sortable="true" sortName="ANTEN_ERROR" style="min-width:80px;max-width:80px;"/>
		<display:column property="sector" titleKey="vAlRbLossAntenna.sector" sortable="true" sortName="SECTOR"  style="min-width:80px;max-width:80px;"/>
		<display:column property="dvtTeamProcess" titleKey="vAlRbLossAntenna.dvtTeamProcess" sortable="true" sortName="DVT_TEAM_PROCESS" style="min-width:100px;max-width:100px;"/>
		<display:column property="dvtUserProcess" titleKey="vAlRbLossAntenna.dvtUserProcess" sortable="true" sortName="DVT_USER_PROCESS" style="min-width:100px;max-width:100px;"/>
		<display:column property="causeby" titleKey="vAlRbLossAntenna.causeby" sortable="true" sortName="CAUSEBY" style="min-width:150px;"/>
		<display:column property="actionProcess"  titleKey="vAlRbLossAntenna.actionProcess" sortable="true" sortName="ACTION_PROCESS" style="min-width:150px;"/>
		<display:column property="description"  titleKey="vAlRbLossAntenna.description" sortable="true" sortName="DESCRIPTION" style="min-width:150px;"/>
		<c:if test="${checkCaTruc==true}">
			<display:column titleKey="title.quanLy" media="html" class="centerColumnMana" style="min-width:60px;max-width:60px;">
			<a href="antenna_form.htm?id=${item.id}"><fmt:message key="button.edit"/></a>&nbsp;
	 			<a href="antenna_delete.htm?sdate=${item.sdateStr}&rncid=${item.rncid}&alarmLevel=${item.alarmLevel}&fmAlarmId=${item.fmAlarmId}"
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
	$(function() {
		var availableTags = [];
		var i = 0;
		<c:forEach items="${rncList}" var="listOfNames">
			availableTags[i] = "<c:out value='${listOfNames}' escapeXml='false' />";
			i = i + 1;
		</c:forEach>
		loadBsc(availableTags);
	});

	$("#dvtTeamProcess").change(function(){
		$.getJSON("${pageContext.request.contextPath}/ajax/getRNCIDByTeam.htm",{team: $(this).val()}, function(j){
			
			var availableTags = [];
			for (var i = 0; i < j.length; i++) {
				availableTags[i] = j[i];
			}
			
			loadBsc(availableTags);
		});
	
	});

	function loadBsc(availableTags){
		function split( val ) {
		return val.split( /,\s*/ );
		}
		function extractLast( term ) {
		return split( term ).pop();
		}
		$( "#rncid" )
		// don't navigate away from the field on tab when selecting an item
		.bind( "keydown", function( event ) {
		if ( event.keyCode === $.ui.keyCode.TAB &&
		$( this ).data( "autocomplete" ).menu.active ) {
		event.preventDefault();
		}
		})
		.autocomplete({
		minLength: 0,
		source: function( request, response ) {
		// delegate back to autocomplete, but extract the last term
		response( $.ui.autocomplete.filter(
		availableTags, extractLast( request.term ) ) );
		},
		focus: function() {
		// prevent value inserted on focus
		return false;
		},
		select: function( event, ui ) {
		var terms = split( this.value );
		// remove the current input
		terms.pop();
		//check and add the selected item
		var temp = ui.item.value;
		var bscTp = $("#rncid").val();
		var bscL = bscTp.split(",");
		var kt = false;
		for (var i=0; i<bscL.length; i++) {
			if (temp==bscL[i])
				kt=true;
		}
		if (kt==false)
		{
			terms.push( ui.item.value );
			// add placeholder to get the comma-and-space at the end
			terms.push( "" );
			this.value = terms.join( "," );
		}
		return false;
		}
		});
	}	
</script>
