<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<title>BSC DATA 2G QUARTER REPORT</title>
<content tag="heading">BSC DATA 2G QUARTER REPORT</content>
	<ul class="ui-tabs-nav">
		<li class=""><a href="${pageContext.request.contextPath}/report/radio/bsc-data/dy/list.htm"><span>Báo cáo ngày</span></a></li>
		<li class=""><a href="${pageContext.request.contextPath}/report/radio/bsc-data/wk/list.htm"><span>Báo cáo tuần</span></a></li>
		<li class=""><a href="${pageContext.request.contextPath}/report/radio/bsc-data/mn/list.htm"><span>Báo cáo tháng</span></a></li>
		<li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/report/radio/bsc-data/qr/list.htm"><span>Báo cáo quý</span></a></li>
		<li class=""><a href="${pageContext.request.contextPath}/report/radio/bsc-data/yr/list.htm"><span>Báo cáo năm</span></a></li>	
	</ul>
<div class="ui-tabs-panel">
		<table width="100%" class="form">
			<tr>
			  <td align="left">
			  <form method="get" action="list.htm">
					<fmt:message key="option.cellgprscs.region"/>
		  			<select name="region">
			        	<option value="">Tất cả</option>
				        <c:forEach var="item" items="${regionList}">
			              <c:choose>
			                <c:when test="${item.region == region}">
			                    <option value="${item.region}" selected="selected">${item.region}</option>
			                </c:when>
			                <c:otherwise>
			                    <option value="${item.region}">${item.region}</option>
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
			        
			        &nbsp;Từ quý <input value="${startQuarter}" name="startQuarter" id="startQuarter" size="1" maxlength="1">
	            	&nbsp;Năm <input value="${startYear}" name="startYear" id="startYear" size="4" maxlength="4">
	            	&nbsp;Tới quý <input value="${endQuarter}" name="endQuarter" id="endQuarter" size="1" maxlength="1">
	            	&nbsp;Năm <input value="${endYear}" name="endYear" id="endYear" size="4" maxlength="4">
					&nbsp;<input type="submit" class="button" name="submit" value="View Report" />
	          </form>
	            </td>
	        </tr>		
		</table>
	<br/>
	
	<div  style="overflow: auto;" class="tableStandar">
		<display:table name="${vRpQrBscData2gList}" style = "width: 150%" id="item" requestURI="" pagesize="100" export="false" sort="external" defaultsort="1" >
				<display:column property="quarter" titleKey="QUARTER" sortable="true" headerClass="master-header-1"/>
				<display:column property="year" titleKey="YEAR" sortable="true" headerClass="master-header-1"/>
			    <display:column property="region" titleKey="REGION" sortable="true" headerClass="master-header-1"/>
			    <display:column property="province" titleKey="PROVINCE" sortable="true" style="width:120px" headerClass="master-header-1"/>
			    <display:column property="bscid" titleKey="BSC" sortable="true" headerClass="master-header-1"/>
			   <%--  <display:column titleKey="BSC" media="html" sortProperty="bscid" sortable="true" headerClass="master-header-1">
			    	<a href="${pageContext.request.contextPath}/report/radio/bsc-data/mn/detail.htm?bscid=${item.bscid}&endMonth=${item.month}&endYear=${item.year}">${item.bscid}</a>
			    </display:column> --%>
			    
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
	    
	    
	    <display:setProperty name="export.csv.include_header" value="true" />
		<display:setProperty name="export.excel.include_header" value="true" />
		<display:setProperty name="export.xml.include_header" value="true" />
		<display:setProperty name="export.xml.filename" value="${exportFileName}.xml" />
		<display:setProperty name="export.csv.filename" value="${exportFileName}.csv" />
		<display:setProperty name="export.excel.filename" value="${exportFileName}.xls" />
	</display:table>
</div>
</div>

<script type="text/javascript">
	$(function() {
		var cacheCell = {}, lastXhrCell;
		$("#cellid")
				.autocomplete(
						{
							minLength : 3,
							source : function(request, response) {
								var term = request.term;
								if (term in cacheCell) {
									response(cacheCell[term]);
									return;
								}

								lastXhrCell = $
										.getJSON(
												"${pageContext.request.contextPath}/ajax/getCell.htm",
												request, function(data, status,
														xhr) {
													cacheCell[term] = data;
													if (xhr === lastXhrCell) {
														response(data);
													}
												});
							}
						});
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

