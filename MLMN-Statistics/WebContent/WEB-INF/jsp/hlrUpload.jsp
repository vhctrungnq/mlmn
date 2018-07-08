<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<title>HLR List</title>
<content tag="heading">IMPORT DỮ LIỆU HLR</content>
 	
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
						<li>Dữ liệu trong file có dạng: <code>&lt;Hlr&gt;,&lt;Nhà sản xuất&gt;,&lt;Phần cứng&gt;,&lt;Phần mềm&gt;,&lt;Vị trí lắp đặt&gt;</code></li>
						<li>File mẫu:&nbsp;<a style="color: blue; " title="HlrExample" href="/VMSC2-Statistics/upload/HlrExample.xls">HlrExample.xls</a>
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
				<display:table name="${HlrList}" class="simple2" id="item" requestURI="" pagesize="15">
							<display:column class="centerColumnMana" titleKey="STT"> <c:out value="${hlrid_rowNum}"/> </display:column>
							<display:column property="hlrid" titleKey="MSC"/>        
						    <display:column property="vendor" titleKey="Nhà sản xuất"/>
						    <display:column property="hardwareVersion" titleKey="Phần cứng"/>
						    <display:column property="softwareVersion" titleKey="Phần mềm"/>
						    <display:column property="location" titleKey="Vị trí lắp đặt"/>
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
