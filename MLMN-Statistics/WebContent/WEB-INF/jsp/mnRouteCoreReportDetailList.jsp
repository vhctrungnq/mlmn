<%@ include file="/commons/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<title>route list</title>
<content tag="heading">ROUTE REPORT</content>

<ul class="ui-tabs-nav">
	<li class=""><a href="${pageContext.request.contextPath}/report/core/route/hr/detail.htm?fromNode=${fromNode}&toNode=${toNode}"><span>Báo cáo giờ</span></a></li>
	<li class=""><a href="${pageContext.request.contextPath}/report/core/route/dy/detail.htm?fromNode=${fromNode}&toNode=${toNode}"><span>Báo cáo ngày</span></a></li>
	<li class=""><a href="${pageContext.request.contextPath}/report/core/route/wk/detail.htm?fromNode=${fromNode}&toNode=${toNode}"><span>Báo cáo tuần</span></a></li>
	<li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/report/core/route/mn/detail.htm?fromNode=${fromNode}&toNode=${toNode}"><span>Báo cáo tháng</span></a></li>
</ul>

<div class="ui-tabs-panel">

	<table width="100%" class="form">
		<tr>
		    <td align="left">
		      <form method="get" action="detail.htm">
		        <input type="hidden" name="fromNode" value="${fromNode}">
		        <input type="hidden" name="toNode" value="${toNode}">
		        Tháng <input value="${month}" name="month" id="month" size="2" maxlength="2"/>
	            Năm <input value="${year}" name="year" id="year" size="4" maxlength="4"/>
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
			            <td>Số STM/E1 : </td>
			            <td>${mnRouteCoreReport.stm1} STM-1 ${mnRouteCoreReport.e1} </td>
			        </tr>
			        <tr>
			            <td>Traffic (Erlang) : </td>
			            <td>${mnRouteCoreReport.routeTraf}</td>
			        </tr>
			        <tr>
			            <td>Utilisation (%) : </td>
			            <td>${mnRouteCoreReport.util}%</td>
			        </tr>
			        <tr>
			            <td>Congestion (%): </td>
			            <td>${mnRouteCoreReport.conges}%</td>
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
				<c:forEach var="mnRouteCore" items="${mnRouteCores}">
				  <h3><a href="#">Route ${mnRouteCore.routeid}</a></h3>
				  <div>
					<table class="simple2">
						<tr>
							<td><b>Route Name</b></td>
							<td><b>${mnRouteCore.routeid}</b></td>	
						</tr>
						<tr>
							<td>From Node</td>
							<td>${mnRouteCore.fromNode}</td>	
						</tr>
						<tr>
							<td>To Node</td>
							<td>${mnRouteCore.toNode}</td>	
						</tr>
						<tr>
							<td>Incomming Route Traffic (Erlang)</td>
							<td>${mnRouteCore.inRouteTraf}</td>	
						</tr>
						<tr>
							<td>Outgoing Route Traffic (Erlang)</td>
							<td>${mnRouteCore.outRouteTraf}</td>	
						</tr>
						<tr>
							<td>Route traffic (Erlang)</td>
							<td>${mnRouteCore.routeTraf}</td>	
						</tr>
						<tr>
							<td>No. of Devices</td>
							<td>${mnRouteCore.dev}</td>	
						</tr>
						<tr>
							<td>No. of Blocked Devices</td>
							<td>${mnRouteCore.blockDev}</td>	
						</tr>
						<tr>
							<td>Utilisation (%)</td>
							<td>${mnRouteCore.util}%</td>	
						</tr>
						<tr>
							<td>Congestion (%)</td>
							<td>${mnRouteCore.conges}%</td>	
						</tr>
						<tr>
							<td>Số STM-1</td>
							<td>${mnRouteCore.stm1}</td>	
						</tr>
						<tr>
							<td>Số E1</td>
							<td>${mnRouteCore.e1}%</td>	
						</tr>
						<tr>
							<td>Data Availability(%)</td>
							<td>N/A</td>	
						</tr>
					</table>
			      </div>
		        </c:forEach>
		      </div>
			</td>
		</tr>
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
});
</script>
