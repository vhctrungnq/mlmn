<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<title>Báo cáo Data District ${district}/${province}</title>
<content tag="heading">DATA DISTRICT ${district}/${province} REPORT</content>
<ul class="ui-tabs-nav">
  <li class=""><a href="${pageContext.request.contextPath}/report/radio/district-data/hr/detail.htm?province=${province}&district=${district}&endDate=${endDate}"><span>Báo cáo giờ</span></a></li>
  <li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/report/radio/district-data/dy/detail.htm?province=${province}&district=${district}&endDate=${endDate}"><span>Báo cáo ngày</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/radio/district-data/wk/detail.htm?province=${province}&district=${district}"><span>Báo cáo tuần</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/radio/district-data/mn/detail.htm?province=${province}&district=${district}"><span>Báo cáo tháng</span></a></li>
</ul>
	<div class="ui-tabs-panel">

	  <form method="get" action="detail.htm" class="form" name="frmSample" onSubmit="return ValidateForm()">
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
			        &nbsp;&nbsp;PROVINCE 
			        <select name="province" onchange="xl()">
			        	<option value="">Tất cả</option>
				        <c:forEach var="prv" items="${provinceList}">
			              <c:choose>
			                <c:when test="${prv.province == province}">
			                    <option value="${prv.province}" selected="selected">${prv.province}</option>
			                </c:when>
			                <c:otherwise>
			                    <option value="${prv.province}">${prv.province}</option>
			                </c:otherwise>
			              </c:choose>
					    </c:forEach>
				    </select>
				    &nbsp;&nbsp;DISTRICT <input value="${district}" name="district" id="district" size="10" maxlength="50">
	            	&nbsp;&nbsp;Từ ngày <input value="${startDate}" name="startDate" id="startDate" size="10" maxlength="10">
	            	&nbsp;&nbsp;Tới ngày <input value="${endDate}" name="endDate" id="endDate" size="10" maxlength="10">
	            	&nbsp;&nbsp;<input type="submit" class="button" name="submit" id="submit" value="View Report"/>
	            </td>
	        </tr>		
		</table>
	  </form>
	<br/>
	<table class="form">
	    	<tr>
	    		<td>
	    			<b>Chọn chỉ số hiển thị: </b>
	    		</td>
	    	</tr>
	        <tr>
	        	<td>${checkColumns}</td>
			</tr>
</table>
<br/>
		<div style="overflow: auto;">
			<display:table style = "width: 150%" name="${vRpDyDistrictList}" id="vRpDyDistrict" requestURI="" pagesize="100" class="simple2" export="true" sort="list">
				<display:column property="day" format="{0,date,dd/MM/yyyy}" titleKey="DAY" sortable="true" headerClass="master-header-1" />
			    <display:column property="region" titleKey="TT" sortable="true" headerClass="master-header-1" />
				<display:column property="province" titleKey="PROVINCE"  sortable="true" headerClass="master-header-1" style = "width: 120px"/>
				<display:column property="district" titleKey="DISTRICT" headerClass="hide" class="hide"/>
			    <display:column titleKey="DISTRICT" media="html" sortable="true" sortProperty="district" headerClass="master-header-1" style = "width: 120px">
			    	<a href="${pageContext.request.contextPath}/report/radio/district-data/hr/detail.htm?province=${vRpDyDistrict.province}&district=${vRpDyDistrict.district}&endDate=<fmt:formatDate pattern="dd/MM/yyyy" value="${vRpDyDistrict.day}"/>">${vRpDyDistrict.district}</a>
			    </display:column>
			    <display:column property="sites" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="SITES" sortable="true" headerClass="master-header-1" />
			    <display:column property="cells" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="CELLS" headerClass="hide" class="hide"/>
			    <display:column titleKey="CELLS" media="html" sortable="true" sortProperty="cells" headerClass="master-header-1 margin" class="margin">
			    	<a href="${pageContext.request.contextPath}/report/radio/cell/dy/list.htm?province=${vRpDyDistrict.province}&day=<fmt:formatDate pattern="dd/MM/yyyy" value="${vRpDyDistrict.day}"/>"><fmt:formatNumber pattern="#,###,###,##0.##" value="${vRpDyDistrict.cells}"/></a>
			    </display:column>
			    <display:column property="gprsUlData" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="GPRS_UL_DATA"  sortable="true"/>
				<display:column property="gprsDlData" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="GPRS_DL_DATA"  sortable="true"/>
				<display:column property="edgeUlData" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="EDGE_UL_DATA"  sortable="true"/>
				<display:column property="edgeDlData" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="EDGE_DL_DATA"  sortable="true"/>
				<display:column property="gprsUlDataThroughput" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="GPRS_UL_DATA_THROUGHPUT"  sortable="true"/>
				<display:column property="gprsDlDataThroughput" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="GPRS_DL_DATA_THROUGHPUT"  sortable="true"/>
				<display:column property="edgeUlDataThroughput" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="EDGE_UL_DATA_THROUGHPUT"  sortable="true"/>
				<display:column property="edgeDlDataThroughput" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="EDGE_DL_DATA_THROUGHPUT" sortable="true"/>
				<display:column property="ulTbfSucr" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="UL_TBF_SUCR" class="UL_TBF_SUCR"  sortable="true"/>
				<display:column property="dlTbfSucr" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="DL_TBF_SUCR" class="DL_TBF_SUCR"  sortable="true"/>
				<display:column property="packetChAllocSucr" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="PACKET_CH_ALLOC_SUCR" class="PACKET_CH_ALLOC_SUCR"  sortable="true"/>
			</display:table>
		</div>
	<br/>
	<div id="tDrprChart" style="width: 1000px; margin: 1em auto"></div>
	<br/>
	<div id="sDrprChart" style="width: 1000px; margin: 1em auto"></div>
	<br/>
	<div id="cssrChart" style="width: 1000px; margin: 1em auto"></div>
	<br/>
</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/text_date.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/highcharts.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/exporting.js"></script>
${tDrprChart}
${sDrprChart}
${cssrChart}

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
		
		${highlight}
	});
</script>
