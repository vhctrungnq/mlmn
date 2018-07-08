<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<title>${title}</title>
<content tag="heading">${title}</content>

<form:form commandName="filter" method="post" action="list.htm">
<table style="width:100%;">
    <tr>
    	<td style="width:100px;">
    		<fmt:message key="fbRpProcess.fbType"/></td>
    	<td style="width:700px;">
	    	
    		<select name="fbType" id="fbType" style="width: 700px;">
	         	<option value=""><fmt:message key="global.All"/></option>
	          		<c:forEach var="item" items="${fbTypeList}">
					<c:choose>
		                <c:when test="${item.code == fbType}">
		                    <option value="${item.code}" selected="selected">${item.name}</option>
		                </c:when>
						<c:otherwise>
							<option value="${item.code}">${item.name}</option>
						</c:otherwise>
					</c:choose>
					</c:forEach>
				</select> 
    	</td>
    	<td style="width:100px; padding-left:5px;">
    		<fmt:message key="fbRpProcess.networkType"/>
    	</td>
    	<td style="width:170px;">
    		<select name="networkType" id="networkType" style="width: 160px;">
	         	<option value=""><fmt:message key="global.All"/></option>
	          		<c:forEach var="item" items="${networkTypeList}">
					<c:choose>
		                <c:when test="${item.name == networkType}">
		                    <option value="${item.name}" selected="selected">${item.value}</option>
		                </c:when>
						<c:otherwise>
							<option value="${item.name}">${item.value}</option>
						</c:otherwise>
					</c:choose>
					</c:forEach>
				</select> 
    	</td>
    	<td></td>
    	</tr>
    	<tr>
    	<td style="width:100px;"><fmt:message key="fbRpProcess.fbContent"/></td>
    	<td colspan="3" >
    		<form:input path="fbContent" id="fbContent" size="159"/>
		</td>
		<td></td>
		</tr>
		<tr>
		<td><fmt:message key="fbRpProcess.causedby"/></td>
    	<td colspan="3">
    		<form:input path="causedby" id="causedby" size="159"/>  	
		</td>
		<td style="padding-left:5px;">
        	<input type="submit" class="button" name="filter" value="Tìm kiếm"/>
        </td>
    </tr>
    <tr>
    			<td colspan="4"></td>
				<td class="ftsize12" align="right" >
		            <a href="form.htm"><fmt:message key="button.add"/></a>
		        </td>
	</tr>
</table>
</form:form>
<br/>
<div>
<display:table name="${fbProcessList}" id="fbRpProcess" class="simple2"requestURI="" pagesize="50" sort="external" defaultsort="1" export="true">
		    <display:column class="centerColumnMana" titleKey="global.list.No" style="width:30px;"> <c:out value="${fbRpProcess_rowNum}"/> </display:column>
			<display:column property="fbType" titleKey="fbRpProcess.fbType" sortable="true" sortName="FB_TYPE"/>        
		    <display:column property="fbContent" titleKey="fbRpProcess.fbContent" sortable="true" sortName="FB_CONTENT"/>
		    <display:column property="networkType" titleKey="fbRpProcess.networkType" sortable="true" sortName="NETWORK_TYPE"/>
		    <display:column property="causedby" titleKey="fbRpProcess.causedby" sortable="true" sortName="CAUSEDBY"/>
		    <display:column property="responseContent" titleKey="fbRpProcess.responseContent" sortable="true" sortName="RESPONSE_CONTENT"/>
		    <display:column titleKey="QUẢN LÝ" media="html" class="centerColumnMana" >
		    	<a href="form.htm?id=${fbRpProcess.id}">Sửa</a>&nbsp;
		    	<a href="delete.htm?id=${fbRpProcess.id}"
		    	   onclick="return confirm('Bạn có thực sự muốn xóa?')">Xóa</a>
		    </display:column>
		    <display:setProperty name="export.csv.include_header" value="true" />
			<display:setProperty name="export.excel.include_header" value="true" />
			<display:setProperty name="export.xml.include_header" value="true" />
			<display:setProperty name="export.xml.filename" value="${exportFileName}.xml" />
			<display:setProperty name="export.csv.filename" value="${exportFileName}.csv" />
			<display:setProperty name="export.excel.filename" value="${exportFileName}.xls" />
		</display:table>
</div>