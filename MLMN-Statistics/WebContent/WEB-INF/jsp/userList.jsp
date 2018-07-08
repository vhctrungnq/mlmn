<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<title>user list</title>
<content tag="heading">QUẢN LÝ NGƯỜI DÙNG</content>
<div>
	<form action="list.htm" method="Get">
		<span>Họ tên </span> 
		<input type="text" name="fullName" id="fullName" value="${fullName}"/>
	
		<span>Phòng ban </span> 
		<input type="text" name="department" id="department" value="${department}"/>
		
		<input type="submit" name="submit" value="Tìm kiếm">
	</form>
</div>

<table style="border:0; width:100%; cellspacing:0; cellpadding:0;">
    <tr>
    	<td align="left">
        </td>
        <td align="right">
            <a href="form.htm">Thêm mới</a>&nbsp;
        </td>
    </tr>
    <tr height="5"><td colspan="2"/></tr>
    <tr><td colspan="2">			
		<div  style="overflow: auto;">
			<display:table name="${userList}" class="simple2" export="true" id="user" requestURI="" pagesize="100" sort="external" defaultsort="1">
			    <display:column property="username" titleKey="TÊN ĐĂNG NHẬP"/>
			    <display:column property="fullName" titleKey="TÊN ĐẦY ĐỦ"/>
			    <display:column property="department" titleKey="PHÒNG BAN"/>
			    <display:column property="email" titleKey="EMAIL"/>
			    <display:column property="mobile" titleKey="SĐT"/>
			    <display:column property="receiveSms" titleKey="NHẬN SMS"/>
			    <display:column property="receiveMail" titleKey="NHẬN MAIL"/>
			    <display:column titleKey="QUẢN LÝ" media="html">
			    	<a href="form.htm?id=${user.userId}">Sửa</a>&nbsp;
			    	<a href="delete.htm?id=${user.userId}"
			    	   onclick="return confirm('Bạn có chắc chắn muốn xóa?')" >Xóa</a>&nbsp;
			    </display:column>
			    <display:setProperty name="export.csv.filename" value="UserList.csv"/>
			    <display:setProperty name="export.excel.filename" value="UserList.xls"/>
			     <display:setProperty name="export.xml.filename" value="UserList.xml"/>
			</display:table>
		</div>
	</td></tr>
</table>
