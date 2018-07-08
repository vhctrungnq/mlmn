<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<title>${titleF}</title>
<content tag="heading">${titleF}</content>

<form:form commandName="cTelnetServer" method="post" action="form.htm"> 
	<form:hidden path="id"/>
	<table class="simple2"> 
		
		<tr>
			<td style="width: 120px;"><b><fmt:message key="telnetServer.module"/></b><font color="red">(*)</font></td>
           <td>
				<form:select path="module" style="width: 165px;height:20px; padding-top: 0px;">
	           		<c:forEach var="item" items="${moduleList}">
						<c:choose>
			                <c:when test="${item.name == module}">
			                    <option value="${item.name}" selected="selected">${item.value}</option>
			                </c:when>
							<c:otherwise>
								<option value="${item.name}">${item.value}</option>
							</c:otherwise>
						</c:choose>
					</c:forEach>
				</form:select> 
			</td> 
      </tr> 
      
      <tr>
           <td><b><fmt:message key="telnetServer.ne"/></b><font color="red">(*)</font></td>
           <td>
           		<form:input type ="text" path="ne" rows="3" maxlength=""/>
           		<font color="red"><form:errors path="ne" cssClass="error"/></font>
           </td>
      </tr> 
      
      <tr>
           <td><b><fmt:message key="telnetServer.ipAddress"/></b><font color="red">(*)</font></td>
           <td>
           		<form:input type ="text" path="ipAddress" rows="3"/>
           		<font color="red"><form:errors path="ipAddress" cssClass="error"/></font>
           </td>
      </tr>
      
      <tr>
           <td><b><fmt:message key="telnetServer.telnetUser"/></b><font color="red">(*)</font></td>
           <td>
           		<form:input type = "text" path="telnetUser" rows="3"/>
           		<font color="red"><form:errors path="telnetUser" cssClass="error"/></font>
           </td>
      </tr>
      
      <tr>
           <td><b><fmt:message key="telnetServer.telnetPassword"/></b></td>
           <td>
           		<form:input type ="text" path="telnetPassword" rows="3"/>
           		<font color="red"><form:errors path="telnetPassword" cssClass="error"/></font>
           </td>
      </tr>
      
      <tr>
           <td><b><fmt:message key="telnetServer.telnetType"/></b></td>
           <td>
           		<form:input type ="text" path="telnetType" rows="3"/> 
           </td>
      </tr>
      
      <tr>
           <td><b><fmt:message key="telnetServer.status"/></b></td>
           <td><form:input type ="text" path="status" rows="3" maxlength="1"/>(Y/N)</td>
      </tr>
      
      <tr>
           <td><b><fmt:message key="telnetServer.loginTime"/></b></td>
           <td>
           		<form:input type ="text"  path="loginTime" maxlength="20" style="width:160px;" rows="3"/>
	   			 <img alt="calendar" title="Click to choose the start date" id="choosesDate" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>(yyyy-MM-dd HH-mm-ss)
           		<font color="red"><form:errors path="loginTime" cssClass="error"/></font>
           </td>
      </tr>
      <tr>
           <td><b><fmt:message key="telnetServer.description"/></b></td>
           <td>
           		<form:textarea type ="text" path="description" maxlength="500" style="width:160px;" cols="20" rows="3"/>
           		<font color="red"><form:errors path="description" cssClass="error"/></font>
           </td>
      </tr>
 		<tr>
           <td></td>
           <td colspan="5">
               <input type="submit" class="button" name="save" value="<fmt:message key="button.save"/>" />
           	   <input type="button" class="button" value="<fmt:message key="button.cancel"/>" onClick='window.location="list.htm"'>
					
           </td>
       </tr>
    </table>
</form:form>

<style>
    .error {
    	color: red;
    }
</style> 
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar_en.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar_setup.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/chosen.jquery.js" ></script>

<link rel="stylesheet" type="text/css" media="all" href="${pageContext.request.contextPath}/styles/calendar-blue.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/chosen.css"/>

<script type="text/javascript">
	
    Calendar.setup({
        inputField		:	"loginTime",	// id of the input field
        ifFormat		:	"%Y-%m-%d %H-%M-%S",   	// format of the input field
        button			:   "choosesDate",  	// trigger for the calendar (button ID)
        showsTime		:	true,
        singleClick		:   false					// double-click mode
    });
</script>
