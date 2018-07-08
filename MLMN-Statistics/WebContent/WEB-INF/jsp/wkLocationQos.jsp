<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<title>location weekly report</title>
<content tag="heading">QOS LOCATION ${location} REPORT</content>
<ul class="ui-tabs-nav">
  <li class=""><a href="${pageContext.request.contextPath}/report/radio/location/hr/detail.htm?location=${location}"><span>Báo cáo giờ</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/radio/location/dy/detail.htm?location=${location}"><span>Báo cáo ngày</span></a></li>
  <li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/report/radio/location/wk/detail.htm?location=${location}&endWeek=${endWeek}&endYear=${endYear}"><span>Báo cáo tuần</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/radio/location/mn/detail.htm?location=${location}"><span>Báo cáo tháng</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/radio/location/dy/bh.htm?location=${location}"><span>Báo cáo BH ngày </span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/radio/location/wk/bh.htm?location=${location}&endWeek=${endWeek}&endYear=${endYear}"><span>Báo cáo BH tuần</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/radio/location/mn/bh.htm?location=${location}"><span>Báo cáo BH tháng</span></a></li>
</ul>
	<div class="ui-tabs-panel">

	  <form method="get" action="detail.htm" name = "frmSample" onSubmit = "return ValidateFormWeek()">
		<table width="100%" class="form">
			<tr>
			<td align="left">
					Trung tâm 
			  			<select name="region" id="region" onchange="xl()">
								<option value=""> Tất cả </option>
								<option value="TT2"> TT2 </option>
			                    <option value="TT6"> TT6 </option>
			              </select>
			        Location 
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
	            	Từ tuần <input value="${startWeek}" name="startWeek" id="startWeek" size="2" onchange ="javascript:checkNumber(document.frmSample.startWeek);">
	            	<input value="" name="startDay" id="startDay" class ="hide"/>
	            	&nbsp;&nbsp;Năm <input value="${startYear}" name="startYear" id="startYear" size="4" maxlength="4" onchange ="javascript:checkNumber(document.frmSample.startYear);">
	            	&nbsp;Tới tuần <input value="${endWeek}" name="endWeek" id="endWeek" size="2" onchange ="javascript:checkNumber(document.frmSample.endWeek);">
	            	<input value="" name="endDay" id="endDay" class ="hide"/>
	            	&nbsp;&nbsp;Năm <input value="${endYear}" name="endYear" id="endYear" size="4" maxlength="4" onchange ="javascript:checkNumber(document.frmSample.endYear);">
	            	&nbsp;&nbsp;<input type="submit" class="button" name="submit" value="View Report"/>
	            </td>
	        </tr>		
		</table>
	  </form>
	<br/>
	<table class="form">
        <tr>
        	<td><input type="checkbox" class="checkAll" checked="checked" /><b>Uncheck all</b></td>
			<c:set var="list1">T_TRAF,HAFT_RATE,CSSR,TSR,T_DRPR,T_BLKR,SSR,S_DRPR,S_BLKR,Og_Ho_Sucr,In_Ho_Sucr,DATALOAD</c:set>
			<c:forTokens items="${list1}" delims="," var="item">
				<c:choose>
				  <c:when test="${item == 'T_TRAF'}">
					<td><input type="checkbox" class="cb-element" name="${item}" id="${item}" checked="checked"/> ${item}</td>
				  </c:when>
				  <c:otherwise>
				    <td><input type="checkbox" class="cb-element" name="${item}" id="${item}" checked="checked"/> ${item} (%)</td>
				  </c:otherwise>
				</c:choose>
			</c:forTokens>
        </tr> 
	</table>
	<br/>
	<div  style="overflow: auto;">
	<display:table name="${vRpWkLocationList}" id="vRpWkLocation" requestURI="" pagesize="100" class="simple2" export="true" sort="list">
		<display:column property="region" titleKey="REGION" sortable="true"/>
		<display:column property="week" titleKey="WEEK" sortable="true"/>
	    <display:column property="location" titleKey="REGION" sortable="true"/>
	    <display:column property="sites" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="SITES"  sortable="true"/>
	    <display:column property="cells" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="CELLS"  sortable="true"/>
	    <display:column property="trxs" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="TRXS" class="margin" headerClass="margin" sortable="true"/>
	    <display:column property="tTraf" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="Traffic (Erl)"  sortable="true"/>
	    <display:column property="haftratePercent" titleKey="Half Rate (%)"  sortable="true"/>
	    <display:column property="cssr" titleKey="CSSR"  sortable="true"/>
	    <display:column property="tAsr" titleKey="T_ASR"  sortable="true"/>
	    <display:column property="tDrpr" titleKey="T_DRPR"  sortable="true"/>
	    <display:column property="tBlkr" titleKey="T_BLKR" sortable="true" class="T_BLKR"/>
	    <display:column property="tHoblkr" titleKey="T_HOBLKR"  class="margin" headerClass="margin" sortable="true"/>
		<display:column property="sSsr" titleKey="SSR (%)"  sortable="true"/>
	    <display:column property="sDrpr" titleKey="S_DRPR"  sortable="true"/>
	    <display:column property="sBlkr" titleKey="S_BLKR"  class="margin" headerClass="margin" sortable="true"/>
	    <display:column property="inHoSucr" titleKey="IN_HO_SUCR" sortable="true"/>
	    <display:column property="ogHoSucr" titleKey="OG_HO_SUCR" class="margin" headerClass="margin" sortable="true"/>
	    <display:column property="dataload" titleKey="DATALOAD"  sortable="true"/>
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
	   for(var i =0; i < strLength+1;i++){
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
	$(function() {
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
		$( '.checkAll' ).live( 'change', function() {
			$( '.cb-element' ).attr( 'checked', $( this ).is( ':checked' ) ? 'checked' : '' );
			$( this ).next().text( $( this ).is( ':checked' ) ? 'Uncheck All' : 'Check All' );
			if($( this ).is( ':checked' )){
				for (var i=7;i<=18;i++)
				{
					$('#vRpWkLocation td:nth-child('+i+'),#vRpWkLocation th:nth-child('+i+')').show();
				}
			} else {
				for (var i=7;i<=18;i++)
				{
					$('#vRpWkLocation td:nth-child('+i+'),#vRpWkLocation th:nth-child('+i+')').hide();
				}
			}
		});

		$( '.cb-element' ).live( 'change', function() {
			$( '.cb-element' ).length == $( '.cb-element:checked' ).length ? $( '.checkAll' ).attr( 'checked', 'checked' ).next().text( 'Uncheck All' ) : $( '.checkAll' ).attr( 'checked', '' ).next().text( 'Check All' );

		});

		${checkColumns}
	});
</script>
