<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<title><fmt:message key="sidebar.stp.qos"/></title>
<content tag="heading"><fmt:message key="sidebar.stp.qos"/></content>
<!-- 
<ul class="ui-tabs-nav">
	<li class=""><a href="${pageContext.request.contextPath}/report/core/stp/hr/list.htm"><span>Báo cáo giờ</span></a></li>
	<li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/report/core/stp/dy/list.htm"><span>Báo cáo ngày</span></a></li>
	<li class=""><a href="${pageContext.request.contextPath}/report/core/stp/wk/list.htm"><span>Báo cáo tuần</span></a></li>
	<li class=""><a href="${pageContext.request.contextPath}/report/core/stp/mn/list.htm"><span>Báo cáo tháng</span></a></li>
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
					    	STP&nbsp;
					    	<form:input path="stpid" size="10"/>
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
			<display:table name="${dyStpQosList}" id="stp" requestURI="" pagesize="100" class="simple2" export="true">
			    <display:column property="stpid" titleKey="STP"/>        
			    <display:column property="vendor" titleKey="NHÀ SẢN XUẤT"/>
			    <display:column property="hardwareVersion" titleKey="PHIÊN BẢN PHẦN CỨNG" />
			    <display:column property="softwareVersion" titleKey="PHIÊN BẢN PHẦN MỀM" />
			    <display:column property="location" titleKey="VỊ TRÍ LẮP ĐẶT"/>
			    <display:column property="region" titleKey="TRUNG TÂM" headerClass="hide" class="hide"/> 
			    <display:column property="type64k" titleKey="DUNG LƯỢNG BÁO HIỆU 64K"/>
			    <display:column property="typeHsl" titleKey="DUNG LƯỢNG BÁO HIỆU HSL"/>
			    <display:column property="noStm" titleKey="SỐ STM" />
			    <display:column property="noSteb" titleKey="SỐ STEB" /> 
			    <display:column titleKey="QUẢN LÝ" media="html">
			    	<a href="${pageContext.request.contextPath}/report/core/stp/dy.htm?stpid=${stp.stpid}">Báo cáo</a>&nbsp;
			    </display:column>
			</display:table>
		</div>
	</td></tr>
</table>
</div>
<script type="text/javascript">
	$(function() {
		$("#notaccordion").addClass("ui-accordion ui-widget ui-helper-reset ui-accordion-icons")
		.find("h3")
			.addClass("ui-accordion-header ui-helper-reset ui-state-active ui-corner-top ui-state-focus")
			.prepend('<span class="ui-icon ui-icon-triangle-1-s"/>')
			.click(function() {
				$(this).toggleClass("ui-state-active ui-corner-top ui-state-focus ui-state-default ui-corner-all")
				.find("> .ui-icon").toggleClass("ui-icon-triangle-1-e ui-icon-triangle-1-s")
				.end().next().toggleClass("ui-icon-triangle-1-e ui-icon-triangle-1-s").toggle();
				return false;
			})
			.next().addClass("ui-accordion-content ui-helper-reset ui-widget-content ui-corner-bottom").show();
		
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
