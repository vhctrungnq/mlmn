<%@ include file="/includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<title><fmt:message key="sidebar.admin.isoPortCard"/></title>
<content tag="heading"><fmt:message key="sidebar.admin.isoPortCard"/></content>

<ul class="ui-tabs-nav">
<c:choose>
<c:when test="${neType == 'BSC_BTS'}">
		<li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/iso/thong-tin-port-card/list.htm?neType=BSC_BTS"><span><fmt:message key="title.tabControls.bscBts"/></span></a></li>
		<li class=""><a href="${pageContext.request.contextPath}/iso/thong-tin-port-card/list.htm?neType=RNC_NODEB"><span><fmt:message key="title.tabControls.rncNodeb"/></span></a></li>
		<%-- <li class=""><a href="${pageContext.request.contextPath}/iso/thong-tin-port-card/list.htm?neType=MGW"><span><fmt:message key="title.tabControls.mgw"/></span></a></li> --%>
		<li class=""><a href="${pageContext.request.contextPath}/iso/thong-tin-port-card/list.htm?neType=MFS"><span><fmt:message key="title.tabControls.bscMfs"/></span></a></li>
		<li class=""><a href="${pageContext.request.contextPath}/iso/thong-tin-port-card/list.htm?neType=SGSN"><span><fmt:message key="title.tabControls.sgsn"/></span></a></li>
	</c:when>
 	<c:when test="${neType == 'RNC_NODEB'}">
		<li class=""><a href="${pageContext.request.contextPath}/iso/thong-tin-port-card/list.htm?neType=BSC_BTS"><span><fmt:message key="title.tabControls.bscBts"/></span></a></li>
		<li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/iso/thong-tin-port-card/list.htm?neType=RNC_NODEB"><span><fmt:message key="title.tabControls.rncNodeb"/></span></a></li>
		<%-- <li class=""><a href="${pageContext.request.contextPath}/iso/thong-tin-port-card/list.htm?neType=MGW"><span><fmt:message key="title.tabControls.mgw"/></span></a></li> --%>
		<li class=""><a href="${pageContext.request.contextPath}/iso/thong-tin-port-card/list.htm?neType=MFS"><span><fmt:message key="title.tabControls.bscMfs"/></span></a></li>
		<li class=""><a href="${pageContext.request.contextPath}/iso/thong-tin-port-card/list.htm?neType=SGSN"><span><fmt:message key="title.tabControls.sgsn"/></span></a></li>
	</c:when>
	<c:when test="${neType == 'MGW'}">
		<li class=""><a href="${pageContext.request.contextPath}/iso/thong-tin-port-card/list.htm?neType=BSC_BTS"><span><fmt:message key="title.tabControls.bscBts"/></span></a></li>
		<li class=""><a href="${pageContext.request.contextPath}/iso/thong-tin-port-card/list.htm?neType=RNC_NODEB"><span><fmt:message key="title.tabControls.rncNodeb"/></span></a></li>
		<li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/iso/thong-tin-port-card/list.htm?neType=MGW"><span><fmt:message key="title.tabControls.mgw"/></span></a></li>
		<li class=""><a href="${pageContext.request.contextPath}/iso/thong-tin-port-card/list.htm?neType=MFS"><span><fmt:message key="title.tabControls.bscMfs"/></span></a></li>
		<li class=""><a href="${pageContext.request.contextPath}/iso/thong-tin-port-card/list.htm?neType=SGSN"><span><fmt:message key="title.tabControls.sgsn"/></span></a></li>
	</c:when>
	<c:when test="${neType == 'MFS'}">
		<li class=""><a href="${pageContext.request.contextPath}/iso/thong-tin-port-card/list.htm?neType=BSC_BTS"><span><fmt:message key="title.tabControls.bscBts"/></span></a></li>
		<li class=""><a href="${pageContext.request.contextPath}/iso/thong-tin-port-card/list.htm?neType=RNC_NODEB"><span><fmt:message key="title.tabControls.rncNodeb"/></span></a></li>
		<%-- <li class=""><a href="${pageContext.request.contextPath}/iso/thong-tin-port-card/list.htm?neType=MGW"><span><fmt:message key="title.tabControls.mgw"/></span></a></li> --%>
		<li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/iso/thong-tin-port-card/list.htm?neType=MFS"><span><fmt:message key="title.tabControls.bscMfs"/></span></a></li>
		<li class=""><a href="${pageContext.request.contextPath}/iso/thong-tin-port-card/list.htm?neType=SGSN"><span><fmt:message key="title.tabControls.sgsn"/></span></a></li>
	</c:when>
	<c:when test="${neType == 'SGSN'}">
		<li class=""><a href="${pageContext.request.contextPath}/iso/thong-tin-port-card/list.htm?neType=BSC_BTS"><span><fmt:message key="title.tabControls.bscBts"/></span></a></li>
		<li class=""><a href="${pageContext.request.contextPath}/iso/thong-tin-port-card/list.htm?neType=RNC_NODEB"><span><fmt:message key="title.tabControls.rncNodeb"/></span></a></li>
		<%-- <li class=""><a href="${pageContext.request.contextPath}/iso/thong-tin-port-card/list.htm?neType=MGW"><span><fmt:message key="title.tabControls.mgw"/></span></a></li> --%>
		<li class=""><a href="${pageContext.request.contextPath}/iso/thong-tin-port-card/list.htm?neType=MFS"><span><fmt:message key="title.tabControls.bscMfs"/></span></a></li>
		<li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/iso/thong-tin-port-card/list.htm?neType=SGSN"><span><fmt:message key="title.tabControls.sgsn"/></span></a></li>
	</c:when>
 	<c:otherwise></c:otherwise>
