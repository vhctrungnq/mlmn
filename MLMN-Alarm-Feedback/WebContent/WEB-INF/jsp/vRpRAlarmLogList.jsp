<%@ include file="/commons/taglibs.jsp"   %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<style type="text/css">
    
	#doublescroll { overflow: auto; overflow-y: hidden; }   
	#doublescroll p { margin: 0; padding: 1em; white-space: nowrap; }
    
    .EDATE{
		width:130px !important;
	}

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
<title><fmt:message key="alarmLog2G.title"/></title>
<content tag="heading"><fmt:message key="alarmLog2G.title"/></content>

<div class="ui-tabs-panel">
<form:form commandName="filter" method="post" action="list.htm">
<input type="hidden" name="reloadStr" id="reloadStr" value="${reloadStr}">
	<table style = "width:100%">
	
	<tr>	
		<td style="width:70px"><fmt:message key="filter.alarmLog.startTime"/></td>
		<td style="width:12%">
			<input type ="text"  value="${startTime}" name="startTime" id="startTime" class = "wid80">
	   		<img alt="calendar" title="Click to choose the start date" id="chooseStartDate" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
		</td>
		
		<td><fmt:message key="alarmLogCurrently.eDate"/></td>
		<td style="width:12%">
			<input type ="text"  value="${endTime}" name="endTime" id="endTime" class = "wid80">
	   		<img alt="calendar" title="Click to choose the start date" id="chooseEndDate" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
		</td>
		
		<td><fmt:message key="filter.alarmLog.bscid"/></td>
		<td>
			<select name="conbsc" id="conbsc" class="wid30" onchange="xl()">
	           		<c:forEach var="item" items="${conditonList}">
						<c:choose>
			                <c:when test="${item.name == conbsc}">
			                    <option value="${item.name}" selected="selected">${item.value}</option>
			                </c:when>
							<c:otherwise>
								<option value="${item.name}">${item.value}</option>
							</c:otherwise>
						</c:choose>
					</c:forEach>
			</select>
			<input type ="text"  value="${bscid}" name="bscid" id="bscid" style="width:65%">
		</td>
		
		<td style="width:70px"><fmt:message key="filter.alarmLog.site"/></td>
		<td>
			<select name="consite" id="consite" class="wid30" onchange="xl()">
	           		<c:forEach var="item" items="${conditonList}">
						<c:choose>
			                <c:when test="${item.name == consite}">
			                    <option value="${item.name}" selected="selected">${item.value}</option>
			                </c:when>
							<c:otherwise>
								<option value="${item.name}">${item.value}</option>
							</c:otherwise>
						</c:choose>
					</c:forEach>
			</select>
			<input type ="text"  value="${siteCell}" name="siteCell" id="siteCell"  style="width:64%">
		</td> 	 
	</tr>
	
	<tr>
		<td><fmt:message key="filter.alarmLog.vendor"/></td>
		<td>
			<select name="vendor" id="vendor" class="wid80" onchange="xl()">
			<option value=""><fmt:message key="global.All"/></option>
           		<c:forEach var="item" items="${vendorList}">
					<c:choose>
		                <c:when test="${item.name == vendor}">
		                    <option value="${item.name}" selected="selected">${item.value}</option>
		                </c:when>
						<c:otherwise>
							<option value="${item.name}">${item.value}</option>
						</c:otherwise>
					</c:choose>
				</c:forEach>
			</select> 	
		</td> 
		
		<td><fmt:message key="filter.alarmLog.alarmClass"/></td>
		<td>
			<input type ="text"  value="${alarmClass}" name="alarmClass" id="alarmClass" class="wid80">
		</td>
		
		<td style = "width:80px;"><fmt:message key="filter.alarmLog.alarmName"/></td>
			<td colspan="3">
				<select name="conAlarmName" id="conAlarmName" style="width:13%" onchange="xl()">
		           		<c:forEach var="item" items="${conditonList}">
							<c:choose>
				                <c:when test="${item.name == conAlarmName}">
				                    <option value="${item.name}" selected="selected">${item.value}</option>
				                </c:when>
								<c:otherwise>
									<option value="${item.name}">${item.value}</option>
								</c:otherwise>
							</c:choose>
						</c:forEach>
				</select> 
				<input type ="text"  value="${alarmName}" name="alarmName" id="alarmName" style="width:84%">
			</td> 
		</tr>			
		<tr>	
			<td ><fmt:message key="filter.alarmLog.status"/></td>
			<td>
				<select name="status" id="status" class="wid80" onchange="xl()">
				<option value=""><fmt:message key="global.All"/></option>
		           		<c:forEach var="item" items="${statusList}">
							<c:choose>
				                <c:when test="${item.name == status}">
				                    <option value="${item.name}" selected="selected">${item.value}</option>
				                </c:when>
								<c:otherwise>
									<option value="${item.name}">${item.value}</option>
								</c:otherwise>
							</c:choose>
						</c:forEach>
				</select> 
			</td> 
			
			<td style = "width:80px;"><fmt:message key="filter.alarmLog.alarmType"/></td>
			<td>
					<select name="alarmType" id="alarmType" class="wid80" onchange="xl()">
						<option value=""><fmt:message key="global.All"/></option>
		           		<c:forEach var="item" items="${alarmTypeList}">
							<c:choose>
				                <c:when test="${item.name == alarmType}">
				                    <option value="${item.name}" selected="selected">${item.value}</option>
				                </c:when>
								<c:otherwise>
									<option value="${item.name}">${item.value}</option>
								</c:otherwise>
							</c:choose>
						</c:forEach>
					</select> 
			</td>
			
			<td colspan="3">
			
				<div style="width:80px;float:left;padding-top:4px;">
						Refresh  	 <c:choose>
								<c:when test="${reload != null}">
									<input id="reload" name="reload" type="checkbox" checked="checked">
								</c:when>
								<c:otherwise>
									<input id="reload" name="reload" type="checkbox" >
								</c:otherwise>
							</c:choose>
				</div>
				
				<div  id="time" style = "float:left;padding-top:4px;">
					Sau <input id="timeLoad" name="timeLoad" value="${timeLoad}" type="text"  maxlength="6" style="width:35px;text-align:center"> giây
				</div>
				
				<div style = "padding-left:15px;float:left;padding-top:4px;">
					<input class="button" type="submit" class="button" value="<fmt:message key="button.search"/>" />
				</div>
			</td>
		</tr>
	</table>
