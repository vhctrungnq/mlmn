
<%@ include file="/commons/taglibs.jsp"   %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!-- <meta http-equiv="Refresh" content="20; URL="> -->
<style>   
 
    #dvtTeamProcess {
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
<input type="hidden" name="reloadStr" id="reloadStr" value="${reloadStr}">
	<input type="hidden" name="highlight" id="highlight" value="${highlight}">
	<input type="hidden" name="highlight1" id="highlight1" value="${highlight1}">
	<input type="hidden" name="highlight2" id="highlight2" value="${highlight2}">

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
			<td  style="padding-left: 5px;width: 70px;"><fmt:message key="vAlRbLossConfig.dvtTeamProcess"/></td>
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
			<td style="padding-left: 5px;width: 70px;"><fmt:message key="vAlRbLossConfig.bscid"/></td>
			<td>
				<input type ="text" value="${bscid}" name="bscid" id="bscid" size="32">
			</td>
			<td style="padding-left: 5px;width: 70px;"><fmt:message key="vAlRbLossConfig.cellid"/></td>
			<td>
				<input type ="text"  value="${cellid}" name="cellid" id="cellid" size="32">
			</td>
			
		</tr>
		<tr>
			<td><fmt:message key="vAlRbLossConfig.totalMCH"/></td>
			<td>
				<input type ="text"  value="${totalMD}" name="totalMD" id="totalMD" size="5" maxlength="10" width="80px">
				&nbsp;-&nbsp;
				<input type ="text"  value="${totalTimeE}" name="totalTimeE" id="totalTimeE" size="5" maxlength="10" width="80px">
			</td>
			<td style="padding-left: 5px;"><fmt:message key="vAlRbLossConfig.dvtUserProcess"/></td>
			<td>
				<input type ="text"  value="${dvtUserProcess}" name="dvtUserProcess" id="dvtUserProcess" size="17" maxlength="19" width="80px">
			</td>
			<td  style="padding-left: 5px;"><fmt:message key="vAlRbLossConfig.ungCuuMpd"/></td>
			<td>
				<select name="ungCuuMpd" id="ungCuuMpd" style="width: 120px;height:20px; padding-top: 4px;">
				<option value=""><fmt:message key="global.All"/></option>
           		<c:forEach var="item" items="${ungCuuMpdList}">
					<c:choose>
		                <c:when test="${item.name == ungCuuMpd}">
		                    <option value="${item.name}" selected="selected">${item.value}</option>
		                </c:when>
						<c:otherwise>
							<option value="${item.name}">${item.value}</option>
						</c:otherwise>
					</c:choose>
				</c:forEach>
			</select> 
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
			<td>
				<select name="alarmType" id="alarmType" style="width: 120px;height:20px; padding-top: 4px;">
				<%-- <option value=""><fmt:message key="global.All"/></option> --%>
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
			
			</tr>
			<tr>
				<td style="width: 120px;" colspan="3">
					<div style="width:100px;float:left;">
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
					Sau <input id="timeLoad" name="timeLoad" value="${timeLoad}"  maxlength="6"  type="text" style="width:35px;text-align:center"> giây
					</div>	
				
				</td>
				<td colspan="7">
					<input class="button" type="submit" class="button" value="<fmt:message key="button.search"/>" />
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
  <div id="doublescroll" class="tableStandar">
	<display:table name="${alarmList}"   id="item" requestURI="" pagesize="50" sort="external" defaultsort="1" export="true">
	  	<display:column class="centerColumnMana" titleKey="global.list.STT" style="width:20px;"> <c:out value="${item_rowNum}"/> </display:column>
		<display:column property="shiftId" titleKey="rAlarmLog.shiftId" class="dpnone SHIFT_ID" headerClass="dpnone" />
		<display:column property="bscid" titleKey="vAlRbLossConfig.bscid" sortable="true" sortName="BSCID" style="min-width:65px;max-width:65px;"/>
	  	<display:column property="cellid" titleKey="vAlRbLossConfig.cellid" sortable="true" sortName="CELLID"  class="dpnone" headerClass="dpnone"/>
		<display:column titleKey="vAlRbLossConfig.cellid" sortable="true" sortName="CELLID" media="html" style="min-width:65px;max-width:65px;">
			<a href="detail.htm?bscId=${item.bscid}&site=${item.cellid}&startTime=${item.mchSdateStr}&alarmType=${alarmType}">${item.cellid}</a>&nbsp;
	 	</display:column>
		<display:column property="mchSdate" format="{0,date,dd/MM/yyyy HH:mm}" titleKey="vAlRbLossConfig.mchSdate" sortable="true" sortName="MCH_SDATE" style="min-width:105px;max-width:105px;"/>
		<display:column property="mchEdate"  format="{0,date,dd/MM/yyyy HH:mm}" titleKey="vAlRbLossConfig.mchEdate" sortable="true" sortName="MCH_EDATE" style="min-width:105px;max-width:105px;"/>
		<display:column property="tgMCH" titleKey="vAlRbLossConfig.tgMCH" sortable="true" sortName="TG_MCH" style="max-width:30px;"/>
		<display:column property="ddhBaoMch" format="{0,date,dd/MM/yyyy HH:mm}" titleKey="vAlRbLossConfig.ddhBaoMch" sortable="true" sortName="DDH_BAO_MCH" style="min-width:105px;max-width:105px;"/>
		<display:column property="sdate"  titleKey="vAlRbLossConfig.sdate" format="{0,date,dd/MM/yyyy HH:mm}" sortable="true" sortName="SDATE"  style="min-width:105px;max-width:105px;"/>
		<display:column property="edate"  titleKey="vAlRbLossConfig.edate" format="{0,date,dd/MM/yyyy HH:mm}" sortable="true" sortName="EDATE"  style="min-width:105px;max-width:105px;"/>
		<display:column property="tgMD" titleKey="vAlRbLossConfig.tgMD" sortable="true" sortName="TG_MD" style="max-width:30px;"/>
		<%-- <display:column property="ddhBaoMd" format="{0,date,dd/MM/yyyy HH:mm}" titleKey="vAlRbLossConfig.ddhBaoMd" sortable="true" sortName="DDH_BAO_MD" style="min-width:105px;max-width:105px;"/> --%>
		<display:column property="dvtTeamProcess" titleKey="vAlRbLossConfig.dvtTeamProcess" sortable="true" sortName="DVT_TEAM_PROCESS" style="max-width:40px;"/>
		<display:column property="dvtUserProcess"  titleKey="vAlRbLossConfig.dvtUserProcess" sortable="true" sortName="DVT_USER_PROCESS"  style="max-width:40px;"/>
		<display:column property="ungCuuMpd"  titleKey="vAlRbLossConfig.ungCuuMpd" sortable="true" sortName="UNG_CUU_MPD" class="UNG_CUU_MPD"  style="max-width:10px;"/>
		<display:column property="causebySy"  titleKey="vAlRbLossConfig.causebySy" sortable="true" sortName="CAUSEBY_SY"  style="max-width:30px;"/>
		<display:column property="causeby"  titleKey="vAlRbLossConfig.causebyUs" sortable="true" sortName="CAUSEBY"  style="max-width:40px;"/>
		<%-- <display:column property="actionProcess"  titleKey="vAlRbLossConfig.actionPeocess" sortable="true" sortName="ACTION_PROCESS"  style="max-width:40px;"/>--%>
		<display:column property="description"  titleKey="vAlRbLossConfig.description" sortable="true" sortName="DESCRIPTION"  style="max-width:40px;"/>
		<c:if test="${alarmType=='DOWN_SITE'}">
			<display:column class="centerColumnMana selectAllCheck" style="max-width:20px;" title="<input type='checkbox' id='selectAllCheck' onClick='javascript:funcSelectAll()' value='Select All' />" media="html">
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
			<display:column titleKey="title.quanLy" media="html" class="centerColumnMana" style="min-width:30px;">
			<a href="form.htm?id=${item.id}&alarmType=${alarmType}"><fmt:message key="button.edit"/></a>&nbsp;
	 			<a href="delete.htm?id=${item.id}&alarmType=${alarmType}"
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

	 function setAction(value) {
   	  var action = document.getElementById("action");
   	  action.value = value;

   	  return true;
   	 }
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

	$("#dvtTeamProcess").change(function(){
		$.getJSON("${pageContext.request.contextPath}/ajax/getBSCIDByTeam.htm",{team: $(this).val()}, function(j){
			
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
		var trs='<tr><th rowspan="2"><a><fmt:message key="global.list.STT"/></a></th>';
	    trs=trs + '<th rowspan="2" class="sortable"><a><fmt:message key="vAlRbLossConfig.bscid"/></a></th>';
	    trs=trs + '<th rowspan="2" class="sortable"><a><fmt:message key="vAlRbLossConfig.cellid"/></a></th>';
	    trs=trs +'<th colspan="3" class="sortable"><a><fmt:message key="vAlRbLossConfig.SLmatDien"/></a></th>';
	    trs=trs +'<th rowspan="2" class="sortable"><a><fmt:message key="vAlRbLossConfig.ddhBaoMd"/></a></th>';
	    trs=trs +'<th colspan="3" class="sortable"><a><fmt:message key="vAlRbLossConfig.TGMatCH"/></a></th>';
	    trs=trs +'<th rowspan="2" class="sortable"><a><fmt:message key="vAlRbLossConfig.ddhBaoMll"/></a></th>';
	    trs=trs +'<th colspan="2" class="sortable"><a><fmt:message key="vAlRbLossConfig.baoDVT"/></a></th>';
	    trs=trs +'<th rowspan="2" class="sortable"><a><fmt:message key="vAlRbLossConfig.ungCuuMpd"/></a></th>';
	    trs=trs +'<th rowspan="2" class="sortable"><a><fmt:message key="vAlRbLossConfig.description"/></a></th>';
	    trs=trs +'<th rowspan="2" class="sortable"><a><fmt:message key="title.quanLy"/></th></a></tr>';
	   	trs=trs +'<tr><th class="sortable"><a><fmt:message key="vAlRbLossConfig.sdate"/></a></th>';
	    trs=trs +'<th class="sortable"><a><fmt:message key="vAlRbLossConfig.edate"/></a></th>';
	    trs=trs +'<th class="sortable"><a><fmt:message key="vAlRbLossConfig.diffMD"/></a></th>';
	    trs=trs +'<th class="sortable"><a><fmt:message key="vAlRbLossConfig.mllSdate"/></a></th>';
	    trs=trs +'<th class="sortable"><a><fmt:message key="vAlRbLossConfig.mllEdate"/></a></th>';
	    trs=trs +'<th class="sortable"><a><fmt:message key="vAlRbLossConfig.diffMLL"/></a></th>';
	    trs=trs +'<th class="sortable"><a><fmt:message key="vAlRbLossConfig.dvtTeamProcess"/></a></th>';
	    trs=trs +'<th class="sortable"><a><fmt:message key="vAlRbLossConfig.dvtUserProcess"/></a></th></tr>';
	    
	//	$('#item thead').html(trs);
	/* var value = document.getElementById('reload').value;
	 if (value=="Starting Reload")
	 {
		 window.onload = setupRefresh;
	 }	 */
		
	});
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
	
    $(function() {
    	$('#item>tbody>tr').each(
    	    	 function(){
        	    	 var value = $(this).find(".UNG_CUU_MPD").text();
        	    	 if( value!=null && value =='Y'){
        	    		// $(this).find("td").css({ 'color' : 'blue', 'text-decoration': 'none'});
         	    		${highlight1}
            	    	}
     	    	 	if(value!=null && value =='N'){
         	    	 //	$(this).find("td").css({'color' : 'red', 'text-decoration': 'none'});
            	    	 ${highlight2}
         	    	 	}
     	    	 	var value = $(this).find(".SHIFT_ID").text();
   				 	if( value!=null && value !=''){
   					 ${highlight}
      					}
     	    	 	});
			   	 
				
    	}); 
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

		$.getJSON("${pageContext.request.contextPath}/LossConfiguration/ajax/checkedList.htm", {checkedList : checkedList, uncheckedList: uncheckedList}, function(j){
			
				window.location = '${pageContext.request.contextPath}/LossConfiguration/list.htm?alarmType=DOWN_SITE';
			
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
		$.getJSON("${pageContext.request.contextPath}/ajax/getSite2GByBsc.htm",{term: $( "#bscid" ).val()}, function(j){
			
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
		$( "#cellid" )
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
		var bscTp = $("#cellid").val();
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