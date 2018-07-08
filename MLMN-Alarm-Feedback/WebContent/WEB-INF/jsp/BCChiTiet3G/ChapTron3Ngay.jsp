
<%@ include file="/commons/taglibs.jsp"   %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<style>   
   
   .ui-autocomplete {
		max-height: 200px;
		overflow-y: auto;
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
<form:form commandName="filter" method="post" action="lostDip3G3Day.htm">
	<table>
		<tr>
			<td style="width: 50px;"><fmt:message key="alDyRpDip3G3G.day"/></td>
			<td>
				<input type ="text"  value="${day}" name="day" id="day" size="17" maxlength="10" style="width:100px">
	   			 <img alt="calendar" title="Click to choose the start date" id="chooseStartDate" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
			</td>
			<td style="padding-left: 10px;width:80px;"><fmt:message key="alDyRpDip3G.dvtTeamProcess"/></td>
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
			<td style="padding-left: 10px;width: 50px;"><fmt:message key="alDyRpDip3G3G.rnc"/></td>
			<td>
				<input type ="text" value="${rnc}" name="rnc" id="rnc" width="80px" size="20">
			</td>
			<td style="padding-left: 10px;width: 50px;"><fmt:message key="alDyRpDip3G3G.nodeb"/></td>
			<td>
				<input type ="text"  value="${nodeb}" name="nodeb" id="nodeb" size="20"  width="80px">
			</td>
			
			<td style="padding-left: 10px;"><input class="button" type="submit" class="button" 
							value="<fmt:message key="button.search"/>" /></td>
		</tr>
	</table>
</form:form>
</div>
  <div>
	<display:table name="${alarmList}" class="simple2" id="item" requestURI="" pagesize="15" sort="external" defaultsort="1" export="true">
	  	<display:column class="centerColumnMana" titleKey="global.list.STT" style="width:30px;"> <c:out value="${item_rowNum}"/> </display:column>
		<display:column property="rnc" titleKey="alDyRpDip3G3G.rnc" sortable="true" sortName="rnc" style="min-width:60px;max-width:60px;"/>
	  	<display:column property="nodeb" titleKey="alDyRpDip3G3G.nodeb" sortable="true" sortName="NODEB" style="min-width:60px;max-width:60px;"/> 
		<display:column property="items2" titleKey="alDyRpDip3G3G.items2" sortable="true" sortName="ITEMS_2" style="min-width:40px;max-width:40px;"/>
		<display:column property="totalTime2" titleKey="alDyRpDip3G3G.totalTime2" sortable="true" sortName="TOTAL_TIME_2" style="min-width:60px;max-width:60px;"/>
		<%-- <display:column property="day2"  titleKey="alDyRpDip3G3G.day2" format="{0,date,dd/MM/yyyy}" sortable="true" sortName="DAY" /> --%>
		<display:column property="items1" titleKey="alDyRpDip3G3G.items1" sortable="true" sortName="ITEMS_1" style="min-width:40px;max-width:40px;"/>
		<display:column property="totalTime1" titleKey="alDyRpDip3G3G.totalTime1" sortable="true" sortName="TOTAL_TIME_1" style="min-width:60px;max-width:60px;"/>
		<%-- <display:column property="day1"  titleKey="alDyRpDip3G3G.day1" format="{0,date,dd/MM/yyyy}" sortable="true" sortName="DAY" /> --%>
		<display:column property="items" titleKey="alDyRpDip3G3G.items" sortable="true" sortName="ITEMS" style="min-width:40px;max-width:40px;"/>
		<display:column property="totalTime" titleKey="alDyRpDip3G3G.totalTime" sortable="true" sortName="TOTAL_TIME" style="min-width:60px;max-width:60px;"/>
		<%-- <display:column property="day"  titleKey="alDyRpDip3G3G.day" format="{0,date,dd/MM/yyyy}" sortable="true" sortName="DAY" /> --%>
		<display:column property="transType"  titleKey="alDyRpDip3G3G.transType" sortable="true" sortName="TRANS_TYPE" style="min-width:100px;"/>
		<display:column property="dvtTeamProcess" titleKey="alDyRpDip3G3G.dvtTeamProcess" sortable="true" sortName="DVT_TEAM_PROCESS" style="min-width:100px;"/>

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
        inputField		:	"day",	// id of the input field
        ifFormat		:	"%d/%m/%Y",   	// format of the input field
        button			:   "chooseStartDate",  	// trigger for the calendar (button ID)
        showsTime		:	true,
        singleClick		:   false					// double-click mode
    });

</script>
<script type="text/javascript">
	function focusIt()
	{
	  var mytext = document.getElementById("day");
	  mytext.focus();
	}
	$(document).ready(function(){
		var trs='<tr><th rowspan="2"><fmt:message key="global.list.STT"/></th>';
	    trs=trs + '<th rowspan="2"><fmt:message key="alDyRpDip3G3G.rnc"/></th>';
	    trs=trs + '<th rowspan="2"><fmt:message key="alDyRpDip3G3G.nodeb"/></th>';
	    trs=trs +'<th colspan="2">${day2}</th>';
	    trs=trs +'<th colspan="2">${day1}</th>';
	    trs=trs +'<th colspan="2">${day}</th>';
	    trs=trs +'<th rowspan="2"><fmt:message key="alDyRpDip3G3G.transType"/></th>';
	    trs=trs +'<th rowspan="2"><fmt:message key="alDyRpDip3G3G.dvtTeamProcess"/></th></tr>';
	   	trs=trs +'<tr><th ><fmt:message key="alDyRpDip3G3G.items2"/></th>';
	    trs=trs +'<th ><fmt:message key="alDyRpDip3G3G.totalTime2"/></th>';
	    trs=trs +'<th ><fmt:message key="alDyRpDip3G3G.items1"/></th>';
	    trs=trs +'<th ><fmt:message key="alDyRpDip3G3G.totalTime1"/></th>';
	    trs=trs +'<th ><fmt:message key="alDyRpDip3G3G.items1"/></th>';
	    trs=trs +'<th ><fmt:message key="alDyRpDip3G3G.totalTime1"/></th></tr>';
	    
		$('#item thead').html(trs);
	
	});
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
		$( "#rnc" )
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
		var bscTp = $("#rnc").val();
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
  