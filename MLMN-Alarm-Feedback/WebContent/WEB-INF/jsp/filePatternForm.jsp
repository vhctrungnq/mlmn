<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<title>file pattern form</title>
<body class="section-5"/>
<content tag="heading">FILE PATTERN</content>

<form:form commandName="filePattern" method="post" action="form.htm">
	<form:hidden path="patternId" />
	
    <table class="simple2">
        <tr>
            <td width="200"><b>Tên file<font color = "red">(*)</font></b></td>
            <td><form:input path="filePattern" size="50" maxlength="200"/> 
            <br/><font color="red"><form:errors path="filePattern"/></font>
            </td>
        </tr>
        <tr>
            <td><b>Node mạng</b></td>
            <td><form:input path="nodeType" maxlength="30"/>
        </tr>
        <tr>
            <td><b>Bảng dữ liệu</b></td>
            <td><form:input path="rawTable" maxlength="30"/>  
        </tr>
        <tr>
            <td><b>Lớp convert<font color = "red">(*)</font></b></td>
            <td><form:input path="convertClass" size="50" maxlength="100"/>
            <br/><font color="red"><form:errors path="convertClass"/></font>
            </td>
        </tr>
        <tr>
            <td><b>Thư mục con</b></td>
            <td><form:input path="subDir" maxlength="50"/></td>
        </tr>
        <tr>
            <td><b>STT lấy node</b></td>
            <td><form:input path="nodePatternGroup" maxlength="2"/>
             <br/><font color="red"><form:errors path="nodePatternGroup"/></font>   
             </td>
        </tr>
        <tr>
            <td><b>STT lấy thời gian</b></td>
            <td><form:input path="timePatternGroup" maxlength="2"/>
            <br/><font color="red"><form:errors path="timePatternGroup"/></font>  
            </td>
        </tr>
        <tr>
            <td><b>Ký tự phân cách</b></td>
            <td><form:input path="separator" maxlength="5"/>
            <br/><font color="red"><form:errors path="separator"/></font> </td>
        </tr>
        <tr>
            <td><b>Ký tự comment</b></td>
            <td><form:input path="commentChar" maxlength="5"/>
            <br/><font color="red"><form:errors path="commentChar"/></font> </td>
        </tr>
        <tr>
            <td><b>Định dạng thời gian</b></td>
            <td><form:input path="timePattern" maxlength="30"/>
            <br/><font color="red"><form:errors path="timePattern"/></font> </td>
        </tr>
        <tr>
            <td><b>Loại import</b></td>
            <td>
            	<form:select path="importRule">
                    <form:option value="F" label="File"/>
                    <form:option value="M" label="Import Mapping"/>
                </form:select>
			</td>
        </tr>
        <tr>
           <td><b>Trạng thái</b></td><td>  
	            	<form:select path="status" onchange="xl()">
	                    <form:option value="Y" label="Đang sử dụng"/>
	                    <form:option value="N" label="Không sử dụng"/>
	                </form:select>
			</td>
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
