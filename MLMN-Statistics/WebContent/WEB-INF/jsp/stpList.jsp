<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<title>stp list</title>
<body class="section-4"/>
<content tag="heading">DANH SÁCH STP</content>

<table style="border:0;width:100%;cellspacing:0;cellpadding:0;">
    <tr>
    	<td align="left">
			<form:form commandName="filter" method="post" action="list.htm">
				<table style="width:100%; border:0; cellspacing:1; cellpadding:0;">
					<tr>
					    <td>
					    	STP&nbsp;
					    	<form:input path="stpid" size="10"/>
							&nbsp;Nhà sản xuất&nbsp;
							<form:select path="vendor">
								<form:option value="" label="Tất cả"/>
								<form:option value="ALCATEL"/>
			                    <form:option value="HUAWEI"/>
			                    <form:option value="ERICSSON"/>
			                    <form:option value = "NOKIA SIEMEN"/>
			                </form:select>
			                &nbsp;Vị trí lắp đặt&nbsp;
							<form:input path="location" size="10"/>
			                &nbsp;<input type="submit" class="button" name="filter" id="submit" value="Tìm kiếm"/>
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
<display:table name="${stpList}" id="stp" requestURI="" pagesize="100" class="simple2" export="true" sort="external" defaultsort="1">
			    <display:column property="stpid" titleKey="STP"/>
			    <display:column property="sliid" titleKey="SLIID" />        
			    <display:column property="vendor" titleKey="NHÀ SẢN XUẤT"/>
			    <display:column property="hardwareVersion" titleKey="PHIÊN BẢN PHẦN CỨNG" />
			    <display:column property="softwareVersion" titleKey="PHIÊN BẢN PHẦN MỀM" />
			    <display:column property="location" titleKey="VỊ TRÍ LẮP ĐẶT"/>
			    <display:column property="region" titleKey="TRUNG TÂM" headerClass="hide" class="hide" media="html"/> 
			    <display:column property="type64k" titleKey="DUNG LƯỢNG BÁO HIỆU 64K"/>
			    <display:column property="typeHsl" titleKey="DUNG LƯỢNG BÁO HIỆU HSL"/>
			    <display:column property="noStm" titleKey="SỐ STM" />
			    <display:column property="noSteb" titleKey="SỐ STEB" />
			   <c:if test="${checkRoleManager==true}">	  
			    <display:column titleKey="QUẢN LÝ" media="html">
			    	<a href="form.htm?stpid=${stp.stpid}">Sửa</a>&nbsp;
			    	<a href="delete.htm?stpid=${stp.stpid}"
			    	   onclick="return confirm('Bạn có thực sự muốn xóa không?')" >Xóa</a>&nbsp;
			    </display:column>
			    </c:if>
			    <display:setProperty name="export.csv.include_header" value="true"/>
			    <display:setProperty name="export.excel.include_header" value="true"/>
			      <display:setProperty name="export.xml.include_header" value="true"/>
		    	<display:setProperty name="export.csv.filename" value="StpList.csv"/>
			    <display:setProperty name="export.excel.filename" value="StpList.xls"/>
			     <display:setProperty name="export.xml.filename" value="StpList.xml"/>
			</display:table>
</div>
	</td></tr>
</table>
<script type="text/javascript">
	$(function() {
		var cache = {},
		lastXhr;
		$( "#stpid" ).autocomplete({
			minLength: 2,
			source: function( request, response ) {
				var term = request.term;
				if ( term in cache ) {
					response( cache[ term ] );
					return;
				}

				lastXhr = $.getJSON( "${pageContext.request.contextPath}/ajax/getStp.htm", request, function( data, status, xhr ) {
					cache[ term ] = data;
					if ( xhr === lastXhr ) {
						response( data );
					}
				});
			}
		});
	});
</script>
