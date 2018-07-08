<%@ include file="/commons/taglibs.jsp"   %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<style type="text/css">

.wid100 {
	    width: 100%;
	}
	.wid30{
	    width: 30%;
	}
	.wid50{
		width: 50%;
	}
	.wid70{
		width:70%;
	}
	.dpnone {
	    display: none;
	}
	.pdl10 {
	    padding-left: 10px;
	}
	.pdl15 {
	    padding-left: 15px;
	}
	.styleRefresh{
		width:35px;
		text-align:center
	}
    
	#doublescroll { overflow: auto; overflow-y: hidden; }   
	#doublescroll p { margin: 0; padding: 1em; white-space: nowrap; }
    
     #dvtTeamProcess {
     visibility: visible !important;
    }
   
    #acLow {
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

<title><fmt:message key="GGSN.title"/></title>
<content tag="heading"><fmt:message key="GGSN.title"/></content> 	
<div>
<form:form commandName="filter" method="post" action="ggsn.htm">
<input type="hidden" name="reloadStr" id="reloadStr" value="${reloadStr}">
	<table style="width:100%">
		<tr>
			<td style="padding-left:10px;width: 90px;"><fmt:message key="alarmLogCurrently.sDate"/></td>
			<td>
				<input type ="text"  value="${sDate}" name="sDate" id="sDate" size="16" maxlength="19" style="width:120px">
	   			 <img alt="calendar" title="Click to choose the start date" id="choosesDate" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
			</td>
			
			<td style="width: 80px;"><fmt:message key="alarmLogCurrently.eDate"/></td>
			<td>
				<input type ="text"  value="${eDate}" name="eDate" id="eDate" size="16" maxlength="19" style="width:120px">
	   			 <img alt="calendar" title="Click to choose the start date" id="chooseeDate" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
			</td>
			
			<td style="width: 90px;"><fmt:message key="alarmLogGGSN.ggsn"/></td>
			<td>
				<input type ="text"  value="${ggsn}" name="ggsn" id="ggsn" style="width: 150px;">
			</td>
					
		</tr>
		<tr>
			<td style="padding-left: 10px;width: 90px;"><fmt:message key="alarmLogGGSN.alarmName"/></td>
			<td>
				<input type ="text"  value="${alarmName}" name="alarmName" id="alarmName" style="width: 150px;">
			</td>
						
			<td style="width: 150px;"><fmt:message key="alarmLogGGSN.perceivedSeverity"/></td>
			<td>
				<input type ="text"  value="${perceivedSeverity}" name="perceivedSeverity" id="perceivedSeverity" style="width: 150px;">
			</td>

			<td class = "pdl15">
			<div style="width:80px;float:left;">
					Refresh  	 <c:choose>
							<c:when test="${reload != null}">
								<input id="reload" name="reload" type="checkbox" checked="checked">
							</c:when>
							<c:otherwise>
								<input id="reload" name="reload" type="checkbox" >
							</c:otherwise>
						</c:choose>
				</div> 
			</td>
			<td>
				<div  id="time" style="padding-left: 8px">
					Sau <input id="timeLoad" name="timeLoad" value="${timeLoad}" type="text"  maxlength="6" style="width:35px;text-align:center"> giây
				</div>	
			</td>
			
			<td style="width:60px;padding-left: 30px">		
				<input class="button" type="submit" class="button" value="<fmt:message key="button.search"/>" />
			</td>
			
		</tr>
	</table>
</form:form>
  <br/>
    <table class="form">
    	<tr>
    		<td>
    			<b>Chọn chỉ số hiển thị: </b>
    		</td>
    	</tr>
        <tr>
        	<td>${checkColumns}</td>
		</tr>
	</table>
