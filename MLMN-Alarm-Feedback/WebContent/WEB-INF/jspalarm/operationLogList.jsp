<%@ include file="/commons/taglibs.jsp"   %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<title><fmt:message key="title.rAlarmOperationLog.formList"/></title>
<content tag="heading"><fmt:message key="title.rAlarmOperationLog.formList"/></content>

<ul class="ui-tabs-nav">
<c:choose>
	<c:when test="${function == 'tong-hop'}">
		<li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/alarm/operation-log/tong-hop.htm"><span><fmt:message key="title.tabControls.tongHop"/></span></a></li>
		<li class=""><a href="${pageContext.request.contextPath}/alarm/operation-log/chi-tiet.htm"><span><fmt:message key="title.tabControls.chiTiet"/></span></a></li>
	</c:when>
 	<c:when test="${function == 'chi-tiet'}">
		<li class=""><a href="${pageContext.request.contextPath}/alarm/operation-log/tong-hop.htm"><span><fmt:message key="title.tabControls.tongHop"/></span></a></li>
		<li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/alarm/operation-log/chi-tiet.htm"><span><fmt:message key="title.tabControls.chiTiet"/></span></a></li>
	</c:when>
 	<c:otherwise></c:otherwise>
</c:choose>
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
			<%-- <td>
					<fmt:message key="label.fromDate"/>&nbsp;
					<input type="text" id="startDate" name="startDate" value="${startDate}" style="width: 8%" maxlength="20"/>
   					<img alt="calendar" title="Click to choose the start date" id="chooseStartDate" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>&nbsp;
					<fmt:message key="label.toDate"/>&nbsp;
					<input type="text" id="endDate" name="endDate" value="${endDate}" style="width: 8%" maxlength="20"/>
   					<img alt="calendar" title="Click to choose the end date" id="chooseEndDate" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>&nbsp;
   					<fmt:message key="rAlarmOperationLog.vendor"/>&nbsp;
   					<select name="vendor" class="wid8" id="vendor">
           				<option value="">All</option>
           				<c:forEach var="items" items="${vendorList}">
			              <c:choose>
			                <c:when test="${items.value == vendor}">
			                    <option value="${items.value}" selected="selected">${items.value}</option>
			                </c:when>
			                <c:otherwise>
			                    <option value="${items.value}">${items.value}</option>
			                </c:otherwise>
			              </c:choose>
					    </c:forEach>
           			</select>&nbsp;
           			<fmt:message key="rAlarmOperationLog.user"/>&nbsp;
					<div id='askUser'></div>&nbsp;
					<fmt:message key="rAlarmOperationLog.neType"/>
           			<input type="text" id="neType" name="neType" value="${neType}" />&nbsp;	 
					<fmt:message key="rAlarmOperationLog.neName"/>&nbsp;   
					<input type="text" id="neName" name="neName" value="${neName}" />&nbsp;
				
					<input class="button" type="submit" name="filter" value="<fmt:message key="global.form.timkiem"/>" />
			</td> --%>
			
			<td align="left" colspan="2"><form:form commandName="filter" method="post" action="list.htm">
					<table border="0" cellspacing="3" cellpadding="0" width="100%">
						<tr> 
							<td class="wid2 mwid60"><fmt:message key="label.fromDate"/></td>
							<td class="wid10">
				           		<input type="text" id="startDate" name="startDate" value="${startDate}" style="width: 70%" maxlength="20"/>
   								<img alt="calendar" title="Click to choose the start date" id="chooseStartDate" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
							</td>
							<td class="wid2 mwid60"><fmt:message key="label.toDate"/></td>
							<td class="wid10">
								<input type="text" id="endDate" name="endDate" value="${endDate}" style="width: 70%" maxlength="20"/>
   								<img alt="calendar" title="Click to choose the end date" id="chooseEndDate" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
							</td>
							<td class="wid2 mwid60"><fmt:message key="rAlarmOperationLog.vendor"/></td>
							<td class="wid10">
								<select name="vendor" class="wid90" id="vendor">
			           				<option value="">All</option>
			           				<c:forEach var="items" items="${vendorList}">
						              <c:choose>
						                <c:when test="${items.value == vendor}">
						                    <option value="${items.value}" selected="selected">${items.value}</option>
						                </c:when>
						                <c:otherwise>
						                    <option value="${items.value}">${items.value}</option>
						                </c:otherwise>
						              </c:choose>
								    </c:forEach>
			           			</select>
							</td>
							<td class="wid2 mwid40"><fmt:message key="rAlarmOperationLog.user"/></td>
							<td class="wid10"><div id='askUser'></div></td>
							<td class="wid2 mwid60"><fmt:message key="rAlarmOperationLog.neType"/></td>
							<td class="wid10"><input type="text" id="neType" name="neType" value="${neType}" /></td>
							<td class="wid2 mwid60"><fmt:message key="rAlarmOperationLog.neName"/></td>
							<td class="wid10"><input type="text" id="neName" name="neName" value="${neName}" /></td>
							<td><input class="button" type="submit" name="filter" value="<fmt:message key="global.form.timkiem"/>" /></td>
						</tr>			
					</table>
				</form:form>
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
var theme = getTheme();
$("#user").jqxInput({ width: '90%', height: 20, minLength: 1, theme: theme });
$("#neType").jqxInput({ width: '90%', height: 20, minLength: 1, theme: theme });
$("#neName").jqxInput({ width: '90%', height: 20, minLength: 1, theme: theme });

