<%@ include file="/includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<c:choose>
  <c:when test="${assetsTypesAddEdit == 'N'}">
      <title><fmt:message key="sidebar.admin.assetsTypesFormAdd"/></title>
	  <content tag="heading"><fmt:message key="sidebar.admin.assetsTypesFormAdd"/></content>
  </c:when>
  <c:when test="${assetsTypesAddEdit == 'Y'}">
      <title><fmt:message key="assetsTypes.assetsTypesFormEdit"/></title>
	  <content tag="heading"><fmt:message key="assetsTypes.assetsTypesFormEdit"/></content>
  </c:when>
  <c:otherwise></c:otherwise>
</c:choose>
   
<form:form commandName="assetsTypes" name="checkform" method="post" action="form.htm"> 
	<div>
    	<form:input path="id" type="hidden" />
    </div>
    <table class="simple2"> 
      <tr>
           <td class="wid15 mwid90">
           		<fmt:message key="assetsTypes.asTypesId"/>&nbsp;<font color="red">(*)</font>
           	</td>
           <td>
           		<c:choose>
                <c:when test="${assetsTypesAddEdit == 'N'}">
                    <form:input path="asTypesId" maxlength="40" cssClass="wid20"/>
                </c:when>
                <c:when test="${assetsTypesAddEdit == 'Y'}">
                    <b><i>${assetsTypes.asTypesId}</i></b><form:hidden path="asTypesId" />
                </c:when>
                <c:otherwise>
                    
                </c:otherwise>
            </c:choose>&nbsp;<form:errors path="asTypesId" cssClass="error"/>
           </td>
      	</tr>
      	<tr>
      		<td>
           		<fmt:message key="assetsTypes.asTypesName"/>
          	</td>
           	<td>
           		<form:input path="asTypesName" maxlength="240" cssClass="wid20"/>
           	</td>
      </tr>
      <tr>
      		<td>
           		<fmt:message key="assetsTypes.warehourse"/>
          	</td>
           	<td>
           		<form:input path="warehourse" maxlength="40" cssClass="wid20"/>
           	</td>
      </tr>
      <tr>
      	<td>
      		<fmt:message key="assetsTypes.ordering"/>
      	</td>
      	<td>
           	<form:input path="ordering" maxlength="4" cssClass="wid10"/>&nbsp;<form:errors path="ordering" cssClass="error"/>
        </td>
        
      </tr>
      <tr>
      	<td><fmt:message key="assetsTypes.description"/></td>
      	<td>
      		<form:input path="description" id ="description" cssClass="wid50" maxlength="900"/>
      	</td>
      	</tr>
       <tr>
           <td></td>
           <td>
               <input class="button" type="submit" class="button" name="save" value="<fmt:message key="global.form.luulai"/>" />
               <input class="button" type="button" value="<fmt:message key="global.form.huybo"/>" onClick='window.location="list.htm"'>
           </td>
       </tr>
    </table>

</form:form>

<script type="text/javascript">

function focusIt()
{
	var sapXepError = '<c:out value="${sapXepError}"/>';
	
	if(document.checkform.asTypesId.value==""){
		  var mytext = document.getElementById("asTypesId");
		  mytext.focus();
		}
		else if(document.checkform.asTypesName.value == "")
		{
			var mytext = document.getElementById("asTypesName");
		  	mytext.focus();
		}
		else if(sapXepError != "")
			{
				var mytext = document.getElementById("ordering");
			  	mytext.focus();
			}
}

onload = focusIt;

</script>

