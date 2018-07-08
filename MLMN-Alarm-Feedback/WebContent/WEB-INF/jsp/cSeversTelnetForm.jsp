<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<title>${titleF}</title>
<content tag="heading">${titleF}</content>

<form:form commandName="cServersTelnet" name="checkform" id="myform" method="post" action="form.htm"> 
	<form:hidden path="serverId"/>
	<table class="simple2"> 
      <tr>
           <td style="width: 120px;"><b><fmt:message key="cServerTelnet.serverName"/></b><font color="red">(*)</font></td>
           <td style="width: 200px;">
           		<form:input type ="text" path="serverName" maxlength="30" style="width:160px;" rows="3"/>
           		<font color="red"><form:errors path="serverName" cssClass="error"/></font>
           </td>
      </tr> 
      
      <tr>
           <td style="width: 120px;"><b><fmt:message key="cServerTelnet.ipAddress"/></b><font color="red">(*)</font></td>
           <td style="width: 200px;">
           		<form:input type ="text" path="ipAddress" maxlength="15" style="width:160px;" rows="3"/>
           		<font color="red"><form:errors path="ipAddress" cssClass="error"/></font>
           </td>
      </tr>
      
      <tr>
           <td style="width: 120px;"><b><fmt:message key="cServerTelnet.alarmId"/></b><font color="red">(*)</font></td>
           <td style="width: 200px;">
           		<form:input type ="text" path="alarmName" maxlength="20" style="width:160px;" rows="3"/>
           		<font color="red"><form:errors path="alarmName" cssClass="error"/></font>
           </td>
      </tr>
      
      <tr>
           <td style="width: 120px;"><b><fmt:message key="cServerTelnet.loginTime"/></b></td>
           <td style="width: 200px;">
           		<form:input type ="text"  path="loginTime" maxlength="20" style="width:160px;" rows="3"/>
	   			 <img alt="calendar" title="Click to choose the start date" id="choosesDate" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>(yyyy-MM-dd HH-mm-ss)
           		<font color="red"><form:errors path="loginTime" cssClass="error"/></font>
           </td>
      </tr>
      
      <tr>
           <td style="width: 120px;"><b><fmt:message key="cServerTelnet.type"/></b><font color="red">(*)</font></td>
           <td>
				<select name="type" id="type" style="width: 165px;height:20px; padding-top: 0px;">
	           		<c:forEach var="item" items="${typeList}">
						<c:choose>
			                <c:when test="${item.name == type}">
			                    <option value="${item.name}" selected="selected">${item.value}</option>
			                </c:when>
							<c:otherwise>
								<option value="${item.name}">${item.value}</option>
							</c:otherwise>
						</c:choose>
					</c:forEach>
				</select> 
			</td>
      </tr>
      
<%--       <tr>
           <td style="width: 120px;"><fmt:message key="cServerTelnet.command"/></td>
           <td style="width: 200px;">
           		<form:input type ="text" path="command" maxlength="18" style="width:160px;" rows="3"/>
           		<font color="red"><form:errors path="command" cssClass="error"/></font>
           </td>
      </tr>
      
      <tr>
           <td style="width: 120px;"><fmt:message key="cServerTelnet.function"/></td>
           <td style="width: 200px;">
           		<form:input type ="text" path="function" maxlength="18" style="width:160px;" rows="3"/>
           		<font color="red"><form:errors path="function" cssClass="error"/></font>
           </td>
      </tr>
      
      <tr>
           <td style="width: 120px;"><fmt:message key="cServerTelnet.deviceType"/></td>
           <td style="width: 200px;">
           		<form:input type ="text" path="deviceType" maxlength="18" style="width:160px;" rows="3"/>
           		<font color="red"><form:errors path="deviceType" cssClass="error"/></font>
           </td>
      </tr> --%>
      
      <tr>
           <td style="width: 120px;"><b><fmt:message key="cServerTelnet.telnetUser"/></b><font color="red">(*)</font></td>
           <td style="width: 200px;">
           		<form:input type ="text" path="telnetUser" maxlength="30" style="width:160px;" rows="3"/>
           		<font color="red"><form:errors path="telnetUser" cssClass="error"/></font>
           </td>
      </tr>
      
      <tr>
           <td style="width: 120px;"><b><fmt:message key="cServerTelnet.telnetPass"/></b><font color="red">(*)</font></td>
           <td style="width: 200px;">
           		<form:input type ="text" path="telnetPassword" maxlength="30" style="width:160px;" rows="3"/>
           		<font color="red"><form:errors path="telnetPassword" cssClass="error"/></font>
           </td>
      </tr>
      
      <tr>
           <td style="width: 120px;"><b><fmt:message key="cServerTelnet.alarmType"/></b><font color="red">(*)</font></td>
           <td>
				<select name="alarmType" id="alarmType" style="width: 165px;height:20px; padding-top: 0px;">
	           		<c:forEach var="item" items="${alarmTypeList}">
						<c:choose>
			                <c:when test="${item.name == alarmType}">
			                    <option value="${item.name}" selected="selected">${item.value}</option>
			                </c:when>
							<c:otherwise>
								<option value="${item.name}">${item.value}</option>
							</c:otherwise>
						</c:choose>
					</c:forEach>
				</select> 
			</td>
      </tr>
      <tr>
      	<td style="width: 90px;"><b><fmt:message key="filter.cServersTelnet.node"/></b><font color="red">(*)</font></td>
			<td>
				<select name="node" id="node" style="width: 165px;height:20px; padding-top: 0px;">
	           		<c:forEach var="item" items="${nodeList}">
						<c:choose>
			                <c:when test="${item.name == node}">
			                    <option value="${item.name}" selected="selected">${item.value}</option>
			                </c:when>
							<c:otherwise>
								<option value="${item.name}">${item.value}</option>
							</c:otherwise>
						</c:choose>
					</c:forEach>
				</select> 
			</td>    
      </tr>
      	<tr>
           <td style="width: 120px;"><b><fmt:message key="filter.cServersTelnet.description"/></b></td>
           <td style="width: 200px;">
           		<form:textarea type ="text" path="description" maxlength="1000" style="width:160px;" cols="20" rows="3"/>
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
