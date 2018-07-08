<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<title><fmt:message key="title.cFilePatterns.formUpload"/></title>
<content tag="heading"><fmt:message key="title.cFilePatterns.formUpload"/></content>
 	
<form:form method="post" action="upload.htm" enctype="multipart/form-data" >
	<table class="simple2">
		<tr>
			<td class="wid10 mwid140"><b>File</b></td>
			<td><input class="button" type="file" name="file" size="90"/>&nbsp;<input class="button" type="submit" class="button" name="save" value="<fmt:message key="global.button.import"/>"/></td>
		</tr>
		<tr>
				<td>
				<b>Định dạng file import</b>
				</td>
				<td colspan="2">
					<ul style="list-style-type: none;">
						<li>File import là file (.xls)</li>
						<li>Dữ liệu trong file có dạng: 
							<code>
								&lt;<fmt:message key="cFilePatterns.filePattern"/>&gt;,
								&lt;<fmt:message key="cFilePatterns.nodeType"/>&gt;,
								&lt;<fmt:message key="cFilePatterns.rawTable"/>&gt;,
								&lt;<fmt:message key="cFilePatterns.convertClass"/>&gt;,
								&lt;<fmt:message key="cFilePatterns.subDir"/>&gt;,
								&lt;<fmt:message key="cFilePatterns.nodePatternGroup"/>&gt;,
								&lt;<fmt:message key="cFilePatterns.timePatternGroup"/>&gt;,
								&lt;<fmt:message key="cFilePatterns.separator"/>&gt;,
								&lt;<fmt:message key="cFilePatterns.commentChar"/>&gt;,
								&lt;<fmt:message key="cFilePatterns.timePattern"/>&gt;,
								&lt;<fmt:message key="cFilePatterns.importRule"/>&gt;,
								&lt;<fmt:message key="cFilePatterns.status"/>&gt;
							</code>
						</li>
						<li>File mẫu:&nbsp;<a style="color: blue; " title="FilepatternExample" href="${pageContext.request.contextPath}/upload/FilepatternExample.xls">FilepatternExample.xls</a>
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
				<display:table name="${filePatternList}" class="simple2" id="item" requestURI="" pagesize="15">
							<display:column class="centerColumnMana" titleKey="STT"> <c:out value="${item_rowNum}"/> </display:column>
							<display:column property="filePattern" titleKey="cFilePatterns.filePattern"/>
							<display:column property="nodeType" titleKey="cFilePatterns.nodeType"/>
							<display:column property="rawTable" titleKey="cFilePatterns.rawTable"/>
							<display:column property="convertClass" titleKey="cFilePatterns.convertClass"/>
							<display:column property="subDir" titleKey="cFilePatterns.subDir"/>
							<display:column property="nodePatternGroup" titleKey="cFilePatterns.nodePatternGroup"/>
							<display:column property="timePatternGroup" titleKey="cFilePatterns.timePatternGroup"/>
							<display:column property="separator" titleKey="cFilePatterns.separator"/>
							<display:column property="commentChar" titleKey="cFilePatterns.commentChar"/>
							<display:column property="timePattern" titleKey="cFilePatterns.timePattern"/>
							<display:column property="importRule" titleKey="cFilePatterns.importRule"/>
							<display:column property="trangthai" titleKey="cFilePatterns.status"/>
				</display:table>
		</div>
		<table class="simple2">
		<tr>
			<td colspan="3">
				
               	<input class="button" type="button" value="<fmt:message key="global.button.back"/>" onClick='window.location="list.htm"'>
			</td>
		</tr>
	</table>
</form:form>
