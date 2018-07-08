<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<style type="text/css">    #doublescroll { overflow: auto; overflow-y: hidden; }    #doublescroll p { margin: 0; padding: 1em; white-space: nowrap; }</style>
<title>CSI CORE REPORT</title>
<content tag="heading">CSI CMD ${title} REPORT</content>
<%@ include file="/WEB-INF/jsp/includes/filterCsCore.jsp" %>
<div  id="doublescroll">
		<display:table name="${dyCsiList}" id="dyCsiList" requestURI="" pagesize="100" class="simple2" export="true">
		    <%@ include file="/WEB-INF/jsp/includes/columnGeneralAll.jsp" %>
		    <c:if test="${level == 'dy'}">
				<display:column property="type" titleKey="Type" headerClass="hide" class="hide"/>
			    <display:column titleKey="Type" media="html" sortable="true" headerClass="master-header-1">
			   	 	<a href="${pageContext.request.contextPath}/report/core/${linkReport}/hr.htm?nodeid=${dyCsiList.type}&startDate=<fmt:formatDate pattern="dd/MM/yyyy" value="${dyCsiList.day}"/>">${dyCsiList.type}</a>
			    </display:column>
			</c:if>
			<c:if test="${level != 'dy'}">
					<display:column  property="type" titleKey="Type" sortable="true" headerClass="master-header-1"/>
			</c:if>
			<display:column class="MEM rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="memused"  titleKey="Memory used(%)"  sortable="true" headerClass="master-header-3"/>

			<display:column class="CPU rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="cpuused"  titleKey="CPU used(%)"  sortable="true" headerClass="master-header-3"/>
			<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="userRate"  titleKey="UserRate(%)"  sortable="true" headerClass="master-header-3"/>
			<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="niceRate"  titleKey="NiceRate(%)"  sortable="true" headerClass="master-header-3"/>
			<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="sysRate"  titleKey="System(%)"  sortable="true" headerClass="master-header-3"/>
			<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="iowait"  titleKey="IO wait"  sortable="true" headerClass="master-header-3"/>
			<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="steal"  titleKey="Steal"  sortable="true" headerClass="master-header-3"/>
			<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="idle"  titleKey="Idle"  sortable="true" headerClass="master-header-3"/>
			
			<c:if test="${level != 'hr'}">
				<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="grandTotal"  titleKey="GradTotal"  sortable="true" headerClass="master-header-3"/>
				<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="subTotal"  titleKey="SubTotal"  sortable="true" headerClass="master-header-3"/>
				<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="dataload"  titleKey="Dataload(%)"  sortable="true" headerClass="master-header-3"/>
				
			</c:if>
			</display:table>
	</div>
<c:if test="${level == 'dy'}">	
	<table style="width:99%">
		<tr>
			<td><div id="chart1" style="width: 100%; margin: 1em auto"></div></td>
		</tr>
		<tr>
			<td><div id="chart2" style="width: 100%; margin: 1em auto"></div></td>
		</tr>
	</table>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/text_date.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/highcharts.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/exporting.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/themes/grid.js"></script>
${chartdiv1}
${chartdiv2}
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
