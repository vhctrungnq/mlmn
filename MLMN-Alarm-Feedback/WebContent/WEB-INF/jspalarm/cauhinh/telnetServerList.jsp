<%@ include file="/commons/taglibs.jsp"   %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<title><fmt:message key="cServersTelnet.title"/></title>
<content tag="heading"><fmt:message key="cServersTelnet.title"/></content> 	

<div class="ui-tabs-panel">
<form:form commandName="filter" method="post" action="list.htm">

	<table style = "width:100%">
		<tr>
			<td><fmt:message key="telnetServer.module"/></td>
			<td>
				<form:select path="module" style="width: 150px;height:20px; padding-top: 0px;" onchange="xl()">
					<option value=""><fmt:message key="global.All"/></option>
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
				
			<td><fmt:message key="telnetServer.vendor"/></td>
			<td>
				<form:select path="vendor" style="width: 150px;height:20px; padding-top: 0px;" onchange="xl()">
					<option value=""><fmt:message key="global.All"/></option>
	           		<c:forEach var="item" items="${nodeList}">
						<c:choose>
			                <c:when test="${item.name == vendor}">
			                    <option value="${item.name}" selected="selected">${item.value}</option>
			                </c:when>
							<c:otherwise>
								<option value="${item.name}">${item.value}</option>
							</c:otherwise>
						</c:choose>
					</c:forEach>
				</form:select> 
			</td>
					
			<td><fmt:message key="telnetServer.ne"/></td>
			<td>
				<form:input type ="text"  path="ne" width="90px"/>
			</td>
			
			<td><fmt:message key="telnetServer.telnetUser"/></td>
			<td>
				<form:input type ="text"  path="telnetUser" width="90px"/>
			</td> <td></td><td></td>
		</tr>
		<tr>
			<td><fmt:message key="telnetServer.network"/></td>
			<td>
				<form:select path="network" style="width: 150px;height:20px; padding-top: 0px;" onchange="xl()">
					<option value=""><fmt:message key="global.All"/></option>
	           		<c:forEach var="item" items="${typeList}">
						<c:choose>
			                <c:when test="${item.name == network}">
			                    <option value="${item.name}" selected="selected">${item.value}</option>
			                </c:when>
							<c:otherwise>
								<option value="${item.name}">${item.value}</option>
							</c:otherwise>
						</c:choose>
					</c:forEach>
				</form:select> 
			</td> 
			
			<td><fmt:message key="telnetServer.ipAddress"/></td>
			<td>
				<form:input type ="text"  path="ipAddress" width="90px"/>
			</td> 
			
			<td><fmt:message key="telnetServer.telnetType"/></td>
			<td>
				<form:input type ="text"  path="telnetType" width="90px"/>
			</td>
			
			<td><fmt:message key="telnetServer.status"/></td>
			<td>
				<form:input type ="text"  path="status" width="90px"/>
			</td>
			
			<td style="padding-left: 0px">
				<input class="button" type="submit" id="submit" class="button" value="<fmt:message key="button.search"/>" />
			</td>
			
			<td align="right">
            	<a href="form.htm"><fmt:message key="button.add"/></a>&nbsp;
       		</td>
		</tr>	
	</table>
</form:form>
</div>

 <div style="overflow: auto;">
	<display:table name="${serverTelnet}" class="simple2" id="item" requestURI="" pagesize="50" sort="external" defaultsort="1" export="true">
	  	<display:column class="centerColumnMana" titleKey="global.list.STT" style="width:20px;"> <c:out value="${item_rowNum}"/> </display:column>
	  	<display:column property="vendor" titleKey="telnetServer.vendor" sortable="true" sortName="VENDOR"/>
	  	<display:column property="network" titleKey="telnetServer.network" sortable="true" sortName="NETWORK"/>
	  	<display:column property="neType" titleKey="telnetServer.neType" sortable="true" sortName="NE_TYPE"/>
		<display:column property="ne" titleKey="telnetServer.ne" sortable="true" sortName="NE"/>
	  	<display:column property="ipAddress" titleKey="telnetServer.ipAddress" sortable="true" sortName="IP_ADDRESS"/>
	  	<display:column property="telnetUser" titleKey="telnetServer.telnetUser" sortable="true" sortName="TELNET_USER"/>
	  	<display:column property="telnetType" titleKey="telnetServer.telnetType" sortable="true" sortName="TELNET_TYPE"/>
	  	<display:column property="telnetCommand" titleKey="telnetServer.telnetCommand" sortable="true" sortName="TELNET_COMMAND"/>
	  	<display:column property="status" titleKey="telnetServer.status" sortable="true" sortName="STATUS"/>
	  	<display:column property="loginTime"  titleKey="telnetServer.loginTime" format="{0,date,dd/MM/yyyy HH:mm:ss}" sortable="true" sortName="LOGIN_TIME"/>
		<display:column property="module" titleKey="telnetServer.module" sortable="true" sortName="MODULE"/>
		
		<display:column titleKey="title.quanLy" media="html" class="centerColumnMana">
			<a href="form.htm?id=${item.id}"><fmt:message key="button.edit"/></a>&nbsp;
	 			<a href="delete.htm?id=${item.id}"
									onclick="return confirm('<fmt:message key="messsage.confirm.delete"/>')"><fmt:message key="button.delete"/></a>&nbsp;
			   			
	 		</display:column>
			    
	   	<display:setProperty name="export.csv.include_header" value="true" />
		<display:setProperty name="export.excel.include_header" value="true" />
		<display:setProperty name="export.xml.include_header" value="true" />
		<display:setProperty name="export.xml.filename" value="${exportFileName}.xml" />
		<display:setProperty name="export.csv.filename" value="${exportFileName}.csv" />
		<display:setProperty name="export.excel.filename" value="${exportFileName}.xls" />

	</display:table>
</div>

<script type="text/javascript">
function xl(){
	var sub = document.getElementById("submit");
	sub.focus();
}
</script> 