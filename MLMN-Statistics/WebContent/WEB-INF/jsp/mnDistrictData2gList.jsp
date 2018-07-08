<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<title>Báo cáo District Data 2G</title>
<content tag="heading">DISTRICT DATA 2G MONTHLY REPORT</content>
<ul class="ui-tabs-nav">
	<li class=""><a href="${pageContext.request.contextPath}/report/radio/district-data/dy/list.htm"><span>Báo cáo ngày</span></a></li>
	<li class=""><a href="${pageContext.request.contextPath}/report/radio/district-data/wk/list.htm"><span>Báo cáo tuần</span></a></li>
	<li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/report/radio/district-data/mn/list.htm"><span>Báo cáo tháng</span></a></li>
	<li class=""><a href="${pageContext.request.contextPath}/report/radio/district-data/qr/list.htm"><span>Báo cáo quý</span></a></li>
	<li class=""><a href="${pageContext.request.contextPath}/report/radio/district-data/yr/list.htm"><span>Báo cáo năm</span></a></li>
</ul>
<div class="ui-tabs-panel">
	
		<table width="100%" class="form">
			<tr>
			    <td align="left">
			  <form method="get" action="list.htm" onSubmit="return ValidateFormYear()">
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
			        &nbsp;Province
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
				    &nbsp;District <input value="${district}" name="district" id="district" size="10" maxlength="50" onchange="xl()">
	              	&nbsp;Từ tháng <select name="startMonth" id="startMonth" onchange="xl()">
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
<display:table name="${vRpMnDistrict}" id="vRpMnDistrict" requestURI="" pagesize="100" class="simple2" export="true" sort="list">
			    <display:column property="month" titleKey="MONTH" sortable="true"/>
			    <display:column property="year" titleKey="YEAR" sortable="true"/>
			    <display:column property="region" titleKey="TT" sortable="true"/>
			    <display:column property="province" titleKey="PROVINCE" sortable="true"/>
			    <display:column property="district" titleKey="DISTRICT" sortable="true" class="hide" headerClass="hide"/>
			    <display:column titleKey="DISTRICT" media="html" sortable="true" sortProperty="district">
			    	<a href="${pageContext.request.contextPath}/report/radio/district-data/mn/detail.htm?province=${vRpMnDistrict.province}&district=${vRpMnDistrict.district}&endMonth=${vRpMnDistrict.month}&endYear=${vRpMnDistrict.year}">${vRpMnDistrict.district}</a>
			    </display:column>
			    <display:column property="sites" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="SITES" sortable="true"/>
			    <display:column property="cells" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="CELLS" headerClass="hide" class="hide"/>
			    <display:column titleKey="CELLS" media="html" sortable="true" sortProperty="cells">
			    	<a href="${pageContext.request.contextPath}/report/radio/cell-data/mn/list.htm?province=${vRpMnDistrict.province}&month=${vRpMnDistrict.month}&year=${vRpMnDistrict.year}"><fmt:formatNumber pattern="#,###,###,##0.##" value="${vRpMnDistrict.cells}"/></a>
			    </display:column>
			    <display:column property="gprsUlData" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="GPRS_UL_DATA_GB"  sortable="true"/>
				<display:column property="gprsDlData" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="GPRS_DL_DATA_GB"  sortable="true"/>
				<display:column property="edgeUlData" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="EDGE_UL_DATA_GB"  sortable="true"/>
				<display:column property="edgeDlData" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="EDGE_DL_DATA_GB"  sortable="true"/>
				<display:column property="gprsUlDataThroughput" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="GPRS_UL_DATA_THROUGHPUT"  sortable="true"/>
				<display:column property="gprsDlDataThroughput" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="GPRS_DL_DATA_THROUGHPUT"  sortable="true"/>
				<display:column property="edgeUlDataThroughput" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="EDGE_UL_DATA_THROUGHPUT"  sortable="true"/>
				<display:column property="edgeDlDataThroughput" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="EDGE_DL_DATA_THROUGHPUT" sortable="true"/>
				<display:column property="ulTbfSucr" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="UL_TBF_SUCR" class="UL_TBF_SUCR"  sortable="true"/>
				<display:column property="dlTbfSucr" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="DL_TBF_SUCR" class="DL_TBF_SUCR"  sortable="true"/>
				<display:column property="packetChAllocSucr" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="PACKET_CH_ALLOC_SUCR" class="PACKET_CH_ALLOC_SUCR"  sortable="true"/>
			</display:table>
</div>
</div>

<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/text_date.js"></script>
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
	  for(var i =0;i<strLength+1;i++){
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
	} 
	$(function() {
		${highlight}
	});
</script>
<script type="text/javascript">
$(function() {
	${highlight}
});
</script>
