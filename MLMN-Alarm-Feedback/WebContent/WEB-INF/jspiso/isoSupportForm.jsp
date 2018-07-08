<%@ include file="/includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<c:choose>
  <c:when test="${isoSupportAddEdit == 'N'}">
      <title><fmt:message key="sidebar.admin.isoSupportFormAdd"/></title>
	  <content tag="heading"><fmt:message key="sidebar.admin.isoSupportFormAdd"/></content>
  </c:when>
  <c:when test="${isoSupportAddEdit == 'Y'}">
      <title><fmt:message key="sidebar.admin.isoSupportFormEdit"/></title>
	  <content tag="heading"><fmt:message key="sidebar.admin.isoSupportFormEdit"/></content>
  </c:when>
  <c:otherwise></c:otherwise>
</c:choose>

<form:form commandName="isoSupport" name="checkform" method="post" action="form.htm"> 
	<div>
    	<form:input path="id" type="hidden" />
    </div>
    <table class="simple2">
      <tr>
           <td class="wid15 mwid110"><fmt:message key="isoSupport.vendor"/></td>
           <td class="wid35">
		       	<form:select path="vendor" cssClass="wid50">
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
				</form:select>
           </td>
           <td class="wid15 mwid110"><fmt:message key="isoSupport.neType"/></td>
           <td>
           		<form:input path="neType" maxlength="15" cssClass="wid90"/>
           </td>       
      </tr> 
      <tr>
           <td><fmt:message key="isoSupport.ne"/></td>
           <td>
           		<form:input path="ne" maxlength="15" cssClass="wid90"/>
           </td>
    	 	<td><fmt:message key="isoSupport.boardName"/>&nbsp;<font color="red">(*)</font></td>
         	<td><form:input path="boardName" maxlength="15" cssClass="wid90"/>&nbsp;<form:errors path="boardName" cssClass="error"/></td>
      </tr>
      <tr>
      	<td><fmt:message key="isoSupport.value"/>&nbsp;<font color="red">(*)</font></td>
      	<td><form:input path="value" maxlength="4" cssClass="wid50"/>&nbsp;<form:errors path="value" cssClass="error"/></td>
        <td><fmt:message key="isoSupport.unit"/>&nbsp;<font color="red">(*)</font></td>
        <td><form:input path="unit" maxlength="15" cssClass="wid90"/>&nbsp;<form:errors path="unit" cssClass="error"/></td>
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

<script type="text/javascript">

function focusIt()
{
	var valueError = '<c:out value="${valueError}"/>';
	
	if(document.checkform.vendor.value==""){
		  var mytext = document.getElementById("vendor");
		  mytext.focus();
		}
	else if(document.checkform.boardName.value==""){
		  var mytext = document.getElementById("boardName");
		  mytext.focus();
		}
	else if(valueError != "")
	{
		var mytext = document.getElementById("value");
	  	mytext.focus();
	}
	else if(document.checkform.value.value == "")
	{
		var mytext = document.getElementById("value");
	  	mytext.focus();
	}
	else if(document.checkform.unit.value == "")
	{
		var mytext = document.getElementById("unit");
	  	mytext.focus();
	}
}

onload = focusIt;

</script>

