<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<c:choose>
  <c:when test="${HQualityNetworkAddEdit == 'N'}">
      <title><fmt:message key="title.qualityNetwork.formAdd"/></title>
	  <content tag="heading"><fmt:message key="title.qualityNetwork.formAdd"/></content>
  </c:when>
  <c:when test="${HQualityNetworkAddEdit == 'Y'}">
      <title><fmt:message key="title.qualityNetwork.formEdit"/></title>
	  <content tag="heading"><fmt:message key="title.qualityNetwork.formEdit"/></content>
  </c:when>
  <c:otherwise></c:otherwise>
</c:choose>

<form:form name="checkform" commandName="qualityNetwork" method="post" action="form.htm">
	<form:input id="id" path="id" type="hidden"/>
    <table class="simple2">
    <tr>
         <td><fmt:message key="qualityNetwork.groupName"/>&nbsp;<font color = "red">(*)</font></td>
         <td>            	
           	 <form:input path="groupName" maxlength="220" cssClass="wid30"/>
           	 &nbsp;<form:errors path="groupName" cssClass="error"/>
         </td>
     </tr>
     <tr>
         <td class="wid15 mwid110"><fmt:message key="qualityNetwork.qualityCode"/>&nbsp;<font color = "red">(*)</font></td>
         <td>
            <form:input path="qualityCode" maxlength="35" cssClass="wid30"/>
            &nbsp;<form:errors path="qualityCode" cssClass="error"/>
         </td>
     </tr>
     <tr>
         <td><fmt:message key="qualityNetwork.qualityName"/>&nbsp;<font color = "red">(*)</font></td>
         <td>            	
           	<form:input path="qualityName" maxlength="220" cssClass="wid30"/>
            &nbsp;<form:errors path="qualityName" cssClass="error"/>
         </td>
     </tr>
      <tr>
          <td><fmt:message key="qualityNetwork.qualityCondition"/></td>
          <td><form:input path="qualityCondition" maxlength="20" cssClass="wid30"/></td>
      </tr>
      <tr>
          <td><fmt:message key="qualityNetwork.qualityValue"/></td>
          <td><form:input path="qualityValue" maxlength="220" cssClass="wid30"/></td>
      </tr>
      <tr>
          <td><fmt:message key="qualityNetwork.qualityUnit"/></td>
          <td><form:input path="qualityUnit" maxlength="20" cssClass="wid30"/></td>
      </tr>
      <tr>
          <td><fmt:message key="qualityNetwork.ordering"/></td>
          <td>
          	<form:input path="ordering" maxlength="4" cssClass="wid10"/>
          	&nbsp;<form:errors path="ordering" cssClass="error"/>
          </td>
      </tr>
      <tr>
          <td></td>
          <td>
              <input class="button" type="submit" name="save" value="<fmt:message key="global.form.luulai"/>" />
              <input class="button" type="button" value="<fmt:message key="global.form.huybo"/>" onClick='window.location="list.htm"'>
          </td>
      </tr>
</table>
</form:form>
<script type="text/javascript">
function focusIt()
{
	var orderingError = '<c:out value="${orderingError}"/>';
	if(document.checkform.groupName.value==""){
		  var mytext = document.getElementById("groupName");
		  mytext.focus();
	}
	else if(document.checkform.qualityCode.value==""){
		  var mytext = document.getElementById("qualityCode");
		  mytext.focus();
	}
	else if(document.checkform.qualityName.value==""){
		  var mytext = document.getElementById("qualityName");
		  mytext.focus();
	}
	else if(orderingError != "")
	{
		var mytext = document.getElementById("ordering");
	  	mytext.focus();
	}
}
onload = focusIt;
</script>