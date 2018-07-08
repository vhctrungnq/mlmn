<%@ include file="/includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<style type="text/css">    
	#doublescroll { overflow: auto; overflow-y: hidden; }    
	#doublescroll p { margin: 0; padding: 1em; white-space: nowrap; }
</style>

<title>${alarmTypeAlias}</title>
<content tag="heading">${alarmTypeAlias}</content>

<div>
	<table border="0" width="100%" cellspacing="0" cellpadding="0" class="form">
			<tr> 
				<td align="left"><form:form commandName="filter" method="post" action="list.htm?alarmType=${alarmType}&network=${network}">
						<table border="0" cellspacing="1" cellpadding="0">
							<tr>
							<td class="mwid60">
								<fmt:message key="baoCaoTongHop.tuNgay"/>
							</td>
							<td class="wid15">
								<input type="text" id="startDate" name="startDate" value="${startDate}" class="wid70" maxlength="20"/>
          						<img alt="calendar" title="Click to choose the start date" id="chooseStartDate" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
							</td>
							<td class="mwid40">
								<fmt:message key="baoCaoTongHop.denNgay"/>
							</td>
							<td class="wid15">
								<input type="text" id="endDate" name="endDate" value="${endDate}" class="wid70" maxlength="20"/>
          						<img alt="calendar" title="Click to choose the end date" id="chooseEndDate" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
							</td>
							<td class="mwid70"><fmt:message key="baoCaoTongHop.dvXuLy"/></td>
							<td class="wid15">
			           			<select id="teamProcess" name="teamProcess" class="wid90">
			           				<option value="">--Tất cả--</option>
			           				<c:forEach var="items" items="${teamList}">
						              <c:choose>
						                <c:when test="${items.team == teamProcess}">
						                    <option value="${items.team}" selected="selected">${items.abbreviated}</option>
						                </c:when>
						                <c:otherwise>
						                    <option value="${items.team}">${items.abbreviated}</option>
						                </c:otherwise>
						              </c:choose>
								    </c:forEach>
			           			</select>
           					</td>
           					<td class="mwid40"><fmt:message key="baoCaoTongHop.bsc"/></td>
							<td class="wid15">		         
							    <input type ="text" value="${bscid}" name="bscid" id="bscid" class="wid90">
           					</td>
           					<td class="mwid40"><fmt:message key="baoCaoTongHop.site"/></td>
							<td class="wid15">		         
							    <input type ="text" value="${site}" name="site" id="site" class="wid90">
           					</td>
							<td class="wid20"><input class="button" type="submit" class="button" name="filter"
								value="<fmt:message key="global.form.timkiem"/>" /></td>
						</tr>					
						</table>
					</form:form>
					</td>
					
					<td>
					</td> 
			</tr>	
	</table>
