<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<style type="text/css">
    
	#doublescroll { overflow: auto; overflow-y: hidden; }   
	#doublescroll p { margin: 0; padding: 1em; white-space: nowrap; }
    
     #dvtTeamProcess {
     visibility: visible !important;
    }
   
    #acLow {
     visibility: visible !important;
    }
    
     #bscid_chzn {

		width: 220px !important;
	}
    .ui-autocomplete {
		max-height: 200px;
		overflow-y: auto;
		/* prevent horizontal scrollbar */
		overflow-x: hidden;
	}
	/* IE 6 doesn't support max-height
	* we use height instead, but this forces the menu to always be this tall
	*/
	* html .ui-autocomplete {
		height: 200px;
	}
</style> 

<ul class="ui-tabs-nav">
<c:choose>
	<c:when test="${csiType == 'AAA'}">
		<title><fmt:message key="alarmCsi.titleAAA"/></title>
		<content tag="heading"><fmt:message key="alarmCsi.titleAAA"/></content>
		<li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/log/csi/list.htm?csiType=AAA"><span>AAA</span></a></li>
		<li class=""><a href="${pageContext.request.contextPath}/log/csi/list.htm?csiType=CMD"><span>CMD</span></a></li>
	</c:when>
 	<c:when test="${csiType == 'CMD'}">
 		<title><fmt:message key="alarmCsi.titleCMD"/></title>
		<content tag="heading"><fmt:message key="alarmCsi.titleCMD"/></content>
		<li class=""><a href="${pageContext.request.contextPath}/log/csi/list.htm?csiType=AAA"><span>AAA</span></a></li>
		<li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/log/csi/list.htm?csiType=CMD"><span>CMD</span></a></li>
	</c:when>
 	<c:otherwise></c:otherwise>
</c:choose>
</ul>
<br>

<form:form commandName="filter" method="post" action="list.htm?csiType=${csiType}">
<input type="hidden" name="reloadStr" id="reloadStr" value="${reloadStr}">
	<table style="width:100%" class="form">
		<tr>
			<td style="width:80px;">Từ ngày</td>
        	<td style="width:200px;"><form:input path="sdate" maxlength="16"/>
            	<img alt="calendar" title="Click to choose the start date" id="choosesDate" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
            	<form:errors path="sdate" cssClass="error"></form:errors></td>
 
        	<td style="width:70px;">Đến ngày</td>
        	<td style="width:200px;"><form:input path="edate" maxlength="16"/>
            	<img alt="calendar" title="Click to choose the end date" id="chooseeDate" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
            	<form:errors path="edate" cssClass="error"></form:errors></td>
            
            <td style="width:70px;"><fmt:message key="alarmCsi.csiName"/></td>
        	<td> <form:input path="csiName" style="width:97%;"/> </td>
        		
            <td style="width:70px;"><fmt:message key="alarmCsi.status"/></td>
        	<td style="width:100px;"><form:input path = "status"/></td>            
       </tr>
       <tr>
       		<td><fmt:message key="alarmCsi.alarmInfo"/></td>
        	<td colspan="5"><form:input path = "alarmInfo" style="width:99%;"/></td>
        	
        	<td >Refresh  	 
		            <c:choose>
						<c:when test="${reload != null}">
							<input id="reload" name="reload" type="checkbox" checked="checked">
						</c:when>
						<c:otherwise>
							<input id="reload" name="reload" type="checkbox" >
						</c:otherwise>
					</c:choose>
			</td>
			<td>
				<div id="refresh">Sau <input id="timeLoad" name="timeLoad" value="${timeLoad}" type="text"  style="width:35px;text-align:center">giây</div>
			</td>
            <td><input class="button" type="submit" class="button" id="submit" value="<fmt:message key="button.search"/>"/>
            </td>
       </tr>
	</table>
</form:form>
<div id="doublescroll">
<display:table name="${alarmLogList}" id="item" class="simple2" export="true" requestURI="" pagesize="100" sort="external" defaultsort="1">
	<display:column class="centerColumnMana" titleKey="global.list.STT" style="width:20px;"> <c:out value="${item_rowNum}"/> </display:column> 
    <display:column property="day" titleKey="alarmCsi.day" format="{0,date,dd/MM/yyyy HH:mm:ss}" style="width: 135px;" sortable="true" sortName="DAY"/>    
    <display:column property="csiName" titleKey="alarmCsi.csiName" style="width: 140px;" sortable="true" sortName="CSI_NAME"/>    
    <display:column property="alarmId" titleKey="alarmCsi.alarmId" style="width: 40px;" sortable="true" sortName="ALARM_ID"/>
    <display:column property="status" titleKey="alarmCsi.status" style="width: 90px;"sortable="true" sortName="STATUS"/> 
    <display:column property="alarmInfo" titleKey="alarmCsi.alarmInfo" sortable="true" sortName="ALARM_INFO"/>         
    
    <display:setProperty name="export.csv.filename" value="CSILog.csv"/>
	<display:setProperty name="export.excel.filename" value="CSILog.xls"/>
	<display:setProperty name="export.xml.filename" value="CSILog.xml"/>
</display:table>
</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/calendar.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/calendar_en.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/calendar_setup.js"></script>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/calendar-blue.css" />

<script type="text/javascript">
	function focusIt()
	{
	  var mytext = document.getElementById("sDate");
	  mytext.focus();
	}
	onload = focusIt;
	</script>

<script type="text/javascript">
$(document).ready(function(){
	var reload = $('#reload').val();
	
	if (reload=='Y') {
		setTimeout(function(){ 
			$('#filter').submit();
		}, 
		10000);
		$('#reloadStr').val('Y');
		
	}
});

$("#reload").change( function() {
	if ($(this).is(':checked')) {
		$('#time').css("display","block");
		$('#reloadStr').val('Y');
		
	} else {
		$('#time').css("display","none");
		$('#reloadStr').val('N');
		/* var timeout = setTimeout(function(){
			$('#filter').submit();
		}, 
		10000);
		clearTimeout(timeout); */
	}
});
//reload
	if ($('#reload').is(':checked')) {
		$('#time').css("display","block");
		var timeLoad = $('#timeLoad').val();
		$('#reloadStr').val('Y');
		setTimeout(function(){
			$('#filter').submit();
		}, 
		timeLoad * 1000);
	} else {
		$('#time').css("display","none");
		$('#reloadStr').val('N');
		/* var timeout = setTimeout(function(){
			$('#filter').submit();
		}, 
		10000);
		clearTimeout(timeout); */
		
	}
</script>

<script type="text/javascript">
	
    Calendar.setup({
        inputField		:	"sdate",	// id of the input field
        ifFormat		:	"%d/%m/%Y %H:%M",   	// format of the input field
        button			:   "choosesDate",  	// trigger for the calendar (button ID)
        showsTime		:	true,
        singleClick		:   false					// double-click mode
    });

    Calendar.setup({
        inputField		:	"edate",	// id of the input field
        ifFormat		:	"%d/%m/%Y %H:%M",   	// format of the input field
        button			:   "chooseeDate",   	// trigger for the calendar (button ID)
        showsTime		:	true,
        singleClick		:   false					// double-click mode
    });
</script>
