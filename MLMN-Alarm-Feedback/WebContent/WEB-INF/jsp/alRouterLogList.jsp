<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<style type="text/css">
    
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
<title><fmt:message key="alarmRouter.title"/></title>
<content tag="heading"><fmt:message key="alarmRouter.title"/></content>

<form:form commandName="filter" method="post" action="router.htm">
<input type="hidden" name="reloadStr" id="reloadStr" value="${reloadStr}">
	<table style="width:80%" class="form">
		<tr>
			<td style = "width:70px;">Từ ngày</td>
        	<td> <input value="${sDate}" name="sDate" id="sDate" size="15" maxlength="16"/>
            	<img alt="calendar" title="Click to choose the start date" id="choosesDate" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/><form:errors path="sDate" cssClass="error"></form:errors></td>
 
        	<td style = "width:70px;">Đến ngày</td>
        	<td><input value="${eDate}" name="eDate" id="eDate" size="15" maxlength="16"/>
            	<img alt="calendar" title="Click to choose the end date" id="chooseeDate" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/><form:errors path="eDate" cssClass="error"></form:errors></td>
            
            <td style = "width:90px;"><fmt:message key="alarmRouter.routerName"/></td>
        	<td> <form:input path="routerName" id="routerName" size="15" />
        	</td>
        		
            <td style = "width:70px;"><fmt:message key="alarmRouter.ip"/></td>
        	<td> <form:input id="ip" path = "ip" width="80px" size="25"/>
        	</td>
        	</tr>
        	<tr>
        	<td style = "width:100px;"><fmt:message key="Thông tin lỗi"/></td>
        	<td colspan = 3> 
        	<form:input   path = "alarmInfo" style = "width:100%;" size="25"/>
        	</td>
        	<td style="padding-left: 10px;">Refresh  	 
		            <c:choose>
						<c:when test="${reload != null}">
							<input id="reload" name="reload" type="checkbox" checked="checked">
						</c:when>
						<c:otherwise>
							<input id="reload" name="reload" type="checkbox" >
						</c:otherwise>
					</c:choose>
			</td>
			<td>
				<div id="refresh" style="padding-left: 8px">Sau <input id="timeLoad" name="timeLoad" value="${timeLoad}" type="text"  style="width:35px;text-align:center">giây</div>
			</td>
            <td><input class="button" type="submit" class="button" id="submit" value="<fmt:message key="button.search"/>"/>
            </td>           
       </tr>
	</table>
</form:form>
<div id="doublescroll">
<display:table name="${alarmRouterLogList}" id="item" class="simple2" export="true" requestURI="" pagesize="100" sort="external" defaultsort="1">
	<display:column class="centerColumnMana" titleKey="global.list.STT" style="width:20px;"> <c:out value="${item_rowNum}"/> </display:column> 
    <display:column property="sdate" titleKey="alarmRouter.sdate" format="{0,date,dd/MM/yyyy HH:mm:ss}" style="width: 10px;" sortable="true" sortName="SDATE"/>    
    <display:column property="routerName" titleKey="alarmRouter.routerName" style="width: 10px;" sortable="true" sortName="ROUTER_NAME"/>    
    <display:column property="ip" titleKey="alarmRouter.ip" style="width: 10px;" sortable="true" sortName="IP"/>
    <display:column property="local" titleKey="alarmRouter.local" style="width: 10px;"sortable="true" sortName="LOCAL"/> 
    <display:column property="alarmInfo" titleKey="alarmRouter.alarmInfo" style="width: 1000px;" sortable="true" sortName="ALARM_INFO"/>        
    <display:column property="createDate" format="{0,date,dd/MM/yyyy HH:mm:ss}" titleKey="alarmRouter.createDate" style="width: 10px;" sortable="true" sortName="CREATE_DATE"/>
    
    <display:setProperty name="export.csv.filename" value="RouterLog.csv"/>
	<display:setProperty name="export.excel.filename" value="RouterLog.xls"/>
	<display:setProperty name="export.xml.filename" value="RouterLog.xml"/>
</display:table>
</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/calendar.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/calendar_en.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/calendar_setup.js"></script>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/calendar-blue.css" />

  <script type="text/javascript">
	$(function() {
		var availableTags = [];
		var i = 0;
		<c:forEach items="${routerNameList}" var="listOfNames">
			availableTags[i] = "<c:out value='${listOfNames.routerName}' escapeXml='false' />";
			i = i + 1;
		</c:forEach>
		loadrouterName(availableTags);
	});
/* 	
  	$("#routerName").change(function(){
		$.getJSON("${pageContext.request.contextPath}/ajax/getRouterName.htm",{team: $(this).val()}, function(j){
			
			var availableTags = [];
			for (var i = 0; i < j.length; i++) {
				availableTags[i] = j[i];
			}
			
			loadrouterName(availableTags);
		});
	
	}); */

	function loadrouterName(availableTags){
		function split( val ) {
		return val.split( /,\s*/ );
		}
		function extractLast( term ) {
		return split( term ).pop();
		}
		$( "#routerName" )
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
		var bscTp = $("#routerName").val();
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
</script>