</div>
<div id="doublescroll">
	 <display:table name="${bcTongHop2GList}" class="simple2" id="item" requestURI="" pagesize="50" sort="external" defaultsort="1" export="true">
	  	<display:column class="centerColumnMana" titleKey="global.list.STT"> <c:out value="${item_rowNum}"/> </display:column>
	  	<display:column property="bscid" titleKey="baoCaoTongHop.bsc" sortable="true" sortName="BSCID" style="min-width:60px;max-width:60px;"/>
		<display:column property="site" titleKey="baoCaoTongHop.site" sortable="true" sortName="SITE" style="min-width:80px;max-width:80px;"/>
		<c:choose>
			<c:when test="${alarmType == 'DOWN_SITE'}">
				<display:column property="transType" titleKey="baoCaoTongHop.transType" sortable="true" sortName="TRANS_TYPE" />
				<display:column property="sdate" format="{0,date,dd/MM/yyyy HH:mm}" titleKey="baoCaoTongHop.sdate" sortable="true" sortName="SDATE" style="min-width:105px;max-width:105px;"/>
				<display:column property="edate" format="{0,date,dd/MM/yyyy HH:mm}" titleKey="baoCaoTongHop.edate" sortable="true" sortName="EDATE" style="min-width:105px;max-width:105px;"/>
				<display:column property="causebySystem" titleKey="baoCaoTongHop.causebySystem" sortable="true" sortName="CAUSEBY_SYSTEM" style="min-width:105px;max-width:105px;"/>
				<display:column property="causebyContent" titleKey="baoCaoTongHop.causebyContent" sortable="true" sortName="CAUSEBY_CONTENT" />
			</c:when>
			<c:when test="${alarmType == 'DOWN_CELL'}">
				<display:column property="system" titleKey="baoCaoTongHop.cell" sortable="true" sortName="SYSTEM" />
				<display:column property="sdate" format="{0,date,dd/MM/yyyy HH:mm}" titleKey="baoCaoTongHop.sdate" sortable="true" sortName="SDATE" style="min-width:105px;max-width:105px;"/>
				<display:column property="edate" format="{0,date,dd/MM/yyyy HH:mm}" titleKey="baoCaoTongHop.edate" sortable="true" sortName="EDATE" style="min-width:105px;max-width:105px;"/>
				<display:column property="causebySystem" titleKey="baoCaoTongHop.causebySystem" sortable="true" sortName="CAUSEBY_SYSTEM" style="min-width:105px;max-width:105px;"/>
				<display:column property="causebyContent" titleKey="baoCaoTongHop.nguyenNhanPhanHoi" sortable="true" sortName="CAUSEBY_CONTENT" />
			</c:when>
			<c:when test="${alarmType == 'DOWN_1800'}">
				<display:column property="system" titleKey="baoCaoTongHop.cfFault" sortable="true" sortName="SYSTEM" />
				<display:column property="day" format="{0,date,dd/MM/yyyy HH:mm}" titleKey="baoCaoTongHop.ngayDDHKiemTraVaXuLyMem" sortable="true" sortName="DAY" style="min-width:105px;max-width:105px;"/>
				<display:column property="causebySystem" titleKey="baoCaoTongHop.nguyenNhanDDHKiemTra" sortable="true" sortName="CAUSEBY_SYSTEM" style="min-width:105px;max-width:105px;"/>
				<display:column property="causebyContent" titleKey="baoCaoTongHop.nguyenNhanPhanHoi" sortable="true" sortName="CAUSEBY_CONTENT" />
			</c:when>
			<c:otherwise></c:otherwise>
		</c:choose>
		<display:column property="resultContent" titleKey="baoCaoTongHop.resultContent" sortable="true" sortName="RESULT_CONTENT" />
		<display:column property="abbreviated" titleKey="baoCaoTongHop.dvXuLy" sortable="true" sortName="ABBREVIATED" />
		
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
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/calendar-blue.css" />

<link type="text/css" rel="Stylesheet" href="${pageContext.request.contextPath}/js/jquery/jquery-ui-1.8.23.custom.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/js/selectStyle/assets/style.css" />

<script type="text/javascript">
	$(function() {
		var availableTags = [];
		var i = 0;
		<c:forEach items="${bscidList}" var="listOfNames">
			availableTags[i] = "<c:out value='${listOfNames.bscid}' escapeXml='false' />";
			i = i + 1;
		</c:forEach>
	
		loadBsc(availableTags);
	});

	$("#teamProcess").change(function(){
		$.getJSON("${pageContext.request.contextPath}/TRX2G/getBscid.htm",{dvtTeamProcess: $(this).val()}, function(j){
			
			var availableTags = [];
			for (var i = 0; i < j.length; i++) {
				availableTags[i] = j[i].bscid;
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
		// add the selected item
		terms.push( ui.item.value );
		// add placeholder to get the comma-and-space at the end
		terms.push( "" );
		this.value = terms.join( "," );
		return false;
		}
		});
	}
		
</script>

<script type="text/javascript">
Calendar.setup({
    inputField		:	"startDate",	// id of the input field
    ifFormat		:	"%d/%m/%Y",   	// format of the input field
    button			:   "chooseStartDate",  	// trigger for the calendar (button ID)
    singleClick		:   false					// double-click mode
});

Calendar.setup({
    inputField		:	"endDate",	// id of the input field
    ifFormat		:	"%d/%m/%Y",   	// format of the input field
    button			:   "chooseEndDate",  	// trigger for the calendar (button ID)
    singleClick		:   false					// double-click mode
});

function focusIt()
{
  var mytext = document.getElementById("startDate");
  mytext.focus();
}

onload = focusIt;
</script>