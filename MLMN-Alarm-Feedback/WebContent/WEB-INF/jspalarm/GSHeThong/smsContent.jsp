<%@ include file="/commons/taglibs.jsp"   %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<title>${title}</title>
<content tag="heading">${title}</content> 	
<style>
.ui-tabs-nav .ui_link a:hover{
color: black;
}
</style>
<div>
	<div align="left">
		<ul class="ui-tabs-nav">
			<c:choose>
				<c:when test="${function == 'total'}">
					<li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/sendSMS/total.htm"><span><fmt:message key="title.tabControls.tongHop"/></span></a></li>
					<li class=""><a href="${pageContext.request.contextPath}/sendSMS/detail.htm"><span><fmt:message key="title.tabControls.chiTiet"/></span></a></li>
				</c:when>
			 	<c:otherwise>
			 		<li class=""><a href="${pageContext.request.contextPath}/sendSMS/total.htm"><span><fmt:message key="title.tabControls.tongHop"/></span></a></li>
					<li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/sendSMS/detail.htm"><span><fmt:message key="title.tabControls.chiTiet"/></span></a></li>
				</c:otherwise>
			</c:choose>
		</ul>
		
	</div>
</div>
<div class="clear"></div>
<div class="ui-tabs-panel">
<form:form commandName="filter" method="post" action="${function}.htm">
	<table>	
		<tr> 
			<td style="width:120px"><fmt:message key="alarmLog.sdateFC"/></td>
			<td style="width:150px">
				<input type ="text"  value="${sdate}" name="sdate" id="sdate" size="17" maxlength="16" style="width:100px">
		   		<img alt="calendar" title="Click to choose the from date" id="chooseSDate" style="cursor: pointer;position: absolute;" src="${pageContext.request.contextPath}/images/calendar.png"/>	
			</td>
			<td style="padding-left: 5px;width:50px"><fmt:message key="alarmLog.sdateT"/></td>
			<td style="width:150px">
				<input type ="text"  value="${edate}" name="edate" id="edate" size="17" maxlength="16" style="width:100px">
		   		<img alt="calendar" title="Click to choose the to date" id="chooseEDate" style="cursor: pointer; position: absolute;" src="${pageContext.request.contextPath}/images/calendar.png"/>	
			</td>
			<c:if test="${function != 'total'}">
				<td style="padding-left: 5px;width:100px"><fmt:message key="alarmLog.alarmType"/></td>
				<td >
					<input type="text" id="alarmType" name="alarmType" style="width: 100px;"/>
				</td>
				<td style="padding-left: 5px;width:100px"><fmt:message key="smsContent.username"/></td>
				<td >
					<input type="text" id="username" name="username" style="width: 100px;"/>
				</td >
				<td style="padding-left: 5px;width:70px"><fmt:message key="smsContent.isdn"/></td>
				<td >
					<input type="text" id="isdn" name="isdn" style="width: 100px;"/>
				</td>
			</c:if>
			<td>
				<input class="button" type="submit" id="btFilter" name="btFilter" value="<fmt:message key="global.form.timkiem"/>" />
			</td>
		</tr>
	</table>
</form:form>
</div>
<div class="clear"></div>
<div style="float: right;" id="listSMS"></div>
<div id="gridSMS"></div>
<div id='Menu'>
            <ul>
            	<%-- <c:if test="${function != 'total'}">
	            	<li><fmt:message key="global.button.editSelected"/></li>
		            <li><fmt:message key="global.button.deleteMultiSelected"/></li>
				</c:if> --%>
		   		<li><fmt:message key="global.button.exportExcel"/></li>
	        </ul>
</div>

<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar_en.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar_setup.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/chosen.jquery.js" ></script>

<link rel="stylesheet" type="text/css" media="all" href="${pageContext.request.contextPath}/styles/calendar-blue.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/chosen.css"/>
<script type="text/javascript">
Calendar.setup({
    inputField		:	"sdate",	// id of the input field
    ifFormat		:	"%d/%m/%Y %H:%M",   	// format of the input field
    button			:   "chooseSDate",  	// trigger for the calendar (button ID)
    showsTime		:	true,
    singleClick		:   false					// double-click mode
}); 

Calendar.setup({
    inputField		:	"edate",	// id of the input field
    ifFormat		:	"%d/%m/%Y %H:%M",   	// format of the input field
    button			:   "chooseEDate",   	// trigger for the calendar (button ID)
    showsTime		:	true,
    singleClick		:   false					// double-click mode
}); 	
</script>
<script type="text/javascript">
${gridSMS}
var theme =  getTheme();
$('#btFilter').jqxButton({ theme: theme });
// Create a input alarmType
$("#alarmType").jqxInput({placeHolder: "Enter alarmType", height: 20, width: 150, minLength: 0, maxLength: 200, theme: theme});
var alarmType =  "<c:out value='${alarmType}'/>";
if(alarmType=="")
	$('#alarmType').val('');
else
	$('#alarmType').val(alarmType);
//input username
$("#username").jqxInput({placeHolder: "Enter username", height: 20, width: 150, minLength: 0, maxLength: 200, theme: theme});
var username =  "<c:out value='${username}'/>";
if(username=="")
	$('#username').val('');
else
	$('#username').val(username);
//input isdn
$("#isdn").jqxInput({placeHolder: "Enter phone", height: 20, width: 150, minLength: 0, maxLength: 200, theme: theme});
var isdn =  "<c:out value='${isdn}'/>";
if(isdn=="")
	$('#isdn').val('');
else
	$('#isdn').val(isdn);	
// handle context menu clicks.
$("#Menu").on('itemclick', function (event) {
    var args = event.args;
  
    var exportFileName =  "<c:out value='${exportFileName}'/>";
	if ($.trim($(args).text()) == '<fmt:message key="global.button.exportExcel"/>')  {
    	$("#gridSMS").jqxGrid('exportdata', 'xls', exportFileName);
    }
});
  
//call view detail    
$("#gridSMS").on('cellselect', function (event) 
	  		{
	   			
	    		//alert(type+'--'+funtion);
	  		    var columnheader = $("#gridSMS").jqxGrid('getcolumn', event.args.datafield).text; 
	  		  	if (columnheader=='Quantity')
	  		    {
	  		    	var rowindex = event.args.rowindex;
	  		    	var row = $("#gridSMS").jqxGrid('getrowdata', rowindex);
	  		    	var day = new Date(row.day);
	  		    	var hour = row.hour;
	  		    	var isdn= '';
	  		    	var username= '';
	  		    	var alarmType='';
	  		    	if(row.username!=null)
  		    		{
	  		    		username=row.username;
  		    		}
  		    		if(row.isdn!=null)
  		    		{
  		    			isdn=row.isdn;
  		    		}
  		    		if(row.alarmType!=null)
  		    		{
  		    			alarmType=row.alarmType;
  		    		}
  		    		
	  		    	var date ='';
	  		    	if (day.getDate()<=9)
  		    		{
  		    		 date +='0';
  		    		}
	  		    	date+=day.getDate() + '/';
	  		    	if (day.getMonth()+1<=9)
  		    		{
  		    		 date +='0';
  		    		}
	  		    	date+=(day.getMonth()+1) + '/' +day.getFullYear();
	  		    	date+=' '+hour;
	  		    	//alert(date);
	  		    	 window.open('${pageContext.request.contextPath}/sendSMS/detail.htm?sdate='+date+':00'+
									'&edate='+date+':59'+
										'&username='+username+
										'&isdn='+isdn+
										'&alarmType='+alarmType,'_blank');
	  		    }
	  		    
	  		});
  

</script>