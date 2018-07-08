<%@ include file="/commons/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<title>linkset list</title>
<content tag="heading">Báo cáo Linkset</content>
<!-- 
<ul class="ui-tabs-nav">
	<li class=""><a href="${pageContext.request.contextPath}/report/core/linkset/hr/list.htm"><span>Báo cáo giờ</span></a></li>
	<li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/report/core/linkset/dy/list.htm"><span>Báo cáo ngày</span></a></li>
	<li class=""><a href="${pageContext.request.contextPath}/report/core/linkset/wk/list.htm"><span>Báo cáo tuần</span></a></li>
	<li class=""><a href="${pageContext.request.contextPath}/report/core/linkset/mn/list.htm"><span>Báo cáo tháng</span></a></li>
</ul>
 -->
<div class="ui-tabs-panel">

<form method="get" action="list.htm">
<table width="100%" class="form">
	<tr>
		<td align="left">
		Linkset <input name="linksetid" value="${linksetid}" id="linksetid" size="10" maxlength="50" />
		&nbsp;&nbsp; Node nguồn <input name="fromNode" value="${fromNode}" id="fromNode" size="10" maxlength="50" />
		&nbsp;&nbsp; Node đích <input name="toNode" value="${toNode}" id="toNode" size="10" maxlength="50" />
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
		  <div style="overflow: auto;">
			<display:table name="${dyLinksetReportList}" id="linkset" requestURI="" pagesize="100" class="simple2" export="true">
				<display:column property="day" format="{0,date,dd/MM/yyyy}" titleKey="Ngày" />
				<display:column property="linksetid" titleKey="LINKSET" />
				<display:column property="fromNode" titleKey="FROM_NODE" />
				<display:column property="toNode" titleKey="TO_NODE" />
				<display:column property="links" titleKey="LINKS" />
				<display:column property="linksetTraf" titleKey="LINKSET_TRAF" />
				<display:column property="linksetUtil" titleKey="LINKSET_UTIL" />
				<display:column titleKey="Báo cáo" media="html">
					<a href="${pageContext.request.contextPath}/report/core/linkset/dy/detail.htm?fromNode=${linkset.fromNode}&toNode=${linkset.toNode}&day=<fmt:formatDate pattern="dd/MM/yyyy" value="${linkset.day}"/>"> Báo cáo</a>&nbsp;
				</display:column>
			</display:table>
		</div></td>
	</tr>
</table>
</div>

<script type="text/javascript">
$(function() {
	$( "#day" ).datepicker({
		dateFormat: "dd/mm/yy",
		showOn: "button",
		buttonImage: "${pageContext.request.contextPath}/images/calendar.png",
		buttonImageOnly: true
	});

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
});
</script>
