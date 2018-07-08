<%@ include file="/commons/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%-- Error Messages --%>
<c:if test="${not empty errors}">
    <div class="error" id="errorMessages">
        <c:forEach var="error" items="${errors}">
            <img src="${pageContext.request.contextPath}/images/iconWarning.gif"
                alt="warning" class="icon" />
            <c:out value="${error}"/><br />
        </c:forEach>
    </div>
    <c:remove var="errors"/>
</c:if>

<%-- Success Messages Title --%>
<c:if test="${not empty successMessagesTitle}">
    <div class="message" id="successMessagesTitle">
        <c:forEach var="msg" items="${successMessagesTitle}">
            <img src="${pageContext.request.contextPath}/images/iconInformation.gif"
                alt="information>" class="icon" />
            <c:out value="${msg}"/><br />
        </c:forEach>
    </div>
    <c:remove var="successMessagesTitle" scope="session"/>
</c:if>

<%-- Success Messages Key --%>
<c:if test="${not empty successMessagesKey}">
    <div class="message" id="successMessagesKey">
        <c:forEach var="msg" items="${successMessagesKey}">
            <img src="${pageContext.request.contextPath}/images/iconInformation.gif"
                alt="information>" class="icon" />
            		<c:choose>
						<c:when test="${empty msg}">
							${msg}
						</c:when>
						<c:otherwise>
							<fmt:message key="${msg}"/>
						</c:otherwise>
					</c:choose>
					<br />
        </c:forEach>
    </div>
    <c:remove var="successMessagesKey" scope="session"/>
</c:if>