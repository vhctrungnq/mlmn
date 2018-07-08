<%@ include file="/includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    

<ul class="ui-tabs-nav">
<c:choose>
	<c:when test="${function == 'cssr-2g'}">
		<li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/report/bieu-do-giam-sat/cssr-2g.htm"><span><fmt:message key="Network 2g"/></span></a></li>
		<li class=""><a href="${pageContext.request.contextPath}/report/bieu-do-giam-sat/cssr-3g.htm"><span><fmt:message key="Network 3g"/></span></a></li>
		<li class=""><a href="${pageContext.request.contextPath}/report/bieu-do-giam-sat/cssr-cell.htm"><span><fmt:message key="Cell (CSSR dưới 50%)"/></span></a></li>
		<li class=""><a href="${pageContext.request.contextPath}/report/bieu-do-giam-sat/kpi-alarm.htm"><span><fmt:message key="KPI Alarm"/></span></a></li>
		
	</c:when>
 	<c:when test="${function == 'cssr-3g'}">
		<li class=""><a href="${pageContext.request.contextPath}/report/bieu-do-giam-sat/cssr-2g.htm"><span><fmt:message key="Network 2g"/></span></a></li>
		<li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/report/bieu-do-giam-sat/cssr-3g.htm"><span><fmt:message key="Network 3g"/></span></a></li>
		<li class=""><a href="${pageContext.request.contextPath}/report/bieu-do-giam-sat/cssr-cell.htm"><span><fmt:message key="Cell (CSSR dưới 50%)"/></span></a></li>
		<li class=""><a href="${pageContext.request.contextPath}/report/bieu-do-giam-sat/kpi-alarm.htm"><span><fmt:message key="KPI Alarm"/></span></a></li>
		
	</c:when>
	<c:when test="${function == 'cssr-cell'}">
		<li class=""><a href="${pageContext.request.contextPath}/report/bieu-do-giam-sat/cssr-2g.htm"><span><fmt:message key="Network 2g"/></span></a></li>
		<li class=""><a href="${pageContext.request.contextPath}/report/bieu-do-giam-sat/cssr-3g.htm"><span><fmt:message key="Network 3g"/></span></a></li>
		<li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/report/bieu-do-giam-sat/cssr-cell.htm"><span><fmt:message key="Cell (CSSR dưới 50%)"/></span></a></li>
		<li class=""><a href="${pageContext.request.contextPath}/report/bieu-do-giam-sat/kpi-alarm.htm"><span><fmt:message key="KPI Alarm"/></span></a></li>
	</c:when>
	<c:when test="${function == 'kpi-alarm'}">
		<li class=""><a href="${pageContext.request.contextPath}/report/bieu-do-giam-sat/cssr-2g.htm"><span><fmt:message key="Network 2g"/></span></a></li>
		<li class=""><a href="${pageContext.request.contextPath}/report/bieu-do-giam-sat/cssr-3g.htm"><span><fmt:message key="Network 3g"/></span></a></li>
		<li class=""><a href="${pageContext.request.contextPath}/report/bieu-do-giam-sat/cssr-cell.htm"><span><fmt:message key="Cell (CSSR dưới 50%)"/></span></a></li>
		<li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/report/bieu-do-giam-sat/kpi-alarm.htm"><span><fmt:message key="KPI Alarm"/></span></a></li>
	</c:when>
 	<c:otherwise></c:otherwise>
