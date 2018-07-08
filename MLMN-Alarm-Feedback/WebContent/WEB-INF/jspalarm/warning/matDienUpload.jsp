<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<title>${titleU}</title>

<content tag="heading">${titleU}</content>
 <form:form  method="post" action="upload.htm" enctype="multipart/form-data">
 <input type="hidden" name="region" id="region" value="${region}">
	<table class="simple2">	
	    	<tr style="height:20px;" >
	    		<td width="150px"><b><fmt:message key="cautruc.filepath"/> <font color="red">(*)</font></b></td>
	    		<td><input type="file" size="110" name="filePath" id="filePath" class="button" />
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
						<li><fmt:message key="global.formatData"/><code style="font-size:12;">&lt;<fmt:message key="matDien.content"/>&gt;,&lt;<fmt:message key="matDien.area"/>&gt;,&lt;<fmt:message key="matDien.userProcess"/>&gt;,&lt;<fmt:message key="matDien.startTime"/>&gt;,&lt;<fmt:message key="matDien.endTime"/>&gt;,&lt;<fmt:message key="matDien.result"/>&gt;</code></li>
						<li><fmt:message key="global.chuY1"/>
						</li>
						<li><fmt:message key="global.file"/>&nbsp;<a href="${pageContext.request.contextPath}/upload/example/MainFailures.xls" title="MainFailures" style="color: blue; ">MainFailures.xls</a></li>
	    			</ul>
	    		</td>
	    	</tr>
	    	
	    	<tr style="height:50px;">
	    		<td><b><fmt:message key="cautruc.insetFile"/> </b></td>
	    		<td><p><font color="red">${errorContent}</font></p></td>
	    	</tr>
	    	</table>
				<div style="overflow-y: auto; overflow-x: hidden; max-height: 500px;">
	    		<display:table name="${matDienList}" class="simple2" id="item" >
				  	<display:column class="centerColumnMana" titleKey="global.list.STT" style="width:30px;"> <c:out value="${item_rowNum}"/> </display:column>
					<display:column property="alarm" titleKey="matDien.content"style="max-width: 130px;word-wrap: break-word;"/>
				  	<display:column property="area"  titleKey="matDien.area" />
					<display:column property="userProcess"  titleKey="matDien.userProcess"style="max-width: 130px;word-wrap: break-word;"/>
					<display:column property="stimeStr"  titleKey="matDien.startTime" />
					<display:column property="etimeStr"  titleKey="matDien.endTime" />
					<display:column property="resultsProcess"  titleKey="matDien.result" style="max-width: 130px;word-wrap: break-word;"/>
				</display:table>
	    		</div>
		<table>
			<tr>
				<td>
	               	<input class="button" type="button" value="<fmt:message key="global.button.back"/>" onClick='window.location="list.htm"'>
				</td>
			</tr>
		</table>
</form:form>