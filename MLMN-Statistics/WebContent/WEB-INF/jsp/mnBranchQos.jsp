<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<title>Báo cáo chi nhánh ${branch}</title>
<content tag="heading">BRANCH REPORT ${branch}</content>
<ul class="ui-tabs-nav">
  <li class=""><a href="${pageContext.request.contextPath}/report/radio/branch/hr/detail.htm?branch=${branch}"><span>Báo cáo giờ</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/radio/branch/dy/detail.htm?branch=${branch}"><span>Báo cáo ngày</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/radio/branch/wk/detail.htm?branch=${branch}"><span>Báo cáo tuần</span></a></li>
  <li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/report/radio/branch/mn/detail.htm?branch=${branch}&endMonth=${endMonth}&endYear=${endYear}"><span>Báo cáo tháng</span></a></li>
</ul>
	<div class="ui-tabs-panel">

	  <form method="get" action="detail.htm" name="frmSample" onSubmit="return ValidateFormYear()">
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
			        Chi nhánh 
			        <select name="province" onchange="xl()">
			        	<option value="">Tất cả</option>
				        <c:forEach var="prv" items="${branchList}">
			              <c:choose>
			                <c:when test="${prv.branch == branch}">
			                    <option value="${prv.branch}" selected="selected">${prv.branch}</option>
			                </c:when>
			                <c:otherwise>
			                    <option value="${prv.branch}">${prv.branch}</option>
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
	<div  style="overflow: auto;">
<display:table name="${vRpMnBranchList}" id="vRpMnBranch" requestURI="" pagesize="100" class="simple2" export="true" sort="list">
		<display:column property="month" titleKey="MONTH" sortable="true"/>
		<display:column property="year" titleKey="YEAR" sortable="true"/>
		<display:column property="region" titleKey="TT" sortable="true"/>
		<display:column property="branch" titleKey="BRANCH" sortable="true"/>
	    <display:column property="sites" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="SITES" sortable="true"/>
	    <display:column property="cells" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="CELLS" sortable="true"/>
	    <display:column property="trxs" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="TRXS"  sortable="true" headerClass="margin" class="margin"/>
	    <display:column property="tTraf" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="Traffic (Erl)"  sortable="true"/>
		<display:column property="tBlkr" titleKey="T_BLKR" class="T_BLKR" sortable="true"/>
		<display:column property="tHoblkr" titleKey="T_HOBLKR"  sortable="true"/>
		<display:column property="tDrpr" titleKey="T_DRPR"  sortable="true" class="T_DRPR"/>
		<display:column property="cssr" titleKey="CSSR"  sortable="true" class="CSSR margin" headerClass="margin"/>
		<display:column property="sSsr" titleKey="S_SSR" sortable="true"/>
		<display:column property="sBlkr" titleKey="S_BLKR"  sortable="true" class="S_BLKR"/>
		<display:column property="sDrpr" titleKey="S_DRPR"  sortable="true"/>
		<display:column property="haftratePercent" titleKey="HALFRATE"  sortable="true" class="margin" headerClass="margin"/>
		<display:column property="inHoSucr" titleKey="IN_HO_SUCR" sortable="true"/>
		<display:column property="ogHoSucr" titleKey="OG_HO_SUCR"  sortable="true" class="margin" headerClass="margin"/>
	    <display:column property="dataload" titleKey="DATALOAD" sortable="true"/>
	</display:table>
</div>
	<br/>
	<div id="tDrprChart" style="width: 1000px; margin: 1em auto"></div>
	<br/>
	<div id="sDrprChart" style="width: 1000px; margin: 1em auto"></div>
	<br/>
	<div id="cssrChart" style="width: 1000px; margin: 1em auto"></div>
	<br/>
	<div id="tBlkrChart" style="width: 1000px; margin: 1em auto"></div>
	<br/>
	<div id="sBlkrChart" style="width: 1000px; margin: 1em auto"></div>
</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/text_date.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/highcharts.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/exporting.js"></script>
${availChart}
${trafChart}
${tDrprChart}
${sDrprChart}
${cssrChart}
${tBlkrChart}
${sBlkrChart}

<script type="text/javascript">
	$(function() {
		${highlight}
	});
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

</script>