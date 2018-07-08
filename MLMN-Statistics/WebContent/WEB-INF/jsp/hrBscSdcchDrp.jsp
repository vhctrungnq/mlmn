<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<title>bsc sdcch drop hourly report</title>
<content tag="heading">SDCCH DROP BSC ${bscid} REPORT</content>
<ul class="ui-tabs-nav">
  <li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/report/radio/bsc/sdrop/hr.htm?bscid=${bscid}&endDate=${endDate}"><span>Báo cáo giờ</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/radio/bsc/sdrop/dy.htm?bscid=${bscid}&endDate=${endDate}"><span>Báo cáo ngày</span></a></li>
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
			        &nbsp;BSC 
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
<display:table name="${hrBscDcrsQosList}" id="hrBscDcrsQos" requestURI="" pagesize="100" class="simple2" export="true">
				<display:column property="region" titleKey="TT" sortable="true"/>
	    <display:column property="day" format="{0,date,dd/MM/yyyy}" titleKey="DAY" sortable="true"/>
	    <display:column titleKey="HOUR" sortable="true"  sortProperty="hour">
	    	${hrBscDcrsQos.hour}:00
	    </display:column>
	    <display:column property="bscid" titleKey="BSC" sortable="true" class="margin" headerClass="margin"/>
		<display:column property ="f1" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="Total No. of Dropperd SDCCH Connections"/>
			    <display:column property ="f2" titleKey="SDCCH DROP (%)" sortable="true"/>
			    <display:column property="f3" titleKey="SDCCH Erlang Minutes per Drop" sortable="true"/>
			    <display:column property="f4" titleKey="Drop Reason, Low SS (%)" sortable="true"/>
			    <display:column property ="f5" titleKey="Drop Reason, Bad Quality (%)" sortable="true"/>
			    <display:column property ="f6" titleKey="Drop Reason, Excessive TA(%)" sortable="true"/>
			    <display:column property="f7" titleKey="Drop Reason, Other (%)" sortable="true"/> 
			    <display:column property="f8" titleKey="Handover Lost of all SDCCH Drop (%)" sortable="true"/>
			    <display:column property ="f9" titleKey="Data Availability (%)" media="html" class="hide" headerClass="hide" sortable="true"/>
</display:table>
</div>

		<br>
		
	<div id="chart" style="width: 1000px; margin: 1em auto"></div>
</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/text_date.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/highcharts.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/exporting.js"></script>
${chart}

<script type="text/javascript">
	function xl(){
		var sub= document.getElementById("submit");
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
	});
</script>
