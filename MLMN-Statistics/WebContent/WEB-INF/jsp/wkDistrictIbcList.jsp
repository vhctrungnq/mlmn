<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<title>Báo cáo District Ibc</title>
<content tag="heading">REPORT DISTRICT IBC</content>
<ul class="ui-tabs-nav">
	<li class=""><a href="${pageContext.request.contextPath}/report/radio/ibc/district/dy/list.htm"><span>Báo cáo ngày</span></a></li>
	<li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/report/radio/ibc/district/wk/list.htm"><span>Báo cáo tuần</span></a></li>
	<li class=""><a href="${pageContext.request.contextPath}/report/radio/ibc/district/mn/list.htm"><span>Báo cáo tháng</span></a></li>
</ul>
<div class="ui-tabs-panel">
	
		<table width="100%" class="form">
			<tr>
			    <td align="left">
			  <form method="get" action="list.htm">
					Trung tâm 
			  			<select name="region" id="region">
			              <c:choose>
			                <c:when test="${region == 'TT2'}">
								<option value=""> Tất cả </option>
								<option value="TT2" selected="selected"> TT2 </option>
			                    <option value="TT6"> TT6 </option>
			                </c:when>
			                <c:when test="${region == 'TT6'}">
								<option value=""> Tất cả </option>
								<option value="TT2"> TT2 </option>
			                    <option value="TT6" selected="selected"> TT6 </option>
			                </c:when>
			                <c:otherwise>
								<option value="" selected="selected"> Tất cả </option>
								<option value="TT2"> TT2 </option>
			                    <option value="TT6"> TT6 </option>
			                </c:otherwise>
			              </c:choose>
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
			        &nbsp; Từ tuần <input value="${startWeek}" name="startWeek" id="startWeek" size="2" maxlength="2" />
					<img alt="calendar" title="Click to choose the start week number" id="chooseStartWeek" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
					&nbsp; Năm <input value="${startYear}" name="startYear" id="startYear" size="4" maxlength="4" />
			        &nbsp; Tới tuần <input value="${endWeek}" name="endWeek" id="endWeek" size="2" maxlength="2" />
					<img alt="calendar" title="Click to choose the end week number" id="chooseEndWeek" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
					&nbsp; Năm <input value="${endYear}" name="endYear" id="endYear" size="4" maxlength="4" />
					&nbsp;&nbsp;<input type="submit" class="button" name="submit" value="View Report" />
	          </form>
	            </td>
	        </tr>		
		</table>
	<br/>
	
		<div  style="overflow: auto;">
			<display:table name="${vRpWkDistrictIbc}" id="vRpWkDistrictIbc" requestURI="" pagesize="100" class="simple2" export="true" sort="list">
			    <display:column property="week" titleKey="WEEK" sortable="true"/>
			    <display:column property="year" titleKey="YEAR" sortable="true"/>
			    <display:column property="region" titleKey="TT" sortable="true"/>
			    <display:column property="province" titleKey="PROVINCE" sortable="true" class="hide" headerClass="hide"/>
			    <display:column titleKey="PROVINCE" media="html" sortable="true" sortProperty="province">
			    	<a href="${pageContext.request.contextPath}/report/radio/ibc/province/wk/detail.htm?province=${vRpWkDistrictIbc.province}&endWeek=${vRpWkDistrictIbc.week}&endYear=${vRpWkDistrictIbc.year}">${vRpWkDistrictIbc.province}</a>
			    </display:column>
			    <display:column property="district" titleKey="DISTRICT" sortable="true" class="hide" headerClass="hide"/>
			    <display:column titleKey="DISTRICT" media="html" sortable="true" sortProperty="district">
			    	<a href="${pageContext.request.contextPath}/report/radio/ibc/district/wk/detail.htm?province=${vRpWkDistrictIbc.province}&district=${vRpWkDistrictIbc.district}&endWeek=${vRpWkDistrictIbc.week}&endYear=${vRpWkDistrictIbc.year}">${vRpWkDistrictIbc.district}</a>
			    </display:column>
			    <display:column property="sites" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="SITES" sortable="true"/>
			    <display:column property="cells" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="CELLS" sortable="true"/>
			    <display:column property="trxs" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="TRXS" sortable="true" class="margin" headerClass="margin"/>
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
			    <display:column property="inHoSucr" titleKey="IN_HO_SUCR" sortable="true"/>
			    <display:column property="ogHoSucr" titleKey="OG_HO_SUCR" sortable="true"/>
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
