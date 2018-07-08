<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<title>Danh sách Cell nghẽn TCH</title>
<content tag="heading">LIST CELL TRFFIC &lt; 10 ERLANG MONTHLY OF BSC</content>
<ul class="ui-tabs-nav">
	<li class=""><a href="${pageContext.request.contextPath}/report/radio/bad-cell-traff/by-cell/list.htm?startDate=${startDate}&endDate=${endDate}"><span>Cell nghẽn TCH</span></a></li>
	<li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/report/radio/bad-cell-traff/by-bsc/list.htm?startDate=${startDate}&endDate=${endDate}"><span>Cell lưu lượng &lt; 10 Erlang của Bsc</span></a></li>
	<li class=""><a href="${pageContext.request.contextPath}/report/radio/bad-cell-traff/by-district/list.htm?startDate=${startDate}&endDate=${endDate}"><span>Cell lưu lượng &lt; 10 Erlang của District</span></a></li>
	<li class=""><a href="${pageContext.request.contextPath}/report/radio/bad-cell-traff/by-province/list.htm?startDate=${startDate}&endDate=${endDate}"><span>Cell lưu lượng &lt; 10 Erlang của Province</span></a></li>
	<li class=""><a href="${pageContext.request.contextPath}/report/radio/bad-cell-traff/by-region/list.htm?startDate=${startDate}&endDate=${endDate}"><span>Cell lưu lượng &lt; 10 Erlang của TT</span></a></li>
</ul>
<div class="ui-tabs-panel">
	<a href="${pageContext.request.contextPath}/report/radio/bad-cell-traff/by-bsc/list.htm?startDate=${startDate}&endDate=${endDate}"><span>Theo Ngày</span></a>
	<a href="${pageContext.request.contextPath}/report/radio/bad-cell-traff/by-bsc/wkList.htm?endWeek=${endWeek}&endYear=${endYear}"><span>Theo Tuần</span></a>
	<span>Theo Tháng</span>
		<table width="100%" class="form">
			<tr>
			  <td align="left">
			 <form method="get" action="mnList.htm">
					BSC <input name="bscid" id="bscid" value="${bscid}" size="10">
	            	Từ tháng <input value="${startMonth}" name="startMonth" id="startMonth" size="2" maxlength="2">
	            	&nbsp;&nbsp;Năm <input value="${startYear}" name="startYear" id="startYear" size="4" maxlength="4">
	            	&nbsp;Tới tháng <input value="${endMonth}" name="endMonth" id="endMonth" size="2" maxlength="2">
	            	&nbsp;&nbsp;Năm <input value="${endYear}" name="endYear" id="endYear" size="4" maxlength="4">
	            	&nbsp;&nbsp;<input type="submit" class="button" name="submit" value="View Report"/>
	          </form>
	          </td>
	        </tr>		
		</table>
	<br/>
	<div  style="overflow: auto;">
		<display:table name="${mnBadCellList}" id="mnBadCell" requestURI="" pagesize="100" class="simple3" export="true">
		    <display:column property ="month" titleKey="MONTH"  sortable="true"/>
		    <display:column property ="year" titleKey="YEAR"  sortable="true"/>
		    <display:column property ="bscid" titleKey="BSCID"  sortable="true"/>
		    <display:column property="badCellR" titleKey="% Cell lưu lượng < 10 Erlang" sortable="true"/>
		</display:table>
	</div>
</div>