<%@ include file="/commons/taglibs.jsp"   %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<title><fmt:message key="title.rAlarmOperationLog.formList"/></title>
<content tag="heading"><fmt:message key="RAlarmLogIpbb.title"/></content>

<ul class="ui-tabs-nav">
<li class=""><a href="${pageContext.request.contextPath}/alarmLog/${function}.htm?netWork="><span><fmt:message key="title.tabControls.All"/></span></a></li>
<li class=""><a href="${pageContext.request.contextPath}/alarmLog/${function}.htm?netWork=2G"><span><fmt:message key="title.tabControls.2G"/></span></a></li>
<li class=""><a href="${pageContext.request.contextPath}/alarmLog/${function}.htm?netWork=3G"><span><fmt:message key="title.tabControls.3G"/></span></a></li>
<li class=""><a href="${pageContext.request.contextPath}/alarmLog/${function}.htm?netWork=CS_CORE"><span><fmt:message key="title.tabControls.CS_CORE"/></span></a></li>
<li class=""><a href="${pageContext.request.contextPath}/alarmLog/${function}.htm?netWork=PS_CORE"><span><fmt:message key="title.tabControls.PS_CORE"/></span></a></li>
<li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/alarmLog/other/ipbb/${function}.htm"><span><fmt:message key="title.tabControls.IPBB"/></span></a></li>
</ul>
<br>

<form:form id="filterController" method="post" action="${function}.htm">
	<div>
		<input id="strWhere" name="strWhere" value="" type="hidden"/>
		<input id="sortfield" name="sortfield" value="" type="hidden"/>
		<input id="sortorder" name="sortorder" value="" type="hidden"/>
	</div>
	<table border="0" width="100%" cellspacing="0" cellpadding="0" class="form">
		<tr>
		 <td align="left" colspan="2"> 
					<table border="0" cellspacing="3" cellpadding="0" width="100%">
						<tr> 
							<td class="wid2 mwid60"><fmt:message key="label.fromDate"/></td>
							<td class="wid10">
				           		<input type="text" id="startDate" name="startDate" value="${startDate}" style="width: 80%" maxlength="20"/>
   								<img alt="calendar" title="Click to choose the start date" id="chooseStartDate" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
							</td>
							<td class="wid2 mwid60"><fmt:message key="label.toDate"/></td>
							<td class="wid10">
								<input type="text" id="endDate" name="endDate" value="${endDate}" style="width: 80%" maxlength="20"/>
   								<img alt="calendar" title="Click to choose the end date" id="chooseEndDate" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
							</td>
							 
							<td class="wid2 mwid60"><fmt:message key="RAlarmLogIpbb.filter.host"/></td>
							<td class="wid20"><input type="text" id="host" name="host" value="${host}" /></td>
							<td><input class="button" type="submit" name="filter" value="<fmt:message key="global.form.timkiem"/>" /></td>
						</tr>			
					</table> 
			</td>
		</tr>
		<tr>
			<td>
				<div id='jqxWidget' style="margin-top:5px">
			    	<div style="float: right;margin-bottom:3px;width:180px;">
			        	<table border="0" cellspacing="1" cellpadding="0" width="100%">
							<tr>
								<td><div style="float: right;" id="jqxlistbox"></div></td>
							</tr>
						</table>
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
	</table>
</form:form>

<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar_en.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar_setup.js"></script>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/calendar-blue.css" />

<script type="text/javascript">
Calendar.setup({
    inputField		:	"startDate",	// id of the input field
    ifFormat		:	"%d/%m/%Y %H:%M",   	// format of the input field
    button			:   "chooseStartDate",  	// trigger for the calendar (button ID)
    singleClick		:   false					// double-click mode
});

Calendar.setup({
    inputField		:	"endDate",	// id of the input field
    ifFormat		:	"%d/%m/%Y %H:%M",   	// format of the input field
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
var theme = getTheme();
$("#host").jqxInput({ width: '95%', height: 20, minLength: 1, theme: theme }); 

${gridReport}


$('#Menu').on('itemclick', function (event) {
	var args = event.args;
	// export data
	if ($.trim($(args).text()) == '<fmt:message key="global.button.exportExcel"/>') {
			window.open('${pageContext.request.contextPath}/alarmLog/other/ipbb/exportData.htm?startDate='+"<c:out value='${startDate}'/>"+
		        	 '&endDate='+"<c:out value='${endDate}'/>"+
		        	 '&host='+"<c:out value='${host}'/>"+  
		        	 '&function='+"<c:out value='${function}'/>"+ 
		        	 '&strWhere='+$("#strWhere").val()+
		        	 '&sortfield='+$("#sortfield").val()+
		        	 '&sortorder='+$("#sortorder").val()
		        	 ,'_blank');
			}
}); 
</script>