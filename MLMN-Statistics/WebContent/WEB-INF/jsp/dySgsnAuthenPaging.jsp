<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<title>SGSN DAY REPORT</title>
<c:choose> 
<c:when test="${linkReport=='sgsn-paging'}">
		<content tag="heading">SGSN PAGING ${title} REPORT</content>   
</c:when>
<c:otherwise> 
	<content tag="heading">SGSN AUTHENTICATION ${title} REPORT</content>   
</c:otherwise>
</c:choose>
<%@ include file="/WEB-INF/jsp/includes/filterCsCore.jsp" %>
<div  id="doublescroll">
		<display:table name="${dySgsnList}" id="dySgsnList" requestURI=""  pagesize="100" class="simple2" export="true">
		    <%@ include file="/WEB-INF/jsp/includes/columnGeneralPs.jsp" %>
		    <c:choose> 
		 	<c:when test="${linkReport=='sgsn-paging'}">
		        <display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="pagingRequest2g"  titleKey="Paging Request 2G"  sortable="true" />
				<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="pagingSuccess2g"  titleKey="Paging Sucs 2G"  sortable="true" />
				<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="pagingSucr2g"  titleKey="Paging Sucr 2G(%)"  sortable="true" />
				<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="pagingRequest3g"  titleKey="Pagign Request 3G"  sortable="true" />
				<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="pagingSuccess3g"  titleKey="Paging Sucs 3G"  sortable="true" />
				<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="pagingSucr3g"  titleKey="Pagign Sucr 3G(%)"  sortable="true" />
				<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="pagingRequest"  titleKey="Paging Request 2G/3G"  sortable="true" />
				<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="pagingSuccess"  titleKey="Paging Sucs 2G/3G"  sortable="true" />
				<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="pagingSucr"  titleKey="Paging Sucr 2G/3G(%)"  sortable="true" />
				
		    </c:when>
		     <c:otherwise>
					<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="authenRequest2g"  titleKey="Authen Request 2G"  sortable="true" />
					<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="authenSuccess2g"  titleKey="Authen Sucs 2G"  sortable="true" />
					<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="authenSucr2g"  titleKey="Authen Sucr 2G(%)"  sortable="true" />
					<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="authenRequest3g"  titleKey="Authen Request 3G"  sortable="true" />
					<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="authenSuccess3g"  titleKey="Authen Sucs 3G"  sortable="true" />
					<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="authenSucr3g"  titleKey="Authen Sucr 3G(%)"  sortable="true" />
					<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="authenRequest"  titleKey="Authen Request 2G/3G "  sortable="true" />
					<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="authenSuccess"  titleKey="Authen Sucr 2G/3G"  sortable="true" />
					<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="authenSucr"  titleKey="Auth Sucr 2G/3G (%)"  sortable="true" />
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
