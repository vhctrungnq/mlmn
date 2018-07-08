<%@ include file="/includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<title><fmt:message key="title.inventoryHome.list"/></title>
<center><h1><fmt:message key="title.inventoryHome.list"/></h1></center>

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
				<td colspan="2"><h3 style="padding-top:8px;">Tài nguyên theo Vendor</h3></td>
			</tr>
			<tr>
				<td colspan="2">
				<div id='jqxWidgetVendor' style="margin-top:3px">
					<div id="gridVendor"></div>		
						<div id='menuVendor'>
					            <ul>
							   		<li><fmt:message key="global.button.exportExcel"/></li>
						        </ul>
					 </div>
				</div>
				</td>
			</tr>
			<tr>
				<td colspan="2"><h3 style="padding-top:10px;">Tài nguyên theo NE Type</h3></td>
			</tr>
			<tr>
				<td colspan="2">
	 				<div id='jqxWidgetNetwork' style="margin-top:3px">
	 				<div id="gridNetwork"></div>		
						<div id='menuNetwork'>
					            <ul>
							   		<li><fmt:message key="global.button.exportExcel"/></li>
						        </ul>
					 </div>
					 </div>
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
<script type="text/javascript">
${gridVendor}
${gridNetwork}

//handle context menu Vendor
$("#menuVendor").on('itemclick', function (event) {
	    var args = event.args;
	    var rowindex = $("#gridVendor").jqxGrid('getselectedrowindex');
	    var row = $("#gridVendor").jqxGrid('getrowdata', rowindex);
	    var exportFileName =  "<c:out value='${exportFileName}'/>";
	    
	    if ($.trim($(args).text()) == '<fmt:message key="global.button.exportExcel"/>')  {
	    	$("#gridVendor").jqxGrid('exportdata', 'xls', exportFileName);
	    }
});

//handle context menu Vendor
$("#menuNetwork").on('itemclick', function (event) {
	    var args = event.args;
	    var rowindex = $("#gridNetwork").jqxGrid('getselectedrowindex');
	    var row = $("#gridNetwork").jqxGrid('getrowdata', rowindex);
	    var exportFileName =  "<c:out value='${exportFileName}'/>";
	    
	    if ($.trim($(args).text()) == '<fmt:message key="global.button.exportExcel"/>')  {
	    	$("#gridNetwork").jqxGrid('exportdata', 'xls', exportFileName);
	    }
});
</script>