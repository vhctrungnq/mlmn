<%@ include file="/commons/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<title>route list</title>
<content tag="heading">Báo cáo Route</content>
<!-- 
<ul class="ui-tabs-nav">
	<li class=""><a href="${pageContext.request.contextPath}/report/core/route/hr/list.htm"><span>Báo cáo giờ</span></a></li>
	<li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/report/core/route/dy/list.htm"><span>Báo cáo ngày</span></a></li>
	<li class=""><a href="${pageContext.request.contextPath}/report/core/route/wk/list.htm"><span>Báo cáo tuần</span></a></li>
	<li class=""><a href="${pageContext.request.contextPath}/report/core/route/mn/list.htm"><span>Báo cáo tháng</span></a></li>
</ul>
 -->
 
<div class="ui-tabs-panel">

<form method="get" action="list.htm">
<table width="100%" class="form">
	<tr>
		<td align="left">
		Routeid <input name="routeid" value="${routeid}" size="10" maxlength="50" />
		&nbsp;&nbsp; Điểm đầu <input name="fromNode" value="${fromNode}" size="10" maxlength="50" />
		&nbsp;&nbsp; Điểm cuối <input name=toNode value="${toNode}" size="10" maxlength="50" />
		&nbsp;&nbsp; Ngày <input value="${day}" name="day" id="day" size="10" maxlength="10" />
		&nbsp;&nbsp;<input type="submit" class="button" name="submit" value="View Report" />
		</td>
	</tr>
</table>
</form>
<table border="0" width="100%" cellspacing="0" cellpadding="0">
	<tr height="5">
		<td colspan="2" />
	</tr>
	<tr>
		<td colspan="2">
		  <div  style="overflow: auto;">
			<display:table name="${dyRouteCoreReportList}" id="routeCore" requestURI="" pagesize="100" class="simple2" export="true">
				<display:column property="day" format="{0,date,dd/MM/yyyy}" titleKey="Ngày" />
				<display:column property="routeid" titleKey="ROUTEID" />
				<display:column property="fromNode" titleKey="FROM NODE" />
				<display:column property="toNode" titleKey="TO NODE" />
				<display:column property="inRouteTraf" titleKey="IN_ROUTE_TRAF" />
				<display:column property="outRouteTraf" titleKey="OUT_ROUTE_TRAF" />
				<display:column property="routeTraf" titleKey="ROUTE_TRAF" />
				<display:column property="dev" titleKey="DEV" />
				<display:column property="blockDev" titleKey="BLOCK_DEV" />
				<display:column property="util" titleKey="UTIL" />
				<display:column property="conges" titleKey="CONGES" />
				<display:column property="stm1" titleKey="STM_1" />
				<display:column property="e1" titleKey="E1" />
				<display:column titleKey="Báo cáo" media="html">
					<a href="detail.htm?fromNode=${routeCore.fromNode}&toNode=${routeCore.toNode}&day=${day}"> Báo cáo</a>&nbsp;
				</display:column>
			</display:table>
		</div></td>
	</tr>
</table>
</div>

<script language="JavaScript" type="text/javascript">
$(document).ready(function() {	
	$( "#day" ).datepicker({
		dateFormat: "dd/mm/yy",
		showOn: "button",
		buttonImage: "${pageContext.request.contextPath}/images/calendar.png",
		buttonImageOnly: true
	});
});
</script>
