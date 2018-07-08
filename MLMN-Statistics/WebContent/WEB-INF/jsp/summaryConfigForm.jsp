<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<title>summary config form</title>
<body class="section-5"/>
<content tag="heading">CẤU HÌNH KPI COUNTER MAPPING</content>

<form:form commandName="summaryConfig" method="post" action="form.htm">
	<form:hidden path="configId" />
	
    <table class="simple2">
        <tr>
            <td width="300"><b>Tên bảng Summary<font color = "red">(*)</font></b></td>
            <td>
            	<form:input path="tableName" maxlength="30"/>
            	 <br/><font color="red"><form:errors path="tableName"/></font>
            </td>
        </tr>
        <tr>
            <td><b>Tên cột Summary<font color = "red">(*)</font></b></td>
            <td>
            	<form:input path="columnName" maxlength="30"/>
            	 <br/><font color="red"><form:errors path="columnName"/></font>
            </td>
        </tr>
        <tr>
            <td><b>Kiểu dữ liệu của cột trong bảng Summary<font color = "red">(*)</font></b></td>
            <td><form:input path="dataType" maxlength="20"/>
            <br/><font color="red"><form:errors path="dataType"/></font>
            </td>
        </tr>
        <tr>
            <td><b>Là khóa chính trong bảng Summary</b></td>
            <td><form:checkbox path="keyColumn" value="Y"/></td>
        </tr>
        <tr>
            <td><b>View hoặc Bảng dữ liệu thô</b></td>
            <td><form:input path="tableSource" maxlength="100"/></td>
        </tr>
        <tr>
            <td><b>Công thức Summary</b></td>
            <td><form:input path="formula" size="50" maxlength="700"/></td>
        </tr>
        <tr>
            <td><b>Kiểu Summary</b></td>
            <td><form:input path="summaryType" size="2" maxlength="3"/>&nbsp;(HX: hourly, DX: daily, WX: weekly, MX: monthly; X là số thứ tự ưu tiên khi chạy thủ tục summary, giá trị nhỏ chạy trước)
        	<br/><font color="red"><form:errors path="summaryType"/></font></td>
        </tr>
        <tr>
            <td><b>Trạng thái của KPI</b></td>
            <td>
            	<form:select path="status">
                    <form:option value="Y" label="Đang sử dụng"/>
                    <form:option value="N" label="Không sử dụng"/>
                </form:select>
			</td>
        </tr>
        <tr>
            <td><b>Ghi chú</b></td>
            <td><form:textarea path="description" maxlength="300"/></td>
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
