<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<title>MSC DAY REPORT</title>
<content tag="heading"> S7LINKSET ${title} REPORT</content>    
<%@ include file="/WEB-INF/jsp/includes/filterCsCore.jsp" %>
<div  id="doublescroll">
		<display:table name="${dyMscList}" id="dyMscList" requestURI="" pagesize="100" class="simple2" export="true">
		    <%@ include file="/WEB-INF/jsp/includes/columnGeneral.jsp" %>
		    <display:column  property="linksetid" titleKey="linksetid" sortable="true" headerClass="master-header-1"/>
		    <display:column  property="linksetName" titleKey="Linkset Name" sortable="true" headerClass="master-header-1"/>
		    <display:column  property="type" titleKey="Type" sortable="true" headerClass="hide" class="hide"/>
		    <display:column titleKey="Type" media="html" sortable="true" headerClass="master-header-1">
			   	 	<a href="${pageContext.request.contextPath}/report/core/${linkReport}/hr.htm?mscid=${dyMscList.mscid}&linkid=${dyMscList.linksetid}&linksetname=${dyMscList.linksetName}&type=${dyMscList.type}&startDate=<fmt:formatDate pattern="dd/MM/yyyy" value="${dyMscList.day}"/>">${dyMscList.type}</a>
			 </display:column>
			<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator"  property ="msu"  titleKey="Msu"  sortable="true" />
			<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="slunavdur"  titleKey="Slunavdur"  sortable="true" />
			<c:if test="${level == 'hr'||level == 'dy'}">
				<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="msutx"  titleKey="Msutx"  sortable="true"  />
				<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="msurx"  titleKey="Msurx"  sortable="true" />
				<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="sifsiotx"  titleKey="Sifsiotx"  sortable="true" />
			</c:if>
			 <c:if test="${level == 'dy'}">
			  	<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="bh"  titleKey="BH"  sortable="true" headerClass="master-header-4" />
				<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="bhSifsiotx"  titleKey="BH Sifsiotx"  sortable="true" headerClass="master-header-4" />
				<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="bhSifsiorx"  titleKey="BH Sifsiorx"  sortable="true" headerClass="master-header-4" />
				<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="bhSifsio"  titleKey="BH Sifsio"  sortable="true" headerClass="master-header-4" />
			</c:if>
			<c:if test="${level != 'hr'}">
				<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="bhMsutx"  titleKey="BH Msutx"  sortable="true" headerClass="master-header-4" />
				<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="bhMsurx"  titleKey="BH Msurx"  sortable="true" headerClass="master-header-4" />
				<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="bhMsu"  titleKey="BH Msu"  sortable="true" headerClass="master-header-4" />
				<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="bhUtil"  titleKey="BH Util(%)"  sortable="true" headerClass="master-header-4" />
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
