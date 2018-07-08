<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<title>${titleForm}</title>
<content tag="heading">${titleForm}</content>
<style>
	.ui-multiselect {
		width:160px !important;
	}
	 #dvtTeamProcess {
     visibility: visible !important;
    }
     #causebySystem {
     visibility: visible !important;
    }
</style>
<form:form commandName="vAlRbLossConfig3G" name="checkform" id="myform" method="post" action="cau_hinh_form.htm"> 
	<form:hidden path="id"/>
	<input type="hidden" name="alarmType" id="alarmType" value="${alarmType}">
	<input type="hidden" name="idShift" id="idShift" value="${idShift}">
	<input type="hidden" name="highlight" id="highlight" value="${highlight}">
	<table class="simple2"> 
      <tr>
           <td style="width: 120px;"><fmt:message key="vAlRbLossConfig3G.rncid"/><font color="red">(*)</font></td>
           <td style="width: 200px;">
           		<select name="rncid" id="rncid" style="width: 160px;height:20px; padding-top: 4px;">
	           		<c:forEach var="item" items="${rncList}">
						<c:choose>
			                <c:when test="${item == rncid}">
			                    <option value="${item}" selected="selected">${item}</option>
			                </c:when>
							<c:otherwise>
								<option value="${item}">${item}</option>
							</c:otherwise>
						</c:choose>
					</c:forEach>
				</select> 
		   		<font color="red"><form:errors path="rncid" /></font>
           </td>
           <td style="width: 80px;"><fmt:message key="vAlRbLossConfig3G.alarmLevel"/><font color="red">(*)</font></td>
           <td colspan="3">
           		<form:input type ="text" path="alarmLevel" maxlength="18" style="width:160px;" rows="3"/>
         		<font color="red"><form:errors path="alarmLevel"/></font>
           </td>
           </tr>
      <tr>
           <td><fmt:message key="vAlRbLossConfig3G.sdate"/><font color="red">(*)</font></td>
           <td>
           		<input type ="text"  value="${sdate}" name="sdate" id="sdate" size="20" maxlength="16" width="80px">
	   			 <img alt="calendar" title="Click to choose the start date" id="choosesdate" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
	    		<font color="red">${errorsdate}<form:errors path="sdate"/></font>
	  		</td>
           <td><fmt:message key="vAlRbLossConfig3G.edate"/></td>
           <td style="width: 180px;">
           		<input type ="text"  value="${edate}" name="edate" id="edate" size="20" maxlength="16" width="80px">
	   			 <img alt="calendar" title="Click to choose the start date" id="chooseedate" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
	    		<font color="red">${erroredate}<form:errors path="edate"/></font>
	    	</td>
	    	<td style="width: 50px;"><fmt:message key="vAlRbLossConfig.ddhBaoMch"/></td>
           <td>
           		<input type ="text"  value="${ddhBaoMch}" name="ddhBaoMch" id="ddhBaoMch" size="20" maxlength="16" width="80px">
	   			 <img alt="calendar" title="Click to choose the start date" id="chooseddhBaoMch" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
	   				<font color="red">${errorddhBaoMch}<form:errors path="ddhBaoMch"/></font>
	    </td>
      </tr> 
      <tr>
           <td><fmt:message key="vAlRbLossConfig.sdate"/></td>
           <td>
           		<input type ="text"  value="${causeSdate}" name="causeSdate" id="causeSdate" size="20" maxlength="16" width="80px">
	   			 <img alt="calendar" title="Click to choose the start date" id="choosecauseSdate" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
	    		<font color="red">${errorcauseSdate}<form:errors path="causeSdate"/></font>
	    		
	    	</td>
        <td><fmt:message key="vAlRbLossConfig.edate"/></td>
           <td colspan="3">
           		<input type ="text"  value="${causeEdate}" name="causeEdate" id="causeEdate" size="20" maxlength="16" width="80px">
	   			 <img alt="calendar" title="Click to choose the start date" id="choosecauseEdate" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
	    		<font color="red">${errorcauseEdate}<form:errors path="causeEdate"/></font>
	    	</td>
      
      </tr> 
		<tr>
           <td><fmt:message key="vAlRbLossConfig3G.dvtTeamProcess"/></td>
           <td>
           <select name="dvtTeamProcess" id="dvtTeamProcess" style="width: 160px;height:20px; padding-top: 4px;">
           		<c:forEach var="item" items="${teamList}">
					<c:choose>
		                <c:when test="${item.deptCode == team}">
		                    <option value="${item.deptCode}" selected="selected">${item.deptCode}</option>
		                </c:when>
						<c:otherwise>
							<option value="${item.deptCode}">${item.deptCode}</option>
						</c:otherwise>
					</c:choose>
				</c:forEach>
			</select> 
           </td>
      
           <td><fmt:message key="vAlRbLossConfig3G.dvtUserProcess"/></td>
           <td style="border:0;" colspan="3">
           		<select name="dvtUserProcess" id="dvtUserProcess" multiple="multiple" style="width:160px;"></select>
           </td>
       </tr>
       <tr>
           <td><fmt:message key="warning.causebySystem"/></td>
           <td colspan="5">
      			<select name="causebySystem" id="causebySystem" style="width: 295px;height:20px; padding-top: 4px;">
           		<option value=""><fmt:message key="global.Chosse"/></option>
           		<c:forEach var="item" items="${causebySyList}">
					<c:choose>
		                <c:when test="${item.name == causebySystem}">
		                    <option value="${item.name}" selected="selected">${item.value}</option>
		                </c:when>
						<c:otherwise>
							<option value="${item.name}">${item.value}</option>
						</c:otherwise>
					</c:choose>
				</c:forEach>
			</select> 
	    	</td>
	    </tr>
		<tr>
           <td><fmt:message key="vAlRbLossConfig3G.causeby"/></td>
           <td colspan="5"> 
           		<form:input type ="text" path="causeby" maxlength="250" style="width:100%;" rows="3" name="causeby" id="causeby"/>
          	</td>
       </tr>
       <tr>
           <td><fmt:message key="vAlRbLossConfig3G.actionProcess"/></td>
         <td colspan="5"> 
           	<form:textarea path="actionProcess" id="actionProcess" name="actionProcess" style="font-family:tahoma;width:100%; font-size:12px;height:30px;" rows="10" maxlength="900"/>
           	
        </td>
       </tr>
		<tr>
           <td><fmt:message key="vAlRbLossConfig3G.description"/></td>
           <td colspan="5"> 
           	<form:textarea path="description" style="font-family:tahoma;width:100%; font-size:12px;height:30px;" rows="10" maxlength="900"/>
           </td>
       </tr>
     
       <tr>
           <td></td>
           <td colspan="5">
               <input type="submit" class="button" value="<fmt:message key="button.save"/>" />
           	   <input type="button" class="button" value="<fmt:message key="button.cancel"/>" onClick='window.location="dsach_ch.htm?alarmType=${alarmType}"'>
					
           </td>
       </tr>
    </table>
