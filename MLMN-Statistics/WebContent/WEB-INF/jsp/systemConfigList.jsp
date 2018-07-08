<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<title>system config list</title>
<body class="section-5"/>
<content tag="heading">THAM SỐ HỆ THỐNG</content>

<form:form commandName="filter" method="post" action="list.htm">
<table style="border:0; width:100%;">
    <tr>
    	<td align="left">
    	Tên tham số hệ thống&nbsp;<form:input name="paramName" value="${paramName}" path="paramName" size="16" maxlength="30"/>
    	&nbsp;&nbsp;Giá trị tham số hệ thống&nbsp;<form:input name="paramValue" value="${paramValue}" path="paramValue" size="16" maxlength="20"/>
    	Loại Config
            	<form:select path="configType" maxlength="20">
            		<form:option value=""   label="Tất cả"/>
                    <form:option value="A" label="Alert"/>
                    <form:option value="C" label="Core"/>
                    <form:option value="I" label="Import"/>
                    <form:option value="R" label="Report"/>
                    <form:option value="S" label="Summary"/>
                </form:select>
        &nbsp;&nbsp;<input type="submit" class="button" name="filter" value="Tìm kiếm"/>
        </td>
        <td align="right">
            <a href="form.htm">Thêm mới</a>&nbsp;
       	</td>
    </tr>
</table>
</form:form>
<br/>
<div  style="overflow: auto;">
<display:table name="${systemConfigList}" id="systemConfig" class="simple2" export="true" sort="external" defaultsort="1" requestURI="" pagesize="100">
		    <display:column property="paramName" titleKey="TÊN THAM SỐ HỆ THỐNG"/>        
		    <display:column property="paramValue" titleKey="GIÁ TRỊ THAM SỐ HỆ THỐNG"/>
		    <display:column property="trangthai" titleKey="LOẠI CONFIG"/>
		    <display:column property="description" titleKey="GHI CHÚ"/>
		    <display:column titleKey="QUẢN LÝ" media="html">
		    	<a href="form.htm?id=${systemConfig.id}">Sửa</a>&nbsp;
		    	<a href="delete.htm?id=${systemConfig.id}"
		    	   onclick="return confirm('Bạn có thực sự muốn xóa?')">Xóa</a>
		    </display:column>
		     <display:setProperty name="export.csv.filename" value="SystemConfig.csv"/>
			    <display:setProperty name="export.excel.filename" value="SystemConfig.xls"/>
			     <display:setProperty name="export.xml.filename" value="SystemConfig.xml"/>
		</display:table>
</div>