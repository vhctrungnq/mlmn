
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
<form:form commandName="filter" method="get" action="list.htm">
	<table>
		<tr>
			<td style="width: 50px;"><fmt:message key="alHardware3G.startDate"/></td>
			<td>
				<input type ="text"  value="${stime}" name="stime" id="stime" size="16" maxlength="16" style="width:100px">
	   			 <img alt="calendar" title="Click to choose the start date" id="chooseStartDate" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
			</td>
			<td style="width: 70px;padding-left: 10px;"><fmt:message key="alHardware3G.endDate"/></td>
			<td>
				<input type ="text"  value="${etime}" name="etime" id="etime" size="16" maxlength="16" style="width:100px">
	   			 <img alt="calendar" title="Click to choose the end date" id="chooseEndDate" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
			</td>
			<td style="padding-left: 10px;"><fmt:message key="alHardware3G.teamProcess"/></td>
			<td>
					<select name="teamProcess" id="teamProcess" style="width: 150px;height:25px; padding-top: 4px;">
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
			<td style="padding-left: 10px;width: 50px;"><fmt:message key="alHardware3G.rnc"/></td>
			<td>
				
				<input type ="text" value="${rnc}" name="rnc" id="rnc" width="80px" size="25">
			</td>
			<td style="width: 50px;"><fmt:message key="alHardware3G.site"/></td>
			<td>
				<input type ="text"  value="${nodeb}" name="nodeb" id="nodeb" size="17"  width="80px">
			</td>
			
		<td style="padding-left: 10px;"><input class="button" type="submit" class="button"
							value="<fmt:message key="button.search"/>" />	</td>
		</tr>
       
	</table>
</form:form>
</div>
<div class="ftsize12" align="right">
 		<c:if test="${checkCaTruc==true}">
		            <a href="form.htm"><fmt:message key="button.add"/></a>
	   	</c:if>	
</div>
  <div>
	<display:table name="${alarmList}" class="simple2" id="item" requestURI="" pagesize="50" sort="external" defaultsort="1" export="true">
	  	<display:column class="centerColumnMana" titleKey="global.list.STT" style="width:30px;"> <c:out value="${item_rowNum}"/> </display:column>
		<display:column property="day"  titleKey="AlDyNonFinishDetail.day" format="{0,date,dd/MM/yyyy}" sortable="true" sortName="DAY"  style="min-width:60px;max-width:60px;"/>		
		<display:column property="rnc" titleKey="alHardware3G.rnc" sortable="true" sortName="RNC"  style="min-width:60px;max-width:60px;"/>
	  	<display:column property="nodeb" titleKey="alHardware3G.site" sortable="true" sortName="NODEB" style="min-width:60px;max-width:60px;"/>
		<display:column property="stime"  titleKey="alHardware3G.startDate" format="{0,date,dd/MM/yyyy HH:mm}" sortable="true" sortName="STIME"  style="min-width:105px;max-width:105px;"/>
		<display:column property="etime"  titleKey="alHardware3G.endDate" format="{0,date,dd/MM/yyyy HH:mm}" sortable="true" sortName="ETIME" style="min-width:105px;max-width:105px;"/>
		<display:column property="cardError" titleKey="alHrHardware3G.cardError" sortable="true" sortName="CARD_ERROR" style="max-width:80px;"/>
		<display:column property="type" titleKey="alHrHardware3G.type" sortable="true" sortName="TYPE" style="max-width:80px;"/>
		<display:column property="checkDay"  format="{0,date,dd/MM/yyyy HH:mm}" titleKey="AlDyNonFinishDetail.checkDay" sortable="true" sortName="CHECK_DAY"  style="min-width:105px;max-width:105px;"/>
        <display:column property="resultContent" titleKey="alHrHardware3G.resultContent" sortable="true" sortName="RESULT_CONTENT"  style="max-width:150px;"/>
		<display:column property="teamProcess"  titleKey="alHardware3G.teamProcess" sortable="true" sortName="TEAM_PROCESS"  style="max-width:60px;"/>
		<display:column property="userProcess"  titleKey="alHardware3G.userProcess" sortable="true" sortName="USER_PROCESS"  style="max-width:100px;"/>
		<c:if test="${checkCaTruc==true}">
			<display:column titleKey="title.quanLy" media="html" class="centerColumnMana"  style="min-width:60px;">
			<a href="form.htm?id=${item.id}"><fmt:message key="button.edit"/></a>&nbsp;
	 			<a href="delete.htm?id=${item.id}"
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
        inputField		:	"stime",	// id of the input field   
        ifFormat		:	"%d/%m/%Y %H:%M",   	// format of the input field
        button			:   "chooseStartDate",  	// trigger for the calendar (button ID)
        showsTime		:	true,
        singleClick		:   true					// double-click mode
    });
    Calendar.setup({
        
        inputField		:	"etime",
        ifFormat		:	"%d/%m/%Y %H:%M",   	// format of the input field
        button			:   "chooseEndDate",  	// trigger for the calendar (button ID)
        showsTime		:	true,
        singleClick		:   true					// double-click mode
    });

</script>
<script type="text/javascript">
	function focusIt()
	{
	  var mytext = document.getElementById("stime");
	  mytext.focus();
	}
	onload = focusIt;
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

	$("#teamProcess").change(function(){
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
 
  