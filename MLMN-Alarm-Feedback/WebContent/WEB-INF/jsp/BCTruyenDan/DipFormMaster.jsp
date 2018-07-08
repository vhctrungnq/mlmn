<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
  <style>   
 	#doublescroll { overflow: auto; overflow-y: hidden; }  
    #doublescroll p { margin: 0; padding: 1em; white-space: nowrap; }
    .ui-multiselect {
		width:160px !important;
	}
	 #dvtTeamProcess {
     visibility: visible !important;
    }
    #ungCuuMpd {
     visibility: visible !important;
    }
    
</style>  
<title>${titleF}</title>
<content tag="heading">${titleF}</content>

<form:form commandName="vAlRbLossComunication" name="checkform" id="myform" method="post" action="formMaster.htm"> 
	<form:hidden path="id"/>
	<table class="simple2"> 
      <tr>
           <td style="width: 120px;"><fmt:message key="VAlRbLossComunication.bscid"/><font color="red">(*)</font></td>
           <td style="width: 200px;">
           		<form:input type ="text" path="bscid" maxlength="18" style="width:160px;" rows="3"/>
           		<font color="red"><form:errors path="bscid"/></font>
           </td>
      
           <td style="width: 80px;"><fmt:message key="VAlRbLossComunication.dip"/><font color="red">(*)</font></td>
           <td >
           		<form:input type ="text" path="dip" maxlength="18" style="width:160px;" rows="3"/>
           		<font color="red"><form:errors path="dip"/></font>
           </td>
      </tr> 
      <tr>
           <td><fmt:message key="VAlRbLossComunication.sdate"/><font color="red">(*)</font></td>
           <td>
           		<input type ="text"  value="${sdate}" name="sdate" id="sdate" size="20" maxlength="16" width="80px">
	   			 <img alt="calendar" title="Click to choose the start date" id="choosesdate" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
	    		<font color="red">${errorsdate}<form:errors path="sdate"/></font>
	    	</td>
      
           <td><fmt:message key="VAlRbLossComunication.edate"/></td>
           <td>
           		<input type ="text"  value="${edate}" name="edate" id="edate" size="20" maxlength="16" width="80px">
	   			<img alt="calendar" title="Click to choose the start date" id="chooseedate" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
	    		<font color="red">${erroredate}<form:errors path="edate"/></font>
	    	</td>
      </tr> 
		<tr>
           <td><fmt:message key="VAlRbLossComunication.teamProcess"/></td>
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
       
           <td ><fmt:message key="VAlRbLossComunication.userProcess"/></td>
            <td style="border:0;">
           		<select name="dvtUserProcess" id="dvtUserProcess" multiple="multiple" style="width:160px;"></select>
           </td>
       </tr>
       <tr>
			<td><fmt:message key="VAlRbLossComunication.ungCuuMpd"/></td>
			<td colspan="3">
				<select name="ungCuuMpd" id="ungCuuMpd" style="width: 160px;height:20px; padding-top: 4px;">
				<option value=""><fmt:message key="global.Chosse"/></option>
           		<c:forEach var="item" items="${ungCuuMpdList}">
					<c:choose>
		                <c:when test="${item.name == ungCuuMpd}">
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
           <td><fmt:message key="VAlRbLossComunication.causeby"/></td>
           <td colspan="3"> 
           			<form:input type ="text" path="causeby" maxlength="250" style="width:100%;" rows="3" name="causeby" id="causeby"/>
          	</td>
       </tr>
        <tr>
           <td><fmt:message key="VAlRbLossComunication.resultContent"/></td>
           <td colspan="3"> 
           			<form:textarea path="actionProcess" style="font-family:tahoma;width:100%; font-size:12px;height:30px;" rows="10" maxlength="250"/>
           </td>
       </tr>
       <tr>
           <td></td>
           <td colspan="3">
               <input type="submit" class="button" value="<fmt:message key="button.save"/>" />
           	   <input type="button" class="button" value="<fmt:message key="button.cancel"/>" onClick='window.location="danh_sach.htm"'>
					
           </td>
       </tr>
    </table>
