<%@ include file="/includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
  
<c:choose>
  <c:when test="${cableTransmissionPartnersAddEdit == 'N'}">
      <title><fmt:message key="title.cableTransmissionPartnersFormAdd"/></title>
	  <content tag="heading"><fmt:message key="title.cableTransmissionPartnersFormAdd"/></content>
  </c:when>
  <c:when test="${cableTransmissionPartnersAddEdit == 'Y'}">
      <title><fmt:message key="title.cableTransmissionPartnersFormEdit"/></title>
	  <content tag="heading"><fmt:message key="title.cableTransmissionPartnersFormEdit"/></content>
  </c:when>
  <c:otherwise></c:otherwise>
</c:choose>

<form:form commandName="cableTransmissionPartners" name="checkform" method="post" action="form.htm"> 
	<div>
    	<form:input path="id" type="hidden" />
    </div>
    <table class="simple2">
      <tr>
           <td class="wid15 mwid110"><fmt:message key="cableTransmissionPartners.vendor"/>&nbsp;<font color="red">(*)</font></td>
           <td class="wid35">
		       	<form:input path="vendor" maxlength="100" />
		       	&nbsp;<form:errors path="vendor" cssClass="error"/>
           </td>
           <td class="wid15 mwid110"><fmt:message key="cableTransmissionPartners.odfName"/>&nbsp;<font color="red">(*)</font></td>
           <td>
           		<form:input path="odfName" maxlength="100" />
           		&nbsp;<form:errors path="odfName" cssClass="error"/>
           </td>       
      </tr>
      <tr>
           <td><fmt:message key="cableTransmissionPartners.fiberCable"/></td>
           <td>
				<form:input path="fiberCable" maxlength="100" />
           </td>
           <td><fmt:message key="cableTransmissionPartners.odfPos"/></td>
           <td>
				<form:input path="odfPos" maxlength="100" />
           </td>       
      </tr>
      <tr>
           <td><fmt:message key="cableTransmissionPartners.transmission"/></td>
           <td>
		       	<form:input path="transmission" maxlength="220" />
           </td>
           <td><fmt:message key="cableTransmissionPartners.threadName"/></td>
           <td>
           		<form:input path="threadName" maxlength="220" />
           </td>       
      </tr> 
      <tr>
           <td><fmt:message key="cableTransmissionPartners.speed"/></td>
           <td>
		       	<form:input path="speed" maxlength="40" />
           </td>
           <td><fmt:message key="cableTransmissionPartners.odfDestination"/></td>
           <td>
           		<form:input path="odfDestination" maxlength="80" />
           </td>       
      </tr>  
      <tr>
           <td><fmt:message key="cableTransmissionPartners.portDestination"/></td>
           <td>
		       	<form:input path="portDestination" maxlength="80" />
           </td>
           <td><fmt:message key="cableTransmissionPartners.purposeUse"/></td>
           <td>
           		<form:input path="purposeUse" maxlength="220" />
           </td>       
      </tr> 
      <tr>
           <td><fmt:message key="cableTransmissionPartners.site"/></td>
           <td>
		       	<form:input path="site" maxlength="80" />
           </td> 
           <td></td>
           <td></td>      
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
	
	if(document.checkform.vendor.value==""){
		  var mytext = document.getElementById("vendor");
		  mytext.focus();
		}
	else if(document.checkform.odfName.value==""){
		  var mytext = document.getElementById("odfName");
		  mytext.focus();
		}
}

onload = focusIt;

</script>
<script type="text/javascript">
var theme = getTheme();
$("#vendor").jqxInput({ width: '60%', height: 20, minLength: 1, theme: theme });
$("#odfName").jqxInput({ width: '60%', height: 20, minLength: 1, theme: theme });
$("#fiberCable").jqxInput({ width: '60%', height: 20, minLength: 1, theme: theme });
$("#odfPos").jqxInput({ width: '60%', height: 20, minLength: 1, theme: theme });
$("#transmission").jqxInput({ width: '60%', height: 20, minLength: 1, theme: theme });
$("#threadName").jqxInput({ width: '60%', height: 20, minLength: 1, theme: theme });
$("#speed").jqxInput({ width: '60%', height: 20, minLength: 1, theme: theme });
$("#odfDestination").jqxInput({ width: '60%', height: 20, minLength: 1, theme: theme });
$("#portDestination").jqxInput({ width: '60%', height: 20, minLength: 1, theme: theme });
$("#purposeUse").jqxInput({ width: '60%', height: 20, minLength: 1, theme: theme });
$("#site").jqxInput({ width: '60%', height: 20, minLength: 1, theme: theme });

</script>
