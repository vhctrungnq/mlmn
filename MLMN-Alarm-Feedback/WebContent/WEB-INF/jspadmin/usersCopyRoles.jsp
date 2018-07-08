<%@ include file="/includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<title><fmt:message key="sidebar.admin.usersFormCopyRole"/></title>
<content tag="heading"><fmt:message key="sidebar.admin.usersFormCopyRole"/></content>

<form:form id="checkform" modelAttribute="usersform" commandName="usersform" method="post" action="copyRole.htm">
	<div>
		<form:input id="id" path="id" type="hidden"/>
	</div>
	<table style="padding-top:10px;padding-bottom:10px;border: 1px solid #dddddd;" width="100%" cellspacing="3" cellpadding="0">
		<tr>
			<td class="wid10 mwid90"><fmt:message key="qLNguoiDung.taiKhoan"/>:</td>
			<td class="wid20"><b><i>${ usersform.username}</i></b><form:hidden path="username" /></td>
			<td class="wid10 mwid90"><fmt:message key="qLNguoiDung.email"/>:</td>
			<td><b><i>${ usersform.email}</i></b><form:hidden path="email" /></td>
			<td class="wid20"></td>
		</tr>
		<tr>
			<td><fmt:message key="qLNguoiDung.dienThoai"/>:</td>
			<td><b><i>${ usersform.phone}</i></b><form:hidden path="phone" /></td>
			<td><fmt:message key="qLNguoiDung.chucDanh"/>:</td>
			<td><b><i>${ usersform.position}</i></b><form:hidden path="position" /></td>
			<td></td>
		</tr>
	</table>
	<div style="padding:10px 0px 10px 30px;">
		<img src="${pageContext.request.contextPath}/images/icon/1382386846_arrow-down.png" title="Copy to">
	</div>
	<table class="simple2" >
		<tr>
			<td class="wid8 mwid90"><fmt:message key="qLNguoiDung.taiKhoan"/>&nbsp;<font color="red">(*)</font></td>
			<td class="wid20"><input id="usernameNew" name="usernameNew" value="${usernameNew}" maxlength="20" class="wid90"/></td>
			<td class="wid8 mwid80"><fmt:message key="qLNguoiDung.matKhau"/></td>
			<td class="wid20"><input id="passwordNew" name="passwordNew" value="${passwordNew}" maxlength="100" class="wid90"/></td>
			<td class="wid8 mwid80"><fmt:message key="qLNguoiDung.email"/>&nbsp;<font color="red">(*)</font></td>
			<td class="wid20"><input id="emailNew" name="emailNew" value="${emailNew}" maxlength="100" class="wid90"/></td>
			<td>
				<input class="button" type="submit" name="save" value="<fmt:message key="global.form.luulai"/>" />
	        	<input class="button" type="button" value="<fmt:message key="button.back"/>" onClick='window.location="danh-sach.htm"'>
			</td>
		</tr>
	</table>
</form:form>

<script type="text/javascript">

function focusIt()
{
  var mytext = document.getElementById("usernameNew");
  mytext.focus();
}

onload = focusIt;
</script>