</c:choose>
</ul>
<br>
<div>
	<input id="strWhere" name="strWhere" value="" type="hidden"/>
	<input id="sortfield" name="sortfield" value="" type="hidden"/>
	<input id="sortorder" name="sortorder" value="" type="hidden"/>
</div>
<form:form id="filterController" method="post" action="${function}.htm?neType=${neType}">
	<table border="0" width="100%" cellspacing="0" cellpadding="0" class="form">
			<%-- <tr> 
				<td align="left" colspan="2">
						<table border="0" cellspacing="1" cellpadding="0" width="100%">
							<tr> 
								<td class="wid7 mwid80"><fmt:message key="isoLicenseSoft.startDate"/></td>
								<td class="wid15">	
							       	<input id="startDate" name="startDate" value="${startDate}" class="wid50" maxlength="20"/>
							 		<img alt="calendar" title="Click to choose the start date" id="chooseStartDate" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
								</td>
								
								<td class="wid7 mwid80"><fmt:message key="isoLicenseSoft.endDate"/></td>
								<td class="wid15">
							       	<input id="endDate" name="endDate" value="${endDate}" class="wid50" maxlength="20"/>
							 		<img alt="calendar" title="Click to choose the end date" id="chooseEndDate" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
								</td>
								<td>
									<input type="submit" value="<fmt:message key="global.form.timkiem"/>" id='jqxSubmitButton' />
								</td>
							</tr>				
						</table>
				</td>
			</tr>--%>
			<tr>
				<td colspan="2">
					<c:choose>
						<c:when test="${neType == 'MFS'}">
							<%@ include file="/WEB-INF/jspiso/jqwidgets/gridSimple1.jsp" %>
						</c:when>
	 					<c:otherwise><%@ include file="/WEB-INF/jspiso/jqwidgets/gridReportFull.jsp" %></c:otherwise>
					</c:choose>
				</td>
			</tr>
	</table>
</form:form> 

<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar_en.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar_setup.js"></script>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/calendar-blue.css" />

 <script type="text/javascript">
$(document).ready(function () {
	var theme = getTheme();
	$("#jqxSubmitButton").jqxButton({theme: theme});
	
});

/* Calendar.setup({
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

onload = focusIt; */

</script> 
<script type="text/javascript">
$('#Menu').on('itemclick', function (event) {
	var args = event.args;
	// export data
	if ($.trim($(args).text()) == '<fmt:message key="global.button.exportExcel"/>') {
		window.open('${pageContext.request.contextPath}/iso/thong-tin-port-card/exportPortCard.htm?startDate='+"<c:out value='${startDate}'/>"+
	        	'&endDate='+"<c:out value='${endDate}'/>"+
	        	 '&neType='+"<c:out value='${neType}'/>"+
	        	 '&strWhere='+$("#strWhere").val()+
	        	 '&sortfield='+$("#sortfield").val()+
	        	 '&sortorder='+$("#sortorder").val()
	        	 ,'_blank');
		}
	
	  });
</script>
