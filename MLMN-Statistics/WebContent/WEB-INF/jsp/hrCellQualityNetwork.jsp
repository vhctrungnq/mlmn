<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<title>HOURLY REPORT ${title} CELL</title>
<content tag="heading">HOURLY REPORT ${title} CELL</content>
 <form method="get" action="${function}.htm">
		<table width="100%" class="form">
			<tr>
				<td align="left">
			        Bscid 
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
				    &nbsp;Cellid <input value="${cellid}" name="cellid" id="cellid" size="10">
	            	&nbsp;Date<input value="${startDate}" name="startDate" id="startDate" size="10" maxlength="10">
	            	&nbsp;From Hour <input value="${startHour}" name="startHour" id="startHour" size="5" maxlength="5">
	            	&nbsp;To Hour <input value="${endHour}" name="endHour" id="endHour" size="5" maxlength="5"> 
	            	&nbsp;&nbsp;<input type="submit" class="button" name="submit" value="View Report"/>
	            </td>
	        </tr>
	    </table>
</form>
<display:table name="${hrCellList}" id="hrCellList" requestURI="" pagesize="100" class="simple2" export="true">
			<display:column property="day" format="{0,date,dd/MM/yyyy}" titleKey="DAY" headerClass="master-header-1" sortable="true"/>
			<display:column titleKey="HOUR" headerClass="master-header-1" sortable="true">
			${hrCellList.hour}:00
			</display:column> 
			<display:column property="bscid" titleKey="BSCID" class="hide" headerClass="hide"/>
			<display:column titleKey="BSCID" headerClass="master-header-1" media="html" sortable="true">
			<a href="${pageContext.request.contextPath}/report/radio/bsc/hr/detail.htm?bscid=${hrCellList.bscid}&startDate=<fmt:formatDate pattern="dd/MM/yyyy" value="${hrCellList.day}"/>&endDate=<fmt:formatDate pattern="dd/MM/yyyy" value="${hrCellList.day}"/>">${hrCellList.bscid}</a>
			</display:column>
			<display:column property="cellid" titleKey="CELLID" class="margin" headerClass="master-header-1 margin" sortable="true"/>
			<c:choose> 
			<c:when test="${function=='low-cssr'}">
				<display:column property="sBlks" class= "rightColumnMana" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="S_BLKS" headerClass="master-header-3" sortable="true"/>
				<display:column property="sDrps" class= "rightColumnMana" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="S_DRPS" headerClass="master-header-3" sortable="true"/>
			    <display:column property ="sSeizs" class= "rightColumnMana" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="S_SEIZS" headerClass="master-header-3" sortable="true"/>
			    <display:column property ="tSeizs" class= "rightColumnMana" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="T_SEIZS" headerClass="master-header-2" sortable="true"/>
			    <display:column property="tAtts" class= "rightColumnMana" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="T_ATTS" headerClass="master-header-2" sortable="true"/>
			    <display:column property="cssr" titleKey="CSSR" headerClass="master-header-2" class="CSSR rightColumnMana" sortable="true"/>
			</c:when>
			
			<c:when test="${function=='tch-dropped'}">
				<display:column property="tAtts" class= "rightColumnMana" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="T_ATTS" headerClass="master-header-2" sortable="true"/>
				<display:column property ="tSeizs" class= "rightColumnMana" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="T_SEIZS" headerClass="master-header-2" sortable="true"/>
				<display:column property="tDrps" class= "rightColumnMana" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="T_DROPS" headerClass="master-header-2" sortable="true"/>
				<display:column titleKey="T_DRPR" media="html" headerClass="master-header-2" class="T_DRPR rightColumnMana" sortable="true">
				<a href="${pageContext.request.contextPath}/report/radio/cell/tdrop/hr.htm?bscid=${hrCellList.bscid}&cellid=${hrCellList.cellid}&endDate=<fmt:formatDate pattern="dd/MM/yyyy" value="${hrCellList.day}"/>">${hrCellList.tDrpr}</a>
				</display:column>
			</c:when>
		    <c:when test="${function=='sdcch-dropped'}">
			    <display:column property="sAtts" class= "rightColumnMana" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="S_ATTS" headerClass="master-header-3" sortable="true"/>
				<display:column property ="sSeizs" class= "rightColumnMana" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="S_SEIZS" headerClass="master-header-3" sortable="true"/>
				<display:column property="sDrps" class= "rightColumnMana" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="S_DRPS" headerClass="master-header-3" sortable="true"/>
				<display:column property="sDrpr" titleKey="S_DRPR" headerClass="hide" class="hide"/>
				<display:column titleKey="S_DRPR" media="html" class= "S_DRPR rightColumnMana" headerClass="master-header-3" sortable="true">
				<a href="${pageContext.request.contextPath}/report/radio/cell/sdrop/hr.htm?bscid=${hrCellList.bscid}&cellid=${hrCellList.cellid}&endDate=<fmt:formatDate pattern="dd/MM/yyyy" value="${hrCellList.day}"/>">${hrCellList.sDrpr}</a>
				</display:column>			    
			</c:when>
		    <c:when test="${function=='tch-blocking'}">
			    <display:column property ="tDef"   class= "rightColumnMana" titleKey="T_DEF" headerClass="master-header-2" sortable="true"/>
				<display:column property="tAvail" titleKey="T_AVAIL" class="T_AVAIL" headerClass="master-header-2" sortable="true"/>
				<display:column property="tAtts"  class= "rightColumnMana" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="T_ATTS" headerClass="master-header-2" sortable="true"/>
				<display:column property="tBlks"  class= "rightColumnMana" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="T_BLKS" headerClass="master-header-2" sortable="true"/>
				<display:column property="tTraf" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="T_TRAF" headerClass="hide" class="hide"/>
				<display:column titleKey="T_TRAF" media="html" class="T_TRAF rightColumnMana" headerClass="master-header-2" sortable="true">
				<a href="${pageContext.request.contextPath}/report/radio/cell/tTraffic/hr.htm?bscid=${hrCellList.bscid}&cellid=${hrCellList.cellid}&endDate=<fmt:formatDate pattern="dd/MM/yyyy" value="${hrCellList.day}"/>"><fmt:formatNumber pattern="#,###,###,##0.##" value="${hrCellList.tTraf}"/></a>
				</display:column>
				<display:column property="tBlkr" titleKey="T_BLKR" class="T_BLKR rightColumnMana" headerClass="master-header-2" sortable="true"/>
			</c:when>
			<c:when test="${function=='no-avail-tch'}">
			    <display:column property ="tDef"   class= "rightColumnMana" titleKey="T_DEF" headerClass="master-header-2" sortable="true"/>
				<display:column property="tAvail" titleKey="T_AVAIL" class="T_AVAIL" headerClass="master-header-2" sortable="true"/>
			</c:when>
		    <c:when test="${function=='sdcch-blocking'}">
			    <display:column property="sAtts" class= "rightColumnMana" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="S_ATTS" headerClass="master-header-3" sortable="true"/>
				<display:column property ="sSeizs" class= "rightColumnMana" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="S_SEIZS" headerClass="master-header-3" sortable="true"/>
				<display:column property="sBlks" class= "rightColumnMana" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="S_BLKS" headerClass="master-header-3" sortable="true"/>
				<display:column property ="sBlkr" titleKey="S_BLKR" headerClass="master-header-3" class="S_BLKR rightColumnMana" sortable="true"/>
				
		    </c:when>
		    <c:otherwise>
			    <display:column property ="tDef" class= "rightColumnMana" titleKey="T_DEF" headerClass="master-header-2" sortable="true"/>
				<display:column property="tAvail" titleKey="T_AVAIL" class="T_AVAIL rightColumnMana" headerClass="master-header-2" sortable="true"/>
			    <display:column property="tTraf" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="T_TRAF" headerClass="master-header-2" sortable="true"/>
			    <display:column property ="tTrafh" titleKey="T_TRAFH" class="H_TRAF margin" headerClass="master-header-2 margin" sortable="true"/>
				<display:column property ="trafhTraf" titleKey="Traffic Util(%)" sortable =  "true" headerClass="master-header-2 margin" class="TRAFH_TRAF rightColumnMana" />
			</c:otherwise>
			</c:choose>
			
	</display:table>
<script type="text/javascript">
	$(document).ready(function() {
		$( "#startDate" ).datepicker({
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