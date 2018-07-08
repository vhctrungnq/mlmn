<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<style type="text/css">    #doublescroll { overflow: auto; overflow-y: hidden; }    #doublescroll p { margin: 0; padding: 1em; white-space: nowrap; }</style>
    
<ul class="ui-tabs-nav">
   <li class="${hr }"><a href="${pageContext.request.contextPath}/report/core/${linkReport}/hr.htm"><span>Báo cáo giờ</span></a></li>
   <li class="${dy }"><a href="${pageContext.request.contextPath}/report/core/${linkReport}/dy.htm"><span>Báo cáo ngày</span></a></li>
   <li class="${wk }"><a href="${pageContext.request.contextPath}/report/core/${linkReport}/wk.htm"><span>Báo cáo tuần</span></a></li>
   <li class="${mn }"><a href="${pageContext.request.contextPath}/report/core/${linkReport}/mn.htm"><span>Báo cáo tháng</span></a></li>
</ul>
 <c:choose> 
 	<c:when test="${level=='hr'}">
         <%@ include file="/WEB-INF/jsp/includes/filterHourly.jsp" %>
    </c:when>
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