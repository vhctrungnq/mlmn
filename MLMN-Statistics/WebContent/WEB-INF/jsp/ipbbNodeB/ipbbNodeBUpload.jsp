<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<title>import dữ liệu link NodeB </title>
<content tag="heading">Import dữ liệu Link NodeB</content>
 	
<form:form method="post" action="upload.htm" enctype="multipart/form-data" >
	<input type="hidden" id="type" name="type" value="${type}"/>
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
						<li>Dữ liệu trong file có dạng: <code>&lt;Nodeb id&gt;<font color="red">(*)</font>,&lt;Tên đối tác&gt;,&lt;Tên trạm&gt;<font color="red">(*)</font>,&lt;Mã trạm&gt;,&lt;BandWidth&gt;,&lt;Ghi chú&gt;</code></li>
						<li>File mẫu:&nbsp;<a style="color: blue; " title="LinkNodeBExample" href="${pageContext.request.contextPath}/upload/LinkNodeBExample.xls">LinkNodeBExample.xls</a>
						</li>
					</ul>
			</td>
		</tr> 
	</table>
	<br></br>
	<c:if test="${fn:length(nodeBSuccessList) gt 0}">
		<div>
			<div>
				Số bản ghi import thành công ${importSuccess}/${total}
			</div>
			
			<div>
				<display:table name="${nodeBSuccessList}" class="simple2" id="item" requestURI="" pagesize="50" defaultsort="1">
					<display:column property="stt" titleKey="Excel line"/>
					<display:column property="nodebId" titleKey="Nodeb id"/>
					<display:column property="tenDoiTac" titleKey="Tên đối tác" />
				    <display:column property="tenTram" titleKey="Tên trạm"/> 
				    <display:column property="maTram" titleKey="Mã trạm" />
				    <display:column property="bw" titleKey="BandWidth" /> 
				    <display:column property="description" titleKey="Ghi chú"/>
				   
				</display:table>
			</div>
		</div>
	</c:if>
	<br></br>
	<c:if test="${fn:length(nodeBFailedList) gt 0}">
		<div>
			<div>
				Số bản ghi import thất bại ${importFailed}/${total}
			</div>
			<div>
				<display:table name="${nodeBFailedList}" class="simple2" id="item" requestURI="" pagesize="50" defaultsort="1">
					<display:column property="stt" titleKey="Excel line"/>
					<display:column property="nodebId" titleKey="Nodeb id"/>
					<display:column property="tenDoiTac" titleKey="Tên đối tác" />
				    <display:column property="tenTram" titleKey="Tên trạm"/> 
				    <display:column property="maTram" titleKey="Mã trạm" />
				    <display:column property="bw" titleKey="BandWidth" /> 
				    <display:column property="description" titleKey="Ghi chú"/>
				</display:table>
			</div> 
		</div> 
	</c:if>
	<br></br>
	<table class="simple2">
		<tr>
			<td colspan="3">
	              <input class="button" type="button" value="Quay lại" onClick='window.location="list.htm?type=${type}"'>
			</td>
		</tr>
	</table>
	
</form:form>