<div><span style="color: blue; padding-top: 10px;"><fmt:message key="vAlAlarmLog.listSame"/></span></div>
<div id="doublescroll" >
		<display:table name="${alarmSameList}" class="simple2" id="item" requestURI="" pagesize="15" sort="external" defaultsort="1">
	  	<display:column class="centerColumnMana" titleKey="global.list.STT"> <c:out value="${item_rowNum}"/> </display:column>
		<display:column property="alarmName" titleKey="rAlarmLog.alarmName" class="ALARM_NAME"/>
	  	<display:column property="bscid"  titleKey="rAlarmLog.bscid" class="BSCID"/>
		<display:column property="system" titleKey="VAlRbLossComunication.dip" class="SYSTEM"/>
		<display:column property="sdate"  titleKey="rAlarmLog.sdate" format="{0,date,dd/MM/yyyy HH:mm:ss}" class="SDATE"/> 
		<display:column property="edate"  titleKey="rAlarmLog.edate" format="{0,date,dd/MM/yyyy HH:mm:ss}"  class="EDATE"/>
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

<script type="text/javascript" src="${pageContext.request.contextPath}/js/selectStyle/jquery.multiselect.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/selectStyle/jquery.multiselect.filter.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/selectStyle/assets/prettify.js"></script>

<script type="text/javascript">
    Calendar.setup({
        inputField		:	"sdate",	// id of the input field
        ifFormat		:	"%d/%m/%Y",   	// format of the input field
        button			:   "choosesdate",  	// trigger for the calendar (button ID)
        showsTime		:	true,
        singleClick		:   false					// double-click mode
    });
    Calendar.setup({
        inputField		:	"edate",	// id of the input field
        ifFormat		:	"%d/%m/%Y",   	// format of the input field
        button			:   "chooseedate",  	// trigger for the calendar (button ID)
        showsTime		:	true,
        singleClick		:   false					// double-click mode
    });
  
</script>
<%-- <script type="text/javascript">

	$('#item>tbody>tr').click(function() {  
		var value = $(this).find(".CAUSEBY").text();
	  $("#causeby").val(value);
	});	
</script> --%>
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
	
	$('#item>tbody>tr .CAUSEBY').live('click', function() {
		var value = $(this).text();
		$("#causeby").val(value);	
	});
	$('#item>tbody>tr .BSCID').live('click', function() {
		var value = $(this).text();
		$("#bscid").val(value);	
	});
	$('#item>tbody>tr .SDATE').live('click', function() {
		var value = $(this).text();
		$("#sdate").val(value);	
	});
	$('#item>tbody>tr .EDATE').live('click', function() {
		var value = $(this).text();
		$("#edate").val(value);	
	});
	$('#item>tbody>tr .ACTION_PROCESS').live('click', function() {
		var value = $(this).text();
		$("#actionProcess").val(value);	
	});
	$('#item>tbody>tr .SYSTEM').live('click', function() {
		var value = $(this).text();
		$("#dip").val(value);	
	});	
	
</script>
<script type="text/javascript">
    function DoubleScroll(element) {
        var scrollbar= document.createElement('div');
        scrollbar.appendChild(document.createElement('div'));
        scrollbar.style.overflow= 'auto';
        scrollbar.style.overflowY= 'hidden';
        scrollbar.firstChild.style.width= element.scrollWidth+'px';
        scrollbar.firstChild.style.paddingTop= '1px';
        scrollbar.firstChild.appendChild(document.createTextNode('\xA0'));
        scrollbar.onscroll= function() {
            element.scrollLeft= scrollbar.scrollLeft;
        };
        element.onscroll= function() {
            scrollbar.scrollLeft= element.scrollLeft;
        };
        element.parentNode.insertBefore(scrollbar, element);
    }

    DoubleScroll(document.getElementById('doublescroll'));
    
  
</script>
<script type="text/javascript">
	$(function(){
		var dvtTeamProcess = $("#dvtTeamProcess").val();
		var dvtUserProcess = '<c:out value="${vAlRbLossComunication.dvtUserProcess}"/>';
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

				/* $("#ngglIdError").text(''); */
				
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