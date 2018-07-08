<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<title><spring:message code="header.title.contentUpload"/></title>

<content tag="heading"><spring:message code="header.title.contentUpload"/></content>
 	
<form:form method="post" action="uploadProcesses.htm?" enctype="multipart/form-data" >
	
	<table class="simple2">
		<tr>
			<td><b>File</b></td>
			<td><input class="button" type="file" name="file"/></td>
			<td style="color:gray">
				Định dạng file upload:
					<ul>
						<li>File upload là file (.csv)</li>
						<li>Dữ liệu trong file có dạng: <code>&lt;TEN_CONG_VIEC&gt;,&lt;NOI_DUNG&gt;,&lt;NGUOI_GIAO_VIEC&gt;,&lt;NGUOI_CHU_TRI&gt;,&lt;NGUOI_NHAN_VIEC&gt;</code></li>
					</ul>
			</td>
		</tr>
		<tr>
			<td>&nbsp;</td>
			<td colspan="2">
				<input class="button" type="submit" name="submit" value="<spring:message code="button.upload"/>"/>
				<input class="button" type="button" value="<spring:message code="button.back"/>" onClick="history.back()">
			</td>
		</tr>
	</table>
</form:form>