<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<style type="text/css">   
    #success p { margin: 0; padding: 1em; white-space: nowrap; } 
    #failed p { margin: 0; padding: 1em; white-space: nowrap; }
    .note{color:red;}
</style>

<title><fmt:message key="sidebar.admin.asCardsUpload"/></title>
<content tag="heading"><fmt:message key="sidebar.admin.asCardsUpload"/></content>
 	
<form:form method="post" action="upload.htm" enctype="multipart/form-data" >
	
	<table class="simple2">
		<tr>
			<td><b><fmt:message key="asCards.warehourse"/></b></td>
			<td>
				<select id="warehourse" name="warehourse" class="wid30">
	   				<c:forEach var="items" items="${warehourseList}">
		              	<c:choose>
		                <c:when test="${items.warehourse == warehourseCBB}">
		                    <option value="${items.warehourse}" selected="selected">${items.warehourse}</option>
		                </c:when>
		                <c:otherwise>
		                    <option value="${items.warehourse}">${items.warehourse}</option>
		                </c:otherwise>
		              	</c:choose>
			    	</c:forEach>
				</select>
			</td>
		</tr>
		<tr>
			<td class="wid10 mwid140"><b><fmt:message key="qLNguoiDung.file"/></b></td>
			<td ><input class="button" type="file" name="file" size="90"/>&nbsp;<input type="submit" class="button" name="save" value="<fmt:message key="global.button.import"/>"/></td>
		</tr>
		<tr>
			<td>
			<b><fmt:message key="qLNguoiDung.dinhDangFile"/></b>
			</td>
			<td>
				<ul style="list-style-type: none;">
					<li>File import là file (.xls)</li>
					<li>Dữ liệu trong file có dạng: <code>&lt;STT&gt;, &lt;Tên thiết bị&gt;<font color="red">(*)</font>, &lt;Mã thiết bị&gt;<font color="red">(*)</font>, &lt;Số seri&gt;<font color="red">(*)</font>, &lt;Số lượng&gt;<font color="red">(*)</font>, &lt;Ngày nhập&gt;
					, &lt;Ngày xuất&gt;, &lt;Nguồn gốc&gt;, &lt;Tổ xuất&gt;, &lt;Ghi chú&gt;, &lt;Số phiếu xuất&gt;, &lt;Ngày trả&gt;, &lt;Nhà sản xuất&gt;, &lt;Ngày hết hạn bảo hành&gt;.</code>
					</li>
					<li>File mẫu:&nbsp;<a style="color: blue; " title="UsersExample" href="${pageContext.request.contextPath}/upload/example/AsCards.xls">AsCards.xls</a></li>
					<li>Chú ý:</li>
					<li>&nbsp;&nbsp;- Những thông tin được đánh dấu <font color="red">(*)</font> là thông tin nhập liệu bắt buộc. </li>
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
						<display:column property="warehourse" titleKey="asCards.warehourse" />
						<display:column style="max-width:150px;word-wrap: break-word;" property="asTypesId" titleKey="asCards.asTypesName" class="NOT_NULL" />
						<display:column class="centerColumnMana" property="stt" titleKey="global.list.STT" />
						<display:column property="productName" titleKey="asCards.productName" class="NOT_NULL" />
						<display:column property="productCode" titleKey="asCards.productCode" class="NOT_NULL" />
						<display:column property="serialNo" titleKey="asCards.serialNo" class="NOT_NULL" />
						<display:column property="amount" titleKey="asCards.amount" class="NOT_NULL rightColumnMana" />
						<display:column format="{0,date,dd/MM/yyyy}" property="importDate" titleKey="asCards.importDate" class="centerColumnMana" />
						<display:column class="centerColumnMana" format="{0,date,dd/MM/yyyy}" property="exportDate" titleKey="asCards.exportDate" />
						<display:column property="ject" titleKey="asCards.ject" />
						<display:column property="departmentId" titleKey="asCards.departmentId" />
						<display:column property="description" titleKey="asCards.description" />
						<display:column property="votesNo" titleKey="asCards.votesNo"/>
						<display:column class="centerColumnMana" format="{0,date,dd/MM/yyyy}" property="dateReturn" titleKey="asCards.dateReturn" />
						<display:column property="vendor" titleKey="asCards.vendor" />
						<display:column class="centerColumnMana" format="{0,date,dd/MM/yyyy}" property="warrantyDate" titleKey="asCards.warrantyDate" />
				</display:table>
			</div>
		</div>
	</c:if>
	<c:if test="${fn:length(successList) gt 0}">
    	<div id="success">
    		<div><b>Dữ liệu thiết bị hợp lệ  ( ${successNum}/${totalNum} )</b></div>
    		
    		<div style="max-height: 500px; overflow: auto;">
    			<display:table name="${successList}" class="simple2" id="item2" requestURI="" export="false" pagesize="700">
						<display:column property="warehourse" titleKey="asCards.warehourse" />
						<display:column style="max-width:150px;word-wrap: break-word;" property="asTypesId" titleKey="asCards.asTypesName" class="NOT_NULL" />
						<display:column class="centerColumnMana" property="stt" titleKey="global.list.STT" />
						<display:column property="productName" titleKey="asCards.productName" class="NOT_NULL" />
						<display:column property="productCode" titleKey="asCards.productCode" class="NOT_NULL" />
						<display:column property="serialNo" titleKey="asCards.serialNo" class="NOT_NULL" />
						<display:column property="amount" titleKey="asCards.amount" class="NOT_NULL rightColumnMana" />
						<display:column format="{0,date,dd/MM/yyyy}" property="importDate" titleKey="asCards.importDate" class="centerColumnMana" />
						<display:column class="centerColumnMana" format="{0,date,dd/MM/yyyy}" property="exportDate" titleKey="asCards.exportDate" />
						<display:column property="ject" titleKey="asCards.ject" />
						<display:column property="departmentId" titleKey="asCards.departmentId" />
						<display:column property="description" titleKey="asCards.description" />
						<display:column property="votesNo" titleKey="asCards.votesNo"/>
						<display:column class="centerColumnMana" format="{0,date,dd/MM/yyyy}" property="dateReturn" titleKey="asCards.dateReturn" />
						<display:column property="vendor" titleKey="asCards.vendor" />
						<display:column class="centerColumnMana" format="{0,date,dd/MM/yyyy}" property="warrantyDate" titleKey="asCards.warrantyDate" />	
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