<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<title>Báo cáo Province QoS</title>
<content tag="heading">PROVINCE QOS MONTHLY REPORT</content>
<ul class="ui-tabs-nav">
	<li class=""><a href="${pageContext.request.contextPath}/report/radio/province/dy/list.htm"><span>Báo cáo ngày</span></a></li>
	<li class=""><a href="${pageContext.request.contextPath}/report/radio/province/wk/list.htm"><span>Báo cáo tuần</span></a></li>
	<li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/report/radio/province/mn/list.htm"><span>Báo cáo tháng</span></a></li>
	<li class=""><a href="${pageContext.request.contextPath}/report/radio/province/qr/list.htm"><span>Báo cáo quý</span></a></li>
	<li class=""><a href="${pageContext.request.contextPath}/report/radio/province/yr/list.htm"><span>Báo cáo năm</span></a></li>
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
<display:table name="${vRpMnProvince}" id="vRpMnProvince" requestURI="" pagesize="100" class="simple2" export="true" sort="list">
				<display:column property="month" titleKey="MONTH" sortable="true"/>
			    <display:column property="year" titleKey="YEAR" sortable="true"/>
			    <display:column property="region" titleKey="TT" sortable="true"/>
			    <display:column property="province" titleKey="PROVINCE" headerClass="hide" class="hide"/>
			    <display:column titleKey="PROVINCE" media="html" sortable="true" sortProperty="province">
			    	<a href="${pageContext.request.contextPath}/report/radio/province/mn/detail.htm?province=${vRpMnProvince.province}&endMonth=${vRpMnProvince.month}&endYear=${vRpMnProvince.year}">${vRpMnProvince.province}</a>
			    </display:column>
			    <display:column property="sites" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="SITES" headerClass="hide" class="hide"/>
			    <display:column titleKey="SITES" media="html" sortable="true" sortProperty="sites">
			    	<a href="${pageContext.request.contextPath}/report/radio/site-qos/mn/list.htm?province=${vRpMnProvince.province}&month=${vRpMnProvince.month}&year=${vRpMnProvince.year}"><fmt:formatNumber pattern="#,###,###,##0.##" value="${vRpMnProvince.sites}"/></a>
			    </display:column>
			    <display:column property="cells" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="CELLS" headerClass="hide" class="hide"/>
			    <display:column titleKey="CELLS" media="html" sortable="true" sortProperty="cells">
			    	<a href="${pageContext.request.contextPath}/report/radio/cell/mn/list.htm?province=${vRpMnProvince.province}&month=${vRpMnProvince.month}&year=${vRpMnProvince.year}"><fmt:formatNumber pattern="#,###,###,##0.##" value="${vRpMnProvince.cells}"/></a>
			    </display:column>
			    <display:column property="trxs" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="TRXS" sortable="true" class="margin" headerClass="margin"/>
			    <display:column property="tTraf" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="T_TRAF" sortable="true"/>
			    <display:column property="tDrpr" titleKey="T_DRPR" class="T_DRPR" sortable="true"/>
			    <display:column property="tBlkr" titleKey="T_BLKR" class="T_BLKR" sortable="true"/>
			    <display:column property="tHoblkr" titleKey="T_HOBLKR" class="T_HOBLKR" sortable="true"/>
			    <display:column property="cssr" titleKey="CSSR" class="CSSR margin" headerClass="margin" sortable="true"/>
			    <display:column property="sSsr" titleKey="SSR (%)" sortable="true"/>
			    <display:column property="sBlkr" titleKey="S_BLKR" class="S_BLKR" sortable="true"/>
			    <display:column property="sDrpr" titleKey="S_DRPR" class="S_DRPR" sortable="true"/>
			    <display:column property="tAsr" titleKey="T_ASR" class="T_ASR" sortable="true"/>
			    <display:column property="haftratePercent" titleKey="HALFRATE" sortable="true" class="margin" headerClass="margin"/>
			    <%-- <display:column property="inHoSucr" titleKey="IN_HO_SUCR" sortable="true"/>
			    <display:column property="ogHoSucr" titleKey="OG_HO_SUCR" sortable="true"/> --%>
			    <display:column property="downtime" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="DOWNTIME"  sortable="true" class="margin" headerClass="margin"/>
			    <display:column property="dataload" titleKey="DATALOAD" sortable="true" />
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
