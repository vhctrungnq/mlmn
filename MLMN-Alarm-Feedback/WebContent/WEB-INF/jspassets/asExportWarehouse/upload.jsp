<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!-- Xuat va hoan tra tai san -->
<style type="text/css">   
 	#success { overflow: auto; overflow-y: hidden; }  
    #success p { margin: 0; padding: 1em; white-space: nowrap; }
    
    #failed { overflow: auto; overflow-y: hidden; }  
    #failed p { margin: 0; padding: 1em; white-space: nowrap; }
    .note{color:red;}
</style>

<title><fmt:message key="sidebar.admin.asExportWarehouseUpload"/></title>
<content tag="heading"><fmt:message key="sidebar.admin.asExportWarehouseUpload"/></content>
 <form:form  method="post" action="upload.htm" enctype="multipart/form-data">
	<table class="simple2">	
    	<tr style="height:20px;" >
    		<td width="150px"><b><fmt:message key="cautruc.filepath"/> <font color="red">(*)</font></b></td>
    		<td><input type="file" size="110" name="file" id="file" class="button" />
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
    						&lt;<fmt:message key="asExportWarehouse.productCode"/><span class="note">(*)</span>&gt;,
    						&lt;<fmt:message key="asExportWarehouse.serialNo"/>&gt;,
    						&lt;<fmt:message key="asExportWarehouse.unit"/>&gt;,
    						&lt;<fmt:message key="asExportWarehouse.amountEx"/><span class="note">(*)</span>&gt;,
    						&lt;<fmt:message key="asExportWarehouse.exportDate"/><span class="note">(*)</span>&gt;,
    						&lt;<fmt:message key="asExportWarehouse.votesNo"/><span class="note">(*)</span>&gt;, 
    						&lt;<fmt:message key="asExportWarehouse.usersEx"/>&gt;,
    						&lt;<fmt:message key="asExportWarehouse.amountReturn"/>&gt;,
    						&lt;<fmt:message key="asExportWarehouse.dateReturn"/>&gt;, 
    						&lt;<fmt:message key="asExportWarehouse.vendor"/>&gt;,
    						&lt;<fmt:message key="asExportWarehouse.departmentId"/>&gt;,
    						&lt;<fmt:message key="asExportWarehouse.organization"/>&gt;,
    						&lt;<fmt:message key="asExportWarehouse.users"/>&gt;,  
    						&lt;<fmt:message key="asExportWarehouse.description"/>&gt;,
    						&lt;<fmt:message key="asExportWarehouse.lydoxuat"/>&gt;,
    						&lt;<fmt:message key="asExportWarehouse.nguonlaytb"/>&gt;
    					</code></li>
						<li><fmt:message key="global.file"/>&nbsp;<a href="${pageContext.request.contextPath}/upload/example/XuatHoanTraThietBi_Ex.xls" title="Dữ liệu Xuất và Hoàn trả thiết bị" style="color: blue; ">XuatHoanTraThietBi_Ex.xls</a></li>
				<li><fmt:message key="global.chuY1"/></li>
    			</ul> 
    		</td>
    	</tr>
    	
    </table>
    
    <c:if test="${status != null}" >
    	<div class="error">${status}</div>
    </c:if> 
    <c:if test="${fn:length(failedList) gt 0}">
    	<div id="failed">
    		<div><b>Dữ liệu xuất thiết bị không hợp lệ ( ${failNum}/${totalNum} )</b></div>
    		
    		<div style="max-height: 500px; overflow: auto;">
		    	<display:table name="${failedList}" class="simple2" id="item1" requestURI="" export="false" pagesize="700" >
					<display:column title="STT">
						<c:out value="${item1_rowNum}" />
					</display:column>   
					<display:column property="productCode" titleKey="asExportWarehouse.productCode" class="NOT_NULL"/>
					<display:column property="serialNo" titleKey="asExportWarehouse.serialNo" />
					<display:column property="unit" titleKey="asExportWarehouse.unit" />  
					<display:column property="amountEx" titleKey="asExportWarehouse.amountEx" class="NOT_NULL"/> 
					<display:column property="exportDate" titleKey="asExportWarehouse.exportDate" class="NOT_NULL" format="{0,date,dd/MM/yyyy}"/> 
					<display:column property="votesNo" titleKey="asExportWarehouse.votesNo" class="NOT_NULL"/>
					<display:column property="usersEx" titleKey="asExportWarehouse.usersEx"/> 
					<display:column property="amountReturn" titleKey="asExportWarehouse.amountReturn"/> 
					<display:column property="dateReturn" titleKey="asExportWarehouse.dateReturn" format="{0,date,dd/MM/yyyy}"/> 
					<display:column property="vendor" titleKey="asExportWarehouse.vendor"/> 
					<display:column property="departmentId" titleKey="asExportWarehouse.departmentId" /> 
					<display:column property="organization" titleKey="asExportWarehouse.organization" />  
					<display:column property="users" titleKey="asExportWarehouse.users" />  
					<display:column property="description" titleKey="asExportWarehouse.description" /> 
					<display:column property="lyDoXuat" titleKey="asExportWarehouse.lydoxuat" /> 
					<display:column property="nguonLayTB" titleKey="asExportWarehouse.nguonlaytb" /> 
					
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
    		<div><b>Dữ liệu xuất thiết bị hợp lệ ( ${successNum}/${totalNum} )</b></div>
    		
    		<div style="max-height: 500px; overflow: auto;">
		    	<display:table name="${successList}" class="simple2" id="item2" requestURI="" export="false" pagesize="700" >
					<display:column title="STT">
						<c:out value="${item2_rowNum}" />
					</display:column> 
					
					<display:column property="productCode" titleKey="asExportWarehouse.productCode" class="NOT_NULL"/>
					<display:column property="serialNo" titleKey="asExportWarehouse.serialNo" />
					<display:column property="unit" titleKey="asExportWarehouse.unit" />  
					<display:column property="amountEx" titleKey="asExportWarehouse.amountEx" class="NOT_NULL"/> 
					<display:column property="exportDate" titleKey="asExportWarehouse.exportDate" class="NOT_NULL" format="{0,date,dd/MM/yyyy}"/> 
					<display:column property="votesNo" titleKey="asExportWarehouse.votesNo" class="NOT_NULL"/>
					<display:column property="usersEx" titleKey="asExportWarehouse.usersEx"/> 
					<display:column property="amountReturn" titleKey="asExportWarehouse.amountReturn"/> 
					<display:column property="dateReturn" titleKey="asExportWarehouse.dateReturn" format="{0,date,dd/MM/yyyy}"/> 
					<display:column property="vendor" titleKey="asExportWarehouse.vendor"/> 
					<display:column property="departmentId" titleKey="asExportWarehouse.departmentId" /> 
					<display:column property="organization" titleKey="asExportWarehouse.organization" />  
					<display:column property="users" titleKey="asExportWarehouse.users" />  
					<display:column property="description" titleKey="asExportWarehouse.description" /> 
					<display:column property="lyDoXuat" titleKey="asExportWarehouse.lydoxuat" /> 
					<display:column property="nguonLayTB" titleKey="asExportWarehouse.nguonlaytb" />
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