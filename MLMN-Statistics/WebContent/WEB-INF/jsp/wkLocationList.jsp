<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<title>location list</title>
<content tag="heading">Danh sách Region QoS tuần ${week}</content>
<ul class="ui-tabs-nav">
	<li class=""><a href="${pageContext.request.contextPath}/report/radio/location/dy/list.htm"><span>Báo cáo ngày</span></a></li>
	<li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/report/radio/location/wk/list.htm"><span>Báo cáo tuần</span></a></li>
	<li class=""><a href="${pageContext.request.contextPath}/report/radio/location/mn/list.htm"><span>Báo cáo tháng</span></a></li>
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
			        &nbsp;&nbsp; Từ tuần <input value="${startWeek}" name="startWeek" id="startWeek" size="2" maxlength="2" />
					<img alt="calendar" title="Click to choose the start week number" id="chooseStartWeek" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
					&nbsp;&nbsp; Năm <input value="${startYear}" name="startYear" id="startYear" size="4" maxlength="4" />
			        &nbsp;&nbsp; Tới tuần <input value="${endWeek}" name="endWeek" id="endWeek" size="2" maxlength="2" />
					<img alt="calendar" title="Click to choose the end week number" id="chooseEndWeek" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
					&nbsp;&nbsp; Năm <input value="${endYear}" name="endYear" id="endYear" size="4" maxlength="4" />
					&nbsp;&nbsp;<input type="submit" class="button" name="submit" value="View Report" />
	          </form>
	            </td>
	        </tr>		
		</table>
	<br/>
	<div  style="overflow: auto;">
	<display:table name="${vRpWkLocation}" id="vRpWkLocation" requestURI="" pagesize="100" class="simple2" export="true" sort="list">
		<display:column property="region" titleKey="TT" sortable="true"/>
	    <display:column property="week" titleKey="WEEK" sortable="true"/>
	    <display:column property="location" titleKey="REGION" media="html" sortable="true">
	    	<a href="${pageContext.request.contextPath}/report/radio/location/wk/detail.htm?location=${vRpWkLocation.location}&startWeek=${startWeek}&startYear=${startYear}&endWeek=${endWeek}&endYear=${endYear}">${vRpWkLocation.location}</a>&nbsp;
	    </display:column>
	    <display:column property="sites" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="SITES" sortable="true"/>
	    <display:column property="cells" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="CELLS" sortable="true"/>
	    <display:column property="trxs" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="TRXS" class="margin" headerClass="margin" sortable="true"/>
	    <display:column property="tTraf" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="Traffic (Erl)"  sortable="true"/>
	    <display:column property="haftratePercent" titleKey="Half Rate (%)"  sortable="true"/>
	    <display:column property="cssr" titleKey="CSSR"  sortable="true"/>
	    <display:column property="tAsr" titleKey="T_ASR"  sortable="true"/>
	    <display:column property="tDrpr" titleKey="T_DRPR" class="T_DRPR" sortable="true"/>
	    <display:column property="tBlkr" titleKey="T_BLKR" sortable="true" class="T_BLKR"/>
	    <display:column property="tHoblkr" titleKey="T_HOBLKR"  class="margin" headerClass="margin" sortable="true"/>
		<display:column property="sSsr" titleKey="SSR (%)"  sortable="true"/>
	    <display:column property="sDrpr" titleKey="S_DRPR" class="S_DRPR" sortable="true"/>
	    <display:column property="sBlkr" titleKey="S_BLKR"  class="margin" headerClass="margin" sortable="true"/>
	    <display:column property="inHoSucr" titleKey="IN_HO_SUCR" sortable="true"/>
	    <display:column property="ogHoSucr" titleKey="OG_HO_SUCR" class="margin" headerClass="margin" sortable="true"/>
	    <display:column property="dataload" titleKey="DATALOAD"  sortable="true"/>
	</display:table>
</div>
</div>

<script type="text/javascript">
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
		${highlight}
	});
</script>
