<!-- ThanhLV -->
<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<title>File Pattern List</title>
<body class="section-5"/>
<content tag="heading">DANH SÁCH MẪU FILE</content>

<form:form commandName="filter" method="post" action="list.htm">
<table width="100%" class="form">
    	<tr>
    		<td>Tên file</td><td><form:input path="filePattern" size="16" maxlength="200"/></td>
    		<td>Node mạng</td><td><form:input path="nodeType" size="16" maxlength="30"/></td>
    		<td>Lớp Convert</td><td><form:input path="convertClass" size="16" maxlength="100"/></td>   
    	</tr>
    	<tr>
    		<td>Tên bảng dữ liệu</td><td><form:input path="rawTable" size="16" maxlength="30"/></td>
    		<td>Trạng thái</td><td>  
	            	<form:select path="status" onchange="xl()">
	                    <form:option value="" label="Tất cả"/>
	                    <form:option value="Y" label="Đang sử dụng"/>
	                    <form:option value="N" label="Không sử dụng"/>
	                </form:select></td>
    		<td></td><td><input type="submit" class="button" id="submit" name="filter" value="Tìm kiếm"/></td>
	        <td align="right">
	            <a href="upload.htm">Upload file</a>&nbsp;
	            <a href="form.htm">Thêm mới</a>&nbsp;
	        </td>
    	</tr>    	
	</table>
</form:form>
<br>
<div  style="overflow: auto;">
<display:table name="${filePatternList}" id="filePattern" class="simple2" requestURI="" pagesize="100" sort="external" defaultsort="1" export="true">
			<display:column class="centerColumnMana" titleKey="STT"> <c:out value="${filePattern_rowNum}"/> </display:column>
			<display:column property="filePattern" title="TÊN FILE"/>
			<display:column property="nodeType" title="NODE MẠNG"/>
			<display:column property="rawTable" title=" BẢNG DỮ LIỆU"/>
			<display:column property="convertClass" title="LỚP CONVERT"/>
			<display:column property="trangthai" titleKey="TRẠNG THÁI"/>
			<display:column property="subDir" title="THƯ MỤC CON" headerClass="hide" class="hide" media="html"/>
			<display:column property="nodePatternGroup" title="STT LẤY NODE" headerClass="hide" class="hide" media="html"/>
			<display:column property="timePatternGroup" title="STT LẤY THỜI GIAN" headerClass="hide" class="hide" media="html"/>
			<display:column property="separator" title="KÝ TỰ PHÂN CÁCH" headerClass="hide" class="hide" media="html"/>
			<display:column property="commentChar" title="KÝ TỰ COMMENT" headerClass="hide" class="hide" media="html"/>
			<display:column property="timePattern" title="PATTERN ĐỊNH DẠNG TIME" headerClass="hide" class="hide" media="html"/>
			<display:column property="importRule" title="LOẠI IMPORT" headerClass="hide" class="hide" media="html"/>
		    <display:column title="QUẢN LÝ" media="html">
		    	<a href="form.htm?patternId=${filePattern.patternId}">Sửa</a>&nbsp;
		    	<a href="delete.htm?patternId=${filePattern.patternId}"
		    	  onclick="return confirm('Bạn có thực sự muốn xóa không?')">Xóa</a>
		    </display:column>
		    
		    <display:setProperty name="export.csv.include_header" value="true"/>
		    <display:setProperty name="export.excel.include_header" value="true"/>
		    <display:setProperty name="export.xml.include_header" value="false"/>
	    	<display:setProperty name="export.csv.filename" value="FilePatternList.csv"/>
		    <display:setProperty name="export.excel.filename" value="FilePatternList.xls"/>
		    <display:setProperty name="export.xml.filename" value="FilePatternList.xml"/>
		    
	</display:table>
</div>
<script type="text/javascript">
function xl(){
	var sub=document.getElementById("submit");
	sub.focus();
}
</script>
