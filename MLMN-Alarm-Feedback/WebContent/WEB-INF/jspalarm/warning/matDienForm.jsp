<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<title>${titleForm}</title>
<content tag="heading">${titleForm}</content>
<style>
	.ui-multiselect {
		width:160px !important;
	}
	
</style>
<form:form commandName="matDien" name="checkform" id="myform" method="post" action="form.htm"> 
	<form:hidden path="id"/>
	<input type="hidden" name="note" id="note" value="${note}">
	<input type="hidden" name="region" id="region" value="${region}">
	<table class="simple2"> 
      <tr>
           <td style="width:120px;"><fmt:message key="matDien.content"/><font color="red">(*)</font></td>
           <td colspan="3">
           		<form:input type ="text" path="alarm" maxlength="220" style="width:95%;" rows="3"/>
           		<font color="red"><form:errors path="alarm"/></font>
           </td>
      </tr>
      
      <tr>
           <td><fmt:message key="matDien.area"/><font color="red">(*)</font></td>
           <td style="width:200px;">
                    <select name="area" id="area" style="width: 230px;height:20px; padding-top: 4px;">
						<c:forEach var="item" items="${areaList}">
							<c:choose>
				                <c:when test="${item == matDien.area}">
				                    <option value="${item}" selected="selected">${item}</option>
				                </c:when>
								<c:otherwise>
									<option value="${item}">${item}</option>
								</c:otherwise>
							</c:choose>
						</c:forEach>
					</select> 
					<font color="red"><form:errors path="area"/></font>
           </td>
      
           <td style="width:120px;"><fmt:message key="matDien.userProcess"/></td>
           <td style="border:0;">
           		<select name="userProcess" id="userProcess" multiple="multiple" ></select>
           </td>
       </tr>
      <tr>
          <td align="left"><fmt:message key="timer.startTime"/><font color="red">(*)</font></td>
	    	<td>
	    		<input type ="text"  value="${startTime}" name="startTime" id="startTime" size="20" maxlength="16" width="80px">
	   			 <img alt="calendar" title="Click to choose the start date" id="chooseStartDate" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
	    		<font color="red">${errorStartTime}<form:errors path="stime"/></font>
	    	</td>
           <td align="left"><fmt:message key="timer.endTime"/></td>
	    	<td>
	    		<input type ="text"  value="${endTime}" name="endTime" id="endTime" size="20" maxlength="16" width="80px">
	    		<img alt="calendar" title="Click to choose the stop date" id="chooseStopDate" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
				<font color="red">${errorendTime}<form:errors path="etime"/></font>
			</td>
      </tr>     
      <tr>
           <td><fmt:message key="matDien.result"/></td>
           <td colspan="3">
          		 <textarea style="font-family:tahoma;width:100%; font-size:12px;height:30px;" rows="3"  name="result" id="result" maxlength="900" >${matDien.resultsProcess}</textarea>
           	 </td>
       </tr>   
       
        <tr>
           <td><fmt:message key="matDien.description"/></td>
           <td colspan="3"> 
           <textarea style="font-family:tahoma;width:100%; font-size:12px;height:30px;" rows="5"  name="description" id="description" maxlength="450" >${matDien.description}</textarea>	
          </td>
       </tr>
     
       <tr>
           <td></td>
           <td colspan="3">
               <input type="submit" class="button" value="<fmt:message key="button.save"/>" />
               <c:choose>
           			<c:when test="${note == 'caTruc'}">
		                   <input type="button" class="button" value="<fmt:message key="button.cancel"/>" onClick='window.location="${pageContext.request.contextPath}/caTruc/list.htm"'>
						
		                </c:when>
						<c:otherwise>
							 <input type="button" class="button" value="<fmt:message key="button.cancel"/>" onClick='window.location="list.htm"'>
						</c:otherwise>
				</c:choose>	
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
        inputField		:	"startTime",	// id of the input field
        ifFormat		:	"%d/%m/%Y %H:%M",   	// format of the input field
        button			:   "chooseStartDate",  	// trigger for the calendar (button ID)
        showsTime		:	true,
        singleClick		:   false					// double-click mode
    });

    Calendar.setup({
        inputField		:	"endTime",	// id of the input field
        ifFormat		:	"%d/%m/%Y %H:%M",   	// format of the input field
        button			:   "chooseStopDate",   	// trigger for the calendar (button ID)
        showsTime		:	true,
        singleClick		:   false					// double-click mode
    });
	
</script>
<script type="text/javascript">

function focusIt()
{
	if(document.checkform.alarm.value==""){
	  var mytext = document.getElementById("alarm");
	  mytext.focus();
	}
}

onload = focusIt;

</script>
<script type="text/javascript">
	$(function(){
		var userProcess = '<c:out value="${matDien.userProcess}"/>';
		var $widget;
			// Tao multiselect jquery
			$widget = $("#userProcess").multiselect(), state = true;
			$widget.multiselect('enable');
			
			// Khoi tao userProcess
			$.getJSON("${pageContext.request.contextPath}/ajax/getUserByDeparment.htm",{dept:'',team: ''}, function(j){
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
	});
</script> 