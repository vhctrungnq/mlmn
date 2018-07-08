<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<title>HLR List</title>
<content tag="heading">LIST HLR DAILY REPORT</content>
<!-- 
<ul class="ui-tabs-nav">
	<li class=""><a href="${pageContext.request.contextPath}/report/core/hlr/hr/list.htm"><span>Báo cáo giờ</span></a></li>
	<li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/report/core/hlr/dy/list.htm"><span>Báo cáo ngày</span></a></li>
	<li class=""><a href="${pageContext.request.contextPath}/report/core/hlr/wk/list.htm"><span>Báo cáo tuần</span></a></li>
	<li class=""><a href="${pageContext.request.contextPath}/report/core/hlr/mn/list.htm"><span>Báo cáo tháng</span></a></li>
</ul>
 -->
<div class="ui-tabs-panel">
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
			                </form:select>
			                &nbsp;Vị trí lắp đặt&nbsp;
							<form:input path="location" size="10"/>
			                &nbsp;<input type="submit" class="button" name="filter" value="Tìm kiếm"/>
			            </td>
			        </tr>		
				</table>
			</form:form>
        </td>
    </tr>
    <tr height="5"><td colspan="2"/></tr>
    <tr><td colspan="2">			
		<div  style="overflow: auto;">
			<display:table name="${dyHlrQosList}" id="dyHlr" requestURI="" pagesize="100" class="simple2" export="true">
			    <display:column property="hlrid" title="HLR"/>        
			    <display:column property="vendor" title="Nhà sản xuất"/>
			    <display:column property="location" title="Vị trí lắp đặt"/>
			    <display:column property ="hardwareVersion" title="Phần cứng" />
			    <display:column property="softwareVersion" title="Phần mềm" />
			    <display:column title="Quản lý" media="html">
			    	<a href="${pageContext.request.contextPath}/report/core/hlr/dy.htm?hlrid=${dyHlr.hlrid}">Báo cáo</a>&nbsp;
			    </display:column>
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
