<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<title>WEEKLY REPORT CELL: ${cellid}</title>
<content tag="heading">WEEKLY REPORT CELL: ${cellid}</content>
<ul class="ui-tabs-nav">
  <li class=""><a href="${pageContext.request.contextPath}/report/radio/cell/hr/detail.htm?cellid=${cellid}&bscid=${bscid}"><span>Báo cáo giờ</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/radio/cell/dy/detail.htm?cellid=${cellid}&bscid=${bscid}"><span>Báo cáo ngày</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/radio/cell/wk/detail.htm?cellid=${cellid}&bscid=${bscid}&endWeek=${endWeek}&endYear=${endYear}"><span>Báo cáo tuần</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/radio/cell/mn/detail.htm?cellid=${cellid}&bscid=${bscid}"><span>Báo cáo tháng</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/radio/cell/dy/bh.htm?cellid=${cellid}&bscid=${bscid}"><span>Báo cáo BH ngày</span></a></li>
  <li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/report/radio/cell/wk/bh.htm?cellid=${cellid}&bscid=${bscid}&endWeek=${endWeek}&endYear=${endYear}"><span>Báo cáo BH tuần</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/radio/cell/mn/bh.htm?cellid=${cellid}&bscid=${bscid}"><span>Báo cáo BH tháng</span></a></li>
</ul>
	<div class="ui-tabs-panel">

	  <form method="get" action="bh.htm">
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
	            	Từ tuần <input value="${startWeek}" name="startWeek" id="startWeek" size="2">
					<img alt="calendar" title="Click to choose the week number" id="chooseStartWeek" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
	            	&nbsp;&nbsp;Năm <input value="${startYear}" name="startYear" id="startYear" size="4" maxlength="4">
	            	&nbsp;Tới tuần <input value="${endWeek}" name="endWeek" id="endWeek" size="2">
					<img alt="calendar" title="Click to choose the week number" id="chooseEndWeek" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
	            	&nbsp;&nbsp;Năm <input value="${endYear}" name="endYear" id="endYear" size="4" maxlength="4">
	            	&nbsp;&nbsp;<input type="submit" class="button" name="submit" value="View Report"/>
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
	<div  style="overflow: auto;">
	<display:table style="width:150%" name="${vRpWkCellBhList}" id="vRpWkCellBh" requestURI="" pagesize="100" class="simple2" export="true">
		<display:column  sortable="true" property ="week" titleKey="WEEK" headerClass="master-header-1"/>
		<display:column  sortable="true" property ="year" titleKey="YEAR" headerClass="master-header-1"/>
	    <display:column  sortable="true" property="province" titleKey="PROVINCE" headerClass="master-header-1" style="width:120px" />
	    <display:column  sortable="true" property="bscid" titleKey="BSC" headerClass="master-header-1"/> 
	    <display:column  sortable="true" property="cellid" titleKey="CELL" headerClass="master-header-1" class="margin"/>
	    <display:column  sortable="true" property ="bhTDef" titleKey="T_DEF" headerClass="master-header-2"/>
	    <display:column  sortable="true" property="bhTAvail" titleKey="T_AVAIL" headerClass="master-header-2"/>
	    <display:column  sortable="true" property ="bhTAtts" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="T_ATTS" headerClass="master-header-2"/>
	    <display:column  sortable="true" property="bhTBlkr" titleKey="T_BLKR" headerClass="master-header-2"/>
	    <display:column  sortable="true" property="bhTBlks" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="T_BLKS" headerClass="master-header-2"/>
	    <display:column  sortable="true" property="bhTHoblkr" titleKey="T_HOBLKR" headerClass="master-header-2"/>
	    <display:column  sortable="true" property="bhTHoblks" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="T_HOBLKS" headerClass="master-header-2"/>
	    <display:column  sortable="true" property="bhCssr" titleKey="CSSR" class="CSSR" headerClass="master-header-2"/> 
	    <display:column  sortable="true" property="bhTDrpr" titleKey="T_DRPR" class="T_DRPR" headerClass="master-header-2"/>
	    <display:column  sortable="true" property ="bhTDrps" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="T_DRPS" headerClass="master-header-2"/>
	    <display:column  sortable="true" property ="bhTTraf" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="T_TRAF" headerClass="master-header-2"/>
	    <display:column  sortable="true" property ="bhTTrafh" titleKey="H_Traff" headerClass="master-header-2"/>
	    <display:column  sortable="true" property="bhTGos" titleKey="GoS (%)" headerClass="master-header-2 margin" class="GoS margin"/>
	    <display:column  sortable="true" property ="bhSDef" titleKey="S_DEF" headerClass="master-header-3"/>
	    <display:column  sortable="true" property="bhSAvail" titleKey="S_AVAIL" headerClass="master-header-3"/>
	    <display:column  sortable="true" property ="bhSAtts" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="S_ATTS" headerClass="master-header-3"/>
	    <display:column  sortable="true" property="bhSBlkr" titleKey="S_BLKR" headerClass="master-header-3"/>
	    <display:column  sortable="true" property ="bhSBlks" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="S_BLKS" headerClass="master-header-3"/>
	    <display:column  sortable="true" property ="bhSDrpr" titleKey="S_DRPR" headerClass="master-header-3"/>
	    <display:column  sortable="true" property ="bhSDrps" titleKey="S_DRPS" headerClass="master-header-3"/>
	</display:table>
	</div>
	<br/>
	<div id="container" style="width: 1000px; margin: 1em auto"></div>
</div>

${chart }

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
