<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<title>SGSN DAY REPORT</title>
<content tag="heading">SGSN RAU 3G ${title} REPORT</content>    
<%@ include file="/WEB-INF/jsp/includes/filterCsCore.jsp" %>
<div  id="doublescroll">
		<display:table name="${dySgsnList}" id="dySgsnList" requestURI="" pagesize="100" class="simple2" export="true">
		    <%@ include file="/WEB-INF/jsp/includes/columnGeneralPs.jsp" %>
		    <c:choose>
		    <c:when test="${linkReport=='sgsn-rau-3g'}">
				<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="suceessInterRau3g"  titleKey="SuceessInterRau3g"  sortable="true" />
				<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="requestInterSgsnRau3g"  titleKey="RequestInterSgsnRau3g"  sortable="true" />
				<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="interRauSr"  titleKey="InterRauSr"  sortable="true" />
				<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="ineRauFail3g"  titleKey="IneRauFail3g"  sortable="true" />
				<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="ineRauIllegalMs3g"  titleKey="IneRauIllegalMs3g"  sortable="true" />
				<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="ineRauIllegalMe3g"  titleKey="IneRauIllegalMe3g"  sortable="true" />
				<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="ineRauSimNotProv3g"  titleKey="IneRauSimNotProv3g"  sortable="true" />
				<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="ineRauSerNonserNa3g"  titleKey="IneRauSerNonserNa3g"  sortable="true" />
				<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="ineRauMsIdentifyFail3g"  titleKey="IneRauMsIdentifyFail3g"  sortable="true" />
				<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="ineRauMsImpDetach3g"  titleKey="IneRauMsImpDetach3g"  sortable="true" />
				<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="ineRauPlmnNa3g"  titleKey="IneRauPlmnNa3g"  sortable="true" />
				<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="ineRauLaNa3g"  titleKey="IneRauLaNa3g"  sortable="true" />
				<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="ineRauRoamingNa3g"  titleKey="IneRauRoamingNa3g"  sortable="true" />
				<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="ineRauSerNaPlmn3g"  titleKey="IneRauSerNaPlmn3g"  sortable="true" />
				<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="ineRauNoCellInLa3g"  titleKey="IneRauNoCellInLa3g"  sortable="true" />
				<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="ineRauProErr3g"  titleKey="IneRauProErr3g"  sortable="true" />
				<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="ineRauOtherCause3g"  titleKey="IneRauOtherCause3g"  sortable="true" />
				<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="ineRauNetworkFail3g"  titleKey="IneRauNetworkFail3g"  sortable="true" />
				<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="ineRauProMsErr3g"  titleKey="IneRauProMsErr3g"  sortable="true" />
				<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="ineRauProRadioErr3g"  titleKey="IneRauProRadioErr3g"  sortable="true" />
				<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="ineRauProSgsnErr3g"  titleKey="IneRauProSgsnErr3g"  sortable="true" />
				<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="ineRauProCollisions3g"  titleKey="IneRauProCollisions3g"  sortable="true" />
				<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="ineRauProHlrVlrErr3g"  titleKey="IneRauProHlrVlrErr3g"  sortable="true" />
				<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="iuFOgInteSgsn3g2gIsho3g"  titleKey="IuFOgInteSgsn3g2gIsho3g"  sortable="true" />
				<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="iuFOgInterSgsnRaUpd3g"  titleKey="IuFOgInterSgsnRaUpd3g"  sortable="true" />
			</c:when>
			 <c:when test="${linkReport=='sgsn-rau-3g-intra-pp-inter'}">
			    <display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="inePpRauFail3g"  titleKey="InePpRauFail3g"  sortable="true" />
				<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="inePpRauIllegalMs3g"  titleKey="InePpRauIllegalMs3g"  sortable="true" />
				<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="inePpRauIllegalMe3g"  titleKey="InePpRauIllegalMe3g"  sortable="true" />
				<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="inePpRauSimNotProv3g"  titleKey="InePpRauSimNotProv3g"  sortable="true" />
				<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="inePpRauSerNonserNa3g"  titleKey="InePpRauSerNonserNa3g"  sortable="true" />
				<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="inePpRauMsIdentifyFail3g"  titleKey="InePpRauMsIdentifyFail3g"  sortable="true" />
				<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="inePpRauMsImpDetach3g"  titleKey="InePpRauMsImpDetach3g"  sortable="true" />
				<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="inePpRauPlmnNa3g"  titleKey="InePpRauPlmnNa3g"  sortable="true" />
				<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="inePpRauLaNa3g"  titleKey="InePpRauLaNa3g"  sortable="true" />
				<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="inePpRauRoamingNa3g"  titleKey="InePpRauRoamingNa3g"  sortable="true" />
				<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="inePpRauSerNaPlmn3g"  titleKey="InePpRauSerNaPlmn3g"  sortable="true" />
				<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="inePpRauNoCellInLa3g"  titleKey="InePpRauNoCellInLa3g"  sortable="true" />
				<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="inePpRauProErr3g"  titleKey="InePpRauProErr3g"  sortable="true" />
				<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="inePpRauOtherCause3g"  titleKey="InePpRauOtherCause3g"  sortable="true" />
				<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="inePpRauNetworkFail3g"  titleKey="InePpRauNetworkFail3g"  sortable="true" />
				<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="inePpRauProMsErr3g"  titleKey="InePpRauProMsErr3g"  sortable="true" />
				<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="inePpRauProRadioErr3g"  titleKey="InePpRauProRadioErr3g"  sortable="true" />
				<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="inePpRauProSgsnErr3g"  titleKey="InePpRauProSgsnErr3g"  sortable="true" />
				<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="inePpRauProCollisions3g"  titleKey="InePpRauProCollisions3g"  sortable="true" />
				<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="inePpRauProHlrVlrErr3g"  titleKey="InePpRauProHlrVlrErr3g"  sortable="true" />
				<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="iuFOgIntePapu3g2gIsho3g"  titleKey="IuFOgIntePapu3g2gIsho3g"  sortable="true" />
				<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="iuFOgInterPapuRaUpd3g"  titleKey="IuFOgInterPapuRaUpd3g"  sortable="true" />
			</c:when>
			<c:otherwise>
			    <display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="successIntraPpRau3g"  titleKey="SuccessIntraPpRau3g"  sortable="true" />
				<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="requestIntraPpRau3g"  titleKey="RequestIntraPpRau3g"  sortable="true" />
				<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="intraPpRauSr3g"  titleKey="IntraPpRauSr3g"  sortable="true" />
				<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="successInterPpSgsnRau3g"  titleKey="SuccessInterPpSgsnRau3g"  sortable="true" />
				<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="requestInterPpSgsnRau3g"  titleKey="RequestInterPpSgsnRau3g"  sortable="true" />
				<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="interPpSgsnRauSr3g"  titleKey="InterPpSgsnRauSr3g"  sortable="true" />
				<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="inaPpRauPeriRoutSucc3g"  titleKey="InaPpRauPeriRoutSucc3g"  sortable="true" />
				<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="inaPpRauPeriRoutReq3g"  titleKey="InaPpRauPeriRoutReq3g"  sortable="true" />
				<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="inaPpRauPeriRoutSucr3g"  titleKey="InaPpRauPeriRoutSucr3g"  sortable="true" />
				<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="inaPpRauFail3g"  titleKey="InaPpRauFail3g"  sortable="true" />
				<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="inaPpRauIllegalMs3g"  titleKey="InaPpRauIllegalMs3g"  sortable="true" />
				<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="inaPpRauIllegalMe3g"  titleKey="InaPpRauIllegalMe3g"  sortable="true" />
				<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="inaPpRauSimNotProv3g"  titleKey="InaPpRauSimNotProv3g"  sortable="true" />
				<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="inaPpRauSerNonserNa3g"  titleKey="InaPpRauSerNonserNa3g"  sortable="true" />
				<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="inaPpRauMsIdentifyFail3g"  titleKey="InaPpRauMsIdentifyFail3g"  sortable="true" />
				<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="inaPpRauMsImpDetach3g"  titleKey="InaPpRauMsImpDetach3g"  sortable="true" />
				<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="inaPpRauPlmnNa3g"  titleKey="InaPpRauPlmnNa3g"  sortable="true" />
				<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="inaPpRauLaNa3g"  titleKey="InaPpRauLaNa3g"  sortable="true" />
				<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="inaPpRauRoamingNa3g"  titleKey="InaPpRauRoamingNa3g"  sortable="true" />
				<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="inaPpRauSerNaPlmn3g"  titleKey="InaPpRauSerNaPlmn3g"  sortable="true" />
				<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="inaPpRauNoCellInLa3g"  titleKey="InaPpRauNoCellInLa3g"  sortable="true" />
				<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="inaPpRauProErr3g"  titleKey="InaPpRauProErr3g"  sortable="true" />
				<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="inaPpRauOtherCause3g"  titleKey="InaPpRauOtherCause3g"  sortable="true" />
				<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="inaPpRauNetworkFail3g"  titleKey="InaPpRauNetworkFail3g"  sortable="true" />
				<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="inaPpRauProMsErr3g"  titleKey="InaPpRauProMsErr3g"  sortable="true" />
				<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="inaPpRauProRadioErr3g"  titleKey="InaPpRauProRadioErr3g"  sortable="true" />
				<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="inaPpRauProSgsnErr3g"  titleKey="InaPpRauProSgsnErr3g"  sortable="true" />
				<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="inaPpRauProCollisions3g"  titleKey="InaPpRauProCollisions3g"  sortable="true" />
				<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="inaPpRauProHlrVlrErr3g"  titleKey="InaPpRauProHlrVlrErr3g"  sortable="true" />
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
