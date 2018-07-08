<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<style type="text/css">    #doublescroll { overflow: auto; overflow-y: hidden; }    #doublescroll p { margin: 0; padding: 1em; white-space: nowrap; }</style>
<title>CSI CORE REPORT</title>
<content tag="heading">SRX ${title} REPORT</content>
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
			<display:column class="LOAD rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="load"  titleKey="Load(%)"  sortable="true" headerClass="master-header-3"/>
			<display:column class="UTIL rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="util"  titleKey="Util(%)"  sortable="true" headerClass="master-header-3"/>
			
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
