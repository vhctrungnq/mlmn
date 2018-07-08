<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<form:form commandName="w_working_processes" name="checkform" method="post" action="formProcesses.htm?id_work_mana=${w_working_processes.idWorks}"> 
	<div>
		<form:hidden path="id"/>
		<form:hidden path="idWorks"/>
	</div>
    <table class="simple2">
    	<tr>
           <td class="wid15 mwid100"><b><spring:message code="title.qLTienDoCongViec.title"/></b>&nbsp;<font color="red">(*)</font></td>
           <td class="wid35 mwid250">
           	<form:input path="title" maxlength="255" cssClass="wid90"/>&nbsp;<form:errors path="title" cssClass="error"/>
           </td>
           <td class="wid15 mwid160"><b><spring:message code="title.qLTienDoCongViec.assess"/></b></td>
           <td class="wid35">
           		<form:select path="assess" class="wid90" id="assess">
					<option value="">--Chọn--</option>
         				<c:forEach var="items" items="${sysParameterByCodeList}">
		              	<c:choose>
		                <c:when test="${items.value == tinhTrangCBB}">
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
           <td><b><spring:message code="title.qLTienDoCongViec.actualDate"/></b></td>
           <td>
           		<input type="text" id="actualDate" name="actualDate" value="${w_working_processes.actualDateStr}" class="wid30" maxlength="30"/>
           		<img alt="calendar" title="Click to choose the actual date" id="chooseActualDate" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>&nbsp;<form:errors path="actualDate" cssClass="error"/>
           </td>
           <td><b><spring:message code="title.qLTienDoCongViec.estimateDate"/></b></td>
           <td>
	           	<input type="text" id="estimateDate" name="estimateDate" value="${w_working_processes.estimateDateStr}" class="wid30" maxlength="30"/>
	           	<img alt="calendar" title="Click to choose the estimate date" id="chooseEstimateDate" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>&nbsp;<form:errors path="estimateDate" cssClass="error"/>
           </td>                
      </tr>
      <tr>
           <td><b><spring:message code="title.qLTienDoCongViec.content"/></b>&nbsp;<font color="red">(*)</font></td>
           <td>
           	<form:textarea path="content" cssClass="textareaCV90" maxlength="255"/>&nbsp;<form:errors path="content" cssClass="error"/>
           </td>
           <td><b><spring:message code="title.qLTienDoCongViec.remark"/></b>&nbsp;<font color="red">(*)</font></td>
           <td><form:textarea path="remark" cssClass="textareaCV90" maxlength="1000"/>&nbsp;<form:errors path="remark" cssClass="error"/></td>
      </tr>
       <tr>
           <td></td>
           <td colspan="3">
               <input class="button" type="submit" name="save" value="<spring:message code="button.save"/>" />
               <input class="button" type="button" value="<spring:message code="button.cancel"/>" onClick='addContent()'>
           </td>
       </tr>
    </table>

</form:form>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/calendar.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/calendar_en.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/calendar_setup.js"></script>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/calendar-blue.css" />
 
<script type="text/javascript">
Calendar.setup({
    inputField		:	"estimateDate",	// id of the input field
    ifFormat		:	"%d/%m/%Y %H:%M:%S",   	// format of the input field
    button			:   "chooseEstimateDate",  	// trigger for the calendar (button ID)
    showsTime		:	true,
    singleClick		:   false					// double-click mode
});

Calendar.setup({
    inputField		:	"actualDate",	// id of the input field
    ifFormat		:	"%d/%m/%Y %H:%M:%S",   	// format of the input field
    button			:   "chooseActualDate",  	// trigger for the calendar (button ID)
    showsTime		:	true,
    singleClick		:   false					// double-click mode
});
		

function focusIt()
{
  
  var estimateDateError = '<c:out value="${estimateDateError}"/>';
  var actualDateError = '<c:out value="${actualDateError}"/>';
  if(document.checkform.title.value == ""){
	  var mytext = document.getElementById("title");
	  mytext.focus();
	}
  else if(document.checkform.content.value == ""){
		  var mytext = document.getElementById("content");
		  mytext.focus();
	}
	else if(estimateDateError != "")
		{
			var mytext = document.getElementById("estimateDate");
		  mytext.focus();
		}
	else if(actualDateError != "")
		{
			var mytext = document.getElementById("actualDate");
		  	mytext.focus();
		}
	else
		{
			var mytext = document.getElementById("title");
		  	mytext.focus();
		}
}

onload = focusIt;

</script>