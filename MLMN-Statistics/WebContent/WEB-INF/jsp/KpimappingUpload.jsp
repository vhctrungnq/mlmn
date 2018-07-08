<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<title>KPI MAPPING</title>
<content tag="heading">IMPORT DỮ LIỆU KPI MAPPING</content>
 	
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
						<li>Dữ liệu trong file có dạng: <code>&lt;Tên báo cáo&gt;,&lt;Tên cột báo cáo&gt;,&lt;Công thức&gt;,&lt;Nhà sản xuất&gt;,&lt;Tên bảng&gt;,&lt;Tên cột&gt;,&lt;Công thức CSDL&gt;,&lt;Trạng thái&gt;,&lt;Ngày cập nhật&gt;,&lt;Ghi chú&gt;,&lt;Mạng&gt;,&lt;Phiên bản&gt;</code></li>
						<li>File mẫu:&nbsp;<a style="color: blue; " title="KpiMappingExample" href="${pageContext.request.contextPath}/upload/KpiMappingExample.xls">KpiMappingExample.xls</a>
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
				<display:table name="${KpiMaplists}" class="simple2" id="item" requestURI="" pagesize="100">
							<display:column title="STT" > <c:out value="${item_rowNum}"/> </display:column>
							<display:column property="reportName" titleKey="Tên Báo Cáo"/>
		    				<display:column property="reportNameColumn" titleKey="Tên cột báo cáo"/>
						    <display:column property="formula" titleKey="Công Thức" style="width:500px"/>
						    <display:column property="vendor" titleKey="Nhà Sản Xuất"/>
						    <display:column property="tableName" titleKey="Tên Bảng"/>
						    <display:column property="tableColumn" titleKey="Tên Cột" />
						    <display:column property="databaseFormula" titleKey="Công Thức CSDL"/>
						    <display:column property="trangthai" titleKey="TRẠNG THÁI"/>
						    <display:column property="updateDate" format="{0,date,dd/MM/yyyy}" titleKey="Ngày Cập Nhật"/>
						    <display:column property="description" titleKey="Ghi Chú"/>
						    <display:column property="network" titleKey="Mạng"/>
						    <display:column property="version" titleKey="Phiên Bản"/>
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
