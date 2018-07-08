<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<title>IPBB DAY REPORT</title>
<content tag="heading">IPBB ${title} REPORT</content>    
<%@ include file="/WEB-INF/jsp/includes/filterCsCore.jsp" %>
<div  id="doublescroll">
		<display:table name="${dyIpbbList}" id="dyIpbbList" requestURI="" pagesize="100" class="simple2" export="true">
		    <%@ include file="/WEB-INF/jsp/includes/columnGeneralIpbb.jsp" %>
		    
		    <display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="maxInBound"  titleKey="Inbound Max"  sortable="true" />
			<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="maxOutBound"  titleKey="Outbound Max"  sortable="true" />
			<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="avgInBound"  titleKey="Inbound AVG"  sortable="true" />
			<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="avgOutBound"  titleKey="Outbound AVG"  sortable="true" />
			</display:table>
	</div>
 <c:choose> 
 	<c:when test="${level=='hr'}">
 	<div id="trafChart" style="width: 100%; margin: 1em auto"></div>
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
