<!-- ThanhLV -->
<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<title>${title}</title>
<content tag="heading">${title}</content> 	

<form:form commandName="filter" method="post" action="list.htm">
<table style="width:100%;" class="form">
    	<tr>
    		<td style="width:100px;"><fmt:message key="filePattern.filePattern"/></td>
    		<td style="width:150px;"><form:input path="filePattern" size="24" maxlength="200"/></td>
    		<td style="width:100px; padding-left:10px;"><fmt:message key="filePattern.nodeType"/></td>
    		<td style="width:150px;"><form:input path="nodeType" size="24" maxlength="30"/></td>
    		<td style="width:120px; padding-left:10px;"><fmt:message key="filePattern.convertClass"/></td>
    		<td><form:input path="convertClass" size="24" maxlength="100"/></td>   
    	</tr>
    	<tr>
    		<td><fmt:message key="filePattern.rawTable"/></td>
    		<td><form:input path="rawTable" size="24" maxlength="30"/></td>
    		<td   style="padding-left:10px;"><fmt:message key="filePattern.status"/></td>
    		<td>  
	            	<form:select path="status" onchange="xl()"  style="width:160px;">
	                    <form:option value="" label="Tất cả"/>
	                    <form:option value="Y" label="Đang sử dụng"/>
	                    <form:option value="N" label="Không sử dụng"/>
	                </form:select></td>
    		<td style="padding-left:10px;">
			<input type="submit" class="button" id="submit" value="<fmt:message key="button.search"/>"/>
			<td>  
		    <td align="right">
	            <a href="upload.htm"><fmt:message key="button.upload"/></a>&nbsp;
	            <a href="form.htm"><fmt:message key="button.add"/></a>&nbsp;
	        </td>
    	</tr>    	
	</table>
</form:form>

<div>
<display:table name="${filePatternList}" id="filePattern" class="simple2"  requestURI="" pagesize="50" sort="external" defaultsort="1" export="true">
			<display:column class="centerColumnMana" titleKey="STT"> <c:out value="${filePattern_rowNum}"/> </display:column>
			<display:column property="filePattern" titleKey="filePattern.filePattern" sortable="true" sortName="FILE_PATTERN" style="min-width:200px;max-width:200px;"/>
			<display:column property="nodeType" titleKey="filePattern.nodeType" sortable="true" sortName="NODE_TYPE" style="min-width:30px;max-width:30px;"/>
			<display:column property="rawTable" titleKey="filePattern.rawTable" sortable="true" sortName="RAW_TABLE" style="min-width:70px;max-width:70px;"/>
			<display:column property="convertClass" titleKey="filePattern.convertClass" sortable="true" sortName="CONVERT_CLASS" style="min-width:200px;max-width:200px;"/>
			<display:column property="status" titleKey="filePattern.status" sortable="true" sortName="STATUS" style="max-width:70px;"/>
			<display:column property="subDir" titleKey="filePattern.subDir" sortable="true" sortName="SUB_DIR" style="max-width:20px;"/>
			<display:column property="nodePatternGroup" titleKey="filePattern.nodePatternGroup" sortable="true" sortName="NODE_PATTERN_GROUP" style="max-width:30px;"/>
			<display:column property="timePatternGroup" titleKey="filePattern.timePatternGroup" sortable="true" sortName="TIME_PATTERN_GROUP" style="max-width:30px;"/>
			<display:column property="separator" titleKey="filePattern.separator" sortable="true" sortName="SEPARATOR" style="max-width:20px;"/>
			<display:column property="commentChar" titleKey="filePattern.commentChar" sortable="true" sortName="COMMENT_CHAR" style="max-width:20px;"/>
			<display:column property="timePattern" titleKey="filePattern.timePattern" sortable="true" sortName="TIME_PATTERN" style="max-width:20px;"/>
			<display:column property="importRule" titleKey="filePattern.importRule" sortable="true" sortName="IMPORT_RULE" style="max-width:20px;"/>
		    <display:column title="QUẢN LÝ" media="html">
		    	<a href="form.htm?patternId=${filePattern.patternId}">Sửa</a>&nbsp;
		    	<a href="delete.htm?patternId=${filePattern.patternId}"
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
	var sub=document.getElementById("submit");
	sub.focus();
}
</script>
