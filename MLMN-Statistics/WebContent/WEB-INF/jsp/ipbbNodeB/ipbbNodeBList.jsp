<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<style>
	#doublescroll { overflow: auto; overflow-y: hidden; }
	#doublescroll p { margin: 0; padding: 1em; white-space: nowrap; }
</style>

<title>${title}</title>
<body class="section-5"/>
<content tag="heading">${title}</content>

<form method="post" action="list.htm?type=${type}">
	<table style="border:0; width:100%;">
		<tr>
			<td align="left">
		        NodeB Id &nbsp; <input value="${nodeId }" id="nodeId" name="nodeId" />&nbsp;&nbsp;
		        Tên trạm &nbsp; <input value="${tentram }" id="tentram" name="tentram"/>&nbsp;&nbsp;
				<input type="submit" class="button" name="filter" id="submit" value="Tìm kiếm"/>
			</td>  
		 </tr>
		 <tr> 
		    <td align="right" style="padding-top:5px;">
	            <a href="upload.htm?type=${type}">Upload file</a>&nbsp;
	            <a href="form.htm?type=${type}">Thêm mới</a>&nbsp;
	        </td>
	     </tr>
	  </table>
</form> 
<div style="overflow: auto">
<display:table name="${ipbbNodeBList}" id="item" requestURI="" pagesize="100" class="simple2" sort="external" defaultsort="1" export="true">
		    <display:column class="centerColumnMana" titleKey="STT"> <c:out value="${item_rowNum}"/> </display:column>
		    <display:column property="nodebId" titleKey="NodeB ID" sortName="nodebId" sortable="true" headerClass="master-header-3"/>
		    <display:column property="tenDoiTac" titleKey="Tên đối tác" sortName="nodebId" sortable="true" headerClass="master-header-3"/>
		    <display:column property="tenTram" titleKey="Tên trạm"  sortName="tenTram" sortable="true" headerClass="master-header-3"/>
		    <display:column property="maTram" titleKey="Mã trạm" sortName="maTram" sortable="true" headerClass="master-header-3"/>
		    <display:column property="bw" titleKey="BandWidth"  sortName="bw" sortable="true" headerClass="master-header-3"/>
		    <display:column property="description" titleKey="Ghi chú"  sortName="description" sortable="true" headerClass="master-header-3"/>  
		    
		    <display:column titleKey="Quản lý" media="html" class="centerColumnMana">
		    	<a href="form.htm?type=${type}&&id=${item.id}">Sửa</a>&nbsp;
		    	<a href="delete.htm?type=${type}&&id=${item.id}"
		    	  onclick="return confirm('Bạn có thực sự muốn xóa không?')">Xóa</a>
		    </display:column>
		    
		    <display:setProperty name="export.csv.include_header" value="true"/>
		    <display:setProperty name="export.excel.include_header" value="true"/>
	    	<display:setProperty name="export.csv.filename" value="IpbbNodeBList.csv"/>
		    <display:setProperty name="export.excel.filename" value="IpbbNodeBList.xls"/>
		</display:table>
</div>