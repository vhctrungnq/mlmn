<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<style type="text/css">    #doublescroll { overflow: auto; overflow-y: hidden; }    #doublescroll p { margin: 0; padding: 1em; white-space: nowrap; }</style>
<title>Báo cáo BSC QoS</title>
<content tag="heading">BSC QOS DAILY REPORT</content>
<ul class="ui-tabs-nav">
	<li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/report/radio/bsc/dy/list.htm"><span>Báo cáo ngày</span></a></li>
	<li class=""><a href="${pageContext.request.contextPath}/report/radio/bsc/wk/list.htm"><span>Báo cáo tuần</span></a></li>
	<li class=""><a href="${pageContext.request.contextPath}/report/radio/bsc/mn/list.htm"><span>Báo cáo tháng</span></a></li>
	<li class=""><a href="${pageContext.request.contextPath}/report/radio/bsc/qr/list.htm"><span>Báo cáo quý</span></a></li>
	<li class=""><a href="${pageContext.request.contextPath}/report/radio/bsc/yr/list.htm"><span>Báo cáo năm</span></a></li>
</ul>
<div class="ui-tabs-panel">
	
		<table style="width:100%;" class="form">
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
			        BSC 
					 <select name="bscid" id="bscid"  style="width: 163px">
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
	                &nbsp;Từ ngày <input value="${startDate}" name="startDate" id="startDate" size="10" maxlength="10">
	                &nbsp;Tới ngày <input value="${endDate}" name="endDate" id="endDate" size="10" maxlength="10">
	                &nbsp;<input type="submit" class="button" name="submit" id="submit" value="View Report"/>
	          	  </form>
	            </td>
	        </tr>		
		</table>
		<div  id="doublescroll">
		<div class = "tableStandar">
			<display:table style = "width: 120%" name="${vRpDyBsc}" id="vRpDyBsc" requestURI="" pagesize="100" export="true" sort="list">
				<display:column property="day" format="{0,date,dd/MM/yyyy}" titleKey="DAY" sortable="true" headerClass="master-header-1"/>		    
			    <display:column property="region" titleKey="TT" sortable="true" headerClass="master-header-1"/>
			    <display:column property="bscid" titleKey="BSCID"  headerClass="hide" class="hide" />
			    <display:column titleKey="BSCID"  media="html" sortable="true" sortProperty="bscid" headerClass="master-header-1">
			    	<a href="${pageContext.request.contextPath}/report/radio/bsc/hr/detail.htm?bscid=${vRpDyBsc.bscid}&startDate=<fmt:formatDate pattern="dd/MM/yyyy" value="${vRpDyBsc.day}"/>&endDate=<fmt:formatDate pattern="dd/MM/yyyy" value="${vRpDyBsc.day}"/>">${vRpDyBsc.bscid}</a>
			    </display:column>
			    <display:column property="sites" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="SITES" headerClass="hide" class="hide"/>
				<display:column titleKey="SITES" media="html" sortable="true" sortProperty="sites" headerClass="master-header-1">
					<a href="${pageContext.request.contextPath}/report/radio/site-qos/dy/list.htm?bscid=${vRpDyBsc.bscid}&day=<fmt:formatDate pattern="dd/MM/yyyy" value="${vRpDyBsc.day}"/>"><fmt:formatNumber pattern="#,###,###,##0.##" value="${vRpDyBsc.sites}"/></a>
				</display:column>
				<display:column property="cells" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="CELLS" headerClass="hide" class="hide"/>
				<display:column titleKey="CELLS" media="html" sortable="true" sortProperty="cells" headerClass="master-header-1">
					<a href="${pageContext.request.contextPath}/report/radio/cell/dy/list.htm?bscid=${vRpDyBsc.bscid}&day=<fmt:formatDate pattern="dd/MM/yyyy" value="${vRpDyBsc.day}"/>"><fmt:formatNumber pattern="#,###,###,##0.##" value="${vRpDyBsc.cells}"/></a>
				</display:column>
				<display:column property ="trxs" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="TRXS" sortable="true" class="margin" headerClass="master-header-1 margin" />
				
				<display:column property ="tDef" titleKey="TCH Def" class="T_DEF" sortable="true"  headerClass="master-header-2"/>
				<display:column property ="tAvail" titleKey="TCH Avail" class="T_AVAIL" sortable="true"  headerClass="master-header-2"/>
				<display:column property ="tAtts" titleKey="TCH Atts" class="T_ATTS" sortable="true"  headerClass="master-header-2"/>
				<display:column property ="tBlkr" titleKey="T_BLKR" class="T_BLKR" sortable="true"  headerClass="master-header-2"/>
				<display:column property ="tBlks" titleKey="TCH Blocks" class="T_BLKS" sortable="true"  headerClass="master-header-2"/>
				<display:column property ="tHoblkr" titleKey="T_HOBLKR" class="T_HOBLKR" sortable="true"  headerClass="master-header-2"/>
				<display:column property ="tHoblks" titleKey="T_HOBLKS" class="T_HOBLKS" sortable="true"  headerClass="master-header-2"/>
				<display:column property="cssr" titleKey="CSSR" class="CSSR margin" sortable="true"  headerClass="master-header-2"/>
				<display:column property ="tDrpr" titleKey="T_DRPR" class="hide" headerClass="hide"/>
				<display:column titleKey="T_DRPR" class="T_DRPR" media="html" sortable="true" sortProperty="tDrpr"  headerClass="master-header-2">
					<a href="${pageContext.request.contextPath}/report/radio/bsc/tdrop/dy.htm?bscid=${vRpDyBsc.bscid}&endDate=<fmt:formatDate pattern="dd/MM/yyyy" value="${vRpDyBsc.day}"/>">${vRpDyBsc.tDrpr}</a>
				</display:column>
				<display:column property ="tDrps" titleKey="TCH Drops" class="T_DRPS" sortable="true"  headerClass="master-header-2"/>
				<display:column property ="tTraf" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="T_TRAF" class="hide" headerClass="hide"/>
				<display:column titleKey="T_TRAF" media="html" sortable="true" sortProperty="tTraf" headerClass="master-header-2">
					<a href="${pageContext.request.contextPath}/report/radio/bsc/tTraffic/dy.htm?bscid=${vRpDyBsc.bscid}&endDate=<fmt:formatDate pattern="dd/MM/yyyy" value="${vRpDyBsc.day}"/>"><fmt:formatNumber pattern="#,###,###,##0.##" value="${vRpDyBsc.tTraf}"/></a>
				</display:column>
				<display:column property ="tEmpdr" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="T_EMPDR" sortable="true"  headerClass="master-header-2"/>
				<display:column property="tAsr" titleKey="T_ASR" sortable="true" headerClass="master-header-2"/> 
				<display:column property="halfrate" titleKey="HALFRATE" sortable="true" class="margin" headerClass="master-header-2"/>
				<display:column property ="sDef" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="S_DEF" sortable="true"  headerClass="master-header-3"/>
				<display:column property="sSsr" titleKey="S_SSR" sortable="true"  headerClass="master-header-3"/>
				<display:column property="sBlkr" titleKey="S_BLKR" class="S_BLKR" sortable="true" headerClass="master-header-3"/>
				<display:column property ="sDrpr" titleKey="S_DRPR" class="hide" headerClass="hide"/>
				<display:column titleKey="S_DRPR" media="html" sortable="true" class="S_DRPR" sortProperty="sDrpr" headerClass="master-header-3">
					<a href="${pageContext.request.contextPath}/report/radio/bsc/sdrop/dy.htm?bscid=${vRpDyBsc.bscid}&endDate=<fmt:formatDate pattern="dd/MM/yyyy" value="${vRpDyBsc.day}"/>">${vRpDyBsc.sDrpr}</a>
				</display:column>
				<%-- <display:column property ="inHoSucr" titleKey="IN_HO_SUCR" sortable="true"/>
				<display:column property="ogHoSucr" titleKey="OG_HO_SUCR" sortable="true"/> --%>
				<display:column property="downtime" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="DOWNTIME"  sortable="true" headerClass="master-header-3"/>
				<display:column property ="badCell" titleKey="BAD_CELL" sortable="true" headerClass="master-header-3"/>
				<display:column property ="badCellR" titleKey="BAD_CELL_R" sortable="true" headerClass="master-header-3"/>
				<display:column property ="dataload" titleKey="DATALOAD" sortable="true" class="margin" headerClass="margin"/>
			    	
			</display:table>
			</div>
		</div>
