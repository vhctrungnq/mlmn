<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <style type="text/css">   
 	  #dvtTeamProcess {
     visibility: visible !important;
    }
      #dvtUserProcess {
     visibility: visible !important;
    }
    .ui-multiselect {
		width:160px !important;
	}
</style>   
<title>${titleForm}</title>
<content tag="heading">${titleForm}</content>



<form:form commandName="alDyMblAblLog" name="checkform" id="myform" method="post" action="trx_form.htm"> 
	<form:hidden path="id"/>
	<input type="hidden" name="alarmType" id="alarmType" value="${alarmType}">
	<table class="simple2"> 
	<tr>	
           <td class="wid12 mwid150"><b><fmt:message key="alDyMblAblLog.bsc"/></b>&nbsp;<font color="red">(*)</font></td>
           <td class="wid20">
           		<form:input type ="text" path="bscid" maxlength="18" style="width:160px;"/>&nbsp;
           		<form:errors path="bscid" cssClass="errorMessages"/>
           </td>
           <td class="wid10 mwid80"><b><fmt:message key="alDyMblAblLog.trx"/></b>&nbsp;<font color="red">(*)</font></td>
           <td class="wid20">
           		<form:input type ="text" path="mo" maxlength="18" style="width:160px;"/>&nbsp;
           		<form:errors path="mo" cssClass="errorMessages"/>
           </td>
           <td class="wid12 mwid140"><b><fmt:message key="alDyMblAblLog.cell"/></b></td>
           <td class="wid20">
           		<form:input type ="text" path="cellid" maxlength="18" style="width:160px;"/>
           </td>
      </tr> 
      <tr>
      		<td ><b><fmt:message key="alDyMblAblLog.day"/></b>&nbsp;<font color="red">(*)</font></td>
           <td>
           		<input type ="text"  value="${day}" name="day" id="day" maxlength="16" style="width:160px;">
	   			<img alt="calendar" title="Click to choose the start date" id="choosesdate" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
	    		&nbsp;<font color="red">${errorsdate}</font>	
	    	</td>
	    	<td><b><fmt:message key="alDyMblAblLog.vendor"/></b></td>
           	<td colspan="3">
           		<select name="vendor" id="vendor" style="width:160px;">
           		<option value=""><fmt:message key="global.All"/></option>
	           		<c:forEach var="item" items="${vendorList}">
						<c:choose>
			                <c:when test="${item.value == vendor}">
			                    <option value="${item.value}" selected="selected">${item.value}</option>
			                </c:when>
							<c:otherwise>
								<option value="${item.value}">${item.value}</option>
							</c:otherwise>
						</c:choose>
					</c:forEach>
				</select> 
           		
	    	</td>
      	</tr> 
		<tr>
			<td><b><fmt:message key="alDyMblAblLog.donViXuLy"/></b></td>
	           	<td>
		           <select name="dvtTeamProcess" id="dvtTeamProcess" style="width:160px;">
		           		<c:forEach var="item" items="${dvXuLyList}">
							<c:choose>
				                <c:when test="${item.team == dvtTeamProcess}">
				                    <option value="${item.team}" selected="selected">${item.team}</option>
				                </c:when>
								<c:otherwise>
									<option value="${item.team}">${item.team}</option>
								</c:otherwise>
							</c:choose>
						</c:forEach>
					</select> 
	           	</td>
			
			<td><b><fmt:message key="alDyMblAblLog.dvtUserProcess"/></b></td>
	          <td colspan="3">
	          	<select name="dvtUserProcess" id="dvtUserProcess" multiple="multiple" style="width:160px;"></select>
	          </td>
			
       	</tr>
       	<tr>
       		<td ><b><fmt:message key="alDyMblAblLog.nguyenNhan"/></b></td>
          <td colspan="5"> 
           	<form:textarea id="causeby" path="causeby" style="font-family:tahoma;width:100%; font-size:12px;height:30px;" rows="10" maxlength="240"/> 
		  </td>
       	</tr>
       	<tr>
       		<td ><b><fmt:message key="alDyMblAblLog.nguyenNhanPhanHoi"/></b></td>
          <td colspan="5"> 
           	<form:textarea id="causebyDvt" path="causebyDvt" style="font-family:tahoma;width:100%; font-size:12px;height:30px;" rows="10" maxlength="240"/> 	
          </td>
       	</tr>
       	<tr>
           <td ><b><fmt:message key="vAlRbErrorTRX.actionProcess"/></b></td>
         	<td colspan="5"> 
           		<form:textarea path="actionProcess" style="font-family:tahoma;width:100%; font-size:12px;height:30px;" rows="10" maxlength="240"/> 	
        	</td>
           
       	</tr>
       	<tr>
       		<td><b><fmt:message key="vAlRbErrorTRX.description"/></b></td>
       		<td colspan="5"> 
           	<form:textarea path="description" style="font-family:tahoma;width:100%; font-size:12px;height:30px;" rows="10" maxlength="240"/>
           </td>
       	</tr>
       	<tr>
           <td></td>
           <td colspan="5">
               <input type="submit" class="button" value="<fmt:message key="button.save"/>" />
           	   <input type="button" class="button" value="<fmt:message key="button.cancel"/>" onClick='window.location="TRX_List.htm?alarmType=${alarmType}"'>
					
           </td>
       </tr>
    </table>
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
        button			:   "choosesdate",  	// trigger for the calendar (button ID)
        showsTime		:	true,
        singleClick		:   false					// double-click mode
    });
   
</script>
<script type="text/javascript">

function focusIt()
{
	if(document.checkform.mo.value==""){
	  var mytext = document.getElementById("mo");
	  mytext.focus();
	}
	else if(document.checkform.day.value==""){
		  var mytext = document.getElementById("day");
		  mytext.focus();
		}
}

onload = focusIt;

</script>
<script type="text/javascript">

	$('#item>tbody>tr').click(function() {  
		var value = $(this).find(".CAUSEBY").text();
	  $("#causeby").val(value);
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
	$("#dvtUserProcess").multiselect().multiselectfilter();
	$(function(){
		var dvtTeamProcess = $("#dvtTeamProcess").val();
		var dvtUserProcess = '<c:out value="${alDyMblAblLog.dvtUserProcess}"/>';
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