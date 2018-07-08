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
						<li><fmt:message key="global.formatData"/> &nbsp;<code style="font-size:12;">&lt;<fmt:message key="importMapping.rawTable"/>&gt;,&lt;<fmt:message key="importMapping.tableColumn"/>&gt;,&lt;<fmt:message key="importMapping.filePattern"/>&gt;,&lt;<fmt:message key="importMapping.dataType"/>&gt;,&lt;<fmt:message key="importMapping.dataFormat"/>&gt;,&lt;<fmt:message key="importMapping.fileColumnHeader"/>&gt;,&lt;<fmt:message key="importMapping.fileColumn"/>&gt;</code></li>
						<li><fmt:message key="global.chuY1"/>
						<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<fmt:message key="importMapping.chuYstatus"/>
						</li>
						<li><fmt:message key="global.file"/>&nbsp;<a href="${pageContext.request.contextPath}/upload/example/ImportMappingExample.xls" title="ImportMappingExample" style="color: blue; ">ImportMappingExample.xls</a></li>
	    			</ul>
	    		</td>
	    	</tr>
		<tr style="height:50px;">
	    		<td><b><fmt:message key="cautruc.insetFile"/> </b></td>
	    		<td><p><font color="red">${errorContent}</font></p></td>
	    </tr>
	</table>
		<div style="width:100%;overflow:auto;max-height: 500px; ">
				<display:table name="${importMappingList}" class="simple2" id="item" >
				    <display:column title="STT" > <c:out value="${item_rowNum}"/> </display:column>
				    <display:column property="rawTable" titleKey="importMapping.rawTable"/>
				    <display:column property="tableColumn" titleKey="importMapping.tableColumn"/>
				    <display:column property="filePattern" titleKey="importMapping.filePattern"/>
				    <display:column property="dataType" titleKey="importMapping.dataType"/>
				    <display:column property="dataFormat" titleKey="importMapping.dataFormat"/>
				   	<display:column property="fileColumnHeader" titleKey="importMapping.fileColumnHeader"/>
				    <display:column property="fileColumn" titleKey="importMapping.fileColumn"/>
				    <display:column property="status" titleKey="importMapping.status"/>
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
