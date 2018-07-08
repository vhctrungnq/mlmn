<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<title>province gprs qos weekly report</title>
<content tag="heading">GPRS QOS PROVINCE ${province} REPORT</content>
<ul class="ui-tabs-nav">
  <li class=""><a href="/VMSC2-Statistics/report/radio/province-gprs-qos/hr/details.htm?province=${province}&day=${endDate}"><span>Báo cáo giờ</span></a></li>
  <li class=""><a href="/VMSC2-Statistics/report/radio/province-gprs-qos/dy/details.htm?province=${province}"><span>Báo cáo ngày</span></a></li>
  <li class=""><a href="/VMSC2-Statistics/report/radio/province-gprs-qos/wk/details.htm?province=${province}&endWeek=${endWeek}&endYear=${endYear}"><span>Báo cáo tuần</span></a></li>
  <li class=""><a href="/VMSC2-Statistics/report/radio/province-gprs-qos/mn/details.htm?province=${province}"><span>Báo cáo tháng</span></a></li>
  <li class=""><a href="/VMSC2-Statistics/report/radio/province-gprs-qos/dy/bhDetails.htm?province=${province}"><span>Báo cáo BH ngày</span></a></li>
  <li class="ui-tabs-selected"><a href="/VMSC2-Statistics/report/radio/province-gprs-qos/wk/bDdetails.htm?province=${province}&endWeek=${endWeek}&endYear=${endYear}"><span>Báo cáo BH tuần</span></a></li>
  <li class=""><a href="/VMSC2-Statistics/report/radio/province-gprs-qos/mn/bhDetails.htm?province=${province}"><span>Báo cáo BH tháng</span></a></li>
</ul>
<div class="ui-tabs-panel">
	  <form method="get" action="bhDetails.htm" name="frmSample" onSubmit="return ValidateFormWeek()">
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
					PROVINCE 
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
					<img alt="calendar" title="Click to choose the start week number" id="chooseStartWeek" style="cursor: pointer;" src="/VMSC2-Statistics/images/calendar.png"/>
	            	&nbsp;Năm <input value="${startYear}" name="startYear" id="startYear" size="4" maxlength="4" onchange ="javascript:checkNumber(document.frmSample.startYear);">
	            	&nbsp;Tới tuần <input value="${endWeek}" name="endWeek" id="endWeek" size="2" maxlength="2" onchange="javascript:checkNumber(document.frmSample.endWeek);">
					<img alt="calendar" title="Click to choose the end week number" id="chooseEndWeek" style="cursor: pointer;" src="/VMSC2-Statistics/images/calendar.png"/>
	            	&nbsp;Năm <input value="${endYear}" name="endYear" id="endYear" size="4" maxlength="4" onchange ="javascript:checkNumber(document.frmSample.endYear);">
	            	&nbsp;<input type="submit" class="button" name="submit" id="submit"value="View Report"/>
	            </td>
	        </tr>		
		</table>
	  </form>
	<br/>
	<table class="form">
        <tr>
			<td align="left">
				<input type="checkbox" class="checkAll" checked="checked" /><b>Check all</b>
				<% 
					String[] provinceGprsQosBhArray = {"DL_TBF_REQ","DL_TBF_SUCR","UL_TBF_REQ","UL_TBF_SUCR","GDL_TRAF","GUL_TRAF","EDL_TRAF","EUL_TRAF"};
					String[]  provinceGprsQosBhNameArray = {"bhDlTbfReq","bhDlTbfSucr","bhUlTbfReq","bhUlTbfSucr","bhGdlTraf","bhGulTraf","bhEdlTraf","bhEulTraf"};
					int i;
					for (i = 0; i < provinceGprsQosBhArray.length; i++) {
				%>
		        <input type="checkbox" class="cb-element" name="<%= provinceGprsQosBhNameArray[i].toString()%>" id="<%= provinceGprsQosBhNameArray[i].toString()%>" checked="checked"/> <%= provinceGprsQosBhArray[i].toString()%>
		        <% } %>
            </td>
        </tr>		
	</table>
	<br/>
   	<div  style="overflow: auto;">
