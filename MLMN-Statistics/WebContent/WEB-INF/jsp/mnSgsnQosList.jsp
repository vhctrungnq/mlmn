<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<title>Báo cáo SGSN QoS</title>
<content tag="heading">SGSN QOS MONTHLY REPORT</content>
<ul class="ui-tabs-nav">
	<li class=""><a href="${pageContext.request.contextPath}/report/core/sgsn/dy/list.htm"><span>Báo cáo ngày</span></a></li>
	<li class=""><a href="${pageContext.request.contextPath}/report/core/sgsn/wk/list.htm"><span>Báo cáo tuần</span></a></li>
	<li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/report/core/sgsn/mn/list.htm"><span>Báo cáo tháng</span></a></li>
</ul>
<div class="ui-tabs-panel">
	
		<table width="100%" class="form">
			<tr>
			    <td align="left">
			  <form method="get" action="list.htm" name = "frmSample" onSubmit = "return ValidateFormYear()">
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
			        &nbsp;SGSN 
			         <select name="sgsnid" id="sgsnid" onchange="xl()">
						<option value="">--Select SGSN--</option>
				        <c:forEach var="sgsn" items="${sgsnList}">
			              <c:choose>
			                <c:when test="${sgsn.sgsnName == sgsnid}">
			                    <option value="${sgsn.sgsnName}" selected="selected">${sgsn.sgsnName}</option>
			                </c:when>
			                <c:otherwise>
			                    <option value="${sgsn.sgsnName}">${sgsn.sgsnName}</option>
			                </c:otherwise>
			              </c:choose>
					    </c:forEach>
					 </select>
					&nbsp;Từ tháng  <select name="startMonth" id="startMonth" onchange="xl()">
	            				<c:forEach var="month" items="${monthList}">
						              <c:choose>
						                <c:when test="${month == startMonth}">
						                    <option value="${month}" selected="selected">${month}</option>
						                </c:when>
						                <c:otherwise>
						                    <option value="${month}">${month}</option>
						                </c:otherwise>
						              </c:choose>
						    </c:forEach>
			               	 </select>&nbsp;
	            	&nbsp;Năm <input value="${startYear}" name="startYear" id="startYear" size="4" maxlength="4" onchange ="javascript:checkNumber(document.frmSample.startYear);">
	            	&nbsp;Tới tháng <select name="endMonth" id="endMonth" onchange="xl()">
	            				<c:forEach var="month" items="${monthList}">
						              <c:choose>
						                <c:when test="${month == endMonth}">
						                    <option value="${month}" selected="selected">${month}</option>
						                </c:when>
						                <c:otherwise>
						                    <option value="${month}">${month}</option>
						                </c:otherwise>
						              </c:choose>
						    </c:forEach>
			               	 </select>&nbsp;
	            	&nbsp;Năm <input value="${endYear}" name="endYear" id="endYear" size="4" maxlength="4" onchange ="javascript:checkNumber(document.frmSample.endYear);">
	            	&nbsp;<input type="submit" class="button" name="submit" id="submit" value="View Report"/>
	          </form>
	            </td>
	        </tr>		
		</table>
	<br/>
	
	<div  style="overflow: auto;">
	<display:table name="${vRpMnSgsnQos}" id="vRpMnSgsnQos" requestURI="" pagesize="100" class="simple2" export="true">
		<display:column property ="month" titleKey="MONTH" sortable="true" headerClass="master-header-1"/>
		<display:column property ="year" titleKey="YEAR" sortable="true" headerClass="master-header-1"/> 
	    <display:column property="region" titleKey="Center" sortable="true" headerClass="master-header-1"/>
	    <display:column property="sgsnName" titleKey="SGSN Name" headerClass="hide" class="hide"/>
	    <display:column titleKey="SGSN Name" media="html" sortable="true" headerClass="master-header-1" sortProperty="sgsnName">
	    	<a href="${pageContext.request.contextPath}/report/core/sgsn/mn/detail.htm?sgsnid=${vRpMnSgsnQos.sgsnName}&endMonth=${vRpMnSgsnQos.month}&endYear=${vRpMnSgsnQos.year}">${vRpMnSgsnQos.sgsnName}</a>
	    </display:column>
	    <display:column property="attachSubs2g" titleKey="ATTACH_SUBS_2G" sortable="true" headerClass="master-header-2 attachSubs2g"/>
		<display:column property="attachSubs3g" titleKey="ATTACH_SUBS_3G" sortable="true" headerClass="master-header-2 attachSubs3g"/>
		<display:column property="attachSubsSgsn" titleKey="ATTACH_SUBS_SGSN" sortable="true" headerClass="master-header-2 attachSubsSgsn"/>
		<display:column property="attachSubsUtilitySgsn" titleKey="ATTACH_SUBS_UTILITY_SGSN" sortable="true" headerClass="master-header-2 attachSubsUtilitySgsn"/>
		<display:column property="attachSuccRate2g" titleKey="ATTACH_SUCC_RATE_2G" sortable="true" headerClass="master-header-3 attachSuccRate2g"/>
		<display:column property="attachSuccRate3g" titleKey="ATTACH_SUCC_RATE_3G" sortable="true" headerClass="master-header-3 attachSuccRate3g"/>
		<display:column property="attachSuccRateSgsn" titleKey="ATTACH_SUCC_RATE_SGSN" sortable="true" headerClass="master-header-3 attachSuccRateSgsn"/>
		<display:column property="activePdp2g" titleKey="ACTIVE_PDP_2G" sortable="true" headerClass="master-header-4 activePdp2g"/>
		<display:column property="activePdp3g" titleKey="ACTIVE_PDP_3G" sortable="true" headerClass="master-header-4 activePdp3g"/>
		<display:column property="activePdpSgsn" titleKey="ACTIVE_PDP_SGSN" sortable="true" headerClass="master-header-4 activePdpSgsn"/>
		<display:column property="activeUtilizationPdpSgsn" titleKey="ACTIVE_UTILIZATION_PDP_SGSN" sortable="true" headerClass="master-header-4 activeUtilizationPdpSgsn"/>
		<display:column property="pdpAttachSucc2g" titleKey="PDP_ATTACH_SUCC_2G" sortable="true" headerClass="master-header-5 pdpAttachSucc2g"/>
		<display:column property="pdpAttachSucc3g" titleKey="PDP_ATTACH_SUCC_3G" sortable="true" headerClass="master-header-5 pdpAttachSucc3g"/>
		<display:column property="pdpAttachSuccSgsn" titleKey="PDP_ATTACH_SUCC_SGSN" sortable="true" headerClass="master-header-5 pdpAttachSuccSgsn"/>
		<display:column property="userTraffic2g" titleKey="USER_TRAFFIC_2G" sortable="true" headerClass="master-header-6 userTraffic2g"/>
		<display:column property="userTraffic3g" titleKey="USER_TRAFFIC_3G" sortable="true" headerClass="master-header-6 userTraffic3g"/>
		<display:column property="userTrafficSgsn" titleKey="USER_TRAFFIC_SGSN" sortable="true" headerClass="master-header-6 userTrafficSgsn"/>
		<display:column property="dlThroughput2g" titleKey="DL_THROUGHPUT_2G" sortable="true" headerClass="master-header-1 dlThroughput2g"/>
		<display:column property="dlThroughput3g" titleKey="DL_THROUGHPUT_3G" sortable="true" headerClass="master-header-1 dlThroughput3g"/>
		<display:column property="dlThroughputSgsn" titleKey="DL_THROUGHPUT_SGSN" sortable="true" headerClass="master-header-1 dlThroughputSgsn"/>
		<display:column property="ulThroughput2g" titleKey="UL_THROUGHPUT_2G" sortable="true" headerClass="master-header-2 ulThroughput2g"/>
		<display:column property="ulThroughput3g" titleKey="UL_THROUGHPUT_3G" sortable="true" headerClass="master-header-2 ulThroughput3g"/>
		<display:column property="ulThroughputSgsn" titleKey="UL_THROUGHPUT_SGSN" sortable="true" headerClass="master-header-2 ulThroughputSgsn"/>
		<display:column property="throughputUtilizationSgsn" titleKey="THROUGHPUT_UTILIZATION_SGSN" sortable="true" headerClass="master-header-4 throughputUtilizationSgsn"/>
		<display:column property="intraSgsnRauSucc2g" titleKey="INTRA_SGSN_RAU_SUCC_2G" sortable="true" headerClass="master-header-3 intraSgsnRauSucc2g"/>
		<display:column property="intraSgsnRauSucc3g" titleKey="INTRA_SGSN_RAU_SUCC_3G" sortable="true" headerClass="master-header-3 intraSgsnRauSucc3g"/>
		<display:column property="intraSgsnRauSuccSgsn" titleKey="INTRA_SGSN_RAU_SUCC_SGSN" sortable="true" headerClass="master-header-3 intraSgsnRauSuccSgsn"/>
		<display:column property="interSgsnRauSucc2g" titleKey="INTER_SGSN_RAU_SUCC_2G" sortable="true" headerClass="master-header-4 interSgsnRauSucc2g"/>
		<display:column property="interSgsnRauSucc3g" titleKey="INTER_SGSN_RAU_SUCC_3G" sortable="true" headerClass="master-header-4 interSgsnRauSucc3g"/>
		<display:column property="interSgsnRauSuccSgsn" titleKey="INTER_SGSN_RAU_SUCC_SGSN" sortable="true" headerClass="master-header-4 interSgsnRauSuccSgsn"/>
		<display:column property="rate2g3gIntraHoSgsn" titleKey="RATE_2G_3G_INTRA_HO_SGSN" sortable="true" headerClass="master-header-5 rate2g3gIntraHoSgsn"/>
		<display:column property="rate3g2gIntraHoSgsn" titleKey="RATE_3G_2G_INTRA_HO_SGSN" sortable="true" headerClass="master-header-5 rate3g2gIntraHoSgsn"/>
		<display:column property="rate2g3gInterHoSgsn" titleKey="RATE_2G_3G_INTER_HO_SGSN" sortable="true" headerClass="master-header-5 rate2g3gInterHoSgsn"/>
		<display:column property="rate3g2gInterHoSgsn" titleKey="RATE_3G_2G_INTER_HO_SGSN" sortable="true" headerClass="master-header-5 rate3g2gInterHoSgsn"/>
		<display:column property="authenSucc2g" titleKey="AUTHEN_SUCC_2G" sortable="true" headerClass="master-header-6 authenSucc2g"/>
		<display:column property="authenSucc3g" titleKey="AUTHEN_SUCC_3G" sortable="true" headerClass="master-header-6 authenSucc3g"/>
		<display:column property="authenSuccSgsn" titleKey="AUTHEN_SUCC_SGSN" sortable="true" headerClass="master-header-6 authenSuccSgsn"/>
		<display:column property="pagingSucc3g" titleKey="PAGING_SUCC_3G" sortable="true" headerClass="master-header-7 pagingSucc3g"/>
		<display:column property="pagingSucc2g" titleKey="PAGING_SUCC_2G" sortable="true" headerClass="master-header-7 pagingSucc2g"/>
		<display:column property="pagingSuccSgsn" titleKey="PAGING_SUCC_SGSN" sortable="true" headerClass="master-header-7 pagingSuccSgsn"/>
		<display:column property="peakCpu" titleKey="PEAK_CPU" sortable="true" headerClass="master-header-8 peakCpu"/>
		<display:column property="averageCpu" titleKey="AVERAGE_CPU" sortable="true" headerClass="master-header-8 averageCpu"/>
	</display:table>
	</div>
</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/jquery.simpletip-1.3.1.pack.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/sgsntip.js"></script>

<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/text_date.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/calendar.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/calendar_en.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/calendar_setup.js"></script>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/calendar-blue.css" />

<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/highcharts.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/exporting.js"></script>

<script type="text/javascript">
function CalcKeyCode(aChar) {
	  var character = aChar.substring(0,1);
	  var code = aChar.charCodeAt(0);
	  return code;
	}
function checkNumber(val) {
	  var strPass = val.value;
	  var strLength = strPass.length;
	  for(var i =0 ;i<strLength+1;i++){
		  var lchar = val.value.charAt((strLength) - i);
		  var cCode = CalcKeyCode(lchar);
	
		  if (cCode < 48 || cCode > 57 ) {
		    var myNumber = val.value.substring(0, (strLength) - i);
		    val.value = myNumber;
		  }
	  }
	  var sub = document.getElementById("submit");
		sub.focus();
	  return false;
	}
function xl(){
		var sub = document.getElementById("submit");
		sub.focus();
	  	return false;
	}
</script>	