
<%@ include file="/commons/taglibs.jsp"   %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!-- <meta http-equiv="Refresh" content="20; URL="> -->
<style type="text/css">   
 	/* #doublescroll { overflow: auto; overflow-y: hidden; }  
    #doublescroll p { margin: 0; padding: 1em; white-space: nowrap; } */
    
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
<title>${title}</title>
<content tag="heading">${title}</content> 	
<div>
<form:form commandName="filter" method="post" action="list.htm">
<input type="hidden" name="reloadStr" id="reloadStr" value="${reloadStr}">
<input type="hidden" name="highlight1" id="highlight1" value="${highlight1}">
<input type="hidden" name="highlight2" id="highlight2" value="${highlight2}">
<input type="hidden" name="highlight" id="highlight" value="${highlight}">

	<table>
		<tr>
			<td style="width: 80px;"><fmt:message key="vAlRbLossPower.TGBDMatDien"/></td>
			<td  style="width: 180px;">
				<input type ="text"  value="${startTime}" name="startTime" id="startTime" size="15" maxlength="10" style="width:70px">
	   			 <img alt="calendar" title="Click to choose the start date" id="chooseStartDate" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
				&nbsp;-&nbsp;
				<input type ="text"  value="${endTime}" name="endTime" id="endTime" size="15" maxlength="10" style="width:70px">
	   			<img alt="calendar" title="Click to choose the end date" id="chooseEndDate" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
				
			</td>
			<td style="width: 100px;"><fmt:message key="vAlRbLossPower.dvtTeamProcess"/></td>
			<td style="width: 150px;">
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
			<td style="width: 100px;"><fmt:message key="vAlRbLossPower.bscid"/></td>
			<td style="width: 150px;">
				
			    <input type ="text" value="${bscid}" name="bscid" id="bscid" width="80px" size="25">
			</td>
			<td style="width: 120px;"><fmt:message key="vAlRbLossPower.cellid"/></td>
			<td >
				<input type ="text"  value="${cellid}" name="cellid" id="cellid" size="17" width="80px">
			</td>
			
			
		</tr>
		<tr>
			<td ><fmt:message key="vAlRbLossPower.totalMD"/></td>
			<td>
				<input type ="text"  value="${totalMD}" name="totalMD" id="totalMD" size="12" maxlength="10" width="80px">
				&nbsp;-&nbsp;
				<input type ="text"  value="${totalMDE}" name="totalMDE" id="totalMDE" size="12" maxlength="10" width="80px">
			</td>
			
			<td ><fmt:message key="vAlRbLossPower.dvtUserProcess"/></td>
			<td>
				<input type ="text"  value="${dvtUserProcess}" name="dvtUserProcess" id="dvtUserProcess" size="20" maxlength="19" width="80px">
			</td>
			<td  ><fmt:message key="vAlRbLossPower.ungCuuMpd"/></td>
			<td>
				<select name="ungCuuMpd" id="ungCuuMpd" style="width: 170px;height:20px; padding-top: 4px;">
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
			<td ><fmt:message key="vAlRbLossPower.nodeTruyenDan"/></td>
			<td>
				<select name="nodeTruyenDan" id="nodeTruyenDan" style="width: 120px;height:20px; padding-top: 4px;">
				<option value=""><fmt:message key="global.All"/></option>
           		<c:forEach var="item" items="${paraList}">
					<c:choose>
		                <c:when test="${item.name == nodeTruyenDan}">
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
			<td><fmt:message key="vAlRbLossPower.acLow"/></td>
			<td>
				<select name="acLow" id="acLow" style="width: 200px;height:20px; padding-top: 4px;">
				<option value=""><fmt:message key="global.All"/></option>
           		<c:forEach var="item" items="${paraList}">
					<c:choose>
		                <c:when test="${item.name == acLow}">
		                    <option value="${item.name}" selected="selected">${item.value}</option>
		                </c:when>
						<c:otherwise>
							<option value="${item.name}">${item.value}</option>
						</c:otherwise>
					</c:choose>
				</c:forEach>
				</select> 
				
			</td>
			<td ><fmt:message key="vAlRbLossPower.statusKTMD"/></td>
			<td>
				<select name="statusKTMD" id="statusKTMD" style="width: 140px;height:20px; padding-top: 4px;">
				<option value=""><fmt:message key="global.All"/></option>
           		<c:forEach var="item" items="${statusKTMDList}">
					<c:choose>
		                <c:when test="${item.name == statusKTMD}">
		                    <option value="${item.name}" selected="selected">${item.value}</option>
		                </c:when>
						<c:otherwise>
							<option value="${item.name}">${item.value}</option>
						</c:otherwise>
					</c:choose>
				</c:forEach>
			</select> 
			</td>
			<td><fmt:message key="vAlRbLossPower.statusKTMLL"/></td>
			<td>
				<select name="statusKTMLL" id="statusKTMLL" style="width: 170px;height:20px; padding-top: 4px;">
				<option value=""><fmt:message key="global.All"/></option>
           		<c:forEach var="item" items="${statusKTMDList}">
					<c:choose>
		                <c:when test="${item.name == statusKTMLL}">
		                    <option value="${item.name}" selected="selected">${item.value}</option>
		                </c:when>
						<c:otherwise>
							<option value="${item.name}">${item.value}</option>
						</c:otherwise>
					</c:choose>
				</c:forEach>
			</select> 
			
			</td>
			
			<td><fmt:message key="vAlRbLossPower.mch1800"/></td>
			<td>
				<select name="mch1800" id="mch1800" style="width: 120px;height:20px; padding-top: 4px;">
				<option value=""><fmt:message key="global.All"/></option>
           		<c:forEach var="item" items="${paraList}">
					<c:choose>
		                <c:when test="${item.name == mch1800}">
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
			<td><fmt:message key="qLPhongBan.region"/></td>
			<td>
		        <select id="region">
		        		<option value=""></option>
	   					<c:forEach var="items" items="${regionList}">
			              <c:choose>
			                <c:when test="${items.name == region}">
			                    <option value="${items.name}" selected="selected">${items.value}</option>
			                </c:when>
			                <c:otherwise>
			                    <option value="${items.name}">${items.value}</option>
			                </c:otherwise>
			              </c:choose>
					    </c:forEach>
	           		</select>	
		        
	        </td>
			<td colspan="2">
				
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
				<div  id="time" style="padding-left: 8px">
				Sau <input id="timeLoad" name="timeLoad" value="${timeLoad}" type="text"  maxlength="6" style="width:35px;text-align:center"> giây
			</div>	
			</td>
			<td colspan="3"><input class="button" type="submit" class="button" value="<fmt:message key="button.search"/>" /></td>
			<c:if test="${checkCaTruc==true}">
				<td class="wid10 mwid60 ftsize12" align="right" colspan="8">
		            <a href="upload.htm"><fmt:message key="button.upload"/></a>
		        </td>
	   		</c:if>
		</tr>
		
	</table>
