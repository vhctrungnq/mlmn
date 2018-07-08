<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<title>Danh sách Cell nghẽn TCH</title>
<content tag="heading">LIST CELL CONGESTION TCH DAILY BY PROVINCE</content>
<ul class="ui-tabs-nav">
	<li class=""><a href="${pageContext.request.contextPath}/report/radio/bad-cell-blk/by-cell/list.htm?bscid=${bscid}&cellid=${cellid}&startDate=${startDate}&endDate=${endDate}"><span>Cell nghẽn TCH</span></a></li>
	<li class=""><a href="${pageContext.request.contextPath}/report/radio/bad-cell-blk/by-bsc/list.htm?bscid=${bscid}&startDate=${startDate}&endDate=${endDate}"><span>Cell nghẽn TCH by Bsc</span></a></li>
	<li class=""><a href="${pageContext.request.contextPath}/report/radio/bad-cell-blk/by-district/list.htm?startDate=${startDate}&endDate=${endDate}"><span>Cell nghẽn TCH by District</span></a></li>
	<li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/report/radio/bad-cell-blk/by-province/list.htm?startDate=${startDate}&endDate=${endDate}"><span>Cell nghẽn TCH by Province</span></a></li>
	<li class=""><a href="${pageContext.request.contextPath}/report/radio/bad-cell-blk/by-region/list.htm?startDate=${startDate}&endDate=${endDate}"><span>Cell nghẽn TCH by Center</span></a></li>
</ul>
<div class="ui-tabs-panel">
	<span>Theo Ngày</span>
	<a href="${pageContext.request.contextPath}/report/radio/bad-cell-blk/by-province/wkList.htm?endWeek=${endWeek}&endYear=${endYear}"><span>Theo Tuần</span></a>
	<a href="${pageContext.request.contextPath}/report/radio/bad-cell-blk/by-province/mnList.htm?endMonth=${endMonth}&endYear=${endYear}"><span>Theo Tháng</span></a>
		<table width="100%" class="form">
			<tr>
			  <td align="left">
			  <form method="get" action="list.htm" name = "frmSample" onSubmit = "return ValidateForm()">
					Province <input name="province" id="province" value="${province}" size="10">
	                &nbsp;&nbsp;Từ Ngày <input value="${startDate}" name="startDate" id="startDate" size="10" maxlength="10">
	                &nbsp;&nbsp;Tới Ngày <input value="${endDate}" name="endDate" id="endDate" size="10" maxlength="10">
	                &nbsp;&nbsp;<input type="submit" class="button" name="submit" value="View Report"/>
	          </form>
	          </td>
	        </tr>		
		</table>
	<br/>
	<div  style="overflow: auto;">
		<display:table name="${dyBadCellList}" id="dyBadCell" requestURI="" pagesize="100" class="simple3" sort="external" defaultsort="1" export="true">
		    <display:column property ="day" format="{0,date,dd/MM/yyyy}" titleKey="DAY"  sortable="true"/>
		    <display:column property ="province" titleKey="PROVINCE"  sortable="true"/>
		    <display:column property="badCell" titleKey="NUMBER CELL BLOCKAGE TCH > 5%" sortable="true"/>
		    <display:column property="totalCell" titleKey="TOTAL CELL" sortable="true"/>
		    <display:column property="badCellR" titleKey="% CELL BLOCKAGE TCH > 5%" sortable="true"/>
		</display:table>
	</div>
</div>

<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/text_date.js"></script>

<script type="text/javascript">
	$(function() {
		$( "#startDate" ).datepicker({
			dateFormat: "dd/mm/yy",
			showOn: "button",
			buttonImage: "${pageContext.request.contextPath}/images/calendar.png",
			buttonImageOnly: true
		});
		$( "#endDate" ).datepicker({
			dateFormat: "dd/mm/yy",
			showOn: "button",
			buttonImage: "${pageContext.request.contextPath}/images/calendar.png",
			buttonImageOnly: true
		});
	});
</script>
