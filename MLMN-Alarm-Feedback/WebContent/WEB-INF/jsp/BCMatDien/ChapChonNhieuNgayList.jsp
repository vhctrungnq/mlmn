
<%@ include file="/commons/taglibs.jsp"   %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<style type="text/css">
 	#doublescroll { overflow: auto; overflow-y: hidden; }  
    #doublescroll p { margin: 0; padding: 1em; white-space: nowrap; }
    
     #teamProcess {
     visibility: visible !important;
    }
    
     #bscid_chzn {
		width: 220px !important;
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
<form:form commandName="filter" method="get" action="list.htm">
	<table>
		<tr>
			<td style="width: 70px;"><fmt:message key="AlDyRpFlutterPower.startDate"/></td>
			<td>
				<input type ="text"  value="${startDate}" name="startDate" id="startDate" size="20" maxlength="10" style="width:120px">
	   			 <img alt="calendar" title="Click to choose the start date" id="chooseStartDate" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
			</td>
			<td style="width: 70px;padding-left: 5px;"><fmt:message key="AlDyRpFlutterPower.endDate"/></td>
			<td>
				<input type ="text"  value="${endDate}" name="endDate" id="endDate" size="20" maxlength="10" style="width:100px">
	   			 <img alt="calendar" title="Click to choose the end date" id="chooseEndDate" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
			</td>
			<td style="padding-left: 5px;width: 70px;"><fmt:message key="alDyRpErrorDip.teamProcess"/></td>
			<td>
					<select name="teamProcess" id="teamProcess" style="width: 120px;height:25px; padding-top: 4px;">
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
			<td style="padding-left: 5px;width: 70px;"><fmt:message key="alDyRpErrorDip.bscid"/></td>
			<td>
			    <input type ="text" value="${bscid}" name="bscid" id="bscid" width="80px" size="25">
			</td>
			</tr>
			<tr>
			<td><fmt:message key="alDyRpErrorDip.site"/></td>
			<td>
				<input type ="text"  value="${site}" name="site" id="site" size="20" width="80px">
			</td>
			<td style="padding-left: 5px;"><fmt:message key="alDyRpDip.itemsL"/></td>
			<td>
				<input type ="text"  value="${items}" name="items" id="items" size="5" maxlength="10" width="80px">
				&nbsp;-&nbsp;
				<input type ="text"  value="${itemsE}" name="itemsE" id="itemsE" size="5" maxlength="10" width="80px">
			
			</td>
			<td style="padding-left: 5px;"><fmt:message key="vAlRbLossConfig.totalMCH"/></td>
			<td>
				<input type ="text"  value="${totalF}" name="totalF" id="totalF" size="5" maxlength="10" width="80px">
				&nbsp;-&nbsp;
				<input type ="text"  value="${totalTimeE}" name="totalTimeE" id="totalTimeE" size="5" maxlength="10" width="80px">
				
			</td>
			<td colspan="2" style="padding-left: 5px;"><input class="button" type="submit" class="button"
							value="<fmt:message key="button.search"/>" /></td>
		</tr>
		
	</table>
</form:form>
</div>
  <div id="doublescroll" class="tableStandar">
	<display:table name="${alarmList}"  id="item" requestURI="" pagesize="50" sort="external" defaultsort="1" export="true">
	  	<display:column class="centerColumnMana" titleKey="global.list.STT"> <c:out value="${item_rowNum}"/> </display:column>
		<display:column property="day"  titleKey="vAlDyRpErrorPower.day" format="{0,date,dd/MM/yyyy}" sortable="true" sortName="DAY"  style="min-width:70px;max-width:70px;"/>
		<display:column property="bscid" titleKey="vAlRbLossPower.bscid" sortable="true" sortName="BSCID" style="min-width:60px;max-width:60px;"/>
	  	<display:column property="site" titleKey="vAlDyRpErrorPower.site" sortable="true" sortName="SITE"  style="min-width:60px;max-width:60px;"/>
		<display:column property="items" titleKey="alDyRpDip.items" sortable="true" sortName="ITEMS" style="min-width:30px;max-width:30px;"/>
		<display:column property="totalTime" titleKey="alDyFlutterPower.totalTime" sortable="true" sortName="TOTAL_TIME" style="min-width:40px;max-width:40px;"/>
		<display:column property="teamProcess" titleKey="vAlDyRpErrorPower.teamProcess" sortable="true" sortName="TEAM_PROCESS"  style="min-width:80px;max-width:80px;"/>
		<display:column property="userProcess"  titleKey="vAlDyRpErrorPower.userProcess" sortable="true" sortName="USER_PROCESS" style="min-width:100px;max-width:100px;"/>
		<display:column property="causebyContent"  titleKey="vAlDyRpErrorPower.causebyContent" sortable="true" sortName="CAUSEBY_CONTENT"  style="min-width:200px;"/>
		<display:column property="resultContent"  titleKey="vAlDyRpErrorPower.resultContent" sortable="true" sortName="RESULT_CONTENT" style="min-width:200px;"/>
		<c:if test="${checkCaTruc==true}">
			<display:column titleKey="title.quanLy" media="html" class="centerColumnMana" style="min-width:60px;max-width:60px;">
			<a href="form.htm?id=${item.id}"><fmt:message key="button.edit"/></a>&nbsp; 
	 			<a href="delete.htm?id=${item.id}"
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
        inputField		:	"startDate",     // id of the input field
        ifFormat		:	"%d/%m/%Y",   	// format of the input field
        button			:   "chooseStartDate",  	// trigger for the calendar (button ID)
        showsTime		:	true,
        singleClick		:   true
    });
    Calendar.setup({
        inputField		:	"endDate",	// id of the input field
        ifFormat		:	"%d/%m/%Y",   	// format of the input field
        button			:   "chooseEndDate",  	// trigger for the calendar (button ID)
        showsTime		:	true,
        singleClick		:   false					// double-click mode
    });

 
</script>
<script type="text/javascript">
	function focusIt()
	{
	  var mytext = document.getElementById("startDate");
	  mytext.focus();
	}
	onload = focusIt;

	 
</script>

<script type="text/javascript">
	$(function() {
		var availableTags = [];
		var i = 0;
		<c:forEach items="${bscidList}" var="listOfNames">
			availableTags[i] = "<c:out value='${listOfNames}' escapeXml='false' />";
			i = i + 1;
		</c:forEach>
		loadBsc(availableTags);
	});

	$("#teamProcess").change(function(){
		$.getJSON("${pageContext.request.contextPath}/ajax/getBSC23GByTeam.htm",{team: $(this).val()}, function(j){
			
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
		$( "#bscid" )
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
		var bscTp = $("#bscid").val();
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
 
  