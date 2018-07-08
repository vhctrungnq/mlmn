<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<style type="text/css">    #doublescroll { overflow: auto; overflow-y: hidden; }    #doublescroll p { margin: 0; padding: 1em; white-space: nowrap; }</style>
<title>BSC DAY REPORT</title>
<content tag="heading">RNC LOAD ${title} REPORT</content>
<%@ include file="/WEB-INF/jsp/includes/filter2G.jsp" %>
<div  id="doublescroll">
		<display:table name="${dyBscList}" id="dyBscList" requestURI="" pagesize="100" class="simple2" export="true">
		    <%@ include file="/WEB-INF/jsp/includes/columnGeneralAll.jsp" %> 
		    <c:choose> 
			    <c:when test="${level=='dy'}"> 
				   	<display:column property="bscid" titleKey="Bscid" headerClass="hide" class="hide"/>
				    <display:column titleKey="Bscid" media="html" sortable="true" headerClass="master-header-1">
				   	 	<a href="${pageContext.request.contextPath}/report/core/${linkReport}/hr.htm?bscid=${dyBscList.bscid}&startDate=<fmt:formatDate pattern="dd/MM/yyyy" value="${dyBscList.day}"/>">${dyBscList.bscid}</a>
				    </display:column>
			    </c:when>
			    <c:otherwise>
	    			<display:column  property="bscid" titleKey="Bscid" sortable="true" headerClass="master-header-1"/>
			    </c:otherwise>
			</c:choose>
			<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="maxLoad"  titleKey="Max Load(%)"  sortable="true" />
			<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="avgLoad"  titleKey="Avg Load(%)"  sortable="true" />
			
			<c:if test="${level == 'dy'}"> 
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
</c:choose>
