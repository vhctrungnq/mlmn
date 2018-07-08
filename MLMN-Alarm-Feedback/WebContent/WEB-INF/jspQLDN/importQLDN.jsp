<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<title>Import QLDN</title>

<content tag="heading">Import QLDN</content>
 <form:form  method="post" action="upload.htm" enctype="multipart/form-data">
 	<table class="simple2">	
			<tr>
				<td width="150px"><b>Loại file import<font color="red">(*)</font></b></td>
				<td>
					<c:choose>
						<c:when test="${typeFile != '' }">
							<input type="hidden" id ="typeFile" name ="typeFile" value="${typeFile}">${typeFile}
						</c:when>
						<c:otherwise>
							<select name="typeFile" id="typeFile" style="width: 160px;height:20px; padding-top: 4px;">
					    	<option value="">Chọn loại file</option>
							<c:forEach var="item" items="${typeFileList}">
							<c:choose>
				                <c:when test="${item.name == typeFile}">
				                    <option value="${item.name}" selected="selected">${item.value}</option>
				                </c:when>
								<c:otherwise>
									<option value="${item.name}">${item.value}</option>
								</c:otherwise>
							</c:choose>
						</c:forEach>
					</select>
						</c:otherwise>
					</c:choose>
					    
				</td>
			</tr>
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
						<li><fmt:message key="global.file"/>
						<div id="fileEx" ></div>
						<%-- &nbsp;<a href="${pageContext.request.contextPath}/upload/example/MainFailures.xls" title="MainFailures" style="color: blue; ">MainFailures.xls</a> --%>
						</li>
						<li>File phải được borders</li>
	    			</ul>
	    		</td>
	    	</tr>
	    	
	    	<tr style="height:50px;">
	    		<td><b><fmt:message key="cautruc.insetFile"/> </b></td>
	    		<td><p><font color="red">${resultImport}</font></p></td>
	    	</tr>
		</table>
</form:form>

<script type="text/javascript">
$(document).ready(function(){
	var typeFile = $('#typeFile').val();
	if (typeFile!=null&&typeFile!='')
		{
			var mydiv = document.getElementById("fileEx");
			$.getJSON("${pageContext.request.contextPath}/import-qldn/loadFileExample.htm", {typeFile: $('#typeFile').val()}, function(j){
				 for (var i = 0; i < j.length; i++) {
					    var href="${pageContext.request.contextPath}/upload/example/"+j[i].remark;
					  	var aTag = document.createElement('a');
						aTag.setAttribute('href',href);
						aTag.innerHTML = j[i].remark;
						mydiv.appendChild(aTag);
					}
			});
		}
	$('#typeFile').change(function(){
		var mydiv = document.getElementById("fileEx");
		
		var typeFile = $('#typeFile').val();
		$.getJSON("${pageContext.request.contextPath}/import-qldn/loadFileExample.htm", {typeFile: $('#typeFile').val()}, function(j){
			 for (var i = 0; i < j.length; i++) {
				    var href="${pageContext.request.contextPath}/upload/example/"+j[i].remark;
				  	var aTag = document.createElement('a');
					aTag.setAttribute('href',href);
					aTag.innerHTML = j[i].remark;
					mydiv.appendChild(aTag);
				}
		});
	});

});


</script>