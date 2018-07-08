<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<title>${titleU}</title>
<content tag="heading">${titleU}</content>

<form:form method="post" action="upload.htm" enctype="multipart/form-data" >
	
	<table class="simple2">
		<tr  style="height:20px;" >
			<td width="150px"><b><fmt:message key="cautruc.filepath"/> <font color="red">(*)</font></b></td>
	    	<td><input class="button" type="file" name="file" size="110" class="button"/>
				<input type="submit" id="upload" class="button" value="<fmt:message key="global.button.import"/>" />
			</td>
		</tr>
		<tr style="height:100px;">
	    		<td><b>
	    			<fmt:message key="global.FileExample"/></b>
	    		</td>
	    		<td>
	    			<ul>
	    				<li><fmt:message key="global.formatFile"/></li>
						<li><fmt:message key="global.formatData"/> &nbsp;<code style="font-size:12;">&lt;<fmt:message key="filePattern.filePattern"/>&gt;,&lt;<fmt:message key="filePattern.nodeType"/>&gt;,&lt;<fmt:message key="filePattern.rawTable"/>&gt;,&lt;<fmt:message key="filePattern.convertClass"/>&gt;,&lt;<fmt:message key="filePattern.subDir"/>&gt;,&lt;<fmt:message key="filePattern.nodePatternGroup"/>&gt;,&lt;<fmt:message key="filePattern.timePatternGroup"/>&gt;,&lt;<fmt:message key="filePattern.separator"/>&gt;,&lt;<fmt:message key="filePattern.commentChar"/>&gt;,&lt;<fmt:message key="filePattern.timePattern"/>&gt;,&lt;<fmt:message key="filePattern.importRule"/>&gt;,&lt;<fmt:message key="filePattern.status"/>&gt;</code></li>
						<li><fmt:message key="global.chuY1"/>
						<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<fmt:message key="filePattern.chuYimportRule"/>
						<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<fmt:message key="filePattern.chuYstatus"/>
						</li>
						<li><fmt:message key="global.file"/>&nbsp;<a href="${pageContext.request.contextPath}/upload/example/FilepatternExample.xls" title="FilePatternExample" style="color: blue; ">FilePatternExample.xls</a></li>
	    			</ul>
	    		</td>
	    	</tr>
		<tr style="height:50px;">
	    		<td><b><fmt:message key="cautruc.insetFile"/> </b></td>
	    		<td><p><font color="red">${errorContent}</font></p></td>
	    </tr>
	</table>
	<div style="width:100%;overflow:auto;max-height: 500px; ">
				<display:table name="${filePatternList}" id="filePattern" class="simple2"  requestURI="" pagesize="50" sort="external" defaultsort="1" export="true">
					<display:column property="filePattern" titleKey="filePattern.filePattern" />
					<display:column property="nodeType" titleKey="filePattern.nodeType" />
					<display:column property="rawTable" titleKey="filePattern.rawTable" />
					<display:column property="convertClass" titleKey="filePattern.convertClass"/>
					<display:column property="subDir" titleKey="filePattern.subDir"/>
					<display:column property="nodePatternGroupStr" titleKey="filePattern.nodePatternGroup"/>
					<display:column property="timePatternGroupStr" titleKey="filePattern.timePatternGroup"/>
					<display:column property="separator" titleKey="filePattern.separator"/>
					<display:column property="commentChar" titleKey="filePattern.commentChar"/>
					<display:column property="timePattern" titleKey="filePattern.timePattern"/>
					<display:column property="importRule" titleKey="filePattern.importRule"/>
	   				<display:column property="status" titleKey="filePattern.status"/>
				</display:table>
		</div>
		<table>
		<tr>
			<td>
               	<input class="button" type="button" value="Quay lại" onClick='window.location="list.htm"'>
			</td>
		</tr>
	</table>
</form:form>
