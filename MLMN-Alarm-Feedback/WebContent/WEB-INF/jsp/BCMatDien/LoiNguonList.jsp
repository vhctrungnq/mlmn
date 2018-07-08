
<%@ include file="/commons/taglibs.jsp"   %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<style type="text/css">   
   #teamProcess {
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
 
<title>${title}</title>
<content tag="heading">${title}</content> 	
<div>
<form:form commandName="filter" method="get" action="list.htm">
	<table>
		<tr>
			<td style="width: 50px;"><fmt:message key="vAlDyRpErrorPower.Sday"/></td>
			<td>
				<input type ="text"  value="${Sday}" name="Sday" id="Sday" size="10" maxlength="10" style="width:100px">
	   			 <img alt="calendar" title="Click to choose the start date" id="chooseStartDate" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
			</td>
			<td style="padding-left: 10px;width: 70px;"><fmt:message key="vAlDyRpErrorPower.Eday"/></td>
			<td>
				<input type ="text"  value="${Eday}" name="Eday" id="Eday" size="10" maxlength="10" style="width:100px">
	   			 <img alt="calendar" title="Click to choose the start date" id="chooseEndDate" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
			</td>
			<td style="padding-left: 10px; width: 50px;"><fmt:message key="vAlDyRpErrorPower.teamProcess"/></td>
			<td>
					<select name="teamProcess" id="teamProcess" style="width: 120px;">
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
			<td style="padding-left: 10px;width: 80px;"><fmt:message key="vAlDyRpErrorPower.bscid"/></td>
			<td>
				<input type ="text"  value="${bscid}" name="bscid" id="bscid" size="25" width="80px">
			</td>
			<td style="padding-left: 10px;width: 30px;"><fmt:message key="vAlDyRpErrorPower.site"/></td>
			<td>
				<input type ="text"  value="${site}" name="site" id="site" size="17" width="80px">
			</td>
			
			<td style="padding-left: 10px;"><input class="button" type="submit" class="button" onclick="setAction('filter')"
							value="<fmt:message key="button.search"/>" /></td>
		</tr>
		
	</table>
		<div align="right">
			<input type="hidden" id="action" name="action">
			<input class="button" type="submit" id="startJob" name = "startJob" onclick="setAction('startJob')" value="<fmt:message key="button.startJob"/>" title="Lấy danh sách mất điện tới thời điểm hiện tại"/>
		</div>
</form:form>
</div>

  <div id="doublescroll" class="tableStandar">
	<display:table name="${alarmList}"  id="item" requestURI="" pagesize="50" sort="external" defaultsort="1" export="true">
	  	<display:column class="centerColumnMana" titleKey="global.list.STT"> <c:out value="${item_rowNum}"/> </display:column>
		<display:column property="day"  titleKey="vAlDyRpErrorPower.day" format="{0,date,dd/MM/yyyy}" sortable="true" sortName="DAY" style="min-width:80px;max-width:80px;"/>
		<display:column property="bscid" titleKey="vAlDyRpErrorPower.bscid" sortable="true" sortName="BSCID" style="min-width:60px;max-width:120px;"/>
	  	<display:column property="site" titleKey="vAlDyRpErrorPower.site" sortable="true" sortName="SITE" style="min-width:60px;max-width:120px;"/>
		<display:column property="sdateStr"  titleKey="vAlDyRpErrorPower.sdate" format="{0,date,dd/MM/yyyy HH:MM}" sortable="true" sortName="SDATE" style="min-width:120px;max-width:120px;"/>
		<display:column property="teamProcess" titleKey="vAlDyRpErrorPower.teamProcess" sortable="true" sortName="TEAM_PROCESS" style="min-width:60px;max-width:120px;"/>
		<display:column property="userProcess"  titleKey="vAlDyRpErrorPower.userProcess" sortable="true" sortName="USER_PROCESS" style="min-width:200px;"/>
		<display:column property="causebyContent"  titleKey="vAlDyRpErrorPower.causebyContent" sortable="true" sortName="CAUSEBY_CONTENT" style="min-width:200px;"/>
		<display:column property="resultContent"  titleKey="vAlDyRpErrorPower.resultContent" sortable="true" sortName="RESULT_CONTENT" style="min-width:200px;"/>
		<c:if test="${checkCaTruc==true}">
			<display:column titleKey="title.quanLy" media="html" class="centerColumnMana" style="min-width:60px;">
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

   /*  Calendar.setup({
        inputField		:	"startDate",	// id of the input field
        ifFormat		:	"%d/%m/%Y %H:%M:%S",   	// format of the input field
        button			:   "chooseStartJob",  	// trigger for the calendar (button ID)
        showsTime		:	true,
        singleClick		:   false					// double-click mode
    }); */
</script>
<script type="text/javascript">
	function focusIt()
	{
	  var mytext = document.getElementById("Sday");
	  mytext.focus();
	}
	onload = focusIt;

	 function setAction(value) {
   	  var action = document.getElementById("action");
   	  action.value = value;

   	  return true;
   	 }
	/*  $(function() {
		
		 $( document ).tooltip({
			 position: {
			 my: "center top",
			 at: "center bottom+5",
			 },
			 show: {
			 	duration: "fast"
			 },
		 
		 });
	}); */
	 $(function() {

			//Load BSCID
			var cacheCell = {}, lastXhrCell;
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
			/*response( $.ui.autocomplete.filter(
			availableTags, extractLast( request.term ) ) );*/
				var term = extractLast( request.term );
				if ( term in cacheCell ) {
					response( cacheCell[ term ] );
					return;
				}
				lastXhrCell = $.getJSON( "${pageContext.request.contextPath}/ajax/getBSC23GByTeamSearch.htm", {term: term}, function( data, status, xhr ) {
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
 function hiddenClick(id) {
		$("#" + id).slideToggle();

		if ($("#openCloseIdentifier_" + id).is(":hidden")) {
			$("#openCloseIdentifier_" + id).show();
		}
		else {
			$("#openCloseIdentifier_" + id).hide();
		}
	}
	
 
</script>
 
  