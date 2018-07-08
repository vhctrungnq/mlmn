<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<title>location gprs qos weekly report</title>
<content tag="heading">GPRS-QOS REGION WEEKLY REPORT ${location}</content>
<ul class="ui-tabs-nav">
  <li class=""><a href="#"><span>Báo cáo giờ</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/radio/location-gprs-qos/dy/details.htm?location=${location}"><span>Báo cáo ngày</span></a></li>
  <li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/report/radio/location-gprs-qos/wk/details.htm?location=${location}&endWeek=${endWeek}&endYear=${endYear}"><span>Báo cáo tuần</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/radio/location-gprs-qos/mn/details.htm?location=${location}"><span>Báo cáo tháng</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/radio/location-gprs-qos/dy/bhDetails.htm?location=${location}"><span>Báo cáo BH ngày</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/radio/location-gprs-qos/wk/bhDetails.htm?location=${location}&endWeek=${endWeek}&endYear=${endYear}"><span>Báo cáo BH tuần</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/radio/location-gprs-qos/mn/bhDetails.htm?location=${location}"><span>Báo cáo BH tháng</span></a></li>
</ul>
<div class="ui-tabs-panel">
	  <form method="get" action="details.htm">
		<table width="100%" class="form">
			<tr>
				<td align="left">
					Trung tâm 
			  			<select name="region" id="region">
								<option value=""> Tất cả </option>
								<option value="TT2"> TT2 </option>
			                    <option value="TT6"> TT6 </option>
			              </select>
					LOCATION 
			        <select name="location">
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
					String[] locationGprsQosArray = {"DL_TBF_REQ","DL_TBF_SUCR","UL_TBF_REQ","UL_TBF_SUCR","GDL_TRAF","GUL_TRAF","EDL_TRAF","EUL_TRAF","DATALOAD"};
					String[]  locationGprsQosNameArray = {"dlTbfReq","dlTbfSucr","ulTbfReq","ulTbfSucr","gdlTraf","gulTraf","edlTraf","eulTraf","dataload"};
					int i;
					for (i = 0; i < locationGprsQosArray.length; i++) {
				%>
		        <input type="checkbox" class="cb-element" name="<%= locationGprsQosNameArray[i].toString()%>" id="<%= locationGprsQosNameArray[i].toString()%>" checked="checked"/> <%= locationGprsQosArray[i].toString()%>
		        <% } %>
            </td>
        </tr>		
	</table>
	<br/>
   	<div  style="overflow: auto;">
<display:table name="${vRpWkLocationGprsQosDetails}" id="vRpWkLocationGprsQosDetail" requestURI="" pagesize="100" class="simple2" export="true">
		<display:column property="region" titleKey="TT"/>
	    <display:column property ="week" titleKey="WEEK" />
	    <display:column property ="year" titleKey="YEAR" />
	    <display:column property ="location" titleKey="LOCATION"/> 
	    <display:column property ="dlTbfReq" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="DL_TBF_REQ" />
	    <display:column property ="dlTbfSucr" titleKey="DL_TBF_SUCR" />
	    <display:column property ="ulTbfReq" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="UL_TBF_REQ" />
	    <display:column property ="ulTbfSucr" titleKey="UL_TBF_SUCR"/>
	    <display:column property ="gdlTraf" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="GDL_TRAF" />
	    <display:column property ="gulTraf" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="GUL_TRAF" />
	    <display:column property ="edlTraf" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="EDL_TRAF" />
	    <display:column property ="eulTraf" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="EUL_TRAF" />
	    <display:column property="dataload" titleKey="DATALOAD" />
	</display:table>
</div>
	
	<div id="container" style="width: 1000px; margin: 1em auto;"></div>
</div>

${chart}

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
				for (var i=6;i<=14;i++)
				{
					$('#vRpWkLocationGprsQosDetail td:nth-child('+i+'),#vRpWkLocationGprsQosDetail th:nth-child('+i+')').show();
				}
			} else {
				for (var i=6;i<=14;i++)
				{
					$('#vRpWkLocationGprsQosDetail td:nth-child('+i+'),#vRpWkLocationGprsQosDetail th:nth-child('+i+')').hide();
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
