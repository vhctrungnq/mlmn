<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<title>STP LIST</title>
<content tag="heading">IMPORT DỮ LIỆU STP</content>
 	
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
						<li>Dữ liệu trong file có dạng: <code>&lt;Stp&gt;,&lt;Sliid&gt;,&lt;Nhà sản xuất&gt;,&lt;Phiên bản phần cứng&gt;,&lt;Phiên bản phần mềm&gt;,&lt;Vị trí lắp đặt&gt;,&lt;Dung lượng báo hiệu 64K&gt;,&lt;Dung lượng báo hiệu HSL&gt;,&lt;Số STM&gt;,&lt;Số STEB&gt;,</code></li>
						<li>File mẫu:&nbsp;<a style="color: blue; " title="StpExample" href="${pageContext.request.contextPath}/upload/StpExample.xls">StpExample.xls</a>
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
				<display:table name="${StpList}" class="simple2" id="item" requestURI="" pagesize="15">
							<display:column class="centerColumnMana" titleKey="STT"> <c:out value="${item_rowNum}"/> </display:column>
							<display:column property="stpid" titleKey="STP"/>
						    <display:column property="sliid" titleKey="SLIID" />        
						    <display:column property="vendor" titleKey="NHÀ SẢN XUẤT"/>
						    <display:column property="hardwareVersion" titleKey="PHIÊN BẢN PHẦN CỨNG" />
						    <display:column property="softwareVersion" titleKey="PHIÊN BẢN PHẦN MỀM" />
						    <display:column property="location" titleKey="VỊ TRÍ LẮP ĐẶT"/>
						    <display:column property="type64k" titleKey="DUNG LƯỢNG BÁO HIỆU 64K"/>
						    <display:column property="typeHsl" titleKey="DUNG LƯỢNG BÁO HIỆU HSL"/>
						    <display:column property="noStm" titleKey="SỐ STM" />
						    <display:column property="noSteb" titleKey="SỐ STEB" />
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
