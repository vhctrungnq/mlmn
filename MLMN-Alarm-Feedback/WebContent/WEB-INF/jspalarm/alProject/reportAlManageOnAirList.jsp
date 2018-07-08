<%@ include file="/commons/taglibs.jsp"   %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<title><fmt:message key="title.reportAlManageOnAir.formList"/></title>
<content tag="heading"><fmt:message key="title.reportAlManageOnAir.formList"/></content>

<ul class="ui-tabs-nav">
<c:choose>
	<c:when test="${function == 'theo-ngay'}">
		<li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/alarm/report-al-manage-on-air/theo-ngay.htm"><span><fmt:message key="title.tabControls.reportAlManageOnAir.theoNgay"/></span></a></li>
		<li class=""><a href="${pageContext.request.contextPath}/alarm/report-al-manage-on-air/luy-tien.htm"><span><fmt:message key="title.tabControls.reportAlManageOnAir.luyTien"/></span></a></li>
		
	</c:when>
 	<c:when test="${function == 'luy-tien'}">
		<li class=""><a href="${pageContext.request.contextPath}/alarm/report-al-manage-on-air/theo-ngay.htm"><span><fmt:message key="title.tabControls.reportAlManageOnAir.theoNgay"/></span></a></li>
		<li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/alarm/report-al-manage-on-air/luy-tien.htm"><span><fmt:message key="title.tabControls.reportAlManageOnAir.luyTien"/></span></a></li>
		
	</c:when>
	<%-- <c:when test="${function == 'tong-hop'}">
		<li class=""><a href="${pageContext.request.contextPath}/alarm/report-al-manage-on-air/theo-ngay.htm"><span><fmt:message key="title.tabControls.reportAlManageOnAir.theoNgay"/></span></a></li>
		<li class=""><a href="${pageContext.request.contextPath}/alarm/report-al-manage-on-air/luy-tien.htm"><span><fmt:message key="title.tabControls.reportAlManageOnAir.luyTien"/></span></a></li>
		<li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/alarm/report-al-manage-on-air/tong-hop.htm"><span><fmt:message key="title.tabControls.reportAlManageOnAir.tongHop"/></span></a></li>
	</c:when> --%>
 	<c:otherwise></c:otherwise>
</c:choose>
</ul>
<br>

<form:form id="filterController" method="post" action="${function}.htm">
	<table border="0" width="100%" cellspacing="0" cellpadding="0" class="form">
		<tr>
			<td>
				<fmt:message key="label.fromDate"/>&nbsp;
				<input type="text" id="startDate" name="startDate" value="${startDate}" style="width: 8%" maxlength="20"/>
				<img alt="calendar" title="Click to choose the start date" id="chooseStartDate" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>&nbsp;
				<fmt:message key="label.toDate"/>&nbsp;
				<input type="text" id="endDate" name="endDate" value="${endDate}" style="width: 8%" maxlength="20"/>
				<img alt="calendar" title="Click to choose the end date" id="chooseEndDate" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>&nbsp;
				
				<input class="button" id="btSearch" type="submit" name="filter" value="<fmt:message key="global.form.timkiem"/>" />
			</td>
		</tr>
		<tr>
			<td>
				<div id='jqxWidget' style="margin-top:5px">
			    	<div style="float: right;margin-bottom:3px;width:180px;">
			        	<!-- <table border="0" cellspacing="1" cellpadding="0" width="100%">
							<tr>
								<td><div style="float: right;" id="jqxlistbox"></div></td>
							</tr>
						</table> -->
			        </div><br>
			        <div id="jqxgrid"></div>
			        <div id='Menu'>
			            <ul>
					   		<li><fmt:message key="global.button.exportExcel"/></li>
				        </ul>
			       </div>
			    </div>
			</td>
		</tr>
		<c:choose>
			<c:when test="${function == 'luy-tien' || function == 'theo-ngay'}">
				<tr>
					<td>
						<div id='jqxWidgetSiteType' style="margin-top:5px">
					    	<div style="float: right;margin-bottom:3px;width:180px;">
					        	<!-- <table border="0" cellspacing="1" cellpadding="0" width="100%">
									<tr>
										<td><div style="float: right;" id="jqxlistboxSiteType"></div></td>
									</tr>
								</table> -->
					        </div><br>
					        <div id="jqxgridSiteType"></div>
					        <div id='MenuSiteType'>
					            <ul>
							   		<li><fmt:message key="global.button.exportExcel"/></li>
						        </ul>
					       </div>
					    </div>
					</td>
				</tr>
			</c:when>
		</c:choose>
	</table>
</form:form>

<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar_en.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar_setup.js"></script>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/calendar-blue.css" />

<script type="text/javascript">
var functionName = "<c:out value='${function}'/>";

if(functionName == 'theo-ngay'){
	
	
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
	
}

		
function focusIt()
{
	if(functionName == 'theo-ngay'){
		var mytext = document.getElementById("startDate");
		  
		  mytext.focus();
	}
	else{
		var mytext = document.getElementById("year");
		  mytext.focus();
	}
  
}
onload = focusIt;

</script>
<script type="text/javascript">
${gridReport}
var exportFileName =  "<c:out value='${exportFileName}'/>";
$('#Menu').on('itemclick', function (event) {
	var args = event.args;
	// export data
	if ($.trim($(args).text()) == '<fmt:message key="global.button.exportExcel"/>') {	
			$("#jqxgrid").jqxGrid('exportdata', 'xls', exportFileName);
		}
});

$('#MenuSiteType').on('itemclick', function (event) {
	var args = event.args;
	// export data
	if ($.trim($(args).text()) == '<fmt:message key="global.button.exportExcel"/>') {	
			$("#jqxgridSiteType").jqxGrid('exportdata', 'xls', exportFileName);
		}
});
</script>
<script type="text/javascript">
var theme = getTheme();
$("#btSearch").jqxButton({theme: theme});
${gridReportSiteType}
</script>