<display:table name="${vRpWkProvinceGprsQosBhDetails}" id="vRpWkProvinceGprsQosBhDetail" requestURI="" pagesize="100" class="simple2" export="true">
		<display:column property ="year" titleKey="YEAR" />
	    <display:column property ="week" titleKey="WEEK" />
	    <display:column property ="region" titleKey="REGION" />
	    <display:column property="province" titleKey="PROVINCE" class="hide" headerClass="hide"/>
	    <display:column titleKey="PROVINCE" media="html">
	    	<a href="/VMSC2-Statistics/report/radio/province-gprs-qos/wk/details.htm?province=${vRpWkProvinceGprsQosBhDetail.province}&endWeek=${week}&endYear=${year}">${vRpWkProvinceGprsQosBhDetail.province}</a>&nbsp;
	    </display:column> 
	    <display:column property ="bhDlTbfReq" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="DL_TBF_REQ" />
	    <display:column property ="bhDlTbfSucr" titleKey="DL_TBF_SUCR" />
	    <display:column property ="bhUlTbfReq" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="UL_TBF_REQ" />
	    <display:column property ="bhUlTbfSucr" titleKey="UL_TBF_SUCR"/>
	    <display:column property ="bhGdlTraf" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="GDL_TRAF" />
	    <display:column property ="bhGulTraf" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="GUL_TRAF" />
	    <display:column property ="bhEdlTraf" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="EDL_TRAF" />
	    <display:column property ="bhEulTraf" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="EUL_TRAF" />
	</display:table>
</div>
	<br/>
	<div id="dlTbfSucrChart" style="width: 1000px; margin: 1em auto"></div>
	<br/>
	<div id="ulTbfSucrChart" style="width: 1000px; margin: 1em auto"></div>
	</div>

<script type="text/javascript" src="/VMSC2-Statistics/scripts/highcharts.js"></script>
<script type="text/javascript" src="/VMSC2-Statistics/scripts/exporting.js"></script>
${dlTbfSucrChart}
${ulTbfSucrChart}

<script type="text/javascript" src="/VMSC2-Statistics/scripts/text_date.js"></script>
<script type="text/javascript" src="/VMSC2-Statistics/scripts/calendar.js"></script>
<script type="text/javascript" src="/VMSC2-Statistics/scripts/calendar_en.js"></script>
<script type="text/javascript" src="/VMSC2-Statistics/scripts/calendar_setup.js"></script>
<link rel="stylesheet" type="text/css" href="/VMSC2-Statistics/styles/calendar-blue.css" />

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
		$( '.checkAll' ).live( 'change', function() {
			$( '.cb-element' ).attr( 'checked', $( this ).is( ':checked' ) ? 'checked' : '' );
			$( this ).next().text( $( this ).is( ':checked' ) ? 'Uncheck All' : 'Check All' );
			if($( this ).is( ':checked' )){
				for (var i=6;i<=13;i++)
				{
					$('#vRpWkProvinceGprsQosBhDetail td:nth-child('+i+'),#vRpWkProvinceGprsQosBhDetail th:nth-child('+i+')').show();
				}
			} else {
				for (var i=5;i<=13;i++)
				{
					$('#vRpWkProvinceGprsQosBhDetail td:nth-child('+i+'),#vRpWkProvinceGprsQosBhDetail th:nth-child('+i+')').hide();
				}
			}
		});

		$( '.cb-element' ).live( 'change', function() {
			$( '.cb-element' ).length == $( '.cb-element:checked' ).length ? $( '.checkAll' ).attr( 'checked', 'checked' ).next().text( 'Uncheck All' ) : $( '.checkAll' ).attr( 'checked', '' ).next().text( 'Check All' );

		});

		${checkColumns}

		${checkSeries}
	});
</script>
