<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<style type="text/css">   
    #success p { margin: 0; padding: 1em; white-space: nowrap; } 
    #failed p { margin: 0; padding: 1em; white-space: nowrap; }
    .note{color:red;}
</style>

<title><fmt:message key="sidebar.admin.isoLicenseSoftFormUpload"/></title>
<content tag="heading"><fmt:message key="sidebar.admin.isoLicenseSoftFormUpload"/></content>
 	
<form:form method="post" action="upload.htm" enctype="multipart/form-data" >
	
	<table class="simple2">
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
					<li>Dữ liệu trong file có dạng: <code>&lt;<fmt:message key="isoLicenseSoft.day"/>&gt;<font color="red">(*)</font>, &lt;<fmt:message key="isoLicenseSoft.vendor"/>&gt;<font color="red">(*)</font>, &lt;<fmt:message key="isoLicenseSoft.neType"/>&gt;, &lt;<fmt:message key="isoLicenseSoft.ne"/>&gt;<font color="red">(*)</font>, &lt;<fmt:message key="isoLicenseSoft.featureCode"/>&gt;<font color="red">(*)</font>, &lt;<fmt:message key="isoLicenseSoft.licenseCode"/>&gt;
					, &lt;<fmt:message key="isoLicenseSoft.featureName"/>&gt;, &lt;<fmt:message key="isoLicenseSoft.status"/>&gt;, &lt;<fmt:message key="isoLicenseSoft.activeDate"/>&gt;, &lt;<fmt:message key="isoLicenseSoft.capacity"/>&gt;, &lt;<fmt:message key="isoLicenseSoft.usage"/>&gt;.</code>
					</li>
					<li>File mẫu:&nbsp;<a style="color: blue; " title="IsoLicenseSoftExample" href="${pageContext.request.contextPath}/upload/example/IsoLicenseSoft.xls">IsoLicenseSoft.xls</a></li>
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
    		<div><b>Dữ liệu bản quyền mềm không hợp lệ  ( ${failNum}/${totalNum} )</b></div>
    		
    		<div style="max-height: 500px; overflow: auto;">
    			<display:table name="${failedList}" class="simple2" id="item1" requestURI="" export="false" pagesize="100">
						<display:column format="{0,date,dd/MM/yyyy}" property="day" titleKey="isoLicenseSoft.day" class="NOT_NULL" />
						<display:column property="vendor" titleKey="isoLicenseSoft.vendor" class="NOT_NULL" />
						<display:column style="max-width: 80px;word-wrap: break-word;" property="neType" titleKey="isoLicenseSoft.neType" />
						<display:column property="ne" titleKey="isoLicenseSoft.ne" class="NOT_NULL" />
						<display:column property="featureCode" titleKey="isoLicenseSoft.featureCode" class="NOT_NULL" />
						<display:column property="licenseCode" titleKey="isoLicenseSoft.licenseCode" />
						<display:column property="featureName" titleKey="isoLicenseSoft.featureName" />
						<display:column property="status" titleKey="isoLicenseSoft.status" />
						<display:column format="{0,date,dd/MM/yyyy}" property="activeDate" titleKey="isoLicenseSoft.activeDate" />
						<display:column property="capacity" titleKey="isoLicenseSoft.capacity" />
						<display:column property="usage" titleKey="isoLicenseSoft.usage" />
				</display:table>
			</div>
		</div>
	</c:if>
	<c:if test="${fn:length(successList) gt 0}">
    	<div id="success">
    		<div><b>Dữ liệu bản quyền mềm hợp lệ  ( ${successNum}/${totalNum} )</b></div>
    		
    		<div style="max-height: 500px; overflow: auto;">
    			<display:table name="${successList}" class="simple2" id="item2" requestURI="" export="false" pagesize="700">
						<display:column format="{0,date,dd/MM/yyyy}" property="day" titleKey="isoLicenseSoft.day" class="NOT_NULL" />
						<display:column property="vendor" titleKey="isoLicenseSoft.vendor" class="NOT_NULL" />
						<display:column style="max-width: 80px;word-wrap: break-word;" property="neType" titleKey="isoLicenseSoft.neType" />
						<display:column property="ne" titleKey="isoLicenseSoft.ne" class="NOT_NULL" />
						<display:column property="featureCode" titleKey="isoLicenseSoft.featureCode" class="NOT_NULL" />
						<display:column property="licenseCode" titleKey="isoLicenseSoft.licenseCode" />
						<display:column property="featureName" titleKey="isoLicenseSoft.featureName" />
						<display:column property="status" titleKey="isoLicenseSoft.status" />
						<display:column format="{0,date,dd/MM/yyyy}" property="activeDate" titleKey="isoLicenseSoft.activeDate" />
						<display:column property="capacity" titleKey="isoLicenseSoft.capacity" />
						<display:column property="usage" titleKey="isoLicenseSoft.usage" />	
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