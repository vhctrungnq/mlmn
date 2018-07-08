<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<title>WEEKLY REPORT CELL ${cellid}</title>
<content tag="heading">WEEKLY REPORT CELL: ${cellid}</content>
<ul class="ui-tabs-nav">
  <li class=""><a href="${pageContext.request.contextPath}/report/radio/cell/hr/detail.htm?cellid=${cellid}&bscid=${bscid}"><span>Báo cáo giờ</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/radio/cell/dy/detail.htm?cellid=${cellid}&bscid=${bscid}"><span>Báo cáo ngày</span></a></li>
  <li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/report/radio/cell/wk/detail.htm?cellid=${cellid}&bscid=${bscid }&endWeek=${endWeek}&endYear=${endYear}"><span>Báo cáo tuần</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/radio/cell/mn/detail.htm?cellid=${cellid}&bscid=${bscid}"><span>Báo cáo tháng</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/radio/cell/dy/bh.htm?cellid=${cellid}&bscid=${bscid}"><span>Báo cáo BH ngày </span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/radio/cell/wk/bh.htm?cellid=${cellid}&bscid=${bscid}&endWeek=${endWeek}&endYear=${endYear}"><span>Báo cáo BH tuần</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/radio/cell/mn/bh.htm?cellid=${cellid}&bscid=${bscid}"><span>Báo cáo BH tháng</span></a></li>
</ul>
	<div class="ui-tabs-panel">

	  <form method="get" action="detail.htm">
		<table width="100%" class="form">
			<tr>
			<td align="left">
			        BSC 
			        <select name="bscid" id="bscid">
						<option value="">--Select BSC--</option>
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
				    &nbsp;&nbsp;CELL 
			        <select name="cellid" id="cellid">
						<option  value="">--Select Cell--</option>
				        <c:forEach var="cell" items="${cellList}">
			              <c:choose>
			                <c:when test="${cell.cellid == cellid}">
			                    <option value="${cell.cellid}" selected="selected">${cell.cellid}</option>
			                </c:when>
			                <c:otherwise>
			                    <option value="${cell.cellid}">${cell.cellid}</option>
			                </c:otherwise>
			              </c:choose>
					    </c:forEach>
					</select>
	            	&nbsp;Từ tuần <input value="${startWeek}" name="startWeek" id="startWeek" size="2" maxlength="2" onchange ="javascript:checkNumber(document.frmSample.startWeek);">
					<img alt="calendar" title="Click to choose the start week number" id="chooseStartWeek" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
	            	&nbsp;Năm <input value="${startYear}" name="startYear" id="startYear" size="4" maxlength="4" onchange ="javascript:checkNumber(document.frmSample.startYear);">
	            	&nbsp;Tới tuần <input value="${endWeek}" name="endWeek" id="endWeek" size="2" maxlength="2" onchange="javascript:checkNumber(document.frmSample.endWeek);">
					<img alt="calendar" title="Click to choose the end week number" id="chooseEndWeek" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
	            	&nbsp;Năm <input value="${endYear}" name="endYear" id="endYear" size="4" maxlength="4" onchange ="javascript:checkNumber(document.frmSample.endYear);">
	            	&nbsp;<input type="submit" class="button" name="submit" id="submit"value="View Report"/>
	            </td>
	        </tr>		
		</table>
	  </form>
	<table class="form">
	    <tr>
	    	<td>
	    		<b>Chọn chỉ số hiển thị: </b>
	    	</td>
	    </tr>
	    <tr>
			<td>${checkColumns}</td>
        </tr> 
</table>
	<div  style="overflow: auto;" class="tableStandar">
		<display:table  style = "width:150%" name="${vRpWkCellList}" id="vRpWkCell" requestURI="" pagesize="100"  export="true">
				<display:column property="week" titleKey="WEEK" headerClass="master-header-1" sortable="true"/>
			    <display:column property="year" titleKey="YEAR" headerClass="master-header-1" sortable="true"/>
			    <display:column titleKey="PROVINCE" media="html" style = "width: 120px" headerClass="master-header-1" sortable="true">
			    	<a href="${pageContext.request.contextPath}/report/radio/province/wk/detail.htm?province=${vRpWkCell.province}&endWeek=${vRpWkCell.week}&endYear=${vRpWkCell.year}">${vRpWkCell.province}</a>
			    </display:column>			    
			    <display:column property="province" titleKey="PROVINCE" headerClass="hide" class="hide"/>
			    <display:column property="bscid" titleKey="BSCID" headerClass="hide" class="hide"/>
			    <display:column titleKey="BSCID" media="html" headerClass="master-header-1" sortable="true">
			    	<a href="${pageContext.request.contextPath}/report/radio/bsc/wk/detail.htm?bscid=${vRpWkCell.bscid}&endWeek=${vRpWkCell.week}&endYear=${vRpWkCell.year}">${vRpWkCell.bscid}</a>
			    </display:column>
			    <display:column property="cellid" titleKey="CELLID" headerClass="margin master-header-1" class="margin" sortable="true"/>
			    <display:column property ="tTrafh" titleKey="T_TRAFH" class="H_TRAF margin" headerClass="master-header-2 margin" sortable="true"/>
			    <display:column property="sDef" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="S_DEF" headerClass="master-header-3" sortable="true"/>
			    <display:column property="sAtts" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="S_ATTS" headerClass="master-header-3" sortable="true"/>		    
			    <display:column property ="sBlkr" titleKey="S_BLKR" headerClass="master-header-3" class="S_BLKR" sortable="true"/>
			    <display:column property="sBlks" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="S_BLKS" headerClass="master-header-3" sortable="true"/>
			    <display:column property ="sSeizs" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="S_SEIZS" headerClass="master-header-3" sortable="true"/>
			    <display:column property="sDrpr" titleKey="S_DRPR"   headerClass="master-header-3" sortable="true"/> 	    
			    <display:column property="sDrps" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="S_DRPS" headerClass="master-header-3 margin" class="margin" sortable="true"/>	
			    <display:column property="dataload" titleKey="DATALOAD" sortable="true"/>
		</display:table>
	</div>
	<br/>
	<div id="tDrprChart" style="width: 1000px; margin: 1em auto"></div>
	<br/>
	<div id="sDrprChart" style="width: 1000px; margin: 1em auto"></div>
	<br/>
	<div id="cssrChart" style="width: 1000px; margin: 1em auto"></div>
	<br/>
	<div id="tBlkrChart" style="width: 1000px; margin: 1em auto"></div>
	<br/>
	<div id="sBlkrChart" style="width: 1000px; margin: 1em auto"></div>
</div>

<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/highcharts.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/exporting.js"></script>
${tDrprChart}
${sDrprChart}
${cssrChart}
${tBlkrChart}
${sBlkrChart}

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
		$("select#bscid").change(function(){
			$.getJSON("${pageContext.request.contextPath}/ajax/getCellOfBsc.htm",{bscid: $(this).val()}, function(j){
				var options = '<option  value="">--Select Cell--</option>';
				for (var i = 0; i < j.length; i++) {
					options += '<option value="' + j[i].cellid + '">' + j[i].cellid + '</option>';
				}
				$("#cellid").html(options);
				$('#cellid option:first').attr('selected', 'selected');
			});
		});
		
		${highlight}
	});
</script>
