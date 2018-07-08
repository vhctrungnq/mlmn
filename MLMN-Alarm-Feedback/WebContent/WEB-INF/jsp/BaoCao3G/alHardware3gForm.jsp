<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<title>${titleForm}</title>
<content tag="heading">${titleForm}</content>
<style>
	.ui-multiselect {
		width:160px !important;
	}
	 #teamProcess {
     visibility: visible !important;
    }
</style>
<form:form commandName="alHrHardware3G" name="checkform" id="myform" method="post" action="form.htm"> 
	<form:hidden path="id"/>
	<table class="simple2"> 
      <tr>
           <td style="width:100px;"><fmt:message key="alHardware3G.rnc"/><font color="red">(*)</font></td>
           <td  style="width:200px;">
           		<form:input type ="text" path="rnc" maxlength="18" style="width:160px;" rows="3"/>
           		<font color="red"><form:errors path="rnc"/></font>
           </td>
     
           <td  style="width:100px;"><fmt:message key="alHardware3G.site"/><font color="red">(*)</font></td>
           <td colspan="3">
           		<form:input type ="text" path="nodeb" maxlength="18" style="width:160px;" rows="3"/>
           		<font color="red"><form:errors path="nodeb" /></font>
           </td>
      </tr> 
      <tr>
           <td><fmt:message key="alHardware3G.startDate"/><font color="red">(*)</font></td>
           <td>
           		<input type ="text"  value="${stime}" name="stime" id="stime" size="20" maxlength="16" width="80px">
	   			 <img alt="calendar" title="Click to choose the start date" id="chooseStartDate" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
	    		<font color="red">${errorstime}<form:errors path="stime"/></font>
	    	</td>
     
           <td ><fmt:message key="alHardware3G.endDate"/></td>
           <td  style="width:180px;">
           		<input type ="text"  value="${etime}" name="etime" id="etime" size="20" maxlength="16" width="80px">
	   			 <img alt="calendar" title="Click to choose the end date" id="chooseEndDate" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
	    		<font color="red">${erroretime}<form:errors path="etime"/></font>
	    	</td>
           <td  style="width:80px;"><fmt:message key="alHardware3G.day"/></td>
           <td>
           		<input type ="text"  value="${day}" name="day" id="day" size="20" maxlength="16" width="80px">
	   			 <img alt="calendar" title="Click to choose the date" id="chooseday" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
	    		<font color="red">${errorday}<form:errors path="day"/></font>
	    	</td>
      </tr>
      <tr>
           <td><fmt:message key="alHrHardware3G.type"/></td>
           <td> 
           		<form:input type ="text" path="type" maxlength="200" style="width:160px;" rows="3"/>
          </td>
    
           <td><fmt:message key="alHrHardware3G.cardError"/></td>
           <td colspan="3"> 
           		<form:input type ="text" path="cardError" maxlength="200" style="width:160px;" rows="3"/>
           </td>
       </tr>
		<tr>
           <td><fmt:message key="alHardware3G.teamProcess"/></td>
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
       
           <td ><fmt:message key="alHardware3G.userProcess"/></td>
           <td style="border:0;" colspan="3">
           		<select name="userProcess" id="userProcess" multiple="multiple" style="width:160px;"></select>
           </td>
       </tr>   
        <tr>
           <td><fmt:message key="alHrHardware3G.resultContent"/></td>
           <td colspan="5"> 
           <textarea style="font-family:tahoma;width:100%; font-size:12px;height:50px;" rows="12"  name="resultContent" id="resultContent" maxlength="900" >${alHrHardware3G.resultContent}</textarea>	
          </td>
       </tr>
     
       <tr>
           <td></td>
           <td colspan="5">
               <input type="submit" class="button" value="<fmt:message key="button.save"/>" />
           	   <input type="button" class="button" value="<fmt:message key="button.cancel"/>" onClick='window.location="list.htm"'>
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
<!-- <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/js/selectStyle/jquery-ui.css" />
<script type="text/javascript" src="${pageContext.request.contextPath}/js/selectStyle/jquery.js"></script>
<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jqueryui/1/jquery-ui.min.js"></script> -->
<script type="text/javascript" src="${pageContext.request.contextPath}/js/selectStyle/jquery.multiselect.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/selectStyle/jquery.multiselect.filter.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/selectStyle/assets/prettify.js"></script>
<script type="text/javascript">
Calendar.setup({
    inputField		:	"stime",	// id of the input field   
    ifFormat		:	"%d/%m/%Y %H:%M",   	// format of the input field
    button			:   "chooseStartDate",  	// trigger for the calendar (button ID)
    showsTime		:	true,
    singleClick		:   true					// double-click mode
});
Calendar.setup({
    
    inputField		:	"etime",
    ifFormat		:	"%d/%m/%Y %H:%M",   	// format of the input field
    button			:   "chooseEndDate",  	// trigger for the calendar (button ID)
    showsTime		:	true,
    singleClick		:   true					// double-click mode
});
Calendar.setup({
    
    inputField		:	"day",
    ifFormat		:	"%d/%m/%Y %H:%M",   	// format of the input field
    button			:   "chooseday",  	// trigger for the calendar (button ID)
    showsTime		:	true,
    singleClick		:   true					// double-click mode
});
  
</script>
<script type="text/javascript">

function focusIt()
{
	if(document.checkform.rnc.value==""){
	  var mytext = document.getElementById("rnc");
	  mytext.focus();
	}
}

onload = focusIt;

</script>
<script type="text/javascript">
	$(function(){
		var teamProcess = $("#teamProcess").val();
		var userProcess = '<c:out value="${alHrHardware3G.userProcess}"/>';
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