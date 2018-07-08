
<%@ include file="/commons/taglibs.jsp"   %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!-- <meta http-equiv="Refresh" content="20; URL="> -->
<style>   
 	
     #teamProcess {
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
<form:form commandName="filter" method="get" action="danh_sach.htm">
<input type="hidden" name="reloadStr" id="reloadStr" value="${reloadStr}">

	<table>
		<tr>
			<td style="width: 70px;"><fmt:message key="VAlRbLossComunication.day"/></td>
			<td>
				<input type ="text"  value="${day}" name="day" id="day" size="17" maxlength="10" style="width:100px">
	   			 <img alt="calendar" title="Click to choose the start date" id="chooseStartDate" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
			</td>
			<td style="padding-left: 5px;width: 70px;"><fmt:message key="VAlRbLossComunication.teamProcess"/></td>
			<td>
					<select name="teamProcess" id="teamProcess" style="width: 140px;height:20px; padding-top: 4px;">
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
			<td style="padding-left: 5px;width: 70px;"><fmt:message key="VAlRbLossComunication.bscid"/></td>
			<td>
				<input type ="text"  value="${bscid}" name="bscid" id="bscid" size="25" width="80px">
			</td>
			<td style="padding-left: 5px;" colspan="2"><fmt:message key="VAlRbLossComunication.dip"/>&nbsp;&nbsp;
			
				<input type ="text"  value="${dip}" name="dip" id="dip" size="20"  width="80px">
			</td>
			
			</tr>
			
			<tr>
				
				<td><fmt:message key="VAlRbLossComunication.userProcess"/></td>
				<td>
					<input type ="text"  value="${dvtUserProcess}" name="dvtUserProcess" id="dvtUserProcess" size="17" maxlength="19" width="80px">
				</td>
				
				<td style="padding-left: 5px;"><fmt:message key="VAlRbLossComunication.statusKT"/></td>
				<td>
					<select name="statusKT" id="statusKT" style="width: 140px;height:20px; padding-top: 4px;">
					<option value=""><fmt:message key="global.All"/></option>
	           		<c:forEach var="item" items="${statusKTList}">
						<c:choose>
			                <c:when test="${item.name == statusKT}">
			                    <option value="${item.name}" selected="selected">${item.value}</option>
			                </c:when>
							<c:otherwise>
								<option value="${item.name}">${item.value}</option>
							</c:otherwise>
						</c:choose>
					</c:forEach>
				</select> 
				</td>
				<td  style="padding-left: 5px;"><fmt:message key="VAlRbLossComunication.totalTime"/>
				</td>
				<td>
					<input type ="text"  value="${totalTimeF}" name="totalTimeF" id="totalTimeF" size="9" maxlength="10" width="80px">&nbsp;-&nbsp;
					<input type ="text"  value="${totalTimeE}" name="totalTimeE" id="totalTimeE" size="9" maxlength="10" width="80px">
					
				</td>
			<td style="padding-left: 5px;width: 200px;">
				
				<div style="width:90px;float:left;">
					Refresh  	 <c:choose>
							<c:when test="${reload != null}">
								<input id="reload" name="reload" type="checkbox" checked="checked">
							</c:when>
							<c:otherwise>
								<input id="reload" name="reload" type="checkbox" >
							</c:otherwise>
						</c:choose>
				</div>
				<div  id="time" style="padding-left: 8px">
				Sau <input id="timeLoad" name="timeLoad" value="${timeLoad}" type="text" maxlength="6"  style="width:35px;text-align:center"> giây
			</div>	
			</td>
			<td>
			
			<input class="button" type="submit" class="button"
							value="<fmt:message key="button.search"/>" />
			</td>
		</tr>
		
	</table>