<div><span style="color: blue; padding-top: 10px;"><fmt:message key="vAlAlarmLog.listSame"/></span></div>
<div id="doublescroll" style=" overflow-x: auto;overflow-y: hidden;">
	<display:table name="${alarmSameList}" class="simple2" id="item">
	  	<display:column class="centerColumnMana" titleKey="global.list.STT" style="width:30px;"> <c:out value="${item_rowNum}"/> </display:column>
		<display:column property="id" titleKey="rAlarmLog.id" class="dpnone ID" headerClass="dpnone" />
		<display:column property="shiftId" titleKey="rAlarmLog.shiftId" class="dpnone SHIFT_ID" headerClass="dpnone" />
		<display:column property="alarmName" titleKey="rAlarmLog.alarmName" />
	  	<display:column property="rncid"  titleKey="vAlAlarmLog.rncid"/>
		<display:column property="alarmLevel" titleKey="rAlarmLog.system"/>
		 <display:column property="sdate"  titleKey="rAlarmLog.sdate" format="{0,date,dd/MM/yyyy HH:mm:ss}"/> 
		<display:column property="edate"  titleKey="rAlarmLog.edate" format="{0,date,dd/MM/yyyy HH:mm:ss}"/>
		<display:column property="totalTime" titleKey="rAlarmLog.totalTime" />
		<display:column property="causeby" titleKey="rAlarmLog.causeby"  class="CAUSEBY"/>
		<display:column property="actionProcess" titleKey="rAlarmLog.actionProcess"  class="ACTION_PROCESS"/>
	<display:column property="alarmInfo" titleKey="rAlarmLog.alarmInfo" class="ALARM_INFO"/>
	
	</display:table>
</div>
</form:form>

<style>
    .error {
    	color: red;
    }
</style> 
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar_en.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar_setup.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/chosen.jquery.js" ></script>

<link rel="stylesheet" type="text/css" media="all" href="${pageContext.request.contextPath}/styles/calendar-blue.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/chosen.css"/>

<link type="text/css" rel="Stylesheet" href="${pageContext.request.contextPath}/js/jquery/jquery-ui-1.8.23.custom.css" />
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery/jquery-ui.min-1.8.9.js"></script>



