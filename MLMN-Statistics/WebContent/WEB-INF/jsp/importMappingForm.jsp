<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<title>import mapping form</title>
<body class="section-5"/>
<content tag="heading">Cấu hình thông tin ánh xạ</content>

<form:form commandName="importMapping" method="post" action="form.htm">
	<form:hidden path="createBy" />
	
    <table class="simple2">
        <tr>
            <td width="200"><b>Bảng dữ liệu thô<font color = "red">(*)</font></b></td>
            <td>
	             <form:input path="rawTable" maxlength="30"/>
	             <br/><font color="red"><form:errors path="rawTable"/></font>
            </td>
        </tr>
        <tr>
            <td><b>Tên cột trong bảng<font color = "red">(*)</font></b></td>
            <td>
	             <form:input path="tableColumn" maxlength="30"/>
	             <br/><font color="red"><form:errors path="tableColumn"/></font>
	               
            </td>
        </tr>
        <tr>
            <td><b>Kiểu dữ liệu</b></td>
            <td><form:input path="dataType" maxlength="30"/></td>
        </tr>
        <tr>
            <td><b>Định dạng dữ liệu</b></td>
            <td><form:input path="dataFormat" maxlength="30"/></td>
        </tr>
        <tr>
            <td><b>File Pattern</b></td>
            <td>

	                    <form:select path="patternId" items="${filePatternList}" itemValue="patternId" itemLabel="filePattern"/>
			</td>
        </tr>
        <tr>
            <td><b>Thứ tự cột trong file</b></td>
            <td><form:input path="fileColumn" maxlength="4"/></td>
        </tr>
        <tr>
            <td><b>Header cột trong file</b></td>
            <td><form:input path="fileColumnHeader" maxlength="100"/></td>
        </tr>
        <tr>
            <td><b>Trạng thái</b></td>
            <td>
            	<form:select path="status">
                    <form:option value="Y" label="Đang sử dụng"/>
                    <form:option value="N" label="Không sử dụng"/>
                </form:select>
			</td>
        </tr>
        <tr>
            <td></td>
            <td>
                <input type="submit" class="button" name="save" value="Lưu lại"/>
                <input type="button" value="Hủy bỏ" onClick="history.back()">
            </td>
        </tr>
    </table>
    
</form:form>
