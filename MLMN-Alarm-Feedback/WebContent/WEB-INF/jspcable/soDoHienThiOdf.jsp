<%@ include file="/includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<title><fmt:message key="displayDiagramOdf.titleList"/></title>
<content tag="heading"><fmt:message key="displayDiagramOdf.titleList"/></content>

<form:form id="filterController" method="post" action="list.htm">
	<div style="padding-bottom: 10px;">
		<fmt:message key="odfLienTang.tenSoDo"/>&nbsp;
		<select id="idOdfTypes" name="idOdfTypes" class="wid30">
			<option value="">--Tất cả--</option>
			<c:forEach var="items" items="${cableOdfTypesList}">
              <c:choose>
                <c:when test="${items.id == idOdfTypesCBB}">
                    <option value="${items.id}" selected="selected">${items.schemaName}</option>
                </c:when>
                <c:otherwise>
                    <option value="${items.id}">${items.schemaName}</option>
                </c:otherwise>
              </c:choose>
		    </c:forEach>
		</select>&nbsp;&nbsp;
		<input class="button" type="submit" name="filter" value="Hiển thị" />
	</div>
</form:form>
<div>
	${html}
</div>


<link rel="stylesheet" type="text/css" media="screen" href="${pageContext.request.contextPath}/js/tinyTips/tinyTips.css" />
<!-- <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.4/jquery.min.js"></script> -->
<script type="text/javascript" src="${pageContext.request.contextPath}/js/tinyTips/jquery.tinyTips.js"></script>

<!-- <script type="text/javascript">
$(document).ready(function() { -->
	${tinyTips}
<!-- });
</script> -->

