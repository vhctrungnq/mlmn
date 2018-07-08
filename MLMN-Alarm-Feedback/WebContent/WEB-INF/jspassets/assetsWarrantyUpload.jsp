<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<style type="text/css">   
    #success p { margin: 0; padding: 1em; white-space: nowrap; } 
    #failed p { margin: 0; padding: 1em; white-space: nowrap; }
    .note{color:red;}
</style>

<title><fmt:message key="sidebar.admin.assetsWarrantyUpload"/></title>
<content tag="heading"><fmt:message key="sidebar.admin.assetsWarrantyUpload"/></content>
 	
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
					<li>Dữ liệu trong file có dạng: <code>&lt;Csr&gt;<font color="red">(*)</font>, &lt;Function&gt;, &lt;Product code&gt;<font color="red">(*)</font>, &lt;Vendor&gt;, &lt;R state&gt;, &lt;Serial number&gt;<font color="red">(*)</font>, &lt;Serial number VMS scan&gt;, &lt;Sent date&gt;, &lt;Same unit&gt;
					, &lt;Reject&gt;, &lt;Serial number replacement&gt;, &lt;Received date&gt;, &lt;Delivery note&gt;, &lt;Qty&gt;, &lt;Name&gt;, &lt;Return date&gt;, &lt;Description&gt;.</code>
					</li>
					<li>File mẫu:&nbsp;<a style="color: blue; " title="UsersExample" href="${pageContext.request.contextPath}/upload/example/AssetsWarranty.xls">AssetsWarranty.xls</a></li>
					<li>Chú ý:- Những thông tin được đánh dấu <font color="red">(*)</font> là thông tin nhập liệu bắt buộc.</li>
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
    		<div><b>Dữ liệu tài sản không hợp lệ  ( ${failNum}/${totalNum} )</b></div>
    		
    		<div style="max-height: 500px; overflow: auto;">
    			<display:table name="${failedList}" class="simple2" id="item1" requestURI="" export="false" pagesize="700">
						<display:column class="centerColumnMana" titleKey="global.list.STT"> <c:out value="${item1_rowNum}"/> </display:column>
						<display:column property="csr" titleKey="assetsWarranty.csr" class="NOT_NULL"/>
						<display:column property="function" titleKey="assetsWarranty.function" />
						<display:column property="productCode" titleKey="assetsWarranty.productCode"  class="NOT_NULL"/>
						<display:column property="vendor" titleKey="assetsWarranty.vendor"/>
						<display:column property="rState" titleKey="assetsWarranty.rState" />
						<display:column property="serialNo" titleKey="assetsWarranty.serialNo"  class="NOT_NULL"/>
						<display:column property="serialNoScan" titleKey="assetsWarranty.serialNoScan" />
						<display:column property="sentDate" format="{0,date,dd/MM/yyyy}" titleKey="assetsWarranty.sentDate" />
						<display:column property="sameUnit" titleKey="assetsWarranty.sameUnit" />
						<display:column property="ject" titleKey="assetsWarranty.ject" />
						<display:column property="serialNoRep" titleKey="assetsWarranty.serialNoRep" />
						<display:column property="receivedDate" format="{0,date,dd/MM/yyyy}" titleKey="assetsWarranty.receivedDate" />
						<display:column class="centerColumnMana" property="deliveryNo" titleKey="assetsWarranty.deliveryNo" />
						<display:column class="centerColumnMana" property="qty" titleKey="assetsWarranty.qty" />
						<display:column class="centerColumnMana" property="departmentId" titleKey="assetsWarranty.departmentId" />
						<display:column property="returnDate" format="{0,date,dd/MM/yyyy}" titleKey="assetsWarranty.returnDate" />
						<display:column class="centerColumnMana" property="description" titleKey="assetsWarranty.description" />	
				</display:table>
			</div>
		</div>
	</c:if>
	<c:if test="${fn:length(successList) gt 0}">
    	<div id="success">
    		<div><b>Dữ liệu tài sản hợp lệ  ( ${successNum}/${totalNum} )</b></div>
    		
    		<div style="max-height: 500px; overflow: auto;">
    			<display:table name="${successList}" class="simple2" id="item2" requestURI="" export="false" pagesize="700">
						<display:column class="centerColumnMana" titleKey="global.list.STT"> <c:out value="${item2_rowNum}"/> </display:column>
						<display:column property="csr" titleKey="assetsWarranty.csr" class="NOT_NULL"/>
						<display:column property="function" titleKey="assetsWarranty.function" />
						<display:column property="productCode" titleKey="assetsWarranty.productCode"  class="NOT_NULL"/>
						<display:column property="vendor" titleKey="assetsWarranty.vendor"/>
						<display:column property="rState" titleKey="assetsWarranty.rState" />
						<display:column property="serialNo" titleKey="assetsWarranty.serialNo"  class="NOT_NULL"/>
						<display:column property="serialNoScan" titleKey="assetsWarranty.serialNoScan" />
						<display:column property="sentDate" format="{0,date,dd/MM/yyyy}" titleKey="assetsWarranty.sentDate" />
						<display:column property="sameUnit" titleKey="assetsWarranty.sameUnit" />
						<display:column property="ject" titleKey="assetsWarranty.ject" />
						<display:column property="serialNoRep" titleKey="assetsWarranty.serialNoRep" />
						<display:column property="receivedDate" format="{0,date,dd/MM/yyyy}" titleKey="assetsWarranty.receivedDate" />
						<display:column class="centerColumnMana" property="deliveryNo" titleKey="assetsWarranty.deliveryNo" />
						<display:column class="centerColumnMana" property="qty" titleKey="assetsWarranty.qty" />
						<display:column class="centerColumnMana" property="departmentId" titleKey="assetsWarranty.departmentId" />
						<display:column property="returnDate" format="{0,date,dd/MM/yyyy}" titleKey="assetsWarranty.returnDate" />
						<display:column class="centerColumnMana" property="description" titleKey="assetsWarranty.description" />	
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