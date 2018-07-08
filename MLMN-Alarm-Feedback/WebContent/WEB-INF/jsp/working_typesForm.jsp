<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<c:choose>
  <c:when test="${WorksAddEdit == 'Y'}">
      <title><spring:message code="header.title.working_typesFormAdd"/></title>
	  <content tag="heading"><spring:message code="header.title.working_typesFormAdd"/></content>
  </c:when>
  <c:when test="${WorksAddEdit == 'N'}">
      <title><spring:message code="header.title.working_typesFormEdit"/></title>
	  <content tag="heading"><spring:message code="header.title.working_typesFormEdit"/></content>
  </c:when>
  <c:otherwise>
      
  </c:otherwise>
</c:choose>

<form:form commandName="w_working_types" name="checkform" method="post" action="form.htm"> 
	<div>
		<form:hidden path="id"/>
	</div>
    <table class="simple2"> 
      <tr>
           <td class="wid20 mwid100"><b><spring:message code="title.qLDanhMucCongViec.loaiCongViec"/></b>&nbsp;<font color="red">(*)</font></td>
           <td>
           	<b><form:input path="name" maxlength="255" cssClass="wid50"/></b>&nbsp;<form:errors path="name" cssClass="error"/>
           </td>
      </tr>
      <tr>
           <td><b><spring:message code="title.qLDanhMucCongViec.sapXep"/></b></td>
           <td><form:input id="ordering" path="ordering" cssClass="wid10" maxlength="4"/>&nbsp;<form:errors path="ordering" cssClass="error"/></td>                
      </tr>
      <tr>
           <td><b><spring:message code="title.qLDanhMucCongViec.moTa"/></b></td>
           <td><form:textarea path="description" cssClass="textareaCV50" maxlength="255"/></td>
      </tr>     
       <tr>
           <td></td>
           <td>
               <input class="button" type="submit" class="button" name="save" value="<spring:message code="button.save"/>" />
               <input class="button" type="button" value="<spring:message code="button.cancel"/>" onClick='window.location="list.htm"' >
           </td>
       </tr>
    </table>

</form:form>

<style>
    .error {
    	color: red;
    }
</style> 
<script type="text/javascript">

function focusIt()
{
	var orderingError = '<c:out value="${orderingError}"/>';
	if(document.checkform.name.value==""){
		  var mytext = document.getElementById("name");
		  mytext.focus();
	}
	else if(orderingError != "")
		{
			var mytext = document.getElementById("ordering");
		  mytext.focus();
		}
	else
		{
			var mytext = document.getElementById("name");
		  	mytext.focus();
		}
}

onload = focusIt;

</script>