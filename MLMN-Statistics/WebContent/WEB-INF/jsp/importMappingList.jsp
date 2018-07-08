<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<title>import mapping list</title>
<body class="section-5"/>
<content tag="heading">THÔNG TIN ÁNH XẠ GIỮA CÁC CỘT</content>

<form:form commandName="filter" method="post" action="list.htm">
<table style="border:0; width:100%;">
	<tr>
		<td>Bảng dữ liệu thô</td>
        <td><form:input path="rawTable" id="rawTable" name="rawTable" size="16" maxlength="30"/></td>
        <td>Tên cột trong bảng</td>
        <td><form:input path="tableColumn" name="tableColumn" id="tableColumn" size="16" maxlength="30"/></td> 
		<td>Tên File</td>
        <td><form:input path="filePattern" id="filePattern" name="filePattern" size="16" maxlength="10"/></td> 
	</tr>
	<tr> 
		<td>Tiêu đề cột</td>
        <td><form:input path="fileColumnHeader" name="fileColumnHeader" id="fileColumnHeader" size="16" maxlength="30"/></td>
        <td>Trạng thái</td>
		<td>
                    <form:select path="status" onchange="xl()">
	                    <form:option value="" label="Tất cả"/>
	                    <form:option value="Y" label="Đang sử dụng"/>
	                    <form:option value="N" label="Không sử dụng"/>
	                </form:select>
        </td> 
		<td>
		<input type="submit" class="button" name="filter" id="submit" value="Tìm kiếm"/><td>  
	    <td align="right">
            <a href="upload.htm">Upload file</a>&nbsp;
            <a href="form.htm">Thêm mới</a>&nbsp;
        </td>
     </tr>
    </table>
</form:form>
<br/>
<div  style="overflow: auto;">
<display:table name="${importMappingList}" id="importMapping" class="simple2" requestURI="" pagesize="100" sort="external" defaultsort="1" export="true">
		    <display:column property="rawTable" titleKey="BẢNG DỮ LIỆU THÔ"/>
		    <display:column property="tableColumn" titleKey="TÊN CỘT TRONG BẢNG"/>
		    <display:column property="dataType" titleKey="KIỂU DỮ LIỆU"/>
		    <display:column property="dataFormat" titleKey="ĐỊNH DẠNG DỮ LIỆU"/>
		    <display:column property="filePattern" titleKey="TÊN FILE" />
		    <display:column property="fileColumn" titleKey="THỨ TỰ CỘT"/>
		    <display:column property="fileColumnHeader" titleKey="TIÊU ĐỀ CỘT"/>
		    <display:column property="trangthai" titleKey="TRẠNG THÁI"/>
		    <display:column titleKey="QUẢN LÝ" media="html">
		    	<a href="form.htm?rawTable=${importMapping.rawTable}&tableColumn=${importMapping.tableColumn}&patternId=${importMapping.patternId}">Sửa</a>&nbsp;
		    	<a href="delete.htm?rawTable=${importMapping.rawTable}&tableColumn=${importMapping.tableColumn}&patternId=${importMapping.patternId}"
		    	  onclick="return confirm('Bạn có thực sự muốn xóa không?')">Xóa</a>
		    </display:column>
		    
		    <display:setProperty name="export.csv.include_header" value="true"/>
		    <display:setProperty name="export.excel.include_header" value="true"/>
	    	<display:setProperty name="export.csv.filename" value="ImportMappingList.csv"/>
		    <display:setProperty name="export.excel.filename" value="ImportMappingList.xls"/>
		</display:table>
</div>
<script type="text/javascript">
function xl(){
	var sub= document.getElementById("submit");
	sub.focus();
}
</script>


