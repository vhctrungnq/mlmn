<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<title><spring:message code="header.title.m_cellForm"/></title>
<content tag="heading"><spring:message code="header.title.m_cellForm"/></content>

<form:form commandName="m_cell" name="checkform" id="myform" method="post" action="form.htm"> 
	
    <table class="simple2"> 
      <tr>
           <td width="200"><b>CELLID</b></td>
           <td>
           	<c:choose>
                <c:when test="${empty m_cell.cellid}">
                    <form:input path="cellid" maxlength="50"/>
                </c:when>
                <c:otherwise>
                    <b><i>${m_cell.cellid}</i></b><form:hidden path="cellid" />
                </c:otherwise>
            </c:choose>&nbsp;<form:errors path="cellid" cssClass="error"/>
           </td>
      </tr>
      <tr>
           <td><b>CELLNAME</b></td>
           <td><form:input path="cellname" maxlength="255"/></td>
      </tr>
      <tr>
           <td><b>SITEID</b></td>
           <td><form:input path="siteid" maxlength="50"/></td>
      </tr>
      <tr>
           <td><b>SITENAME</b></td>
           <td><form:input path="sitename" maxlength="50"/></td>
      </tr>
      <tr>
           <td><b><spring:message code="m_cellForm.lable.vendor"/></b></td>
           <td><form:input path="vendor" maxlength="50"/></td>
      </tr>
      <tr>
           <td><b>MC_TYPE</b></td>
           <td><form:input path="mcType" maxlength="50"/></td>                
      </tr>
      <tr>
           <td><b>CGI</b></td>
           <td><form:input path="cgi" maxlength="50"/></td>
      </tr>  
      <tr>
           <td><b>AVG_MONTH_TRAFF</b></td>
           <td><form:input path="avgMonthTraff" maxlength="12"/>&nbsp;<form:errors path="avgMonthTraff" cssClass="error"/></td>
      </tr>
      <tr>
           <td><b>LAST_ACTIVE</b></td>
           <td><form:input path="lastActive"/><img alt="calendar" title="Click to choose the launch active" id="chooseLastActive" style="cursor: pointer;" src="/VMSC2-Alarm/images/calendar.png"/>&nbsp;<form:errors path="lastActive" cssClass="error"/></td>
      </tr>   
      <tr>
           <td><b>LAUNCH_DATE</b></td>
           <td><form:input path="launchDate"/><img alt="calendar" title="Click to choose the launch date" id="chooseLaunchDate" style="cursor: pointer;" src="/VMSC2-Alarm/images/calendar.png"/>&nbsp;<form:errors path="launchDate" cssClass="error"/></td>
       </tr>
       <tr>
           <td><b>PROVINCE</b></td>
           <td><form:input path="province" maxlength="50"/></td>
       </tr>   
       <tr>
           <td><b>REGION</b></td>
           <td><form:input path="region" maxlength="50"/></td>
       </tr>
       <tr>
           <td><b>DESCRIPTION</b></td>
           <td><form:input path="description" maxlength="255"/></td>
       </tr>
        <tr>
           <td><b>BSCID</b></td>
           <td> <form:select path="bscid" items="${bscidList}" itemValue="bscid" itemLabel="bscid" /></td>
       </tr>
       <tr>
           <td><b>DISTRICT</b></td>
           <td> <form:select path="district" items="${districtList}" itemValue="district" itemLabel="district" /></td>
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
<script type="text/javascript" src="/VMSC2-Alarm-Feedback/scripts/calendar.js"></script>
<script type="text/javascript" src="/VMSC2-Alarm-Feedback/scripts/calendar_en.js"></script>
<script type="text/javascript" src="/VMSC2-Alarm-Feedback/scripts/calendar_setup.js"></script>
<link rel="stylesheet" type="text/css" href="/VMSC2-Alarm-Feedback/styles/calendar-blue.css" />
<style>
    .error {
    	color: red;
    }
  </style> 
<script type="text/javascript">
Calendar.setup({
    inputField		:	"lastActive",	// id of the input field
    ifFormat		:	"%d/%m/%Y",   	// format of the input field
    button			:   "chooseLastActive",  	// trigger for the calendar (button ID)
    singleClick		:   false					// double-click mode
});

Calendar.setup({
    inputField		:	"launchDate",	// id of the input field
    ifFormat		:	"%d/%m/%Y",   	// format of the input field
    button			:   "chooseLaunchDate",  	// trigger for the calendar (button ID)
    singleClick		:   false					// double-click mode
});

function focusIt()
{
	if(document.checkform.cellid.value==""){
	  var mytext = document.getElementById("cellid");
	  mytext.focus();
	}
	else
		{
		var mytext = document.getElementById("cellname");
		  mytext.focus();
		}
		
}

onload = focusIt;

</script>