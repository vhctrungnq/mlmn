<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<title>upload file patterns</title>
<body class="section-5"/>
<content tag="heading">UPLOAD CẤU HÌNH FILE PATTERNS</content>

<form:form method="post" action="upload.htm" enctype="multipart/form-data" >
	
	<table class="simple2">
		<tr>
			<td><b>File Patterns</b></td>
			<td><input type="file" name="file"/></td>
			<td style="color:gray">
				File format:
					<ul>
						<li>The data file is a csv file</li>
						<li>The line-format is: <code>&lt;FILE_PATTERN&gt;,&lt;NODE_TYPE&gt;,&lt;RAW_TABLE&gt;,&lt;CONVERT_CLASS&gt;,&lt;STATUS&gt;,&lt;SUB_DIR&gt;,&lt;NODE_PATTERN_GROUP&gt;,&lt;TIME_PATTERN_GROUP&gt;,&lt;SEPARATOR&gt;,&lt;COMMENT_CHAR&gt;,&lt;TIME_PATTERN&gt;,&lt;IMPORT_RULE&gt;</code></li>
					</ul>
			</td>
		</tr>
		<tr>
			<td>&nbsp;</td>
			<td colspan="2">
				<input type="submit" class="button" name="submit" value="Upload"/>
				<input type="button" value="Quay lại" onClick="history.back()">
			</td>
		</tr>
	</table>
</form:form>
