
<%@ include file="/commons/taglibs.jsp"   %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
 <style>
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
<form:form commandName="filter" method="post" action="mat_han.htm">
	<table>
		<tr>
			<td style="width: 100px;"><fmt:message key="alDyRpErrorDip.Sday"/></td>
			<td>
				<input type ="text"  value="${Sday}" name="Sday" id="Sday" size="17" maxlength="16" style="width:100px">
	   			 <img alt="calendar" title="Click to choose the start date" id="chooseStartDate" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
			</td>
			<td style="padding-left: 5px;width: 70px;"><fmt:message key="alDyRpErrorDip.Eday"/></td>
			<td>
				<input type ="text"  value="${Eday}" name="Eday" id="Eday" size="17" maxlength="16" style="width:100px">
	   			 <img alt="calendar" title="Click to choose the start date" id="chooseEndDate" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
			</td>
			<td style="padding-left: 5px;width: 100px;"><fmt:message key="alDyRpErrorDip.teamProcess"/></td>
			<td>
					<select name="teamProcess" id="teamProcess" style="width: 120px;height:20px; padding-top: 4px;">
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
			<td style="padding-left: 5px;width: 70px;"><fmt:message key="alDyRpErrorDip.bscid"/></td>
			<td>
				<input type ="text"  value="${bscid}" name="bscid" id="bscid" size="25" width="80px">
			</td>
			
		</tr>
		<tr>
			<td><fmt:message key="alDyRpDip3G.transType"/></td>
			<td>
				<input type ="text"   value="${transType}" style="width:120px;" id = "transType" name="transType"/>	
			</td>
			<td style="padding-left: 5px;"><fmt:message key="alDyRpErrorDip.faultType"/></td>
			<td>
					<input type ="text"   value="${faultType}" style="width:120px;" id = "faultType" name="faultType"/>
			</td>
			<td style="padding-left: 5px;"><fmt:message key="vAlRbLossConfig3G.dvtUserProcess"/></td>
			<td>
				<input type ="text"  value="${dvtUserProcess}" name="dvtUserProcess" id="dvtUserProcess" size="17" maxlength="19" width="80px">
			</td>
			
			<td style="padding-left: 5px;width: 30px;"><fmt:message key="alDyRpErrorDip.dip"/></td>
			<td>
				<input type ="text"  value="${dip}" name="dip" id="dip" size="25" width="80px">
			</td>
			<td style="padding-left: 5px;"><input class="button" type="submit" class="button" 
								value="<fmt:message key="button.search"/>" /></td>
			</tr>
	</table>
</form:form>
</div>
  <div  id="doublescroll" >
	<display:table name="${alarmList}" class="simple2" id="item" requestURI="" pagesize="50" sort="external" defaultsort="1" export="true">
	  	<display:column class="centerColumnMana" titleKey="global.list.STT" style="width:30px;"> <c:out value="${item_rowNum}"/> </display:column>
		<display:column property="day"  titleKey="alDyRpErrorDip.day" format="{0,date,dd/MM/yyyy}" sortable="true" sortName="DAY"  style="min-width:70px;max-width:70px;"/>
		<display:column property="bscid" titleKey="alDyRpErrorDip.bscid" sortable="true" sortName="BSCID"  style="min-width:60px;"/>
	  	<display:column property="dip" titleKey="alDyRpErrorDip.dip" sortable="true" sortName="DIP" style="min-width:60px;"/>
		<display:column property="sdateStr"  titleKey="alDyRpErrorDip.sdate" format="{0,date,dd/MM/yyyy HH:MM}" sortable="true" sortName="SDATE" style="min-width:120px;max-width:120px;"/>
		<display:column property="edateStr"  titleKey="alDyRpErrorDip.edate" format="{0,date,dd/MM/yyyy HH:MM}" sortable="true" sortName="EDATE" style="min-width:120px;max-width:120px;"/>
		<display:column property="faultType"  titleKey="alDyRpErrorDip.faultType"  sortable="true" sortName="Fault_Type" style="min-width:60px;" />
		<display:column property="transType"  titleKey="alDyRpErrorDip.transType" sortable="true" sortName="TRANS_TYPE"  style="min-width:60px;"/>
		<display:column property="causebyContent"  titleKey="alDyRpErrorDip.causebyContent" sortable="true" sortName="CAUSEBY_CONTENT" style="min-width:120px;"/>
		<display:column property="resultContent"  titleKey="alDyRpErrorDip.resultContent" sortable="true" sortName="RESULT_CONTENT" style="min-width:120px;"/>
		<display:column property="teamProcess" titleKey="alDyRpErrorDip.teamProcess" sortable="true" sortName="TEAM_PROCESS"  style="min-width:70px;"/>
		<display:column property="userProcess"  titleKey="alDyRpErrorDip.userProcess" sortable="true" sortName="USER_PROCESS"  style="min-width:100px;"/>
		<c:if test="${checkCaTruc==true}">
			<display:column titleKey="title.quanLy" media="html" class="centerColumnMana" style="min-width:70px;">
			<a href="formMH.htm?id=${item.id}"><fmt:message key="button.edit"/></a>&nbsp;
	 			<a href="deleteMH.htm?id=${item.id}"
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
        inputField		:	"Sday",	// id of the input field
        ifFormat		:	"%d/%m/%Y",   	// format of the input field
        button			:   "chooseStartDate",  	// trigger for the calendar (button ID)
        showsTime		:	true,
        singleClick		:   false					// double-click mode
    });
    Calendar.setup({
        inputField		:	"Eday",	// id of the input field
        ifFormat		:	"%d/%m/%Y",   	// format of the input field
        button			:   "chooseEndDate",  	// trigger for the calendar (button ID)
        showsTime		:	true,
        singleClick		:   false					// double-click mode
    });
	
</script>
<script type="text/javascript">
	function focusIt()
	{
	  var mytext = document.getElementById("Sday");
	  mytext.focus();
	}
	onload = focusIt;

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
	$(function() {
		var availableTags = [];
		var i = 0;
		<c:forEach items="${faultTypeList}" var="listOfNames">
			availableTags[i] = "<c:out value='${listOfNames}' escapeXml='false' />";
			i = i + 1;
		</c:forEach>
		loadfaultType(availableTags);

		var availableTags2 = [];
		var i = 0;
		<c:forEach items="${transTypeList}" var="listOfNames1">
			availableTags2[i] = "<c:out value='${listOfNames1}' escapeXml='false' />";
			i = i + 1;
		</c:forEach>
		loadtransType(availableTags2);
	});
	function loadtransType(availableTags){
		function split( val ) {
		return val.split( /,\s*/ );
		}
		function extractLast( term ) {
		return split( term ).pop();
		}
		$("#transType")
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
	//	terms.pop();
		//check and add the selected item
		
		//	terms.push( ui.item.value );
			// add placeholder to get the comma-and-space at the end
		//	terms.push( "" );
		//	this.value = terms;
		this.value = ui.item.value;
		return false;
		}
		});
	}	

	function loadfaultType(availableTags){
		function split( val ) {
		return val.split( /,\s*/ );
		}
		function extractLast( term ) {
		return split( term ).pop();
		}
		$("#faultType")
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
		//terms.pop();
		//check and add the selected item
		
			//terms.push( ui.item.value );
			// add placeholder to get the comma-and-space at the end
		//	terms.push( "" );
		//	this.value = terms;
		this.value = ui.item.value;
		return false;
		}
		});
	}	
</script>
  