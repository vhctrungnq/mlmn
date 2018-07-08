<%@ include file="/includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    

<ul class="ui-tabs-nav">
<c:choose>
	<c:when test="${function == 'cssr-2g'}">
		<li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/alarm/bieu-do-giam-sat/cssr-2g.htm"><span><fmt:message key="Network 2g"/></span></a></li>
		<li class=""><a href="${pageContext.request.contextPath}/alarm/bieu-do-giam-sat/cssr-3g.htm"><span><fmt:message key="Network 3g"/></span></a></li>
		<li class=""><a href="${pageContext.request.contextPath}/alarm/bieu-do-giam-sat/cssr-cell.htm"><span><fmt:message key="Cell (CSSR dưới 50%)"/></span></a></li>
		
	</c:when>
 	<c:when test="${function == 'cssr-3g'}">
		<li class=""><a href="${pageContext.request.contextPath}/alarm/bieu-do-giam-sat/cssr-2g.htm"><span><fmt:message key="Network 2g"/></span></a></li>
		<li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/alarm/bieu-do-giam-sat/cssr-3g.htm"><span><fmt:message key="Network 3g"/></span></a></li>
		<li class=""><a href="${pageContext.request.contextPath}/alarm/bieu-do-giam-sat/cssr-cell.htm"><span><fmt:message key="Cell (CSSR dưới 50%)"/></span></a></li>
		
	</c:when>
	<c:when test="${function == 'cssr-cell'}">
		<li class=""><a href="${pageContext.request.contextPath}/alarm/bieu-do-giam-sat/cssr-2g.htm"><span><fmt:message key="Network 2g"/></span></a></li>
		<li class=""><a href="${pageContext.request.contextPath}/alarm/bieu-do-giam-sat/cssr-3g.htm"><span><fmt:message key="Network 3g"/></span></a></li>
		<li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/alarm/bieu-do-giam-sat/cssr-cell.htm"><span><fmt:message key="Cell (CSSR dưới 50%)"/></span></a></li>
		
	</c:when>
 	<c:otherwise></c:otherwise>
</c:choose>
</ul>
<title>BIỂU ĐỒ GIÁM SÁT CSSR</title>
<content tag="heading">BIỂU ĐỒ GIÁM SÁT CSSR </content>

<div class="ui-tabs-panel">
<form:form id="filterController" method="post" action="${function}.htm">
<table border="0" width="100%" cellspacing="0" cellpadding="0" class="form">
		<tr>
			<td class="wid50"></td>
			<td class="wid50"></td> 
		</tr>
		<tr>
			<td>
			<c:choose>
				<c:when test="${function == 'cssr-cell'}">
					<input type="text" id="hour_2g" name="hour_2g" value="${hour_2g}" style="width: 6%" maxlength="4"/>&nbsp;giờ 2G&nbsp;
					<input type="text" id="hour_3g" name="hour_3g" value="${hour_3g}" style="width: 6%" maxlength="4"/>&nbsp;giờ 3G&nbsp;
				</c:when>
				<c:otherwise>
					<input type="text" id="hour" name="hour" value="${hour}" style="width: 6%" maxlength="4"/>&nbsp;giờ&nbsp;
				</c:otherwise>
			</c:choose>
			<input class="button" type="submit" name="filter" value="<fmt:message key="Hiển thị"/>" />
			</td>
			<td align="right">
						<fmt:message key="baoCaoTuan.autoRefresh"/>&nbsp;
						<input type="text" id="autoRefresh" name="autoRefresh" value="${autoRefresh}" style="width: 6%" maxlength="4"/>&nbsp;giây
			</td>			
		</tr>
</table>
<br>
<table border="0" width="100%" cellspacing="0" cellpadding="0" class="form">
<c:choose>
		<c:when test="${function == 'cssr-2g'}">
			<tr>
				<td style = "width:50%" id="CssrChart_VTD"></td>
				<td style = "width:50%" id="CssrChart_VTT"></td>
			</tr>
			<tr>
				 <td style = "width:50%" id="ChartCSSR_H_VTD"></td>
				<td style = "width:50%" id="ChartCSSR_H_VTT"></td>
			</tr>
		</c:when>
		<c:when test="${function == 'cssr-3g'}">
			<tr>
				<td style = "width:99%" id="speechCssrChart"></td>
			</tr>
			<tr>
				 <td style = "width:99%" id="cssrHourlyChart"></td>
			</tr>
			<tr>
				 <td style = "width:99%" id="columnlinechar"></td>
			</tr>
		</c:when>
		<c:when test="${function == 'cssr-cell'}">
			<tr>
				<td style = "width:50%" id="CssrChart_Cell_2G"></td>
				<td style = "width:50%" id="CssrChart_Cell_3G"></td>
			</tr>
			
		</c:when>
		<c:otherwise></c:otherwise>
</c:choose>
</table>
</form:form>
</div>

<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/highcharts.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/exporting.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/themes/grid.js"></script>
${CssrChart_VTD}
${CssrChart_VTT}
${ChartCSSR_H_VTD}
${ChartCSSR_H_VTT}
${speechCssrChart}
${cssrHourlyChart}
${columnlinechar}
${CssrChart_Cell_2G}
${CssrChart_Cell_3G}

<script type="text/javascript">
var autoRefresh = $('#autoRefresh').val();	
	setTimeout(function(){
		$('#filterController').submit();
	}, 
	autoRefresh * 1000);
</script>