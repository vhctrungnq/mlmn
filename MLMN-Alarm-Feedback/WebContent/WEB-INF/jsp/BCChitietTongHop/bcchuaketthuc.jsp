<%@ include file="/commons/taglibs.jsp"   %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<title>${title}</title>
<style>   
 	#doublescroll { overflow: auto; overflow-y: hidden; }  
    #doublescroll p { margin: 0; padding: 1em; white-space: nowrap; }
    
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
<form:form commandName="filter" method="get" action="danhsach.htm">
<input type="hidden" name="alarmType" id="alarmType" value="${alarmType}">
<input type="hidden" name="netWork" id="netWork" value="${netWork}">
<%-- <input type="hidden" name="reloadStr" id="reloadStr" value="${reloadStr}"> --%>

	<table style = "width:90%">
		<tr>
			
			<td align="left" ><fmt:message key="AlDyNonFinishDetail.startT"/></td>
	    	<td>
	    		<input type ="text" value="${startTime}" name="startTime" id="startTime" size="15" maxlength="16" width="60px">
	   			 <img alt="calendar" title="Click to choose the start date" id="chooseStartDate" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
	    	</td>
	    	<td style="width:70px; padding-left:5px;" align="left"><fmt:message key="AlDyNonFinishDetail.endT"/> </td>
	    	<td>
	    		<input type ="text" value="${endTime}" name="endTime" id="endTime" size="15" maxlength="16" width="60px">
	    		<img alt="calendar" title="Click to choose the stop date" id="chooseStopDate" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
			</td>
			<td><fmt:message key="AlDyNonFinishDetail.teamProcess"/></td>
			<td style = "padding-left:5px">
				<select name="dvtTeamProcess" id="dvtTeamProcess" style="width: 120px;height:20px;">
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
			<td>
			<c:choose>
                <c:when test="${netWork == '3G'}">
                    <fmt:message key="AlDyNonFinishDetail.rncid"/>
                </c:when>
				<c:otherwise>
					<fmt:message key="AlDyNonFinishDetail.bscid"/>
				</c:otherwise>
			</c:choose>
			</td>
		    <td style = "padding-left:5px"><input type ="text"  value="${bscid}" name="bscid" id="bscid" size="20" width="120px"></td> 
			<td><fmt:message key="AlDyNonFinishDetail.site"/></td>
			<td style = "padding-left:5px"><input type ="text"  value="${siteid}" name="siteid" id="siteid" size="20"  width="120px"></td>
			<td style = "padding-left:5px"><input class="button" type="submit" class="button" value="Tìm kiếm" /></td>
	</table>
</form:form>
</div>

  <div id="doublescroll" >
	<display:table name="${alarmList}"  class="simple2" id="item" requestURI="" pagesize="50" sort="external" defaultsort="1" export="true">
	  	<display:column class="centerColumnMana" titleKey="global.list.STT" style="width:30px;"> <c:out value="${item_rowNum}"/> </display:column>
	  	 <display:column property="day" format="{0,date,dd/MM/yyyy}" titleKey="AlDyNonFinishDetail.day" sortable="true" sortName="DAY" class = "styleDay"/>
	  	<c:choose>
            <c:when test="${netWork == '3G'}">
               <display:column property="bscid" titleKey="AlDyNonFinishDetail.rncid" sortable="true" sortName="BSCID" class = "styleBsc"/>
            </c:when>
			<c:otherwise>
					<display:column property="bscid" titleKey="AlDyNonFinishDetail.bscid" sortable="true" sortName="BSCID" class = "styleBsc"/>
			</c:otherwise>
		</c:choose>
		<display:column property="site" titleKey="AlDyNonFinishDetail.site" sortable="true" sortName="SITE" class ="styleBsc"/>
	  	<c:if test="${alarmType == 'DOWN_CELL'}">
			<display:column property="system" titleKey="AlDyNonFinishDetail.cellid" sortable="true" sortName="SYSTEM" class = "styleBsc"/>
	   	</c:if>
	   	<c:if test="${alarmType == 'DOWN_1800'}">
			<display:column property="system" titleKey="AlDyNonFinishDetail.cffautid" sortable="true" sortName="SYSTEM" class = "styleBsc"/>
	   	</c:if>
	   	<c:if test="${alarmType == 'DOWN_SITE'}">
			<display:column property="transType" titleKey="AlDyNonFinishDetail.transtype" sortable="true" sortName="TRANS_TYPE" class = "styleTrantype"/>
	   	</c:if>
	   	<c:if test="${alarmType != 'DOWN_1800'}">
			<display:column property="sdate" format="{0,date,dd/MM/yyyy HH:mm}" titleKey="AlDyNonFinishDetail.sdate" sortable="true" sortName="SDATE" class= "styleTime"/>
			<display:column property="edate"  format="{0,date,dd/MM/yyyy HH:mm}" titleKey="AlDyNonFinishDetail.edate" sortable="true" sortName="EDATE"   class= "styleTime"/>
       </c:if>
       	<c:if test="${alarmType == 'DOWN_1800'}">
        	<display:column property="checkDay"  format="{0,date,dd/MM/yyyy HH:mm}" titleKey="AlDyNonFinishDetail.checkDay" sortable="true" sortName="CHECK_DAY"  class= "styleTime"/>
        </c:if>
        <c:if test="${netWork == '3G' && alarmType == 'DOWN_CELL'}">
			<display:column property="cardError" titleKey="AlDyNonFinishDetail.cardError" sortable="true" sortName="CARD_ERROR" style="max-width:60px;"/>
			<display:column property="type" titleKey="AlDyNonFinishDetail.type" sortable="true" sortName="TYPE" style="max-width:60px;"/>
	   	</c:if>
		<display:column property="causebySystem" titleKey="AlDyNonFinishDetail.causebySystem" sortable="true" sortName="CAUSEBY_SYSTEM"  class= "styleCausebySystem"/>
		<display:column property="causebyContent" titleKey="AlDyNonFinishDetail.causebyContent" sortable="true" sortName="CAUSEBY_CONTENT" class= "styleCausebySystem"/>
		<display:column property="resultContent" titleKey="AlDyNonFinishDetail.resultContent" sortable="true" sortName="RESULT_CONTENT" class= "styleCausebySystem"/>
		<display:column property="teamProcess" titleKey="AlDyNonFinishDetail.teamProcess" sortable="true" sortName="TEAM_PROCESS"  class= "styleWid80"/>
		<c:if test="${checkCaTruc==true}">
			<display:column titleKey="title.quanLy" media="html" class="centerColumnMana" style="min-width:60px;">
			   	 <a href="form.htm?id=${item.id}&alarmType=${alarmType}&netWork=${netWork}"><fmt:message key="button.edit"/></a>&nbsp;
	 			<a href="delete_row.htm?id=${item.id}&alarmType=${alarmType}&netWork=${netWork}" onclick="return confirm('<fmt:message key="messsage.confirm.delete"/>')"><fmt:message key="button.delete"/></a>&nbsp;	
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
        button			:   "chooseStopDate",   	// trigger for the calendar (button ID)
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

	$("#dvtTeamProcess").change(function(){
		$.getJSON("${pageContext.request.contextPath}/ajax/getBSCIDByTeamAndType.htm",{team: $(this).val(),netWork:$("#netWork").val()}, function(j){
			
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