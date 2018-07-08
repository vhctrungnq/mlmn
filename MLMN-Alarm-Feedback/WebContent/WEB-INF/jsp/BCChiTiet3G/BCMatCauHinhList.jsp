<%@ include file="/commons/taglibs.jsp"   %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<title>${title}</title>
<style>   
 	#doublescroll { overflow: auto; overflow-y: hidden; }  
    #doublescroll p { margin: 0; padding: 1em; white-space: nowrap; }
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
<content tag="heading">${title}</content> 	
<div>
<form:form commandName="filter" method="get" action="dsach_ch.htm">
<input type="hidden" name="reloadStr" id="reloadStr" value="${reloadStr}">
<input type="hidden" name="highlight" id="highlight" value="${highlight}">
	
	<table>
		<tr>
			<td style="width: 70px;"><fmt:message key="vAlRbLossConfig.TGBDMatDien"/></td>
			<td style="width:150px;">
				<input type ="text"  value="${startTime}" name="startTime" id="startTime" size="17" maxlength="16" style="width:100px">
	   			 <img alt="calendar" title="Click to choose the start date" id="chooseStartDate" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
			</td>
			<td style="padding-left: 5px;width: 70px;"><fmt:message key="vAlRbLossConfig.TGKTMatDien"/></td>
			<td style="width:125px;">
				<input type ="text"  value="${endTime}" name="endTime" id="endTime" size="17" maxlength="16" style="width:100px">
		   		<img alt="calendar" title="Click to choose the start date" id="chooseEndDate" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
			</td>
			<td  style="padding-left: 5px;width: 80px;" ><fmt:message key="vAlRbLossConfig3G.dvtTeamProcess"/></td>
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
			<td style="padding-left: 5px;width: 80px;"><fmt:message key="vAlRbLossConfig3G.rncid"/></td>
			<td colspan="2" >
			     <input type ="text" value="${rncid}" name="rncid" id="rncid" size="32">
			</td>
			<td style="padding-left: 5px;width: 80px;"><fmt:message key="vAlRbLossConfig3G.alarmLevel"/></td>
			<td colspan="2" >
				<%-- <form:input type ="text" path="alarmLevel" maxlength="18" style="width:80px;" rows="3"/> --%>
				<input type ="text"  value="${alarmLevel}" name="alarmLevel" id="alarmLevel" size="32">
			</td>
			
		</tr>
		<tr>
			<td><fmt:message key="vAlRbLossConfig3G.totalMD"/></td>	
			<td>
						<input type ="text"  value="${totalMD}" name="totalMD" id="totalMD" size="5" maxlength="10" width="80px">
						&nbsp;-&nbsp;
						<input type ="text"  value="${totalTimeE}" name="totalTimeE" id="totalTimeE" size="5" maxlength="10" width="80px">
			</td>
			<td style="padding-left: 5px;"><fmt:message key="vAlRbLossConfig3G.dvtUserProcess"/></td>
			<td>
				<input type ="text"  value="${dvtUserProcess}" name="dvtUserProcess" id="dvtUserProcess" size="17" maxlength="19" width="80px">
			</td>
			<td style="padding-left: 5px;"><fmt:message key="vAlRbLossPower.statusKTMCH"/></td>
			<td>
				<select name="statusKTMCH" id="statusKTMCH" style="width: 120px;height:20px; padding-top: 4px;">
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
			<td  style="padding-left: 5px;"><fmt:message key="vAlRbLossConfig.alarmTypeTK"/></td>
			<td style="width: 120px;">
				<select name="alarmType" id="alarmType" style="width: 120px;height:20px; padding-top: 4px;">
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
			<td style="padding-left:5px;width: 200px;" colspan="2">
				<div style="padding-left:5px;width:80px;float:left;">
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
				Sau <input id="timeLoad" name="timeLoad" value="${timeLoad}" type="text" maxlength="6" style="width:35px;text-align:center"> giây
				</div>	
			</td>
			<td colspan="2">
				<input class="button" type="submit" class="button"  value="<fmt:message key="button.search"/>" />
			</td>
			</tr>	
			
	</table>
