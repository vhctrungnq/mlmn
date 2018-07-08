<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<title>location gprs qos list</title>
<content tag="heading">LIST REGION GPRS-QoS WEEK ${week}/${year}</content>
<ul class="ui-tabs-nav">
	<li class=""><a href="${pageContext.request.contextPath}/report/radio/location-gprs-qos/dy/list.htm"><span>Báo cáo ngày</span></a></li>
	<li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/report/radio/location-gprs-qos/wk/list.htm"><span>Báo cáo tuần</span></a></li>
	<li class=""><a href="${pageContext.request.contextPath}/report/radio/location-gprs-qos/mn/list.htm"><span>Báo cáo tháng</span></a></li>
</ul>
<div class="ui-tabs-panel">
	
		<table width="100%" class="form">
			<tr>
			    <td align="left">
			  <form method="get" action="list.htm">
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
	                Tuần <input value="${week}" name="week" id="week" size="2" maxlength="2"/>
					<img alt="calendar" title="Click to choose the week number" id="chooseWeek" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
	                Năm <input value="${year}" name="year" id="year" size="4" maxlength="4"/>
	                &nbsp;&nbsp;<input type="submit" class="button" name="submit" value="View Report"/>
	          </form>
	            </td>
	        </tr>		
		</table>
	<br/>
		<div  style="overflow: auto;">
<display:table name="${vRpWkLocationGprsQos}" id="vRpWkLocationGprsQos" requestURI="" pagesize="100" class="simple2" export="true">
			    <display:column property ="region" titleKey="TT" />
			    <display:column property ="location" titleKey="LOCATION" media="html">
			    	<a href="details.htm?location=${vRpWkLocationGprsQos.location}&endWeek=${week}&endYear=${year}">${vRpWkLocationGprsQos.location}</a>&nbsp;
			    </display:column> 
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
</div>

<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/calendar.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/calendar_en.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/calendar_setup.js"></script>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/calendar-blue.css" />

<script type="text/javascript">
    Calendar.setup({
        inputField		:	"week",	// id of the input field
        ifFormat		:	"%W",   	// format of the input field
        button			:   "chooseWeek",  	// trigger for the calendar (button ID)
        singleClick		:   false					// double-click mode
    });
</script>
