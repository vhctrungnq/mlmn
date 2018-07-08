<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<title>${title}</title>
<content tag="heading">${title}</content> 	
<form:form commandName="filter" method="post" action="list.htm">
<table class="form" style="width:100%;">
	<tr>
		<td style="width:100px;"><fmt:message key="importMapping.rawTable"/></td>
        <td style="width:150px;"><form:input path="rawTable" id="rawTable" name="rawTable" size="24" maxlength="30"/></td>
        <td style="width:70px; padding-left:5px;"><fmt:message key="importMapping.tableColumn"/></td>
        <td style="width:150px;"><form:input path="tableColumn" name="tableColumn" id="tableColumn" size="24" maxlength="30"/></td> 
		<td style="width:70px; padding-left:5px;"><fmt:message key="importMapping.filePattern"/></td>
        <td style="width:150px;"><form:input path="filePattern" id="filePattern" name="filePattern" size="24" maxlength="10"/></td> 
	</tr>
	<tr> 
		<td><fmt:message key="importMapping.fileColumnHeader"/></td>
        <td><form:input path="fileColumnHeader" name="fileColumnHeader" id="fileColumnHeader" size="24" maxlength="30"/></td>
        <td  style="padding-left:5px;"><fmt:message key="importMapping.status"/></td>
		<td>
                    <form:select path="status" onchange="xl()" style="width:160px;">
	                    <form:option value="" label="Tất cả"/>
	                    <form:option value="Y" label="Đang sử dụng"/>
	                    <form:option value="N" label="Không sử dụng"/>
	                </form:select>
        </td> 
		<td style="padding-left:5px;">
		<input type="submit" class="button" name="filter" id="submit" value="<fmt:message key="button.search"/>"/>
		<td>  
	    <td align="right">
            <a href="upload.htm"><fmt:message key="button.upload"/></a>&nbsp;
            <a href="form.htm"><fmt:message key="button.add"/></a>&nbsp;
        </td>
     </tr>
    </table>
</form:form>
<div>
<display:table name="${importMappingList}" id="importMapping" class="simple2" requestURI="" pagesize="50" sort="external" defaultsort="1" export="true">
		    <display:column class="centerColumnMana" titleKey="global.list.STT" style="width:30px;"> <c:out value="${importMapping_rowNum}"/> </display:column>
			<display:column property="rawTable" titleKey="importMapping.rawTable" sortable="true" sortName="RAW_TABLE"/>
		    <display:column property="tableColumn" titleKey="importMapping.tableColumn" sortable="true" sortName="TABLE_COLUMN"/>
		    <display:column property="filePattern" titleKey="importMapping.filePattern" sortable="true" sortName="FILE_PATTERN"/>
		    <display:column property="dataType" titleKey="importMapping.dataType" sortable="true" sortName="DATA_TYPE"/>
		    <display:column property="dataFormat" titleKey="importMapping.dataFormat" sortable="true" sortName="DATA_FORMAT"/>
		   	<display:column property="fileColumnHeader" titleKey="importMapping.fileColumnHeader" sortable="true" sortName="FILE_COLUMN_HEADER"/>
		    <display:column property="fileColumn" titleKey="importMapping.fileColumn" sortable="true" sortName="FILE_COLUMN"/>
		    <display:column property="status" titleKey="importMapping.status" sortable="true" sortName="STATUS"/>
		    <display:column titleKey="QUẢN LÝ" media="html" class="centerColumnMana" >
		    	<a href="form.htm?rawTable=${importMapping.rawTable}&tableColumn=${importMapping.tableColumn}&patternId=${importMapping.patternId}">Sửa</a>&nbsp;
		    	<a href="delete.htm?rawTable=${importMapping.rawTable}&tableColumn=${importMapping.tableColumn}&patternId=${importMapping.patternId}"
		    	  onclick="return confirm('Bạn có thực sự muốn xóa không?')">Xóa</a>
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
	var sub= document.getElementById("submit");
	sub.focus();
}
</script>


