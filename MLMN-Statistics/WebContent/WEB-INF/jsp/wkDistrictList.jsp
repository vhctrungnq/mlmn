<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<title>Báo cáo District QoS</title>
<content tag="heading">DISTRICT QOS WEEKLY REPORT</content>
<ul class="ui-tabs-nav">
	<li class=""><a href="${pageContext.request.contextPath}/report/radio/district/dy/list.htm"><span>Báo cáo ngày</span></a></li>
	<li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/report/radio/district/wk/list.htm"><span>Báo cáo tuần</span></a></li>
	<li class=""><a href="${pageContext.request.contextPath}/report/radio/district/mn/list.htm"><span>Báo cáo tháng</span></a></li>
	<li class=""><a href="${pageContext.request.contextPath}/report/radio/district/qr/list.htm"><span>Báo cáo quý</span></a></li>
	<li class=""><a href="${pageContext.request.contextPath}/report/radio/district/yr/list.htm"><span>Báo cáo năm</span></a></li>
</ul>
<div class="ui-tabs-panel">
	
		<table width="100%" class="form">
			<tr>
			    <td align="left">
			  <form method="get" action="list.htm" name = "frmSample" onSubmit = "return ValidateForm()">
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
			        <select name="province">
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
				    &nbsp; District <input value="${district}" name="district" id="district" size="10" maxlength="50" />
			       &nbsp;Từ tuần <input value="${startWeek}" name="startWeek" id="startWeek" size="2" maxlength="2" onchange ="javascript:checkNumber1(document.frmSample.startWeek);">
					<img alt="calendar" title="Click to choose the start week number" id="chooseStartWeek" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
	            	&nbsp;Năm <input value="${startYear}" name="startYear" id="startYear" size="4" maxlength="4" onchange ="javascript:checkNumber(document.frmSample.startYear);">
	            	&nbsp;Tới tuần <input value="${endWeek}" name="endWeek" id="endWeek" size="2" maxlength="2" onchange ="javascript:checkNumber1(document.frmSample.endWeek);">
					<img alt="calendar" title="Click to choose the end week number" id="chooseEndWeek" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
	            	&nbsp;Năm <input value="${endYear}" name="endYear" id="endYear" size="4" maxlength="4" onchange ="javascript:checkNumber(document.frmSample.endYear);">
	            	&nbsp;<input type="submit" class="button" name="submit" id="submit" value="View Report"/>
	          </form>
	            </td>
	        </tr>		
		</table>
	<br/>
	
		<div  style="overflow: auto;">
			<display:table name="${vRpWkDistrict}" id="vRpWkDistrict" requestURI="" pagesize="100" class="simple2" export="true" sort="list">
			    <display:column property="week" titleKey="WEEK" sortable="true"/>
			    <display:column property="year" titleKey="YEAR" sortable="true"/>
			    <display:column property="region" titleKey="TT" sortable="true"/>
			    <display:column property="province" titleKey="PROVINCE" sortable="true" class="hide" headerClass="hide"/>
			    <display:column titleKey="PROVINCE" media="html" sortable="true" sortProperty="province">
			    	<a href="${pageContext.request.contextPath}/report/radio/province/wk/detail.htm?province=${vRpWkDistrict.province}&endWeek=${vRpWkDistrict.week}&endYear=${vRpWkDistrict.year}">${vRpWkDistrict.province}</a>
			    </display:column>
			    <display:column property="district" titleKey="DISTRICT" sortable="true" class="hide" headerClass="hide"/>
			    <display:column titleKey="DISTRICT" media="html" sortable="true" sortProperty="district">
			    	<a href="${pageContext.request.contextPath}/report/radio/district/wk/detail.htm?province=${vRpWkDistrict.province}&district=${vRpWkDistrict.district}&endWeek=${vRpWkDistrict.week}&endYear=${vRpWkDistrict.year}">${vRpWkDistrict.district}</a>
			    </display:column>
			    <display:column property="sites" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="SITES" headerClass="hide" class="hide"/>
			    <display:column titleKey="SITES" media="html" sortable="true" sortProperty="sites">
			    	<a href="${pageContext.request.contextPath}/report/radio/site-qos/wk/list.htm?province=${vRpWkDistrict.province}&week=${vRpWkDistrict.week}&year=${vRpWkDistrict.year}"><fmt:formatNumber pattern="#,###,###,##0.##" value="${vRpWkDistrict.sites}"/></a>
			    </display:column>
			    <display:column property="cells" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="CELLS" headerClass="hide" class="hide"/>
			    <display:column titleKey="CELLS" media="html" sortable="true" sortProperty="cells">
			    	<a href="${pageContext.request.contextPath}/report/radio/cell/wk/list.htm?province=${vRpWkDistrict.province}&week=${vRpWkDistrict.week}&year=${vRpWkDistrict.year}"><fmt:formatNumber pattern="#,###,###,##0.##" value="${vRpWkDistrict.cells}"/></a>
			    </display:column>
			    <display:column property="trxs" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="TRXS" sortable="true"  class="margin" headerClass="margin"/>
			    <display:column property="tTraf" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="T_TRAF" sortable="true"/>
			    <display:column property="tDrpr" titleKey="T_DRPR" class="T_DRPR" sortable="true"/>
			    <display:column property="tBlkr" titleKey="T_BLKR" class="T_BLKR" sortable="true"/>
			    <display:column property="tHoblkr" titleKey="T_HOBLKR" class="T_HOBLKR" sortable="true"/>
			    <display:column property="cssr" titleKey="CSSR" class="CSSR margin" sortable="true" headerClass="margin"/>
			    <display:column property="sSsr" titleKey="S_SSR" sortable="true"/>
			    <display:column property="sBlkr" titleKey="S_BLKR" class="S_BLKR" sortable="true"/>
			    <display:column property="sDrpr" titleKey="S_DRPR" class="S_DRPR" sortable="true"/>
			    <display:column property="tAsr" titleKey="T_ASR" class="T_ASR" sortable="true"/>
			    <display:column property="haftratePercent" titleKey="HALFRATE" sortable="true" class="margin" headerClass="margin"/>
			    <%-- <display:column property="inHoSucr" titleKey="IN_HO_SUCR" sortable="true"/>
			    <display:column property="ogHoSucr" titleKey="OG_HO_SUCR" sortable="true"/> --%>
			    <display:column property="downtime" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="DOWNTIME"  sortable="true"  class="margin" headerClass="margin"/>
			    <display:column property="dataload" titleKey="DATALOAD" sortable="true" />
			</display:table>
		</div>
