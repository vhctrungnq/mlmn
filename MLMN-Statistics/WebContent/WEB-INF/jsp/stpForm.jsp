<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<title>stp form</title>
<body class="section-4"/>
<content tag="heading">STP</content>

<form:form commandName="stp" method="post" action="form.htm">
	<form:hidden path="region" />
	
    <table class="simple2">
        <tr>
            <td width="200"><b>Tên STP<font color = "red">(*)</font></b></td>
            <td>            	
              <c:choose>
                <c:when test="${empty stp.region}">
                    <form:input path="stpid" maxlength="80"/>
                </c:when>
                <c:otherwise>
                    <b><i>${stp.stpid}</i></b><form:hidden path="stpid" />
                </c:otherwise>
              </c:choose>
              <br/><font color="red"><form:errors path="stpid"/></font>  
            </td>
        </tr>
         <tr>
            <td><b>SLIID</b></td>
            <td><form:input path="sliid" maxlength="20"/><br/><font color="red"><form:errors path="sliid"/></font>  </td>
        </tr>
        <tr>
            <td><b>Nhà sản xuất</b></td>
            <td>
            	<form:select path="vendor">
					<form:option value="ALCATEL"/>
                    <form:option value="HUAWEI"/>
                    <form:option value="ERICSSON"/>
                    <form:option value = "NOKIA SIEMEN"/>
                </form:select>
			</td>
        </tr>
        <tr>
            <td><b>Phiên bản phần cứng</b></td>
            <td><form:input path="hardwareVersion" maxlength="20"/></td>
        </tr>
        <tr>
            <td><b>Phiên bản phần mềm</b></td>
            <td><form:input path="softwareVersion" maxlength="20"/></td>
        </tr>
        <tr>
            <td><b>Vị trí lắp đặt</b></td>
            <td><form:input path="location" maxlength="80"/></td>
        </tr>
        <tr>
            <td><b>Dung lượng báo hiệu 64K</b></td>
            <td><form:input path="type64k" maxlength="20"/><br/><font color="red"><form:errors path="type64k"/></font></td>
        </tr>
        <tr>
            <td><b>Dung lượng báo hiệu HSL</b></td>
            <td><form:input path="typeHsl" maxlength="20"/><br/><font color="red"><form:errors path="typeHsl"/></font></td>
        </tr>
        <tr>
            <td><b>Số STM</b></td>
            <td><form:input path="noStm" maxlength="20"/><br/><font color="red"><form:errors path="noStm"/></font></td>
        </tr>
        <tr>
            <td><b>Số STEB</b></td>
            <td><form:input path="noSteb" maxlength="20"/><br/><font color="red"><form:errors path="noSteb"/></font>  </td>
        </tr>
       
        <tr>
            <td></td>
            <td>
                <input type="submit" class="button" name="save" value="Lưu lại"/>
                <input type="button" value="Hủy bỏ" onClick='window.location="list.htm"' class = "button">
            </td>
        </tr>
    </table>
    
</form:form>
