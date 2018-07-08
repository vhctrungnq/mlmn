<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<title>system config form</title>
<body class="section-5"/>
<content tag="heading">THAM SỐ HỆ THỐNG</content>

<form:form commandName="systemConfig" method="post" action="form.htm">
	<form:hidden path="id" />
    <table class="simple2">
        <tr>
            <td width="200"><b>Tên tham số hệ thống<font color = "red">(*)</font></b></td>
            <td><form:input path="paramName" maxlength="30"/>
              <br/><font color="red"><form:errors path="paramName"/></font>    
            </td>
        </tr>
        <tr>
            <td><b>Giá trị tham số hệ thống<font color = "red">(*)</font></b></td>
            <td><form:input path="paramValue" maxlength="50"/>
            	<br/><font color="red"><form:errors path="paramValue"/></font>
            </td>
        </tr>
        <tr>
            <td><b>Ghi chú</b></td>
            <td><form:textarea path="description" maxlength="200"/></td>
        </tr>
        <tr>
            <td><b>Loại Config</b></td>
            <td>
            	<form:select path="configType" maxlength="20">
                    <form:option value="A" label="Alert"/>
                    <form:option value="C" label="Core"/>
                    <form:option value="I" label="Import"/>
                    <form:option value="R" label="Report"/>
                    <form:option value="S" label="Summary"/>
                </form:select>
			</td>
        </tr>
        <tr>
            <td></td>
            <td>
                <input type="submit" class="button" name="Lưu lại" id="submit" value="Lưu lại"/>
                <input type="button" value="Hủy bỏ" onClick='window.location="list.htm"' class = "button">
            </td>
        </tr>
    </table>
    
</form:form>
