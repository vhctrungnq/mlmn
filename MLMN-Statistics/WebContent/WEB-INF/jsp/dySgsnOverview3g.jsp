<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<title>SGSN DAY REPORT</title>
<content tag="heading">SGSN OVERVIEW 3G ${title} REPORT</content>    
<%@ include file="/WEB-INF/jsp/includes/filterCsCore.jsp" %>
<div  id="doublescroll">
		<display:table name="${dySgsnList}" id="dySgsnList" requestURI="" pagesize="100" class="simple2" style="width:200%"  export="true">
		    <%@ include file="/WEB-INF/jsp/includes/columnGeneralPs.jsp" %>
		    <display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="utilSubs3g"  titleKey="Util Subs(%)"  sortable="true" />
			<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="utilPdpContext3g"  titleKey="Ultil PDP Context"  sortable="true" />
			<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="attachedSub3g"  titleKey="Attached Sub"  sortable="true" />
			<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="attachSucr3g"  titleKey="Attach Sucr include All Cause(%)"  sortable="true" />
			<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="attachSrDropCauses3g"  titleKey="Attach Sucr(%)"  sortable="true" />
			<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="actPdp3g"  titleKey="Act PDP"  sortable="true" />
			<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="pssr3g"  titleKey="PSSR Include All Cause(%)"  sortable="true" />
			<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="pssrDropCauses3g"  titleKey="PSSR(%)"  sortable="true"   headerClass="master-header-2"/>
			<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="userTrafGb3g"  titleKey="User Traf GB(MB)"  sortable="true" />
			<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="thpGb3g"  titleKey="THP Gb(Mbit/s)"  sortable="true" />
			<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="utilThp3g"  titleKey="Util THP(%)"  sortable="true" />
			<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="intraRauSucr3g"  titleKey="Intra Rau Sucr Include All Cause(%)"  sortable="true" />
			<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="intraRauSucrDropCauses3g"  titleKey="Intra Rau Sucr(%)"  sortable="true"   headerClass="master-header-2"/>
			<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="intraPpRauSucr3g"  titleKey="Intra PP Rau Sucr Include All Cause(%)"  sortable="true" />
			<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="intraPpRauSucrDropCau3g"  titleKey="Intra PP Rau Sucr(%)"  sortable="true" />

			<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="interPpRauSucr3g"  titleKey="Inter PP Rau Sucr Include All Cause(%)"  sortable="true" />
			<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="interPpRauSucrDropCau3g"  titleKey="Inter Papu Rau Sucr(%)"  sortable="true" />
			<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="interRauSucr3g"  titleKey="Inter Rau Sucr Include All Cause(%)"  sortable="true" />
			<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="interRauSucrDropCauses3g"  titleKey="Inter Rau Sucr(%)"  sortable="true" />
			<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="authSucr3g"  titleKey="Authen Sucr(%)"  sortable="true"   headerClass="master-header-2"/>
			<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="pagingSucr3g"  titleKey="Paging Sucr(%)"  sortable="true"   headerClass="master-header-2"/>
			<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="loadAvg"  titleKey="Load Avg"  sortable="true" />
			<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="attDetach3g"  titleKey="Att Detact"  sortable="true" />
			<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="nwAttDetach3g"  titleKey="Nw Att Detact"  sortable="true" />
			<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="msActPdp"  titleKey="Ms Act Pdp"  sortable="true" />
			<c:if test="${level != 'hr'}">
				<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="grandTotal"  titleKey="GrandTotal"  sortable="true" headerClass="master-header-3"/>
				<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="subTotal"  titleKey="SubTotal"  sortable="true" headerClass="master-header-3"/>
			</c:if>
			</display:table>
	</div>
 <c:choose> 
 	<c:when test="${level=='hr'}">
        <%@ include file="/WEB-INF/jsp/includes/scriptHourly.jsp" %>
    </c:when>
    <c:when test="${level=='dy'}">
        <%@ include file="/WEB-INF/jsp/includes/scriptDaily.jsp" %>
    </c:when>
    <c:when test="${level=='wk'}">
         <%@ include file="/WEB-INF/jsp/includes/scriptWeekly.jsp" %>
    </c:when>
    <c:otherwise>
        <%@ include file="/WEB-INF/jsp/includes/scriptMonthly.jsp" %>
    </c:otherwise>
</c:choose>
