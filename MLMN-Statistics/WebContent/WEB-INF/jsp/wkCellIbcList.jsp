<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<title>Báo cáo Trạm Ibc</title>
<content tag="heading">Báo cáo Trạm IBC</content>
<ul class="ui-tabs-nav">
	<!--li class=""><a href="${pageContext.request.contextPath}/report/radio/cell/hr/list.htm"><span>Báo cáo giờ</span></a></li-->
	<li class=""><a href="${pageContext.request.contextPath}/report/radio/ibc/cell/dy/list.htm"><span>Báo cáo ngày</span></a></li>
	<li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/report/radio/ibc/cell/wk/list.htm"><span>Báo cáo tuần</span></a></li>
	<li class=""><a href="${pageContext.request.contextPath}/report/radio/ibc/cell/mn/list.htm"><span>Báo cáo tháng</span></a></li>
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
			  		&nbsp;PROVINCE 
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
			        
			        &nbsp;BSC 
			        <select name="bscid">
			        	<option value="">Tất cả</option>
				        <c:forEach var="bsc" items="${bscList}">
			              <c:choose>
			                <c:when test="${bsc.bscid == bscid}">
			                    <option value="${bsc.bscid}" selected="selected">${bsc.bscid}</option>
			                </c:when>
			                <c:otherwise>
			                    <option value="${bsc.bscid}">${bsc.bscid}</option>
			                </c:otherwise>
			              </c:choose>
					    </c:forEach>
				    </select>
			        &nbsp;CELL <input value="${cellid}" name="cellid" id="cellid" size="10">
	            	&nbsp;Từ tuần <input value="${startWeek}" name="startWeek" id="startWeek" size="2">
					<img alt="calendar" title="Click to choose the start week number" id="chooseStartWeek" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
	            	&nbsp;Năm <input value="${startYear}" name="startYear" id="startYear" size="4" maxlength="4">
	            	&nbsp;Tới tuần <input value="${endWeek}" name="endWeek" id="endWeek" size="2">
					<img alt="calendar" title="Click to choose the end week number" id="chooseEndWeek" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
	            	&nbsp;Năm <input value="${endYear}" name="endYear" id="endYear" size="4" maxlength="4">
					&nbsp;<input type="submit" class="button" name="submit" value="View Report" />
	          </form>
	            </td>
	        </tr>		
		</table>
	<br/>
	
		<div  style="overflow: auto;">
			<display:table name="${vRpWkCellIbc}" id="vRpWkCellIbc" requestURI="" pagesize="100" class="simple2" export="true" sort="list">
				<display:column property="week" titleKey="WEEK" sortable="true"/>
			    <display:column property="year" titleKey="YEAR" sortable="true"/>
			    <display:column property="region" titleKey="TT" sortable="true"/>
			    <display:column titleKey="PROVINCE" media="html" sortable="true" sortProperty="province">
			    	<a href="${pageContext.request.contextPath}/report/radio/ibc/province/wk/detail.htm?province=${vRpWkCellIbc.province}&endWeek=${vRpWkCell.week}&endYear=${vRpWkCell.year}">${vRpWkCellIbc.province}</a>
			    </display:column>			    
			    <display:column property="province" titleKey="PROVINCE" headerClass="hide" class="hide"/>
			    <display:column property="bscid" titleKey="BSC" headerClass="hide" class="hide"/>
			    <display:column titleKey="BSC" media="html" sortable="true" sortProperty="bscid">
			    	<a href="${pageContext.request.contextPath}/report/radio/ibc/bsc/wk/detail.htm?bscid=${vRpWkCellIbc.bscid}&endWeek=${vRpWkCell.week}&endYear=${vRpWkCell.year}">${vRpWkCellIbc.bscid}</a>
			    </display:column>
			    <display:column property="cellid" titleKey="CELL" headerClass="hide" class="hide"/>
			    <display:column titleKey="CELL" media="html"  headerClass="margin" class="margin" sortable="true" sortProperty="cellid">
			    	<a href="${pageContext.request.contextPath}/report/radio/cell/wk/detail.htm?cellid=${vRpWkCellIbc.cellid}&bscid=${vRpWkCellIbc.bscid}&endWeek=${vRpWkCell.week}&endYear=${vRpWkCell.year}">${vRpWkCellIbc.cellid}</a>
			    </display:column>
			    <display:column property ="tDef" titleKey="T_DEF" sortable="true"/>
			    <display:column property ="tAvail" class="T_AVAIL" titleKey="T_AVAIL" sortable="true"/>
			    <display:column property="tAtts" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="T_ATTS" sortable="true"/>
			    <display:column property="tBlkr" titleKey="T_BLKR" class="T_BLKR" sortable="true"/>
			    <display:column property="tHoblkr" titleKey="T_HOBLKR" class="T_HOBLKR" sortable="true"/>
			    <display:column property="cssr" titleKey="CSSR_CELL" class="CSSR_CELL" sortable="true"/> 
			    <display:column property="tDrpr" titleKey="T_DRPR" class="T_DRPR" sortable="true"/>
			    <display:column property="tTraf" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="T_TRAF" class="T_TRAF" sortable="true"/>
			    <display:column property="tTrafh" titleKey="T_TRAFH" class="H_TRAF margin" sortable="true" headerClass="margin"/>
			    <display:column property="sAtts" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="S_ATTS" sortable="true"/>
			    <display:column property="sBlkr" titleKey="S_BLKR" class="S_BLKR" sortable="true"/>
			    <display:column property ="sDrpr" titleKey="S_DRPR" class="S_DRPR" sortable="true"/>
				<display:column property="downtime" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="DOWNTIME" class="DOWNTIME margin" headerClass="margin" sortable="true"/>
			    <display:column property ="dataload" titleKey="DATALOAD" sortable="true"/>
			    <display:column property ="defAvail" titleKey="DEF_AVAIL" headerClass="hide" class="hide DEF_AVAIL" media="html"/>
			    <display:column property ="trafAvail" titleKey="TRAF_AVAIL" headerClass="hide" class="hide TRAF_AVAIL" media="html"/>			    
			    <display:column property ="trafhTraf" titleKey="TRAFH_TRAF" headerClass="hide" class="hide TRAFH_TRAF" media="html"/>
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
