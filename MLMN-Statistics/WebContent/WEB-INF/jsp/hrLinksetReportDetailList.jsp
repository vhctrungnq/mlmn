<%@ include file="/commons/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<title>linkset report</title>
<content tag="heading">Báo cáo Linkset</content>

<ul class="ui-tabs-nav">
	<li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/report/core/linkset/hr/detail.htm?fromNode=${fromNode}&toNode=${toNode}&day=${day}"><span>Báo cáo giờ</span></a></li>
	<li class=""><a href="${pageContext.request.contextPath}/report/core/linkset/dy/detail.htm?fromNode=${fromNode}&toNode=${toNode}"><span>Báo cáo ngày</span></a></li>
	<li class=""><a href="${pageContext.request.contextPath}/report/core/linkset/wk/detail.htm?fromNode=${fromNode}&toNode=${toNode}"><span>Báo cáo tuần</span></a></li>
	<li class=""><a href="${pageContext.request.contextPath}/report/core/linkset/mn/detail.htm?fromNode=${fromNode}&toNode=${toNode}"><span>Báo cáo tháng</span></a></li>
</ul>

<div class="ui-tabs-panel">

	<table width="100%" class="form">
		<tr>
		    <td align="left">
		      <form method="get" action="detail.htm" name = "frmSample" onSubmit = "return ValidateForm()">
		        <input type="hidden" name="fromNode" value="${fromNode}">
		        <input type="hidden" name="toNode" value="${toNode}">
		        Giờ <input value="${hour}" name="hour" id="hour" size="2" maxlength="2">
		        Ngày <input value="${day}" name="day" id="day" size="10" maxlength="10">
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
			            <td>${hrLinksetReports.linksets}</td>
			        </tr>
			        <tr>
			            <td>Linkset Traffic </td>
			            <td>${hrLinksetReports.linksetTraf}</td>
			        </tr>
			        <tr>
			            <td>Utilisation (%)</td>
			            <td>${hrLinksetReports.linksetUtil} %</td>
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
				<c:forEach var="hrLinkset" items="${hrLinksets}">
				  <h3><a href="#">Linkset ${hrLinkset.linksetid}</a></h3>
				  <div>
					<table class="simple2">
						<tr>
							<td><b>Linkset Name</b></td>
							<td><b>${hrLinkset.linksetid}</b></td>	
						</tr>
						<tr>
							<td>From Node</td>
							<td>${hrLinkset.fromNode}</td>	
						</tr>
						<tr>
							<td>To Node</td>
							<td>${hrLinkset.toNode}</td>	
						</tr>
						<tr>
							<td>No. Linkset</td>
							<td>${hrLinkset.links}</td>	
						</tr>
						<tr>
							<td>Linkset traffic (Erlang)</td>
							<td>${hrLinkset.linksetTraf}</td>	
						</tr>
						<tr>
							<td>Utilisation (%)</td>
							<td>${hrLinkset.linksetUtil} %</td>	
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
