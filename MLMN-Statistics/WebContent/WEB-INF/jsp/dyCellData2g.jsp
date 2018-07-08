<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<style type="text/css">    #doublescroll { overflow: auto; overflow-y: hidden; }    #doublescroll p { margin: 0; padding: 1em; white-space: nowrap; }</style>
<title>Báo cáo Data Cell ${cellid}</title>
<content tag="heading">DATA CELL ${cellid} DAILY REPORT</content>
<ul class="ui-tabs-nav">
  <li class=""><a href="${pageContext.request.contextPath}/report/radio/cell-data/hr/detail.htm?bscid=${bscid}&cellid=${cellid}&endDate=${endDate}"><span>Báo cáo giờ</span></a></li>
  <li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/report/radio/cell-data/dy/detail.htm?cellid=${cellid}&bscid=${bscid}&startDate=${startDate}&endDate=${endDate}"><span>Báo cáo ngày</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/radio/cell-data/wk/detail.htm?cellid=${cellid}&bscid=${bscid}"><span>Báo cáo tuần</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/radio/cell-data/mn/detail.htm?cellid=${cellid}&bscid=${bscid}"><span>Báo cáo tháng</span></a></li>
</ul>
	<div class="ui-tabs-panel">

	  <form method="get" action="detail.htm" name="frmSample" onSubmit="return ValidateForm()">
		<table width="100%" class="form">
			<tr>
			<td align="left">
			        BSC 
			        <select name="bscid" id="bscid" onchange="xl()">
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
			        <select name="cellid" id="cellid" onchange="xl()">
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
	            	&nbsp;&nbsp;<input type="submit" class="button" name="submit" id="submit" value="View Report"/>
	            </td>
	        </tr>		
		</table>
	  </form>
	<br/>
	
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
	<br/>
	<div class = "tableStandar">
		<display:table style = "width: 150%" name="${vRpDyCellData2gList}" id="vRpDyCell" requestURI="" pagesize="100" class="simple2" export="true" sort="list">
				<display:column property="day" format="{0,date,dd/MM/yyyy}" titleKey="DAY" headerClass="master-header-1" sortable="true"/>
				<display:column property="region" titleKey="TT" headerClass="master-header-1"  sortable="true"/>
	    		<display:column property="province" titleKey="PROVINCE"  headerClass="master-header-1" sortable="true" style = "width: 120px"/>
			    <display:column property="bscid" titleKey="BSC" headerClass="hide" class="hide"/> 
			    <display:column titleKey="BSC" media="html" headerClass="master-header-1" sortable="true">
			    	<a href="${pageContext.request.contextPath}/report/radio/bsc-data/dy/detail.htm?bscid=${vRpDyCell.bscid}&endDate=<fmt:formatDate pattern="dd/MM/yyyy" value="${vRpDyCell.day}"/>">${vRpDyCell.bscid}</a>
			    </display:column>
			    <display:column property="cellid" titleKey="CELL" headerClass="hide" class="hide"/>
			    <display:column titleKey="CELL" media="html"   sortable="true" headerClass="master-header-1 margin" class="margin">
			    	<a href="${pageContext.request.contextPath}/report/radio/cell-data/hr/detail.htm?bscid=${vRpDyCell.bscid}&cellid=${vRpDyCell.cellid}&endDate=<fmt:formatDate pattern="dd/MM/yyyy" value="${vRpDyCell.day}"/>">${vRpDyCell.cellid}</a>
			    </display:column>
			    <display:column property="pdchAllocated" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="PDCH_ALLOCATED"  sortable="true"/>
				<display:column property="pdchUsed" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="PDCH_USED"  sortable="true"/>
				<display:column property="packetChAllocAtt" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="PACKET_CH_ALLOC_ATT"  sortable="true"/>
				<display:column property="packetChAllocFail" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="PACKET_CH_ALLOC_FAIL"  sortable="true"/>
				<display:column property="packetChAllocSucr" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="PACKET_CH_ALLOC_SUCR" class="PACKET_CH_ALLOC_SUCR"  sortable="true"/>
				<display:column property="ulTbfReq" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="UL_TBF_REQ"  sortable="true"/>
				<display:column property="ulTbfFail" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="UL_TBF_FAIL"  sortable="true"/>
				<display:column property="ulTbfSucr" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="UL_TBF_SUCR" class="UL_TBF_SUCR"  sortable="true"/>
				<display:column property="dlTbfReq" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="DL_TBF_REQ"  sortable="true"/>
				<display:column property="dlTbfFail" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="DL_TBF_FAIL"  sortable="true"/>
				<display:column property="dlTbfSucr" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="DL_TBF_SUCR" class="DL_TBF_SUCR"  sortable="true"/>
				<display:column property="gprsUlData" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="GPRS_UL_DATA"  sortable="true"/>
				<display:column property="gprsDlData" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="GPRS_DL_DATA"  sortable="true"/>
				<display:column property="edgeUlData" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="EDGE_UL_DATA"  sortable="true"/>
				<display:column property="edgeDlData" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="EDGE_DL_DATA"  sortable="true"/>
				<display:column property="gprsUlDataThroughput" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="GPRS_UL_DATA_THROUGHPUT"  sortable="true"/>
				<display:column property="gprsDlDataThroughput" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="GPRS_DL_DATA_THROUGHPUT"  sortable="true"/>
				<display:column property="edgeUlDataThroughput" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="EDGE_UL_DATA_THROUGHPUT"  sortable="true"/>
				<display:column property="edgeDlDataThroughput" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="EDGE_DL_DATA_THROUGHPUT" sortable="true"/>
		</display:table>
	</div>
	<br/>
	<div id="tDrprChart" style="width: 1000px; margin: 1em auto"></div>
	<br/>
	<div id="sDrprChart" style="width: 1000px; margin: 1em auto"></div>
	<br/>
	<div id="cssrChart" style="width: 1000px; margin: 1em auto"></div>
	<br/>
</div>

<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/text_date.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/highcharts.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/exporting.js"></script>
${tDrprChart}
${sDrprChart}
${cssrChart}
<script type="text/javascript">
function xl(){
	var sub=document.getElementById("submit");
	sub.focus();
}
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
			})
		});
		
		${highlight}
	});
</script>
<script type="text/javascript">
    function DoubleScroll(element) {
        var scrollbar= document.createElement('div');
        scrollbar.appendChild(document.createElement('div'));
        scrollbar.style.overflow= 'auto';
        scrollbar.style.overflowY= 'hidden';
        scrollbar.firstChild.style.width= element.scrollWidth+'px';
        scrollbar.firstChild.style.paddingTop= '1px';
        scrollbar.firstChild.appendChild(document.createTextNode('\xA0'));
        scrollbar.onscroll= function() {
            element.scrollLeft= scrollbar.scrollLeft;
        };
        element.onscroll= function() {
            scrollbar.scrollLeft= element.scrollLeft;
        };
        element.parentNode.insertBefore(scrollbar, element);
    }

    DoubleScroll(document.getElementById('doublescroll'));
</script>