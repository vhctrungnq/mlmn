<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<style type="text/css">    #doublescroll { overflow: auto; overflow-y: hidden; }    #doublescroll p { margin: 0; padding: 1em; white-space: nowrap; }</style><title>Báo cáo District QoS 3G</title>
<content tag="heading">QOS 3G DISTRICT REPORT</content>
<ul class="ui-tabs-nav">
	<li class=""><a href="${pageContext.request.contextPath}/report/radio3g/district/dy/list.htm"><span>Báo cáo ngày</span></a></li>
	<li class=""><a href="${pageContext.request.contextPath}/report/radio3g/district/wk/list.htm"><span>Báo cáo tuần</span></a></li>
	<li class=""><a href="${pageContext.request.contextPath}/report/radio3g/district/mn/list.htm"><span>Báo cáo tháng</span></a></li>
	<li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/report/radio3g/district/qr/list.htm"><span>Báo cáo quý</span></a></li>
	<li class=""><a href="${pageContext.request.contextPath}/report/radio3g/district/yr/list.htm"><span>Báo cáo năm</span></a></li>
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
			    	</select> &nbsp;PROVINCE <select name="province">
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
					</select> &nbsp;DISTRICT <input value="${district}" name="district" id="district" size="10" maxlength="50" onchange="xl()">
					&nbsp;Từ quý <input value="${startQuarter}" name="startQuarter" id="startQuarter" size="1" maxlength="1">
	            	&nbsp;Năm <input value="${startYear}" name="startYear" id="startYear" size="4" maxlength="4">
	            	&nbsp;Tới quý <input value="${endQuarter}" name="endQuarter" id="endQuarter" size="1" maxlength="1">
	            	&nbsp;Năm <input value="${endYear}" name="endYear" id="endYear" size="4" maxlength="4">
					&nbsp;<input type="submit" class="button" name="submit" value="View Report" />
				</form>
			</td>
		</tr>
	</table>
	<br />
<div  style="overflow: auto;" class="tableStandar" id = "doublescroll">
	<display:table name="${vRpQrDistrict3G}" id="item" requestURI="" pagesize="100" class="simple2" export="true" sort="list">
	    <display:column property="quarter" titleKey="QUARTER" sortable="true" headerClass="master-header-1"/>
	    <display:column property="year" titleKey="YEAR" sortable="true" headerClass="master-header-1"/>
	    <display:column property="region" titleKey="TT" sortable="true" headerClass="master-header-1"/>
	    <display:column property="province" titleKey="Province" sortable="true"/>
	    <display:column property="district" titleKey="District" sortable="true"/>
	    <display:column property="sites" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="Sites" headerClass="master-header-1" sortable="true"/>
	    <display:column property="cells" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="Cells" sortable="true" />
	    <display:column property="speechTraff" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="SPEECH_TRAFF" class="SPEECH_TRAFF"  sortable="true" headerClass="master-header-3"/>
	    <display:column property="cs64Traff" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="CS64_TRAFF" sortable="true" headerClass="master-header-3"/>
	    <display:column property="psr99UlTraff" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="PSR99_UL_TRAFF"  sortable="true" headerClass="master-header-3"/>
	    <display:column property="psr99DlTraff" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="PSR99_DL_TRAFF"  sortable="true" headerClass="master-header-3"/>
	    <display:column property="hsupaUlTraff" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="HSUPA_UL_TRAFF"  sortable="true" headerClass="master-header-3"/>
	    <display:column property ="hsdpaDlTraff" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="HSDPA_DL_TRAFF"  sortable="true" headerClass="master-header-3 margin" class="margin"/>
	    <display:column property="speechCssr" titleKey="SPEECH_CSSR" class="SPEECH_CSSR" sortable="true" headerClass="master-header-4"/>
	    <display:column property ="cs64Cssr" titleKey="CS64_CSSR" class="CS64_CSSR" sortable="true" headerClass="master-header-4"/>
	    <display:column property="psr99Cssr" titleKey="PSR99_CSSR" class="PSR99_CSSR" sortable="true" headerClass="master-header-4"/>			    
	    <display:column property ="hsupaCssr" titleKey="HSUPA_CSSR" class="HSUPA_CSSR" sortable="true" headerClass="master-header-4"/>
	    <display:column property="hsdpaCssr" titleKey="HSDPA_CSSR" class="HSDPA_CSSR margin" sortable="true" headerClass="master-header-4 margin"/>
	    <display:column property ="speechDrpr" titleKey="SPEECH_DRPR" class="SPEECH_DRPR" sortable="true" headerClass="master-header-5"/>
	    <display:column property="cs64Drpr" titleKey="CS64_DRPR" class="CS64_DRPR" sortable="true" headerClass="master-header-5"/>
   		<display:column property="psr99Drpr" titleKey="PSR99_DRPR" class="PSR99_DRPR" sortable="true" headerClass="master-header-5"/>
   		<display:column property ="hsupaDrpr" titleKey="HSUPA_DRPR" class="HSUPA_DRPR" sortable="true" headerClass="master-header-5"/>
   		<display:column property="hsdpaDrpr" titleKey="HSDPA_DRPR" class="HSDPA_DRPR margin" sortable="true" headerClass="master-header-5 margin"/>
   		<display:column property="psr99UlThroughtput" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="PSR99_UL_THROUGHTPUT" sortable="true" headerClass="master-header-3"/>
	    <display:column property="psr99DlThroughtput" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="PSR99_DL_THROUGHTPUT" sortable="true" headerClass="master-header-3"/>
	    <display:column property="hsupaUlThroughtput" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="HSUPA_UL_THROUGHTPUT" sortable="true" headerClass="master-header-3"/>
	    <display:column property="hsdpaDlThroughtput" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="HSDPA_DL_THROUGHTPUT" sortable="true" headerClass="master-header-3 margin" class="margin"/>
   		<display:column property="csIratHoSr" titleKey="CS_IRAT_HO_SR" sortable="true" headerClass="master-header-6"/>
   		<display:column property ="psIratHoSr" titleKey="PS_IRAT_HO_SR" sortable="true" headerClass="master-header-6"/>
   		<display:column property="shoSrIn" titleKey="SHO_SR_IN" sortable="true" headerClass="master-header-6"/>
   		<display:column property="shoSrOut" titleKey="SHO_SR_OUT" sortable="true" headerClass="master-header-6 margin" class="margin"/>
   		<display:column property ="cellDowntime" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="CELL_DOWNTIME" sortable="true" headerClass="master-header-2"/>
	    <display:column property ="hsDowntime" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="HS_DOWNTIME" sortable="true" headerClass="master-header-2"/>
	    <display:column property ="eulDowntime" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="EUL_DOWNTIME" sortable="true" headerClass="master-header-2 margin" class="margin"/>
	    <display:column property ="dataload" titleKey="DATALOAD" sortable="true"/>
	    
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