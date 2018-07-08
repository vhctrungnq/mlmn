<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<title>IMPORT DỮ LIỆU SUMMARY CONFIG</title>
<content tag="heading">CẤU HÌNH KPI MAPPING</content>
 	
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
						<li>Dữ liệu trong file có dạng: <code>&lt;Tên bảng summary&gt;,&lt;Tên cột&gt;,&lt;Kiểu dữ liệu&gt;,&lt;Bảng nguồn&gt;,&lt;công thức&gt;,&lt;Kiểu summary&gt;,&lt;Trạng thái&gt;,&lt;Ghi chú&gt;</code></li>
						<li>File mẫu:&nbsp;<a style="color: blue; " title="KpiExample" href="${pageContext.request.contextPath}/upload/KpiExampleExample.xls">KpiExampleExample.xls</a>
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
				<display:table name="${summaryConfigList}" class="simple2" id="item" requestURI="" pagesize="100">
						<display:column title="STT" > <c:out value="${item_rowNum}"/> </display:column>
						<display:column property="tableName" titleKey="TÊN BẢNG SUMMARY"/>
					    <display:column property="columnName" titleKey="TÊN CỘT"/>
					    <display:column property="dataType" titleKey="KIỂU DỮ LIỆU"/>
					    <display:column property="keyColumn" titleKey="KHÓA CHÍNH" headerClass="hide" class="hide"/>
					    <display:column property="tableSource" titleKey="BẢNG NGUỒN"/>
					    <display:column property="formula" titleKey="CÔNG THỨC" style="width:500px"/>
					    <display:column property="summaryType" titleKey="KIỂU SUMMARY"/>
					    <display:column property="trangthai" titleKey="TRẠNG THÁI"/>
					    <display:column property="description" titleKey="Ghi chú"/>
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
