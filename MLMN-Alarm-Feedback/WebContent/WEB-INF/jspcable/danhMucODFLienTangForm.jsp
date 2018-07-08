<%@ include file="/includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<c:choose>
  <c:when test="${tenSoDoAddEdit == 'N'}">
      <title><fmt:message key="sidebar.admin.cableOdfTypesAdd"/></title>
	  <content tag="heading"><fmt:message key="sidebar.admin.cableOdfTypesAdd"/></content>
  </c:when>
  <c:when test="${tenSoDoAddEdit == 'Y'}">
      <title><fmt:message key="sidebar.admin.cableOdfTypesEdit"/></title>
	  <content tag="heading"><fmt:message key="sidebar.admin.cableOdfTypesEdit"/></content>
  </c:when>
  <c:otherwise>
      
  </c:otherwise>
</c:choose>
   
<form:form commandName="cableOdfTypesForm" name="checkform" method="post" action="form.htm"> 
	<div>
   		<form:input path="id" type="hidden" />
    </div>
    <table class="simple2"> 
      <tr>
           <td class="wid15 mwid90">
           		<b><fmt:message key="odfLienTang.tenSoDo"/></b>&nbsp;<font color="red">(*)</font>
           	</td>
           <td>
           		<c:choose>
                <c:when test="${tenSoDoAddEdit == 'N'}">
                    <form:input path="schemaName" maxlength="240" cssClass="wid20"/>
                </c:when>
                <c:when test="${tenSoDoAddEdit == 'Y'}">
                    <b><i>${cableOdfTypesForm.schemaName}</i></b><form:hidden path="schemaName" />
                </c:when>
                <c:otherwise>
                    
                </c:otherwise>
            </c:choose>&nbsp;<form:errors path="schemaName" cssClass="error"/>
           </td>
      	</tr>
      	<tr>
      		<td>
           		<b><fmt:message key="odfLienTang.viTri"/></b>&nbsp;<font color="red">(*)</font>
          	</td>
           	<td>
           		<form:input path="locationPort" maxlength="4" cssClass="wid10"/>&nbsp;<form:errors path="locationPort" cssClass="error"/>
           	</td>
      </tr>
      <tr>
      	<td>
      		<b><fmt:message key="odfLienTang.ordering"/></b>
      	</td>
      	<td>
           		<form:input path="ordering" maxlength="4" cssClass="wid10"/>&nbsp;<form:errors path="ordering" cssClass="error"/>
        </td>
        
      </tr>
      <tr>
      	<td><b><fmt:message key="odfLienTang.ghiChu"/></b></td>
      	<td>
      		<form:input path="description" maxlength="245" cssClass="wid50"/>
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
	var locationPortError = '<c:out value="${locationPortError}"/>';
	var sapXepError = '<c:out value="${sapXepError}"/>';
	if(document.checkform.schemaName.value==""){
		  var mytext = document.getElementById("schemaName");
		  mytext.focus();
		}
		else if(document.checkform.locationPort.value == "")
			{
			var mytext = document.getElementById("locationPort");
			  mytext.focus();
			}
		else if(locationPortError != "")
			{
				var mytext = document.getElementById("locationPort");
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

    