<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<title><spring:message code="header.title.m_bscForm"/></title>
<content tag="heading"><spring:message code="header.title.m_bscForm"/></content>

<form:form commandName="m_bsc" name="checkform" id="myform" method="post" action="form.htm"> 
	
    <table class="simple2"> 
      <tr>
           <td width="200"><b>BSCID</b></td>
           <td>
           	<c:choose>
                <c:when test="${empty m_bsc.bscid}">
                    <form:input path="bscid" maxlength="10" size="15"/>
                </c:when>
                <c:otherwise>
                    <b><i>${m_bsc.bscid}</i></b><form:hidden path="bscid" maxlength="10" />
                </c:otherwise>
            </c:choose>&nbsp;<form:errors path="bscid" cssClass="error"/>
           </td>
      </tr>
      <tr>
           <td><b><spring:message code="m_bscForm.lable.vendor"/></b></td>
           <td><form:input path="vendor" maxlength="20"/></td>
      </tr>
      <tr>
           <td><b>TRAU</b></td>
           <td><form:input path="trau" maxlength="12" size="10"/>&nbsp;<form:errors path="trau" cssClass="error"/></td>                
      </tr>
      <tr>
           <td><b>TRX</b></td>
           <td><form:input path="trx" maxlength="12" size="10"/>&nbsp;<form:errors path="trx" cssClass="error"/></td>
      </tr>  
      <tr>
           <td><b>LAUNCH_DATE</b></td>
           <td><form:input path="launchDate" size="10" maxlength="10"/><img alt="calendar" title="Click to choose the launch date" id="chooseLaunchDate" style="cursor: pointer;" src="/VMSC2-Alarm/images/calendar.png"/>&nbsp;<form:errors path="launchDate" cssClass="error"/></td>
      </tr>
      <tr>
           <td><b>BSC_TYPE</b></td>
           <td><form:input path="bscType" maxlength="10"/>&nbsp;<form:errors path="bscType" cssClass="error"/></td>
      </tr>   
      <tr>
           <td><b>ORDERING</b></td>
           <td><form:input path="ordering" maxlength="4" size="10"/>&nbsp;<form:errors path="ordering" cssClass="error"/></td>
       </tr>
       <tr>
           <td><b>DEP_ID</b></td>
           <td> <form:select cssClass="w_combo" path="depId" items="${depidList}" itemValue="depId" itemLabel="depId" /></td>
       </tr>   
       <tr>
           <td><b>MSCID</b></td>
           <td> <form:select cssClass="w_combo" path="mscid" items="${mscidList}" itemValue="mscid" itemLabel="mscid"/></td>
       </tr>
        <tr>
           <td><b>REGION</b></td>
           <td> <form:select cssClass="w_combo" path="region" items="${regionList}" itemValue="region" itemLabel="region"/></td>
       </tr>
       <tr>
           <td></td>
           <td>
               <input type="submit" class="button" name="save" value="<spring:message code="button.save"/>" />
               <input type="button" value="<spring:message code="button.cancel"/>" onClick="history.back()">
           </td>
       </tr>
    </table>

</form:form>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/calendar.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/calendar_en.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/calendar_setup.js"></script>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/calendar-blue.css" />
<style>
    .error {
    	color: red;
    }
    
    .w_combo {
    width: 100px;
    }
  </style> 
<script type="text/javascript">
Calendar.setup({
    inputField		:	"launchDate",	// id of the input field
    ifFormat		:	"%d/%m/%Y",   	// format of the input field
    button			:   "chooseLaunchDate",  	// trigger for the calendar (button ID)
    singleClick		:   false					// double-click mode
});


function focusIt()
{
	if(document.checkform.bscid.value==""){
	  var mytext = document.getElementById("bscid");
	  mytext.focus();
	}
	else
		{
		var mytext = document.getElementById("vendor");
		  mytext.focus();
		}
		
}

onload = focusIt;

</script>