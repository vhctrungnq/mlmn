<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
  
<style>
	.ui-multiselect {
		width:160px !important;
	}
	 #teamProcess {
     visibility: visible !important;
    }
</style>   
<title>${titleF}</title>
<content tag="heading">${titleF}</content>
<form:form commandName="alDyRpFlutterPower" name="checkform" id="myform" method="post" action="form.htm"> 
	<form:hidden path="id"/>
	<table class="simple2"> 
      <tr>
           <td style="width:120px;"><fmt:message key="vAlDyRpErrorPower.bscid"/><font color="red">(*)</font></td>
           <td style="width:200px;">
           		<form:input type ="text" path="bscid" maxlength="18" style="width:160px;" rows="3"/>
           		<font color="red"><form:errors path="bscid"/></font>
           </td>
           <td style="width:120px;"><fmt:message key="vAlDyRpErrorPower.site"/><font color="red">(*)</font></td>
           <td>
           		<form:input type ="text" path="site" maxlength="18" style="width:160px;" rows="3"/>
           		<font color="red"><form:errors path="site"/></font>
           </td>
      </tr> 
      <tr>
           <td><fmt:message key="vAlDyRpErrorPower.day"/><font color="red">(*)</font></td>
           <td colspan="3">
           		<input type ="text"  value="${day}" name="day" id="day" size="20" maxlength="10" width="80px">
	   			 <img alt="calendar" title="Click to choose the start date" id="chooseday" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
	    		<font color="red">${errorday}<form:errors path="day"/></font>
	    	</td>
      </tr> 
  
		<tr>
           <td><fmt:message key="vAlDyRpErrorPower.teamProcess"/></td>
           <td>
           <select name="teamProcess" id="teamProcess" style="width: 160px;height:20px; padding-top: 4px;">
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
     
           <td><fmt:message key="vAlDyRpErrorPower.userProcess"/></td>
           <td style="border:0;">
           		<select name="userProcess" id="userProcess" multiple="multiple" style="width:160px;"></select>
           </td>
       </tr>
       <tr>
           <td><fmt:message key="vAlDyRpErrorPower.causebyContent"/></td>
           <td colspan="3">
           	 <form:input type ="text" path="causebyContent" maxlength="250" style="width:100%;" rows="3" name="causebyContent" id="causebyContent"/>
         </td>
       </tr>
        <tr>
           <td><fmt:message key="vAlDyRpErrorPower.resultContent"/></td>
           <td colspan="3"> 
           <textarea style="font-family:tahoma;width:100%; font-size:12px;height:30px;" rows="12"  name="resultContent" id="resultContent" maxlength="900" >${alDyRpFlutterPower.resultContent}</textarea>	
          </td>
       </tr>
     
       <tr>
           <td></td>
           <td colspan="3">
               <input type="submit" class="button" value="<fmt:message key="button.save"/>" />
           	   <input type="button" class="button" value="<fmt:message key="button.cancel"/>" onClick='window.location="list.htm"'>
					
           </td>
       </tr>
    </table>
<%-- <div><span style="color: blue; padding-top: 10px;"><fmt:message key="vAlAlarmLog.listSame"/></span></div>
<div id="doublescroll">
	<display:table name="${alarmSameList}" class="simple2" id="item" requestURI="" pagesize="15" sort="external" defaultsort="1">
	  	<display:column class="centerColumnMana" titleKey="global.list.STT"> <c:out value="${item_rowNum}"/> </display:column>
		<display:column property="alarmName" titleKey="rAlarmLog.alarmName" sortable="true" sortName="ALARM_NAME"/>
	  	<display:column property="bscid"  titleKey="WarningDipSame.bscid" sortable="true" sortName="BSCID"/>
		<display:column property="device" titleKey="WarningDipSame.device" sortable="true" sortName="DEVICE"/>
		<display:column property="alarmType"  titleKey="rAlarmLog.alarmType" sortable="true" sortName="ALARM_TYPE" style="max-width: 150px;word-wrap: break-word;"/>
		 <display:column property="day"  titleKey="WarningDipSame.day" format="{0,date,dd/MM/yyyy}" sortable="true" sortName="DAY" /> 
		<display:column property="causeby" titleKey="WarningDipSame.causeby" sortable="true" sortName="CAUSEBY" class="CAUSEBY"/>
		<display:column property="actionProcess" titleKey="WarningDipSame.actionProcess" sortable="true" sortName="ACTION_PROCESS"/>
	</display:table>
</div> --%>
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

<script type="text/javascript" src="${pageContext.request.contextPath}/js/selectStyle/jquery.multiselect.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/selectStyle/jquery.multiselect.filter.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/selectStyle/assets/prettify.js"></script>
<script type="text/javascript">
    Calendar.setup({
        inputField		:	"day",	// id of the input field
        ifFormat		:	"%d/%m/%Y",   	// format of the input field
        button			:   "chooseday",  	// trigger for the calendar (button ID)
        showsTime		:	true,
        singleClick		:   false					// double-click mode
    });
  
</script>
<script type="text/javascript">

function focusIt()
{
	if(document.checkform.bscid.value==""){
	  var mytext = document.getElementById("bscid");
	  mytext.focus();
	}
}

onload = focusIt;

</script>

<script type="text/javascript">
	$(function(){
		var teamProcess = $("#teamProcess").val();
		var userProcess = '<c:out value="${alDyRpFlutterPower.userProcess}"/>';
		var $widget;
		
		if (teamProcess != null && teamProcess != '') {

			// Tao multiselect jquery
			$widget = $("#userProcess").multiselect(), state = true;
			$widget.multiselect('enable');
			
			// Khoi tao userProcess
			$.getJSON("${pageContext.request.contextPath}/ajax/getUserByDeparment.htm",{dept: teamProcess,team:''}, function(j){
				var options = '';
				for (var i = 0; i < j.length; i++) {
					options += '<option value="' + j[i].username + '">' + j[i].username+ '</option>';
				}
				$("#userProcess").html(options);
		
				var userProcessVar = userProcess.split(",");
				if (userProcessVar != null) {
					for (var i=0; i<userProcessVar.length; i++) {
						$("#userProcess option[value='" + userProcessVar[i] + "']").attr('selected', 'selected');
					}
				}
				
				$("#userProcess").multiselect('refresh');
			});

			
		} else {
			$widget = $("#userProcess").multiselect(), state = true;
			$widget.multiselect('disable');
		}
		
		$("#teamProcess").change(function(){
			if ($("#teamProcess").val() == '' || $("#teamProcess").val() == null) {
				$widget.multiselect('disable');
			} else {

				/* $("#ngglIdError").text(''); */
				
				$widget.multiselect('enable');

				$.getJSON("${pageContext.request.contextPath}/ajax/getUserByDeparment.htm",{dept: $(this).val(),team:''}, function(j){
					var options = '';
					for (var i = 0; i < j.length; i++) {
						options += '<option value="' + j[i].username + '">' + j[i].username+ '</option>';
					}
					$("#userProcess").html(options);
					$("#userProcess").multiselect('refresh');
				});

			}
		});
	});
</script> 