</form:form>
</div>

  <div id="doublescroll" >
	<display:table name="${alarmList}" class="simple2" id="item" requestURI="" pagesize="50" sort="external" defaultsort="1" export="true">
	  	<display:column class="centerColumnMana" titleKey="global.list.STT" style="width:30px;"> <c:out value="${item_rowNum}"/> </display:column>
		<display:column property="bscid" titleKey="VAlRbLossComunication.bscid" sortable="true" sortName="BSCID" style="min-width:60px;max-width:60px;"/>
	  	<display:column property="dip" titleKey="VAlRbLossComunication.dip" sortable="true" sortName="DIP" class="dpnone" headerClass="dpnone"/>
		<display:column titleKey="VAlRbLossComunication.dip" sortable="true" sortName="DIP"  media="html" style="min-width:60px;max-width:60px;" >
			<a href="detail.htm?bscId=${item.bscid}&dip=${item.dip}&startTime=${item.sdateStr}&type=MT">${item.dip}</a>&nbsp;
	 	</display:column>
		<%-- <display:column property="alarm" titleKey="VAlRbLossComunication.alarm" sortable="true" sortName="ALARM"/>
		<display:column property="alarmClass" titleKey="VAlRbLossComunication.alarmClass" sortable="true" sortName="ALARM_CLASS"/> --%>
		<display:column property="sdate"  titleKey="VAlRbLossComunication.sdate" format="{0,date,dd/MM/yyyy HH:mm}" sortable="true" sortName="SDATE" style="min-width:115px;max-width:115px;"/>
		<display:column property="edate"  titleKey="VAlRbLossComunication.edate" format="{0,date,dd/MM/yyyy HH:mm}" sortable="true" sortName="EDATE" style="min-width:115px;max-width:115px;"/>
		<display:column property="totalTime" titleKey="VAlRbLossComunication.totalTime" sortable="true" sortName="TOTAL_TIME" style="min-width:40px;max-width:40px;"/>
		<display:column property="ungCuuMpd" titleKey="VAlRbLossComunication.ungCuuMpd" sortable="true" sortName="UNG_CUU_MPD" class="UNG_CUU_MPD" style="min-width:40px;max-width:40px;"/>
		<display:column property="dvtTeamProcess" titleKey="VAlRbLossComunication.teamProcess" sortable="true" sortName="DVT_TEAM_PROCESS" style="min-width:100px;max-width:100px;"/>
		<display:column property="dvtUserProcess"  titleKey="VAlRbLossComunication.userProcess" sortable="true" sortName="DVT_USER_PROCESS" style="min-width:100px;max-width:100px;"/>
		<display:column property="causeby"  titleKey="VAlRbLossComunication.causeby" sortable="true" sortName="CAUSEBY" style="min-width:200px;"/>
		<display:column property="actionProcess"  titleKey="VAlRbLossComunication.resultContent" sortable="true" sortName="ACTION_PROCESS" style="min-width:200px;"/>
		<c:if test="${checkCaTruc==true}">
			<display:column titleKey="title.quanLy" media="html" class="centerColumnMana" style="min-width:60px;max-width:60px;">
			<a href="formMaster.htm?id=${item.id}"><fmt:message key="button.edit"/></a>&nbsp;
	 			<a href="deleteMaster.htm?id=${item.id}"
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
	onload = focusIt;
	
	$("#reload").change( function() {
		if ($(this).is(':checked')) {
			$('#time').css("display","block");
			$('#reloadStr').val('Y');
		} else {
			$('#time').css("display","none");
			$('#reloadStr').val('N');
		}
	});
	$(function() {

		//Load Dip
		var cacheCell = {}, lastXhrCell;
		function split( val ) {
		return val.split( /,\s*/ );
		}
		function extractLast( term ) {
		return split( term ).pop();
		}
		$( "#dip" )
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
		/*response( $.ui.autocomplete.filter(
		availableTags, extractLast( request.term ) ) );*/
			var term = extractLast( request.term );
			if ( term in cacheCell ) {
				response( cacheCell[ term ] );
				return;
			}
			lastXhrCell = $.getJSON( "${pageContext.request.contextPath}/ajax/getDipList.htm", {term: term}, function( data, status, xhr ) {
				cacheCell[ term ] = data;
				if ( xhr === lastXhrCell ) {
					response( data );
				}
			});
			
		},
		focus: function() {
		// prevent value inserted on focus
		return false;
		},
		select: function( event, ui ) {
		var terms = split( this.value );
		// remove the current input
		terms.pop();
		// add the selected item
		terms.push( ui.item.value );
		// add placeholder to get the comma-and-space at the end
		terms.push( "" );
		this.value = terms.join( "," );
		return false;
		}
		});
	});
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

	$(function() {
    	$('#item>tbody>tr').each(
    	    	 function(){
        	    	 var value = $(this).find(".UNG_CUU_MPD").text();
        	    	 if( value!=null && value =='Y'){
        	    		 $(this).find("td").css({ 'color' : 'blue', 'text-decoration': 'none'});
            	    	}
     	    	 	if(value!=null && value =='N'){$(this).find("td").css({'color' : 'red', 'text-decoration': 'none'});}});
    	}); 
  </script>