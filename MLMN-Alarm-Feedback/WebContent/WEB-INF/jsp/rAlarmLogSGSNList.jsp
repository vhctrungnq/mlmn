<%@ include file="/commons/taglibs.jsp"   %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<style type="text/css">

	.wid100 {
	    width: 100%;
	}
	.wid50{
		width: 50%;
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

<title><fmt:message key="SGSN.title"/></title>
<content tag="heading"><fmt:message key="SGSN.title"/></content> 	
<div>
<form:form commandName="filter" method="post" action="sgsn.htm">
<input type="hidden" name="reloadStr" id="reloadStr" value="${reloadStr}">
	<table>
		<tr>
			<td style="padding-left:10px;width: 90px;"><fmt:message key="alarmLogCurrently.sDate"/></td>
			<td>
				<input type ="text"  value="${sDate}" name="sDate" id="sDate" size="16" maxlength="19" style="width:120px">
	   			 <img alt="calendar" title="Click to choose the start date" id="choosesDate" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
			</td>
			
			<td style="padding-left: 30px;width: 80px;"><fmt:message key="alarmLogCurrently.eDate"/></td>
			<td>
				<input type ="text"  value="${eDate}" name="eDate" id="eDate" size="16" maxlength="19" style="width:120px">
	   			 <img alt="calendar" title="Click to choose the start date" id="chooseeDate" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
			</td>
			
			<td style="padding-left: 30px;width: 90px;"><fmt:message key="alarmLogSGSN.sgsn"/></td>
			<td>
				<input type ="text"  value="${sgsn}" name="sgsn" id="sgsn" style="width: 150px;">
			</td>
					
		</tr>
		<tr>
			<td style="padding-left: 10px;width: 90px;"><fmt:message key="alarmLogSGSN.alarmClass"/></td>
			<td>
				<input type ="text"  value="${alarmClass}" name="alarmClass" id="alarmClass" style="width: 150px;">
			</td>
						
			<td style="padding-left: 30px;width: 90px;"><fmt:message key="alarmLogSGSN.sologan"/></td>
			<td>
				<input type ="text"  value="${sologan}" name="sologan" id="sologan" style="width: 150px;">
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
</div>

  <div id="doublescroll">
	<display:table name="${alarmList}" class="simple4" id="alarmList" requestURI="" pagesize="50" sort="external" defaultsort="1" export="true">
	  	<display:column class="centerColumnMana" titleKey="global.list.STT" style="width:20px !important;"> <c:out value="${alarmList_rowNum}"/> </display:column>
		<display:column property="alarmClass" titleKey="alarmLogSGSN.alarmClass" sortable="true" sortName="ALARM_CLASS" class="ALARM_CLASS"/>
	  	<display:column property="alarmLogId" titleKey="alarmLogSGSN.notificationId" sortable="true" sortName="ALARM_LOG_ID" style="width:40px !important;"/>
	  	<display:column property="bsc" titleKey="alarmLogSGSN.sgsn" sortable="true" sortName="BSC" class="styleBsc"/>
	  	<display:column property="sdate"  titleKey="alarmLogSGSN.sdate" sortable="true" format="{0,date,dd/MM/yyyy HH:mm:ss}" sortName="SDATE" class="styleTime"/>
		<display:column property="alarmName"  titleKey="alarmLogSGSN.alarmId" sortable="true" sortName="ALARM_NAME" style="width:20px !important;"/>
		<display:column property="sologan" titleKey="alarmLogSGSN.sologan" sortable="true" sortName="SOLOGAN" class="styleAlarmName"/>
		<display:column property="alarmInfo"  titleKey="alarmLogSGSN.alarmInfo" sortable="true" sortName="ALARM_INFO" class="styleAlarmInfo"/>
		
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
	$(function() {
		var availableTags = [];
		var i = 0;
		<c:forEach items="${sgsnList}" var="listOfNames">
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
		$( "#sgsn" )
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
		var bscTp = $("#sgsn").val();
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