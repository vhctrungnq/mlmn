<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<title>user form</title>
<content tag="heading">QUẢN LÝ NGƯỜI DÙNG</content>

<form:form commandName="user" method="post" action="form.htm">
	<form:hidden path="userId" />
	<form:hidden path="enabled" />
    <table class="simple2">
        <tr>
            <td width="200">Tên đăng nhập<font color = "red">(*)</font></td>
            <td>            	
            	<c:choose>
				  <c:when test="${empty user.userId}">
				    <form:input path="username" maxlength="20" />
				  </c:when>
				  <c:otherwise>
				    <b><i>${user.username}</i></b><form:hidden path="username" />
				  </c:otherwise>
				</c:choose>
 					<br/><font color="red"><form:errors path="username"/></font>
            </td>
        </tr>
        <tr>
            <td>Mật khẩu<font color = "red">(*)</font></td>
            <td><form:input type="password" path="password" maxlength="50" size="35"/>
  				<br/><font color="red"><form:errors path="password"/></font>
            </td>
        </tr>
        
         <tr>
            <td>Nhập lại mật khẩu<font color = "red">(*)</font></td>
            <td><input type="password" name="passreenter" value="${passreenter}" id="passreenter" maxlength="255" size="35"/><br/>
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
            <td>Tên đầy đủ</td>
            <td>            	
            	<form:input path="fullName" maxlength="30"/>
            	<br/><font color="red"><form:errors path="fullName"/></font>
            </td>
        </tr>
        <tr>
            <td>Phòng ban</td>
            <td>            	
            	<form:input path="department" maxlength="40"/>
            	<br/><font color="red"><form:errors path="fullName"/></font>
            </td>
        </tr>
        <tr>
            <td>Email</td>
            <td>
            	<form:input path="email"/>
			</td>
        </tr>
        <tr>
            <td>SĐT</td>
            <td>
            	<form:input path="mobile" maxlength="15"/>
			</td>
        </tr>
        <tr>
            <td>Nhận SMS</td>
            <td>            	
            	<form:checkbox path="receiveSms" value="Y"/>
            </td>
        </tr>
        <tr>
            <td>Nhận Email</td>
            <td>
            	<form:checkbox path="receiveMail" value="Y"/>
			</td>
        </tr>
        <c:if test="${not empty user.userId}">
            <tr>
                <td>Quyền<font color = "red">(*)</font></td>
                <td style="color:gray"><i>(Lựa chọn quyền ở danh sách bên trái rồi thêm vào danh sách bên phải)</i></td>
            </tr>
            <tr>
                <td>&nbsp;</td>
                <td>
                    <form:select path="roles" multiple="true" items="${roleList}" itemValue="roleId" itemLabel="roleId"/>
                </td>
            </tr>
        </c:if>
        <tr>
            <td></td>
            <td>
                <input type="submit" class="button" name="save" value="Lưu lại"/>
                <input type="button" value="Hủy bỏ" onClick='window.location="list.htm"' class="button">
            </td>
        </tr>
    </table>
    
</form:form>

    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/jquery.multiselect2side.css"/>
    <script type="text/javascript" src="${pageContext.request.contextPath}/scripts/jquery.multiselect2side.js" ></script>
    <script type="text/javascript">
        $().ready(function() {
            $('select').multiselect2side();
        });
    </script>