</form:form>
</div>

  <div id="doublescroll" class="tableStandar">
	<display:table name="${alarmList}"  id="item" requestURI="" pagesize="50" sort="external" defaultsort="1" export="true">
	  	<display:column class="centerColumnMana" titleKey="global.list.STT" style="width:20px;"> <c:out value="${item_rowNum}"/> </display:column>
		<display:column property="shiftId" titleKey="rAlarmLog.shiftId" class="dpnone SHIFT_ID" headerClass="dpnone" media="html" />
		<display:column property="bscid" titleKey="vAlRbLossPower.bscid" sortable="true" sortName="BSCID" style="min-width:55px;max-width:55px;"/>
	  	<display:column property="cellid" titleKey="vAlRbLossPower.cellid" sortable="true" sortName="CELLID"  class="dpnone" headerClass="dpnone"/>
		<display:column titleKey="vAlRbLossPower.cellid" sortable="true" sortName="CELLID"  media="html" style="min-width:50px;max-width:50px;">
			<a href="detail.htm?bscId=${item.bscid}&cellid=${item.cellid}&startTime=${item.sdateStr}">${item.cellid}</a>&nbsp;
	 	</display:column>
	  	<display:column property="sdate"  titleKey="vAlRbLossPower.sdate" format="{0,date,dd/MM/yyyy HH:mm}" sortable="true" sortName="SDATE" style="min-width:105px;max-width:105px;"  media="html"/>
		<display:column property="edate"  titleKey="vAlRbLossPower.edate" format="{0,date,dd/MM/yyyy HH:mm}" sortable="true" sortName="EDATE" style="min-width:105px;max-width:105px;"  media="html"/>
		<display:column property="sdate"  titleKey="vAlRbLossPower.sdate" format="{0,date,dd/MM/yyyy HH:mm:ss}" class="dpnone" headerClass="dpnone"/>
		<display:column property="edate"  titleKey="vAlRbLossPower.edate" format="{0,date,dd/MM/yyyy HH:mm:ss}" class="dpnone" headerClass="dpnone"/>
		<display:column property="tgMD" titleKey="vAlRbLossPower.diffMD" sortable="true" sortName="TG_MD" style="max-width:30px;"/>
		<display:column property="ddhBaoMd" format="{0,date,dd/MM/yyyy HH:mm}" titleKey="vAlRbLossPower.ddhBaoMd" sortable="true" sortName="DDH_BAO_MD" style="min-width:105px;max-width:105px;"  media="html"/>
		<display:column property="mllSdate" format="{0,date,dd/MM/yyyy HH:mm}" titleKey="vAlRbLossPower.mllSdate" sortable="true" sortName="MLL_SDATE" style="min-width:105px;max-width:105px;"  media="html"/>
		<display:column property="mllEdate"  format="{0,date,dd/MM/yyyy HH:mm}" titleKey="vAlRbLossPower.mllEdate" sortable="true" sortName="MLL_EDATE" style="min-width:105px;max-width:105px;"  media="html"/>
		<display:column property="ddhBaoMd" format="{0,date,dd/MM/yyyy HH:mm:ss}" titleKey="vAlRbLossPower.ddhBaoMd"  class="dpnone" headerClass="dpnone"/>
		<display:column property="mllSdate" format="{0,date,dd/MM/yyyy HH:mm:ss}" titleKey="vAlRbLossPower.mllSdate"  class="dpnone" headerClass="dpnone"/>
		<display:column property="mllEdate"  format="{0,date,dd/MM/yyyy HH:mm:ss}" titleKey="vAlRbLossPower.mllEdate"  class="dpnone" headerClass="dpnone"/>
		<display:column property="tgMLL" titleKey="vAlRbLossPower.diffMLL" sortable="true" sortName="TG_MLL" style="max-width:30px;"/>
		<display:column property="ddhBaoMll" format="{0,date,dd/MM/yyyy HH:mm}" titleKey="vAlRbLossPower.ddhBaoMll" sortable="true" sortName="DDH_BAO_MLL" style="min-width:105px;max-width:105px;"  media="html"/>
		<display:column property="ddhBaoMll" format="{0,date,dd/MM/yyyy HH:mm:ss}" titleKey="vAlRbLossPower.ddhBaoMll" class="dpnone" headerClass="dpnone"/>
		<display:column property="dvtTeamProcess" titleKey="vAlRbLossPower.dvtTeamProcess" sortable="true" sortName="DVT_TEAM_PROCESS" style="max-width:40px;"/>
		<display:column property="dvtUserProcess"  titleKey="vAlRbLossPower.dvtUserProcess" sortable="true" sortName="DVT_USER_PROCESS" style="max-width:50px;"/>
		<display:column property="ungCuuMpd"  titleKey="vAlRbLossPower.ungCuuMpd" sortable="true" sortName="UNG_CUU_MPD" class="UNG_CUU_MPD" style="max-width:10px;"/>
		<display:column property="nodeTruyenDan"  titleKey="vAlRbLossPower.nodeTruyenDan" sortable="true" sortName="NODE_TRUYEN_DAN" style="max-width:10px;"/>
		<display:column property="acLow"  titleKey="vAlRbLossPower.acLow" sortable="true" sortName="AC_LOW" style="max-width:10px;"/>
		<display:column property="mch1800"  titleKey="vAlRbLossPower.mch1800" sortable="true" sortName="MCH_1800" style="max-width:10px;"/>
		<display:column property="region"  titleKey="qLPhongBan.region" sortable="true" sortName="REGION" style="max-width:50px;"/>
		<display:column property="description"  titleKey="vAlRbLossPower.description" sortable="true" sortName="DESCRIPTION" style="max-width:50px;"/>
		<c:if test="${checkCaTruc==true}">
			<display:column titleKey="title.quanLy" media="html" class="centerColumnMana">
			<a href="form.htm?id=${item.id}"><fmt:message key="button.edit"/></a>&nbsp;
	 			<a href="delete.htm?id=${item.id}&region=${region}"
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

