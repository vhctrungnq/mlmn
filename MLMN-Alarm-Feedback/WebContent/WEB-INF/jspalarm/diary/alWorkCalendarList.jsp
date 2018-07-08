<%@ include file="/includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<script type="text/javascript" src="${pageContext.request.contextPath}/js/jQWidgets/jqwidgets/jqxdropdownbutton.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jQWidgets/jqwidgets/jqxtree.js"></script>
<title><fmt:message key="title.alWorkCalendar.alWorkCalendarlist"/></title>
<content tag="heading"><fmt:message key="title.alWorkCalendar.alWorkCalendarlist"/></content>

<div>
	<input id="strWhere" name="strWhere" value="" type="hidden"/>
	<input id="sortfield" name="sortfield" value="" type="hidden"/>
	<input id="sortorder" name="sortorder" value="" type="hidden"/>
</div>
<table border="0" width="100%" cellspacing="0" cellpadding="0" class="form">
		<tr> 
			<td align="left"><form:form commandName="filter" method="post" action="list.htm?region=${region}">
					<table width="100%" border="0" cellspacing="3" cellpadding="0">
						<tr> 
							
							<td class="wid6 mwid70">
								<fmt:message key="alWorkCalendar.startDate"/>
							</td>
							<td class="wid12">
								<input type="text" id="startDate" name="startDate" value="${startDate}" style="width: 70%" maxlength="20"/>
   								<img alt="calendar" title="Click to choose the start date" id="chooseStartDate" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>&nbsp;
			           		</td>
							<td class="wid6 mwid70"><fmt:message key="alWorkCalendar.endDate"/></td>
							<td class="wid12">
								<input type="text" id="endDate" name="endDate" value="${endDate}" style="width: 70%" maxlength="20"/>
   								<img alt="calendar" title="Click to choose the end date" id="chooseEndDate" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>&nbsp;
							</td>
							<%-- <td  class="wid6 mwid70">
				        	<fmt:message key="qLPhongBan.region"/>
					        </td>
					        <td class="wid10">
						        <select id="region" name ="region" class="wid90">
						       			 <option value=""></option>
					   					<c:forEach var="items" items="${regionList}">
							              <c:choose>
							                <c:when test="${items.name == region}">
							                    <option value="${items.name}" selected="selected">${items.value}</option>
							                </c:when>
							                <c:otherwise>
							                    <option value="${items.name}">${items.value}</option>
							                </c:otherwise>
							              </c:choose>
									    </c:forEach>
					           		</select>	
						        
					        </td> --%>
							<td class="wid3"><fmt:message key="alWorkCalendar.shift"/></td>
							<td class="wid9">
								<select id="shift" name="shift" class="wid90">
									<option value="">--Tất cả--</option>
									<c:forEach var="items" items="${shiftList}">
										<c:choose>
										<c:when test="${items.value == shift}">
										   <option value="${items.value}" selected="selected">${items.value}</option>
										</c:when>
										<c:otherwise>
										   <option value="${items.value}">${items.value}</option>
										</c:otherwise>
										</c:choose>
									</c:forEach>
								</select>
							</td>	
							<td class="wid6 mwid70"><fmt:message key="alWorkCalendar.deptCode"/></td>
							<td class="wid15">
								<select id="deptCode" name="deptCode" class="wid90">
									<option value="">--Tất cả--</option>
									<c:forEach var="items" items="${deptCodeList}">
										<c:choose>
										<c:when test="${items.deptValue == deptCode}">
										   <option value="${items.deptValue}" selected="selected">${items.deptName}</option>
										</c:when>
										<c:otherwise>
										   <option value="${items.deptValue}">${items.deptName}</option>
										</c:otherwise>
										</c:choose>
									</c:forEach>
								</select>
			           		</td>
			           		<td class="wid5"><fmt:message key="alWorkCalendar.email"/></td>
			           		<td class="wid15">
								<input type="text" id="email" name="email" value="${email}" />
			           		</td>
							<td><input class="button" id="btSearch" type="submit" name="filter"
								value="<fmt:message key="global.form.timkiem"/>" /></td>
						</tr>			
					</table>
				</form:form>
				</td>
				<td class="wid6">
				</td> 
		</tr>
		<tr>
			<td colspan="2">
				<div id='jqxWidget' style="margin-top:5px;">
				<div style="float: right;margin-bottom:3px;width:300px;">
			        	<table border="0" cellspacing="1" cellpadding="0" width="100%">
							<tr>
							<c:if test="${checkCaTruc == true}">
			    	
								<td align="right">
									<input type="button" value="<fmt:message key="global.button.addNew"/>" id='addNew' />&nbsp;
									<c:if test="${isRoleManager == 'Y' }">
										<input type="button" value="<fmt:message key="global.button.confirm"/>" id='confirm' />&nbsp;
									</c:if>					
			      					<input type="button" value="<fmt:message key="global.button.import"/>" id='importFile' /></td>
			      			 </c:if>
								<td style="width:33px"><div style="float: right;" id="jqxlistbox"></div></td>
							</tr>
						</table>
			        </div><br>
			   
			        <div id="jqxgrid"></div>
			        <div id='Menu'>
			            <ul>
			            <c:if test="${checkCaTruc == true}">
							<li><fmt:message key="global.button.addNew"/></li>
				            <li><fmt:message key="global.button.editSelected"/></li>
					   		<li><fmt:message key="global.button.deleteMultiSelected"/></li>
					   </c:if>
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
<script type="text/javascript">
var theme = getTheme();
${gridManage}
var isRoleManager = '<c:out value="${isRoleManager}"/>';
var startDate = '<c:out value="${startDate}"/>';
var endDate = '<c:out value="${endDate}"/>';
var region = '<c:out value="${region}"/>';


