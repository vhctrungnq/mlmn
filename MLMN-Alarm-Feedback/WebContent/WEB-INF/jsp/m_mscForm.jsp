<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<title><spring:message code="header.title.m_mscForm"/></title>
<content tag="heading"><spring:message code="header.title.m_mscForm"/></content>

<form:form commandName="m_msc" name="checkform" id="myform" method="post" action="form.htm"> 
	
    <table class="simple2"> 
      <tr>
           <td width="200"><b>MSCID</b></td>
           <td>
           	<c:choose>
                <c:when test="${empty m_msc.mscid}">
                    <form:input path="mscid" maxlength="80"/>
                </c:when>
                <c:otherwise>
                    <b><i>${m_msc.mscid}</i></b><form:hidden path="mscid" />
                </c:otherwise>
            </c:choose>&nbsp;<form:errors path="mscid" cssClass="error"/>
           </td>
      </tr>
      <tr>
           <td><b><spring:message code="m_mscForm.lable.vender"/></b></td>
           <td><form:input path="vender" maxlength="50"/></td>
      </tr>
      <tr>
           <td><b>MSU_CAPACITY</b></td>
           <td><form:input id="msuCapacity" path="msuCapacity" maxlength="12" size="10"/>&nbsp;<form:errors path="msuCapacity" cssClass="error"/></td>                
      </tr>
      <tr>
           <td><b>MM_TYPE</b></td>
           <td><form:input path="mmType" maxlength="3" size="5"/></td>
      </tr>     
      <tr>
           <td><b>ORDERING</b></td>
           <td><form:input path="ordering" maxlength="4" size="5"/>&nbsp;<form:errors path="ordering" cssClass="error"/></td>
       </tr>   
       
        <tr>
           <td><b>REGION</b></td>
           <td> <form:select style="width: 100px" path="region" items="${regionList}" itemValue="region" itemLabel="region" /></td>
       </tr>
       <%--<tr>
           <td><b>Region</b></td>
           <td><input name="regionid" id="regionid" value="${regionid}" size="10"></td>
       </tr> --%>
       <tr>
           <td></td>
           <td>
               <input type="submit" class="button" name="save" value="<spring:message code="button.save"/>" />
               <input type="button" value="<spring:message code="button.cancel"/>" onClick="history.back()">
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
	if(document.checkform.mscid.value==""){
	  var mytext = document.getElementById("mscid");
	  mytext.focus();
	}
	else
		{
		var mytext = document.getElementById("vender");
		  mytext.focus();
		}
		
}

onload = focusIt;

</script>