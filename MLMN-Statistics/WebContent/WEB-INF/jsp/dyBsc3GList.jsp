<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<style type="text/css">    #doublescroll { overflow: auto; overflow-y: hidden; }    #doublescroll p { margin: 0; padding: 1em; white-space: nowrap; }</style><title>Báo cáo RNC QoS 3G</title>
<content tag="heading">RNC QOS 3G DAILY REPORT</content>
<ul class="ui-tabs-nav">
	<li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/report/radio3g/rnc/dy/list.htm"><span>Báo cáo ngày</span></a></li>
	<li class=""><a href="${pageContext.request.contextPath}/report/radio3g/rnc/wk/list.htm"><span>Báo cáo tuần</span></a></li>
	<li class=""><a href="${pageContext.request.contextPath}/report/radio3g/rnc/mn/list.htm"><span>Báo cáo tháng</span></a></li>
	<li class=""><a href="${pageContext.request.contextPath}/report/radio3g/rnc/qr/list.htm"><span>Báo cáo quý</span></a></li>
	<li class=""><a href="${pageContext.request.contextPath}/report/radio3g/rnc/yr/list.htm"><span>Báo cáo năm</span></a></li>
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
			        &nbsp;RNC 
			        <select name="bscid" id="bscid" style="width: 163px">
			        <option value="">--Select RNC--</option>
				        <c:forEach var="bsc" items="${bscList}">
			              <c:choose>
			                <c:when test="${bsc == bscid}">
			                    <option value="${bsc}" selected="selected">${bsc}</option>
			                </c:when>
			                <c:otherwise>
			                    <option value="${bsc}">${bsc}</option>
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
			<display:table style = "width: 200%" name="${vRpDyBsc}" id="vRpDyBsc" requestURI="" pagesize="100"  export="true" sort="list">
				<display:column property="day" format="{0,date,dd/MM/yyyy}" titleKey="Date" sortable="true"  headerClass="master-header-1"/>		    
			    <display:column property="region" titleKey="Center" sortable="true" headerClass="master-header-1"/>
			    <display:column property="bscid" titleKey="RNC" sortable="true" headerClass="hide" class="hide"/>
			    <display:column titleKey="RNC" media="html" sortable="true" headerClass="master-header-1" sortProperty="bscid">
			    	<a href="${pageContext.request.contextPath}/report/radio3g/rnc/hr/detail.htm?bscid=${vRpDyBsc.bscid}&startDate=<fmt:formatDate pattern="dd/MM/yyyy" value="${vRpDyBsc.day}"/>&endDate=<fmt:formatDate pattern="dd/MM/yyyy" value="${vRpDyBsc.day}"/>">${vRpDyBsc.bscid}</a>
			    </display:column>
			    <display:column property="sites" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="Sites" sortable="true"  headerClass="master-header-1"/>
			    <display:column property="cells" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="Cells" sortable="true" headerClass="hide" class="hide"/>
			    <display:column titleKey="Cells" media="html" sortable="true"  headerClass="master-header-1 margin" class="margin" sortProperty="cells">
			    	<a href="${pageContext.request.contextPath}/report/radio3g/cell/dy/list.htm?bscid=${vRpDyBsc.bscid}&day=<fmt:formatDate pattern="dd/MM/yyyy" value="${vRpDyBsc.day}"/>"><fmt:formatNumber pattern="#,###,###,##0.##" value="${vRpDyBsc.cells}"/></a>
			    </display:column>
                <display:column property="speechCssr" titleKey="SPEECH_CSSR" sortable="true" headerClass="hide" class="hide"/>
			    <display:column titleKey="SPEECH_CSSR" class="SPEECH_CSSR" sortable="true" headerClass="master-header-4" media="html" sortProperty="speechCssr">
                    <a href="${pageContext.request.contextPath}/report/radio3g/rnc/cssr/dy.htm?bscid=${vRpDyBsc.bscid}&endDate=<fmt:formatDate pattern="dd/MM/yyyy" value="${vRpDyBsc.day}"/>">${vRpDyBsc.speechCssr}</a>
                </display:column>
                <display:column property="speechDrpr" titleKey="SPEECH_DRPR" sortable="true" headerClass="hide" class="hide"/>
                <display:column titleKey="SPEECH_DRPR" class="SPEECH_DRPR" sortable="true" headerClass="master-header-5" media="html" sortProperty="speechDrpr">
                    <a href="${pageContext.request.contextPath}/report/radio3g/rnc/drop/dy.htm?bscid=${vRpDyBsc.bscid}&endDate=<fmt:formatDate pattern="dd/MM/yyyy" value="${vRpDyBsc.day}"/>">${vRpDyBsc.speechDrpr}</a>
                </display:column>
                <display:column property ="pasr3g" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" 
                            titleKey="PASR3G" sortable="true" headerClass="master-header-4"/>
                <display:column property ="padr3g" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator"
                      titleKey="PADR3G" sortable="true" headerClass="master-header-5"/>  
			    <display:column property="speechTraff" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="SPEECH_TRAFF" class="SPEECH_TRAFF"  sortable="true" headerClass="master-header-3"/>
			    <display:column property="cs64Traff" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="CS64_TRAFF" sortable="true" headerClass="master-header-3"/>
			    <display:column property="psr99UlTraff" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="PSR99_UL_TRAFF"  sortable="true" headerClass="master-header-3"/>
			    <display:column property="psr99DlTraff" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="PSR99_DL_TRAFF"  sortable="true" headerClass="master-header-3"/>
			    <display:column property="hsupaUlTraff" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="HSUPA_UL_TRAFF"  sortable="true" headerClass="master-header-3"/>
			    <display:column property ="hsdpaDlTraff" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="HSDPA_DL_TRAFF"  sortable="true" headerClass="master-header-3 margin" class="margin"/>
			    <display:column property="speechCssr" titleKey="SPEECH_CSSR" sortable="true" headerClass="hide" class="hide"/>
			    
			    <display:column property ="cs64Cssr" titleKey="CS64_CSSR" class="CS64_CSSR" sortable="true" headerClass="master-header-4"/>
			    <display:column property="psr99Cssr" titleKey="PSR99_CSSR" class="PSR99_CSSR" sortable="true" headerClass="master-header-4"/>			    
			    <display:column property ="hsupaCssr" titleKey="HSUPA_CSSR" class="HSUPA_CSSR" sortable="true" headerClass="master-header-4"/>
			    <display:column property="hsdpaCssr" titleKey="HSDPA_CSSR" class="HSDPA_CSSR margin" sortable="true" headerClass="master-header-4 margin"/>
			 
			    <display:column property="cs64Drpr" titleKey="CS64_DRPR" class="CS64_DRPR" sortable="true" headerClass="master-header-5"/>
				<display:column property="psr99Drpr" titleKey="PSR99_DRPR" class="PSR99_DRPR" sortable="true" headerClass="master-header-5"/>
				<display:column property ="hsupaDrpr" titleKey="HSUPA_DRPR" class="HSUPA_DRPR" sortable="true" headerClass="master-header-5"/>
				<display:column property="hsdpaDrpr" titleKey="HSDPA_DRPR" class="HSDPA_DRPR margin" sortable="true" headerClass="master-header-5 margin"/>
			    <display:column property="psr99UlThroughtput" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="PSR99_UL_THROUGHTPUT" sortable="true" headerClass="master-header-3"/>
			    <display:column property="psr99DlThroughtput" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="PSR99_DL_THROUGHTPUT" sortable="true" headerClass="master-header-3"/>
			    <display:column property="hsupaUlThroughtput" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="HSUPA_UL_THROUGHTPUT" sortable="true" headerClass="master-header-3"/>
			    <display:column property="hsdpaDlThroughtput" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="HSDPA_DL_THROUGHTPUT" sortable="true" headerClass="master-header-3 margin" class="margin"/>
				<%-- <display:column property="speechShoSrOut" titleKey="SPEECH_SHO_SR_OUT" sortable="true" headerClass="master-header-6"/>
				<display:column property="shoSrOut" titleKey="SHO_SR_OUT" sortable="true" headerClass="master-header-6"/>
				<display:column property="shoSrIn" titleKey="SHO_SR_IN" sortable="true" headerClass="master-header-6"/> --%>
				<display:column property="csIratHoSr" titleKey="CS_IRAT_HO_SR" sortable="true" headerClass="master-header-6"/>
				<display:column property ="psIratHoSr" titleKey="PS_IRAT_HO_SR" sortable="true" headerClass="master-header-6"/>
				<display:column property ="softHsSr" titleKey="SOFT_HS_SR" sortable="true" headerClass="master-header-6 margin" class="margin"/>
