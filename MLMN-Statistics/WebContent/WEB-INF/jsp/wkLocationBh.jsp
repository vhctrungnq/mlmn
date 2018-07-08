<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<title>location Weekly report</title>
<content tag="heading">LOCATION QOS ${location} REPORT</content>
<ul class="ui-tabs-nav">
  <li class=""><a href="${pageContext.request.contextPath}/report/radio/location/hr/detail.htm?location=${location}"><span>Báo cáo giờ</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/radio/location/dy/detail.htm?location=${location}"><span>Báo cáo ngày</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/radio/location/wk/detail.htm?location=${location}&endWeek=${endWeek}&endYear=${endYear}"><span>Báo cáo tuần</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/radio/location/mn/detail.htm?location=${location}"><span>Báo cáo tháng</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/radio/location/dy/bh.htm?location=${location}"><span>Báo cáo BH ngày</span></a></li>
  <li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/report/radio/location/wk/bh.htm?location=${location}&endWeek=${endWeek}&endYear=${endYear}"><span>Báo cáo BH tuần</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/radio/location/mn/bh.htm?location=${location}"><span>Báo cáo BH tháng</span></a></li>
</ul>
	<div class="ui-tabs-panel">

	  <form method="get" action="bh.htm" name="frmSample" onSubmit="return ValidateFormWeek()">
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
			         LOCATION 
			        <select name="location" onchange="xl()">
			        	<option value="">Tất cả</option>
				        <c:forEach var="prv" items="${locationList}">
			              <c:choose>
			                <c:when test="${prv.location == location}">
			                    <option value="${prv.location}" selected="selected">${prv.location}</option>
			                </c:when>
			                <c:otherwise>
			                    <option value="${prv.location}">${prv.location}</option>
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
	        	<td><input type="checkbox" class="checkAll" checked="checked" /><b>Uncheck all</b></td>
				<c:set var="list1">T_DEF,T_AVAIL,T_ATT,TRAF,H_TRAF,CSSR,T_DRPS,T_DRPR,T_BLKS,T_BLKR,T_HOBLKS,T_HOBLKR</c:set>
				<c:forTokens items="${list1}" delims="," var="item">
					<c:choose>
					  <c:when test="${item == 'CSSR' || item == 'T_DRPR' || item == 'T_BLKR'}">
						<td><input type="checkbox" class="cb-element" name="${item}" id="${item}" checked="checked"/> ${item} (%)</td>
					  </c:when>
					  <c:otherwise>
					    <td><input type="checkbox" class="cb-element" name="${item}" id="${item}" checked="checked"/> ${item}</td>
					  </c:otherwise>
					</c:choose>
				</c:forTokens>
			</tr>
			<tr>
				<c:set var="list2">GoS,S_DEF,S_AVAIL,S_ATT,S_DRPS,S_DRPR,S_BLKS,S_BLKR</c:set>
				<c:forTokens items="${list2}" delims="," var="item">
					<c:choose>
					  <c:when test="${item == 'GoS' || item == 'S_DRPR' || item == 'S_BLKR'}">
						<td><input type="checkbox" class="cb-element" name="${item}" id="${item}" checked="checked"/> ${item} (%)</td>
					  </c:when>
					  <c:otherwise>
					    <td><input type="checkbox" class="cb-element" name="${item}" id="${item}" checked="checked"/> ${item}</td>
					  </c:otherwise>
					</c:choose>
				</c:forTokens>
	        </tr>
	</table>
	<br/>
	<div  style="overflow: auto;">
	<display:table name="${vRpWkLocationBhList}" id= "vRpWkLocationBh" requestURI="" pagesize="100" class="simple2" export="true">
		<display:column property="region" titleKey="TT"/>
		<display:column property ="week" titleKey="WEEK"/>
		<display:column property ="year" titleKey="YEAR"/>
	    <display:column property="location" titleKey="REGION" class="margin" headerClass="margin"/>
	    <display:column property ="bhTDef" titleKey="T_DEF" />
	    <display:column property="bhTAvail" titleKey="T_AVAIL" />
	    <display:column property ="bhTAtts" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="T_ATTS" />
	    <display:column property ="bhTTraf" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="BH_T_TRAF" />
	    <display:column property ="bhTTrafh" titleKey="T_TRAFH" />
	    <display:column property="bhCssr" titleKey="CSSR"/> 
	    <display:column property ="bhTDrps" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="T_DRPS" />
	    <display:column property="bhTDrpr" titleKey="T_DRPR" />
	    <display:column property="bhTBlks" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="T_BLKS"/>
	    <display:column property="bhTBlkr" titleKey="T_BLKR"/>
	    <display:column property="bhTHoblks" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="T_HOBLKS"/>
	    <display:column property="bhTHoblkr" titleKey="T_HOBLKR"/>
	    <display:column property="bhTGos" titleKey="GoS (%)" class="margin" headerClass="margin"/>
	    <display:column property ="bhSDef" titleKey="S_DEF" />
	    <display:column property="bhSAvail" titleKey="S_AVAIL" />
	    <display:column property ="bhSAtts" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="S_ATTS" />
	    <display:column property ="bhSDrps" titleKey="S_DRPS" />
	    <display:column property ="bhSDrpr" titleKey="S_DRPR" />
	    <display:column property ="bhSBlks" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="S_BLKS"/>
	    <display:column property="bhSBlkr" titleKey="S_BLKR" />
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
${tDrprChart}
${sDrprChart}
${cssrChart}
${tBlkrChart}
${sBlkrChart}

<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/calendar.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/calendar_en.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/calendar_setup.js"></script>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/calendar-blue.css" />

<script type="text/javascript">
	function xl(){
		var sub=document.getElementById("submit");
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
				for (var i=5;i<=22;i++)
				{
					$('#vRpWkLocationBh td:nth-child('+i+'),#vRpWkLocationBh th:nth-child('+i+')').show();
				}
			} else {
				for (var i=5;i<=22;i++)
				{
					$('#vRpWkLocationBh td:nth-child('+i+'),#vRpWkLocationBh th:nth-child('+i+')').hide();
				}
			}
		});

		$( '.cb-element' ).live( 'change', function() {
			$( '.cb-element' ).length == $( '.cb-element:checked' ).length ? $( '.checkAll' ).attr( 'checked', 'checked' ).next().text( 'Uncheck All' ) : $( '.checkAll' ).attr( 'checked', '' ).next().text( 'Check All' );

		});

		${checkColumns}
	});
</script>
