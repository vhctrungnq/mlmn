<%@ include file="/includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<c:choose>
  <c:when test="${isoLicenseSoftAddEdit == 'N'}">
      <title><fmt:message key="sidebar.admin.isoLicenseSoftFormAdd"/></title>
	  <content tag="heading"><fmt:message key="sidebar.admin.isoLicenseSoftFormAdd"/></content>
  </c:when>
  <c:when test="${isoLicenseSoftAddEdit == 'Y'}">
      <title><fmt:message key="sidebar.admin.isoLicenseSoftFormEdit"/></title>
	  <content tag="heading"><fmt:message key="sidebar.admin.isoLicenseSoftFormEdit"/></content>
  </c:when>
  <c:otherwise></c:otherwise>
</c:choose>

<form:form commandName="isoLicenseSoft" name="checkform" method="post" action="form.htm"> 
	<div>
    	<form:input path="id" type="hidden" />
    </div>
    <table class="simple2">
      <tr>
           <td class="wid15 mwid110"><fmt:message key="isoLicenseSoft.day"/>&nbsp;<font color="red">(*)</font></td>
           <td class="wid35">
		       	<input id="day" name="day" value="${day}" class="wid30" maxlength="20"/>
 				<img alt="calendar" title="Click to choose the date" id="chooseDay" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
               	&nbsp;<form:errors path="day" cssClass="error"/> <span class="error">${errorDay}</span>
           </td>
           <td class="wid15 mwid110"><fmt:message key="isoLicenseSoft.vendor"/>&nbsp;<font color="red">(*)</font></td>
           <td>
           		<form:select path="vendor" cssClass="wid30">
					<c:forEach var="items" items="${vendorForResourceList}">
						<c:choose>
						<c:when test="${items.value == vendorCBB}">
						   <option value="${items.value}" selected="selected">${items.name}</option>
						</c:when>
						<c:otherwise>
						   <option value="${items.value}">${items.name}</option>
						</c:otherwise>
						</c:choose>
					</c:forEach>
				</form:select>&nbsp;<form:errors path="vendor" cssClass="error"/>
           </td>       
      </tr> 
      <tr>
           <td><fmt:message key="isoLicenseSoft.neType"/></td>
           <td>
           		<form:input path="neType" maxlength="15" cssClass="wid90"/>
           </td>
    	 	<td><fmt:message key="isoLicenseSoft.ne"/>&nbsp;<font color="red">(*)</font></td>
         	<td><form:input path="ne" maxlength="15" cssClass="wid90"/>&nbsp;<form:errors path="ne" cssClass="error"/></td>
      </tr>
      <tr>
      	<td><fmt:message key="isoLicenseSoft.featureCode"/>&nbsp;<font color="red">(*)</font></td>
      	<td><form:input path="featureCode" maxlength="15" cssClass="wid90"/>&nbsp;<form:errors path="featureCode" cssClass="error"/></td>
        <td><fmt:message key="isoLicenseSoft.licenseCode"/></td>
        <td><form:input path="licenseCode" maxlength="30" cssClass="wid90"/></td>
      </tr>
      <tr>
      	<td><fmt:message key="isoLicenseSoft.featureName"/></td>
      	<td>
			<form:input path="featureName" maxlength="200" cssClass="wid90"/>
      	</td>
      	<td><fmt:message key="isoLicenseSoft.status"/></td>
      	<td>
			<form:select path="status" cssClass="wid30">
				<c:forEach var="items" items="${statusLicenseSoftList}">
					<c:choose>
					<c:when test="${items.value == statusCBB}">
					   <option value="${items.value}" selected="selected">${items.name}</option>
					</c:when>
					<c:otherwise>
					   <option value="${items.value}">${items.name}</option>
					</c:otherwise>
					</c:choose>
				</c:forEach>
			</form:select>
      	</td>
      </tr>
      <tr>
      	<td><fmt:message key="isoLicenseSoft.activeDate"/></td>
      	<td>
		    <input id="activeDate" name="activeDate" value="${activeDate}" class="wid30" maxlength="20"/>
	 		<img alt="calendar" title="Click to choose the active date" id="chooseActiveDate" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
	 		&nbsp;<form:errors path="activeDate" cssClass="error"/>
		</td>
      	<td><fmt:message key="isoLicenseSoft.capacity"/></td>
      	<td>
      		<form:input path="capacity" maxlength="6" cssClass="wid30"/>&nbsp;<form:errors path="capacity" cssClass="error"/>
      	</td>
      </tr>
      <tr>
      	<td><fmt:message key="isoLicenseSoft.usage"/></td>
      	<td><form:input path="usage" maxlength="4" cssClass="wid30"/>&nbsp;<form:errors path="usage" cssClass="error"/></td>
      </tr>
      <tr>
        <td></td>
        <td colspan="3">
            <input class="button" type="submit" name="save" value="<fmt:message key="global.form.luulai"/>" />
            <input class="button" type="button" value="<fmt:message key="global.form.huybo"/>" onClick='window.location="list.htm"'>
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
    inputField		:	"day",	// id of the input field
    ifFormat		:	"%d/%m/%Y",   	// format of the input field
    button			:   "chooseDay",  	// trigger for the calendar (button ID)
    singleClick		:   false					// double-click mode
});

Calendar.setup({
    inputField		:	"activeDate",	// id of the input field
    ifFormat		:	"%d/%m/%Y",   	// format of the input field
    button			:   "chooseActiveDate",  	// trigger for the calendar (button ID)
    singleClick		:   false					// double-click mode
});

function focusIt()
{
	var capacityError = '<c:out value="${capacityError}"/>';
	var usageError = '<c:out value="${usageError}"/>';
	var dayError = '<c:out value="${dayError}"/>';
	var activeDateError = '<c:out value="${activeDateError}"/>';
	
	if(document.checkform.day.value==""){
		  var mytext = document.getElementById("day");
		  mytext.focus();
		}
	else if(dayError != "")
	{
		var mytext = document.getElementById("day");
	  	mytext.focus();
	}
	else if(document.checkform.ne.value == "")
	{
		var mytext = document.getElementById("ne");
	  	mytext.focus();
	}
	else if(document.checkform.featureCode.value == "")
	{
		var mytext = document.getElementById("featureCode");
	  	mytext.focus();
	}
	else if(activeDateError != "")
	{
		var mytext = document.getElementById("activeDate");
	  	mytext.focus();
	}
	else if(capacityError != "")
		{
			var mytext = document.getElementById("capacity");
		  	mytext.focus();
		}
	else if(usageError != "")
	{
		var mytext = document.getElementById("usage");
	  	mytext.focus();
	}
}

onload = focusIt;

</script>

