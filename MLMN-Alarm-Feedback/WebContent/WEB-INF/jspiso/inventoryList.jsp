<%@ include file="/includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<c:choose>
  <c:when test="${status == 'N'}">    
		<title><fmt:message key="sidebar.admin.isoInventoryNew"/></title>
		<content tag="heading"><fmt:message key="sidebar.admin.isoInventoryNew"/></content>
  </c:when>
  <c:when test="${status == 'H'}">
  		<title><fmt:message key="sidebar.admin.isoInventoryHistory"/></title>
		<content tag="heading"><fmt:message key="sidebar.admin.isoInventoryHistory"/></content>
  </c:when>
  <c:when test="${status == 'R'}">
  		<title><fmt:message key="sidebar.admin.isoInventoryRemove"/></title>
		<content tag="heading"><fmt:message key="sidebar.admin.isoInventoryRemove"/></content>
  </c:when>
  <c:otherwise></c:otherwise>
</c:choose>

<div>
	<input id="strWhere" name="strWhere" value="" type="hidden"/>
	<input id="sortfield" name="sortfield" value="" type="hidden"/>
	<input id="sortorder" name="sortorder" value="" type="hidden"/>
</div>
<table border="0" width="100%" cellspacing="0" cellpadding="0" class="form">
		<tr> 
			<td align="left" colspan="2"><form method="post" action="list.htm?status=${status}">
					<table border="0" cellspacing="1" cellpadding="0" width="100%">
						<tr>
							<c:choose>
	  							<c:when test="${status == 'N'}"> 
									<td style="padding-left: 5px;" class="wid6 mwid100"><fmt:message key="isoInventory.startInitialize"/></td>
									<td class="wid10">
								       	<input id="startDate" name="startDate" value="${startDate}" class="wid60" maxlength="20"/>
									 	<img alt="calendar" title="Click to choose the start date" id="chooseStartDate" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
									</td>
									<td style="padding-left: 5px;" class="wid6 mwid70"><fmt:message key="isoInventory.endInitialize"/></td>
									<td class="wid10">
								       <input id="endDate" name="endDate" value="${endDate}" class="wid60" maxlength="20"/>
									 	<img alt="calendar" title="Click to choose the end date" id="chooseEndDate" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
									</td>
									<td style="padding-left: 5px;" class="wid7"><fmt:message key="${aliasName}"/></td>
									<td class="wid10"><input type="text" id="oldNe" name="oldNe" value="${oldNeName}" /></td> 
									<td style="padding-left: 5px;" class="wid6"><fmt:message key="isoEquipment.ne"/></td>
									<td class="wid10"><input type="text" id="ne" name="ne" value="${neName}" /></td>  
									
								</c:when>
							  	<c:otherwise>
							  		<td  style="padding-left: 5px;" class="wid6 mwid100"><fmt:message key="isoLicenseSoft.startDate"/></td>
									<td class="wid10">	
								       <input id="startDate" name="startDate" value="${startDate}" class="wid60" maxlength="20"/>
									 	<img alt="calendar" title="Click to choose the start date" id="chooseStartDate" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
									</td>
									<td  style="padding-left: 5px;" class="wid6 mwid70"><fmt:message key="isoLicenseSoft.endDate"/></td>
									<td class="wid10">
								       	<input id="endDate" name="endDate" value="${endDate}" class="wid60" maxlength="20"/>
								 		<img alt="calendar" title="Click to choose the end date" id="chooseEndDate" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
									</td>
									<td  style="padding-left: 5px;" class="wid7"><fmt:message key="${aliasName}"/></td>
									<td class="wid10"><input type="text" id="oldNe" name="oldNe" value="${oldNeName}" /></td> 
									<td  style="padding-left: 5px;" class="wid6"><fmt:message key="isoEquipment.ne"/></td>
									<td class="wid10"><input type="text" id="ne" name="ne" value="${neName}" /></td>  
									
							  	</c:otherwise>
							</c:choose>		 
							
						</tr>			
						<tr>
								
								<td style="padding-left: 5px;" class="wid6 mwid100"><fmt:message key="isoEquipment.productName"/></td> 
								<td><input type="text" id="productName" name="productName" value="${productName}" /></td>
								<td style="padding-left: 5px;" class="wid6 mwid80"><fmt:message key="isoEquipment.productCode"/></td>
								<td class="wid10"><input type="text" id="productCode" name="productCode" value="${productCode}" class="wid90" /></td>
								<td  style="padding-left: 5px;" class="wid6 mwid100"><fmt:message key="isoEquipment.seriNo"/></td>
								<td><input type="text" id="seriNo" name="seriNo" value="${seriNo}" /></td>
								<td  style="padding-left: 5px;" class="wid6 mwid100"><fmt:message key="isoEquipment.locationName"/></td>
								<td><input type="text" id="locationName" name="locationName" value="${locationName}" /></td>
								<td><input type="submit" value="<fmt:message key="global.form.timkiem"/>" id='jqxSubmitButton' /></td>
						</tr>	
					</table>
				</form>
			</td>
		</tr>
		<tr>
			<td colspan="2">
				<%@ include file="/WEB-INF/jspiso/jqwidgets/gridReportByPage.jsp" %>
			</td>
		</tr>
</table>

<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar_en.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar_setup.js"></script>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/calendar-blue.css" />

<script type="text/javascript">
 $(document).ready(function () {
	var theme = getTheme();
	$("#jqxSubmitButton").jqxButton({theme: theme});
	$("#ne").jqxInput({ width: '98%', height: 20, minLength: 1, theme: theme });
	$("#oldNe").jqxInput({ width: '98%', height: 20, minLength: 1, theme: theme });
	$("#productCode").jqxInput({ height: 20, minLength: 1, theme: theme });
	$("#productName").jqxInput({ height: 20, minLength: 1, theme: theme });
	$("#locationName").jqxInput({ height: 20, minLength: 1, theme: theme });
	$("#seriNo").jqxInput({ height: 20, minLength: 1, theme: theme });
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
$('#Menu').on('itemclick', function (event) {
	var args = event.args;
	var rowindex = $('#jqxgrid').jqxGrid('getselectedrowindex');
	var row = $('#jqxgrid').jqxGrid('getrowdata', rowindex);
	// export data
	if ($.trim($(args).text()) == '<fmt:message key="global.button.exportExcel"/>') {
		window.open('${pageContext.request.contextPath}/iso/theo-doi-tai-nguyen-mang-luoi/exportTrackInventory.htm?startDate='+"<c:out value='${startDate}'/>"+
	        	'&endDate='+"<c:out value='${endDate}'/>"+
	        	 '&productCode='+"<c:out value='${productCode}'/>"+
	        	 '&productName='+"<c:out value='${productName}'/>"+
	        	 '&locationName='+"<c:out value='${locationName}'/>"+
	        	 '&seriNo='+"<c:out value='${seriNo}'/>"+
	        	 '&status='+"<c:out value='${status}'/>"+
	        	 '&strWhere='+$("#strWhere").val()+
	        	 '&sortfield='+$("#sortfield").val()+
	        	 '&sortorder='+$("#sortorder").val(),'_blank');
		}
	
	  });
</script>
