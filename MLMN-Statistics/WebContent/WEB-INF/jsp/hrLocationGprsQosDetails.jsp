<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<title>location gprs qos hourly report</title>
<content tag="heading">GPRS-QOS LOCATION ${location} REPORT</content>
<ul class="ui-tabs-nav">
  <li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/report/radio/location-gprs-qos/hr/details.htm?location=${location}&endDate=${endDate}"><span>Báo cáo giờ</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/radio/location-gprs-qos/dy/details.htm?location=${location}&endDate=${endDate}"><span>Báo cáo ngày</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/radio/location-gprs-qos/wk/details.htm?location=${location}"><span>Báo cáo tuần</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/radio/location-gprs-qos/mn/details.htm?location=${location}"><span>Báo cáo tháng</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/radio/location-gprs-qos/dy/bhDetails.htm?location=${location}&endDate=${endDate}"><span>Báo cáo BH ngày</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/radio/location-gprs-qos/wk/bhDetails.htm?location=${location}"><span>Báo cáo BH tuần</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/radio/location-gprs-qos/mn/bhDetails.htm?location=${location}"><span>Báo cáo BH tháng</span></a></li>
</ul>
	<div class="ui-tabs-panel">

	  <form method="get" action="details.htm" name = "frmSample" onSubmit = "return ValidateForm()">
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
	            	&nbsp;Từ <select name="startHour" id="startHour"onchange="xl()">
	            				<c:forEach var="hour" items="${hourList}">
						              <c:choose>
						                <c:when test="${hour == startHour}">
						                    <option value="${hour}" selected="selected">${hour}</option>
						                </c:when>
						                <c:otherwise>
						                    <option value="${hour}">${hour}</option>
						                </c:otherwise>
						              </c:choose>
						    </c:forEach>
			               	 </select>&nbsp; giờ
	                &nbsp;<input value="${startDate}" name="startDate" id="startDate" size="10" maxlength="10"/>
	                &nbsp;Đến <select name="endHour" id="endHour" onchange="xl()">
	            				<c:forEach var="hour" items="${hourList}">
						              <c:choose>
						                <c:when test="${hour == endHour}">
						                    <option value="${hour}" selected="selected">${hour}</option>
						                </c:when>
						                <c:otherwise>
						                    <option value="${hour}">${hour}</option>
						                </c:otherwise>
						              </c:choose>
						    </c:forEach>
			               	 </select>&nbsp;  giờ
	                &nbsp;<input value="${endDate}" name="endDate" id="endDate" size="10" maxlength="10"/>
	                &nbsp;&nbsp;<input type="submit" class="button" name="submit" id="submit" value="View Report"/>
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
					String[] locationGprsQosArray = {"DL_TBF_REQ","DL_TBF_SUCR","UL_TBF_REQ","UL_TBF_SUCR","GDL_TRAF","GUL_TRAF","EDL_TRAF","EUL_TRAF"};
					String[]  locationGprsQosNameArray = {"dlTbfReq","dlTbfSucr","ulTbfReq","ulTbfSucr","gdlTraf","gulTraf","edlTraf","eulTraf"};
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
<display:table name="${vRpHrLocationGprsQosDetails}" id="vRpHrLocationGprsQosDetail" requestURI="" pagesize="100" class="simple2" export="true">
				<display:column property="region" titleKey="TT"/>
	    <display:column property ="hour" titleKey="HOUR" />
	    <display:column property="day" format="{0,date,dd/MM/yyyy}" titleKey="DAY"/>
	    <display:column property="location" titleKey="LOCATION"/> 
	    <display:column property ="dlTbfReq" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="DL_TBF_REQ" />
	    <display:column property ="dlTbfSucr" titleKey="DL_TBF_SUCR" />
	    <display:column property ="ulTbfReq" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="UL_TBF_REQ" />
	    <display:column property ="ulTbfSucr" titleKey="UL_TBF_SUCR"/>
	    <display:column property ="gdlTraf" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="GDL_TRAF" />
	    <display:column property ="gulTraf" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="GUL_TRAF" />
	    <display:column property ="edlTraf" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="EDL_TRAF" />
	    <display:column property ="eulTraf" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="EUL_TRAF" />
	</display:table>
</div>
	
	<div id="container" style="width: 1000px; margin: 1em auto;"></div>
</div>

${chart}

<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/text_date.js"></script>
<script type="text/javascript">
function xl(){
	var sub= document.getElementById("submit");
	sub.focus();
}
	$(function() {
		$( "#day" ).datepicker({
			dateFormat: "dd/mm/yy",
			showOn: "button",
			buttonImage: "${pageContext.request.contextPath}/images/calendar.png",
			buttonImageOnly: true
		});
		
		$( '.checkAll' ).live( 'change', function() {
			$( '.cb-element' ).attr( 'checked', $( this ).is( ':checked' ) ? 'checked' : '' );
			$( this ).next().text( $( this ).is( ':checked' ) ? 'Uncheck All' : 'Check All' );
			if($( this ).is( ':checked' )){
				for (var i=5;i<=12;i++)
				{
					$('#vRpHrLocationGprsQosDetail td:nth-child('+i+'),#vRpHrLocationGprsQosDetail th:nth-child('+i+')').show();
				}
			} else {
				for (var i=5;i<=12;i++)
				{
					$('#vRpHrLocationGprsQosDetail td:nth-child('+i+'),#vRpHrLocationGprsQosDetail th:nth-child('+i+')').hide();
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