</form:form>
</div>
<c:if test="${alarmType=='DOWN_SITE'}">
	<div class="paddingTop10" align="right">
		<input class="button" type="button" id="capNhat" name="capNhat" value="<fmt:message key="global.form.capnhathienthi"/>" title="Cập nhật trạng thái hiển thị cảnh báo"/>
	</div>
</c:if>
  <div id="doublescroll" >
	<display:table name="${alarmList}"  class="simple2" id="item" requestURI="" pagesize="50" sort="external" defaultsort="1" export="true">
	  	<display:column class="centerColumnMana" titleKey="global.list.STT" style="width:20px;"> <c:out value="${item_rowNum}"/> </display:column>
		<display:column property="shiftId" titleKey="rAlarmLog.shiftId" class="dpnone SHIFT_ID" headerClass="dpnone" />
		<display:column property="rncid" titleKey="vAlRbLossConfig3G.rncid" sortable="true" sortName="RNCID" style="min-width:65px;max-width:65px;"/>
	  	<display:column property="alarmLevel" titleKey="vAlRbLossConfig3G.alarmLevel" sortable="true" sortName="ALARM_LEVEL" class="dpnone" headerClass="dpnone"/>
	  	<display:column titleKey="vAlRbLossConfig3G.alarmLevel" sortable="true" sortName="ALARM_LEVEL" media="html" style="min-width:65px;max-width:65px;">
			<a href="detail.htm?rncid=${item.rncid}&alarmLevel=${item.alarmLevel}&startTime=${item.sdateStr}&alarmType=${alarmType}">${item.alarmLevel}</a>&nbsp;
	 	</display:column>
	 	<display:column property="sdate" format="{0,date,dd/MM/yyyy HH:mm}" titleKey="vAlRbLossConfig3G.sdate" sortable="true" sortName="SDATE" style="min-width:105px;max-width:105px;"/>
		<display:column property="edate"  format="{0,date,dd/MM/yyyy HH:mm}" titleKey="vAlRbLossConfig3G.edate" sortable="true" sortName="EDATE"  style="min-width:105px;max-width:105px;"/>
		<display:column property="duration" titleKey="vAlRbLossConfig3G.duration" sortable="true" sortName="DURATION"  style="max-width:20px;"/>
		<display:column property="ddhBaoMch" format="{0,date,dd/MM/yyyy HH:mm}" titleKey="vAlRbLossConfig.ddhBaoMch" sortable="true" sortName="DDH_BAO_MCH" style="min-width:105px;max-width:105px;"/>
		<display:column property="causeSdate"  titleKey="vAlRbLossConfig.sdate" format="{0,date,dd/MM/yyyy HH:mm}" sortable="true" sortName="CAUSE_SDATE"  style="min-width:105px;max-width:105px;"/>
		<display:column property="causeEdate"  titleKey="vAlRbLossConfig.edate" format="{0,date,dd/MM/yyyy HH:mm}" sortable="true" sortName="CAUSE_EDATE"  style="min-width:105px;max-width:105px;"/>
		<display:column property="causebySy" titleKey="vAlRbLossConfig3G.causebySy" sortable="true" sortName="CAUSEBY_SY" style="max-width:30px;"/>
		<display:column property="causeby" titleKey="vAlRbLossConfig3G.causeby" sortable="true" sortName="CAUSEBY" style="max-width:48px;"/>
		<%--<display:column property="actionProcess"  titleKey="vAlRbLossConfig3G.actionProcess" sortable="true" sortName="ACTION_PROCESS" style="max-width:40px;" />--%>
		<display:column property="description"  titleKey="vAlRbLossConfig3G.description" sortable="true" sortName="DESCRIPTION" style="max-width:40px;"/>
		<display:column property="dvtTeamProcess" titleKey="vAlRbLossConfig3G.dvtTeamProcess" sortable="true" sortName="DVT_TEAM_PROCESS"  style="max-width:40px;"/>
		<display:column property="dvtUserProcess" titleKey="vAlRbLossConfig3G.dvtUserProcess" sortable="true" sortName="DVT_USER_PROCESS"  style="max-width:40px;"/>
		<c:if test="${alarmType=='DOWN_SITE'}">
			<display:column class="centerColumnMana selectAllCheck" style="max-width:20px;" title="<input type='checkbox' id='selectAllCheck' value='Select All' />" media="html">
				<c:choose>
					<c:when test="${item.statusView == 'Y'}">
						<input class="selectall" type="checkbox" name="lang" value="${item.id}" checked="checked"/>
					</c:when>
					<c:otherwise>
						<input class="selectall" type="checkbox" name="lang" value="${item.id}"/>
					</c:otherwise>
				</c:choose>
		    </display:column>
	    </c:if>
		<c:if test="${checkCaTruc==true}">
			<display:column titleKey="title.quanLy" media="html" class="centerColumnMana" style="min-width:60px;max-width:30px;">
			<%-- <a href="cau_hinh_form.htm?sdate=${item.sdateStr}&rncid=${item.rncid}&alarmLevel=${item.alarmLevel}&fmAlarmId=${item.fmAlarmId}&alarmType=${alarmType}"><fmt:message key="button.edit"/></a>&nbsp;
	 			<a href="cau_hinh_delete.htm?sdate=${item.sdateStr}&rncid=${item.rncid}&alarmLevel=${item.alarmLevel}&fmAlarmId=${item.fmAlarmId}&alarmType=${alarmType}"
									onclick="return confirm('<fmt:message key="messsage.confirm.delete"/>')"><fmt:message key="button.delete"/></a>&nbsp;
			   --%>	
			   	 <a href="cau_hinh_form.htm?id=${item.id}&alarmType=${alarmType}"><fmt:message key="button.edit"/></a>&nbsp;
	 			<a href="cau_hinh_delete.htm?id=${item.id}&alarmType=${alarmType}" onclick="return confirm('<fmt:message key="messsage.confirm.delete"/>')"><fmt:message key="button.delete"/></a>&nbsp;
			  	
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
	var reload = $("#reload").val();
	if (reload=='Y') {
		setTimeout(function(){
			$('#filter').submit();
		}, 
		10000);
		$('#reloadStr').val('Y');
	}
	$('#item>tbody>tr').each( function() { 
   	 var value = $(this).find(".SHIFT_ID").text();
   	 if( value!=null && value !=''){
   		 ${highlight}
	    	}
	 	});
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

	$('#capNhat').click(function(){
		var val = [];
		var checkedList = "";
		var uncheckedList = "";
		
		
		$(':checkbox').each(function(i){
			if($(this).is(':checked'))
			{
				if($(this).val() != "Select All" && $(this).val() != "on"){
				checkedList += $(this).val() + "-";}
			}
			else
			{
				if($(this).val() != "Select All" && $(this).val() != "on"){
				uncheckedList += $(this).val() + "-";
				}
			}
			
		});

		$.getJSON("${pageContext.request.contextPath}/BCChiTiet3G/ajax/checkedList.htm", {checkedList : checkedList, uncheckedList: uncheckedList}, function(j){
			
				window.location = '${pageContext.request.contextPath}/BCChiTiet3G/dsach_ch.htm?alarmType=DOWN_SITE';
			
		});
	});

	$("#selectAllCheck").click(function() {
		 if($("#selectAllCheck").is(":checked")) // "this" refers to the element that fired the event
	    {
	       
	    	$('#item input:checkbox:not(:checked)').each(function() {
	    		$(this).attr('checked',true);
	    	});
	    }
	    else
	    {
	    	$('#item input:checked').each(function() {
	    		$(this).removeAttr('checked');
	    	});
	    }  

		
	
	});
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

	$("#cellid").focus(function(){
		alert($("#rncid").val());
		$.getJSON("${pageContext.request.contextPath}/ajax/getSite3GByBsc.htm",{term: $("#rncid").val()}, function(j){
				
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
		$( "#alarmLevel" )
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
		var bscTp = $("#alarmLevel").val();
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