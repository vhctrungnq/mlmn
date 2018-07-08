<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<style type="text/css">   
    #success p { margin: 0; padding: 1em; white-space: nowrap; } 
    #failed p { margin: 0; padding: 1em; white-space: nowrap; }
    .note{color:red;}
</style>
<title><fmt:message key="sidebar.admin.usersUpload"/></title>
<content tag="heading"><fmt:message key="sidebar.admin.usersUpload"/></content>
 	
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
							&lt;<fmt:message key="qLNguoiDung.taiKhoan"/>&gt;<font color="red">(*)</font>, 
							&lt;<fmt:message key="qLNguoiDung.matKhau"/>&gt;, 
							&lt;<fmt:message key="qLNguoiDung.tenDayDu"/>&gt;<font color="red">(*)</font>, 
							&lt;<fmt:message key="qLNguoiDung.gioiTinh"/>&gt;, 
							&lt;<fmt:message key="qLNguoiDung.chucDanh"/>&gt;, 
							&lt;<fmt:message key="qLNguoiDung.dienThoai"/>&gt;<font color="red">(*)</font>, 
							&lt;<fmt:message key="qLNguoiDung.email"/>&gt;<font color="red">(*)</font>, 
							&lt;<fmt:message key="qLNguoiDung.trangThai"/>&gt;, 
							&lt;<fmt:message key="qLNguoiDung.thoiGianHieuLuc"/>&gt;, 
							&lt;<fmt:message key="qLNguoiDung.hieuLucDenNgay"/>&gt;, 
							&lt;<fmt:message key="qLNguoiDung.dienGiai"/>&gt;, 
							&lt;<fmt:message key="qLNguoiDung.maPhong"/>&gt;<font color="red">(*)</font>, 
							&lt;<fmt:message key="qLNguoiDung.team"/>&gt;,
							&lt;<fmt:message key="qLNguoiDung.subTeam"/>&gt;,
							&lt;<fmt:message key="qLNguoiDung.nhomTruyCap"/>&gt;<font color="red">(*)</font>,
							&lt;<fmt:message key="qLNguoiDung.regionRole"/>&gt;.
							</code>
						</li>
						<li>File mẫu:&nbsp;<a style="color: blue; " title="UsersExample" href="${pageContext.request.contextPath}/upload/example/UsersExample.xls">UsersExample.xls</a>
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
    		<div><b>Dữ liệu người dùng không hợp lệ  ( ${failNum}/${totalNum} )</b></div>
    		
    		<div style="max-height: 500px; overflow: auto;">
    			<display:table name="${failedList}" class="simple2" id="item1" requestURI="" export="false" pagesize="100">
							<display:column class="centerColumnMana" title="STT" > <c:out value="${item1_rowNum}"/> </display:column>
							<display:column property="username" titleKey="qLNguoiDung.taiKhoan" class="NOT_NULL"/>
							<display:column property="fullname" titleKey="qLNguoiDung.tenDayDu" class="NOT_NULL"/>  
							<display:column property="sexStr" titleKey="qLNguoiDung.gioiTinh"/>
							<display:column property="position" titleKey="qLNguoiDung.chucDanh"/>
							<display:column property="phone" titleKey="qLNguoiDung.dienThoai" class="NOT_NULL"/>
							<display:column property="email" titleKey="qLNguoiDung.email" class="NOT_NULL"/>
							<display:column property="nameTrangThai" titleKey="qLNguoiDung.trangThai"/>
							<display:column property="activeDate" titleKey="qLNguoiDung.thoiGianHieuLuc" format="{0,date,dd/MM/yyyy}"/>
							<display:column property="expired" titleKey="qLNguoiDung.hieuLucDenNgay" format="{0,date,dd/MM/yyyy}"/>
							<display:column property="description" titleKey="qLNguoiDung.dienGiai"/>
							<display:column property="maPhong" titleKey="qLNguoiDung.maPhong" class="NOT_NULL"/>
							<display:column property="team" titleKey="qLNguoiDung.team" />
							<display:column property="subTeam" titleKey="qLNguoiDung.subTeam" />
							<display:column property="groupName" titleKey="qLNguoiDung.nhomTruyCap" class="NOT_NULL"/>	
							<display:column property="regionRole" titleKey="qLNguoiDung.regionRole"/>
				</display:table>
			</div>
		</div>
	</c:if>
	<c:if test="${fn:length(successList) gt 0}">
    	<div id="success">
    		<div><b>Dữ liệu người dùng hợp lệ  ( ${successNum}/${totalNum} )</b></div>
    		
    		<div style="max-height: 500px; overflow: auto;">
    			<display:table name="${successList}" class="simple2" id="item2" requestURI="" export="false" pagesize="700">
							<display:column class="centerColumnMana" title="STT" > <c:out value="${item2_rowNum}"/> </display:column>
							<display:column property="username" titleKey="qLNguoiDung.taiKhoan" class="NOT_NULL"/>
							<display:column property="fullname" titleKey="qLNguoiDung.tenDayDu" class="NOT_NULL"/>  
							<display:column property="sexStr" titleKey="qLNguoiDung.gioiTinh"/>
							<display:column property="position" titleKey="qLNguoiDung.chucDanh"/>
							<display:column property="phone" titleKey="qLNguoiDung.dienThoai" class="NOT_NULL"/>
							<display:column property="email" titleKey="qLNguoiDung.email" class="NOT_NULL"/>
							<display:column property="nameTrangThai" titleKey="qLNguoiDung.trangThai"/>
							<display:column property="activeDate" titleKey="qLNguoiDung.thoiGianHieuLuc" format="{0,date,dd/MM/yyyy}"/>
							<display:column property="expired" titleKey="qLNguoiDung.hieuLucDenNgay" format="{0,date,dd/MM/yyyy}"/>
							<display:column property="description" titleKey="qLNguoiDung.dienGiai"/>
							<display:column property="maPhong" titleKey="qLNguoiDung.maPhong" class="NOT_NULL"/>
							<display:column property="team" titleKey="qLNguoiDung.team" />
							<display:column property="subTeam" titleKey="qLNguoiDung.subTeam" />
							<display:column property="groupName" titleKey="qLNguoiDung.nhomTruyCap" class="NOT_NULL"/>	
							<display:column property="regionRole" titleKey="qLNguoiDung.regionRole"/>
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