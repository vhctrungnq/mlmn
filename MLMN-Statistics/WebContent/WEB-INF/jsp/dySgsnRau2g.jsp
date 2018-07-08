<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<title>SGSN DAY REPORT</title>
<content tag="heading">SGSN RAU 2G ${title} REPORT</content>    
<%@ include file="/WEB-INF/jsp/includes/filterCsCore.jsp" %>
<div  id="doublescroll">
		<display:table name="${dySgsnList}" id="dySgsnList" requestURI="" pagesize="100" class="simple2" export="true">
		    <%@ include file="/WEB-INF/jsp/includes/columnGeneralPs.jsp" %>
		     <c:choose> 
			 	<c:when test="${linkReport=='sgsn-rau-2g'}">
			 		<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="interRauSr"  titleKey="Inter Rau Sr2G(%)"  sortable="true" />
			 		<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="suceessInterRau2g"  titleKey="SuceessInterRau2g"  sortable="true" />
					<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="requestInterSgsnRau2g"  titleKey="RequestInterSgsnRau2g"  sortable="true" />
					<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="ineRauFail2g"  titleKey="IneRauFail2g"  sortable="true" />
					<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="ineRauIllegalMs2g"  titleKey="IneRauIllegalMs2g"  sortable="true" />
					<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="ineRauIllegalMe2g"  titleKey="IneRauIllegalMe2g"  sortable="true" />
					<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="ineRauSimNotProv2g"  titleKey="IneRauSimNotProv2g"  sortable="true" />
					<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="ineRauSerNonserNa2g"  titleKey="IneRauSerNonserNa2g"  sortable="true" />
					<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="ineRauMsIdentifyFail2g"  titleKey="IneRauMsIdentifyFail2g"  sortable="true" />
					<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="ineRauMsImpDetach2g"  titleKey="IneRauMsImpDetach2g"  sortable="true" />
					<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="ineRauPlmnNa2g"  titleKey="IneRauPlmnNa2g"  sortable="true" />
					<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="ineRauLaNa2g"  titleKey="IneRauLaNa2g"  sortable="true" />
					<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="ineRauRoamingNa2g"  titleKey="IneRauRoamingNa2g"  sortable="true" />
					<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="ineRauSerNaPlmn2g"  titleKey="IneRauSerNaPlmn2g"  sortable="true" />
					<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="ineRauNoCellInLa2g"  titleKey="IneRauNoCellInLa2g"  sortable="true" />
					<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="ineRauProErr2g"  titleKey="IneRauProErr2g"  sortable="true" />
					<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="ineRauOtherCause2g"  titleKey="IneRauOtherCause2g"  sortable="true" />
					<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="ineRauNetworkFail2g"  titleKey="IneRauNetworkFail2g"  sortable="true" />
					<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="ineRauProMsErr2g"  titleKey="IneRauProMsErr2g"  sortable="true" />
					<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="ineRauProRadioErr2g"  titleKey="IneRauProRadioErr2g"  sortable="true" />
					<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="ineRauProSgsnErr2g"  titleKey="IneRauProSgsnErr2g"  sortable="true" />
					<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="ineRauProCollisions2g"  titleKey="IneRauProCollisions2g"  sortable="true" />
					<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="ineRauProHlrVlrErr2g"  titleKey="IneRauProHlrVlrErr2g"  sortable="true" />
					<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="fOutgInterSgsnRaUpdat2g"  titleKey="FOutgInterSgsnRaUpdat2g"  sortable="true" />
					<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="fInterSgsnRaUpdatImsi2g"  titleKey="FInterSgsnRaUpdatImsi2g"  sortable="true" />

			    </c:when>
			   <c:when test="${linkReport=='sgsn-rau-2g-intra-pp-inter'}">
			        <display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="interPpSgsnRauSr2g"  titleKey="Inter PP Rau Sr2g(%)"  sortable="true" />
					<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="successInterPpSgsnRau2g"  titleKey="SuccessInterPpSgsnRau2g"  sortable="true" />
					<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="requestInterPpSgsnRau2g"  titleKey="RequestInterPpSgsnRau2g"  sortable="true" />
					<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="inePpRauFail2g"  titleKey="InePpRauFail2g"  sortable="true" />
					<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="inePpRauIllegalMs2g"  titleKey="InePpRauIllegalMs2g"  sortable="true" />
					<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="inePpRauIllegalMe2g"  titleKey="InePpRauIllegalMe2g"  sortable="true" />
					<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="inePpRauSimNotProv2g"  titleKey="InePpRauSimNotProv2g"  sortable="true" />
					<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="inePpRauSerNonserNa2g"  titleKey="InePpRauSerNonserNa2g"  sortable="true" />
					<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="inePpRauMsIdentifyFail2g"  titleKey="InePpRauMsIdentifyFail2g"  sortable="true" />
					<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="inePpRauMsImpDetach2g"  titleKey="InePpRauMsImpDetach2g"  sortable="true" />
					<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="inePpRauPlmnNa2g"  titleKey="InePpRauPlmnNa2g"  sortable="true" />
					<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="inePpRauLaNa2g"  titleKey="InePpRauLaNa2g"  sortable="true" />
					<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="inePpRauRoamingNa2g"  titleKey="InePpRauRoamingNa2g"  sortable="true" />
					<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="inePpRauSerNaPlmn2g"  titleKey="InePpRauSerNaPlmn2g"  sortable="true" />
					<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="inePpRauNoCellInLa2g"  titleKey="InePpRauNoCellInLa2g"  sortable="true" />
					<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="inePpRauProErr2g"  titleKey="InePpRauProErr2g"  sortable="true" />
					<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="inePpRauOtherCause2g"  titleKey="InePpRauOtherCause2g"  sortable="true" />
					<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="inePpRauNetworkFail2g"  titleKey="InePpRauNetworkFail2g"  sortable="true" />
					<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="inePpRauProMsErr2g"  titleKey="InePpRauProMsErr2g"  sortable="true" />
					<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="inePpRauProRadioErr2g"  titleKey="InePpRauProRadioErr2g"  sortable="true" />
					<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="inePpRauProSgsnErr2g"  titleKey="InePpRauProSgsnErr2g"  sortable="true" />
					<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="inePpRauProCollisions2g"  titleKey="InePpRauProCollisions2g"  sortable="true" />
					<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="inePpRauProHlrVlrErr2g"  titleKey="InePpRauProHlrVlrErr2g"  sortable="true" />
				    <display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="fOutgInterPapuRaUpdat2g"  titleKey="FOutgInterPapuRaUpdat2g"  sortable="true" />
					<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="iuFOgIntePapu2g3gSho2g"  titleKey="IuFOgIntePapu2g3gSho2g"  sortable="true" />
					<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="fInterPapuRaUpdatImsi2g"  titleKey="FInterPapuRaUpdatImsi2g"  sortable="true" />
					
			    </c:when>
			    <c:otherwise>
					<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="intraPpRauSr2g"  titleKey="Intra PP Rau Sr2g(%)"  sortable="true" />
					<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="successIntraPpRau2g"  titleKey="SuccessIntraPpRau2g"  sortable="true" />
					<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="requestIntraPpRau2g"  titleKey="RequestIntraPpRau2g"  sortable="true" />
					<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="inaPpRauPeriRoutSucr2g"  titleKey="Periodic Routing sr(%)"  sortable="true" />
					<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="inaPpRauPeriRoutSucc2g"  titleKey="InaPpRauPeriRoutSucc2g"  sortable="true" />
					<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="inaPpRauPeriRoutReq2g"  titleKey="InaPpRauPeriRoutReq2g"  sortable="true" />
					<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="inaPpRauFail2g"  titleKey="InaPpRauFail2g"  sortable="true" />
					<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="inaPpRauIllegalMs2g"  titleKey="InaPpRauIllegalMs2g"  sortable="true" />
					<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="inaPpRauIllegalMe2g"  titleKey="InaPpRauIllegalMe2g"  sortable="true" />
					<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="inaPpRauSimNotProv2g"  titleKey="InaPpRauSimNotProv2g"  sortable="true" />
					<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="inaPpRauSerNonserNa2g"  titleKey="InaPpRauSerNonserNa2g"  sortable="true" />
					<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="inaPpRauMsIdentifyFail2g"  titleKey="InaPpRauMsIdentifyFail2g"  sortable="true" />
					<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="inaPpRauMsImpDetach2g"  titleKey="InaPpRauMsImpDetach2g"  sortable="true" />
					<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="inaPpRauPlmnNa2g"  titleKey="InaPpRauPlmnNa2g"  sortable="true" />
					<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="inaPpRauLaNa2g"  titleKey="InaPpRauLaNa2g"  sortable="true" />
					<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="inaPpRauRoamingNa2g"  titleKey="InaPpRauRoamingNa2g"  sortable="true" />
					<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="inaPpRauSerNaPlmn2g"  titleKey="InaPpRauSerNaPlmn2g"  sortable="true" />
					<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="inaPpRauNoCellInLa2g"  titleKey="InaPpRauNoCellInLa2g"  sortable="true" />
					<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="inaPpRauProErr2g"  titleKey="InaPpRauProErr2g"  sortable="true" />
					<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="inaPpRauOtherCause2g"  titleKey="InaPpRauOtherCause2g"  sortable="true" />
					<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="inaPpRauNetworkFail2g"  titleKey="InaPpRauNetworkFail2g"  sortable="true" />
					<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="inaPpRauProMsErr2g"  titleKey="InaPpRauProMsErr2g"  sortable="true" />
					<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="inaPpRauProRadioErr2g"  titleKey="InaPpRauProRadioErr2g"  sortable="true" />
					<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="inaPpRauProSgsnErr2g"  titleKey="InaPpRauProSgsnErr2g"  sortable="true" />
					<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="inaPpRauProCollisions2g"  titleKey="InaPpRauProCollisions2g"  sortable="true" />
					<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="inaPpRauProHlrVlrErr2g"  titleKey="InaPpRauProHlrVlrErr2g"  sortable="true" />
					<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="iuFOgIntrPapu2g3gSho2g"  titleKey="IuFOgIntrPapu2g3gSho2g"  sortable="true" />
					<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="fIntraPapuRaUpdatImsi2g"  titleKey="FIntraPapuRaUpdatImsi2g"  sortable="true" />
				</c:otherwise>
			</c:choose>
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
