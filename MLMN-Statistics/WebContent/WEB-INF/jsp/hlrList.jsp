<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<title>HLR List</title>
<body class="section-4"/>
<content tag="heading">DANH SÁCH HLR</content>

<table border="0" width="100%" cellspacing="0" cellpadding="0">
    <tr>
    	<td align="left">
			<form:form commandName="filter" method="post" action="list.htm">
				<table width="100%" border="0" cellspacing="1" cellpadding="0">
					<tr>
					    <td>
					    	HLR&nbsp;
					    	<form:input path="hlrid" size="10"/>
							&nbsp;Nhà sản xuất&nbsp;
							<form:select path="vendor">
								<form:option value="" label="Tất cả"/>
								<form:option value="ALCATEL"/>
			                    <form:option value="HUAWEI"/>
			                    <form:option value="ERICSSON"/>
			                    <form:option value="NOKIA SIEMEN"/>
			                </form:select>
			                &nbsp;Vị trí lắp đặt&nbsp;
							<form:input path="location" size="10"/>
			                &nbsp;<input type="submit" class="button" name="filter" value="Tìm kiếm"/>
			            </td>
			        </tr>		
				</table>
			</form:form>
        </td>
         <c:if test="${checkRoleManager==true}">	
        <td align="right">
            <a href="upload.htm">Upload file</a>&nbsp;
            <a href="form.htm">Thêm mới</a>&nbsp;
        </td>
        </c:if>
    </tr>
    <tr height="5"><td colspan="2"/></tr>
    <tr><td colspan="2">			
			<div  style="overflow: auto;">
<display:table name="${hlrList}" id="hlr" requestURI="" pagesize="100" class="simple2" export="true" sort="external" defaultsort="1">
			    <display:column property="hlrid" titleKey="HLR"/>        
			    <display:column property="vendor" titleKey="NHÀ SẢN XUẤT"/>
			    <display:column property="location" titleKey="VỊ TRÍ LẮP ĐẶT"/>
			    <display:column property ="hardwareVersion" titleKey="PHẦN CỨNG" />
			    <display:column property="softwareVersion" titleKey="PHẦN MỀM" />
			   <c:if test="${checkRoleManager==true}">	
			    <display:column titleKey="QUẢN LÝ" media="html">
			    	<a href="form.htm?hlrid=${hlr.hlrid}">Sửa</a>&nbsp;
			    	<a href="delete.htm?hlrid=${hlr.hlrid}"
			    	   onclick="return confirm('Bạn có chắc muốn xóa?')" >Xóa</a>&nbsp;
			    </display:column>
			   </c:if> 
			    <display:setProperty name="export.csv.include_header" value="true"/>
		    	<display:setProperty name="export.excel.include_header" value="true"/>
		    	<display:setProperty name="export.xml.include_header" value="true"/>
	    		<display:setProperty name="export.xml.filename" value="HlrList.xml"/>
	    		<display:setProperty name="export.csv.filename" value="HlrList.csv"/>
		    	<display:setProperty name="export.excel.filename" value="HlrList.xls"/>
		    	
			</display:table>
</div>
	</td></tr>
</table>

<script type="text/javascript">
$(function() {
	var cache = {},
	lastXhr;
	$( "#hlrid" ).autocomplete({
		minLength: 2,
		source: function( request, response ) {
			var term = request.term;
			if ( term in cache ) {
				response( cache[ term ] );
				return;
			}

			lastXhr = $.getJSON( "${pageContext.request.contextPath}/ajax/getHlr.htm", request, function( data, status, xhr ) {
				cache[ term ] = data;
				if ( xhr === lastXhr ) {
					response( data );
				}
			});
		}
	});
});	
</script>
