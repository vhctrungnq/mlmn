<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<title>bsc traffic hourly report</title>
<content tag="heading">TRAFFIC BSC ${bscid} REPORT</content>
<ul class="ui-tabs-nav">
  <li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/report/radio/bsc/tTraffic/hr.htm?bscid=${bscid}&endDate=${endDate}"><span>Báo cáo giờ</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/radio/bsc/tTraffic/dy.htm?bscid=${bscid}&endDate=${endDate}"><span>Báo cáo ngày</span></a></li>
</ul>
	<div class="ui-tabs-panel">

	   <form method="get" action="hr.htm" name = "frmSample" onSubmit = "return ValidateForm()">
		<table width="100%" class="form">
			<tr>
				<td align="left">
					Trung tâm 
			  		<select name="region" id="region" onchange="xl()">
			  			<option value="">Tất cả</option>
			              <c:forEach var="items" items="${regionList}">
				              <c:choose>
				                <c:when test="${items.region == region}">
				                    <option value="${items.region}" selected="selected">${items.region}</option>
				                </c:when>
				                <c:otherwise>
				                    <option value="${items.region}">${items.region}</option>
				                </c:otherwise>
				              </c:choose>
						    </c:forEach>
			        </select>
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
	            	&nbsp;Từ <select name="startHour" id="startHour"onchange="xl()">
	            				<c:forEach var="hour" items="${hourList}">
						              <c:choose>
						                <c:when test="${hour == startHour}">
						                    <option value="${hour}" selected="selected">${hour}</option>
						                </c:when>
						                <c:otherwise>
						                    <option value="${hour}">${hour}</option>
						                </c:otherwise>
						              </c:choose>
						    </c:forEach>
			               	 </select>&nbsp; giờ
	                &nbsp;<input value="${startDate}" name="startDate" id="startDate" size="10" maxlength="10"/>
	                &nbsp;Đến <select name="endHour" id="endHour" onchange="xl()">
	            				<c:forEach var="hour" items="${hourList}">
						              <c:choose>
						                <c:when test="${hour == endHour}">
						                    <option value="${hour}" selected="selected">${hour}</option>
						                </c:when>
						                <c:otherwise>
						                    <option value="${hour}">${hour}</option>
						                </c:otherwise>
						              </c:choose>
						    </c:forEach>
			               	 </select>&nbsp;  giờ
	                &nbsp;<input value="${endDate}" name="endDate" id="endDate" size="10" maxlength="10"/>
	                &nbsp;&nbsp;<input type="submit" class="button" name="submit" id="submit" value="View Report"/>
	            </td>
	        </tr>		
		</table>
	  </form>
	<br/>
	
	<div  style="overflow: auto;">
<display:table name="${hrBscTrafficQosList}" id="hrBscTrafficQos" requestURI="" pagesize="100" class="simple2" export="true">
				<display:column property="region" title="TT" sortable="true"/>
	    <display:column property="day" format="{0,date,dd/MM/yyyy}" title="DAY" sortable="true"/>
	    <display:column title="HOUR" sortable="true" sortProperty="hour">
	    	${hrBscTrafficQos.hour}:00
	    </display:column>
	    <display:column property= "bscid" title="BSC" sortable="true" class="margin" headerClass="margin"/>
			    <display:column property ="f1" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" title="EMPD" sortable="true"/>
			    <display:column property ="f2" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="TCH Traffic" sortable="true"/>
			    <display:column property="f3" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="TCH Traffic FR" sortable="true"/>
			    <display:column property="f4" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="TCH Traffic HR" sortable="true"/>
			    <display:column property ="f5" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="TCH Traffic UL FullRate" sortable="true"/>
			    <display:column property ="f6" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="TCH Traffic UL HalfRate" sortable="true"/>
			    <display:column property="f7" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="TCH Traffic OL FullRate" sortable="true"/> 
			    <display:column property="f8" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="TCH Traffic OL HalfRate" sortable="true"/>
</display:table>
</div>

		
		<br>
		
	<div id="f1Chart" style="width: 1000px; margin: 1em auto"></div>
	<br/>
	<div id="f2Chart" style="width: 1000px; margin: 1em auto"></div>
	<br/>
	<div id="f3Chart" style="width: 1000px; margin: 1em auto"></div>
	<br/>
	<div id="f4Chart" style="width: 1000px; margin: 1em auto"></div>
	<br/>
	<div id="f5Chart" style="width: 1000px; margin: 1em auto"></div>
	<br/>
	<div id="f6Chart" style="width: 1000px; margin: 1em auto"></div>
	<br/>
	<div id="f7Chart" style="width: 1000px; margin: 1em auto"></div>
	<br>
	<div id="f8Chart" style="width: 1000px; margin: 1em auto"></div>
	<br/>
	<div id="f9Chart" style="width: 1000px; margin: 1em auto"></div>
</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/text_date.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/highcharts.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/exporting.js"></script>
${f1Chart}
${f2Chart}
${f3Chart}
${f4Chart}
${f5Chart}
${f6Chart}
${f7Chart}
${f8Chart}
${f9Chart}

<script type="text/javascript">
	function xl(){
		var sub = document.getElementById("submit");
		sub.focus();
	}
	$(function() {
		$( "#startDate" ).datepicker({
			dateFormat: "dd/mm/yy",
			showOn: "button",
			buttonImage: "${pageContext.request.contextPath}/images/calendar.png",
			buttonImageOnly: true
		});
		$( "#endDate" ).datepicker({
			dateFormat: "dd/mm/yy",
			showOn: "button",
			buttonImage: "${pageContext.request.contextPath}/images/calendar.png",
			buttonImageOnly: true
		});
		
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
	});
</script>
