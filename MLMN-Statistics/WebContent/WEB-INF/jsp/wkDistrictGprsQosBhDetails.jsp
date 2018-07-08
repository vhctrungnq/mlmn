<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<title>district gprs qos weekly report</title>
<content tag="heading">GPRS-QOS DISTRICT REPORT ${district}/${province}</content>
<ul class="ui-tabs-nav">
  <li class=""><a href="#"><span>Báo cáo giờ</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/radio/district-gprs-qos/dy/details.htm?province=${province}&district=${district}"><span>Báo cáo ngày</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/radio/district-gprs-qos/wk/details.htm?province=${province}&district=${district}&endWeek=${endWeek}&endYear=${endYear}"><span>Báo cáo tuần</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/radio/district-gprs-qos/mn/details.htm?province=${province}&district=${district}"><span>Báo cáo tháng</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/radio/district-gprs-qos/dy/bhDetails.htm?province=${province}&district=${district}"><span>Báo cáo BH ngày</span></a></li>
  <li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/report/radio/district-gprs-qos/wk/bDdetails.htm?province=${province}&district=${district}&endWeek=${endWeek}&endYear=${endYear}"><span>Báo cáo BH tuần</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/radio/district-gprs-qos/mn/bhDetails.htm?province=${province}&district=${district}"><span>Báo cáo BH tháng</span></a></li>
</ul>
<div class="ui-tabs-panel">
	  <form method="get" action="bhDetails.htm">
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
					&nbsp;&nbsp;DISTRICT <input value="${district}" name="district" id="district" size="10" maxlength="50">
	            	&nbsp;Từ tuần <input value="${startWeek}" name="startWeek" id="startWeek" size="2">
					<img alt="calendar" title="Click to choose the start week number" id="chooseStartWeek" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
	            	&nbsp;Năm <input value="${startYear}" name="startYear" id="startYear" size="4" maxlength="4">
	            	&nbsp;Tới tuần <input value="${endWeek}" name="endWeek" id="endWeek" size="2">
					<img alt="calendar" title="Click to choose the end week number" id="chooseEndWeek" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
	            	&nbsp;Năm <input value="${endYear}" name="endYear" id="endYear" size="4" maxlength="4">
	            	&nbsp;&nbsp;<input type="submit" class="button" name="submit" value="View Report"/>
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
					String[] districtGprsQosBhArray = {"DL_TBF_REQ","DL_TBF_SUCR","UL_TBF_REQ","UL_TBF_SUCR","GDL_TRAF","GUL_TRAF","EDL_TRAF","EUL_TRAF"};
					String[]  districtGprsQosBhNameArray = {"bhDlTbfReq","bhDlTbfSucr","bhDlTbfReq","bhUlTbfSucr","bhGdlTraf","bhGulTraf","bhEdlTraf","bhEulTraf"};
					int i;
					for (i = 0; i < districtGprsQosBhArray.length; i++) {
				%>
		        <input type="checkbox" class="cb-element" name="<%= districtGprsQosBhNameArray[i].toString()%>" id="<%= districtGprsQosBhNameArray[i].toString()%>" checked="checked"/> <%= districtGprsQosBhArray[i].toString()%>
		        <% } %>
            </td>
        </tr>		
	</table>
	<br/>
   	<div  style="overflow: auto;">
<display:table name="${vRpWkDistrictGprsQosBhDetails}" id="vRpWkDistrictGprsQosBhDetail" requestURI="" pagesize="100" class="simple2" export="true">
		<display:column property ="year" titleKey="YEAR" />
	    <display:column property ="week" titleKey="WEEK" />
	    <display:column property ="region" titleKey="TT" />
	    <display:column property="province" titleKey="PROVINCE" headerClass="hide" class="hide"/>
	    <display:column titleKey="PROVINCE" media="html">
	    	<a href="${pageContext.request.contextPath}/report/radio/province-gprs-qos/wk/details.htm?province=${vRpWkDistrictGprsQosBhDetail.province}&endWeek=${week}&endYear=${year}">${vRpWkDistrictGprsQosBhDetail.province}</a>&nbsp;
	    </display:column>
	    <display:column property="district" titleKey="DISTRICT" headerClass="hide" class="hide"/>
	    <display:column titleKey="DISTRICT" media="html">
	    	<a href="${pageContext.request.contextPath}/report/radio/district-gprs-qos/wk/details.htm?province=${vRpWkDistrictGprsQosBhDetail.province}&district=${vRpWkDistrictGprsQosBhDetail.district}&endWeek=${week}&endYear=${year}">${vRpWkDistrictGprsQosBhDetail.district}</a>&nbsp;
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

<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/highcharts.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/exporting.js"></script>
${dlTbfSucrChart}
${ulTbfSucrChart}

<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/calendar.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/calendar_en.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/calendar_setup.js"></script>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/calendar-blue.css" />

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
		$( '.checkAll' ).live( 'change', function() {
			$( '.cb-element' ).attr( 'checked', $( this ).is( ':checked' ) ? 'checked' : '' );
			$( this ).next().text( $( this ).is( ':checked' ) ? 'Uncheck All' : 'Check All' );
			if($( this ).is( ':checked' )){
				for (var i=8;i<=15;i++)
				{
					$('#vRpWkDistrictGprsQosBhDetail td:nth-child('+i+'),#vRpWkDistrictGprsQosBhDetail th:nth-child('+i+')').show();
				}
			} else {
				for (var i=8;i<=15;i++)
				{
					$('#vRpWkDistrictGprsQosBhDetail td:nth-child('+i+'),#vRpWkDistrictGprsQosBhDetail th:nth-child('+i+')').hide();
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
