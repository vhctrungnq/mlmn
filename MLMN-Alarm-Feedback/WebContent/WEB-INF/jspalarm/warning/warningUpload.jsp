<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<title>${titleWarningUpload}</title>

<content tag="heading">${titleWarningUpload}</content>
 <form:form  method="post" action="upload.htm" enctype="multipart/form-data">
<input type="hidden" name="warningTp" id="warningTp" value="${warningTp}">
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
						<li><fmt:message key="global.formatData"/>&nbsp;<code style="font-size:12;">&lt;<fmt:message key="warning.warningName"/>&gt;,&lt;<fmt:message key="warning.mscid"/>&gt;,&lt;<fmt:message key="warning.alarmInfo"/>&gt;,&lt;<fmt:message key="warning.startTime"/>&gt;,&lt;<fmt:message key="warning.endTime"/>&gt;,&lt;<fmt:message key="warning.causes"/>&gt;,&lt;<fmt:message key="warning.methodTreatment"/>&gt;,&lt;<fmt:message key="warning.teamProcess"/>&gt;</code></li>
						<li><fmt:message key="global.chuY1"/>
						<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<fmt:message key="global.chuY2"/>
						</li>
						<li><fmt:message key="global.file"/>&nbsp;<a href="${pageContext.request.contextPath}/upload/example/CanhBao.xls" title="CanhBao" style="color: blue; ">CanhBao.xls</a></li>
	    			</ul>
	    		</td>
	    	</tr>
	    	
	    	<tr style="height:50px;">
	    		<td><b><fmt:message key="cautruc.insetFile"/> </b></td>
	    		<td><p><font color="red">${errorContent}</font></p></td>
	    	</tr>
	    	</table>
				<div style="overflow-y: auto; overflow-x: hidden; max-height: 500px;">
	    			<display:table name="${warningList}" class="simple2" id="item" >
					  	<display:column property="alarm" titleKey="warning.warningName"/>
					  	<display:column property="system" titleKey="warning.mscid"/>
						<display:column property="alarmInfo" titleKey="warning.alarmInfo"/>
						<display:column property="stimeStr"  titleKey="warning.startTime" />
						<display:column property="etimeStr"  titleKey="warning.endTime" />
						<display:column property="causeby"  titleKey="warning.causes"/>
						<display:column property="teamProcess"  titleKey="warning.teamProcess"/>
						<display:column property="actionProcess"  titleKey="warning.methodTreatment"/>
					</display:table>
	    		</div>
		<table>
			<tr>
				<td>
	               	<input class="button" type="button" value="<fmt:message key="global.button.back"/>" onClick='window.location="list.htm?warningTp=${warningTp}"'>
				</td>
			</tr>
		</table>
</form:form>