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
						<li><fmt:message key="global.formatData"/> &nbsp;<code style="font-size:12;">&lt;<fmt:message key="alarmExtend.content"/>&gt;,&lt;<fmt:message key="alarmExtend.teamProcess"/>&gt;,&lt;<fmt:message key="alarmExtend.area"/>&gt;,&lt;<fmt:message key="alarmExtend.startTime"/>&gt;,&lt;<fmt:message key="alarmExtend.endTime"/>&gt;,&lt;<fmt:message key="alarmExtend.result"/>&gt;</code></li>
						<li><fmt:message key="global.chuY1"/>
						<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<fmt:message key="global.chuY2"/>
						</li>
						<li><fmt:message key="global.file"/>&nbsp;<a href="${pageContext.request.contextPath}/upload/example/HieuChinhMR.xls" title="HieuChinhMR" style="color: blue; ">HieuChinhMR.xls</a></li>
	    			</ul>
	    		</td>
	    	</tr>
	    	
	    	<tr style="height:50px;">
	    		<td><b><fmt:message key="cautruc.insetFile"/> </b></td>
	    		<td><p><font color="red">${errorContent}</font></p></td>
	    	</tr>
	    	</table>
				<div style="overflow-y: auto; overflow-x: hidden; max-height: 500px;">
	    			<display:table name="${alarmExtendList}" class="simple2" id="item" >
					  	<display:column property="alarm" titleKey="alarmExtend.content"/>
					  	<display:column property="teamProcess" titleKey="alarmExtend.teamProcess"/>
						<display:column property="area" titleKey="alarmExtend.area" />
						<display:column property="stimeStr"  titleKey="alarmExtend.startTime" />
						<display:column property="etimeStr"  titleKey="alarmExtend.endTime" />
						<display:column property="resultsProcess" titleKey="alarmExtend.result"/>
						
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