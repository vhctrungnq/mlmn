<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<style type="text/css">   
    #success p { margin: 0; padding: 1em; white-space: nowrap; } 
    #failed p { margin: 0; padding: 1em; white-space: nowrap; }
    .note{color:red;}
</style>
<title><fmt:message key="title.qualityNetwork.formUpload"/></title>
<content tag="heading"><fmt:message key="title.qualityNetwork.formUpload"/></content>
 	
<form:form method="post" action="upload.htm" enctype="multipart/form-data" >
	
	<table class="simple2">
		<tr>
			<td class="wid10 mwid140"><b><fmt:message key="hProvincesCode.file"/></b></td>
			<td><input class="button" type="file" name="file" size="90"/>&nbsp;
			<input class="button" type="submit" class="button" name="save" value="<fmt:message key="global.button.import"/>"/></td>
		</tr>
		<tr>
				<td>
				<b><fmt:message key="hProvincesCode.dinhDangFile"/></b>
				</td>
				<td>
					<ul style="list-style-type: none;">
						<li>File import là file (.xls)</li>
						<li>Dữ liệu trong file có dạng: 
							<code>
							&lt;<fmt:message key="qualityNetwork.groupName"/>&gt;<font color="red">(*)</font>,
							&lt;<fmt:message key="qualityNetwork.qualityCode"/>&gt;<font color="red">(*)</font>, 
							&lt;<fmt:message key="qualityNetwork.qualityName"/>&gt;<font color="red">(*)</font>, 
							&lt;<fmt:message key="qualityNetwork.qualityCondition"/>&gt;, 
							&lt;<fmt:message key="qualityNetwork.qualityValue"/>&gt;, 
							&lt;<fmt:message key="qualityNetwork.qualityUnit"/>&gt;, 
							&lt;<fmt:message key="qualityNetwork.ordering"/>&gt;.
							</code>
						</li>
						<li>File mẫu:&nbsp;<a style="color: blue; " title="QualityNetworkExample" href="${pageContext.request.contextPath}/upload/QualityNetworkExample.xls">QualityNetworkExample.xls</a>
						</li>
					</ul>
			</td>
		</tr>
	</table>
	<c:if test="${status != null}">
    	<div class="error">${status} ${statusExists}</div>
    </c:if>
    <c:if test="${fn:length(failedList) gt 0}">
    	<div id="failed">
    		<div><b>Dữ liệu quality network không hợp lệ  ( ${failNum}/${totalNum} )</b></div>
    		
    		<div style="max-height: 500px; overflow: auto;">
    			<display:table name="${failedList}" class="simple2" id="item1" requestURI="" export="false" pagesize="100">
							<display:column class="centerColumnMana" titleKey="global.list.STT" > <c:out value="${item1_rowNum}"/> </display:column>
							 
							<display:column property="groupName" titleKey="qualityNetwork.groupName" class="NOT_NULL"/>
							<display:column property="qualityCode" titleKey="qualityNetwork.qualityCode" class="NOT_NULL"/>
							<display:column property ="qualityName" titleKey="qualityNetwork.qualityName" class="NOT_NULL"/>
						    <display:column property="qualityCondition" titleKey="qualityNetwork.qualityCondition" />  
						    <display:column property ="qualityValue" titleKey="qualityNetwork.qualityValue" /> 
						    <display:column property ="qualityUnit" titleKey="qualityNetwork.qualityUnit" />    
						    <display:column property ="ordering" titleKey="qualityNetwork.ordering" class="rightColumnMana"/>
				</display:table>
			</div>
		</div>
	</c:if>
	<c:if test="${fn:length(successList) gt 0}">
    	<div id="success">
    		<div><b>Dữ liệu quality network hợp lệ  ( ${successNum}/${totalNum} )</b></div>
    		
    		<div style="max-height: 500px; overflow: auto;">
    			<display:table name="${successList}" class="simple2" id="item2" requestURI="" export="false" pagesize="700">
							<display:column class="centerColumnMana" titleKey="global.list.STT" > <c:out value="${item2_rowNum}"/> </display:column>
							<display:column property="groupName" titleKey="qualityNetwork.groupName" class="NOT_NULL"/>
							<display:column property="qualityCode" titleKey="qualityNetwork.qualityCode" class="NOT_NULL"/>
							<display:column property ="qualityName" titleKey="qualityNetwork.qualityName" class="NOT_NULL"/>
						    <display:column property="qualityCondition" titleKey="qualityNetwork.qualityCondition" />  
						    <display:column property ="qualityValue" titleKey="qualityNetwork.qualityValue" /> 
						    <display:column property ="qualityUnit" titleKey="qualityNetwork.qualityUnit" />    
						    <display:column property ="ordering" titleKey="qualityNetwork.ordering" class="rightColumnMana"/>	
				</display:table>
			</div>
		</div>
	</c:if>
		<table>
		<tr>
			<td >
               	<input class="button" type="button" value="<fmt:message key="global.button.back"/>" onClick='window.location="list.htm"'>
			</td>
		</tr>
	</table>
</form:form>
<script type="text/javascript">  
    $(function() {
    	$('#item2>tbody>tr').each(
    	    	 function(){
   					  ${highlight}
   						});

    	$('#item1>tbody>tr').each(
   	    	 function(){
  					  ${highlight}
  					});
		}); 
</script>
