<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<style type="text/css">    #doublescroll { overflow: auto; overflow-y: hidden; }    #doublescroll p { margin: 0; padding: 1em; white-space: nowrap; }</style>
<title>kpi mapping list</title>
<content tag="heading">DANH SÁCH KPI MAPPING</content>

<form:form commandName="filter" method="post" action="list.htm">
<table style="border:0; width:100%; cellspacing:0; cellpadding:0;">
    <tr>
    	<td>Tên báo cáo</td><td><form:input path="reportName" size="16" maxlength="30"/></td>
    	<td>Tên cột báo cáo</td><td><form:input path="reportNameColumn" size="16" maxlength="30"/></td>
    	<td>Nhà sản xuất</td><td>
							<form:select path="vendor">
								<form:option value="" label="Tất cả"/>
								<form:option value="ALCATEL"/>
			                    <form:option value="HUAWEI"/>
			                    <form:option value="ERICSSON"/>
			                    <form:option value="NOKIA SIEMEN"/>
			                </form:select></td> 
    	<td>Tên bảng</td><td><form:input path="tableName" size="16" maxlength="100"/></td>      	
	</tr>
    <tr>
    	<td>Tên cột</td><td><form:input path="tableColumn" size="16" maxlength="700"/></td> 
    	<td>Trạng thái</td><td> 
	            	<form:select style="width:130px" path="status">
	                    <form:option value="" label="Tất cả"/>
	                    <form:option value="Y" label="Đang sử dụng"/>
	                    <form:option value="N" label="Không sử dụng"/>
	                </form:select></td>
	     <td>Mạng</td><td>
	     				<form:select path="network">
								<form:option value="" label="Tất cả"/>
								<form:option value="2G"/>
			                    <form:option value="3G"/>
			                    <form:option value="CORE"/>
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

<div  id="doublescroll">
<display:table name="${KpiMaplists}" id="kpimapping" class="simple2" requestURI="" pagesize="100" export="true" sort="external" defaultsort="1">
		    <display:column property="reportName" titleKey="Tên Báo Cáo"/>
		    <display:column property="reportNameColumn" titleKey="Tên cột báo cáo"/>
		    <display:column property="formula" titleKey="Công Thức" style="width:500px"/>
		    <display:column property="vendor" titleKey="Nhà Sản Xuất"/>
		    <display:column property="tableName" titleKey="Tên Bảng"/>
		    <display:column property="tableColumn" titleKey="Tên Cột" />
		    <display:column property="databaseFormula" titleKey="Công Thức CSDL"/>
		    <display:column property="trangthai" titleKey="TRẠNG THÁI"/>
		    <display:column property="updateDate" format="{0,date,dd/MM/yyyy}" titleKey="Ngày Cập Nhật"/>
		    <display:column property="description" titleKey="Ghi Chú"/>
		    <display:column property="network" titleKey="Mạng"/>
		    <display:column property="version" titleKey="Phiên Bản"/>
		    <display:column titleKey="QUẢN LÝ" media="html">
		    	<a href="form.htm?configId=${kpimapping.configId}">Sửa</a>&nbsp;
		    	<a href="delete.htm?configId=${kpimapping.configId}"
		    	   onclick="return confirm('Bạn có thực sự muốn xóa?')">Xóa</a>
		    </display:column>
		    
		    <display:setProperty name="export.csv.include_header" value="true"/>
		    <display:setProperty name="export.excel.include_header" value="true"/>
	    	<display:setProperty name="export.csv.filename" value="KpiMappingList.csv"/>
		    <display:setProperty name="export.excel.filename" value="KpiMappingList.xls"/>
		</display:table>
</div>	
<script type="text/javascript">
    function DoubleScroll(element) {
        var scrollbar= document.createElement('div');
        scrollbar.appendChild(document.createElement('div'));
        scrollbar.style.overflow= 'auto';
        scrollbar.style.overflowY= 'hidden';
        scrollbar.firstChild.style.width= element.scrollWidth+'px';
        scrollbar.firstChild.style.paddingTop= '1px';
        scrollbar.firstChild.appendChild(document.createTextNode('\xA0'));
        scrollbar.onscroll= function() {
            element.scrollLeft= scrollbar.scrollLeft;
        };
        element.onscroll= function() {
            scrollbar.scrollLeft= element.scrollLeft;
        };
        element.parentNode.insertBefore(scrollbar, element);
    }
    DoubleScroll(document.getElementById('doublescroll'));
  </script>