</div>

  <div id="doublescroll">
	<display:table name="${alarmList}" class="simple2" id="alarmListId" requestURI="" pagesize="50" sort="external" defaultsort="1" export="true">
	  	<display:column class="centerColumnMana" titleKey="global.list.STT" style="width:20px !important;"> <c:out value="${alarmListId_rowNum}"/> </display:column>
	  	<display:column property="notificationId" titleKey="alarmLogGGSN.notificationId" sortable="true" sortName="NOTIFICATION_ID" class="styleBsc"/>
	  	<display:column property="bsc" titleKey="alarmLogGGSN.ggsn" sortable="true" sortName="BSC" class="styleBsc"/>
		<display:column property="alarmId"  titleKey="alarmLogGGSN.alarmId" sortable="true" sortName="ALARM_ID" class="styleAlarmId"/>
		<display:column property="specificProblem" titleKey="alarmLogGGSN.specificProblem" sortable="true" sortName="SPECIFIC_PROBLEM" class="styleAlarmId"/>
		<display:column property="alarmName" titleKey="alarmLogGGSN.alarmName" sortable="true" sortName="ALARM_NAME" class="styleAlarmName"/>
		<display:column property="appInfo"  titleKey="alarmLogGGSN.appInfo" sortable="true" sortName="APP_INFO" class="styleAppInfo"/>
		<display:column property="perceivedSeverity" titleKey="alarmLogGGSN.perceivedSeverity" sortable="true" sortName="PERCEIVED_SEVERITY" class="ALARM_CLASS"/>
	  	<display:column property="alarmTime"  titleKey="alarmLogGGSN.alarmTime" sortable="true" format="{0,date,dd/MM/yyyy HH:mm:ss}" sortName="ALARM_TIME" class="styleTime"/>
		<display:column property="eventType" titleKey="alarmLogGGSN.eventType" sortable="true" sortName="EVENT_TYPE" class="styleEventType"/>
		<display:column property="probableCause"  titleKey="alarmLogGGSN.probableCause" sortable="true" sortName="PROBABLE_CAUSE" class="styleWid80"/>
		<display:column property="eventTypeName" titleKey="alarmLogGGSN.eventTypeName" sortable="true" sortName="EVENT_TYPE_NAME" class="styleWid80"/>
		<display:column property="acknowledged" titleKey="alarmLogGGSN.acknowledged" sortable="true" sortName="ACKNOWLEDGED" class="styleBsc"/>
		<display:column property="acknowledgedTime"  titleKey="alarmLogGGSN.acknowledgedTime" sortable="true" sortName="ACKNOWLEDGED_TIME" class="styleTime"/>
		<display:column property="acknowledgedUserId" titleKey="alarmLogGGSN.acknowledgedUserId" sortable="true" sortName="ACKNOWLEDGED_USER_ID" class="styleBsc"/>
		<display:column property="cleared"  titleKey="alarmLogGGSN.cleared" sortable="true" sortName="CLEARED" class="styleBsc"/>
		<display:column property="managedObjectId" titleKey="alarmLogGGSN.managedObjectId" sortable="true" sortName="MANAGED_OBJECT_ID" class="styleAlarmName"/>
		<display:column property="applicationId" titleKey="alarmLogGGSN.applicationId" sortable="true" sortName="APPLICATION_ID" class="styleAlarmInfo"/>
		<display:column property="identifyingAppInfo"  titleKey="alarmLogGGSN.identifyingAppInfo" sortable="true" sortName="IDENTIFYING_APP_INFO" class="styleBsc"/>
		<display:column property="controlIndicator" titleKey="alarmLogGGSN.controlIndicator" sortable="true" sortName="CONTROL_INDICATOR" class="styleBsc"/>
		
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

<script type="text/javascript">
	
    Calendar.setup({
        inputField		:	"sDate",	// id of the input field
        ifFormat		:	"%d/%m/%Y %H:%M",   	// format of the input field
        button			:   "choosesDate",  	// trigger for the calendar (button ID)
        showsTime		:	true,
        singleClick		:   false					// double-click mode
    });

    Calendar.setup({
        inputField		:	"eDate",	// id of the input field
        ifFormat		:	"%d/%m/%Y %H:%M",   	// format of the input field
        button			:   "chooseeDate",   	// trigger for the calendar (button ID)
        showsTime		:	true,
        singleClick		:   false					// double-click mode
    });
    
    ${highlight}
</script>

<script type="text/javascript">
	function focusIt()
	{
	  var mytext = document.getElementById("sDate");
	  mytext.focus();
	}
	onload = focusIt;
</script>

<script type="text/javascript">
$(document).ready(function(){
	var reload = $('#reload').val();
	
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
		/* var timeout = setTimeout(function(){
			$('#filter').submit();
		}, 
		10000);
		clearTimeout(timeout); */
	}
});
//reload
	if ($('#reload').is(':checked')) {
		$('#time').css("display","block");
		var timeLoad = $('#timeLoad').val();
		$('#reloadStr').val('Y');
		setTimeout(function(){
			$('#filter').submit();
		}, 
		timeLoad * 1000);
	} else {
		$('#time').css("display","none");
		$('#reloadStr').val('N');
		/* var timeout = setTimeout(function(){
			$('#filter').submit();
		}, 
		10000);
		clearTimeout(timeout); */
		
	}
  </script>

<script type="text/javascript">
    function DoubleScroll(element) {
        var scrollbar= document.createElement('div');
        scrollbar.appendChild(document.createElement('div'));
        scrollbar.style.overflow= 'auto';
        scrollbar.style.overflowY= 'hidden';
        scrollbar.firstChild.style.width= element.scrollWidth+'px';
        scrollbar.firstChild.style.paddingTop= '1px';
        scrollbar.firstChild.appendChild(document.createTextNode('\xA0'));
        scrollbar.onscroll= function() {
            element.scrollLeft= scrollbar.scrollLeft;
        };
        element.onscroll= function() {
            scrollbar.scrollLeft= element.scrollLeft;
        };
        element.parentNode.insertBefore(scrollbar, element);
    }

    DoubleScroll(document.getElementById('doublescroll'));
</script>
<script type="text/javascript">
	$(function() {
		var availableTags = [];
		var i = 0;
		<c:forEach items="${ggsnList}" var="listOfNames">
			availableTags[i] = "<c:out value='${listOfNames}' escapeXml='false' />";
			i = i + 1;
		</c:forEach>
		loadBsc(availableTags);
	});

	function loadBsc(availableTags){
		function split( val ) {
		return val.split( /,\s*/ );
		}
		function extractLast( term ) {
		return split( term ).pop();
		}
		$( "#ggsn" )
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
		var bscTp = $("#ggsn").val();
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