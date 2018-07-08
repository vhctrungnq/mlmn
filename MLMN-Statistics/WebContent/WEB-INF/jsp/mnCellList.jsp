<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<style>
	#doublescroll { overflow: auto; overflow-y: hidden; }
	#doublescroll p { margin: 0; padding: 1em; white-space: nowrap; }
</style>

<title>CELL QOS MONTHLY REPORT</title>
<content tag="heading">CELL QOS MONTHLY REPORT</content>
<ul class="ui-tabs-nav">
	<!--li class=""><a href="${pageContext.request.contextPath}/report/radio/cell/hr/list.htm"><span>Báo cáo giờ</span></a></li-->
	<li class=""><a href="${pageContext.request.contextPath}/report/radio/cell/dy/list.htm"><span>Báo cáo ngày</span></a></li>
	<li class=""><a href="${pageContext.request.contextPath}/report/radio/cell/wk/list.htm"><span>Báo cáo tuần</span></a></li>
	<li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/report/radio/cell/mn/list.htm"><span>Báo cáo tháng</span></a></li>
	<li class=""><a href="${pageContext.request.contextPath}/report/radio/cell/qr/list.htm"><span>Báo cáo quý</span></a></li>
	<li class=""><a href="${pageContext.request.contextPath}/report/radio/cell/yr/list.htm"><span>Báo cáo năm</span></a></li>
