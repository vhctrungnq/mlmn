
<%@ include file="/commons/taglibs.jsp"   %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<style>   
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
	
	#doublescroll { overflow: auto; overflow-y: hidden; }    
	#doublescroll p { margin: 0; padding: 1em; white-space: nowrap; }
</style>
<title>${title}</title>
<content tag="heading">${title}</content> 	
<div>
	<table border="0" width="100%" cellspacing="0" cellpadding="0" class="form">
			<tr> 
				<td align="left"><form:form commandName="filter" method="get" action="TRX_List.htm">
						<input type="hidden" name="alarmType" id="alarmType" value="${alarmType}">
						<table border="0" cellspacing="1" cellpadding="0">
							<tr>
					        	<td style="width:60px;"><fmt:message key="alDyMblAblLog.tuNgay"/></td>
					        	<td>
					        		<input type="text" id="startDate" name="startDate" value="${startDate}"  size="8" maxlength="10"/>
		          					<img alt="calendar" title="Click to choose the start date" id="chooseStartDate" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
					        	</td>
					        	<td  style="width:70px; padding-left:5px;"><fmt:message key="alDyMblAblLog.denNgay"/></td>
					        	<td>
					        		<input type="text" id="endDate" name="endDate" value="${endDate}" size="8" maxlength="10"/>
		          					<img alt="calendar" title="Click to choose the start date" id="chooseEndDate" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
					        	</td>
					        	<td style="width:70px; padding-left:5px;"><fmt:message key="alDyMblAblLog.donViXuLy"/></td>
								<td>
				           			<select id="dvtTeamProcess" name="dvtTeamProcess" style="width: 100px;">
				           				<option value="">--Tất cả--</option>
				           				<c:forEach var="items" items="${dvXuLyList}">
							              <c:choose>
							                <c:when test="${items.team == dvtTeamProcess}">
							                    <option value="${items.team}" selected="selected">${items.team}</option>
							                </c:when>
							                <c:otherwise>
							                    <option value="${items.team}">${items.team}</option>
							                </c:otherwise>
							              </c:choose>
									    </c:forEach>
				           			</select>
	           					</td>
	           					<td style="width:50px;padding-left:5px;"><fmt:message key="alDyMblAblLog.bsc"/></td>
								<td>		         
								    <input type ="text" value="${bscid}" name="bscid" id="bscid" size="20">
	           					</td>
	           					<td style="width:50px;padding-left:5px;"><fmt:message key="alDyMblAblLog.cell"/></td>
								<td><input type ="text" name="cellid" id="cellid" value="${cellid}" size="20" /></td>
								<td style="width:50px;padding-left:5px;"><fmt:message key="alDyMblAblLog.trx"/></td>
								<td><input type ="text" name="trx" id="trx" value="${trx}" size="20"/></td>
								<td style="padding-left:5px;"><input class="button" type="submit" class="button"
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

<div id="doublescroll" class="tableStandar">
	<display:table name="${mblAblList}"   id="item" requestURI="" pagesize="50" sort="external" defaultsort="1" export="true">
	  	<display:column class="centerColumnMana" titleKey="global.list.STT"> <c:out value="${item_rowNum}"/> </display:column>
	  	<display:column property="day" format="{0,date,dd/MM/yyyy}" titleKey="alDyMblAblLog.ngayKiemTra" sortable="true" sortName="DAY" style="min-width:80px;max-width:80px;"/>
	  	<display:column property="bscid" titleKey="alDyMblAblLog.bsc" sortable="true" sortName="BSCID" style="min-width:60px;max-width:60px;"/>
		<display:column property="cellid" titleKey="alDyMblAblLog.cell" sortable="true" sortName="CELLID" style="min-width:60px;max-width:60px;"/>	
		<display:column property="mo" titleKey="alDyMblAblLog.trx" sortable="true" sortName="MO" style="min-width:100px;max-width:100px;"/>
		<display:column property="vendor" titleKey="alDyMblAblLog.vendor" sortable="true" sortName="VENDOR"  style="min-width:100px;max-width:100px;" />
		<display:column property="causeby" titleKey="alDyMblAblLog.nguyenNhan" sortable="true" sortName="CAUSEBY" style="min-width:200px; "/>
		<display:column property="causebyDvt" titleKey="alDyMblAblLog.nguyenNhanPhanHoi" sortable="true" sortName="CAUSEBY_DVT" style="min-width:200px; "/>
		<display:column property="actionProcess" titleKey="alDyMblAblLog.ketQuaXuLy" sortable="true" sortName="ACTION_PROCESS" style="min-width:200px; " />
		<display:column property="dvtTeamProcess" titleKey="alDyMblAblLog.donViXuLy" sortable="true" sortName="DVT_TEAM_PROCESS"  style="min-width:60px;max-width:60px;"/>
		<c:if test="${checkCaTruc==true}">
			<display:column titleKey="title.quanLy" media="html" class="centerColumnMana" style="min-width:60px;max-width:60px;">
			<a href="trx_form.htm?id=${item.id}&alarmType=${alarmType}"><fmt:message key="button.edit"/></a>&nbsp;
	 			<a href="trx_delete.htm?id=${item.id}&alarmType=${alarmType}"
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
        inputField		:	"startDate",	// id of the input field
        ifFormat		:	"%d/%m/%Y",   	// format of the input field
        button			:   "chooseStartDate",  	// trigger for the calendar (button ID)
        showsTime		:	true,
        singleClick		:   false					// double-click mode
    });
    Calendar.setup({
        inputField		:	"endDate",	// id of the input field
        ifFormat		:	"%d/%m/%Y",   	// format of the input field
        button			:   "chooseEndDate",  	// trigger for the calendar (button ID)
        showsTime		:	true,
        singleClick		:   false					// double-click mode
    });

	
</script>
<script type="text/javascript">
	function focusIt()
	{
	  var mytext = document.getElementById("startDate");
	  mytext.focus();
	}
	onload = focusIt;
</script>

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

	$("#dvtTeamProcess").change(function(){
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
	$(function() {
		var availableTags = [];
		var i = 0;
		<c:forEach items="${cellList}" var="listOfNames">
			availableTags[i] = "<c:out value='${listOfNames}' escapeXml='false' />";
			i = i + 1;
		</c:forEach>
	
		loadCell(availableTags);
	});

	function loadCell(availableTags){
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
<script type="text/javascript">
	$(function() {
		var availableTags = [];
		var i = 0;
		<c:forEach items="${trxList}" var="listOfNames">
			availableTags[i] = "<c:out value='${listOfNames}' escapeXml='false' />";
			i = i + 1;
		</c:forEach>
	
		loadTrx(availableTags);
	});

	function loadTrx(availableTags){
		function split( val ) {
			return val.split( /,\s*/ );
			}
			function extractLast( term ) {
			return split( term ).pop();
			}
			$( "#trx" )
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
			var bscTp = $("#trx").val();
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

	    