</form:form>
</div>

 <div id = "doublescroll">
	<display:table name="${alarmList}" class="simple4" id="alarmList" requestURI="" pagesize="100" sort="external" defaultsort="1" export="true">
	  	<display:column class="centerColumnMana" titleKey="global.list.STT" style="width:20px !important;"> <c:out value="${alarmList_rowNum}"/> </display:column>
	  	<display:column property="alarmClass" titleKey="table.alarmLog.alarmClass" sortable="true" sortName="ALARM_CLASS" class="ALARM_CLASS"/>
		<display:column property="bscid" titleKey="table.alarmLog.bscid" sortable="true" sortName="BSCID" class="styleBsc"/>
	  	<display:column property="siteCell" titleKey="table.alarmLog.site" sortable="true" sortName="SITE" class="styleBsc"/>
	  	<display:column property="sdate"  titleKey="table.alarmLog.startdate" format="{0,date,dd/MM/yyyy HH:mm:ss}" sortable="true" sortName="SDATE" class="styleTime"/>
		<display:column property="edate"  titleKey="table.alarmLog.enddate" format="{0,date,dd/MM/yyyy HH:mm:ss}" sortable="true" sortName="EDATE" class="EDATE"/>
		<display:column property="alarmType" titleKey="table.alarmLog.alarmType" sortable="true" sortName="ALARM_TYPE" class="styleAlarmType"/>
		<display:column property="alarmName" titleKey="table.alarmLog.alarmName" sortable="true" sortName="ALARM_NAME" class="styleAlarmName"/>
		<display:column property="alarmInfo" titleKey="table.alarmLog.alarmInfo" sortable="true" sortName="ALARM_INFO" class="styleAlarmInfo"/>
		
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
$('#alarmList>tbody>tr').each( function() {  
	var value = $(this).find(".EDATE").text();
	if( value!= ""){
		$(this).find("td").css({'background-color' : '#66FFFF'});
		}
	});

${highlight} 
</script>

<script type="text/javascript">
    Calendar.setup({
        inputField		:	"startTime",	// id of the input field
        ifFormat		:	"%d/%m/%Y %H:%M",   	// format of the input field
        button			:   "chooseStartDate",  	// trigger for the calendar (button ID)
        showsTime		:	true,
        singleClick		:   false					// double-click mode
    }); 

    Calendar.setup({
        inputField		:	"endTime",	// id of the input field
        ifFormat		:	"%d/%m/%Y %H:%M",   	// format of the input field
        button			:   "chooseEndDate",   	// trigger for the calendar (button ID)
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
		<c:forEach items="${bscidList}" var="listOfNames">
			availableTags[i] = "<c:out value='${listOfNames}' escapeXml='false' />";
			i = i + 1;
		</c:forEach>
		loadBsc(availableTags);
	});

/*  	$("#vendor").change(function(){
		$.getJSON("${pageContext.request.contextPath}/ajax/getBscByVendor.htm",{team: $(this).val()}, function(j){
			
			var availableTags = [];
			for (var i = 0; i < j.length; i++) {
				availableTags[i] = j[i];
			}
			
			loadBsc(availableTags);
		});
	
	});  */

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
	$(function() {
		var availableTags2 = [];
		var i = 0;
		<c:forEach items="${siteidList}" var="listOfNames">
			availableTags2[i] = "<c:out value='${listOfNames}' escapeXml='false' />";
			i = i + 1;
		</c:forEach>
		loadSite(availableTags2);
	});

	$("#bscid").change(function(){
		$.getJSON("${pageContext.request.contextPath}/ajax/getSite2GByBsc.htm",{term: $(this).val()}, function(j){
			
			var availableTags2 = [];
			for (var i = 0; i < j.length; i++) {
				availableTags2[i] = j[i];
			}
			
			loadSite(availableTags2);
		});
	
	});

	function loadSite(availableTags){
		function split( val ) {
		return val.split( /,\s*/ );
		}
		function extractLast( term ) {
		return split( term ).pop();
		}
		$( "#siteCell" )
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
		var bscTp = $("#siteCell").val();
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