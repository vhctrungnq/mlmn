
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
<form:form commandName="filter" method="post" action="lostFlutter3Day.htm">
	<table>
		<tr>
			<td style="width: 50px;"><fmt:message key="alDyRpDip.day"/></td>
			<td>
				<input type ="text"  value="${day}" name="day" id="day" size="17" maxlength="10" style="width:100px">
	   			 <img alt="calendar" title="Click to choose the start date" id="chooseStartDate" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
			</td>
			<td style="padding-left: 5px;width: 60px;"><fmt:message key="alDyRpDip.teamProcess"/></td>
			<td>
					<select name="teamProcess" id="teamProcess" style="width: 150px;height:20px; padding-top: 4px;">
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
			<td style="padding-left: 5px;width: 50px;"><fmt:message key="alDyRpDip.bscid"/></td>
			<td>
				<input type ="text"  value="${bscid}" name="bscid" id="bscid" size="25" width="80px">
			</td>
			<td style="padding-left: 5px;width: 50px;"><fmt:message key="alDyRpFlutter.site"/></td>
			<td>
				<input type ="text"  value="${site}" name="site" id="site" size="20" width="80px">
			</td>
			
			<td style="padding-left: 5px;"><input class="button" type="submit" class="button"
							value="<fmt:message key="button.search"/>" /></td>
		</tr>
	</table>
</form:form>
</div>
  <div id="doublescroll">
	<display:table name="${alarmList}" class="simple2" id="item" requestURI="" pagesize="50" sort="external" defaultsort="1" export="true">
	  	<display:column class="centerColumnMana" titleKey="global.list.STT" style="width:30px;"> <c:out value="${item_rowNum}"/> </display:column>
		<display:column property="bscid" titleKey="alDyRpDip.bscid" sortable="true" sortName="BSCID" style="min-width:60px;"/>
	  	<display:column property="site" titleKey="alDyRpFlutter.site" sortable="true" sortName="SITE" style="min-width:60px;"/> 
		<%-- <display:column property="transType"  titleKey="alDyRpFlutter.transType" sortable="true" sortName="TRANS_TYPE" style="min-width:60px;"/> --%>
		<display:column property="items2" titleKey="alDyRpDip.items2" sortable="true" sortName="ITEMS_2" style="min-width:40px;"/>
		<display:column property="totalTime2" titleKey="alDyRpDip.totalTime2" sortable="true" sortName="TOTAL_TIME_2" style="min-width:60px;"/>
	
		<display:column property="items1" titleKey="alDyRpDip.items1" sortable="true" sortName="ITEMS_1" style="min-width:40px;"/>
		<display:column property="totalTime1" titleKey="alDyRpDip.totalTime1" sortable="true" sortName="TOTAL_TIME_1" style="min-width:60px;"/>
		
		<display:column property="items" titleKey="alDyRpDip.items" sortable="true" sortName="ITEMS" style="min-width:40px;"/>
		<display:column property="totalTime" titleKey="alDyRpDip.totalTime" sortable="true" sortName="TOTAL_TIME" style="min-width:60px;"/>
		
		<display:column property="teamProcess" titleKey="alDyRpDip.teamProcess" sortable="true" sortName="TEAM_PROCESS" />

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
        inputField		:	"day",	// id of the input field
        ifFormat		:	"%d/%m/%Y",   	// format of the input field
        button			:   "chooseStartDate",  	// trigger for the calendar (button ID)
        showsTime		:	true,
        singleClick		:   false					// double-click mode
    });

</script>
<script type="text/javascript">
	function focusIt()
	{
	  var mytext = document.getElementById("day");
	  mytext.focus();
	}
	$(document).ready(function(){
		var trs='<tr><th rowspan="2"><fmt:message key="global.list.STT"/></th>';
	    trs=trs + '<th rowspan="2"><fmt:message key="alDyRpDip.bscid"/></th>';
	    trs=trs + '<th rowspan="2"><fmt:message key="alDyRpFlutter.site"/></th>';
	    /* trs=trs +'<th rowspan="2"><fmt:message key="alDyRpFlutter.transType"/></th>'; */
	    trs=trs +'<th colspan="2">${day2}</th>';
	    trs=trs +'<th colspan="2">${day1}</th>';
	    trs=trs +'<th colspan="2">${day}</th>';
	    trs=trs +'<th rowspan="2"><fmt:message key="alDyRpDip.teamProcess"/></th></tr>';
	   	trs=trs +'<tr><th ><fmt:message key="alDyRpDip.items2"/></th>';
	    trs=trs +'<th ><fmt:message key="alDyRpDip.totalTime2"/></th>';
	    trs=trs +'<th ><fmt:message key="alDyRpDip.items1"/></th>';
	    trs=trs +'<th ><fmt:message key="alDyRpDip.totalTime1"/></th>';
	    trs=trs +'<th ><fmt:message key="alDyRpDip.items"/></th>';
	    trs=trs +'<th ><fmt:message key="alDyRpDip.totalTime"/></th></tr>';
	    
		$('#item thead').html(trs);
	
	});

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
 <%-- <script type="text/javascript">
    function DoubleScroll(element) {
        var scrollbar= document.createElement('div');
        scrollbar.appendChild(document.createElement('div'));
        scrollbar.style.overflow= 'auto';
        scrollbar.style.overflowY= 'hidden';
        scrollbar.firstChild.style.width= element.scrollWidth+'px';
        scrollbar.firstChild.style.paddingTop= '1px';
        scrollbar.firstChild.appendChild(document.createTextNode('\xA0'));
        scrollbar.onscroll= function() {
            element.scrollLeft= scrollbar.scrollLeft;
        };
        element.onscroll= function() {
            scrollbar.scrollLeft= element.scrollLeft;
        };
        element.parentNode.insertBefore(scrollbar, element);
    }

    DoubleScroll(document.getElementById('doublescroll'));
</script>
  --%>
  