</div>
<script type="text/javascript">
	function xl(){
		var sub= document.getElementById("submit");
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
		
		${highlight}
		
		 var cache = {},
		  lastXhr;
		  $( "#bscid" ).autocomplete({
		   minLength: 2,
		   source: function( request, response ) {
		    var term = request.term;
		    if ( term in cache ) {
		     response( cache[ term ] );
		     return;
		    }
		
		    lastXhr = $.getJSON( "${pageContext.request.contextPath}/ajax/getBsc.htm", request, function( data, status, xhr ) {
		     cache[ term ] = data;
		     if ( xhr === lastXhr ) {
		      response( data );
		     }
		    });
		   } 
		  });
		  
		<c:if test="${empty bscid}">
		$($('#vRpDyBsc > tbody tr')[0]).before('<c:forEach var="vRpDyRegion" items="${vRpDyRegionList}"><tr class="${vRpDyRegion.region}"><td><fmt:formatDate pattern="dd/MM/yyyy" value="${vRpDyRegion.day}"/></td><td>${vRpDyRegion.region}</td><td/><td><fmt:formatNumber pattern="#,###,###,##0.##" value="${vRpDyRegion.sites}"/></td><td><fmt:formatNumber pattern="#,###,###,##0.##" value="${vRpDyRegion.cells}"/></td><td class="margin"><fmt:formatNumber pattern="#,###,###,##0.##" value="${vRpDyRegion.trxs}"/></td><td>${vRpDyRegion.tDef}</td><td>${vRpDyRegion.tAvail}</td><td><fmt:formatNumber pattern="#,###,###,##0.##" value="${vRpDyRegion.tAtts}"/></td><td>${vRpDyRegion.tBlkr}</td><td>${vRpDyRegion.tBlks}</td><td class="margin">${vRpDyRegion.tHoblkr}</td><td>${vRpDyRegion.tHoblks}</td><td>${vRpDyRegion.cssr}</td><td class="margin">${vRpDyRegion.tDrpr}</td><td class="margin">${vRpDyRegion.tDrps}</td><td class="margin">${vRpDyRegion.tTraf}</td><td class="margin">${vRpDyRegion.tEmpdr}</td><td class="margin">${vRpDyRegion.tAsr}</td><td class="margin">${vRpDyRegion.halfrate}</td><td><fmt:formatNumber pattern="#,###,###,##0.##" value="${vRpDyRegion.sDef}"/></td><td>${vRpDyRegion.sSsr}</td><td>${vRpDyRegion.sBlkr}</td><td>${vRpDyRegion.sDrpr}</td><td>${vRpDyRegion.badCell}</td><td/><td>${vRpDyRegion.badCellR}</td><td>${vRpDyRegion.dataload}</td></tr></c:forEach>');
		</c:if>
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