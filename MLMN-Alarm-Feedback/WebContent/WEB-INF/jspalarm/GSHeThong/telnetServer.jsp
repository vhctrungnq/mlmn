<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<style type="text/css">
    .textrt{
    	text-align: right;
    }
    
    .textct {
    	text-align: center;
    }
    	.ui-multiselect {
		width:255 !important;
	}
	#caTK {
     visibility: visible !important;
    }
    
  .red
	{
		background-color : red;
		width:100%; 
		height:100%
	}
    
</style>


<title>${title}</title>
<content tag="heading">${title}</content>
<div>
<form:form commandName="telnet" method="post" id ="filter" name="filter" action="list.htm"> 	
<input type="hidden" name="reloadStr" id="reloadStr" value="${reloadStr}">
<table>
	<tr>

		<td style="width:40px; width:70px;">
			<fmt:message key="telnet.ngayTK"/>
		</td>
		<td>
			<input type="text" value="${day}" name="day" id="day" size="10" maxlength="10">
			<img alt="calendar" title="Click to choose the start date" id="choosengayTK" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
						
		</td>
		<td style="width:70px;padding-left: 10px;">
			<fmt:message key="telnet.hourFrom"/>
		</td>
		<td>
			<input type="text" id="hourFrom" name="hourFrom" style="width: 100px;"/>
		</td>
		<td style="width:50px;padding-left: 10px;">
			<fmt:message key="telnet.hourTo"/>
		</td>
		<td>
			<input type="text" id="hourTo" name="hourTo" style="width: 100px;"/>
		</td>
		<td style="padding-left: 5px;" >
					<div style="width:90px;float:left;">
						Refresh <c:choose>
								<c:when test="${reload != null}">
									<input id="reload" name="reload" type="checkbox" checked="checked">
								</c:when>
								<c:otherwise>
									<input id="reload" name="reload" type="checkbox" >
								</c:otherwise>
							</c:choose>
					</div>
					<%-- <div  id="time" style="padding-left: 8px">
					Sau <input id="timeLoad" name="timeLoad" value="${timeLoad}"  maxlength="6"  type="text" style="width:35px;text-align:center"> giây
					</div>	 --%>
				</td>
		<td>
				<input class="button" type="submit" id="btFilter" name="btFilter" value="<fmt:message key="global.form.timkiem"/>" />
		</td>

	</tr>
</table>
</form:form>
</div>
<!-- -------------------------------------------------------------------------
------------------------------------------------------------------------- -->	

<table style="width:100%">
	<tr>
		<td align="left"><b><fmt:message key="telnet.detail"/></b></td>
		<td style="width: 30px"><div style="float: right;" id="jqxlistboxdetail"></div></td>
	</tr>
</table>
<div id="griddetail"></div>
<div id='menudetail'>
            <ul>
				<li><fmt:message key="global.button.exportExcel"/></li>
	        </ul>
 </div>
	<br/>
<!-- -------------------------------------------------------------------------
------------------------------------------------------------------------- -->
<table style="width:100%">
	<tr>
		<td align="left"><b><fmt:message key="telnet.qualityAlarm"/></b></td>
		<!-- <td style="width: 30px"><div style="float: right;" id="jqxlistboxAlarm"></div> -->
		</td>
	</tr>
</table>
<div id="gridAlarm"></div>	
<div id='menuAlarm'>
            <ul>
				<li><fmt:message key="global.button.exportExcel"/></li>
	        </ul>
 </div>
	<br/>
<!-- -------------------------------------------------------------------------
------------------------------------------------------------------------- -->	
<table style="width:100%">
	<tr>
		<td align="left"><b><fmt:message key="telnet.qualityKPI"/></b></td>
		<!-- <td style="width: 30px"><div style="float: right;" id="jqxlistboxKPI"></div></td> -->
	</tr>
</table>
<div id="gridKPI"></div>	
<div id='menuKPI'>
            <ul>
				<li><fmt:message key="global.button.exportExcel"/></li>
	        </ul>
 </div>	
<br/>
<!-- -------------------------------------------------------------------------
------------------------------------------------------------------------- -->	
<table style="width:100%">
	<tr>
		<td align="left"><b><fmt:message key="telnet.qualityInvent"/></b></td>
		<!-- <td style="width: 30px"><div style="float: right;" id="jqxlistboxInvent"></div></td> -->
	</tr>
