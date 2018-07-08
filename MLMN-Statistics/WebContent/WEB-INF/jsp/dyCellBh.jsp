<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<title>DAILY REPORT BH CELL</title>
<content tag="heading">DAILY REPORT BH CELL: ${cellid}</content>
<ul class="ui-tabs-nav">
  <li class=""><a href="${pageContext.request.contextPath}/report/radio/cell/hr/detail.htm?cellid=${cellid}&bscid=${bscid}&endDate=${endDate}"><span>Báo cáo giờ</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/radio/cell/dy/detail.htm?cellid=${cellid}&bscid=${bscid}&startDate=${startDate}&endDate=${endDate}"><span>Báo cáo ngày</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/radio/cell/wk/detail.htm?cellid=${cellid}&bscid=${bscid}"><span>Báo cáo tuần</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/radio/cell/mn/detail.htm?cellid=${cellid}&bscid=${bscid}"><span>Báo cáo tháng</span></a></li>
  <li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/report/radio/cell/dy/bh.htm?cellid=${cellid}&bscid=${bscid}&startDate=${startDate}&endDate=${endDate}"><span>Báo cáo BH ngày</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/radio/cell/wk/bh.htm?cellid=${cellid}&bscid=${bscid}"><span>Báo cáo BH tuần</span></a></li>
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
		           	Từ ngày <input value="${startDate}" name="startDate" id="startDate" size="10" maxlength="10">
		           	&nbsp;Tới ngày <input value="${endDate}" name="endDate" id="endDate" size="10" maxlength="10">
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
	<display:table style = "width:150%" name="${vRpDyCellBhList}" id="vRpDyCellBh" requestURI="" pagesize="100" class="simple2" export="true">
		<display:column property ="day" format="{0,date,dd/MM/yyyy}" titleKey="DAY" sortable="true" headerClass="master-header-1"/>
		<display:column titleKey="BH" sortable="true" sortProperty="bh" headerClass="master-header-1">
			${vRpDyCellBh.bh}:00
		</display:column>
	    <display:column property="province" titleKey="PROVINCE" headerClass="hide" class="hide"/>
   		<display:column titleKey="PROVINCE" media="html" sortable="true" sortProperty="province" style = "width: 120px" headerClass="master-header-1">
	    	<a href="${pageContext.request.contextPath}/report/radio/province/dy/bh.htm?province=${vRpDyCellBh.province}&endDate=<fmt:formatDate pattern="dd/MM/yyyy" value="${vRpDyCellBh.day}"/>">${vRpDyCellBh.province}</a>
	    </display:column> 
	    <display:column property="bscid" titleKey="BSCID" headerClass="hide" class="hide"/> 
	    <display:column titleKey="BSCID" media="html" sortable="true" sortProperty="bscid" headerClass="master-header-1">
	    	<a href="${pageContext.request.contextPath}/report/radio/bsc/dy/bh.htm?bscid=${vRpDyCellBh.bscid}&endDate=<fmt:formatDate pattern="dd/MM/yyyy" value="${vRpDyCellBh.day}"/>">${vRpDyCellBh.bscid}</a>
	    </display:column>
	    <display:column property="cellid" titleKey="CELLID" headerClass="hide" class="hide"/>
	    <display:column titleKey="CELLID" class="margin" media="html" sortable="true" sortProperty="cellid" headerClass="master-header-1">
	    	<a href="${pageContext.request.contextPath}/report/radio/cell/hr/detail.htm?bscid=${vRpDyCellBh.bscid}&cellid=${vRpDyCellBh.cellid}&endDate=<fmt:formatDate pattern="dd/MM/yyyy" value="${vRpDyCellBh.day}"/>">${vRpDyCellBh.cellid}</a>
	    </display:column>
	    <display:column property ="bhTDef" titleKey="T_DEF"  sortable="true" headerClass="master-header-2"/>
	    <display:column property="bhTAvail" titleKey="T_AVAIL"  sortable="true" headerClass="master-header-2"/>
	    <display:column property ="bhTAtts" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="T_ATTS"  sortable="true" headerClass="master-header-2"/>
	    <display:column property="bhTBlkr" titleKey="T_BLKR" sortable="true" headerClass="master-header-2"/>
	    <display:column property="bhTBlks" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="T_BLKS" sortable="true" headerClass="master-header-2"/>
	    <display:column property ="bhTHoblkr" titleKey="T_HOBLKR" headerClass="master-header-2" sortable="true"/>
	    <display:column property="bhTHoblks" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="T_HOBLKS" sortable="true" headerClass="master-header-2"/>
	    <display:column property="bhCssr" titleKey="CSSR" class="BH_CSSR" sortable="true" headerClass="master-header-2"/> 
	    <display:column property="bhTDrpr" titleKey="T_DRPR" class="BH_T_DRPR" sortable="true" headerClass="master-header-2"/>
	    <display:column property ="bhTDrps" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="T_DRPS"  sortable="true" headerClass="master-header-2"/>
	    <display:column property ="bhTTraf" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="T_TRAF"  sortable="true" headerClass="master-header-2"/>
	    <display:column property ="bhTTrafh" titleKey="T_TRAFH" sortable="true" headerClass="master-header-2"/>
	    <display:column property="bhTGos" titleKey="T_GoS"  sortable="true" headerClass="master-header-2" class="GoS margin"/>
	    <display:column property ="bhSDef" titleKey="S_DEF"  sortable="true" headerClass="master-header-3"/>
	    <display:column property="bhSAvail" titleKey="S_AVAIL"  sortable="true" headerClass="master-header-3"/>
	    <display:column property ="bhSAtts" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="S_ATTS"  sortable="true" headerClass="master-header-3"/>
	    <display:column property="bhSBlkr" titleKey="S_BLKR"  sortable="true" headerClass="master-header-3"/>
	    <display:column property ="bhSBlks" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="S_BLKS" sortable="true" headerClass="master-header-3"/>
	    <display:column property ="bhSDrpr" titleKey="S_DRPR"  sortable="true" headerClass="master-header-3"/>
	    <display:column property ="bhSDrps" titleKey="S_DRPS"  sortable="true" headerClass="master-header-3"/>
	</display:table>
	</div>
	<br/>
	<div id="container" style="width: 1000px; margin: 1em auto"></div>
</div>

${chart }

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
