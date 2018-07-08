<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
	<!-- Danh muc tai san -->
<style type="text/css">   
 	#success { overflow: auto; overflow-y: hidden; }  
    #success p { margin: 0; padding: 1em; white-space: nowrap; }
    
    #failed { overflow: auto; overflow-y: hidden; }  
    #failed p { margin: 0; padding: 1em; white-space: nowrap; }
    .note{color:red;}
</style>

<title>${titleU}</title>
<content tag="heading">${titleU}</content>
 <form:form  method="post" action="upload.htm" enctype="multipart/form-data">
	<table class="simple2">	
    	<tr style="height:20px;" >
    		<td width="150px"><b><fmt:message key="cautruc.filepath"/> <font color="red">(*)</font></b></td>
    		<td><input type="file" size="110" name="filePath" id="filePath" class="button" />
    			<input type="submit" name="upload" id="upload" class="button" value="<fmt:message key="global.button.import"/>" />
    		</td>
    	</tr>
    	<tr>
    		<td><b>
    			<fmt:message key="global.FileExample"/></b>
    		</td>
    		<td>
    		<ul>
    			<li><fmt:message key="global.formatFile"/></li>
    			<li>Cấu trúc file: 
    					<code>
    						&lt;<fmt:message key="assetsManagement.asTypesId"/><span class="note">(*)</span>&gt;,
    						&lt;<fmt:message key="assetsManagement.productCode"/><span class="note">(*)</span>&gt;,
    						&lt;<fmt:message key="assetsManagement.assetsName"/><span class="note">(*)</span>&gt;,
    						<%-- &lt;<fmt:message key="assetsManagement.serialNo"/>&gt;, --%>
    						&lt;<fmt:message key="assetsManagement.unit"/>&gt;,
    						&lt;<fmt:message key="assetsManagement.ordering"/>&gt;, 
    						&lt;<fmt:message key="assetsManagement.description"/>&gt;
    					</code></li>
						<li><fmt:message key="global.file"/>&nbsp;<a href="${pageContext.request.contextPath}/upload/example/DanhMucThietBi_Ex.xls" title="Danh mục thiết bị" style="color: blue; ">DanhMucThietBi_Ex.xls</a></li>
				<li><fmt:message key="global.chuY1"/></li>
    			</ul> 
    		</td>
    	</tr>
    	
    </table>
    
    <c:if test="${status != null}" >
    	<div class="error">${status} ${statusExists}</div>
    </c:if>
    <c:if test="${fn:length(failedList) gt 0}">
    	<div id="failed">
    		<div><b>Danh mục thiết bị không hợp lệ ( ${failNum}/${totalNum} )</b></div>
    		
    		<div style="max-height: 500px; overflow: auto;">
		    	<display:table name="${failedList}" class="simple2" id="item1" requestURI="" export="false" pagesize="700" >
					<display:column title="STT">
						<c:out value="${item1_rowNum}" />
					</display:column> 
					<display:column property="asTypesId" titleKey="assetsManagement.asTypesId" class="NOT_NULL"/> 
					<display:column property="productCode" titleKey="assetsManagement.productCode" class="NOT_NULL"/>
					<display:column property="assetsName" titleKey="assetsManagement.assetsName" class="NOT_NULL"/>
					<%-- <display:column property="serialNo" titleKey="assetsManagement.serialNo"/> --%>
					<display:column property="unit" titleKey="assetsManagement.unit"/> 
					<display:column property="ordering" titleKey="assetsManagement.ordering"/>
					<display:column property="description" titleKey="assetsManagement.description"/>
					
					<display:setProperty name="export.csv.include_header" value="true" />
					<display:setProperty name="export.excel.include_header" value="true" />
					<display:setProperty name="export.xml.include_header" value="true" />
					<display:setProperty name="export.xml.filename" value="${exportFileName}.xml" />
					<display:setProperty name="export.csv.filename" value="${exportFileName}.csv" />
					<display:setProperty name="export.excel.filename" value="${exportFileName}.xls" />
				</display:table>
			</div>
		</div>
	</c:if>
	<c:if test="${fn:length(successList) gt 0}">
    	<div id="success">
    		<div><b>Danh mục thiết bị hợp lệ ( ${successNum}/${totalNum} )</b></div>
    		
    		<div style="max-height: 500px; overflow: auto;">
		    	<display:table name="${successList}" class="simple2" id="item2" requestURI="" export="false" pagesize="700" >
					<display:column title="STT">
						<c:out value="${item2_rowNum}" />
					</display:column> 
					
					<display:column property="asTypesId" titleKey="assetsManagement.asTypesId" class="NOT_NULL"/> 
					<display:column property="productCode" titleKey="assetsManagement.productCode" class="NOT_NULL"/>
					<display:column property="assetsName" titleKey="assetsManagement.assetsName" class="NOT_NULL"/>
					<%-- <display:column property="serialNo" titleKey="assetsManagement.serialNo"/> --%>
					<display:column property="unit" titleKey="assetsManagement.unit"/> 
					<display:column property="ordering" titleKey="assetsManagement.ordering"/>
					<display:column property="description" titleKey="assetsManagement.description"/>
				</display:table>
			</div>
		</div>
	</c:if>
    
	<table>
		<tr>
			<td>
               	<input class="button" type="button" value="<fmt:message key="global.button.back"/>" onClick='window.location="list.htm"'>
			</td>
		</tr>
	</table>
</form:form> 

<link rel="stylesheet" type="text/css" media="all" href="${pageContext.request.contextPath}/styles/calendar-blue.css"> 

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