<%-- 			    <display:column property ="cellDowntime" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="CELL_DOWNTIME" sortable="true" headerClass="master-header-2"/>
			    <display:column property ="hsDowntime" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="HS_DOWNTIME" sortable="true" headerClass="master-header-2"/>
			    <display:column property ="eulDowntime" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="EUL_DOWNTIME" sortable="true" headerClass="master-header-2 margin" class="margin"/> --%>
			    <display:column property ="totBadCell" titleKey="BAD_CELL" sortable="true"/>
			    <display:column property ="badCellR" titleKey="BAD_CELL_R" sortable="true" headerClass="margin" class="margin"/>
			    <display:column property ="dataload" titleKey="DATALOAD" sortable="true"/>
			</display:table>
			</div>
	</div>
	</div>
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
		
		<c:if test="${empty bscid}">
		$($('#vRpDyBsc > tbody tr')[0]).before('<c:forEach var="vRpDyRegion" items="${vRpDyRegionList}"><tr class="${vRpDyRegion.region}"><td><fmt:formatDate pattern="dd/MM/yyyy" value="${vRpDyRegion.day}"/></td><td>${vRpDyRegion.region}</td><td/><td><fmt:formatNumber pattern="#,###,###,##0.##" value="${vRpDyRegion.sites}"/></td><td class="margin"><fmt:formatNumber pattern="#,###,###,##0.##" value="${vRpDyRegion.cells}"/></td><td><fmt:formatNumber pattern="#,###,###,##0.##" value="${vRpDyRegion.speechTraff}"/></td><td><fmt:formatNumber pattern="#,###,###,##0.##" value="${vRpDyRegion.cs64Traff}"/></td><td><fmt:formatNumber pattern="#,###,###,##0.##" value="${vRpDyRegion.psr99UlTraff}"/></td><td><fmt:formatNumber pattern="#,###,###,##0.##" value="${vRpDyRegion.psr99DlTraff}"/></td><td><fmt:formatNumber pattern="#,###,###,##0.##" value="${vRpDyRegion.hsupaUlTraff}"/></td><td class ="margin"><fmt:formatNumber pattern="#,###,###,##0.##" value="${vRpDyRegion.hsdpaDlTraff}"/></td><td>${vRpDyRegion.speechCssr}</td><td>${vRpDyRegion.cs64Cssr}</td><td>${vRpDyRegion.psr99Cssr}</td><td>${vRpDyRegion.hsupaCssr}</td><td class="margin">${vRpDyRegion.hsdpaCssr}</td><td>${vRpDyRegion.speechDrpr}</td><td>${vRpDyRegion.cs64Drpr}</td><td>${vRpDyRegion.psr99Drpr}</td><td>${vRpDyRegion.hsupaDrpr}</td><td class="margin">${vRpDyRegion.hsdpaDrpr}</td><td><fmt:formatNumber pattern="#,###,###,##0.##" value="${vRpDyRegion.psr99UlThroughtput}"/></td><td>${vRpDyRegion.psr99DlThroughtput}</td><td><fmt:formatNumber pattern="#,###,###,##0.##" value="${vRpDyRegion.hsupaUlThroughtput}"/></td><td class="margin"><fmt:formatNumber pattern="#,###,###,##0.##" value="${vRpDyRegion.hsdpaDlThroughtput}"/></td><td>${vRpDyRegion.csIratHoSr}</td><td>${vRpDyRegion.psIratHoSr}</td><td class="margin"></td><td><fmt:formatNumber pattern="#,###,###,##0.##" value="${vRpDyRegion.cellDowntime}"/></td><td><fmt:formatNumber pattern="#,###,###,##0.##" value="${vRpDyRegion.hsDowntime}"/></td><td class="margin"><fmt:formatNumber pattern="#,###,###,##0.##" value="${vRpDyRegion.eulDowntime}"/></td><td>${vRpDyRegion.totBadCell}</td><td>${vRpDyRegion.badCellR}</td></tr></c:forEach>');
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