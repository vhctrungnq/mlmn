<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<style type="text/css">   
    #success p { margin: 0; padding: 1em; white-space: nowrap; } 
    #failed p { margin: 0; padding: 1em; white-space: nowrap; }
    .note{color:red;}
</style>

<title><fmt:message key="sidebar.admin.asImportWarehouseUpload"/></title>
<content tag="heading"><fmt:message key="sidebar.admin.asImportWarehouseUpload"/></content>
 	
<form:form method="post" action="upload.htm" enctype="multipart/form-data" >
	
	<table class="simple2">
		<tr>
			<td class="wid10 mwid140"><b><fmt:message key="qLNguoiDung.file"/></b></td>
			<td ><input class="button" type="file" name="file" size="90"/>&nbsp;<input class="button" type="submit" class="button" name="save" value="<fmt:message key="global.button.import"/>"/></td>
		</tr>
		<tr>
			<td>
			<b><fmt:message key="qLNguoiDung.dinhDangFile"/></b>
			</td>
			<td>
				<ul style="list-style-type: none;">
					<li>File import là file (.xls)</li>
					<li>Dữ liệu trong file có dạng: <code>&lt;Mã loại thiết bị&gt;<font color="red">(*)</font>, &lt;Tên thiết bị&gt;<font color="red">(*)</font>, &lt;Mã thiết bị&gt;<font color="red">(*)</font>, &lt;Số seri&gt;<font color="red">(*)</font>, &lt;Số lượng&gt;<font color="red">(*)</font>, &lt;Đơn vị&gt;, &lt;Ngày nhập&gt;<font color="red">(*)</font>
					, &lt;Ngày sản xuất&gt;, &lt;Nhà sản xuất&gt;, &lt;Ngày hết hạn bảo hành&gt;, &lt;Nguồn gốc&gt;, &lt;Ghi chú&gt;.</code>
					</li>
					<li>File mẫu:&nbsp;<a style="color: blue; " title="UsersExample" href="${pageContext.request.contextPath}/upload/example/AsImportWarehouse.xls">AsImportWarehouse.xls</a></li>
					<li>Chú ý:</li>
					<li>&nbsp;&nbsp;- Những thông tin được đánh dấu <font color="red">(*)</font> là thông tin nhập liệu bắt buộc. </li>
					<li>&nbsp;&nbsp;- Những ô nhập liệu dạng ngày phải có định dạng dd/mm/yyyy.</li>
				</ul>
			</td>
		</tr>
	</table>
	
	<c:if test="${status != null}">
    	<div class="error">${status} ${statusExists}</div>
    </c:if>
    <c:if test="${fn:length(failedList) gt 0}">
    	<div id="failed">
    		<div><b>Dữ liệu thiết bị không hợp lệ  ( ${failNum}/${totalNum} )</b></div>
    		
    		<div style="max-height: 500px; overflow: auto;">
    			<display:table name="${failedList}" class="simple2" id="item1" requestURI="" export="false" pagesize="700">
						<display:column class="centerColumnMana" titleKey="global.list.STT"> <c:out value="${item1_rowNum}"/> </display:column>
						<display:column property="asTypesId" titleKey="assetsTypes.asTypesId" class="NOT_NULL"/>
						<display:column property="productName" titleKey="asImportWarehouse.productName" class="NOT_NULL"/>
						<display:column property="productCode" titleKey="asImportWarehouse.productCode" class="NOT_NULL"/>
						<display:column property="serialNo" titleKey="asImportWarehouse.serialNo" class="NOT_NULL"/>
						<display:column property="amount" titleKey="asImportWarehouse.amount" class="centerColumnMana NOT_NULL"/>
						<display:column class="centerColumnMana" property="unit" titleKey="asImportWarehouse.unit" />
						<display:column format="{0,date,dd/MM/yyyy}" property="importDate" titleKey="asImportWarehouse.importDate" class="centerColumnMana NOT_NULL"/>
						<display:column class="centerColumnMana" format="{0,date,dd/MM/yyyy}" property="produceDate" titleKey="asImportWarehouse.produceDate" />
						<display:column property="vendor" titleKey="asImportWarehouse.vendor" />
						<display:column format="{0,date,dd/MM/yyyy}" property="warrantyDate" titleKey="asImportWarehouse.warrantyDate" />
						<display:column property="ject" titleKey="asImportWarehouse.ject" />
						<display:column property="description" titleKey="asImportWarehouse.description" />
						<display:column class="centerColumnMana" property="createdBy" titleKey="asImportWarehouse.createdBy" />	
				</display:table>
			</div>
		</div>
	</c:if>
	<c:if test="${fn:length(successList) gt 0}">
    	<div id="success">
    		<div><b>Dữ liệu thiết bị hợp lệ  ( ${successNum}/${totalNum} )</b></div>
    		
    		<div style="max-height: 500px; overflow: auto;">
    			<display:table name="${successList}" class="simple2" id="item2" requestURI="" export="false" pagesize="700">
						<display:column class="centerColumnMana" titleKey="global.list.STT"> <c:out value="${item2_rowNum}"/> </display:column>
						<display:column property="asTypesId" titleKey="assetsTypes.asTypesId" class="NOT_NULL"/>
						<display:column property="productName" titleKey="asImportWarehouse.productName" class="NOT_NULL"/>
						<display:column property="productCode" titleKey="asImportWarehouse.productCode" class="NOT_NULL"/>
						<display:column property="serialNo" titleKey="asImportWarehouse.serialNo" class="NOT_NULL"/>
						<display:column property="amount" titleKey="asImportWarehouse.amount" class="centerColumnMana NOT_NULL"/>
						<display:column class="centerColumnMana" property="unit" titleKey="asImportWarehouse.unit" />
						<display:column format="{0,date,dd/MM/yyyy}" property="importDate" titleKey="asImportWarehouse.importDate" class="centerColumnMana NOT_NULL"/>
						<display:column class="centerColumnMana" format="{0,date,dd/MM/yyyy}" property="produceDate" titleKey="asImportWarehouse.produceDate" />
						<display:column property="vendor" titleKey="asImportWarehouse.vendor" />
						<display:column format="{0,date,dd/MM/yyyy}" property="warrantyDate" titleKey="asImportWarehouse.warrantyDate" />
						<display:column property="ject" titleKey="asImportWarehouse.ject" />
						<display:column property="description" titleKey="asImportWarehouse.description" />
						<display:column class="centerColumnMana" property="createdBy" titleKey="asImportWarehouse.createdBy" />		
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