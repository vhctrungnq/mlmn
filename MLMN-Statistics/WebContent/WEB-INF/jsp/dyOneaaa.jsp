<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<style type="text/css">    #doublescroll { overflow: auto; overflow-y: hidden; }    #doublescroll p { margin: 0; padding: 1em; white-space: nowrap; }</style>
<title>CSI CORE REPORT</title>
<content tag="heading">ONEAAA ${title} REPORT</content>
<%@ include file="/WEB-INF/jsp/includes/filterCsCore.jsp" %>
<div  id="doublescroll">
		<display:table name="${dyCsiList}" id="dyCsiList" requestURI="" pagesize="100" class="simple2" export="true">
		    <%@ include file="/WEB-INF/jsp/includes/columnGeneralAll.jsp" %>
		    <display:column property="nodeid" titleKey="nodeid" headerClass="hide" class="hide"/>
			<c:if test="${level == 'dy'}">
				<display:column property="nodeid" titleKey="Nodeid" headerClass="hide" class="hide"/>
			    <display:column titleKey="Nodeid" media="html" sortable="true" headerClass="master-header-1">
			   	 	<a href="${pageContext.request.contextPath}/report/core/${linkReport}/hr.htm?nodeid=${dyCsiList.nodeid}&startDate=<fmt:formatDate pattern="dd/MM/yyyy" value="${dyCsiList.day}"/>">${dyCsiList.nodeid}</a>
			    </display:column>
			</c:if>
			<c:if test="${level != 'dy'}">
					<display:column  property="nodeid" titleKey="nodeid" sortable="true" headerClass="master-header-1"/>
			</c:if>
			<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="inaccessrequestavg"  titleKey="InAccessRequest"  sortable="true" headerClass="master-header-3"/>
			<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="outaccessacceptavg"  titleKey="OutAccessAccept"  sortable="true" headerClass="master-header-3"/>
			<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="outacessrejectavg"  titleKey="OutAcessReject"  sortable="true" headerClass="master-header-3"/>
			<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="inaccountingavg"  titleKey="InAccounting"  sortable="true" headerClass="master-header-3"/>
			<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="outaccountingresponseavg"  titleKey="OutAccountingResponse"  sortable="true" headerClass="master-header-3"/>
			<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="outaccoutingavg"  titleKey="OutAccouting"  sortable="true" headerClass="master-header-3"/>
			<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="inaccoutingresponseavg"  titleKey="InAccoutingResponse"  sortable="true" headerClass="master-header-3"/>
			<display:column class="AAALOAD rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="aaaLoad"  titleKey="AAA_LOAD%"  sortable="true" headerClass="master-header-3"/>
			<display:column class="AAARESSR rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="aaaRessr"  titleKey=" AAA_RESSR%"  sortable="true" headerClass="master-header-3"/>
			
			<c:if test="${level != 'hr'}">
				<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="dataload"  titleKey="Dataload"  sortable="true" headerClass="master-header-3"/>
			</c:if>
			</display:table>
	</div>
<c:if test="${level == 'dy'}">	
	<table style="width:99%">
		<tr>
			<td><div id="chart1" style="width: 100%; margin: 1em auto"></div></td>
		</tr>
	</table>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/text_date.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/highcharts.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/exporting.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/themes/grid.js"></script>
${chartdiv1}
</c:if>
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
