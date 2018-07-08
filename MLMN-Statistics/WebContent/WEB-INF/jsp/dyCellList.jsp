<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<style type="text/css">    #doublescroll { overflow: auto; overflow-y: hidden; }    #doublescroll p { margin: 0; padding: 1em; white-space: nowrap; }</style>
<title>CELL QOS DAILY REPORT</title>
<content tag="heading">CELL QOS DAILY REPORT</content>
<ul class="ui-tabs-nav">
	<!--li class=""><a href="${pageContext.request.contextPath}/report/radio/cell/hr/list.htm"><span>Báo cáo giờ</span></a></li-->
	<li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/report/radio/cell/dy/list.htm"><span>Báo cáo ngày</span></a></li>
	<li class=""><a href="${pageContext.request.contextPath}/report/radio/cell/wk/list.htm"><span>Báo cáo tuần</span></a></li>
	<li class=""><a href="${pageContext.request.contextPath}/report/radio/cell/mn/list.htm"><span>Báo cáo tháng</span></a></li>
	<li class=""><a href="${pageContext.request.contextPath}/report/radio/cell/qr/list.htm"><span>Báo cáo quý</span></a></li>
	<li class=""><a href="${pageContext.request.contextPath}/report/radio/cell/yr/list.htm"><span>Báo cáo năm</span></a></li>
	
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
			        &nbsp;CELL <input value="${cellid}" name="cellid" id="cellid" size="10" >
	                &nbsp;Từ ngày <input value="${startDate}" name="startDate" id="startDate" size="10" maxlength="10">
	                &nbsp;Tới ngày <input value="${endDate}" name="endDate" id="endDate" size="10" maxlength="10">
	                &nbsp;&nbsp;<input type="submit" class="button" name="submit" value="View Report"/>
	          </form>
	            </td>
	        </tr>		
		</table>
		<div id="doublescroll">
		<div class="tableStandar">
			<display:table style = "width: 250%" name="${vRpDyCellList}" id="vRpDyCell" requestURI="" pagesize="100" sort="external" defaultsort="1" partialList="true" size="${totalSize}">
				<display:column property="day" format="{0,date,dd/MM/yyyy}" titleKey="DAY" headerClass="master-header-1" sortable="true" sortName="DAY"/>
			    <display:column property="region" titleKey="TT" headerClass="master-header-1" sortable="true" sortName="region"/>
			    <display:column titleKey="PROVINCE" headerClass="master-header-1" media="html" sortable="true" sortName="PROVINCE">
			    	<a href="${pageContext.request.contextPath}/report/radio/province/hr/detail.htm?province=${vRpDyCell.province}&endDate=<fmt:formatDate pattern="dd/MM/yyyy" value="${vRpDyCell.day}"/>">${vRpDyCell.province}</a>
			    </display:column>			    
			    <display:column property="province" titleKey="PROVINCE" headerClass="hide" class="hide"/>
			    <display:column property="bscid" titleKey="BSCID" headerClass="hide" class="hide"/>
			    <display:column titleKey="BSCID" headerClass="master-header-1" media="html" sortable="true" sortName="bscid">
			    	<a href="${pageContext.request.contextPath}/report/radio/bsc/hr/detail.htm?bscid=${vRpDyCell.bscid}&endDate=<fmt:formatDate pattern="dd/MM/yyyy" value="${vRpDyCell.day}"/>">${vRpDyCell.bscid}</a>
			    </display:column>
			    <display:column property="cellid" headerClass="hide" class="hide"/>
			    <display:column titleKey="CELLID" headerClass="master-header-1 margin" media="html" sortable="true" class="margin" sortName="cellid">
			    	<a href="${pageContext.request.contextPath}/report/radio/cell/hr/detail.htm?cellid=${vRpDyCell.cellid}&bscid=${vRpDyCell.bscid}&endDate=<fmt:formatDate pattern="dd/MM/yyyy" value="${vRpDyCell.day}"/>">${vRpDyCell.cellid}</a>
			    </display:column>
			    <display:column property ="tDef" titleKey="T_DEF" sortable="true" headerClass="master-header-2" sortName="T_DEF"/>
			    <display:column property ="tAvail" titleKey="T_AVAIL" class="T_AVAIL" sortable="true" headerClass="master-header-2" sortName="T_AVAIL"/>
			    <display:column property ="defAvail" title="DEF_AVAIL" headerClass="hide" class="hide DEF_AVAIL" media="html"/>
			    <display:column property="tAtts" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="T_ATTS" sortable="true" headerClass="master-header-2" sortName="T_ATTS"/>
			    <display:column property="tBlkr" titleKey="T_BLKR" class="T_BLKR" sortable="true" headerClass="master-header-2" sortName="T_BLKR"/>
			    <display:column property="tBlks" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="T_BLKS" sortable="true" headerClass="master-header-2" sortName="T_BLKS"/>
			    <display:column property="tHoblkr" titleKey="T_HOBLKR"  sortable="true" headerClass="master-header-2" sortName="T_HOBLKR"/>
			    <display:column property="tHoblks" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="T_HOBLKS" sortable="true" headerClass="master-header-2" sortName="T_HOBLKS"/>
			    <display:column property ="tSeizs" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="T_SEIZS" sortable="true" headerClass="master-header-2" sortName="T_SEIZS"/>
			    <display:column property="cssr" titleKey="CSSR" headerClass="hide" class="hide"/>
			    <display:column titleKey="CSSR" class="CSSR" sortable="true" headerClass="master-header-2" sortName="cssr" media = "html">
			     <a href="${pageContext.request.contextPath}/report/radio/cell/dy/chart.htm?bscid=${vRpDyCell.bscid}&cellid=${vRpDyCell.cellid}&kpi=cssr">
			         ${vRpDyCell.cssr}</a>
			    </display:column>
			    <display:column property="tDrpr" titleKey="T_DRPR" headerClass="hide" class="hide"/>
			    <display:column titleKey="T_DRPR" media="html" sortable="true" headerClass="master-header-2" class="T_DRPR" sortName="T_DRPR">
			    	<a href="${pageContext.request.contextPath}/report/radio/cell/tdrop/dy.htm?bscid=${vRpDyCell.bscid}&cellid=${vRpDyCell.cellid}&endDate=<fmt:formatDate pattern="dd/MM/yyyy" value="${vRpDyCell.day}"/>">${vRpDyCell.tDrpr}</a>
			    </display:column>
			    <display:column property="tDrps" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="TCH Drps" sortable="true" headerClass="master-header-2" sortName="T_DRPS"/>
			    <display:column property="tEmpdr" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="T_EMPDR" sortable="true" headerClass="master-header-2" sortName="T_EMPDR"/>
			    <display:column property="tTraf" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="TCH Traffic" headerClass="hide" class="hide"/>
			    <display:column titleKey="TCH Traffic" class="T_TRAF" media="html" sortable="true" headerClass="master-header-2" sortName="T_TRAF">
			    	<a href="${pageContext.request.contextPath}/report/radio/cell/tTraffic/dy.htm?bscid=${vRpDyCell.bscid}&cellid=${vRpDyCell.cellid}&endDate=<fmt:formatDate pattern="dd/MM/yyyy" value="${vRpDyCell.day}"/>"><fmt:formatNumber pattern="#,###,###,##0.##" value="${vRpDyCell.tTraf}"/></a>
			    </display:column>
			    <display:column property ="trafAvail" titleKey="TRAF_AVAIL" headerClass="hide" class="hide TRAF_AVAIL" media="html"/>
			    <display:column property="tTrafh" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="T_TRAFH" class="H_TRAF margin" sortable="true" headerClass="master-header-2 margin" sortName="T_TRAFH"/>			    
			    <display:column property ="trafhTraf" titleKey="TRAFH_TRAF" headerClass="hide" class="hide TRAFH_TRAF" media="html" sortName="DAY"/>
			    <display:column property="sDef" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="S_DEF"  sortable="true" headerClass="master-header-3" sortName="S_DEF"/>
			    <display:column property="sAtts" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="S_ATTS"  sortable="true" headerClass="master-header-3" sortName="S_ATTS"/>
			    <display:column property ="sBlkr" titleKey="S_BLKR" class="S_BLKR" sortable="true" headerClass="master-header-3" sortName="S_BLKR"/>
			    <display:column property ="sBlks" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="S_BLKS" sortable="true" headerClass="master-header-3" sortName="S_BLKS"/>
			    <display:column property="sDrpr" titleKey="S_DRPR"  class="hide" headerClass="hide"/>
			    <display:column titleKey="S_DRPR" media="html" sortable="true" headerClass="master-header-3" class="S_DRPR" sortName="S_DRPR">
			    	<a href="${pageContext.request.contextPath}/report/radio/cell/sdrop/dy.htm?bscid=${vRpDyCell.bscid}&cellid=${vRpDyCell.cellid}&endDate=<fmt:formatDate pattern="dd/MM/yyyy" value="${vRpDyCell.day}"/>">${vRpDyCell.sDrpr}</a>
			    </display:column>
			    <display:column property="sDrps" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="S_DRPS"  sortable="true" headerClass="master-header-3" sortName="S_DRPS"/>
				<display:column property="downtime" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="DOWNTIME"  sortable="true" class="margin" headerClass="master-header-3 margin" sortName="DOWNTIME"/>
				<display:column property="meanholdtime" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="MEANHOLDTIME"  sortable="true" headerClass="master-header-5" sortName="MEANHOLDTIME"/>
				<display:column property="sdcchmeanholdtime" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="SDCCHMEANHOLDTIME"  sortable="true" class="margin" headerClass="master-header-5 margin" sortName="SDCCHMEANHOLDTIME"/>
				<display:column property="doKhaDung" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="dokhadung"headerClass="master-header-5" sortable="true"/>
				<display:column title="BH" sortable="true" headerClass="master-header-4" sortName="bh">
					${vRpDyCell.bh}:00
				</display:column>
	    		<display:column property="bhTAvail" titleKey="T_AVAIL"  sortable="true" headerClass="master-header-4" sortName="BH_T_AVAIL"/>
	    		<display:column property="bhTchVar" titleKey="T_VAR" class="TCH_VAR" sortable="true" headerClass="master-header-4" sortName="BH_TCH_VAR"/>
	    		<display:column property ="bhTAtts" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="T_ATTS"  sortable="true" headerClass="master-header-4" sortName="BH_T_ATTS"/>
	    		<display:column property="bhTBlkr" titleKey="T_BLKR" sortable="true" headerClass="master-header-4" sortName="BH_T_BLKR"/>
	    		<display:column property="bhTBlks" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="T_BLKS" sortable="true" headerClass="master-header-4" sortName="BH_T_BLKS"/>
	    		<display:column property="bhTHoblkr" titleKey="T_HOBLKR" sortable="true" headerClass="master-header-4" sortName="BH_T_HOBLKR"/>
	    		<display:column property="bhTHoblks" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="T_HOBLKS" sortable="true" headerClass="master-header-4" sortName="BH_T_HOBLKS"/>
	    		<display:column property="bhCssr" titleKey="CSSR" class="CSSR" sortable="true" headerClass="master-header-4" sortName="BH_CSSR"/>
	    		<display:column property="bhTDrpr" titleKey="T_DRPR" class="T_DRPR" sortable="true" headerClass="master-header-4" sortName="BH_T_DRPR"/>
	    		<display:column property ="bhTDrps" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="T_DROPS"  sortable="true" headerClass="master-header-4" sortName="BH_T_DRPS"/>
	    		<display:column property ="bhTTraf" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="T_TRAF"  sortable="true" headerClass="master-header-4" sortName="BH_T_TRAF"/>
	    		<display:column property="bhTGos" titleKey="T_GoS" class="GoS margin" sortable="true" headerClass="master-header-4 margin" sortName="BH_T_GoS"/>
	    		<display:column property ="sitename" title="SITENAME"  class="hide" headerClass="hide"/>
	    		<display:column title="Map" media="html"  sortable="true" sortName="sitename">
			    	<a href='${pageContext.request.contextPath}/map/list.htm?siteid=${vRpDyCell.sitename}' target="_blank">${vRpDyCell.sitename}</a>&nbsp;
			    </display:column>
	    		<%-- <display:column property ="sitename" title="Siteid"   class="hide" headerClass="hide"/>
	    		<display:column title="Map" media="html">
			    	<a href='${pageContext.request.contextPath}/map/list.htm?siteid=${vRpDyCellList.sitename}' target="_blank">Map</a>&nbsp;
			    </display:column> --%>
	    		<%-- <display:column property ="type" title="TYPE"  sortable="true" sortName="type"/>
	    		<display:column property="status" title="BADCELL" style="color:red;" sortable="true" sortName="status"/>
	    		
			    <display:column title="Link" media="html">
			    	<a href='http://10.151.67.67:8000/BTS/index?CELLID=${vRpDyCell.cellid}&day=<fmt:formatDate pattern="dd-MMM-yyyy" value="${vRpDyCell.day}"/>' target="_blank">Map</a>&nbsp;
			    </display:column> --%>
			</display:table>
			</div>
			<div class="exportlinks">Export options:
				<a href="${pageContext.request.contextPath}/exportExcel/cell.htm?region=${region}&startDate=${startDate}&cellid=${cellid}&bscid=${bscid}&province=${province}&endDate=${endDate}"><span class="export excel">Excel </span></a>
			</div>
		</div>
</div>

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