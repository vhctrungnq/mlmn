<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<title><fmt:message key="alConfigAlarmType.title"/></title>
<content tag="heading"><fmt:message key="alConfigAlarmType.title"/></content>
<div class="ui-tabs-panel">
	<form:form commandName="filter" method="get" action="list.htm">
		<table style="width:100%">
			<tr>
				<td style = "padding-left: 10px;width:70px;"><fmt:message key="alConfigAlarmType.filter.vendor"/></td>
				<td>
					<select name = "vendor" id="vendor" style="width: 140px;height:20px; padding-top: 0px;" onchange = "xl()">
					<option value=""><fmt:message key="global.All"/></option>
		           		<c:forEach var="item" items="${vendorList}">
							<c:choose>
				                <c:when test="${item.name == vendor}">
				                    <option value="${item.name}" selected="selected">${item.value}</option>
				                </c:when>
								<c:otherwise>
									<option value="${item.name}">${item.value}</option>
								</c:otherwise>
							</c:choose>
						</c:forEach>
					</select> 
				</td>
				
				<td style = "padding-left: 0px;width:70px;"><fmt:message key="alConfigAlarmType.filter.alarmType"/></td>
				<td>
					<select name = "alarmType" id="alarmType" style="width: 155px;height:20px; padding-top: 0px;" onchange = "xl()">
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
				
	            <td style = "padding-left: 0px;width:70px;"><fmt:message key="alConfigAlarmType.filter.rawTable"/></td>
	            <td>
	            	<input value = "${rawTable}" name="rawTable" id="rawTable" size="19"/>
	            </td>
	            
	           	<td style = "padding-left: 0px;width:90px;"><fmt:message key="alConfigAlarmType.filter.alarmBlockValue"/></td>
	           	<td> 
	           		<input value="${blockValue}" name="blockValue" id="blockValue" size="19"/>
	           	</td>
        	</tr>	         
	         <tr>
	         	<td style = "padding-left: 10px;width:70px;"><fmt:message key="alConfigAlarmType.filter.search"/></td>
	         	<td>			     
				     <select name="search" id="search" style="width: 140px;height:20px; padding-top: 0px;">
				     <option value=""><fmt:message key="global.All"/></option>
		          		<c:forEach var="item" items="${searchList}">
							<c:choose>
				                <c:when test="${item.name == search}">
				                    <option value="${item.name}" selected="selected">${item.value}</option>
				                </c:when>
								<c:otherwise>
									<option value="${item.name}">${item.value}</option>
								</c:otherwise>
							</c:choose>
						</c:forEach>
					</select> 
	           	</td>
	           	
	           	<td style = "padding-left: 0px;width:70px;"><fmt:message key="alConfigAlarmType.filter.node"/></td>
				<td>
					<select name = "node" id="node" style="width: 155px;height:20px; padding-top: 0px;" onchange = "xl()">
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
				
				<td style = "padding-left: 0px;width:70px;"><fmt:message key="alConfigAlarmType.filter.alarmInfo"/></td>
	           	<td> 
	           		<input value = "${alarmInfo}" name="alarmInfo" id="alarmInfo" size="19"/>
	           	</td> 
	           	
	         	<td style="padding-left: 0px;">
	            	<input class="button" type="submit" class="button" id="submit" value="<fmt:message key="button.search"/>"/>
	            </td> 
	            
	            <td align="right">
           			<a href="form.htm"><fmt:message key="button.add"/></a>&nbsp;
       			</td>         
	        </tr>
		</table>
	</form:form>
</div>
    			
<div  style="overflow: auto;">
	<display:table name="${alConfigAlarmType}" id="item" class="simple2" export="true" requestURI="" pagesize="50" sort="external" defaultsort="1">
	    <display:column class="centerColumnMana" titleKey="global.list.STT" style="width:20px;"> <c:out value="${item_rowNum}"/> </display:column> 
	    <display:column property="vendor" titleKey="alConfigAlarmType.filter.vendor" style = "width:80px;" sortable="true" sortName="VENDOR"/>   
	    <display:column property="alarmType" titleKey="alConfigAlarmType.filter.alarmType" style = "width:80px;" sortable="true" sortName="ALARM_TYPE"/>
	    <display:column property="rawTable" titleKey="alConfigAlarmType.filter.rawTable" style = "width:150px;" sortable="true" sortName="RAW_TABLE"/>        
	    <display:column property="blockColumnName" titleKey="alConfigAlarmType.filter.colunmName" style = "width:100px;" sortable="true" sortName="BLOCK_COLUMN_NAME"/>
	    <display:column property="blockValue" titleKey="alConfigAlarmType.filter.alarmBlockValue" style = "width:400px;" sortable="true" sortName="BLOCK_VALUE"/>   
	    <display:column property="alarmInfoColumnName" titleKey="alConfigAlarmType.filter.colunmInfo" style = "width:120px;" sortable="true" sortName="ALARM_INFO_COLUMN_NAME"/>   
	    <display:column property="alarmInfoValue" titleKey="alConfigAlarmType.filter.alarmInfo" style = "width:150px;" sortable="true" sortName="ALARM_INFO_VALUE"/>
	    <display:column property="search" titleKey="alConfigAlarmType.filter.search" style = "width:80px;" sortable="true" sortName="SEARCH"/>
	    <display:column property="node" titleKey="alConfigAlarmType.filter.node" style = "width:80px;" sortable="true" sortName="NODE"/>
	    <display:column property="description" titleKey="alConfigAlarmType.filter.description" style = "width:80px;" sortable="true" sortName="DESCRIPTION"/>
	    <display:column property="createDate" titleKey="alConfigAlarmType.filter.createDate" format="{0,date,dd/MM/yyyy HH:mm}" style = "width:80px;" sortable="true" sortName="CREATE_DATE"/>
	    <display:column titleKey="title.quanLy" media="html" class="centerColumnMana" style = "width:100px">
				    	<a href="form.htm?id=${item.id}"><fmt:message key="button.edit"/></a>&nbsp;
				    	<a href="delete.htm?id=${item.id}"
				    	   onclick="return confirm('<fmt:message key="messsage.confirm.delete"/>')"><fmt:message key="button.delete"/></a>&nbsp;
				    </display:column>
				       
	    <display:setProperty name="export.csv.filename" value="AlarmConfigType.csv"/>
		<display:setProperty name="export.excel.filename" value="AlarmConfigType.xls"/>
		<display:setProperty name="export.xml.filename" value="AlarmConfigType.xml"/>
	</display:table>
</div>

<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/calendar.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/calendar_en.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/calendar_setup.js"></script>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/calendar-blue.css" />

<script type="text/javascript">
	function xl(){
		var sub = document.getElementById("submit");
		sub.focus();
	}
</script>
