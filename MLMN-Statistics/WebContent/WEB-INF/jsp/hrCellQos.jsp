<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<style type="text/css">    #doublescroll { overflow: auto; overflow-y: hidden; }    #doublescroll p { margin: 0; padding: 1em; white-space: nowrap; }</style>
<title>HOURLY REPORT CELL ${cellid}</title>
<content tag="heading">HOURLY REPORT CELL: ${cellid}</content>
<ul class="ui-tabs-nav">
  <li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/report/radio/cell/hr/detail.htm?cellid=${cellid}&bscid=${bscid}&endDate=${endDate}"><span>Báo cáo giờ</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/radio/cell/dy/detail.htm?cellid=${cellid}&bscid=${bscid}&endDate=${endDate}"><span>Báo cáo ngày</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/radio/cell/wk/detail.htm?cellid=${cellid}&bscid=${bscid}"><span>Báo cáo tuần</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/radio/cell/mn/detail.htm?cellid=${cellid}&bscid=${bscid}"><span>Báo cáo tháng</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/radio/cell/dy/bh.htm?cellid=${cellid}&bscid=${bscid}&endDate=${endDate}"><span>Báo cáo BH ngày</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/radio/cell/wk/bh.htm?cellid=${cellid}&bscid=${bscid}"><span>Báo cáo BH tuần</span></a></li>
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
				    &nbsp;CELL <input value="${cellid}" name="cellid" id="cellid" size="10">
	            	&nbsp;Từ <input value="${startHour}" name="startHour" id="startHour" size="5" maxlength="5"> giờ
	            	&nbsp;<input value="${startDate}" name="startDate" id="startDate" size="10" maxlength="10">
	            	&nbsp;&nbsp;Tới <input value="${endHour}" name="endHour" id="endHour" size="5" maxlength="5"> giờ
	            	&nbsp;<input value="${endDate}" name="endDate" id="endDate" size="10" maxlength="10">
	            	&nbsp;&nbsp;<input type="submit" class="button" name="submit" value="View Report"/>
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
		<div id="doublescroll">
		<div class="tableStandar">
			<display:table style = "width: 160%" name="${vRpHrCellList}" id="vRpHrCell" requestURI="" pagesize="100" class="simple2" export="true">
				<display:column property="day" format="{0,date,dd/MM/yyyy}" titleKey="DAY" headerClass="master-header-1" sortable="true"/>
			    <display:column titleKey="HOUR" headerClass="master-header-1" sortable="true">
			    	${vRpHrCell.hour} 
			    </display:column> 
			    <display:column titleKey="PROVINCE" headerClass="master-header-1" media="html"  style = "width: 120px" sortable="true">
			    	<a href="${pageContext.request.contextPath}/report/radio/province/hr/detail.htm?province=${vRpHrCell.province}&startDate=<fmt:formatDate pattern="dd/MM/yyyy" value="${vRpHrCell.day}"/>&endDate=<fmt:formatDate pattern="dd/MM/yyyy" value="${vRpHrCell.day}"/>">${vRpHrCell.province}</a>
			    </display:column>			    
			    <display:column property="province" titleKey="PROVINCE" headerClass="hide" class="hide"  style = "width: 120px"/>
			    <display:column property="bscid" titleKey="BSCID" class="hide" headerClass="hide"/>
			    <display:column titleKey="BSCID" headerClass="master-header-1" media="html" sortable="true">
			    	<a href="${pageContext.request.contextPath}/report/radio/bsc/hr/detail.htm?bscid=${vRpHrCell.bscid}&startDate=<fmt:formatDate pattern="dd/MM/yyyy" value="${vRpHrCell.day}"/>&endDate=<fmt:formatDate pattern="dd/MM/yyyy" value="${vRpHrCell.day}"/>">${vRpHrCell.bscid}</a>
			    </display:column>
			    <display:column property="cellid" titleKey="CELLID" class="margin" headerClass="master-header-1 margin" sortable="true"/>
			    <display:column property ="tDef" titleKey="T_DEF" headerClass="master-header-2" sortable="true"/>
			    <display:column property="tAvail" titleKey="T_AVAIL" class="T_AVAIL" headerClass="master-header-2" sortable="true"/>
			    <display:column property="tAtts" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="T_ATTS" headerClass="master-header-2" sortable="true"/>
			    <display:column property="tBlkr" titleKey="T_BLKR" class="T_BLKR" headerClass="master-header-2" sortable="true"/>
			    <display:column property="tBlks" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="T_BLKS" headerClass="master-header-2" sortable="true"/>
			    <display:column property="tHoblkr" titleKey="T_HOBLKR" headerClass="master-header-2" sortable="true"/>
			    <display:column property="tHoblks" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="T_HOBLKS" headerClass="master-header-2" sortable="true"/>
			    <display:column property ="tSeizs" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="T_SEIZS" headerClass="master-header-2" sortable="true"/>
			    <display:column property="cssr" titleKey="CSSR" headerClass="master-header-2" class="CSSR" sortable="true"/>
			    <display:column property="tDrpr" titleKey="T_DRPR" headerClass="hide" class="hide"  sortable="true" />
			    <display:column titleKey="T_DRPR" media="html" headerClass="master-header-2" class="T_DRPR" sortable="true">
			    	<a href="${pageContext.request.contextPath}/report/radio/cell/tdrop/hr.htm?bscid=${vRpHrCell.bscid}&cellid=${vRpHrCell.cellid}&endDate=<fmt:formatDate pattern="dd/MM/yyyy" value="${vRpHrCell.day}"/>">${vRpHrCell.tDrpr}</a>
				</display:column>
			    <display:column property="tDrps" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="T_DROPS" headerClass="master-header-2" sortable="true"/>
			    <display:column property="tTraf" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="T_TRAF" headerClass="hide" class="hide"/>
			    <display:column titleKey="T_TRAF" media="html" class="T_TRAF" headerClass="master-header-2" sortable="true">
			    	<a href="${pageContext.request.contextPath}/report/radio/cell/tTraffic/hr.htm?bscid=${vRpHrCell.bscid}&cellid=${vRpHrCell.cellid}&endDate=<fmt:formatDate pattern="dd/MM/yyyy" value="${vRpHrCell.day}"/>"><fmt:formatNumber pattern="#,###,###,##0.##" value="${vRpHrCell.tTraf}"/></a>
				</display:column>
			    <display:column property ="tTrafh" titleKey="T_TRAFH" class="H_TRAF margin" headerClass="master-header-2 margin" sortable="true"/>
			    <display:column property="sDef" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="S_DEF" headerClass="master-header-3" sortable="true"/>
			    <display:column property ="sAvail" titleKey="S_AVAIL" headerClass="master-header-3" sortable="true"/>
			    <display:column property="sAtts" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="S_ATTS" headerClass="master-header-3" sortable="true"/>
			    <display:column property ="sBlkr" titleKey="S_BLKR" headerClass="master-header-3" class="S_BLKR" sortable="true"/>
			    <display:column property="sBlks" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="S_BLKS" headerClass="master-header-3" sortable="true"/>
			    <display:column property ="sSeizs" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="S_SEIZS" headerClass="master-header-3" sortable="true"/>
			    <display:column property="sDrpr" titleKey="S_DRPR" headerClass="hide" class="hide"/>
			    <display:column titleKey="S_DRPR" media="html" headerClass="master-header-3" sortable="true">
			    	<a href="${pageContext.request.contextPath}/report/radio/cell/sdrop/hr.htm?bscid=${vRpHrCell.bscid}&cellid=${vRpHrCell.cellid}&endDate=<fmt:formatDate pattern="dd/MM/yyyy" value="${vRpHrCell.day}"/>">${vRpHrCell.sDrpr}</a>
				</display:column>			    
			    <display:column property="sDrps" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="S_DRPS" headerClass="master-header-3" sortable="true"/>
			    <display:column property="downtime" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="DOWNTIME" class="margin" headerClass="master-header-3 margin" sortable="true"/>
			    <display:column property="meanholdtime" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="MEANHOLDTIME"headerClass="master-header-5" sortable="true"/>
			    <display:column property="sdcchmeanholdtime" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="SDCCHMEANHOLDTIME"headerClass="master-header-5" sortable="true"/>
			    <display:column property ="defAvail" titleKey="DEF_AVAIL" headerClass="hide" class="hide DEF_AVAIL" media="html"/>
			    <display:column property ="trafAvail" titleKey="TRAF_AVAIL" headerClass="hide" class="hide TRAF_AVAIL" media="html"/>			    
			    <display:column property ="trafhTraf" titleKey="TRAFH_TRAF" headerClass="hide" class="hide TRAFH_TRAF" media="html"/>
			</display:table>
		</div>
		</div>
	<br/>
	<div id="availChart" style="width: 1000px; margin: 1em auto"></div>
	<br/>
	<div id="trafChart" style="width: 1000px; margin: 1em auto"></div>
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
${availChart}
${trafChart}
${tDrprChart}
${sDrprChart}
${cssrChart}
${tBlkrChart}
${sBlkrChart}

<script type="text/javascript">
	$(document).ready(function() {
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