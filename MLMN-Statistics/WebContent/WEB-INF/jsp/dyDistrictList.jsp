<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<title><fmt:message key="sidebar.district.qos"/></title>
<content tag="heading">DISTRICT QOS DAILY REPORT</content>
<ul class="ui-tabs-nav">
	<li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/report/radio/district/dy/list.htm"><span>Báo cáo ngày</span></a></li>
	<li class=""><a href="${pageContext.request.contextPath}/report/radio/district/wk/list.htm"><span>Báo cáo tuần</span></a></li>
	<li class=""><a href="${pageContext.request.contextPath}/report/radio/district/mn/list.htm"><span>Báo cáo tháng</span></a></li>
	<li class=""><a href="${pageContext.request.contextPath}/report/radio/district/qr/list.htm"><span>Báo cáo quý</span></a></li>
	<li class=""><a href="${pageContext.request.contextPath}/report/radio/district/yr/list.htm"><span>Báo cáo năm</span></a></li>
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
				    &nbsp;DISTRICT <input value="${district}" name="district" id="district" size="10" maxlength="50">
	                &nbsp;Từ ngày <input value="${startDate}" name="startDate" id="startDate" size="10" maxlength="10">
	                &nbsp;Tới ngày <input value="${endDate}" name="endDate" id="endDate" size="10" maxlength="10">
	                &nbsp;&nbsp;<input type="submit" class="button" name="submit" id="submit" value="View Report"/>
	          </form>
	            </td>
	        </tr>		
		</table>
	<br/>
	
		<div  style="overflow: auto;">
			<display:table style = "width: 150%" name="${vRpDyDistrict}" id="vRpDyDistrict" requestURI="" pagesize="100" class="simple2" export="true" sort="list">
				<display:column property="day" format="{0,date,dd/MM/yyyy}" titleKey="DAY" sortable="true"  headerClass="master-header-1"/>
			    <display:column property="region" titleKey="TT" sortable="true"  headerClass="master-header-1"/>
				<display:column property="province" titleKey="PROVINCE" headerClass="hide" class="hide"/>
			    <display:column titleKey="PROVINCE" media="html" sortable="true" sortProperty="province" style = "width: 120px"  headerClass="master-header-1">
			    	<a href="${pageContext.request.contextPath}/report/radio/province/hr/detail.htm?province=${vRpDyDistrict.province}&endDate=<fmt:formatDate pattern="dd/MM/yyyy" value="${vRpDyDistrict.day}"/>">${vRpDyDistrict.province}</a>
			    </display:column>
				<display:column property="district" titleKey="DISTRICT" headerClass="hide" class="hide"/>
			    <display:column titleKey="DISTRICT" media="html" sortable="true" sortProperty="district" style = "width: 120px"  headerClass="master-header-1">
			    	<a href="${pageContext.request.contextPath}/report/radio/district/hr/detail.htm?province=${vRpDyDistrict.province}&district=${vRpDyDistrict.district}&endDate=<fmt:formatDate pattern="dd/MM/yyyy" value="${vRpDyDistrict.day}"/>">${vRpDyDistrict.district}</a>
			    </display:column>
			    <display:column property="sites" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="SITES" headerClass="hide" class="hide"/>
			    <display:column titleKey="SITES" media="html" sortable="true" sortProperty="sites"  headerClass="master-header-1">
			    	<a href="${pageContext.request.contextPath}/report/radio/site-qos/dy/list.htm?province=${vRpDyDistrict.province}&day=<fmt:formatDate pattern="dd/MM/yyyy" value="${vRpDyDistrict.day}"/>"><fmt:formatNumber pattern="#,###,###,##0.##" value="${vRpDyDistrict.sites}"/></a>
			    </display:column>
			    <display:column property="cells" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="CELLS" headerClass="hide" class="hide"/>
			    <display:column titleKey="CELLS" media="html" sortable="true" sortProperty="cells"  headerClass="master-header-1">
			    	<a href="${pageContext.request.contextPath}/report/radio/cell/dy/list.htm?province=${vRpDyDistrict.province}&day=<fmt:formatDate pattern="dd/MM/yyyy" value="${vRpDyDistrict.day}"/>"><fmt:formatNumber pattern="#,###,###,##0.##" value="${vRpDyDistrict.cells}"/></a>
			    </display:column>
			    <display:column property="trxs" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="TRXS" sortable="true"  class="margin"  headerClass="master-header-1"/>
			    <display:column property ="tDef" titleKey="TCH Def" class="T_DEF" sortable="true"  headerClass="master-header-2"/>
				<display:column property ="tAvail" titleKey="TCH Avail" class="T_AVAIL" sortable="true"  headerClass="master-header-2"/>
				<display:column property ="tAtts" titleKey="TCH Atts" class="T_ATTS" sortable="true"  headerClass="master-header-2"/>
				<display:column property ="tBlkr" titleKey="T_BLKR" class="T_BLKR" sortable="true"  headerClass="master-header-2"/>
				<display:column property ="tBlks" titleKey="TCH Blocks" class="T_BLKS" sortable="true"  headerClass="master-header-2"/>
				<display:column property ="tHoblkr" titleKey="T_HOBLKR" class="T_HOBLKR" sortable="true"  headerClass="master-header-2"/>
				<display:column property ="tHoblks" titleKey="T_HOBLKS" class="T_HOBLKS" sortable="true"  headerClass="master-header-2"/>
			    <display:column property="cssr" titleKey="CSSR" class="CSSR margin" sortable="true" headerClass="master-header-2 margin"/>
				<display:column property="tAsr" titleKey="T_ASR" sortable="true" headerClass="master-header-2" /> 
			    <display:column property ="tTraf" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="T_TRAF" class="T_TRAF" sortable="true"  headerClass="master-header-2"/>
			    <display:column property ="tDrpr" titleKey="T_DRPR" class="T_DRPR" sortable="true"  headerClass="master-header-2"/>
			    <display:column property ="tEmpdr" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="T_EMPDR" sortable="true"  headerClass="master-header-2"/>
			    <display:column property="haftratePercent" titleKey="HALFRATE" sortable="true" class="margin" headerClass="master-header-2"/>
			    <display:column property ="sDef" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="S_DEF" sortable="true"  headerClass="master-header-3"/>
			    <display:column property="sSsr" titleKey="S_SSR" sortable="true"  headerClass="master-header-3"/>
			    <display:column property="sBlkr" titleKey="S_BLKR" class="S_BLKR" sortable="true"  headerClass="master-header-3"/>
			    <display:column property ="sDrpr" titleKey="S_DRPR" class="S_DRPR" sortable="true"  headerClass="master-header-3"/>


			    <%-- <display:column property ="inHoSucr" titleKey="IN_HO_SUCR" sortable="true"/>
			    <display:column property="ogHoSucr" titleKey="OG_HO_SUCR" sortable="true"/>
			     --%>
			     <display:column property="downtime" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="DOWNTIME"  sortable="true" class="margin" headerClass="margin"/>
			    <display:column property ="dataload" titleKey="DATALOAD" sortable="true"/>
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
		${highlight}
		<c:if test="${empty district}">
			$($('#vRpDyDistrict > tbody tr')[0]).before('<c:forEach var="vRpDyRegion" items="${vRpDyRegionList}"><tr class="${vRpDyRegion.region}"><td><fmt:formatDate pattern="dd/MM/yyyy" value="${vRpDyRegion.day}"/></td><td>${vRpDyRegion.region}</td><td/><td/><td><fmt:formatNumber pattern="#,###,###,##0.##" value="${vRpDyRegion.sites}"/></td><td><fmt:formatNumber pattern="#,###,###,##0.##" value="${vRpDyRegion.cells}"/></td><td class="margin"><fmt:formatNumber pattern="#,###,###,##0.##" value="${vRpDyRegion.trxs}"/></td><td><fmt:formatNumber pattern="#,###,###,##0.##" value="${vRpDyRegion.tDef}"/></td><td><fmt:formatNumber pattern="#,###,###,##0.##" value="${vRpDyRegion.tAvail}"/></td><td><fmt:formatNumber pattern="#,###,###,##0.##" value="${vRpDyRegion.tTraf}"/></td><td>${vRpDyRegion.tBlkr}</td><td>${vRpDyRegion.tBlks}</td><td>${vRpDyRegion.tHoblkr}</td><td>${vRpDyRegion.tHoblks}</td><td>${vRpDyRegion.cssr}</td><td>${vRpDyRegion.tAsr}</td><td>${vRpDyRegion.tTraf}</td><td>${vRpDyRegion.tDrpr}</td><td>${vRpDyRegion.tEmpdr}</td><td class="margin">${vRpDyRegion.cssr}</td><td><fmt:formatNumber pattern="#,###,###,##0.##" value="${vRpDyRegion.sDef}"/></td><td>${vRpDyRegion.sSsr}</td><td>${vRpDyRegion.sBlkr}</td><td>${vRpDyRegion.sDrpr}</td><td class="margin"/><td>${vRpDyRegion.dataload}</td></tr></c:forEach>');
		</c:if>
	});
</script>
