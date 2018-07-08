<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<style type="text/css">    #doublescroll { overflow: auto; overflow-y: hidden; }    #doublescroll p { margin: 0; padding: 1em; white-space: nowrap; }</style>
<title>Báo cáo Trạm Ibc</title>
<content tag="heading">Báo cáo Trạm IBC</content>
<ul class="ui-tabs-nav">
	<!--li class=""><a href="${pageContext.request.contextPath}/report/radio/cell/hr/list.htm"><span>Báo cáo giờ</span></a></li-->
	<li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/report/radio/ibc/cell/dy/list.htm"><span>Báo cáo ngày</span></a></li>
	<li class=""><a href="${pageContext.request.contextPath}/report/radio/ibc/cell/wk/list.htm"><span>Báo cáo tuần</span></a></li>
	<li class=""><a href="${pageContext.request.contextPath}/report/radio/ibc/cell/mn/list.htm"><span>Báo cáo tháng</span></a></li>
</ul>
<div class="ui-tabs-panel">
	
		<table width="100%" class="form">
			<tr>
			    <td align="left">
			  <form method="get" action="list.htm" name="frmSample" onSubmit="return ValidateForm()">
					Trung tâm 
			  		<select name="region" id="region" onchange="xl()">
			  			<option value="">Tất cả</option>
			              <c:forEach var="items" items="${regionList}">
				              <c:choose>
				                <c:when test="${items.region == region}">
				                    <option value="${items.region}" selected="selected">${items.region}</option>
				                </c:when>
				                <c:otherwise>
				                    <option value="${items.region}">${items.region}</option>
				                </c:otherwise>
				              </c:choose>
						    </c:forEach>
			        </select>
			        &nbsp;PROVINCE 
			        <select name="province" onchange="xl()">
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
			        <select name="bscid" onchange="xl()">
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
			        CELL <input value="${cellid}" name="cellid" id="cellid" size="10">
	                &nbsp;Từ ngày <input value="${startDate}" name="startDate" id="startDate" size="10" maxlength="10">
	                &nbsp;Tới ngày <input value="${endDate}" name="endDate" id="endDate" size="10" maxlength="10">
	                &nbsp;<input type="submit" class="button" name="submit" id="submit" value="View Report"/>
	          </form>
	            </td>
	        </tr>		
		</table>
	<br/>
	
		<div id="doublescroll">
			<display:table name="${vRpDyCellIbcList}" id="vRpDyCellIbc" requestURI="" pagesize="100" class="simple2" export="true" sort="list">
				<display:column property="day" format="{0,date,dd/MM/yyyy}" titleKey="DAY" headerClass="master-header-1" sortable="true"/>
			    <display:column property="region" titleKey="TT" headerClass="master-header-1" sortable="true"/>
			    <display:column titleKey="PROVINCE" headerClass="master-header-1" media="html" sortable="true" sortProperty="province">
			    	<a href="${pageContext.request.contextPath}/report/radio/ibc/province/hr/detail.htm?province=${vRpDyCellIbc.province}&endDate=<fmt:formatDate pattern="dd/MM/yyyy" value="${vRpDyCell.day}"/>">${vRpDyCellIbc.province}</a>
			    </display:column>			    
			    <display:column property="province" titleKey="PROVINCE" headerClass="hide" class="hide"/>
			    <display:column property="bscid" titleKey="BSC" headerClass="hide" class="hide"/>
			    <display:column titleKey="BSC" headerClass="master-header-1" media="html" sortable="true" sortProperty="bscid">
			    	<a href="${pageContext.request.contextPath}/report/radio/ibc/bsc/hr/detail.htm?bscid=${vRpDyCellIbc.bscid}&endDate=<fmt:formatDate pattern="dd/MM/yyyy" value="${vRpDyCell.day}"/>">${vRpDyCellIbc.bscid}</a>
			    </display:column>
			    <display:column property="cellid" titleKey="CELL" headerClass="hide" class="hide"/>
			    <display:column titleKey="CELL" headerClass="master-header-1 margin" media="html" sortable="true" class="margin" sortProperty="cellid">
			    	<a href="${pageContext.request.contextPath}/report/radio/cell/hr/detail.htm?cellid=${vRpDyCellIbc.cellid}&bscid=${vRpDyCellIbc.bscid}&endDate=<fmt:formatDate pattern="dd/MM/yyyy" value="${vRpDyCell.day}"/>">${vRpDyCellIbc.cellid}</a>
			    </display:column>
			    <display:column property ="tDef" titleKey="T_DEF" sortable="true" headerClass="master-header-2"/>
			    <display:column property ="tAvail" titleKey="T_AVAIL" class="T_AVAIL" sortable="true" headerClass="master-header-2"/>
			    <display:column property ="defAvail" title="DEF_AVAIL" headerClass="hide" class="hide DEF_AVAIL" media="html"/>
			    <display:column property="tAtts" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="T_ATTS" sortable="true" headerClass="master-header-2"/>
			    <display:column property="tBlkr" titleKey="T_BLKR" class="T_BLKR" sortable="true" headerClass="master-header-2"/>
			    <display:column property="tBlks" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="T_BLKS" sortable="true" headerClass="master-header-2"/>
			    <display:column property="tHoblkr" titleKey="T_HOBLKR"  sortable="true" headerClass="master-header-2"/>
			    <display:column property="tHoblks" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="T_HOBLKS" sortable="true" headerClass="master-header-2"/>
			    <display:column property ="tSeizs" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="T_SEIZS" sortable="true" headerClass="master-header-2"/>
			    <display:column property="cssr" titleKey="CSSR_CELL" class="CSSR_CELL" sortable="true" headerClass="master-header-2"/>
			    <display:column property="tDrpr" titleKey="T_DRPR" headerClass="hide" class="hide"/>
			    <display:column titleKey="T_DRPR" media="html" sortable="true" headerClass="master-header-2" class="T_DRPR" sortProperty="tDrpr">
			    	<a href="${pageContext.request.contextPath}/report/radio/cell/tdrop/dy.htm?bscid=${vRpDyCellIbc.bscid}&cellid=${vRpDyCellIbc.cellid}&endDate=<fmt:formatDate pattern="dd/MM/yyyy" value="${vRpDyCell.day}"/>">${vRpDyCellIbc.tDrpr}</a>
			    </display:column>
			    <display:column property="tDrps" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="T_DRPS" sortable="true" headerClass="master-header-2"/>
			    <display:column property="tEmpdr" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="T_EMPDR" sortable="true" headerClass="master-header-2"/>
			    <display:column property="tTraf" title="T_TRAF" headerClass="hide" class="hide"/>
			    <display:column titleKey="T_TRAF"  class="T_TRAF" media="html" sortable="true" headerClass="master-header-2" sortProperty="tTraf">
			    	<a href="${pageContext.request.contextPath}/report/radio/cell/tTraffic/dy.htm?bscid=${vRpDyCellIbc.bscid}&cellid=${vRpDyCellIbc.cellid}&endDate=<fmt:formatDate pattern="dd/MM/yyyy" value="${vRpDyCellIbc.day}"/>"><fmt:formatNumber pattern="#,###,###,##0.##" value="${vRpDyCellIbc.tTraf}"/></a>
			    </display:column>
			    <display:column property ="trafAvail" titleKey="TRAF_AVAIL" headerClass="hide" class="hide TRAF_AVAIL" media="html"/>
			    <display:column property="tTrafh" titleKey="T_TRAFH" class="H_TRAF margin" sortable="true" headerClass="master-header-2 margin"/>			    
			    <display:column property ="trafhTraf" titleKey="TRAFH_TRAF" headerClass="hide" class="hide TRAFH_TRAF" media="html"/>
			    <display:column property="sAtts" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="S_ATTS"  sortable="true" headerClass="master-header-3"/>
			    <display:column property ="sBlkr" titleKey="S_BLKR" class="S_BLKR" sortable="true" headerClass="master-header-3"/>
			    <display:column property ="sBlks" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="S_BLKS" sortable="true" headerClass="master-header-3"/>
			    <display:column property="sDrps" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="S_DRPS"  sortable="true" headerClass="master-header-3"/>
			    <display:column property="sDrpr" titleKey="S_DRPR"  class="hide" headerClass="hide"/>
			    <display:column titleKey="S_DRPR" media="html" sortable="true" headerClass="master-header-3" class="S_DRPR" sortProperty="sDrpr">
			    	<a href="${pageContext.request.contextPath}/report/radio/cell/sdrop/dy.htm?bscid=${vRpDyCellIbc.bscid}&cellid=${vRpDyCellIbc.cellid}&endDate=<fmt:formatDate pattern="dd/MM/yyyy" value="${vRpDyCell.day}"/>">${vRpDyCellIbc.sDrpr}</a>
			    </display:column>
				<display:column property="downtime" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="DOWNTIME"  sortable="true" class="margin" headerClass="master-header-3 margin"/>
				
				<display:column title="Busy Hour" sortable="true" headerClass="master-header-4" sortProperty="bh">
					${vRpDyCellIbc.bh}:00
				</display:column>
	    		<display:column property="bhTAvail" titleKey="BH_T_AVAIL"  sortable="true" headerClass="master-header-4"/>
	    		<display:column property="bhTchVar" titleKey="BH_TCH_VAR" class="TCH_VAR" sortable="true" headerClass="master-header-4"/>
	    		<display:column property ="bhTAtts" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="BH_T_ATTS"  sortable="true" headerClass="master-header-4"/>
	    		<display:column property="bhTBlkr" titleKey="BH_T_BLKR" sortable="true" headerClass="master-header-4"/>
	    		<display:column property="bhTBlks" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="BH_T_BLKS"  sortable="true" headerClass="master-header-4"/>
	    		<display:column property="bhTHoblkr" titleKey="BH_T_HOBLKR" sortable="true" headerClass="master-header-4"/>
	    		<display:column property="bhTHoblks" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="BH_T_HOBLKS" sortable="true" headerClass="master-header-4"/>
	    		<display:column property="bhCssr" titleKey="BH_CSSR" class="BH_CSSR" sortable="true" headerClass="master-header-4"/>
	    		<display:column property="bhTDrpr" titleKey="BH_T_DRPR" class="BH_T_DRPR" sortable="true" headerClass="master-header-4"/>
	    		<display:column property ="bhTDrps" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="BH_T_DRPS"  sortable="true" headerClass="master-header-4"/>
	    		<display:column property ="bhTTraf" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="BH_T_TRAF"  sortable="true" headerClass="master-header-4"/>
	    		<display:column property="bhTGos" titleKey="BH_T_GoS" class="GoS margin" sortable="true" headerClass="master-header-4 margin"/>
	    		
	    		<display:column property ="sitename" title="SITENAME"  sortable="true"/>
	    		<display:column property ="type" title="TYPE"  sortable="true"/>
	    		<display:column property="status" title="BADCELL" style="color:red;" sortable="true"/>
	    		
			    <display:column title="Link" media="html">
			    	<a href='http://10.151.67.67:8000/BTS/index?CELLID=${vRpDyCellIbc.cellid}&day=<fmt:formatDate pattern="dd-MMM-yyyy" value="${vRpDyCellIbc.day}"/>' target="_blank">Map</a>&nbsp;
			    </display:column>
			</display:table>
		</div>
</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/text_date.js"></script>
<script type="text/javascript">  
function xl(){
	var sub = document.getElementById("submit");
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