</ul>
<div class="ui-tabs-panel">
	
		<table width="100%" class="form">
			<tr>
			    <td align="left">
			  <form method="get" action="list.htm">
					Trung tâm 
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
			        &nbsp;CELL <input value="${cellid}" name="cellid" id="cellid" size="10">
			        &nbsp;Từ tháng <input value="${startMonth}" name="startMonth" id="startMonth" size="2" maxlength="2">
	            	&nbsp;Năm <input value="${startYear}" name="startYear" id="startYear" size="4" maxlength="4">
	            	&nbsp;Tới tháng <input value="${endMonth}" name="endMonth" id="endMonth" size="2" maxlength="2">
	            	&nbsp;Năm <input value="${endYear}" name="endYear" id="endYear" size="4" maxlength="4">
					&nbsp;<input type="submit" class="button" name="submit" value="View Report" />
	          </form>
	            </td>
	        </tr>		
		</table>
	<br/>
	
			<div  id="doublescroll" class="tableStandar">
				<display:table name="${vRpMnCell}" style = "width: 150%" id="vRpMnCell" requestURI="" pagesize="100" export="false" sort="external" defaultsort="1" partialList="true" size="${totalSize}">
				<display:column property="month" titleKey="MONTH" sortable="true" sortName="month" headerClass="master-header-1"/>
			    <display:column property="year" titleKey="YEAR" sortable="true" sortName="year" headerClass="master-header-1"/>
			    <display:column property="region" titleKey="TT" sortable="true" sortName="region" headerClass="master-header-1"/>
			    <display:column titleKey="PROVINCE" media="html" sortable="true" sortName="province" headerClass="master-header-1">
			    	<a href="${pageContext.request.contextPath}/report/radio/province/mn/detail.htm?province=${vRpMnCell.province}&endMonth=${vRpMnCell.month}&endYear=${vRpMnCell.year}">${vRpMnCell.province}</a>
			    </display:column>			    
			    <display:column property="province" titleKey="PROVINCE" headerClass="hide" class="hide"/>
			    <display:column property="bscid" titleKey="BSCID" headerClass="hide" class="hide"/>
			    <display:column titleKey="BSCID" media="html" sortable="true" sortName="bscid" headerClass="master-header-1">
			    	<a href="${pageContext.request.contextPath}/report/radio/bsc/mn/detail.htm?bscid=${vRpMnCell.bscid}&endMonth=${vRpMnCell.month}&endYear=${vRpMnCell.year}">${vRpMnCell.bscid}</a>
			    </display:column>
			    <display:column property="cellid" titleKey="CELLID"  headerClass="hide" class="hide"/>
			    <display:column titleKey="CELLID"  media="html"  headerClass="master-header-1" class="margin" sortable="true" sortName="cellid">
			    	<a href="${pageContext.request.contextPath}/report/radio/cell/mn/detail.htm?cellid=${vRpMnCell.cellid}&bscid=${vRpMnCell.bscid}&startMonth=${vRpMnCell.month}&endMonth=${vRpMnCell.month}&startYear=${vRpMnCell.year}&endYear=${vRpMnCell.year}">${vRpMnCell.cellid}</a>
			    </display:column>
			    <display:column property ="tDef" titleKey="T_DEF" sortable="true" sortName="T_DEF" headerClass="master-header-2"/>
			    <display:column property ="tAvail" titleKey="T_AVAIL" class="T_AVAIL" sortable="true" sortName="T_AVAIL" headerClass="master-header-2"/>
			    <display:column property="tAtts" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="T_ATTS" sortable="true" sortName="T_ATTS" headerClass="master-header-2"/>
			    <display:column property="tBlkr" titleKey="T_BLKR" class="T_BLKR" sortable="true" sortName="T_BLKR" headerClass="master-header-2"/>
			    <display:column property="tHoblkr" titleKey="T_HOBLKR" class="T_HOBLKR" sortable="true" sortName="T_HOBLKR" headerClass="master-header-2"/>
			    <display:column property="cssr" titleKey="CSSR" class="CSSR" sortable="true" sortName="cssr" headerClass="master-header-2"/>
			    <display:column property="tDrpr" titleKey="T_DRPR" class="T_DRPR" sortable="true" sortName="T_DRPR" headerClass="master-header-2"/>
			    <display:column property="tTraf" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="T_TRAF" class="T_TRAF" sortable="true" sortName="T_TRAF" headerClass="master-header-2"/>
			    <display:column property="tTrafh" titleKey="T_TRAFH" class="H_TRAF margin" sortable="true" sortName="T_TRAFH" headerClass="master-header-2" />
			    <display:column property ="sDef" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="S_DEF" sortable="true" sortName="S_DEF" headerClass="master-header-3"/>
			    <display:column property="sAtts" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="S_ATTS" sortable="true" sortName="S_ATTS" headerClass="master-header-3"/>
			    <display:column property ="sBlkr" titleKey="S_BLKR" class="S_BLKR" sortable="true" sortName="S_BLKR" headerClass="master-header-3"/>
			    <display:column property="sDrpr" titleKey="S_DRPR" class="S_DRPR" sortable="true" sortName="S_DRPR" headerClass="master-header-3"/>
				<display:column property="downtime" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="DOWNTIME"   class="DOWNTIME margin" headerClass="master-header-3" sortable="true" sortName="downtime"/>
			    <display:column property ="dataload" titleKey="DATALOAD" sortable="true" sortName="dataload"/>
			    <display:column property="doKhaDung" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="dokhadung"headerClass="master-header-5" sortable="true"/>
			    <display:column property ="defAvail" titleKey="DEF_AVAIL" headerClass="hide" class="hide DEF_AVAIL" media="html"/>
			    <display:column property ="trafAvail" titleKey="TRAF_AVAIL" headerClass="hide" class="hide TRAF_AVAIL" media="html"/>			    
			    <display:column property ="trafhTraf" titleKey="TRAFH_TRAF" headerClass="hide" class="hide TRAFH_TRAF" media="html"/>
			</display:table>
			<div class="exportlinks">Export options:
					<a href="${pageContext.request.contextPath}/exportExcel/mnCell.htm?region=${region}&province=${province}&bscid=${bscid}&cellid=${cellid}&startMonth=${startMonth}&startYear=${startYear}&endMonth=${endMonth}&endYear=${endYear}"><span class="export excel">Excel </span></a>
			</div>
</div>
</div>

<script type="text/javascript">
$(function() {
	var cacheCell = {}, lastXhrCell;
	$( "#cellid" ).autocomplete({
		minLength: 3,
		source: function( request, response ) {
			var term = request.term;
			if ( term in cacheCell ) {
				response( cacheCell[ term ] );
				return;
			}
	
			lastXhrCell = $.getJSON( "${pageContext.request.contextPath}/ajax/getCell.htm", request, function( data, status, xhr ) {
				cacheCell[ term ] = data;
				if ( xhr === lastXhrCell ) {
					response( data );
				}
			});
		}
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