</div>

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
		var minYear=1900;
		var maxYear=2100;
		var minWeek=1;
		var maxWeek=53;
		function isDate(dtStr){
			var year = dtStr;
			if (year.length != 4 || year==0 || year<minYear || year>maxYear || year != year.getfullyear()){
				alert("Vui lòng nhập năm trong khoảng từ "+minYear+" đến "+maxYear);
				return false;
			}
			return true;
		}
		function isDate2(dtStr){
			var week = dtStr;
			if (week==0 || week<minWeek || week>maxWeek){
				alert("Vui lòng nhập tuần trong khoảng từ "+minWeek+" đến "+maxWeek);
				return false;
			}
			return true;
		}

		function ValidateForm(){
			
			var dt=document.frmSample.startYear;
			var dt1=document.frmSample.endYear;
			var wk=document.frmSample.startWeek;
			var wk1=document.frmSample.endWeek;
			
			if (isDate2(wk.value)==false){
				wk.focus();
				return false;
			}
			if (isDate2(wk1.value)==false){
				wk1.focus();
				return false;
			}
			if (isDate(dt.value)==false){
				dt.focus();
				return false;
			}
			if (isDate(dt1.value)==false){
				dt1.focus();
				return false;
			}
			
		    return true;
		 }
</script>	
<script type="text/javascript">

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
