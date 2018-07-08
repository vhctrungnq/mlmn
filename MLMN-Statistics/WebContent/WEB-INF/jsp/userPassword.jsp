<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<title>user form</title>
<content tag="heading">ĐỔI MẬT KHẨU</content>

<form:form commandName="user" method="post" action="password.htm">
	<form:hidden path="userId" />
	<form:hidden path="fullName"/>
	<form:hidden path="department"/>
	<form:hidden path="email"/>
	<form:hidden path="mobile"/>
	<form:hidden path="receiveSms"/>
	<form:hidden path="receiveMail"/>
	<form:hidden path="enabled" />
    <table class="simple2">
         <tr>
            <td width="200">Tên đăng nhập</td>
            <td>   
				    <b><i>${user.username}</i></b><form:hidden path="username" />
            </td>
        </tr>
        <tr>
            <td>Mật khẩu hiện tại<font color = "red">(*)</font></td>
            <td>            	
            	<input type="password" name="oldpass" value="${oldpass}" maxlength="50"/>
            	<br/><font color="red"><c:choose>
			  <c:when test="${empty oldpasserror}">
			  	${oldpasserror}
			  </c:when>
			  <c:otherwise>
			    <fmt:message key="${oldpasserror}"/>
			  </c:otherwise>
			</c:choose></font>
            </td>
        </tr>
        <tr>
            <td>Mật khẩu mới</td>
            <td>            	
            	<form:password path="password"/>
            	<br/><font color="red">${passworderror}</font>
            </td>
        </tr>
        <tr>
            <td>Nhập lại Password</td>           	
            <td><input type="password" name="passreenter" value="${passreenter}" id="passreenter" maxlength="255"/><br/>
            <font color="red">
            <c:choose>
			  <c:when test="${empty passreentererror}">
			  	${passreentererror}
			  </c:when>
			  <c:otherwise>
			    <fmt:message key="${passreentererror}"/>
			  </c:otherwise>
			</c:choose>
			</font>
            </td>
        </tr>
        <tr>
            <td></td>
            <td>
                <input type="submit" class="button" name="save" value="Save"/>
                <input type="button" value="Cancel" onClick='window.location="${pageContext.request.contextPath}/welcome.htm"' class = "button">
            </td>
        </tr>
    </table>
    
</form:form>
