<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<title>SGSN DAY REPORT</title>
<content tag="heading">SGSN RAU ${title} REPORT</content>    
<%@ include file="/WEB-INF/jsp/includes/filterCsCore.jsp" %>
<div  id="doublescroll">
		<display:table name="${dySgsnList}" id="dySgsnList" requestURI="" pagesize="100" class="simple2" export="true">
		    <%@ include file="/WEB-INF/jsp/includes/columnGeneralPs.jsp" %>
		     <c:choose> 
			 	<c:when test="${linkReport=='sgsn-rau'}">
					<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="interRauSr"  titleKey="Inter Rau Sr(%)"  sortable="true" />
					<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="suceessInterRau"  titleKey="SuceessInterRau"  sortable="true" />
					<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="requestInterSgsnRau"  titleKey="RequestInterSgsnRau"  sortable="true" />
					<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="ineRauFail"  titleKey="IneRauFail"  sortable="true" />
					<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="ineRauIllegalMs"  titleKey="IneRauIllegalMs"  sortable="true" />
					<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="ineRauIllegalMe"  titleKey="IneRauIllegalMe"  sortable="true" />
					<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="ineRauSimNotProv"  titleKey="IneRauSimNotProv"  sortable="true" />
					<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="ineRauSerNonserNa"  titleKey="IneRauSerNonserNa"  sortable="true" />
					<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="ineRauMsIdentifyFail"  titleKey="IneRauMsIdentifyFail"  sortable="true" />
					<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="ineRauMsImpDetach"  titleKey="IneRauMsImpDetach"  sortable="true" />
					<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="ineRauPlmnNa"  titleKey="IneRauPlmnNa"  sortable="true" />
					<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="ineRauLaNa"  titleKey="IneRauLaNa"  sortable="true" />
					<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="ineRauRoamingNa"  titleKey="IneRauRoamingNa"  sortable="true" />
					<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="ineRauSerNaPlmn"  titleKey="IneRauSerNaPlmn"  sortable="true" />
					<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="ineRauNoCellInLa"  titleKey="IneRauNoCellInLa"  sortable="true" />
					<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="ineRauProErr"  titleKey="IneRauProErr"  sortable="true" />
					<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="ineRauOtherCause"  titleKey="IneRauOtherCause"  sortable="true" />
					<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="ineRauNetworkFail"  titleKey="IneRauNetworkFail"  sortable="true" />
					<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="ineRauProMsErr"  titleKey="IneRauProMsErr"  sortable="true" />
					<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="ineRauProRadioErr"  titleKey="IneRauProRadioErr"  sortable="true" />
					<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="ineRauProSgsnErr"  titleKey="IneRauProSgsnErr"  sortable="true" />
					<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="ineRauProCollisions"  titleKey="IneRauProCollisions"  sortable="true" />
					<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="ineRauProHlrVlrErr"  titleKey="IneRauProHlrVlrErr"  sortable="true" />
					<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="fOutgInterSgsnRaUpdat2g"  titleKey="FOutgInterSgsnRaUpdat2g"  sortable="true" />
					<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="fInterSgsnRaUpdatImsi2g"  titleKey="FInterSgsnRaUpdatImsi2g"  sortable="true" />
					<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="iuFOgIntrPapu2g3gSho2g"  titleKey="IuFOgIntrPapu2g3gSho2g"  sortable="true" />
					<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="iuFOgInteSgsn3g2gIsho3g"  titleKey="IuFOgInteSgsn3g2gIsho3g"  sortable="true" />
					<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="iuFOgInterSgsnRaUpd3g"  titleKey="IuFOgInterSgsnRaUpd3g"  sortable="true" />
				</c:when>
			    <c:when test="${linkReport=='sgsn-rau-intra-pp-inter'}">
					<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="interPpSgsnRauSr"  titleKey="Inter PP Rau Sr(%)"  sortable="true" />
					<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="successInterPpSgsnRau"  titleKey="SuceessInterPpSgsnRau"  sortable="true" />
					<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="requestInterPpSgsnRau"  titleKey="RequestInterPpSgsnRau"  sortable="true" />
					<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="inePpRauFail"  titleKey="InePpRauFail"  sortable="true" />
					<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="inePpRauIllegalMs"  titleKey="InePpRauIllegalMs"  sortable="true" />
					<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="inePpRauIllegalMe"  titleKey="InePpRauIllegalMe"  sortable="true" />
					<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="inePpRauSimNotProv"  titleKey="InePpRauSimNotProv"  sortable="true" />
					<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="inePpRauSerNonserNa"  titleKey="InePpRauSerNonserNa"  sortable="true" />
					<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="inePpRauMsIdentifyFail"  titleKey="InePpRauMsIdentifyFail"  sortable="true" />
					<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="inePpRauMsImpDetach"  titleKey="InePpRauMsImpDetach"  sortable="true" />
					<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="inePpRauPlmnNa"  titleKey="InePpRauPlmnNa"  sortable="true" />
					<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="inePpRauLaNa"  titleKey="InePpRauLaNa"  sortable="true" />
					<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="inePpRauRoamingNa"  titleKey="InePpRauRoamingNa"  sortable="true" />
					<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="inePpRauSerNaPlmn"  titleKey="InePpRauSerNaPlmn"  sortable="true" />
					<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="inePpRauNoCellInLa"  titleKey="InePpRauNoCellInLa"  sortable="true" />
					<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="inePpRauProErr"  titleKey="InePpRauProErr"  sortable="true" />
					<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="inePpRauOtherCause"  titleKey="InePpRauOtherCause"  sortable="true" />
					<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="inePpRauNetworkFail"  titleKey="InePpRauNetworkFail"  sortable="true" />
					<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="inePpRauProMsErr"  titleKey="InePpRauProMsErr"  sortable="true" />
					<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="inePpRauProRadioErr"  titleKey="InePpRauProRadioErr"  sortable="true" />
					<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="inePpRauProSgsnErr"  titleKey="InePpRauProSgsnErr"  sortable="true" />
					<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="inePpRauProCollisions"  titleKey="InePpRauProCollisions"  sortable="true" />
					<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="inePpRauProHlrVlrErr"  titleKey="InePpRauProHlrVlrErr"  sortable="true" />
					<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="fOutgInterPapuRaUpdat2g"  titleKey="FOutgInterPapuRaUpdat2g"  sortable="true" />
					<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="iuFOgIntePapu2g3gSho2g"  titleKey="IuFOgIntePapu2g3gSho2g"  sortable="true" />
					<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="fInterPapuRaUpdatImsi2g"  titleKey="FInterPapuRaUpdatImsi2g"  sortable="true" />
					<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="iuFOgIntePapu3g2gIsho3g"  titleKey="IuFOgIntePapu3g2gIsho3g"  sortable="true" />
					<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="iuFOgInterPapuRaUpd3g"  titleKey="IuFOgInterPapuRaUpd3g"  sortable="true" />

			    </c:when>
			    <c:otherwise>
					<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="intraPpRauSr"  titleKey="Intra PP Rau Sr(%)"  sortable="true" />
					<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="inaPpRauPeriRoutSucc"  titleKey="InaPpRauPeriRoutSucc"  sortable="true" />
					<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="inaPpRauPeriRoutReq"  titleKey="InaPpRauPeriRoutReq"  sortable="true" />
					<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="inaPpRauPeriRoutSucr"  titleKey="Periodic Routing sr(%)"  sortable="true" />
					<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="successIntraPpRau"  titleKey="SuceessIntraPpRau"  sortable="true" />
					<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="requestIntraPpRau"  titleKey="RequestIntraPpRau"  sortable="true" />
					<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="inaPpRauFail"  titleKey="InaPpRauFail"  sortable="true" />
					<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="inaPpRauIllegalMs"  titleKey="InaPpRauIllegalMs"  sortable="true" />
					<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="inaPpRauIllegalMe"  titleKey="InaPpRauIllegalMe"  sortable="true" />
					<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="inaPpRauSimNotProv"  titleKey="InaPpRauSimNotProv"  sortable="true" />
					<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="inaPpRauSerNonserNa"  titleKey="InaPpRauSerNonserNa"  sortable="true" />
					<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="inaPpRauMsIdentifyFail"  titleKey="InaPpRauMsIdentifyFail"  sortable="true" />
					<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="inaPpRauMsImpDetach"  titleKey="InaPpRauMsImpDetach"  sortable="true" />
					<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="inaPpRauPlmnNa"  titleKey="InaPpRauPlmnNa"  sortable="true" />
					<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="inaPpRauLaNa"  titleKey="InaPpRauLaNa"  sortable="true" />
					<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="inaPpRauRoamingNa"  titleKey="InaPpRauRoamingNa"  sortable="true" />
					<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="inaPpRauSerNaPlmn"  titleKey="InaPpRauSerNaPlmn"  sortable="true" />
					<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="inaPpRauNoCellInLa"  titleKey="InaPpRauNoCellInLa"  sortable="true" />
					<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="inaPpRauProErr"  titleKey="InaPpRauProErr"  sortable="true" />
					<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="inaPpRauOtherCause"  titleKey="InaPpRauOtherCause"  sortable="true" />
					<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="inaPpRauNetworkFail"  titleKey="InaPpRauNetworkFail"  sortable="true" />
					<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="inaPpRauProMsErr"  titleKey="InaPpRauProMsErr"  sortable="true" />
					<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="inaPpRauProRadioErr"  titleKey="InaPpRauProRadioErr"  sortable="true" />
					<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="inaPpRauProSgsnErr"  titleKey="InaPpRauProSgsnErr"  sortable="true" />
					<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="inaPpRauProCollisions"  titleKey="InaPpRauProCollisions"  sortable="true" />
					<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="inaPpRauProHlrVlrErr"  titleKey="InaPpRauProHlrVlrErr"  sortable="true" />
					<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="fIntraPapuRaUpdatImsi2g"  titleKey="FIntraPapuRaUpdatImsi2g"  sortable="true" />
					<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="iuFOgIntaPapu3g2gIsho3g"  titleKey="IuFOgIntaPapu3g2gIsho3g"  sortable="true" />
					<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="iuFOgIntraPapuRaUpd3g"  titleKey="IuFOgIntraPapuRaUpd3g"  sortable="true" />
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
