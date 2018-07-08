<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<title>Báo cáo QoS Province ${province}</title>
<content tag="heading">PROVINCE QOS ${province} REPORT</content>
<ul class="ui-tabs-nav">
  <li class=""><a href="${pageContext.request.contextPath}/report/radio/province/hr/detail.htm?province=${province}"><span>Báo cáo giờ</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/radio/province/dy/detail.htm?province=${province}"><span>Báo cáo ngày</span></a></li>
  <li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/report/radio/province/wk/detail.htm?province=${province}&endWeek=${endWeek}&endYear=${endYear}"><span>Báo cáo tuần</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/radio/province/mn/detail.htm?province=${province}"><span>Báo cáo tháng</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/radio/province/dy/bh.htm?province=${province}"><span>Báo cáo BH ngày </span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/radio/province/wk/bh.htm?province=${province}&endWeek=${endWeek}&endYear=${endYear}"><span>Báo cáo BH tuần</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/radio/province/mn/bh.htm?province=${province}"><span>Báo cáo BH tháng</span></a></li>
</ul>
	<div class="ui-tabs-panel">

	  <form method="get" action="detail.htm" name="frmSample" onSubmit="return ValidateFormWeek()">
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
	            	&nbsp;Từ tuần <input value="${startWeek}" name="startWeek" id="startWeek" size="2" maxlength="2" onchange ="javascript:checkNumber(document.frmSample.startWeek);">
					<img alt="calendar" title="Click to choose the start week number" id="chooseStartWeek" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
	            	&nbsp;Năm <input value="${startYear}" name="startYear" id="startYear" size="4" maxlength="4" onchange ="javascript:checkNumber(document.frmSample.startYear);">
	            	&nbsp;Tới tuần <input value="${endWeek}" name="endWeek" id="endWeek" size="2" maxlength="2" onchange="javascript:checkNumber(document.frmSample.endWeek);">
					<img alt="calendar" title="Click to choose the end week number" id="chooseEndWeek" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
	            	&nbsp;Năm <input value="${endYear}" name="endYear" id="endYear" size="4" maxlength="4" onchange ="javascript:checkNumber(document.frmSample.endYear);">
	            	&nbsp;<input type="submit" class="button" name="submit" id="submit"value="View Report"/>
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
<display:table name="${vRpWkProvinceList}" id="vRpWkProvince" requestURI="" pagesize="100" class="simple2" export="true" sort="list">
		<display:column property="week" titleKey="WEEK" sortable="true"/>
		<display:column property="year" titleKey="YEAR" sortable="true"/>
	    <display:column property="region" titleKey="REGION" sortable="true"/>
	    <display:column property="province" titleKey="PROVINCE" class="hide" headerClass="hide"/>
		<display:column titleKey="PROVINCE" media="html" sortable="true" sortProperty="province">
	    	<a href="${pageContext.request.contextPath}/report/radio/district/wk/list.htm?province=${vRpWkProvince.province}&endWeek=${vRpWkProvince.week}&endYear=${vRpWkProvince.year}&startWeek=${vRpWkProvince.week}&startYear=${vRpWkProvince.year}">${vRpWkProvince.province}</a>
	    </display:column>
	    <display:column property="sites" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="SITES" headerClass="hide" class="hide"/>
	    <display:column titleKey="SITES" media="html" sortable="true" sortProperty="sites">
	    	<a href="${pageContext.request.contextPath}/report/radio/site-qos/wk/list.htm?province=${vRpWkProvince.province}&week=${vRpWkProvince.week}&year=${vRpWkProvince.year}"><fmt:formatNumber pattern="#,###,###,##0.##" value="${vRpWkProvince.sites}"/></a>
	    </display:column>
	    <display:column property="cells" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="CELLS" headerClass="hide" class="hide"/>
	    <display:column titleKey="CELLS" media="html" sortable="true" sortProperty="cells">
	    	<a href="${pageContext.request.contextPath}/report/radio/cell/wk/list.htm?province=${vRpWkProvince.province}&week=${vRpWkProvince.week}&year=${vRpWkProvince.year}"><fmt:formatNumber pattern="#,###,###,##0.##" value="${vRpWkProvince.cells}"/></a>
	    </display:column>
	    <display:column property="trxs" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="TRXS"  sortable="true" class="margin" headerClass="margin"/>
	    <display:column property="tTraf" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="Traffic (Erl)" sortable="true"/>
	    <display:column property="tDrpr" titleKey="T_DRPR" class="T_DRPR" sortable="true"/>
	    <display:column property="tBlkr" titleKey="T_BLKR" sortable="true" class="T_BLKR"/>
	    <display:column property="tHoblkr" titleKey="T_HOBLKR" sortable="true"/>
	    <display:column property="cssr" titleKey="CSSR" class="CSSR margin" sortable="true" headerClass="margin"/>
		<display:column property="sSsr" titleKey="SSR (%)" sortable="true"/>
	    <display:column property="sBlkr" titleKey="S_BLKR" sortable="true"  class="S_BLKR"/>
	    <display:column property="sDrpr" titleKey="S_DRPR" sortable="true"/>
	    <display:column property="tAsr" titleKey="T_ASR" sortable="true" />
	    <display:column property="haftratePercent" titleKey="Half Rate (%)" sortable="true"  class="margin" headerClass="margin"/>
	    <display:column property="inHoSucr" titleKey="IN_HO_SUCR" sortable="true"/>
	    <display:column property="ogHoSucr" titleKey="OG_HO_SUCR" sortable="true"  class="margin" headerClass="margin"/>
	    <display:column property="dataload" titleKey="DATALOAD" sortable="true" />
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
	<br/>
	<div id="tTrafChart" style="width: 1000px; margin: 1em auto"></div>
</div>

<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/highcharts.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/exporting.js"></script>
${availChart}
${trafChart}
${tDrprChart}
${sDrprChart}
${cssrChart}
${tBlkrChart}
${sBlkrChart}
${tTrafChart}

<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/text_date.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/calendar.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/calendar_en.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/calendar_setup.js"></script>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/calendar-blue.css" />

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
	  for(var i=0;i<strLength+1;i++){
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
    Calendar.setup({
        inputField		:	"startWeek",	// id of the input field
        ifFormat		:	"%W",   	// format of the input field
        button			:   "chooseStartWeek",  	// trigger for the calendar (button ID)
        singleClick		:   false					// double-click mode
    });
    Calendar.setup({
        inputField		:	"endWeek",	// id of the input field
        ifFormat		:	"%W",   	// format of the input field
        button			:   "chooseEndWeek",  	// trigger for the calendar (button ID)
        singleClick		:   false					// double-click mode
    });
	$(function() {		
		${highlight}
	});
</script>
