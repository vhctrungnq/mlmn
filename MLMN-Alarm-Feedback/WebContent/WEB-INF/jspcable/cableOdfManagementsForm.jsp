<%@ include file="/includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<c:choose>
  <c:when test="${tenSoDoAddEdit == 'N'}">
      <title><fmt:message key="sidebar.admin.cableOdfManagementAdd"/></title>
	  <content tag="heading"><fmt:message key="sidebar.admin.cableOdfManagementAdd"/></content>
  </c:when>
  <c:when test="${tenSoDoAddEdit == 'Y'}">
      <title><fmt:message key="sidebar.admin.cableOdfManagementEdit"/></title>
	  <content tag="heading"><fmt:message key="sidebar.admin.cableOdfManagementEdit"/></content>
  </c:when>
  <c:otherwise>
      
  </c:otherwise>
</c:choose>
   
<form:form commandName="cableOdfManagementsForm" name="checkform" method="post" action="form.htm"> 
	<div>
   		<form:input path="id" type="hidden" />
   		<input id="schemaLink" name="schemaLink" value="${schemaLink}" type="hidden" />
   		<input id="nameLink" name="nameLink" value="${nameLink}" type="hidden" />
    </div>
    <table class="simple2"> 
      <tr>
           <td class="wid15 mwid90">
           		<b><fmt:message key="odfLienTang.tenSoDo"/></b>&nbsp;<font color="red">(*)</font>
           	</td>
           <td>
                   <select id="idOdfTypes" name="idOdfTypes" class="wid30">
					<c:forEach var="items" items="${cableOdfTypesList}">
		              <c:choose>
		                <c:when test="${items.id == idOdfTypesCBB}">
		                    <option value="${items.id}" selected="selected">${items.schemaName}</option>
		                </c:when>
		                <c:otherwise>
		                    <option value="${items.id}">${items.schemaName}</option>
		                </c:otherwise>
		              </c:choose>
				    </c:forEach>
				</select>
               &nbsp;<form:errors path="schemaName" cssClass="error"/>
           </td>
      	</tr>
      	<tr>
      		<td>
           		<b><fmt:message key="cableOdfManagements.viTri"/></b>&nbsp;<font color="red">(*)</font>
          	</td>
           	<td>
           		<form:input path="port" maxlength="4" cssClass="wid12"/>&nbsp;<form:errors path="port" cssClass="error"/>
           	</td>
      </tr>
      <tr>
      		<td>
           		<b><fmt:message key="cableOdfManagements.name"/></b>
          	</td>
           	<td>
           		<form:input path="name" maxlength="240" cssClass="wid30"/>
           	</td>
      	</tr>
      	<tr>
      		<td>
           		<b><fmt:message key="cableOdfManagements.vendor"/></b>
          	</td>
           	<td>
           		<form:input path="vendor" maxlength="240" cssClass="wid30"/>
           	</td>
      	</tr>
      <tr>
      		<td>
           		<b><fmt:message key="cableOdfManagements.isEnable"/></b>
          	</td>
           	<td>
           		<form:select path="isEnable" cssClass="wid12">
   					<c:forEach var="items" items="${getIsEnableCableOdfList}">
		              <c:choose>
		                <c:when test="${items.value == trangThaiCBB}">
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
      	<td>
      		<b><fmt:message key="cableOdfManagements.ordering"/></b>
      	</td>
      	<td>
           		<form:input path="ordering" maxlength="4" cssClass="wid12"/>&nbsp;<form:errors path="ordering" cssClass="error"/>
        </td>
        
      </tr>
      <tr>
      	<td><b><fmt:message key="cableOdfManagements.description"/></b></td>
      	<td>
      		<form:input path="description" maxlength="490" cssClass="wid50"/>
      	</td>
      </tr>
       <tr>
           <td></td>
           <td>
               <input class="button" type="submit" class="button" name="save" value="<fmt:message key="global.form.luulai"/>" />
               <input class="button" type="button" value="<fmt:message key="global.form.huybo"/>" onClick='window.location="list.htm<c:if test="${not empty schemaLink || not empty nameLink}">?</c:if><c:if test="${not empty schemaLink}">schemaLink=${schemaLink}</c:if><c:if test="${not empty nameLink && not empty schemaLink}">&</c:if><c:if test="${not empty nameLink}">nameLink=${nameLink}</c:if>"'>
           </td>
       </tr>
    </table>

</form:form>

<script type="text/javascript">

function focusIt()
{
	var portError = '<c:out value="${portError}"/>';
	var sapXepError = '<c:out value="${sapXepError}"/>';
	if(document.checkform.port.value == "")
		{
		var mytext = document.getElementById("port");
		  mytext.focus();
		}
	else if(portError != "")
		{
			var mytext = document.getElementById("port");
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

    