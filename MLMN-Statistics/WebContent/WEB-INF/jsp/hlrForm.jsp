<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<title>HLR Form</title>
<body class="section-4"/>
<content tag="heading">HLR</content>

<form:form commandName="hlr" method="post" action="form.htm">
	<form:hidden path="region" />
	
    <table class="simple2">
        <tr>
            <td width="200"><b>HLR<font color = "red">(*)</font></b></td>
            <td>            	
              <c:choose>
                <c:when test="${empty hlr.region}">
                    <form:input path="hlrid" maxlength="80"/>
                </c:when>
                <c:otherwise>
                    <b><i>${hlr.hlrid}</i></b><form:hidden path="hlrid" />
                </c:otherwise>
              </c:choose>
              <br/><font color="red"><form:errors path="hlrid"/></font>  
            </td>
        </tr>
        <tr>
            <td><b>Nhà sản xuất</b></td>
            <td>
            	<form:select path="vendor">
					<form:option value="ALCATEL"/>
                    <form:option value="HUAWEI"/>
                    <form:option value="ERICSSON"/>
                    <form:option value="NOKIA SIEMEN"/>
                </form:select>
			</td>
        </tr>
        <tr>
            <td><b>Phần cứng</b></td>
            <td><form:input path="hardwareVersion"  maxlength="20"/></td>
        </tr>
        <tr>
            <td><b>Phần mềm</b></td>
            <td><form:input path="softwareVersion"  maxlength="20"/></td>
        </tr>
        <tr>
            <td><b>Vị trí lắp đặt</b></td>
            <td><form:input path="location"  maxlength="80"/></td>
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
