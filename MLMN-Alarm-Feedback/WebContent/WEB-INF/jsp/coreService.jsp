<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<title><spring:message code="title.qlTienTrinh"/></title>
<content tag="heading"><spring:message code="title.qlTienTrinh"/></content>
<form:form id="coreServiceController" method="post" action="view.htm">
<c:choose>
	<c:when test="${serviceStarted}">
		CoreService is running. <a href="stop.htm">Stop</a>
	</c:when>
	<c:otherwise>
		CoreService is stop. <a href="start.htm">Start</a>
	</c:otherwise>
</c:choose>
</form:form>
<c:forEach var="att" items="${attList}">
    ${att.name}: ${att.value}<br />
</c:forEach>
<script type="text/javascript">

	setTimeout(function(){
		$('#coreServiceController').submit();
	}, 
	10 * 1000);
</script>