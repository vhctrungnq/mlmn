<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<title>summary config list</title>
<content tag="heading">CẤU HÌNH KPI COUNTER MAPPING</content>

<form:form commandName="filter" method="post" action="list.htm">
<table style="border:0; width:100%; cellspacing:0; cellpadding:0;">
    <tr>
    	<td>Tên bảng Summary</td><td><form:input path="tableName" size="16" maxlength="30"/></td>
    	<td>Tên cột</td><td><form:input path="columnName" size="16" maxlength="30"/></td>
    	<td>Tên bảng nguồn</td><td><form:input path="tableSource" size="16" maxlength="100"/></td>        	
	</tr>
    <tr>
    	<td>Công thức</td><td><form:input path="formula" size="16" maxlength="700"/></td> 
    	<td>Kiểu Summary</td><td><form:input path="summaryType" size="16" maxlength="3"/></td>
    	<td>Trạng thái KPI</td><td> 
	            	<form:select style="width:130px" path="status">
	                    <form:option value="" label="Tất cả"/>
	                    <form:option value="Y" label="Đang sử dụng"/>
	                    <form:option value="N" label="Không sử dụng"/>
	                </form:select></td>
	     <td></td><td><input type="submit" class="button" name="filter" value="Tìm kiếm"/></td>
	     <td align="right">
            <a href="upload.htm">Upload file</a>&nbsp;
            <a href="form.htm">Thêm mới</a>&nbsp;
       	 </td>
     </tr>
 </table>
</form:form>
<br/>

<div  style="overflow: auto;">
<display:table name="${summaryConfigList}" id="summaryConfig" class="simple2" requestURI="" pagesize="100" export="true" sort="external" defaultsort="1">
		    <display:column property="tableName" titleKey="TÊN BẢNG SUMMARY"/>
		    <display:column property="columnName" titleKey="TÊN CỘT"/>
		    <display:column property="dataType" titleKey="KIỂU DỮ LIỆU"/>
		    <display:column property="keyColumn" titleKey="KHÓA CHÍNH" headerClass="hide" class="hide"/>
		    <display:column property="tableSource" titleKey="BẢNG NGUỒN"/>
		    <display:column property="formula" titleKey="CÔNG THỨC" style="width:500px"/>
		    <display:column property="description" titleKey="MÔ TẢ" headerClass="hide" class="hide"/>
		    <display:column property="summaryType" titleKey="KIỂU SUMMARY"/>
		     <display:column property="trangthai" titleKey="TRẠNG THÁI"/>
		    <display:column titleKey="QUẢN LÝ" media="html">
		    	<a href="form.htm?configId=${summaryConfig.configId}">Sửa</a>&nbsp;
		    	<a href="delete.htm?configId=${summaryConfig.configId}"
		    	   onclick="return confirm('Bạn có thực sự muốn xóa?')">Xóa</a>
		    </display:column>
		    
		    <display:setProperty name="export.csv.include_header" value="true"/>
		    <display:setProperty name="export.excel.include_header" value="true"/>
	    	<display:setProperty name="export.csv.filename" value="SummaryConfigList.csv"/>
		    <display:setProperty name="export.excel.filename" value="SummaryConfigList.xls"/>
		</display:table>
</div>	
