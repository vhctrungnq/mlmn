<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<style type="text/css">   
    #success p { margin: 0; padding: 1em; white-space: nowrap; } 
    #failed p { margin: 0; padding: 1em; white-space: nowrap; }
    .note{color:red;}
</style>
<title><fmt:message key="sidebar.admin.phongBanUpload"/></title>
<content tag="heading"><fmt:message key="sidebar.admin.phongBanUpload"/></content>
 	
<form:form method="post" action="upload.htm" enctype="multipart/form-data" >
	
	<table class="simple2">
		<tr>
			<td class="wid10 mwid140"><b><fmt:message key="qLNguoiDung.file"/></b></td>
			<td><input class="button" type="file" name="file" size="90"/>&nbsp;
			<input class="button" type="submit" class="button" name="save" value="<fmt:message key="global.button.import"/>"/></td>
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
							&lt;<fmt:message key="qLPhongBan.maPhong"/>&gt;<font color="red">(*)</font>, 
							&lt;<fmt:message key="qLPhongBan.tenVietTat"/>&gt;<font color="red">(*)</font>, 
							&lt;<fmt:message key="qLPhongBan.tenPhong"/>&gt;<font color="red">(*)</font>, 
							&lt;<fmt:message key="qLPhongBan.parentDepCode"/>&gt;(Y/N), 
							&lt;<fmt:message key="qLPhongBan.region"/>&gt;, 
							&lt;<fmt:message key="qLPhongBan.dienThoai"/>&gt;, 
							&lt;<fmt:message key="qLPhongBan.fax"/>&gt;, 
							&lt;<fmt:message key="qLPhongBan.trangThai"/>&gt;, 
							&lt;<fmt:message key="qLPhongBan.diaChi"/>&gt;, 
							&lt;<fmt:message key="qLPhongBan.sapXep"/>&gt;
							</code>
						</li>
						<li>File mẫu:&nbsp;<a style="color: blue; " title="DepartmentExample" href="${pageContext.request.contextPath}/upload/example/DepartmentExample.xls">DepartmentExample.xls</a>
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
    		<div><b>Dữ liệu không hợp lệ  ( ${failNum}/${totalNum} )</b></div>
    		
    		<div style="max-height: 500px; overflow: auto;">
    			<display:table name="${failedList}" class="simple2" id="item1" requestURI="" export="false" pagesize="100">
							<display:column class="centerColumnMana" title="STT" > <c:out value="${item1_rowNum}"/> </display:column>
							<display:column property="deptCode" titleKey="qLPhongBan.maPhong"  class="NOT_NULL" />
							<display:column property="abbreviated" titleKey="qLPhongBan.tenVietTat"  class="NOT_NULL"/>
							<display:column property="deptName"  class="NOT_NULL" titleKey="qLPhongBan.tenPhong" />
							<display:column property="deptCodeParent" titleKey="qLPhongBan.parentDepCode" />
							<display:column property="region" titleKey="qLPhongBan.region" />
							<display:column property="ordering" titleKey="qLPhongBan.sapXep" />
							<display:column property="phone" titleKey="qLPhongBan.dienThoai" />
							<display:column property="fax"  titleKey="qLPhongBan.fax" />
							<display:column property="address" titleKey="qLPhongBan.diaChi" />
							<display:column property="name" titleKey="qLPhongBan.trangThai" />
				</display:table>
			</div>
		</div>
	</c:if>
	<c:if test="${fn:length(successList) gt 0}">
    	<div id="success">
    		<div><b>Dữ liệu hợp lệ  ( ${successNum}/${totalNum} )</b></div>
    		
    		<div style="max-height: 500px; overflow: auto;">
    			<display:table name="${successList}" class="simple2" id="item2" requestURI="" export="false" pagesize="700">
							<display:column class="centerColumnMana" title="STT" > <c:out value="${item1_rowNum}"/> </display:column>
							<display:column property="deptCode" titleKey="qLPhongBan.maPhong"  class="NOT_NULL" />
							<display:column property="abbreviated" titleKey="qLPhongBan.tenVietTat"  class="NOT_NULL"/>
							<display:column property="deptName"  class="NOT_NULL" titleKey="qLPhongBan.tenPhong" />
							<display:column property="deptCodeParent" titleKey="qLPhongBan.parentDepCode" />
							<display:column property="region" titleKey="qLPhongBan.region" />
							<display:column property="ordering" titleKey="qLPhongBan.sapXep" />
							<display:column property="phone" titleKey="qLPhongBan.dienThoai" />
							<display:column property="fax"  titleKey="qLPhongBan.fax" />
							<display:column property="address" titleKey="qLPhongBan.diaChi" />
							<display:column property="name" titleKey="qLPhongBan.trangThai" />
				</display:table>
			</div>
		</div>
	</c:if>
		<table>
		<tr>
			<td >
               	<input class="button" type="button" value="<fmt:message key="global.button.back"/>" onClick='window.location="danh-sach.htm"'>
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