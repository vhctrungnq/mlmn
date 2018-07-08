<%@ include file="/includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<title><fmt:message key="kpismark.title"/></title>
<content tag="heading"><fmt:message key="kpismark.title"/></content>
<ul class="ui-tabs-nav">
	<li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/alarm/kpi-mark-dept/list.htm"><span><fmt:message key="kpismark.title.dept"/></span></a></li>
	<li class=""><a href="${pageContext.request.contextPath}/alarm/kpi-mark-team/list.htm"><span><fmt:message key="kpismark.title.team"/></span></a></li>
	<li class=""><a href="${pageContext.request.contextPath}/alarm/kpi-mark-group/list.htm"><span><fmt:message key="kpismark.title.group"/></span></a></li>
	<li class=""><a href="${pageContext.request.contextPath}/alarm/kpi-mark-group-causeby/list.htm"><span><fmt:message key="kpismark.title.groupcauseby"/></span></a></li>
	<li class=""><a href="${pageContext.request.contextPath}/alarm/kpi-mark-detail/list.htm"><span><fmt:message key="kpismark.title.detail"/></span></a></li>
</ul>
<br>
<table border="0" width="100%" cellspacing="0" cellpadding="0" class="form">
		<tr> 
			<td align="left" colspan="2"><form:form commandName="filter" method="post" action="${function}.htm">
					<table border="0" cellspacing="1" cellpadding="0" width="100%">
						<tr> 
							<td class="wid8 mwid90"><fmt:message key="kpismark.filter.startdate"/></td>
							<td class="wid15">	
							       	<input id="startDate" name="startDate" value="${startDate}" class="wid50" maxlength="20"/>
							 		<img alt="calendar" title="Click to choose the start date" id="chooseStartDate" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
							</td>	
							<td class="wid7 mwid100"><fmt:message key="kpismark.filter.enddate"/></td>
							<td class="wid15">
							       	<input id="endDate" name="endDate" value="${endDate}" class="wid50" maxlength="20"/>
							 		<img alt="calendar" title="Click to choose the end date" id="chooseEndDate" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
							</td> 
							<td>
								<input type="submit" value="<fmt:message key="global.form.timkiem"/>" id='jqxSubmitButton' /></td>
						</tr>				
					</table>
				</form:form>
			</td>
		</tr>
		<tr>
			<td colspan="2"> 
				<div id='jqxWidget' style="margin-top:5px">
			    	<div id="jqxgrid"></div>
			        <div id='Menu'>
			            <ul>
					   		<li><fmt:message key="global.button.exportExcel"/></li>
				        </ul>
			       </div>
			    </div>
			</td>
		</tr>
</table>

<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar_en.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar_setup.js"></script>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/calendar-blue.css" />
<script type="text/javascript">
	var theme = getTheme();
	$("#jqxSubmitButton").jqxButton({theme: theme});
	${gridReport}
	
	$('#Menu').on('itemclick', function (event) {
		var args = event.args;
		// export data
		if ($.trim($(args).text()) == '<fmt:message key="global.button.exportExcel"/>') {
				window.open('${pageContext.request.contextPath}/alarm/kpi-mark-dept/exportData.htm?startDate='+"<c:out value='${startDate}'/>"+
			        	 '&endDate='+"<c:out value='${endDate}'/>" 
			        	 ,'_blank');
				}
	});
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
</script>
 