<script type="text/javascript">
    Calendar.setup({
        inputField		:	"startTime",	// id of the input field
        ifFormat		:	"%d/%m/%Y",   	// format of the input field
        button			:   "chooseStartDate",  	// trigger for the calendar (button ID)
        showsTime		:	true,
        singleClick		:   false					// double-click mode
    });

    Calendar.setup({
        inputField		:	"endTime",	// id of the input field
        ifFormat		:	"%d/%m/%Y",   	// format of the input field
        button			:   "chooseEndDate",  	// trigger for the calendar (button ID)
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
		var trs='<tr><th rowspan="2"><a><fmt:message key="global.list.STT"/></a></th>';
	    trs=trs + '<th rowspan="2" class="sortable"><a><fmt:message key="vAlRbLossPower.bscid"/></a></th>';
	    trs=trs + '<th rowspan="2" class="sortable"><a><fmt:message key="vAlRbLossPower.cellid"/></a></th>';
	    trs=trs +'<th colspan="3" class="sortable"><a><fmt:message key="vAlRbLossPower.SLmatDien"/></a></th>';
	    trs=trs +'<th rowspan="2" class="sortable"><a><fmt:message key="vAlRbLossPower.ddhBaoMd"/></a></th>';
	    trs=trs +'<th colspan="3" class="sortable"><a><fmt:message key="vAlRbLossPower.TGMatLienLac"/></a></th>';
	    trs=trs +'<th rowspan="2" class="sortable"><a><fmt:message key="vAlRbLossPower.ddhBaoMll"/></a></th>';
	    trs=trs +'<th colspan="2" class="sortable"><a><fmt:message key="vAlRbLossPower.baoDVT"/></a></th>';
	    trs=trs +'<th rowspan="2" class="sortable"><a><fmt:message key="vAlRbLossPower.ungCuuMpd"/></a></th>';
	    trs=trs +'<th rowspan="2" class="sortable"><a><fmt:message key="vAlRbLossPower.nodeTruyenDan"/></a></th>';
	    trs=trs +'<th rowspan="2" class="sortable"><a><fmt:message key="vAlRbLossPower.acLow"/></a></th>';
	    trs=trs +'<th rowspan="2" class="sortable"><a><fmt:message key="vAlRbLossPower.description"/></a></th>';
	    trs=trs +'<th rowspan="2" class="sortable"><a><fmt:message key="title.quanLy"/></th></a></tr>';
	   	trs=trs +'<tr><th class="sortable"><a><fmt:message key="vAlRbLossPower.sdate"/></a></th>';
	    trs=trs +'<th class="sortable"><a><fmt:message key="vAlRbLossPower.edate"/></a></th>';
	    trs=trs +'<th class="sortable"><a><fmt:message key="vAlRbLossPower.diffMD"/></a></th>';
	    trs=trs +'<th class="sortable"><a><fmt:message key="vAlRbLossPower.mllSdate"/></a></th>';
	    trs=trs +'<th class="sortable"><a><fmt:message key="vAlRbLossPower.mllEdate"/></a></th>';
	    trs=trs +'<th class="sortable"><a><fmt:message key="vAlRbLossPower.diffMLL"/></a></th>';
	    trs=trs +'<th class="sortable"><a><fmt:message key="vAlRbLossPower.dvtTeamProcess"/></a></th>';
	    trs=trs +'<th class="sortable"><a><fmt:message key="vAlRbLossPower.dvtUserProcess"/></a></th></tr>';
	    
	//	$('#item thead').html(trs);
	/* var value = document.getElementById('reload').value;
	 if (value=="Starting Reload")
	 {
		 window.onload = setupRefresh;
	 }	 */

	  //  $("#bscid").dropdownchecklist( { firstItemChecksAll: 'exclusive', explicitClose: '<i>...close</i>' } );
	});
	
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
	</script>
  <!-- <script type="text/javascript">
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
</script> -->