$("#email").jqxInput({ width: '90%', height: 20, minLength: 1, theme: theme });
$("#btSearch").jqxButton({theme: theme});
$('#addNew').jqxButton({ theme: theme });
$('#confirm').jqxButton({ theme: theme });
$('#addNew').click(function () {
	if(startDate != "" && endDate != "")
		window.location = 'form.htm?startDate='+startDate+'&endDate='+endDate+'&region='+region;
	else
		window.location = 'form.htm?region='+region;
});

$('#importFile').jqxButton({ theme: theme });
$('#importFile').click(function () {
	if(startDate != "" && endDate != "")
		window.location = 'upload.htm?startDate='+startDate+'&endDate='+endDate+'&region='+region;
	else
		window.location = 'upload.htm?region='+region;
});
$('#Menu').on('itemclick', function (event) {
	var args = event.args;
	var rowindex = $('#jqxgrid').jqxGrid('getselectedrowindex');
	var row = $('#jqxgrid').jqxGrid('getrowdata', rowindex);
	// add new row
	if ($.trim($(args).text()) == '<fmt:message key="global.button.addNew"/>') {
		if(startDate != "" && endDate != "")
			window.location = 'form.htm?startDate='+startDate+'&endDate='+endDate+'&region='+region;
		else
			window.location = 'form.htm?region='+region;
		}
	else if ($.trim($(args).text()) == '<fmt:message key="global.button.editSelected"/>') {
		
		$.getJSON("${pageContext.request.contextPath}/alarm/al-work-calendar/alWorkCalendarById.htm", {id: row.id}, function(j){
			
			var shiftMode = j.shiftMode;
			if(shiftMode == 'Y'){
				if(startDate != "" && endDate != "")
					window.location = 'form.htm?id='+row.id+'&startDate='+startDate+"&endDate="+endDate+'&region='+region;
				else
					window.location = 'form.htm?id='+row.id+'&region='+region;
			}
				
			else
			{
				if(isRoleManager == 'N')
					alert("Ca trực này đã đóng. Bạn không thể đổi ca được.");
				else
					if(startDate != "" && endDate != "")
						window.location = 'form.htm?id='+row.id+'&startDate='+startDate+"&endDate="+endDate+'&region='+region;
					else
						window.location = 'form.htm?id='+row.id+'&region='+region;
				}	
		});
		
	}
	else if ($.trim($(args).text()) == '<fmt:message key="global.button.deleteMultiSelected"/>') {
		var r=confirm('<fmt:message key="messsage.confirm.delete"/>');
		if (r==true)
		  {
			var selectedrowindexes = $('#jqxgrid').jqxGrid('getselectedrowindexes'); 
    		var idList="";
    		var cond="";
    		if (selectedrowindexes != null) {
    			for (var i=0; i<selectedrowindexes.length; i++) {
    				var row = $("#jqxgrid").jqxGrid('getrowdata', selectedrowindexes[i]);
    				idList+=cond+row.id;
    				cond=",";
    			}
    			window.location = 'delete.htm?idList='+idList
    			+'&startDate='+'<c:out value="${startDate}"/>'
				+'&endDate='+'<c:out value="${endDate}"/>'
				+'&region='+'<c:out value="${region}"/>';
				
    		}
		  }
		return false;
    }
	// export data
	else{
		window.open('${pageContext.request.contextPath}/alarm/al-work-calendar/exportData.htm?startDate='+"<c:out value='${startDate}'/>"+
	        	'&endDate='+"<c:out value='${endDate}'/>"+
	        	 '&shift='+"<c:out value='${shift}'/>"+
	        	 '&deptCode='+"<c:out value='${deptCode}'/>"+
	        	 '&email='+"<c:out value='${email}'/>"+
	        	 '&region='+"<c:out value='${region}'/>"+
	        	 '&strWhere='+$("#strWhere").val()+
	        	 '&sortfield='+$("#sortfield").val()+
	        	 '&sortorder='+$("#sortorder").val()
	        	 ,'_blank');
	}
});


function focusIt()
{
  var mytext = document.getElementById("startDate");
  mytext.focus();
}
onload = focusIt;
</script>
<script type="text/javascript">
$('#confirm').click(function () {
	var r=confirm('Bạn có chắc chắn muốn xác nhận đổi ca trực không?');
	if (r==true)
	{
		var startDate = $("#startDate").val();
		var endDate = $("#endDate").val();
		var shift = $("#shift").val();
		var deptCode = $("#deptCode").val();
		var email = $("#email").val();
		var region = "<c:out value='${region}'/>";
		$.getJSON("${pageContext.request.contextPath}/alarm/al-work-calendar/confirm.htm",{startDate:startDate,endDate:endDate,shift:shift,deptCode:deptCode,email:email,region:region}, function(j){
			
			window.location = '${pageContext.request.contextPath}/alarm/al-work-calendar/list.htm?startDate='+startDate+'&endDate='+endDate+'&shift='+shift+'&deptCode='+deptCode+'&email='+email+'&region='+region;
			
	 	});
	}
	return false;

});
	
	
</script>
