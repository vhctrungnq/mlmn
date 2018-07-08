<%@ include file="/commons/taglibs.jsp"   %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<title><fmt:message key="cServersTelnet.title"/></title>
<content tag="heading"><fmt:message key="cServersTelnet.title"/></content> 	

<div class="ui-tabs-panel">
<form:form commandName="filter" method="post" action="list.htm">

	<table style = "width:95%">
		<tr>	
			<td style="padding-left: 20px;width: 50px;"><fmt:message key="filter.cServersTelnet.node"/></td>
			<td>
				<select name="node" id="node" style="width: 125px;height:20px; padding-top: 0px;" onchange="xl()">
					<option value=""><fmt:message key="global.All"/></option>
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
					
			<td style="padding-left: 20px;width:90px;"><fmt:message key="filter.cServersTelnet.serverName"/></td>
			<td>
				<input type ="text"  value="${serverName}" name="serverName" id="serverName" size="15" width="90px">
			</td>
			
			<td style="padding-left: 20px;width: 90px;"><fmt:message key="filter.cServersTelnet.telnetUser"/></td>
			<td>
				<input type ="text"  value="${telnetUser}" name="telnetUser" id="telnetUser" size="15" width="90px">
			</td>
			
			<td></td><td></td>
			
<%-- 			<td style="padding-left: 30px;width: 90px;"><fmt:message key="filter.cServersTelnet.command"/></td>
			<td>
				<input type ="text"  value="${command}" name="command" id="command" size="15" maxlength="19" width="90px">
			</td> --%>	
			
		</tr>
		<tr>
			<td style="padding-left: 20px;width: 90px;"><fmt:message key="filter.cServersTelnet.type"/></td>
			<td>
				<select name="type" id="type" style="width: 125px;height:20px; padding-top: 0px;" onchange="xl()">
					<option value=""><fmt:message key="global.All"/></option>
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
			
			<td style="padding-left: 20px;width: 90px;"><fmt:message key="filter.cServersTelnet.alarmType"/></td>
			<td>
				<select name="alarmType" id="alarmType" style="width: 185px;height:20px; padding-top: 0px;" onchange="xl()">
					<option value=""><fmt:message key="global.All"/></option>
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
			
			<td style="padding-left: 20px;width: 90px;"><fmt:message key="filter.cServersTelnet.alarmId"/></td>
			<td>
				<input type ="text"  value="${alarmId}" name="alarmId" id="alarmId" size="15" width="90px">
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
	<display:table name="${cSeversTelnet}" class="simple2" id="item" requestURI="" pagesize="50" sort="external" defaultsort="1" export="true">
	  	<display:column class="centerColumnMana" titleKey="global.list.STT" style="width:20px;"> <c:out value="${item_rowNum}"/> </display:column>
	  	<display:column property="node" titleKey="filter.cServersTelnet.node" sortable="true" sortName="NODE"/>
		<display:column property="serverName" titleKey="filter.cServersTelnet.serverName" sortable="true" sortName="SERVER_NAME"/>
	  	<display:column property="ipAddress" titleKey="filter.cServersTelnet.ipAddress" sortable="true" sortName="IP_ADDRESS"/>
	  	<display:column property="telnetUser" titleKey="filter.cServersTelnet.telnetUser" sortable="true" sortName="TELNET_USER"/>
	  	<display:column property="loginTime"  titleKey="filter.cServersTelnet.loginTime" format="{0,date,dd/MM/yyyy HH:mm}" sortable="true" sortName="LOGIN_TIME"/>
		<display:column property="alarmName" titleKey="filter.cServersTelnet.alarmId" sortable="true" sortName="ALARM_NAME"/>
		<display:column property="type" titleKey="filter.cServersTelnet.type" sortable="true" sortName="TYPE"/>	
		<%-- <display:column property="function" titleKey="filter.cServersTelnet.function" sortable="true" sortName="FUNCTION"/>
		<display:column property="deviceType" titleKey="filter.cServersTelnet.deviceType" sortable="true" sortName="DEVICE_TYPE"/> --%>
		<display:column property="alarmType" titleKey="filter.cServersTelnet.alarmType" sortable="true" sortName="ALARM_TYPE"/>
		<display:column property="description"  titleKey="filter.cServersTelnet.description" sortable="true" sortName="DESCRIPTION"/>
		<%-- <display:column property="command" titleKey="filter.cServersTelnet.command" sortable="true" sortName="COMMAND"/> --%>
		
		<display:column titleKey="title.quanLy" media="html" class="centerColumnMana">
			<a href="form.htm?serverId=${item.serverId}"><fmt:message key="button.edit"/></a>&nbsp;
	 			<a href="delete.htm?serverId=${item.serverId}"
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

