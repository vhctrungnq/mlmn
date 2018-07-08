<%@ include file="/includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<c:choose>
  <c:when test="${fbVipAddEdit == 'N'}">
      <title><fmt:message key="sidebar.admin.truongVipFormAdd"/></title>
	  <content tag="heading"><fmt:message key="sidebar.admin.truongVipFormAdd"/></content>
  </c:when>
  <c:when test="${fbVipAddEdit == 'Y'}">
      <title><fmt:message key="sidebar.admin.truongVipFormEdit"/></title>
	  <content tag="heading"><fmt:message key="sidebar.admin.truongVipFormEdit"/></content>
  </c:when>
  <c:otherwise>
      
  </c:otherwise>
</c:choose>
   
<form:form commandName="fbVip" name="checkform" method="post" action="form.htm"> 
	<div>
    		<form:input path="id" type="hidden" />
    </div>
    <table class="simple2"> 
      <tr>
           <td class="wid15 mwid90">
           		<b><fmt:message key="dinhNghiaTruongVip.vipCode"/></b>&nbsp;<font color="red">(*)</font>
           	</td>
           <td>
           		<c:choose>
                <c:when test="${fbVipAddEdit == 'N'}">
                    <form:input path="vipCode" maxlength="40" cssClass="wid20"/>
                </c:when>
                <c:when test="${fbVipAddEdit == 'Y'}">
                    <b><i>${fbVip.vipCode}</i></b><form:hidden path="vipCode" />
                </c:when>
                <c:otherwise>
                    
                </c:otherwise>
            </c:choose>&nbsp;<form:errors path="vipCode" cssClass="error"/>
           </td>
      	</tr>
      	<tr>
      		<td>
           		<b><fmt:message key="dinhNghiaTruongVip.vipName"/></b>
          	</td>
           	<td>
           		<form:input path="vipName" maxlength="240" cssClass="wid20"/>
           	</td>
      </tr>
      <tr>
      	<td>
      		<b><fmt:message key="dinhNghiaTruongVip.ordering"/></b>
      	</td>
      	<td>
           		<form:input path="ordering" maxlength="4" cssClass="wid10"/>&nbsp;<form:errors path="ordering" cssClass="error"/>
        </td>
        
      </tr>
      <tr>
      	<td><b><fmt:message key="dinhNghiaTruongVip.thoiHanXuLy"/></b>&nbsp;<font color="red">(*)</font></td>
      	<td>
      		<form:input path="timeProcess" id ="timeProcess" cssClass="wid10" maxlength="5"/><font color="#0560A6"> (HH:MM) </font>&nbsp;<form:errors path="timeProcess" cssClass="error"/>
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

<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery/timePicker/jquery.timePicker.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery/timePicker/jquery.timePicker.min.js"></script>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/timePicker.css" />

<script type="text/javascript">
jQuery(function() {
    // Default.
    $("#timeProcess").timePicker();
});
</script>
<script type="text/javascript">

function focusIt()
{
	var sapXepError = '<c:out value="${sapXepError}"/>';
	
	if(document.checkform.vipCode.value==""){
		  var mytext = document.getElementById("vipCode");
		  mytext.focus();
		}
		else if(document.checkform.timeProcess.value == "")
		{
			var mytext = document.getElementById("timeProcess");
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

