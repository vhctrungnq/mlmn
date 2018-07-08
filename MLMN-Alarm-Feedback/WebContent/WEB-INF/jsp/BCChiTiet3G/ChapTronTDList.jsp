<%@ include file="/commons/taglibs.jsp"   %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<title>${title}</title>
<style>   
   #rnc {
     visibility: visible !important;
    }
   #transType {
   visibility: visible !important;
  }
   .ui-autocomplete {
		max-height: 200px;
		overflow-y: auto;
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
<form:form commandName="filter" method="get" action="chapchon.htm">
	<table>
		<tr>
			<td style="width: 120px;"><fmt:message key="alDyRpErrorDip.Sday"/></td>
			<td>
				<input type ="text"  value="${Sday}" name="Sday" id="Sday" size="17" maxlength="16" style="width:100px">
	   			 <img alt="calendar" title="Click to choose the start date" id="chooseStartDate" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
			</td>
			<td style="padding-left: 5px;width: 100px;"><fmt:message key="alDyRpErrorDip.Eday"/></td>
			<td>
				<input type ="text"  value="${Eday}" name="Eday" id="Eday" size="17" maxlength="16" style="width:100px">
	   			 <img alt="calendar" title="Click to choose the start date" id="chooseEndDate" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
			</td>
			<td style="padding-left: 5px;width:80px;"><fmt:message key="alDyRpDip3G.dvtTeamProcess"/></td>
			<td>
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
			<td style="padding-left: 5px;width: 50px;"><fmt:message key="alDyRpDip3G.rnc"/></td>
			<td>
				
				<input type ="text" value="${rnc}" name="rnc" id="rnc" width="80px" size="20">
			</td>
			
			
			
		</tr>
		<tr>
			<td><fmt:message key="alDyRpDip3G.transType"/></td>
			<td>
				<input type ="text"   style="width:120px;" id = "transType" name="transType"/>	
			</td>
			
			<td style="padding-left: 5px;"><fmt:message key="vAlRbLossConfig3G.dvtUserProcess"/></td>
			<td>
				<input type ="text"  value="${dvtUserProcess}" name="dvtUserProcess" id="dvtUserProcess" size="17" maxlength="19" width="80px">
			</td>
			<td style="padding-left: 5px;"><fmt:message key="alDyRpDip.itemsL"/></td>
			<td>
				<input type ="text"  value="${items}" name="items" id="items" size="7" maxlength="10" width="80px">
				&nbsp;-&nbsp;
				<input type ="text"  value="${itemsE}" name="itemsE" id="itemsE" size="7" maxlength="10" width="80px">
			
			</td>
			<td style="padding-left: 5px;"><fmt:message key="alDyRpDip3G.nodeb"/></td>
			<td>
				<input type ="text"  value="${nodeb}" name="nodeb" id="nodeb" size="20" width="80px">
			</td>
			<td style="padding-left: 5px;"><input class="button" type="submit" class="button" 
								value="<fmt:message key="button.search"/>" /></td>
			</tr>
	</table>
</form:form>
</div>

  <div id="doublescroll" >
	<display:table name="${alarmList}"  class="simple2" id="item" requestURI="" pagesize="50" sort="external" defaultsort="1" export="true">
	  	<display:column class="centerColumnMana" titleKey="global.list.STT" style="width:30px;"> <c:out value="${item_rowNum}"/> </display:column>
		<display:column property="day" format="{0,date,dd/MM/yyyy}" titleKey="alDyRpDip3G.day" sortable="true" sortName="DAY" style="min-width:60px;max-width:60px;"/>
		<display:column property="rnc" titleKey="alDyRpDip3G.rnc" sortable="true" sortName="RNC" style="min-width:60px;max-width:60px;"/>
	  	<display:column property="nodeb" titleKey="alDyRpDip3G.nodeb" sortable="true" sortName="NODEB" style="min-width:60px;max-width:60px;"/>
	  	<display:column property="items" titleKey="alDyRpDip3G.items" sortable="true" sortName="ITEMS"  style="min-width:30px;max-width:30px;"/>
		<display:column property="transType" titleKey="alDyRpDip3G.transType" sortable="true" sortName="TRANS_TYPE"  style="min-width:100px;max-width:100px;"/>
		<display:column property="causebyContent" titleKey="alDyRpDip3G.causebyContent" sortable="true" sortName="CAUSEBY_CONTENT"  style="min-width:150px;"/>
		<display:column property="resultContent"  titleKey="alDyRpDip3G.resultContent" sortable="true" sortName="RESULT_CONTENT" style="min-width:150px;"/>
		<display:column property="dvtTeamProcess" titleKey="alDyRpDip3G.dvtTeamProcess" sortable="true" sortName="DVT_TEAM_PROCESS"  style="min-width:100px;max-width:100px;"/>
		<display:column property="dvtUserProcess" titleKey="alDyRpDip3G.dvtUserProcess" sortable="true" sortName="DVT_USER_PROCESS" style="min-width:100px;max-width:100px;" />
		<c:if test="${checkCaTruc==true}">
			<display:column titleKey="title.quanLy" media="html" class="centerColumnMana" style="min-width:60px;max-width:60px;">
			<a href="chapchon_form.htm?id=${item.id}"><fmt:message key="button.edit"/></a>&nbsp;
	 			<a href="chapchon_delete.htm?id=${item.id}"
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
		$( "#rnc" )
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
		var bscTp = $("#rnc").val();
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

	
		
</script>
  
