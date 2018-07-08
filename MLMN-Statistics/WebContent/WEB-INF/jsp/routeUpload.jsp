<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<title>ROUTE LIST</title>
<content tag="heading">IMPORT DỮ LIỆU ROUTE</content>
 	
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
						<li>Dữ liệu trong file có dạng: <code>&lt;Route&gt;,&lt;Điểm đầu&gt;,&lt;Điểm cuối&gt;,&lt;Tên Route&gt;,&lt;Tên thiết bị&gt;</code></li>
						<li>File mẫu:&nbsp;<a style="color: blue; " title="RouteExample" href="${pageContext.request.contextPath}/upload/RouteExample.xls">RouteExample.xls</a>
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
				<display:table name="${RouteList}" class="simple2" id="item" requestURI="" pagesize="15">
							<display:column class="centerColumnMana" titleKey="STT"> <c:out value="${item_rowNum}"/> </display:column>
							<display:column property="routeid" title="ROUTEID"/>
							<display:column property ="routename" title="TÊN ROUTE" />
							<display:column property="fromMscid" title="ĐIỂM ĐẦU"/>
						    <display:column property="toMscid" title="ĐIỂM CUỐI"/>
						    <display:column property="dev" title="TÊN THIẾT BỊ" />
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
