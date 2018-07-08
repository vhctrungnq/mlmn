<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<title>MSC DAY REPORT</title>
<content tag="heading">NETWORK QUALITY ${title} REPORT</content>    
<style type="text/css">    #doublescroll { overflow: auto; overflow-y: hidden; }    #doublescroll p { margin: 0; padding: 1em; white-space: nowrap; }</style>
    
<ul class="ui-tabs-nav">
   <li class="${dy }"><a href="${pageContext.request.contextPath}/report/core/${linkReport}/dy.htm"><span>Báo cáo ngày</span></a></li>
   <li class="${wk }"><a href="${pageContext.request.contextPath}/report/core/${linkReport}/wk.htm"><span>Báo cáo tuần</span></a></li>
   <li class="${mn }"><a href="${pageContext.request.contextPath}/report/core/${linkReport}/mn.htm"><span>Báo cáo tháng</span></a></li>
</ul>
 <c:choose> 
    <c:when test="${level=='dy'}">
         <%@ include file="/WEB-INF/jsp/includes/filterDaily.jsp" %>
    </c:when>
    <c:when test="${level=='wk'}">
         <%@ include file="/WEB-INF/jsp/includes/filterWeekly.jsp" %>
    </c:when>
    <c:otherwise>
        <%@ include file="/WEB-INF/jsp/includes/filterMonthly.jsp" %>
    </c:otherwise>
</c:choose>
<div  id="doublescroll">
		<display:table name="${dyMscList}" id="dyMscList" requestURI="" pagesize="100" class="simple2" export="true">
		    <display:column property="region" titleKey="TT" sortable="true" class="TT" headerClass="master-header-1"/>
		    <c:choose> 
			    <c:when test="${level=='dy'}">
			    <display:column property="day" titleKey="Day" format="{0,date,dd/MM/yyyy}" sortable="true" headerClass="master-header-1"/>
			 	</c:when>
			    <c:when test="${level=='mn'}">
			        <display:column property="month" titleKey="Month"  sortable="true" headerClass="master-header-1"/>
	    			<display:column property="year" titleKey="Year"  sortable="true" headerClass="master-header-1"/>
			    </c:when>
			    <c:otherwise>
			        <display:column property="week" titleKey="Week"  sortable="true" headerClass="master-header-1"/>
	    			<display:column property="year" titleKey="Year"  sortable="true" headerClass="master-header-1"/>
			    </c:otherwise>
			</c:choose>
		    <display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="nbrRoute"  titleKey="NbrRoute"  sortable="true" />
			<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="nbrRoute70"  titleKey="NbrRoute70"  sortable="true" />
			<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="routeNr"  titleKey="RouteNr"  sortable="true" />
			<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="nbrLinkset"  titleKey="NbrLinkset"  sortable="true" />
			<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="nbrLinkset40"  titleKey="NbrLinkset40"  sortable="true" />
			<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="nbrConnection"  titleKey="NbrConnection"  sortable="true" />
			<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="nbrConnection70"  titleKey="NbrConnection70"  sortable="true" />
			<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="ipbackboneNr"  titleKey="IpbackboneNr"  sortable="true" />
		</display:table>
	</div>
 <c:choose> 
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