</c:choose>
</ul>
<title>BIỂU ĐỒ GIÁM SÁT CSSR</title>
<content tag="heading">BIỂU ĐỒ GIÁM SÁT CSSR </content> 
<div class="ui-tabs-panel">
<form:form id="filterController" method="post" action="${function}.htm">

	<c:choose>
				<c:when test="${function == 'kpi-alarm'}">
				<table border="0" width="80%" cellspacing="0" cellpadding="0" class="form">
					 <tr>
					 <td class="mwid50">NE Parent</td>
						<td class="wid15">
							&nbsp;&nbsp;<input type="text" id="neParent" value="${neParent}" name = "neParent" class="wid90"/>
						</td> 
						<td class="mwid50">NE</td>
						<td class="wid15">
							&nbsp;&nbsp;<input type="text" id="ne" value="${neName}" name = "ne" class="wid90"/>
						</td> 
						
						<td class="mwid70">NE Type</td>
						<td class="wid20">
							&nbsp;&nbsp;<select id="neType" name = "neType" class="wid90" onchange="xl()"> 
								<option  value="">Tất cả</option >
								<c:forEach var="item" items="${netypeList}">
									<c:choose>
										<c:when test="${item.name == neType}">
											<option  value="${item.name}" selected="selected">${item.value}</option >
										</c:when>
										<c:otherwise>
											<option  value="${item.name}">${item.value}</option >
										</c:otherwise>
									</c:choose>
								</c:forEach>
							</select>
						</td>
						<td class="mwid70">Alarm Level</td>
						<td class="wid20">
							&nbsp;&nbsp;<select id="alarmLevel" name = "alarmLevel" class="wid90" onchange="xl()"> 
								<option  value="">Tất cả</option >
								<c:forEach var="item" items="${levelList}">
									<c:choose>
										<c:when test="${item.name == alarmLevel}">
											<option  value="${item.name}" selected="selected">${item.value}</option >
										</c:when>
										<c:otherwise>
											<option  value="${item.name}">${item.value}</option >
										</c:otherwise>
									</c:choose>
								</c:forEach>
							</select> 
						</td>
						<td>
						 	&nbsp;&nbsp;<input class="button" type="submit" name="filter" value="<fmt:message key="Tìm kiếm"/>" />
						</td>
					 </tr>
				</table>
				</c:when>
				<c:otherwise>
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
						
						</td>
						<td align="right">
									<fmt:message key="Refresh"/>
									<input type="text" id="autoRefresh" name="autoRefresh" value="${autoRefresh}" style="width: 6%" maxlength="4"/>&nbsp;giây
						&nbsp;&nbsp;<input class="button" type="submit" name="filter" value="<fmt:message key="Cập nhật"/>" />
						</td>			
					</tr>
					</table>
				</c:otherwise>
	</c:choose>
		

<br>
<c:choose>
	   <c:when test="${function == 'kpi-alarm'}">
		 <display:table name="${kpiBadList}" id="kpiBad" requestURI="" pagesize="100" class="simple2" sort="external" defaultsort="1"  export="true">				   
 			<display:column property="neParent" titleKey="NE Parent"/>
 			<display:column property="ne" titleKey="NE"/>   
			<display:column property="neType" titleKey="NE Type"/>
		    <display:column property="alarmLevel" titleKey="Alarm Level" class="ALARM_LEVEL"/>
		    <display:column property="day" title="Day" format="{0,date,dd/MM/yyyy}"/>  
		    <display:column property="hour" title="Hour"/>  
		    <display:column property="kpi" title="KPI"/> 
		    <display:column property="val" title="Values" class="rightColumnMana" />  
		</display:table>
		</c:when>
		<c:otherwise>
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
							<tr>
								<td style = "width:50%" id="ChartCSSR_H_VTD2"></td>
								<td style = "width:50%" id="ChartCSSR_H_VTT2"></td>
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
		</c:otherwise>
</c:choose>
</form:form>
</div>

<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/highcharts.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/exporting.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/themes/grid.js"></script>
<c:choose>
		<c:when test="${function == 'cssr-2g'}">
			${CssrChart_VTD}
			${CssrChart_VTT}
			${ChartCSSR_H_VTD}
			${ChartCSSR_H_VTT}
			${ChartCSSR_H_VTD2}
			${ChartCSSR_H_VTT2}
		</c:when>
		<c:when test="${function == 'cssr-3g'}">
			${speechCssrChart}
			${cssrHourlyChart}
			${columnlinechar}
		</c:when>
		<c:when test="${function == 'cssr-cell'}">
			${CssrChart_Cell_2G}
			${CssrChart_Cell_3G}
		</c:when>
		<c:otherwise>
			<script type="text/javascript">
	function xl(){
		var sub = document.getElementById("submit");
		sub.focus();
	}
	$(function() {${highlight}
	});
	</script>
		</c:otherwise>
</c:choose>

<c:if test="${function != 'kpi-alarm'}">
<script type="text/javascript">
var autoRefresh = $('#autoRefresh').val();	
	setTimeout(function(){
		$('#filterController').submit();
	}, 
	autoRefresh * 1000);
</script>
</c:if>
