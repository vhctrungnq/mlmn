<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<title>MSC DAY REPORT</title>
<content tag="heading">MSC CIPHER ${title} REPORT</content>
<%@ include file="/WEB-INF/jsp/includes/filterCsCore.jsp" %>
<div  id="doublescroll">
		<display:table name="${dyMscList}" id="dyMscList" requestURI="" pagesize="100" class="simple2" export="true">
		    <%@ include file="/WEB-INF/jsp/includes/columnGeneral.jsp" %>
		    <display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="cipheratts"  titleKey="Cipher Att"  sortable="true" />
		<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="ciphersucs"  titleKey="Cipher Succ"  sortable="true" />
		<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="ciphersucr"  titleKey="Cipher Succr(%)"  sortable="true" />

			<c:if test="${level == 'dy'}">
			  	<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="bh"  titleKey="BH"  sortable="true" headerClass="master-header-4" />
			</c:if>
			<c:if test="${level != 'hr'}">
				<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="bhCipheratts"  titleKey="BH Cipher Att"  sortable="true" headerClass="master-header-4"/>
				<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="bhCiphersucs"  titleKey="BH Cipher Succ"  sortable="true" headerClass="master-header-4"/>
				<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="bhCiphersucr"  titleKey="BH Cipher Succr(%)"  sortable="true" headerClass="master-header-4"/>
				<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="grandTotal"  titleKey="GrandTotal"  sortable="true" headerClass="master-header-3"/>
				<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="subTotal"  titleKey="SubTotal"  sortable="true" headerClass="master-header-3"/>
				<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="dataload"  titleKey="Dataload"  sortable="true" headerClass="master-header-3"/>
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