</table>
<div id="gridInvent"></div>
<div id='menuInvent'>
            <ul>
				<li><fmt:message key="global.button.exportExcel"/></li>
	        </ul>
 </div>		
	<br/>

<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar_en.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar_setup.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/chosen.jquery.js" ></script>

<link rel="stylesheet" type="text/css" media="all" href="${pageContext.request.contextPath}/styles/calendar-blue.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/chosen.css"/>


<script type="text/javascript">
${griddetail}
${gridAlarm}
${gridKPI}
${gridInvent}
$("#reload").change( function() {
	if ($(this).is(':checked')) {
		$('#time').css("display","block");
		$('#reloadStr').val('Y');
	} else {
		$('#time').css("display","none");
		$('#reloadStr').val('N');
	}
});
//reload
	if ($('#reload').is(':checked')) {
		//$('#time').css("display","block");
		$('#reloadStr').val('Y');
		var timeLoad = 180;
		
		setTimeout(function(){
			$('#filter').submit();
		}, 
		timeLoad * 1000);
	} else {
		//$('#time').css("display","none");
		$('#reloadStr').val('N');
	}
	//refresh 
	$(document).ready(function(){
		var reload = $("#reload").val();
		if (reload=='Y') {
			setTimeout(function(){
				$('#filter').submit();
			}, 
			10000);
			$('#reloadStr').val('Y');
		}
		var theme =  getTheme();
		$('#btFilter').jqxButton({ theme: theme });
		//Create a input hour from
		$("#hourFrom").jqxInput({placeHolder: "Enter hour from", height: 20, width: 80, minLength: 0, maxLength: 200, theme: theme});
		var hourFrom =  "<c:out value='${hourFrom}'/>";
		if(hourFrom=="")
			$('#hourFrom').val('');
		else
			$('#hourFrom').val(hourFrom);
		//input hour to
		$("#hourTo").jqxInput({placeHolder: "Enter hour to", height: 20, width: 80, minLength: 0, maxLength: 200, theme: theme});
		var hourTo =  "<c:out value='${hourTo}'/>";
		if(hourTo=="")
			$('#hourTo').val('');
		else
			$('#hourTo').val(hourTo);
	});
// handle context menu detail
$("#menudetail").on('itemclick', function (event) {
	    var args = event.args;
	    var exportFileName =  "<c:out value='${exportFileNamedetail}'/>";
	    if ($.trim($(args).text()) == '<fmt:message key="global.button.exportExcel"/>')  {
	    	$("#griddetail").jqxGrid('exportdata', 'xls', exportFileName);
	    }
});

// handle context menu Alarm
$("#menuAlarm").on('itemclick', function (event) {
	    var args = event.args;
	    var exportFileName =  "<c:out value='${exportFileNameAlarm}'/>";
	    if ($.trim($(args).text()) == '<fmt:message key="global.button.exportExcel"/>')  {
	    	$("#gridAlarm").jqxGrid('exportdata', 'xls', exportFileName);
	    }
});

// handle context menu Alarm
$("#menuKPI").on('itemclick', function (event) {
	    var args = event.args;
	    var exportFileName =  "<c:out value='${exportFileNameKPI}'/>";
	    if ($.trim($(args).text()) == '<fmt:message key="global.button.exportExcel"/>')  {
	    	$("#gridKPI").jqxGrid('exportdata', 'xls', exportFileName);
	    }
});

// handle context menu Alarm
$("#menuInvent").on('itemclick', function (event) {
	    var args = event.args;
	    var exportFileName =  "<c:out value='${exportFileNameInvent}'/>";
	    if ($.trim($(args).text()) == '<fmt:message key="global.button.exportExcel"/>')  {
	    	$("#gridInvent").jqxGrid('exportdata', 'xls', exportFileName);
	    }
});


</script>
<script type="text/javascript">
Calendar.setup({
    inputField		:	"day",	// id of the input field
    ifFormat		:	"%d/%m/%Y",   	// format of the inputfield
    button			:   "choosengayTK",  	// trigger for the calendar (button ID)
    showsTime		:	true,
    singleClick		:   false					// double-click mode
});
</script>