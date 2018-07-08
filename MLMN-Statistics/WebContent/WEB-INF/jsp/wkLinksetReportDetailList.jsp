<%@ include file="/commons/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<title>linkset report</title>
<content tag="heading">REPORT LINKSET</content>

<ul class="ui-tabs-nav">
	<li class=""><a href="#"><span>Báo cáo giờ</span></a></li>
	<li class=""><a href="${pageContext.request.contextPath}/report/core/linkset/dy/detail.htm?fromNode=${fromNode}&toNode=${toNode}"><span>Báo cáo ngày</span></a></li>
	<li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/report/core/linkset/wk/detail.htm?fromNode=${fromNode}&toNode=${toNode}"><span>Báo cáo tuần</span></a></li>
	<li class=""><a href="${pageContext.request.contextPath}/report/core/linkset/mn/detail.htm?fromNode=${fromNode}&toNode=${toNode}"><span>Báo cáo tháng</span></a></li>
</ul>

<div class="ui-tabs-panel">

	<table width="100%" class="form">
		<tr>
		    <td align="left">
		      <form method="get" action="detail.htm">
		        <input type="hidden" name="fromNode" value="${fromNode}">
		        <input type="hidden" name="toNode" value="${toNode}">
	                &nbsp;&nbsp;Tuần <input value="${week}" name="week" id="week" size="2" maxlength="2">
	            	<img alt="calendar" title="Click to choose the week number" id="chooseWeek" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
	                &nbsp;&nbsp;Năm <input value="${year}" name="year" id="year" size="4" maxlength="4">
	            &nbsp;&nbsp;<input type="submit" class="button" name="submit" value="View Report"/>
	      	  </form>
	        </td>
	    </tr>		
	</table>
	<br/>
	<table>
		<tr>
			<td width="20%" valign="top">
			    <table class="simple2">
			        <tr>
			            <th><b>From</b></th>
			            <th><b><i>${fromNode}</i></b></th>
			        </tr>
			        <c:forEach var="fromNode" items="${fromNodeMap}">
			        <tr>
			            <td>${fromNode.key}</td>
			            <td>${fromNode.value}</td>
			        </tr>			        
			        </c:forEach>
			    </table>
	    	</td>
	    	<td width="2%" valign="top"/>
			<td width="20%" valign="top">
			    <table class="simple2">
			        <tr>
			            <td>Số Link </td>
			            <td>${wkLinksetReports.linksets}</td>
			        </tr>
			        <tr>
			            <td>Linkset Traffic </td>
			            <td>${wkLinksetReports.linksetTraf}</td>
			        </tr>
			        <tr>
			            <td>Utilisation (%)</td>
			            <td>${wkLinksetReports.linksetUtil}%</td>
			        </tr>
			    </table>
	    	</td>
	    	<td width="2%" valign="top"/>
			<td width="20%" valign="top">
			    <table class="simple2">
			        <tr>
			            <th><b>To</b></th>
			            <th><b><i>${toNode}</i></b></th>
			        </tr>
			        <c:forEach var="toNode" items="${toNodeMap}">
			        <tr>
			            <td>${toNode.key}</td>
			            <td>${toNode.value}</td>
			        </tr>			        
			        </c:forEach>
			    </table>
	    	</td>
	    	<td width="5%" valign="top"/>
			<td valign="top">
			  <div id="notaccordion" style="width: 500px; margin: 0 auto">
				<c:forEach var="wkLinkset" items="${wkLinksets}">
				  <h3><a href="#">Linkset ${wkLinkset.linksetid}</a></h3>
				  <div>
					<table class="simple2">
						<tr>
							<td><b>Linkset Name</b></td>
							<td><b>${wkLinkset.linksetid}</b></td>	
						</tr>
						<tr>
							<td>From Node</td>
							<td>${wkLinkset.fromNode}</td>	
						</tr>
						<tr>
							<td>To Node</td>
							<td>${wkLinkset.toNode}</td>	
						</tr>
						<tr>
							<td>No. Linkset</td>
							<td>${wkLinkset.links}</td>	
						</tr>
						<tr>
							<td>Linkset traffic (Erlang)</td>
							<td>${wkLinkset.linksetTraf}</td>	
						</tr>
						<tr>
							<td>Utilisation (%)</td>
							<td>${wkLinkset.linksetUtil}%</td>	
						</tr>
					</table>
			      </div>
		        </c:forEach>
		      </div>
			</td>
		</tr>
	</table>
</div>

<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/calendar.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/calendar_en.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/calendar_setup.js"></script>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/calendar-blue.css" />

<script type="text/javascript">
	Calendar.setup({
	    inputField		:	"week",	// id of the input field
	    ifFormat		:	"%W",   	// format of the input field
	    button			:   "chooseWeek",  	// trigger for the calendar (button ID)
	    singleClick		:   false					// double-click mode
	});
	
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
	});
</script>
