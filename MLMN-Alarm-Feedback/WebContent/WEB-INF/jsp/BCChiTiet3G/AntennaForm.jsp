<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<title>${titleF}</title>
<content tag="heading">${titleF}</content>
<style>
	.ui-multiselect {
		width:160px !important;
	}
	 #dvtTeamProcess {
     visibility: visible !important;
    }
</style>
<form:form commandName="vAlRbLossAntenna" name="checkform" id="myform" method="post" action="antenna_form.htm"> 
	<form:hidden path="id"/>
	<form:hidden path="fmAlarmId"/>
	<table class="simple2"> 
      <tr>
           <td style="width: 120px;"><fmt:message key="vAlRbLossAntenna.rncid"/><font color="red">(*)</font></td>
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
		   		<font color="red"><form:errors path="rncid"/></font>
           </td>
     
           <td style="width: 120px;"><fmt:message key="vAlRbLossAntenna.alarmLevel"/><font color="red">(*)</font></td>
           <td>
           		<form:input type ="text" path="alarmLevel" maxlength="18" style="width:160px;" rows="3"/>
         		<font color="red"><form:errors path="alarmLevel"/></font>
           </td>
      </tr> 
      <tr>
           <td ><fmt:message key="vAlRbLossAntenna.antenError"/></td>
           <td>
           		<form:input type ="text" path="antenError" maxlength="18" style="width:160px;" rows="3"/>
           </td>
  
           <td><fmt:message key="vAlRbLossAntenna.sector"/></td>
           <td>
           		<form:input type ="text" path="sector" maxlength="18" style="width:160px;" rows="3"/>
           </td>
      </tr> 
      <tr>
           <td width="200"><fmt:message key="vAlRbLossAntenna.sdate"/><font color="red">(*)</font></td>
           <td>
           		<input type ="text"  value="${sdate}" name="sdate" id="sdate" size="20" maxlength="16" width="80px">
	   			 <img alt="calendar" title="Click to choose the start date" id="choosesdate" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
	    		<font color="red">${errorsdate}<form:errors path="sdate"/></font>
	    	</td>
      <td><fmt:message key="vAlRbLossAntenna.edate"/></td>
           <td>
           		<input type ="text"  value="${edate}" name="edate" id="edate" size="20" maxlength="16" width="80px">
	   			 <img alt="calendar" title="Click to choose the start date" id="chooseedate" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
	    		<font color="red">${erroredate}<form:errors path="edate"/></font>
	    	</td>
      </tr> 
 
		<tr>
           <td><fmt:message key="vAlRbLossAntenna.dvtTeamProcess"/></td>
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
       
           <td><fmt:message key="vAlRbLossAntenna.dvtUserProcess"/></td>
           <td style="border:0;">
           		<select name="dvtUserProcess" id="dvtUserProcess" multiple="multiple" style="width:160px;"></select>
           </td>
       </tr>
		<tr>
           <td><fmt:message key="vAlRbLossAntenna.causeby"/></td>
           <td colspan="3"> 
           	<form:input type ="text" path="causeby" maxlength="250" style="width:100%;" rows="3" name="causeby" id="causeby"/>
          	</td>
       </tr>
       <tr>
           <td><fmt:message key="vAlRbLossAntenna.actionProcess"/></td>
	        <td colspan="3"> 
	           	<form:textarea path="actionProcess" style="font-family:tahoma;width:100%; font-size:12px;height:30px;" rows="10" maxlength="900"/>
	           	
	        </td>
       </tr>
		<tr>
           <td><fmt:message key="vAlRbLossAntenna.description"/></td>
           <td colspan="3"> 
           	<form:textarea path="description" style="font-family:tahoma;width:100%; font-size:12px;height:30px;" rows="10" maxlength="900"/>
           </td>
       </tr>
     
       <tr>
           <td></td>
           <td colspan="3">
               <input type="submit" class="button" value="<fmt:message key="button.save"/>" />
           	   <input type="button" class="button" value="<fmt:message key="button.cancel"/>" onClick='window.location="antenna.htm"'>
					
           </td>
       </tr>
    </table>
<div><span style="color: blue; padding-top: 10px;"><fmt:message key="vAlAlarmLog.listSame"/></span></div>
<div id="doublescroll" style=" overflow-x: auto;overflow-y: hidden;">
		<display:table name="${alarmSameList}" class="simple2" id="item" requestURI="" pagesize="15" sort="external" defaultsort="1">
	  	<display:column class="centerColumnMana" titleKey="global.list.STT" style="width:30px;"> <c:out value="${item_rowNum}"/> </display:column>
		<%-- <display:column property="alarmName" titleKey="rAlarmLog.alarmName" sortable="true" sortName="ALARM_NAME"/>--%>
		<display:column property="rncid"  titleKey="vAlRbLossAntenna.rncid" sortable="true" sortName="RNCID"/>
		<display:column property="alarmLevel" titleKey="vAlAlarmLog.alarmLevel" sortable="true" sortName="ALARM_LEVEL"/>
		<display:column property="sdate"  titleKey="rAlarmLog.sdate" format="{0,date,dd/MM/yyyy HH:mm:ss}" sortable="true" sortName="SDATE" /> 
		<display:column property="edate"  titleKey="rAlarmLog.edate" format="{0,date,dd/MM/yyyy HH:mm:ss}" sortable="true" sortName="EDATE" />
		<display:column property="causeby" titleKey="rAlarmLog.causeby" sortable="true" sortName="CAUSEBY" class="CAUSEBY"/>
		<display:column property="actionProcess" titleKey="rAlarmLog.actionProcess" sortable="true" sortName="ACTION_PROCESS"/>
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
</script>
<script type="text/javascript">

	$('#item>tbody>tr').click(function() {  
		var value = $(this).find(".CAUSEBY").text();
	  $("#causeby").val(value);
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

</script>
<script type="text/javascript">
	$(function(){
		var dvtTeamProcess = $("#dvtTeamProcess").val();
		var dvtUserProcess = '<c:out value="${vAlRbLossAntenna.dvtUserProcess}"/>';
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