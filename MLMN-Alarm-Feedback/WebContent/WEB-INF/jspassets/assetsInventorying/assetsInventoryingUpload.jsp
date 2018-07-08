<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<style type="text/css">   
    #success p { margin: 0; padding: 1em; white-space: nowrap; } 
    #failed p { margin: 0; padding: 1em; white-space: nowrap; }
    .note{color:red;}
</style>

<title><fmt:message key="sidebar.admin.assetsInventoryingUpload"/></title>
<content tag="heading"><fmt:message key="sidebar.admin.assetsInventoryingUpload"/></content>
<div class="ui-tabs-panel"> 	
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
					<li>Dữ liệu trong file có dạng: 
						<code>
							&lt;<fmt:message key="assetsInventorying.dotKiemKe"/>&gt;<font color="red">(*)</font>, 
							&lt;<fmt:message key="assetsInventorying.ngayKiemKe"/>&gt;<font color="red">(*)</font>, 
							&lt;<fmt:message key="assetsInventorying.productCode"/>&gt;<font color="red">(*)</font>, 
							&lt;<fmt:message key="assetsInventorying.productName"/>&gt;<font color="red">(*)</font>, 
							&lt;<fmt:message key="assetsInventorying.serialNo"/>&gt;, 
							&lt;<fmt:message key="assetsInventorying.unit"/>&gt;, 
							&lt;<fmt:message key="assetsInventorying.amount"/>&gt;, 
							&lt;<fmt:message key="assetsInventorying.departmentUse"/>&gt;, 
							&lt;<fmt:message key="assetsInventorying.departmentId"/>&gt;, 
							&lt;<fmt:message key="assetsInventorying.users"/>&gt;, 
							&lt;<fmt:message key="assetsInventorying.createdBy"/>&gt;, 
							&lt;<fmt:message key="assetsInventorying.description"/>&gt;.
						</code>
					</li>
					<li><fmt:message key="global.file"/>&nbsp;<a href="${pageContext.request.contextPath}/upload/example/KiemKe_Ex.xls" title="Danh sách kiểm kê thiết bị" style="color: blue; ">KiemKe_Ex.xls</a></li>
					<li><fmt:message key="global.chuY1"/></li>
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
						<display:column property="dotKiemKe" titleKey="assetsInventorying.dotKiemKe" class="NOT_NULL"/>
						<display:column property="importDate" format="{0,date,dd/MM/yyyy}" titleKey="assetsInventorying.ngayKiemKe" class="NOT_NULL"/>
						<display:column property="productCode" titleKey="assetsInventorying.productCode" class="NOT_NULL"/>
						<display:column property="productName" titleKey="assetsInventorying.productName" class="NOT_NULL"/>
						<display:column property="serialNo" titleKey="assetsInventorying.serialNo"/>
						<display:column property="unit" titleKey="assetsInventorying.unit" />
						<display:column property="amount" titleKey="assetsInventorying.amount"/>
						<display:column property="departmentUse" titleKey="assetsInventorying.departmentUse" />
						<display:column property="departmentId" titleKey="assetsInventorying.departmentId" />
						<display:column property="users" titleKey="assetsInventorying.users" />
						<display:column property="createdBy" titleKey="assetsInventorying.createdBy"/>
						<display:column property="description" titleKey="assetsInventorying.description" />	
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
						<display:column property="dotKiemKe" titleKey="assetsInventorying.dotKiemKe" class="NOT_NULL"/>
						<display:column property="importDate" format="{0,date,dd/MM/yyyy}" titleKey="assetsInventorying.ngayKiemKe" class="NOT_NULL"/>
						<display:column property="productCode" titleKey="assetsInventorying.productCode" class="NOT_NULL"/>
						<display:column property="productName" titleKey="assetsInventorying.productName" class="NOT_NULL"/>
						<display:column property="serialNo" titleKey="assetsInventorying.serialNo"/>
						<display:column property="unit" titleKey="assetsInventorying.unit" />
						<display:column property="amount" titleKey="assetsInventorying.amount"/>
						<display:column property="departmentUse" titleKey="assetsInventorying.departmentUse" />
						<display:column property="departmentId" titleKey="assetsInventorying.departmentId" />
						<display:column property="users" titleKey="assetsInventorying.users" />
						<display:column property="createdBy" titleKey="assetsInventorying.createdBy"/>
						<display:column property="description" titleKey="assetsInventorying.description" />		
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
</div>
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