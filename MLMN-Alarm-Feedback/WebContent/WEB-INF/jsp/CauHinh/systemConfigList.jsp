<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<title>${title}</title>
<content tag="heading">${title}</content>
<form:form commandName="filter" method="post" action="list.htm">
<table style="width:100%;">
    <tr>
    	<td style="width:100px;">
    		<fmt:message key="systemConfig.paramName"/></td>
    	<td style="width:150px;">
    		<input type ="text" value="${paramName}" name="paramName" id="paramName" width="80px" size="25">
    	</td>
    	<td style="width:50px; padding-left:5px;">
    		<fmt:message key="systemConfig.paramValue"/>
    	</td>
    	<td style="width:150px;">
    		<input type ="text" value="${paramValue}" name="paramValue" id="paramValue" width="80px" size="25">
    	</td>
    	<td style="width:100px; padding-left:5px;"><fmt:message key="systemConfig.configType"/></td>
    	<td style="width:150px;">
         	<select name="configType" id="configType" style="width: 160px;height:20px; padding-top: 4px;">
         	<option value=""><fmt:message key="global.All"/></option>
          		<c:forEach var="item" items="${configTypeList}">
				<c:choose>
	                <c:when test="${item.name == configType}">
	                    <option value="${item.name}" selected="selected">${item.value}</option>
	                </c:when>
					<c:otherwise>
						<option value="${item.name}">${item.value}</option>
					</c:otherwise>
				</c:choose>
				</c:forEach>
			</select> 
		</td>
		<td style="width:100px; padding-left:5px;">
        <input type="submit" class="button" name="filter" value="Tìm kiếm"/>
        </td>
        <td align="right">
            <a href="form.htm"><fmt:message key="button.add"/></a>&nbsp;
       	</td>
    </tr>
</table>
</form:form>
<br/>
<div>
<display:table name="${systemConfigList}" id="systemConfig" class="simple2"requestURI="" pagesize="50" sort="external" defaultsort="1" export="true">
		    <display:column class="centerColumnMana" titleKey="global.list.STT" style="width:30px;"> <c:out value="${systemConfig_rowNum}"/> </display:column>
			<display:column property="paramName" titleKey="systemConfig.paramName" sortable="true" sortName="PARAM_NAME"/>        
		    <display:column property="paramValue" titleKey="systemConfig.paramValue" sortable="true" sortName="PARAM_VALUE"/>
		    <display:column property="configType" titleKey="systemConfig.configType" sortable="true" sortName="CONFIG_TYPE"/>
		    <display:column property="description" titleKey="systemConfig.description" sortable="true" sortName="DESCRIPTION"/>
		    <display:column titleKey="QUẢN LÝ" media="html" class="centerColumnMana" >
		    	<a href="form.htm?id=${systemConfig.id}">Sửa</a>&nbsp;
		    	<a href="delete.htm?id=${systemConfig.id}"
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