<%@ include file="/includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<c:choose>
  <c:when test="${isoAssetTypeAddEdit == 'N'}">
      <title><fmt:message key="sidebar.admin.isoAssetTypeFormAdd"/></title>
	  <content tag="heading"><fmt:message key="sidebar.admin.isoAssetTypeFormAdd"/></content>
  </c:when>
  <c:when test="${isoAssetTypeAddEdit == 'Y'}">
      <title><fmt:message key="sidebar.admin.isoAssetTypeFormEdit"/></title>
	  <content tag="heading"><fmt:message key="sidebar.admin.isoAssetTypeFormEdit"/></content>
  </c:when>
  <c:otherwise></c:otherwise>
</c:choose>
   
<form:form commandName="isoAssetType" name="checkform" method="post" action="form.htm"> 
	<div>
    	<form:input path="id" type="hidden" />
    </div>
    <table class="simple2"> 
      <tr>
           <td class="wid15 mwid90">
           		<fmt:message key="isoAssetType.code"/>&nbsp;<font color="red">(*)</font>
           	</td>
           <td>
           		<c:choose>
                <c:when test="${isoAssetTypeAddEdit == 'N'}">
                    <form:input path="code" maxlength="30" cssClass="wid20"/>
                </c:when>
                <c:when test="${isoAssetTypeAddEdit == 'Y'}">
                    <b><i>${isoAssetType.code}</i></b><form:hidden path="code" />
                </c:when>
                <c:otherwise>
                    
                </c:otherwise>
            </c:choose>&nbsp;<form:errors path="code" cssClass="error"/>
           </td>
      	</tr>
      	<tr>
      		<td>
           		<fmt:message key="isoAssetType.name"/>
          	</td>
           	<td>
           		<form:input path="name" maxlength="190" cssClass="wid20"/>
           	</td>
      </tr>
      <tr>
      	<td>
      		<fmt:message key="isoAssetType.ordering"/>
      	</td>
      	<td>
           	<form:input path="ordering" maxlength="4" cssClass="wid10"/>&nbsp;<form:errors path="ordering" cssClass="error"/>
        </td>
        
      </tr>
      <tr>
      	<td><fmt:message key="isoAssetType.description"/></td>
      	<td>
      		<form:input path="description" id ="description" cssClass="wid50" maxlength="190"/>
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
	
	if(document.checkform.code.value==""){
		  var mytext = document.getElementById("code");
		  mytext.focus();
		}
		else if(document.checkform.name.value == "")
		{
			var mytext = document.getElementById("name");
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

