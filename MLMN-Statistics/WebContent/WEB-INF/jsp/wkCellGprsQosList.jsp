<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<title>Báo cáo Cell GPRS-QoS</title>
<content tag="heading">REPORT CELL GPRS-QOS</content>
<ul class="ui-tabs-nav">
	<li class=""><a href="${pageContext.request.contextPath}/report/radio/cell-gprs-qos/dy/list.htm"><span>Báo cáo ngày</span></a></li>
	<li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/report/radio/cell-gprs-qos/wk/list.htm"><span>Báo cáo tuần</span></a></li>
	<li class=""><a href="${pageContext.request.contextPath}/report/radio/cell-gprs-qos/mn/list.htm"><span>Báo cáo tháng</span></a></li>
</ul>
<div class="ui-tabs-panel">
	
		<table width="100%" class="form">
			<tr>
			    <td align="left">
			  <form method="get" action="list.htm">
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
					&nbsp;&nbsp;<input type="submit" class="button" name="submit" value="View Report" />
	          </form>
	            </td>
	        </tr>		
		</table>
	<br/>
	
			<div  style="overflow: auto;">
<display:table name="${vRpWkCellGprsQos}" id="vRpWkCellGprsQos" requestURI="" pagesize="100" class="simple2" export="true" sort="list">
			    <display:column property="week" titleKey="WEEK" sortable="true" sortName="week"/>
			    <display:column property="year" titleKey="YEAR" sortable="true" sortName="year"/>
			    <display:column property="region" titleKey="TT" sortable="true" sortName="region"/>
			    <display:column property="province" titleKey="PROVINCE" sortable="true" sortName="province"/> 
			    <display:column property="bscid" titleKey="BSC" sortable="true" sortName="bscid"/> 
			    <display:column property="cellid" titleKey="CELL" headerClass="hide" class="hide"/>
			    <display:column titleKey="CELL" media="html" sortable="true" sortProperty="cellid">
			    	<a href="${pageContext.request.contextPath}/report/radio/cell-gprs-qos/wk/detail.htm?cellid=${vRpWkCellGprsQos.cellid}&bscid=${vRpWkCellGprsQos.bscid}&endWeek=${week}&endYear=${year}">${vRpWkCellGprsQos.cellid}</a>&nbsp;
			    </display:column> 
			    <display:column property="dlTbfReq" titleKey="DL_TBF_REQ" sortable="true" sortName="DL_TBF_REQ"/>
			    <display:column property ="dlTbfSucr" titleKey="DL_TBF_SUCR" sortable="true" sortName="DL_TBF_SUCR"/>
			    <display:column property="ulTbfReq" titleKey="UL_TBF_REQ" sortable="true" sortName="UL_TBF_REQ"/>
			    <display:column property="ulTbfSucr" titleKey="UL_TBF_SUCR" sortable="true" sortName="UL_TBF_SUCR"/>
			    <display:column property ="gdlTraf" titleKey="GDL_TRAF" sortable="true" sortName="GDL_TRAF"/>
			    <display:column property="gulTraf" titleKey="GUL_TRAF" sortable="true" sortName="GUL_TRAF"/>
			    <display:column property ="edlTraf" titleKey="EDL_TRAF" sortable="true" sortName="EDL_TRAF"/>
			    <display:column property="eulTraf" titleKey="EUL_TRAF" sortable="true" sortName="EUL_TRAF"/>
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
</script>
