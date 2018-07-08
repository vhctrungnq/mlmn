<%@ include file="/includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<c:choose>
  <c:when test="${isoPortCardAddEdit == 'N'}">
      <title><fmt:message key="sidebar.admin.isoPortCardFormAdd"/></title>
	  <content tag="heading"><fmt:message key="sidebar.admin.isoPortCardFormAdd"/></content>
  </c:when>
  <c:when test="${isoPortCardAddEdit == 'Y'}">
      <title><fmt:message key="sidebar.admin.isoPortCardFormEdit"/></title>
	  <content tag="heading"><fmt:message key="sidebar.admin.isoPortCardFormEdit"/></content>
  </c:when>
  <c:otherwise></c:otherwise>
</c:choose>
   
<form:form commandName="isoPortCard" name="checkform" method="post" action="form.htm"> 
	<div>
    	<form:input path="id" type="hidden" />
    </div>
    <table class="simple2"> 
    	<tr>
      	<td class="wid15 mwid90">
      		<fmt:message key="isoPortCard.activeDay"/>&nbsp;<font color="red">(*)</font>
      	</td>
      	<td>
	     	<input id="activeDay" name="activeDay" value="${activeDay}" class="wid10" maxlength="20"/>
	 		<img alt="calendar" title="Click to choose the active date" id="chooseActiveDay" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
	 		&nbsp;<form:errors path="activeDay" cssClass="error"/><span class="error">${errorActiveDay}</span>
        </td>
        
      </tr>
      <tr>
           <td >
           		<fmt:message key="isoPortCard.bsc"/>&nbsp;<font color="red">(*)</font>
           	</td>
           <td>
               <form:input path="neParent" maxlength="18" cssClass="wid50"/>&nbsp;<form:errors path="neParent" cssClass="error"/>
           </td>
      	</tr>
      	<tr>
      		<td>
           		<fmt:message key="isoPortCard.mfs"/>&nbsp;<font color="red">(*)</font>
          	</td>
           	<td>
           		<form:input path="ne" maxlength="18" cssClass="wid50"/>&nbsp;<form:errors path="ne" cssClass="error"/>
           	</td>
      </tr>
      
       <tr>
           <td></td>
           <td>
               <input class="button" type="submit" class="button" name="save" value="<fmt:message key="global.form.luulai"/>" />
               <input class="button" type="button" value="<fmt:message key="global.form.huybo"/>" onClick='window.location="list.htm?neType=MFS"'>
           </td>
       </tr>
    </table>

</form:form>

<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar_en.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar_setup.js"></script>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/calendar-blue.css" />
<script type="text/javascript">

Calendar.setup({
    inputField		:	"activeDay",	// id of the input field
    ifFormat		:	"%d/%m/%Y",   	// format of the input field
    button			:   "chooseActiveDay",  	// trigger for the calendar (button ID)
    singleClick		:   false					// double-click mode
});

function focusIt()
{
	var activeDayError = '<c:out value="${activeDayError}"/>';
	if(document.checkform.activeDay.value==""){
		  var mytext = document.getElementById("activeDay");
		  mytext.focus();
		}
	else if(activeDayError != "")
	{
		var mytext = document.getElementById("activeDay");
	  	mytext.focus();
	}
	else if(document.checkform.neParent.value == "")
	{
		var mytext = document.getElementById("neParent");
	  	mytext.focus();
	}
	else if(document.checkform.ne.value == "")
		{
			var mytext = document.getElementById("ne");
		  	mytext.focus();
		}
}

onload = focusIt;

</script>

