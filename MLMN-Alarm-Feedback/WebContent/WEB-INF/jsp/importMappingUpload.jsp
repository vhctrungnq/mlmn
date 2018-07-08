<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<title>IMPORT DỮ LIỆU CẤU HÌNH THÔNG TIN ÁNH XẠ</title>
<content tag="heading">IMPORT DỮ LIỆU CẤU HÌNH THÔNG TIN ÁNH XẠ</content>
 	
<form:form method="post" action="upload.htm" enctype="multipart/form-data" >
	
	<table class="simple2">
		<tr>
			<td class="wid10 mwid140"><b>File</b></td>
			<td class="wid50"><input class="button" type="file" name="file" size="90"/></td>
			<td><input class="button" type="submit" class="button" name="save" value="Import file"/></td>
		</tr>
		<tr>
				<td>
				<b>Định dạng file import</b>
				</td>
				<td colspan="2">
					<ul>
						<li>File import là file (.xls)</li>
						<li>Dữ liệu trong file có dạng: <code>&lt;RawTable&gt;,&lt;TableColumn&gt;,&lt;DataType&gt;,&lt;DataFormat&gt;,&lt;FileColumn&gt;,&lt;FileColumnHeader&gt;,&lt;Trangthai&gt;</code></li>
						<li>File mẫu:&nbsp;<a style="color: blue; " title="ImportMappingExample" href="/MLMN-Statistics/upload/ImportMappingExample.xls">ImportMappingExample.xls</a>
						</li>
					</ul>
			</td>
		</tr>
		<tr>
			<td><b>Thông tin import</b></td>
			<td colspan="2"><p><font color="red">${errorContent}</font></p></td>
		</tr>
	</table>
		<div style="width:100%;overflow:auto; ">
				<display:table name="${importMappingList}" class="simple2" id="item" requestURI="" pagesize="15">
							    <display:column title="STT" > <c:out value="${item_rowNum}"/> </display:column>
							    <display:column property="rawTable" titleKey="BẢNG DỮ LIỆU THÔ"/>
							    <display:column property="tableColumn" titleKey="TÊN CỘT TRONG BẢNG"/>
							    <display:column property="dataType" titleKey="KIỂU DỮ LIỆU"/>
							    <display:column property="dataFormat" titleKey="ĐỊNH DẠNG DỮ LIỆU"/>
							    <display:column property="filePattern" titleKey="TÊN FILE" />
							    <display:column property="fileColumn" titleKey="THỨ TỰ CỘT"/>
							    <display:column property="fileColumnHeader" titleKey="TIÊU ĐỀ CỘT"/>
							    <display:column property="trangthai" titleKey="TRẠNG THÁI"/>
				</display:table>
		</div>
		<table class="simple2">
		<tr>
			<td colspan="3">
				
               	<input class="button" type="button" value="Quay lại" onClick='window.location="list.htm"'>
			</td>
		</tr>
	</table>
</form:form>
