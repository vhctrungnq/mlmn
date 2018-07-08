<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<title>IMPORT DỮ LIỆU IPBACKBONE</title>
<content tag="heading">IMPORT DỮ LIỆU IPBACKBONE</content>
 	
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
						<li>Dữ liệu trong file có dạng: <code>&lt;Direction&gt;,&lt;Link&gt;,&lt;BandWidth&gt;,&lt;Pha&gt;,&lt;Ghi chú&gt;</code></li>
						<li>File mẫu:&nbsp;<a style="color: blue; " title="IpbbExample" href="${pageContext.request.contextPath}/upload/IpbbExample.xls">IpbbExample.xls</a>
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
				<display:table name="${IpbbBwList}" class="simple2" id="item" requestURI="" pagesize="100">
							<display:column title="STT" > <c:out value="${item_rowNum}"/> </display:column>
							<display:column property="direction" titleKey="Direction"/>
						    <display:column property="link" titleKey="Link"/>
						    <display:column property="bw" titleKey="BandWidth" />
						    <display:column property="pha" titleKey="Pha"/>
						    <display:column property="localId" titleKey="LocalId"/>
						    <display:column property="locationName" titleKey="LocationName"/>
						    <display:column property="ip" titleKey="IP"/>
						    <display:column property="dept" titleKey="Dept"/>
						    <display:column property="team" titleKey="Team"/>
						    <display:column property="subTeam" titleKey="SubTeam"/>
						    <display:column property="diemDau" titleKey="Điểm đầu"/>
						    <display:column property="diemCuoi" titleKey="Điểm cuối"/>
						    <display:column property="donViQuanLy" titleKey="Đơn vị quản lý"/>
						    <display:column property="doiTacTruyenDan" titleKey="Đối tác truyền dẫn"/>
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
