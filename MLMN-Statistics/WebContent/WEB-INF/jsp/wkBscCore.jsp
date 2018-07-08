<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<title>bsc week report</title>
<content tag="heading">BSC WEEKLY REPORT</content>
<ul class="ui-tabs-nav">
  <li class=""><a href="${pageContext.request.contextPath}/report/core/bsc/dy/main.htm?bscid=${bscid}"><span>Báo cáo ngày</span></a></li>
  <li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/report/core/bsc/wk/main.htm?bscid=${bscid}"><span>Báo cáo tuần</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/core/bsc/mn/main.htm?bscid=${bscid}"><span>Báo cáo tháng</span></a></li>
</ul>
	<div class="ui-tabs-panel">

	  <form method="get" action="main.htm">
		<table width="100%" class="form">
			<tr>
				<td align="left">
			        BSC 
					<select name="bscid" id="bscid" style="width: 163px">
			        <option value="">--Select BSC--</option>
				        <c:forEach var="bsc" items="${bscList}">
			              <c:choose>
			                <c:when test="${bsc.bscid == bscid}">
			                    <option value="${bsc.bscid}" selected="selected">${bsc.bscid}</option>
			                </c:when>
			                <c:otherwise>
			                    <option value="${bsc.bscid}">${bsc.bscid}</option>
			                </c:otherwise>
			              </c:choose>
					    </c:forEach>
					</select>
	                Tuần <input value="${week}" name="week" id="week" size="2" maxlength="2"/>
					<img alt="calendar" title="Click to choose the week number" id="chooseWeek" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
	                Năm <input value="${year}" name="year" id="year" size="4" maxlength="4"/>
	                &nbsp;&nbsp;<input type="submit" class="button" name="submit" value="View Report"/>
	            </td>
	        </tr>		
		</table>
	  </form>
	<br/>
	
	<table>
		<tr>
			<td width="30%" valign="top">
			    <table class="simple2">
			        <tr>
			            <th><b>Bsc</b></th>
			            <th><b><i>${bscid}</i></b></th>
			        </tr>
			        <tr>
			            <td>%</td>
			            <td>N/A</td>
			        </tr>
			        <tr>
			            <td>CSSR (%)</td>
			            <td><a href="detail.htm?bscid=${bscid}&week=${week}&year=${year}#cssr">${wkBscCore.cssr}%</a></td>
			        </tr>
			        <tr>
			            <td>PSR (%)</td>
			            <td><a href="detail.htm?bscid=${bscid}&week=${week}&year=${year}#psr">${wkBscCore.psr}%</a></td>
			        </tr>
			        <tr>
			            <td>DCRS (%)</td>
			            <td><a href="detail.htm?bscid=${bscid}&week=${week}&year=${year}#dcrs">${wkBscCore.dcrs}%</a></td>
			        </tr>
			        <tr>
			            <td>DCRT (%)</td>
			            <td><a href="detail.htm?bscid=${bscid}&week=${week}&year=${year}#dcrt">${wkBscCore.dcrt}%</a></td>
			        </tr>
			        <tr>
			            <td>TRAUCR (%)</td>
			            <td><a href="detail.htm?bscid=${bscid}&week=${week}&year=${year}#traucr">${wkBscCore.traucr}%</a></td>
			        </tr>
			        <tr>
			            <td>HSR (%)</td>
			            <td><a href="detail.htm?bscid=${bscid}&week=${week}&year=${year}#hsr">${wkBscCore.hsr}%</a></td>
			        </tr>
			    </table>
	    	</td>
	    	<td width="5%" valign="top"/>
			<td valign="top" style="width: 1000px;">
				<ul class="ui-tabs-nav" id="tabs">
					<li class="ui-tabs-selected"><a href="#tab1"><span>Đồ thị</span></a></li>
					<li class=""><a href="#tab2"><span>Bảng biểu</span></a></li>
				</ul>
				<div class="ui-tabs-panel">					
				    <div id="tab1" class="tab_content">
						<div id="container" style="width: 800px; margin: 1em auto"></div>
				    </div>
				    <div id="tab2" class="tab_content">
				        <display:table name="${dyBscCoreList}" id="dyBscCore" requestURI="" pagesize="100" class="simple2" export="true">
							<display:column property="day" format="{0,date,dd/MM/yyyy}" titleKey="DAY"/>
							<display:column titleKey="CSSR">
								<a href="${pageContext.request.contextPath}/report/core/bsc/dy/detail.htm?bscid=${bscid}&day=<fmt:formatDate pattern="dd/MM/yyyy" value="${dyBscCore.day}"/>#cssr">${dyBscCore.cssr}</a>
							</display:column>
							<display:column titleKey="PSR">
								<a href="${pageContext.request.contextPath}/report/core/bsc/dy/detail.htm?bscid=${bscid}&day=<fmt:formatDate pattern="dd/MM/yyyy" value="${dyBscCore.day}"/>#psr">${dyBscCore.psr}</a>
							</display:column>
							<display:column titleKey="DCRS">
								<a href="${pageContext.request.contextPath}/report/core/bsc/dy/detail.htm?bscid=${bscid}&day=<fmt:formatDate pattern="dd/MM/yyyy" value="${dyBscCore.day}"/>#dcrs">${dyBscCore.dcrs}</a>
							</display:column>
							<display:column titleKey="DCRT">
								<a href="${pageContext.request.contextPath}/report/core/bsc/dy/detail.htm?bscid=${bscid}&day=<fmt:formatDate pattern="dd/MM/yyyy" value="${dyBscCore.day}"/>#dcrs">${dyBscCore.dcrt}</a>
							</display:column>
							<display:column titleKey="TRAUCR">
								<a href="${pageContext.request.contextPath}/report/core/bsc/dy/detail.htm?bscid=${bscid}&day=<fmt:formatDate pattern="dd/MM/yyyy" value="${dyBscCore.day}"/>#traucr">${dyBscCore.traucr}</a>
							</display:column>
							<display:column titleKey="HSR">
								<a href="${pageContext.request.contextPath}/report/core/bsc/dy/detail.htm?bscid=${bscid}&day=<fmt:formatDate pattern="dd/MM/yyyy" value="${dyBscCore.day}"/>#hsr">${dyBscCore.hsr}</a>
							</display:column>
						</display:table>
				    </div>
				</div>
			</td>
		</tr>
	</table>
</div>

${chart}

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
		var cache = {},
		lastXhr;
		$( "#bscid" ).autocomplete({
			minLength: 2,
			source: function( request, response ) {
				var term = request.term;
				if ( term in cache ) {
					response( cache[ term ] );
					return;
				}

				lastXhr = $.getJSON( "${pageContext.request.contextPath}/ajax/getBsc.htm", request, function( data, status, xhr ) {
					cache[ term ] = data;
					if ( xhr === lastXhr ) {
						response( data );
					}
				});
			}
		});
		
		//Tab Default Action
		$(".tab_content").hide(); //Hide all content
		$("ul#tabs li:first").addClass("ui-tabs-selected").show(); //Activate first tab
		$(".tab_content:first").show(); //Show first tab content
		
		//Tab On Click Event
		$("ul#tabs li").click(function() {
			$("ul#tabs li").removeClass("ui-tabs-selected"); //Remove any "active" class
			$(this).addClass("ui-tabs-selected"); //Add "active" class to selected tab
			$(".tab_content").hide(); //Hide all tab content
			var activeTab = $(this).find("a").attr("href"); //Find the rel attribute value to identify the active tab + content
			$(activeTab).fadeIn(); //Fade in the active content
			return false;
		});
	});
</script>
