<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<title>Báo cáo BSC QoS</title>
<content tag="heading">BSC QoS MONTHLY REPORT</content>
<ul class="ui-tabs-nav">
	<li class=""><a href="${pageContext.request.contextPath}/report/radio/bsc/dy/list.htm"><span>Báo cáo ngày</span></a></li>
	<li class=""><a href="${pageContext.request.contextPath}/report/radio/bsc/wk/list.htm"><span>Báo cáo tuần</span></a></li>
	<li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/report/radio/bsc/mn/list.htm"><span>Báo cáo tháng</span></a></li>
	<li class=""><a href="${pageContext.request.contextPath}/report/radio/bsc/qr/list.htm"><span>Báo cáo quý</span></a></li>
	<li class=""><a href="${pageContext.request.contextPath}/report/radio/bsc/yr/list.htm"><span>Báo cáo năm</span></a></li>
</ul>
<div class="ui-tabs-panel">
	
		<table width="100%" class="form">
			<tr>
			    <td align="left">
			  <form method="get" action="list.htm" name="frmSample" onSubmit="return ValidateFormYear()">
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
	            	&nbsp;Tới tháng  <select name="endMonth" id="endMonth" onchange="xl()">
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
	            	&nbsp;<input type="submit" class="button" name="submit" id = "submit" value="View Report"/>
	          </form>
	            </td>
	        </tr>		
		</table>
	<br/>
	
			<div  style="overflow: auto;">
<display:table name="${vRpMnBsc}" id="vRpMnBsc" requestURI="" pagesize="100" class="simple2" export="true">
		<display:column property="month" titleKey="MONTH" sortable="true"/>
	    <display:column property="year" titleKey="YEAR" sortable="true"/>
	    <display:column property="region" titleKey="TT" sortable="true"/>
		<display:column property="bscid" titleKey="BSC" headerClass="hide" class="hide"/>
	    <display:column titleKey="BSC" media="html" sortable="true" sortProperty="bscid">
	    	<a href="${pageContext.request.contextPath}/report/radio/bsc/mn/detail.htm?bscid=${vRpMnBsc.bscid}&endMonth=${vRpMnBsc.month}&endYear=${vRpMnBsc.year}">${vRpMnBsc.bscid}</a>
	    </display:column>
	    <display:column property="sites" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="SITES" headerClass="hide" class="hide"/>
	    <display:column titleKey="SITES" media="html" sortable="true" sortProperty="sites">
	    	<a href="${pageContext.request.contextPath}/report/radio/site-qos/mn/list.htm?bscid=${vRpMnBsc.bscid}&month=${vRpMnBsc.month}&year=${vRpMnBsc.year}"><fmt:formatNumber pattern="#,###,###,##0.##" value="${vRpMnBsc.sites}"/></a>
	    </display:column>
	    <display:column property="cells" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="CELLS" headerClass="hide" class="hide"/>
	    <display:column titleKey="CELLS" media="html" sortable="true" sortProperty="cells">
	    	<a href="${pageContext.request.contextPath}/report/radio/cell/mn/list.htm?bscid=${vRpMnBsc.bscid}&month=${vRpMnBsc.month}&year=${vRpMnBsc.year}"><fmt:formatNumber pattern="#,###,###,##0.##" value="${vRpMnBsc.cells}"/></a>
	    </display:column>
	    <display:column property ="trxs" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="TRXS" sortable="true" headerClass="margin" class="margin"/>
	    <display:column property ="tTraf" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="T_TRAF" sortable="true"/>
	    <display:column property="tDrpr" titleKey="T_DRPR" class="T_DRPR" sortable="true"/>
	    <display:column property ="tBlkr" titleKey="T_BLKR" sortable="true" class="T_BLKR"/>
	    <display:column property ="tHoblkr" titleKey="T_HOBLKR" sortable="true" class="T_HOBLKR"/>
	    <display:column property="cssr" titleKey="CSSR" class="CSSR margin" sortable="true" headerClass="margin"/>
		<display:column property="sSsr" titleKey="SSR (%)" sortable="true"/>
	    <display:column property="sBlkr" titleKey="S_BLKR" sortable="true" class="S_BLKR"/>
	    <display:column property ="sDrpr" titleKey="S_DRPR" class="S_DRPR" sortable="true"/>
	    <display:column property="tAsr" titleKey="T_ASR" sortable="true"/>
	    <display:column property="halfrate" titleKey="HALFRATE" sortable="true" headerClass="margin" class="margin"/>
		<display:column property="downtime" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="DOWNTIME"  sortable="true"/>
	    <display:column property ="dataload" titleKey="DATALOAD" sortable="true"/>
</display:table>
</div>
</div>

<script type="text/javascript">
function xl(){
	var sub = document.getElementById("submit");
	sub.focus();
	} 
function CalcKeyCode(aChar) {
	   var character = aChar.substring(0,1);
	   var code = aChar.charCodeAt(0);
	   return code;
	 }
function checkNumber(val) {
	var strPass = val.value;
	var strLength = strPass.length;
	for(var i=1; i<= strLength;i++){
	  var lchar = val.value.charAt((strLength) - i);
	  var cCode = CalcKeyCode(lchar);

	  if (cCode < 48 || cCode > 57 ) {
	    var myNumber = val.value.substring(0, (strLength)-i);
	    val.value = myNumber;
	  }
	}
	  var sub = document.getElementById("submit");
		sub.focus();
	  return false;
	}
$(function() {
	${highlight}
});
</script>