<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/js/selectStyle/jquery.multiselect.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/js/selectStyle/jquery.multiselect.filter.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/js/selectStyle/assets/style.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/js/selectStyle/assets/prettify.css" />
<!-- <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/js/selectStyle/jquery-ui.css" />
<script type="text/javascript" src="${pageContext.request.contextPath}/js/selectStyle/jquery.js"></script>
<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jqueryui/1/jquery-ui.min.js"></script> -->
<script type="text/javascript" src="${pageContext.request.contextPath}/js/selectStyle/jquery.multiselect.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/selectStyle/jquery.multiselect.filter.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/selectStyle/assets/prettify.js"></script>
<script type="text/javascript">
    Calendar.setup({
        inputField		:	"sdate",	// id of the input field
        ifFormat		:	"%d/%m/%Y %H:%M",   	// format of the input field
        button			:   "choosesdate",  	// trigger for the calendar (button ID)
        showsTime		:	true,
        singleClick		:   false					// double-click mode
    });
    Calendar.setup({
        inputField		:	"edate",	// id of the input field
        ifFormat		:	"%d/%m/%Y %H:%M",   	// format of the input field
        button			:   "chooseedate",  	// trigger for the calendar (button ID)
        showsTime		:	true,
        singleClick		:   false					// double-click mode
    });
    Calendar.setup({
        inputField		:	"causeSdate",	// id of the input field
        ifFormat		:	"%d/%m/%Y %H:%M",   	// format of the input field
        button			:   "choosecauseSdate",  	// trigger for the calendar (button ID)
        showsTime		:	true,
        singleClick		:   false					// double-click mode
    });
    Calendar.setup({
        inputField		:	"causeEdate",	// id of the input field
        ifFormat		:	"%d/%m/%Y %H:%M",   	// format of the input field
        button			:   "choosecauseEdate",  	// trigger for the calendar (button ID)
        showsTime		:	true,
        singleClick		:   false					// double-click mode
    });

    Calendar.setup({
        inputField		:	"ddhBaoMch",	// id of the input field
        ifFormat		:	"%d/%m/%Y %H:%M",   	// format of the input field
        button			:   "chooseddhBaoMch",   	// trigger for the calendar (button ID)
        showsTime		:	true,
        singleClick		:   false					// double-click mode
    });
</script>
<script type="text/javascript">

function focusIt()
{
	if(document.checkform.rncid.value==""){
	  var mytext = document.getElementById("rncid");
	  mytext.focus();
	}
}

onload = focusIt;
$(document).ready(function(){
	$('#item>tbody>tr').each( function() { 
    	 var value = $(this).find(".SHIFT_ID").text();
    	 if( value!=null && value !=''){
    		 ${highlight}
	    	}
	 	});
});
</script>
<script type="text/javascript">
	$('#item>tbody>tr').click(function() {  
		var value1 = $(this).find(".CAUSEBY").text();
		var value2 = $(this).find(".ACTION_PROCESS").text();
	  $("#causeby").val(value1);
	  $("#actionProcess").val(value2);
	//to mau va  luu vao ca truc
		${highlight}
		var alarmT='3G';
		 $.getJSON("${pageContext.request.contextPath}/ajax/updateAlarmLog.htm",{id: $(this).find(".ID").text(),shiftId:$("#idShift").val(),alarmType:alarmT}, function(j){
			
		});
	});	
</script>
<script type="text/javascript">
	$(function(){
		var dvtTeamProcess = $("#dvtTeamProcess").val();
		var dvtUserProcess = '<c:out value="${vAlRbLossConfig3G.dvtUserProcess}"/>';
		var $widget;
		
		if (dvtTeamProcess != null && dvtTeamProcess != '') {

			// Tao multiselect jquery
			$widget = $("#dvtUserProcess").multiselect(), state = true;
			$widget.multiselect('enable');
			
			// Khoi tao dvtUserProcess
			$.getJSON("${pageContext.request.contextPath}/ajax/getUserByDeparment.htm",{dept: dvtTeamProcess,team:''}, function(j){
				var options = '';
				for (var i = 0; i < j.length; i++) {
					options += '<option value="' + j[i].username + '">' + j[i].username+ '</option>';
				}
				$("#dvtUserProcess").html(options);
		
				var dvtUserProcessVar = dvtUserProcess.split(",");
				if (dvtUserProcessVar != null) {
					for (var i=0; i<dvtUserProcessVar.length; i++) {
						$("#dvtUserProcess option[value='" + dvtUserProcessVar[i] + "']").attr('selected', 'selected');
					}
				}
				
				$("#dvtUserProcess").multiselect('refresh');
			});

			
		} else {
			$widget = $("#dvtUserProcess").multiselect(), state = true;
			$widget.multiselect('disable');
		}
		
		$("#dvtTeamProcess").change(function(){
			if ($("#dvtTeamProcess").val() == '' || $("#dvtTeamProcess").val() == null) {
				$widget.multiselect('disable');
			} else {

				$widget.multiselect('enable');

				$.getJSON("${pageContext.request.contextPath}/ajax/getUserByDeparment.htm",{dept: $(this).val(),team:''}, function(j){
					var options = '';
					for (var i = 0; i < j.length; i++) {
						options += '<option value="' + j[i].username + '">' + j[i].username+ '</option>';
					}
					$("#dvtUserProcess").html(options);
					$("#dvtUserProcess").multiselect('refresh');
				});

			}
		});
	});
</script> 