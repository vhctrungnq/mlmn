<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<title>server detail form</title>
<body class="section-5"/>
<content tag="heading">CẤU HÌNH FTP SERVER</content>

<form:form commandName="serverDetail" method="post" action="form.htm">
	<form:hidden path="serverId" />
	
    <table class="simple2">
        <tr class="gray">
            <td width="300"><b>Tên Server <font color="#ff0000">(*)</font></b></td>
            <td>
            	${server.serverName}<input type="hidden" value="${server.serverId}" name="srvId" id="srvId">
            </td>
        </tr>
        <tr>
            <td><b>Thư mục lưu file dữ liệu thô<font color="#ff0000">(*)</font></b></td>
            <td>
              <c:choose>
            	<c:when test="${empty serverDetail.serverId}">
            		<form:input path="baseDir"/>
            		<br/><font color="red"><form:errors path="baseDir"/></font>
            	</c:when>
	            <c:otherwise>
	                <b><i>${serverDetail.baseDir}</i></b><form:hidden path="baseDir" />
	            </c:otherwise>
	          </c:choose>
            </td>
        </tr>
        <tr>
            <td><b>Ghi chú</b></td>
            <td><form:textarea path="description" maxlength="500"/></td>
        </tr>
        <tr>
            <td></td>
            <td>
                <input type="submit" class="button" name="save" value="Lưu lại"/>
                <input type="button" value="Hủy bỏ" onClick="window.history.back()" class = "button">
            </td>
        </tr>
    </table>
    
</form:form>
