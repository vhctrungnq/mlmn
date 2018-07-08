<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<title><spring:message code="header.title.m_cellUpload"/></title>

<content tag="heading"><spring:message code="header.title.m_cellUpload"/></content>
 	
<form:form method="post" action="upload.htm" enctype="multipart/form-data" >
	
	<table class="simple2">
		<tr>
			<td><b>File</b></td>
			<td><input type="file" name="file"/></td>
			<td style="color:gray">
				Định dạng file upload:
					<ul>
						<li>File upload là file (.csv)</li>
						<li>Dữ liệu trong file có dạng: <code>&lt;CELLID&gt;,&lt;CELLNAME&gt;,&lt;SITEID&gt;,&lt;SITENAME&gt;,&lt;VENDOR&gt;,&lt;MC_TYPE&gt;,&lt;CGI&gt;,&lt;AVG_MONTH_TRAFF&gt;,&lt;LAST_ACTIVE&gt;,</code></li>
						<li><code>&lt;LAUNCH_DATE&gt;,&lt;PROVINCE&gt;,&lt;REGION&gt;,&lt;DESCRIPTION&gt;,&lt;BSCID&gt;,&lt;DISTRICT&gt;</code></li>
					</ul>
			</td>
		</tr>
		<tr>
			<td>&nbsp;</td>
			<td colspan="2">
				<input type="submit" class="button" name="submit" value="<spring:message code="button.upload"/>"/>
				<input type="button" value="<spring:message code="button.back"/>" onClick="history.back()">
			</td>
		</tr>
	</table>
</form:form>