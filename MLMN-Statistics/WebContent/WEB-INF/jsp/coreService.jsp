<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<title>core service</title>
<content tag="heading">Quản lý tiến trình Core Service</content>

<c:choose>
	<c:when test="${serviceStarted}">
		CoreService is running. <a href="stop.htm">Stop</a>
	</c:when>
	<c:otherwise>
		CoreService is stop. <a href="start.htm">Start</a>
	</c:otherwise>
</c:choose>

<br />

<c:forEach var="att" items="${attList}">
    ${att.name}: ${att.value}<br />
</c:forEach>
