<%@ include file="/includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<title><fmt:message key="sidebar.admin.isoInventoryHardUtility"/></title>
<content tag="heading"><fmt:message key="sidebar.admin.isoInventoryHardUtility"/></content>

<ul class="ui-tabs-nav">
<c:choose>
<c:when test="${function == 'trx'}">
		<li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/iso/hard-utility/trx.htm"><span><fmt:message key="title.tabControls.hardUtilityTrx"/></span></a></li>
		<li class=""><a href="${pageContext.request.contextPath}/iso/hard-utility/gprs.htm"><span><fmt:message key="title.tabControls.hardUtilityGprs"/></span></a></li>
		<li class=""><a href="${pageContext.request.contextPath}/iso/hard-utility/abis-ater.htm"><span><fmt:message key="title.tabControls.hardUtilityAbisAter"/></span></a></li>
		<li class=""><a href="${pageContext.request.contextPath}/iso/hard-utility/nodeb.htm"><span><fmt:message key="title.tabControls.hardUtilityNodeB"/></span></a></li>
		<li class=""><a href="${pageContext.request.contextPath}/iso/hard-utility/sgsn.htm"><span><fmt:message key="title.tabControls.hardUtilitySgsn"/></span></a></li>
		<li class=""><a href="${pageContext.request.contextPath}/iso/hard-utility/msc.htm"><span><fmt:message key="title.tabControls.hardUtilityMsc"/></span></a></li>
	</c:when>
 	<c:when test="${function == 'gprs'}">
		<li class=""><a href="${pageContext.request.contextPath}/iso/hard-utility/trx.htm"><span><fmt:message key="title.tabControls.hardUtilityTrx"/></span></a></li>
		<li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/iso/hard-utility/gprs.htm"><span><fmt:message key="title.tabControls.hardUtilityGprs"/></span></a></li>
		<li class=""><a href="${pageContext.request.contextPath}/iso/hard-utility/abis-ater.htm"><span><fmt:message key="title.tabControls.hardUtilityAbisAter"/></span></a></li>
		<li class=""><a href="${pageContext.request.contextPath}/iso/hard-utility/nodeb.htm"><span><fmt:message key="title.tabControls.hardUtilityNodeB"/></span></a></li>
		<li class=""><a href="${pageContext.request.contextPath}/iso/hard-utility/sgsn.htm"><span><fmt:message key="title.tabControls.hardUtilitySgsn"/></span></a></li>
		<li class=""><a href="${pageContext.request.contextPath}/iso/hard-utility/msc.htm"><span><fmt:message key="title.tabControls.hardUtilityMsc"/></span></a></li>
	</c:when>
	<c:when test="${function == 'abis-ater'}">
		<li class=""><a href="${pageContext.request.contextPath}/iso/hard-utility/trx.htm"><span><fmt:message key="title.tabControls.hardUtilityTrx"/></span></a></li>
		<li class=""><a href="${pageContext.request.contextPath}/iso/hard-utility/gprs.htm"><span><fmt:message key="title.tabControls.hardUtilityGprs"/></span></a></li>
		<li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/iso/hard-utility/abis-ater.htm"><span><fmt:message key="title.tabControls.hardUtilityAbisAter"/></span></a></li>
		<li class=""><a href="${pageContext.request.contextPath}/iso/hard-utility/nodeb.htm"><span><fmt:message key="title.tabControls.hardUtilityNodeB"/></span></a></li>
		<li class=""><a href="${pageContext.request.contextPath}/iso/hard-utility/sgsn.htm"><span><fmt:message key="title.tabControls.hardUtilitySgsn"/></span></a></li>
		<li class=""><a href="${pageContext.request.contextPath}/iso/hard-utility/msc.htm"><span><fmt:message key="title.tabControls.hardUtilityMsc"/></span></a></li>
	</c:when>
	<c:when test="${function == 'nodeb'}">
		<li class=""><a href="${pageContext.request.contextPath}/iso/hard-utility/trx.htm"><span><fmt:message key="title.tabControls.hardUtilityTrx"/></span></a></li>
		<li class=""><a href="${pageContext.request.contextPath}/iso/hard-utility/gprs.htm"><span><fmt:message key="title.tabControls.hardUtilityGprs"/></span></a></li>
		<li class=""><a href="${pageContext.request.contextPath}/iso/hard-utility/abis-ater.htm"><span><fmt:message key="title.tabControls.hardUtilityAbisAter"/></span></a></li>
		<li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/iso/hard-utility/nodeb.htm"><span><fmt:message key="title.tabControls.hardUtilityNodeB"/></span></a></li>
		<li class=""><a href="${pageContext.request.contextPath}/iso/hard-utility/sgsn.htm"><span><fmt:message key="title.tabControls.hardUtilitySgsn"/></span></a></li>
		<li class=""><a href="${pageContext.request.contextPath}/iso/hard-utility/msc.htm"><span><fmt:message key="title.tabControls.hardUtilityMsc"/></span></a></li>
	</c:when>
	<c:when test="${function == 'sgsn'}">
		<li class=""><a href="${pageContext.request.contextPath}/iso/hard-utility/trx.htm"><span><fmt:message key="title.tabControls.hardUtilityTrx"/></span></a></li>
		<li class=""><a href="${pageContext.request.contextPath}/iso/hard-utility/gprs.htm"><span><fmt:message key="title.tabControls.hardUtilityGprs"/></span></a></li>
		<li class=""><a href="${pageContext.request.contextPath}/iso/hard-utility/abis-ater.htm"><span><fmt:message key="title.tabControls.hardUtilityAbisAter"/></span></a></li>
		<li class=""><a href="${pageContext.request.contextPath}/iso/hard-utility/nodeb.htm"><span><fmt:message key="title.tabControls.hardUtilityNodeB"/></span></a></li>
		<li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/iso/hard-utility/sgsn.htm"><span><fmt:message key="title.tabControls.hardUtilitySgsn"/></span></a></li>
		<li class=""><a href="${pageContext.request.contextPath}/iso/hard-utility/msc.htm"><span><fmt:message key="title.tabControls.hardUtilityMsc"/></span></a></li>
	</c:when>
	<c:when test="${function == 'msc'}">
		<li class=""><a href="${pageContext.request.contextPath}/iso/hard-utility/trx.htm"><span><fmt:message key="title.tabControls.hardUtilityTrx"/></span></a></li>
		<li class=""><a href="${pageContext.request.contextPath}/iso/hard-utility/gprs.htm"><span><fmt:message key="title.tabControls.hardUtilityGprs"/></span></a></li>
		<li class=""><a href="${pageContext.request.contextPath}/iso/hard-utility/abis-ater.htm"><span><fmt:message key="title.tabControls.hardUtilityAbisAter"/></span></a></li>
		<li class=""><a href="${pageContext.request.contextPath}/iso/hard-utility/nodeb.htm"><span><fmt:message key="title.tabControls.hardUtilityNodeB"/></span></a></li>
		<li class=""><a href="${pageContext.request.contextPath}/iso/hard-utility/sgsn.htm"><span><fmt:message key="title.tabControls.hardUtilitySgsn"/></span></a></li>
		<li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/iso/hard-utility/msc.htm"><span><fmt:message key="title.tabControls.hardUtilityMsc"/></span></a></li>
	</c:when>
 	<c:otherwise></c:otherwise>
</c:choose>
</ul>
<br>

<form:form id="filterController" method="post" action="${function}.htm">
	<table border="0" width="100%" cellspacing="0" cellpadding="0" class="form">
			<tr> 
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
			</tr>
			<tr>
				<td colspan="2">
	 				<%@ include file="/WEB-INF/jspiso/jqwidgets/gridDefault.jsp" %>
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