${gridReport}


$('#Menu').on('itemclick', function (event) {
	var args = event.args;
	// export data
	if ($.trim($(args).text()) == '<fmt:message key="global.button.exportExcel"/>') {
			window.open('${pageContext.request.contextPath}/alarm/operation-log/exportData.htm?startDate='+"<c:out value='${startDate}'/>"+
		        	 '&endDate='+"<c:out value='${endDate}'/>"+
		        	 '&vendor='+"<c:out value='${vendor}'/>"+
		        	 '&neType='+"<c:out value='${neType}'/>"+
		        	 '&neName='+"<c:out value='${neName}'/>"+
		        	 '&askUser='+"<c:out value='${askUser}'/>"+
		        	 '&function='+"<c:out value='${function}'/>"+
		        	 '&strWhere='+$("#strWhere").val()+
		        	 '&sortfield='+$("#sortfield").val()+
		        	 '&sortorder='+$("#sortorder").val()
		        	 ,'_blank');
			}
});

//call view detail    
$("#jqxgrid").on('cellselect', function (event) 
{	var functionName = "<c:out value='${function}'/>";
    var columnheader = $("#jqxgrid").jqxGrid('getcolumn', event.args.datafield).text; 
  	if (columnheader =='Qty' && functionName == 'tong-hop')
    {
    	var rowindex = event.args.rowindex;
    	var row = $("#jqxgrid").jqxGrid('getrowdata', rowindex);
    	var startDate= '';
    	var endDate= '';
    	var vendor = '';
    	var neType = '';
    	var neName = '';
    	var askUser = '';
    	var day = new Date(row.startTime);
    	if(row.startTime!=null)
    	{
    		startDate = dateToYMD(day);
    		endDate=startDate;
    	}
    	if(row.vendor!=null)
    	{
    		vendor=row.vendor;
    	}
    	if(row.neType!=null)
    	{
    		neType=row.neType;
    	}
    	if(row.neName!=null)
    	{
    		neName=row.neName;
    	}
    	if(row.askUser!=null)
    	{
    		askUser=row.askUser;
    	}
    	
    	window.open('${pageContext.request.contextPath}/alarm/operation-log/chi-tiet.htm?startDate=' + startDate + '&endDate='+endDate+'&vendor='+vendor+'&neType='+neType+'&neName='+neName+'&askUser='+askUser,'_blank');
    }
    
});

function dateToYMD(date) {
    var d = date.getDate();
    var m = date.getMonth() + 1;
    var y = date.getFullYear();
    return '' + (d <= 9 ? '0' + d : d) + '/' + (m<=9 ? '0' + m : m) + '/' + y ;
}
</script>
<script type="text/javascript">
$(document).ready(function(){
	var theme =  getTheme(); 
	// Create a jqxComboBox user
   	var urlUser = "${pageContext.request.contextPath}/alarm/operation-log/userList.htm";
    // prepare the data
    var sourceUser =
    {
        datatype: "json",
        datafields: [
            { name: 'askUser' },
            { name: 'askUser' }
        ],
        url: urlUser,
        async: false
    };
    var dataAdapterUser = new $.jqx.dataAdapter(sourceUser);
    // Create a jqxComboBox
    $("#askUser").jqxComboBox({ source: dataAdapterUser, displayMember: "askUser", valueMember: "askUser", width: '90%',height: 20,itemHeight: 30,theme: theme });
    var askUser =  "<c:out value='${askUser}'/>";
    if(askUser=="")
		$('#askUser').val('ALL');
	else
		$('#askUser').val(askUser);
});
</script>