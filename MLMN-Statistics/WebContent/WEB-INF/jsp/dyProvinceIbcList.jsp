<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<style type="text/css">    #doublescroll { overflow: auto; overflow-y: hidden; }    #doublescroll p { margin: 0; padding: 1em; white-space: nowrap; }</style>
<title>Báo cáo Province Ibc</title>
<content tag="heading">PROVINCE IBC REPORT</content>
<ul class="ui-tabs-nav">
	<li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/report/radio/ibc/province/dy/list.htm"><span>Báo cáo ngày</span></a></li>
	<li class=""><a href="${pageContext.request.contextPath}/report/radio/ibc/province/wk/list.htm"><span>Báo cáo tuần</span></a></li>
	<li class=""><a href="${pageContext.request.contextPath}/report/radio/ibc/province/mn/list.htm"><span>Báo cáo tháng</span></a></li>
</ul>
<div class="ui-tabs-panel">
	
		<table width="100%" class="form">
			<tr>
			    <td align="left">
			  <form method="get" action="list.htm" name = "frmSample" onSubmit = "return ValidateForm()">
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
	                &nbsp;Từ ngày <input value="${startDate}" name="startDate" id="startDate" size="10" maxlength="10">
	                &nbsp;Tới ngày <input value="${endDate}" name="endDate" id="endDate" size="10" maxlength="10">
	                &nbsp;&nbsp;<input type="submit" class="button" name="submit" id="submit" value="View Report"/>
	          </form>
	            </td>
	        </tr>		
		</table>
	<br/>
	
		<div id="doublescroll">
			<display:table name="${vRpDyProvinceIbc}" id="vRpDyProvinceIbc" requestURI="" pagesize="100" class="simple2" export="true" sort="list">
				<display:column property="day" format="{0,date,dd/MM/yyyy}" titleKey="DAY" sortable="true"/>
			    <display:column property="region" titleKey="TT" sortable="true"/>
				<display:column property="province" titleKey="PROVINCE" headerClass="hide" class="hide"/>
			    <display:column titleKey="PROVINCE" media="html" sortable="true" sortProperty="province">
			    	<a href="${pageContext.request.contextPath}/report/radio/ibc/province/hr/detail.htm?province=${vRpDyProvinceIbc.province}&endDate=<fmt:formatDate pattern="dd/MM/yyyy" value="${vRpDyProvinceIbc.day}"/>">${vRpDyProvinceIbc.province}</a>
			    </display:column>
			    <display:column property="sites" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="SITES" sortable="true"/>
			    <display:column property="cells" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="CELLS" sortable="true"/>
			    <display:column property="trxs" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="TRXS" sortable="true" class="margin" headerClass="margin"/>
			    <display:column property ="tTraf" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="T_TRAF" sortable="true"/>
			    <display:column property ="tDrpr" titleKey="T_DRPR" sortable="true"/>
			    <display:column property ="tBlkr" titleKey="T_BLKR" class="T_BLKR" sortable="true"/>
			    <display:column property ="tHoblkr" titleKey="T_HOBLKR" class="T_HOBLKR" sortable="true"/>
			    <display:column property ="tEmpdr" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="T_EMPDR" sortable="true"/>
			    <display:column property="cssr" titleKey="CSSR" class="CSSR margin" sortable="true" headerClass="margin"/>
			    <display:column property ="sDef" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="S_DEF" sortable="true"/>
			    <display:column property="sSsr" titleKey="S_SSR" sortable="true"/>
			    <display:column property="sBlkr" titleKey="S_BLKR" class="S_BLKR" sortable="true"/>
			    <display:column property ="sDrpr" titleKey="S_DRPR" class="S_DRPR" sortable="true"/>
			    <display:column property="tAsr" titleKey="T_ASR" sortable="true"/> 
			    <display:column property="haftratePercent" titleKey="HALFRATE" sortable="true" class="margin" headerClass="margin"/>
			    <display:column property ="inHoSucr" titleKey="IN_HO_SUCR" sortable="true"/>
			    <display:column property="ogHoSucr" titleKey="OG_HO_SUCR" sortable="true"/>
			    <display:column property="downtime" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="DOWNTIME"  sortable="true"/>
			    <display:column property ="dataload" titleKey="DATALOAD" sortable="true" class="margin" headerClass="margin"/>
			    <display:column property ="badCell" titleKey="BAD_CELL" sortable="true"/>
			    <display:column property ="badCellR" titleKey="BAD_CELL_R" sortable="true"/>
			</display:table>
		</div>
</div>

<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/text_date.js"></script>
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
		
		${highlight};
		<c:if test="${empty province}">
		$($('#vRpDyProvinceIbc > tbody tr')[0]).before('<c:forEach var="vRpDyRegion" items="${vRpDyRegionList}"><tr class="${vRpDyRegion.region}"><td><fmt:formatDate pattern="dd/MM/yyyy" value="${vRpDyRegion.day}"/></td><td>${vRpDyRegion.region}</td><td/><td><fmt:formatNumber pattern="#,###,###,##0.##" value="${vRpDyRegion.sites}"/></td><td><fmt:formatNumber pattern="#,###,###,##0.##" value="${vRpDyRegion.cells}"/></td><td class="margin"><fmt:formatNumber pattern="#,###,###,##0.##" value="${vRpDyRegion.trxs}"/></td><td><fmt:formatNumber pattern="#,###,###,##0.##" value="${vRpDyRegion.tTraf}"/></td><td>${vRpDyRegion.tDrpr}</td><td>${vRpDyRegion.tBlkr}</td><td>${vRpDyRegion.tHoblkr}</td><td>${vRpDyRegion.tEmpdr}</td><td class="margin">${vRpDyRegion.cssr}</td><td><fmt:formatNumber pattern="#,###,###,##0.##" value="${vRpDyRegion.sDef}"/></td><td>${vRpDyRegion.sSsr}</td><td>${vRpDyRegion.sBlkr}</td><td>${vRpDyRegion.sDrpr}</td><td>${vRpDyRegion.tAsr}</td><td class="margin">${vRpDyRegion.halfrate}</td><td>${vRpDyRegion.inHoSucr}</td><td>${vRpDyRegion.ogHoSucr}</td><td/><td class="margin">${vRpDyRegion.dataload}</td><td>${vRpDyRegion.badCell}</td><td>${vRpDyRegion.badCellR}</td></tr></c:forEach>');
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