<%@ include file="/includes/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<title>Cấu hình router</title>
<content tag="heading">UPLOAD CẤU HÌNH ROUTER</content>

<form:form method="post" action="upload.htm"
	enctype="multipart/form-data">

	<table class="simple2">
		<tr>
			<td class="wid10 mwid140"><b><fmt:message
						key="qLNguoiDung.file" /></b></td>
			<td class="wid50"><input class="button" type="file" name="file"
				size="90" /></td>
			<td><input class="button" type="submit" class="button"
				name="save" value="<fmt:message key="global.button.import"/>" /></td>
		</tr>
		<tr>
			<td><b><fmt:message key="qLNguoiDung.dinhDangFile" /></b></td>
			<td colspan="2">
				<ul style="list-style-type: none;">
					<li>File import là file (.xls)</li>
					<li>Dữ liệu trong file có dạng: <code>
							&lt;Router name&gt;<font color="red">(*)</font>, 
							&lt;Router id&gt;<font color="red">(*)</font>,
							&lt;Device type&gt;, 
							&lt;Function&gt;,
							&lt;OAM&gt;, 
							&lt;Id&gt;,
						</code></li>
					<li>File mẫu:&nbsp;<a style="color: blue;"
						title="RouterExample"
						href="${pageContext.request.contextPath}/upload/example/RouterExample.xls">RouterExample.xls</a>
					</li>
				</ul>
			</td>
		</tr>
		<tr>
			<td><b><fmt:message key="qLNguoiDung.thongTinImport" /></b></td>
			<td colspan="2"><p>
					<font color="red">${errorContent}</font>
				</p></td>
		</tr>
	</table>
	<div style="width: 100%; overflow: auto;">
		<display:table name="${routerList}" class="simple2" id="item"
			requestURI="" pagesize="15">
			<display:column title="STT"><c:out value="${item_rowNum}" /></display:column>
			<display:column property="routerName" titleKey="Router name" />
			<display:column property="deviceType" titleKey="Device type" />
			<display:column property="function" titleKey="Function" />
			<display:column property="routerId" titleKey="Router id" />
			<display:column property="oam" titleKey="OAM" />
		</display:table>
	</div>
	<table class="simple2">
		<tr>
			<td colspan="3"><input type="button" value="Quay lại" onClick="history.back()" class="button"></td>
		</tr>
	</table>
</form:form>
